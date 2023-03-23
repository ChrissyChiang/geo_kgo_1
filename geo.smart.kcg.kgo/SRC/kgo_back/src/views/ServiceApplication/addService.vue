<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    機關
                  </label>
                  <div class="col-sm-8" style="padding-top: 7px">
                    <label>{{ organName }}</label>
                  </div>
                </div>

                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    科室
                  </label>
                  <div class="col-sm-8" style="padding-top: 7px">
                    <label>{{ unitName }}</label>
                  </div>
                </div>
              </div>
            </div>
            <div>
              <div class="row">
                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    申請人
                  </label>
                  <div class="col-sm-8" style="padding-top: 7px">
                    <label>{{ applicant }}</label>
                  </div>
                </div>
                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    聯絡電話
                  </label>
                  <div class="col-sm-8" style="padding-top: 7px">
                    <base-input
                      v-model="phone"
                      :placeholder="'請輸入聯絡電話'"
                      input-length="20"
                    ></base-input>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  電子郵件
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <validate-container :rules="'email'">
                    <base-input
                      v-model="email"
                      :placeholder="'輸入電子郵件'"
                      input-length="50"
                    ></base-input>
                  </validate-container>
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  <span style="color: red; size: 18px">*</span>
                  審核主管
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <base-select
                      v-model="reviewer"
                      :select="reviewer"
                      :options="reviewerOptions"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <table class="table table-unstyled">
                <tbody>
                  <tr>
                    <th>
                      <span style="color: red; size: 18px">*</span>
                      作業流程
                    </th>
                    <td colspan="2">
                      <validate-container :rules="'required'">
                        <base-select
                          v-model="caseTypeComboBox.selectedVal"
                          :options="caseTypeComboBox.options"
                          required
                          :select="caseTypeComboBox.selectedVal"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr>
                    <th>
                      <span style="color: red; size: 18px">*</span>
                      案件名稱
                    </th>
                    <td colspan="2">
                      <validate-container :rules="'required'">
                        <input
                          v-model="caseSetName"
                          class="form-control"
                          type="text"
                          placeholder="請輸入案件名稱"
                        />
                      </validate-container>
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
                    <th>
                      <span style="color: red; size: 18px">
                        {{ checkRequiredCss() }}
                      </span>
                      站外連結方式
                    </th>
                    <td colspan="2">
                      <validate-container
                        :rules="
                          caseFlowTypeComboBox.selectedVal == 'A'
                            ? 'required'
                            : ''
                        "
                      >
                        <base-select
                          v-model="linkTypeComboBox.selectedVal"
                          :options="linkTypeComboBox.options"
                          :select="linkTypeComboBox.selectedVal"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr>
                    <th>
                      <span style="color: red; size: 18px">
                        {{ checkRequiredCss() }}
                      </span>
                      站外連結網址
                    </th>
                    <td colspan="3">
                      <validate-container
                        :rules="isNeedLinkURL ? 'required|url' : 'url'"
                      >
                        <input
                          v-model="linkUrl"
                          class="form-control"
                          type="text"
                          placeholder="請輸入站外連結網址"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr>
                    <th>機關分類</th>
                    <td colspan="2">
                      <validate-container :rules="'required'">
                        <base-select
                          v-model="organComboBox.selectedVal"
                          :options="organComboBox.options"
                          search
                          :select="organComboBox.selectedVal"
                        />
                      </validate-container>
                    </td>
                  </tr>
                  <tr>
                    <th>角色分類</th>
                    <td colspan="2">
                      <base-select
                        v-model="roleComboBox.selectedVal"
                        :options="roleComboBox.options"
                        :select="roleComboBox.selectedVal"
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>服務分類</th>
                    <td colspan="2">
                      <base-select
                        v-model="serviceComboBox.selectedVal"
                        :options="serviceComboBox.options"
                        :select="serviceComboBox.selectedVal"
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>權責機關</th>
                    <td colspan="2">
                      {{ ownerOrganName }}
                    </td>
                  </tr>
                  <tr>
                    <th>
                      <span style="color: red; size: 18px">
                        {{ checkRequiredCss() }}
                      </span>
                      案件管理者
                    </th>
                    <td colspan="2">
                      <validate-container
                        :rules="isManagerIdRequired ? 'required' : ''"
                      >
                        <input
                          class="form-control"
                          type="text"
                          disabled
                          :value="managerName"
                          placeholder=""
                        />
                      </validate-container>
                    </td>
                    <td width="14%" valign="top">
                      <button
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
                        :options="caseSetTypeCheckList"
                        :selected-list="selectedServerList"
                      />
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="saveService"
                      >
                        <i class="fa fa-send" aria-hidden="true"></i>
                        送出申請
                      </button>
                      <button type="button" class="btn btn-fsm" @click="goBack">
                        <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                        返回
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </validation-observer>
        </div>
      </div>
    </div>
    <app-modal ref="choosePeopleTab1Modal" :modal-title="'請指定案件管理者'">
      <choose-officer
        ref="choosePeopleTab1"
        :organ-options="organOptions"
        :is-case-m="true"
        :unit-options="unitOptions"
        @close-modal="closeChoose"
        @decide-people="showDecidePeople"
      ></choose-officer>
    </app-modal>
  </div>
