<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                主分類名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="mainType"
                  :options="mainTypeOptions.options"
                  :select="mainType"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                次分類名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="secondType"
                  :options="secondTypeOptions.options"
                  :select="secondType"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                上下架時間
              </label>
              <div class="col-sm-8">
                <base-date
                  v-model="pfDate"
                  placeholder="請選擇上下架日期區間"
                  :is-range="true"
                  :value="pfDate"
                />
              </div>
            </div>

            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="button" class="btn btn-fsm" @click="search()">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="addClassify(null)"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      分類新增
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
                  :draggable="false"
                  :show-pagination="true"
                >
                  <template v-slot:mainTypeNM="{ data: { mainTypeNM } }">
                    <label v-if="mainTypeNM == '角色'">身份</label>
                    <label v-else>{{ mainTypeNM }}</label>
                  </template>
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
                      @click="addClassify(seq)"
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
        ref="modalConfirm"
        :close-text="'取消'"
        :modal-content="'是否確定刪除資料？'"
        :modal-warn="'確認刪除，系統將執行刪除'"
        @confirm="delType()"
      />
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  components: {},
  mixins: [page],
  data() {
    return {
      pfDate: [],
      tableData: [],
      checkList: [],
      selectData: '',
      confirmContent: '',
      confirmWarn: '',
      changeStateSeq: '',
      changeStateType: '',
      delTypeSeq: '',
      mainType: '',
      secondType: '',
      modalcontent: '',
      modalwarn: '',
      mainTypeOptions: {},
      secondTypeOptions: {}
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '項目',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
        {
          title: '主分類名稱',
          dataIndex: 'mainTypeNM',
          tdClass: 'text-center',
          slot: 'mainTypeNM'
        },
        {
          title: '次分類名稱',
          dataIndex: 'detailNM',
          tdClass: 'text-center'
        },
        {
          title: '狀態',
          dataIndex: 'state',
          slot: status,
          tdClass: 'text-center'
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
  watch: {
    mainType() {
      this.getSubClass()
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getClassifyManagementHomeData()
        //this.mainTypeOptions = res.data.data.mainTypeComboBox
        this.mainTypeOptions = {
          isShow: true,
          isDisabled: false,
          selectedVal: 'Organ',
          options: [
            { label: '機關', value: 'Organ', groupKey: '', selected: false },
            { label: '身份', value: 'Role', groupKey: '', selected: false },
            { label: '服務', value: 'Service', groupKey: '', selected: false }
          ]
        }
        this.secondTypeOptions = res.data.data.subTypeComboBox
        this.tableData = res.data.data.grid
      })
    },
    async search() {
      if (this.pfDate.length != 0) {
        this.pfDate[0] = this.pfDate[0].replace(/\//g, '')
        this.pfDate[1] = this.pfDate[1].replace(/\//g, '')
      }
      let request = {
        mainType: this.mainType,
        name: this.secondType,
        publishTime: this.pfDate
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.queryClassify(request)
        this.tableData = res.data.data.grid
      })
    },
    addClassify(id) {
      this.$router.push('/classifyManagement/addClassify/' + id)
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
    showDelConfirm(seq) {
      this.$refs.modalConfirm.show()
      this.delTypeSeq = seq
    },
    async changeState() {
      let request = { seq: this.changeStateSeq, state: this.changeStateType }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.ShowClassify(request)
        this.$refs.confirmChangeState.hide()
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    },
    async delType() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteClassify({
          seq: this.delTypeSeq
        })
        this.notifySuccess(res.data.data.msg)
      })
      this.$refs.modalConfirm.hide()
      this.getHomeData()
    },
    async getSubClass() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getSubClass({
          mainType: this.mainType
        })
        this.secondTypeOptions = res.data.data.subTypeComboBox
        this.secondType = ''
      })
    }
  }
}
</script>
