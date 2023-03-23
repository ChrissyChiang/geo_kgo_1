<template>
  <div>
    <flow-panel
      ref="editFlow"
      :edit-flow-id="$route.params.flowId || null"
      :flow-organ-id="flowOrganId"
      :is-copy="isCopy"
      :is-default="isDefault"
      @close-flow="goBack"
      @open-desc-edit="openDescEdit"
      @open-node-edit="openNodeEdit"
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
        :edit-node-id="editNodeId"
        @node-confirm-Save="onNodeEditModalConfirmSave"
      ></edit-node>
    </app-modal>
    <app-modal
      ref="descEditModal"
      :modal-title="'編輯說明'"
      @before-hide="closeDescEditModal"
    >
      <edit-desc ref="descEdit" @confirm-save-desc="onSaveDesc"></edit-desc>
    </app-modal>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  components: {},
  mixins: [page],
  data() {
    return {
      editNodeId: null,
      flowOrganId: '',
      isCopy: false,
      isDefault: false
    }
  },
  async mounted() {
    this.isCopy = this.$route.params.isCopy
    this.isDefault = this.$route.params.isDefault
  },
  methods: {
    goBack() {
      this.$router.push('/actFlowManager')
    },
    onNodeEditModalConfirmSave() {
      this.$refs.nodeEditModal.hide()
      this.$refs.editFlow.repaintEverything()
    },
    async openNodeEdit(data) {
      this.editNodeId = data.editNodeId
      this.flowOrganId = data.flowOrganId
      await this.$refs.nodeEdit.initData(data)
      this.$refs.nodeEditModal.show()
    },
    closeEditFlowTaskModal() {
      this.$refs.nodeEdit.clear()
    },
    openDescEdit(data) {
      console.log(data)
      this.$refs.descEdit.initData(data)
      this.$refs.descEditModal.show()
    },
    onSaveDesc() {
      this.$refs.descEditModal.hide()
      this.$refs.editFlow.repaintEverything()
    },
    closeDescEditModal() {},
    async saveFlow() {
      console.log(this.$refs.editFlow.getSaveForm())
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
