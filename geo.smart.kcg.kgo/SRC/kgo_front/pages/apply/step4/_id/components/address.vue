<template>
  <div class="form-row">
    <div class="col">
      {{ showvalue }}
    </div>
  </div>
</template>
<script>
export default {
  name: 'addressInput',
  data() {
    return {
      valueV: '',
      valueC: '',
      valueZ: '',
      address: ''
    }
  },
  props: {
    /**資料來源陣列 */
    options: {
      type: Array,
      default: () => []
    },
    /**下拉選單value&label */
    value: {
      type: String,
      default: ''
    },
    datavalue: {
      type: [String, Number],
      default: ''
    },
    inputLength: {
      type: Number,
      default: 50
    },
    showvalue: {
      type: [String, Number],
      default: ''
    }
  },
  watch: {},
  methods: {
    setValue() {
      var val = ''
      var county = ''
      if (this.valueC != '') {
        var countyData = this.options[0].options.filter(
          x => x.value == this.valueC
        )
        county += countyData[0].label
        val += countyData[0].label
      }

      if (this.valueZ != '') {
        var zipData = this.options[1].options.filter(
          x => x.value == this.valueZ
        )
        county += zipData[0].label
        val += '＠' + zipData[0].label
      } else {
        val += '＠'
      }
      county += this.address
      val += '＠' + this.address
      this.$emit('update:datavalue', val)
      this.$emit('update:showvalue', county)
    }
  }
  /*computed: {
    optionList() {
      let value = this.value
      let temList = value.split(',')
      let dataList = []
      for (let i = 0; i < temList.length; i++) {
        let temList2 = temList[i].split('-')
        if (i == 0) {
          this.setSelect(temList2[0])
        }
        dataList.push({ value: temList2[0], label: temList2[1] })
      }
      return dataList
    }
  },*/
}
</script>
