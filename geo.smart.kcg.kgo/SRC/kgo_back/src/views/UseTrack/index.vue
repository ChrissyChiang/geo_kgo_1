<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                日期
              </label>
              <div class="col-sm-8">
                <base-date
                  v-model="pfDate"
                  placeholder="請選擇查詢日期區間"
                  :is-range="true"
                  :value="pfDate"
                />
              </div>
            </div>
            <table class="col-xs-6 form-group col-md-6">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="button" class="btn btn-fsm" @click="query">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      查詢
                    </button>
                    <button type="submit" class="btn btn-fsm">
                      <i class="fa fsm-icon-arrow-down" aria-hidden="true"></i>
                      匯出EXCEL
                    </button>
                    <button type="button" class="btn btn-fsm">
                      <i class="fa fsm-icon-arrow-down" aria-hidden="true"></i>
                      匯出PDF
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="fsm-form">
          <div class="row">
            <chart-pie
              chart-class="col-xs-6 form-group col-md-6"
              :pie-label="pieLabel"
              :pie-data="pieData"
              :pie-color="pirColor"
            ></chart-pie>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
// import LineChart from '@/plugins/vue-chartjs/lineChart.js'
// import ChartLine from '../../components/vue-chart/ChartLine.vue'

export default {
  components: {
    // LineChart
    // ChartLine
  },
  mixins: [page],

  data() {
    return {
      pfDate: '',
      datacollection: null,
      pieLabel: ['元', '餅', '圖'],
      pieData: [1, 2, 3],
      pieColor: '	#F3F3FA'
    }
  },
  computed: {},
  async mounted() {
    this.fillData()

    // this.getHomeAction()
  },
  methods: {
    query() {
      console.log(this.pfDate)
    },
    fillData() {
      this.datacollection = {
        labels: ['1', '2', '3'],
        datasets: [
          {
            label: 'Data 1',
            backgroundColor: '#f87979',
            data: [2, 5, 2]
          },
          {
            label: 'Data 2',
            backgroundColor: '#33333',
            data: [5, 5, 5]
          },
          {
            label: 'Data 3',
            backgroundColor: '#33333',
            data: [4.5, 4.5, 4.5]
          }
        ]
      }
    },
    getRandomInt() {
      console.log(Math.floor(Math.random() * (50 - 5 + 1)) + 5)
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5
    },

    async getHomeAction() {
      await this.loadingContainer(async () => {
        // const res = await this.$api.management.getCassManagementHomeData()
        // const data = res.data.data
        // this.organNameOptions = data.organComboBox ? data.organComboBox : {}
        // if (
        //   this.organNameOptions.options &&
        //   this.organNameOptions.options.length > 0
        // ) {
        //   this.query.organId = this.organNameOptions.options[0].value
        // }
        // this.unitNameOptions = data.unitComboBox ? data.unitComboBox : {}
        // this.roleOptions = data.caseManagerComboBox
        //   ? data.caseManagerComboBox
        //   : {}
        // this.tableData = data.grid || []
      })
    }
  }
}
</script>
