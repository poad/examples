package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.config.WebAuthnConfig;
import com.github.poad.examples.webauthn.entity.Credential;
import com.github.poad.examples.webauthn.entity.User;
import com.github.poad.examples.webauthn.repository.WebAuthnCredentialRepository;
import com.github.poad.examples.webauthn.repository.WebAuthnUserRepository;
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.authenticator.Authenticator;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.*;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.server.ServerProperty;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebAuthnAuthenticationService {

    private final WebAuthnUserRepository userRepository;
    private final WebAuthnCredentialRepository credentialRepository;
    private final WebAuthnConfig config;

    public WebAuthnAuthenticationService(WebAuthnUserRepository userRepository, WebAuthnCredentialRepository credentialRepository, WebAuthnConfig config) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.config = config;
    }

    public Optional<User> find(String email) {
        return userRepository.find(email);
    }

    public PublicKeyCredentialRequestOptions requestOptions(User user) {

        // challenge ── リプレイ攻撃を回避する乱数
        var challenge = new DefaultChallenge();

        // timeout ── 認証のタイムアウト時間（ミリ秒）
        var timeout = config.getTimeout();

        // rpId ── 中間者攻撃を回避するRPサーバの有効ドメインを指定
        var rpId = config.getRp().id();

        // allowCredentials ── RPサーバに登録されたクレデンシャルIDの一覧
        List<PublicKeyCredentialDescriptor> allowCredentials = List.of();
        if (user != null) {
            var credentials = credentialRepository.finds(user.getId());
            allowCredentials = credentials.stream()
                    .map(credential -> new PublicKeyCredentialDescriptor(
                            PublicKeyCredentialType.PUBLIC_KEY,
                            credential.getCredentialId(),
                            Set.of()))
                    .collect(Collectors.toList());
        }

        // userVerification ── 認証器の生体認証やPINを要求
        var userVerification = UserVerificationRequirement.DISCOURAGED;

        // extensions ── 登録の拡張機能
        // ※サンプルコードでは、認証器がWebAuthnのどの拡張機能に対応しているのかを調べる拡張機能 "exts" のコードを記載
        //   https://www.w3.org/TR/webauthn-1/#sctn-supported-extensions-extension
        var builder = new AuthenticationExtensionsClientInputs.BuilderForAuthentication();
        builder.setAppid(config.getOrigin().asStringWithoutPort());
        var extensions = builder.build();

        // 公開鍵クレデンシャル要求API（navigator.credentials.get）のパラメータを作成
        return new PublicKeyCredentialRequestOptions(
                challenge,
                timeout,
                rpId,
                allowCredentials,
                userVerification,
                extensions
        );
    }

    public User assertionFinish(
            Challenge challenge,
            byte[] credentialId,
            byte[] userHandle,
            byte[] authenticatorData,
            byte[] clientDataJSON,
            String clientExtensionsJSON,
            byte[] signature) {

        // originの検証 ── 中間者攻撃耐性
        var origin = Origin.create(config.getOrigin().asString());

        // rpIdHashの検証 ── 中間者攻撃耐性
        var rpId = config.getRp().id();

        // challengeの検証 ── リプレイ攻撃耐性
        var challengeBase64 = new DefaultChallenge(Base64
                .getEncoder()
                .encode(challenge.getValue()));

        byte[] tokenBindingId = null;

        var serverProperty = new ServerProperty(
                origin,
                rpId,
                challengeBase64,
                tokenBindingId);

        var authenticationRequest =
                new AuthenticationRequest(
                        credentialId,
                        userHandle,
                        authenticatorData,
                        clientDataJSON,
                        clientExtensionsJSON,
                        signature
                );

        // DBから登録済みの公開鍵クレデンシャルを取得
        var credential = credentialRepository.find(credentialId)
                .orElseThrow();

        var converter = new ObjectConverter();

        // 公開鍵クレデンシャルをバイナリからデシリアライズ
        //   ※サンプルコードでは、DB保存した公開鍵クレデンシャルにアテステーションも含まれる
        //     https://github.com/webauthn4j/webauthn4j/issues/148
        Authenticator authenticator = converter.getCborConverter().readValue(credential.getPublicKey(), AuthenticatorImpl.class);

        var manager = WebAuthnManager.createNonStrictWebAuthnManager(converter);
        var authenticationData = manager.parse(authenticationRequest);

        var params = new AuthenticationParameters(serverProperty, authenticator, false);

        // clientDataJSONの検証 ── 認証情報の生成に渡されたデータ
        // signatureの検証 ── 公開鍵による署名の検証
        // signCountの検証 ── クローン認証器の検出
        var response = manager.validate(authenticationData, params);

        // 署名カウンタの更新
        var currentCounter = response.getAuthenticatorData().getSignCount();
        credentialRepository.saveAndFlush(new Credential(credential.getCredentialId(), credential.getUser(), credential.getPublicKey(), currentCounter));

        return credential.getUser();
    }
}
