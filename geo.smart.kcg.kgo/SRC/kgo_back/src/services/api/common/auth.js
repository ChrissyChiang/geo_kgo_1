import { $axios } from '../../axios'
import { LOGOUT, LOGIN, LOGIN_TEST } from './constants'

/** 登出 */
export function logout() {
  return $axios.get(LOGOUT)
}

/** 登入 */
export function login(jwt) {
  return $axios.post(LOGIN, { jwt })
}

/** 登入() */
export function loginTest(userId, pw) {
  return $axios.post(LOGIN_TEST, { userId: userId, password: pw })
}

/** 測試用api */
export function test() {
  return $axios.post('testApi', { test: 123 })
}

/** 測試用api */
export function pdfBase64Test(param) {
  return $axios.post('common/pdfBase64Test', param, {
    responseType: 'arraybuffer'
  })
}

export function testDownload() {
  return $axios.post('auth/dlTest', {})
}
