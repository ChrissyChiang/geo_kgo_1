import {
  DETAIL_CHECK,
  CASE_HOME_ACTION,
  PROCESS_SEARCH,
  DETAIL_SAVE,
  DETAIL_ACTION,
  BID_CASE_LIST,
} from './constants'
export default ($axios, inject) => {
  inject('apiCaseList', {
    getBidCaseList(param) {
      return $axios.post(BID_CASE_LIST, param)
    },
    async getCaseDetail(param, onError) {
      try {
        return await $axios.post(DETAIL_ACTION, param)
      } catch (error) {
        if (onError) onError(error)
        return null
      }
    },
    async getCaseDetailCheck(param, onError) {
      try {
        return await $axios.post(DETAIL_CHECK, param)
      } catch (error) {
        if (onError) onError(error)
        return null
      }
    },
    async detailSave(param, onError) {

      try {
        return await $axios.post(DETAIL_SAVE, param)
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }


    },
    async getCaseList(param, onError) {
      try {
        return await $axios.post(PROCESS_SEARCH, param)
      } catch (error) {
        if (onError) onError(error)
        return null
      }
    },
    async getCaseListHome(onError) {
      try {
        return await $axios.post(CASE_HOME_ACTION, {})
      } catch (error) {
        if (onError) onError(error)
        return null
      }
    }
  })
}
