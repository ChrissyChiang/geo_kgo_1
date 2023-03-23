const env = require('./env')
export default {
  /*
   ** Nuxt rendering mode
   ** See https://nuxtjs.org/api/configuration-mode
   */
  //mode: process.env.MODE == 'kgo_test' ? 'spa' : 'universal',
  mode: 'universal',
  /*
   ** Nuxt target
   ** See https://nuxtjs.org/api/configuration-target
   */
  target: 'server',
  /*
   ** Headers of the page
   ** See https://nuxtjs.org/api/configuration-head
   */
  publicRuntimeConfig: {
    APP_PUBLIC_ID: env[process.env.MODE].APP_PUBLIC_ID, //單登 APP_ID
    GO_INDEX_URL: env[process.env.MODE].GO_INDEX_URL //市民科技網址
  },
  env: {
    APP_PUBLIC_ID: env[process.env.MODE].APP_PUBLIC_ID, //單登 APP_ID
    GO_INDEX_URL: env[process.env.MODE].GO_INDEX_URL //市民科技網址
  },
  server: {
    port: env[process.env.MODE].PORT, // 公司80 正式機8000
    host: '0.0.0.0', // default: localhost,
    timing: false
  },
  loading: '~/components/LoadingBar.vue',
  axios: {
    proxy: env[process.env.MODE].MODE === 'development',
    prefix: '/kgo',
    baseURL: env[process.env.MODE].ENV_API,
    credentials: true
  },
  proxy: {
    '/kgo': {
      target: env[process.env.MODE].ENV_API,
      changeOrigin: true,
      pathRewrite: {
        '^/kgo': ''
      }
    }
  },
  head: {
    htmlAttrs: {
      lang: 'zh-TW'
    },
    title: '高雄市民服務平台',
    meta: [
      { charset: 'utf-8' },
      {
        name: 'viewport',
        content: 'width=device-width, initial-scale=1, shrink-to-fit=no'
      },
      { name: 'thumbnail', content: 'https://kgo.kcg.gov.tw/img/logo_fb.jpg' },
      {
        hid: 'description',
        name: 'description',
        content: '高雄市民服務平台-便民一路通'
      },
      { property: 'og:url', content: 'https://kgo.kcg.gov.tw' },
      { property: 'og:title', content: '高雄市民服務平台' },
      { property: 'og:description', content: '高雄市民服務平台-便民一路通' },
      { property: 'og:site_name', content: '高雄市民服務平台' },
      {
        property: 'og:image',
        content: 'https://kgo.kcg.gov.tw/img/logo_fb.jpg'
      },
      { property: 'og:image:alt', content: '高雄市民服務平台-便民一路通' },
      { property: 'og:image:width', content: '1200' },
      { property: 'og:image:height', content: '630' },
      { property: 'og:locale', content: 'zh_TW' },
      { property: 'og:type', content: 'website' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/img/favicon.ico' },
      { rel: 'apple-touch-icon', href: '/img/apple-touch-icon.png' },
      { rel: 'shortcut icon', href: '/img/favicon.ico' },
      {
        rel: 'stylesheet',
        href: '/plugins/bootstrap/css/bootstrap.min.css'
      },
      {
        rel: 'stylesheet',
        href: '/fonts/font-awesome.min.css'
      },
      {
        rel: 'stylesheet',
        href: '/plugins/slick/slick.css'
      },
      {
        rel: 'stylesheet',
        href: '/css/main.min.css'
      }
    ],
    script: [
      {
        src: '/plugins/jquery.min.js'
      },
      {
        src: '/plugins/bootstrap/js/bootstrap.min.js'
      },
      {
        src: '/plugins/slick/slick.min.js'
      }
    ]
  },
  /*
   ** Global CSS
   */
  css: [],
  /*
   ** Plugins to load before mounting the App
   ** https://nuxtjs.org/guide/plugins
   */
  plugins: [
    '~/plugins/services/axios.js',
    '~/plugins/services/api',
    '~/plugins/common.js',
    { src: '~/plugins/third/vuex-persistedstate/index.js', mode: 'client' },
    '~/plugins/vee-validate/index.js'
  ],
  /*
   ** Auto import components
   ** See https://nuxtjs.org/api/configuration-components
   */
  components: true,
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [],
  /*
   ** Nuxt.js modules
   */
  modules: ['@nuxtjs/axios', 'cookie-universal-nuxt'],
  /*
   ** Build configuration
   ** See https://nuxtjs.org/api/configuration-build/
   */
  build: {
    transpile: ['vee-validate/dist/rules'],
    loaders: {
      vue: {
        transformAssetUrls: {
          audio: 'src'
        }
      }
    },

    extend(config, ctx) {
      if (ctx.isDev) {
        config.devtool = ctx.isClient ? 'source-map' : 'inline-source-map'
      } else {
        config.devtool = false
      }
      config.module.rules.push({
        test: /\.(ogg|mp3|wav|mpe?g)$/i,
        loader: 'file-loader',
        options: {
          name: '[path][name].[ext]'
        }
      })
    }
  }
}
