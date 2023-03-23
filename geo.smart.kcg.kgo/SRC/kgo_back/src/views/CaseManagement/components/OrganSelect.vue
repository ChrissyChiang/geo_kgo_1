<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  選擇機關
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <base-select
                    v-model="organComboBox.selectedVal"
                    :options="organComboBox.options"
                    search
                    :select="organComboBox.selectedVal"
                  />
                </div>
              </div>
            </div>
            <div class="row">
              <app-table
                :table-class="'table'"
                :columns="tableColumns"
                :data="tableData"
                :select-by="'organId'"
                :selected.sync="selectList"
                :show-pagination="true"
              ></app-table>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled ">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        data-dismiss="modal"
                      >
                        <i class="fa " aria-hidden="true"></i>
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="selectOrgan"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        儲存
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
  name: 'OrganSelect',
  mixins: [page],
  data() {
    return {
      selectList: [],
      organComboBox: {
        selectedVal: '',
        options: []
      },
      tableData: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '受理機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        }
      ]
    }
  },
  watch: {
    'organComboBox.selectedVal': {
      async handler() {
        if (this.organComboBox.selectedVal) {
          await this.getHome()
        }
      }
    }
  },
  async mounted() {
    const res = await this.$api.share.getOrganSelectHome({
      organId: this.organComboBox.selectedVal
    })
    const data = res.data.data
    if (data.organComboBox) {
      this.organComboBox = data.organComboBox
    }
  },
  methods: {
    async getHome() {
      const res = await this.$api.share.getOrganSelectHome({
        organId: this.organComboBox.selectedVal
      })
      const data = res.data.data
      this.tableData = data.grid
    },
    selectOrgan() {
      const selectList = this.tableData.filter(x =>
        this.selectList.includes(x.organId)
      )
      this.$emit('select-organ', selectList)
    },
    clear() {
      this.selectList = []
    }
  }
}
</script>
