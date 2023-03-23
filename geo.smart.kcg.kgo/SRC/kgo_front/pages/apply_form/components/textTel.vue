<template>
  <validate-container v-slot="data" :rules="required ? 'required' : ''">
    <div class="row">
      <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
      <div class="col-sm-auto">
        <input
          v-bind="$attrs"
          v-model="valueV"
          :maxlength="20"
          :disabled="disabled"
          placeholder="請輸入電話，如(07)12345678電話格式"
          type="text"
          class="form-control"
          :class="[data && data.errors[0] ? 'invalid is-error' : '']"
          style="width:310px"
        />
      </div>
      <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
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
