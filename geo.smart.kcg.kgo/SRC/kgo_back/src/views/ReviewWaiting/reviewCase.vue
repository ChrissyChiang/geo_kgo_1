<template>
  <div class>
    <div class="panel panel-fsm">
      <div class="panel-tabs-body">
        <ul class="nav nav-tabs" role="tablist">
          <li class="active" role="presentation">
            <a role="tab" aria-controls="tab01" href="#tab01" data-toggle="tab">
              案件內容
            </a>
          </li>
          <li role="presentation">
            <a role="tab" aria-controls="tab02" href="#tab02" data-toggle="tab">
              補正作業
            </a>
          </li>
          <div class="text-right">
            <button type="button" class="btn btn-fsm" @click="downloadFile">
              <i class="fa fa-download" aria-hidden="true"></i>
              案件下載
            </button>
            <button
              v-if="isShowAgree"
              type="button"
              class="btn btn-fsm"
              @click="agreeFlow(true)"
            >
              <i class="fa fa-check" aria-hidden="true"></i>
              {{ btnDisplayName }}
            </button>
            <button
              v-if="isShowBack"
              type="button"
              class="btn btn-fsm"
              @click="agreeFlow(false)"
            >
              <i class="fa fsm-icon-arrow-left" aria-hidden="true"></i>
              返回上一關
            </button>
            <button
              v-if="!isDynamicFlow"
              type="button"
              class="btn btn-fsm"
              @click="showCancel"
            >
              <i class="fa fa-edit" aria-hidden="true"></i>
              取消簽收
            </button>
            <button
              v-if="isShowEnd"
              type="button"
              class="btn btn-fsm"
              @click="showFinishCase"
            >
              <i class="fa fa-check" aria-hidden="true"></i>
              結案
            </button>
            <button
              v-if="isDynamicFlow"
              type="button"
              class="btn btn-fsm"
              @click="openReSendFlow"
            >
              <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
              修改重送流程
            </button>
            <button type="button" class="btn btn-fsm" @click="goBack">
              <i class="fa fa-undo" aria-hidden="true"></i>
              返回
            </button>
          </div>
        </ul>
      </div>
      <div class="panel-body">
        <div class="tab-content">
          <!--案件內容-->
          <div id="tab01" role="tabpanel" class="tab-pane active">
            <case-content
              ref="caseContent"
              :data-object="caseContent"
              :history-image="historyImage"
              @reload="getHomeData"
            ></case-content>
          </div>
          <!--補正作業-->
          <div id="tab02" role="tabpanel" class="tab-pane">
            <modify-operation
              ref="modifyOperation"
              :data-object="modifyOperation"
              :table-data="modifyOperation.applyData"
              :case-id="caseId"
            ></modify-operation>
          </div>
        </div>
      </div>
    </div>
    <app-modal
      ref="reSendFlowModal"
      :is-large-size="true"
      :modal-title="'修改重送流程'"
      @after-show="afterShowreSendFlow"
      @before-hide="closeReSendFlowModal"
    >
      <!-- <edit-flow
        ref="editFlow"
        :edit-flow-id="flowId"
        :close-text="'關閉'"
        :users-options="usersOptions"
        :flow-organ-id="flowOrganId"
        :is-copy="true"
        @close-flow="closeFlow"
        @edit-node="editNode"
        @confirm-save-flow="confirmSaveFlow"
      />-->
      <flow-panel
        ref="editFlow"
        :edit-flow-id="flowId"
        :flow-organ-id="flowOrganId"
        :close-text="'關閉'"
        @close-flow="closeFlow"
        @open-desc-edit="openDescEdit"
        @open-node-edit="openNodeEdit"
        @confirm-save-flow="confirmSaveFlow"
      />
    </app-modal>
    <app-modal
      ref="nodeEditModal"
      :modal-title="'編輯節點'"
      @before-hide="closeEditFlowTaskModal"
    >
      <edit-node
        ref="nodeEdit"
        :flow-organ-id="flowOrganId"
        :edit-node-id="editNodeId"
        @node-confirm-Save="onNodeEditModalConfirmSave"
      ></edit-node>
    </app-modal>
    <app-modal ref="popFinishCase" :modal-title="'結案'">
      <finish-case
        ref="finishCase"
        :case-status-options="caseStatusOptions"
        @close-modal="closePopFinishCase()"
        @savefinish="saveFinishCase"
      ></finish-case>
    </app-modal>
    <modal-confirm
      ref="modalReviewSuccess"
      :show-close="false"
      :close-text="'取消'"
      :modal-content="successMsg"
      @confirm="hideReviewSuccess()"
    />
    <app-modal
      ref="descEditModal"
      :modal-title="'編輯說明'"
      @before-hide="closeDescEditModal"
    >
      <edit-desc ref="descEdit" @confirm-save-desc="onSaveDesc"></edit-desc>
    </app-modal>
    <modal-confirm
      ref="modalCanel"
      :close-text="'取消'"
      :modal-content="'是否確定取消簽收案件？'"
      :modal-warn="'確認後，案件狀態回到待簽收。'"
      @confirm="cancelReceive()"
    />
    <modal-confirm
      ref="confirmSaveFlow"
      :close-text="'取消'"
      :modal-content="'是否要重送流程？'"
      @confirm="reSendFlow"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
