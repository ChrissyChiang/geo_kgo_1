<template>
  <div
    style="display: inline-block;"
    class="form-check formCheck formCheck-inline"
    v-bind="$attrs"
  >
    <input
      :id="$attrs.id || `input-checkbox-${_uid}`"
      class="form-check-input"
      type="radio"
      :name="itemName"
      :checked="isChecked"
      :value="inputValue"
      :disabled="disabled"
      @change="change"
    />
    <label
      class="form-check-label"
      :for="$attrs.id || `input-checkbox-${_uid}`"
    >
      {{ label }}
    </label>
  </div>
</template>

<script>
export default {
  name: 'InputRadio',
  inheritAttrs: false,
  model: {
    prop: 'checked',
    event: 'change'
  },
  props: {
    itemName: {
      type: [String, Number, Array],
      default: () => []
    },
    label: {
      type: String,
      default: ''
    },
    checkedValue: {
      type: [Array, Boolean, Number, String],
      default: () => []
    },
    labelClass: {
      type: String,
      default: ''
    },
    trueValue: {
      type: [Boolean, Number, String],
      default: true
    },
    falseValue: {
      type: [Boolean, Number, String],
      default: false
    },
    value: {
      type: [Number, String],
      default: null
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    inputValue() {
      return this.value ? this.value.toString() : ''
    },
    isChecked() {
      return Array.isArray(this.checkedValue)
        ? this.checkedValue.includes(this.inputValue)
        : this.checkedValue === this.trueValue
    }
  },
  methods: {
    change() {
      let checked
      if (Array.isArray([this.checkedValue])) {
        // this.checkedValue = []
        checked = []
        checked.push(this.inputValue)
      } else {
        checked =
          this.checkedcheckedValue === this.trueValue
            ? this.falseValue
            : this.trueValue
      }
      this.$emit('change', checked)
    }
  }
}
</script>

<style lang="scss" scoped>
.radioInput {
  width: 24px;
  height: 24px;
  margin: 0;
  padding: 0;
  position: absolute;
  cursor: pointer;
  opacity: 1;
}
</style>
