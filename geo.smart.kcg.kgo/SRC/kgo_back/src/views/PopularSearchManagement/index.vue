<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4">
                  <td align="right" style="padding:0px">
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="saveOrder()"
                    >
                      <i class="fa fa-save" aria-hidden="true"></i>
                      儲存
                    </button>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="addSearch()"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      熱門搜尋新增
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12">
              <label style="padding-left:5px;font-size:20px">設定順序</label>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="true"
                  :show-pagination="true"
                >
                  <template v-slot:keyword="{ data: { keyword, showEdit } }">
                    <label v-show="showEdit == '1'">{{ keyword }}</label>
                    <input
                      v-show="showEdit != '1'"
                      v-model="editKeyWord"
                      input-length="200"
                    />
                  </template>
                  <template v-slot:operating="{ data: { seq, showEdit } }">
                    <button
                      v-show="showEdit == '1'"
                      type="button"
                      class="btn-line btn-warning"
                      @click="edit(seq)"
                    >
                      <i class="fa fa-cog" aria-hidden="true"></i>
                      編輯
                    </button>
                    <button
                      v-show="showEdit == '2'"
                      type="button"
                      class="btn-line btn-danger"
                      @click="showModalConfirm(seq)"
                    >
                      <i class="fa fa-trash-o" aria-hidden="true"></i>
                      刪除
                    </button>
                    <button
                      v-show="showEdit != '1'"
                      type="button"
                      class="btn btn-fsm"
                      @click="save(seq)"
                    >
                      <i class="fa fa-save" aria-hidden="true"></i>
                      儲存
                    </button>
                    <button
                      v-show="showEdit != '1'"
                      type="button"
                      class="btn btn-fsm"
                      @click="cancel(seq)"
                    >
                      <i class="fa" aria-hidden="true"></i>
                      取消
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
            <div class="col-md-12">
              <label style="padding-left:5px;font-size:20px">每月前10名</label>
              <div class="table-responsive">
                <app-table
                  :columns="top10TableColumns"
                  :data="top10TableData"
                  :draggable="false"
                  :show-pagination="true"
                ></app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <modal-confirm
      ref="modalConfirm"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delType()"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      tableData: [],
      top10TableData: [],
      organName: null,
      organOptions: [],
      delSeq: '',
      editKeyWord: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '設定順序',
          dataIndex: 'order',
          tdClass: 'text-center w-20',
          slot: 'order'
        },
        {
          title: '關鍵字',
          tdClass: 'text-center w-30',
          slot: 'keyword'
        },
        {
          title: '維護',
          tdClass: 'text-center w-40',
          slot: 'operating'
        }
      ]
    },
    top10TableColumns() {
      return [
        {
          title: '項次',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
        {
          title: '關鍵字',
          dataIndex: 'keyword',
          tdClass: 'text-center'
        },
        {
          title: '數量統計(以該月份)',
          dataIndex: 'statistics',
          tdClass: 'text-center'
        }
      ]
    }
  },
  mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getPopularSearchManagementHomeData()
        this.tableData = res.data.data.popularSearchGrid
        this.top10TableData = res.data.data.top10PopularSearchGrid
      })
    },
    edit(seq) {
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].seq == seq) {
          this.tableData[i].showEdit = '2'
          this.editKeyWord = this.tableData[i].keyword
        } else {
          this.tableData[i].showEdit = '1'
        }
      }
    },
    cancel(seq) {
      let index = this.tableData.indexOf(
        this.tableData.find(item => item.seq == seq)
      )
      if (seq == '') {
        this.tableData.splice(index, 1)
      } else {
        this.tableData[index].showEdit = '1'
      }
    },
    showModalConfirm(seq) {
      this.$refs.modalConfirm.show()
      this.delSeq = seq
    },
    async delType() {
      this.$refs.modalConfirm.hide()
      await this.loadingContainer(async () => {
        const res = await this.$api.management.delPopularSearch({
          seq: this.delSeq
        })
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    },
    async save(seq) {
      let request = { seq: seq, keyword: this.editKeyWord }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.savePopularSearch(request)
      })
      this.getHomeData()
    },
    addSearch() {
      if (this.tableData.length >= 6) {
        this.notifySuccess('已超過最大設定筆數，請先刪除資料')
        return
      } else {
        for (let m = 0; m < this.tableData.length; m++) {
          if (this.tableData[m].seq == '') {
            return
          }
        }
      }
      for (let i = 0; i < this.tableData.length; i++) {
        this.tableData[i].showEdit = '1'
      }
      this.editKeyWord = ''
      this.tableData.push({ seq: '', keyword: '', showEdit: '3' })
    },
    async saveOrder() {
      let popularSearch = []
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].seq != '') {
          popularSearch.push({
            keyword: this.tableData[i].keyword,
            orderNum: i + 1
          })
        }
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.savePopularSearchOrder({
          grid: popularSearch
        })
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    }
  }
}
</script>
