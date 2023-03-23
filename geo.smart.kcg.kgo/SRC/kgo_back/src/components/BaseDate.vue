<template>
  <date-picker
    v-model="time"
    :clearable="false"
    :popup-class="'datepickerPopup'"
    :editable="false"
    v-bind="$attrs"
    :format="dateFormat"
    :type="dateType"
    :value-type="dateFormat"
    :lang="dpLang"
    :range="isRange"
    @input="inputDate"
  ></date-picker>
</template>
<script>
/** 要注意放置區域內的
 * css屬性若有overflow-x設置為
 * scroll&hidden&auto會造成Popup滾動位置異常 */
import DatePicker from 'vue2-datepicker'
//import 'vue2-datepicker/index.css'
import '@/assets/css/datepicker.css'
import 'vue2-datepicker/locale/zh-tw'
import { vuexStore } from '@/mixins'
export default {
  components: {
    DatePicker
  },
  mixins: [vuexStore],
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
    }
  },
  data() {
    return {
      time: this.value
    }
  },
  computed: {
    dpLang() {
      return 'zh-tw'
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
<style lang="scss">
.mx-icon-calendar {
  display: none;
}
</style>
