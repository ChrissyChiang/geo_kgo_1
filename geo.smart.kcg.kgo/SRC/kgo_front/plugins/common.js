export default ({ app }, inject) => {
  // Inject $hello(msg) in Vue, context and store.
  inject('common', {
    errorRspMsg(error) {
      if (error && error.response && error.response.data && error.response.data.rtnCode && error.response.data.msg) {
        return `系統異常<br/>代碼:${error.response.data.rtnCode}<br/>訊息:${error.response.data.msg}`
      } else if (error && error.response && error.response.status && error.message) {
        const status = error.response.status;
        return `系統異常<br/>代碼:${status}<br/>訊息:${status == 413? '總檔案大小超過上限': error.message}`
      }else {
        return `系統異常<br/><br/>訊息:${error.message}`
      }
    }
  })
}
