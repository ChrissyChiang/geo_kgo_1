import Vue from 'vue'
import {
  ValidationObserver,
  ValidationProvider,
  configure,
  extend
} from 'vee-validate'
import * as rules from 'vee-validate/dist/rules'

configure({
  classes: {
    invalid: 'is-invalid'
  },
})
Object.keys(rules).forEach(rule => {
  extend(rule, {
    ...rules[rule]
  })
})

/** required */
extend('required', {
  validate(value) {
    if (value == '' || value == []) {
      return false
    }
    else {
      return true
    }

  },
  message: '此欄位為必填'
})
extend('max', {
  params: ['max'],
  validate(value, { max }) {
    if (!value) return true
    return !(value.length > max)
  },
  message: '字數不可超過{max}字'
})
//max

//captch
extend('captch', {
  params: ['code', 'isr'],
  validate(value, { code, isr }) {
    return value == code
  },
  computesRequired: true,
  message: (fieldName, params) => {
    return params._value_ == '' ? '此欄位為必填' : '驗證碼錯誤'
  }

})





/** 檢核父層是否有值，若有值則兒子必填 */
extend('required_parent', {
  params: ['parent'],
  validate(value, { parent }) {
    if (parent && parent != '') {//父項有值時
      return value != ''
    }
    return true
  },
  computesRequired: true,
  message: '此欄位為必填'
})
/** 檢核有無選擇選項 */
extend('must_upload', {
  validate(value) {

    if (value == null) {
      return false
    }
    else {
      return true
    }
  },
  message: '此欄位為必選'
})
/** 檢核有無填寫日期 */
extend('inputDate', {
  validate(value) {

    // console.log(value)
    //if(value[0] != '' && value[1] != ''){
    // return true
    //}
    // else{
    return false
    //}
  },
  message: '此欄位為必選'
})

/** 檢核有無選擇選項 */
extend('mustSelect', {
  validate(value) {
    //if(value == null){
    //return false
    //}
    //else{
    return true
    //}
  },
  message: '此欄位為必選'
})
/** 數字 */
extend('num', {
  validate(value) {
    const reg = RegExp(`^[0-9,]+$`, 'g')
    return reg.test(value)
  },
  message: '此欄位僅可輸入數字'
})
/** email */
extend('email', {
  validate(value) {
    return /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+$/.test(value)
  },
  message: '請輸入正確email格式'
})
/** 身分證 */
extend('id', {
  validate(value) {
    return /^[A-Z][1-2]\d{8}$/.test(value)
  },
  message: '請輸入正確身分證字號格式'
})

/** 手機 */
extend('phone', {
  validate(value) {
    return /^09[0-8][0-9]{7}$/.test(value)
  },
  message: '請輸入正確手機格式'
})
/** 統一編號 */
extend('taxId', {
  validate(value) {
    return /\d\d\d\d\d\d\d\d$/.test(value)
  },
  message: '請輸入正確統一編號格式'
})
/** 室內電話 */
extend('telephone', {
  validate(value) {
    return /^\((02|03|04|05|06|07|08|037|049|082|0826|0836|089)\)\d{5,8}$/.test(value)
  },
  message: '請輸入正確電話格式，如(07)12345678電話格式'
})

/** 市府 */
extend('orgcheck', {
  validate(value) {
    return /^[A-Za-z]{3}-\d{2}-\d{2}$/.test(value)
  },
  message: '格式錯誤'
})


Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)
