<template>
  <div class="container">
    <div>
      <input id="email" v-model="email" type="text" />
      <button type="button" @click="register">登録</button>
    </div>
  </div>
</template>

<script lang="es2018">
import axios from 'axios'

export default {
  methods: {
    register() {
      this.options()
    },
    options() {
      axios
        .post('/attestation/options', {
          email: this.email
        })
        .then((res) => {
          return this.createCredential(res.data);
        });
    },
    createCredential(options) {
    options.challenge = this.stringToArrayBuffer(options.challenge.value);
    options.user.id = this.stringToArrayBuffer(options.user.id);
    options.excludeCredentials =
        options.excludeCredentials
            .map(credential => Object.assign({},
                credential, {
                    id: this.base64ToArrayBuffer(credential.id),
                }));
      
      return navigator.credentials.create({
        'publicKey': options
      });
    },
    registerFinish(credential){
      axios.post('/attestation/result', {
        'clientDataJSON': this.arrayBufferToBase64(
          credential.response.clientDataJSON
        ),
        'attestationObject': arrayBufferToBase64(
          credential.response.attestationObject
        )}
      )
      .then((res) => {
        return res.data;
      })
    },
    stringToArrayBuffer(string) {
        return new TextEncoder().encode(string);
    },
    base64ToArrayBuffer(base64String) {
        return Uint8Array.from(atob(base64String), c => c.charCodeAt(0));
    },
    arrayBufferToBase64(arrayBuffer) {
        return btoa(String.fromCharCode(...new Uint8Array(arrayBuffer)));
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
