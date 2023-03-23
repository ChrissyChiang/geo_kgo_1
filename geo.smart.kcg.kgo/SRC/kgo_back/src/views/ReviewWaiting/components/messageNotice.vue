<template>
  <div>
    <div>
      <validation-observer ref="observer">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <table class="table table-form">
                <tr>
                  <th class="col-xs-6 form-group col-md-2">常用詞庫</th>
                  <td class="col-xs-6 form-group col-md-4">
                    <base-select
                      v-model="vocabulary"
                      :options="vocabularyOptions"
                      search
                      required
                      :select="vocabulary"
                      :label="'title'"
                      :track-by="'seq'"
                    />
                  </td>
                  <td>
                    <button
                      class="btn btn-fsm"
                      type="button"
                      @click="getVocabularyContent"
                    >
                      <i class="fa fa-location-arrow" aria-hidden="true"></i>
                      載入辭庫範本
                    </button>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                填寫內容
              </label>
              <div class="col-sm-8">
                <validate-container :rules="'required'">
                  <base-textarea
                    v-model="content"
                    :row="10"
                    input-length="500"
                  ></base-textarea>
                </validate-container>
              </div>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button
                    class="btn-line btn-danger"
                    type="button"
                    @click="sendMsg"
                  >
                    <i class="fa fa-send" aria-hidden="true"></i>
                    訊息發送
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="fsm-form">
          <app-table
            :table-class="'table'"
            :columns="historyTableColumns"
            :data="dataObject.caseHandlePendingReviewNotifyHistoryDataGrids"
            :show-pagination="false"
          ></app-table>
        </div>
      </validation-observer>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'MessageNotice',
  mixins: [page],
  props: {
    dataObject: {
      type: Object,
      default: () => ({})
    },
    caseId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      downloadTableData: [],
      vocabulary: '',
      vocabularyOptions: [],
      content: ''
    }
  },
  computed: {
    historyTableColumns() {
      return [
        //處理歷程欄位
        {
          title: '案件狀態',
          dataIndex: 'status',
          tdClass: 'text-center'
        },
        {
          title: '處理人',
          dataIndex: 'handler',
          tdClass: 'text-center'
        },
        {
          title: '訊息內容',
          dataIndex: 'memo',
          tdClass: 'text-center  w-40'
        },
        {
          title: '處理時間',
          dataIndex: 'handleTime',
          tdClass: 'text-center'
        }
      ]
    }
  },
  watch: {
    dataObject() {
      let date = ''
      if (
        this.dataObject.caseHandlePendingReviewNotifyHistoryDataGrids !==
        undefined
      ) {
        this.dataObject.caseHandlePendingReviewNotifyHistoryDataGrids.forEach(
          item => {
            if (item.status == 'C3') {
              item.status = '補正中'
            } else if (item.status == 'P3' || item.status == 'P') {
              item.status = '待處理'
            }
            let date = new Date(item.handleTime)
            item.handleTime =
              date.getFullYear() +
              '/' +
              (date.getMonth() + 1).toString().padStart(2, '0') +
              '/' +
              date
                .getDate()
                .toString()
                .padStart(2, '0') +
              ' ' +
              date
                .getHours()
                .toString()
                .padStart(2, '0') +
              ':' +
              date
                .getMinutes()
                .toString()
                .padStart(2, '0') +
              ':' +
              date
                .getSeconds()
                .toString()
                .padStart(2, '0')
          }
        )
      }
    }
  },
  mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getVocabularySettingHomeData()
        this.vocabularyOptions = res.data.data.grid
      })
    },
    async getVocabularyContent() {
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getEditVocabularySettingHomeData({
          seq: this.vocabulary
        })
        this.content = res.data.data.word
      })
    },
    async sendMsg() {
      let msg = ''
      let rtnCode = ''
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = { caseId: this.caseId, content: this.content }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getReviewWaitingSignSendingMessage(
          request
        )
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('已發送訊息！')
      }
      this.goBack()
    },
    goBack() {
      this.$router.push(`/reviewWaiting`)
    }
  }
}
</script>
