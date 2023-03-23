<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <app-table
            :columns="tableColumns"
            :data="tableData"
            :draggable="false"
            :show-pagination="false"
          >
            <template v-slot:operating="{ data: {} }">
              <button
                type="button"
                class="btn-line btn-warning"
                @click="checkType()"
              >
                <i class="fa fa-cog" aria-hidden="true"></i>
                <a>申請</a>
              </button>
            </template>
          </app-table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      keyWord: '',
      showCaseAdd: '',
      tableData: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '類型',
          dataIndex: 'typeName',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    }
  },
  mounted() {
    this.tableData = [
      {
        caseType: 'URA',
        typeName: '系統權限申請'
      }
    ]
  },
  methods: {
    checkType() {
      this.$router.push('/serviceApplication/addAuthority/')
    }
  }
}
</script>
