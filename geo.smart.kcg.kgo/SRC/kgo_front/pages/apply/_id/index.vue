<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ classifyName }}</h2>
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
      <app-navigation :page-title="classifyName" />
      <!-- breadcrumb end -->

      <div class="btn-list">
        <!--<a
          class="btn-gray active"
          href="#"
          :title="classifyName"
          role="button"
        >
          <i class="fa fa-file-text" aria-hidden="true"></i>
          {{ classifyName  }}
        </a>-->
        <a
          v-for="item in classifyTabList"
          :key="item.id"
          :class="id == item.value ? 'btn-gray active' : 'btn-gray'"
          href="javascript: void(0)"
          :title="item.name"
          role="button"
          @click="changeTab(item.value)"
        >
          <i class="fa fa-file-text" aria-hidden="true"></i>
          {{ item.name }}
        </a>
      </div>
      <div class="apply-inner">
        <div class="apply-item">
          <table
            id="tablesorter"
            class="tablesorter tablesorter-default table table-striped text-center mb-5 mt-4 tablesorter783b2369e38b2"
            :summary="`此表格是${classifyName}名稱列表，表格分為四直欄，第一直欄標示案件名稱，第二三四直欄則依序為該網路申辦連結、臨櫃申辦連結、表書下載連結`"
            role="grid"
          >
            <thead>
              <tr role="row" class="tablesorter-headerRow">
                <td
                  scope="col"
                  data-column="1"
                  class="text-left tablesorter-header sorter-false tablesorter-headerUnSorted"
                  role="columnheader"
                  aria-disabled="true"
                  unselectable="on"
                  aria-sort="none"
                  aria-label="網路申辦: No sort applied, sorting is disabled"
                  style="user-select: none"
                >
                  <div class="tablesorter-header-inner">案件名稱</div>
                </td>
                <td
                  class="tablesorter-header tablesorter-headerUnSorted"
                  scope="col"
                  data-column="0"
                  tabindex="0"
                  role="columnheader"
                  aria-disabled="false"
                  aria-controls="tablesorter"
                  unselectable="on"
                  aria-sort="none"
                  aria-label="網路申辦: No sort applied, activate to apply an ascending sort"
                  style="user-select: none"
                  @click.prevent="sortApplyTypeEActive"
                  id="thTypeE"
                >
                  <div class="tablesorter-header-inner">網路申辦</div>
                </td>
                <td
                  class="tablesorter-header tablesorter-headerUnSorted"
                  scope="col"
                  data-column="0"
                  tabindex="0"
                  role="columnheader"
                  aria-disabled="false"
                  aria-controls="tablesorter"
                  unselectable="on"
                  aria-sort="none"
                  aria-label="臨櫃申辦: No sort applied, activate to apply an ascending sort"
                  style="user-select: none"
                  @click.prevent="sortApplyTypeCActive"
                  id="thTypeC"
                >
                  <div class="tablesorter-header-inner">臨櫃申辦</div>
                </td>
                <td
                  class="tablesorter-header tablesorter-headerUnSorted"
                  scope="col"
                  data-column="0"
                  tabindex="0"
                  role="columnheader"
                  aria-disabled="false"
                  aria-controls="tablesorter"
                  unselectable="on"
                  aria-sort="none"
                  aria-label="表書下載: No sort applied, activate to apply an ascending sort"
                  style="user-select: none"
                  @click.prevent="sortApplyTypeLActive"
                  id="thTypeL"
                >
                  <div class="tablesorter-header-inner">表書下載</div>
                </td>
              </tr>
            </thead>
            <tbody aria-live="polite" aria-relevant="all">
              <tr role="row" v-for="item in dataList" :key="item.caseSetId">
                <td class="text-left" scope="row">{{ item.caseSetName }}</td>
                <td>
                  <a
                    href="#"
                    v-show="item.isApplyTypeEActive"
                    class="btn-gray-outline"
                    title="立即申辦"
                    role="button"
                    @click.prevent="
                      checkIsAFlow(item.caseSetId, 'E', item.caseFlowType)
                    "
                  >
                    <i class="fa fa-desktop" aria-hidden="true"></i>
                    立即申辦
                  </a>
                </td>
                <td>
                  <a
                    class="btn-gray-outline"
                    v-show="item.isApplyTypeCActive"
                    href=""
                    title="臨櫃資料"
                    role="button"
                    @click.prevent="goApplyDescription(item.caseSetId, 'C')"
                  >
                    <i class="fa fa-suitcase" aria-hidden="true"></i>
                    臨櫃資料
                  </a>
                </td>
                <td>
                  <a
                    class="btn-gray-outline"
                    href=""
                    title="下載"
                    role="button"
                    v-show="item.isApplyTypeLActive"
                    @click.prevent="goApplyDescription(item.caseSetId, 'L')"
                  >
                    <i class="fa fa-download" aria-hidden="true"></i>
                    立即下載
                  </a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="text-center">
          <paging :currentIndex.sync="current" :total="totalPage" />
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
export default {
  layout: 'caseList',
  mixins: [page],
  async asyncData({ app, params, query, store }) {
    let id = params.id
    let type = query.type
    let caseType = ''
    if (query.type == '1') {
      caseType = 'Organ'
    } else if (query.type == '2') {
      caseType = 'Role'
    } else {
      caseType = 'Service'
    }
    let request = { mainType: caseType, value: id }
    try {
      const res = await app.$apiCaseList.getBidCaseList(request)
      const ac = await store.dispatch('getParam', 'AC')
      const acEnable = ac === '1'
      const caseList = res.data.dataGrid || []
      const tabList = res.data.typeGrid || []
      const title = res.data.titleName
      // store.commit('setRoot' , {title: title,id: id,type: query.type})
      return {
        caseList: caseList,
        classifyTabList: tabList,
        id: id,
        classifyName: title,
        menuType: type,
        aFlowCheckEnable: acEnable
      }
    } catch (error) {
      console.log(error)
      return {
        caseList: [],
        classifyTabList: [],
        id: '',
        classifyName: '',
        menuType: '',
        aFlowCheckEnable: false
      }
    }
  },
  head() {
    return {
      title: this.classifyName
    }
  },
  data() {
    return {
      id: '',
      menuType: '',
      page: 1,
      classifyName: '', //分類名
      classifyTabList: [], //分類頁籤名
      caseList: [],
      totalPage: 0,
      current: 1,
      applyTypeEActive: -1,
      applyTypeCActive: 1,
      applyTypeLActive: 1,
      tempCaseSetId: '',
      tempApplyType: '',
      tempCaseFlowType: '',
      /** A流程是否要開啟驗證碼檢核 */
      aFlowCheckEnable: false,
      msg: ''
    }
  },
  computed: {
    dataList() {
      return this.caseList.slice(
        (this.current - 1) * 10,
        (this.current - 1) * 10 + 10
      )
    }
  },
  watchQuery: ['id'],
  watch: {
    page() {
      this.$emit('input', this.page)
    }
  },
  async mounted() {
    //將類別名(社會局)＆類別ID&所選類別(機關別)存入store=>切換上面選單時用
    this.root = { title: this.classifyName, id: this.id, type: this.menuType }
    this.totalPage =
      this.caseList.length / 10 <= 1
        ? 1
        : parseInt(this.caseList.length / 10) + 1
  },
  methods: {
    validatePass() {
      this.goApplyInfo(
        this.tempCaseSetId,
        this.tempApplyType,
        this.tempCaseFlowType
      )
      this.$refs.identifycode.hide()
    },
    afterCloseIdentify() {
      this.tempCaseSetId = ''
      this.tempApplyType = ''
      this.tempCaseFlowType = ''
      this.$refs.identifycode.clear()
    },
    sortApplyTypeEActive() {
      // $(this.$refs.thTypeE).css('tablesorter-header tablesorter-headerAsc')
      // console.log()
      $('#thTypeE').prop(
        'class',
        this.applyTypeEActive == -1
          ? 'tablesorter-header tablesorter-headerAsc'
          : 'tablesorter-header tablesorter-headerDesc'
      )

      $('#thTypeC').prop(
        'class',
        'tablesorter-header tablesorter-headerUnSorted'
      )
      $('#thTypeL').prop(
        'class',
        'tablesorter-header tablesorter-headerUnSorted'
      )
      this.applyTypeEActive *= -1

      this.caseList = this.caseList.sort((x, y) => {
        if (x.isApplyTypeEActive == y.isApplyTypeEActive) return 0
        return x.isApplyTypeEActive == true
          ? this.applyTypeEActive
          : this.applyTypeEActive * -1
      })
    },
    sortApplyTypeCActive() {
      $('#thTypeC').prop(
        'class',
        this.applyTypeCActive == -1
          ? 'tablesorter-header tablesorter-headerAsc'
          : 'tablesorter-header tablesorter-headerDesc'
      )
      $('#thTypeE').prop(
        'class',
        'tablesorter-header tablesorter-headerUnSorted'
      )
      $('#thTypeL').prop(
        'class',
        'tablesorter-header tablesorter-headerUnSorted'
      )

      this.applyTypeCActive *= -1
      this.caseList = this.caseList.sort((x, y) => {
        if (x.isApplyTypeCActive == y.isApplyTypeCActive) return 0
        return x.isApplyTypeCActive == true
          ? this.applyTypeCActive
          : this.applyTypeCActive * -1
      })
    },
    sortApplyTypeLActive() {
      $('#thTypeL').prop(
        'class',
        this.applyTypeLActive == -1
          ? 'tablesorter-header tablesorter-headerAsc'
          : 'tablesorter-header tablesorter-headerDesc'
      )

      $('#thTypeE').prop(
        'class',
        'tablesorter-header tablesorter-headerUnSorted'
      )
      $('#thTypeC').prop(
        'class',
        'tablesorter-header tablesorter-headerUnSorted'
      )
      this.applyTypeLActive *= -1
      this.caseList = this.caseList.sort((x, y) => {
        if (x.isApplyTypeLActive == y.isApplyTypeLActive) return 0
        return x.isApplyTypeLActive == true
          ? this.applyTypeLActive
          : this.applyTypeLActive * -1
      })
    },
    goApplyDescription(id, type) {
      //this.$router.push({ path: '/apply/info/'+id,query: { id:  id ,applyType: type,rootTitle: this.classifyName}  })
      this.$router.push({
        path: '/apply/info/' + id,
        query: { applyType: type }
      })
    },
    async checkIsAFlow(caseSetId, applyType, caseFlowType) {
      if (!(await this.checkCaseOnline(caseSetId))) {
        return
      }
      if (caseFlowType == 'A' && this.aFlowCheckEnable) {
        this.tempCaseSetId = caseSetId
        this.tempApplyType = applyType
        this.tempCaseFlowType = caseFlowType
        this.$refs.identifycode.show()
      } else {
        this.goApplyInfo(caseSetId, applyType, caseFlowType)
      }
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
    async goApplyInfo(caseSetId, applyType, caseFlowType) {
      if (caseFlowType === 'A') {
        try {
          const res = await this.$apiApplyDescription.getBidUrl(
            {
              caseSetId: caseSetId,
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
        this.$router.push({
          path: `/apply/info/${caseSetId}`,
          query: { applyType: applyType }
        })
      }
    },
    changeTab(id) {
      this.$router.push({
        path: '/apply/' + id,
        query: { type: this.menuType }
      })
    },
    getNewRoute() {
      this.id = this.$route.query.seq
    },
    getdata() {
      console.log(this.current)
    }
  }
}
</script>
