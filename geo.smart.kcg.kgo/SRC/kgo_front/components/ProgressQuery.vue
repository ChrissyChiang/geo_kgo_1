<template>
  <div class="wrap case-wrap">
    <h3>
      <img src="/img/icon-6-title.svg" alt="" />
      案件進度查詢
    </h3>
    <validation-observer ref="observer">
      <div class="w-100">
        <div class="form-group row">
          <label class="col-12 col-sm-3 col-form-label" for="case_number">
            案件編號
          </label>

          <div class="col-12 col-sm-9">
            <validation-provider rules="required" v-slot="{ errors }">
              <input
                id="case_number"
                type="text"
                class="form-control"
                name="case_number"
                title="案件編號欄位"
                v-model="caseId"
              />
              <span class="error_label w-100" style="color: red">
                {{ errors[0] }}
              </span>
            </validation-provider>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-12 col-sm-3 col-form-label" for="id_four_number">
            身份證後四碼
          </label>
          <div class="col-12 col-sm-9">
            <validation-provider rules="required|num" v-slot="{ errors }">
              <input
                id="id_four_number"
                type="password"
                class="form-control"
                maxlength="4"
                oninput="value=value.replace(/[^\d]/g,'')"
                v-model="identity"
                name="id_four_number"
                title="身份證後四碼欄位"
              />
              <span class="error_label w-100" style="color: red">
                {{ errors[0] }}
              </span>
            </validation-provider>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-12 col-sm-3 col-form-label" for="id_captcha">
            驗證碼
          </label>
          <div class="col-12 col-sm-9">
            <div class="form-row">
              <div class="col">
                <validation-provider
                  :rules="'num|captch:' + captchaCode"
                  v-slot="{ errors }"
                  mode="passive"
                >
                  <input
                    id="id_captcha"
                    type="text"
                    class="form-control"
                    title="驗證碼欄位"
                    maxlength="4"
                    oninput="value=value.replace(/[^\d]/g,'')"
                    v-model="inputidentifyCode"
                  />
                  <span class="error_label w-100" style="color: red">
                    {{ errors[0] }}
                  </span>
                </validation-provider>
              </div>
              <captcha
                :code.sync="captchaCode"
                :token.sync="captchaToken"
                ref="captcha"
              />
            </div>
          </div>
        </div>
        <div class="form-group row text-center">
          <div class="col-12 col-sm-3"></div>
          <div class="col-12 col-sm-9">
            <button
              class="btn btn-block btn-org"
              @click="next"
              type="button"
              title="查詢按鈕"
            >
              查詢
            </button>
          </div>
        </div>
      </div>
    </validation-observer>
    <system-confirm ref="modal" :msg="msg" />
  </div>
</template>
<script>
import SystemConfirm from './SystemConfirm.vue'
import page from '@/mixins/page.js'
export default {
  components: { SystemConfirm },
  mixins: [page],
  data() {
    return {
      caseId: '',
      identity: '',
      captchaCode: '',
      captchaToken: '',
      inputidentifyCode: '',
      msg: ''
    }
  },
  async mounted() {
    if (this.$route.query.caseid) {
      this.caseId = this.$route.query.caseid
    }
  },
  methods: {
    async next() {
      //表單驗證
      if (!(await this.$refs.observer.validate())) {
        this.$refs.captcha.refreshCode()
        return
      }

      this.validateToken = this.captchaToken
      this.validateCode = this.captchaCode

      //驗證輸入資料
      var post = {
        gstrCaseId: this.caseId,
        idCheck: this.identity,
        validateCode: this.validateCode,
        validateCodeToken: this.validateToken
      }
      this.$nuxt.$loading.start()
      const res = await this.$apiCaseList.getCaseDetailCheck(post, error => {
        this.msg = this.$common.errorRspMsg(error)
        this.$refs.modal.show()
      })
      this.$nuxt.$loading.finish()
      if (!res) return
      if (res.data && !res.data.result) {
        this.msg = '查詢資料有誤'
        this.$refs.modal.show()
        this.$refs.captcha.refreshCode()
        this.inputidentifyCode = ''
        return
      }

      //進入表單查詢
      this.$router.push({
        name: 'case-detail-id',
        params: {
          id: this.caseId,
          type: 0,
          identity: this.identity
        }
      })
    }
  }
}
</script>
