<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>
          {{ title }}
        </h2>
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
      <app-navigation page-title="案件查詢" />
      <!-- breadcrumb end -->

      <div class="apply-inner">
        <form>
          <div class="apply-item">
            <div class="form-inline row case-filter">
              <div class="case-item col-lg">
                <label class="sr-only" for="case_num">請輸入申請編號</label>
                <input
                  type="email"
                  class="form-control"
                  id="case_num"
                  v-model="caseId"
                  placeholder="請輸入申請編號"
                />
              </div>
              <div class="case-item col-lg">
                <label class="sr-only" for="case_name">請輸入案件名稱</label>
                <input
                  id="case_name"
                  v-model="caseSetName"
                  type="text"
                  class="form-control"
                  placeholder="請輸入案件名稱"
                />
              </div>
              <div class="case-item pb-0 col-lg">
                <label for="apply_date">申請日期</label>
              </div>
              <div class="case-item col-lg">
                <base-date
                  id="apply_date"
                  placeholder="請選擇日期"
                  :is-range="false"
                  :editable="true"
                  :value="applyDate"
                  v-model="applyDate"
                />
              </div>
              <div class="case-item col-lg">
                <label class="sr-only" for="state_list">狀態選擇</label>
                <select
                  id="state_list"
                  v-model="selectStatus"
                  class="custom-select form-control"
                >
                  <option
                    v-for="(item, index) in status"
                    :key="item.value + index"
                    :value="item.value"
                  >
                    {{ item.label }}
                  </option>
                </select>
              </div>
              <div class="case-item col text-center">
                <button type="button" class="btn btn-org" @click="srarch">
                  確定
                </button>
              </div>
            </div>
            <table
              class="rwdtable table table-striped text-center mb-5 mt-4"
              summary="此表格是案件查詢列表，表格分為五直欄，第一直欄標示申請日期，第二三四五直欄則依序為該案件編號、案件名稱、狀態、案號下載，點案件編號可進入該案件內頁。"
            >
              <caption>案件查詢列表</caption>
              <thead>
                <tr>
                  <td scope="col">
                    <a
                      href="#"
                      title="排序申請日期"
                      @click.prevent="sortApplyDate"
                      style="cursor: pointer; text-decoration: underline"
                    >
                      申請日期
                    </a>
                  </td>
                  <td scope="col">
                    <a
                      href="#"
                      title="排序案件編號"
                      @click.prevent="sortCaseId"
                      style="cursor: pointer; text-decoration: underline"
                    >
                      案件編號
                    </a>
                  </td>
                  <td scope="col">
                    <a
                      href="#"
                      title="排序案件名稱"
                      @click.prevent="sortName"
                      style="cursor: pointer; text-decoration: underline"
                    >
                      案件名稱
                    </a>
                  </td>
                  <td scope="col">
                    <a
                      href="#"
                      title="排序狀態"
                      @click.prevent="sortStatus"
                      style="cursor: pointer; text-decoration: underline"
                    >
                      狀態
                    </a>
                  </td>
                  <td scope="col">案號下載</td>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in dataList" :key="item.caseId">
                  <td scope="row" data-label="申請日期">
                    {{ item.applyDate }}
                  </td>
                  <td data-label="案件編號">
                    <nuxt-link
                      :to="{
                        name: 'case-detail-id',
                        params: { id: item.caseId, type: 3 }
                      }"
                      class="nav-link"
                      :alt="`案件編號${item.caseId}詳細內容`"
                      :title="`案件編號${item.caseId}詳細內容`"
                    >
                      {{ item.caseId }}
                    </nuxt-link>
                  </td>
                  <td data-label="案件名稱">{{ item.caseSetName }}</td>
                  <td class="text-red" data-label="狀態">
                    {{ item.status }}
                  </td>
                  <td data-label="案號下載">
                    <a
                      data-toggle="modal"
                      href="#apply-stub"
                      id="autoclick"
                      class="btn btn-sm btn-org"
                      title="案號下載按鈕"
                      @click="showDownload(item.caseId)"
                    >
                      下載
                    </a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </form>
        <div class="text-center">
          <paging
            :currentIndex.sync="current"
            :total="totalPage"
            @getdata="getData"
          />
        </div>
      </div>
    </div>
    <system-confirm :msg="msg" ref="modal" />

    <div
      class="modal fade"
      id="apply-stub"
      tabindex="-1"
      role="dialog"
      aria-labelledby="apply-stubLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content p-3">
          <div class="modal-header border-0 text-center">
            <div class="modal-title w-100" id="apply-stubLabel">線上申辦</div>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <i class="fa fa-times fa-lg"></i>
            </button>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-8">
                <table
                  class="table apply-stub-table"
                  :summary="`此線上申辦表格，第一直欄是申請編號。`"
                >
                  <caption></caption>
                  <!-- <tr>
                    <th>申請人姓名</th>
                    <td>{{ applyUserName }}</td>
                  </tr> -->
                  <tr>
                    <td>申請編號</td>
                    <td>{{ downloadCaseId }}</td>
                  </tr>
                  <!-- <tr v-if="isShowCaseOrgan">
                    <th>受理機關</th>
                    <td>{{ caseOrganName }}</td>
                  </tr> -->
                </table>
              </div>
              <div class="col-md-4 qrcode-item">
                <div>案件QRcode</div>

                <VueQrcode :value="href" />
                <p>
                  您可以掃描QRcode連結至案件查詢頁面輸入案件密碼後進行案件進度查詢，或連結至便民一路通首頁點選案件進度查詢進入。
                </p>
              </div>
            </div>
          </div>
          <div class="modal-footer justify-content-center">
            <button
              type="button"
              class="btn btn-gray-light"
              data-dismiss="modal"
            >
              關閉
            </button>
            <!-- <button type="button" class="btn btn-gray">列印帳單</button> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import page from '~/mixins/page.js'
