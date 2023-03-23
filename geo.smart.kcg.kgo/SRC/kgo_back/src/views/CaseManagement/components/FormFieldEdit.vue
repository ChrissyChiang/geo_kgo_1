<template>
  <div class="container-fluid">
    <div class="fancybox-layout-contents">
      <validation-observer ref="formFieldEditObserver">
        <div class="panel panel-fsm">
          <div class="panel-heading">欄位屬性設定</div>
          <div class="panel-body"></div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  <span class="must">*</span>
                  欄位名稱
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <input
                      v-model="form.columnName"
                      class="form-control"
                      type="text"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  <span class="must">*</span>
                  欄位別名
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <input
                      v-model="form.columnId"
                      class="form-control"
                      type="text"
                      :disabled="isDisabled"
                    />
                  </validate-container>
                  <div v-if="isColumnIdsRepeat" class="text-wrapper">
                    <span class="error_label w-100">此欄位別名已存在！</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  欄位型態
                </label>
                <div class="col-sm-4">
                  <validate-container :rules="'required'">
                    <base-select
                      v-model="form.columnType"
                      :options="columnTypeOptions"
                      required
                      :select="form.columnType"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div v-if="isShowIntgrApiId" class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  城市資料平台Api
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <input
                      v-model="form.columnValue"
                      class="form-control"
                      type="text"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <option-setting v-if="isShowDrp" ref="optionSetting" />

            <div v-if="isShowLengthField" class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  欄位長度
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <input
                      v-model="form.length"
                      class="form-control"
                      type="number"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div v-if="isShowFil" class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  附件類型
                </label>
                <div class="col-sm-7">
                  <validate-container :rules="'required'">
                    <base-check-list
                      v-model="extSelectList"
                      :options="extOptions"
                      :selected-list="extSelectList"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  是否必填
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <base-select
                      v-model="form.isMustKey"
                      :options="isMustKeyOptions"
                      required
                      :select="
                        form.columnId == 'Email' ||
                        form.columnId == 'CellPhone' ||
                        form.columnId == 'Name' ||
                        form.columnId == 'ID'
                          ? '1'
                          : form.isMustKey
                      "
                      :disabled="isDisabled"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  備註說明
                </label>
                <div class="col-sm-8">
                  <input v-model="form.memo" class="form-control" type="text" />
                </div>
              </div>
            </div>
          </div>
          <div v-if="form.columnType == 'M'">
            <div class="add-row-button">
              <button
                v-if="form.complex.length > 0"
                type="button"
                class="btn btn-warning text-right"
                @click="editSubField(form.complex.length - 1, -1)"
              >
                <i class="fa fa-external-link" aria-hidden="true"></i>
                新增子欄位
              </button>
              <button
                type="button"
                class="btn btn-warning text-right"
                @click="addComplexRow"
              >
                <i class="fa fa-external-link" aria-hidden="true"></i>
                新增複合列
              </button>
            </div>
            <div v-for="(fieldList, rowIndex) in form.complex" :key="rowIndex">
              <i
                style="margin-right: 3px; cursor: pointer; float: right"
                class="fa fa-trash-o"
                aria-hidden="true"
                @click="confirmSubRowDelete(rowIndex)"
              ></i>
              <draggable
                id="horizontal-list"
                v-model="form.complex[rowIndex]"
                class="menu-outer"
                tag="ul"
                group="people"
                v-bind="dragOptions"
                @start="isDragging = true"
                @end="isDragging = false"
              >
                <transition-group
                  type="transition"
                  name="flip-list"
                  class="fieldContent"
                >
                  <li
                    v-for="(subField, subIndex) in form.complex[rowIndex]"
                    :key="subIndex + rowIndex"
                    class="field"
                    style="cursor: move"
                  >
                    {{ subField.fText }}({{
                      findTypeName(subField.columnType)
                    }}){{ subField.bText }}
                    <i
                      style="margin-right: 3px; cursor: pointer"
                      class="fa fa-trash-o"
                      aria-hidden="true"
                      @click="delSubField(rowIndex, subIndex)"
                    ></i>
                    <i
                      style="cursor: pointer"
                      class="fa fa-edit"
                      aria-hidden="true"
                      @click="editSubField(rowIndex, subIndex)"
                    ></i>
                  </li>
                </transition-group>
              </draggable>
            </div>
          </div>
          <div v-if="isShowMyData">
            <div class="panel-heading">MyData設定</div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-4 control-label">
                    MyData資料集
                  </label>
                  <div class="col-sm-8">
                    <base-select
                      v-model="form.myDataId"
                      :options="myDataIdOptions"
                      :select="form.myDataId"
                    />
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-4 control-label">
                    MyData欄位別名
                  </label>
                  <div class="col-sm-8">
                    <base-select
                      v-model="form.myDataColumn"
                      :options="myDataColumnOptions"
                      :select="form.myDataColumn"
                    />
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-4 control-label">
                    MyData資料驗證設定
                  </label>
                  <div class="col-sm-4">
                    <base-select
                      v-model="form.myDataCheckType"
                      :options="myDataCheckTypeOptions"
                      :select="form.myDataCheckType"
                    />
                  </div>
                  <div class="col-sm-4">
                    <input
                      v-model="form.myDataType"
                      class="form-control"
                      type="text"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </validation-observer>
    </div>
    <modal-confirm
      ref="confirmSubRowDelete"
      :close-text="'取消'"
      :modal-content="'是否確定此複合列？'"
      :modal-warn="'確認刪除，系統將刪除，此複合列所有子組件'"
      @confirm="delSubRow"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
