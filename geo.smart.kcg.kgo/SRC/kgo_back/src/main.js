import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import i18n from '@/i18n/vue-i18n'
import '@/plugins'
import '@/assets/css/main.scss'

Vue.config.productionTip = false

i18n.locale = store.state.lang

new Vue({
  i18n,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
