<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span v-if="showvalue != ''" class="col-sm-auto">{{ showvalue }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
</template>

<script>
export default {
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
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputLength: {
      type: Number,
      default: 50
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
    valueV() {
      this.$emit('update:datavalue', this.valueV)
      this.$emit('update:showvalue', this.valueV)
    },
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
