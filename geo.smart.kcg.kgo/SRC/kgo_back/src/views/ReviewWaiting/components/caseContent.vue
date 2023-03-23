<template>
  <div>
    <div>
      <div class="fsm-form">
        <div class="row">
          <div class="col-xs-6 form-group col-md-6">
            <label for="input_event" class="col-sm-4 control-label">
              案件狀態
            </label>
            <div class="col-sm-8">
              <label for="input_event" style="padding-top: 7px">
                {{ dataObject.status }}
              </label>
            </div>
          </div>
        </div>
      </div>
      <div
        id="accordion"
        class="panel-group"
        role="tablist"
        aria-multiselectable="true"
      >
        <div
          id="accordion2"
          class="panel panel-default defaultcollapse"
          :class="[pdfPadding ? 'pdfPadding' : '']"
        >
          <div
            id="headingOne"
            class="panel-heading"
            role="tab"
            :href="'#collapse1'"
            data-toggle="collapse"
            aria-expanded="true"
            :aria-controls="'collapse1'"
          >
            <h4 class="panel-title" style="font-size: 1em;font-weight:500">
              <a role="button">申請資料</a>
            </h4>
          </div>
          <div
            :id="'collapse1'"
            class="panel-collapse collapse in"
            role="tabpanel"
            aria-labelledby="headingOne"
          >
            <div class="panel-body">
              <table id="apply-data" class="table table-bordered">
                <tr>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label">案件編號</label>
                  </td>
                  <td style="border: 1px solid #ddd; padding: 16px">
                    <label class="control-label">{{ dataObject.caseId }}</label>
                  </td>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label">申請時間</label>
                  </td>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 300px"
                  >
                    <label class="control-label">
                      {{ dataObject.applyDate }}
                    </label>
                  </td>
                </tr>
                <tr>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label">案件名稱</label>
                  </td>
                  <td style="border: 1px solid #ddd; padding: 16px">
                    <label class="control-label">
                      {{ dataObject.caseName }}
                    </label>
                  </td>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label"></label>
                  </td>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 300px"
                  >
                    <label class="control-label"></label>
                  </td>
                </tr>
                <tr>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label">限辦天數</label>
                  </td>
                  <td style="border: 1px solid #ddd; padding: 16px">
                    <label class="control-label">
                      {{ dataObject.limitDay }}
                    </label>
                    天
                  </td>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label">限辦期間</label>
                  </td>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 300px"
                  >
                    <label class="control-label">
                      {{ dataObject.limitTime }}
                    </label>
                  </td>
                </tr>
                <tr>
                  <td
                    style="border: 1px solid #ddd; padding: 16px; width: 200px"
                  >
                    <label class="control-label">申請資料</label>
                  </td>
                  <td colspan="3" style="border: 1px solid #ddd; padding: 16px">
                    <table class="table table-bordered">
                      <tr>
                        <td
                          style="
                            border: 1px solid #ddd;
                            padding: 16px;
                            width: 200px;
                          "
                        >
                          <label class="control-label">欄位名稱</label>
                        </td>
                        <td style="border: 1px solid #ddd; padding: 16px">
                          <label class="control-label">資料</label>
                        </td>
                      </tr>
                      <!--加入動態資料-->
                      <tr
                        v-for="(item, index) in dataObject.applyData"
                        :key="index"
                      >
                        <td
                          style="
                            border: 1px solid #ddd;
                            padding: 16px;
                            width: 200px;
                          "
                        >
                          <label class="control-label">
                            {{ item.columnName }}
                          </label>
                        </td>
                        <td
                          v-if="
                            item.columnType != 'Fil' &&
                              item.columnType != 'M' &&
                              item.columnType != 'Radio' &&
                              item.columnType != 'Checkbox'
                          "
                          style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                          class="change_line"
                        >
                          <label class="control-label">
                            {{ item.columnValue }}
                          </label>
                        </td>
                        <td
                          v-if="item.columnType == 'Radio'"
                          style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                          class="change_line"
                        >
                          <input
                            v-if="item.columnValue != ''"
                            type="radio"
                            checked
                            readonly
                            disabled
                          />
                          <label class="control-label">
                            {{ item.columnValue }}
                          </label>
                        </td>
                        <td
                          v-if="item.columnType == 'Checkbox'"
                          style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                          class="change_line"
                        >
                          <div v-for="(item1, index) in item.file" :key="index">
                            <input
                              v-if="item1 != ''"
                              type="checkbox"
                              checked
                              readonly
                              disabled
                            />
                            <label class="control-label">
                              {{ item1 }}
                            </label>
                          </div>
                        </td>
                        <td
                          v-if="
                            item.columnType == 'Fil' &&
                              item.columnValue != '' &&
                              item.file.length == 1
                          "
                          style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                          class="change_line"
                        >
                          <a
                            style="cursor: pointer; text-decoration: underline;color:blue"
                            @click="downloadFile(item.columnValue)"
                          >
                            {{ item.columnValue }}
                          </a>
                        </td>
                        <td
                          v-if="
                            item.columnType == 'Fil' &&
                              item.columnValue != '' &&
                              item.file.length > 1
                          "
                          style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                          class="change_line"
                        >
                          <table>
                            <tr v-for="(item, index) in item.file" :key="index">
                              <td>
                                <a
                                  style="cursor: pointer; text-decoration: underline;color:blue"
                                  @click="downloadFile(item)"
                                >
                                  {{ item }}
                                </a>
                              </td>
                            </tr>
                          </table>
                        </td>
                        <td
                          v-if="item.columnType == 'M'"
                          style="border: 1px solid #ddd;padding: 16px;vertical-align:top;position:relative"
                          class="change_line"
                        >
                          <div
                            v-for="(item2, index) in item.complex"
                            :key="index"
                          >
                            <div
                              v-for="(item3, index) in item2.length"
                              :key="index"
                              style="display:inline-block;"
                            >
                              <div
                                v-if="
                                  item2[index].columnType == 'SCheckbox' &&
                                    item2[index].value != ''
                                "
                              >
                                {{ item2[index].fText }}
                                <input
                                  type="checkbox"
                                  checked
                                  readonly
                                  disabled
                                />
                                {{ item2[index].bText }}
                              </div>
                              <div
                                v-else-if="
                                  item2[index].columnType == 'SCheckbox' &&
                                    item2[index].value == ''
                                "
                              >
                                {{ item2[index].fText }}
                                <input type="checkbox" readonly disabled />
                                {{ item2[index].bText }}
                              </div>
                              <div
                                v-else-if="
                                  item2[index].columnType == 'SRadio' &&
                                    item2[index].value != ''
                                "
                              >
                                {{ item2[index].fText }}
                                <input type="radio" checked readonly disabled />
                                {{ item2[index].bText }}
                              </div>
                              <div
                                v-else-if="
                                  item2[index].columnType == 'SRadio' &&
                                    item2[index].value == ''
                                "
                              >
                                {{ item2[index].fText }}
                                <input type="radio" readonly disabled />
                                {{ item2[index].bText }}
                              </div>
                              <div
                                v-else-if="
                                  item2[index].columnType != 'SCheckbox' &&
                                    item2[index].columnType != 'SRadio'
                                "
                              >
                                {{ item2[index].fText }}
                                {{ item2[index].value }}
                                {{ item2[index].bText }}
                              </div>
                            </div>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default defaultcollapse">
        <div
          id="headingOne"
          class="panel-heading"
          role="tab"
          :href="'#collapse2'"
          data-toggle="collapse"
          aria-expanded="true"
          :aria-controls="'collapse2'"
        >
          <h4 class="panel-title" style="font-size: 1em;font-weight:500">
            <a role="button">上傳附件</a>
          </h4>
        </div>
        <div
          :id="'collapse2'"
          class="panel-collapse collapse"
          role="tabpanel"
          aria-labelledby="headingOne"
        >
          <div class="panel-body">
            <button type="button" class="btn btn-fsm" @click="addExplain">
              <i class="fa fa-plus" aria-hidden="true"></i>
              新增附件
            </button>
            <app-table
              :table-class="'table'"
              :columns="downloadTableColumns"
              :data="dataObject.downloadData"
              :show-pagination="false"
            >
              <template v-slot:fileName="{ data: { fileName, seq } }">
                <a
                  style="cursor: pointer; text-decoration: underline;color:blue"
                  @click="downloadAnnex(fileName, seq)"
                >
                  {{ fileName }}
                </a>
              </template>
              <template v-slot:delete="{ data: { seq } }">
                <button type="button" @click="deleteAnnex(seq)">
                  <i class="fa fa-trash-o" aria-hidden="true"></i>
                </button>
              </template>
            </app-table>
          </div>
        </div>
      </div>
      <div class="panel panel-default defaultcollapse">
        <div
          id="headingOne"
          class="panel-heading"
          role="tab"
          :href="'#collapse3'"
          data-toggle="collapse"
          aria-expanded="true"
          :aria-controls="'collapse3'"
        >
          <h4 class="panel-title" style="font-size: 1em;font-weight:500">
            <a role="button">處理歷程</a>
          </h4>
        </div>
        <div
          :id="'collapse3'"
          class="panel-collapse collapse"
          role="tabpanel"
          aria-labelledby="headingOne"
        >
          <div class="panel-body">
            <app-table
              :table-class="'table'"
              :columns="historyTableColumns"
              :data="dataObject.historyData"
              :show-pagination="false"
            ></app-table>
            <!-- 移除 -->
            <!-- <img :src="'data:image/svg+xml;base64,' + historyImage" /> -->
          </div>
        </div>
      </div>
    </div>
    <app-modal
      ref="addExplainModal"
      :modal-title="'新增說明'"
      @before-hide="closeAddExplainModal"
    >
      <add-explain ref="sortEdit" @save-file="uploadFile"></add-explain>
    </app-modal>
  </div>
