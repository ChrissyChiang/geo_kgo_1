<template>
  <div>
    <validation-observer ref="observer">
      <table class="table table-unstyled">
        <tbody>
          <tr align="center">
            <td>流程名稱</td>
            <td style="width: 30%">
              <validate-container v-if="isEdit" :rules="'required'">
                <input
                  v-model="form.flowName"
                  type="text"
                  class="form-control"
                  maxlength="30"
                  placeholder="請輸入流程名稱"
                />
              </validate-container>
              <div v-if="!isEdit" class="read-only">{{ form.flowName }}</div>
            </td>
            <td>流程說明</td>
            <td style="width: 40%">
              <validate-container v-if="isEdit" :rules="'required|max:200'">
                <textarea
                  v-model="form.flowDesc"
                  type="text"
                  rows="2"
                  style="width: 100%"
                  class="form-control"
                  placeholder="請輸入流程說明"
                />
              </validate-container>
              <div v-if="!isEdit" class="read-only">{{ form.flowDesc }}</div>
            </td>
            <td v-if="isEdit">
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
      <div
        v-if="isShowTaskError && isEdit"
        class="approved-error"
        v-html="errorMsg"
      ></div>
    </validation-observer>
    <div v-if="flowVisible" style="height: calc(100vh)">
      <div style="display: flex; height: calc(100%)">
        <div
          v-if="isEdit"
          style="width: 180px; border-right: 1px solid #dce3e8"
        >
          <node-menu
            ref="nodeMenu"
            @delete-line="deleteSelectLine"
            @addNode="addNode"
          ></node-menu>
        </div>
        <div
          id="flowContainer"
          ref="flowContainer"
          v-flowDrag
          class="container"
        >
          <template v-for="node in data.nodeList">
            <flow-node
              :id="node.id"
              :key="node.id"
              :node="node"
              :users="getTaskUsers(node.id)"
              :is-edit="isEdit"
              :active-element="activeElement"
              @changeNodeSite="changeNodeSite"
              @repaintEverything="repaintEverything"
              @delete-node="onNodeToDelete"
              @nodeRightMenu="nodeRightMenu"
              @edit-node="editNode"
              @clickNode="clickNode"
            ></flow-node>
          </template>
          <!-- 給畫布一個預設的寬度和高度 -->
          <div style="position: absolute; top: 3000px; left: 2000px">
            &nbsp;
          </div>
        </div>
        <!-- 右側表單 -->
        <div
          v-if="false"
          style="
            width: 250px;
            border-left: 1px solid #dce3e8;
            background-color: #fbfbfb;
          "
        >
          <flow-node-form
            ref="nodeForm"
            @setLineLabel="setLineLabel"
            @repaintEverything="repaintEverything"
          ></flow-node-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '@/components/FlowEngine/index.css'
