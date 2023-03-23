export const base = {
  data() {
    return {}
  },
  computed: {
    /** 是否有啟用線上服務 */
    isEServer() {
      return this.serverList.includes('E')
    },
    /** 是否有啟用臨櫃服務 */
    isCServer() {
      return this.serverList.includes('C')
    },
    /** 是否有啟用書表服務 */
    isLServer() {
      return this.serverList.includes('L')
    },
    isAdmin() {
      const roles = this.$store.state.user.userInfo.roles || []
      return roles.includes('ADMIN')
    }
  },
  methods: {
    delay(interval) {
      return new Promise(resolve => {
        setTimeout(resolve, interval)
      })
    },
    /**
     * loading容器
     * @param {Function} asyncFunc 非同步的function
     * @param {Function} finalFunc loading結束finally時，要執行的function
     */
    async loadingContainer(asyncFunc, finalFunc = null) {
      if (!(asyncFunc && {}.toString.call(asyncFunc) === '[object Function]')) {
        return
      }
      const loader = this.$loading.show()
      //this.loading(true)
      try {
        await asyncFunc()
      } catch (e) {
        this.notifyFail(e)
      } finally {
        if (finalFunc && {}.toString.call(finalFunc) === '[object Function]') {
          finalFunc()
        }
        loader.hide()
        //this.loading(false)
      }
    }
  }
}
