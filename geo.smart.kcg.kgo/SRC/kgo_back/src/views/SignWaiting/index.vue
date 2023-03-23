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
                    <button type="button" class="btn btn-fsm" @click="search">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button
                      type="submit"
                      class="btn btn-fsm"
                      @click="checkSignMany"
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      批次簽收
                    </button>
                    <!--<button
                      type="submit"
                      class="btn btn-fsm"
                      @click="checkAssignMany"
                    >
                      <i class="fa fa-send" aria-hidden="true"></i>
                      批次分文
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
                  :select-by="'caseId'"
                  :selected.sync="selectList"
                >
                  <template
                    v-slot:operating="{
                      data: { statusType, caseId, type, taskId, comboBox }
                    }"
                  >
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="showCase(caseId, type)"
                    >
                      <i class="fa fa-eye" aria-hidden="true"></i>
                      <a>檢視</a>
                    </button>
                    <button
                      v-if="statusType != 'A3'"
                      type="button"
                      class="btn-line btn-warning"
                      @click="checkSign(statusType, caseId, taskId)"
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      <a>簽收</a>
                    </button>
                    <button
                      v-if="type == 'SA' && statusType != 'W3'"
                      type="button"
                      class="btn btn-warning"
                      @click="assignCase(statusType, caseId, taskId, comboBox)"
                    >
                      <i class="fa fa-external-link" aria-hidden="true"></i>
                      分案
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <app-modal ref="choose" :modal-title="'請指定機關或承辦人'">
        <choose-organ-applicant
          ref="chooseOrganApplicant"
          :organ-id="organId"
          :organ-list="organList"
          @close-modal="closeChoose()"
          @save-organ-applicant="saveOrganApplicant"
        ></choose-organ-applicant>
      </app-modal>
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
import ChooseOrganApplicant from './components/chooseOrganApplicant'
export default {
  components: { ChooseOrganApplicant },
  mixins: [page],
  data() {
    return {
      caseId: '',
      pfDate: [],
      applicant: '',
      caseName: '',
      tableData: [],
      selectList: [],
      signCaseId: '',
      assignCaseId: '',
      assignTaskId: '',
      successMsg: '',
      organId: '',
      organList: {},
      taskId: ''
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
          title: '案件處理',
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
      //初始畫面
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getSingWaitingCaseSearch({
          caseId: '',
          applyDate: ['', ''],
          applicant: '',
          caseName: ''
        })
        const resT = await this.$api.cases.getSingWaitingHomeData()
        this.organId = resT.data.organId
        this.tableData = res.data.data.grid
      })
    },
    async search() {
      //案件查詢
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
        const res = await this.$api.cases.getSingWaitingCaseSearch(request)
        this.tableData = res.data.data.grid
      })
    },
    async checkSign(statusType, caseId, taskId) {
      if (statusType == 'W3') {
        //案件狀態為W3時才可簽收
        this.signCaseId = caseId
        this.taskId = taskId
        this.$refs.modalSign.show()
      } else {
        this.notifySuccess(
          '案件編號:' + caseId + '為機關分文案件，不可簽收，請確認！'
        )
      }
    },
    checkSignMany() {
      if (this.selectList.length <= 0) {
        this.notifySuccess('請選擇要簽收之案件')
        return
      }
      let noMeetCase = []
      for (let i = 0; i < this.selectList.length; i++) {
        let tem = this.tableData.filter(
          item => item.caseId == this.selectList[i]
        )
        if (tem[0].statusType != 'W3') {
          noMeetCase.push(tem[0].caseId)
        }
      }
      if (noMeetCase.length == 0) {
        this.$refs.modalSign.show()
      } else {
        this.notifySuccess(
          '案件編號:' +
            noMeetCase.join('&') +
            '為機關分文案件，不可簽收，請確認'
        )
      }
    },
    async signMany() {
      let rtnCode = ''
      if (this.signCaseId != '') {
        //簽收一個
        await this.loadingContainer(async () => {
          const res = await this.$api.cases.getSingWaitingCaseSign({
            caseId: this.signCaseId,
            taskId: this.taskId
          })
          this.successMsg = res.data.caseId + '此案件已簽收成功!'
          rtnCode = res.data.rtnCode
        })
      } else {
        let returnMsg = []
        let selectTaskId = []
        //簽收多個
        for (let i = 0; i < this.selectList.length; i++) {
          for (let m = 0; m < this.tableData.length; m++) {
            if (this.selectList[i] == this.tableData[m].caseId) {
              selectTaskId.push({
                caseId: this.selectList[i],
                taskId: this.tableData[m].taskId
              })
            }
          }
        }
        //selectTaskId-簽收多筆
        await this.loadingContainer(async () => {
          const res = await this.$api.cases.getSingWaitingCaseSignMany({
            forms: selectTaskId
          })
          returnMsg = res.data
          rtnCode = res.data.rtnCode
        })
        if (returnMsg.length > 0) {
          let msg = []
          returnMsg.forEach(item => {
            msg.push(item.caseId)
          })
          this.successMsg = msg.join('&') + '案件已簽收成功!'
        }
      }
      //if (rtnCode == '0000') {回傳沒給rtnCode
      this.notifySuccess(this.successMsg)
      //}
      this.$refs.modalSign.hide()
      this.selectList = []
      this.signCaseId = ''
      //取得目前待簽收匣的案件數
      await this.$store.dispatch('getMenu')
      this.getHomeData()
    },
    checkAssignMany() {
      if (this.selectList.length <= 0) {
        this.notifySuccess('請選擇要分案之案件')
        return
      }
      let noMeetCase = []
      for (let i = 0; i < this.selectList.length; i++) {
        let tem = this.tableData.filter(
          item => item.caseId == this.selectList[i]
        )
        if (tem[0].statusType != 'A3') {
          //案件狀態為A3時才可分文
          noMeetCase.push(tem[0].caseId)
        }
      }
      if (noMeetCase.length == 0) {
        this.showChoose()
      } else {
        this.notifySuccess(
          '案件編號:' +
            noMeetCase.join('&') +
            '為承辦簽收案件，不可分文，請確認！'
        )
      }
    },
    assignCase(statusType, caseId, taskId, organList) {
      //if(statusType != 'A3' ){
      //this.notifySuccess('案件編號:'+caseId+'為承辦簽收案件，不可分文，請確認！')
      //}
      //else{
      this.organList = organList
      this.showChoose()
      this.assignCaseId = caseId
      this.assignTaskId = taskId
      // }
    },
    async saveOrganApplicant(type, shareId) {
      let request = {}
      let returnMsg = []
      let form = []
      let rtnCode = ''
      if (this.assignCaseId != '') {
        //分文一個
        form = [{ caseId: this.assignCaseId, taskId: this.assignTaskId }]

        request = {
          acceptType: type,
          acceptor: shareId,
          forms: form
        }
      } else {
        //分文多個
        for (let i = 0; i < this.selectList.length; i++) {
          for (let m = 0; m < this.tableData.length; m++) {
            if (this.selectList[i] == this.tableData[m].caseId) {
              form.push({
                caseId: this.selectList[i],
                taskId: this.tableData[m].taskId
              })
            }
          }
        }
        request = {
          acceptType: type,
          acceptor: shareId,
          forms: form
        }
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getSingWaitingCaseShareMany(request)
        returnMsg = res.data
        rtnCode = res.data.rtnCode
      })
      if (returnMsg.length > 0) {
        let msg = []
        returnMsg.forEach(item => {
          msg.push(item.caseId)
        })
        this.successMsg = msg.join('&') + '案件已分案成功!'
      }

      //if (rtnCode == '0000') {
      this.notifySuccess(this.successMsg)
      //}
      //加入分案api
      this.$refs.choose.hide()
      this.selectList = []
      this.assignCaseId = []
      await this.$store.dispatch('getMenu')
      this.getHomeData()
    },
    showChoose() {
      this.$refs.chooseOrganApplicant.getData()

      this.$refs.choose.show()
    },
    closeChoose() {
      this.$refs.choose.hide()
    },
    showCase(id, type) {
      if (type == 'URA') {
        //權限申請
        this.$router.push('/signWaiting/viewSignAuthority/' + id)
      } else {
        //服務申辦
        this.$router.push('/signWaiting/viewSignCase/' + id)
      }
    }
  }
}
</script>
