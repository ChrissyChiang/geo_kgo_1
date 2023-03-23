<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                群組名稱
              </label>
              <div class="col-sm-8">
                <input
                  v-model="addGroupName"
                  class="form-control"
                  type="text"
                />
                <button
                  type="button"
                  class="btn-line btn-warning"
                  @click="addGroup"
                >
                  <i class="fa fa-cog" aria-hidden="true"></i>
                  新增群組
                </button>
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
                  @drag-change="handleDragChange"
                >
                  <template v-slot:operating="{ index }">
                    <button
                      type="button"
                      class="btn-line btn-danger"
                      @click="delGroup(index)"
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
        <div class="fsm-form">
          <table class="table table-unstyled ">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button
                    type="button"
                    class="btn btn-fsm"
                    data-dismiss="modal"
                  >
                    <i class="fa " aria-hidden="true"></i>
                    取消
                  </button>
                  <button
                    type="button"
                    class="btn btn-fsm"
                    data-dismiss="modal"
                    @click="save"
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
      tableData: [],
      addGroupName: ''
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
          title: '群組名稱',
          dataIndex: 'groupName',
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
  methods: {
    handleDragChange({ data, moved }) {
      /** 移動項目的新 index */
      const newIndex = moved.newIndex
      // 排序全部重算
      data.forEach((item, index) => {
        item.orderNum = index + 1
      })

      this.tableData = [...data]
    },
    async initData(groupData) {
      this.tableData = [...groupData]
    },
    delGroup(index) {
      this.tableData.splice(index, 1)
      this.tableData.forEach((item, index) => {
        item.orderNum = index + 1
      })
    },
    addGroup() {
      if (!this.addGroupName) return
      // 找出小於0的暫時新增的群組長度
      const tempLength = this.tableData.filter(x => x.groupSeq < 0).length
      this.tableData.push({
        // 以負數遞增
        groupSeq: -tempLength + -1,
        orderNum: this.tableData.length + 1,
        groupName: this.addGroupName
      })
    },
    clear() {
      this.addGroupName = ''
      this.tableData = []
    },
    async save() {
      this.$emit('update-group', this.tableData)
    }
  }
}
</script>