import VueQrcode from '@chenfengyuan/vue-qrcode'
export default {
  components: { VueQrcode },
  async asyncData({ app, store, $cookies, redirect }) {},
  mixins: [page],
  data() {
    return {
      applyDate: '',
      caseId: '',
      caseSetName: '',
      selectStatus: '',
      totalPage: 0,
      data: [],
      current: 1,
      status: [],
      msg: '',
      downloadCaseId: '',
      href: '/#',
      sortApplyDateStatus: -1,
      sortIdStatus: 1,
      sortNameStatus: 1,
      sortState: 1,
      title: '案件查詢'
    }
  },
  head() {
    return {
      title: this.title
    }
  },
  computed: {
    dataList() {
      return this.data.slice(
        (this.current - 1) * 10,
        (this.current - 1) * 10 + 10
      )
    }
  },
  mounted() {
    if (!this.isLogin) {
      this.$router.push({
        name: 'case-search'
      })
      return
    }

    this.$nextTick(async () => {
      this.$nuxt.$loading.start()

      //var aa = await this.$apiUserAuth.testLogin()

      var res = await this.$apiCaseList.getCaseList({}, error => {
        this.msg = this.$common.errorRspMsg(error)
        this.$refs.modal.show()
      })

      this.$nuxt.$loading.finish()

      if (!res) return
      this.$nuxt.$loading.start()

      var statusDataRes = await this.$apiCaseList.getCaseListHome(error => {
        this.msg = this.$common.errorRspMsg(error)
        this.$refs.modal.show()
      })
      this.$nuxt.$loading.finish()
      if (!statusDataRes) return

      if (
        statusDataRes.rtnCode == '0000' &&
        statusDataRes.data &&
        statusDataRes.data.caseMainStatusList
      ) {
        this.status = statusDataRes.data.caseMainStatusList
      }
      if (res.rtnCode == '0000') {
        this.data = res.data.grids
        this.totalPage =
          this.data.length / 10 <= 1 ? 1 : parseInt(this.data.length / 10) + 1
      }
    })
  },
  methods: {
    sortApplyDate() {
      this.sortApplyDateStatus *= -1
      this.data = this.data.sort((x, y) => {
        if (x.applyDate == y.applyDate) return 0
        return x.applyDate > y.applyDate
          ? this.sortApplyDateStatus
          : this.sortApplyDateStatus * -1
      })
    },
    sortCaseId() {
      this.sortIdStatus *= -1
      this.data = this.data.sort((x, y) => {
        if (x.caseId == y.caseId) return 0
        return x.caseId > y.caseId ? this.sortIdStatus : this.sortIdStatus * -1
      })
    },
    sortName() {
      this.sortNameStatus *= -1
      this.data = this.data.sort((x, y) => {
        if (x.caseSetName == y.caseSetName) return 0
        return x.caseSetName > y.caseSetName
          ? this.sortNameStatus
          : this.sortNameStatus * -1
      })
    },
    sortStatus() {
      this.sortState *= -1
      this.data = this.data.sort((x, y) => {
        if (x.status == y.status) return 0
        return x.status > y.status ? this.sortState : this.sortState * -1
      })
    },

    showDownload(id) {
      this.downloadCaseId = id
      var http = window.location.href.indexOf('https') != -1 ? 'https' : 'http'
      this.href = http + '://' + window.location.host + '/?caseid=' + id
    },
    async srarch() {
      this.current = 1
      await this.getData()
    },
    async getData() {
      this.$nuxt.$loading.start()

      try {
        var res = await this.$apiCaseList.getCaseList({
          applyDate: this.applyDate,
          caseId: this.caseId,
          caseSetName: this.caseSetName,
          pageNumber: this.current,
          pageSize: 0,
          status: this.selectStatus
        })

        if (res.rtnCode == '0000') {
          this.data = res.data.grids
          this.totalPage =
            this.data.length / 10 <= 1 ? 1 : parseInt(this.data.length / 10) + 1
        }
      } catch (error) {
        console.log(error)
      }
      this.$nuxt.$loading.finish()
    }
  }
}
</script>
