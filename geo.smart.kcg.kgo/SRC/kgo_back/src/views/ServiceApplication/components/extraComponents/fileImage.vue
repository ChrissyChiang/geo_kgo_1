<template>
  <file-uploader
    id="importExcel"
    ref="importExcel"
    v-model="valueV"
    accept=".jpg,png"
    :ext-name="'jpg、png'"
  ></file-uploader>
</template>

<script>
import { page } from '@/mixins'
export default {
  name: 'File',
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
      test: null,
      valueV: this.value
    }
  }
}
</script>
