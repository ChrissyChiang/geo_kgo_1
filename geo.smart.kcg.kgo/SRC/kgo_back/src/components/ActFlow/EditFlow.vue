<template>
  <div>
    <div class="fsm-form">
      <validation-observer ref="observer">
        <div
          class="col-xs-12 form-group col-md-12"
          style="padding-left: 20px; padding-top: 10px"
        >
          <div class="col-xs-5 form-group col-md-5">
            <label for="input_event" class="col-sm-4 control-label">
              流程名稱
            </label>
            <div class="col-sm-8">
              <validate-container v-if="isEdit" :rules="'required'">
                <input
                  v-model="form.flowName"
                  type="text"
                  class="form-control"
                  placeholder="請輸入流程名稱"
                />
              </validate-container>
              <div v-if="!isEdit" class="read-only">{{ form.flowName }}</div>
            </div>
          </div>
          <div class="col-xs-7 form-group col-md-7">
            <label for="input_event" class="col-sm-2 control-label">
              流程說明
            </label>
            <div class="col-sm-10">
              <validate-container v-if="isEdit" :rules="'required'">
                <textarea
                  v-model="form.flowDesc"
                  type="text"
                  rows="4"
                  style="width: 100%;"
                  class="form-control"
                  placeholder="請輸入流程說明"
                />
              </validate-container>
              <div v-if="!isEdit" class="read-only">{{ form.flowDesc }}</div>
            </div>
          </div>
        </div>
      </validation-observer>
    </div>
    <div v-if="isEdit" class="fsm-form">
      <table class="table table-unstyled">
        <tbody>
          <tr colspan="4" align="center">
            <td>
              <button type="button" class="btn btn-fsm" @click="saveTask">
                <i class="fa fa-save" aria-hidden="true"></i>
                儲存
              </button>
              <button type="button" class="btn btn-fsm" @click="closeFlowEvent">
                <i aria-hidden="true" class="fa fsm-icon-refresh"></i>
                {{ closeText }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="col-xs-12 form-group col-md-12">
      <div v-if="isEdit" class="col-sm-1" style="">
        <draggable
          v-model="sourceList"
          class=""
          style=""
          tag="ul"
          :group="{ name: 'people', pull: 'clone', put: false }"
          :clone="cloneTask"
          v-bind="dragOptions"
          @start="isDragging = true"
          @end="isDragging = false"
        >
          <transition-group type="transition" name="flip-list">
            <div
              v-for="element in sourceList"
              :key="element.taskOrder"
              class="source-content"
            >
              <img
                v-if="element.taskType == '1'"
                src="@/assets/img/usergroup.png"
                width="50px"
              />
              <img
                v-if="element.taskType == '2'"
                width="50px"
                src="@/assets/img/user123.png"
              />
              <img
                v-if="element.taskType == '3'"
                width="50px"
                src="@/assets/img/user123.png"
              />
              <p class="word">
                {{ getTaskType(element.taskType) }}
                <!-- {{ element.taskType == '1' ? '會簽' : '簽核' }} -->
              </p>
            </div>
          </transition-group>
        </draggable>
      </div>
      <div :class="isEdit ? 'col-sm-11' : 'col-sm-12'">
        <span
          v-if="isShowTaskError && isEdit"
          style="color: red"
          v-html="errorMsg"
        ></span>
        <draggable
          v-model="targetList"
          class=""
          tag="ul"
          group="people"
          v-bind="dragOptions"
          :disabled="!isEdit"
          @start="isDragging = true"
          @end="isDragging = false"
        >
          <transition-group
            class="target-timeline people-content"
            type="transition"
            name="flip-list"
          >
            <li
              v-for="(element, index) in targetList"
              :key="element.taskOrder"
              :style="isEdit ? 'cursor: move' : ''"
            >
              <span class="rate-align"></span>
              <img
                v-if="element.taskType == '1'"
                src="@/assets/img/usergroup.png"
                width="70px"
              />
              <img
                v-if="element.taskType == '2' || element.taskType == '3'"
                src="@/assets/img/user123.png"
                width="70px"
              />
              <p class="rate-txt">
                節點名稱：{{
                  element.taskType == '3' ? '返回' : element.taskName
                }}
              </p>
              <div v-if="element.taskType != '3'">
                <p class="word user-name">
                  簽核：{{ findUserName(element.taskAssigneesCombox) }}
                </p>
              </div>
              <p
                v-if="element.taskType != '3' && isEdit && !isAdmin"
                style="color: red"
              >
                {{
                  validateTaskRequest(
                    element.taskAssigneesCombox,
                    element.taskType
                  )
                    ? ''
                    : element.taskType == 2
                    ? '請選擇簽核人員'
                    : '請選擇至少兩位會簽人員'
                }}
              </p>
              <button
                v-if="element.taskType != '3' && !isAdmin && isEdit"
                class="btn btn-primary"
                type="button"
                @click="edit(element, index)"
              >
                編輯
              </button>
              <button
                v-if="isEdit"
                class="icon btn-delete"
                @click="deleteItem(index)"
              >
                <img src="@/assets/img/btn-delete.png" class="img-responsive" />
              </button>
            </li>
          </transition-group>
        </draggable>
      </div>
    </div>
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import { page } from '@/mixins'
export default {
  components: {
    draggable
  },
  mixins: [page],
  props: {
    /** 編輯FlowId */
    editFlowId: {
      type: String,
      default: null
    },
    /** 關閉按鈕描述字 */
    closeText: {
      type: String,
      default: '返回'
    },
    /** 流程機關代碼 */
    flowOrganId: {
      type: String,
      default: ''
    },
    /** 是否複製 */
    isCopy: {
      type: Boolean,
      default: false
    },
    /** 可否編輯 */
    isEdit: {
      type: Boolean,
      default: true
    },
    /** 此流程是否為預設流程 */
    isDefault: {
      type: Boolean,
      default: false
    },
    /** 使用者列表 */
    usersOptions: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      form: {
        flowName: '',
        flowDesc: ''
      },
      //usersOptions: [],
      editIndex: 0,
      sourceList: [
        {
          taskSeq: null,
          taskOrder: 1,
          taskName: '',
          taskType: 1
        },
        {
          taskSeq: null,
          taskOrder: 2,
          taskName: '',
          taskType: 2
        },
        {
          taskSeq: null,
          taskOrder: 3,
          taskName: '',
          taskType: 3
        }
      ],
      targetList: [],
      errorMsg: '',
      isShowTaskError: false
    }
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: 'description',
        disabled: false,
        ghostClass: 'ghost'
      }
    }
  },
  watch: {
    targetList: {
      handler() {
        this.checkShowTaskError()
      },
      deep: true
    }
  },
  async mounted() {
    //await this.getHomeData()
  },
  methods: {
    initData(targetList, flowName, flowDesc) {
      this.targetList = targetList || []
      this.form.flowName = flowName
      this.form.flowDesc = flowDesc
    },
    // async getHomeData() {
    //   if (this.editFlowId) {
    //     await this.loadingContainer(async () => {
    //       const res = await this.$api.management.getTaskDetail({
    //         flowId: this.editFlowId
    //       })
    //       const data = res.data.data.tpiFlow
    //       this.targetList = data.flowTasks || []
    //       this.form.flowName = data.flowName
    //       this.form.flowDesc = data.flowDesc
    //     })
    //   }
    // },
    cloneTask({ taskName, taskType }) {
      if (taskType == 3) {
        this.targetList.push({
          taskSeq: null,
          taskOrder: this.targetList.length + 2,
          taskName: '任務節點',
          taskType: taskType,
          taskAssignees: '',
          taskAssigneesCombox: {
            options: []
          }
        })
      } else {
        return {
          taskSeq: null,
          taskOrder: this.targetList.length + 2,
          taskName: '任務節點',
          taskType: taskType,
          taskAssignees: '',
          taskAssigneesCombox: {
            options: []
          }
        }
      }
    },
    async edit(data, index) {
      this.$emit('edit-node', { ...data, editIndex: index })
    },
    deleteItem(index) {
      this.targetList.splice(index, 1)
    },
    closeFlowEvent() {
      this.$emit('close-flow')
    },
    sendNodeSaveData(data) {
      this.targetList[data.index].taskName = data.taskName
      this.targetList[data.index].taskType = data.taskType
      this.targetList[data.index].taskAssigneesCombox.options = [
        ...data.selectUserIds
      ]
    },
    findUserName(taskAssigneesCombox) {
      if (!taskAssigneesCombox) return ''
      const nameList = taskAssigneesCombox.options
        ? taskAssigneesCombox.options.map(x => x.label)
        : []
      let nameStr = nameList.join(',')
      if (nameStr.length > 20) {
        nameStr = nameStr.substr(0, 20) + '...'
      }
      return nameStr
    },
    closeEditFlowTaskModal() {
      this.$refs.nodeEdit.clear()
    },
    async saveTask() {
      this.checkShowTaskError()
      if (!(await this.$refs.observer.validate()) || this.isShowTaskError) {
        return
      }
      this.$emit('confirm-save-flow')
    },
    getSaveForm() {
      return {
        tpiFlow: {
          flowId: this.isCopy ? null : this.editFlowId,
          flowName: this.form.flowName,
          flowDesc: this.form.flowDesc,
          organId: this.flowOrganId,
          isDefault: this.isCopy ? false : this.isDefault,
          flowTasks: this.getFlowTasksSaveData()
        }
      }
    },
    getFlowTasksSaveData() {
      // 深層複製，避免影響畫面
      let tempData = JSON.parse(JSON.stringify(this.targetList))
      tempData.forEach((item, index) => {
        item.taskOrder = index + 1
        item.taskSeq = this.isCopy ? null : item.taskSeq
        item.taskAssignees = item.taskAssigneesCombox.options
          .map(x => x.value)
          .join(',')
      })
      return tempData
    },
    clear() {
      this.form.flowName = ''
      this.form.flowDesc = ''
      this.targetList = []
      this.$refs.observer.reset()
    },
    validateTaskRequest(taskAssigneesCombox, taskType) {
      if (taskType == 1) {
        return taskAssigneesCombox.options.length > 1
      } else {
        return taskAssigneesCombox.options.length == 1
      }
      return true
    },
    getTaskType(type) {
      switch (type) {
        case 1:
          return '會簽'
          break
        case 2:
          return '簽核'
          break
        case 3:
          return '返回'
          break
        default:
          return ''
          break
      }
    },
    /** 檢核錯誤 */
    checkShowTaskError() {
      this.isShowTaskError = false
      let isError = false
      let errMsgs = []
      if (this.targetList.length <= 1) {
        // isError = true
        // errMsgs.push('<p>請至少選擇兩關節點</p>')
      } else {
        if (this.targetList[0].taskType == 1) {
          isError = true
          errMsgs.push('<p>第一關請選擇簽核類型</p>')
        }

        if (this.targetList.length > 1) {
          if (this.targetList[this.targetList.length - 1].taskType == 1) {
            isError = true
            errMsgs.push('<p>最後一關請選擇簽核類型</p>')
          }
        }
        if (this.targetList.length > 1) {
          if (
            this.targetList[this.targetList.length - 1].taskType == 3 &&
            (this.targetList[this.targetList.length - 2].taskType == 1 ||
              this.targetList[this.targetList.length - 2].taskType == 3)
          ) {
            isError = true
            errMsgs.push('<p>倒數二關請選擇簽核類型</p>')
          }

          this.targetList.forEach((item, index) => {
            if (index < this.targetList.length - 1) {
              if (item.taskType == 3) {
                isError = true
                errMsgs.push('<p>返回請放置於最後一個節點</p>')
              }
            }
          })
        }
      }
      if (isError) {
        this.isShowTaskError = true
        this.errorMsg = errMsgs.join('')
      } else {
        this.isShowTaskError = false
        this.errorMsg = ''
      }
      if (!this.isAdmin) {
        this.targetList
          .filter(x => x.taskType != '3')
          .forEach(item => {
            let isTaskError = this.validateTaskRequest(
              item.taskAssigneesCombox,
              item.taskType
            )
            if (isTaskError == false) {
              this.isShowTaskError = true
            }
          })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.btn-blue {
  color: #fff !important;
  background-color: #007bff !important;
  border-color: #007bff !important;
}

.form-control {
  border-radius: 0px !important;
}

/deep/ .btn {
  border-radius: 5px !important;
}

.btn-blue:hover {
  color: #fff !important;
  background-color: #007bffaf !important;
  border-color: #007bffaf !important;
}

.btn-delete:hover {
  color: #fff !important;
  background-color: #f2f8feaf !important;
}
.flip-list-move {
  transition: transform 0.5s;
}
.no-move {
  transition: transform 0s;
}
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
.list-group {
  min-height: 20px;
}
.list-group-item {
  cursor: move;
}
.outside2 {
  width: 100% !important;
  height: 100% !important;
  // background-color: #FF0;
  width: 510px;
  overflow: auto;
  margin: 5px;
  padding: 10px;
}
#left1 {
  height: 100% !important;
  padding: 10px;
  width: 100px;
  background-color: rgb(203, 197, 197);
  float: left;
  margin-right: 5px;
}
#right1 {
  height: 100% !important;
  width: 90% !important;
  float: right;
}

.icon {
  border: 0 !important;
  padding: 0 !important;
  background-color: transparent !important;
  cursor: pointer !important;
}

.icon:focus {
  outline: 0 !important;
}
.word {
  color: black;
  font-size: 18px;
  text-align: center;
  padding: 5px;
}

.user-name {
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  //white-space: nowrap;
  //width: 200px;
}

.people-content {
  width: 100%;
  //   height: 100%;
  min-height: 14rem;
  padding: 20px;
  /* background-color: red; */
  border: 1px solid;
  border-radius: 5px !important;
  box-shadow: rgb(183, 173, 173) 5px 5px 5px 6px;
  border: 2px solid rgb(80, 83, 84);
}
.source-content {
  text-align: center;
  box-shadow: 5px 5px 5px 6px #b7adad;
  border: 2px solid #505354;
  border-radius: 5px !important;
  margin-bottom: 5px;
  cursor: move;
}

.form-control {
  border-radius: 5px !important;
}

.read-only {
  margin-top: 8px;
  color: black;
}

.error-people {
  margin-bottom: 5px;
}
</style>
