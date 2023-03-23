<template>
  <div>
    <!--<label v-if="showTitle" class="label">{{ titleText }}</label>-->
    <div>
      <select
        ref="selectbase"
        v-model="value"
        :disabled="disabled"
        title="請選擇"
        name="state"
        :data-live-search="search"
        v-bind="$attrs"
        data-display="static"
        data-dropup-auto="false"
        :data-size="optionsShowSize"
        @change="change"
      >
        <option
          v-for="option in optionList"
          :key="option[trackBy]"
          :value="option[trackBy]"
        >
          {{ option[label] }}
        </option>
      </select>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BaseSelect',
  inheritAttrs: false,
  props: {
    /** 樣式 */
    classInput: {
      type: String,
      default: 'form-control'
    },
    /**資料來源文字顯示的物件key */
    label: {
      type: [Function, String],
      default: 'label'
    },
    /**資料來源vlaue值的物件key */
    trackBy: {
      type: String,
      default: 'value'
    },
    /**資料來源陣列 */
    options: {
      type: Array,
      default: () => []
    },
    /**已選選項值 */
    select: {
      type: [String, Number],
      default: null
    },
    /**是否顯示選項標題 */
    showTitle: {
      type: Boolean,
      default: false
    },
    /**選項標題文字 */
    titleText: {
      type: String,
      default: ''
    },
    /**是否為必選 */
    required: {
      type: Boolean,
      default: false
    },
    /**是否為disabled */
    disabled: {
      type: Boolean,
      default: false
    },
    /** 是否要開啟本地快搜 */
    search: {
      type: Boolean,
      default: false
    },
    /** 下拉選單最大顯示數量，超過就顯示scroll bar */
    optionsShowSize: {
      type: Number,
      default: 8
    }
  },
  data() {
    return {
      value: this.select,
      /**是否有要刷新選項 */
      isRefreshOption: false,
      /**是否有要刷新已選擇選項 */
      isRefreshSelect: false,
      noSelectTitle: '請選擇'
    }
  },
  computed: {
    optionList() {
      if (this.required) {
        return this.options
      } else {
        return [
          {
            [this.label]: '請選擇',
            [this.trackBy]: ''
          },
          ...this.options
        ]
      }
    }
  },
  watch: {
    disabled(newValue) {
      this.isRefreshOption = true
    },
    optionList(newValue) {
      this.isRefreshOption = true
    },
    select(newValue) {
      this.value = newValue
      this.isRefreshSelect = true
      //$(this.$refs.selectbase).selectpicker('val', newValue)
    },
    value(newValue) {
      if (newValue !== null && newValue !== undefined) {
        this.$emit('input', newValue)
      }
    }
  },
  async mounted() {
    $(this.$refs.selectbase).selectpicker()
    if (this.select) {
      $(this.$refs.selectbase).selectpicker('val', this.select)
    }
  },
  updated() {
    if (this.isRefreshOption) {
      $(this.$refs.selectbase).selectpicker('refresh')
      this.isRefreshOption = false

      if (this.required && !this.select) {
        if (this.options.length > 0) {
          this.value = this.options[0][this.trackBy]
          $(this.$refs.selectbase).selectpicker('val', this.value)
        }
      } else {
        if (this.select) {
          $(this.$refs.selectbase).selectpicker('val', this.select)
        }
      }
    }

    if (this.isRefreshSelect) {
      $(this.$refs.selectbase).selectpicker('val', this.select)
      this.isRefreshSelect = false
    }
  },
  methods: {
    change() {
      this.$emit('select-change')
    }
  }
}
</script>
<style lang="scss"></style>
