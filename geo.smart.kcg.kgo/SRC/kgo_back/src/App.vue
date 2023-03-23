<template>
  <div>
    <router-view />
    <app-notification group="action" />
    <loading
      loader="dots"
      :active.sync="this.$store.state.isLoading"
      :can-cancel="true"
      :is-full-page="true"
      color="#fff"
      :width="128"
      :height="128"
      background-color="#000"
      :opacity="0.3"
      :z-index="9999"
    ></loading>
  </div>
</template>
<script>
import VueLoading from 'vue-loading-overlay'
export default {
  components: {
    loading: VueLoading
  },
  data() {
    return {}
  },
  async mounted() {},
  methods: {}
}
</script>
