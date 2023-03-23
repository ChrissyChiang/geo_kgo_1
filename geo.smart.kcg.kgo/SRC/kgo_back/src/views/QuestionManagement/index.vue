<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_category" class="col-sm-4 control-label">
                問題名稱
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="searchQuestion"
                  :placeholder="'請輸入問題名稱'"
                ></base-input>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                上下架日期
              </label>
              <div class="col-sm-8">
                <base-date v-model="pfDate" :is-range="true" :value="pfDate" />
              </div>
            </div>
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="submit" class="btn btn-fsm" @click="search()">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button
                      type="submit"
                      class="btn btn-fsm"
                      @click="addQusetion(null)"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      新增問題
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :show-pagination="true"
                >
                  <template v-slot:operating="{ data: { seq, state } }">
                    <button
                      v-if="state == '上架'"
                      type="button"
                      class="btn-line btn-warning"
                      @click="showConfirm('off', seq)"
                    >
                      <i class="fa fsm-icon-arrow-down" aria-hidden="true"></i>
                      <a>下架</a>
                    </button>
                    <button
                      v-if="state == '下架'"
                      type="button"
                      class="btn-line btn-warning"
                      @click="showConfirm('on', seq)"
                    >
                      <i class="fa fsm-icon-arrow-top" aria-hidden="true"></i>
                      <a>上架</a>
                    </button>
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="addQusetion(seq)"
                    >
                      <i class="fa fa-cog" aria-hidden="true"></i>
                      編輯
                    </button>
                    <button
                      type="button"
                      class="btn-line btn-danger"
                      @click="showDelConfirm(seq)"
                    >
                      <i class="fa fa-trash-o" aria-hidden="true"></i>
                      刪除
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <modal-confirm
        ref="confirmChangeState"
        :close-text="'取消'"
        :modal-content="modalcontent"
        :modal-warn="modalwarn"
        @confirm="changeState()"
      />
      <modal-confirm
        ref="confirmDelete"
        :close-text="'取消'"
        :modal-content="'是否確定刪除資料？'"
        :modal-warn="'確認刪除，系統將執行刪除'"
        @confirm="delQuestion()"
      />
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      searchQuestion: '',
      delQusetion: '',
      changeStateSeq: '',
      changeStateType: '',
      confirmContent: '',
      modalcontent: '',
      modalwarn: '',
      tableData: [],
      pfDate: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '項次',
          slot: 'order',
          tdClass: 'text-center'
        },
        {
          title: '問題名稱',
          dataIndex: 'question',
          tdClass: 'text-center'
        },
        {
          title: '狀態',
          tdClass: 'text-center',
          dataIndex: 'state'
        },
        {
          title: '上下架日期',
          dataIndex: 'publishTime',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getQuestionManagementHomeData()
        this.tableData = res.data.data.grid
      })
    },
    showDelConfirm(seq) {
      this.delQusetion = seq
      this.$refs.confirmDelete.show()
    },
    async changeState() {
      let request = { seq: this.changeStateSeq, state: this.changeStateType }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.questionOnOrOff(request)
        this.$refs.confirmChangeState.hide()
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    },
    showConfirm(param, seq) {
      if (param == 'off') {
        this.modalcontent = '是否確定下架分類？'
        this.modalwarn = '確認後，將下架此分類'
      } else if (param == 'on') {
        this.modalcontent = '是否確定上架分類？'
        this.modalwarn = '確認後，將上架此分類'
      }
      this.$refs.confirmChangeState.show()
      this.changeStateSeq = seq
      this.changeStateType = param
    },
    async delQuestion() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteQuestion({
          seq: this.delQusetion
        })
        this.$refs.confirmDelete.hide()
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    },
    async search() {
      let request = { question: this.searchQuestion, publishDate: this.pfDate }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.queryQuestion(request)
        this.tableData = res.data.data.grid
      })
    },
    addQusetion(id) {
      this.$router.push('questionManagement/addQuestion/' + id)
    }
  }
}
</script>
