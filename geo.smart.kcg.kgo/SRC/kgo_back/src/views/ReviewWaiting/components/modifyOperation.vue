<template>
  <div>
    <div>
      <div class="fsm-form">
        <div class="row">
          <div class="col-xs-6 form-group col-md-6">
            <label for="input_event" class="col-sm-4 control-label">
              補正狀態
            </label>
            <div class="col-sm-8">
              <label for="input_event" style="padding-top:7px">
                {{ dataObject.state }}
              </label>
            </div>
          </div>
        </div>
      </div>
      <div class="fsm-form">
        <div class="row">
          <div class="col-xs-6 form-group col-md-6">
            <label for="input_event" class="col-sm-4 control-label">
              補正期限
            </label>
            <div class="col-sm-8">
              <input
                v-if="dataObject.state == '未通知'"
                id="fixlimitdate"
                v-model="limitDay"
                name="fixlimitdate"
                :maxlength="2"
                type="text"
              />
              <label v-if="dataObject.state != '未通知'">
                {{ dataObject.correctDeadline }}
              </label>
              日
              <button
                v-if="dataObject.state == '未通知'"
                class="btn btn-fsm"
                @click="modifyNotice"
              >
                <i class="fa" aria-hidden="true"></i>
                補正通知
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="fsm-form">
        <div class="row">
          <app-table
            style="padding-left:20px;padding-right:20px"
            :table-class="'table'"
            :columns="
              dataObject.state == '已補正'
                ? modifyAftertableColumns
                : tableColumns
            "
            :data="tableData"
            :show-pagination="true"
            :select-by="'columnId'"
            :show-select-field="dataObject.state == '未通知'"
            :selected.sync="selectList"
          >
            <template
              v-slot:columnValue="{
                data: { columnType, columnValue, complex, valueList }
              }"
            >
              <div v-if="columnType != 'M' && columnType != 'Checkbox'">
                {{ columnValue }}
              </div>
              <!--顯示Checkbox樣式 -->
              <div v-else-if="columnType == 'Checkbox'">
                <div v-for="(item, index) in valueList" :key="index">
                  <input
                    v-if="item != ''"
                    type="checkbox"
                    checked
                    readonly
                    disabled
                  />
                  {{ item }}
                </div>
              </div>
              <div v-else>
                <div v-for="(item, index) in complex" :key="index">
                  <div v-for="(item2, index) in item.length" :key="index">
                    <!--顯示覆核欄位Checkbox樣式 -->
                    <div
                      v-if="
                        item[index].columnType == 'SCheckbox' &&
                          item[index].value != ''
                      "
                    >
                      {{ item[index].fText }}
                      <input type="checkbox" checked readonly disabled />
                      {{ item[index].bText }}
                    </div>
                    <div
                      v-else-if="
                        item[index].columnType == 'SCheckbox' &&
                          item[index].value == ''
                      "
                    >
                      {{ item[index].fText }}
                      <input type="checkbox" readonly disabled />
                      {{ item[index].bText }}
                    </div>
                    <!--顯示覆核欄位Radio樣式 -->
                    <div
                      v-else-if="
                        item[index].columnType == 'SRadio' &&
                          item[index].value != ''
                      "
                    >
                      {{ item[index].fText }}
                      <input type="radio" checked readonly disabled />
                      {{ item[index].bText }}
                    </div>
                    <div
                      v-else-if="
                        item[index].columnType == 'SRadio' &&
                          item[index].value == ''
                      "
                    >
                      {{ item[index].fText }}
                      <input type="radio" readonly disabled />
                      {{ item[index].bText }}
                    </div>
                    <div v-else>
                      {{ item[index].fText }}
                      {{ item[index].value }}
                      {{ item[index].bText }}
                    </div>
                  </div>
                </div>
              </div>
            </template>
            <template
              v-slot:correctBValue="{
                data: {
                  columnType,
                  columnValue,
                  complex,
                  correctBValue,
                  isCorrect,
                  valueBList
                }
              }"
            >
              <div v-if="!isCorrect">
                {{ '無需補正' }}
              </div>
              <div v-else>
                <div v-if="columnType != 'M' && columnType != 'Checkbox'">
                  {{ correctBValue }}
                </div>
                <div v-else-if="columnType == 'Checkbox'">
                  <div v-for="(item, index) in valueBList" :key="index">
                    <input
                      v-if="item != ''"
                      type="checkbox"
                      checked
                      readonly
                      disabled
                    />
                    {{ item }}
                  </div>
                </div>
                <div v-else>
                  <div v-for="(item, index) in complex" :key="index">
                    <div v-for="(item2, index) in item.length" :key="index">
                      <div
                        v-if="
                          item[index].columnType == 'SCheckbox' &&
                            item[index].correctBValue != ''
                        "
                      >
                        {{ item[index].fText }}
                        <input type="checkbox" checked readonly disabled />
                        {{ item[index].bText }}
                      </div>
                      <div
                        v-else-if="
                          item[index].columnType == 'SCheckbox' &&
                            item[index].correctBValue == ''
                        "
                      >
                        {{ item[index].fText }}
                        <input type="checkbox" readonly disabled />
                        {{ item[index].bText }}
                      </div>
                      <div
                        v-else-if="
                          item[index].columnType == 'SRadio' &&
                            item[index].correctBValue != ''
                        "
                      >
                        {{ item[index].fText }}
                        <input type="radio" checked readonly disabled />
                        {{ item[index].bText }}
                      </div>
                      <div
                        v-else-if="
                          item[index].columnType == 'SRadio' &&
                            item[index].correctBValue == ''
                        "
                      >
                        {{ item[index].fText }}
                        <input type="radio" readonly disabled />
                        {{ item[index].bText }}
                      </div>
                      <div v-else>
                        {{ item[index].fText }}
                        {{ item[index].correctBValue }}
                        {{ item[index].bText }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
            <template v-slot:explain="{ data: { correctMemo, columnId } }">
              <input
                v-if="dataObject.state == '未通知'"
                :class="'form-control'"
                input-length="50"
                style="width: 100%;"
                :value="correctMemo"
                @change="inputMemo($event.target.value, columnId)"
              />
              <label v-else>{{ correctMemo }}</label>
            </template>
          </app-table>
        </div>
      </div>
      <div class="panel panel-default defaultcollapse">
        <div
          id="headingOne"
          class="panel-heading"
          role="tab"
          :href="'#collapse4'"
          data-toggle="collapse"
          aria-expanded="true"
          :aria-controls="'collapse4'"
        >
          <h4 class="panel-title" style="font-size: 1em;font-weight:500">
            <a role="button">補正紀錄</a>
          </h4>
        </div>
        <div
          :id="'collapse4'"
          class="panel-collapse collapse in"
          role="tabpanel"
          aria-labelledby="headingOne"
        >
          <div class="panel-body">
            <app-table
              :table-class="'table'"
              :columns="historyTableColumns"
              :data="dataObject.recordForms"
              :show-pagination="false"
            ></app-table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'ModifyOperation',
  mixins: [page],
  props: {
    dataObject: {
      type: Object,
      default: () => ({})
    },
    tableData: {
      type: Array,
      default: () => []
    },
    taskId: {
      type: String,
      default: ''
    },
    caseId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dataTable: this.tableData,
      downloadTableData: [],
      selectList: [],
      modifyData: [],
      memo: '',
      limitDay: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        //尚未補正資料欄位
        {
          title: '群組／資料名稱',
          dataIndex: 'columnName',
          tdClass: 'text-center '
        },
        {
          title: '資料內容',
          dataIndex: 'columnValue',
          tdClass: 'text-center w-50',
          slot: 'columnValue'
        },
        {
          title: '補正說明',
          dataIndex: 'description',
          tdClass: 'text-center w-30',
          slot: 'explain'
        }
      ]
    },
    modifyAftertableColumns() {
      return [
        //補正後資料欄位
        {
          title: '群組／資料名稱',
          dataIndex: 'columnName',
          tdClass: 'text-center w-20'
        },
        {
          title: '資料內容',
          dataIndex: 'columnValue',
          tdClass: 'text-center w-30',
          slot: 'columnValue'
        },
        {
          title: '補正前內容',
          dataIndex: 'correctBValue',
          tdClass: 'text-center w-30',
          slot: 'correctBValue'
        },
        {
          title: '補正說明',
          dataIndex: 'description',
          tdClass: 'text-center',
          slot: 'explain'
        }
      ]
    },
    historyTableColumns() {
      return [
        //處理歷程欄位
        {
          title: '案件狀態',
          dataIndex: 'statusName',
          tdClass: 'text-center'
        },
        {
          title: '處理人',
          dataIndex: 'handlerName',
          tdClass: 'text-center'
        },
        {
          title: '處理內容',
          dataIndex: 'memo',
          tdClass: 'text-center'
        },
        {
          title: '處理時間',
          dataIndex: 'handleTime',
          tdClass: 'text-center'
        }
      ]
    }
  },
  watch: {
    tableData() {
      this.dataTable = this.tableData
    }
  },
  methods: {
    async modifyNotice() {
      let rtnCode = []
      let modifyData = []
      modifyData = this.dataTable.map(item => ({
        columnId: item.columnId,
        correctMemo: item.correctMemo,
        isCorrect: this.selectList.indexOf(item.columnId) >= 0 ? '1' : ''
      }))
      let request = {
        caseHandlePendingReviewCorrectUpdateDataGridList: modifyData,
        caseId: this.caseId,
        correctDeadLine: this.limitDay
      }
      await this.loadingContainer(async () => {
        //補正通知待初始畫面調整完後
        const res = await this.$api.cases.getReviewWaitingSignCaseNoticeCorrection(
          request
        )
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('補正通知已成功!')
      }
      this.goBack()
    },
    inputMemo(value, columnId) {
      for (let i = 0; i < this.dataTable.length; i++) {
        if (this.dataTable[i].columnId == columnId) {
          this.dataTable[i].correctMemo = value
        }
      }
    },
    goBack() {
      this.$router.push(`/reviewWaiting`)
    }
  }
}
</script>
