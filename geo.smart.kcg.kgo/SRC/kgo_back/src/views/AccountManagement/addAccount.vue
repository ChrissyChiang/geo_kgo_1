<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                機關名稱
              </label>
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ organName }}</label>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                科室名稱
              </label>
              <div class="col-sm-8" style="padding-top:7px">
                <label>{{ unitName }}</label>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  姓名
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ name }}</label>
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  公務帳號
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <label>{{ account }}</label>
                </div>
              </div>
            </div>
          </div>
          <div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  電子郵件
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'email'">
                    <base-input
                      v-model="email"
                      :placeholder="'輸入電子郵件'"
                      input-length="80"
                    ></base-input>
                  </validate-container>
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  電話
                </label>
                <div class="col-sm-8">
                  <base-input
                    v-model="phone"
                    :placeholder="'輸入電話'"
                    input-length="20"
                  ></base-input>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <base-check-list
                v-model="selectedAuthRoles"
                :options="authRole"
                :selected-list="selectedAuthRoles"
              />
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
                      @click="saveAccount"
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
      id: '',
      organName: '',
      organId: '',
      unitName: '',
      unitId: '',
      name: '',
      account: '',
      email: '',
      officalEmail: '',
      phone: '',
      authRole: [],
      selectedAuthRoles: [],
      organNameOptions: {},
      unitNameOptions: {},
      tem: true
    }
  },
  mounted() {
    this.id = this.$route.params.id
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.editAccountHomeData({
          userId: this.id
        })
        this.organNameOptions = res.data.data.organComboBox
        let temO = res.data.data.organComboBox.options.find(
          item => item.value == res.data.data.organComboBox.selectedVal
        )
        this.organName = temO === undefined ? '' : temO.label
        this.organId = this.organNameOptions.selectedVal
        /* this.organName = res.data.data.organComboBox.options.find(
          item => item.value == res.data.data.organComboBox.selectedVal
        ).label*/
        this.unitNameOptions = res.data.data.unitComboBox
        /*this.unitName = res.data.data.unitComboBox.options.find(
          item => item.value == res.data.data.unitComboBox.selectedVal
        ).label*/
        let temU = res.data.data.unitComboBox.options.find(
          item => item.value == res.data.data.unitComboBox.selectedVal
        )
        this.unitId = this.unitNameOptions.selectedVal
        this.unitName = temU === undefined ? '' : temU.label
        this.authRole = res.data.data.checkBoxList.filter(
          item =>
            item.value == 'CASE_M' ||
            item.value == 'UNIT_U' ||
            item.value == 'UNIT_M' ||
            item.value == 'UNIT_A'
        )
        for (let i = 0; i < res.data.data.checkBoxList.length; i++) {
          if (
            res.data.data.checkBoxList[i].value == 'ADMIN' &&
            res.data.data.checkBoxList[i].selected == true
          ) {
            this.authRole.push({
              label: '系統管理者',
              value: 'ADMIN',
              groupKey: '',
              selected: true
            })
          }
        }
        res.data.data.checkBoxList.forEach(item => {
          if (item.selected) {
            this.selectedAuthRoles.push(item.value)
          }
        })

        this.name = res.data.data.name == null ? '' : res.data.data.name
        this.account = res.data.data.userId
        this.email = res.data.data.email == null ? '' : res.data.data.email
        this.officalEmail =
          res.data.data.publicEmail == null ? '' : res.data.data.publicEmail
        this.phone = res.data.data.tel == null ? '' : res.data.data.tel
      })
    },
    async saveAccount() {
      if (this.selectedAuthRoles.length <= 0) {
        this.notifySuccess('請至少選擇一個角色')
        return
      } else {
        let request = {
          organId: this.organId,
          unitId: this.unitId,
          roleId: this.selectedAuthRoles,
          name: this.name,
          userId: this.account,
          email: this.email,
          publicEmail: this.officalEmail,
          tel: this.phone
        }
        const res = await this.$api.management.saveAccount(request)
        this.notifySuccess(res.data.data.msg)
        this.goBack()
      }
    },
    goBack() {
      this.$router.push(`/accountManagement`)
    }
  }
}
</script>
