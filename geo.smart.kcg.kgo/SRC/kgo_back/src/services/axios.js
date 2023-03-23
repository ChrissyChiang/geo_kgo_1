import axios from 'axios'
import store from '@/store'
import * as types from '@/store/mutations_types.js'

const $axios = axios.create({
  baseURL: getBaseURL(),
  timeout: 100000,
  withCredentials: true
})
var timeoutMinute = 10
var secondsRemaining = process.env.VUE_APP_REMAINING_SECONDS
var timeoutProcess
function triggerTimeout(timeOutMinute) {
  timeoutProcess = setTimeout(function() {
    $('#timeoutModal').modal('show')
  }, timeOutMinute * 60 * 1000 - 2000)
}
function triggerStopTimeout() {
  if (timeoutProcess) {
    clearTimeout(timeoutProcess)
  }
}

$axios.interceptors.request.use(
  config => {
    config.data = config.data || {}
    const timeOutMinute = store.state.user.userInfo.timeout || 30
    triggerStopTimeout()
    triggerTimeout(timeOutMinute)
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

$axios.interceptors.response.use(
  response => {
    return response
  },
  err => {
    if (err.response) {
      switch (err.response.status) {
        case 401:
          const errCode = err.response.data ? err.response.data.rtnCode : ''
          // 無操作權限登出
          if (errCode == 'COMMON-0001') {
            logout()
          }
          if (errCode == 'COMMON-0003') {
            $('#timeoutModal').modal('show')
            // logout()
          }
          break
      }
    }
    return Promise.reject(err)
  }
)

function getBaseURL() {
  return process.env.VUE_APP_API || '/Api'
}

function getRefreshToken() {
  return $axios.post('auth/refreshToken', {
    userEmail: store.state.user.userEmail,
    refreshToken: store.state.user.refreshToken
  })
}

function logout() {
  store.dispatch('logout')
  const ENV = process.env.NODE_ENV
  if (ENV === 'development' || ENV === 'tp' || ENV === 'local') {
    window.location = '/mockLogin'
  } else {
    const logout = `${process.env.VUE_APP_API}/backend/auth/logout`
    window.location = logout
  }
}

export { $axios }
