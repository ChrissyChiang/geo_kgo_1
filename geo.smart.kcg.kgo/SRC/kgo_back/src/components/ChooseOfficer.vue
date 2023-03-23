<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-12 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  選擇機關
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <base-select
                    v-model="organId"
                    :options="organNameOptions.options"
                    search
                    required
                    :select="organId"
                  />
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-2 control-label">
                    選擇科室
                  </label>
                  <div class="col-sm-8">
                    <base-select
                      v-model="unitId"
                      :options="unitNameOptions.options"
                      search
                      :select="unitId"
                    />
                    (僅顯示該機關承辦人員清單)
                  </div>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-2 control-label">
                    承辦人姓名
                  </label>
                  <div class="col-sm-8">
                    <input
                      v-model="keyWord"
                      class="form-control"
                      input-length="200"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <app-table
                :table-class="'table'"
                :columns="tableColumns"
                :data="tableData"
                :select-by="'userId'"
                :selected.sync="selectList"
                :show-pagination="true"
              ></app-table>
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
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="decidePeople"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        儲存
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </validation-observer>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'ChooseOfficer',
  mixins: [page],
  props: {
    /** 是否只撈案件管理者 */
    isCaseM: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      id: '',
      keyWord: '',
      tableData: [],
      tempTableData: [],
      selectList: [],
      organId: '',
      organNameOptions: {},
      unitId: '',
      unitNameOptions: {}
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '科室',
          dataIndex: 'unitName',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'name',
          tdClass: 'text-center'
        }
      ]
    }
  },
  watch: {
    async organId(val) {
      if (this.organId != '') {
        await this.loadingContainer(async () => {
          await this.getUnit()
          this.getTaker()
          this.selectList = []
        })
        this.unitId = ''
      }
    },
    async unitId(val) {
      await this.loadingContainer(async () => {
        this.getTaker()
      })
    },
    keyWord() {
      this.tableData = this.tempTableData.filter(x =>
        x.name.includes(this.keyWord)
      )
      this.selectList = []
    }
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const resOrgan = await this.$api.share.getOrganSelectHome({
          organId: ''
        })
        const data = resOrgan.data.data
        // const res = await this.$api.share.getTaker({ organId: '', unitId: '' })
        // this.organNameOptions = res.data.data.organComboBox
        // this.organId = res.data.data.organComboBox.options[0].value
        this.organNameOptions = data.organComboBox
        this.organId = data.organComboBox.options[0].value
        this.getUnit()
      })
    },
    decidePeople() {
      const selectedTable = this.tableData.filter(x =>
        this.selectList.includes(x.userId)
      )
      const findData = this.organNameOptions.options.find(
        x => x.value == this.organId
      )
      const findName = findData ? findData.label : ''
      this.$emit('decide-people', {
        selectedTable,
        selectedOrganId: this.organId,
        selectedOrganName: findName
      })
    },
    async getUnit() {
      const res = await this.$api.share.getUnit({ organId: this.organId })
      this.unitNameOptions = res.data.data.unitComboBox
    },
    clear() {
      this.selectList = []
      this.tableData = []
      this.tempTableData = []
      this.unitId = ''
      this.organId = ''
      this.keyWord = ''
      this.organNameOptions = ''
      this.unitNameOptions = ''
    },
    async getTaker() {
      const res = await this.$api.share.getOrganUintUserSelectHome({
        organId: this.organId,
        unitId: this.unitId || '',
        //unitId: this.unitId || this.organId,
        roleId: this.isCaseM ? 'CASE_M' : ''
      })
      const data = res.data.data
      this.tableData = data.grid || []
      this.tempTableData = [...this.tableData]
      // let temData = []
      // temData = res.data.data.grid
      // if (temData != null) {
      //   this.optionDataGrid = temData.map((item, index) => ({
      //     ...item,
      //     check: true
      //   }))
      //   this.tableData = this.optionDataGrid
      //   this.tempTableData = this.tableData
      //   this.selectList = []
      // } else {
      //   this.tableData = []
      //   this.tempTableData = []
      //   this.selectList = []
      // }
    }
  }
}
</script>
