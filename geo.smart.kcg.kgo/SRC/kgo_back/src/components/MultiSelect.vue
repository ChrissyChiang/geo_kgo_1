<template>
  <vue-multiselect
    v-model="selectedData"
    v-bind="$attrs"
    :label="optionLabel"
    :track-by="optionValue"
    placeholder="請輸入關鍵字"
    :select-label="''"
    :selected-label="'已選取'"
    :deselect-label="'點擊移除'"
    open-direction="bottom"
    :loading="isLoading"
    :options="options"
    :multiple="selectMultiple"
    :searchable="true"
    :options-limit="optionsLimit"
    :limit="showSelectedLimit"
    :limit-text="limitText"
    :max-height="maxHeight"
    :show-no-results="true"
    v-on="$listeners"
    @search-change="searchChange"
  >
    <!--<template slot="tag" slot-scope="{ option, remove }">
      <span class="custom__tag">
        <span>{{ option[optionLabel] }}</span>
        <span class="custom__remove" @click="remove(option)"></span>
      </span>
    </template>-->
    <!-- <template slot="clear" slot-scope="props">
      <div
        v-if="false"
        class="multiselect__clear"
        @mousedown.prevent.stop="clearAll(props.search)"
      ></div>
    </template>-->
    <span slot="noResult">
      {{ $t('GENERAL.NO_DATA') }}
    </span>
    <span slot="noOptions">
      列表為空，請輸入關鍵字來查詢!
    </span>
  </vue-multiselect>
</template>
<script>
export default {
  name: 'MultiSelect',
  props: {
    options: {
      type: Array,
      default: () => []
    },
    /** 選項最大上限 */
    optionsLimit: {
      type: Number,
      default: 300
    },
    /** 顯示已選選項最大上限 */
    showSelectedLimit: {
      type: Number,
      default: 3
    },
    maxHeight: {
      type: Number,
      default: 600
    },
    /** 選項顯示文字的物件key */
    optionLabel: {
      type: String,
      default: 'label'
    },
    /** 選項值的物件key */
    optionValue: {
      type: String,
      default: 'value'
    },
    /** 是否要顯示loading效果 */
    isLoading: {
      type: Boolean,
      default: false
    },
    /** 已選的值 */
    value: {
      type: [Array, Object],
      default: () => []
    },
    /** 是否為多選 */
    selectMultiple: {
      type: Boolean,
      default: true
    },
    closedata: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      selectedData: this.value
    }
  },
  watch: {
    /** 移除 請選擇 項目 */
    selectedData(newValue) {
      if (Array.isArray(newValue)) {
        this.$emit('input', newValue)
      }
    },
    value(newValue) {
      this.selectedData = newValue
    }
  },
  methods: {
    clearAll() {
      if (this.multiple) {
        this.selectedData = []
      } else {
        this.selectedData = null
      }
    },
    limitText(count) {
      return `其他 ${count} 個已選項目`
    },
    searchChange(query) {
      this.$emit('search-change', query)
    }
  }
}
</script>
<style lang="scss">
input {
  min-width: initial !important;
}
.multiselect__tags {
  border: 1px solid #ccc !important;
}
</style>
