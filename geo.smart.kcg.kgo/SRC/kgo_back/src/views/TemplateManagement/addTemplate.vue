<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-8">
              <label for="input_event" class="col-sm-2 control-label">
                表單名稱
              </label>
              <div class="col-sm-8">
                <validation-observer ref="observer">
                  <validate-container :rules="'required'">
                    <base-input
                      v-model="formName"
                      :disabled="type == 'R'"
                      :placeholder="'請輸入表單名稱'"
                      input-length="20"
                    ></base-input>
                  </validate-container>
                </validation-observer>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-4 text-right">
              <button
                v-if="type != 'R'"
                type="button"
                class="btn btn-warning"
                @click="addColumn"
              >
                新增欄位
              </button>
            </div>
          </div>
          <app-table
            :columns="tableColumns"
            :draggable="true"
            :data="tableData"
            :show-pagination="false"
          >
            <template v-slot:operating="{ data: { columnId } }">
              <button
                v-if="
                  columnId != 'Email' &&
                    columnId != 'CellPhone' &&
                    columnId != 'Name' &&
                    columnId != 'ID'
                "
                type="button"
                class="btn-line btn-danger"
                @click="deleteForm(columnId)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                <a>刪除</a>
              </button>
              <button
                type="button"
                class="btn-line btn-warning"
                @click="EditForm(columnId)"
              >
                <i class="fa fa-cog" aria-hidden="true"></i>
                <a>編輯</a>
              </button>
            </template>
          </app-table>
          <div v-if="showError" class="text-wrapper">
            <span class="error_label w-100">請至少新增一筆欄位</span>
          </div>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button type="submit" class="btn btn-fsm" @click="goBack">
                    <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                    返回
                  </button>
                  <button type="submit" class="btn btn-fsm" @click="saveForm">
                    <i class="fa fa-save" aria-hidden="true"></i>
                    儲存
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <app-modal
      ref="formFieldEditModal"
      :is-scroll="true"
      :modal-title="'欄位設定'"
      @before-hide="closeFormFieldEditModal"
    >
      <form-field-edit
        ref="formFieldEdit"
        @open-subField="openSubField"
        @save-field="saveformField"
      ></form-field-edit>
      <template v-slot:button>
        <button data-dismiss="modal" type="button" class="btn btn-fsm">
          <i aria-hidden="true" class="fa"></i>
          取消
        </button>
        <button
          type="button"
          class="btn btn btn-primary"
          @click="triggerSaveFormField"
        >
          <i aria-hidden="true" class="fa fa-save"></i>
          儲存
        </button>
      </template>
    </app-modal>
    <app-modal
      ref="SubFieldEditModal"
      :modal-title="'子欄位維護'"
      :modal-class="'modal-dialog modal-xl'"
      @before-hide="closeSubFieldEditModal"
    >
      <sub-field-edit
        ref="subFieldEdit"
        @save-subfield="saveSubfield"
      ></sub-field-edit>
    </app-modal>
    <modal-confirm
      ref="confirmDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除此欄位？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delColumn"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
