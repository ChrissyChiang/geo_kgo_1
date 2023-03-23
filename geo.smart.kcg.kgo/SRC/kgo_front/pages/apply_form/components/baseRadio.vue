<template>
  <validate-container :rules="required ? 'required' : ''">
    <div
      class="custom-control custom-radio custom-control-inline"
      v-for="(item, index) in allData"
      :key="index"
    >
      <input
        v-model="valueV"
        :name="id"
        type="radio"
        :id="id + item.value"
        :value="item.value"
        class="custom-control-input"
        :title="item.label"
        :disabled="disabled"
      />
      <label class="custom-control-label" :for="id + item.value">
        {{ item.label }}
      </label>
    </div>
  </validate-container>
</template>

<script>
export default {
  name: 'baseRadio',
  data() {
    let value = this.value
    let temList = value.split(',')
    let dataList = []
    for (let i = 0; i < temList.length; i++) {
      let temList2 = temList[i].split('-')
      if (i == 0) {
        // 若為必選則將第一個選項設為預設
        this.required ? this.setSelect(temList2[0]) : this.setSelect('')
      }
      dataList.push({ value: temList2[0], label: temList2[1] })
    }
    let valueV = this.datavalue

    return {
      valueV: valueV,
      select: '',
      allData: dataList
    }
  },
  watch: {
    selectedValue() {
      // this.valueV = this.selectedValue
    },
    valueV() {
      var items = this.allData.filter(x => x.value == this.valueV)
      if (items) {
        this.$emit('update:datavalue', this.valueV)
        //this.$emit('update:showvalue', items[0].label)
      }
    }
  },
  props: {
    id: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
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

  methods: {
    setSelect(value) {
      // if (this.selectedValue == '') {
      //   this.valueV = value
      //   this.$emit('update:datavalue', this.valueV)
      // }
    }
  }
}
</script>
