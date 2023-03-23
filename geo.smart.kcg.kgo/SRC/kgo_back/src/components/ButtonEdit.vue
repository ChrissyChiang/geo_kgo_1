<template>
  <router-link :to="link">
    <div class="button_style" v-bind="$attrs" v-on="$listeners"></div>
  </router-link>
</template>

<script>
export default {
  name: 'ButtonEdit',
  inheritAttrs: false,
  props: {
    link: {
      type: String,
      default: ''
    }
  }
}
</script>
