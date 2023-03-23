<template>
  <div style="display: inline-flex;">
    <div>
      <select v-model="selectMonth" class="form-control">
        <option
          v-for="item in monthOptions"
          :key="item.value"
          :value="item.value"
        >
          {{ item.label }}
        </option>
      </select>
      月
    </div>
    <div>
      <select v-model="selectDay" class="form-control">
        <option
          v-for="item in dayOptions"
          :key="item.value"
          :value="item.value"
        >
          {{ item.label }}
        </option>
      </select>
      日
    </div>
  </div>
</template>
<script>
export default {
  name: 'BaseMonthDay',
  props: {
    select: {
      type: String,
      default: '0101'
    }
  },
  data() {
    return {
      monthOptions: [],
      dayOptions: [],
      selectMonth: '01',
      selectDay: '01'
    }
  },
  watch: {
    select(newVal) {
      if (newVal && newVal.length == 4) {
        this.selectMonth = newVal.slice(0, 2)
        this.selectDay = newVal.slice(-2)
      }
    },
    selectMonth(newVal) {
      this.$emit('input', this.selectMonth + this.selectDay)
    },
    selectDay(newVal) {
      this.$emit('input', this.selectMonth + this.selectDay)
    }
  },
  mounted() {
    this.initMonthOptions()
    this.initDayOptions()
  },
  methods: {
    initMonthOptions() {
      for (let index = 1; index < 13; index++) {
        this.monthOptions.push({
          label: index.toString(),
          value: ('0' + index).slice(-2)
        })
      }
    },
    initDayOptions() {
      for (let index = 1; index < 32; index++) {
        this.dayOptions.push({
          label: index.toString(),
          value: ('0' + index).slice(-2)
        })
      }
    }
  }
}
</script>
