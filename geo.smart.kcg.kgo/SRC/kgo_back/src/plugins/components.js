import Vue from 'vue'
import * as components from '@/components'

Object.keys(components).forEach(key => {
  const component = components[key]
  Vue.component(component.name || key, component)
})
