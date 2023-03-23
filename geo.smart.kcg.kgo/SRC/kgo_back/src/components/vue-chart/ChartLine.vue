<template>
  <div :class="chartClass">
    <line-chart
      v-if="haveData"
      :chart-data="datacollection"
      :width="500"
      :options="options"
    ></line-chart>
    <div v-if="!haveData">
      <p class="no-data-title">{{ chartLabel }}</p>
      <p class="no-data">查無資料</p>
    </div>
  </div>
</template>

<script>
import LineChart from '@/plugins/vue-chartjs/lineChart.js'

export default {
  name: 'ChartLine',
  components: {
    LineChart
  },
  props: {
    /** chart標題名稱 */
    chartLabel: {
      type: String,
      default: ''
    },
    chartClass: {
      type: String,
      default: ''
    },
    titleLabel1: {
      type: String,
      default: ''
    },
    titleLabel2: {
      type: String,
      default: ''
    },
    lineLabel: {
      type: Array,
      default: () => []
    },
    line1Data: {
      type: Array,
      default: () => []
    },
    line2Data: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      haveData: true,
      datacollection: {},
      options: {
        responsive: true,
        title: {
          display: true,
          fontSize: 18,
          text: this.chartLabel
        },
        plugins: {
          datalabels: {
            display: true,
            // align: 'bottom',
            // backgroundColor: '#ccc',
            // borderRadius: 3,
            font: {
              size: 16
            }
          }
        },
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                // 把y軸數值整數化
                userCallback: function(label, index, labels) {
                  if (Math.floor(label) === label) {
                    return label
                  }
                }
              }
            }
          ]
        }
      }
    }
  },
  watch: {
    chartLabel() {
      this.options.title.text = this.chartLabel
    },
    line1Data: {
      handler() {
        this.fillData()
      },
      deep: true
    },
    line2Data: {
      handler() {
        this.fillData()
      },
      deep: true
    }
  },
  mounted() {
    this.fillData()
  },
  methods: {
    dynamicColors() {
      const r = Math.floor(Math.random() * 255)
      const g = Math.floor(Math.random() * 255)
      const b = Math.floor(Math.random() * 255)
      return 'rgb(' + r + ',' + g + ',' + b + ')'
    },
    fillData() {
      this.datacollection = {
        labels: this.lineLabel,
        datasets: [
          {
            label: this.titleLabel1,
            borderColor: this.dynamicColors(),
            data: this.line1Data,
            borderWidth: 2,
            fill: false
          },
          {
            label: this.titleLabel2,
            borderColor: this.dynamicColors(),
            borderWidth: 2,
            data: this.line2Data,
            fill: false
          }
        ]
      }
      this.haveData =
        (this.line1Data && this.line1Data.length > 0) ||
        (this.line2Data && this.line2Data.length) > 0
    }
  }
}
</script>

<style>
.small {
  max-width: 600px;
  margin: 150px auto;
}
.no-data {
  font-size: 30px;
  text-align: center;
  padding: 100px;
}
.no-data-title {
  text-align: center;
  font-size: 18px;
  color: #666666;
  font-weight: bold;
}
.no-data {
  font-size: 30px;
  text-align: center;
  padding: 100px;
}
</style>
