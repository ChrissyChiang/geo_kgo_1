<template>
  <div>
    <input
      :id="$attrs.id || `input-checkbox-${_uid}`"
      class="checkbox-custom"
      type="checkbox"
      :checked="isChecked"
      :value="inputValue"
      :disabled="disabled"
      @change="change"
    />
    <label
      class="checkbox-custom-label"
      :class="labelClass"
      :for="$attrs.id || `input-checkbox-${_uid}`"
    >
      {{ label }}
    </label>
  </div>
</template>

<script>
export default {
  name: 'InputCheckbox',
  inheritAttrs: false,
  model: {
    prop: 'checked',
    event: 'change'
  },
  props: {
    label: {
      type: String,
      default: ''
    },
    checked: {
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
      return Array.isArray(this.checked)
        ? this.checked.includes(this.inputValue)
        : this.checked === this.trueValue
    }
  },
  methods: {
    change() {
      let checked
      if (Array.isArray(this.checked)) {
        checked = [].concat(this.checked)
        if (checked.includes(this.inputValue)) {
          checked.splice(checked.indexOf(this.inputValue), 1)
        } else {
          checked.push(this.inputValue)
        }
      } else {
        checked =
          this.checked === this.trueValue ? this.falseValue : this.trueValue
      }
      this.$emit('change', checked)
    }
  }
}
</script>

<style lang="scss"></style>