import FormFieldEdit from './components/addColumn'
import SubFieldEdit from './components/SubFieldEdit'
export default {
  components: {
    FormFieldEdit,
    SubFieldEdit
  },
  mixins: [page],
  data() {
    return {
      tableData: [],
      seq: '',
      formName: '',
      type: '',
      showError: false,
      delColumnSeq: ''
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
          title: '欄位長度',
          dataIndex: 'length',
          tdClass: 'text-center'
        },
        {
          title: '欄位別名',
          dataIndex: 'columnId',
          tdClass: 'text-center'
        },
        {
          title: '必填',
          dataIndex: 'isMustKeyStr',
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
  watch: {
    tableData() {
      if (this.tableData.length <= 0) {
        this.showError = true
      } else {
        this.showError = false
      }
    }
  },
  mounted() {
    this.seq = this.$route.params.id == 'null' ? '' : this.$route.params.id
    this.formName =
      this.$route.params.name == 'null' ? '' : this.$route.params.name
    this.type = this.$route.params.type
    if (this.seq != '' && this.formName != '') {
      this.getHomeData()
    } else {
      this.getInsertData()
    }
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getTemplateManagementDetail({
          seq: this.seq
        })
        this.tableData = res.data.data.grid
        this.tableData = this.tableData.sort(function(a, b) {
          return a.orderNum > b.orderNum ? 1 : -1
        })
      })
    },
    getInsertData() {
      this.tableData = [
        {
          columnId: 'Email',
          columnName: 'Email',
          columnType: 'TextEmail',
          columnTypeName: '單行文字',
          columnValue: '',
          isMustKey: '1',
          isMustKeyStr: '是',
          length: 50,
          memo: '',
          myDataCheckType: '',
          myDataCheckValue: '',
          myDataColumn: '',
          myDataId: '',
          orderNum: 1,
          seq: ''
        },
        {
          columnId: 'CellPhone',
          columnName: '手機',
          columnType: 'TextPhone',
          columnTypeName: '單行文字',
          columnValue: '',
          isMustKey: '1',
          isMustKeyStr: '是',
          length: 50,
          memo: '',
          myDataCheckType: '',
          myDataCheckValue: '',
          myDataColumn: '',
          myDataId: '',
          orderNum: 2,
          seq: ''
        },
        {
          columnId: 'Name',
          columnName: '姓名',
          columnType: 'TextBox',
          columnTypeName: '單行文字',
          columnValue: '',
          isMustKey: '1',
          isMustKeyStr: '是',
          length: 50,
          memo: '',
          myDataCheckType: '',
          myDataCheckValue: '',
          myDataColumn: '',
          myDataId: '',
          orderNum: 3,
          seq: ''
        },
        {
          columnId: 'ID',
          columnName: '身分證',
          columnType: 'TextId',
          columnTypeName: '身分證',
          columnValue: '',
          isMustKey: '1',
          isMustKeyStr: '是',
          length: 0,
          memo: '',
          myDataCheckType: '',
          myDataCheckValue: '',
          myDataColumn: '',
          myDataId: '',
          orderNum: 4,
          seq: ''
        }
      ]
    },
    openMyDataEdit() {
      this.$refs.myDataEditModal.show()
    },
    saveSubfield(data) {
      this.$refs.formFieldEdit.saveSubfield(data)
      this.$refs.SubFieldEditModal.hide()
    },
    closeFormFieldEditModal() {
      this.$refs.formFieldEdit.clear()
    },
    openSubField(data) {
      this.$refs.subFieldEdit.initData(data)
      this.$refs.SubFieldEditModal.show()
    },
    closeSubFieldEditModal() {
      this.$refs.subFieldEdit.clear()
    },
    triggerSaveFormField() {
      this.$refs.formFieldEdit.saveField()
    },
    saveformField(data) {
      if (data.editGroupSeq != null) {
        const findGroup = this.tableData.find(
          x => x.groupSeq == data.editGroupSeq
        )
        if (data.editIndex != null) {
          this.tableData[data.editIndex].seq = data.seq || ''
          this.tableData[data.editIndex].columnId = data.columnId
          this.tableData[data.editIndex].columnName = data.columnName
          this.tableData[data.editIndex].columnType = data.columnType
          this.tableData[data.editIndex].columnValue = data.columnValue || ''
          this.tableData[data.editIndex].columnTypeName = data.columnTypeName
          this.tableData[data.editIndex].length = data.length || 0
          this.tableData[data.editIndex].isMustKey = data.isMustKey
          this.tableData[data.editIndex].isMustKeyStr =
            data.isMustKey == '0' ? '否' : '是'
          this.tableData[data.editIndex].memo = data.memo || ''
          this.tableData[data.editIndex].myDataId = data.myDataId || ''
          this.tableData[data.editIndex].myDataColumn = data.myDataColumn || ''
          this.tableData[data.editIndex].myDataCheckType =
            data.myDataCheckType || ''
          this.tableData[data.editIndex].myDataCheckValue =
            data.myDataCheckValue || ''
          this.tableData[data.editIndex].complex = data.complex
          this.tableData[data.editIndex].fileType = data.fileType || ''
        } else {
          this.tableData.push({
            seq: '',
            columnId: data.columnId,
            columnName: data.columnName,
            columnType: data.columnType,
            columnTypeName: data.columnTypeName,
            columnValue: data.columnValue || '',
            length: data.length || 0,
            isMustKey: data.isMustKey,
            isMustKeyStr: data.isMustKey == '0' ? '否' : '是',
            memo: data.memo || '',
            myDataId: data.myDataId || '',
            myDataColumn: data.myDataColumn || '',
            myDataCheckType: data.myDataCheckType || '',
            myDataCheckValue: data.myDataCheckValue || '',
            orderNum: this.tableData.length + 1,
            complex: data.complex,
            fileType: data.fileType || ''
          })
        }
      }
      this.$refs.formFieldEditModal.hide()
    },
    addColumn() {
      let allColumnIds = []
      this.tableData.forEach(item => {
        allColumnIds.push(item.columnId)
      })
      this.$refs.formFieldEdit.initData(null, this.seq, null, allColumnIds)
      this.$refs.formFieldEditModal.show()
    },
    EditForm(columnId) {
      let data = this.tableData.find(item => item.columnId == columnId)
      let index = ''
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].columnId == columnId) {
          index = i
        }
      }
      let allColumnIds = []
      this.tableData.forEach(item => {
        if (item.columnId != columnId) {
          allColumnIds.push(item.columnId)
        }
      })
      this.$refs.formFieldEdit.initData(data, this.seq, index, allColumnIds)
      this.$refs.formFieldEditModal.show()
    },
    deleteForm(columnId) {
      this.delColumnSeq = columnId
      this.$refs.confirmDelete.show()
    },
    async delColumn() {
      let index = this.tableData.findIndex(
        item => item.columnId == this.delColumnSeq
      )
      if (index) {
        this.tableData.splice(index, 1)
        this.$refs.confirmDelete.hide()
      }
    },
    async saveForm() {
      if (
        !(await this.$refs.observer.validate()) ||
        this.tableData.length <= 0
      ) {
        if (this.tableData.length <= 0) {
          this.showError = true
        }
        return
      }
      this.resetFormSetColumnDataSort()
      let rtnCode = ''
      let request = {
        columnData: this.tableData,
        name: this.formName,
        seq: this.seq
      }
      await this.loadingContainer(async () => {
        if (this.type == 'E' || this.type == 'R') {
          const res = await this.$api.management.getTemplateManagementEdit(
            request
          )
          rtnCode = res.data.rtnCode
        } else {
          const res = await this.$api.management.getTemplateManagementAdd(
            request
          )
          rtnCode = res.data.rtnCode
        }
      })
      if (rtnCode == '0000' && (this.type == 'E' || this.type == 'R')) {
        this.notifySuccess('表單更新成功！')
      } else if (rtnCode == '0000' && this.type == 'A') {
        this.notifySuccess('表單新增成功！')
      }
      this.goBack()
    },
    resetFormSetColumnDataSort() {
      this.tableData.forEach((subItem, subIndex) => {
        subItem.orderNum = subIndex + 1
      })
    },
    closeMyDataEditModal() {
      this.$refs.myDataEdit.clear()
    },
    goBack() {
      this.$router.push(`/templateManagement`)
    }
  }
}
</script>
<style lang="scss" scoped>
.error_label {
  font-size: 14px;
  color: red;
  position: absolute;
  white-space: nowrap;
}
</style>
