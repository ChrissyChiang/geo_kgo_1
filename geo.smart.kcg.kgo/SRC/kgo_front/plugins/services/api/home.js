import { HOT_SEARCH, QUERY_ACTION, BID_SERVICE_MENU, ACTIVITY_MESSAGE, ACTIVITY_MESSAGE_DETAIL, ACTIVITY_MESSAGE_FILE_DOWNLOAD} from './constants'
export default ($axios, inject) => {
  inject('apiHome', {
    getBidServiceMenu(param) {
      return $axios.post(BID_SERVICE_MENU, param)
    },
    getActivityMessage() {
      return $axios.post(ACTIVITY_MESSAGE, {})
    },
    getActivityMessageDetail(param) {
      return $axios.post(ACTIVITY_MESSAGE_DETAIL, param)
    },
    getActivityMessageFileDownload(param) {
      return $axios.post(ACTIVITY_MESSAGE_FILE_DOWNLOAD, param,{
        responseType: 'arraybuffer'
      })
    },
    getHotSearchQuery(param) {
      return $axios.post(QUERY_ACTION, param)
    },
    getHotSearch() {
      return $axios.post(HOT_SEARCH, {})
    },
  })
}
