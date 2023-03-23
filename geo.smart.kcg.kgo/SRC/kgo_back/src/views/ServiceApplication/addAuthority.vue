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
                    <span style="color: red; size: 18px">*</span>
                    審核者
                  </label>
                  <div class="col-sm-8">
                    <validate-container :rules="'required'">
                      <base-select
                        v-if="showManager"
                        v-model="reviewer"
                        search
                        required
                        :select="reviewer"
                        :options="reviewerOptions"
                      />
                      <base-select
                        v-else
                        v-model="reviewerManager"
                        search
                        required
                        :select="reviewerManager"
                        :options="reviewerManagerOptions"
                      />
                    </validate-container>
                  </div>
                </div>
              </div>
            </div>
            <!--<div class="row">
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
                      search
                      :select="reviewer"
                      :options="reviewerOptions"
                    />
                  </validate-container>
                </div>
              </div>
            </div>-->
            <div class="fsm-form">
              <div class="row">
                <div
                  class="col-xs-6 form-group col-md-12"
                  style="padding-left: 0px"
                >
                  <label
                    for="input_event"
                    class="col-sm-2 control-label"
                    style="padding-left: 0px"
                  >
                    <span style="color: red; size: 18px">*</span>
                    選擇角色
                  </label>
                  <div class="col-xs-10 form-group col-md-10">
                    <validate-container :rules="'required'">
                      <base-check-list
                        v-model="selectedAuthRoles"
                        :options="authRoleOptions"
                        :selected-list="selectedAuthRoles"
                        :disabled-by="'selected'"
                      />
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
                        type="button"
                        class="btn btn-fsm"
                        @click="saveAuthority"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        儲存
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
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      organName: '',
      organId: '',
      unitName: '',
      unitId: '',
      applicant: '',
      phone: '',
      email: '',
      reviewer: '',
      reviewerManager: '',
      reviewerOptions: [],
      reviewerManagerOptions: [],
      authRoleOptions: [],
      selectedAuthRoles: [],
      managerLabel: '',
      showManager: true
      // extraData: []
    }
  },
  computed: {
    dataList() {
      return this.extraData
    }
  },
  watch: {
    selectedAuthRoles() {
      //選擇的角色包含機關管理者->審核者顯示主管2
      if (this.selectedAuthRoles.indexOf('UNIT_M') >= 0) {
        this.showManager = false
      } else {
        this.showManager = true
      }
    }
  },
  async mounted() {
    await this.loadingContainer(async () => {
      await this.getHomeData()
    })
  },
  methods: {
    async getHomeData() {
      const res = await this.$api.management.addAuthorityApplication({
        caseSetId: this.id
      })
      const data = res.data.data
      //加入初始畫面api
      this.organName = data.organName || ''
      this.organId = data.organ || ''
      this.unitName = data.unitName || ''
      this.unitId = data.unit || ''
      this.applicant = data.applyUserName || ''
      this.applicantId = data.applyUser || ''
      this.reviewerOptions = data.reviewerComboBox
        ? data.reviewerComboBox.options
        : []
      this.reviewerManagerOptions = data.reviewer2ComboBox
        ? data.reviewer2ComboBox.options
        : []
      this.authRoleOptions = data.applyRoleCheckBox || []
      this.authRoleOptions.forEach(item => {
        if (item.selected) {
          this.selectedAuthRoles.push(item.value)
        }
      })
    },
    async saveAuthority() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let success = true
      await this.loadingContainer(async () => {
        let reviewer = this.showManager ? this.reviewer : this.reviewerManager
        let tem = this.showManager ? '機關管理者' : '系統管理者'
        const res = await this.$api.management.saveAuthorityApplication({
          applyRole: this.selectedAuthRoles.join(','),
          applyUser: this.applicantId,
          email: this.email,
          organ: this.organId,
          phone: this.phone,
          reviewer: reviewer,
          unit: this.unitId
        })
        success = this.checkResSuccess(res, false)
      })

      if (success) {
        this.toastSuccess('系統權限申請流程成功')
        this.$router.push(`/serviceApplication`)
      }
    },
    goBack() {
      this.$router.push(`/serviceApplication`)
    }
  }
}
</script>
