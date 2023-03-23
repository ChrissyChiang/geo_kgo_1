<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                機關分類
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="query.organId"
                  :options="organNameOptions.options"
                  required
                  search
                  :select="query.organId"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                服務編號
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="query.caseSetId"
                  :placeholder="'請輸入服務編號'"
                  :input-length="'50'"
                ></base-input>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  權責機關
                </label>
                <div class="col-sm-8">
                  <base-select
                    v-model="query.ownerOrganId"
                    :options="ownerOrganOptions.options"
                    :select="query.ownerOrganId"
                    search
                  />
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  服務管理者
                </label>
                <div class="col-sm-8">
                  <base-select
                    v-model="query.manager"
                    search
                    :options="roleOptions.options"
                    :select="query.manager"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  服務名稱
                </label>
                <div class="col-sm-8">
                  <base-input
                    v-model="query.caseSetName"
                    :placeholder="'請輸入服務名稱'"
                    :input-length="'50'"
                  ></base-input>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="queryCaseAction"
                    >
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="batchOnStatus"
                    >
                      <i class="fa fsm-icon-arrow-top" aria-hidden="true"></i>
                      批次上架
                    </button>
                    <button type="button" class="btn btn-fsm" @click="addCase">
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      服務新增
                    </button>
                    <button type="button" class="btn btn-fsm" @click="sortEdit">
                      <i class="fa fa-arrows-v" aria-hidden="true"></i>
                      排序設定
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <app-table
            :columns="tableColumns"
            :data="tableData"
            :draggable="false"
            :select-by="'caseSetId'"
            :selected.sync="selectCaseIdList"
            :show-pagination="true"
          >
            <template v-slot:status="{ data: { status } }">
              {{ mappingStatusWord(status) }}
            </template>
            <template
              v-slot:flowName="{ data: { caseType, taskName, flowId } }"
            >
              <a
                v-if="caseType != 'SA' && taskName !== undefined"
                style="cursor: pointer; text-decoration: underline;color:blue"
                @click="showFlow(flowId)"
              >
                {{ taskName }}
              </a>
              <a
                v-if="caseType == 'SA'"
                style="cursor: pointer; text-decoration: underline;color:blue"
                @click="showFlow('SA')"
              >
                服務異動流程
              </a>
            </template>
            <template
              v-slot:operating="{ data: { caseSetId, status, allowDelete } }"
            >
              <button
                type="button"
                class="btn-line btn-warning"
                @click="onOffStatus(caseSetId, status)"
              >
                <i :class="onOffStatusIcon(status)" aria-hidden="true"></i>
                {{ onOffStatusWord(status) }}
              </button>
              <button
                v-if="false"
                type="button"
                class="btn-line btn-warning"
                @click="startStopAcceptingStatus(caseSetId, status)"
              >
                <a>
                  <i class="fa fa-play" aria-hidden="true"></i>
                </a>
                {{ startStopAccepting(status) }}
              </button>

              <button
                type="button"
                class="btn-line btn-warning"
                @click="editCase(caseSetId)"
              >
                <a>
                  <i class="fa fa-cog" aria-hidden="true"></i>
                </a>
                編輯
              </button>
              <button
                type="button"
                class="btn-line btn-warning"
                @click="editMsg(caseSetId)"
              >
                <a>
                  <i class="fa" aria-hidden="true"></i>
                </a>
                推播訊息
              </button>
              <button
                type="button"
                class="btn-line btn-danger"
                :disabled="allowDelete === 'false'"
                @click="showDelConfirm(caseSetId)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
        </div>
      </div>
      <modal-confirm
        ref="confirmDelete"
        :close-text="'取消'"
        :modal-content="'是否確定刪除資料？'"
        :modal-warn="'確認刪除，系統將執行刪除'"
        @confirm="delCase()"
      />
    </div>
    <app-modal
      ref="sortEditModal"
      :modal-title="'排序設定'"
      @after-show="openSortEditModal"
      @before-hide="closeSortEditModal"
    >
      <sort-edit ref="sortEdit" @update-sort="updateOrder"></sort-edit>
      <template v-slot:button>
        <button type="button" class="btn btn-primary" @click="saveSort">
          儲存
        </button>
      </template>
    </app-modal>
    <app-modal
      ref="previewFlowModal"
      :modal-title="'預覽流程圖'"
      :is-large-size="true"
      @after-show="afterShowPreview"
      @before-hide="closeBeforePreviewModal"
    >
      <flow-panel
        ref="previewFlow"
        :edit-flow-id="previewFlowId"
        :is-edit="false"
      />
    </app-modal>
    <app-modal
      ref="pushMsgModal"
      :modal-title="'推播訊息'"
      :is-large-size="true"
    >
      <edit-msg
        ref="pushMsg"
        :case-set-id="editMsgCaseSetId"
        @close-edit-msg="closeEditMsg"
      />
    </app-modal>
  </div>
