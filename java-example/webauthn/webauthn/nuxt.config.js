// import NuxtConfiguration from '@nuxt/config'
import colors from 'vuetify/es5/util/colors'

require('dotenv').config()

// const nuxtConfig: NuxtConfiguration = {
const nuxtConfig = {
  mode: 'spa',
  /*
   ** Headers of the page
   */
  head: {
    titleTemplate: '%s - ' + process.env.npm_package_name,
    title: process.env.npm_package_name || '',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: process.env.npm_package_description || ''
      }
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }]
  },
  /*
   ** Customize the progress-bar color
   */
  loading: { color: '#fff' },
  /*
   ** Global CSS
   */
  css: [],
  /*
   ** Plugins to load before mounting the App
   */
  plugins: [
    { src: '~/plugins/web-authn-client.ts', mode: 'client' }
  ],
  // router: {
  //   middleware: ['auth']
  // },
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
    '@nuxt/typescript-build',
    '@nuxtjs/vuetify',
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/proxy',
    '@nuxtjs/auth',
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/dotenv'
  ],
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    // See https://github.com/nuxt-community/axios-module#options
    credentials: true,
    proxy: true,
    headers: {
      'Content-Type':'application/json'
    }
  },
  /*
   ** vuetify module configuration
   ** https://github.com/nuxt-community/vuetify-module
   */
  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    theme: {
      dark: false,
      light: true,
      themes: {
        dark: {
          primary: colors.blue.darken2,
          accent: colors.grey.darken3,
          secondary: colors.amber.darken3,
          info: colors.teal.lighten1,
          warning: colors.amber.base,
          error: colors.deepOrange.accent4,
          success: colors.green.accent3
        },
        light: {
          primary: colors.purple,
          secondary: colors.grey.darken1,
          accent: colors.shades.black,
          error: colors.red.accent3,
        },
      }
    }
  },
  auth: {
    strategies: {
      local1: { _scheme: 'local', /* ... */ }
    },
    localStorage: {
      prefix: 'auth.'
    }
  },
  proxy: {
    '/attestation/options': { target: process.env.BASE_URL ? process.env.BASE_URL : "localhost:8080" },
    '/attestation/result': { target: process.env.BASE_URL ? process.env.BASE_URL : "localhost:8080" },
    '/assertion/options': { target: process.env.BASE_URL ? process.env.BASE_URL : "localhost:8080" },
    '/assertion/result': { target: process.env.BASE_URL ? process.env.BASE_URL : "localhost:8080" }
  },
  typescript: {
    typeCheck: {
      eslint: false
    }
  },
  resolve: {
    extensions: [ '.tsx', '.ts', '.js', '.css', '.html' ],
  },
  srcDir: 'src',
  server: {
    host: '0.0.0.0'
  },
  /*
   ** Build configuration
   */
  build: {
    /*
     ** You can extend webpack config here
     */
    extend(config, ctx) { }
  }
}

export default nuxtConfig
