<template>
  <validate-container :rules="'email' + (required ? '|required' : '')">
    <base-input v-model="valueV" type="text" />
  </validate-container>
</template>

<script>
export default {
  inheritAttrs: false,
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
