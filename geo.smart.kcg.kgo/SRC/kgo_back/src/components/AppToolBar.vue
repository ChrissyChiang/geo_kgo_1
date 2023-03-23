<template>
  <div
    style="
      position: fixed;
      width: 100%;
      height: 37px;
      background: #c7d2e2;
      z-index: 1;
    "
  >
    <div style="width: 200px">
      <ul style="list-style-type: none; padding: 0; margin: 0">
        <li class="title_li" style="background: #41a005">
          <nav id="fsm-navbar">
            <a
              href="javascript:void(0)"
              title="系統公告"
              style="color: white"
              @click="getMenuList('AnnApply')"
            >
              <i class="fa fa-bullhorn"></i>
              <span class="text-hide">系統公告{{ menuList.length }}</span>
            </a>
          </nav>
        </li>
        <li class="title_li" style="background: #ffd400">
          <nav id="fsm-navbar">
            <a
              href="javascript:void(0)"
              title="案件處理"
              style="color: #4d4d4d"
              @click="getMenuList('CaseHandle')"
            >
              <i class="fa fa-edit"></i>
              <span class="text-hide">案件處理</span>
            </a>
          </nav>
        </li>
        <li class="title_li" style="background: #ff7600">
          <nav id="fsm-navbar">
            <a
              href="javascript:void(0)"
              title="系統設定"
              style="color: white"
              @click="getMenuList('SystemSetting')"
            >
              <i class="fa fsm-icon-cog"></i>
              <span class="text-hide">系統設定</span>
            </a>
          </nav>
        </li>
        <li class="title_li" style="background: #1e63b9">
          <nav id="fsm-navbar" class="dropdown nav-nemu">
            <a
              href="javascript:void(0)"
              class="btn-turn-menu"
              title="顯示/隱藏選單"
              style="color: white"
            >
              <i class="fa fa-bars"></i>
              <span class="text-hide">顯示/隱藏選單</span>
            </a>
          </nav>
        </li>
      </ul>
    </div>
    <div
      style="
        float: right;
        padding-left: 10px;
        width: calc(100% - 200px);
        height: 35px;
        padding-top: 5px;
      "
    >
      <span>
        <font color="#4D4D4D">
          <i class="fa fa-home"></i>
          {{ this.menuRootName }}&gt;{{ this.menuSubName }}
        </font>
      </span>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
import * as types from '@/store/mutations_types.js'
export default {
  mixins: [page],
  data() {
    return {}
  },
  async mounted() {},
  methods: {
    getMenuList(functionId) {
      const findRootMenu = this.menuList.find(x => x.functionId == functionId)
      if (!findRootMenu) return
      if (!findRootMenu.childs) return
      // if (findRootMenu.childs && findRootMenu.childs == 0) return
      this.setMenuRootName(findRootMenu.name)
      if (functionId == 'AnnApply') {
        this.setMenuSubName('')
        this.$router.push('/').catch(error => {})
      } else {
        const sub = findRootMenu.childs[0]
        this.setMenuRootId(functionId)
        this.setMenuSubId(sub.seq)
        this.setMenuSubName(sub.name)
        this.$router.push(sub.url).catch(error => {})
      }
    }
  }
}
</script>
