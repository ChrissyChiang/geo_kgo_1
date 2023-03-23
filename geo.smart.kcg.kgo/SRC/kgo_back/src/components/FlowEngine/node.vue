<template>
  <div
    ref="node"
    :style="nodeContainerStyle"
    :class="nodeTypeCss"
    @click="clickNode"
    @mouseup="changeNodeSite"
  >
    <!-- 人頭Icon -->
    <div
      v-if="['1', 'D', 'A', 'F', '3'].includes(node.type)"
      class="flow-node-left"
    >
      <img
        v-if="node.type === '1'"
        src="@/assets/img/usergroup.png"
        width="40px"
        height="40px"
      />
      <img
        v-if="['D', 'A', 'F'].includes(node.type)"
        src="@/assets/img/user123.png"
        width="40px"
        height="40px"
      />
      <img
        v-if="node.type === '3'"
        src="@/assets/img/refreshing.png"
        width="40px"
        height="40px"
      />
    </div>
    <!-- 節點類型的圖標 -->
    <div class="flow-node-left-ico flow-node-drag">
      <!-- fa fa-share-alt -->
      <i :class="nodeIcoClass"></i>
    </div>
    <!-- 節點名稱 -->
    <div class="flow-node-text" :show-overflow-tooltip="true">
      <p v-if="isShowTaskName">節點名稱：{{ node.taskName }}</p>
      <p v-if="isShowTaskName">節點類型：{{ getTaskTypeName(node.type) }}</p>
      {{ node.name }}
      <p
        v-if="['1', 'D', 'A', 'F'].includes(node.type) && isEdit && !isAdmin"
        style="color: red"
      >
        {{ validateTaskRequestMsg() }}
      </p>
    </div>
    <!-- 節點狀態圖標 -->
    <div class="node-right-ico">
      <i
        v-if="isEdit"
        class="fa fa-trash-o delete-icon node-icon"
        @click="deleteNode"
      ></i>
      <!-- 不是管理者＆不是返回並且是編輯模式才顯示編輯 -->
      <!-- 是管理者＆是流程文字類型並且是編輯模式才顯示編輯 -->
      <i
        v-if="node.type !== '3' && isEdit"
        class="fa fa-pencil-square-o node-icon"
        @click="editNode"
      ></i>
    </div>
  </div>
</template>

<script>
import { page } from '@/mixins'
import { getNodeTypeName } from './utils'
import 'jquery-ui/ui/widgets/resizable'
export default {
  mixins: [page],
  props: {
    node: Object,
    activeElement: Object,
    users: {
      type: Array,
      default: () => []
    },
    /** 可否編輯 */
    isEdit: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {}
  },
  computed: {
    isShowTaskName() {
      const showTaskTypes = ['1', 'D', 'A', 'F']
      return showTaskTypes.includes(this.node.type)
    },
    nodeTypeCss() {
      switch (this.node.type) {
        case '1':
          return this.nodeMultiApprovedContainerClass
          break
        case 'D':
        case 'A':
        case 'F':
          return this.nodeApprovedContainerClass
          break
        case '3':
          return this.nodeBackContainerClass
          break
        default:
          return this.nodeContainerClass
          break
      }
    },
    nodeContainerClass() {
      return {
        'flow-node-container': true,
        'flow-node-active':
          this.activeElement.type == 'node'
            ? this.activeElement.nodeId === this.node.id
            : false
      }
    },
    nodeApprovedContainerClass() {
      return {
        'flow-node-approved-container': true,
        'flow-node-approved-active':
          this.activeElement.type == 'node'
            ? this.activeElement.nodeId === this.node.id
            : false
      }
    },
    nodeMultiApprovedContainerClass() {
      return {
        'flow-node-multi-approved-container': true,
        'flow-node-multi-approved-active':
          this.activeElement.type == 'node'
            ? this.activeElement.nodeId === this.node.id
            : false
      }
    },
    nodeBackContainerClass() {
      return {
        'flow-node-back-container': true,
        'flow-node-back-active':
          this.activeElement.type == 'node'
            ? this.activeElement.nodeId === this.node.id
            : false
      }
    },
    // 節點容器樣式(節點放置位置)
    nodeContainerStyle() {
      return {
        top: this.node.top,
        left: this.node.left,
        width: this.node.width,
        height: this.node.height
      }
    },
    nodeIcoClass() {
      var nodeIcoClass = {}
      nodeIcoClass['fa fa-share-alt'] = true
      // 添加該class可以拖曳連線出來，viewOnly可以控制節點是否可編輯
      // nodeIcoClass['flow-node-drag'] = this.node.viewOnly ? false : true
      nodeIcoClass['flow-node-drag'] = this.isEdit ? true : false
      return nodeIcoClass
    }
  },
  mounted() {
    this.$nextTick(() => {
      $(document).ready(() => {
        if (this.isEdit) {
          /* global jsPlumb, $ */
          $(this.$refs.node).resizable({
            resize: (event, ui) => {
              this.updateNodeLocation()
              this.$emit('repaintEverything')
            },
            handles: 'all'
          })
        }
      })
    })
  },
  methods: {
    // 點擊節點
    clickNode() {
      this.$emit('clickNode', this.node.id)
    },
    deleteNode() {
      this.$emit('delete-node', this.node.id)
    },
    editNode() {
      this.$emit('edit-node', this.node.id)
    },
    getTaskTypeName(type) {
      return getNodeTypeName(type)
    },
    validateTaskRequestMsg() {
      if (this.node.type === '1') {
        return this.users.length > 1 ? '' : '請選擇至少兩位會簽人員'
      }

      if (['D', 'A', 'F'].includes(this.node.type)) {
        return this.users.length == 1
          ? ''
          : `請選擇${getNodeTypeName(this.node.type)}人員`
        return
      }
      return ''
    },
    // 滑鼠移動後抬起
    changeNodeSite() {
      // 避免抖動
      if (
        this.node.left == this.$refs.node.style.left &&
        this.node.top == this.$refs.node.style.top
      ) {
        return
      }
      this.updateNodeLocation()
    },
    updateNodeLocation() {
      this.$emit('changeNodeSite', {
        nodeId: this.node.id,
        left: this.$refs.node.style.left,
        top: this.$refs.node.style.top,
        width: this.$refs.node.style.width,
        height: this.$refs.node.style.height
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.node-icon {
  cursor: pointer;
  color: rgba(0, 0, 0, 0.776);
}
.delete-icon {
  color: red !important;
}
.node-right-ico {
  line-height: 32px;
  display: table;
  width: 25px;
  // right: 5px;
  // cursor: default;
}
</style>
