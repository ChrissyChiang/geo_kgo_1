<template>
  <div class="demo-container">
    <div class="fsm-form">
      <div class="row">
        <div class="col-xs-6 form-group col-md-6">
          <label for="input_event" class="col-sm-4 control-label">
            案件狀態
          </label>
          <div class="col-sm-8">
            <label for="input_event" style="padding-top: 7px">
              {{ status }}
            </label>
          </div>
        </div>
      </div>
    </div>
    <div
      id="accordion"
      class="panel-group"
      role="tablist"
      aria-multiselectable="true"
    >
      <div class="panel panel-default defaultcollapse">
        <div
          id="headingOne"
          class="panel-heading"
          role="tab"
          :href="'#collapse1'"
          data-toggle="collapse"
          aria-expanded="true"
          :aria-controls="'collapse1'"
        >
          <h4 class="panel-title" style="font-size: 1em; font-weight: 500">
            <a role="button">申請資料</a>
          </h4>
        </div>
        <div
          :id="'collapse1'"
          class="panel-collapse collapse in"
          role="tabpanel"
          aria-labelledby="headingOne"
        >
          <div class="panel-body">
            <table class="table table-bordered">
              <tr>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label">案件編號</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;">
                  <label class="control-label">{{ caseId }}</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label">申請時間</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;width: 300px">
                  <label class="control-label">{{ applyDate }}</label>
                </td>
              </tr>
              <tr>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label">案件名稱</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px">
                  <label class="control-label">{{ caseName }}</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label"></label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;width: 300px">
                  <label class="control-label"></label>
                </td>
              </tr>
              <tr>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label">限辦天數</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;">
                  <label class="control-label">{{ limitDay }}</label>
                  天
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label">限辦期間</label>
                </td>
                <td style="border: 1px solid #ddd;padding: 16px;width: 300px">
                  <label class="control-label">{{ limitTime }}</label>
                </td>
              </tr>
              <tr>
                <td style="border: 1px solid #ddd;padding: 16px;width: 200px">
                  <label class="control-label">申請資料</label>
                </td>
                <td colspan="3" style="border: 1px solid #ddd;padding: 16px;">
                  <table class="table table-bordered">
                    <tr>
                      <td
                        style="border: 1px solid #ddd;padding: 16px ;width: 200px"
                      >
                        <label class="control-label">欄位名稱</label>
                      </td>
                      <td style="border: 1px solid #ddd;padding: 16px;">
                        <label class="control-label">資料</label>
                      </td>
                    </tr>
                    <tr v-for="(item, index) in extraData" :key="index">
                      <!--加入動態資料-->
                      <td
                        style="border: 1px solid #ddd;padding: 16px;width: 200px"
                      >
                        <label class="control-label">
                          {{ item.columnName }}
                        </label>
                      </td>
                      <td
                        v-if="
                          item.columnType != 'M' &&
                            item.columnType != 'Radio' &&
                            item.columnType != 'Checkbox'
                        "
                        style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                      >
                        <label class="control-label">
                          {{ item.columnValue }}
                        </label>
                      </td>
                      <!--顯示Radio樣式-->
                      <td
                        v-if="item.columnType == 'Radio'"
                        style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                        class="change_line"
                      >
                        <input type="radio" checked readonly disabled />
                        <label class="control-label">
                          {{ item.columnValue }}
                        </label>
                      </td>
                      <!--顯示Checkbox樣式 -->
                      <td
                        v-if="item.columnType == 'Checkbox'"
                        style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                      >
                        <div v-for="(item1, index) in item.data" :key="index">
                          <input type="checkbox" checked readonly disabled />
                          <label class="control-laberl">{{ item1 }}</label>
                        </div>
                      </td>
                      <td
                        v-if="item.columnType == 'M'"
                        style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                      >
                        <div
                          v-for="(item2, index) in item.complex"
                          :key="index"
                        >
                          <div
                            v-for="(item3, index) in item2.length"
                            :key="index"
                            style="display:inline-block;"
                          >
                            <!--顯示覆核欄位Checkbox樣式 -->
                            <div
                              v-if="
                                item2[index].columnType == 'SCheckbox' &&
                                  item2[index].value != ''
                              "
                            >
                              <input
                                type="checkbox"
                                checked
                                readonly
                                disabled
                              />
                              {{ item2[index].fText }}
                              {{ item2[index].bText }}
                            </div>
                            <div
                              v-else-if="
                                item2[index].columnType == 'SCheckbox' &&
                                  item2[index].value == ''
                              "
                            >
                              <input type="checkbox" readonly disabled />
                              {{ item2[index].fText }}
                              {{ item2[index].bText }}
                            </div>
                            <!--顯示覆核欄位Radio樣式 -->
                            <div
                              v-else-if="
                                item2[index].columnType == 'SRadio' &&
                                  item2[index].value != ''
                              "
                            >
                              {{ item2[index].fText }}
                              <input type="radio" checked readonly disabled />
                              {{ item2[index].bText }}
                            </div>
                            <div
                              v-else-if="
                                item2[index].columnType == 'SRadio' &&
                                  item2[index].value == ''
                              "
                            >
                              {{ item2[index].fText }}
                              <input type="radio" readonly disabled />
                              {{ item2[index].bText }}
                            </div>
                            <div
                              v-else-if="
                                item2[index].columnType != 'SCheckbox' &&
                                  item2[index].columnType != 'SRadio'
                              "
                              style="width:auto"
                            >
                              {{ item2[index].fText }}
                              {{ item2[index].value }}
                              {{ item2[index].bText }}
                            </div>
                          </div>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="panel panel-default defaultcollapse">
        <div
          id="headingOne"
          class="panel-heading"
          role="tab"
          :href="'#collapse2'"
          data-toggle="collapse"
          aria-expanded="true"
          :aria-controls="'collapse2'"
        >
          <h4 class="panel-title" style="font-size: 1em;font-weight:500">
            <a role="button">上傳附件</a>
          </h4>
        </div>
        <div
          :id="'collapse2'"
          class="panel-collapse collapse"
          role="tabpanel"
          aria-labelledby="headingOne"
        >
          <div class="panel-body">
            <app-table
              :table-class="'table'"
              :columns="tableColumns"
              :data="tableData"
              :show-pagination="false"
            ></app-table>
          </div>
        </div>
      </div>
      <div class="panel panel-default defaultcollapse">
        <div
          id="headingOne"
          class="panel-heading"
          role="tab"
          :href="'#collapse3'"
          data-toggle="collapse"
          aria-expanded="true"
          :aria-controls="'collapse3'"
        >
          <h4 class="panel-title" style="font-size: 1em; font-weight: 500">
            <a role="button">處理歷程</a>
          </h4>
        </div>
        <div
          :id="'collapse3'"
          class="panel-collapse collapse"
          role="tabpanel"
          aria-labelledby="headingOne"
        >
          <div class="panel-body">
            <app-table
              :table-class="'table'"
              :columns="historyTableColumns"
              :data="historyTableData"
              :show-pagination="false"
            ></app-table>
            <!-- 隱藏Activity流程圖 -->
            <img
              v-if="false"
              :src="'data:image/svg+xml;base64,' + historyImage"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="fsm-form">
      <table class="table table-unstyled">
        <tbody>
          <tr colspan="4" align="center">
            <td>
              <button type="button" class="btn btn-fsm" @click="goBack()">
                <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                返回
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      status: '',
      caseId: '',
      applyDate: '',
      caseName: '',
      tableData: [],
      extraData: [],
      limitDay: '',
      limitTime: '',
      historyTableData: [],
      historyImage: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '標題',
          dataIndex: 'title',
          tdClass: 'text-center'
        },
        {
          title: '附件',
          dataIndex: 'fileName',
          tdClass: 'text-center'
        }
      ]
    },
    historyTableColumns() {
      return [
        {
          title: '案件狀態',
          dataIndex: 'status',
          tdClass: 'text-center'
        },
        {
          title: '處理機關',
          dataIndex: 'organ',
          tdClass: 'text-center'
        },
        {
          title: '內容',
          dataIndex: 'content',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'taker',
          tdClass: 'text-center'
        },
        {
          title: '處理時間',
          dataIndex: 'dealTime',
          tdClass: 'text-center'
        }
      ]
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      this.caseId = this.$route.params.id
      await this.loadingContainer(async () => {
        //所有的案件檢視都共用同一隻api
        const res = await this.$api.cases.getCaseViewSaHomeData({
          caseId: this.caseId
        })
        this.status = res.data.data.status
        this.applyDate = res.data.data.applyDate
        this.caseName = res.data.data.caseName
        this.extraData = res.data.data.applyData
        this.extraData = this.extraData.map(item => ({
          ...item,
          data:
            item.columnType == 'Checkbox' && item.columnValue.indexOf(',') > 0
              ? item.columnValue.split(',')
              : [item.columnValue]
        }))
        this.limitDay = res.data.data.limitDay
        this.limitTime = res.data.data.limitTime
        this.tableData = res.data.data.downloadData
        this.historyImage = res.data.data.image
        this.historyTableData = res.data.data.historyData
      })
    },
    goBack() {
      this.$router.push(`/signWaiting`)
    }
  }
}
</script>
