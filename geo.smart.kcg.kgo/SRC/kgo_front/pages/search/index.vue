<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ title }}</h2>
        <a
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          class="accesskey"
          accesskey="C"
          name="C"
        >
          :::
        </a>
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <app-navigation page-title="站內搜索結果" />
      <!-- breadcrumb end -->

      <div class="search-text">關鍵字：{{ keywords }}</div>
      <div class="apply-inner">
        <div class="apply-item">
          <div class="search-list" v-for="(item, index) in data" :key="index">
            <a
              :href="'javascript:void(0)'"
              :title="item.caseSetName"
              @click="
                checkIsAFlow(item.caseSetId, item.caseFlowType, item.applyType,item.organId,item.organName)
              "
            >
              <h3>{{ item.caseSetName }}</h3>
              <div v-html="item.contentHtml"></div>
            </a>
          </div>
        </div>
        <div class="text-center">
          <paging
            :currentIndex.sync="current"
            :total="totalPage"
            @getdata="getData"
          />
        </div>
      </div>
    </div>
    <system-confirm ref="modal" :msg="msg" />
    <identify-code-confirm
      ref="identifycode"
      @after-hidden="afterCloseIdentify"
      @validate-pass="validatePass"
    />
  </div>
</template>
<script>
import page from '@/mixins/page.js'
export default {
  mixins:[page],
  data() {
    return {
      keywords: '',
      data: [],
      totalPage: 0,
      //showIndex: 5,
      current: 1,
      tempCaseId: '',
      tempCaseFlowType: '',
      tempApplyType: '',
      /** A流程是否要開啟驗證碼檢核 */
      aFlowCheckEnable: false,
      msg: '',
      title: '站內搜索結果'
      //allPages: []
    }
  },

  async mounted() {
    var params = this.$route.params

    this.keywords = params.keywords
    if (!this.keywords || this.keywords.trim() == '') {
      return
    }
    const ac = await this.$store.dispatch('getParam', 'AC')
    this.aFlowCheckEnable = ac === '1'

    this.$nextTick(async () => {
      await this.getData()
    })
  },
  head() {
    return {
      title: this.title
    }
  },
  methods: {
    validatePass() {
      this.goToUrl(this.tempCaseId, this.tempCaseFlowType, this.tempApplyType)
      this.$refs.identifycode.hide()
    },
    afterCloseIdentify() {
      this.tempCaseId = ''
      this.tempCaseFlowType = ''
      this.tempApplyType = ''
      this.$refs.identifycode.clear()
    },
    async checkCaseOnline(caseSetId) {
      let isOnline = false
      try {
        const res = await this.$apiApplyDescription.checkCaseOnline(caseSetId)
        isOnline = res === true
      } catch (error) {
        console.log(error)
      }
      if (!isOnline) {
        this.msg = '此案件已下架'
        this.$refs.modal.show()
      }
      return isOnline
    },
    async getData() {
      this.$nuxt.$loading.start()
      try {
        var res = await this.$apiHome.getHotSearchQuery({
          gstrKeyword: this.keywords,
          pageNumber: this.current,
          pageSize: 0
        })
        this.totalPage = parseInt(res.data.totalPages)

        this.data = res.data.grids
      } catch (error) {
        console.log(error)
      }
      this.$nuxt.$loading.finish()
    },
    async checkIsAFlow(caseId, caseFlowType, applyType, organId, organName) {
      if (!(await this.checkCaseOnline(caseId))) {
        return
      }
      if (caseFlowType == 'A' && this.aFlowCheckEnable) {
        this.tempCaseId = caseId
        this.tempCaseFlowType = caseFlowType
        this.tempApplyType = applyType
        this.$refs.identifycode.show()
      } else {
        this.goToUrl(caseId, caseFlowType, applyType, organId, organName)
      }
    },
    async goToUrl(caseId, caseFlowType, applyType, organId, organName) {
      if (caseFlowType == 'A') {
        try {
          const res = await this.$apiApplyDescription.getBidUrl(
            {
              caseSetId: caseId,
              validateCode: this.validateCode,
              validateCodeToken: this.validateToken
            },
            error => {
              this.msg = this.$common.errorRspMsg(error)
              this.$refs.modal.show()
            }
          )
          const data = res.data
          const openUrlType = data.linkType || ''
          const linkUrl = data.linkUrl || ''
          if (!linkUrl) return
          if (openUrlType === 'OPEN') {
            window.open(linkUrl)
          } else {
            document.location.href = linkUrl //本頁開啟
          }
        } catch (error) {
          console.error(error)
        }
      } else {
        
        this.root = { title: organName, id: organId, type: 1 }
        this.$router.push({
          path: `/apply/info/${caseId}?applyType=${applyType}`
        })
      }
    }
  }
}
</script>
