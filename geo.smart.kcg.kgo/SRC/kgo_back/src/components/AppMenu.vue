<template>
  <div class="fsm-layout-menu" style="margin-bottom: auto; margin-top: 35px">
    <ul class="nav nav-stacked">
      <li v-if="currentTopMenu.length > 0" class="dropdown-menu">
        <a
          v-for="item in currentTopMenu"
          :key="item.seq"
          style="padding: 25px"
          @click="setCurrentMenuId(item.url, item.name, item.seq)"
        >
          {{ item.name }}
          <button v-if="item.count >= 0" type="button" class="btn btn-fsm">
            {{ item.count }}
          </button>
        </a>
      </li>
      <li v-if="menuSubSeq != null" class="active">
        <a
          style="padding: 25px"
          :title="currentActiveMenu.name"
          @click="
            setCurrentMenuId(
              currentActiveMenu.url,
              currentActiveMenu.name,
              currentActiveMenu.seq
            )
          "
        >
          {{ currentActiveMenu.name }}
          <button
            v-if="currentActiveMenu.count >= 0"
            type="button"
            class="btn btn-fsm"
          >
            {{ currentActiveMenu.count }}
          </button>
        </a>
      </li>
      <li
        v-if="currentBottomMenu.length > 0 && menuSubSeq != null"
        class="dropdown-menu"
      >
        <a
          v-for="item in currentBottomMenu"
          :key="item.seq"
          style="padding: 25px"
          @click="setCurrentMenuId(item.url, item.name, item.seq)"
        >
          {{ item.name }}
          <button v-if="item.count >= 0" type="button" class="btn btn-fsm">
            {{ item.count }}
          </button>
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
import { page } from '@/mixins'
import * as types from '@/store/mutations_types.js'

export default {
  mixins: [page],
  props: {},
  data() {
    return {
      menuTop: [],
      menuActive: {
        name: '',
        url: ''
      },
      menuBottom: []
    }
  },
  computed: {
    currentSubMenu() {
      const findSubMenuSubList = this.menuList.find(
        x => x.functionId == this.menuRootId
      )
      return findSubMenuSubList ? findSubMenuSubList.childs : []
    },
    currentActiveMenu() {
      const acData = this.currentSubMenu.find(x => x.seq === this.menuSubSeq)
      if (acData) {
        return { name: acData.name, url: acData.url, count: acData.count }
        //return { name: acData.name, url: acData.url }
      } else {
        //return { name: '', url: '', count: 0 }
        return { name: '', url: '', count: -1 }
      }
    },
    currentTopMenu() {
      if (this.menuSubSeq) {
        const acIndex = this.currentSubMenu.findIndex(
          x => x.seq === this.menuSubSeq
        )
        if (acIndex == 0) {
          return []
        } else if (acIndex == this.currentSubMenu.length - 1) {
          return this.currentSubMenu.slice(0, this.currentSubMenu.length - 1)
        } else {
          return this.currentSubMenu.slice(0, acIndex)
        }
      } else {
        return this.currentSubMenu
      }
    },
    currentBottomMenu() {
      const acIndex = this.currentSubMenu.findIndex(
        x => x.seq === this.menuSubSeq
      )
      if (acIndex == 0) {
        return this.currentSubMenu.slice(1)
      } else if (acIndex == this.currentSubMenu.length - 1) {
        return []
      } else {
        return this.currentSubMenu.slice(
          acIndex + 1,
          this.currentSubMenu.length
        )
      }
    }
  },
  async mounted() {
    const acData = this.currentSubMenu.find(x => x.seq === this.menuSubSeq)
    if (acData) {
      this.menuActive.name = acData.name
      this.menuActive.url = acData.url
      this.menuActive.count = acData.count
    }

    const acIndex = this.currentSubMenu.findIndex(
      x => x.seq === this.menuSubSeq
    )
    if (acIndex == 0) {
      this.menuTop = []
      this.menuBottom = this.currentSubMenu.slice(1)
    } else if (acIndex == this.currentSubMenu.length - 1) {
      this.menuBottom = []
      this.menuTop = this.currentSubMenu.slice(
        0,
        this.currentSubMenu.length - 1
      )
    } else {
      this.menuTop = this.currentSubMenu.slice(0, acIndex)
      this.menuBottom = this.currentSubMenu.slice(
        acIndex + 1,
        this.currentSubMenu.length
      )
    }
  },

  methods: {
    //  goToHerf(url, id) {
    //   // this.currentId = id
    //   this.setMenuSubId()
    //   window.location.href = `/${url}`
    // },
    setCurrentMenuId(url, name, id) {
      if (!url) {
        return
      }
      // this.setMenuSubName(name)
      // this.setMenuSubId(id)
      this.$router.push(url).catch(error => {
        window.location.href = url
      })
      // if(id != this.currentId){
      //   this.$router.push('/'+url).catch(error => {
      //     this.currentId = id
      //   })
      //   console.log(url)
      //   //console.log(url)
      //   this.currentId = id
      // }
      // console.log(url, id);
      //  const acData = this.currentSubMenu.find(x => x.seq === this.menuSubSeq)
    }
  }
}
</script>
