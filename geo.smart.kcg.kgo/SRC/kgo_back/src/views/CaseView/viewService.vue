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
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ caseId }}</label>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  機關
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <label>{{ organName }}</label>
                </div>
              </div>

              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  科室
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <label>{{ unitName }}</label>
                </div>
              </div>
            </div>
          </div>
          <div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  申請人
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <label>{{ applicant }}</label>
                </div>
              </div>
              <div class="col-xs-6 form-group col-md-6">
                <label for="input_event" class="col-sm-4 control-label">
                  聯絡電話
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <label>{{ phone }}</label>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                電子郵件
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ email }}</label>
              </div>
            </div>
            <div class="col-xs-6 form-group col-md-6">
              <label for="input_event" class="col-sm-4 control-label">
                審核主管
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ supervisor }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                案件名稱
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ caseName }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                整合流程分類
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ processClassification }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                整合機關
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ integrationAgency }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                站外連結方式
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ linkType }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                站外連結網址
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ linkUrl }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                機關別分類
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ organClassify }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                身份別分類
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ roleClassify }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                服務別分類
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ serviceCalssify }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                權責機關
              </label>
              <div class="col-sm-8" style="padding-top: 7px">
                <label>{{ powerOrgan }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                案件管理者
              </label>
              <div class="col-sm-6" style="padding-top: 7px">
                <label>{{ managerName }}</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-6 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                服務啟用
              </label>
              <div class="col-xs-2 form-group col-md-2">
                <input-checkbox
                  v-model="serviceEnableList[0].selected"
                  :disabled="true"
                  :label="serviceEnableList[0].label"
                ></input-checkbox>
              </div>
              <div class="col-xs-2 form-group col-md-2">
                <input-checkbox
                  v-model="serviceEnableList[1].selected"
                  :disabled="true"
                  :label="serviceEnableList[1].label"
                ></input-checkbox>
              </div>
              <div class="col-xs-2 form-group col-md-2">
                <input-checkbox
                  v-model="serviceEnableList[2].selected"
                  :disabled="true"
                  :label="serviceEnableList[2].label"
                ></input-checkbox>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="panel panel-default defaultcollapse">
      <div
        id="headingOne"
        class="panel-heading"
        role="tab"
        :href="'#collapse3'"
        data-toggle="collapse"
        aria-expanded="true"
        :aria-controls="'collapse3'"
      >
        <h4 class="panel-title" style="font-size: 1em; font-weight: 500">
          <a role="button">處理歷程</a>
        </h4>
      </div>
      <div
        :id="'collapse3'"
        class="panel-collapse collapse"
        role="tabpanel"
        aria-labelledby="headingOne"
      >
        <div class="panel-body">
          <app-table
            :table-class="'table'"
            :columns="historyTableColumns"
            :data="historyTableData"
            :show-pagination="false"
          ></app-table>
        </div>
      </div>
    </div>
    <div class="fsm-form">
      <table class="table table-unstyled">
        <tbody>
          <tr colspan="4" align="center">
            <td>
              <button type="button" class="btn btn-fsm" @click="goBack()">
                <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                返回
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      id: '',
      historyTableData: [],
      caseId: '',
      organName: '',
      organId: '',
      unitName: '',
      unitId: '',
      applicant: '',
      phone: '',
      email: '',
      supervisor: '',
      caseName: '',
      processClassification: '',
      integrationAgency: '',
      linkType: '',
      linkUrl: '',
      organClassify: '',
      roleClassify: '',
      serviceCalssify: '',
      powerOrgan: '',
      managerName: '',
      managerId: '',
      serviceEnableList: [
        { label: '', selected: false },
        { label: '', selected: false },
        { label: '', selected: false },
        { label: '', selected: false },
        { label: '', selected: false }
      ]
    }
  },
  computed: {
    historyTableColumns() {
      return [
        {
          title: '案件狀態',
          dataIndex: 'status',
          tdClass: 'text-center'
        },
        {
          title: '處理機關',
          dataIndex: 'organ',
          tdClass: 'text-center'
        },
        {
          title: '內容',
          dataIndex: 'content',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'taker',
          tdClass: 'text-center'
        },
        {
          title: '處理時間',
          dataIndex: 'dealTime',
          tdClass: 'text-center'
        }
      ]
    }
  },

  mounted() {
    this.id = this.$route.params.id
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      await this.loadingContainer(async () => {
        const res = await this.$api.cases.getCaseViewScaHomeData({
          caseId: this.id
        }) //初始畫面帶入案件狀態
        this.caseId = res.data.data.caseId
        this.organName = res.data.data.organName
        this.organId = res.data.data.caseId
        this.unitName = res.data.data.unitName
        //this.unitId = res.data.data.caseId
        this.applicant = res.data.data.applicant
        this.phone = res.data.data.phone
        this.email = res.data.data.email
        this.supervisor = res.data.data.supervisor
        this.caseName = res.data.data.caseName
        this.processClassification = res.data.data.processClassification
        this.integrationAgency = res.data.data.ownerOrgan
        this.linkType = res.data.data.linkType
        this.linkUrl = res.data.data.linkUrl
        this.organClassify = res.data.data.organClassify
        this.roleClassify = res.data.data.roleClassify
        this.serviceCalssify = res.data.data.serviceCalssify
        this.powerOrgan = res.data.data.ownerOrgan
        this.managerName = res.data.data.managerName
        this.historyData = res.data.data.historyData
        this.serviceEnableList = res.data.data.checkBoxList
      })
      this.caseId = 'caseid00001'
      this.organName = '機關1'
      this.organId = 'o00001'
      this.unitName = '科室1'
      this.unitId = 'u00001'
      this.applicant = '王Ｏ名'
      this.phone = '0976747372'
      this.email = 'test@gmail.com'
      this.supervisor = '主管1'
      this.caseName = '服務案件1'
      this.processClassification = 'A流程'
      this.integrationAgency = '民政局'
      this.linkType = '本頁開啟'
      this.linkUrl = 'http:google.com'
      this.organClassify = '民政局'
      this.roleClassify = '兒童'
      this.serviceCalssify = '綜合'
      this.powerOrgan = '東區區公所'
      this.managerName = '王Ｏ東'
      this.serviceEnableList = [
        { label: '網路申辦', value: 'ADMIN', groupKey: '', selected: true },
        { label: '臨櫃申辦', value: 'CASE_M', groupKey: '', selected: false },
        { label: '書表下載', value: 'UNIT_M', groupKey: '', selected: true }
      ]
      this.historyTableData = [
        {
          status: '分文',
          organ: '文化局',
          content: '由林文欽分文給王文豪',
          taker: '王Ｏ賢',
          dealTime: '2019/09/01 15:00:00'
        }
      ]
    },
    goBack() {
      this.$router.push(`/caseView`)
    }
  }
}
</script>
