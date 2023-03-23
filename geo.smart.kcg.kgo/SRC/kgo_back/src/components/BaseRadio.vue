<template>
  <div style="display: inline-block;margin: 0px 10px;">
    <input
      :id="$attrs.id || `input-checkbox-${_uid}`"
      v-model="nowSelect"
      class="form-check-input"
      type="radio"
      v-bind="$attrs"
      :name="itemName"
      :disabled="disabled"
      :value="value"
      v-on="listeners"
      @change="$emit('input', $event.target.value)"
    />
    <label
      class="form-check-label"
      :for="$attrs.id || `input-checkbox-${_uid}`"
    >
      {{ label }}
    </label>
  </div>
</template>

<script>
/** 組件用法
<base-radio
    v-for="item in testoptions"
    :key="item.value"
    :item-name="'test1'"
    :label="item.label"
    :value="item.value"
    :select="testValue"
    v-model="testValue"
/>
 */
export default {
  name: 'BaseRadio',
  inheritAttrs: false,
  model: {
    prop: 'checked'
    //event: 'change'
  },
  props: {
    itemName: {
      type: [String, Number, Array],
      default: () => []
    },
    label: {
      type: String,
      default: ''
    },
    labelClass: {
      type: String,
      default: ''
    },
    value: {
      type: [Number, String],
      default: null
    },
    disabled: {
      type: Boolean,
      default: false
    },
    /** 已選的值 */
    select: {
      type: String,
      default: ''
    },
    containerClass: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      nowSelect: this.select
    }
  },
  computed: {
    listeners() {
      // eslint-disable-next-line no-unused-vars
      const { input, ...listeners } = this.$listeners
      return listeners
    }
  },
  watch: {
    select(newValue) {
      this.nowSelect = newValue
    }
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.radioInput {
  width: 24px;
  height: 24px;
  margin: 0;
  padding: 0;
  position: absolute;
  cursor: pointer;
  opacity: 1;
}
</style>
