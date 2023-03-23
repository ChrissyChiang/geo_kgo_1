<template>
  <div class="panel panel-fsm">
    <div class="panel-tabs-body">
      <!-- Nav tabs -->
      <ul class="nav nav-tabs" role="tablist">
        <li class="active" role="presentation">
          <a
            role="tab"
            aria-controls="tab01"
            href="#tab01"
            data-toggle="tab"
            aria-expanded="true"
          >
            基本資料
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
          <case-tab1 ref="caseTab1" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import CaseTab1 from './components/CaseTab1'
import { page } from '@/mixins'
export default {
  components: {
    CaseTab1
  },
  mixins: [page],
  data() {
    return {}
  },
  async mounted() {},
  methods: {
    goBack() {
      this.$router.push('/caseManagement')
    },

    async saveAllTab() {
      const caseTab1Pass = await this.$refs.caseTab1.validateCaseTabOne()
      if (!caseTab1Pass) {
        this.notifyFail('請檢查欄位是否正確或必填')
        $(this.$refs.tab1).click()
        return
      }
      //新增tab1
      let addCaseSetId = null
      await this.loadingContainer(async () => {
        addCaseSetId = await this.$refs.caseTab1.saveAndGetCaseId()
      })

      if (addCaseSetId != null) {
        this.editCaseId = addCaseSetId
        this.$router.push(`/caseManagement/caseEdit/${addCaseSetId}`)
      }
    }
  }
}
</script>
