<template>
  <div class="fsm-form">
    <span>
      <div class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label for="input_event" class="col-sm-2 control-label">
            節點名稱
          </label>
          <div class="col-sm-10" style="padding-top: 7px">
            <input
              v-model="taskName"
              type="text"
              class="form-control formItem_control"
              placeholder="請輸入節點名稱"
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label for="input_event" class="col-sm-2 control-label">
            節點類型
          </label>
          <div class="col-sm-10" style="padding-top: 7px">
            <base-select
              v-model="taskType"
              :select="taskType"
              required
              :options="typeOptions"
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label for="input_event" class="col-sm-2 control-label">
            人員設定
          </label>
          <div class="col-sm-10" style="padding-top: 7px">
            <base-select
              v-model="organId"
              :options="organOptions"
              disabled
              required
              :select="organId"
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12 form-group col-md-12">
          <label for="input_event" class="col-sm-2 control-label"></label>
          <div class="col-sm-10" style="padding-top: 7px">
            <multi-select
              v-model="selectedUser"
              :value="selectedUser"
              :closedata="!isMulti"
              :show-selected-limit="10"
              :select-multiple="isMulti"
              :options="userSourceList"
            />
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
    </span>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'NodeEdit',
  mixins: [page],
  props: {
    userSourceList: {
      type: Array,
      default: () => []
    },
    editIndex: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      organId: '',
      isMulti: false,
      organOptions: [],
      usersOptions: [],
      selectedUser: [],
      taskName: '',
      taskType: '2',
      taskAssignees: '',
      typeOptions: [
        {
          label: '簽核',
          value: '2'
        },
        {
          label: '會簽',
          value: '1'
        }
      ]
    }
  },
  watch: {
    async organId() {
      await this.loadingContainer(async () => {
        this.selectedUser = this.taskType == '1' ? [] : null
      })
    },
    taskType(val) {
      // 如果是會簽多選
      this.isMulti = val == '1'
      this.selectedUser = val == '1' ? [] : null
    }
  },
  async mounted() {
    await this.getOrgan()
  },
  methods: {
    async initData(selectUserIds, taskName, type) {
      this.taskType = String(type)
      await this.delay(200)
      this.taskName = taskName
      this.initSelectList(selectUserIds)
    },
    initSelectList(userIds) {
      if (!userIds) {
        this.selectedUser = null
        return
      }
      if (userIds.length == 0) {
        this.selectedUser = []
        return
      }
      if (this.taskType == '1') {
        const userList = userIds.split(',')
        this.selectedUser = [
          ...this.userSourceList.filter(x => userList.includes(x.value))
        ]
      } else {
        const finduser = this.userSourceList.find(x => x.value == userIds)
        this.selectedUser = finduser ? { ...finduser } : null
      }
    },
    async getOrgan() {
      await this.loadingContainer(async () => {
        const resOrgan = await this.$api.share.getOrganSelectHome({
          organId: this.$store.state.user.userInfo.organId || ''
        })
        const data = resOrgan.data.data
        if (data.organComboBox) {
          this.organOptions = data.organComboBox.options || []
          this.organId = data.organComboBox.selectedVal
        }
      })
    },
    clear() {
      this.selectedUser = null
    },
    save() {
      let selectUserIds = ''
      if (this.taskType == '1') {
        selectUserIds = this.selectedUser.map(x => x.value).join(',')
      } else {
        selectUserIds = this.selectedUser != null ? this.selectedUser.value : ''
      }
      this.$emit('save', {
        index: this.editIndex,
        selectUserIds,
        taskName: this.taskName,
        taskType: Number(this.taskType)
      })
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
