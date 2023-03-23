<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                案件編號
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="caseId"
                  :placeholder="'請輸入案件編號'"
                  input-length="20"
                ></base-input>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                申請時間起迄
              </label>
              <div class="col-sm-8">
                <base-date
                  v-model="pfDate"
                  placeholder="請選擇申請日期區間"
                  :is-range="true"
                  :value="pfDate"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                承辦人
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="taker"
                  :placeholder="'請輸入承辦人'"
                  input-length="20"
                ></base-input>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                服務案件名稱
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="caseName"
                  :placeholder="'請輸入服務案件名稱'"
                  input-length="20"
                ></base-input>
              </div>
            </div>

            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="button" class="btn btn-fsm" @click="search()">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <!--<button
                      type="submit"
                      class="btn btn-fsm"
                      @click="checkChange()"
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      批次異動
                    </button>-->
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12" style>
              <div class="table-responsive">
                <h4 class="alert alert-success">
                  <label style="color='#FFFFFF'">案件列表</label>
                </h4>
                <app-table
                  :table-class="'table'"
                  :columns="tableColumns"
                  :data="tableData"
                  :show-pagination="true"
                >
                  <template
                    v-slot:operating="{ data: { caseId, organId, taskId } }"
                  >
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="changeCase(caseId, organId, taskId)"
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      <a>變更承辦人</a>
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <app-modal ref="popCaseChange" :modal-title="'請指定受理承辦人'">
        <case-change
          ref="caseChange"
          :organ-id="organId"
          :organ-options="organOptions"
          :unit-options="unitOptions"
          :choose-single="true"
          @close-modal="closeCaseChange"
          @decide-people="saveCaseChange"
        ></case-change>
      </app-modal>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
import caseChange from './components/caseChange'
export default {
  components: { caseChange },
  mixins: [page],
  data() {
    return {
      caseId: '',
      organId: '',
      taskId: '',
      pfDate: [],
      taker: '',
      caseName: '',
      tableData: [],
      selectList: [],
      changeSeq: '',
      organOptions: {},
      unitOptions: {}
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '案件編號',
          dataIndex: 'caseId',
          tdClass: 'text-center',
          sorter: true
        },
        {
          title: '申請時間',
          dataIndex: 'applyDate',
          tdClass: 'text-center',
          sorter: true
        },
        {
          title: '申請人',
          dataIndex: 'applicant',
          tdClass: 'text-center'
        },
        {
          title: '案件名稱',
          dataIndex: 'caseName',
          tdClass: 'text-center w-350',
          sorter: true
        },
        {
          title: '限辦日期',
          dataIndex: 'limitDate',
          tdClass: 'text-center',
          sorter: true
        },
        {
          title: '承辦人',
          dataIndex: 'officerName',
          tdClass: 'text-center'
        },
        {
          title: '案件異動',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.queryCaseChangeCase({
          applyDate: ['', ''],
          caseId: '',
          caseName: '',
          officer: ''
        })
        this.tableData = res.data.data.grid
      })
    },
    async search() {
      let date = [
        this.pfDate.length >= 1 ? this.pfDate[0].replace(/\//g, '') : '',
        this.pfDate.length == 2 ? this.pfDate[1].replace(/\//g, '') : ''
      ]
      let request = {
        caseId: this.caseId,
        applyDate: date,
        officer: this.taker,
        caseName: this.caseName
      }
      //加入查詢api
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.queryCaseChangeCase(request)
        this.tableData = res.data.data.grid
      })
    },
    checkChange() {
      if (this.selectList.length <= 0) {
        this.notifySuccess('請選擇要異動之案件')
        return
      }
      this.changeCase('')
    },
    changeCase(caseId, organId, taskId) {
      this.changeSeq = caseId
      this.organId = organId
      this.taskId = taskId
      this.$refs.caseChange.getHomeData()
      this.$refs.popCaseChange.show()
    },
    closeCaseChange() {
      this.$refs.popCaseChange.hide()
    },
    async saveCaseChange(selectTakerList) {
      let rtnCode = ''
      let msg = ''
      let request = [
        {
          caseId: this.changeSeq,
          taskId: this.taskId,
          officer: selectTakerList.selectedTable[0].userId
        }
      ]
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getChangeTaker({
          caseHandleCaseUpdateUpdateColumnList: request
        })
        rtnCode = res.data.rtnCode
        msg = res.data.msg
      })
      if (rtnCode == '0000') {
        this.notifySuccess(this.changeSeq + '變更承辦人成功！')
      } else {
        this.notifySuccess(msg)
      }
      //this.selectList = []
      this.changeSeq = ''
      this.taskId = ''
      this.$refs.popCaseChange.hide()
      this.getHomeData()
    }
  }
}
</script>
