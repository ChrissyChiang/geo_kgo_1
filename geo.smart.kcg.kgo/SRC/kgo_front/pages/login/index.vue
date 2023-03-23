<template>
  <div>{{ loginStatus }}</div>
</template>
<script>
import page from '~/mixins/page.js'
export default {
  layout: 'blank',
  mixins: [page],
  data() {
    return {
      loginStatus: '登入中...'
    }
  },
  async asyncData({ app, params, query, store, $cookies }) {
  },
  async mounted() {
    const query = this.$route.query
    const _exchange = query.exchange
    const _jwt = query.jwt
    if (!_exchange && !_jwt) {
      this.$router.push({ path: '/' })
      return
    }
    try {
      const res = await this.$apiUserAuth.login({
        jwt: query.jwt ? query.jwt : '',
        exchange: query.exchange
          ? this.findGetParameter('exchange').replace(/\%20/g, '%2B')
          : ''
      })
      if (res.rtnCode == '0000') {
        const data = res.data
        if (data.sessionToken) {
          this.loginToken = data.sessionToken
        }
        const resUser = await this.$apiUserAuth.getLoginUser()
        if (resUser.rtnCode == '0000') {
          const userInfo = resUser.data.userInfo
          if (userInfo) {
            const timeout = await this.$store.dispatch('getParam', 'TO')
            this.userName = userInfo.name
            this.kcoin = userInfo.kCoinBalance ? Number(userInfo.kCoinBalance) : 0
            this.timeout = timeout != null ? +timeout : 10
            this.exchange = userInfo.exchange
            this.oauthType = userInfo.loginAuthTokenType
          }
          const tempCaseId = sessionStorage.getItem('tempCaseId') || ''
          if (tempCaseId) {
            sessionStorage.removeItem('tempCaseId')
            sessionStorage.removeItem('myDataTemp')
            this.$router.push({
              path: '/apply_form/',
              query: { casesetid: tempCaseId, tx_id: null }
            })
          } else {
            sessionStorage.removeItem('tempCaseId')
            sessionStorage.removeItem('myDataTemp')
            this.$router.push({ path: '/' })
          }
        }
      }
    } catch (error) {
      this.loginStatus = '登入失敗!'
      console.log(error)
    }
  },
  methods: {
    findGetParameter(parameterName) {
      var result = null,
        tmp = []
      location.search
        .substr(1)
        .split('&')
        .forEach(function (item) {
          tmp = item.split('=')
          if (tmp[0] === parameterName)
            //result = decodeURIComponent(tmp[1]);
            result = tmp[1]
        })
      return result
    }
  }
}
</script>