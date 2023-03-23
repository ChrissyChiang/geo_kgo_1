<template>
  <div>
    <div class="form-row">
      <div v-if="fText != ''" class="col-sm">
        <span>{{ fText }}</span>
      </div>
      <div class="col-6 col-md-3">
        <div class="input-group">
          <select
            ref="selectbase"
            v-model="valueC"
            title="請選擇縣市"
            name="state"
            class="custom-select form-control"
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

          <span class="col-form-label">縣市</span>
        </div>
      </div>
      <div class="col-6 col-md-3">
        <div class="input-group">
          <select
            ref="selectbase"
            v-model="valueZ"
            title="請選擇行政區"
            name="state"
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

          <span class="col-form-label">行政區</span>
        </div>
      </div>
      <div class="col-6 col-md-4">
        <div class="input-group">
          <select
            ref="selectbase"
            v-model="valueK"
            title="請選擇段小段"
            name="state"
            class="custom-select form-control"
            v-bind="$attrs"
            :disabled="disabled"
          >
            <option
              v-for="option in kcntOptionsList"
              :key="option.value"
              :value="option.value"
            >
              {{ option.label }}
            </option>
          </select>

          <span class="col-form-label">段小段</span>
        </div>
      </div>
      <div class="col-6 col-md-2 mt-2 mt-md-0">
        <div class="input-group">
          <input
            id="land_num"
            v-model="num"
            title="請輸入地號"
            type="text"
            class="form-control"
            @input="change"
            :disabled="disabled"
          />

          <span class="col-form-label">地號</span>
        </div>
      </div>

      <div v-if="bText != ''" class="col-sm">
        <span>{{ bText }}</span>
      </div>
    </div>
    <validate-container v-slot="data" :rules="required ? 'required' : ''">
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
    var vk = ''
    var nc = ''
    var nz = ''
    var nk = ''
    var n = ''
    var zipOptionsList = []
    var kcntOptionsList = []
    if (this.datavalue) {
      var items = this.datavalue.split('＠')
      if (items.length == 4) {
        vc =
          items[0] == ''
            ? ''
            : this.options[0].options.find(x => x.label == items[0]).value
        nc =
          items[0] == ''
            ? ''
            : this.options[0].options.find(x => x.label == items[0]).label

        if (vc != '') {
          vz =
            items[1] == ''
              ? ''
              : this.options[1].options.find(x => x.label == items[1]).value
          nz =
            items[1] == ''
              ? ''
              : this.options[1].options.find(x => x.label == items[1]).label
        }
        if (vz != '') {
          vk =
            items[2] == ''
              ? ''
              : this.options[2].options.find(x => x.label == items[2]).value
          nk =
            items[2] == ''
              ? ''
              : this.options[2].options.find(x => x.label == items[2]).label
          kcntOptionsList = this.options[2].options.filter(
            item => item.groupKey == vz
          )
        }
        n = items[3]
      } else {
        n = this.datavalue
      }
    } else {
      vc = this.options[0].options[0].value
      nc = this.options[0].options[0].label
    }

    zipOptionsList = this.options[1].options.filter(item => item.groupKey == vc)

    var validValue =
      nc != '' && nz != '' && (nk != '' + n.trim()) != '' ? n : ''

    return {
      valueV: '',
      valueC: vc,
      valueZ: vz,
      valueK: vk,
      nameC: nc,
      nameZ: nz,
      nameK: nk,
      countyOptionList: this.options[0].options,
      zipOptionsList: zipOptionsList,
      kcntOptionsList: kcntOptionsList,
      num: n,
      validValue: validValue
    }
  },
  watch: {
    /*value() {
      this.valueV = this.value
      this.countyOptionList = this.valueV[0].options
      this.zipOptionsList = this.valueV[1].options
      this.kcntOptionsList = this.valueV[2].options
    },*/
    options() {
      this.countyOptionList = this.options[0].options
      this.zipOptionsList = this.options[1].options
      this.kcntOptionsList = this.options[2].options
    },
    valueC() {
      this.zipOptionsList = this.options[1].options.filter(
        item => item.groupKey == this.valueC
      )
      this.kcntOptionsList = []
      this.nameK = ''
      this.nameC = this.options[0].options.find(
        item => item.value == this.valueC
      ).label
      this.change()
    },
    valueZ() {
      this.nameZ = this.options[1].options.find(
        item => item.value == this.valueZ
      ).label
      this.kcntOptionsList = this.options[2].options.filter(
        item => item.groupKey == this.valueZ
      )
      this.change()
    },
    valueK() {
      this.nameK = this.options[2].options.find(
        item => item.value == this.valueK
      ).label
      this.change()
    },
    num() {
      this.change()
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
    required: {
      type: Boolean,
      default: true
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputLength: {
      type: Number,
      default: 50
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    datavalue: {
      type: String,
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    }
  },
  methods: {
    setSelect(value) {
      this.valueV = value
    },
    change() {
      let val =
        this.nameC + '＠' + this.nameZ + '＠' + this.nameK + '＠' + this.num
      let address = this.nameC + this.nameZ + this.nameK + this.num + '號'
      this.$emit('update:datavalue', val)
      this.$emit('update:showvalue', address)

      this.validValue =
        this.nameC != '' &&
        this.nameZ != '' &&
        (this.nameK != '' + this.num.trim()) != ''
          ? val
          : ''
    }
  }
}
</script>
