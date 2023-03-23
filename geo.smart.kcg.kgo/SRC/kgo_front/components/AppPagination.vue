<template>
  <div class="text-center">
    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center">
        <li class="page-item first"><a class="page-link page-btn" href="javascript: void(0)" title="第一頁按鈕" @click="goFirstPage()">第一頁</a></li>
        <li class="page-item prev"><a class="page-link text-hide" href="javascript: void(0)" title="上一頁按鈕" @click="goPreviousPage()">上一頁</a></li>
        <li class="page-item"><a class="page-link" href="javascript: void(0)" title="第1頁按鈕" v-if="previousPage != '0'"  @click="clickPage('pre')">{{ previousPage  }}</a></li>
        <li class="page-item active"><a class="page-link" href="javascript: void(0)" title="第2頁按鈕" @click="clickPage('now')">{{ nowPage }}</a></li>
        <li class="page-item"><a class="page-link" href="javascript: void(0)" title="第3頁按鈕" v-if="nextPage <= pageCount"  @click="clickPage('next')">{{ nextPage }}</a></li>
        <li class="page-item next"><a class="page-link text-hide" href="javascript: void(0)" title="下一頁按鈕" @click="goNextPage()">下一頁</a></li>
        <li class="page-item last"><a class="page-link page-btn" href="javascript: void(0)" title="最末頁按鈕" @click="goLastPage()">最末頁</a> </li>
        <li class="gotopage">跳至
        <select class="cu-select" title="跳頁下拉選單" @change="selectPage()" v-model="select">
          <option v-for="item in pageCount" :key="item" >{{ item  }}</option>
        </select>頁</li>
      </ul>          
    </nav>
  </div>
</template>
<script>
export default {
  data() {
    return{
      pageCount: 0,
      nowPage: 1,
      previousPage: 0,
      nextPage: 0,
      select: 1,

    }
  },
  props: {
    dataLength: {
      type: Number,
      default: 0
    }
  },
  watch: {
    nowPage(newValue) {
      if (newValue) {
        this.select = this.nowPage
        this.$emit('input-Page', +newValue)
      }
    }
  },
  computed: {
  },
  mounted() {
    this.pageCount = Math.ceil(this.dataLength/10)
    this.previousPage = 0
    this.nowPage = 1
    this.nextPage = 2
  },
  methods: {
    goFirstPage() {
      this.nowPage = 1
      this.previousPage = 0
      this.nextPage = 2
    },
    goPreviousPage() {
      if(this.nowPage != 1){
        this.nowPage = this.nowPage -1
        this.previousPage = this.previousPage -1
        this.nextPage = this.nextPage -1
      }
    },
    goNextPage() {
      if(this.nowPage != this.pageCount){
        this.nowPage = this.nowPage +1
        this.previousPage = this.previousPage +1
        this.nextPage = this.nextPage +1
      }
    },
    goLastPage() {
      this.nowPage = this.pageCount
      this.previousPage = this.pageCount -1
      this.nextPage = this.pageCount +1
    },
    selectPage() {
      this.nowPage = parseInt(this.select)
      this.previousPage = parseInt(this.select) -1
      this.nextPage = parseInt(this.select) +1
    },
    clickPage(type) {
      if(type == 'pre') {
        this.nowPage = this.nowPage -1
        this.previousPage = this.previousPage -1
        this.nextPage = this.nextPage -1
      }
      else if( type == 'next'){
        this.nowPage = this.nowPage +1
        this.previousPage = this.previousPage +1
        this.nextPage = this.nextPage +1
      }
      else{
        return
      }
    }
  }
}
</script>