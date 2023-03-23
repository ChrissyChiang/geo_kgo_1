<template>
  <div>
    <edit-flow
      ref="editFlow"
      :edit-flow-id="$route.params.flowId || null"
      :users-options="usersOptions"
      :flow-organ-id="flowOrganId"
      :is-copy="isCopy"
      :is-default="isDefault"
      @close-flow="goBack"
      @edit-node="editNode"
      @confirm-save-flow="saveFlow"
    />
    <app-modal
      ref="nodeEditModal"
      :modal-title="'編輯節點'"
      @before-hide="closeEditFlowTaskModal"
    >
      <edit-node
        ref="nodeEdit"
        :flow-organ-id="flowOrganId"
        :user-source-list="usersOptions"
        :edit-index="editIndex"
        @node-confirm-Save="onNodeEditModalConfirmSave"
      ></edit-node>
    </app-modal>
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
  data() {
    return {
      usersOptions: [],
      editIndex: 0,
      flowOrganId: '',
      isCopy: false,
      isDefault: false
    }
  },
  async mounted() {
    this.isCopy = this.$route.params.isCopy
    this.isDefault = this.$route.params.isDefault
    await this.getFolwHomeData()
    await this.getTaker()
  },
  methods: {
    async getFolwHomeData() {
      if (this.$route.params.flowId) {
        await this.loadingContainer(async () => {
          const res = await this.$api.management.getTaskDetail({
            flowId: this.$route.params.flowId
          })
          const data = res.data.data.tpiFlow
          this.flowOrganId =
            data.organId || this.$store.state.user.userInfo.organId
          // this.targetList = data.flowTasks || []
          // this.form.flowName = data.flowName
          // this.form.flowDesc = data.flowDesc
          this.$refs.editFlow.initData(
            data.flowTasks,
            data.flowName,
            data.flowDesc
          )
        })
      } else {
        this.flowOrganId = this.$store.state.user.userInfo.organId
      }
    },
    goBack() {
      this.$router.push('/actFlowManager')
    },
    onNodeEditModalConfirmSave(data) {
      this.$refs.editFlow.sendNodeSaveData(data)
      this.$refs.nodeEditModal.hide()
    },
    async editNode(data) {
      this.editIndex = data.editIndex
      await this.$refs.nodeEdit.initData(
        data.taskAssigneesCombox,
        data.taskName,
        data.taskType
      )
      this.$refs.nodeEditModal.show()
    },
    async getTaker() {
      const res = await this.$api.share.getOrganUintUserSelectHome({
        organId: this.flowOrganId,
        unitId: ''
        //roleId: this.isCaseM ? 'CASE_M' : ''
      })
      const data = res.data.data
      const tableData = data.grid || []
      this.usersOptions = tableData.map(x => ({
        label: x.name,
        value: x.userId
      }))
    },
    closeEditFlowTaskModal() {
      this.$refs.nodeEdit.clear()
    },
    async saveFlow() {
      let success = false
      await this.loadingContainer(async () => {
        const res = await this.$api.management.saveActFlow(
          this.$refs.editFlow.getSaveForm()
        )
        success = this.checkResSuccess(res, false)
        if (success) {
          this.toastSuccess('儲存成功')
        }
      })
      if (success) {
        this.$router.push('/actFlowManager')
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
  height: 100vh;
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
</style>
