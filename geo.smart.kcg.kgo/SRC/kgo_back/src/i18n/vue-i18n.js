import Vue from 'vue'
import VueI18n from 'vue-i18n'
import { locale as locale_en } from '@/i18n/langs/en-US'
import { locale as locale_tw } from '@/i18n/langs/zh-TW'
import { validateLang as v_tw } from '@/plugins/vee-validate/i18n/tw.js'
import { validateLang as v_en } from '@/plugins/vee-validate/i18n/en.js'

Vue.use(VueI18n)
let messages = {}
const en = Object.assign(locale_en, v_en)
const tw = Object.assign(locale_tw, v_tw)
messages = { ...messages, en, tw }
const i18n = new VueI18n({
  messages
})

export default i18n
