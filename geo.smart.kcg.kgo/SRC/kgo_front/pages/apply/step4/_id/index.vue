<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ caseName }}</h2>
        <a
          class="accesskey"
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          accesskey="C"
          name="C"
        >:::</a>
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="/" title="回首頁">回首頁</a>
          </li>
          <li class="breadcrumb-item">
            <button
              class="btn-gray-outline"
              @click="goCaseList()"
              :title="rootTitle || '標題名稱'"
            >{{ rootTitle || '標題名稱' }}</button>
          </li>
          <li class="breadcrumb-item">
            <button
              class="btn-gray-outline"
              @click="goBack()"
              :title="caseName || '案件名稱'"
            >{{ caseName || '案件名稱' }}</button>
          </li>
          <li class="breadcrumb-item active" aria-current="page">網路申請</li>
        </ol>
      </nav>
      <!-- breadcrumb end -->

      <div class="apply-inner">
        <div class="step">
          <ul>
            <li class="done">
              網路申請
              <br />同意書
            </li>
            <li class="done">
              身分
              <br />驗證
            </li>
            <li class="done">
              填寫
              <br />申請表
            </li>
            <li class="done">
              確認
              <br />申請內容
            </li>
            <li class>
              步驟
              <br />完成
            </li>
          </ul>
        </div>
        <!--<form>-->
        <div class="apply-item">
          <h3>確認申請內容</h3>
          <div id="accordion2">
            <fieldset v-for="(item, index) in dataList" :key="index">
              <legend
                style="
                font-weight: bold;
                padding: 0 15px 5px 15px;
                margin-bottom: 0;
                color: #333;
                font-size: 1.2em;
              "
              >{{ item.memo }}</legend>
              <div
                class="form-group row"
                v-for="(dataItem, dataIndex) in item['columnData']"
                :key="'columnData' + dataIndex"
              >
                <label
                  class="col-sm-3 col-form-label"
                  :for="dataItem.columnId"
                >{{ dataItem.columnName }}</label>
                <!-- 動態表單開始  -->
                <!-- 非覆核欄位 -->
                <div class="col-sm-9" v-if="dataItem.columnType != 'M'">
                  <component
                    :is="dataItem.columnType"
                    :disabled="mydatatxid == null ? false : dataItem.isReadonly"
                    :value="
                    dataItem.isReadonly ? dataItem.value : dataItem.columnValue
                  "
                    :id="dataItem.columnId"
                    :datavalue.sync="dataItem.value"
                    :showvalue.sync="dataItem.columnValue"
                    :ref="'cardList' + index + dataIndex + 0"
                    :required="dataItem.isMustKey == '0' ? false : true"
                    :inputLength="dataItem.length"
                  ></component>
                </div>
                <div v-else class="col-sm-9">
                  <div
                    class="form-row"
                    v-for="(detailItem, detailIndex) in dataItem['complex']"
                    :key="'detail' + detailIndex"
                  >
                    <div
                      v-for="(detailOrderItem, detailOrderIndex) in detailItem"
                      :key="'detailOrder' + detailOrderIndex"
                    >
                      <div style="padding-bottom: 10px">
                        <component
                          :is="detailOrderItem.columnType"
                          :ref="'cardList' + index + dataIndex + detailIndex"
                          :disabled="
                          mydatatxid == null
                            ? false
                            : detailOrderItem.isReadonly
                        "
                          :id="detailOrderItem.cColumnId"
                          :value="
                          detailOrderItem.isReadonly
                            ? detailOrderItem.value
                            : detailOrderItem.columnValue
                        "
                          :f-text="detailOrderItem.fText"
                          :b-text="detailOrderItem.bText"
                          :required="
                          detailOrderItem.isMustKey == '0' ? false : true
                        "
                          :datavalue.sync="detailOrderItem.value"
                          :showvalue.sync="detailOrderItem.columnValue"
                          :group="detailOrderItem.vGroup"
                          :parent="detailOrderItem.pColumnId"
                          :inputLength="detailOrderItem.length"
                        ></component>
                      </div>
                    </div>
                    <div></div>
                  </div>
                </div>
              </div>
            </fieldset>
          </div>
          <hr />
          <validation-observer ref="observer">
            <fieldset v-if="isShowCaseOrganComboBox">
              <legend class="text-hide">受理機關</legend>
              <div class="form-group row">
                <label class="col-sm-3 col-form-label" for="organlist">受理機關</label>
                <div class="col-sm-9">
                  <div class="form-row">
                    <Drop
                      :id="'organlist'"
                      :value="caseOrganComboBoxList"
                      :datavalue.sync="caseOrgan"
                    />
                  </div>
                </div>
              </div>
            </fieldset>
            <hr v-if="isShowCaseOrganComboBox" />
            <fieldset>
              <legend class="text-hide">驗證碼</legend>
              <div class="form-group row">
                <label class="col-sm-3 col-form-label" for="id_captch">驗證碼</label>
                <div class="col-sm-9">
                  <div class="form-row">
                    <div class="col">
                      <validation-provider
                        :rules="'required|num|captch:' + identifyCode"
                        v-slot="{ errors }"
                        mode="passive"
                      >
                        <input
                          type="text"
                          class="form-control"
                          id="id_captch"
                          v-model="inputidentifyCode"
                        />
                        <span class="error_label w-100" style="color: red">{{ errors[0] }}</span>
                      </validation-provider>
                    </div>
                    <captcha :code.sync="identifyCode" :token.sync="captchaToken" ref="captcha" />
                  </div>
                </div>
              </div>
            </fieldset>
          </validation-observer>
        </div>
        <div class="form-group text-center btn-even">
          <button class="btn btn-lg btn-gray-light" type="button" @click="goBack" title="上一步按鈕">上一步</button>
          <button class="btn btn-lg btn-org" @click="next" type="button" title="下一步按鈕">下一步</button>
        </div>
        <!--</form>-->
      </div>
    </div>

    <!--modal start-->
    <system-confirm :msg="msg" ref="modal" />
    <!--modal end-->
  </div>
