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
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ caseId }}</label>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  機關
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ organName }}</label>
                </div>
              </div>

              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  科室
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ unitName }}</label>
                </div>
              </div>
            </div>
          </div>
          <div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  申請人
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ applicant }}</label>
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  聯絡電話
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ phone }}</label>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                電子郵件
              </label>
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ email }}</label>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                審核主管
              </label>
              <div class="col-sm-8">
                <label>{{ supervisor }}</label>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  選擇身份
                </label>
                <div class="col-xs-2 form-group col-md-2">
                  <input-checkbox
                    v-model="authRole[0].selected"
                    :disabled="true"
                    :label="authRole[0].label"
                  ></input-checkbox>
                </div>
                <div class="col-xs-2 form-group col-md-2">
                  <input-checkbox
                    v-model="authRole[1].selected"
                    :disabled="true"
                    :label="authRole[1].label"
                  ></input-checkbox>
                </div>
                <div class="col-xs-2 form-group col-md-2">
                  <input-checkbox
                    v-model="authRole[2].selected"
                    :disabled="true"
                    :label="authRole[2].label"
                  ></input-checkbox>
                </div>
                <div class="col-xs-2 form-group col-md-2">
                  <input-checkbox
                    v-model="authRole[3].selected"
                    :disabled="true"
                    :label="authRole[3].label"
                  ></input-checkbox>
                </div>
                <div class="col-xs-2 form-group col-md-2">
                  <input-checkbox
                    v-model="authRole[4].selected"
                    :disabled="true"
                    :label="authRole[4].label"
                  ></input-checkbox>
                </div>
              </div>
            </div>
          </div>
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
        <h4 class="panel-title" style="font-size: 1em;font-weight:500">
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
      id: '',
      caseId: '',
      organName: '',
      organId: '',
      unitName: '',
      unitId: '',
      applicant: '',
      phone: '',
      email: '',
      historyTableData: [],
      supervisor: '',
      authRole: [
        { label: '', selected: false },
        { label: '', selected: false },
        { label: '', selected: false },
        { label: '', selected: false },
        { label: '', selected: false }
      ]
    }
  },
  computed: {
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
  mounted() {
    this.id = this.$route.params.id
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getCaseViewUraHomeData({
          caseId: this.id
        }) //初始畫面帶入案件狀態
        this.caseId = res.data.data.caseId
        this.organName = res.data.data.organName
        //this.organId = res.data.data.caseId
        this.unitName = res.data.data.unitName
        //this.unitId = res.data.data.caseId
        this.applicant = res.data.data.applicant
        this.phone = res.data.data.phone
        this.email = res.data.data.email
        this.historyTableData = res.data.data.historyData
      })
    },
    goBack() {
      this.$router.push(`/caseView`)
    }
  }
}
</script>
