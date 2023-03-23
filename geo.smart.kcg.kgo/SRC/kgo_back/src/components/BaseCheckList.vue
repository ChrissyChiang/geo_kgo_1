<template>
  <div style="line-height: 1.42857;">
    <span v-for="(item, index) in options" :key="index">
      <input
        :id="`_tmcaseparam_isverify${_uid}_${index}`"
        v-model="checkedList"
        :name="`tmcaseparam.isverify${_uid}_${index}`"
        class="checkbox-custom"
        type="checkbox"
        :disabled="disabledBy == '' ? disabled : item[disabledBy]"
        :value="item[trackBy]"
      />
      <label
        class="checkbox-custom-label"
        :for="`_tmcaseparam_isverify${_uid}_${index}`"
      >
        {{ item[label] }}
      </label>
    </span>
  </div>
</template>
<script>
export default {
  name: 'BaseCheckList',
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
    /** 是否可修改 */
    disabled: {
      type: Boolean,
      default: false
    },
    disabledBy: {
      type: String,
      default: ''
    },
    /** 選項 */
    options: {
      type: Array,
      default: () => []
    },
    /** 已選選 */
    selectedList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      checkedList: this.selectedList
    }
  },
  watch: {
    selectedList(newVal) {
      this.checkedList = newVal
    },
    checkedList(newVal) {
      this.$emit('input', this.checkedList)
    }
  }
}
</script>
