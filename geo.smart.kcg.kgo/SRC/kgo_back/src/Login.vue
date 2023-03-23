<template>
  <div>
    登入中...
  </div>
</template>
<script>
import { page } from '@/mixins'
import * as types from '@/store/mutations_types.js'
export default {
  mixins: [page],
  data() {
    return {
      jwt: ''
    }
  },
  async mounted() {
    this.jwt = this.$route.query.jwt
    await this.login()
  },
  methods: {
    async login() {
      this.loading(true)
      const resLogin = await this.$store.dispatch('login', this.jwt)
      const resUserInfo = await this.$store.dispatch('getLoginUser')
      this.$store.commit(types.SET_MENU, [])
      if (!resLogin || !resUserInfo) {
        this.loading(false)
        alert('登入失敗')
      } else {
        this.loading(false)
        this.$router.push('/')
      }
    }
  }
}
</script>
<style>
#login {
  margin: 0;
  padding: 0;
  background-color: rgb(159, 240, 178);
  height: 100vh;
}

#login .container #login-row #login-column #login-box {
  margin-top: 120px;
  max-width: 600px;
  height: 320px;
  border: 1px solid #9c9c9c;
  background-color: #eaeaea;
}
#login .container #login-row #login-column #login-box #login-form {
  padding: 20px;
}
#login
  .container
  #login-row
  #login-column
  #login-box
  #login-form
  #register-link {
  margin-top: -85px;
}
</style>
