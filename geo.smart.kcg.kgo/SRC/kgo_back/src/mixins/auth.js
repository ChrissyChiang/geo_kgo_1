export const auth = {
  data() {
    return {
      authList: []
    }
  },
  computed: {
    /** 可執行動作 */
    actions() {
      return this.authList
        .filter(auth => auth.value)
        .map(auth => auth.id.split('_').pop())
    }
  },
  methods: {
    /**
     * 是否有權限執行此動作
     * @param {String} action 執行動作
     */
    isAuth(action) {
      return this.actions.includes(action)
    }
  }
}
