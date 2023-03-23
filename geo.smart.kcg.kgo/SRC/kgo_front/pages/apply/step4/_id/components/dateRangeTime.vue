<template>
  <validate-container :rules="'required'">
    <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span class="col-sm-auto">{{ showvalue }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
  </validate-container>
</template>
<script>
export default {
   name: 'DateRangeTime',
  data() {
    return {
      //pfDate: [],
      valueV: this.value,
      valueList: [],
    }
  },
  props: {
    value: {
      type: String,
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
    disabled: {
      type: Boolean,
      default: false
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
    value() {
      this.getDataList()
      //console.log(this.pfDateV)
    }
  },
  mounted() {
    this.getDataList()
  },
  /*computed: {
   pfDateV:{
      get(){
        if(this.value != ''){
          let value = this.value
          let temList = value.split('-')
          let dataList = []
          dataList.push(temList[0])
          dataList.push(temList[1])
          return dataList
        }
        else{
          return []
        }
      },
      set(newValue){
        this.pfDate = newValue
      }
   }
  },*/
  methods: {
    inputDate() {
      this.valueV = this.valueList.join('-')
    },
    getDataList() {
      /*if(this.value != ''){
        let value = this.value
        let temList = value.split('-')
        let dataList = []
        dataList.push(temList[0])
        dataList.push(temList[1])
        this.valueList = dataList
        this.valueV = this.valueList.join('-')
      }
      else{
        this.valueList = []
        this.valueV = ""
      }*/
    }
  }
}
</script>