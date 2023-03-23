<template>
  <validate-container :rules="'id' + (required ? '|required' : '')">
    <base-input v-model="valueV" type="text" />
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
  }
}
</script>
