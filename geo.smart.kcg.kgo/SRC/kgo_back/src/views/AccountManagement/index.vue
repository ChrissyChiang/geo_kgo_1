<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                機關名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="organName"
                  :options="organNameOptions.options"
                  search
                  :select="organName"
                  :disabled="organDisabled"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                科室名稱
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="unitName"
                  :options="unitNameOptions.options"
                  search
                  :select="unitName"
                />
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  姓名
                </label>
                <div class="col-sm-8">
                  <base-input
                    v-model="name"
                    :placeholder="'請輸入姓名'"
                    :input-length="'50'"
                  ></base-input>
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  帳號
                </label>
                <div class="col-sm-8">
                  <base-input
                    v-model="account"
                    :placeholder="'請輸入帳號'"
                    :input-length="'50'"
                  ></base-input>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  角色
                </label>
                <div class="col-sm-8">
                  <base-select
                    v-model="roleName"
                    :options="roleOptions.options"
                    search
                    :select="roleName"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="button" class="btn btn-fsm" @click="search">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <app-table
            :columns="tableColumns"
            :data="tableData"
            :draggable="false"
            :show-pagination="true"
          >
            <template v-slot:operating="{ data: { userId } }">
              <button
                type="button"
                class="btn-line btn-warning"
                @click="addAccount(userId)"
              >
                <i class="fa fa-cog" aria-hidden="true"></i>
                編輯
              </button>
              <button
                type="button"
                class="btn-line btn-danger"
                @click="showDelConfirm(userId)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
        </div>
      </div>
      <modal-confirm
        ref="confirmDelete"
        :close-text="'取消'"
        :modal-content="'是否確定刪除資料？'"
        :modal-warn="'確認刪除，系統將執行刪除'"
        @confirm="delAccount()"
      />
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      tableData: [],
      organName: '',
      unitName: '',
      roleName: '',
      delAccountSeq: '',
      account: '',
      name: '',
      organNameOptions: {},
      unitNameOptions: {},
      roleOptions: {},
      organDisabled: true
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '項目',
          dataIndex: 'index',
          tdClass: 'text-center',
          slot: 'order'
        },
        {
          title: '機關名稱',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '科室名稱',
          dataIndex: 'unitName',
          tdClass: 'text-center'
        },
        {
          title: '帳號',
          dataIndex: 'userId',
          slot: status,
          tdClass: 'text-center'
        },
        {
          title: '姓名',
          dataIndex: 'name',
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
  watch: {
    organName() {
      this.getUnit()
    }
  },
  beforeRouteLeave(to, from, next) {
    // 不是導到快取頁面的話，清除快取
    if (from.meta.catheTo && !from.meta.catheTo.includes(to.name)) {
      from.meta.keepAlive = false
    } else {
      from.meta.keepAlive = true
    }
    next()
  },
  async mounted() {
    await this.loadingContainer(async () => {
      this.getHomeData()
    })
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getAccountManagementHomeData()
        this.organNameOptions = res.data.data.organComboBox
        this.unitNameOptions = res.data.data.unitComboBox
        this.roleOptions = res.data.data.roleComboBox
        this.roleOptions.options = this.roleOptions.options.filter(
          item => item.value != 'DEFAULT'
        )
        this.tableData = res.data.data.grid
        this.organName = res.data.data.organComboBox.selectedVal
      })
      this.organDisabled =
        this.userInfo.roles.indexOf('ADMIN') >= 0 ? false : true
    },
    showDelConfirm(seq) {
      this.$refs.confirmDelete.show()
      this.delAccountSeq = seq
    },
    async delAccount() {
      //加入刪除api
      this.$refs.confirmDelete.hide()
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteAccount({
          userId: this.delAccountSeq
        })
        this.notifySuccess(res.data.data.msg)
        this.getHomeData()
      })
    },
    async search() {
      await this.loadingContainer(async () => {
        //加入搜尋api
        let request = {
          organId: this.organName,
          unitId: this.unitName,
          roleId: this.roleName,
          name: this.name,
          userId: this.account
        }
        const res = await this.$api.management.queryAccount(request)
        this.tableData = res.data.data.grid
      })
    },
    async getUnit() {
      const res = await this.$api.share.getUnit({ organId: this.organName })
      // res.data.data.unitComboBox.options.push({
      //   groupKey: '',
      //   label: '請選擇',
      //   selected: false,
      //   value: ''
      // })
      this.unitNameOptions = res.data.data.unitComboBox
      this.unitName = ''
    },
    addAccount(id) {
      this.$router.push('/accountManagement/addAccount/' + id)
    }
  }
}
</script>
