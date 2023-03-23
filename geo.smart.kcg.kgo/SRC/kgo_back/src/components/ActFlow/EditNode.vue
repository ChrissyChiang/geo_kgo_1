<template>
  <div class="fsm-form">
    <validation-observer ref="observer">
      <div class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label
            for="input_event"
            class="col-sm-2 control-label"
            style="padding-left: 16px"
          >
            節點名稱
          </label>
          <div class="col-sm-10" style="padding-top: 7px">
            <validate-container :rules="'required|max:20'">
              <input
                v-model="taskName"
                type="text"
                class="form-control formItem_control"
                placeholder="請輸入節點名稱"
              />
            </validate-container>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label
            for="input_event"
            class="col-sm-2 control-label"
            style="padding-left: 16px"
          >
            節點類型
          </label>
          <div class="col-sm-10" style="padding-top: 7px">
            <validate-container :rules="'required'">
              <base-select
                v-model="taskType"
                :select="taskType"
                required
                :options="typeOptions"
              />
            </validate-container>
          </div>
        </div>
      </div>
      <div v-if="!isAdmin" class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label
            for="input_event"
            class="col-sm-2 control-label"
            style="padding-left: 16px"
          >
            人員設定
          </label>
          <div class="col-sm-10" style="padding-top: 7px">
            <base-select
              v-model="organId"
              :options="organOptions"
              search
              required
              :select="organId"
            />
          </div>
        </div>
      </div>
      <div v-if="!isAdmin" class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label for="input_event" class="col-sm-2 control-label"></label>
          <div class="col-sm-10" style="padding-top: 7px">
            <validate-container
              :rules="`required${isMulti ? '|min_len:2' : ''}`"
            >
              <multi-select
                v-model="selectedUser"
                :value="selectedUser"
                :closedata="!isMulti"
                :show-selected-limit="10"
                :select-multiple="isMulti"
                :options="userSourceList"
              />
            </validate-container>
          </div>
        </div>
      </div>
      <div class="fsm-form">
        <table class="table table-unstyled">
          <tbody>
            <tr colspan="4" align="center">
              <td>
                <button data-dismiss="modal" type="button" class="btn btn-fsm">
                  <i aria-hidden="true" class="fa"></i>
                  取消
                </button>
                <button type="button" class="btn btn btn-primary" @click="save">
                  <i aria-hidden="true" class="fa fa-save"></i>
                  儲存
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </validation-observer>
  </div>
