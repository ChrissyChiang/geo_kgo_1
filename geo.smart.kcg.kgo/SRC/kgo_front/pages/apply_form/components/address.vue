<template>
  <div>
    <div class="form-row">
      <div v-if="fText != ''" class="col-sm">
        <span>{{ fText }}</span>
      </div>
      <div class="col">
        <select
          class="custom-select form-control"
          ref="selectbase"
          v-model="valueC"
          title="請選擇"
          v-bind="$attrs"
          :disabled="disabled"
        >
          <option
            v-for="option in countyOptionList"
            :key="option.value"
            :value="option.value"
          >
            {{ option.label }}
          </option>
        </select>
      </div>
      <div class="col">
        <select
          ref="selectbase"
          v-model="valueZ"
          title="請選擇"
          class="custom-select form-control"
          v-bind="$attrs"
          :disabled="disabled"
        >
          <option
            v-for="option in zipOptionsList"
            :key="option.value"
            :value="option.value"
          >
            {{ option.label }}
          </option>
        </select>
      </div>
      <div class="col-12 col-md-6 mt-2 mt-md-0">
        <input
          type="text"
          class="form-control"
          v-model="address"
          placeholder="請輸入地址"
          :maxlength="inputLength"
          :disabled="disabled"
          style="min-width:250px"
        />
      </div>
      <div v-if="bText != ''" class="col-sm">
        <span>{{ bText }}</span>
      </div>
    </div>
    <validate-container v-slot="data" :rules="required ? validrule : ''">
      <input type="hidden" v-model="validValue" class="col" />
      <span class="error_label w-100">{{ data[0] }}</span>
    </validate-container>
  </div>
</template>
<script>
export default {
  name: 'addressInput',
  data() {
    
    var vc = ''
    var vz = ''
    var addr = ''
    if (this.datavalue) {
      var items = this.datavalue.split('＠')
      if (items.length == 3) {
        vc = this.options[0].options.filter(x => x.label == items[0])[0].value
        vz = this.options[1].options.filter(x => x.label == items[1])[0].value
        addr = items[2]
      } else {
        addr = this.datavalue
      }
    } else {
      //###預設顯示第一個縣市
      vc = this.options[0].options[0].value
    }
    var zipOptionsList = this.options[1].options.filter(
      item => item.groupKey == vc
    )

    var rule = 'required'
    if (this.parent && this.parent != '') {
      rule = 'required_parent:@' + this.parent
    }

    return {
      valueV: '',
      valueC: vc,
      valueZ: vz,
      countyOptionList: this.options[0].options || [],
      zipOptionsList: zipOptionsList,
      address: addr,
      validValue: addr,
      validrule: rule
    }
  },
  props: {
    parent: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
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
    showvalue: {
      type: [String, Number],
      default: ''
    },
    inputLength: {
      type: Number,
      default: 50
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
    }
  },
  watch: {
    options() {
      this.valueV = this.value
      this.countyOptionList = this.options[0].options || []
      this.zipOptionsList = this.options[1].options || []
    },
    valueC() {
      this.zipOptionsList = this.options[1].options.filter(
        item => item.groupKey == this.valueC
      )
      this.setValue()
    },
    valueZ() {
      this.setValue()
    },
    address() {
      this.setValue()
    }
  },
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
      this.validValue =
        this.valueC != '' && this.valueZ != '' && this.address.trim() != ''
          ? val
          : ''
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
