import { mapFields } from 'vuex-map-fields';
export default {
  data() {
    return {
      isLogin: false,
      loginType: ''
    }
  },
  watch: {
    loginToken(val) {
      this.isLogin = val != ''
    },
    oauthType(val) {
      this.loginType = val
    }
  },
  mounted() {
    const loginToken = this.loginToken || ''
    this.isLogin = loginToken != ''

    this.loginType = this.oauthType
  },
  computed: {
    ...mapFields([
      'timeout',
      'root',
      'apiCookie',
      'kcoin',
      'validateCode',
      'validateToken',
      'loginToken',
      'exchange',
      'userName',
      'oauthType'
    ]),
  },
  methods: {
    delay(interval) {
      return new Promise(resolve => {
        setTimeout(resolve, interval)
      })
    },
  }
}
