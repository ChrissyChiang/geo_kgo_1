import { LOG_OUT_LOG, USER_INFO, LOGIN, GET_PARAM_SET } from './constants'
export default ($axios, inject) => {
  inject('apiUserAuth', {
    login(data) {
      return $axios.post(LOGIN, data)
    },
    getLoginUser() {
      return $axios.post(USER_INFO, {})
    },
    getParamSet() {
      return $axios.post(GET_PARAM_SET, {})
    },
    logoutLog() {
      return $axios.post(LOG_OUT_LOG, {})
    },
    testLogin() {
      return $axios.post('auth/loginTestAction', {
        userId: 'A123456789',
        loginType: ''
      })
    },
    getValidateCode() {
      return $axios.post('auth/getValidateCodeAction', {})
    }
  })
}