</template>
<script>
import { page } from '@/mixins'
import { cloneDeep } from 'lodash'
export default {
  name: 'EditNode',
  mixins: [page],
  props: {
    /** 流程機關代碼 */
    flowOrganId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      organId: '',
      isMulti: false,
      organOptions: [],
      userSourceList: [],
      selectedUser: [],
      taskName: '',
      taskType: '',
      // 頁面節點與連線資料
      data: {},
      editNodeId: '',
      // 全部簽核與會簽人員資料
      targetList: [],
      typeOptions: [
        {
          label: '分文',
          value: 'D'
        },
        {
          label: '陳核',
          value: 'A'
        },
        {
          label: '結案',
          value: 'F'
        },
        {
          label: '會簽',
          value: '1'
        }
      ]
    }
  },
  watch: {
    flowOrganId() {
      this.organId = this.flowOrganId
    },
    /*
    async organId() {
      await this.loadingContainer(async () => {
        if (this.taskType == '2') {
          this.selectedUser = null
        }
        //await this.getOrgan()
        await this.getAllOrgan()
        await this.getTaker()
      })
    },
    */
    async organId(val) {
      await this.loadingContainer(async () => {
        if (['D', 'A', 'F'].includes(this.taskType)) {
          this.selectedUser = null
        }
        await this.getTaker()
      })
    },
    async taskType(val) {
      if (!val) return
      // 如果是會簽就是多選
      this.isMulti = val === '1'
      this.selectedUser = val === '1' ? [] : null
      await this.loadingContainer(async () => {
        if (this.taskType === '1') {
          await this.getAllOrgan()
        } else {
          await this.getOrgan()
        }
        await this.getTaker()
      })
    }
  },
  async mounted() {
    //await this.getOrgan()
  },
  methods: {
    async initData(data) {
      this.data = data.data
      this.targetList = data.targetList
      this.editNodeId = data.editNodeId
      const findNode = this.data.nodeList.find(x => x.id == data.editNodeId)
      const findTaskAssignees = this.targetList.find(
        x => x.nodeId == data.editNodeId
      )
      if (findNode && findTaskAssignees) {
        this.taskType = findNode.type
        this.taskName = findTaskAssignees.taskName
        await this.delay(220)
        this.initSelectList(findTaskAssignees.taskAssigneesCombox)
      }
      // this.taskType = String(type)
      // await this.delay(220)
      // this.taskName = taskName
      // this.initSelectList(taskAssignees)
    },
    initSelectList(taskAssignees) {
      if (!taskAssignees) {
        this.selectedUser = null
        return
      }
      // if (taskAssignees.length == 0) {
      //   this.selectedUser = []
      //   return
      // }
      if (this.taskType === '1') {
        this.selectedUser = taskAssignees.options.map(x => ({
          label: x.label,
          value: x.value
        }))
      } else {
        if (taskAssignees.options.length > 0) {
          const userItem = taskAssignees.options[0]
          this.selectedUser = {
            label: userItem.label,
            value: userItem.value
          }
        } else {
          this.selectedUser = null
        }
      }
    },
    async getTaker() {
      const res = await this.$api.share.getOrganUintUserSelectHome({
        organId: this.organId,
        unitId: ''
        //roleId: this.isCaseM ? 'CASE_M' : ''
      })
      const data = res.data.data
      const tableData = data.grid || []
      this.userSourceList = tableData.map(x => ({
        label: x.name,
        value: x.userId
      }))
    },
    async getOrgan() {
      await this.loadingContainer(async () => {
        const resOrgan = await this.$api.share.getOrganSelectHome({
          organId: this.organId
        })
        const data = resOrgan.data.data
        if (data.organComboBox) {
          this.organOptions = data.organComboBox.options || []
          const firstValue =
            this.organOptions.length > 0 ? this.organOptions[0].value : ''
          this.organId = data.organComboBox.selectedVal || firstValue
        }
      })
    },
    async getAllOrgan() {
      await this.loadingContainer(async () => {
        const resOrgan = await this.$api.share.getAllOrganSelectHome({
          organId: this.organId
        })
        const data = resOrgan.data.data
        if (data.organComboBox) {
          this.organOptions = data.organComboBox.options || []
          const firstValue =
            this.organOptions.length > 0 ? this.organOptions[0].value : ''
          this.organId = data.organComboBox.selectedVal || firstValue
        }
      })
    },
    clear() {
      this.selectedUser = null
      this.taskType = ''
    },
    getAllUsersNameStr(selectUserOption) {
      if (selectUserOption.length == 0) return ''
      const nameList = selectUserOption.map(x => x.label)
      let nameStr = nameList.join(',')
      if (nameStr.length > 20) {
        nameStr = nameStr.substr(0, 30) + '...'
      }
      return nameStr
    },
    async save() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let selectUserOption = []
      if (this.taskType === '1') {
        selectUserOption = [...this.selectedUser]
      } else {
        selectUserOption =
          this.selectedUser != null
            ? [
                {
                  label: this.selectedUser.label,
                  value: this.selectedUser.value
                }
              ]
            : []
      }

      this.data.nodeList.forEach(node => {
        if (node.id == this.editNodeId) {
          // 如果是admin不用設置人員
          node.name = this.isAdmin
            ? ''
            : `人員: ${this.getAllUsersNameStr(selectUserOption)}`
          node.type = this.taskType
          node.taskName = this.taskName
        }
      })

      // 如果是admin不用設置人員
      this.targetList.forEach(item => {
        if (item.nodeId == this.editNodeId) {
          item.taskType = this.taskType
          item.taskName = this.taskName
          item.taskAssignees = this.isAdmin
            ? ''
            : selectUserOption.map(x => x.label).join(',')
          item.taskAssigneesCombox.options = this.isAdmin
            ? []
            : cloneDeep(selectUserOption)
        }
      })

      this.$emit('node-confirm-Save')
    }
  }
}
</script>
<style lang="scss" scoped>
.multiselect {
  width: 80% !important;
}

.control-label {
  padding-left: 17px;
}
</style>
