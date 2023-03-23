<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div v-if="false" class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                流程名稱
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="flowName"
                  :placeholder="'請輸入流程名稱'"
                  :input-length="'50'"
                ></base-input>
              </div>
            </div>
          </div>
          <div class="row">
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="right">
                  <td>
                    <button
                      v-if="false"
                      type="button"
                      class="btn btn-fsm"
                      @click="search"
                    >
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="edit(null, false, isAdmin ? true : false)"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      新增流程
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="false"
                  :show-pagination="true"
                >
                  <template v-slot:flowName="{ data: { flowName, isDefault } }">
                    {{ flowName }}{{ isDefault ? '(預設)' : '' }}
                  </template>
                  <template v-slot:operating="{ data: { flowId, isDefault } }">
                    <button
                      v-if="!isDefault || isAdmin"
                      type="button"
                      class="btn-line btn-warning"
                      @click="edit(flowId, false, isDefault)"
                    >
                      <i class="fa fa-cog" aria-hidden="true"></i>
                      編輯
                    </button>
                    <button
                      v-if="!isAdmin"
                      type="button"
                      class="btn-line btn-warning"
                      @click="edit(flowId, true, isDefault)"
                    >
                      <i class="fa fa-paste" aria-hidden="true"></i>
                      複製
                    </button>
                    <button
                      v-if="(!isDefault && !isAdmin) || isAdmin"
                      type="button"
                      class="btn-line btn-danger"
                      @click="showConfirmDeleteFlow(flowId)"
                    >
                      <i class="fa fa-trash-o" aria-hidden="true"></i>
                      刪除
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <modal-confirm
      ref="confirmDeleteFlow"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delFlow"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  components: {},
  mixins: [page],
  data() {
    return {
      tableData: [],
      flowName: '',
      delFlowId: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '項目',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
        {
          title: '流程名稱',
          slot: 'flowName',
          width: '25%',
          tdClass: 'text-left'
        },
        {
          title: '流程說明',
          dataIndex: 'flowDesc',
          tdClass: 'text-left'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          width: '25%',
          slot: 'operating'
        }
      ]
    }
  },
  mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getActFlowHomeData()
        const data = res.data.data
        this.tableData = data.grid || []
      })
    },
    edit(flowId, isCopy, isDefault) {
      this.$router.push({
        name: 'actFlowManagerEdit',
        params: {
          flowId: flowId,
          isCopy: isCopy,
          isDefault: isDefault
        }
      })
    },
    showConfirmDeleteFlow(flowId) {
      this.delFlowId = flowId
      this.$refs.confirmDeleteFlow.show()
    },
    async delFlow() {
      let success = false
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteFlowActFlow({
          flowId: this.delFlowId
        })
        success = this.checkResSuccess(res, false)
      })
      this.$refs.confirmDeleteFlow.hide()
      if (success) {
        this.toastSuccess('刪除成功')
        await this.getHomeData()
      }
    },
    async search() {}
  }
}
</script>