</template>
<script>
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
import { page } from '@/mixins'
import AddExplain from './addExplain'
import { saveAs } from 'file-saver'
export default {
  name: 'CaseContent',
  components: {
    AddExplain
  },
  mixins: [page],
  props: {
    dataObject: {
      type: Object,
      default: () => ({})
    },
    historyImage: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      downloadTableData: [],
      pdfPadding: false
    }
  },
  computed: {
    downloadTableColumns() {
      return [
        //上傳附件欄位
        {
          title: '標題',
          dataIndex: 'title',
          tdClass: 'text-center'
        },
        {
          title: '附件',
          dataIndex: 'fileName',
          tdClass: 'text-center',
          slot: 'fileName'
        },
        {
          title: '',
          tdClass: 'text-center',
          slot: 'delete'
        }
      ]
    },
    historyTableColumns() {
      return [
        //處理歷程欄位
        {
          title: '案件狀態',
          dataIndex: 'status',
          tdClass: 'text-center'
        },
        {
          title: '處理機關',
          dataIndex: 'organ',
          tdClass: 'text-center'
        },
        {
          title: '內容',
          dataIndex: 'content',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'taker',
          tdClass: 'text-center'
        },
        {
          title: '處理時間',
          dataIndex: 'dealTime',
          tdClass: 'text-center'
        }
      ]
    }
  },
  methods: {
    addExplain() {
      this.$refs.addExplainModal.show()
    },
    closeAddExplainModal() {
      this.$refs.sortEdit.clear()
    },
    async uploadFile(file) {
      let rtnCode = ''
      let request = {
        caseId: this.dataObject.caseId,
        file: file.uploadFile,
        title: file.title
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getFileUpload({
          caseId: this.dataObject.caseId,
          file: file.uploadFile,
          title: file.title
        })
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('新增附件成功！')
      }
      this.$refs.addExplainModal.hide()
      this.$emit('reload')
    },
    async downloadDataToPdf() {
      this.pdfPadding = true
      await this.delay(220)
      let PDF = new JsPDF('p', 'pt')
      let node = document.getElementById('accordion2')
      let canvas = await html2Canvas(node, {
        useCORS: true,
        scale: 2
      })
      let width = PDF.internal.pageSize.getWidth()
      let height = PDF.internal.pageSize.getHeight()
      let widthRatio = width / canvas.width
      let heightRatio = height / canvas.height
      let ratio = widthRatio > heightRatio ? heightRatio : widthRatio

      let pageData = canvas.toDataURL('image/jpeg', 1.0)
      PDF.addImage(
        pageData,
        'JPEG',
        5,
        5,
        canvas.width * ratio - 10,
        canvas.height * ratio - 10
      )
      PDF.save('案件下載.pdf')
      this.pdfPadding = false
    },
    async downloadAnnex(fileName, seq) {
      let request = { caseId: this.dataObject.caseId, seq: seq }
      let data = ''
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getFileDownload(request)
        data = res.data
      })
      if (fileName.toLocaleLowerCase().indexOf('pdf')) {
        this.toPdf(data, fileName)
      } else if (fileName.toLocaleLowerCase().indexOf('png')) {
        this.toPng(data, fileName)
      } else if (fileName.toLocaleLowerCase().indexOf('jpg')) {
        this.toJpg(data, fileName)
      } else {
        this.toOdt(data, fileName)
      }
    },
    async downloadFile(fileName) {
      let request = { caseId: this.dataObject.caseId, fileName: fileName }
      let file = ''
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getDownloadFile(request)
        ;(file = res.data), fileName
      })

      if (fileName.toLocaleLowerCase().indexOf('pdf')) {
        this.toPdf(file, fileName)
      } else if (fileName.toLocaleLowerCase().indexOf('png')) {
        this.toPng(file, fileName)
      } else if (fileName.toLocaleLowerCase().indexOf('jpg')) {
        this.toJpg(file, fileName)
      } else {
        this.toOdt(file, fileName)
      }
    },
    async deleteAnnex(seq) {
      let rtnCode = ''
      let request = { caseId: this.dataObject.caseId, seq: seq }
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getFileDelete(request)
        rtnCode = res.data.rtnCode
      })

      if (rtnCode == '0000') {
        this.notifySuccess('刪除附件成功！')
      }
      this.$emit('reload')
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
<style lang="scss" scoped>
.change_line {
  word-break: break-all;
}
.pdfPadding {
  padding: 10px;
}
</style>
