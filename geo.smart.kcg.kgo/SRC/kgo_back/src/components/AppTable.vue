<template>
  <div style="width: 100%;">
    <table :class="tableClass">
      <thead>
        <tr v-if="!hasCustomerHeaderSlot">
          <th
            v-if="showSelectField && selected"
            class="text-center"
            style="width: 80px; vertical-align: middle;"
          >
            <slot name="selectName">
              <input
                v-if="selectAll"
                id="checkbox"
                class="form-check-input"
                type="checkbox"
                style="opacity: 1;"
                :checked="isSelectAllChecked()"
                @change="handleSelectAllCheck"
              />
              <label for="checkbox" class="form-check-label mb-0" />
              {{ selectTitle }}
            </slot>
          </th>
          <!-- {{ sort.order }} -->
          <th
            v-for="(column, index) in columns"
            :key="column.dataIndex || index"
            scope="col"
            :style="{
              width: column.width
            }"
            class="text-center"
            :class="[column.thClass ? column.thClass : '']"
            @click="column.sorter && setSort(column.dataIndex)"
          >
            <div
              class="th-inner"
              :class="[
                column.dataIndex === sortIndex && column.sorter
                  ? `sortable ${sort.order}`
                  : '' || column.sorter
                  ? 'both sortable'
                  : ''
              ]"
            >
              <span>{{ column.title }}</span>
              <span v-if="column.required" class="color-red">*</span>
            </div>
          </th>
        </tr>
      </thead>
      <draggable
        ref="draggable"
        :list="data"
        :group="groupName"
        tag="tbody"
        v-bind="dragOptions"
        :disabled="!draggable"
        v-on="dragEvents"
        @start="drag = true"
        @end="drag = false"
      >
        <tr
          v-for="(item, index) in filterData"
          :key="rowKey ? item[rowKey] : index"
        >
          <th
            v-if="showSelectField && selected"
            :key="`selectLeft${item[selectBy]}`"
            style="text-align: center"
          >
            <input
              v-if="
                item[showSelectBy] === undefined || item[showSelectBy] === true
              "
              id="checkbox"
              class="form-check-input"
              type="checkbox"
              :value="item[selectBy]"
              style="vertical-align: middle;opacity: 1;"
              :checked="selected.includes(item[selectBy].toString())"
              @change="handleSelectCheck"
            />
            <label for="checkbox" class="form-check-label mb-0" />
          </th>
          <td
            v-for="column in columns"
            :key="column.dataIndex"
            :data-title="column.title"
            :style="{
              align: column.align,
              padding: column.padding
            }"
            :class="[column.tdClass ? column.tdClass : '']"
          >
            <template v-if="column.slot && column.slot === 'order'">
              {{ (pagination.page - 1) * 10 + index + 1 }}
            </template>
            <template v-else-if="column.slot">
              <slot
                :name="column.slot"
                :data="item"
                :value="item[column.dataIndex]"
                :index="index"
              />
            </template>
            <template v-else>{{ item[column.dataIndex] }}</template>
          </td>
        </tr>
        <tr v-if="filterData.length === 0">
          <td
            class="text-center"
            :colspan="selected ? columns.length + 1 : columns.length"
          >
            無資料
          </td>
        </tr>
      </draggable>
    </table>
    <app-pagination
      v-if="showPagination"
      :value="defaultPage"
      :total="data.length"
      :size="Number(pagination.size)"
      @input-Page="page => (pagination.page = page)"
    />
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import AppPagination from '@/components/AppPagination'
export default {
  name: 'AppTable',
  display: 'Transitions',
  components: {
    draggable,
    AppPagination
  },
  props: {
    /**
     * 欄位資訊
     * @type {string[]} columns
     */
    columns: {
      type: Array,
      required: true
    },
    /**
     * Table 資料
     * @type {Object[]} data
     */
    data: {
      type: Array,
      default: () => []
    },
    /**
     * 欄位 Key
     * @type {String | null}
     */
    rowKey: {
      type: [String, null],
      default: null
    },
    /**
     * 是否可拖曳
     */
    draggable: {
      type: Boolean,
      default: false
    },
    tableClass: {
      type: String,
      default: 'table table-bordered table-hover'
    },
    dragOption: {
      type: Object,
      default: () => ({})
    },
    selected: {
      type: [Array, null],
      default: null
    },
    select: {
      type: [Array, null],
      default: null
    },
    selectBy: {
      type: String,
      default: 'id'
    },
    //控制 checkBox顯示,使用 Data內 Flag參數名稱
    showSelectBy: {
      type: String,
      default: ''
    },
    //是否顯示全選勾選
    selectAll: {
      type: Boolean,
      default: true
    },
    //是否單選
    selectSingle: {
      type: Boolean,
      default: false
    },
    /** 是否顯示checkBox整個欄位 */
    showSelectField: {
      type: Boolean,
      default: true
    },
    tablePagination: {
      type: Object,
      default: () => ({
        page: 1,
        size: 10
      })
    },
    showPagination: {
      type: Boolean,
      default: true
    },
    selectTitle: {
      type: String,
      default: function() {
        return '全選'
      }
    },
    /** 設置拖曳群組名稱，
     *  若要不同組別不互相拖曳，
     *  請設定不同名稱 */
    groupName: {
      type: String,
      default: 'groupName'
    }
  },
  data() {
    return {
      drag: false,
      pagination: this.tablePagination,
      sort: {
        sort: null,
        order: 'asc'
      },
      sortIndex: '',
      defaultPage: this.tablePagination.page
    }
  },
  computed: {
    hasCustomerHeaderSlot() {
      return !!this.$slots.customerHeader
    },
    /** draggable 事件 */
    dragEvents() {
      const dragOptions = this.dragOptions

      return Object.keys(dragOptions)
        .filter(key => typeof dragOptions[key] === 'function')
        .reduce(
          (a, b) =>
            typeof a === 'object'
              ? { ...a, ...{ [b]: dragOptions[b] } }
              : { [a]: dragOptions[a], [b]: dragOptions[b] },
          {}
        )
    },
    /** draggable 選項 */
    dragOptions() {
      const dragOption = {
        change: ({ moved }) => {
          this.$emit('drag-change', {
            data: this.data,
            moved: { ...moved }
          })
        },
        ...this.dragOption
      }
      return {
        animation: 200,
        group: 'description',
        disabled: false,
        ghostClass: 'ghost',
        ...dragOption
      }
    },
    totalPage() {
      return Math.ceil(this.data.length / this.tablePagination.size)
    },
    /** 分頁資料 */
    filterData() {
      if (!this.showPagination) {
        return this.sortData
      } else {
        // 如果資料總長，小於等於此頁數開始數，重置頁數
        if (
          this.data.length <=
          (this.pagination.page - 1) * this.pagination.size
        ) {
          this.resetPage()
        }

        const { page, size } = this.pagination
        const start = (page - 1) * size
        const end = (page - 1) * size + size
        if (!this.sortData) return []
        return this.sortData.slice(start, end)
      }
    },
    /** 排序資料  */
    sortData() {
      const { order, sort } = this.sort
      const dataList = this.formatData
      let list = dataList
      if (sort) {
        list = dataList.sort((a, b) => {
          const sortA = a[sort] || ''
          const sortB = b[sort] || ''
          if (order === 'asc') {
            return sortA.toString().localeCompare(sortB.toString(), 'zh-Hant')
          } else {
            return sortB.toString().localeCompare(sortA.toString(), 'zh-Hant')
          }
        })
      }
      return list
    },
    /** 格式化資料 */
    formatData() {
      const dataList = [...this.data]

      if (this.formatFields.length > 0) {
        return dataList.map(data => {
          const object = {}

          Object.keys(data).forEach(key => {
            const field = this.formatFields.find(
              ({ dataIndex }) => dataIndex === key
            )
            object[key] = field ? field.format(data[key], data) : data[key]
          })
          return object
        })
      } else {
        return dataList
      }
    },
    /** 格式化欄位列表 */
    formatFields() {
      return this.columns.filter(({ format }) => format)
    }
  },
  watch: {
    'pagination.size': {
      handler() {
        this.pagination.page = 1
      }
    },
    'pagination.page': {
      handler() {
        this.$emit('input', this.pagination.page)
      }
    }
  },
  methods: {
    handleSelectAllCheck({ target: { checked } }) {
      const { page, size } = this.pagination
      const start = page === 1 ? 0 : (page - 1) * size
      const end = page === 1 ? page * size : page * size - 1
      const array = [...this.selected]
      let data = [...this.data]
      data = data.splice(start, end)
      if (this.showSelectBy) {
        data = data.filter(item => item[this.showSelectBy] === true)
      }
      data = data.map(item => item[this.selectBy].toString())

      if (checked) {
        data.forEach(id => {
          if (!array.includes(id)) {
            array.push(id)
          }
        })
        this.$emit('update:selected', array)
      } else {
        data.forEach(id => {
          const index = array.indexOf(id)

          if (index !== -1) {
            array.splice(index, 1)
          }
        })
        this.$emit('update:selected', array)
      }
    },
    /** 全選 chenckbox 是否勾選 */
    isSelectAllChecked() {
      if (this.data.length === 0) {
        return false
      }
      const { page, size } = this.pagination
      const start = page === 1 ? 0 : (page - 1) * size
      const end = page === 1 ? page * size : page * size - 1
      const array = [...this.selected]
      let data = [...this.data]
      data = data.slice(start, end)
      if (this.showSelectBy) {
        data = data.filter(item => item[this.showSelectBy] === true)
      }
      data = data.map(item => item[this.selectBy].toString())
      if (data.length != 0) {
        return data.every(item => array.includes(item))
      } else {
        return false
      }
    },
    /** 選擇鈕點擊 */
    handleSelectCheck({ target: { checked, value } }) {
      if (this.selectSingle) {
        const array = []
        array.push(value)
        this.$emit('update:selected', array)
      } else {
        const array = [...this.selected]

        if (checked && !array.includes(value)) {
          // 如果勾選則寫入陣列
          array.push(value)
          this.$emit('update:selected', array)
        } else if (!checked && array.includes(value)) {
          // 如果未勾選則移除陣列
          array.splice(array.indexOf(value), 1)
          this.$emit('update:selected', array)
        }
      }
    },
    /** 重設頁數 */
    resetPage() {
      this.pagination.page = 1
      this.defaultPage = 1
      if (this.$refs.pagination) {
        this.$refs.pagination.setCurrentPage(this.defaultPage)
      }
    },
    setSort(field) {
      this.sortIndex = field
      const oldSortField = this.sort.sort
      this.sort.sort = field
      // 判斷換欄位重置排序
      if (oldSortField != null && oldSortField != field) {
        this.sort.order = 'asc'
      } else {
        this.sort.order = this.sort.order === 'desc' ? 'asc' : 'desc'
      }

      this.$emit('change')
    }
  }
}
</script>
