<template>
  <validate-container
    v-slot="data"
    :rules="'num' + (required ? '|required' : '')"
  >
    <div class="row">
      <span v-if="fText != ''" class="col-sm">{{ fText }}</span>
      <div class="col-sm-6">
        <input
          v-bind="$attrs"
          v-model="valueV"
          :maxlength="inputLength"
          :disabled="disabled"
          type="text"
          placeholder="請輸入數字"
          class="form-control"
          :class="[data && data.errors[0] ? 'invalid is-error' : '']"
          style="min-width:150px"
        />
      </div>
      <span v-if="bText != ''" class="col-sm">{{ bText }}</span>
    </div>
  </validate-container>
</template>

<script>
export default {
  name: 'TextNum',
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
    disabled: {
      type: Boolean,
      default: false
    },
    inputLength: {
      type: Number,
      default: 50
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
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