</template>
<script>
import { page } from '@/mixins'
import ChoosePeople from './components/choosePeople.vue'
export default {
  components: { ChoosePeople },
  mixins: [page],
  data() {
    return {
      organName: '',
      organId: '',
      organOptions: {},
      unitName: '',
      unitId: '',
      unitOptions: {},
      applicant: '',
      applicantId: '',
      phone: '',
      email: '',
      reviewer: '',
      reviewerOptions: [],
      // integrationAgency: '',
      //linkType: '',
      //organClassify: '',
      //roleClassify: '',
      //serviceCalssify: '',
      // powerOrgan: '',
      // powerOrganOptions: {},
      //managerName: '',
      //managerIdList: '',

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
      /** 服務分類 */
      serviceComboBox: {
        selectedVal: '',
        options: []
      },
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
      caseSetTypeCheckList: [],
      /** 已勾選服務啟用清單 */
      selectedServerList: []
    }
  },
  computed: {
    /** 站外連結網址是否必填 */
    isNeedLinkURL() {
      const requireds = ['A', 'B1', 'B2']
      return requireds.includes(this.caseFlowTypeComboBox.selectedVal)
    },
    /** 案件管理者是否必填 */
    isManagerIdRequired() {
      const requireds = ['B3']
      return requireds.includes(this.caseFlowTypeComboBox.selectedVal)
    }
  },
  async mounted() {
    await this.loadingContainer(async () => {
      this.getHomeData()
    })
  },
  methods: {
    checkRequiredCss() {
      return this.caseFlowTypeComboBox.selectedVal == 'A' ? `*` : ''
    },
    async getHomeData() {
      const res = await this.$api.management.addServiceApplication({})
      const data = res.data.data
      this.organName = data.applyOrganName || ''
      this.organId = data.applyOrgan || ''
      this.unitName = data.applyUnitName || ''
      this.unitId = data.applyUnit || ''
      this.applicant = data.applyUserName || ''
      this.applicantId = data.applyUser || ''
      this.reviewerOptions = data.reviewerComboBox
        ? data.reviewerComboBox.options
        : []
      if (data.serviceToComboBox) {
        this.serviceToComboBox = data.serviceToComboBox
      }

      if (data.caseTypeComboBox) {
        this.caseTypeComboBox = data.caseTypeComboBox
      }

      if (data.caseFlowTypeComboBox) {
        this.caseFlowTypeComboBox = data.caseFlowTypeComboBox
      }

      if (data.linkTypeComboBox) {
        this.linkTypeComboBox = data.linkTypeComboBox
      }

      if (data.organComboBox) {
        this.organComboBox = data.organComboBox
      }

      if (data.roleComboBox) {
        this.roleComboBox = data.roleComboBox
      }

      if (data.serviceComboBox) {
        this.serviceComboBox = data.serviceComboBox
      }
      if (data.ownerOrganComboBox) {
        this.ownerOrganComboBox = data.ownerOrganComboBox
        const findSelectData = this.ownerOrganComboBox.options.find(
          x => x.selected == true
        )
        this.ownerOrganName = findSelectData ? findSelectData.label : ''
        this.ownerOrganId = findSelectData ? findSelectData.value : ''
      }

      if (
        data.caseSetTypeCheckList &&
        Array.isArray(data.caseSetTypeCheckList)
      ) {
        this.caseSetTypeCheckList = data.caseSetTypeCheckList || []
      }
    },
    openChoosePeopleTab1Modal() {
      this.$refs.choosePeopleTab1Modal.show()
      this.$refs.choosePeopleTab1.getHomeData()
    },
    showDecidePeople(selectData) {
      this.managerName = selectData.selectedTable.map(x => x.name).join(',')
      this.managerId = selectData.selectedTable.map(x => x.userId).join(',')

      this.ownerOrganName = selectData.selectedOrganName
      this.ownerOrganId = selectData.selectedOrganId
      this.$refs.choosePeopleTab1Modal.hide()
    },
    async saveService() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = {
        applyOrgan: this.organId,
        applyUnit: this.unitId,
        applyUser: this.applyUser,
        caseFlowType: this.caseFlowTypeComboBox.selectedVal,
        caseSetName: this.caseSetName,
        caseSetType: this.selectedServerList.join(','),
        caseType: this.caseTypeComboBox.selectedVal,
        email: this.email,
        linkType: this.linkTypeComboBox.selectedVal,
        linkUrl: this.linkUrl,
        manager: this.managerId,
        organ: this.organComboBox.selectedVal,
        ownerOrgan: this.ownerOrganId,
        phone: this.phone,
        reviewer: this.reviewer,
        role: this.roleComboBox.selectedVal,
        service: this.serviceComboBox.selectedVal
      }
      let success = true
      await this.loadingContainer(async () => {
        const res = await this.$api.management.saveServiceApplication(request)
        success = this.checkResSuccess(res, false)
      })
      if (success) {
        this.toastSuccess('服務案件新增流程成功')
        this.$router.push(`/serviceApplication`)
      }
    },
    goBack() {
      this.$router.push(`/serviceApplication`)
    },
    closeChoose() {
      this.$refs.choosePeopleTab1Modal.hide()
    }
  }
}
</script>
<style lang="scss" scoped>
table th {
  width: 16%;
  padding: 0.4rem;
}
table td {
  padding: 0.4rem;
}
</style>
