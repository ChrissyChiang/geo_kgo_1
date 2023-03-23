<template>
  <div class="wrap list-wrap">
    <h3>
      <img :src="titleImage" alt="" />
      {{ title }}
    </h3>
    <div class="department" ref="departmentRef">
      <div v-for="(item1, index) in dataList" :key="index">
        <div class="item" v-for="item in item1" :key="item.value">
          <a :title="item.name" href="#" @click.prevent="goApply(item.value)">
            <i>{{ item.count }}</i>
            <span>{{ item.name }}</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {}
  },
  props: {
    depData: {
      type: [String, Number, Array],
      default: () => []
    },
    menuType: {
      type: String,
      default: ''
    }
  },
  computed: {
    dataList() {
      //每6個機關在包一個陣列
      let temData = []
      let temData2 = []
      let count6 = Math.ceil(this.depData.length / 6)
      for (let i = 0; i < count6; i++) {
        for (let m = 0; m <= 5; m++) {
          if (i * 6 + m < this.depData.length) {
            temData2.push(this.depData[i * 6 + m])
          }
        }
        temData.push(temData2)
        temData2 = []
      }
      return temData
    },
    title() {
      if (this.menuType == '1') {
        return '選擇機關'
      } else if (this.menuType == '2') {
        return '選擇身份'
      } else {
        return '選擇服務'
      }
    },
    titleImage() {
      if (this.menuType == '1') {
        return '/img/icon-1-title.svg'
      } else if (this.menuType == '2') {
        return '/img/icon-2-title.svg'
      } else {
        return '/img/icon-3-title.svg'
      }
    }
  },
  async mounted() {},
  methods: {
    goApply(id) {
      //location.href = `/apply/${id}?type=${this.menuType}`
      this.$router.push({
        path: '/apply/' + id,
        query: { type: this.menuType }
      })
    }
  }
}
</script>
