<template>
  <div :class="chartClass">
    <pie-chart
      v-if="haveData"
      ref="pieChart"
      :width="500"
      :chart-data="datacollection"
      :options="options"
    ></pie-chart>
    <div v-if="!haveData">
      <p class="no-data-title">{{ chartLabel }}</p>
      <p class="no-data">查無資料</p>
    </div>
  </div>
</template>

<script>
import pieChart from '@/plugins/vue-chartjs/pieChart.js'
import { FixedColors } from '@/plugins/vue-chartjs/randomColor.js'
export default {
  name: 'ChartPie',
  components: {
    pieChart
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
    pieLabel: {
      type: Array,
      default: () => []
    },
    pieData: {
      type: Array,
      default: () => []
    },
    pieColor: {
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
        }
      },
      colors: []
    }
  },
  watch: {
    pieData: {
      handler() {
        this.setColors()
        this.fillData()
      },
      deep: true
    },
    pieLabel: {
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
        labels: this.pieLabel,
        datasets: [
          {
            label: 'Data One',
            backgroundColor: FixedColors,
            data: this.pieData
          }
        ]
      }
      this.haveData = this.pieData && this.pieData.length > 0
    },
    setColors() {
      this.colors = []
      const b = Math.floor(Math.random() * 255)
      const randomRedColors = [10, 50, 160, 40, 120, 100, 150, 180, 200, 250]
      const randomGreenColors = [130, 150, 180, 200, 250]
      this.pieData.forEach((item, index) => {
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
.no-data {
  font-size: 30px;
  text-align: center;
  padding: 100px;
}
.no-data-title {
  text-align: center;
  margin-top: 15px;
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
