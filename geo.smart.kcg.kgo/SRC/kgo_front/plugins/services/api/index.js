import { default as userAuth }from './userAuth'
import { default as home }from './home'
import { default as caseList }from './caseList'
import { default as applyDescription }from './applyDescription'
import { default as applyConsent }from './applyConsent'
import { default as commonQuestion } from './commonQuestion'
import { default as fillForm } from './fillForm'
export default ({ app: { $axios } }, inject) => {
    userAuth($axios, inject)
    home($axios, inject)
    caseList($axios, inject)
    applyDescription($axios, inject)
    applyConsent($axios, inject)
    commonQuestion($axios,inject)
    fillForm($axios,inject)
}