import draggable from 'vuedraggable'
import OptionSetting from './OptionSetting'
export default {
  name: 'FormFieldEdit',
  components: {
    draggable,
    OptionSetting
  },
  mixins: [page],
  data() {
    return {
      /** 要刪除的row index */
      delSubRowIndex: null,
      /** 案件代碼 */
      caseSetId: this.$route.params.id || '',

      /** 目前所有欄位Id集合 */
      columnIds: [],
      /** 欄位Id */
      isColumnIdsRepeat: false,

      tempColumnTypeOptions: [],
      /** 欄位型態下拉式選單 */
      columnTypeOptions: [],
      /** 是否必填下拉式選單 */
      isMustKeyOptions: [],
      /** MYDATA資料集下拉式選單 */
      myDataIdOptions: [],
      /** MyData整合方式下拉式選單 */
      myDataTypeOptions: [],
      /** MyData欄位別名下拉式選單 */
      myDataColumnOptions: [],
      /** MyData資料驗證設定下拉式選單 */
      myDataCheckTypeOptions: [],
      /** 目前編輯中群組編號 */
      editGroupSeq: null,
      extSelectList: [],
      extOptions: [],
      /** 編輯欄位的索引值 */
      editIndex: null,
      isFieldDisabled: false,
      isFieldColumnIdIsNameDisabled: false,
      form: {
        columnId: '',
        columnName: '',
        columnType: '',
        /** 欄位型態名稱 */
        columnTypeName: '',
        length: 0,
        isMustKey: '',
        /** 欄位設定值 */
        columnValue: '',
        /** MyData資料集ID */
        myDataId: '',
        myDataColumn: '',
        myDataCheckType: '',
        myDataCheckValue: '',
        /** 附件類型 */
        fileType: '',
        // myDataType: '',
        memo: '',
        /** 複合欄位 */
        complex: []
      }
    }
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: 'description',
        disabled: false,
        ghostClass: 'ghost'
      }
    },
    isShowMyData() {
      if (this.form.columnType == 'M') {
        return false
      } else {
        const showMyData = ['B1', 'B2', 'B3']
        return showMyData.includes(this.caseFlow)
      }
    },
    isShowLengthField() {
      const needShowLength = [
        'TextEmail',
        'Address',
        'TextBox',
        'TextArea',
        'Num',
        'OrgCheck',
        'AddressTextBox'
      ]
      return needShowLength.includes(this.form.columnType)
    },
    /** ID&Email&Name的columnId不能修改 */
    isDisabled() {
      const needShowDrp = ['ID', 'Email', 'Name', 'CellPhone']
      return needShowDrp.includes(this.form.columnId)
    },
    /** ID&Email的columnId不能修改 */
    isColumnIdIsNameDisabled() {
      const needShowDrp = ['ID', 'Email', 'CellPhone']
      return needShowDrp.includes(this.form.columnId)
    },
    isShowDrp() {
      const needShowDrp = ['Drp', 'Radio', 'Checkbox']
      return needShowDrp.includes(this.form.columnType)
    },
    isShowFil() {
      const needShowFil = ['Fil']
      return needShowFil.includes(this.form.columnType)
    },
    /** 是否顯示整合串接(身分證)輸入值欄位 */
    isShowIntgrApiId() {
      const needShowIntgrApiId = ['IntgrApiId']
      return needShowIntgrApiId.includes(this.form.columnType)
    }
  },
  watch: {
    /** columnId變動，檢查有沒有重覆 */
    'form.columnId': {
      handler() {
        if (this.form.columnId) {
          this.form.columnId = this.form.columnId.replace(/ /g, '')
        }
        const findColumnIds = this.columnIds.filter(
          x => x == this.form.columnId
        )
        this.isColumnIdsRepeat = findColumnIds.length > 0 ? true : false
      }
    },
    'form.myDataId': {
      async handler() {
        await this.getMyDataColumn()
      }
    }
  },
  async mounted() {},
  methods: {
    delSubField(rowIndex, subIndex) {
      this.checkDelSubFieldSubParent(rowIndex, subIndex)
      this.form.complex[rowIndex].splice(subIndex, 1)
    },
    /** subIndex為-1，則為新增 */
    editSubField(rowIndex, subIndex) {
      let editData = null
      if (subIndex >= 0) {
        editData = this.form.complex[rowIndex][subIndex]
      }

      /** 二維陣列打平 */
      const allFields = this.form.complex.reduce((prev, curr) => {
        return prev.concat(curr)
      })
      this.$emit('open-subField', {
        ...editData,
        allSubField: allFields,
        rowIndex: rowIndex,
        subIndex: subIndex
      })
    },
    async getMyDataColumn() {
      if (this.form.myDataId) {
        const res = await this.$api.management.getInternetApplyFormSetMyDataColumnCombobox(
          {
            myDataId: this.form.myDataId
          }
        )
        const data = res.data.data
        this.myDataColumnOptions = data.myDataColumnComboBox
          ? data.myDataColumnComboBox.options
          : []
      } else {
        this.myDataColumnOptions = []
      }
    },
    confirmSubRowDelete(rowIndex) {
      this.delSubRowIndex = rowIndex
      if (this.form.complex[this.delSubRowIndex].length > 0) {
        this.$refs.confirmSubRowDelete.show()
      } else {
        this.form.complex.splice(this.delSubRowIndex, 1)
      }
    },
    delSubRow() {
      this.checkDelSubFieldSubParent(this.delSubRowIndex, -1)
      this.form.complex.splice(this.delSubRowIndex, 1)
      this.$refs.confirmSubRowDelete.hide()
    },
    /** 檢查這些要刪除的子欄位，有沒有已被其他子欄位被使用pColumnId，有的話就清除 */
    /** rowIndex >0 & subIndex若為-1則檢核刪除整列 */
    checkDelSubFieldSubParent(rowIndex, subIndex) {
      let findClearpColumnIds = []
      if (rowIndex >= 0 && subIndex >= 0) {
        const findSub = this.form.complex[rowIndex][subIndex]
        this.form.complex
          .filter(x => x.pColumnId == findSub.cColumnId)
          .forEach(item => {
            item.pColumnId = ''
          })
      }
      // 檢查刪除整個列
      if (rowIndex >= 0 && subIndex < 0) {
        const findcColumnId = this.form.complex[rowIndex].map(x => x.cColumnId)
        this.form.complex.forEach(itme => {
          itme.forEach(subItem => {
            if (findcColumnId.includes(subItem.pColumnId)) {
              subItem.pColumnId = ''
            }
          })
        })
      }
    },
    saveSubfield(data) {
      if (data.cColumnId == null) {
        this.form.complex[data.editRowIndex].push({
          cColumnId: this.$f.genUUID(),
          columnType: data.columnType,
          columnValue: data.columnValue || '',
          length: data.length || 0,
          isMustKey: data.isMustKey || '',
          pColumnId: data.pColumnId || '',
          fText: data.fText || '',
          bText: data.bText || '',
          vGroup: data.vGroup || '',
          orderNum: 1,
          row: data.editRowIndex
        })
      } else {
        const findData = this.form.complex[data.editRowIndex][data.editSubIndex]
        findData.columnType = data.columnType
        findData.columnValue = data.columnValue || ''
        findData.pColumnId = data.pColumnId || ''
        findData.fText = data.fText || ''
        findData.bText = data.bText || ''
        findData.length = data.length || 0
        findData.vGroup = data.vGroup || ''
        findData.isMustKey = data.isMustKey || ''
        findData.fileType = data.fileType || ''
      }
    },
    async initData(data, groupSeq, editIndex, columnIds, organName) {
      await this.loadingContainer(async () => {
        await this.getHomeData(organName)
        this.filterColumnType()
        this.editGroupSeq = groupSeq
        this.editIndex = editIndex
        this.columnIds = columnIds
        if (data != null) {
          this.form.columnId = data.columnId
          this.form.columnName = data.columnName
          this.form.columnType = data.columnType
          this.form.columnTypeName = data.columnTypeName
          this.form.length = data.length
          this.form.isMustKey = data.isMustKey
          this.form.columnValue = data.columnValue
          if (this.isShowDrp) {
            this.$nextTick(() => {
              this.$refs.optionSetting.clear()
              this.$refs.optionSetting.initData(this.form.columnValue)
            })
          }
          this.form.myDataId = data.myDataId
          this.form.myDataColumn = data.myDataColumn
          this.form.myDataCheckType = data.myDataCheckType
          this.form.myDataCheckValue = data.myDataCheckValue
          this.form.memo = data.memo
          this.form.complex = data.complex || []
          this.isFieldDisabled = this.isDisabled
          this.isFieldColumnIdIsNameDisabled = this.isColumnIdIsNameDisabled
          this.form.fileType = data.fileType || ''
          this.extSelectList = this.form.fileType.split(',')
        }
      })
    },
    async getHomeData(organName) {
      const res = await this.$api.management.getInternetApplyApplyFormSetColumnHome(
        {
          caseSetId: this.caseSetId
        }
      )
      const data = res.data.data
      this.columnTypeOptions = data.columnTypeComboBox
        ? data.columnTypeComboBox.options
        : []
      if (organName != '稅捐處' && this.columnTypeOptions.length > 0) {
        this.columnTypeOptions = this.columnTypeOptions.filter(
          item => item.value != 'AddressTextBox'
        )
      }
      this.tempColumnTypeOptions = [...this.columnTypeOptions]
      this.isMustKeyOptions = data.isMustKeyComboBox
        ? data.isMustKeyComboBox.options
        : []
      this.myDataIdOptions = data.myDataIdComboBox
        ? data.myDataIdComboBox.options
        : []

      this.myDataTypeOptions = data.myDataTypeComboBox
        ? data.myDataTypeComboBox.options
        : []
      this.myDataColumnOptions = data.myDataColumnComboBox
        ? data.myDataColumnComboBox.options
        : []
      this.myDataCheckTypeOptions = data.myDataCheckTypeComboBox
        ? data.myDataCheckTypeComboBox.options
        : []
      if (data.fileTypeComboBox) {
        this.extOptions = data.fileTypeComboBox
          ? data.fileTypeComboBox.options.map(x => ({
              label: x.label,
              value: x.value
            }))
          : []
      }
    },
    async saveField() {
      if (this.isColumnIdsRepeat) {
        return
      }
      if (!(await this.$refs.formFieldEditObserver.validate())) {
        return
      }
      if (this.isShowDrp) {
        if (!this.$refs.optionSetting.validateEmpty()) {
          return
        }
      }

      const findColumnType = this.columnTypeOptions.find(
        x => x.value == this.form.columnType
      )
      if (findColumnType != null) {
        this.form.columnTypeName = findColumnType.label
      }
      /** 欄位值清除邏輯 */
      if (this.isShowDrp) {
        this.form.columnValue = this.$refs.optionSetting.getParseOptionToValue()
      } else {
        if (this.form.columnType !== 'IntgrApiId') {
          this.form.columnValue = ''
        }
      }

      if (!this.isShowLengthField) {
        this.form.length = 0
      }

      /** 如果是複合型態，重新排序Complex＆清除My Data相關資料 */
      if (this.form.columnType == 'M') {
        this.resortComplexData()
        this.form.myDataId = ''
        this.form.myDataColumn = ''
        this.form.myDataCheckType = ''
        this.form.myDataCheckValue = ''
      } else {
        this.form.complex = []
      }
      if (this.form.columnType != 'Fil') {
        this.form.fileType = ''
      } else {
        this.form.fileType = this.extSelectList.join(',')
      }
      this.$emit('save-field', {
        ...this.form,
        editGroupSeq: this.editGroupSeq,
        editIndex: this.editIndex
      })
    },
    /** 重新排序 */
    resortComplexData() {
      this.form.complex.forEach((item, index) => {
        item.forEach((subItem, subIndex) => {
          subItem.row = index + 1
          subItem.orderNum = subIndex + 1
        })
      })
    },
    findTypeName(columnType) {
      const findColumnType = this.tempColumnTypeOptions.find(
        x => x.value == columnType
      )
      return findColumnType != null ? findColumnType.label : ''
    },
    async addComplexRow() {
      this.form.complex.push([])
      await this.delay(100)
      const scrollHTMLCollection = document.getElementsByClassName(
        'modal-scroll'
      )
      if (scrollHTMLCollection && scrollHTMLCollection.length > 0) {
        scrollHTMLCollection[0].scrollTop = scrollHTMLCollection[0].scrollHeight
      }
    },
    /** 篩選目前的ColumnType */
    filterColumnType() {
      /** 濾掉[複合欄位-勾選框][複合欄位-單選][文字][附件-照片] */
      const filterType = ['SCheckbox', 'SRadio', 'TextLabel', 'Pic']
      this.columnTypeOptions = this.columnTypeOptions.filter(
        x => !filterType.includes(x.value)
      )
    },
    clear() {
      this.editGroupSeq = null
      this.editIndex = null
      this.isColumnIdsRepeat = false
      this.extSelectList = []
      this.columnIds = []
      this.form.columnId = ''
      this.form.columnName = ''
      this.form.columnType = ''
      this.form.columnTypeName = ''
      this.form.columnValue = ''
      this.form.length = 0
      this.form.isMustKey = ''
      this.form.myDataId = ''
      this.form.myDataColumn = ''
      this.form.myDataCheckType = ''
      this.form.myDataCheckValue = ''
      this.form.myDataType = ''
      this.form.memo = ''
      this.form.complex = []
      this.isFieldDisabled = false
      this.isFieldColumnIdIsNameDisabled = false
      this.$refs.formFieldEditObserver.reset()
    }
  }
}
</script>
<style lang="scss" scoped>
.must {
  color: red;
}
.error_label {
  font-size: 14px;
  color: red;
  position: absolute;
  white-space: nowrap;
}
.menu-outer {
  height: 100%;
  background-color: rgb(205, 199, 199);
  // margin-bottom: 10px;
  min-height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.fieldContent {
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.field {
  border: 1px solid;
  padding: 5px;
  margin: 2px;
  //width: 100px;
  border-radius: 10px;
  color: black;
}

.flip-list-move {
  transition: transform 0.5s;
}
.no-move {
  transition: transform 0s;
}

ul#horizontal-list {
  min-width: 100%;
  list-style: none;
  margin: 5px;
  padding: 5px;
}
.add-row-button {
  display: flex;
  justify-content: flex-end;
  margin: 5px;
}
</style>
