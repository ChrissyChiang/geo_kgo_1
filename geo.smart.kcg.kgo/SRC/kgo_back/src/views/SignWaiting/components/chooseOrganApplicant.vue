<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  選擇類別
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <base-select
                    v-model="organName"
                    :options="organNameOptions.options"
                    :required="true"
                    :select="organName"
                  />
                  <span v-if="organName == 'O'">
                    (已設定受理案件機關列表，且只能選擇一個機關)
                  </span>
                </div>
              </div>
            </div>
            <div v-if="organName == 'O'" class="fsm-form">
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-2 control-label">
                    機關名稱
                  </label>
                  <div class="col-sm-8">
                    <input
                      v-model="keyWordO"
                      class="form-control"
                      input-length="200"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div v-if="organName == 'O'" class="fsm-form">
              <app-table
                :table-class="'table'"
                :columns="organTableColumns"
                :data="organTableData"
                :select-by="'value'"
                :select-all="false"
                :select-title="'勾選'"
                :selected.sync="selectListO"
                :select-single="true"
                :show-pagination="true"
              ></app-table>
            </div>
            <div v-if="organName == 'A'" class="fsm-form">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  選擇機關
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <base-select
                    v-model="organId"
                    :options="organOptions.options"
                    :search="true"
                    required
                    :disabled="true"
                    :select="organId"
                  />
                </div>
              </div>
            </div>
            <div v-if="organName == 'A'" class="fsm-form">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  選擇科室
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <base-select
                    v-model="departmentId"
                    :options="departmentOptions.options"
                    search
                    :select="departmentId"
                  />
                  (僅顯示該機關承辦人員清單)
                </div>
              </div>
            </div>
            <div v-if="organName == 'A'" class="fsm-form">
              <div class="row">
                <div class="col-xs-12 form-group col-md-12">
                  <label for="input_event" class="col-sm-2 control-label">
                    承辦人姓名
                  </label>
                  <div class="col-sm-8">
                    <input
                      v-model="keyWordP"
                      class="form-control"
                      input-length="200"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div v-if="organName == 'A'" class="fsm-form">
              <app-table
                :table-class="'table'"
                :columns="takerTableColumns"
                :data="takerTableData"
                :select-by="'userId'"
                :selected.sync="selectListP"
                :select-all="false"
                :select-title="'勾選'"
                :select-single="true"
                :show-pagination="true"
              ></app-table>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="close()"
                      >
                        <i class="fa" aria-hidden="true"></i>
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="saveOrganApplicant()"
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
  name: 'ChooseOrganApplicant',
  mixins: [page],
  props: {
    organId: {
      type: String,
      default: ''
    },
    organList: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      id: '',
      keyWordP: '',
      keyWordO: '',
      organTableData: [], //機關清單
      takerTableData: [], ///承辦人清單
      organOptions: [], //機關下拉選單
      departmentOptions: [], //科室下拉選單
      departmentId: '',
      selectListP: [],
      selectListO: [],
      tempTableDataT: [],
      tempTableDataO: [],
      organName: 'O',
      organNameOptions: {}
    }
  },
  computed: {
    organTableColumns() {
      return [
        {
          title: '機關',
          dataIndex: 'label',
          tdClass: 'text-center'
        }
      ]
    },
    takerTableColumns() {
      return [
        {
          title: '機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'name',
          tdClass: 'text-center'
        }
      ]
    }
  },
  watch: {
    async organId() {
      await this.loadingContainer(async () => {
        await this.getUnit()
        this.getTaker()
      })
      this.departmentId = ''
    },
    async departmentId() {
      await this.loadingContainer(async () => {
        this.getTaker()
      })
    },
    keyWordP() {
      this.takerTableData = this.tempTableDataT.filter(item =>
        item.name.includes(this.keyWordP)
      )
      this.selectListP = []
    },
    keyWordO() {
      this.organTableData = this.tempTableDataO.filter(item =>
        item.label.includes(this.keyWordO)
      )
      this.selectListO = []
    }
  },
  mounted() {
    this.id = this.$route.params.id
  },
  methods: {
    async getData() {
      await this.loadingContainer(async () => {
        this.organNameOptions = {
          isShow: true,
          isDisabled: false,
          selectedVal: '',
          options: [
            { label: '機關', value: 'O', groupKey: '', selected: false },
            { label: '承辦人', value: 'A', groupKey: '', selected: false }
          ]
        }
        const resO = await this.$api.share.getOrganSelectHome({
          organId: this.organId
        })
        const res = await this.$api.share.getTaker({
          organId: this.organId,
          unitId: ''
        })
        this.organTableData = this.organList.options
        this.tempTableDataO = [...this.organTableData]
        this.organOptions = res.data.data.organComboBox
        this.getUnit()
      })
    },
    async getUnit() {
      const res = await this.$api.share.getUnit({ organId: this.organId })
      this.departmentOptions = res.data.data.unitComboBox
    },
    async getTaker() {
      const res = await this.$api.share.getTaker({
        organId: this.organId,
        unitId: this.departmentId || this.organId
      })
      this.takerTableData = res.data.data.grid
      this.tempTableDataT = [...this.takerTableData]
    },
    close() {
      this.clear()
      this.$emit('close-modal')
    },
    clear() {
      this.id = ''
      this.keyWordP = ''
      this.organTableData = [] //機關清單
      this.takerTableData = [] ///承辦人清單
      this.organOptions = [] //機關下拉選單
      this.departmentOptions = [] //科室下拉選單
      this.departmentId = ''
      this.selectListP = []
      this.selectListO = []
      this.tempTableDataT = []
      this.tempTableDataO = []
      this.organName = 'O'
      this.organNameOptions = {}
    },
    saveOrganApplicant() {
      if (this.organName == 'O') {
        if (this.selectListO.length <= 0) {
          this.notifySuccess('請選擇一個機關')
          return
        } else {
          this.$emit('save-organ-applicant', 'UNIT', this.selectListO[0])
        }
      } else {
        if (this.selectListP.length <= 0) {
          this.notifySuccess('請選擇一個承辦人')
          return
        } else {
          this.$emit('save-organ-applicant', 'OFFICER', this.selectListP[0])
        }
      }
      this.selectListO = []
      this.selectListP = []
    }
  }
}
</script>
