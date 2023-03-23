import * as types from '../mutations_types.js'
import { $api } from '@/services'
import { resetRouter } from '@/router'
const state = {
  accessToken: '',
  refreshToken: '',
  userInfo: {}
}

const getters = {
  getAccessToken: state => state.accessToken,
  getRefreshToken: state => state.refreshToken,
  getUserInfo: state => state.userInfo
}

const actions = {
  async login({ commit }, jwt) {
    let isLoginPass = true
    try {
      const _res = await $api.common.login(jwt)
      const res = _res && _res.data && _res.data.data ? _res.data.data : null

      if (res) {
        commit(types.SET_ACCESS_TOKEN, '')
        commit(types.SET_REFRESH_TOKEN, '')
        commit(types.SET_USER_INFO, {})
        commit(types.SET_MENU_ROOT_ID, 'CaseHandle')
        commit(types.SET_MENU_ROOT_NAME, '')
        commit(types.SET_MENU_SUB_NAME, '')
        commit(types.SET_MENU_SUB_ID, null)
        commit(types.SET_MENU, [])
      }
    } catch (e) {
      isLoginPass = false
    }
    return isLoginPass
  },
  async mockLogin({ commit }, { userId, pw }) {
    let isLoginPass = true
    try {
      const _res = await $api.common.loginTest(userId, pw)
      const res = _res && _res.data && _res.data.data ? _res.data.data : null

      if (res) {
        commit(types.SET_ACCESS_TOKEN, '')
        commit(types.SET_REFRESH_TOKEN, '')
        commit(types.SET_MENU_ROOT_ID, 'CaseHandle')
        commit(types.SET_MENU_ROOT_NAME, '')
        commit(types.SET_MENU_SUB_NAME, '')
        commit(types.SET_MENU_SUB_ID, null)
        commit(types.SET_USER_INFO, {})
        commit(types.SET_MENU, [])
      }
    } catch (e) {
      isLoginPass = false
    }
    return isLoginPass
  },
  async logout({ commit }) {
    commit(types.SET_ACCESS_TOKEN, '')
    commit(types.SET_REFRESH_TOKEN, '')
    commit(types.SET_USER_INFO, {})
    commit(types.SET_MENU_ROOT_ID, 'CaseHandle')
    commit(types.SET_MENU_ROOT_NAME, '')
    commit(types.SET_MENU_SUB_NAME, '')
    commit(types.SET_MENU_SUB_ID, null)
    commit(types.SET_MENU, [])
    resetRouter()
  },
  async getLoginUser({ commit }) {
    let isPass = true
    try {
      const _res = await $api.common.getUserInfo()
      const res = _res && _res.data && _res.data.data ? _res.data.data : null
      const userInfo = res.userInfo ? res.userInfo : {}
      const timeout = +res.timeout || 30
      commit(types.SET_USER_INFO, { ...userInfo, timeout })
    } catch (e) {
      isPass = false
    }
    return isPass
  }
}

// mutations
const mutations = {
  [types.SET_ACCESS_TOKEN](state, accessToken) {
    state.accessToken = accessToken
  },
  [types.SET_REFRESH_TOKEN](state, refreshToken) {
    state.refreshToken = refreshToken
  },
  [types.SET_USER_INFO](state, userInfo) {
    state.userInfo = userInfo
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
