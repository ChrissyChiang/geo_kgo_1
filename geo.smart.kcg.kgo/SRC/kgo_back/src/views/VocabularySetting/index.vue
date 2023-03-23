<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-5 form-group" style="width: 70%;">
              <label
                for="input_category"
                class="col-sm-4 control-label"
                style="width: 15%;"
              >
                詞彙名稱
              </label>
              <div class="col-sm-12" style="width: 85%;">
                <base-input
                  v-model="searchWord"
                  :placeholder="'請輸入詞彙名稱'"
                ></base-input>
              </div>
            </div>
            <table class="table table-unstyled ">
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
                      詞彙新增
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12" style="">
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :show-pagination="true"
                >
                  <template v-slot:operating="{ data: { seq } }">
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
        ref="confirmDelete"
        :close-text="'取消'"
        :modal-content="'是否確定刪除資料？'"
        :modal-warn="'確認刪除，系統將執行刪除'"
        @confirm="delVocabulary()"
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
      searchWord: '',
      delWord: '',
      changeStateSeq: '',
      confirmContent: '',
      tableData: []
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
          title: '詞彙名稱',
          dataIndex: 'title',
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
      //const res = await this.$api.cases.getVocabularySettingHomeData()
      let request = { title: '' }
      const res = await this.$api.cases.queryVocabulary(request)
      this.tableData = res.data.data.grid
    },
    showDelConfirm(seq) {
      this.delWord = seq
      this.$refs.confirmDelete.show()
    },
    async delVocabulary() {
      const res = await this.$api.cases.delVocabulary({ seq: this.delWord })
      this.$refs.confirmDelete.hide()
      this.notifySuccess(res.data.data.msg)
      this.getHomeData()
    },
    async search() {
      let request = { title: this.searchWord }
      const res = await this.$api.cases.queryVocabulary(request)
      this.tableData = res.data.data.grid
    },
    addQusetion(id) {
      this.$router.push('/vocabularysetting/addVocabulary/' + id)
    }
  }
}
</script>
