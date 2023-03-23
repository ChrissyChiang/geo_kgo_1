<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  選擇機關
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <base-select
                    v-model="organName"
                    :options="organNameOptions.options"
                    search
                    required
                    :select="organName"
                  />
                  <!-- <select
                  v-model="organName"
                  :options="organNameOptions.options"
                  class="form-control"
                >
                  <option
                    v-for="item in organNameOptions.options"
                    :key="item.value"
                    :value="item.value"
                 >
                    {{item.label }}
                 </option>
                </select>             -->
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    選擇科室
                  </label>
                  <div class="col-sm-8">
                    <!-- <select
                  v-model="unitName"
                  :options="unitNameOptions.options"
                  class="form-control"
                >
                  <option
                    v-for="item in unitNameOptions.options"
                    :key="item.value"
                    :value="item.value"
                 >
                    {{item.label }}
                 </option>
                </select> -->
                    <base-select
                      v-model="unitName"
                      :options="unitNameOptions.options"
                      required
                      :select="unitName"
                    />
                    (僅顯示該機關承辦人員清單)
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
                :show-select-by="'check'"
                :show-pagination="true"
              ></app-table>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled ">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="close()"
                      >
                        <i class="fa " aria-hidden="true"></i>
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="decidePeople()"
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
  name: 'ChoosePeople',
  mixins: [page],
  data() {
    return {
      id: '',
      tableData: [],
      selectList: [],
      organName: '',
      organNameOptions: {},
      unitName: '',
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
    organName() {
      this.getUnit()
      this.getTaker()
    },
    unitName() {
      this.getTaker()
    },
    selectList() {
      this.getSelectedName()
    }
  },
  methods: {
    async getHomeData() {
      const res = await this.$api.share.getTaker({ organId: '', unitId: '' })
      let temData = []
      temData = res.data.data.grid
      this.optionDataGrid = temData.map((item, index) => ({
        ...item,
        check: true
      }))
      this.tableData = this.optionDataGrid
      this.organNameOptions = res.data.data.organComboBox
      this.organName = res.data.data.organComboBox.options[0].value
      this.getUnit()
    },
    close() {
      this.$emit('close-modal')
    },
    decidePeople() {
      let nameData = []
      for (let i = 0; i < this.selectList.length; i++) {
        let delIndex = this.tableData.indexOf(
          this.tableData.find(item => item.userId == this.selectList[i])
        )
        nameData.push(this.tableData[delIndex].name)
      }
      this.$emit('decide-people', this.selectList, nameData)
    },
    getSelectedName() {},
    async getUnit() {
      const res = await this.$api.share.getUnit({ organId: this.organName })
      this.unitNameOptions = res.data.data.unitComboBox
      if (this.unitNameOptions.options.length > 0) {
        this.unitName = res.data.data.unitComboBox.options[0].value
      }
    },
    async getTaker() {
      const res = await this.$api.share.getTaker({
        organId: this.organName,
        unitId: this.unitName
      })
      let temData = []
      temData = res.data.data.grid
      if (temData != null) {
        this.optionDataGrid = temData.map((item, index) => ({
          ...item,
          check: true
        }))
        this.tableData = this.optionDataGrid
      } else {
        this.tableData = []
      }
    }
  }
}
</script>
