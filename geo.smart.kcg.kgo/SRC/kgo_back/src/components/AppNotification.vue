<template>
  <notifications
    :classes="customClass"
    v-bind="$attrs"
    :position="position"
    v-on="$listeners"
  >
    <template slot="body">
      <slot />
    </template>
  </notifications>
</template>

<script>
export default {
  name: 'AppNotification',
  inheritAttrs: false,
  props: {
    position: {
      type: String,
      default: 'bottom right'
    },
    customClass: {
      type: String,
      default: 'app-notification'
    }
  }
}
</script>

<style lang="scss">
.vue-notification-group {
  margin-right: 20px !important;
  margin-bottom: 20px !important;
}
.vue-notification-wrapper {
  width: auto !important;
  text-align: right !important;
}
.app-notification {
  padding: 22px;
  margin: 8px 0px;
  color: #333;
  border-radius: 3px;
  box-shadow: 0 2px 8px 0 rgba(74, 74, 74, 0.17);
  font-size: 16px;
  font-weight: 500;
  line-height: 1.5;
  letter-spacing: 0.61px;
  width: 195px;
  display: inline-block;
  .notification-title {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: start;
    align-items: flex-start;
  }
  &.success {
    background: #ebf7e8;
    .icon-img {
      background-image: url('../assets/img/ic-msg-completed@2x.png');
    }
  }
  &.error {
    background: #f9cdda;
    .icon-img {
      background-image: url('../assets/img/ic-msg-warning@2x.png');
    }
  }
  .icon-img {
    width: 20px;
    height: 20px;
    -webkit-background-size: contain;
    background-size: contain;
    background-position: center center;
    background-repeat: no-repeat;
    display: inline-table;
    margin-right: 7px;
    margin-top: 1px;
  }
}
</style>
