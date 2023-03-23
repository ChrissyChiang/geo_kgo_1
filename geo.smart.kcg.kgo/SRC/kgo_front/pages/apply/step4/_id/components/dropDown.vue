<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span v-if="show != ''" class="col-sm-auto">{{ show }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
</template>
<script>
export default {
  name: 'DropDown',
  data() {
    let dataList = []
    var showval = ''
    if (this.value != '') {
      let value = this.value
      let temList = value.split(',')

      for (let i = 0; i < temList.length; i++) {
        let temList2 = temList[i].split('-')
        
        dataList.push({ value: temList2[0], label: temList2[1] })
      }
      if (dataList) {
        var item = dataList.find(x => x.value == this.datavalue)
        if (item) showval = dataList.find(x => x.value == this.datavalue).label
      }
    }

    return {
      valueV: this.selectedValue,
      dataItems: dataList,
      show: showval
    }
  },
  props: {
    /**下拉選單value&label */
    value: {
      type: String,
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    },
    selectedValue: {
      type: String,
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    parent: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputLength: {
      type: Number,
      default: 50
    },
    datavalue: {
      type: [String, Number],
      default: ''
    }
  },
  watch: {
    selectedValue() {
      this.valueV = this.selectedValue
    },
    valueV() {
      this.$emit('update:datavalue', this.valueV)
      var label = this.dataItems.find(x => x.value == this.valueV).label
    }
  },

  methods: {
    setSelect(value) {
      if (this.selectedValue == '') {
        this.valueV = value
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.is-error {
  border-color: #d33f39;
}
</style>
