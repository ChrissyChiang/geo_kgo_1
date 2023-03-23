import Vue from 'vue'
import * as filters from '@/filters'

Object.keys(filters).forEach(key => {
  const filter = filters[key]
  Vue.filter(key, filter)
})
