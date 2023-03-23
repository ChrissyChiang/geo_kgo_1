<template>
  <validation-observer ref="observer" class="row">
    <slot />
  </validation-observer>
</template>

<script>
export default {
  name: 'AppForm',
  methods: {
    async submit() {
      if (await this.$refs.observer.validate()) {
        this.$emit('submit')
        this.resetValidate()
      }
    },
    resetValidate() {
      this.$refs.observer.reset()
    }
  }
}
</script>
