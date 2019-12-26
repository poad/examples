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
        <v-col cols="24" sm="12">
          <v-alert v-show="!!status" :type="status" light="true">
            {{ message }}
          </v-alert>
        </v-col>
      </v-row>
    </v-form>
    <v-overlay :value="overlay">
      <v-progress-linear
        :active="active"
        :background-opacity="opacity"
        :buffer-value="buffer"
        :indeterminate="indeterminate"
        :value="100"
        color="primary"
      />
    </v-overlay>
  </v-container>
</template>

<script lang="ts">
import { Vue, Component, Emit } from 'vue-property-decorator'

@Component
export default class WebAuthn extends Vue {
   displayName: string = ''
   email: string = ''
   status: string = ''
   message: string = ''
   overlay: Boolean = false

   absolute: Boolean = false
   active: Boolean = true
   opacity: number = 0.3
   bottom: Boolean = false
   buffer: number = 100
   fixed: Boolean = true
   height: number = 4
   indeterminate: Boolean = true
   query: Boolean = false
   rounded: Boolean = false
   stream: Boolean = false
   striped: Boolean = false
   top: Boolean = false
   value: number = 100

  @Emit()
  async register() {
    try {
      if (!window.PublicKeyCredential) {
        this.status = 'error'
        this.message = '未対応のブラウザです'
        this.overlay = false
        return
      }
      const options = await this.$attestationOptions(
        this.email,
        this.displayName
      )
      this.status = ''
      this.overlay = true

      const credential = await this.$createCredential(options)
      const ret = await this.$registerFinish(credential)
      this.overlay = false
      return ret
    } catch (error) {
      this.status = 'error'
      this.message = error
      this.overlay = false
    }
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
