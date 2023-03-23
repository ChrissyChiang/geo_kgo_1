<template>
  <div class="demo-container">
    <div v-show="!isShow" class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  選擇機關
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <base-select
                    v-model="organComboBox.selectedVal"
                    :options="organComboBox.options"
                    required
                    search
                    :select="organComboBox.selectedVal"
                  />
                </div>
              </div>
            </div>
            <div v-if="false" class="fsm-form">
              <div class="row">
                <div class="col-xs-6 form-group col-md-6">
                  <label for="input_event" class="col-sm-4 control-label">
                    選擇縣市
                  </label>
                  <div class="col-sm-8">
                    <base-select
                      v-model="countyComboBox.selectedVal"
                      :options="countyComboBox.options"
                      disabled
                      :select="countyComboBox.selectedVal"
                    />
                  </div>
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
                :show-pagination="false"
              >
                <template v-slot:operating="{ data: { organId } }">
                  <button
                    type="button"
                    class="btn-line btn-danger"
                    @click="onSelectArea(organId)"
                  >
                    <i class="fa fa-trash-o" aria-hidden="true"></i>
                    選擇區域
                  </button>
                </template>
              </app-table>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        data-dismiss="modal"
                      >
                        <i class="fa" aria-hidden="true"></i>
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        data-dismiss="modal"
                        @click="confirm"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        確定
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
    <select-area
      v-show="isShow"
      ref="selectArea"
      @select-zips="selectZips"
    ></select-area>
  </div>
</template>
<script>
import { page } from '@/mixins'
import SelectArea from './SelectArea'
export default {
  name: 'SelectAcceptSetArea',
  components: {
    SelectArea
  },
  mixins: [page],
  data() {
    return {
      isShow: false,
      tableData: [],
      organComboBox: {
        selectedVal: '',
        options: []
      },
      countyComboBox: {
        selectedVal: '',
        options: []
      },
      selectList: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '區域',
          dataIndex: 'zipName',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    }
  },
  watch: {
    'organComboBox.selectedVal': {
      async handler() {
        await this.getHomeData(this.organComboBox.selectedVal)
      }
    }
  },
  async mounted() {
    await this.getHomeData('')
  },
  methods: {
    confirm() {
      const selectList = this.tableData.filter(x =>
        this.selectList.includes(x.organId)
      )
      this.$emit('select-area', selectList)
    },
    clear() {
      this.selectList = []
    },
    selectZips(data) {
      this.isShow = false
      const findData = this.tableData.find(x => x.organId == data.editOrganId)
      if (findData) {
        findData.zip = data.selectZips.map(x => x.value).join(',')
        findData.zipName = data.selectZips.map(x => x.label).join(',')
      }
    },
    onSelectArea(organId) {
      this.$refs.selectArea.initData('16', organId)
      this.isShow = true
    },
    closeSelectAreaModal() {
      this.$refs.selectArea.clear()
    },
    async getHomeData(setOrganId) {
      const res = await this.$api.share.getAreaOrgan({
        organId: setOrganId
      })
      const data = res.data.data
      let tempGrid = data.grid || []
      if (data.organComboBox) {
        this.organComboBox = data.organComboBox
      }

      if (data.countyComboBox) {
        this.countyComboBox = data.countyComboBox
      }

      this.tableData = tempGrid.map(item => ({
        organId: item.organId,
        organName: item.organName,
        zip: '',
        zipName: ''
      }))
    }
  }
}
</script>
