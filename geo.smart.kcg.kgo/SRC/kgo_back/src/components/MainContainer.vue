<template>
  <div class="fsm-contents fsm-layout-col-2 fsm-layout-col-2-l">
    <div class="container-fluid">
      <app-menu />

      <!-- FSM Panel -->
      <div
        class="fsm-layout-contents"
        style="margin-bottom: auto; margin-top: 35px;"
      >
        <!--<div class="demo-container">-->
        <slot />
        <!--</div>-->
      </div>
    </div>
  </div>
</template>
<script>
import AppMenu from '@/components/AppMenu.vue'
export default {
  components: {
    AppMenu
  }
}
</script>
<style scoped>
.demo-container {
  background-color: #fff;
  padding: 15px;
  border-radius: 20px;
}

.demo-title {
  color: #009bad;
  background-color: #eee;
  padding: 8px 15px;
  margin: 20px -15px 10px;
}
.title_li {
  width: 25%;
  float: left;
  text-align: center;
  height: 35px;
  padding-top: 6px;
}
</style>
