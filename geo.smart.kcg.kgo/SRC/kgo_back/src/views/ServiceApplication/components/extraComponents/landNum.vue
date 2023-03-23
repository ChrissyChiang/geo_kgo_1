<template>
  <div>
    <div>
      <tr>
        <td style="padding-right:10px">
          <select
            ref="selectbase"
            v-model="valueV"
            title="請選擇"
            name="state"
            style="width: 150px"
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
        </td>
        <td style="padding-right:10px">
          <select
            ref="selectbase"
            v-model="valueV"
            title="請選擇"
            name="state"
            style="width: 150px;"
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
        </td>
        <td style="padding-right:10px">
          <select
            ref="selectbase"
            v-model="valueV"
            title="請選擇"
            name="state"
            style="width: 150px;"
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
        </td>
        <td>
          <base-input style="width: 300px"></base-input>
        </td>
      </tr>
    </div>
  </div>
</template>
<script>
export default {
  name: 'AddressInput',
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
          this.setSelect(temList2[0])
        }
        dataList.push({ value: temList2[0], label: temList2[1] })
      }
      return dataList
    }
  },
  methods: {
    setSelect(value) {
      this.valueV = value
    },
    initData() {
      console.log('test')
    }
  }
}
</script>
