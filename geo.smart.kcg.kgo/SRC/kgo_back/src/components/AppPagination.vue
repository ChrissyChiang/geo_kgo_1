<template>
  <div class="pagination">
    <div class="page">
      <button type="button" class="btn page_btn" @click.prevent="onFirstClick">
        {{ firstText }}
      </button>
      <button
        type="button"
        class="btn page_btn page_btn-pre"
        @click.prevent="onPreviousClick"
      ></button>
      <div class="page_control">
        <input
          ref="inputPage"
          type="text"
          value="1"
          class="page_input"
          @input="onPageInput"
          @blur="onBlur"
        />
        <span>
          /
          <span>{{ pages }}</span>
        </span>
      </div>
      <button
        type="button"
        class="btn page_btn page_btn-next"
        @click.prevent="onNextClick"
      ></button>
      <button type="button" class="btn page_btn" @click.prevent="onLastText">
        {{ lastText }}
      </button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'AppPagination',
  props: {
    total: {
      type: Number,
      default: 0
    },
    size: {
      type: Number,
      default: 10
    },
    value: {
      type: Number,
      default: 1
    },
    buttonClass: {
      type: String,
      default: 'paginate_button'
    },
    firstText: {
      type: String,
      default: '第一頁'
    },
    previousText: {
      type: String,
      default: '<'
    },
    nextText: {
      type: String,
      default: '>'
    },
    lastText: {
      type: String,
      default: '最後一頁'
    }
  },
  data() {
    return {
      nowPage: this.value
    }
  },
  computed: {
    pages() {
      const pages = Math.ceil(this.total / this.size)
      return pages || 1
    },
    currentPage: {
      get() {
        return this.nowPage
      },
      set(value) {
        this.nowPage = value
        this.$refs.inputPage.value = value
      }
    }
  },
  watch: {
    nowPage(newValue) {
      if (newValue) {
        this.$emit('input-Page', +newValue)
      }
    }
  },
  methods: {
    onFirstClick() {
      this.currentPage = 1
    },
    onPreviousClick() {
      if (this.nowPage > 1) {
        this.currentPage = this.nowPage - 1
      }
    },
    onNextClick() {
      if (this.nowPage < this.pages) {
        this.currentPage = this.nowPage + 1
      }
    },
    onLastText() {
      this.currentPage = this.pages
    },
    onPageInput(ev) {
      const inputPage = ev.target.value
      if (!inputPage) {
        this.nowPage = 1
      } else {
        if (isNaN(inputPage)) {
          this.currentPage = 1
        } else {
          if (+inputPage > this.pages || +inputPage <= 0) {
            this.currentPage = 1
          } else {
            this.currentPage = +inputPage
          }
        }
      }
    },
    onBlur() {
      if (!this.$refs.inputPage.value) {
        this.currentPage = 1
      }
    }
  }
}
</script>
<style scoped>
/* .paginate_button {
  box-sizing: border-box;
  display: inline-block;
  min-width: 1.5em;
  padding: 0.5em 1em;
  margin-left: 2px;
  text-align: center;
  text-decoration: none !important;
  cursor: pointer;
  *cursor: hand;
  color: #333 !important;
  border-radius: 2px;
  border: 1px solid #ddd;
  margin: 0;
  outline: none;
}
.paginate_text {
  box-sizing: border-box;
  display: inline-block;
  min-width: 1.5em;
  padding: 0.5em 1em;
  margin-left: 2px;
  text-align: center;
  text-decoration: none !important;
  *cursor: hand;
  color: #333 !important;
  border-radius: 2px;
  margin: 0;
  outline: none;
} */
</style>
