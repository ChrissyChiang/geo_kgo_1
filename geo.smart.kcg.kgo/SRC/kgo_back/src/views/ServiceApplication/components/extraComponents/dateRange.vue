<template>
  <validate-container :rules="required ? '|required' : ''">
    <base-date
      v-model="pfDate"
      placeholder="請選擇日期區間"
      :is-range="true"
      :value="pfDate"
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
      pfDateV: [],
      valueV: this.value
    }
  },
  computed: {
    pfDate: {
      get() {
        if (this.value != '') {
          let value = this.value
          let temList = value.split('-')
          let dataList = []
          dataList.push(temList[0])
          dataList.push(temList[1])
          return dataList
        } else {
          return []
        }
      },
      set(newValue) {
        this.pfDateV = newValue
      }
    }
  }
}
</script>
