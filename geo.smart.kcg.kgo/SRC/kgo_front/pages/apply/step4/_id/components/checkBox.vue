<template>
  <div class="row">
    <div v-if="fText != ''" clas="col-sm-auto">
      <span>{{ fText }}</span>
    </div>
    <div class="col-sm-auto custom-control custom-checkbox custom-control-inline">
      <div v-for="(item,index) in showSelectedLabel" :key="index" >
        <input v-if="showSelectedLabel.length != 0" type="checkbox" checked readonly disabled />{{item}}
      </div>
      <!--{{ showSelectedLabel }}-->
    </div>
    <div v-if="bText != ''" clas="col-sm-auto">
      <span>{{ bText }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'checkBox',
  data() {
    //this.datavalue: value1,value3
    //this.showvalue: value1-單選1,value-單選2,value-單選3
    let selectedVal = this.datavalue != '' ? this.datavalue.split(',') : []
    //this.value: [value1,value3]
    let temList = this.showvalue != '' ? this.showvalue.split(',') : []
    //this.value: [value1-單選1,value-單選2,value-單選3]

    let showSelected = []
    let showSelectedLabel = ''
    let date = []
    for (let i = 0; i < temList.length; i++) {
      let temList2 = temList[i].split('-')
      //[value1,單選1]
      if (this.selectedValue.includes(temList2[0])) {
        date.push({
          label: temList2[1],
          value: temList2[0],
          select: true
        })
      } else {
        date.push({
          label: temList2[1],
          value: temList2[0],
          select: false
        })
      }
    }
    date.forEach( item =>{
      selectedVal.forEach( item2 =>{
        if(item.value == item2){
          showSelected.push(item.label)
        }
      })
    })
    //showSelectedLabel = showSelected.join(',')

    return {
      showSelectedLabel: showSelected,
      valueV: [],
      select: '',
      dataList: date,
      check: false
    }
  },
  watch: {},
  props: {
    value: {
      type: String,
      default: ''
    },
    selectedValue: {
      type: String,
      default: ''
    },
    datavalue: {
      type: [String, Number],
      default: ''
    },
    required: {
      type: Boolean,
      default: true
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
  computed: {},
  methods: {
    /*change({ target: { checked, value } }) { 把名稱也帶入DB的方法
      if (checked && !this.valueList.includes(value)) {
        this.valueList.push(value)
      }
      else if (!checked && this.valueList.includes(value)) {
        this.valueList.splice(this.valueList.indexOf(value), 1)
      }
      this.valueV = ''
      for (let i = 0 ; i < this.valueList.length ; i++){
        let tem  = this.dataList.find(item => item.value == this.valueList[i])
        if(i ==0 && this.valueList.length != 1) {
          this.valueV = this.valueV + this.valueList[i] +'-'+ tem.label+','
        }
        else {
          this.valueV = this.valueV + this.valueList[i] +'-'+ tem.label
        }
      }
    }*/
    change({ target: { checked, value } }) {
      if (checked && !this.valueList.includes(value)) {
        this.valueList.push(value)
      } else if (!checked && this.valueList.includes(value)) {
        this.valueList.splice(this.valueList.indexOf(value), 1)
      }
      this.valueV = this.valueList.join(',')

      this.$emit('update:datavalue', this.valueV)
      var showlist = []
      var list = this.dataList
      this.valueList.forEach(val => {
        showlist.push(list.find(x => x.value == val).label)
      })
      var showval = showlist.join(',')
      this.$emit('update:showvalue', showval)
    }
  }
}
</script>
