<template>
  <div id="tab03" role="tabpanel" class="tab-pane">
    <div
      id="onlinedata"
      class="panel-group"
      role="tablist"
      aria-multiselectable="true"
    >
      <div class="panel panel-default defaultcollapse">
        <div id="heading113" class="panel-heading" role="tab">
          <h4 class="panel-title">
            <a
              class="collapsed"
              role="button"
              aria-expanded="false"
              aria-controls="collapse113"
              href="#collapse113"
              data-toggle="collapse"
              data-parent="#accordion"
            >
              臨櫃申請說明設定(必填)
            </a>
          </h4>
        </div>
        <div
          id="collapse113"
          class="panel-collapse collapse in"
          role="tabpanel"
          aria-labelledby="heading113"
        >
          <div class="panel-body">
            <div class="panel-group">
              <button
                class="btn btn-fsm"
                type="button"
                @click="editCounterApply(null)"
              >
                <i class="fa fa-plus" aria-hidden="true"></i>
                新增說明
              </button>
            </div>
            <app-table
              :columns="counterApplyTableColumns"
              :data="counterApplyHomeDataGrid"
              :draggable="false"
              :show-pagination="true"
            >
              <template v-slot:desc="{ data: { contentHtml } }">
                {{ $f.subString($f.clearHTML(contentHtml), 100) }}
              </template>
              <template v-slot:operating="{ index }">
                <button
                  type="button"
                  class="btn-line btn-warning"
                  @click="editCounterApply(index)"
                >
                  <i class="fa fa-cog" aria-hidden="true"></i>
                  編輯
                </button>
                <button
                  type="button"
                  class="btn-line btn-danger"
                  @click="showDelCounterApplyConfirm(index)"
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
      ref="confirmCounterApplyDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delLocalCounterApply"
    />
    <app-modal
      ref="counterApplyEditModal"
      :modal-title="modalTitleName"
      @before-hide="closeCounterApplyEditModal"
    >
      <attachment-edit
        ref="counterApplyEdit"
        @confirm-edit="confirmEdit"
      ></attachment-edit>
    </app-modal>
  </div>
</template>
<script>
import { page } from '@/mixins'
import AttachmentEdit from './AttachmentEdit'
export default {
  name: 'CaseTab3',
  components: {
    AttachmentEdit
  },
  mixins: [page],
  data() {
    return {
      counterApplyHomeDataGrid: [],
      delIndex: null,
      caseSetId: this.$route.params.id || null,
      modalTitleName: ''
    }
  },
  computed: {
    /** 受理機關設定欄位 */
    counterApplyTableColumns() {
      return [
        {
          title: '標題',
          dataIndex: 'title',
          width: '20%',
          tdClass: 'text-center'
        },
        {
          title: '說明內容',
          slot: 'desc',
          tdClass: 'text-left'
        },
        {
          title: '附件',
          dataIndex: 'fileName',
          tdClass: 'text-center'
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
    await this.loadingContainer(async () => {
      const res = await this.$api.management.getCounterApplyHomeData({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.counterApplyHomeDataGrid = data.grid || []
    })
  },
  methods: {
    showDelCounterApplyConfirm(index) {
      this.delIndex = index
      this.$refs.confirmCounterApplyDelete.show()
    },
    delLocalCounterApply() {
      this.counterApplyHomeDataGrid.splice(this.delIndex, 1)
      this.$refs.confirmCounterApplyDelete.hide()
    },
    async saveCounterApply() {
      let pass = true
      if (this.counterApplyHomeDataGrid.length > 0) {
        try {
          const res = await this.$api.management.saveCounterApply({
            caseSetId: this.caseSetId,
            grid: this.counterApplyHomeDataGrid
          })
        } catch (error) {
          pass = false
        }
      }
      return pass
    },
    async confirmEdit(data) {
      let uploadFileName = ''
      if (data.uploadFile != null) {
        await this.loadingContainer(async () => {
          const res = await this.$api.management.attachmentUploadAction({
            caseSetId: this.caseSetId,
            file: data.uploadFile
          })
          if (this.checkResSuccess(res, false)) {
            const data = res.data.data
            uploadFileName = data.fileName
          }
        })
      } else {
        uploadFileName = data.fileName
      }
      if (uploadFileName == '' && data.uploadFile != null) {
        return
      }

      if (data.index == null) {
        this.counterApplyHomeDataGrid.push({
          title: data.title,
          contentHtml: data.contentHtml,
          fileName: uploadFileName
        })
      } else {
        this.counterApplyHomeDataGrid[data.index].title = data.title
        this.counterApplyHomeDataGrid[data.index].contentHtml = data.contentHtml
        this.counterApplyHomeDataGrid[data.index].fileName = uploadFileName
      }

      this.$refs.counterApplyEditModal.hide()
    },
    editCounterApply(index) {
      if (index != null) {
        this.modalTitleName = '編輯臨櫃申請說明'
        const editData = this.counterApplyHomeDataGrid[index]
        this.$refs.counterApplyEdit.initData({ ...editData, index })
      } else {
        this.modalTitleName = '新增臨櫃申請說明'
      }

      this.$refs.counterApplyEditModal.show()
    },
    closeCounterApplyEditModal() {
      this.$refs.counterApplyEdit.clear()
    }
  }
}
</script>
