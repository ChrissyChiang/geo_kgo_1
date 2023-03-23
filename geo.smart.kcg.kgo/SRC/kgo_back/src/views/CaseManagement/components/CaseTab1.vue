<template>
  <div id="tab01" role="tabpanel" class="tab-pane active">
    <validation-observer ref="caseTabOneObserver">
      <div
        id="accordion"
        class="panel-group"
        role="tablist"
        aria-multiselectable="true"
      >
        <div class="panel panel-default defaultcollapse">
          <div id="heading1" class="panel-heading" role="tab">
            <h4 class="panel-title">
              <a
                role="button"
                aria-expanded="true"
                aria-controls="collapse1"
                href="#collapse1"
                data-toggle="collapse"
                data-parent="#accordion"
                class=""
              >
                服務基本設定
              </a>
            </h4>
          </div>
          <div
            id="collapse1"
            class="panel-collapse collapse in"
            role="tabpanel"
            aria-labelledby="heading1"
            aria-expanded="true"
            style=""
          >
            <div class="panel-body">
              <table class="table table-unstyled">
                <tbody>
                  <tr v-if="caseSetId != null">
                    <th>服務編號</th>
                    <td colspan="2">
                      {{ caseSetId }}
                    </td>
                  </tr>
                  <tr>
                    <th>整合流程分類</th>
                    <td colspan="2">
                      <base-select
                        v-model="caseFlowTypeComboBox.selectedVal"
                        :options="caseFlowTypeComboBox.options"
                        required
                        :select="caseFlowTypeComboBox.selectedVal"
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>服務名稱(必填)</th>
                    <td colspan="3">
                      <validate-container :rules="'required'">
                        <input
                          v-model="caseSetName"
                          class="form-control"
                          type="text"
                          :disabled="isEditDisabled"
                          placeholder="請輸入服務名稱"
                          maxlength="100"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr v-if="['B1', 'B2', 'B3'].includes(caseFlow)">
                    <th>作業流程(必填)</th>
                    <td colspan="2">
                      <validate-container
                        :rules="
                          ['B1', 'B2', 'B3'].includes(caseFlow)
                            ? 'required'
                            : ''
                        "
                      >
                        <base-select
                          v-model="caseTypeComboBox.selectedVal"
                          :options="caseTypeComboBox.options"
                          :disabled="caseFlowTypeDisabled"
                          :select="caseTypeComboBox.selectedVal"
                        />
                      </validate-container>
                    </td>
                    <td v-if="isShowPreviewFlowBtn">
                      <button
                        class="btn btn-fsm"
                        type="button"
                        @click="openPreviewFlowModal"
                      >
                        <i class="fa fa-search" aria-hidden="true"></i>
                        預覽流程圖
                      </button>
                    </td>
                  </tr>
                  <tr>
                    <th>服務連結方式{{ caseFlow == 'A' ? '(必填)' : '' }}</th>
                    <td colspan="2">
                      <validate-container
                        :rules="caseFlow == 'A' ? 'required' : ''"
                      >
                        <base-select
                          v-model="linkTypeComboBox.selectedVal"
                          :options="linkTypeComboBox.options"
                          :disabled="isEditLinkTypeDisabled"
                          :select="linkTypeComboBox.selectedVal"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr v-if="isNeedLinkURL">
                    <th>介接機關網址{{ isNeedLinkURL ? '(必填)' : '' }}</th>
                    <td colspan="3">
                      <validation-provider
                        v-slot="data"
                        :rules="isNeedLinkURL ? 'required|url' : 'url'"
                      >
                        <input
                          v-model="linkUrl"
                          class="form-control"
                          type="text"
                          :disabled="isEditDisabled"
                          placeholder="請輸入服務連結網址"
                        />
                        <div class="error_label">{{ data.errors[0] }}</div>
                        <span v-if="isShowLinkURLDesc">
                          如果您沒有介接網址，請到以下連結建立API服務：
                          <a
                            class="link"
                            target="_blank"
                            href="https://api.kcg.gov.tw/SupplyService"
                          >
                            https://api.kcg.gov.tw/SupplyService
                          </a>
                          ，再將建立後的介接網址複製至此欄位。
                        </span>
                      </validation-provider>
                    </td>
                  </tr>
                  <tr>
                    <th>機關別</th>
                    <td colspan="2">
                      <validate-container :rules="'required'">
                        <base-select
                          v-model="organComboBox.selectedVal"
                          :options="organComboBox.options"
                          :disabled="isEditDisabled"
                          search
                          :select="organComboBox.selectedVal"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr>
                    <th>身份別</th>
                    <td colspan="2">
                      <!-- <base-select
                        v-model="roleComboBox.selectedVal"
                        :options="roleComboBox.options"
                        :disabled="isEditDisabled"
                        :select="roleComboBox.selectedVal"
                      /> -->
                      <multi-select
                        v-model="roleSelectList"
                        :value="roleSelectList"
                        :closedata="false"
                        :disabled="isEditDisabled"
                        :show-selected-limit="10"
                        :select-multiple="true"
                        :options="roleComboBox.options"
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>服務別</th>
                    <td colspan="2">
                      <!-- <base-select
                        v-model="serviceComboBox.selectedVal"
                        :options="serviceComboBox.options"
                        :disabled="isEditDisabled"
                        :select="serviceComboBox.selectedVal"
                      /> -->
                      <multi-select
                        v-model="serviceSelectList"
                        :value="serviceSelectList"
                        :closedata="false"
                        :disabled="isEditDisabled"
                        :show-selected-limit="10"
                        :select-multiple="true"
                        :options="serviceComboBox.options"
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>權責機關</th>
                    <td colspan="2">
                      <!-- <base-select
                        :options="ownerOrganComboBox.options"
                        required
                        disabled
                        v-model="ownerOrganComboBox.selectedVal"
                        :select="ownerOrganComboBox.selectedVal"
                      /> -->
                      {{ ownerOrganName }}
                    </td>
                  </tr>
                  <tr>
                    <th>服務管理者{{ isManagerIdRequired ? '(必填)' : '' }}</th>
                    <td colspan="2">
                      <validate-container
                        :rules="isManagerIdRequired ? 'required' : ''"
                      >
                        <input
                          class="form-control"
                          type="text"
                          :disabled="true"
                          :value="managerName"
                          placeholder=""
                        />
                      </validate-container>
                    </td>
                    <td width="14%" valign="top">
                      <button
                        :disabled="isEditDisabled"
                        class="btn btn-fsm"
                        type="button"
                        @click="openChoosePeopleTab1Modal"
                      >
                        <i class="fa fa-location-arrow" aria-hidden="true"></i>
                        選擇人員
                      </button>
                    </td>
                  </tr>
                  <tr>
                    <th>服務啟用</th>
                    <td colspan="3">
                      <base-check-list
                        v-model="selectedServerList"
                        :options="serverCheckList"
                        :selected-list="selectedServerList"
                      />
                    </td>
                  </tr>
                  <tr v-if="caseSetId != null">
                    <th>城市幣啟用狀態</th>
                    <td colspan="2">
                      <span v-for="(task, index) in taskList" :key="index">
                        <p style="margin-bottom: 5px">
                          {{ task.enable == true ? '已啟用' : '未啟用' }}
                        </p>
                        <p style="margin-bottom: 5px">{{ task.title }}</p>
                      </span>
                    </td>
                    <td width="14%" valign="top"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </validation-observer>
    <app-modal
      ref="choosePeopleTab1Modal"
      :modal-title="'選擇服務管理者'"
      @before-hide="closeChoosePeopleTab1Modal"
    >
      <choose-officer
        ref="choosePeopleTab1"
        :organ-options="organOptions"
        :is-case-m="true"
        :unit-options="unitOptions"
        @close-modal="closeChoose"
        @decide-people="showDecidePeople"
      ></choose-officer>
    </app-modal>
    <app-modal
      ref="previewFlowModal"
      :modal-title="'預覽流程圖'"
      :is-large-size="true"
      @after-show="afterShowPreview"
      @before-hide="closeBeforePreviewModal"
    >
      <flow-panel
        ref="previewFlow"
        :edit-flow-id="previewFlowId"
        :is-edit="false"
      />
    </app-modal>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'CaseTab1',
  components: {},
  mixins: [page],
  data() {
    return {
      organOptions: {},
      unitOptions: {},
      /** 案件代碼 */
      caseSetId: this.$route.params.id || null,
      /** 案件服務對象 */
      serviceToComboBox: {
        selectedVal: '',
        options: []
      },
      /** 作業流程 */
      caseTypeComboBox: {
        selectedVal: '',
        options: []
      },
      /** 暫存作業流程 */
      tempCaseTypeOption: [],
      /** 案件名稱 */
      caseSetName: '',
      /** 作業流程分類 */
      caseFlowTypeComboBox: {
        selectedVal: '',
        options: []
      },
      /** 站外連結方式 */
      linkTypeComboBox: {
        selectedVal: '',
        options: []
      },
      /** 站外連結網址 */
      linkUrl: '',
      /** 機關分類 */
      organComboBox: {
        selectedVal: '',
        options: []
      },
      /** 角色分類 */
      roleComboBox: {
        selectedVal: '',
        options: []
      },
      /** 已選到的角色分類(多選用) */
      roleSelectList: [],
      /** 服務分類 */
      serviceComboBox: {
        selectedVal: '',
        options: []
      },
      /** 已選到的服務分類 */
      serviceSelectList: [],
      /** 權責機關下拉物件 */
      ownerOrganComboBox: {
        selectedVal: '',
        options: []
      },
      /** 權責機關名稱 */
      ownerOrganName: '',
      /** 權責機關Id */
      ownerOrganId: '',
      /** 案件管理者代碼(點選選擇人員按鈕，顯示時一人以上後串『,』ＥＸ：u0001,u0002) */
      managerId: '',
      /** 案件管理者名稱(點選選擇人員按鈕，顯示時一人以上後串『、』ＥＸ：王小明、李曉芳) */
      managerName: '',
      /** 服務啟用清單 */
      serverCheckList: [],
      /** 已勾選服務啟用清單 */
      selectedServerList: [],
      /** 城市幣任務清單 */
      taskList: [],
      /** 案件是否Disabled */
      caseFlowTypeDisabled: true,
      previewFlowId: ''
    }
  },
  computed: {
    // todo 僅機關管理者、系統管理者可修改
    isEditDisabled() {
      return false
    },
    /** 是否顯示流程預覽按鈕 */
    isShowPreviewFlowBtn() {
      //const noShowFlowBtn = ['SCA', 'URA', 'SA']
      //return !noShowFlowBtn.includes(this.caseTypeComboBox.selectedVal)
      return true
    },
    /** 站外連結網址是否必填 */
    isNeedLinkURL() {
      const requireds = ['A', 'B1', 'B2']
      return requireds.includes(this.caseFlow)
    },
    /** 是否顯示站外連結說明 */
    isShowLinkURLDesc() {
      const requireds = ['B1', 'B2']
      return requireds.includes(this.caseFlow)
    },
    // todo 僅機關管理者、系統管理者可修改且是A流程
    isEditLinkTypeDisabled() {
      return false
    },
    /** 案件管理者是否必填 */
    isManagerIdRequired() {
      const requireds = ['B3']
      return requireds.includes(this.caseFlow)
    }
  },
  watch: {
    'organComboBox.selectedVal': {
      handler() {
        let organName = this.organComboBox.options.find(
          item => item.value == this.organComboBox.selectedVal
        ).label
        this.$emit('change-organ', organName)
      }
    },
    'caseFlowTypeComboBox.selectedVal': {
      handler() {
        if (!this.caseFlowTypeComboBox.selectedVal) return
        this.setCaseFlow(this.caseFlowTypeComboBox.selectedVal)
        // B3類型作業流程排除暫存類型
        if (this.caseFlowTypeComboBox.selectedVal == 'B3') {
          const excludeTempTypeCaseTypeOptions = this.tempCaseTypeOption.filter(
            x => x.groupKey != 'T'
          )
          this.caseTypeComboBox.options = [...excludeTempTypeCaseTypeOptions]
        } else if (
          // B1,B2類型作業流程撈暫存類型
          this.caseFlowTypeComboBox.selectedVal == 'B1' ||
          this.caseFlowTypeComboBox.selectedVal == 'B2'
        ) {
          const tempTypeCaseTypeOptions = this.tempCaseTypeOption.filter(
            x => x.groupKey == 'T'
          )
          this.caseTypeComboBox.options = [...tempTypeCaseTypeOptions]
          this.caseTypeComboBox.selectedVal = ''
        } else {
          const SACaseType = this.tempCaseTypeOption.find(x => x.value == 'SA')
          if (SACaseType) {
            this.caseTypeComboBox.options = [
              {
                label: SACaseType.label,
                value: SACaseType.value
              }
            ]
            this.caseTypeComboBox.selectedVal = ''
          }
        }
      }
    },
    'caseTypeComboBox.selectedVal': {
      handler() {
        this.setCaseType(this.caseTypeComboBox.selectedVal)
      }
    },
    // 'serviceToComboBox.selectedVal': {
    //   handler() {
    //     this.filterCaseType()
    //   },
    // },
    selectedServerList: {
      handler() {
        this.setServerList(this.selectedServerList)
      },
      deep: true
    }
  },
  async mounted() {
    this.caseSetId = this.$route.params.id || null
    await this.loadingContainer(async () => {
      await this.getCaseHome()
    })
  },
  methods: {
    /** 初始化篩選作業流程選項 */
    initFilterCaseType() {
      // 先由前端過濾掉這兩個類型
      this.caseTypeComboBox.options = this.caseTypeComboBox.options.filter(
        x => !['SCA', 'URA'].includes(x.value)
      )

      // 如果是編輯模式，選到娃娃圖類型，移除SA類型
      // if (this.caseSetId != null && this.isShowPreviewFlowBtn) {
      //   this.caseTypeComboBox.options = this.caseTypeComboBox.options.filter(
      //     x => !['SA'].includes(x.value)
      //   )
      // }
    },
    openChoosePeopleTab1Modal() {
      this.$refs.choosePeopleTab1Modal.show()
      this.$refs.choosePeopleTab1.getHomeData()
    },
    closeChoose() {
      this.$refs.choosePeopleTab1Modal.hide()
    },
    showDecidePeople(selectData) {
      this.managerName = selectData.selectedTable.map(x => x.name).join(',')
      this.managerId = selectData.selectedTable.map(x => x.userId).join(',')
      this.ownerOrganName = selectData.selectedOrganName
      this.ownerOrganId = selectData.selectedOrganId

      this.$refs.choosePeopleTab1Modal.hide()
    },
    async getCaseHome() {
      const res = await this.$api.management.editCassManagementHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.initData(data)
    },
    closeChoosePeopleTab1Modal() {
      this.$refs.choosePeopleTab1.clear()
    },
    async initData(data) {
      if (!data) {
        return
      }
      if (data.serviceToComboBox) {
        this.serviceToComboBox = data.serviceToComboBox
      }
      this.caseSetName = data.caseSetName || ''
      let selectCaseType = ''
      if (data.caseTypeComboBox) {
        this.caseTypeComboBox = data.caseTypeComboBox
        selectCaseType = this.caseTypeComboBox.selectedVal
        this.initFilterCaseType()
        this.tempCaseTypeOption = this.caseTypeComboBox.options
      }
      if (data.caseFlowTypeComboBox) {
        this.caseFlowTypeComboBox = data.caseFlowTypeComboBox
      }
      await this.delay(200)
      this.caseTypeComboBox.selectedVal = selectCaseType

      if (data.linkTypeComboBox) {
        this.linkTypeComboBox = data.linkTypeComboBox
      }
      this.linkUrl = data.linkUrl || ''
      if (data.organComboBox) {
        this.organComboBox = data.organComboBox
      }

      if (data.roleComboBox) {
        this.roleComboBox = data.roleComboBox
        this.roleSelectList = this.roleComboBox.options
          .filter(x => x.selected)
          .map(x => ({
            label: x.label,
            value: x.value
          }))
      }

      if (data.serviceComboBox) {
        this.serviceComboBox = data.serviceComboBox
        this.serviceSelectList = this.serviceComboBox.options
          .filter(x => x.selected)
          .map(x => ({
            label: x.label,
            value: x.value
          }))
      }
      /* if (data.ownerOrganComboBox) {
        this.ownerOrganComboBox = data.ownerOrganComboBox
        const findSelectData = this.ownerOrganComboBox.options.find(
          x => x.selected == true
        )
        this.ownerOrganName = findSelectData ? findSelectData.label : ''
        this.ownerOrganId = findSelectData ? findSelectData.value : ''
      }*/
      this.ownerOrganName = data.ownerOrganLabel || ''
      this.ownerOrganId = data.ownerOrganValue || ''

      this.managerId = data.managerId || ''

      this.managerName = data.managerName || ''
      if (data.serverCheckList && Array.isArray(data.serverCheckList)) {
        this.serverCheckList = data.serverCheckList
        const selectedList = this.serverCheckList
          .filter(x => x.selected == true)
          .map(item => item.value)
        this.selectedServerList = [...selectedList]
      }
      if (data.taskList && Array.isArray(data.taskList)) {
        this.taskList = data.taskList
      }

      this.checkCaseTypeDisabled()
    },
    async validateCaseTabOne() {
      const pass = await this.$refs.caseTabOneObserver.validate()
      return pass
    },
    afterShowPreview() {
      this.previewFlowId = this.caseTypeComboBox.selectedVal
    },
    closeBeforePreviewModal() {
      this.previewFlowId = ''
      this.$refs.previewFlow.clear()
    },
    async saveAndGetCaseId() {
      if (!(await this.$refs.caseTabOneObserver.validate())) {
        return null
      }
      const res = await this.$api.management.saveCassManagement(
        this.getFormData()
      )

      if (this.checkResSuccess(res, false)) {
        const data = res.data.data
        return data.caseSetId || null
      } else {
        return null
      }
    },
    checkCaseTypeDisabled() {
      /** 先暫時打開 */
      this.caseFlowTypeDisabled = false
      return

      if (this.caseSetId == null) {
        this.caseFlowTypeDisabled = false
      } else {
        /** 以下幾個類型不能改 */
        const disabledEditType = ['SCA', 'URA', 'SA']
        this.caseFlowTypeDisabled = disabledEditType.includes(
          this.caseTypeComboBox.selectedVal
        )
      }
    },
    getFormData() {
      return {
        caseSetId: this.caseSetId,
        serviceTo: this.serviceToComboBox.selectedVal,
        caseSetName: this.caseSetName,
        caseType: this.caseTypeComboBox.selectedVal,
        caseFlowType: this.caseFlowTypeComboBox.selectedVal,
        linkType: this.linkTypeComboBox.selectedVal,
        linkUrl: this.linkUrl,
        // role: this.roleComboBox.selectedVal,
        // service: this.serviceComboBox.selectedVal,
        role: this.roleSelectList.map(x => x.value).join(','),
        service: this.serviceSelectList.map(x => x.value).join(','),
        organ: this.organComboBox.selectedVal,
        ownerOrgan: this.ownerOrganId,
        managerId: this.managerId,
        caseSetType: this.selectedServerList.join(',')
      }
    },
    async openPreviewFlowModal() {
      //await this.$refs.previewFlow.initData(this.caseTypeComboBox.selectedVal)
      this.$refs.previewFlowModal.show()
    },
    closePreviewFlowModal() {}
  }
}
</script>
<style lang="scss" scoped>
.link:hover {
  color: rgb(29, 142, 191);
}
.error_label {
  font-size: 14px;
  color: red;
  // position: absolute;
  white-space: nowrap;
}
.text-wrapper {
  white-space: pre-wrap;
}
</style>
