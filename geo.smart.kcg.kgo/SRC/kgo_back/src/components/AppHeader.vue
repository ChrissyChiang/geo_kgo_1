<template>
  <div>
    <header class="fsm-header">
      <div class="container-fluid">
        <div class="fsm-navbar-header">
          <a
            class="navbar-brand fsm-brand"
            href="/"
            title="Fleet Safety Management"
          >
            <img
              src="@/assets/img/logo-md.png"
              class="hidden-xs"
              alt="Fleet Safety Management"
            />
            <span class="brand-name">便民一路通管理平台</span>
            <span class="brand-time">V.{{ version }}</span>
          </a>
          <nav class="nav-user pull-right" aria-expanded="false">
            <ul class="nav navbar-nav">
              <!-- <li class="item-logout">
                <a href="#this" title="字體大小" style="color: white">
                  <i class="fa fa-adn"></i>
                </a>
              </li>
              <li class="item-logout">
                <a href="#this" title="報修資訊" style="color: white">
                  <i class="fa fa-info-circle"></i>
                </a>
              </li> -->
              <li class="item-logout">
                <a
                  href="javascript:void(0)"
                  title="登出"
                  style="color: white"
                  @click="logout"
                >
                  <i class="fsm-icon-logout"></i>
                </a>
              </li>

              <li
                class="nav-user-account dropdown"
                style="background-size: 20%; background-repeat: repeat"
              >
                <a
                  data-toggle="dropdown"
                  href="#this"
                  :title="userName"
                  style="width: 120px; line-height: 40px; color: #fff"
                >
                  <span class="name">{{ userName }}</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </header>
  </div>
</template>
<script>
import { page } from '@/mixins'
import * as types from '@/store/mutations_types.js'
export default {
  mixins: [page],
  data() {
    return {
      version: ''
    }
  },
  computed: {
    userName() {
      return this.userInfo && this.userInfo.name ? this.userInfo.name : ''
    }
  },
  async mounted() {
    await this.getVersion()
    $('.btn-turn-menu').click(function() {
      $('body').toggleClass('open')
    })
  },
  methods: {
    async getVersion() {
      const version = require(`../assets/version.json`)
      if (version) {
        this.version = version.buildNum
      }
    },
    async logout() {
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
