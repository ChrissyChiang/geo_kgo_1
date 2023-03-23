import Vue from 'vue'
import VueAxios from 'vue-axios'
import { $api, $axios } from '@/services'

Vue.use(vue => {
  vue.prototype.$api = $api
})

Vue.use(VueAxios, $axios)
