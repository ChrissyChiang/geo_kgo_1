import { getField, updateField } from 'vuex-map-fields';
export const state = () => ({
  apiCookie: '',
  root: { title: '', id: '', type: '' },
  timeout: 10,
  userName: '',
  kcoin: 0,
  loginToken: '',
  exchange: '',
  oauthType: '',
  validateCode: '',
  validateToken: '',
})

export function responseError(error) {

}
export const actions = {

  /** @param {string} type SUP智能客服('1'開,'0'關);TO單登閒置時間 */
  async getParam({ commit }, type) {
    let param = ''
    try {
      const resParam = await this.$apiUserAuth.getParamSet()
      const paramData = resParam.data
      if (paramData && paramData.paramSetList) {
        const supParam = paramData.paramSetList.find(x => x.type === type)
        param = supParam ? supParam.value : null
      }
    } catch (error) {
      console.error(error);
      param = ''
    }
    return param
  }

}
export const getters = {
  getField,
}
export const mutations = {
  updateField
  // setApiCookie(state, setCookie){
  //   state.apiCookie = setCookie
  // },
  // setRoot(state, setRoot) {
  //   state.root = setRoot
  // },
  // setTest(state, settest) {
  //   state.test = settest
  // }
}
