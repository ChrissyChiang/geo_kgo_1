<template>
  <div class="fsm-wrapper">
    <app-header />
    <app-tool-bar />
    <!-- <app-menu /> -->
    <main-container>
      <!-- <router-view /> -->
      <!-- 緩存的頁面 -->
      <keep-alive>
        <router-view v-if="$route.meta.keepAlive"></router-view>
      </keep-alive>
      <!-- 不緩存的頁面 -->
      <router-view v-if="!$route.meta.keepAlive"></router-view>
    </main-container>
    <!-- <app-footer /> -->
    <app-notification group="action" />
    <loading
      loader="dots"
      :active.sync="this.$store.state.isLoading"
      :can-cancel="true"
      :is-full-page="true"
      color="#fff"
      :width="128"
      :height="128"
      background-color="#000"
      :opacity="0.3"
      :z-index="9999"
    ></loading>
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
            <h5 id="personalResultsAler-2Title" class="modal-title">
              系統訊息
            </h5>
            <div class="modal-text">
              {{
                `親愛的使用者您好，您已閒置超過${$store.state.user.userInfo
                  .timeout || 1}分鐘，系統已將您登出。`
              }}
            </div>
          </div>
          <div class="modal-footer">
            <div class="btns">
              <button
                type="button"
                class="btn btn-primary"
                data-dismiss="modal"
                @click="confirmLogout"
              >
                確認
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <system-confirm
      id="sysConfirm"
      :modal-title="$t('GENERAL.SYSTEM_MSG')"
      :submit-text="$t('GENERAL.CONFIRM')"
    />
  </div>
</template>
<script>
import VueLoading from 'vue-loading-overlay'
import AppHeader from '@/components/AppHeader.vue'
import AppMenu from '@/components/AppMenu.vue'
import AppToolBar from '@/components/AppToolBar.vue'
import AppFooter from '@/components/AppFooter.vue'
import MainContainer from '@/components/MainContainer.vue'
import { vuexStore } from '@/mixins'
export default {
  components: {
    loading: VueLoading,
    AppHeader,
    AppMenu,
    AppToolBar,
    AppFooter,
    MainContainer
  },
  mixins: [vuexStore],
  data() {
    return {}
  },
  async mounted() {
    $(document).ready(function() {
      $('body div')
        .first()
        .css('height', '100%')
    })
    //當modal會有多層顯示會用到
    $(document).on('show.bs.modal', '.modal', function(event) {
      const zIndex = 1040 + 10 * $('.modal:visible').length
      $(this).css('z-index', zIndex)
      setTimeout(function() {
        $('.modal-backdrop')
          .not('.modal-stack')
          .css('z-index', zIndex - 1)
          .addClass('modal-stack')
      }, 50)
    })
  },
  methods: {
    async confirmLogout() {
      await this.$store.dispatch('logout')
      const ENV = process.env.NODE_ENV
      if (ENV === 'development' || ENV === 'tp' || ENV === 'local') {
        this.$router.push('/mockLogin')
      } else {
        const logout = `${process.env.VUE_APP_API}/backend/auth/logout`
        window.location = logout
      }
    }
  }
}
</script>
<style lang="scss" scoped></style>
