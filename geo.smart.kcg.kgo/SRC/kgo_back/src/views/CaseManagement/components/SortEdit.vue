<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="true"
                  :show-pagination="false"
                  @drag-change="handleDragChange"
                ></app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'SortEdit',
  mixins: [page],
  data() {
    return {
      tableData: [],
      organId: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '項次',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
        {
          title: '案件編號',
          dataIndex: 'caseSetId',
          tdClass: 'text-center'
        },
        {
          title: '案件名稱',
          dataIndex: 'caseSetName',
          tdClass: 'text-center'
        },
        {
          title: '機關分類',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '案件管理者',
          dataIndex: 'managerName',
          tdClass: 'text-center'
        }
      ]
    }
  },
  methods: {
    handleDragChange({ data, moved }) {
      /** 移動項目的新 index */
      const newIndex = moved.newIndex
      // 排序全部重算
      data.forEach((item, index) => {
        item.order = index + 1
      })

      this.tableData = [...data]
    },
    async initData(organId) {
      this.organId = organId
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getCassManagementOrderData({
          organId: this.organId
        })
        const data = res.data.data
        this.tableData = data.grid || []
      })
    },
    clear() {
      this.organId = ''
      this.tableData = []
    },
    async save() {
      const saveOrderList = this.tableData.map(item => ({
        caseSetId: item.caseSetId,
        order: item.order
      }))
      if (saveOrderList.length > 0) {
        await this.loadingContainer(async () => {
          const res = await this.$api.management.saveCassManagementOrder({
            grid: saveOrderList
          })
        })
      }
      this.$emit('update-sort')
    }
  }
}
</script>
