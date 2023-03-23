<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ caseName }}</h2>
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
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="/" title="回首頁">回首頁</a>
          </li>
          <li class="breadcrumb-item">
            <button
              class="btn-gray-outline"
              :title="rootTitle || '標題名稱'"
              @click="goApplyCaseList()"
            >
              {{ rootTitle || '標題名稱' }}
            </button>
          </li>
          <li class="breadcrumb-item">
            <a
              href="javascript:window.location.reload()"
              :title="caseName || '案件名稱'"
            >
              {{ caseName || '案件名稱' }}
            </a>
          </li>
          <li class="breadcrumb-item active" aria-current="page">
            {{ applyTypeName }}
          </li>
        </ol>
      </nav>
      <!-- breadcrumb end -->
      <!-- btn-gray active -->
      <div class="btn-list">
        <a
          v-if="
            dataListE.length > 0 &&
            (dataListC.length > 0 || dataListL.length > 0)
          "
          :class="applyType == 'E' ? 'btn-gray active' : 'btn-gray'"
          href="javascript: void(0)"
          title="網路申辦"
          role="button"
          @click="clickTab('E')"
        >
          <i class="fa fa-desktop" aria-hidden="true"></i>
          網路申辦
        </a>
        <a
          v-if="
            dataListC.length > 0 &&
            (dataListE.length > 0 || dataListL.length > 0)
          "
          :class="applyType == 'C' ? 'btn-gray active' : 'btn-gray'"
          href="javascript: void(0)"
          title="臨櫃申辦"
          role="button"
          @click="clickTab('C')"
        >
          <i class="fa fa-suitcase" aria-hidden="true"></i>
          臨櫃申辦
        </a>
        <a
          v-if="
            dataListL.length > 0 &&
            (dataListC.length > 0 || dataListE.length > 0)
          "
          :class="applyType == 'L' ? 'btn-gray active' : 'btn-gray'"
          href="javascript: void(0)"
          title="書表下載"
          role="button"
          @click="clickTab('L')"
        >
          <i class="fa fa-download" aria-hidden="true"></i>
          書表下載
        </a>
      </div>
      <div class="apply-inner">
        <div class="apply-item" v-for="(item, index) in dataList" :key="index">
          <h3>{{ applyType != 'L' ? item.title : item.typeName }}</h3>
          <div v-if="applyType != 'L'">
            <!--書表下載不顯示html內容-->
            <ol>
              <span v-html="item.contentHtml"></span>
            </ol>
            <ol>
              <button
                v-if="item.canDownload"
                title="附件下載"
                class="btn-gray-outline"
                @click="downloadFile(item.seq, item.fileName)"
              >
                附件下載
              </button>
              <!--<button v-else class="btn-gray-outline" @click="downloadFile(item.contentHtml)">{{  item.contentHtml }}</button>-->
            </ol>
          </div>
          <div v-else>
            <ol v-for="downloadItem in item.grid" :key="downloadItem.seq">
              <button
                class="btn-gray-outline"
                :title="downloadItem.title"
                @click="downloadFile(downloadItem.seq, downloadItem.fileName)"
              >
                {{ downloadItem.title }}
              </button>
            </ol>
          </div>
        </div>
        <div class="form-group text-center">
          <button
            class="btn btn-lg btn-blue-lt"
            title="立即線上申辦"
            v-if="applyType == 'E'"
            @click="checkIsAFlow"
          >
            <i class="fa fa-desktop" aria-hidden="true"></i>
            立即線上申辦
          </button>
          <h4 v-if="false">
            受理期間:{{ acceptDataStart }} ~ {{ acceptDataEnd }}
          </h4>
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
import page from '~/mixins/page.js'
import { saveAs } from 'file-saver'
import moment from 'moment' //format套件
export default {
  layout: 'applyDescription',
  mixins: [page],
  head() {
    return {
      title: this.caseName
    }
  },
  data() {
    var title = ''
    var rootId = ''
    var type = ''
    if (this.root) {
      title = this.root.title
      rootId = this.root.id
      type = this.root.type
    }
    var params = this.$route.params
    var query = this.$route.query
    return {
      id: params.id,
      caseName: '',
      acceptDataStart: '', //受理日期
      acceptDataEnd: '', //受理日期
      applyOnline: '', //案件狀態
      applyType: query.applyType, //申請方式
      applyTypeName: '',
      applyProcess: '', //申辦流程(A流程轉跳從其他頁填寫，其他皆繼續往下填)
      openUrl: '', //若為Ａ流程則要轉跳的網址
      openUrlType: '', //若為Ａ流程則要轉跳網址的方式
      dataListE: [], //網路申辦
      dataListC: [], //臨櫃申辦
      dataListL: [], //書表下載
      rootTitle: title,
      rootType: type,
      rootId: rootId,
      /** A流程是否要開啟驗證碼檢核 */
      aFlowCheckEnable: false,
      msg: ''
    }
  },
  computed: {
    dataList() {
      if (this.applyType == 'E') {
        return this.dataListE
      } else if (this.applyType == 'C') {
        return this.dataListC
      } else {
        return this.dataListL
      }
    }
  },
  watchQuery: ['applyType'],
  async mounted() {
    this.$nextTick(async () => {
      this.rootTitle = this.root.title
      this.rootType = this.root.type
      this.rootId = this.root.id

      this.$nuxt.$loading.start()
      const ac = await this.$store.dispatch('getParam', 'AC')
      this.aFlowCheckEnable = ac === '1'
      var res = await this.$apiApplyDescription.getBidDescription(
        {
          caseSetId: this.id
        },
        error => {
          this.msg = this.$common.errorRspMsg(error)
          this.$refs.modal.show(() => {
            this.$router.push({ path: '/' })
          })
        }
      )
      this.$nuxt.$loading.finish()
      if (!res) return
      //好孕表單特殊需求，點臨櫃申辦進去後，左上方不要出現網路申辦，只留下臨櫃申辦，且直接導到「臨櫃申辦說明」頁
      if(this.id =='S2020112500002' && this.applyType =='C'){
        this.dataListE = []
      }
      else{
        this.dataListE = res.data.eData.grid
      }

      //this.dataListE = res.data.eData.grid
      this.dataListC = res.data.cData.grid
      this.dataListL = res.data.lData.grid
      this.caseName = res.data.caseSetName

      this.applyOnline = res.data.isApply
      this.applyProcess = res.data.caseFlowType

      this.openUrlType = res.data.isOpen //true另開視窗
      this.acceptDataStart =
        res.data.dateStart != ''
          ? moment(res.data.dateStart, 'MMDD').format('MM月DD日')
          : ''

      this.acceptDataEnd =
        res.data.dateEnd != ''
          ? moment(res.data.dateEnd, 'MMDD').format('MM月DD日')
          : ''

      this.changeApplyTypeName()
      // 如果有網路申辦，預點網路申辦頁籤
      /*if (
        this.dataListE.length > 0 &&
        (this.dataListC.length > 0 || this.dataListL.length > 0)
      ) {
        this.clickTab('E')
      }*/
    })
  },
  methods: {
    changeApplyTypeName() {
      switch (this.applyType) {
        case 'E':
          this.applyTypeName = '網路申辦'
          break
        case 'C':
          this.applyTypeName = '臨櫃申辦'
          break
        case 'L':
          this.applyTypeName = '書表下載'
          break
      }
    },
    goApplyCaseList() {
      this.$router.push({
        path: '/apply/' + this.rootId,
        query: { type: this.rootType }
      })
    },
    afterCloseIdentify() {
      this.$refs.identifycode.clear()
    },
    validatePass() {
      this.goApplyStep1()
      this.$refs.identifycode.hide()
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
    async checkIsAFlow() {
      if (!(await this.checkCaseOnline(this.id))) {
        return
      }
      if (this.applyProcess == 'A' && this.aFlowCheckEnable) {
        this.$refs.identifycode.show()
      } else {
        this.goApplyStep1()
      }
    },
    async goApplyStep1() {
      if (this.applyProcess == 'A') {
        //轉到至其他頁
        const res = await this.$apiApplyDescription.getBidUrl(
          {
            caseSetId: this.id,
            validateCode: this.validateCode,
            validateCodeToken: this.validateToken
          },
          error => {
            this.msg = this.$common.errorRspMsg(error)
            this.$refs.modal.show()
          }
        )
        if (this.openUrlType) window.open(res.data.linkUrl)
        //另開分頁
        else {
          document.location.href = res.data.linkUrl //本頁開啟
        }
      } else {
        this.$router.push({
          name: 'apply-step1-id',
          params: {
            id: this.id,
            type: this.applyType
          }
        })
      }
    },
    clickTab(type) {
      this.applyType = type
      this.changeApplyTypeName()
    },
    async downloadFile(seq, fileName) {
      if (fileName.toLocaleLowerCase().indexOf('pdf')) {
        let request = {
          caseSetId: this.id,
          applyType: this.applyType,
          seq: seq
        }
        const res = await this.$apiApplyDescription.downloadBidFile(request)
        this.toPdf(res, fileName)
      } else if (fileName.toLocaleLowerCase().indexOf('xlsx')) {
        let request = {
          caseSetId: this.id,
          applyType: this.applyType,
          seq: seq
        }
        const res = await this.$apiApplyDescription.downloadBidFile(request)
        this.toExcel(res, fileName)
      }
    },
    toPdf(res, setFileName) {
      const file = res
      const blob = new Blob([file], { type: 'application/pdf' })
      saveAs(blob, setFileName)
    },
    toExcel(res, setFileName) {
      const file = res.data
      const blob = new Blob([file])
      saveAs(blob, setFileName)
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
