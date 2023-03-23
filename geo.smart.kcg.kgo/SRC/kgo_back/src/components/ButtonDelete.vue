<template>
  <div class="button_style btn-remove" v-bind="$attrs" v-on="$listeners">
    <a data-toggle="modal" data-target="#modalDelete"></a>
  </div>
</template>

<script>
export default {
  name: 'ButtonDelete',
  inheritAttrs: false
}
</script>

<style lang="scss" scoped>
.btn-remove {
  &:hover {
    background: #ef4f41;
  }
}
</style>
