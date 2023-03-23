<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span v-if="showSelectedLabel != ''" class="col-sm-auto">{{ showSelectedLabel }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
</template>

<script>
export default {
  name: 'baseRadio',
  data() {
    let selectedVal = this.datavalue.split(',')
    let temList = this.showvalue.split(',')
    let showSelectedLabel = ''
    let dataList = []
    for (let i = 0; i < temList.length; i++) {
      let temList2 = temList[i].split('-')
      dataList.push({ value: temList2[0], label: temList2[1] })
    }
    dataList.forEach( item =>{
      selectedVal.forEach( item2 =>{
        if(item.value == item2){
          showSelectedLabel = item.label
        }
      })
    })

    return {
      showSelectedLabel: showSelectedLabel
    }
  },
  watch: {
    valueV() {
      var items = this.allData.filter(x => x.value == this.valueV)
      if (items) {
        this.$emit('update:datavalue', this.valueV)
        this.$emit('update:showvalue', items[0].label)
      }
    }
  },
  props: {
    id: {
      type: String,
      default: ''
    },
    value: {
      type: String,
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
    datavalue: {
      type: [String, Number],
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    }
  },
}
</script>
