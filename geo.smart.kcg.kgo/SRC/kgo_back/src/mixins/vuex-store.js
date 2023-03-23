import { mapMutations, mapGetters } from 'vuex'
import * as types from '@/store/mutations_types.js'
export const vuexStore = {
  data() {
    return {}
  },
  computed: {
    ...mapGetters({
      lang: 'getLang'
    }),
    ...mapGetters({
      userInfo: 'getUserInfo'
    }),
    ...mapGetters({
      menuList: 'getMenus'
    }),
    ...mapGetters({
      caseFlow: 'getCaseFlow'
    }),
    ...mapGetters({
      serverList: 'getServerList'
    }),
    ...mapGetters({
      caseType: 'getCaseType'
    }),
    ...mapGetters({
      menuRootId: 'getMenuRootId'
    }),
    ...mapGetters({
      menuRootName: 'getMenuRootName'
    }),
    ...mapGetters({
      menuSubSeq: 'getMenuSubId'
    }),
    ...mapGetters({
      menuSubName: 'getMenuSubName'
    })
  },
  methods: {
    ...mapMutations({
      loading: types.LOADING
    }),
    ...mapMutations({
      setLang: types.LANGUAGE
    }),
    ...mapMutations({
      setCaseFlow: types.SET_CASE_FLOW
    }),
    ...mapMutations({
      setServerList: types.SET_SERVER_LIST
    }),
    ...mapMutations({
      setCaseType: types.SET_CASE_TYPE
    }),
    ...mapMutations({
      setMenuRootId: types.SET_MENU_ROOT_ID
    }),
    ...mapMutations({
      setMenuRootName: types.SET_MENU_ROOT_NAME
    }),
    ...mapMutations({
      setMenuSubId: types.SET_MENU_SUB_ID
    }),
    ...mapMutations({
      setMenuSubName: types.SET_MENU_SUB_NAME
    })
  }
}
