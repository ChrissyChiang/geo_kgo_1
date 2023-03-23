<template>
  <div
    id="modalConfirm"
    ref="modalConfirm"
    class="modal fade modal-alert"
    :class="[modalContent == '' ? 'onlyTitle' : '']"
    tabindex="-1"
    role="dialog"
    data-backdrop="static"
    aria-labelledby="alert-defult-title"
  >
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-body">
          <h5 id="alert-defult-title" class="modal-title">{{ modalTitle }}</h5>
          <div v-if="modalContent != ''" class="modal-text text-center">
            {{ modalContent }}
          </div>
          <br />
          <div
            v-if="modalWarn != ''"
            style="color:red;"
            class="modal-text text-center"
          >
            {{ modalWarn }}
          </div>
        </div>
        <div class="modal-footer">
          <div class="btns">
            <button
              v-if="showClose"
              type="button"
              class="btn btn-secondary"
              @click="cancel"
            >
              {{ closeText }}
            </button>
            <button type="button" class="btn btn-primary" @click="confirm">
              {{ submitText }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModalConfirm',
  props: {
    /** 關閉鈕文字 */
    closeText: {
      type: String,
      default: '關閉'
    },
    modalTitle: {
      type: String,
      default: ''
    },
    /** 紅字警告訊息 */
    modalWarn: {
      type: String,
      default: ''
    },
    /** 是否顯示關閉鈕 */
    showClose: {
      type: Boolean,
      default: true
    },
    /** 確認鈕文字 */
    submitText: {
      type: String,
      default: '確認'
    },
    /** 確認按鈕是否要觸發確認事件，沒有的話直接關閉 */
    confirmEvent: {
      type: Boolean,
      default: true
    },
    modalContent: {
      type: String,
      default: ''
    }
  },
  computed: {
    modal() {
      return this
    }
  },
  mounted() {
    // 當 Modal 關閉後執行
    $(this.$refs.modalConfirm).on('hidden.bs.modal', () => {
      this.$emit('after-hidden')
    })
  },
  methods: {
    show() {
      $(this.$refs.modalConfirm).modal('show')
    },
    hide() {
      $(this.$refs.modalConfirm).modal('hide')
      this.$emit('close')
    },
    cancel() {
      $(this.$refs.modalConfirm).modal('hide')
      this.$emit('cancel')
    },
    confirm() {
      if (this.confirmEvent) {
        this.$emit('confirm', this.$refs.modal)
      } else {
        $(this.$refs.modalConfirm).modal('hide')
        this.$emit('close')
      }
    }
  }
}
</script>

<style lang="scss" scoped></style>
