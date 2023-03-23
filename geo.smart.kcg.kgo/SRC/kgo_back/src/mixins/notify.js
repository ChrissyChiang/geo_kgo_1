export const notify = {
  methods: {
    notifyFail(title) {
      let errMsg = null
      let errCode = ''
      if (title) {
        if (typeof title === 'object') {
          const errResponse = title.response
          if (
            errResponse &&
            errResponse.data &&
            errResponse.data.byteLength &&
            errResponse.data.byteLength > 0
          ) {
            const enc = new TextDecoder('utf-8')
            const arr = new Uint8Array(errResponse.data)
            const res = JSON.parse(enc.decode(arr))
            errMsg = res && res.msg ? res.msg : title
            errCode = res && res.rtnCode ? res.rtnCode : ''
          } else {
            errMsg =
              errResponse && errResponse.data && errResponse.data.msg
                ? errResponse.data.msg
                : title
            errCode =
              errResponse && errResponse.data && errResponse.data.rtnCode
                ? errResponse.data.rtnCode
                : ''
          }
        } else {
          errMsg = title
        }

        let err = ''
        if (typeof title === 'string') {
          err = errMsg
        } else {
          err += '系統異常<br/>'
          if (errCode) {
            err += `異常代碼: ${errCode}<br/>`
          }
          err += `異常訊息: ${errMsg}`
        }

        $('#sysMsg').html(err)
        $('#sysConfirm').modal('show')
      }
    },
    /**
     * 新增成功
     */
    notifySuccess(msg) {
      $('#sysMsg').html(msg)
      $('#sysConfirm').modal('show')
    },
    /**
     * 新增資料成功
     */
    notifyCreateSuccess() {
      $('#sysMsg').html(this.$t('GENERAL.CREATED_SUCCESS'))
      $('#sysConfirm').modal('show')
    },
    /**
     * 新增資料失敗
     */
    notifyCreateFail() {
      $('#sysMsg').html(this.$t('GENERAL.CREATED_FAIL'))
      $('#sysConfirm').modal('show')
    },
    /**
     * 浮動模式-成功訊息
     */
    toastSuccess(msg) {
      this.$notify({
        group: 'action',
        title: `<div class="icon-img"></div>${msg}`,
        type: 'success'
      })
    },
    toastError(msg) {
      this.$notify({
        group: 'action',
        title: `<div class="icon-img"></div>${msg}`,
        type: 'error'
      })
    },
    checkResSuccess(res, isShowAlert = true) {
      const rtnCode = res.data.rtnCode || ''
      const msg = res.data && res.data.msg ? res.data.msg : ''
      if (rtnCode === '0000') {
        if (isShowAlert) {
          this.toastSuccess(msg)
          //this.notifySuccess(msg)
        }
        return true
      } else {
        if (isShowAlert) {
          this.notifyFail(msg)
        }
        return false
      }
    }
  }
}
