<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ caseName }}</h2>
        <a
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          class="accesskey"
          accesskey="C"
          name="C"
        >
          :::
        </a>
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="/" title="回首頁">回首頁</a>
          </li>
          <li class="breadcrumb-item">
            <button
              class="btn-gray-outline"
              @click="goCaseList()"
              :title="rootTitle || '標題名稱'"
            >
              {{ rootTitle || '標題名稱' }}
            </button>
          </li>
          <li class="breadcrumb-item">
            <a
              href="javascript:window.location.reload()"
              :title="caseName || '案件名稱'"
            >
              {{ caseName || '案件名稱' }}
            </a>
          </li>
          <li class="breadcrumb-item active" aria-current="page">網路申請</li>
        </ol>
      </nav>
      <!-- breadcrumb end -->

      <div class="apply-inner">
        <div class="step">
          <ul>
            <li class="done">
              網路申請
              <br />
              同意書
            </li>
            <li>
              身分
              <br />
              驗證
            </li>
            <li class="">
              填寫
              <br />
              申請表
            </li>
            <li class="">
              確認
              <br />
              申請內容
            </li>
            <li class="">
              步驟
              <br />
              完成
            </li>
          </ul>
        </div>
        <div class="apply-item">
          <h3>網路服務規定</h3>
          <ol class="scrollbar">
            <span v-html="consentRule"></span>
          </ol>
        </div>
        <form>
          <div class="custom-control custom-checkbox text-center mb-3">
            <input
              type="checkbox"
              class="custom-control-input"
              id="agree-text"
              title="已閱讀並同意欄位"
            />
            <label class="custom-control-label" for="agree-text">
              我已閱讀，並已清楚上前述內容
            </label>
          </div>
          <div class="form-group text-center btn-even">
            <button
              class="btn btn-lg btn-gray-light"
              type="button"
              @click="back"
              title="不同意按鈕"
            >
              不同意
            </button>
            <button
              class="btn btn-lg btn-org"
              id="agree-btn"
              type="button"
              @click="checkOauth"
              title="已閱讀並同意按鈕"
              disabled
            >
              已閱讀並同意
            </button>
          </div>
        </form>
      </div>
    </div>
    <div
      class="modal fade"
      id="modal-login"
      ref="modalLogin"
      tabindex="-1"
      role="dialog"
      aria-labelledby="loginLabel"
      aria-hidden="true"
    >
      <div
        class="modal-dialog modal-dialog-centered"
        :class="[checkTypes.length > 1 ? 'modal-lg' : '']"
        role="document"
      >
        <div class="modal-content">
          <div class="modal-header border-0 text-center">
            <div class="modal-title w-100" id="loginLabel">
              <img class="city-icon" src="/img/kaohsiung-city.png" alt="" />
              單一認證平台
            </div>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <i class="fa fa-times fa-lg"></i>
            </button>
          </div>
          <form>
            <div class="modal-body">
              <div class="row" style="justify-content: center">
                <div
                  v-if="checkTypes.includes('EGOV')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="「我的 e 政府」會員登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_egov_auth.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <i class="fa fa-lock" aria-hidden="true"></i>
                    </div>
                    <div class="passwordMethod__list__text">
                      「我的 e 政府」會員
                    </div>
                  </a>
                </div>
                <div
                  v-if="checkTypes.includes('MOICA')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="自然人憑證登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_moica_card.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <i class="fa fa-user" aria-hidden="true"></i>
                    </div>
                    <div class="passwordMethod__list__text">自然人憑證</div>
                  </a>
                </div>
                <div
                  v-if="checkTypes.includes('HCA')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="健保卡登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_hca_card.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <i class="fa fa-id-card" aria-hidden="true"></i>
                    </div>
                    <div class="passwordMethod__list__text">健保卡</div>
                  </a>
                </div>
                <div
                  v-if="checkTypes.includes('FACEBOOK')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="Facebook登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_facebook_auth.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <i class="fa fa-facebook" aria-hidden="true"></i>
                    </div>
                    <div class="passwordMethod__list__text">Facebook登入</div>
                  </a>
                </div>
                <div
                  v-if="checkTypes.includes('GOOGEL')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="Google登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_google_auth.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <i class="fa fa-google" aria-hidden="true"></i>
                    </div>
                    <div class="passwordMethod__list__text">Google登入</div>
                  </a>
                </div>
                <div
                  v-if="checkTypes.includes('LINE')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="Line登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_line_auth.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <img src="/img/icon-line.png" alt="" />
                    </div>
                    <div class="passwordMethod__list__text">Line</div>
                  </a>
                </div>
                <div
                  v-if="checkTypes.includes('BASIC')"
                  class="col-md-auto col-xs-12"
                >
                  <a
                    class="passwordMethod__list"
                    id="noStatus-card"
                    title="市府員工登入"
                    @click="ssoLogin"
                    :href="
                      `https://accounts.kcg.gov.tw/index_app.php?app_id=${appPublicId}`
                    "
                  >
                    <div class="passwordMethod__list__icon">
                      <img
                        src="/img/Logo_login.png"
                        style="padding-bottom: 25px"
                        alt=""
                      />
                    </div>
                    <div class="passwordMethod__list__text">市府員工</div>
                  </a>
                </div>
              </div>
            </div>
            <!-- <div class="modal-footer justify-content-center">
              <button
                @click="login"
                data-dismiss="modal"
                type="button"
                class="btn btn-org"
              >
                登入
              </button>
            </div> -->
          </form>
        </div>
      </div>
    </div>
    <system-confirm
      ref="systemConfirm"
      :msg="'此案件設定登入驗證方式不符合。'"
    />
    <system-confirm ref="modal" :msg="msg" />
  </div>
