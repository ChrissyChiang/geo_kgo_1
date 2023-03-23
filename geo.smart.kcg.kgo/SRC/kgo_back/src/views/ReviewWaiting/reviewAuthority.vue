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
              </div>
            </div>
          </div>
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
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  審核意見
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <base-textarea
                    v-model="opinion"
                    :placeholder="'請輸入審核意見'"
                    :row="5"
                    input-length="500"
                  ></base-textarea>
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
              <button type="button" class="btn btn-fsm" @click="disagree">
                <i class="fa" aria-hidden="true"></i>
                不同意
              </button>
              <button type="button" class="btn btn-fsm" @click="agree">
                <i class="fa" aria-hidden="true"></i>
                同意
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
      historyTableData: [],
      manager1: '',
      manager2: '',
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
        this.uraStage = res.data.data.uraStage
        this.authRole = res.data.data.applyRoles.filter(
          item =>
            item.value == 'CASE_M' ||
            item.value == 'UNIT_A' ||
            item.value == 'UNIT_M'
        )
        this.manager1 = res.data.data.manager1
        res.data.data.applyRoles.forEach(item => {
          if (item.selected) {
            this.selectedAuthRoles.push(item.value)
          }
        })
        this.historyImage = res.data.data.image
        this.historyTableData =
          res.data.data.serviceApplyUraPendingReviewHistoryDataGrid
      })
    },
    goBack() {
      this.$router.push(`/reviewWaiting`)
    },
    async agree() {
      let rtnCode = ''
      let request = { taskId: this.taskId, result: this.opinion }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getAuthorityApplyOmApproveToEnd(
          request
        )
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('送出成功！')
      }
      this.goBack()
    },
    async disagree() {
      let rtnCode = ''
      let request = { taskId: this.taskId, result: this.opinion }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getAuthorityApplyNotApprove(request)
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('送出成功！')
      }
      this.goBack()
    }
  }
}
</script>
