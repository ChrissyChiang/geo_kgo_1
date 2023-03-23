<template>
  <div class="demo-container">
    <div class="panel-body">
      <validation-observer ref="observer">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-10">
              <label for="input_event" class="col-sm-4 control-label">
                詞彙標題
              </label>
              <div class="col-sm-8">
                <validate-container :rules="'required'">
                  <base-input
                    v-model="title"
                    placeholder="請輸入詞彙標題"
                    input-length="100"
                  ></base-input>
                </validate-container>
              </div>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group  col-md-10">
              <label for="input_event" class="col-sm-4 control-label">
                詞彙內容
              </label>
              <div class="col-sm-8">
                <validate-container :rules="'required'">
                  <base-textarea
                    v-model="word"
                    :placeholder="'請輸入詞彙內容'"
                    :row="10"
                    input-length="500"
                  ></base-textarea>
                </validate-container>
              </div>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled ">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button
                    type="submit"
                    class="btn btn-fsm"
                    @click="saveVocabulary()"
                  >
                    <i class="fa fa-save" aria-hidden="true"></i>
                    儲存
                  </button>
                  <button type="submit" class="btn btn-fsm" @click="goBack()">
                    <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                    返回
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </validation-observer>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      seq: '',
      title: '',
      word: ''
    }
  },
  async mounted() {
    this.seq = this.$route.params.id
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      let request = {}
      if (this.seq != 'null') {
        request = { seq: this.seq }
      } else {
        request = { seq: '' }
      }
      const res = await this.$api.cases.getEditVocabularySettingHomeData(
        request
      )
      this.title = res.data.data.title
      this.word = res.data.data.word
    },
    async saveVocabulary() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = { seq: this.seq, title: this.title, word: this.word }
      const res = await this.$api.cases.editVocabulary(request)
      this.notifySuccess(res.data.data.msg)
    },
    goBack() {
      this.$router.push(`/vocabularySetting`)
    }
  }
}
</script>
