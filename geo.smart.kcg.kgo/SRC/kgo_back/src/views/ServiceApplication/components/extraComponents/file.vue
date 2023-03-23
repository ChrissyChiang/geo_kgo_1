<template>
  <validate-container :rules="'input_date'">
    <file-uploader
      id="importExcel"
      ref="importExcel"
      v-model="valueV"
      accept=".pdf"
      :ext-name="'pdf'"
    ></file-uploader>
  </validate-container>
</template>

<script>
import { page } from '@/mixins'
export default {
  name: 'File',
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
      valueV: this.value
    }
  }
}
</script>
