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
            <chart-pie
              chart-class="col-xs-6 form-group col-md-6"
              :chart-label="'區間身份驗證方式統計'"
              :pie-label="loginTypeLabel"
              :pie-data="loginTypeData"
              :pie-color="loginTypeColor"
            ></chart-pie>
            <chart-line
              :chart-label="'區間每日登入/登出次數統計'"
              :title-label1="'登入'"
              :title-label2="'登出'"
              :line1-data="loginData"
              :line2-data="logoutData"
              :line-label="loginLogoutLabel"
              chart-class="col-xs-6 form-group col-md-6"
            />
            <chart-bar
              chart-class="col-xs-6 form-group col-md-6"
              style="margin-top: 30px"
              :chart-label="'區間本府員工登入IP前十名'"
              :bar-label="top10IpLabel"
              :bar-data="top10IpData"
              :bar-color="top10IpColor"
            ></chart-bar>
            <chart-pie
              chart-class="col-xs-6 form-group col-md-6"
              style="margin-top: 30px"
              :chart-label="'區間功能使用前十名統計'"
              :pie-label="top10FuncOrServiceCountLabel"
              :pie-data="top10FuncOrServiceCountData"
              :pie-color="top10FuncOrServiceCountColor"
            ></chart-pie>
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
      pfDate: '',
      loginTypeLabel: [],
      loginTypeData: [],
      loginTypeColor: '#2183e4',

      loginLogoutLabel: [],
      loginData: [],
      logoutData: [],

      top10IpLabel: [],
      top10IpData: [],
      top10IpColor: '#299e1b',

      top10FuncOrServiceCountLabel: [],
      top10FuncOrServiceCountData: [],
      top10FuncOrServiceCountColor: '#1b379e'
    }
  },
  computed: {},
  async mounted() {
    this.$nextTick(() => {
      this.search({ ...this.$refs.search.getQuery(), systemType: 'B' })
    })
  },
  methods: {
    async search(query) {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.queryLog({
          ...query,
          systemType: 'B'
        })
        const data = res.data.data
        this.loginTypeLabel = data.loginTypeCountList
          ? data.loginTypeCountList.map(x => x.loginType)
          : []
        this.loginTypeData = data.loginTypeCountList
          ? data.loginTypeCountList.map(x => x.count)
          : []

        const loginLabel = data.loginLogoutData.loginCountList
          ? data.loginLogoutData.loginCountList.map(x => x.logDate)
          : []
        const logoutLabel = data.loginLogoutData.logoutCountList
          ? data.loginLogoutData.logoutCountList.map(x => x.logDate)
          : []
        this.loginLogoutLabel = this.uniqueArray(loginLabel.concat(logoutLabel))
        this.loginData = this.getTotalValue(
          this.loginLogoutLabel,
          data.loginLogoutData.loginCountList || []
        )
        this.logoutData = this.getTotalValue(
          this.loginLogoutLabel,
          data.loginLogoutData.logoutCountList || []
        )

        this.top10IpLabel = data.top10IpCountList.map(x => x.ip)
        this.top10IpData = data.top10IpCountList.map(x => x.count)

        this.top10FuncOrServiceCountLabel = data.top10FuncOrServiceCountList.map(
          x => x.name
        )
        this.top10FuncOrServiceCountData = data.top10FuncOrServiceCountList.map(
          x => x.count
        )
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
        const findData = listData.find(x => x.logDate == label)
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

      PDF.addImage(pageData, 'JPEG', -35, 105, 630, 530)
      PDF.save('後台使用軌跡統計.pdf')
    },
    async excel(query) {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.exportLogExcel({
          ...query,
          systemType: 'B'
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