import caseContent from './components/caseContent.vue'
import modifyOperation from './components/modifyOperation'
import finishCase from './components/finishCase'
export default {
  components: {
    caseContent,
    modifyOperation,
    finishCase
  },
  mixins: [page],
  data() {
    return {
      caseId: '',
      taskId: '',
      status: '',
      acceptSet: '',
      caseContent: {}, //案件狀態
      historyImage: '',
      modifyOperation: {}, //補正作業
      caseStatusOptions: {}, //結案狀態
      successMsg: '',
      editIndex: 0,
      usersOptions: [],
      flowOrganId: '',
      /** 是否為動態流程 */
      isDynamicFlow: false,
      /** 是否允許退回上一關 */
      isCanReject: false,
      /** 是否結案 */
      isEnd: false,
      flowId: '',
      tempFlowId: '',
      editNodeId: '',
      /** 流程按鈕顯示名稱 (分文、會簽 or陳核) */
      btnDisplayName: ''
    }
  },
  computed: {
    isShowEnd() {
      if (this.isDynamicFlow) {
        return this.isEnd
      } else {
        return true
      }
    },
    isShowBack() {
      if (this.isDynamicFlow) {
        return this.isCanReject
      } else {
        return false
      }
    },
    isShowAgree() {
      if (this.isDynamicFlow) {
        return !this.isEnd
      } else {
        return false
      }
    }
  },
  async mounted() {
    this.caseId = this.$route.params.id
    this.taskId = this.$route.params.taskId
    this.acceptSet = this.$route.params.acceptSet
    this.getHomeData()
    //await this.getTaker()
    this.flowOrganId = this.$store.state.user.userInfo.organId
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        //所有的案件檢視都共用同一隻api
        const res = await this.$api.cases.getCaseViewSaHomeData({
          caseId: this.caseId,
          taskId: this.taskId
        })
        this.status = res.data.data.status
        this.isDynamicFlow = res.data.data.isDynamicFlow
        this.isCanReject = res.data.data.isCanReject
        this.tempFlowId = res.data.data.flowId || null
        this.btnDisplayName = res.data.data.btnDisplayName || ''
        this.isEnd = res.data.data.isEnd
        this.caseContent = res.data.data
        this.caseContent.applyData = res.data.data.applyData.map(item => ({
          ...item,
          file:
            (item.columnType == 'Fil' || item.columnType == 'Checkbox') &&
            item.columnValue.indexOf(',') > 0
              ? item.columnValue.split(',')
              : [item.columnValue]
        }))
        this.historyImage = res.data.data.image
        //補正作業api
        const resM = await this.$api.cases.getReviewWaitingSignCaseCorrection({
          caseId: this.caseId,
          taskId: this.taskId
        })
        this.modifyOperation = resM.data.data

        if (resM.data.data.applyData != []) {
          this.modifyOperation.applyData = this.modifyOperation.applyData.map(
            item => ({
              columnName: item.columnName,
              columnValue: item.columnValue,
              columnType: item.columnType,
              complex: item.complex,
              columnId: item.columnId,
              correctMemo: !item.correctMemo ? '' : item.correctMemo,
              correctBValue: item.correctBValue,
              isCorrect: item.isCorrect,
              valueList:
                item.columnType == 'Checkbox' &&
                item.columnValue.indexOf(',') > 0
                  ? item.columnValue.split(',')
                  : [item.columnValue],
              valueBList:
                item.columnType == 'Checkbox' &&
                item.correctBValue.indexOf(',') > 0
                  ? item.correctBValue.split(',')
                  : [item.correctBValue]
            })
          )
          this.modifyOperation.state = !resM.data.data.state
            ? '未通知'
            : resM.data.data.state
        }
      })
      this.caseStatusOptions = {
        isShow: true,
        isDisabled: false,
        selectedVal: '',
        options: [
          { label: '結案通過', value: 'F3', groupKey: '', selected: false },
          { label: '結案不通過', value: 'J3', groupKey: '', selected: false },
          { label: '結案存查', value: 'S3', groupKey: '', selected: false }
        ]
      }
      this.caseStatusOptions.selectedVal = this.caseStatusOptions.options[0].value
    },
    async openNodeEdit(data) {
      this.editNodeId = data.editNodeId
      this.flowOrganId = data.flowOrganId
      await this.$refs.nodeEdit.initData(data)
      this.$refs.nodeEditModal.show()
    },
    openDescEdit(data) {
      this.$refs.descEdit.initData(data)
      this.$refs.descEditModal.show()
    },
    onSaveDesc() {
      this.$refs.descEditModal.hide()
      this.$refs.editFlow.repaintEverything()
    },
    afterShowreSendFlow() {
      this.flowId = this.tempFlowId
    },
    onNodeEditModalConfirmSave() {
      this.$refs.nodeEditModal.hide()
      this.$refs.editFlow.repaintEverything()
    },
    closeDescEditModal() {},
    async getTaker() {
      const res = await this.$api.share.getOrganUintUserSelectHome({
        organId: this.flowOrganId,
        unitId: ''
        //roleId: this.isCaseM ? 'CASE_M' : ''
      })
      const data = res.data.data
      const tableData = data.grid || []
      this.usersOptions = tableData.map(x => ({
        label: x.name,
        value: x.userId
      }))
    },
    showFinishCase() {
      this.$refs.popFinishCase.show()
    },
    hideReviewSuccess() {
      this.$refs.modalReviewSuccess.hide()
      this.goBack()
    },
    goBack() {
      this.$router.push(`/reviewWaiting`)
    },
    closePopFinishCase() {
      this.$refs.popFinishCase.hide()
      this.$refs.finishCase.finishExplain = ''
    },
    downloadFile() {
      this.$refs.caseContent.downloadDataToPdf()
    },
    showCancel() {
      this.$refs.modalCanel.show()
    },
    async cancelReceive() {
      this.$refs.modalCanel.hide()
      let returnMsg = []
      let rtnCode = ''
      let successMsg = ''
      let cancelCaseList = [
        { caseId: this.caseId, rejectTo: this.acceptSet, taskId: this.taskId }
      ]
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
        this.successMsg = msg.join('&') + '案件已取消簽收成功!'
      }
      this.notifySuccess(this.successMsg)
      await this.$store.dispatch('getMenu')
      this.$router.push(`/reviewWaiting`)
    },
    async saveFinishCase(caseStatus, finishExplain) {
      //加入結案api
      let rtnCode = ''
      let request = {
        caseId: this.caseContent.caseId,
        description: finishExplain,
        status: caseStatus,
        taskId: this.taskId
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getReviewWaitingEndCase(request)
        this.successMsg = res.data.data.caseId + '此案件已結案成功！'
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess(this.successMsg)
      }
      this.$refs.popFinishCase.hide()
      this.$router.push(`/reviewWaiting`)
      //this.$refs.modalReviewSuccess.show()
    },
    closeFlow() {
      this.$refs.reSendFlowModal.hide()
    },
    // onNodeEditModalConfirmSave(data) {
    //   this.$refs.editFlow.sendNodeSaveData(data)
    //   this.$refs.nodeEditModal.hide()
    // },
    async editNode(data) {
      this.editIndex = data.editIndex
      await this.$refs.nodeEdit.initData(
        data.taskAssigneesCombox,
        data.taskName,
        data.taskType
      )
      this.$refs.nodeEditModal.show()
    },
    closeReSendFlowModal() {
      // this.$refs.editFlow.clear()
    },
    confirmSaveFlow() {
      this.$refs.confirmSaveFlow.show()
    },
    openReSendFlow() {
      this.$refs.reSendFlowModal.show()
    },
    async reSendFlow() {
      let success = false
      await this.loadingContainer(async () => {
        const res = await this.$api.management.resendFlowActFlow({
          caseId: this.caseId,
          ...this.$refs.editFlow.getSaveForm()
        })
        success = this.checkResSuccess(res, false)
        if (success) {
          this.toastSuccess('重送流程成功')
        } else {
          this.toastError('重送流程失敗')
        }
      })
      if (success) {
        this.$refs.confirmSaveFlow.hide()
        await this.delay(100)
        this.$refs.reSendFlowModal.hide()
        this.$router.push(`/reviewWaiting`)
      }
    },
    closeEditFlowTaskModal() {
      this.$refs.nodeEdit.clear()
    },
    async agreeFlow(agree) {
      let success = false
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.doDynamicFlow({
          caseId: this.caseId,
          isAgree: agree,
          taskId: this.taskId
        })
        success = this.checkResSuccess(res)
      })
      if (success) {
        this.$router.push(`/reviewWaiting`)
      }
    }
  }
}
</script>
