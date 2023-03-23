import Vue from 'vue'
import Loading from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/vue-loading.css'

Vue.use(Loading, {
  loader: 'dots',
  color: '#fff',
  width: 128,
  height: 128,
  backgroundColor: '#000',
  opacity: 0.3,
  zIndex: 9999
})
