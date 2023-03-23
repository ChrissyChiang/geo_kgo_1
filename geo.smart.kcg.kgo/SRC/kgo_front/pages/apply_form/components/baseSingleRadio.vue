<template>
  <validate-container :rules="required ? validrule : ''" :name="id">
    <div class="form-check-inline">
      <span v-if="fText != ''">{{ fText }}</span>
      <div class="custom-control custom-radio">
        <input
          type="radio"
          :id="id"
          class="custom-control-input"
          v-model="valueV"
          value="Y"
          :title="bText"
          :name="group"
          :disabled="disabled"
          @change="check($event.target.value)"
          @uncheck="uncheck"
        />
        <label class="custom-control-label" :for="id">&zwnj;</label>
      </div>
      <span class="" v-if="bText != ''">
        {{ bText }}
      </span>
    </div>

    <!-- <label v-if="fText != ''" :for="$attrs.id">{{ fText }}</label>
    <input
      :name="group"
      type="radio"
      v-model="valueV"
      value="Y"
      :id="id"
      @change="check($event.target.value)"
      @uncheck="uncheck"
      v-bind="$attrs"
      :title="bText"
      :disabled="disabled"
    />
    <label :title="bText" v-if="bText != ''" :for="$attrs.id">
      {{ bText }}
    </label> -->
  </validate-container>
</template>



<script>
export default {
  name: 'baseSingleRadio',

  data() {
    var id = this.id
    if (this.datavalue != '') {
      $(function () {
        document.getElementById(id).dispatchEvent(new CustomEvent('click'))
      })
    }

    return {
      valueV: this.datavalue,
      select: '',
      validrule: 'required'
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
    disabled: {
      type: Boolean,
      default: false
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
      this.$emit('update:datavalue', 'Y')
      this.$emit('update:showvalue', 'Y')
    },
    uncheck(e) {
      //###會觸發uncheck表示同radio group中有其他的radio被選中,因此清除此radio的值###
      this.valueV = ''
      this.$emit('update:datavalue', '')
      this.$emit('update:showvalue', '')

      //##如果此radio是必填，但又觸發uncheck，表示不需要再驗證此radio是必填
      this.validrule = ''
    }
  }
}
</script>
