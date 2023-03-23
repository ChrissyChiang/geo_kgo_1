import createPersistedState from 'vuex-persistedstate'
import * as CryptoJS from 'crypto-js'
export default function({ store, req, isDev }) {
  createPersistedState({
    paths: [
      'apiCookie',
      'root',
      'timeout',
      'kcoin',
      'loginToken',
      'exchange',
      'userName',
      'oauthType'
    ],
    storage: {
      getItem: key => {
        if (key === 'vuex') {
          const itemValue = window.sessionStorage.getItem(key)
          const bytes = CryptoJS.AES.decrypt(itemValue, 'secretgogo')
          const decryptValue = bytes.toString(CryptoJS.enc.Utf8)
          return decryptValue
        } else {
          return window.sessionStorage.getItem(key)
        }
      },
      setItem: (key, value) => {
        if (key === 'vuex') {
          const encryptValue = CryptoJS.AES.encrypt(
            value,
            'secretgogo'
          ).toString()
          window.sessionStorage.setItem(key, encryptValue)
        } else {
          window.sessionStorage.setItem(key, value)
        }
      },
      removeItem: key => window.sessionStorage.removeItem(key)
    }
    // storage: {
    //   getItem: key =>
    //     process.client
    //       ? cookies.getJSON(key)
    //       : cookie.parse(req.headers.cookie || '')[key],
    //   setItem: (key, value) =>
    //     cookies.set(key, value, { expires: 365, secure: false }),
    //   removeItem: key => cookies.remove(key)
    // }
  })(store)
}
