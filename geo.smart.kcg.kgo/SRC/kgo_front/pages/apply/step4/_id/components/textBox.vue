<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span v-if="showvalue != ''" class="col-sm-auto">{{ showvalue }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
</template>

<script>
export default {
  name: 'TextId',
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
    datavalue: {
      type: [String, Number],
      default: ''
    },
    showvalue: {
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
    parent: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputLength: {
      type: Number,
      default: 50
    }
  },
  watch: {
    value() {
      this.valueV = this.value
    },
    valueV() {
      this.$emit('update:datavalue', this.valueV)
      this.$emit('update:showvalue', this.valueV)
    },
    inputMax() {
      this.inputMax = this.inputLength
    }
  },
  computed: {
    check() {
      if (process.client) {
        if (this.parent != '') {
          if (document.getElementById('Radio111').checked) {
            return 'required'
          } else {
            return ''
          }
        } else {
          return ''
        }
      }
    }
  },
  methods: {
    checkRule() {}
  }
}
</script>
<style lang="scss" scoped>
.is-error {
  border-color: #d33f39;
}
</style>
