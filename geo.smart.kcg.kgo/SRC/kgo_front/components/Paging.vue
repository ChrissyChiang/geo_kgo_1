<template>
  <div>
    <p v-if="totalPage == 0">沒有任何結果</p>
    <nav v-if="totalPage != 0" aria-label="Page navigation">
      <ul class="pagination justify-content-center">
        <li class="page-item first">
          <a
            class="page-link page-btn"
            href="#"
            title="第一頁按鈕"
            @click.prevent="first"
          >
            第一頁
          </a>
        </li>
        <li v-if="current != 1" class="page-item prev">
          <a
            class="page-link text-hide"
            href="#"
            title="上一頁按鈕"
            @click.prevent="back"
          >
            上一頁
          </a>
        </li>
        <template v-for="i in allPages">
          <li :class="current == i ? 'page-item active' : 'page-item'" :key="i">
            <a
              class="page-link"
              href="#"
              :title="`第${i}頁按鈕`"
              :page="i"
              @click.prevent="changePageClick"
            >
              {{ i }}
            </a>
          </li>
        </template>
        <li v-if="current != totalPage" class="page-item next">
          <a
            class="page-link text-hide"
            href="#"
            title="下一頁按鈕"
            @click.prevent="next"
          >
            下一頁
          </a>
        </li>
        <li class="page-item last">
          <a
            class="page-link page-btn"
            href="#"
            title="最末頁按鈕"
            @click.prevent="last"
          >
            最末頁
          </a>
        </li>
        <li class="gotopage">
          跳至
          <select
            class="cu-select"
            title="跳頁下拉選單"
            v-model="current"
            @change="changePage"
          >
            <template v-for="(i, index) in totalPage">
              <option :key="index + 1 + 'page'">{{ index + 1 }}</option>
            </template>
          </select>
          頁
        </li>
      </ul>
    </nav>
  </div>
</template>
<script>
export default {
  watch: {
    total() {
      this.totalPage = this.total
      var pages = []
      if (this.totalPage < this.showIndex) this.showIndex = this.totalPage

      for (var i = this.current; i <= this.showIndex; i++) pages.push(i)
      this.allPages = pages
    }
  },
  data() {
    return {
      totalPage: 0,
      showIndex: 5,
      current: 1,
      allPages: []
    }
  },
  props: {
    total: {
      type: [String, Number],
      default: ''
    },
    currentIndex: {
      type: [String, Number],
      default: ''
    }
  },
  mounted() {
    this.$nextTick(() => {})
  },
  methods: {
    getData() {
      this.$emit('update:currentIndex', this.current)
      this.$emit('getdata')
    },
    changePageClick() {
      this.current = parseInt(event.target.getAttribute('page'))
      this.changePage()
    },
    changePage() {
      this.current = parseInt(this.current)
      var add = this.showIndex % 2
      var center = parseInt(this.showIndex / 2) + add
      var centerNonAdd = parseInt(this.showIndex / 2)
      var start = 1
      var pages = []

      if (this.current >= this.totalPage - centerNonAdd) {
        for (i = this.totalPage - this.showIndex + 1; i <= this.totalPage; i++)
          pages.push(i)
      } else if (this.current < center) {
        for (i = 1; i <= this.showIndex; i++) pages.push(i)
      } else {
        if (this.current - center >= 1) start = this.current - center + 1

        for (var i = start; i <= this.current + centerNonAdd; i++) pages.push(i)
      }
      this.allPages = pages
      this.getData()
    },
    next() {
      this.current += 1
      this.changeAllPage(true)
    },
    back() {
      this.current -= 1
      this.changeAllPage(false)
    },
    first() {
      if (this.current == 1) return
      this.current = 1
      var pages = []
      for (var i = 1; i <= this.showIndex; i++) pages.push(i)
      this.allPages = pages
      this.getData()
    },
    last() {
      if (this.current == this.totalPage) return
      this.current = this.totalPage
      var start = this.totalPage - this.showIndex + 1
      var pages = []
      for (var i = start; i <= this.totalPage; i++) pages.push(i)
      this.allPages = pages
      this.getData()
    },
    changeAllPage(isNext) {
      if (this.totalPages <= this.showIndex) return //不處理
      var add = this.showIndex % 2
      var needAdd = parseInt(this.showIndex / 2) + add

      if (this.current > needAdd) {
        var pages = []
        this.allPages.forEach(x => pages.push(isNext ? x + 1 : x - 1))
        var last = pages[pages.length - 1]
        if (
          (isNext && last <= this.totalPage) ||
          (!isNext && this.current <= this.totalPage - needAdd)
        ) {
          this.allPages = pages
        }
        // if ((last < this.totalPage && !isNext))
      }

      if (!isNext && this.current == needAdd) {
        var pages = []
        for (var i = 1; i <= this.showIndex; i++) pages.push(i)
        this.allPages = pages
      }
      this.getData()
    }
  }
}
</script>
