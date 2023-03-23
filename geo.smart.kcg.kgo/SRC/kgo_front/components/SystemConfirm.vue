<template>
  <div
    class="modal fade"
    ref="sysModal"
    id="sysModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="sysModalTitle"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content p-3">
        <div class="modal-header">
          <div class="modal-title" style="color: red" id="sysModalTitle">
            系統提示
          </div>
          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
            title="系統提示"
            @click="onOK"
          >
            <i class="fa fa-times fa-lg"></i>
          </button>
        </div>
        <div>
          <p v-html="msg"></p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      onOKClick: function () {}
    }
  },
  props: {
    msg: {
      type: String,
      default: ''
    }
  },
  methods: {
    onOK() {
      this.onOKClick()
    },
    show(onOK) {
      if (onOK) this.onOKClick = onOK
      $(this.$refs.sysModal).modal('show')
    },
    hide() {
      $(this.$refs.sysModal).modal('hide')
    },
    confirm() {
      $(this.$refs.sysModal).modal('hide')
      this.$emit('confirm')
    }
  }
}
</script>
