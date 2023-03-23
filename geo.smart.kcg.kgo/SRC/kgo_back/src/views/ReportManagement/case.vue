<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <report-search
            ref="search"
            @search="search"
            @excel="excel"
            @pdf="pdf"
          />
        </div>
        <div class="fsm-form">
          <div id="chart-container" class="row">
            <chart-line
              :chart-label="'區間前兩名每日進案數量統計'"
              :title-label1="caseSetName1"
              :title-label2="caseSetName2"
              :line1-data="entryCaseTop1Data"
              :line2-data="entryCaseTop2Data"
              :line-label="entryCaseLabel"
              chart-class="col-xs-6 form-group col-md-6"
            />
            <chart-pie
              chart-class="col-xs-6 form-group col-md-6"
              :chart-label="'區間前十名承辦人處理案件數'"
              :pie-label="casesAccLabel"
              :pie-data="casesAccData"
              :pie-color="casesAccColor"
            ></chart-pie>

            <chart-pie
              chart-class="col-xs-6 form-group col-md-6"
              style="margin-top: 30px"
              :chart-label="'區間案件狀態統計分析'"
              :pie-label="caseStatusLabel"
              :pie-data="caseStatusData"
              :pie-color="caseStatusColor"
            ></chart-pie>
            <chart-bar
              chart-class="col-xs-6 form-group col-md-6"
              :chart-label="'區間每日結案數量統計'"
              :bar-label="finishCountLabel"
              :bar-data="finishCountData"
              :bar-color="finishCountColor"
            ></chart-bar>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
import ReportSearch from './components/ReportSearch'
import moment from 'moment'
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
export default {
  components: {
    ReportSearch
  },
  mixins: [page],
  data() {
    return {
      casesAccLabel: [],
      casesAccData: [],
      casesAccColor: '#2183e4',

      caseSetName1: '',
      caseSetName2: '',
      entryCaseLabel: [],
      entryCaseTop1Data: [],
      entryCaseTop2Data: [],

      caseStatusLabel: [],
      caseStatusData: [],
      caseStatusColor: '#299e1b',

      finishCountLabel: [],
      finishCountData: [],
      finishCountColor: '#1b379e'
    }
  },
  computed: {},
  async mounted() {
    this.$nextTick(() => {
      this.search({ ...this.$refs.search.getQuery() })
    })
  },
  methods: {
    async search(query) {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.queryCaseLog({
          ...query
        })
        const data = res.data.data

        // 進案時間
        this.caseSetName1 =
          data.entryCase.entryCaseTop1CountList &&
          data.entryCase.entryCaseTop1CountList.length > 0
            ? data.entryCase.entryCaseTop1CountList[0].caseSetName
            : ''
        this.caseSetName2 =
          data.entryCase.entryCaseTop2CountList &&
          data.entryCase.entryCaseTop2CountList.length > 0
            ? data.entryCase.entryCaseTop2CountList[0].caseSetName
            : ''

        const entryCaseTop1Label = data.entryCase.entryCaseTop1CountList
          ? data.entryCase.entryCaseTop1CountList.map(x => x.date)
          : []
        const entryCaseTop2Label = data.entryCase.entryCaseTop2CountList
          ? data.entryCase.entryCaseTop2CountList.map(x => x.date)
          : []
        this.entryCaseLabel = this.uniqueArray(
          entryCaseTop1Label.concat(entryCaseTop2Label)
        )

        this.entryCaseTop1Data = this.getTotalValue(
          this.entryCaseLabel,
          data.entryCase.entryCaseTop1CountList || []
        )
        this.entryCaseTop2Data = this.getTotalValue(
          this.entryCaseLabel,
          data.entryCase.entryCaseTop2CountList || []
        )

        // 承辦人受理案件數
        this.casesAccLabel = data.casesAccCountList.map(x => x.caseSetName)
        this.casesAccData = data.casesAccCountList.map(x => x.count)

        // 案件狀態統計分析
        this.caseStatusLabel = data.casesStatusCountList.map(x => x.caseStatus)
        this.caseStatusData = data.casesStatusCountList.map(x => x.count)

        // 區間每日結案數量統計
        this.finishCountLabel = data.finishCountList.map(x => x.finishDate)
        this.finishCountData = data.finishCountList.map(x => x.count)
      })
    },
    uniqueArray(_a) {
      let a = _a.concat()
      for (let i = 0; i < a.length; ++i) {
        for (let j = i + 1; j < a.length; ++j) {
          if (a[i] === a[j]) a.splice(j, 1)
        }
      }

      return a
    },
    getTotalValue(allLabelKeys, listData) {
      let counts = []
      allLabelKeys.forEach(label => {
        const findData = listData.find(x => x.date == label)
        counts.push(findData ? findData.count : 0)
      })
      return counts
    },
    async pdf(query) {
      let PDF = new JsPDF('p', 'pt')
      let node = document.getElementById('chart-container')
      let canvas = await html2Canvas(node, {
        useCORS: true,
        scale: 2
      })
      let searchNode = document.getElementById('divSearch')
      let searchCanvas = await html2Canvas(searchNode, {
        useCORS: true,
        scale: 2
      })
      let searchData = searchCanvas.toDataURL('image/jpeg', 1.0)
      PDF.addImage(searchData, 'JPEG', 10, 15, 620, 22)

      let pageData = canvas.toDataURL('image/jpeg', 1.0)

      PDF.addImage(pageData, 'JPEG', -25, 105, 630, 530)
      PDF.save('案件軌跡統計.pdf')
    },
    async excel(query) {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.exportCaseLogExcel({
          ...query
        })
        this.$f.toExcel(res, 'documentFile.pdf')
      })
    }
  }
}
</script>
<style lang="scss" scoped>
#chart-container {
  background: white;
}
</style>
