<template>
  <validate-container>
    <div class="row">
      <!--<span v-if="fText != ''" class="col-sm"></span>-->
      <div class="col-sm-auto">
        <span>{{ fText }}{{ valueV }}{{ bText }}</span>
      </div>
      <!--<span v-if="bText != ''" class="col-sm"></span>-->
    </div>
  </validate-container>
</template>

<script>
export default {
  name: 'TextLabel',
  data() {
    return {
      valueV: this.value
    }
  },
  props: {
    value: {
      type: [String, Number],
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
    inputLength: {
      type: Number,
      default: 50
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    value() {
      this.valueV = this.value
    }
  }
}
</script>