</template>
<script>
import { page } from '@/mixins'
import SortEdit from './components/SortEdit'
import EditMsg from './components/EditMsg'
export default {
  components: {
    SortEdit,
    EditMsg
  },
  mixins: [page],
  data() {
    return {
      tableData: [],
      unitName: '',
      deleteCaseSetId: '',
      editMsgCaseSetId: '',
      organNameOptions: {},
      ownerOrganOptions: {},
      roleOptions: {},
      selectCaseIdList: [],
      previewFlowId: '',
      tempFlowId: '',
      query: {
        /** 服務種類ID */
        caseSetId: '',
        /** 服務名稱 */
        caseSetName: '',
        /** 管理者帳號 */
        manager: '',
        /** 機關分類代碼 */
        organId: '',
        /** 權責機關代碼 */
        ownerOrganId: ''
      }
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '核選',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
        {
          title: '服務編號',
          dataIndex: 'caseSetId',
          tdClass: 'text-center',
          sorter: true
        },
        {
          title: '服務名稱',
          dataIndex: 'caseSetName',
          tdClass: 'text-left w-350',
          sorter: true
        },
        {
          title: '權責機關',
          dataIndex: 'ownerOrganName',
          tdClass: 'text-center'
        },
        {
          title: '服務管理者',
          dataIndex: 'managerName',
          tdClass: 'text-center'
        },
        {
          title: '流程名稱',
          dataIndex: 'text-center',
          slot: 'flowName'
        },
        {
          title: '狀態',
          tdClass: 'text-center',
          slot: 'status'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    }
  },
  watch: {
    'query.ownerOrganId': {
      handler() {
        this.getServiceManager()
      }
    }
  },
  async activated() {
    this.getHomeAction()
  },
  async mounted() {
    this.getHomeAction()
  },
  methods: {
    async getHomeAction() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getCassManagementHomeData()
        const data = res.data.data
        this.organNameOptions = data.organComboBox ? data.organComboBox : {}
        this.ownerOrganOptions = data.ownerOrganComboBox
          ? data.ownerOrganComboBox
          : {}
        /*this.roleOptions = data.caseManagerComboBox
          ? data.caseManagerComboBox
          : {}*/
        await this.queryCase()
      })
    },
    async getServiceManager() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getCaseManagementGetServiceManager(
          { organId: this.query.ownerOrganId }
        )
        this.roleOptions = res.data.data.caseManagerComboBox
      })
    },
    sortEdit() {
      this.$refs.sortEditModal.show()
    },
    saveSort() {
      this.$refs.sortEdit.save()
    },
    openSortEditModal() {
      this.$refs.sortEdit.initData(this.query.organId)
    },
    closeSortEditModal() {
      this.$refs.sortEdit.clear()
    },
    async queryCaseAction() {
      await this.loadingContainer(async () => {
        await this.queryCase()
      })
    },
    async queryCase() {
      const res = await this.$api.management.queryCassManagementOrder(
        this.query
      )
      const data = res.data.data
      this.tableData = data.grid || []
    },
    async updateOrder() {
      this.$refs.sortEditModal.hide()
      await this.loadingContainer(async () => {
        await this.queryCase()
      })
    },
    showDelConfirm(caseSetId) {
      this.deleteCaseSetId = caseSetId
      this.$refs.confirmDelete.show()
    },
    editCase(caseSetId) {
      this.$router.push(`/caseManagement/caseEdit/${caseSetId}`)
    },
    async delCase() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteCassManagement({
          caseSetId: this.deleteCaseSetId
        })
        const success = this.checkResSuccess(res, false)
        this.$refs.confirmDelete.hide()

        if (success) {
          this.toastSuccess('刪除成功')
          await this.queryCase()
        } else {
          this.toastSuccess('刪除失敗')
        }
      })
    },
    async batchOnStatus() {
      if (this.selectCaseIdList.length > 0) {
        await this.updateStatus(this.selectCaseIdList, 'On')
        this.selectCaseIdList = []
      }
    },
    afterShowPreview() {
      this.previewFlowId = this.tempFlowId
    },
    async onOffStatus(caseSetId, nowStatus) {
      let changeStatus = ''
      switch (nowStatus) {
        case 'Accept':
          // case 'Stop_Accept':
          changeStatus = 'On'
          break
        case 'On':
          changeStatus = 'Off'
          break
        case 'Off':
          changeStatus = 'On'
          break
      }
      if (changeStatus) {
        await this.updateStatus([caseSetId], changeStatus)
      }
    },
    async startStopAcceptingStatus(caseSetId, nowStatus) {
      let changeStatus = ''
      switch (nowStatus) {
        case 'Accept':
          changeStatus = 'Off'
          break
        // case 'Stop_Accept':
        case 'On':
        case 'Off':
          changeStatus = 'Accept'
          break
      }
      if (changeStatus) {
        await this.updateStatus([caseSetId], changeStatus)
      }
    },
    async updateStatus(caseSetIdArray, changeStatus) {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.cassManagementStatusUpdate({
          caseSetIdList: caseSetIdArray,
          status: changeStatus
        })
        await this.queryCase()
      })
    },
    addCase() {
      this.$router.push(`/caseManagement/caseAdd/`)
    },
    mappingStatusWord(val) {
      switch (val) {
        case 'Accept':
          return '受理中'
          break
        case 'On':
          return '已上架'
          break
        case 'Off':
          return '未上架'
          break
        default:
          return ''
          break
      }
    },
    /** 上下架按鈕顯示狀態判斷 */
    onOffStatusWord(val) {
      switch (val) {
        case 'Accept':
          // case 'Stop_Accept':
          return '下架'
          break
        case 'On':
          return '下架'
          break
        case 'Off':
          return '上架'
          break
        default:
          return ''
          break
      }
    },
    onOffStatusIcon(val) {
      switch (val) {
        case 'Accept':
          // case 'Stop_Accept':
          return 'fa fsm-icon-arrow-down'
          break
        case 'On':
          return 'fa fsm-icon-arrow-down'
          break
        case 'Off':
          return 'fa fsm-icon-arrow-top'
          break
        default:
          return ''
          break
      }
    },
    startStopAccepting(val) {
      switch (val) {
        case 'Accept':
          return '停止受理'
          break
        // case 'Stop_Accept':
        case 'On':
        case 'Off':
          return '立即受理'
          break
        default:
          return ''
          break
      }
    },
    //顯示流程圖
    async showFlow(caseType) {
      this.tempFlowId = caseType
      this.$refs.previewFlowModal.show()
    },
    closeBeforePreviewModal() {
      this.tempFlowId = ''
      this.previewFlowId = ''
      this.$refs.previewFlow.clear()
    },
    //編輯推播訊息
    editMsg(caseSetId) {
      this.$refs.pushMsg.caseSetId = caseSetId
      this.$refs.pushMsgModal.show()
      this.$refs.pushMsg.getHomeData()
    },
    closeEditMsg() {
      this.$refs.pushMsgModal.hide()
    }
  }
}
</script>
