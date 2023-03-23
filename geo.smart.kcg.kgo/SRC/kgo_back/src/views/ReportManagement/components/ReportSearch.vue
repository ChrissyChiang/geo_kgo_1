<template>
  <div class="row">
    <div id="divSearch" class="col-xs-8 form-group col-md-8">
      <label for="input_event" class="col-sm-2 control-label">區間日期</label>
      <div class="col-sm-5">
        <base-date
          v-model="pfDate"
          placeholder="請選擇查詢日期區間"
          :is-range="true"
          :value="pfDate"
        />
      </div>
      <label
        v-if="isShowOrgan"
        for="input_event"
        class="col-sm-1 control-label"
      >
        機關
      </label>
      <div v-if="isShowOrgan" class="col-sm-4">
        <base-select
          v-model="organId"
          :options="organOptions"
          required
          search
          :select="organId"
        />
      </div>
    </div>
    <table class="col-xs-4 form-group col-md-4">
      <tbody>
        <tr colspan="4" align="center">
          <td>
            <button type="button" class="btn btn-fsm" @click="search">
              <i class="fa fa-search" aria-hidden="true"></i>
              查詢
            </button>
            <button type="button" class="btn btn-fsm" @click="excel">
              <i class="fa fsm-icon-arrow-down" aria-hidden="true"></i>
              匯出EXCEL
            </button>
            <button type="button" class="btn btn-fsm" @click="pdf">
              <i class="fa fsm-icon-arrow-down" aria-hidden="true"></i>
              匯出PDF
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'ReportSearch',
  mixins: [page],
  props: {
    isShowOrgan: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      pfDate: [],
      organOptions: [],
      organId: this.isAdmin ? '' : this.$store.state.user.userInfo.organId,
      query: {
        startDate: '',
        endDate: ''
      }
    }
  },
  async mounted() {
    const endDate = moment().format('YYYY/MM/DD')
    const startDate = moment()
      .add(-1, 'M')
      .format('YYYY/MM/DD')
    this.pfDate = [startDate, endDate]
    if (this.isShowOrgan) {
      await this.getOrgan()
    }
  },
  methods: {
    async getOrgan() {
      await this.loadingContainer(async () => {
        const resOrgan = await this.$api.share.getOrganSelectHome({
          organId: this.isAdmin ? '' : this.$store.state.user.userInfo.organId
        })
        const data = resOrgan.data.data
        if (data.organComboBox) {
          this.organOptions = this.isAdmin
            ? [{ label: '請選擇', value: '' }, ...data.organComboBox.options]
            : data.organComboBox.options
          this.organId =
            data.organComboBox.selectedVal ||
            this.$store.state.user.userInfo.organId
        }
      })
    },
    search() {
      this.$emit('search', this.getQuery())
    },
    excel() {
      this.$emit('excel', this.getQuery())
    },
    pdf() {
      this.$emit('pdf', this.getQuery())
    },
    getQuery() {
      this.setPfDate()
      if (this.isShowOrgan) {
        return { ...this.query, organId: this.organId }
      } else {
        return this.query
      }
    },
    setPfDate() {
      let start = ''
      let end = ''
      if (this.pfDate.length == 2) {
        start = this.pfDate[0]
        end = this.pfDate[1]
      }
      this.query.startDate = start
      this.query.endDate = end
    }
  }
}
</script>
