<template>
  <div
    ref="modal"
    class="modal fade"
    tabindex="-1"
    role="dialog"
    data-backdrop="static"
    aria-labelledby="myModalLabel"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <h3 class="dialog_header">{{ modalTitle }}</h3>
        <div class="modal-body">
          <main class="dialog_body">
            <div id="sysMsg" class="dialog_content"></div>
          </main>
          <button type="button" class="button_dialog_submit" @click="confirm">
            {{ submitText }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SystemConfirm',
  props: {
    /** 確認鈕文字 */
    submitText: {
      type: String,
      default: '確認'
    },
    modalTitle: {
      type: String,
      default: 'title'
    }
  },
  computed: {
    modal() {
      return this
    }
  },
  methods: {
    show() {
      $(this.$refs.modal).modal('show')
    },
    hide() {
      $(this.$refs.modal).modal('hide')
    },
    confirm() {
      $(this.$refs.modal).modal('hide')
      this.$emit('confirm')
    }
  }
}
</script>
<style lang="scss" scoped>
.dialog_header {
  text-align: center;
  margin: 0;
  padding-top: 20px;
  padding-bottom: 16px;
  line-height: 24px;
  color: #333333;
  font-weight: bold;
}

.dialog_body {
  padding: 0px 20px 0px 20px;
}

.dialog_content {
  line-height: 24px;
  overflow: auto;
  font-size: 15px;
  word-wrap: break-word;
  word-break: break-all;
  color: #333333;
}

.dialog_footer {
  text-align: center;
  padding: 20px;
}

.button_dialog_submit {
  font-size: 15px;
  height: auto;
  padding: 11px;
  display: block;
  width: 100%;
  background: rgb(199, 210, 226);
  border-color: rgb(199, 210, 226);
  color: black;
  font-weight: bold;
  border-radius: 3px;
}
.modal-dialog-centered {
  display: -ms-flexbox !important;
  display: flex !important;
  -ms-flex-align: center !important;
  align-items: center !important;
  min-height: calc(100% - 1rem) !important;
}
.modal {
  text-align: center;
  padding: 0 !important;
}

.modal:before {
  content: '';
  display: inline-block;
  height: 100%;
  vertical-align: middle;
  margin-right: -4px;
}

.modal-dialog {
  display: inline-block;
  text-align: left;
  vertical-align: middle;
}
</style>
