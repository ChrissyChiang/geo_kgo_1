<template>
  <validation-provider
    v-slot="data"
    :rules="rules"
    v-bind="$attrs"
    v-on="$listeners"
    :name="name"
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
    },
    name: {
      type: String,
      default: ''
    }
  }
}
</script>

<style lang="scss" scoped>
.error_label {
  font-size: 0.9rem;
  color: red;
}
.text-wrapper {
  white-space: pre-wrap;
}
</style>
