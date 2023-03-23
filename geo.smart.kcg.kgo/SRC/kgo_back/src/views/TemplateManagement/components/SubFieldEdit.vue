<template>
  <div class="container-fluid">
    <div class="fancybox-layout-contents">
      <validation-observer ref="formSubFieldEditObserver">
        <div class="panel panel-fsm">
          <div class="panel-heading">子欄位屬性設定</div>
          <div class="panel-body"></div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  欄位型態
                </label>
                <div class="col-sm-8">
                  <!-- <span v-if="form.cColumnId != null">
                    {{ form.columnType }}
                  </span> -->
                  <base-select
                    v-model="form.columnType"
                    :options="columnTypeOptions"
                    required
                    :select="form.columnType"
                    @select-change="changeColumnType"
                  />
                </div>
              </div>
            </div>
            <div v-if="isShowValueField" class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  欄位值
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <input
                      v-model="form.columnValue"
                      class="form-control"
                      placeholder="請輸入Value"
                      type="text"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  <span class="must"></span>
                  後文字
                </label>
                <div class="col-sm-8">
                  <!-- <validate-container :rules="'required'"> -->
                  <input
                    v-model="form.bText"
                    class="form-control"
                    type="text"
                  />
                  <!-- </validate-container> -->
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  <span class="must"></span>
                  前文字
                </label>
                <div class="col-sm-8">
                  <input
                    v-model="form.fText"
                    class="form-control"
                    type="text"
                  />
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
                      :select="form.isMustKey"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  父驗證欄位
                </label>
                <div class="col-sm-8">
                  <base-select
                    v-model="form.pColumnId"
                    :options="pChcekOption"
                    :select="form.pColumnId"
                  />
                </div>
              </div>
            </div>
            <option-setting
              v-if="form.columnType == 'Drp'"
              ref="subOptionSetting"
            />
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
            <!-- radio才需要設定群組名稱 -->
            <div v-if="isShowGroupField" class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-4 control-label">
                  群組名稱
                </label>
                <div class="col-sm-8">
                  <input
                    v-model="form.vGroup"
                    class="form-control"
                    type="text"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </validation-observer>
      <div class="fsm-form">
        <table class="table table-unstyled ">
          <tbody>
            <tr colspan="4" align="center">
              <td>
                <button type="button" class="btn btn-fsm" data-dismiss="modal">
                  <i class="fa " aria-hidden="true"></i>
                  取消
                </button>
                <button type="button" class="btn btn-fsm" @click="saveField">
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
</template>
<script>
import { page } from '@/mixins'
import draggable from 'vuedraggable'
import OptionSetting from './OptionSetting'
export default {
  name: 'SubFieldEdit',
  components: {
    draggable,
    OptionSetting
  },
  mixins: [page],
  data() {
    return {
      /** 案件代碼 */
      caseSetId: this.$route.params.id || null,
      /** 所有子欄位項目集合 */
      allSubFields: [],
      /** 父驗證選項(抓SRadio＆checkbox) */
      pChcekOption: [],
      tempColumnTypeOptions: [],
      /** 欄位類型 */
      columnTypeOptions: [],
      /** 必填選項 */
      isMustKeyOptions: [],
      /** 編輯的Row */
      editRowIndex: null,
      /** 編輯的Index */
      editSubIndex: null,
      form: {
        seq: '',
        /** 子欄位Id  */
        cColumnId: null,
        /** 欄位類型 */
        columnType: '',
        /** 欄位值 */
        columnValue: '',
        /** 父驗證CColumnId */
        pColumnId: '',
        // 前文字
        fText: '',
        // 後文字
        bText: '',
        length: 0,
        // 群組名稱
        vGroup: '',
        // 是否必填
        isMustKey: ''
      }
    }
  },
  computed: {
    isShowLengthField() {
      const needLength = ['TextEmail', 'Address', 'TextBox', 'TextArea', 'Num']
      return needLength.includes(this.form.columnType)
    },
    isShowValueField() {
      const needLength = ['SRadio', 'SCheckbox']
      return needLength.includes(this.form.columnType)
    },
    isShowGroupField() {
      const needLength = ['SRadio', 'SCheckbox']
      return needLength.includes(this.form.columnType)
    }
  },
  watch: {},
  async mounted() {},
  methods: {
    /** 變動類型，清空值 */
    changeColumnType() {
      //   this.form.columnType = ''
      //   if (this.$refs.subOptionSetting) {
      //     this.$refs.subOptionSetting.clear()
      //   }
      // console.log('change');
    },
    async getHomeData() {
      const res = await this.$api.management.getInternetApplyApplyFormSetColumnHome(
        {
          caseSetId: ''
        }
      )
      const data = res.data.data
      /** 移除M＆Radio&checkbox欄位型態 */
      this.columnTypeOptions = data.columnTypeComboBox
        ? data.columnTypeComboBox.options
        : []
      this.tempColumnTypeOptions = [...this.columnTypeOptions]
      this.isMustKeyOptions = data.isMustKeyComboBox
        ? data.isMustKeyComboBox.options
        : []
    },
    async initData(data) {
      await this.loadingContainer(async () => {
        await this.getHomeData()
        this.filterColumnType()
        if (data != null) {
          this.form.cColumnId = data.cColumnId
          this.form.seq = data.seq || ''
          this.form.columnType = data.columnType
          this.form.columnValue = data.columnValue
          if (this.form.columnType == 'Drp') {
            this.$nextTick(() => {
              this.$refs.subOptionSetting.clear()
              this.$refs.subOptionSetting.initData(this.form.columnValue)
            })
          }
          this.form.pColumnId = data.pColumnId
          this.form.fText = data.fText
          this.form.bText = data.bText
          this.form.length = data.length
          // 群組名稱
          this.form.vGroup = data.vGroup || ''
          // 是否必填
          this.form.isMustKey = data.isMustKey
        }
        this.allSubFields = [...data.allSubField]
        this.setPChcekOption()

        this.editRowIndex = data.rowIndex
        this.editSubIndex = data.subIndex
      })
    },
    /** 取得父驗證欄位 */
    setPChcekOption() {
      /** 取得radio&Checkbox，並且不包含自己 */
      const checkField = ['SRadio', 'SCheckbox']
      const pField = this.allSubFields
        .filter(
          x =>
            checkField.includes(x.columnType) &&
            x.cColumnId != this.form.cColumnId
        )
        .map(item => ({
          label: `(${this.findTypeName(item.columnType)})${item.bText}`,
          value: item.cColumnId
        }))

      this.pChcekOption = [...pField]
    },
    async saveField() {
      if (!(await this.$refs.formSubFieldEditObserver.validate())) {
        return
      }
      /** 清除欄位值判斷 */
      if (this.form.columnType == 'Drp') {
        this.form.columnValue = this.$refs.subOptionSetting.getParseOptionToValue()
      } else {
        if (!this.isShowValueField) {
          this.form.columnValue = ''
        }
      }
      /** 清除群組名稱判斷 */
      if (!this.isShowGroupField) {
        this.form.vGroup = ''
      }

      /** 清除欄位長度值判斷 */
      if (!this.isShowLengthField) {
        this.form.length = 0
      }
      this.$emit('save-subfield', {
        ...this.form,
        editRowIndex: this.editRowIndex,
        editSubIndex: this.editSubIndex
      })
    },
    findTypeName(columnType) {
      const findColumnType = this.tempColumnTypeOptions.find(
        x => x.value == columnType
      )
      return findColumnType != null ? findColumnType.label : ''
    },
    /** 篩選目前的ColumnType */
    filterColumnType() {
      /** 濾掉[複合欄位][勾選框][單選] */
      const filterType = ['M', 'Checkbox', 'Radio', 'Fil', 'Pic']
      this.columnTypeOptions = this.columnTypeOptions.filter(
        x => !filterType.includes(x.value)
      )
    },
    clear() {
      this.form.cColumnId = null
      this.form.seq = ''
      this.form.columnType = ''
      this.form.columnValue = ''
      this.form.pColumnId = ''
      this.form.fText = ''
      this.form.bText = ''
      this.form.length = 0
      this.form.vGroup = ''
      this.form.isMustKey = ''
      this.$refs.formSubFieldEditObserver.reset()
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
</style>
