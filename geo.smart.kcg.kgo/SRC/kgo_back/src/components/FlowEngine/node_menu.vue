<template>
  <div ref="tool" class="flow-menu">
    <li
      class="delect-line-li"
      style="list-style-type: none;"
      @click="deleteLine"
    >
      <i class="fa fa-trash-o"></i>
      刪除選取連線
    </li>
    <div v-for="menu in menuList" :key="menu.id">
      <span class="flow-node-pmenu" @click="menu.open = !menu.open">
        <i
          :class="{
            'fa fa-chevron-down': menu.open,
            'fa fa-chevron-right': !menu.open
          }"
        ></i>
        &nbsp;{{ menu.name }}
      </span>
      <ul v-show="menu.open" class="flow-node-menu-ul">
        <draggable
          v-model="menu.children"
          :options="draggableOptions"
          tag="ul"
          @end="end"
          @start="move"
        >
          <li
            v-for="subMenu in menu.children"
            :key="subMenu.id"
            class="flow-node-menu-li"
            style="list-style-type: none;"
            :type="subMenu.type"
          >
            <i :class="subMenu.ico"></i>
            {{ subMenu.name }}
          </li>
        </draggable>
      </ul>
    </div>
  </div>
</template>
<script>
import draggable from 'vuedraggable'

var mousePosition = {
  left: -1,
  top: -1
}

export default {
  components: {
    draggable
  },
  data() {
    return {
      activeNames: '1',
      // draggable設置
      draggableOptions: {
        preventOnFilter: false,
        sort: false,
        disabled: false,
        ghostClass: 'tt',
        // 不使用H5原生的配置
        forceFallback: true
        // 拖曳的時候樣式
        // fallbackClass: 'flow-node-draggable'
      },
      // 預設打開的左側選單的id
      defaultOpeneds: ['1'],
      menuList: [
        {
          id: '1',
          type: 'group',
          name: '節點類型',
          ico: '',
          open: true,
          children: [
            {
              id: '12',
              type: '1',
              name: '會簽',
              ico: 'fa fa-group',
              style: {}
            },
            {
              id: '11',
              type: 'D',
              name: '分文',
              ico: 'fa fsm-icon-user',
              style: {}
            },
            {
              id: '10',
              type: 'A',
              name: '陳核',
              ico: 'fa fsm-icon-user',
              style: {}
            },
            {
              id: '9',
              type: 'F',
              name: '結案',
              ico: 'fa fsm-icon-user',
              style: {}
            },
            {
              id: '13',
              type: '3',
              name: '返回',
              ico: 'fa fsm-icon-refresh',
              style: {}
            },
            {
              id: '21',
              type: '4',
              name: '流程狀態',
              ico: 'fa fa-pencil-square-o',
              style: {}
            }
          ]
        }
      ],
      nodeMenu: {}
    }
  },
  created() {
    /**
     * 以下是為了解決在火狐瀏覽器上拖曳時彈出tab頁到搜索問題
     * @param event
     */
    if (this.isFirefox()) {
      document.body.ondrop = function(event) {
        // 解決火狐瀏覽器無法獲取滑鼠拖曳結束的坐標問題
        mousePosition.left = event.layerX
        mousePosition.top = event.clientY - 50
        event.preventDefault()
        event.stopPropagation()
      }
    }
  },
  methods: {
    // 根據類型獲取左側選單對象
    getMenuByType(type) {
      for (let i = 0; i < this.menuList.length; i++) {
        let children = this.menuList[i].children
        for (let j = 0; j < children.length; j++) {
          if (children[j].type == type) {
            return children[j]
          }
        }
      }
    },
    deleteLine() {
      this.$emit('delete-line')
    },
    // 拖曳開始時觸發
    move(evt, a, b, c) {
      var type = evt.item.attributes.type.nodeValue
      this.nodeMenu = this.getMenuByType(type)
    },
    // 拖曳結束時觸發
    end(evt, e) {
      this.$emit('addNode', evt, this.nodeMenu, mousePosition)
    },
    // 是否為Firefox瀏覽器
    isFirefox() {
      var userAgent = navigator.userAgent
      if (userAgent.indexOf('Firefox') > -1) {
        return true
      }
      return false
    }
  }
}
</script>
<style lang="scss" scoped>
.delect-line-li {
  cursor: pointer;
  color: #f9060a;
  width: 150px;
  border: 1px solid #f9060a;
  margin: 5px 0 5px 0;
  padding: 10px;
  border-radius: 5px;
  padding-left: 8px;
}
</style>
