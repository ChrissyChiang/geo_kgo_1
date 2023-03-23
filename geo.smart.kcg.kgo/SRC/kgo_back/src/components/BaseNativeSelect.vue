<template>
  <select v-model="selectedData" class="form-control">
    <option v-for="item in options" :key="item[trackBy]" :value="item[trackBy]">
      {{ item[label] }}
    </option>
  </select>
</template>
<script>
export default {
  name: 'BaseNativeSelect',
  inheritAttrs: false,
  props: {
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
    /** 選項 */
    options: {
      type: Array,
      default: () => []
    },
    /** 已選選 */
    select: {
      type: String,
      default: ''
    },
    /**是否為必選 */
    required: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      selectedData: this.select
    }
  },
  watch: {
    select(newVal) {
      this.selectedData = newVal
    },
    options(newVal) {
      if (newVal && newVal.length > 0 && !this.select) {
        if (this.required == true) {
          this.selectedData = newVal[0].value
        }
      }
    },
    selectedData(newVal) {
      this.$emit('input', this.selectedData)
    }
  }
}
</script>
