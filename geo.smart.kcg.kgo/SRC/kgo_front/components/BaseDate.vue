<template>
  <date-picker
    v-model="time"
    :clearable="false"
    :popup-class="'datepickerPopup'"
    :editable="editable"
    v-bind="$attrs"
    :format="dateFormat"
    :type="dateType"
    :value-type="dateFormat"
    :range="isRange"
    :lang="dpLang"
    @input="inputDate"
  ></date-picker>
</template>
<script>
/** 要注意放置區域內的
 * css屬性若有overflow-x設置為
 * scroll&hidden&auto會造成Popup滾動位置異常 */
import DatePicker from 'vue2-datepicker'
//import 'vue2-datepicker/index.css'
import '../assets/css/baseDate/index.css'
import 'vue2-datepicker/locale/zh-tw'
export default {
  components: {
    DatePicker
  },
  //mixins: [vuexStore],
  inheritAttrs: false,
  props: {
    isRange: {
      type: Boolean,
      default: false
    },
    value: {
      type: [Array, String],
      default: () => []
    },
    dateType: {
      type: String,
      default: 'date'
    },
    dateFormat: {
      type: String,
      default: 'YYYY/MM/DD'
    },
    editable: {
      type: Boolean,
      default: false
    },
  },
  computed: {
    dpLang() {
      return 'zh-tw'
    }
  },
  data() {
    return {
      time: this.value
    }
  },
  watch: {
    value(newValue) {
      this.time = newValue
    }
  },
  methods: {
    inputDate() {
      this.$emit('input', this.time)
    }
  }
}
</script>
<style lang="scss" scoped>
.is-error {
  border-color: #d33f39;
}
</style>