<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                選擇區域
              </label>
              <div class="col-sm-8">
                <base-check-list
                  v-model="selectAreaList"
                  :options="zipOptions"
                  :selected-list="selectAreaList"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled ">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button type="button" class="btn btn-fsm" @click="confirm">
                    <i class="fa fa-save" aria-hidden="true"></i>
                    確定
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'SelectArea',
  mixins: [page],
  data() {
    return {
      selectAreaList: [],
      zipOptions: [],
      editOrganId: null
    }
  },
  methods: {
    confirm() {
      const selectZips = this.zipOptions.filter(item => {
        return this.selectAreaList.find(x => x == item.value) != null
      })
      this.$emit('select-zips', { selectZips, editOrganId: this.editOrganId })
    },
    clear() {
      this.selectAreaList = []
    },
    async initData(countyId, organId) {
      this.editOrganId = organId
      this.selectAreaList = []
      this.zipOptions = []
      const res = await this.$api.share.getZipHome({
        countyId: countyId
      })
      const data = res.data.data
      this.zipOptions = data.zipCheckBox || []
    }
  }
}
</script>
