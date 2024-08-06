'use client';
import { Box, Button, Link, TextField } from '@mui/material';
import { SubmitHandler, useForm } from 'react-hook-form';

export interface Challenge {
  value: string;
}

export interface IError {
  timestamp: string;
  status: number;
  error: string;
  path: string;
}

export interface ICredentialCreationOptionsUser
  extends PublicKeyCredentialEntity {
  displayName: string;
  id: string;
}

export interface ICredentialOptionsCredentialDescriptor {
  id: string;
  transports?: AuthenticatorTransport[];
  type: PublicKeyCredentialType;
}

export interface ICredentialCreationOptions {
  attestation?: AttestationConveyancePreference;
  authenticatorSelection?: AuthenticatorSelectionCriteria;
  challenge: Challenge;
  excludeCredentials?: ICredentialOptionsCredentialDescriptor[];
  extensions?: AuthenticationExtensionsClientInputs;
  pubKeyCredParams: PublicKeyCredentialParameters[];
  rp: PublicKeyCredentialRpEntity;
  timeout?: number;
  user: ICredentialCreationOptionsUser;
}

export interface IAuthenticatorAttestationJSON {
  clientDataJSON: string;
  attestationObject: string;
  clientExtensionsJSON: string;
}

interface SignUpProp {
  container?: Element;
  onShowSignIn: () => void;
  onError: (message: string) => void;
  onSignUped: (session: unknown | undefined) => void;
}

const SignUp = (props: SignUpProp): JSX.Element => {
  interface Inputs {
    name: string;
    email: string;
  }

  const { register, handleSubmit } = useForm<Inputs>();

  async function attestationOptions<T>(
    email: string,
    displayName: string,
  ): Promise<T> {
    const options: T = await fetch('/api/attestation/options', {
      method: 'POST',
      body: JSON.stringify({
        email,
        displayName,
      }),
      headers: { 'Content-Type': 'application/json' },
    }).then((response) => response.json());
    return options;
  }

  async function registerFinish<T>(
    credential: IAuthenticatorAttestationJSON,
  ): Promise<T> {
    const response = await fetch('/api/attestation/result', {
      method: 'POST',
      body: JSON.stringify({
        clientDataJSON: credential.clientDataJSON,
        attestationObject: credential.attestationObject,
      }),
      headers: { 'Content-Type': 'application/json' },
    });
    return response.bodyUsed && response.body !== null
      ? response.json()
      : undefined;
  }

  const stringToArrayBuffer = (string: string): Uint8Array =>
    new TextEncoder().encode(string);

  const base64ToArrayBuffer = (base64String: string): Uint8Array =>
    Uint8Array.from(atob(base64String), (_) => _.charCodeAt(0));

  const convertCreateOptions = (
    source: ICredentialCreationOptions,
  ): CredentialCreationOptions => {
    const excludeCredentials = source.excludeCredentials || [];

    const credentialCreationOptions: PublicKeyCredentialCreationOptions = {
      attestation: source.attestation,
      authenticatorSelection: source.authenticatorSelection,
      challenge: stringToArrayBuffer(source.challenge.value),
      excludeCredentials: excludeCredentials.map(
        (credential) =>
          ({
            id: base64ToArrayBuffer(credential.id),
            type: credential.type,
            transports: credential.transports,
          }) as PublicKeyCredentialDescriptor,
      ),
      extensions: source.extensions,
      pubKeyCredParams: source.pubKeyCredParams,
      rp: source.rp,
      timeout: source.timeout,
      user: {
        name: source.user.name,
        // icon: source.user.icon,
        displayName: source.user.displayName,
        id: base64ToArrayBuffer(source.user.id),
      },
    };
    return {
      publicKey: credentialCreationOptions,
    };
  };

  const arrayBufferToBase64 = (arrayBuffer: ArrayBuffer): string =>
    btoa(String.fromCharCode(...new Uint8Array(arrayBuffer)));

  const credentialToJSON = (
    credential: PublicKeyCredential,
  ): IAuthenticatorAttestationJSON => {
    const credentialJSON: IAuthenticatorAttestationJSON = {
      clientDataJSON: arrayBufferToBase64(credential.response.clientDataJSON),
      attestationObject: arrayBufferToBase64(
        (credential.response as AuthenticatorAttestationResponse)
          .attestationObject,
      ),
      clientExtensionsJSON: JSON.stringify(
        credential.getClientExtensionResults(),
      ),
    };
    return credentialJSON;
  };

  async function registerToServer(
    email: string,
    displayName: string,
  ): Promise<void> {
    if (window === undefined) {
      return;
    }
    if (!window.PublicKeyCredential) {
      props.onError('未対応のブラウザです');
      return;
    }

    try {
      const response: ICredentialCreationOptions =
        await attestationOptions<ICredentialCreationOptions>(
          email,
          displayName,
        );

      const options = convertCreateOptions(response);

      const credential: PublicKeyCredential = await navigator.credentials
        .create(options)
        .then((reply: Credential | null) => {
          if (reply != null) {
            return reply as PublicKeyCredential;
          }
          throw new Error('unmatched type');
        })
        .catch((reason) => {
          throw reason;
        });

      const credentialJSON = credentialToJSON(credential);

      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const ret = await registerFinish<any | IError>(credentialJSON);
      if (ret?.error !== undefined) {
        props.onError(ret.error);
      } else {
        props.onSignUped(ret);
      }
    } catch (error) {
      props.onError((error as Error).message);
    }
  }

  const onSubmit: SubmitHandler<Inputs> = (data: Inputs): void => {
    registerToServer(data.email, data.name).catch((error) => {
       
      console.error(error);
      props.onError(JSON.stringify(error, null, 2));
    });
  };

  return (
    <div>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Box>
          <TextField
            required={true}
            label="user name"
            {...register('name', { required: true })}
          />
        </Box>
        <Box>
          <TextField
            required={true}
            label="e-mail"
            {...register('email', { required: true })}
          />
        </Box>
        <Button type="submit" variant="contained" color="primary">
          サインアップ
        </Button>
      </form>
      <Box>アカウントは作成済みですか？</Box>
      <Link onClick={props.onShowSignIn} variant="body2">
        サインイン
      </Link>
    </div>
  );
};

export default SignUp;
