<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                受理機關
              </label>
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ organName }}</label>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6 text-right">
              <button type="button" class="btn btn-fsm" @click="addMessage">
                <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                新增訊息
              </button>
            </div>
          </div>
          <app-table
            :columns="tableColumns"
            :data="tableData"
            :draggable="false"
            :show-pagination="false"
          >
            <template v-slot:operating="{ data: { status, isDefault } }">
              <button
                type="button"
                class="btn-line btn-warning"
                @click="goEdit(status)"
              >
                <i class="fa fa-cog" aria-hidden="true"></i>
                <a>編輯</a>
              </button>
              <button
                v-if="isDefault == 'false'"
                type="button"
                class="btn-line btn-danger"
                @click="showDeleteConfirm(status)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
        </div>
      </div>
    </div>
    <modal-confirm
      ref="confirmMessageDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除此推播訊息？'"
      :modal-warn="'確認刪除，系統將刪除此推播訊息'"
      @confirm="deleteMessage"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      tableData: [],
      temTableData: [],
      organName: '',
      userId: '',
      organId: '',
      delSeq: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '案件狀態',
          dataIndex: 'caseStatusName',
          tdClass: 'text-center'
        },
        {
          title: '維護',
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
        const res = await this.$api.management.getPushMsgManagementHomeData()
        const resO = await this.$api.share.getOrganSelectHome({
          organId: this.userInfo.organId
        })
        this.organName = resO.data.data.grid[0].organName
        this.userId = res.data.data.userId
        this.organId = res.data.data.organId
        this.temTableData = res.data.data.pushMsgManagementHomeDataGridList
      })
      this.tableData = [
        {
          caseStatusName: '民眾入案通知',
          status: 'W',
          isDefault: true
        },
        {
          caseStatusName: '承辦簽收通知',
          status: 'P',
          isDefault: true
        },
        {
          caseStatusName: '民眾補正通知',
          status: 'C',
          isDefault: true
        },
        {
          caseStatusName: '結案通過通知',
          status: 'F3',
          isDefault: true
        },
        {
          caseStatusName: '結案不通過通知',
          status: 'J3',
          isDefault: true
        },
        {
          caseStatusName: '補正完成通知',
          status: 'B',
          isDefault: true
        }
      ]
      let temList = []

      this.temTableData.forEach(item => {
        if (
          item.status != 'W' &&
          item.status != 'P' &&
          item.status != 'C' &&
          item.status != 'F3' &&
          item.status != 'J3' &&
          item.status != 'B'
        ) {
          temList.push(item)
        }
      })
      if (temList.length > 0) {
        temList.forEach(item => {
          this.tableData.push(item)
        })
      }
    },
    goEdit(caseType) {
      this.$router.push({
        name: 'editMessage',
        params: {
          caseType: caseType,
          userId: this.userId,
          organId: this.organId
        }
      })
    },
    addMessage() {
      this.$router.push({
        name: 'editMessage',
        params: {
          caseType: 'null',
          userId: this.userId,
          organId: this.organId
        }
      })
    },
    showDeleteConfirm(caseTypeId) {
      this.delSeq = caseTypeId
      this.$refs.confirmMessageDelete.show()
    },
    async deleteMessage() {
      let rtnCode = ''
      let request = { organId: this.organId, status: this.delSeq }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deletePushMsgManagement(request)
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('刪除推播訊息成功！')
      }
      this.$refs.confirmMessageDelete.hide()
      this.getHomeData()
    }
  }
}
</script>
