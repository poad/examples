import { NuxtConfig } from '@nuxt/types'
import colors from 'vuetify/es5/util/colors'
import { Configuration } from 'webpack'

require('dotenv').config()

const nuxtConfig: NuxtConfig = {
  mode: 'spa',
  target: 'static',
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
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
    '@nuxt/typescript-build',
    '@nuxtjs/vuetify',
    'nuxt-typed-vuex',
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/proxy',
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/dotenv',
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
      themes: {
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
    typescript: {
      typeCheck: {
        eslint: {
          enabled: true
        }
      }
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
    extend(config: Configuration, { }) {
    }
  }
}

export default nuxtConfig
