import Vue from 'vue'
import ismobile from 'ismobilejs'

function isMobile() {
  return ismobile().any
}

Vue.prototype.$isMobile = isMobile
