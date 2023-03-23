<template>
  <div>
    <label :class="getLabelClass" :for="labelFor">
      <span v-if="isRequired" style="color: red">*</span>
      {{ label }}
    </label>

    <div v-if="rules" :class="getInputClass">
      <validate-container v-slot="data" :rules="rules">
        <slot v-bind="{ ...data }" />
      </validate-container>
    </div>

    <div v-else :class="getInputClass">
      <slot />
    </div>

    <slot name="suffix" :class="suffixClass" />
  </div>
</template>

<script>
export default {
  name: 'FormGroup',
  props: {
    label: {
      type: String,
      default: ''
    },
    labelFor: {
      type: String,
      default: 'input_event'
    },
    rules: {
      type: String,
      default: ''
    },
    inputClass: {
      type: String,
      default: ''
    },
    labelClass: {
      type: String,
      default: ''
    },
    suffixClass: {
      type: String,
      default: ''
    }
  },
  computed: {
    isRequired() {
      return this.rules.includes('required')
    },
    getInputClass() {
      return this.inputClass || 'col-8 col-sm-9 col-lg-8'
    },
    getLabelClass() {
      return this.labelClass || ''
    }
  }
}
</script>

<style lang="scss" scoped>
.form-group__label {
  font-size: 16px;
  color: #000;
  text-align: right;
  letter-spacing: 2.5px;
  width: 80px;
  margin-bottom: 0;
}

.min {
  width: 50px;
}

.mid {
  width: 100px;
}

.max {
  width: 200px;
}
</style>
