<template>
  <div :class="chartClass">
    <bar-chart
      v-if="haveData"
      ref="barChart"
      :width="500"
      :chart-data="datacollection"
      :options="options"
    ></bar-chart>
    <div v-if="!haveData">
      <p class="no-data-title">{{ chartLabel }}</p>
      <p class="no-data">查無資料</p>
    </div>
  </div>
</template>

<script>
import barChart from '@/plugins/vue-chartjs/barChart.js'
import { FixedColors } from '@/plugins/vue-chartjs/randomColor.js'
export default {
  name: 'ChartBar',
  components: {
    barChart
  },
  props: {
    /** chart標題名稱 */
    chartLabel: {
      type: String,
      default: ''
    },
    chartClass: {
      type: String,
      default: 'small'
    },
    barLabel: {
      type: Array,
      default: () => []
    },
    barData: {
      type: Array,
      default: () => []
    },
    barColor: {
      type: String,
      default: () => ''
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
        legend: {
          display: false
        },
        plugins: {
          datalabels: {
            color: '#ffffff',
            display: true,
            // align: 'bottom',
            backgroundColor: '#e4d6d68a',
            borderRadius: 3,
            font: {
              //weight: 'bold',
              size: 18
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
      },
      colors: []
    }
  },
  watch: {
    barData: {
      handler() {
        this.setColors()
        this.fillData()
      },
      deep: true
    },
    barLabel: {
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
    fillData() {
      this.datacollection = {
        labels: this.barLabel,
        datasets: [
          {
            label: '',
            backgroundColor: FixedColors,
            data: this.barData
          }
        ]
      }
      this.haveData = this.barData && this.barData.length > 0
    },
    setColors() {
      this.colors = []
      const b = Math.floor(Math.random() * 255)
      const randomRedColors = [10, 50, 160, 40, 120, 100, 150, 180, 200, 250]
      const randomGreenColors = [130, 150, 180, 200, 250]
      this.barData.forEach((item, index) => {
        const ramRedIndex = Math.floor(Math.random() * 10)
        const ramGreenIndex = Math.floor(Math.random() * 5)
        this.colors.push(
          'rgb(' +
            randomRedColors[ramRedIndex] +
            ',' +
            randomGreenColors[ramGreenIndex] +
            ',' +
            b +
            ')'
        )
      })
    }
  }
}
</script>

<style>
.small {
  max-width: 600px;
  margin: 150px auto;
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