</template>
<script>
import page from '~/mixins/page.js'
import SystemConfirm from '../../../../components/SystemConfirm.vue'
//const env = require('../../../../env')

export default {
  components: { SystemConfirm },
  layout: 'applyConsent',
  mixins: [page],
  head() {
    return {
      title: '網路申請同意書'
    }
  },
  data() {
    var params = this.$route.params
    return {
      applyType: '',
      consentRule: '',
      caseName: '',
      rootTitle: '',
      rootType: '',
      rootId: '',
      id: params.id,
      /** 驗證欄位欄位 */
      checkTypes: [],
      msg: '',
      appPublicId: ''
    }
  },
  async asyncData({ app, query, store, params }) {},
  mounted() {
    this.rootTitle = this.root.title
    this.rootType = this.root.type
    this.rootId = this.root.id
    // this.appPublicId = process.env.APP_PUBLIC_ID
    this.appPublicId = this.$config.APP_PUBLIC_ID
    this.$nextTick(async () => {
      $('#agree-text').click(function() {
        $('#agree-btn').prop('disabled', !$('#agree-text:checked').length)
      })

      this.$nuxt.$loading.start()
      var res = await this.$apiApplyConsent.getServiceConsent(
        {
          caseSetId: this.id
        },
        error => {
          this.msg = this.$common.errorRspMsg(error)
          this.$refs.modal.show(() => {
            this.$router.push({ path: '/' })
          })
        }
      )
      this.$nuxt.$loading.finish()
      if (!res) return
      this.consentRule = res.data.serviceHtml
      this.caseName = res.data.caseSetName
      this.checkTypes = res.data.checkTypes || []
    })
  },
  methods: {
    /** 檢核此案件驗證方式 */
    checkOauth() {
      if (
        this.isLogin &&
        this.checkTypes.length > 0 &&
        !this.checkTypes.includes('NAN') &&
        !this.checkTypes.includes(this.loginType)
      ) {
        this.$refs.systemConfirm.show()
        return
      }
      /** 如果沒有設定驗證方式或是免驗證，直接進入填單頁 */
      if (
        this.checkTypes.includes('NAN') ||
        this.isLogin ||
        this.checkTypes.length == 0
      ) {
        this.goApplyForm()
      } else {
        $(this.$refs.modalLogin).modal('show')
      }
    },
    goApplyForm() {
      this.$router.push({
        path: '/apply_form/',
        query: { casesetid: this.id }
      })
    },
    ssoLogin() {
      sessionStorage.setItem('tempCaseId', this.id)
    },
    goCaseList() {
      this.$router.push({
        path: '/apply/' + this.rootId,
        query: { type: this.rootType }
      })
    },
    back() {
      this.$router.push({
        path: `/apply/info/${this.id}`,
        query: { applyType: this.applyType }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.passwordMethod__list {
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 0 5px 0 #4eafb1;
  height: auto;
  display: block;
  padding: 8% 0;
  margin: 0 auto 20px;
  max-width: 280px;
  overflow: hidden;
}
.passwordMethod__list .passwordMethod__list__icon {
  border: 5px solid #fff;
  background: #06b1b1;
  border-radius: 100%;
  width: 100px;
  height: 110px;
  text-align: center;
  line-height: 120px;
  font-size: 2.2rem;
  box-shadow: 0 0 0 2px #afeaea;
  margin: 0 auto;
  position: relative;
  left: 0;
  transition: 0.2s;
}
.passwordMethod__list .passwordMethod__list__icon i {
  color: #fff;
}
.passwordMethod__list .passwordMethod__list__text {
  text-align: center;
  padding-top: 20px;
  font-size: 1rem;
  color: #00a3a5;
  transition: 0.2s;
}
.passwordMethod__list:hover {
  box-shadow: 0 0 16px 0 #4eafb1;
  border-bottom: solid 0 #41b0b1;
}
.passwordMethod__list:hover .passwordMethod__list__icon {
  // width: 105px;
  // height: 105px;
  // margin-top: -10px;
  // line-height: 140px;
  background: #078484;
}
.passwordMethod__list:hover .passwordMethod__list__text {
  //font-weight: 700;
  //padding-top: 10px;
  color: #078484;
}

.fa {
  margin-right: 0 !important;
}
</style>
