<template>
  <validate-container
    v-slot="data"
    :rules="'orgcheck' + (required ? '|' + validRule : '')"
  >
    <div class="row">
      <div v-if="fText != ''" class="col-sm">
        <span>{{ fText }}</span>
      </div>
      <div :class="columnType != 'Address' ? 'col-sm-6' : 'col-sm'">
        <input
          v-bind="$attrs"
          :maxlength="inputLength"
          v-model="valueV"
          :disabled="disabled"
          type="text"
          class="form-control"
          :class="[data && data.errors[0] ? 'invalid is-error' : '']"
        />
      </div>
      <div v-if="bText != ''" class="col-sm">
        <span>{{ bText }}</span>
      </div>
    </div>
  </validate-container>
</template>

<script>
export default {
  data() {
    if (this.value != '') {
      this.$emit('update:datavalue', this.value)
      this.$emit('update:showvalue', this.value)
    }
    var rule = 'required'
    if (this.parent && this.parent != '') {
      rule = 'required_parent:@' + this.parent
    }

    return {
      valueV: this.value,
      validRule: rule
    }
  },
  props: {
    value: {
      type: [String, Number],
      default: ''
    },
    columnType: {
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
  computed: {},
  methods: {}
}
</script>
<style lang="scss" scoped>
.is-error {
  border-color: #d33f39;
}
</style>
