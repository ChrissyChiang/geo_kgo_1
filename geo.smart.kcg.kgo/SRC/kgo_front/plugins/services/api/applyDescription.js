import {
  CheckData,
  BID_INSTRUCTION,
  BID_DOWNLOAD,
  BID_INSTRUCTION_GET_LINK,
  BID_INSTRUCTION_CHECK_STATUS_IS_ON
} from './constants'
export default ($axios, inject) => {
  inject('apiApplyDescription', {
    async getBidDescription(param, onError) {
      try {
        return CheckData(await $axios.post(BID_INSTRUCTION, param))
      } catch (error) {
        if (onError) onError(error)
        return null
      }
    },
    checkCaseOnline(caseSetId) {
      const formData = new FormData()
      formData.append('caseSetId', caseSetId)
      return $axios.post(BID_INSTRUCTION_CHECK_STATUS_IS_ON, formData)
    },
    downloadBidFile(param) {
      return $axios.post(BID_DOWNLOAD, param, { responseType: 'arraybuffer' })
    },
    getBidUrl(param) {
      return $axios.post(BID_INSTRUCTION_GET_LINK, param)
    }
  })
}
