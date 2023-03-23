<template>
  <validate-container :rules="required ? validRule : ''">
    <div class="row">
      <div v-if="fText != ''" class="col-sm-auto">
        <span>{{ fText }}</span>
      </div>
      <div class="col-sm-auto">
        <base-date
          placeholder="請選擇日期"
          v-model="valueV"
          :disabled="disabled"
          title="請選擇日期"
          :is-range="false"
          :value="valueV"
          style="min-width:200px"
        />
      </div>
      <div v-if="bText != ''" class="col-sm-auto">
        <span>{{ bText }}</span>
      </div>
    </div>
  </validate-container>
</template>

<script>
import moment from 'moment' //format套件
export default {
  name: 'Date',
  data() {
    var date = ''
    if (this.datavalue) {
      date = this.datavalue
    } else {
      date = moment().format('YYYY/MM/DD')
      this.$emit('update:datavalue', date)
      this.$emit('update:showvalue', date)
    }

     var rule = 'required'
    if (this.parent && this.parent != '') {
      rule = 'required_parent:@'+this.parent
    }
    return {
      pfDateV: [],
      valueV: date,
      validRule:rule
      //valueV: this.value
    }
  },
  props: {
    value: {
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
    }
  },
  computed: {
    pfDate: {
      get() {
        let data = this.value
        if (this.value.length < 8) {
          //民國年
          data =
            parseInt(this.value.substr(0, 3)) +
            1911 +
            '/' +
            this.value.substr(3, 2) +
            '/' +
            this.value.substr(5, 2)
        }
        this.valueV = data
        return data
      },
      set(newValue) {
        this.valueV = newValue
      }
    }
  },
  watch: {
    valueV() {
      this.$emit('update:datavalue', this.valueV)
      this.$emit('update:showvalue', this.valueV)
    },
    value() {
      this.valueV = this.value
    }
  }
}
</script>