</template>
<script>
import orgcheck from './components/textBox'
import TextBox from './components/textBox'
import TextId from './components/textId.vue'
import TextArea from './components/textArea.vue'
import TextEmail from './components/textEmail'
import TextTel from './components/textTel'
import TextTaxId from './components/textTaxId'
import TextPhone from './components/textPhone'
import Num from './components/textNum'
import Drp from './components/dropDown.vue'
import Drop from '../../../apply_form/components/dropDown.vue'
import Address from './components/address.vue'
import Radio from './components/baseRadio.vue'
import SRadio from './components/baseSingleRadio'
import Checkbox from './components/checkBox.vue'
import Date from './components/date.vue'
import Time from './components/dateTime.vue'
import DateSE from './components/dateRange.vue'
import TimeSE from './components/dateRangeTime'
import LandNum from './components/landNum.vue'
import TextLabel from './components/textLabel'
import page from '~/mixins/page.js'
import SCheckbox from './components/checkSingleBox'
import Fil from './components/file.vue'
import Pic from './components/fileImage'
import AddressTextBox from './components/addressTextBox'
export default {
  components: {
    orgcheck,
    Drop,
    TextBox,
    TextId,
    TextArea,
    TextEmail,
    TextTel,
    TextTaxId,
    TextPhone,
    Num,
    Drp,
    Address,
    Radio,
    SRadio,
    Checkbox,
    Date,
    DateSE,
    TimeSE,
    Time,
    LandNum,
    TextLabel,
    SCheckbox,
    Fil,
    Pic,
    AddressTextBox
  },
  mixins: [page],
  head() {
    return {
      title: '確認申請內容'
    }
  },
  data() {
    return {
      caseOrganComboBox: [],
      identifyCode: '',
      captchaToken: '',
      mydatatxid: '',
      dataList: [],
      caseFlowType: '', //案件流程
      landNumOption: [],
      caseName: '',
      caseOrganComboBoxList: '',
      caseOrgan: '',
      version: '',
      rootTitle: '',
      msg: '',
      inputidentifyCode: '',
      isShowCaseOrganComboBox: false
    }
  },
  async asyncData({ app, store, redirect, query, params }) {
    if (!params.id) {
      redirect('/')
    }
    let id = params.id
    let data = params.data
    if (!data) redirect(`/apply_form/?casesetid=${id}`)

    let isShow = params.caseOrganComboBox
      ? params.caseOrganComboBox.isShow
      : false

    let caseOrganComboBox = params.caseOrganComboBox
      ? params.caseOrganComboBox.options
      : []
    let caseOrganComboBoxListTemp = []
    if (caseOrganComboBox) {
      caseOrganComboBox.forEach(opt => {
        caseOrganComboBoxListTemp.push(opt.value + '-' + opt.label)
      })
    }
    let caseOrganComboBoxList = caseOrganComboBoxListTemp.join(',')
    let version = params.version
    let caseName = params.caseName
    let rootTitle = params.rootTitle
    let mydatatxid = params.mydatatxid

    return {
      caseOrganComboBox: caseOrganComboBox,
      dataList: data,
      mydatatxid: mydatatxid,
      id: id,
      caseName: caseName,
      caseFlowType: '',
      landNumOption: '',
      caseOrganComboBoxList: caseOrganComboBoxList,
      version: version,
      rootTitle: rootTitle,
      backData: params,
      isShowCaseOrganComboBox: isShow
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.rootTitle = this.root.title
      this.rootType = this.root.type
      this.rootId = this.root.id
      this.$nuxt.$loading.start()
      setTimeout(() => this.$nuxt.$loading.finish(), 1000)
    })
  },
  methods: {
    goBack() {
      this.$router.push({
        name: 'apply_form',
        params: {
          id: this.id,
          backData: this.backData
        }
      })
    },
    async next() {
      if (!(await this.$refs.observer.validate())) {
        this.$refs.captcha.refreshCode()
        return
      }

      let postdata = {
        caseOrgan: this.caseOrgan,
        caseSetId: this.id,
        txId: this.mydatatxid,
        version: this.version,
        columnData: []
      }
      this.dataList.forEach(items => {
        items.columnData.forEach(item => {
          let columnData = {
            columnId: item.columnId,
            columnType: item.columnType,
            value: item.value.replace(/＠/g, ""),
            complex: [],
            files: []
          }

          if (item.columnType != 'Fil') {
            item.complex.forEach(details => {
              var complexs = []
              columnData.complex.push(complexs)
              details.forEach(detail => {
                console.error(detail);
                if (detail.columnType != 'Fil') {
                  complexs.push({
                    columnId: detail.cColumnId,
                    columnType: detail.columnType,
                    value: detail.value.replace(/＠/g, "")
                  })
                } else {
                  postdata.files.push({
                    fileBase64Str: detail.value,
                    fileName: detail.columnValue
                  })
                }
              })
            })
          } else {
            // 如果isReadonly代表是myData帶回的檔案
            //columnData.value = item.isReadonly ? item.value : item.columnValue
            columnData.value = item.columnValue
            // 如果isReadonly代表是myData帶回的檔案，不用塞files=>isReadonly也有可能是附件必填，所以無法用isReadonly來判斷是不是mydata
            //if (!item.isReadonly) {
            if (item.columnValue != '') {
              columnData.files.push({
                fileBase64Str: item.value,
                fileName: item.columnValue
              })
            }

            //}
          }

          postdata.columnData.push(columnData)
        })
      })
      this.$nuxt.$loading.start()
      const res = await this.$apiFillForm.saveAction(postdata, error => {
        this.msg = this.$common.errorRspMsg(error)
        this.$refs.modal.show()
      })
      this.$nuxt.$loading.finish()
      if (!res) return
      var caseOrganItem = []
      if (this.caseOrganComboBox) {
        caseOrganItem = this.caseOrganComboBox.filter(
          item => item.value == this.caseOrgan
        )
      }
      var caseOrganName = ''
      if (caseOrganItem.length > 0) caseOrganName = caseOrganItem[0].label

      this.validateToken = this.captchaToken
      this.validateCode = this.identifyCode

      if (res.rtnCode == '0000') {
        this.$router.push({
          name: 'apply-step5-id',
          params: {
            id: this.id,
            data: res,
            caseName: this.caseName,
            caseOrganName: caseOrganName,
            isShowCaseOrgan: this.isShowCaseOrganComboBox,
            rootTitle: this.rootTitle,
            rootType: this.rootType,
            rootId: this.rootId
          }
        })
      } else {
        if (res.data) {
          if (res.data.validationMsg) {
            this.msg = res.data.validationMsg.join('/r/n')
          } else {
            this.msg = res.msg
          }
          this.$refs.modal.show()
        }
      }
    },
    goCaseList() {
      this.$router.push({
        path: '/apply/' + this.rootId,
        query: { type: this.rootType }
      })
    }
  }
}
</script>
