<template>
  <v-container>
    <v-form ref="form" v-model="valid" :lazy-validation="lazy">
      <v-row>
        <v-col cols="24">
          <v-text-field
            id="email"
            v-model="email"
            label="e-mail"
            clearable="true"
            required
          />
          <v-btn color="primary" @click="authentication">
            ログイン
          </v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
            <p class="message">
                アカウントが未登録ですか？
                <nuxt-link to="/signup">
                    アカウント作成
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
export default class Auth extends Vue {
  displayName: string = ''
  email: string = ''
  status: string = ''
  message: string = ''
  auth: Boolean = false

  @Emit()
  async authentication() {
    try {
      if (!window.PublicKeyCredential) {
        this.status = 'error'
        this.message = '未対応のブラウザです'
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
          console.error(reason)
          throw reason
        })

      const json = await this.credentialToJSON(credential)

      const ret = await this.$authenticationFinish(json)

      this.status = 'success'
      this.message = 'Logged In'

      return ret
    } catch (error) {
      this.status = 'error'
      this.message = error
      throw error
    }
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
    const rsponse = await (credential.response as AuthenticatorAssertionResponse)
    const credentialJSON: WebAuthnClient.AuthenticatorAssertionJSON = {
      credentialId: this.arrayBufferToBase64(credential.rawId),
      clientDataJSON: this.arrayBufferToBase64(rsponse.clientDataJSON),
      authenticatorData: this.arrayBufferToBase64(rsponse.authenticatorData),
      signature: this.arrayBufferToBase64(rsponse.signature)
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
