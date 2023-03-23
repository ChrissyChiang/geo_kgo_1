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
                申請人
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="applicant"
                  :placeholder="'請輸入申請人'"
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
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                手機
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="cellphone"
                  :placeholder="'請輸入手機'"
                  input-length="20"
                ></base-input>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                身分證字號
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="IdNumber"
                  :placeholder="'請輸入身分證字號'"
                  input-length="20"
                ></base-input>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                案件狀態
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="caseStatus"
                  :options="caseStatusOptions.options"
                  :select="caseStatus"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                案件類型
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="flowType"
                  :options="flowTypeComboBox.options"
                  :select="flowType"
                />
              </div>
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
                  v-slot:operating="{ data: { caseId, type, statusType } }"
                >
                  <button
                    type="button"
                    class="btn-line btn-warning"
                    @click="showCase(caseId, type, statusType)"
                  >
                    <i class="fa fa-eye" aria-hidden="true"></i>
                    <a>檢視</a>
                  </button>
                </template>
              </app-table>
            </div>
          </div>
        </div>
      </div>
      <modal-confirm
        ref="modalSign"
        :close-text="'取消'"
        :modal-content="'是否確定簽收？'"
        :modal-warn="'確認後，完成簽收案件。案件狀態將會改為審核中'"
        @confirm="signMany()"
      />
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      caseId: '',
      pfDate: [],
      applicant: '',
      caseName: '',
      tableData: [],
      selectList: [],
      caseStatus: '',
      caseStatusOptions: {},
      signSeq: '',
      assignSeq: '',
      flowType: '',
      cellphone: '',
      IdNumber: '',
      flowTypeComboBox: {}
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '序號',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
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
          title: '案件狀態',
          dataIndex: 'statusName',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'officer',
          tdClass: 'text-center'
        },
        {
          title: '案件檢視',
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
      //加入初始api
      await this.loadingContainer(async () => {
        const resH = await this.$api.cases.getCaseViewHomeData() //初始畫面帶入案件狀態
        this.caseStatusOptions = resH.data.data.statusComboBox
        this.flowTypeComboBox = resH.data.data.flowTypeComboBox
        const resQ = await this.$api.cases.queryCaseView({
          caseId: '',
          applyDate: ['', ''],
          applicant: '',
          caseName: '',
          status: '',
          caseFlowType: '',
          id: '',
          cellPhone: ''
        })
        this.tableData = resQ.data.data.grid
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
        applicant: this.applicant,
        caseName: this.caseName,
        status: this.caseStatus,
        caseFlowType: this.flowType,
        id: this.IdNumber,
        cellPhone: this.cellphone
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.queryCaseView(request)
        this.tableData = res.data.data.grid
      })
    },
    showCase(id, type, statusType) {
      if (type == 'URA') {
        //權限申請
        this.$router.push('/caseView/viewAuthority/' + id)
      } else {
        //服務申辦
        //this.$router.push('/caseView/viewCase/' + id)
        this.$router.push({
          name: 'viewCase',
          params: { id: id, type: statusType }
        })
      }
    }
  }
}
</script>
