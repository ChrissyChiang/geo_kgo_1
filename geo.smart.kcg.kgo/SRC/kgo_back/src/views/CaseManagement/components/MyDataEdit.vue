<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                來源
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="sTypeComboBox.selectedVal"
                  :options="sTypeComboBox.options"
                  required
                  :select="sTypeComboBox.selectedVal"
                />
              </div>
            </div>
          </div>
          <div v-if="sTypeComboBox.selectedVal == 'O'" class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                服務
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="serviceComboBox.selectedVal"
                  :options="serviceComboBox.options"
                  required
                  :select="serviceComboBox.selectedVal"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                資料集
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="myDataSetComboBox.selectedVal"
                  :options="myDataSetComboBox.options"
                  required
                  :select="myDataSetComboBox.selectedVal"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <button type="button" class="btn-line btn-warning" @click="add">
                <i class="fa fa-cog" aria-hidden="true"></i>
                新增
              </button>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="false"
                  :show-pagination="false"
                >
                  <template
                    v-slot:operating="{ data: { myDataId, canDelete } }"
                  >
                    <button
                      v-if="canDelete"
                      type="button"
                      class="btn-line btn-danger"
                      @click="delMyDataConfirm(myDataId)"
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
        <div class="fsm-form">
          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button
                    type="button"
                    class="btn btn-fsm"
                    data-dismiss="modal"
                  >
                    <i class="fa" aria-hidden="true"></i>
                    關閉視窗
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <modal-confirm
      ref="confirmMyDataDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delMyData"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'MyDataEdit',
  mixins: [page],
  data() {
    return {
      tableData: [],
      delMydataId: null,
      /** MYDATA資料集合名稱下拉式選單 */
      myDataSetComboBox: {
        selectedVal: '',
        options: []
      },
      /** 來源下拉式選單 */
      sTypeComboBox: {
        selectedVal: '',
        options: []
      },
      /** 服務下拉式選 */
      serviceComboBox: {
        selectedVal: '',
        options: []
      },
      /** 案件代碼 */
      caseSetId: this.$route.params.id || ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '類別',
          dataIndex: 'sTypeName',
          tdClass: 'text-center'
        },
        {
          title: '資料集',
          dataIndex: 'myDataName',
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
    'sTypeComboBox.selectedVal': {
      async handler() {
        await this.getMydataComboBox()
      }
    },
    'serviceComboBox.selectedVal': {
      async handler() {
        await this.getMydataComboBox()
      }
    }
  },
  async mounted() {
    await this.loadingContainer(async () => {
      await this.getHomeData()
    })
  },
  methods: {
    async getHomeData() {
      const res = await this.$api.management.getInternetApplyFormSetMyDataHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.tableData = data.grid || []
      if (data.myDataSetComboBox) {
        this.myDataSetComboBox = data.myDataSetComboBox
      }

      if (data.sTypeComboBox) {
        this.sTypeComboBox = data.sTypeComboBox
      }

      if (data.serviceComboBox) {
        this.serviceComboBox = data.serviceComboBox
      }
    },
    async queryData() {
      const res = await this.$api.management.getInternetApplyFormSetMyDataHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.tableData = data.grid || []
    },
    async getMydataComboBox() {
      const res = await this.$api.management.getMydataComboBox({
        clientId:
          this.sTypeComboBox.selectedVal == 'O'
            ? this.serviceComboBox.selectedVal
            : ''
      })
      const data = res.data.data
      if (data.mydataComboBox) {
        this.myDataSetComboBox = data.mydataComboBox
      }
    },
    delMyDataConfirm(myDataId) {
      this.delMydataId = myDataId
      this.$refs.confirmMyDataDelete.show()
    },
    async delMyData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteInternetApplyFormSetMyData(
          {
            caseSetId: this.caseSetId,
            mydataId: this.delMydataId
          }
        )
        this.$refs.confirmMyDataDelete.hide()
        await this.queryData()
      })
    },
    async add() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.saveInternetApplyFormSetMyDataHome(
          {
            caseSetId: this.caseSetId,
            mydataId: this.myDataSetComboBox.selectedVal,
            // 市府的clientId:KAPI
            clientId:
              this.sTypeComboBox.selectedVal == 'O'
                ? this.serviceComboBox.selectedVal
                : 'KAPI.Disability'
          }
        )
        await this.queryData()
      })
    },
    clear() {
      // this.myDataSetComboBox.options = []
      // this.myDataSetComboBox.selectedVal = ''
    }
  }
}
</script>
