<template>
  <validate-container v-slot="data" :rules="required ? 'required' : ''">
    <div class="row">
      <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
      <div class="col-sm-auto">
        <select
          ref="selectbase"
          v-model="valueV"
          title="請選擇"
          name="state"
          style="width: auto"
          class="custom-select form-control"
          :class="[data && data.errors[0] ? 'invalid is-error' : '']"
          v-bind="$attrs"
          :disabled="disabled"
        >
          <option
            v-for="option in dataItems"
            :key="option.value"
            :value="option.value"
          >
            {{ option.label }}
          </option>
        </select>
      </div>
      <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
    </div>
  </validate-container>
</template>
<script>
export default {
  name: 'DropDown',
  data() {
    let dataList = []
    //this.value: radio1-單選1,radio2-單選2,radio3-單選3
    if (this.value != '') {
      let value = this.value
      let temList = value.split(',')
      //[radio1-單選1,radio2-單選2,radio3-單選3]

      for (let i = 0; i < temList.length; i++) {
        let temList2 = temList[i].split('-')
        //[radio1,單選1]
        if (i == 0) {
          // 若為必選則將第一個選項設為預設，若非必選則加入請選擇
          dataList.push({ value: '', label: '請選擇' })
        }
        dataList.push({ value: temList2[0], label: temList2[1] })
      }
    }
    var val = this.selectedValue
    if (this.datavalue) val = this.datavalue
    return {
      valueV: val,
      dataItems: dataList
    }
  },
  props: {
    /**下拉選單value&label */
    value: {
      type: String,
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    },
    selectedValue: {
      type: String,
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    parent: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputLength: {
      type: Number,
      default: 50
    },
    datavalue: {
      type: [String, Number],
      default: ''
    }
  },
  watch: {
    selectedValue() {
      this.valueV = this.selectedValue
    },
    valueV() {
      this.$emit('update:datavalue', this.valueV)
      //var label = this.dataItems.find(x => x.value == this.valueV).label
      //this.$emit('update:showvalue', this.valueV == '' ? '' : label)
    }
  },
}
</script>
<style lang="scss" scoped>
.is-error {
  border-color: #d33f39;
}
</style>
