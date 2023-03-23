<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                隸屬主分類
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="belongType.selectedVal"
                  :options="belongType.options"
                  :select="belongType.selectedVal"
                />
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  分類名稱
                </label>
                <div class="col-sm-8">
                  <validation-observer ref="observer">
                    <validate-container :rules="'required'">
                      <base-input
                        v-if="belongType.selectedVal != 'Organ'"
                        v-model="typeName"
                        :input-class="'form-control'"
                        :placeholder="'分類名稱'"
                        input-length="20"
                      ></base-input>
                      <base-select
                        v-if="belongType.selectedVal == 'Organ'"
                        v-model="organSelect"
                        :options="organNameOptions.options"
                        :search="true"
                        required
                        :select="organSelect"
                      />
                    </validate-container>
                  </validation-observer>
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
                      @click="saveType()"
                    >
                      <i class="fa fa-save" aria-hidden="true"></i>
                      儲存
                    </button>
                    <button type="button" class="btn btn-fsm" @click="goBack()">
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
      tableData: [],
      typeName: '',
      confirmContent: '',
      organSelect: '',
      organNameOptions: [],
      belongType: {}
    }
  },
  computed: {
    optionList() {
      return this.type.options
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getAddClassifyManagementHomeData(
          { seq: this.$route.params.id }
        )
        this.belongType = res.data.data.mainTypeComboBox
        this.organNameOptions = res.data.data.organComboBox
        if (this.$route.params.id != 'null') {
          this.typeName = res.data.data.name
          if (res.data.data.mainTypeComboBox.selectedVal == 'Organ') {
            let temSelect = this.organNameOptions.options.find(
              item => item.label == this.typeName
            )
            this.organSelect = temSelect.value
          }
        } else {
          this.belongType.selectedVal = this.belongType.options[0].value
        }
      })
    },
    async saveType() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = {}
      if (this.belongType.selectedVal == 'Organ') {
        let typeName = this.organNameOptions.options.find(
          item => item.value == this.organSelect
        )
        request = {
          seq: this.$route.params.id,
          name: typeName.label,
          mainType: this.belongType.selectedVal,
          organId: this.organSelect
        }
      } else {
        request = {
          seq: this.$route.params.id,
          name: this.typeName,
          mainType: this.belongType.selectedVal,
          organId: ''
        }
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.addClassify(request)
        this.notifySuccess(res.data.data.msg)
      })
    },
    goBack() {
      this.$router.push(`/classifyManagement`)
    }
  }
}
</script>
