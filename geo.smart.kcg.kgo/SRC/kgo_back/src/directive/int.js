import Vue from 'vue'
Vue.directive('Int', {
  bind: function(el, binding, vnode) {
    let input = vnode.elm
    input.onkeyup = function(e) {
      if (input.value.length === 1) {
        input.value = input.value.replace(/[^0-9]/g, '')
      } else {
        input.value = input.value.replace(/[^\d]/g, '')
      }
      trigger(input, 'input')
    }
    input.onblur = function(e) {
      if (input.value.length === 1) {
        input.value = input.value.replace(/[^0-9]/g, '')
      } else {
        input.value = input.value.replace(/[^\d]/g, '')
      }
      trigger(input, 'input')
    }
  }
})
const trigger = (el, type) => {
  const e = document.createEvent('HTMLEvents')
  e.initEvent(type, true, true)
  el.dispatchEvent(e)
}
