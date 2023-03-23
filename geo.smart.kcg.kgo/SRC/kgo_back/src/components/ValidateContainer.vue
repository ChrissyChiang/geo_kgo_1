<template>
  <validation-provider
    v-slot="data"
    :rules="rules"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <slot v-bind="{ ...data }" />
    <div class="text-wrapper">
      <span class="error_label w-100">{{ data.errors[0] }}</span>
    </div>
  </validation-provider>
</template>

<script>
export default {
  name: 'ValidateContainer',
  inheritAttrs: false,
  props: {
    rules: {
      type: String,
      default: ''
    }
  }
}
</script>

<style lang="scss" scoped>
.error_label {
  font-size: 14px;
  color: red;
  position: absolute;
  white-space: nowrap;
}
.text-wrapper {
  white-space: pre-wrap;
}
</style>
