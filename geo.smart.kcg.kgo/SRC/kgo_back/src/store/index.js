import Vue from 'vue'
import Vuex from 'vuex'
import * as types from './mutations_types.js'
import i18n from '@/i18n/vue-i18n'
// modules
import caseFlow from './modules/caseFlow'
import user from './modules/user'

import { $api } from '@/services'
import createPersistedState from 'vuex-persistedstate'
import SecureLS from 'secure-ls'
var ls = new SecureLS({
  encodingType: 'aes',
  isCompression: false,
  encryptionSecret: 'test'
})

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLoading: false,
    lang: 'tw',
    menuList: [],
    menuRootId: 'CaseHandle',
    menuRootName: '',
    menuSubId: null,
    menuSubName: '',
    // { title: '主選單名稱' ,mainSeq: '主選單序號', subSeq: '子選單選單序號', subTitle: '子選單名稱', subUrl: '子選單連結' }
    currentTitle: {
      title: '',
      mainSeq: '',
      subSeq: '',
      subTitle: '',
      subUrl: ''
    }
  },
  getters: {
    getLang: state => {
      return state.lang
    },
    getMenus: state => {
      return state.menuList
    },
    getTitle: state => {
      return state.currentTitle
    },
    getMenuRootId: state => {
      return state.menuRootId
    },
    getMenuRootName: state => {
      return state.menuRootName
    },
    getMenuSubId: state => {
      return state.menuSubId
    },
    getMenuSubName: state => {
      return state.menuSubName
    }
  },
  mutations: {
    [types.LOADING](state, trigger) {
      state.isLoading = trigger
    },
    [types.SET_MENU](state, menus) {
      state.menuList = menus
    },
    [types.SET_TITLE](state, title) {
      state.currentTitle = title
    },
    [types.SET_MENU_ROOT_ID](state, menuRootId) {
      state.menuRootId = menuRootId
    },
    [types.SET_MENU_ROOT_NAME](state, menuRootName) {
      state.menuRootName = menuRootName
    },
    [types.SET_MENU_SUB_ID](state, menuSubId) {
      state.menuSubId = menuSubId
    },
    [types.SET_MENU_SUB_NAME](state, menuSubName) {
      state.menuSubName = menuSubName
    },
    LANGUAGE: function(state, lang) {
      state.lang = lang
      i18n.locale = lang
    }
  },
  actions: {
    async getMenu({ commit }, currentRoleId) {
      let isPass = true
      let error = {}
      try {
        const res = await $api.common.getMenu({
          roleId: currentRoleId
        })
        const data = res.data && res.data.data
        const menuList = data && data.menuList ? data.menuList : []

        //暫時取案件數方式-START
        let indexS = menuList[1].childs.indexOf(
          menuList[1].childs.find(item => item.functionId == 'signWaiting') //待簽收匣
        )
        let indexRW = menuList[1].childs.indexOf(
          menuList[1].childs.find(item => item.functionId == 'ReviewWaiting') //待審核匣
        )
        if (indexS >= 0) {
          try {
            const resS = await $api.cases.getSingWaitingCaseSearch({
              caseId: '',
              applyDate: ['', ''],
              applicant: '',
              caseName: ''
            })
            menuList[1].childs[indexS] = {
              ...menuList[1].childs[indexS],
              count: resS.data.data.grid.length
            }
          } catch (error) {
            console.error(error)
          }
        }
        if (indexRW >= 0) {
          try {
            const resRW = await $api.cases.getReviewWaitingCaseSearch({
              caseId: '',
              applyDate: ['', ''],
              applicant: '',
              caseName: ''
            })
            menuList[1].childs[indexRW] = {
              ...menuList[1].childs[indexRW],
              count: resRW.data.data.grid.length
            }
          } catch (error) {
            console.error(error)
          }
        }
        //暫時取案件數方式-END

        //menu 排序
        menuList[1].childs = menuList[1].childs.sort(function(a, b) {
          return a.orderNum > b.orderNum ? 1 : -1
        })
        menuList[2].childs = menuList[2].childs.sort(function(a, b) {
          return a.orderNum > b.orderNum ? 1 : -1
        })
        if (menuList[1].childs.length <= 0) {
          commit(types.SET_MENU_ROOT_ID, 'SystemSetting')
        }
        commit(types.SET_MENU, menuList)
        const rtnCode = res.data.rtnCode || ''
        isPass = rtnCode === '0000'
      } catch (e) {
        error = e
        isPass = false
      }
      return { isPass, error }
    }
  },
  modules: {
    user,
    caseFlow
  },
  plugins: [
    createPersistedState({
      paths: [
        'lang',
        // 'menuRootId',
        // 'menuRootName',
        // 'menuSubId',
        // 'menuSubName',
        'menuList',
        'currentTitle',
        'user.accessToken',
        'user.refreshToken',
        'user.userInfo'
      ],
      storage: {
        getItem: key => ls.get(key),
        setItem: (key, value) => ls.set(key, value),
        removeItem: key => ls.remove(key)
      }
    })
  ]
})
