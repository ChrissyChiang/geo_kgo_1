<template>
  <div>
    <div>
      <validate-container :rules="required ? 'must_select' : ''">
        <select
          ref="selectbase"
          v-model="valueV"
          title="請選擇"
          name="state"
          style="width: auto;"
          class="form-control"
          v-bind="$attrs"
        >
          <option
            v-for="option in optionList"
            :key="option.value"
            :value="option.value"
          >
            {{ option.label }}
          </option>
        </select>
      </validate-container>
    </div>
  </div>
</template>
<script>
export default {
  name: 'DropDown',
  props: {
    /**資料來源陣列 */
    options: {
      type: Array,
      default: () => []
    },
    /**下拉選單value&label */
    value: {
      type: String,
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      valueV: ''
    }
  },
  computed: {
    optionList() {
      let value = this.value
      let temList = value.split(',')
      let dataList = []
      for (let i = 0; i < temList.length; i++) {
        let temList2 = temList[i].split('-')
        if (i == 0) {
          // 若為必選則將第一個選項設為預設，若非必選則加入請選擇
          this.required
            ? this.setSelect(temList2[0])
            : dataList.push({ value: '', label: '請選擇' })
        }
        dataList.push({ value: temList2[0], label: temList2[1] })
      }
      return dataList
    }
  },
  methods: {
    setSelect(value) {
      this.valueV = value
    }
  }
}
</script>
