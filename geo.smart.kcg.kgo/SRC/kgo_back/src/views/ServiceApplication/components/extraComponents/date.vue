<template>
  <validate-container :rules="required ? '|required' : ''">
    <base-date
      v-model="valueV"
      placeholder="請選擇日期"
      :is-range="false"
      :value="valueV"
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
      //pfDate: [],
      valueV: this.value
    }
  },
  computed: {}
}
</script>
