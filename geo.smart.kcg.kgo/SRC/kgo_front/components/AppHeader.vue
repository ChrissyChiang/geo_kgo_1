<template>
  <header>
    <meta
      name="Accessible_website"
      content="本網站通過AA檢測等級無障礙網頁檢測"
    />
    <div class="for_accessibility">
      <a href="#accesskey_c" title="跳到主要內容區塊">跳到主要內容區塊</a>
    </div>
    <div class="fixed-top">
      <div>
        <noscript style="color: black">
          您的瀏覽器不支援JavaScript功能，本網站在沒有JavaScript支援的情形下無法正常使用，請開啟瀏覽器的JavaScript支援。
        </noscript>
      </div>
      <nav class="navbar navbar-expand-lg">
        <a
          href="javascript: void(0)"
          @click="goIndex"
          class="navbar-brand mr-auto"
          alt="高雄市民服務平台"
          title="高雄市民服務平台"
        >
          <img src="/img/logo.png" alt="" />
          <h1>高雄市民服務平台</h1>
        </a>
        <button
          class="navbar-toggler collapsed"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarsDefault"
          aria-expanded="false"
          aria-label="Toggle navigation"
          title="手機版選單按鈕"
        >
          <span class="icon-bar top-bar"></span>
          <span class="icon-bar middle-bar"></span>
          <span class="icon-bar bottom-bar"></span>
        </button>
        <div
          class="collapse navbar-collapse flex-grow-0"
          id="navbarSupportedContent"
        >
          <a
            href="#accesskey_u"
            title="右上方功能區塊"
            id="accesskey_u"
            class="accesskey"
            accesskey="U"
            name="U"
          >
            :::
          </a>
          <ul class="navbar-nav text-right navbar-tool">
            <li class="nav-item">
              <a
                class="nav-link"
                title="網站導覽"
                @click="goIndex"
                href="javascript: void(0)"
              >
                網站導覽
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                title="回首頁"
                @click="goIndex"
                href="javascript: void(0)"
              >
                回首頁
              </a>
            </li>
            <li class="nav-item change-font">
              字級
              <a class="font-size-small" title="字級小" href="#">小</a>
              <a class="font-size-middle active" title="字級中" href="#">中</a>
              <a class="font-size-large" title="字級大" href="#">大</a>
            </li>
            <li class="nav-item">
              <a
                :href="isLogin ? '/case/info' : '/case/search'"
                class="nav-link"
                alt="案件進度查詢"
                title="案件進度查詢"
              >
                案件進度查詢
              </a>
            </li>
            <li class="nav-item">
              <a href="/faq" class="nav-link" alt="常見問題" title="常見問題">
                常見問題
              </a>
            </li>
            <li class="nav-item nav-member active">
              <!-- 1123修改 -->
              <a
                id="login"
                v-if="!isLogin"
                class="nav-link"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
                href="#"
                @click.prevent="loginClick"
                style="z-index: 1030"
              >
                <img src="/img/icon-member.svg" alt="" />
                <i>會員登入</i>
              </a>

              <a
                id="login"
                v-if="isLogin"
                class="nav-link login-active"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
                href="#"
                @click.prevent="loginClick"
                style="z-index: 1030"
              >
                <img src="/img/icon-member-white.svg" alt="" />
                <i>會員登入</i>
              </a>
              <!-- 1123修改 -->

              <span>會員資料</span>
              <div
                class="member-dropdown dropdown-menu dropdown-tool-menu"
                aria-labelledby="login"
              >
                <ul class="nav-dropdown-items">
                  <li>
                    <a></a>
                  </li>
                  <li v-if="!isLogin">
                    <a @click="loginSSO" href="javascript:void(0)">會員登入</a>
                  </li>
                  <li v-if="isLogin">
                    <a style="pointer-events: none">
                      <span class="name">您好，{{ userName }}</span>
                      城市幣
                      <picture>
                        <source
                          srcset="/img/coin.svg"
                          media="(min-width: 992px)"
                        />
                        <source
                          srcset="/img/coin-m.svg"
                          media="(max-width: 991px)"
                        />
                        <img class="citycoin" src="/img/coin.svg" alt="" />
                      </picture>
                      {{ kcoin }}
                    </a>
                  </li>
                  <li v-if="isShowCityFunc && isLogin">
                    <a @click="cityCurrencyPayment" href="javascript:void(0)">
                      城市幣支付
                    </a>
                  </li>
                  <li v-if="isShowCityFunc && isLogin">
                    <a @click="basicInform" href="javascript:void(0)">
                      基本資料維護
                    </a>
                  </li>
                  <li v-if="isLogin">
                    <a @click="logout" href="javascript:void(0)">登出</a>
                  </li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
        <div class="overlay mobile-show"></div>
      </nav>
    </div>
    <div
      id="timeoutModal"
      class="modal fade modal-alert singleBtn"
      tabindex="-1"
      role="dialog"
      data-backdrop="static"
      aria-labelledby="myModalLabel"
    >
      <div
        class="modal-dialog modal-dialog-centered modal-dialog-scrollable"
        role="document"
      >
        <div class="modal-content">
          <div class="modal-body">
            <div id="personalResultsAler-2Title" class="modal-title">
              系統訊息
            </div>
            <div class="modal-text">
              {{
                `親愛的使用者您好，您已閒置${getTimeOut}分鐘，系統已將您登出。`
              }}
            </div>
          </div>
          <div class="modal-footer">
            <div class="btns">
              <button
                type="button"
                class="btn btn-primary"
                data-dismiss="modal"
                @click="logout"
              >
                確認
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import page from '~/mixins/page.js'
//const env = require('../env')

