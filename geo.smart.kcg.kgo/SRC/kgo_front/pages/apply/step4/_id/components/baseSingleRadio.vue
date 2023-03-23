<template>
  <validate-container :rules="required ? 'required' : ''" :name="id">
    <label v-if="fText != ''" :for="id">{{ fText }}</label>
    <input
      :name="group"
      type="radio"
      :checked="valueV"
      :id="id"
      @change="check($event.target.value)"
      @uncheck="uncheck"
      disabled
    />
    <label v-if="bText != ''" :for="id">{{ bText }}</label>
  </validate-container>
</template>



<script>
export default {
  name: 'baseSingleRadio',
  data() {
    return {
      valueV: this.datavalue != '',
      select: ''
    }
  },
  watch: {
    valueV(newVal) {
      //console.log(this.valueV)
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
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    group: {
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
    id: {
      type: String,
      default: ''
    }
  },
  methods: {
    check(val) {
      this.$emit('update:datavalue', this.value)
      this.$emit('update:showvalue', this.value)
    },
    uncheck() {
      this.$emit('update:datavalue', '')
      this.$emit('update:showvalue', '')
    }
  }
}
</script>
