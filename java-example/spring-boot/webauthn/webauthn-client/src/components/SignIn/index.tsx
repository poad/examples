'use client';
import { Button, Link, TextField, Box } from '@mui/material';
import { SubmitHandler, useForm } from 'react-hook-form';

export interface Challenge {
  value: string;
}

export interface ICredentialOptionsCredentialDescriptor {
  id: string;
  transports?: AuthenticatorTransport[];
  type: PublicKeyCredentialType;
}

export interface IAuthenticatorAssertionJSON {
  credentialId: string;
  clientDataJSON: string;
  authenticatorData: string;
  signature: string;
  clientExtensionsJSON: string;
}

export interface ICredentialRequestOptions {
  challenge: Challenge;
  timeout?: number;
  allowCredentials?: ICredentialOptionsCredentialDescriptor[];
  extensions?: AuthenticationExtensionsClientInputs;
  rpId?: string;
  userVerification?: UserVerificationRequirement;
}

interface SignInProp {
  container?: Element;
  onAuthenticated: () => void;
  onError: (message: string) => void;
  onShowSignUp: () => void;
}

async function assertionOptions<T>(email: string): Promise<T> {
  const mail = email;
  return (
    await fetch('/api/assertion/options', {
      method: 'POST',
      body: JSON.stringify({
        email: mail,
      }),
      headers: { 'Content-Type': 'application/json' },
    })
  ).json();
}

async function authenticationFinish<T>(
  credential: IAuthenticatorAssertionJSON,
): Promise<T> {
  const response = await fetch('/api/assertion/result', {
    method: 'POST',
    body: JSON.stringify({
      clientDataJSON: credential.clientDataJSON,
      credentialId: credential.credentialId,
      authenticatorData: credential.authenticatorData,
      signature: credential.signature,
    }),
    headers: { 'Content-Type': 'application/json' },
  });
  return response.bodyUsed && response.body !== null
    ? response.json()
    : undefined;
}

const SignIn = (props: SignInProp): JSX.Element => {
  interface Inputs {
    email: string;
  }

  const { register, handleSubmit } = useForm<Inputs>();

  const stringToArrayBuffer = (string: string): Uint8Array =>
    new TextEncoder().encode(string);

  const base64ToArrayBuffer = (base64String: string): Uint8Array =>
    Uint8Array.from(atob(base64String), (_) => _.charCodeAt(0));

  const arrayBufferToBase64 = (arrayBuffer: ArrayBuffer): string =>
    btoa(String.fromCharCode(...new Uint8Array(arrayBuffer)));

  const credentialToJSON = async (
    credential: PublicKeyCredential,
  ): Promise<IAuthenticatorAssertionJSON> => {
    const response = credential.response as AuthenticatorAssertionResponse;
    const credentialJSON: IAuthenticatorAssertionJSON = {
      credentialId: arrayBufferToBase64(credential.rawId),
      clientDataJSON: arrayBufferToBase64(response.clientDataJSON),
      authenticatorData: arrayBufferToBase64(response.authenticatorData),
      signature: arrayBufferToBase64(response.signature),
      clientExtensionsJSON: JSON.stringify(
        credential.getClientExtensionResults(),
      ),
    };
    return credentialJSON;
  };

  const convertRequestOptions = (
    source: ICredentialRequestOptions,
  ): CredentialRequestOptions => {
    const allowCredentials =
      source.allowCredentials != null ? source.allowCredentials : [];

    const credentialRequestOptions: PublicKeyCredentialRequestOptions = {
      allowCredentials: allowCredentials.map(
        (credential) =>
          ({
            id: base64ToArrayBuffer(credential.id),
            type: credential.type,
            transports: credential.transports,
          }) as PublicKeyCredentialDescriptor,
      ),
      challenge: stringToArrayBuffer(source.challenge.value),
      extensions: source.extensions,
      rpId: source.rpId,
      timeout: source.timeout,
      userVerification: source.userVerification,
    };
    return {
      publicKey: credentialRequestOptions,
    };
  };

  async function authentication(email: string): Promise<void> {
    props.onError('');

    if (window === undefined) {
      return;
    }
    if (!window.PublicKeyCredential) {
      props.onError('未対応のブラウザです');
      return;
    }

    try {
      const response = await assertionOptions<ICredentialRequestOptions>(email);

      const options = convertRequestOptions(response);

      const credential = await navigator.credentials
        .get(options)
        .then((reply: Credential | null) => {
          if (reply !== null) {
            return reply as PublicKeyCredential;
          }
          throw new Error('unmatched type');
        })
        .catch((reason) => {
          throw reason;
        });

      const json = await credentialToJSON(credential);

      await authenticationFinish(json);
      props.onAuthenticated();
    } catch (error) {
      props.onError((error as Error).message);
    }
  }

  const onSubmit: SubmitHandler<Inputs> = (data: Inputs) => {
    authentication(data.email).catch((error) => {
      props.onError(JSON.stringify(error, null, 2));
    });
  };

  return (
    <div>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Box>
          <TextField
            required={true}
            label="e-mail"
            {...register('email', { required: true })}
          />
        </Box>
        <Button type="submit" variant="contained" color="primary">
          サインイン
        </Button>
      </form>
      <div>
        <Box>アカウントは作成済みですか？</Box>
        <Link onClick={props.onShowSignUp}>サインアップ</Link>
      </div>
    </div>
  );
};

export default SignIn;
