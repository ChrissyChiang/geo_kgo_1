<template>
  <div id="tab04" role="tabpanel" class="tab-pane">
    <div
      id="onlinedata"
      class="panel-group"
      role="tablist"
      aria-multiselectable="true"
    >
      <div class="panel panel-default defaultcollapse">
        <div id="heading114" class="panel-heading" role="tab">
          <h4 class="panel-title">
            <a
              class="collapsed"
              role="button"
              aria-expanded="false"
              aria-controls="collapse114"
              href="#collapse114"
              data-toggle="collapse"
              data-parent="#accordion"
            >
              書表維護設定(必填)
            </a>
          </h4>
        </div>
        <div
          id="collapse114"
          class="panel-collapse collapse in"
          role="tabpanel"
          aria-labelledby="heading114"
        >
          <div class="panel-body">
            <div class="panel-group">
              <button
                class="btn btn-fsm"
                type="button"
                @click="editFormDownload(null)"
              >
                <i class="fa fa-plus" aria-hidden="true"></i>
                新增文件
              </button>
            </div>

            <app-table
              :columns="formDownloadTableColumns"
              :data="formDownloadQueryDataGrid"
              :draggable="false"
              :show-pagination="true"
            >
              <template v-slot:type="{ data: { type } }">
                {{ findformTypeName(type) }}
              </template>
              <template v-slot:attachFile="{ data: { fileName } }">
                <!-- <a href="javascript:void(0)"> -->
                {{ fileName }}
                <!-- </a> -->
              </template>
              <template v-slot:operating="{ data: { seq } }">
                <button
                  type="button"
                  class="btn-line btn-warning"
                  @click="editFormDownload(seq)"
                >
                  <i class="fa fa-cog" aria-hidden="true"></i>
                  編輯
                </button>
                <button
                  type="button"
                  class="btn-line btn-danger"
                  @click="showDelFormDownloadConfirm(seq)"
                >
                  <i class="fa fa-trash-o" aria-hidden="true"></i>
                  刪除
                </button>
              </template>
            </app-table>
          </div>
        </div>
      </div>
    </div>
    <modal-confirm
      ref="confirmFormDownloadDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delFormDownload"
    />
    <app-modal ref="formDownloadEditModal" :modal-title="modalTitleName">
      <form-download-edit
        ref="formDownload"
        :form-type-options="formTypeOptions"
        @confirm-edit="confirmEdit"
      ></form-download-edit>
    </app-modal>
  </div>
</template>
<script>
import { page } from '@/mixins'
import FormDownloadEdit from './FormDownloadEdit'
export default {
  name: 'CaseTab4',
  components: {
    FormDownloadEdit
  },
  mixins: [page],
  data() {
    return {
      modalTitleName: '',
      formDownloadQueryDataGrid: [],
      formTypeOptions: [],
      delSeq: null,
      caseSetId: ''
    }
  },
  computed: {
    /** 受理機關設定欄位 */
    formDownloadTableColumns() {
      return [
        {
          title: '分類',
          tdClass: 'text-center',
          width: '20%',
          slot: 'type'
        },
        {
          title: '標題',
          dataIndex: 'title',
          width: '20%',
          tdClass: 'text-center'
        },
        {
          title: '附件',
          tdClass: 'text-center',
          slot: 'attachFile'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          width: '20%',
          slot: 'operating'
        }
      ]
    }
  },
  async mounted() {
    this.caseSetId = this.$route.params.id || null
    await this.getHomeData()
  },
  methods: {
    async confirmEdit(data) {
      let uploadFileName = ''
      await this.loadingContainer(async () => {
        if (data.uploadFile != null) {
          const res = await this.$api.management.uploadFormDownload({
            caseSetId: this.caseSetId,
            file: data.uploadFile
          })
          if (this.checkResSuccess(res, false)) {
            const data = res.data.data
            uploadFileName = data.fileName
          }
        } else {
          uploadFileName = data.fileName
        }
      })
      if (uploadFileName == '' && data.uploadFile != null) {
        return
      }
      try {
        await this.$api.management.editFormDownload({
          caseSetId: this.caseSetId,
          seq: data.seq,
          type: data.type,
          title: data.title,
          fileName: uploadFileName
        })
      } catch (error) {
        this.notifyFail('儲存失敗')
      }
      await this.getHomeData()
      this.$refs.formDownloadEditModal.hide()
    },
    closeFormDownloadEditModal() {
      this.$refs.formDownload.clear()
    },
    showDelFormDownloadConfirm(seq) {
      this.delSeq = seq
      this.$refs.confirmFormDownloadDelete.show()
    },
    async delFormDownload() {
      const res = await this.$api.management.deleteFormDownload({
        caseSetId: this.caseSetId,
        seq: this.delSeq
      })
      if (this.checkResSuccess(res, false)) {
        this.$refs.confirmFormDownloadDelete.hide()
        await this.getHomeData()
      }
    },
    findformTypeName(type) {
      const findName = this.formTypeOptions.find(x => x.value == type)
      return findName ? findName.label : ''
    },
    editFormDownload(seq) {
      this.modalTitleName = seq == null ? '新增書表下載' : '編輯書表下載'
      this.$refs.formDownload.clear()
      if (seq != null) {
        const findData = this.formDownloadQueryDataGrid.find(x => x.seq == seq)
        this.$refs.formDownload.initData({ ...findData })
      }

      this.$refs.formDownloadEditModal.show()
    },
    async downloadFile(fileSeq) {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.downloadFormDownload({
          caseSetId: this.caseSetId,
          seq: fileSeq
        })
        this.$f.toPdf(res, 'documentFile.pdf')
      })
    },
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getFormDownloadHomeData({
          caseSetId: this.caseSetId
        })
        const data = res.data.data
        this.formDownloadQueryDataGrid = data.grid || []
        this.formTypeOptions = data.formTypeComboBox
          ? data.formTypeComboBox.options
          : []
      })
    }
  }
}
</script>
