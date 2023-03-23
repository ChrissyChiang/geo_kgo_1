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
                      @click="showCancel()"
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      取消簽收
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
                  <template v-slot:limitDate="{ data: { limitDate, type } }">
                    <label v-if="type != 'URA'">{{ limitDate }}</label>
                  </template>
                  <template v-slot:statusName="{ data: { statusName } }">
                    <label v-if="statusName == '機關管理者審核'">審核中</label>
                    <label v-else>{{ statusName }}</label>
                  </template>
                  <template
                    v-slot:operating="{
                      data: { caseId, type, taskId, acceptSet, uraStage }
                    }"
                  >
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="
                        reviewCase(caseId, type, taskId, acceptSet, uraStage)
                      "
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      <a>簽核</a>
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <modal-confirm
        ref="modalCanel"
        :close-text="'取消'"
        :modal-content="'是否確定取消簽收案件？'"
        :modal-warn="'確認後，案件狀態回到待簽收。'"
        @confirm="cancelReceive()"
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
      signSeq: '',
      assignSeq: ''
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
          slot: 'limitDate',
          sorter: true
        },
        {
          title: '案件狀態',
          tdClass: 'text-center',
          dataIndex: 'statusName',
          slot: 'statusName'
        },
        {
          title: '案件簽核',
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
        const res = await this.$api.cases.getReviewWaitingCaseSearch({
          caseId: '',
          applyDate: ['', ''],
          applicant: '',
          caseName: ''
        })
        this.tableData = res.data.data.grid
        this.tableData = res.data.data.grid.map(item => ({
          ...item,
          check: !item.acceptSet ? false : true
        }))
      })
    },
    async search() {
      let date = [
        this.pfDate.length >= 1 ? this.pfDate[0].replace(/\//g, '-') : '',
        this.pfDate.length == 2 ? this.pfDate[1].replace(/\//g, '-') : ''
      ]
      let request = {
        caseId: this.caseId,
        applyDate: date,
        applicant: this.applicant,
        caseName: this.caseName
      }

      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getReviewWaitingCaseSearch(request)
        this.tableData = res.data.data.grid
      })
    },
    showCancel() {
      if (this.selectList.length <= 0) {
        this.notifySuccess('請選擇要取消簽收之案件')
        return
      }
      this.$refs.modalCanel.show()
    },
    async cancelReceive() {
      this.$refs.modalCanel.hide()
      let cancelCaseList = []
      let returnMsg = []
      let rtnCode = ''
      let successMsg = ''
      let tem = ''
      for (let i = 0; i < this.selectList.length; i++) {
        let tem = this.tableData.find(item => item.caseId == this.selectList[i])
        for (let m = 0; m < this.tableData.length; m++) {
          if (this.selectList[i] == this.tableData[m].caseId) {
            cancelCaseList.push({
              caseId: this.selectList[i],
              rejectTo: this.tableData[m].acceptSet,
              taskId: tem.taskId
            })
          }
        }
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getReviewWaitingSignCaseCancel(
          cancelCaseList
        )
        rtnCode = res.data.rtnCode
        returnMsg = res.data
      })
      if (returnMsg.length > 0) {
        let msg = []
        returnMsg.forEach(item => {
          msg.push(item.caseId)
        })
        this.successMsg = msg.join('&') + '案件已取取消簽收成功!'
      }
      this.notifySuccess(this.successMsg)
      await this.$store.dispatch('getMenu')
      this.getHomeData()
    },
    reviewCase(id, type, taskId, acceptSet, uraStage) {
      if (type == 'URA') {
        this.$router.push({
          name: 'reviewAuthority',
          params: { id: id, taskId: taskId }
        })
      } else {
        this.$router.push({
          name: 'reviewCase',
          params: { id: id, taskId: taskId, acceptSet: acceptSet }
        })
      }
    }
  }
}
</script>
