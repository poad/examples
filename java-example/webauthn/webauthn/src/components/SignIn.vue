<template>
  <v-container>
    <v-layout justify-center align-center>
      <v-row justify-center align-center>
        <v-col cols="6" md="6" xs8 md8 sm8>
          <v-form ref="form">
            <v-text-field
              id="email"
              v-model="email"
              label="e-mail"
              clearable
              :rules="[
                (email) => !!email || 'E-mail is required',
                email && email.length > 0
              ]"
              required
            />
            <v-btn color="primary" name="signIn" @click="authentication">
              サインイン
            </v-btn>
          </v-form>
        </v-col>
        <v-col cols="6" md="4">
          アカウントは作成済みですか？
          <v-btn
            block
            color="primary"
            name="signIn"
            sm="12"
            @click="showSignUp"
          >
            サインアップ
          </v-btn>
        </v-col>
      </v-row>
    </v-layout>
    <v-dialog
      v-model="showDialog"
      hide-overlay
      overlay-color="#000"
      overlay-opacity="80%"
      max-width="290"
    >
      <v-alert :type="status">
        {{ message }}
      </v-alert>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import { Vue, Component, Emit } from 'vue-property-decorator'
import WebAuthnClient from '../plugins/types'
import { WebAuthnPage } from '../store'

@Component
export default class SignIn extends Vue {
  displayName: string = ''
  email: string = ''
  status: string = ''
  message: string = ''
  showDialog: Boolean = false
  auth: Boolean = false

  @Emit()
  showSignUp() {
    this.$accessor.changePage(WebAuthnPage.SignUp)
  }

  @Emit()
  showIndex() {
    this.$accessor.changePage(WebAuthnPage.Index)
  }

  @Emit()
  async authentication() {
    try {
      if (!window.PublicKeyCredential) {
        this.changeMessage('error', '未対応のブラウザです')
        return
      }

      if (!(this.$refs.form as Vue & { validate: () => boolean }).validate()) {
        return
      }

      const email: string = this.email
      const response: WebAuthnClient.CredentialRequestOptions = await this.$assertionOptions<
        WebAuthnClient.CredentialRequestOptions
      >(email)

      this.status = ''

      const options = this.convertRequestOptions(response)

      const credential = await navigator.credentials
        .get(options)
        .then((reply: Credential | null) => {
          if (reply != null) {
            return reply as PublicKeyCredential
          } else {
            throw new Error('unmatched type')
          }
        })
        .catch((reason) => {
          throw reason
        })

      const json = await this.credentialToJSON(credential)

      const ret = await this.$authenticationFinish(json)

      this.showIndex()

      return ret
    } catch (error) {
      this.changeMessage('error', error)
    }
  }

  @Emit()
  changeMessage(status: string, message: string) {
    this.status = status
    this.message = message
    this.showDialog = status !== ''
  }

  private convertRequestOptions(
    source: WebAuthnClient.CredentialRequestOptions
  ): CredentialRequestOptions {
    const allowCredentials =
      source.allowCredentials != null ? source.allowCredentials : []

    const credentialRequestOptions: PublicKeyCredentialRequestOptions = {
      allowCredentials: allowCredentials.map((credential) => {
        return {
          id: this.base64ToArrayBuffer(credential.id),
          type: credential.type,
          transports: credential.transports
        } as PublicKeyCredentialDescriptor
      }),
      challenge: this.stringToArrayBuffer(source.challenge.value),
      extensions: source.extensions,
      rpId: source.rpId,
      timeout: source.timeout,
      userVerification: source.userVerification
    }
    return {
      publicKey: credentialRequestOptions
    }
  }

  private async credentialToJSON(
    credential: PublicKeyCredential
  ): Promise<WebAuthnClient.AuthenticatorAssertionJSON> {
    const response = await (credential.response as AuthenticatorAssertionResponse)
    const credentialJSON: WebAuthnClient.AuthenticatorAssertionJSON = {
      credentialId: this.arrayBufferToBase64(credential.rawId),
      clientDataJSON: this.arrayBufferToBase64(response.clientDataJSON),
      authenticatorData: this.arrayBufferToBase64(response.authenticatorData),
      signature: this.arrayBufferToBase64(response.signature),
      clientExtensionsJSON: JSON.stringify(
        credential.getClientExtensionResults()
      )
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
