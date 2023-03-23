<template>
  <div class="panel panel-fsm">
    <div class="panel-tabs-body">
      <!-- Nav tabs -->
      <ul class="nav nav-tabs" role="tablist">
        <li class="active" role="presentation">
          <a
            ref="tab1"
            role="tab"
            aria-controls="tab01"
            href="#tab01"
            data-toggle="tab"
            aria-expanded="true"
          >
            基本資料
          </a>
        </li>
        <li
          v-if="editCaseId != null"
          v-show="isEServer"
          role="presentation"
          class=""
        >
          <a
            ref="tab2"
            role="tab"
            aria-controls="tab02"
            href="#tab02"
            data-toggle="tab"
            aria-expanded="false"
          >
            網路申請
          </a>
        </li>
        <li
          v-if="editCaseId != null"
          v-show="isCServer"
          role="presentation"
          class=""
        >
          <a
            ref="tab3"
            role="tab"
            aria-controls="tab03"
            href="#tab03"
            data-toggle="tab"
            aria-expanded="false"
          >
            臨櫃申請
          </a>
        </li>
        <li
          v-if="editCaseId != null"
          v-show="isLServer"
          role="presentation"
          class=""
        >
          <a
            ref="tab4"
            role="tab"
            aria-controls="tab04"
            href="#tab04"
            data-toggle="tab"
            aria-expanded="false"
          >
            書表維護
          </a>
        </li>
        <div class="text-right">
          <button class="btn btn-fsm" type="button" @click="saveAllTab">
            <i class="fa fa-save" aria-hidden="true"></i>
            儲存
          </button>
          <button class="btn btn-fsm" type="button" @click="goBack">
            <i class="fa fa-undo" aria-hidden="true"></i>
            返回
          </button>
        </div>
      </ul>
    </div>
    <div class="panel-body">
      <div class="demo-container">
        <div class="tab-content">
          <case-tab1 ref="caseTab1" @change-organ="changeOrgan" />
          <case-tab2
            v-if="editCaseId != null"
            ref="caseTab2"
            :organ-name="organName"
          />
          <case-tab3 v-if="editCaseId != null" ref="caseTab3" />
          <case-tab4 v-if="editCaseId != null" ref="caseTab4" />
        </div>
      </div>
    </div>
    <modal-confirm
      ref="confirmSaveForm"
      :close-text="'取消'"
      :modal-content="'表單資料有異動，是否要進版儲存？'"
      @after-hidden="closeConfirmSaveForm"
      @cancel="onConfirmSaveForm(false)"
      @confirm="onConfirmSaveForm(true)"
    />
  </div>
</template>
<script>
import CaseTab1 from './components/CaseTab1'
import CaseTab2 from './components/CaseTab2'
import CaseTab3 from './components/CaseTab3'
import CaseTab4 from './components/CaseTab4'
import { page } from '@/mixins'
export default {
  components: {
    CaseTab1,
    CaseTab2,
    CaseTab3,
    CaseTab4
  },
  mixins: [page],
  data() {
    return {
      isSaveForm: false,
      organName: '', //服務的機關別
      editCaseId: this.$route.params.id || null
    }
  },
  async mounted() {},
  methods: {
    goBack() {
      this.$router.push('/caseManagement')
    },
    changeOrgan(organName) {
      this.organName = organName
    },
    async saveAllTab() {
      const caseTab1Pass = await this.$refs.caseTab1.validateCaseTabOne()
      if (!this.editCaseId) {
        // if (!caseTab1Pass) {
        //   this.notifyFail('請檢查欄位是否正確或必填')
        //   $(this.$refs.tab1).click()
        //   return
        // }
        // //新增tab1
        // let addCaseSetId = null
        // await this.loadingContainer(async () => {
        //   addCaseSetId = await this.$refs.caseTab1.saveAndGetCaseId()
        // })
        // if (addCaseSetId != null) {
        //   this.editCaseId = addCaseSetId
        //   //window.location.href = `/kgo/caseManagement/caseEdit/${addCaseSetId}`
        //   // this.$router.push(`/caseManagement/caseEdit/${addCaseSetId}`)
        // }
      } else {
        if (!caseTab1Pass) {
          this.notifyFail('請檢查欄位是否正確或必填')
          $(this.$refs.tab1).click()
          return
        }
        const tab2ErrMsgArray = this.$refs.caseTab2.getCheckFlowRequiredMsgArray()
        if (this.isEServer && tab2ErrMsgArray.length > 0) {
          this.notifyFail(tab2ErrMsgArray.join('<br />'))
          $(this.$refs.tab2).click()
          return
        }

        // 編輯case
        if (
          this.isCServer &&
          this.$refs.caseTab3.counterApplyHomeDataGrid.length == 0
        ) {
          this.notifyFail('臨櫃申請，請至少上傳一個附件')
          $(this.$refs.tab3).click()
          return
        }

        if (
          this.isLServer &&
          this.$refs.caseTab4.formDownloadQueryDataGrid.length == 0
        ) {
          this.notifyFail('書表維護，請至少上傳一個附件')
          $(this.$refs.tab4).click()
          return
        }
        if (this.$refs.caseTab2.isModifyForm == true) {
          this.$refs.confirmSaveForm.show()
        } else {
          await this.loadingContainer(async () => {
            await this.save()
          })
        }
      }
    },
    async save() {
      let pass = true
      const caseid = await this.$refs.caseTab1.saveAndGetCaseId()
      if (caseid == null) {
        this.notifyFail('請檢查案件基本設定資料')
      } else {
        if (this.isEServer) {
          pass = await this.$refs.caseTab2.saveAllData()
        }

        if (this.isCServer) {
          pass = await this.$refs.caseTab3.saveCounterApply()
        }

        this.notifyFail(pass ? '儲存成功' : '儲存失敗')
      }
    },
    onConfirmSaveForm(isSaveForm) {
      this.isSaveForm = isSaveForm
      this.$refs.confirmSaveForm.hide()
    },
    async closeConfirmSaveForm() {
      await this.loadingContainer(async () => {
        if (this.isSaveForm == true) {
          await this.$refs.caseTab2.saveForm()
        }
        await this.save()
      })
    }
  }
}
</script>
