<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                機關名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="organName"
                  :options="organOptions"
                  :select="organName"
                  track-by="value"
                  search
                  @input="getOrganId"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                科室名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="unitName"
                  track-by="value"
                  label="label"
                  placeholder="請輸入關鍵字"
                  search
                  :options="unitOptions"
                ></base-select>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                機關代碼
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="organId"
                  :placeholder="'請輸入機關代碼'"
                  :input-length="'50'"
                  @blur="getorganName()"
                ></base-input>
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
                      @click="addOrganUnit('organ', null)"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      機關新增
                    </button>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="addOrganUnit('unit', null)"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      科室新增
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
                  <template v-slot:operating="{ data: { organId, unitId } }">
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="checkId(organId, unitId)"
                    >
                      <i class="fa fa-cog" aria-hidden="true"></i>
                      編輯
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
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
      tableData: [],
      organName: '',
      organOptions: [],
      unitName: '',
      unitOptions: [],
      organId: ''
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
          title: '機關名稱',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '科室名稱',
          dataIndex: 'unitName',
          tdClass: 'text-center'
        },
        {
          title: '機關代碼',
          dataIndex: 'organId',
          slot: status,
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
  mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getOrganUnitManagementHomeData()
        this.tableData = res.data.data.grid
        this.unitOptions = res.data.data.unitComboBox.options
        this.organOptions = res.data.data.organComboBox.options
        this.organName = res.data.data.organComboBox.selectedVal
        this.organId = res.data.data.organComboBox.selectedVal
      })
    },
    async getUnit() {
      await this.loadingContainer(async () => {
        const res = await this.$api.share.getUnit({ organId: this.organName })
        this.unitOptions = res.data.data.unitComboBox.options
      })
    },
    checkId(organId, unitId) {
      if (unitId == '') {
        this.addOrganUnit('organ', organId)
      } else {
        this.addOrganUnit('unit', unitId)
      }
    },
    addOrganUnit(type, id) {
      this.$router.push('/organUnitManagement/addOrganUnit/' + type + '-' + id)
      //this.$router.push(`organUnitManagement/addOrganUnit/${id}/${type}`)
    },
    async getOrganId() {
      this.organId = this.organName
      await this.loadingContainer(async () => {
        const res = await this.$api.share.getUnit({ organId: this.organName })
        this.unitOptions = res.data.data.unitComboBox.options
      })
      this.unitName = ''
    },
    getorganName() {
      let organ = this.organOptions.filter(item => item.value == this.organId)
      if (organ.length > 0) {
        this.organName = ''
      } else {
        this.organName = []
        this.unitName = ''
      }
    },
    async search() {
      let request = {
        organId: this.organName,
        unitId: this.unitName == null ? '' : this.unitName
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.queryOrganUnit(request)
        this.tableData = res.data.data.grid
      })
    }
  }
}
</script>
