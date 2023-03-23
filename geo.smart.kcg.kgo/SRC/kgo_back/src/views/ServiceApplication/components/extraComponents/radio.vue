<template>
  <validate-container :rules="required ? 'required' : ''">
    <base-radio
      v-for="item in optionList"
      :key="item.value"
      v-model="valueV"
      :item-name="'test1'"
      :label="item.label"
      :value="item.value"
      :select="valueV"
    />
  </validate-container>
</template>

<script>
import { page } from '@/mixins'
export default {
  name: 'TextId',
  mixins: [page],
  props: {
    value: {
      type: [String, Number],
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      valueV: this.value
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
          // 若為必選則將第一個選項設為預設
          this.required ? this.setSelect(temList2[0]) : this.setSelect('')
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
