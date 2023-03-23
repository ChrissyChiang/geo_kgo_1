import Vue from 'vue'
import {
  ValidationObserver,
  ValidationProvider,
  configure,
  extend
} from 'vee-validate'
import * as rules from 'vee-validate/dist/rules'
import i18n from '@/i18n/vue-i18n'

configure({
  classes: {
    invalid: 'is-invalid'
  },
  defaultMessage: (field, values) => {
    if (i18n.te(`fields.${values._rule_}`)) {
      const vMsg = `fields.${values._rule_}`
      return i18n.t(vMsg, values)
    }

    if (values._field_ == '{field}') {
      values._field_ = i18n.t('generalField')
    }
    return i18n.t(`validation.${values._rule_}`, values)
  }
})
Object.keys(rules).forEach(rule => {
  extend(rule, {
    ...rules[rule]
  })
})

/**檔名＋副檔名字數限制 */
extend('file_name_max', {
  params: ['max'],
  validate(value, { max }) {
    if (Array.isArray(value) && value.length && value[0].name) {
      return value[0].name.length <= max
    }
    return true
  }
})

/** 檔案大小限制 (MB) */
extend('file_size', {
  params: ['size'],
  validate(value, { size }) {
    let fileSize = 0
    if (Array.isArray(value)) {
      fileSize = value.map(file => file.size).reduce((a, b) => a + b, 0)
    } else {
      fileSize = value.size
    }
    return fileSize / 1024 ** 2 <= Number(size)
  }
})

extend('file_size', {
  params: ['size'],
  validate(value, { size }) {
    let fileSize = 0
    if (Array.isArray(value)) {
      fileSize = value.map(file => file.size).reduce((a, b) => a + b, 0)
    } else {
      fileSize = value.size
    }
    return fileSize / 1024 ** 2 <= Number(size)
  }
})

/** 值必需相等於 */
extend('equal_value', {
  params: ['digital'],
  validate(value, { digital }) {
    if (value != digital) {
      return false
    }
    return true
  }
})

extend('url', {
  validate(value) {
    if (!value) return true
    const reg = RegExp(
      `^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$`
    )
    return reg.test(value)
  }
})

/** 檢核西元年 */
extend('full_year', {
  validate(value) {
    if (!value) return true
    if (isNaN(value)) return false
    const year = +value
    if (year <= 1991) return false

    if (year >= 2200) return false
    return true
  }
})
/** 檢核有無選擇選項 */
extend('must_upload', {
  validate(value) {
    console.log(value)
    if (value == null) {
      return false
    } else {
      return true
    }
  }
})
/** 檢核有無填寫日期 */
extend('input_date', {
  validate(value) {
    console.log(value)
    if (value[0] != '' && value[1] != '') {
      return true
    } else {
      return false
    }
  }
})
extend('min_len', {
  validate(value, { length }) {
    return value.length >= length
  },
  params: ['length'],
  message: '請選擇{length}個以上項目'
})
/** 檢核有無選擇選項 */
extend('must_select', {
  validate(value) {
    if (value == null) {
      return false
    } else {
      return true
    }
  }
})
/** 網址開頭限制 */
extend('url_start', {
  params: ['start'],
  validate(value, { start }) {
    const reg = RegExp(`^${start}`, 'g')
    return reg.test(value)
  }
})

/** 數字 */
extend('num', {
  validate(value) {
    const reg = RegExp(`^[0-9,]+$`, 'g')
    return reg.test(value)
  }
})
/** email */
extend('email', {
  validate(value) {
    return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/.test(
      value
    )
    //const reg = RegExp(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)
    //return reg.test(value)
  }
})
/** 身分證 */
extend('id', {
  validate(value) {
    return /^[A-Za-z][1-2]\d\d\d\d\d\d\d\d$/.test(value)
  }
})
/** 手機 */
extend('phone', {
  validate(value) {
    return /^09\d\d\d\d\d\d\d\d$/.test(value)
  }
})
/** 統一編號 */
extend('taxId', {
  validate(value) {
    return /\d\d\d\d\d\d\d\d$/.test(value)
  }
})
/** 室內電話 */
extend('telephone', {
  validate(value) {
    return /^0\d{8,9}$/.test(value)
  }
})

Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)
