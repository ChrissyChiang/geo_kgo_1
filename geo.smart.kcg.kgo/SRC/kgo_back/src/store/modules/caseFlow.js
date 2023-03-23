import * as types from '../mutations_types.js'
import { $api } from '@/services'
const state = {
  caseFlow: '',
  serverList: [],
  caseType: ''
}

const getters = {
  getCaseFlow: state => state.caseFlow,
  getServerList: state => state.serverList,
  getCaseType: state => state.caseType
}

const actions = {}

// mutations
const mutations = {
  [types.SET_CASE_FLOW](state, caseflow) {
    state.caseFlow = caseflow
  },
  [types.SET_SERVER_LIST](state, serverList) {
    state.serverList = serverList
  },
  [types.SET_CASE_TYPE](state, caseType) {
    state.caseType = caseType
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
