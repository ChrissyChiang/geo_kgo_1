<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-md-12" style>
              <div class="table-responsive">
                <app-table
                  :columns="tableColumns"
                  :data="tableData"
                  :show-pagination="false"
                >
                  <template v-slot:limitDate="{ data: { status, send } }">
                    <base-radio
                      v-for="item in send.optionList"
                      :key="item.value"
                      v-model="send.selected"
                      :item-name="status"
                      :label="item.label"
                      :value="item.value"
                      :select="send.selected"
                    ></base-radio>
                  </template>
                </app-table>
              </div>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button type="button" class="btn btn-fsm" @click="goBack">
                    <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                    返回
                  </button>
                  <button type="button" class="btn btn-primary" @click="save">
                    儲存
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'EditMsg',
  mixins: [page],
  data() {
    return {
      tableData: [],
      optionList: [],
      organId: '',
      caseSetId: ''
    }
  },
  /*props: {
    caseSetId: {
      type: String,
      default: ''
    }
  },*/
  computed: {
    tableColumns() {
      return [
        {
          title: '案件狀態',
          dataIndex: 'caseStatusName',
          tdClass: 'text-center'
        },
        {
          title: '開啟推播',
          dataIndex: 'caseSetName',
          tdClass: 'text-center',
          slot: 'limitDate'
        }
      ]
    }
  },
  methods: {
    async getHomeData() {
      //加入取服務的api
      let data = []
      await this.loadingContainer(async () => {
        const res = await this.$api.management.showPushMsg({
          caseSetId: this.caseSetId
        })
        data = res.data.data.pushMsgManagementHomeDataGridList
        /*const set = new Set();
        data = data.filter(item => !set.has(item.statusMsgSeq) ? set.add(item.statusMsgSeq) : false);
        console.log('data')
        console.log(data)*/
      })

      let tem = []
      let tem2 = {}
      data.forEach(item => {
        let tem2 = {
          caseStatusName: item.caseStatusName,
          statusMsgSeq: item.statusMsgSeq,
          send: {
            optionList: [
              { label: '開啟', value: 'true' },
              { label: '關閉', value: 'false' }
            ],
            selected: item.isEnable
          }
        }
        tem.push(tem2)
      })
      /*this.tableData = [
        { caseStatusName: '民眾入案通知', status: 'W', send: {optionList: [{ label: '開啟', value: 'true' },{ label: '關閉', value: 'false' }] ,selected:'true' } },
        { caseStatusName: '承辦簽收通知', status: 'P', send: {optionList: [{ label: '開啟', value: 'true' },{ label: '關閉', value: 'false' }] ,selected:'false' } },
        { caseStatusName: '民眾補正通知', status: 'C', send: {optionList: [{ label: '開啟', value: 'true' },{ label: '關閉', value: 'false' }] ,selected:'true' } }
      ]*/
      this.tableData = tem
      this.optionList = [
        { label: '開啟', value: 'true' },
        { label: '關閉', value: 'false' }
      ]
    },
    goBack() {
      this.$emit('close-edit-msg')
    },
    async save() {
      let rtnCode = ''
      let tem = this.tableData.map(item => ({
        statusMsgSeq: item.statusMsgSeq,
        isEnable: item.send.selected
      }))
      let request = {
        caseSetId: this.caseSetId,
        pushMsgCaseSetEditDataGridList: tem
      }

      await this.loadingContainer(async () => {
        const res = await this.$api.management.editPushMsgOpenOrNot(request)
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('編輯成功！')
      }
      this.$emit('close-edit-msg')
    }
  }
}
</script>