import draggable from 'vuedraggable'
import { jsPlumb } from 'jsplumb'
import { flowMixin } from '@/components/FlowEngine/mixins'
import { page } from '@/mixins'
import flowNode from '@/components/FlowEngine/node'
import nodeMenu from '@/components/FlowEngine/node_menu'
import lodash from 'lodash'
import { hasLine, hashOppositeLine, getNodeTypeName } from './utils'
export default {
  name: 'FlowPanel',
  components: {
    draggable,
    flowNode,
    nodeMenu
  },
  directives: {
    // 設置畫布拖曳效果
    flowDrag: {
      bind(el, binding, vnode, oldNode) {
        if (!binding) {
          return
        }
        el.onmousedown = e => {
          if (e.button == 2) {
            // 滑鼠右鍵disabled
            return
          }
          //  滑鼠按下，計算當前原始距離可視區的高度
          let disX = e.clientX
          let disY = e.clientY
          el.style.cursor = 'move'

          document.onmousemove = function(e) {
            // 移動時禁止預設事件
            e.preventDefault()
            const left = e.clientX - disX
            disX = e.clientX
            el.scrollLeft += -left

            const top = e.clientY - disY
            disY = e.clientY
            el.scrollTop += -top
          }

          document.onmouseup = function(e) {
            el.style.cursor = 'auto'
            document.onmousemove = null
            document.onmouseup = null
          }
        }
      }
    }
  },
  // jsPlumb基礎配置
  mixins: [flowMixin, page],
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
    }
  },
  data() {
    return {
      // jsPlumb 實體
      jsPlumb: null,
      // 控制畫布是否銷毀
      flowVisible: true,
      // 是否加載完畢Flag
      loadFlowFinish: false,
      // 資料
      data: {
        nodeList: [],
        lineList: []
      },
      // 暫存資料
      tempData: null,
      // 被點擊的節點或是連線元素，
      activeElement: {
        // 可選值 node 、line
        type: undefined,
        // 節點ID
        nodeId: undefined,
        // 連線ID
        sourceId: undefined,
        // 目標ID
        targetId: undefined
      },
      zoom: 0.5,
      /** 流程機關代碼 */
      flowOrganId: null,
      form: {
        flowName: '',
        flowDesc: ''
      },
      /** 簽核, */
      targetList: [],
      errorMsg: '',
      isShowTaskError: false,
      /** 暫存找到的節點 */
      tempLoopFindNodes: []
    }
  },
  watch: {
    'data.lineList': {
      handler() {
        this.checkShowTaskError()
      }
    },
    async editFlowId(val) {
      if (val) {
        this.tempData = null
        await this.getFolwHomeData()
        this.$nextTick(async () => {
          // await this.delay(200)
          // load data
          this.dataReload(this.tempData)
        })
      }
    }
  },
  async mounted() {
    this.jsPlumb = jsPlumb.getInstance()
    this.tempData = null
    await this.getFolwHomeData()
    this.$nextTick(() => {
      // load data
      this.dataReload(this.tempData)
    })
  },
  methods: {
    clear() {
      this.flowVisible = false
      this.data.nodeList = []
      this.data.lineList = []
      this.targetList = []
    },
    async getFolwHomeData() {
      if (this.editFlowId) {
        if (this.editFlowId == 'SA') {
          this.targetList = [
            {
              taskSeq: null,
              taskOrder: 0,
              taskName: '',
              taskType: 2,
              taskAssignees: '',
              taskAssigneesCombox: {
                options: []
              }
            }
          ]
          this.form.flowName = ''
          this.form.flowDesc = ''
          this.tempData = {
            nodeList: [
              {
                id: 'nodeA',
                name: '',
                taskName: '',
                type: 2,
                left: '350px',
                top: '50px'
              }
            ],
            lineList: []
          }
        } else {
          await this.loadingContainer(async () => {
            const res = await this.$api.management.getTaskDetail({
              flowId: this.editFlowId
            })
            const data = res.data.data.tpiFlow
            this.flowOrganId =
              data.organId || this.$store.state.user.userInfo.organId
            this.targetList = data.flowTasks
            this.form.flowName = data.flowName
            this.form.flowDesc = data.flowDesc
            //this.tempData = getDataB()

            this.tempData = data.jsonData || this.data
          })
        }
      } else {
        this.tempData = this.data
        this.flowOrganId = this.$store.state.user.userInfo.organId
      }
    },
    jsPlumbInit() {
      this.jsPlumb.ready(() => {
        this.jsPlumb.registerConnectionTypes({
          basic: {
            paintStyle: { stroke: 'blue', strokeWidth: 7 }
          },
          selected: {
            paintStyle: { stroke: 'red', strokeWidth: 5 }
          }
        })
        // 導入預設配置
        this.jsPlumb.importDefaults(this.jsplumbSetting)
        // 會使整個jsPlumb立即重繪
        this.jsPlumb.setSuspendDrawing(false, true)
        // 初始化節點
        this.loadFlow()
        // 點擊連接線事件
        this.jsPlumb.bind('click', (conn, originalEvent) => {
          this.activeElement.type = 'line'
          this.activeElement.sourceId = conn.sourceId
          this.activeElement.targetId = conn.targetId

          if (this.isEdit) {
            this.resetAllLineStyle()
            conn.setType('selected')

            setTimeout(() => {
              //this.setConnActive(conn)
            }, 200)
          }
        })
        // 連線事件
        this.jsPlumb.bind('connection', evt => {
          let from = evt.source.id
          let to = evt.target.id
          if (this.loadFlowFinish) {
            this.data.lineList.push({ from: from, to: to })
          }
        })

        // 刪除連接線的call back
        this.jsPlumb.bind('connectionDetached', evt => {
          this.deleteLine(evt.sourceId, evt.targetId)
        })

        // 改變現地連接節點事件
        this.jsPlumb.bind('connectionMoved', evt => {
          this.changeLine(evt.originalSourceId, evt.originalTargetId)
        })

        // 連線滑鼠右點擊
        this.jsPlumb.bind('contextmenu', evt => {
          console.log('contextmenu', evt)
        })

        // 連線事件
        this.jsPlumb.bind('beforeDrop', evt => {
          let from = evt.sourceId
          let to = evt.targetId
          if (from === to) {
            this.toastError('節點不支持連接自己')
            return false
          }
          if (hasLine(this.data, from, to)) {
            this.toastError('該關係已存在,不允許重複創建')
            return false
          }
          if (hashOppositeLine(this.data, from, to)) {
            this.toastError('不支持兩個節點之間連線回環')
            return false
          }
          this.toastSuccess('連接成功')
          return true
        })

        // beforeDetach
        this.jsPlumb.bind('beforeDetach', evt => {
          console.log('beforeDetach', evt)
        })
        this.jsPlumb.setContainer(this.$refs.flowContainer)
      })
    },
    resetAllLineStyle() {
      var connectionList = this.jsPlumb.getConnections() || []
      connectionList.forEach(conn => {
        conn.removeType('selected')
      })
    },
    getTaskUsers(nodeId) {
      const findUser = this.targetList.find(x => x.nodeId == nodeId)
      return findUser ? findUser.taskAssigneesCombox.options : []
    },
    // 讀取流程圖
    loadFlow() {
      // 初始化節點
      for (let i = 0; i < this.data.nodeList.length; i++) {
        let node = this.data.nodeList[i]
        // 設置來源點，觸發可拖曳的位置
        this.jsPlumb.makeSource(
          node.id,
          lodash.merge(this.jsplumbSourceOptions, {})
        )
        // 設置目標點，讓其他來源點的線可連接該節點
        this.jsPlumb.makeTarget(node.id, this.jsplumbTargetOptions)
        if (this.isEdit) {
          this.jsPlumb.draggable(node.id, {
            containment: 'parent',
            filter: '.ui-resizable-handle',
            stop: function(el) {
              // 拖曳到節點後的call back
              console.log('拖曳结束: ', el)
            }
          })
        }
      }
      // 初始化連線
      for (let i = 0; i < this.data.lineList.length; i++) {
        let line = this.data.lineList[i]
        let connParam = {
          source: line.from,
          target: line.to,
          label: line.label ? line.label : '',
          connector: line.connector ? line.connector : '',
          anchors: line.anchors ? line.anchors : undefined,
          paintStyle: line.paintStyle ? line.paintStyle : undefined
        }
        this.jsPlumb.connect(connParam, this.jsplumbConnectOptions)
      }
      this.$nextTick(function() {
        this.loadFlowFinish = true
      })
    },
    // 設置連線的Label
    setLineLabel(from, to, label) {
      var conn = this.jsPlumb.getConnections({
        source: from,
        target: to
      })[0]
      if (!label || label === '') {
        conn.removeClass('flowLabel')
        conn.addClass('emptyFlowLabel')
      } else {
        conn.addClass('flowLabel')
      }
      conn.setLabel({
        label: label
      })
      this.data.lineList.forEach(function(line) {
        if (line.from == from && line.to == to) {
          line.label = label
        }
      })
    },
    // 刪除連線
    deleteLine(from, to) {
      this.data.lineList = this.data.lineList.filter(function(line) {
        if (line.from == from && line.to == to) {
          return false
        }
        return true
      })
    },
    // 改變連線
    changeLine(oldFrom, oldTo) {
      this.deleteLine(oldFrom, oldTo)
    },
    // 改變節點位置
    changeNodeSite(data) {
      for (var i = 0; i < this.data.nodeList.length; i++) {
        let node = this.data.nodeList[i]
        if (node.id === data.nodeId) {
          node.left = data.left
          node.top = data.top
          node.width = data.width
          node.height = data.height
        }
      }
    },
    deleteSelectLine() {
      if (this.activeElement.type === 'line') {
        var conn = this.jsPlumb.getConnections({
          source: this.activeElement.sourceId,
          target: this.activeElement.targetId
        })[0]
        this.jsPlumb.deleteConnection(conn)
      }
    },
    /**
     * 拖拽結束後添加新的節點
     * @param evt
     * @param nodeMenu 被添加的節點對象
     * @param mousePosition 滑鼠拖曳結束的坐標
     */
    addNode(evt, nodeMenu, mousePosition) {
      var screenX = evt.originalEvent.clientX,
        screenY = evt.originalEvent.clientY
      let flowContainer = this.$refs.flowContainer
      var containerRect = flowContainer.getBoundingClientRect()
      var left = screenX,
        top = screenY
      // 計算是否拖曳到容器中
      if (
        left < containerRect.x ||
        left > containerRect.width + containerRect.x ||
        top < containerRect.y ||
        containerRect.y > containerRect.y + containerRect.height
      ) {
        this.toastError('請把節點拖入到畫布中')
        return
      }
      left = left - containerRect.x + flowContainer.scrollLeft
      top = top - containerRect.y + flowContainer.scrollTop
      // 居中
      left -= 85
      top -= 16
      const nodeId = this.$f.genUUID()
      const nodeName = nodeMenu.name
      var node = {
        id: nodeId,
        name: nodeMenu.type === '4' ? nodeName : '',
        taskName: '',
        type: nodeMenu.type,
        left: left + 'px',
        top: top + 'px',
        width: '300px',
        height: '72px'
      }
      /**
       * 可在這判斷能否可添加該節點
       */
      this.data.nodeList.push(node)
      // 除了流程說明類型，其他新增一筆流程定義步驟(明細)檔
      if (node.type !== '4') {
        this.targetList.push({
          taskSeq: null,
          taskOrder: 0,
          taskName: getNodeTypeName(node.type),
          taskType: node.type,
          nodeId: node.id,
          taskAssignees: '',
          taskAssigneesCombox: {
            options: []
          }
        })
      }
      this.$nextTick(function() {
        this.jsPlumb.makeSource(nodeId, this.jsplumbSourceOptions)
        this.jsPlumb.makeTarget(nodeId, this.jsplumbTargetOptions)
        this.jsPlumb.draggable(nodeId, {
          containment: 'parent',
          filter: '.ui-resizable-handle',
          stop: el => {
            // 拖拽節點結束後的call back
            console.log('拖曳結束: ', el)
          }
        })
      })
    },
    /**
     * 刪除節點
     * @param nodeId 被刪除節點的ID
     */
    deleteNode(nodeId) {
      /**
       * 可在這判斷是否可以刪除
       */
      this.data.nodeList = this.data.nodeList.filter(function(node) {
        if (node.id === nodeId) {
          return false
        }
        return true
      })
      this.$nextTick(function() {
        this.jsPlumb.removeAllEndpoints(nodeId)
      })
    },
    deleteFlowTasks(nodeId) {
      this.targetList = this.targetList.filter(function(item) {
        if (item.nodeId === nodeId) {
          return false
        }
        return true
      })
    },
    clickNode(nodeId) {
      this.activeElement.type = 'node'
      this.activeElement.nodeId = nodeId
      this.resetAllLineStyle()
      //this.$refs.nodeForm.nodeInit(this.data, nodeId)
    },
    onNodeToDelete(nodeId) {
      this.clickNode(nodeId)
      //this.deleteElement()
      this.deleteNode(nodeId)
      this.deleteFlowTasks(nodeId)
    },
    editNode(nodeId) {
      const findNode = this.data.nodeList.find(x => x.id == nodeId)
      if (findNode) {
        // 文字流程編輯類型
        if (findNode.type === '4') {
          this.$emit('open-desc-edit', { data: this.data, editNodeId: nodeId })
        } else {
          this.$emit('open-node-edit', {
            data: this.data,
            targetList: this.targetList,
            editNodeId: nodeId,
            flowOrganId: this.flowOrganId
          })
        }
      }
    },
    getSaveForm() {
      const flowTasksList = this.getFlowTasksSaveData()
      this.excludeNotNodeLinkFlowTasks(flowTasksList)
      return {
        tpiFlow: {
          flowId: this.isCopy ? null : this.editFlowId,
          flowName: this.form.flowName,
          flowDesc: this.form.flowDesc,
          organId: this.isCopy
            ? this.$store.state.user.userInfo.organId
            : this.flowOrganId,
          isDefault: this.isCopy ? false : this.isDefault,
          jsonData: JSON.stringify(this.data),
          flowTasks: flowTasksList
        }
      }
    },
    /** 排除node沒連線的Flow Task */
    excludeNotNodeLinkFlowTasks(flowTasksList) {
      const flowTasksTypes = ['1', 'D', 'A', 'F', '3']
      const flowTasksNodeIds = flowTasksList.map(x => x.nodeId)
      const tempRemoveNodes = this.data.nodeList.filter(
        x => flowTasksTypes.includes(x.type) && !flowTasksNodeIds.includes(x.id)
      )
      tempRemoveNodes.forEach(node => {
        this.deleteNode(node.id)
      })
    },
    findFirstNodeId() {
      let firstNodeId = null
      this.data.lineList.forEach(line => {
        // 找出來源沒被連線的就是第一個
        const findFromLine = this.data.lineList.find(x => x.to == line.from)
        if (!findFromLine) {
          firstNodeId = line.from
        }
      })

      return firstNodeId
    },
    getFlowTasksSaveData() {
      let tempData = []
      this.tempLoopFindNodes = []
      // 找出第一個連線，再依序找下去
      let firstNodeId = this.findFirstNodeId()
      if (firstNodeId != null) {
        const findFirstTarget = this.targetList.find(
          x => x.nodeId == firstNodeId
        )
        console.log('first')
        console.log(findFirstTarget)
        if (findFirstTarget != null) {
          tempData.push(lodash.cloneDeep(findFirstTarget))
        }
        this.whileLoopFindTask(tempData, firstNodeId)
        /*var id = firstNodeId
        while (id != null) {
          const findLines = this.data.lineList.find(x => x.from == id)
          if (findLines != null) {
            const findTarget = this.targetList.find(
              x => x.nodeId == findLines.to
            )
            if (findTarget) {
              tempData.push(lodash.cloneDeep(findTarget))
              id = findLines.to
            } else {
              id = findLines.to
              //id = null
            }
          } else {
            id = null
          }
        }*/
      }

      tempData.forEach((item, index) => {
        item.taskOrder = index + 1
        item.taskSeq = this.isCopy ? null : item.taskSeq
        item.taskAssignees = item.taskAssigneesCombox.options
          .map(x => x.value)
          .join(',')
      })
      return tempData
    },
    whileLoopFindTask(tempData, nodeId) {
      var id = nodeId
      //while (id != null) {
      const findLine = this.data.lineList.filter(x => x.from == id)

      if (findLine.length > 0 && !this.tempLoopFindNodes.includes(id)) {
        this.tempLoopFindNodes.push(id)
        findLine.forEach(line => {
          const findTarget = this.targetList.find(x => x.nodeId == line.to)
          if (findTarget && !this.tempLoopFindNodes.includes(line.to)) {
            tempData.push(lodash.cloneDeep(findTarget))
          }
          id = line.to
          this.whileLoopFindTask(tempData, id)
        })
      } else {
        id = null
      }
      //}
    },
    // 是否具有該線
    // hasLine(from, to) {
    //   for (var i = 0; i < this.data.lineList.length; i++) {
    //     var line = this.data.lineList[i]
    //     if (line.from === from && line.to === to) {
    //       return true
    //     }
    //   }
    //   return false
    // },
    // 是否含有相反的線
    // hashOppositeLine(from, to) {
    //   return this.hasLine(to, from)
    // },
    nodeRightMenu(nodeId, evt) {
      this.menu.show = true
      this.menu.curNodeId = nodeId
      this.menu.left = evt.x + 'px'
      this.menu.top = evt.y + 'px'
    },
    /** 元素重新計算端點和連接線 */
    repaintEverything() {
      this.jsPlumb.repaintEverything()
      //this.jsPlumb.repaint()
    },
    // 讀取流程圖
    dataReload(data) {
      this.flowVisible = false
      this.data.nodeList = []
      this.data.lineList = []
      this.$nextTick(() => {
        data = lodash.cloneDeep(data)
        this.flowVisible = true
        this.data = data
        this.$nextTick(() => {
          this.jsPlumb = jsPlumb.getInstance()
          this.$nextTick(() => {
            this.jsPlumbInit()
          })
        })
      })
    },
    closeFlowEvent() {
      this.$emit('close-flow')
    },
    /** 檢核錯誤 */
    checkShowTaskError() {
      const reSortTargetList = this.getFlowTasksSaveData()
      this.isShowTaskError = false
      let isError = false
      let errMsgs = []

      // 人員必填檢查
      // admin不檢查
      if (!this.isAdmin) {
        reSortTargetList
          .filter(x => x.taskType !== '3')
          .forEach(item => {
            const isRequestError = this.validateTaskRequest(
              item.taskAssigneesCombox,
              item.taskType
            )
            if (isRequestError === false) {
              isError = true
            }
          })
      }
      if (isError) {
        //errMsgs.push('<p>節點人員請必填</p>')
      }

      // 檢查每個節點擺放邏輯
      if (reSortTargetList.length <= 1) {
        // isError = true
        // errMsgs.push('<p>請至少選擇兩關節點</p>')
      } else {
        /** 檢查第一關是否為分文、陳核 */
        if (['1', 'F'].includes(reSortTargetList[0].taskType)) {
          isError = true
          errMsgs.push('<p>第一關請選擇分文、陳核類型</p>')
        }

        if (reSortTargetList.length > 1) {
          /**  檢查最後一關不能是會簽 */
          if (reSortTargetList[reSortTargetList.length - 1].taskType === '1') {
            isError = true
            errMsgs.push('<p>最後一關請選擇分文、陳核、結案類型</p>')
          }

          /** 檢查返回前一關不能是會簽或返回 */
          if (
            reSortTargetList[reSortTargetList.length - 1].taskType === '3' &&
            (reSortTargetList[reSortTargetList.length - 2].taskType === '1' ||
              reSortTargetList[reSortTargetList.length - 2].taskType === '3')
          ) {
            isError = true
            errMsgs.push('<p>倒數二關請選擇分文、陳核、結案類型</p>')
          }
          // D(分文)、A(陳核)的index集合
          let mapType_DAndA_Indexs = []
          // F(結案)的index集合
          let mapType_F_Indexs = []
          reSortTargetList.forEach((item, index) => {
            // 取得分文跟陳核的index
            if (['D', 'A'].includes(item.taskType)) {
              mapType_DAndA_Indexs.push(index)
            }
            // 取得結案index
            if (item.taskType === 'F') {
              mapType_F_Indexs.push(index)
            }
            /** 檢查返回必須是最後一個節點 */
            if (index < reSortTargetList.length - 1) {
              if (item.taskType === '3') {
                isError = true
                errMsgs.push('<p>返回請放置於最後一個節點</p>')
              }
            }
          })

          /** 檢查結案數量邏輯 */
          if (mapType_F_Indexs.length > 1) {
            isError = true
            errMsgs.push('<p>結案類型只能放置一個</p>')
          }

          let isLargeTypeF = false
          if (mapType_DAndA_Indexs.length > 0 && mapType_F_Indexs.length == 1) {
            mapType_DAndA_Indexs.forEach(itemIndex => {
              if (itemIndex > mapType_F_Indexs[0]) {
                isLargeTypeF = true
              }
            })
          }
          if (isLargeTypeF) {
            isError = true
            errMsgs.push('<p>分文、陳核類型只能放置在結案類型之前</p>')
          }
        }
      }
      if (isError) {
        this.isShowTaskError = true
        this.errorMsg = errMsgs.join('')
      } else {
        this.isShowTaskError = false
        this.errorMsg = ''
      }
    },
    validateTaskRequest(taskAssigneesCombox, taskType) {
      if (taskType == 1) {
        return taskAssigneesCombox.options.length > 1
      } else {
        return taskAssigneesCombox.options.length == 1
      }
      return true
    },
    async saveTask() {
      this.checkShowTaskError()
      if (!(await this.$refs.observer.validate()) || this.isShowTaskError) {
        return
      }

      this.$emit('confirm-save-flow')
    }
  }
}
</script>
<style lang="scss" scoped>
.approved-error {
  color: red;
  padding-left: 180px;
  margin-bottom: 5px;
}
</style>
