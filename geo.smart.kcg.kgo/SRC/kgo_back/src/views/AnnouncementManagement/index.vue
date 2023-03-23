<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                標題名稱
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="titleName"
                  :placeholder="'輸入標題名稱'"
                  input-length="50"
                ></base-input>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                上架日期
              </label>
              <div class="col-sm-8">
                <base-date
                  v-model="pfDate"
                  placeholder="請選擇上下架日期區間"
                  :is-range="true"
                  :value="pfDate"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                發布對象
              </label>
              <div class="col-sm-8">
                <base-select
                  v-model="releaseObject"
                  :options="releaseObjectOptions.options"
                  :select="releaseObject"
                />
              </div>
            </div>
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="button" class="btn btn-fsm" @click="search()">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="addTask('null', 'null')"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      任務新增
                    </button>
                    <button
                      v-if="showAdd"
                      type="button"
                      class="btn btn-fsm"
                      @click="addAnnouncement('null', 'null')"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      公告新增
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="false"
                  :show-pagination="true"
                >
                  <template
                    v-slot:operating="{
                      data: { seq, state, releaseObject, functionCode }
                    }"
                  >
                    <button
                      v-if="state == '上架'"
                      type="button"
                      class="btn-line btn-warning"
                      @click="
                        showConfirm('off', seq, releaseObject, functionCode)
                      "
                    >
                      <i class="fa fsm-icon-arrow-down" aria-hidden="true"></i>
                      <a>下架</a>
                    </button>
                    <button
                      v-if="state == '下架'"
                      type="button"
                      class="btn-line btn-warning"
                      @click="
                        showConfirm('on', seq, releaseObject, functionCode)
                      "
                    >
                      <i class="fa fsm-icon-arrow-top" aria-hidden="true"></i>
                      <a>上架</a>
                    </button>
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="checkType(seq, releaseObject, functionCode)"
                    >
                      <i class="fa fa-cog" aria-hidden="true"></i>
                      編輯
                    </button>
                    <button
                      type="button"
                      class="btn-line btn-danger"
                      @click="showDelConfirm(seq, releaseObject, functionCode)"
                    >
                      <i class="fa fa-trash-o" aria-hidden="true"></i>
                      刪除
                    </button>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <modal-confirm
        ref="confirmChangeState"
        :close-text="'取消'"
        :modal-content="modalcontent"
        :modal-warn="modalwarn"
        @confirm="changeState()"
      />
      <modal-confirm
        ref="modalConfirm"
        :close-text="'取消'"
        :modal-content="'是否確定刪除資料？'"
        :modal-warn="'確認刪除，系統將執行刪除'"
        @confirm="delActivity()"
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
      pfDate: [],
      tableData: [],
      roleList: [],
      showAdd: true,
      titleName: '',
      releaseObjectOptions: [],
      releaseObject: '',
      modalcontent: '',
      modalwarn: '',
      changeStateSeq: '',
      changeStateType: '',
      changeType: '',
      changeFunctionCode: '',
      delTypeSeq: '',
      delType: '',
      delFunctionCode: ''
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
          title: '標題名稱',
          dataIndex: 'titleName',
          tdClass: 'text-center'
        },
        {
          title: '狀態',
          dataIndex: 'state',
          tdClass: 'text-center'
        },
        {
          title: '上下架日期',
          dataIndex: 'publishTime',
          tdClass: 'text-center'
        },
        {
          title: '發布對象',
          dataIndex: 'releaseObjectName',
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
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getAnnouncementManagementHomeData()
        this.tableData = res.data.data.grid
        this.releaseObjectOptions = res.data.data.releaseObejctComboBox
        this.roleList = res.data.data.roles
      })
      this.showAdd =
        this.roleList.length == 1 && this.roleList[0] == 'UNIT_M' ? false : true
    },
    async search() {
      let request = {
        titleName: this.titleName,
        publishTime: this.pfDate,
        releaseObject: this.releaseObject
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.queryAnnouncement(request)
        this.tableData = res.data.data.grid
      })
    },
    addTask(id, release) {
      //新增任務
      //this.$router.push('/announcementManagement/addTask/' + id)
      this.$router.push({
        name: 'addTask',
        params: { id: id, release: release }
      })
    },
    addAnnouncement(id, release) {
      //新增公告
      //this.$router.push('/announcementManagement/addAnnouncement/' + id)
      this.$router.push({
        name: 'addAnnouncement',
        params: { id: id, release: release }
      })
    },
    checkType(seq, releaseObject, functionCode) {
      if (functionCode == 'TaskM') {
        this.addTask(seq, releaseObject)
      } else {
        this.addAnnouncement(seq, releaseObject)
      }
    },
    async changeState() {
      let request = {
        seq: this.changeStateSeq,
        isPublish: this.changeStateType,
        releaseObject: this.changeType,
        functionCode: this.changeFunctionCode
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.showAnnouncement(request)
        this.$refs.confirmChangeState.hide()
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    },
    async delActivity() {
      let request = {
        seq: this.delTypeSeq,
        releaseObject: this.delType,
        functionCode: this.delFunctionCode
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.deleteAnnouncement(request)
        this.$refs.modalConfirm.hide()
        this.notifySuccess(res.data.data.msg)
      })
      this.getHomeData()
    },
    showConfirm(param, seq, type, functionCode) {
      if (param == 'off') {
        this.modalcontent =
          '是否確定下架' + (type == 'F' ? '任務' : '公告') + '？'
        this.modalwarn = '確認後，將下架此' + (type == 'F' ? '任務' : '公告')
        this.changeStateType = false
      } else if (param == 'on') {
        this.modalcontent =
          '是否確定上架' + (type == 'F' ? '任務' : '公告') + '？'
        this.modalwarn = '確認後，將上架此' + (type == 'F' ? '任務' : '公告')
        this.changeStateType = true
      }
      this.changeFunctionCode = functionCode
      this.changeType = type
      this.$refs.confirmChangeState.show()
      this.changeStateSeq = seq
    },
    showDelConfirm(seq, releaseObject, functionCode) {
      this.$refs.modalConfirm.show()
      this.delTypeSeq = seq
      this.delType = releaseObject
      this.delFunctionCode = functionCode
    }
  }
}
</script>
