<template>
  <v-container>
    <v-form ref="form" v-model="valid" :lazy-validation="lazy">
      <v-row>
        <v-col cols="24">
          <v-text-field
            id="displayName"
            v-model="displayName"
            label="user name"
            clearable="true"
            required
          />
          <v-text-field
            id="email"
            v-model="email"
            label="e-mail"
            clearable="true"
            required
          />
          <v-btn color="primary" @click="register">
            登録
          </v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
            <p class="message">
                アカウントは作成済みですか？
                <nuxt-link to="/login">
                    ログイン
                </nuxt-link>
            </p>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="24" sm="12">
          <v-alert v-show="!!status" :type="status" light="true">
            {{ message }}
          </v-alert>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script lang="ts">
import { Vue, Component, Emit } from 'vue-property-decorator'
import WebAuthnClient from '~/plugins/types'

@Component
export default class SignUp extends Vue {
  displayName: string = ''
  email: string = ''
  status: string = ''
  message: string = ''
  auth: Boolean = false

  @Emit()
  async register() {
    try {
      if (!window.PublicKeyCredential) {
        this.status = 'error'
        this.message = '未対応のブラウザです'
        return
      }

      const email: string = this.email
      const displayName: string = this.displayName
      const response: WebAuthnClient.CredentialCreationOptions = await this.$attestationOptions<
        WebAuthnClient.CredentialCreationOptions
      >(email, displayName)

      this.status = ''

      const options = this.convertCreateOptions(response)

      const credential: PublicKeyCredential = await navigator.credentials
        .create(options)
        .then((reply: Credential | null) => {
          if (reply != null) {
            return (reply as PublicKeyCredential)
          } else {
            throw new Error('unmatched type')
          }
        })
        .catch((reason) => {
          console.error(reason)
          throw reason
        })

      const credentialJSON = this.credentialToJSON(credential)

      const ret = await this.$registerFinish(credentialJSON)

      this.status = 'success'
      this.message = 'Registerd'

      return ret
    } catch (error) {
      this.status = 'error'
      this.message = error
      throw error
    }
  }

  private convertCreateOptions(
    source: WebAuthnClient.CredentialCreationOptions
  ): CredentialCreationOptions {
    const excludeCredentials =
      source.excludeCredentials != null ? source.excludeCredentials : []

    const credentialCreationOptions: PublicKeyCredentialCreationOptions = {
      attestation: source.attestation,
      authenticatorSelection: source.authenticatorSelection,
      challenge: this.stringToArrayBuffer(source.challenge.value),
      excludeCredentials: excludeCredentials.map((credential) => {
        return {
          id: this.base64ToArrayBuffer(credential.id),
          type: credential.type,
          transports: credential.transports
        } as PublicKeyCredentialDescriptor
      }),
      extensions: source.extensions,
      pubKeyCredParams: source.pubKeyCredParams,
      rp: source.rp,
      timeout: source.timeout,
      user: {
        name: source.user.name,
        icon: source.user.icon,
        displayName: source.user.displayName,
        id: this.base64ToArrayBuffer(source.user.id)
      }
    }
    return {
      publicKey: credentialCreationOptions
    }
  }

  private credentialToJSON(
    credential: PublicKeyCredential
  ): WebAuthnClient.AuthenticatorAttestationJSON {
    const credentialJSON: WebAuthnClient.AuthenticatorAttestationJSON = {
      clientDataJSON: this.arrayBufferToBase64(credential.response.clientDataJSON),
      attestationObject: this.arrayBufferToBase64((credential.response as AuthenticatorAttestationResponse).attestationObject),
      clientExtensionsJSON: JSON.stringify(credential.getClientExtensionResults())
    }
    return credentialJSON
  }

  private stringToArrayBuffer(string: string): Uint8Array {
    return new TextEncoder().encode(string)
  }

  private base64ToArrayBuffer(base64String: string): Uint8Array {
    return Uint8Array.from(atob(base64String), (_) => {
      return _.charCodeAt(0)
    })
  }

  private arrayBufferToBase64(arrayBuffer: ArrayBuffer): string {
    return btoa(String.fromCharCode(...new Uint8Array(arrayBuffer)))
  }
}
</script>

<style>
.container {
  margin: 0 auto;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.title {
  font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  display: block;
  font-weight: 300;
  font-size: 100px;
  color: #35495e;
  letter-spacing: 1px;
}

.subtitle {
  font-weight: 300;
  font-size: 42px;
  color: #526488;
  word-spacing: 5px;
  padding-bottom: 15px;
}

.links {
  padding-top: 15px;
}
</style>
