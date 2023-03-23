<template>
  <div id="login">
    <div class="containerx">
      <div
        id="login-row"
        style="width: 500px"
        class="row justify-content-center align-items-center"
      >
        <div id="login-column" class="col-md-12">
          <div id="login-box" class="col-md-12">
            <div id="login-form">
              <h1 class="text-center text-info mb-5" style="font-size: 30px">
                便民一路通管理平台登入
              </h1>
              <div class="form-group">
                <div class="row">
                  <label for="username" class="text-info mb-1">角色：</label>
                  <select v-model="selectedRole" @change="setUserId">
                    <option value="geoscteam">系統管理者</option>
                    <option value="AC2214">機關管理者</option>
                    <option value="chy118">機關承辦-江惠月</option>
                    <option value="a0004">機關分文-林久美</option>
                    <option value="bob">機關分文-bob</option>
                    <option value="a02107">案件管理者-徐意欣</option>
                    <option value="a0310">機關管理者-潘金來</option>
                    <option value="a0103">機關案件管理者-莊麗美</option>
                    <option value="a0137">機關管理者-黃碧淑</option>
                    <option value="16-may">機關分文-王美</option>
                    <option value="judy6704">承辦-鄭雅文</option>
                    <option value="bob2">機關承辦-bob2</option>
                    <option value="a3541516">承辦人員-李苑祺</option>
                    <!--  -->
                    <option value="a3314327">林麗娟</option>
                    <option value="a89c042">范姜秀玉</option>
                    <option value="a3373082">楊詠淇</option>
                    <option value="a89044">王靜慧</option>
                    <option value="billyw">王百成</option>
                    <option value="a89c031">蔡佳真</option>
                    <option value="d1095341">陳永昇</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <div class="row">
                  <label for="username" class="text-info mb-1">帳號：</label>
                  <input
                    id="username"
                    v-model="userId"
                    type="text"
                    name="username"
                    class="form-control w-150"
                  />
                </div>
              </div>
              <div class="form-group">
                <div class="row">
                  <label for="username" class="text-info mb-1">密碼：</label>
                  <input
                    id="pw"
                    v-model="pw"
                    type="password"
                    name="pw"
                    class="form-control w-150"
                  />
                </div>
              </div>
              <div class="form-group">
                <div class="row"></div>
                <div class="form-group">
                  <input
                    type="button"
                    style="width: 100%"
                    name="確認"
                    class="btn btn-info btn-md"
                    value="登入"
                    @click="login"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
import * as types from '@/store/mutations_types.js'
import { resetRouter } from '@/router'
export default {
  mixins: [page],
  data() {
    return {
      selectedRole: 'geoscteam',
      userId: 'geoscteam',
      pw: ''
    }
  },
  computed: {
    envName() {
      return process.env.NODE_ENV
    }
  },
  mounted() {},
  methods: {
    setUserId() {
      this.userId = this.selectedRole
    },
    async login() {
      if (!this.userId) {
        alert('請選擇角色')
      } else {
        this.loading(true)
        console.log(this.pw)
        const resLogin = await this.$store.dispatch('mockLogin', {
          userId: this.userId,
          pw: this.pw
        })
        const resUserInfo = await this.$store.dispatch('getLoginUser')
        this.$store.commit(types.SET_MENU, [])
        if (!resLogin || !resUserInfo) {
          this.loading(false)
          this.toastError('登入失敗')
        } else {
          this.loading(false)
          this.$router.push('/')
        }
      }
    }
  }
}
</script>
<style lang="scss">
#login {
  margin: 0;
  padding: 0;
  background-color: #4f5467;
  height: 100vh;
}
.containerx {
  margin-right: auto;
  margin-left: auto;
  padding-left: 15px;
  padding-right: 15px;
  display: flex;
  justify-content: center;
}
.row {
  display: flex;
  flex-wrap: wrap;
  margin-right: -15px;
  margin-left: -15px;
}

.mb-5,
.my-5 {
  margin-bottom: 3rem !important;
}

#login .containerx #login-row #login-column #login-box {
  margin-top: 120px;
  max-width: 600px;
  height: 300px;
  border: 1px solid #9c9c9c;
  background-color: #eaeaea;
}
#login .containerx #login-row #login-column #login-box #login-form {
  padding: 20px;
}
#login
  .containerx
  #login-row
  #login-column
  #login-box
  #login-form
  #register-link {
  margin-top: -85px;
}
</style>
