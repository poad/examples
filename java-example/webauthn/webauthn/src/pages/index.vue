<template>
  <v-container>
    <index v-if="this.$store.state.page === 'Index'" />
    <sign-up v-if="this.$store.state.page === 'SignUp'" />
    <sign-in v-if="this.$store.state.page === 'SignIn'" />
    <inspire v-if="this.$store.state.page === 'Inspire'" />
  </v-container>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'

@Component({
  components: {
    index: () => import('../components/Index.vue'),
    'sign-in': () => import('../components/SignIn.vue'),
    'sign-up': () => import('../components/SignUp.vue'),
    inspire: () => import('../components/Inspire.vue')
  }
})
export default class WebAuthnIndex extends Vue {}
</script>
