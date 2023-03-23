<template>
  <div class="demo-container">
    <div class="panel-body">
      <validation-observer ref="observer">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-10">
              <label for="input_event" class="col-sm-4 control-label">
                問題
              </label>
              <div class="col-sm-8">
                <validate-container :rules="'required'">
                  <base-input
                    v-model="question"
                    placeholder="請輸入問題"
                    :input-length="'100'"
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
                解答內容
              </label>
              <div class="col-sm-8">
                <validate-container :rules="'required'">
                  <base-textarea
                    v-model="answer"
                    :placeholder="'請輸入解答內容'"
                    :row="10"
                    :max="'500'"
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
                    @click="saveQusetion()"
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
      question: '',
      answer: ''
    }
  },
  async mounted() {
    //加入初始api
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
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getEditQuestionManagementHomeData(
          request
        )
        this.question = res.data.data.qestion
        this.answer = res.data.data.content
      })
    },
    async saveQusetion() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = {
        seq: this.seq,
        question: this.question,
        content: this.answer,
        state: ''
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.editQuestion(request)
        this.notifySuccess(res.data.data.msg)
      })
    },
    goBack() {
      this.$router.push(`/questionManagement`)
    }
  }
}
</script>
