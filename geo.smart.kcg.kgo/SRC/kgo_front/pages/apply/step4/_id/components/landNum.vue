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
      valueK: '',
      num: '',
      countyOptionList: [],
      zipOptionsList: [],
      kcntOptionsList: []
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
    showvalue: {
      type: [String, Number],
      default: ''
    }
  },
  computed: {
      /*valueC() {
        let temList = this.value.split(',')
        let temList2 = temList[0].split('-')
        return temList2[0]
      },
      valueZ() {
        let temList = this.value.split(',')
        let temList2 = temList[0].split('-')
        return temList2[0]
      },
      valueK() {
        let temList = this.value.split(',')
        let temList2 = temList[0].split('-')
        return temList2[0]
      },
      num() {
        let temList = this.value.split(',')
        return temList2[1]
      },
      countyOptionList() {
        let temList = this.value.split(',')
        let temList2 = temList[0].split('-')
        return [{value: temList2[0],label: temList2[1]}]
      },
      zipOptionsList() {
        let temList = this.value.split(',')
        let temList2 = temList[0].split('-')
        return [{value: temList2[0],label: temList2[1]}]
      },
      kcntOptionsList() {
        let temList = this.value.split(',')
        let temList2 = temList[0].split('-')
        return [{value: temList2[0],label: temList2[1]}]
      }*/
  },
  watch: {
    value() {
      this.valueV = this.value
      this.countyOptionList = this.valueV[0].options
      this.zipOptionsList = this.valueV[1].options
      this.kcntOptionsList = this.valueV[2].options
    },
    
  },
  methods: {
    setSelect(value) {
      this.valueV = value
    },
    change() {
      this.valueV = this.valueC+'/'+this.valueZ+'/'+this.valueK+'/'+this.num
      //console.log(this.valueC+'/'+this.valueZ+'/'+this.valueK+'/'+this.num)
    }
  }
}
</script>