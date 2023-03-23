<template>
  <div
    :id="`appModal${_uid}`"
    ref="modal"
    class="modal fade modal-page"
    role="dialog"
    :aria-labelledby="`appModalLabel${_uid}`"
    aria-hidden="true"
  >
    <div
      :class="modalClass"
      :style="isLargeSize ? 'width:90%;' : ''"
      role="document"
    >
      <div class="modal-content">
        <div class="panel panel-fsm" style="margin-bottom: 0px;">
          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
            @click="hide"
          >
            &times;
          </button>
          <span
            :id="`appModalLabel${_uid}`"
            class="panel-heading"
            style="border-bottom: 1px solid #ddd;"
          >
            {{ modalTitle }}
          </span>
        </div>
        <div :class="[isScroll ? 'modal-scroll' : '']">
          <slot />
        </div>
        <div class="modal-footer">
          <div class="btns">
            <slot name="button">
              <button
                v-if="hideButton"
                type="button"
                class="btn"
                :class="`${$listeners.submit ? 'btn-danger' : 'btn-primary'}`"
                @click="hide"
              >
                {{ closeText }}
              </button>

              <button
                v-if="$listeners.submit"
                type="button"
                class="btn btn-primary ml-5"
                @click="$emit('submit')"
              >
                {{ submitText }}
              </button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    /** 是否使用html內容 */
    isUseHtmlContent: {
      type: Boolean,
      default: false
    },
    /** 關閉鈕文字 */
    closeText: {
      type: String,
      default: '關閉'
    },
    /** 確認鈕文字 */
    submitText: {
      type: String,
      default: '確認'
    },
    modalTitle: {
      type: String,
      default: ''
    },
    webWidth: {
      type: String,
      default: 'width: 900px;'
    },
    hideButton: {
      type: Boolean,
      default: false
    },
    closeButton: {
      type: Boolean,
      default: true
    },
    modalClass: {
      type: String,
      default: 'modal-dialog modal-lg'
    },
    isScroll: {
      type: Boolean,
      default: false
    },
    /** 是否為大尺寸 */
    isLargeSize: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    getContentClass() {
      return this.isUseHtmlContent ? 'html-content' : 'modal-body'
    }
  },
  mounted() {
    // 當 Modal開啟前執行
    $(this.$refs.modal).on('show.bs.modal', () => {
      this.$emit('before-show')
    })

    // 當 Modal 開啟後執行
    $(this.$refs.modal).on('shown.bs.modal', () => {
      this.$emit('after-show')
    })

    // 當 Modal 關閉前執行
    $(this.$refs.modal).on('hide.bs.modal', () => {
      this.$emit('before-hide')
    })

    // 當 Modal 關閉後執行
    $(this.$refs.modal).on('hidden.bs.modal', () => {
      this.$emit('after-hidden')
    })
  },
  methods: {
    /** 顯示 Modal */
    show(isStatic = false) {
      if (isStatic) {
        $(this.$refs.modal).modal({
          backdrop: 'static',
          keyboard: false
        })
      } else {
        $(this.$refs.modal).modal('show')
      }
    },
    /** 關閉 Modal */
    hide() {
      $(this.$refs.modal).modal('hide')
    }
  }
}
</script>

<style lang="scss" scoped>
.html-content {
  height: 76vh;
  overflow-x: hidden;
  overflow-y: auto;
  padding: 10px 50px 30px;
}
.modal-scroll {
  height: 70vh;
  overflow-y: auto;
}
</style>
