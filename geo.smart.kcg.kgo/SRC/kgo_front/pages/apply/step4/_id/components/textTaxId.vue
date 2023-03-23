<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm">{{ fText }}</span>
    <span v-if="showvalue != ''" class="col-sm-auto">{{ showvalue }}</span>
    <span v-if="bText != ''" class="col-sm">{{ bText }}</span>
  </div>
</template>

<script>
export default {
  inheritAttrs: false,
  data() {
    return {
      valueV: this.value
    }
  },
  props: {
    value: {
      type: [String, Number],
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
    datavalue: {
      type: [String, Number],
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    }
  },
  watch: {
    value() {
      this.valueV = this.value
    }
  }
}
</script>
<style lang="scss" scoped>
.is-error {
  border-color: #d33f39;
}
</style>
