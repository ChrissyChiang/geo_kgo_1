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
                  審核者
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ manager1 }}</label>
                </div>
                <!--<label for="input_event" class="col-sm-4 control-label">
                  聯絡電話
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ phone }}</label>
                </div>-->
              </div>
            </div>
          </div>
          <!--<div class="row">
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
                主管1
              </label>
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ manager1 }}</label>
              </div>
            </div>
          </div>-->
          <!--<div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                機關管理者
              </label>
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ manager2 }}</label>
              </div>
            </div>
          </div>-->
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  選擇角色
                </label>
                <base-check-list
                  v-model="selectedAuthRoles"
                  :options="authRole"
                  :selected-list="selectedAuthRoles"
                  :disabled="true"
                />
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
          <img :src="'data:image/svg+xml;base64,' + historyImage" />
        </div>
      </div>
    </div>
    <div class="fsm-form">
      <table class="table table-unstyled">
        <tbody>
          <tr colspan="4" align="center">
            <td>
              <button type="button" class="btn btn-fsm" @click="goBack">
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
      taskId: '',
      organName: '',
      organId: '',
      unitName: '',
      unitId: '',
      applicant: '',
      phone: '',
      email: '',
      manager1: '',
      manager2: '',
      historyTableData: [],
      supervisor: '',
      supervisor2: '',
      authRole: [],
      selectedAuthRoles: [],
      opinion: '',
      reviewer: {},
      uraStage: '',
      historyImage: ''
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
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      //加入初始畫面api
      this.caseId = this.$route.params.id
      this.taskId = this.$route.params.taskId
      this.uraStage = this.$route.params.uraStage
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getAuthorityApplyHomeData({
          caseId: this.caseId,
          taskId: this.taskId
        })
        this.organName = res.data.data.applyOrgan
        this.unitName = res.data.data.applyUnit
        this.applicant = res.data.data.applyUser
        this.email = res.data.data.email
        this.phone = res.data.data.phone
        this.authRole = res.data.data.applyRoles.filter(
          item =>
            item.value == 'CASE_M' ||
            item.value == 'UNIT_A' ||
            item.value == 'UNIT_M'
        )
        this.supervisor = res.data.data.applyUser
        this.historyImage = res.data.data.image
        this.manager1 = res.data.data.manager1
        this.manager2 =
          res.data.data.manager2 === undefined ? '' : res.data.data.manager2
        this.historyTableData =
          res.data.data.serviceApplyUraPendingReviewHistoryDataGrid
        this.reviewer = res.data.data.reviewer
        res.data.data.applyRoles.forEach(item => {
          if (item.selected) {
            this.selectedAuthRoles.push(item.value)
          }
        })
      })
    },
    goBack() {
      this.$router.push(`/reviewAlready`)
    }
  }
}
</script>
