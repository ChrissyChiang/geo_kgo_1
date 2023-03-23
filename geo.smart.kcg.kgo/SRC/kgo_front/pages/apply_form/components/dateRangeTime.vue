<template>
  <validate-container :rules="(required ? '|required' : '')">
    <div class="row">
      <div v-if="fText != ''" class="col-sm-auto">
        <span>{{ fText }}</span>
      </div>
      <div class="col-sm-auto">
        <base-date
          placeholder="請選擇日期時間區間"
          :date-type="'datetime'"
          :disabled="disabled"
          :date-format="'YYYY/MM/DD hh:mm'"
          v-model="valueList"
          :is-range="true"
          :value="valueList"
          @input="inputDate"
          style="min-width:300px"
        />
      </div>
      <div v-if="bText != ''" class="col-sm-auto">
        <span>{{ bText }}</span>
      </div>
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
      valueList: []
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
    },
    valueList() {
      var val = this.valueList.join('-')
      this.$emit('update:datavalue', val)
      this.$emit('update:showvalue', val)
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
      this.$emit('update:datavalue', this.valueV)
    },
    getDataList() {
      if (this.value != '') {
        let value = this.value
        let temList = value.split('-')
        let dataList = []
        dataList.push(temList[0])
        dataList.push(temList[1])
        this.valueList = dataList
        this.valueV = this.valueList.join('-')
      } else {
        this.valueList = []
        this.valueV = ''
      }
    }
  }
}
</script>
