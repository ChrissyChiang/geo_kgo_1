<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ title }}</h2>
        <a
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          class="accesskey"
          accesskey="C"
          name="C"
        >
          :::
        </a>
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <app-navigation page-title="案件進度查詢" />
      <!-- breadcrumb end -->

      <div class="apply-inner">
        <validation-observer ref="observer">
          <div class="apply-item">
            <fieldset>
              <legend class="text-hide">認證方式</legend>
              <div class="form-group row">
                <label class="col-sm-2 col-form-label" for="certi2">
                  認證方式
                </label>
                <div class="col-sm-10 v-center">
                  <div
                    class="custom-control custom-radio custom-control-inline"
                  >
                    <input
                      type="radio"
                      id="certi2"
                      name="certiradio"
                      class="custom-control-input"
                      value="0"
                      v-model="type"
                      checked
                    />
                    <label class="custom-control-label" for="certi2">
                      身份證後四碼
                    </label>
                  </div>
                  <div
                    class="custom-control custom-radio custom-control-inline"
                  >
                    <input
                      type="radio"
                      id="certi3"
                      name="certiradio"
                      class="custom-control-input"
                      value="2"
                      v-model="type"
                    />
                    <label class="custom-control-label" for="certi3">
                      行動電話
                    </label>
                  </div>
                </div>
              </div>
            </fieldset>
            <hr />
            <fieldset>
              <legend>請輸入申請編號及{{ idlabel }}進行承辦案件查詢</legend>
              <div class="form-group row">
                <label class="col-sm-2 col-form-label" for="case_id">
                  申請編號
                </label>
                <div class="col-sm-10">
                  <validation-provider rules="required" v-slot="{ errors }">
                    <input
                      id="case_id"
                      placeholder="請輸入申請編號"
                      type="text"
                      class="form-control"
                      v-model="caseId"
                    />
                    <span class="error_label w-100" style="color: red">
                      {{ errors[0] }}
                    </span>
                  </validation-provider>
                </div>
              </div>
              <div class="form-group row">
                <label class="col-sm-2 col-form-label" for="password-field">
                  {{ idlabel }}
                </label>
                <div class="col-sm-10">
                  <div class="form-row">
                    <div class="col">
                      <validation-provider
                        :rules="(type == 0 ? 'num' : 'phone') + '|required'"
                        v-slot="{ errors }"
                      >
                        <input
                          id="password-field"
                          type="password"
                          :maxlength="type == 0 ? '4' : '10'"
                          oninput="(value = value.replace(/[^\d]/g, ''))"
                          class="form-control"
                          name="password"
                          v-model="identity"
                          :placeholder="'請輸入' + idlabel"
                          :title="'請輸入' + idlabel"
                        />
                        <span class="error_label w-100" style="color: red">
                          {{ errors[0] }}
                        </span>
                      </validation-provider>
                    </div>
                    <div class="col d-flex align-items-center captcha">
                      <i
                        toggle="#password-field"
                        class="fa fa-eye toggle-password"
                      ></i>
                    </div>
                  </div>
                </div>
              </div>
            </fieldset>
            <hr />
            <fieldset>
              <legend class="text-hide">驗證碼</legend>
              <div class="form-group row">
                <label class="col-sm-2 col-form-label" for="id_captch">
                  驗證碼
                </label>
                <div class="col-sm-10">
                  <div class="form-row">
                    <div class="col">
                      <validation-provider
                        :rules="'num|captch:' + captchaCode"
                        v-slot="{ errors }"
                        mode="passive"
                      >
                        <input
                          id="id_captch"
                          type="text"
                          class="form-control"
                          v-model="inputidentifyCode"
                          placeholder="請輸入驗證碼"
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
            </fieldset>
          </div>
          <div class="form-group text-center btn-even">
            <button
              @click="search"
              class="btn btn-lg btn-org"
              type="button"
              title="查詢按鈕"
            >
              查詢
            </button>
          </div>
        </validation-observer>
      </div>
    </div>
    <system-confirm :msg="msg" ref="modal" />
  </div>
</template>
<script>
import SystemConfirm from '../../components/SystemConfirm.vue'
import page from '@/mixins/page.js'
export default {
  components: { SystemConfirm },
  mixins: [page],
  watch: {
    type() {
      this.idlabel = this.type == 0 ? '身份證後四碼' : '行動電話'
      this.identity = ''
    }
  },
  data() {
    return {
      caseId: '',
      identity: '',
      captchaCode: '',
      captchaToken: '',
      inputidentifyCode: '',
      msg: '',
      idlabel: '身份證後四碼',
      type: 0,
      title: '案件進度查詢'
    }
  },
  mounted() {
    this.$nextTick(() => {
      $('.toggle-password').click(function() {
        $(this).toggleClass('fa-eye fa-eye-slash')
        var input = $($(this).attr('toggle'))
        if (input.attr('type') == 'password') {
          input.attr('type', 'text')
        } else {
          input.attr('type', 'password')
        }
      })
    })
  },
  head() {
    return {
      title: this.title
    }
  },
  methods: {
    async search() {
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
        validateCode: this.validateCode,
        validateCodeToken: this.validateToken
      }
      switch (parseInt(this.type)) {
        case 0:
          post.idCheck = this.identity
          break
        case 2:
          post.phone = this.identity
          break
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

      //將參數帶入明細頁
      this.$router.push({
        name: 'case-detail-id',
        params: {
          id: this.caseId,
          type: this.type,
          identity: this.identity
        }
      })
    }
  }
}
</script>
