export interface Challenge {
  value: string
}

export interface CredentialCreationOptionsUser
  extends PublicKeyCredentialEntity {
  displayName: string
  id: string
}

export interface CredentialOptionsCredentialDescriptor {
  id: string
  transports?: AuthenticatorTransport[]
  type: PublicKeyCredentialType
}

export interface AuthenticatorAttestationJSON {
  clientDataJSON: string
  attestationObject: string
  clientExtensionsJSON: string
}

export interface AuthenticatorAssertionJSON {
  credentialId: string
  clientDataJSON: string
  authenticatorData: string
  signature: string
  clientExtensionsJSON: string
}

export interface CredentialCreationOptions {
  attestation?: AttestationConveyancePreference
  authenticatorSelection?: AuthenticatorSelectionCriteria
  challenge: Challenge
  excludeCredentials?: CredentialOptionsCredentialDescriptor[]
  extensions?: AuthenticationExtensionsClientInputs
  pubKeyCredParams: PublicKeyCredentialParameters[]
  rp: PublicKeyCredentialRpEntity
  timeout?: number
  user: CredentialCreationOptionsUser
}

export module WebAuthnClient {
  export interface CredentialRequestOptions {
    challenge: Challenge
    timeout?: number
    allowCredentials?: CredentialOptionsCredentialDescriptor[]
    extensions?: AuthenticationExtensionsClientInputs
    rpId?: string
    userVerification?: UserVerificationRequirement
  }

  interface Client {
    $attestationOptions<T>(email: string, displayName: string): Promise<T>
    $registerFinish<T>(credential: AuthenticatorAttestationJSON): Promise<T>
    $assertionOptions<T>(email: string): Promise<T>
    $authenticationFinish<T>(credential: AuthenticatorAssertionJSON): Promise<T>
  }
}

declare module 'vue/types/vue' {
  interface Vue {
    $attestationOptions<T>(email: string, displayName: string): Promise<T>
    $registerFinish<T>(credential: AuthenticatorAttestationJSON): Promise<T>
    $assertionOptions<T>(email: string): Promise<T>
    $authenticationFinish<T>(credential: AuthenticatorAssertionJSON): Promise<T>
  }
}
