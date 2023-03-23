<template>
  <div
    class="modal fade"
    ref="identifyCodeModal"
    id="identifyCodeModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="identifyCodeTitle"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content p-3">
        <div class="modal-header">
          <div class="modal-title" style="color: red" id="identifyCodeTitle">
            驗證碼檢核
          </div>

          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
            title="系統提示"
            @click="cancel"
          >
            <i class="fa fa-times fa-lg"></i>
          </button>
        </div>
        <div class="">
          <validation-observer ref="identifyObserver">
            <div class="form-group row">
              <label class="col-12 col-sm-3 col-form-label" for="id_captch">
                驗證碼
              </label>
              <div class="col-12 col-sm-9">
                <div class="form-row">
                  <div class="col">
                    <validation-provider
                      :rules="'required|num|captch:' + captchaCode"
                      v-slot="{ errors }"
                      mode="passive"
                    >
                      <input
                        id="id_captch"
                        type="text"
                        class="form-control"
                        title="驗證碼欄位"
                        maxlength="4"
                        oninput="value=value.replace(/[^\d]/g,'')"
                        v-model="inputidentifyCode"
                        @keyup.enter.prevent="confirm"
                      />
                      <span class="error_label w-100" style="color: red">
                        {{ errors[0] }}
                      </span>
                    </validation-provider>
                  </div>
                  <captcha :code.sync="captchaCode" :token.sync="captchaToken" ref="captcha" />
                </div>
              </div>
            </div>
          </validation-observer>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="confirm">
            確認
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import page from '@/mixins/page.js'
export default {
  name: 'IdentifyCodeConfirm',
  mixins: [page],
  data() {
    return {
      captchaCode: '',
      captchaToken: '',
      inputidentifyCode: ''
    }
  },
  mounted() {
    // 當 Modal 關閉後執行
    $(this.$refs.identifyCodeModal).on('hidden.bs.modal', () => {
      this.$emit('after-hidden')
    })
  },
  methods: {
    show() {
      this.$refs.captcha.refreshCode()
      $(this.$refs.identifyCodeModal).modal('show')
    },
    hide() {
      $(this.$refs.identifyCodeModal).modal('hide')
    },
    clear() {
      this.inputidentifyCode = ''
      this.$refs.identifyObserver.reset()
    },
    async confirm() {
      if (!(await this.$refs.identifyObserver.validate())) {
        this.$refs.captcha.refreshCode()
        return
      } else {
        this.validateToken = this.captchaToken
        this.validateCode = this.captchaCode
      }
      this.$emit('validate-pass')
    },
    cancel() {
      this.clear()
    }
  }
}
</script>
