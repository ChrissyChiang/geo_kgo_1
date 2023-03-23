<template>
  <div>
    <label v-if="label != ''" for>{{ label }}</label>
    <textarea
      id
      name
      class="form-control"
      :class="textareaClass"
      cols="30"
      :rows="row"
      :maxlength="max"
      :value="value"
      :placeholder="placeholder"
      :disabled="disabled"
      :style="textareaStyle"
      v-bind="$attrs"
      v-on="listeners"
      @keyup="inputText"
      @input="$emit('input', $event.target.value)"
    ></textarea>
    <!--<div class="formInput_description">
      限{{ max }}字(中文、英文、數字或符號)，目前已
      <span class="formInput_count">{{ inputText() }}</span>
      字
    </div>-->
  </div>
</template>

<script>
export default {
  inheritAttrs: false,
  props: {
    label: {
      type: String,
      default: ''
    },
    textareaClass: {
      type: String,
      default: ''
    },
    textareaStyle: {
      type: String,
      default: ''
    },
    row: {
      type: Number,
      default: 6
    },
    value: {
      type: [String, Number],
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    max: {
      type: String,
      default: '100'
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {}
  },
  computed: {
    listeners() {
      // eslint-disable-next-line no-unused-vars
      const { input, ...listeners } = this.$listeners
      return listeners
    }
  },
  // watch: {
  //   value: 'inputText'
  // },
  methods: {
    inputText() {
      if (this.value === null || this.value === undefined) {
        return 0
      } else {
        return this.value.length
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.tip {
  color: gray;
  font-size: 10px;
}
</style>
