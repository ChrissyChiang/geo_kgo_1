<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm-auto">{{ fText }}</span>
    <span class="col-sm-auto">{{ disabled ? value : showvalue }}</span>
    <span v-if="bText != ''" class="col-sm-auto">{{ bText }}</span>
  </div>
</template>

<script>
export default {
  name: 'Date',
  data() {
    return {
      pfDateV: [],
      valueV: ''
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
    showvalue: {
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
