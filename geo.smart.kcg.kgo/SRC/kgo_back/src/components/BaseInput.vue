<template>
  <input
    :class="inputClass"
    :value="value"
    :placeholder="placeholder"
    :type="type"
    v-bind="$attrs"
    :maxlength="inputLength"
    :disabled="disabled"
    v-on="listeners"
    @input="$emit('input', $event.target.value)"
  />
</template>

<script>
export default {
  inheritAttrs: false,
  props: {
    value: {
      type: [String, Number],
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    name: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'text'
    },
    inputLength: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputClass: {
      type: String,
      default: 'form-control '
    }
  },
  computed: {
    listeners() {
      // eslint-disable-next-line no-unused-vars
      const { input, ...listeners } = this.$listeners
      return listeners
    }
  }
}
</script>

<style lang="scss" scoped>
.full-width {
  width: 100%;
}
</style>
