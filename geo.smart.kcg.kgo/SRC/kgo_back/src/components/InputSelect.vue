<template>
  <div class="col-12" style="padding: 0px ;margin:5px">
    <div class="col-3" style="display: inline-block">
      <label class="form-group__label p-0">
        {{ titleLabel }}
      </label>
    </div>
    <div class="col-9" style="display: inline-block">
      <vue-multiselect
        class="style-chooser"
        v-bind="$attrs"
        :custom-label="getLabel"
        :options="optionKeys"
        :placeholder="inputPlaceholder"
        :show-labels="false"
        :value="value"
        :multiple="multiple"
        :internal-search="false"
        :disabled="disabled"
        v-on="$listeners"
        @search-change="searchChange"
      >
        <span slot="noResult">
          沒有符合的選項
        </span>
      </vue-multiselect>
    </div>
  </div>
</template>

<script>
export default {
  name: 'InputSelect',
  inheritAttrs: false,
  props: {
    multiple: {
      type: Boolean,
      default: false
    },
    label: {
      type: String,
      default: 'value'
    },
    placeholder: {
      type: String,
      default: '請選擇'
    },
    trackBy: {
      type: [Function, String],
      default: 'id'
    },
    required: {
      type: Boolean,
      default: false
    },
    options: {
      type: Array,
      default: () => []
    },
    filterQuery: {
      type: Function,
      default: null
    },
    value: {
      type: [Array, Number, String],
      default: null
    },
    titleLabel: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      query: ''
    }
  },
  computed: {
    optionList() {
      if (this.required) {
        return this.options
      } else {
        return [
          { [this.label]: '請選擇', [this.trackBy]: 'noChoose' },
          ...this.options
        ]
      }
    },
    filterOption() {
      if (this.filterQuery && this.filterQuery(this.query, this.optionList)) {
        return this.filterQuery(this.query, this.optionList)
      }

      return this.optionList.filter(option =>
        option[this.label].toUpperCase().includes(this.query.toUpperCase())
      )
    },
    optionKeys() {
      return this.filterOption.map(option => option[this.trackBy].toString())
    },
    inputPlaceholder() {
      const option = this.optionList.find(
        option => option[this.trackBy] == this.value
      )
      if (this.multiple) {
        return this.placeholder
      }

      return option ? option[this.label] : this.placeholder
    }
  },
  watch: {
    /** 移除 請選擇 項目 */
    value(newValue) {
      if (Array.isArray(newValue)) {
        const index = newValue.indexOf('noChoose')

        if (index !== -1) {
          newValue.splice(index, 1)
          this.$emit('input', newValue)
        }
      } else if (newValue === 'noChoose') {
        this.$emit('input', null)
      }
    }
  },
  methods: {
    getLabel(id) {
      const option = this.optionList.find(option => option[this.trackBy] == id)
      return option ? option[this.label] : ''
    },
    searchChange(query) {
      this.query = query
    }
  }
}
</script>

<style lang="scss">
$placeholder-color: #bfc3ca;

.style-chooser {
  $min-height: 40px;
  $line-height: 40px;
  $option-min-height: 44px;
  $background-color: rgb(245, 246, 248);
  $border: none;
  $border-radius: 0;
  $color: #8e8e8e;
  $dark-color: #555;
  $font-size: 15px;
  $content-border: 1px solid rgba(0, 0, 0, 0.15);
  $content-border-radius: 5px;
  $content-box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);

  // 電腦版
  &.multiselect {
    &.multiselect--active {
      border: 1px solid #4686ff;
      box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
    }

    .multiselect__tags {
      background-color: $background-color;
      border: $border;
      border-radius: $border-radius;
      color: $color;
      font-size: $font-size;
      padding: 0 40px 0 10px;

      .multiselect__tag {
        background-color: #e1e8f9;
        font-size: 14px;
        color: $color;
        margin-top: 8px;
        margin-bottom: 2px;

        .multiselect__tag-icon {
          color: $color;

          &::after {
            color: $color;
          }

          &:focus,
          &:hover {
            background-color: #e1e8f9;
          }
        }
      }
    }

    .multiselect__input {
      background-color: $background-color;
      border: $border;
      font-size: $font-size;
      line-height: $line-height;
      margin-bottom: 0;
      padding: 0;
    }

    .multiselect__placeholder {
      color: $placeholder-color;
      line-height: $line-height;
      margin-bottom: 0;
      padding: 0;
    }

    .multiselect__single {
      background-color: $background-color;
      color: $dark-color;
      font-size: $font-size;
      line-height: $line-height;
      margin-bottom: 0;
      padding: 0;
    }

    .multiselect__content-wrapper {
      border: $content-border;
      border-radius: $content-border-radius;
      box-shadow: $content-box-shadow;
      margin: 3px 0;
      padding: 5px 0;

      .multiselect__element {
        color: $color;
        font-size: $font-size - 1;

        .multiselect__option {
          padding: 0px 1em;
          line-height: 40px;
        }

        .multiselect__option:hover {
          background: #e1e8f9;
          color: $color;
        }

        .multiselect__option--selected {
          background: #e1e8f9 !important;
          color: $color;
          font-weight: 500;
        }

        .multiselect__option--highlight {
          background: none;
          color: $color;
        }
      }
    }
  }
}
</style>
