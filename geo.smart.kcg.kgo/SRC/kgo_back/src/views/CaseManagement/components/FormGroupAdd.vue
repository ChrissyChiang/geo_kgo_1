<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                樣板名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="formId"
                  :options="formGroupOptions"
                  search
                  required
                  :select="formId"
                  :label="'name'"
                  :track-by="'seq'"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="true"
                  :show-pagination="false"
                ></app-table>
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
                    data-dismiss="modal"
                    @click="cancel"
                  >
                    <i class="fa" aria-hidden="true"></i>
                    取消
                  </button>
                  <button
                    type="button"
                    class="btn btn-fsm"
                    data-dismiss="modal"
                    @click="addFormGroup"
                  >
                    <i class="fa fa-save" aria-hidden="true"></i>
                    確定
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
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
      formId: '',
      formGroupOptions: [],
      tableData: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '欄位名稱',
          dataIndex: 'columnName',
          tdClass: 'text-center'
        },
        {
          title: '欄位型態',
          dataIndex: 'columnTypeName',
          tdClass: 'text-center'
        },
        {
          title: '欄位別名',
          dataIndex: 'columnId',
          tdClass: 'text-center'
        }
      ]
    }
  },
  watch: {
    formId() {
      this.addGroup()
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
        this.formGroupOptions = res.data.data.templateQueryActionDataGrids
      })
    },
    async addGroup() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getTemplateManagementDetail({
          seq: this.formId
        })
        this.tableData = res.data.data.grid
        if (this.tableData !== undefined) {
          this.tableData = this.tableData.sort(function(a, b) {
            return a.orderNum > b.orderNum ? 1 : -1
          })
        }
      })
    },
    cancel() {
      this.$emit('close-modal')
    },
    addFormGroup() {
      let allColumnIds = []
      let temIndex = 0
      this.tableData.forEach(item => {
        allColumnIds.push(item.columnId)
      })
      this.$emit('add-form', this.formId, allColumnIds)
    },
    clear() {
      this.formId = ''
      this.tableData = []
    }
  }
}
</script>