export default {
  mixins: [page],
  watch: {
    exchange(val) {
      this.isShowCityFunc = val != ''
    }
  },
  data() {
    return {
      isShowCityFunc: false,
      appPublicId: '',
      goIndexUrl: ''
    }
  },
  mounted() {
    const exchange = this.exchange || ''
    this.isShowCityFunc = exchange != ''
    // this.appPublicId = process.env.APP_PUBLIC_ID
    // this.goIndexUrl = process.env.GO_INDEX_URL
    this.appPublicId = this.$config.APP_PUBLIC_ID
    this.goIndexUrl = this.$config.GO_INDEX_URL
    this.$nextTick(async () => {
      $('.change-font').on('click', 'a', function(e) {
        e.preventDefault()
        if ($(this).hasClass('font-size-small')) {
          $('body')
            .removeClass('middle large')
            .addClass('small')
          $('.smart_iframe')
            .contents()
            .find('body')
            .css('font-size', '95%')
        } else if ($(this).hasClass('font-size-middle')) {
          $('body')
            .removeClass('small large')
            .addClass('middle')
          $('.smart_iframe')
            .contents()
            .find('body')
            .css('font-size', '100%')
        } else if ($(this).hasClass('font-size-large')) {
          $('body')
            .removeClass('small middle')
            .addClass('large')
          $('.smart_iframe')
            .contents()
            .find('body')
            .css('font-size', '105%')
        }
      })

      $('.change-font a').click(function() {
        $('.change-font a.active').removeClass('active')
        $(this).addClass('active')
      })

      // 手機時主選單展開
      // 右上工具列點擊主選單隱藏
      $('.navbar-tool a').click(function() {
        $('.navbar-collapse.show').collapse('hide')
      })

      // 手機時點黑幕右上工具關閉
      $('.overlay').click(function() {
        $('.navbar-collapse.show').collapse('hide')
      })
    })
  },
  computed: {
    getTimeOut() {
      return this.$store.state.timeout
    }
  },
  methods: {
    goIndex() {
      const exchange = this.exchange || ''
      if (exchange != '') {
        location.href = `${this.goIndexUrl}/receive?exchange=${exchange}&url=${this.goIndexUrl}/`
      } else {
        location.href = this.goIndexUrl
      }
    },
    loginSSO() {
      window.location.href = `https://accounts.kcg.gov.tw/index_external.php?app_id=${this.appPublicId}&login_auth=login_moica_card_auth,login_line_auth,login_google_auth,login_facebook_auth`
    },
    async logout() {
      this.$nuxt.$loading.start()
      try {
        await this.$apiUserAuth.logoutLog()
      } catch (error) {
        console.log(error)
      }
      this.$nuxt.$loading.finish()
      this.loginToken = ''
      this.exchange = ''
      this.oauthType = ''
      this.userName = ''
      this.kcoin = 0
      sessionStorage.removeItem('tempCaseId')
      sessionStorage.removeItem('myDataTemp')
      window.location.href = '/'
    },
    cityCurrencyPayment() {
      window.location.href = `${this.goIndexUrl}/receive?exchange=${this.exchange}&url=${this.goIndexUrl}/kcoin/pay`
    },
    basicInform() {
      window.location.href = `${this.goIndexUrl}/receive?exchange=${this.exchange}&url=${this.goIndexUrl}/member-info/profile`
    },
    loginClick() {
      $('.dropdown-toggle').dropdown()
    }
  }
}
</script>
<style lang="scss" scoped>
.no-script {
  position: fixed;
  top: 0;
  height: 200px;
  font-size: 1.6rem;
  padding: 0.6rem;
  width: 100%;
  z-index: 2000 !important;
  background-color: white !important;
}
</style>
