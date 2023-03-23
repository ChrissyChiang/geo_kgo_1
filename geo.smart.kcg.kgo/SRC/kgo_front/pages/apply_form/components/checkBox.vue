<template>
  <div class="v-center">
    <div
      class="custom-control custom-checkbox custom-control-inline"
      v-for="(item, index) in dataList"
      :key="index"
    >
      <input
        type="checkbox"
        class="custom-control-input"
        v-model="item.select"
        :value="item.value"
        :id="'customCheck' + id + item.value + index"
        v-bind="$attrs"
        @change="change"
        :disabled="disabled"
      />
      <label class="custom-control-label" :for="'customCheck' + id + item.value + index">
        {{ item.label }}
      </label>
    </div>
  </div>
</template>

<script>
export default {
  name: 'checkBox',
  data() {
    let value = this.value
    let valueList = []
    let selectedValue = []
    selectedValue =this.datavalue =='' ? [] : this.datavalue.split(',')
    if(selectedValue.length >0){
      selectedValue.forEach(item =>{
        valueList.push(item)
      })
      /*for(let i=0;i<this.datavalue.length;i++){
        if(this.datavalue[i] != ','){
          valueList.push(this.datavalue[i])
        }
      }*/
    }
    let temList = value.split(',')
    var date = []
    for (let i = 0; i < temList.length; i++) {
      let temList2 = temList[i].split('-')
      //dataList.push({value: (temList2[0] == 'true' ? true : false),label: temList2[1],select: false})
      if (this.datavalue.includes(temList2[0])) {
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
    return {
      valueList: valueList,
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
    disabled: {
      type: Boolean,
      default: false
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
    id: {
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
      //this.$emit('update:showvalue', showval)
    }
  }
}
</script>
