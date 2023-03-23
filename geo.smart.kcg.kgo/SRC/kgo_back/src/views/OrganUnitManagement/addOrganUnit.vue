<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  {{ label1 }}
                </label>
                <div class="col-sm-8">
                  <base-select
                    v-model="organName"
                    :options="organNameOptions.options"
                    :select="organName"
                    search
                  />
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  {{ label2 }}
                </label>
                <div class="col-sm-8">
                  <validate-container :rules="'required'">
                    <base-input
                      v-model="unitId"
                      :disabled="id != '' ? true : false"
                      :placeholder="'輸入機關代碼'"
                      input-length="50"
                    ></base-input>
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    {{ label3 }}
                  </label>
                  <div class="col-sm-8">
                    <validate-container :rules="'required'">
                      <base-input
                        v-model="unitName"
                        :placeholder="'輸入機關名稱'"
                        input-length="50"
                      ></base-input>
                    </validate-container>
                  </div>
                </div>

                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    類別
                  </label>
                  <div class="col-sm-8">
                    <label style="padding-top:7px">{{ label4 }}</label>
                  </div>
                </div>
              </div>
            </div>
            <div></div>
            <div class="fsm-form">
              <table class="table table-unstyled">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="saveOrganUnit()"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        儲存
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="goBack()"
                      >
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
      id: '',
      type: '',
      organName: '',
      unitId: '',
      unitName: '',
      organNameOptions: {},
      label1: '上層機關',
      label2: '機關代碼',
      label3: '機關名稱',
      label4: '機關'
    }
  },
  mounted() {
    let check = this.$route.params.id.split('-')
    this.id = check[1] == 'null' ? '' : check[1]
    this.type = check[0]
    // this.id = this.$route.params.id == 'null' ? '' : this.$route.params.id
    // this.type = this.$route.params.type
    let request
    if (this.type == 'organ') {
      this.label1 = '上層機關'
      this.label2 = '機關代碼'
      this.label3 = '機關名稱'
      this.label4 = '機關'
    } else {
      this.label1 = '機關名稱'
      this.label2 = '科室代碼'
      this.label3 = '科室名稱'
      this.label4 = '科室'
    }
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      let request =
        this.type == 'organ'
          ? { organId: this.id, unitId: '', type: 'organ' }
          : { organId: '', unitId: this.id, type: 'unit' }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.editOrganUnitManagementHomeData(
          request
        )

        if (this.type == 'organ') {
          this.organNameOptions = res.data.data.parentOrganComboBox
          this.unitId = res.data.data.organId
          this.unitName = res.data.data.organName
          this.organName = res.data.data.parentOrganComboBox.selectedVal
        } else {
          this.organNameOptions = res.data.data.organComboBox
          this.unitId = res.data.data.unitId
          this.unitName = res.data.data.unitName
          this.organName =
            res.data.data.organComboBox.selectedVal == ''
              ? res.data.data.organComboBox.options[0].value
              : res.data.data.organComboBox.selectedVal
        }
      })
    },
    async saveOrganUnit() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = {}
      if (this.type == 'organ') {
        //機關
        request = {
          organId: this.unitId,
          parentOrganId: this.organName,
          organName: this.unitName,
          unitId: '',
          unitName: ''
        }
      } else {
        request = {
          organId: this.organName,
          parentOrganId: '',
          organName: '',
          unitId: this.unitId,
          unitName: this.unitName
        }
      }
      const res = await this.$api.management.editOrganUnitManagement(request)
      this.notifySuccess(res.data.data.msg)
    },
    goBack() {
      this.$router.push(`/organUnitManagement`)
    }
  }
}
</script>
