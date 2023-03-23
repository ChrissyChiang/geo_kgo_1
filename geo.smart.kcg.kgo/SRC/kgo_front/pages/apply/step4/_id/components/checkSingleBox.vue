<template>
  <div>
    <label v-if="fText != ''" :for="$attrs.id">{{ fText }}</label>
    <input type="checkbox" v-model="datavalue" readonly disabled />
    <label v-if="bText != ''" :for="$attrs.id">{{ bText }}</label>
  </div>
</template>

<script>
export default {
  watch: {
    valueV() {
      this.$emit('update:datavalue', this.valueV ? 'Y' : '123')
      this.$emit('update:showvalue', this.valueV ? 'Y' : '123')
    }
  },
  name: 'checkBox',
  data() {
    return {
      valueV: false,
      select: ''
    }
  },

  props: {
    value: {
      type: String,
      default: ''
    },
    selectedValue: {
      type: String,
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
  }
}
</script>
