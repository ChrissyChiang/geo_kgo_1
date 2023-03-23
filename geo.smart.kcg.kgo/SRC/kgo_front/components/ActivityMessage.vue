<template>
  <div class="wrap">
    <div class="news-wrap">
      <h3>
        <img src="/img/icon-5-title.svg" alt />
        活動訊息
      </h3>
      <ul>
        <li v-for="item in messageList" :key="item.seq">
          <a
            href="#newsModal"
            data-toggle="modal"
            @click="popupDetail(item.activitySeq,item.functionCode)"
          >
            <span style="width: 330px">{{ item.activityName }}</span>
            <time style="width: 170px">{{ item.activityDate }}</time>
          </a>
        </li>
      </ul>
    </div>
    <hr />
    <!--modal start-->
    <div
      class="modal fade"
      id="newsModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="newsModalTitle"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content p-3">
          <div class="modal-header">
            <div class="modal-title" id="newsModalTitle">{{ title }}</div>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
              title="關閉活動訊息"
            >
              <i class="fa fa-times fa-lg"></i>
            </button>
          </div>
          <div>
            <p v-if="date != ''">活動日期：{{ date }}</p>
            <p v-if="caseName != ''">服務案件：{{ caseName }}</p>
            <p v-html="contentHtml"></p>
            <div v-if="fileList!== undefined">
              <div
                v-for="(fileItem, index) in fileList"
                :key="index"
                style="display: flex; width: 450px"
              >
                <a
                  style="cursor: pointer; text-decoration: underline;color:blue"
                  @click="downloadFile(fileItem.key,fileItem.name)"
                >{{ fileItem.name }}</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--modal end-->
  </div>
</template>
<script>
import page from '~/mixins/page.js'
import { saveAs } from 'file-saver'
export default {
  layout: 'activityMessage',
  mixins: [page],
  data() {
    return {
      title: '',
      date: '',
      caseName: '',
      contentHtml: '',
      fileList: [],
      seq: '',
      functionCode: ''
    }
  },
  props: {
    messageList: {
      type: [String, Number, Array],
      default: () => []
    }
  },
  methods: {
    async popupDetail(seq, functionCode) {
      this.title = ''
      this.date = ''
      this.caseName = ''
      this.contentHtml = ''
      this.seq = seq
      this.functionCode = functionCode
      
      const res = await this.$apiHome.getActivityMessageDetail({
        activitySeq: seq,
        functionCode: functionCode
      })
      this.$nuxt.$loading.start()
      setTimeout(() => this.$nuxt.$loading.finish(), 1000)

      this.title = res.data.activityName
      this.date = res.data.activityDate
      this.caseName = res.data.caseSetName
      this.contentHtml = res.data.contentHtml
      this.fileList = res.data.files
    },
    async downloadFile(key, name) {
      let request = {
        fileKey: key,
        functionCode: this.functionCode,
        releaseObject: 'F',
        seq: this.seq
      }
      const res = await this.$apiHome.getActivityMessageFileDownload(request)
      if (name.toLocaleLowerCase().indexOf('pdf') >=0) {
        this.toPdf(res,name)
      } else if (name.toLocaleLowerCase().indexOf('png') >=0) {
        this.toPng(res,name)
      } else if (name.toLocaleLowerCase().indexOf('jpg') >=0 || name.toLocaleLowerCase().indexOf('jpeg') >=0 ) {
        this.toJpg(res,name)
      } else {
        this.toOdf(res,name)
      }
    },
    toPdf(res, setFileName) {
      const file = res
      const blob = new Blob([file], { type: 'application/pdf' })
      saveAs(blob, setFileName)
    },
    toPng(res, setFileName) {
      const file = res
      const blob = new Blob([file], { type: 'image/png' })
      saveAs(blob, setFileName)
    },
    toJpg(res, setFileName) {
      const file = res
      const blob = new Blob([file], { type: 'image/jpg' })
      saveAs(blob, setFileName)
    },
    toOdf(res, setFileName) {
      const file = res
      const blob = new Blob([file], {
        type: 'application/vnd.oasis.opendocument.text'
      })
      saveAs(blob, setFileName)
    }
  }
}
</script>
