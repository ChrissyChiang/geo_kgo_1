<template>
  <div>
    <!-- 如果是Drp下拉選項才出現 -->
    <div class="row">
      <div class="col-xs-12 form-group col-md-12">
        <label for="input_event" class="col-sm-4 control-label">
          選項設定
        </label>
        <div class="col-sm-2">
          選項文字：
          <input
            v-model="optionLabel"
            class="form-control"
            placeholder="輸入字"
            type="text"
          />
        </div>
        <div class="col-sm-2">
          選項值：
          <input
            v-model="optionValue"
            class="form-control"
            placeholder="輸入值"
            type="text"
          />
        </div>
        <div class="col-sm-2">
          <br />
          <button class="btn" @click="addOption">新增選項</button>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-12 form-group col-md-12">
        <label for="input_event" class="col-sm-4 control-label"></label>
        <div class="col-sm-8">
          <app-table
            :columns="optionTableColumns"
            :data="optionGrid"
            :draggable="true"
            :show-pagination="false"
            @drag-change="handleDragChangeOption"
          >
            <template v-slot:operating="{ index }">
              <button
                type="button"
                class="btn-line btn-danger"
                @click="deleteOption(index)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
          <div v-if="!isPass" class="text-wrapper">
            <span class="error_label w-100">請至少設定一組選項！</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'OptionSetting',
  mixins: [page],
  data() {
    return {
      optionLabel: '',
      optionValue: '',
      optionGrid: [],
      isPass: true
    }
  },
  computed: {
    optionTableColumns() {
      return [
        {
          title: '選項名稱',
          dataIndex: 'label',
          tdClass: 'text-center'
        },
        {
          title: '選項參數',
          dataIndex: 'value',
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
    validateEmpty() {
      this.isPass = this.optionGrid.length > 0
      return this.optionGrid.length > 0
    },
    initData(value) {
      this.parseValueToOption(value)
    },
    /** 解析Value-Text,Value-Text to Option */
    parseValueToOption(value) {
      const newOptions = []
      const splitList = value.split(',')
      splitList.forEach(item => {
        const subSplitList = item.split('-')
        if (subSplitList.length == 2) {
          this.optionGrid.push({
            label: subSplitList[1],
            value: subSplitList[0]
          })
        }
      })
    },
    /** 解析Option to Value-Text,Value-Text */
    getParseOptionToValue() {
      const tempArr = []
      this.optionGrid.forEach(item => {
        tempArr.push(`${item.value}-${item.label}`)
      })
      return tempArr.join(',')
    },
    addOption() {
      if (!this.optionLabel || !this.optionValue) {
        return
      }
      const findOptionVal = this.optionGrid.find(
        x => x.value == this.optionValue
      )
      if (findOptionVal) {
        this.notifyFail('已存在選項值！')
      } else {
        this.optionGrid.push({
          label: this.optionLabel,
          value: this.optionValue,
          order: this.optionGrid.length + 1
        })
        this.isPass = this.optionGrid.length > 0
        this.optionLabel = ''
        this.optionValue = ''
      }
    },
    deleteOption(index) {
      this.optionGrid.splice(index, 1)
      this.optionGrid.forEach((item, index) => {
        item.order = index + 1
      })
    },
    handleDragChangeOption({ data, moved }) {
      /** 移動項目的新 index */
      const newIndex = moved.newIndex
      // 排序全部重算
      data.forEach((item, index) => {
        item.order = index + 1
      })

      this.optionGrid = [...data]
    },
    clear() {
      this.optionLabel = ''
      this.optionValue = ''
      this.optionGrid = []
      this.isPass = true
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
