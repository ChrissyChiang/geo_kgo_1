<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <app-table
            :columns="tableColumns"
            :data="tableData"
            :draggable="false"
            :show-pagination="false"
          >
            <template v-slot:setting="{ data }">
              <div class="row" style="margin-left: 10px">
                <input
                  v-if="!isEnableType(data.type)"
                  v-model="data.value"
                  class="form-control formItem_control w-150 text-center mr-1"
                  input-length="20"
                  placeholder="0"
                  type="text"
                />
                <multi-select
                  v-if="data.type === 'FT'"
                  v-model="extSelectList"
                  :value="extSelectList"
                  style="width:50%"
                  :closedata="false"
                  :show-selected-limit="10"
                  :select-multiple="true"
                  :options="data.combox.options"
                />
                <base-select
                  v-if="data.type !== 'FT'"
                  v-model="data.combox.selectedVal"
                  :select="data.combox.selectedVal"
                  class="w-150 col-4"
                  :disabled="data.type == 'TO'"
                  required
                  :options="data.combox.options"
                />
              </div>
            </template>
          </app-table>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button type="button" class="btn btn-fsm" @click="save">
                    <i class="fa" aria-hidden="true"></i>
                    儲存
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
  mixins: [page],
  data() {
    return {
      tableData: [],
      extSelectList: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '類型',
          dataIndex: 'typeName',
          tdClass: 'text-center'
        },
        {
          title: '設定值',
          tdClass: 'text-left',
          slot: 'setting'
        }
      ]
    }
  },
  async mounted() {
    await this.loadingContainer(async () => {
      await this.getHomeData()
    })
  },
  methods: {
    /** 是不是只顯示下拉開關類型 */
    isEnableType(type) {
      const enableTypes = ['SUP', 'SUI', 'AC', 'FT']
      return enableTypes.includes(type)
    },
    async getHomeData() {
      const res = await this.$api.management.getParamSetHomeData()
      const data = res.data.data
      this.tableData = data.grid || []
      const filData = this.tableData.find(x => x.type === 'FT')
      if (filData) {
        this.extSelectList = filData.combox.options
          .filter(x => x.selected)
          .map(x => ({
            label: x.label,
            value: x.value
          }))
      }
    },
    getDetailType(type, selectedVal) {
      if (type == 'SUP' || type == 'SUI' || type == 'AC') {
        return 'B'
      } else if (type == 'FT') {
        return 'C'
      } else {
        return selectedVal
      }
    },
    getValue(type, value, selectedVal) {
      if (type == 'SUP' || type == 'SUI' || type == 'AC') {
        return selectedVal
      } else if (type == 'FT') {
        return this.extSelectList.map(x => x.value).join(',')
      } else {
        return value
      }
    },
    async save() {
      await this.loadingContainer(async () => {
        const saveRq = this.tableData.map(x => ({
          type: x.type,
          detailType: this.getDetailType(x.type, x.combox.selectedVal),
          // x.type !== 'SUP' && x.type !== 'SUI' ? x.combox.selectedVal : 'B',
          value: this.getValue(x.type, x.value, x.combox.selectedVal)
          // x.type !== 'SUP' && x.type !== 'SUI'
          //   ? x.value
          //   : x.combox.selectedVal
        }))
        const res = await this.$api.management.saveParamSetData({
          paramSetList: saveRq
        })
        if (this.checkResSuccess(res, false)) {
          this.toastSuccess('參數設定成功')
        }
      })
    }
  }
}
</script>
