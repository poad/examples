<template>
  <v-container>
    <index v-if="this.$store.state.page === 'Index'" />
    <sign-up v-if="this.$store.state.page === 'SignUp'" />
    <sign-in v-if="this.$store.state.page === 'SignIn'" />
  </v-container>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
// import { RootState, WebAuthnPage } from '../store'

@Component({
  components: {
    index: () => import('../components/Index.vue'),
    'sign-in': () => import('../components/SignIn.vue'),
    'sign-up': () => import('../components/SignUp.vue')
  }
})
export default class WebAuthnIndex extends Vue {}
</script>
