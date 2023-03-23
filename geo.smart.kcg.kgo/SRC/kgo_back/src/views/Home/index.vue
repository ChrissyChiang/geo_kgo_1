<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                案件編號
              </label>
              <div class="col-sm-8">
                <input
                  id="inputPassword2"
                  type="text"
                  class="form-control"
                  placeholder="請輸入案件編號"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                申請時間起迄
              </label>
              <div class="col-sm-8">
                <base-date v-model="pfDate" :is-range="true" :value="pfDate" />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                申請人
              </label>
              <div class="col-sm-8">
                <input
                  id="inputPassword2"
                  type="text"
                  class="form-control"
                  placeholder="請輸入申請人"
                />
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                服務案件名稱
              </label>
              <div class="col-sm-8">
                <input
                  id="inputPassword2"
                  type="text"
                  class="form-control"
                  placeholder="請輸入服務案件名稱"
                />
              </div>
            </div>

            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button type="button" class="btn btn-fsm">
                      <i class="fa fa-search" aria-hidden="true"></i>
                      送出查詢
                    </button>
                    <button type="button" class="btn btn-fsm">
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      取消簽收
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="col-md-12" style>
              <div class="table-responsive">
                <h4 class="alert alert-success">
                  <label style="color='#FFFFFF'">案件列表</label>
                </h4>
                <app-table
                  :selected.sync="checkList"
                  :columns="tableColumns"
                  :data="tableData"
                  :draggable="false"
                  :select-title="'核選'"
                  :show-pagination="true"
                  :select-by="'caseId'"
                >
                  <template v-slot:operating="{ data }">
                    <button
                      type="button"
                      class="btn-line btn-warning"
                      @click="test"
                    >
                      <i class="fa fa-edit" aria-hidden="true"></i>
                      簽核{{ data.id }}
                    </button>
                  </template>
                </app-table>
                <table class="table">
                  <thead>
                    <tr>
                      <th class="text-center">□核選</th>
                      <th class="text-center">案號編號</th>
                      <th class="text-center">申請時間</th>
                      <th class="text-center">申請人</th>
                      <th class="text-center">案件名稱</th>
                      <th class="text-center">限辦日期</th>
                      <th class="text-center">案件狀態</th>
                      <th class="text-center">案件簽核</th>
                      <th class="text-center"></th>
                      <th class="text-center"></th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr>
                      <th class="text-center">□</th>
                      <td class="text-center">2020009010001</td>
                      <td class="text-center">109/09/01</td>
                      <td class="text-center">林OO</td>
                      <td class="text-center">戶籍謄本申請</td>
                      <td class="text-center">109/10/01</td>
                      <td class="text-center">待審核</td>
                      <td class="text-center">
                        <button type="button" class="btn-line btn-warning">
                          <i class="fa fa-edit" aria-hidden="true"></i>
                          <a href="CaseSigned.htm">簽核</a>
                        </button>
                      </td>
                    </tr>
                    <tr>
                      <th class="text-center">□</th>
                      <td class="text-center">2020009010002</td>
                      <td class="text-center">109/09/01</td>
                      <td class="text-center">王OO</td>
                      <td class="text-center">戶籍謄本申請</td>
                      <td class="text-center">109/10/01</td>
                      <td class="text-center">待審核</td>
                      <td class="text-center">
                        <button type="button" class="btn-line btn-warning">
                          <i class="fa fa-edit" aria-hidden="true"></i>
                          <a href="CaseSigned.htm">簽核</a>
                        </button>
                      </td>
                    </tr>
                    <tr>
                      <th class="text-center">□</th>
                      <td class="text-center">2020009010003</td>
                      <td class="text-center">109/09/01</td>
                      <td class="text-center">陳OO</td>
                      <td class="text-center">戶籍謄本申請</td>
                      <td class="text-center">109/10/01</td>
                      <td class="text-center">待補正</td>
                      <td class="text-center">
                        <button type="button" class="btn-line btn-warning">
                          <i class="fa fa-edit" aria-hidden="true"></i>
                          <a href="CaseSigned.htm">簽核</a>
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <app-modal ref="add" :modal-title="'test'" @after-hidden="clearData">
        <template v-slot:button>
          <button type="button" class="btn btn-secondary" @click="close">
            取消
          </button>
          <button type="button" class="btn btn-primary" @click="close">
            儲存
          </button>
        </template>
      </app-modal>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  components: {},
  mixins: [page],
  data() {
    return {
      pfDate: [],
      tableData: [],
      checkList: [],
      selectData: '',
      options: []
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '案號編號',
          dataIndex: 'caseId',
          tdClass: 'text-center'
        },
        {
          title: '申請時間',
          dataIndex: 'time',
          tdClass: 'text-center'
        },
        {
          title: '案件簽核',
          dataIndex: 'id',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    }
  },
  async mounted() {
    await this.loadingContainer(async () => {
      await this.delay(1000)
      this.options = [
        {
          label: 'x',
          value: 'x'
        }
      ]
      this.tableData = [
        {
          id: 1,
          caseId: 1,
          time: '2020/10/10'
        },
        {
          id: 2,
          caseId: 2,
          time: '2020/10/10'
        },
        {
          id: 3,
          caseId: 3,
          time: '2020/10/10'
        },
        {
          id: 4,
          caseId: 4,
          time: '2020/10/10'
        },
        {
          id: 5,
          caseId: 5,
          time: '2020/10/10'
        },
        {
          id: 6,
          caseId: 6,
          time: '2020/10/10'
        },
        {
          id: 7,
          caseId: 7,
          time: '2020/10/10'
        },
        {
          id: 8,
          caseId: 8,
          time: '2020/10/10'
        },
        {
          id: 9,
          caseId: 9,
          time: '2020/10/10'
        },
        {
          id: 10,
          caseId: 10,
          time: '2020/10/10'
        },
        {
          id: 11,
          caseId: 11,
          time: '2020/10/10'
        }
      ]
    })
  },
  methods: {
    test() {
      //this.notifySuccess('x')
      this.$refs.add.show()
      //this.toastSuccess('toastSuccess')
    },
    close() {
      this.$refs.add.hide()
    },
    clearData() {
      console.log('close')
    }
  }
}
</script>
<style lang="scss" scoped></style>
