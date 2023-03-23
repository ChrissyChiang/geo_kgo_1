<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row text-right">
            <div class="col-xs-6 form-group col-md-12">
              <button
                type="submit"
                class="btn btn-fsm"
                @click="goAdd('null', 'null', 'A', '')"
              >
                <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                新增表單
              </button>
            </div>
          </div>
          <app-table
            :columns="tableColumns"
            :data="tableData"
            :show-pagination="false"
          >
            <template v-slot:operating="{ data: { seq, name, isDefault } }">
              <button
                v-if="isDefault != '1'"
                type="button"
                class="btn-line btn-danger"
                @click="deleteForm(seq)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                <a>刪除</a>
              </button>
              <button
                type="button"
                class="btn-line btn-warning"
                @click="goAdd(seq, name, 'E', isDefault)"
              >
                <i class="fa fa-cog" aria-hidden="true"></i>
                <a>編輯</a>
              </button>
              <button
                type="button"
                class="btn-line btn-warning"
                @click="goAdd(seq, name, 'A', '')"
              >
                <i class="fa" aria-hidden="true"></i>
                <a>複製新增</a>
              </button>
            </template>
          </app-table>
        </div>
      </div>
    </div>
    <modal-confirm
      ref="confirmDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除此表單？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delForm"
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
      delFormSeq: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '表單名稱',
          dataIndex: 'name',
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
  mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getTemplateManagementQuery({
          name: ''
        })
        this.tableData = res.data.data.templateQueryActionDataGrids
      })
    },
    deleteForm(seq) {
      this.$refs.confirmDelete.show()
      this.delFormSeq = seq
    },
    async delForm() {
      let rtnCode = ''
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getTemplateManagementDelete({
          seq: this.delFormSeq
        })
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('表單刪除成功！')
      }
      this.delFormSeq = ''
      this.$refs.confirmDelete.hide()
      this.getHomeData()
    },
    goAdd(seq, name, type, isDefault) {
      if (isDefault == '1') {
        this.$router.push({
          name: 'addTemplate',
          params: {
            id: seq,
            name: name,
            type: 'R'
          }
        })
      } else {
        this.$router.push({
          name: 'addTemplate',
          params: {
            id: seq,
            name: name,
            type: type
          }
        })
      }
    }
  }
}
</script>
