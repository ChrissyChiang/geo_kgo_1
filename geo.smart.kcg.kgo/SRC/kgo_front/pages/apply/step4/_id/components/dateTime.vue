<template>
  <validate-container :rules="required ? '|required' : ''">
    <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span v-if="showvalue != ''" class="col-sm-auto">{{ showvalue }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
  </validate-container>
</template>

<script>
export default {
  name: 'DateTime',
  data() {
    return {
      pfDate: null,
      valueV: this.value
    }
  },
  props: {
    value: {
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
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    parent: {
      type: String,
      default: ''
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
      //console.log(this.valueV)
    },
    value() {
      this.valueV = this.value
    }
  },
  computed: {}
}
</script>
