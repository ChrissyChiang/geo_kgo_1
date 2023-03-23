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
            <a
              :href="'/apply_form/?casesetid=' + id"
              :title="caseName || '案件名稱'"
            >{{ caseName || '案件名稱' }}</a>
          </li>
          <li class="breadcrumb-item active" aria-current="page">網路申請</li>
        </ol>
      </nav>
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
            <li class>
              確認
              <br />申請內容
            </li>
            <li class>
              步驟
              <br />完成
            </li>
          </ul>
        </div>
        <div>
          <div class="apply-item">
            <h3>填寫申請表</h3>
            <validation-observer ref="observer">
              <fieldset v-for="(item, index) in dataList" :key="index">
                <legend
                  style="
                    font-weight: bold;
                    padding: 0 15px 5px 15px;
                    margin-bottom: 0;
                    color: #333;
                    font-size: 1.2em;
                  "
                >
                  <button
                    v-if="index == 0 && isMyData"
                    name
                    title="載入資料MyData按鈕"
                    class="btn btn-gray pull-right"
                    type="button"
                    @click="callMyData()"
                  >
                    <i class="fa fa-plus" aria-hidden="true"></i>
                    載入資料MyData
                  </button>
                  {{ item.memo }}
                </legend>
                <div
                  class="form-group row"
                  v-for="(dataItem, dataIndex) in item['columnData']"
                  :key="'columnData' + dataIndex"
                >
                  <label class="col-sm-3 col-form-label" :for="dataItem.columnId">
                    {{ dataItem.columnName }}
                    <span
                      v-if="dataItem.isMustKey == '0' ? false : true"
                      style="
                        font-size: 0.8rem;
                        color: #e37b00;
                        padding-left: 0.4em;
                      "
                    >必填</span>
                  </label>
                  <!-- 動態表單開始  -->
                  <!-- 非覆核欄位 -->
                  <!-- disabled  mydatatxid來判斷是否已從mydata帶回資料，若是則綁mydata的欄位不能編輯 -->
                  <!-- options 地址和段小段要傳入縣市資料 -->
                  <!-- columnValue 為選項值 ex.Drp的radio1-單選1,radio2-單選2,radio3-單選3 -->
                  <!-- value 為所選填的值 ex.TextId的S222222222 or  Drp的radio1 -->
                  <div class="col-sm-9" v-if="dataItem.columnType != 'M'">
                    <component
                      :is="dataItem.columnType"
                      :disabled="dataItem.isReadonly
                      "
                      :title="dataItem.columnName"
                      :options="
                        dataItem.columnType == 'LandNum' ||
                        dataItem.columnType == 'Address'
                          ? landNumOption
                          : []
                      "
                      :value="
                        (dataFromMyData && (dataItem.columnType != 'Drp' && dataItem.columnType != 'Checkbox' && dataItem.columnType != 'Radio' ) )
                          ? dataItem.value
                          : dataItem.columnValue
                      "
                      :fileType="dataItem.fileType"
                      :datavalue.sync="dataItem.value"
                      :showvalue.sync="dataItem.columnValue"
                      :ref="'cardList' + index + dataIndex + 0"
                      :required="dataItem.isMustKey == '0' ? false : true"
                      :inputLength="dataItem.length"
                      @download-file="downloadFile"
                      :id="dataItem.columnId"
                    ></component>
                    <span>{{dataItem.memo }}</span>
                  </div>
                  <div v-else class="col-sm-9">
                    <div
                      class="form-row"
                      v-for="(detailItem, detailIndex) in dataItem['complex']"
                      :key="'detail' + detailIndex"
                    >
                      <div
                        v-for="(
                          detailOrderItem, detailOrderIndex
                        ) in detailItem"
                        :key="'detailOrder' + detailOrderIndex"
                      >
                        <div style="padding-bottom: 10px">
                          <component
                            :is="detailOrderItem.columnType"
                            :ref="'cardList' + index + dataIndex + detailIndex"
                            :title="dataItem.columnName"
                            :options="
                              detailOrderItem.columnType == 'LandNum' ||
                              detailOrderItem.columnType == 'Address'
                                ? landNumOption
                                : []
                            "
                            :disabled="detailOrderItem.isReadonly
                            "
                            :id="detailOrderItem.cColumnId"
                            :value="
                              dataFromMyData
                          ? detailOrderItem.value
                          : detailOrderItem.columnValue
                            "
                            :fileType="detailOrderItem.fileType"
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
            </validation-observer>
          </div>

          <div class="form-group text-center btn-even">
            <button
              class="btn btn-lg btn-gray-light"
              type="button"
              @click="goBack"
              title="上一步按鈕"
            >上一步</button>
            <button class="btn btn-lg btn-org" type="button" @click="next" title="下一步按鈕">下一步</button>
          </div>
        </div>
      </div>
    </div>
    <!--modal start-->
    <system-confirm ref="modal" :msg="msg" />
    <div
      id="inputIDModal"
      class="modal fade modal-alert singleBtn"
      tabindex="-1"
      role="dialog"
      data-backdrop="static"
      aria-labelledby="inputIDModal"
    >
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
              title="系統提示"
              @click="cancelID"
            >
              <i class="fa fa-times fa-lg"></i>
            </button>
          </div>
          <div class="modal-body">
            <div id="personalResultsAler-2Title" class="modal-title">請輸入身分證字號</div>
            <div class="row">
              <div class="col-sm-auto">
                <validation-observer ref="observerID">
                  <validate-container v-slot="data" :rules="'id|required'">
                    <input
                      :maxlength="10"
                      v-model="inputID"
                      type="text"
                      title="請輸入身分證字號"
                      class="form-control"
                      :class="[
                        data && data.errors[0] ? 'invalid is-error' : ''
                      ]"
                    />
                  </validate-container>
                </validation-observer>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <div class="btns">
              <button type="button" title="確認按鈕" class="btn btn-primary" @click="onInputIdMyData">確認</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      ref="sysＷaitModal"
      id="sysＷaitModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="sysModalTitle"
      aria-hidden="true"
      data-backdrop="static"
    >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content p-3">
          <div>
            <label style="font-size:20px" id="counter"></label>
          </div>
        </div>
      </div>
    </div>
    <!--modal end-->
  </div>
</template>
<script>
import TextBox from './components/textBox'
import TextId from './components/textId.vue'
import TextArea from './components/textArea.vue'
import TextEmail from './components/textEmail'
import TextTel from './components/textTel'
import TextTaxId from './components/textTaxId'
import TextPhone from './components/textPhone'
import Num from './components/textNum'
import Drp from './components/dropDown.vue'
import Address from './components/address.vue'
import Radio from './components/baseRadio.vue'
import SRadio from './components/baseSingleRadio'
import Checkbox from './components/checkBox.vue'
import SCheckbox from './components/checkSingleBox'
import Date from './components/date.vue'
import Time from './components/dateTime.vue'
import DateSE from './components/dateRange.vue'
import TimeSE from './components/dateRangeTime'
import LandNum from './components/landNum.vue'
import TextLabel from './components/textLabel'
import page from '~/mixins/page.js'
import Fil from './components/file.vue'
import Pic from './components/fileImage'
import AddressTextBox from './components/addressTextBox'
import { saveAs } from 'file-saver'
import SystemConfirm from '../../components/SystemConfirm.vue'
import OrgCheck from './components/orgcheck'
export default {
  components: {
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
    SCheckbox,
    Date,
    DateSE,
    TimeSE,
    Time,
    LandNum,
    TextLabel,
    Fil,
    Pic,
    AddressTextBox,
    SystemConfirm,
    OrgCheck
  },
  mixins: [page],
  head() {
    return {
      title: '填寫申請表'
    }
  },
  data() {
    return {
      id: '', //案件編號
      dataFromMyData: false,
      inputID: '',
      mydatatxid: '',//mydata導回時帶在網址
      rootTitle: '',
      caseName: '',
      rootType: '',
      rootId: '',
      landNumOption: [],
      isMyData: '',
      dataList: [],
      caseOrganComboBox: {},
      version: '',
      msg: '',
      tx_id: ''//mydata導回時帶在網址
    }
  },
  async asyncData({ app, store, $cookies, redirect, query, params }) {},
  async mounted() {
    await this.getFormData()

    this.rootTitle = this.root.title
    this.rootType = this.root.type
    this.rootId = this.root.id

    this.$nextTick(() => {})
  },
  updated() {
  },
  methods: {
    async getFormData() {
      this.$nextTick(async () => {
        let id = ''
        let tx_id = this.$route.query.tx_id
        let mydatatxid = this.$route.query.mydatatxid
        if (!this.$route.query.casesetid) {
          id = this.$route.params.casesetid
        } else {
          id = this.$route.query.casesetid
        }
        let caseName = ''
        let landNumOption = []
        let caseOrganComboBox = {}
        let isMyData = ''
        let version = 0
        let data = {}
        let res = {}
        let dataFromMyData = false

        //步驟4回到3將資料帶在this.$route.params.backData ->將步驟4資料帶回
        if (this.$route.params.backData) {
          id = this.$route.params.id
          mydatatxid = this.$route.params.backData.mydatatxid
          version = this.$route.params.backData.version
          caseName = this.$route.params.backData.caseName
          landNumOption = this.$route.params.backData.landNumOption
          isMyData = this.$route.params.backData.isMyData
          caseOrganComboBox = this.$route.params.backData.caseOrganComboBox
          data = this.$route.params.backData.data
          tx_id = this.$route.params.tx_id
        } else {
          this.$nuxt.$loading.start()
          //不為mydata帶回
          if (!tx_id) {
            dataFromMyData = false
            res = await this.$apiFillForm.getFillHomeData(
              {
                caseSetId: id
              },
              error => {
                this.msg = this.$common.errorRspMsg(error)
                this.$refs.modal.show(() => {
                  this.$router.push({ path: '/' })
                })
              }
            )
            //mydata帶回
          } else {
            dataFromMyData = true
            res = await this.$apiFillForm.getFillMyData(
              {
                caseSetId: id,
                txId: tx_id,
                myDataTxId: mydatatxid
              },
              error => {
                this.msg = this.$common.errorRspMsg(error)
                this.$refs.modal.show(() => {
                  this.$router.push({ path: '/' })
                })
              }
            )
          }

          this.$nuxt.$loading.finish()
          //有些mydata需花較多時間才能將資料帶回，需顯示等待秒數，res.data.delaySeconds為要延遲的秒數
          if (
            res.data.delaySeconds !== undefined &&
            res.data.delaySeconds != 0
          ) {
            // 需要延遲
            let count = res.data.delaySeconds
            let timer = setInterval(() => {
              if (count > 0) {
                $('#counter').text('資料正在帶回中，請稍等('+count+'秒)...');
                $('#sysＷaitModal').modal('show')
                count--
              } else {
                $('#sysＷaitModal').modal('hide')
                clearInterval(timer)
              }
            }, 1000)
            await this.delay(count *1000)
            this.$nuxt.$loading.start()
            res = await this.$apiFillForm.getFillMyData(
              {
                caseSetId: id,
                txId: tx_id,
                myDataTxId: mydatatxid
              },
              error => {
                this.msg = this.$common.errorRspMsg(error)
                this.$refs.modal.show(() => {
                  this.$router.push({ path: '/' })
                })
              }
            )
            this.$nuxt.$loading.finish()
          }

          if (!res) return
          version = res.data.version
          caseName = res.data.caseSetName
          landNumOption = res.data.options
          isMyData = res.data.isMyData
          data = res.data.grid

          //console.log(JSON.stringify(data))
          caseOrganComboBox = res.data.caseOrganComboBox
        }

        //取得開啟MyData後的暫存資料並將資料塞回畫面上
        var tempDataJson = sessionStorage.getItem('myDataTemp')
        if (tempDataJson) {
          sessionStorage.removeItem('myDataTemp')
          var tempData = JSON.parse(tempDataJson)
          data.forEach((items, itemsIndex) => {
            var x = tempData[itemsIndex]
            items.columnData.forEach((item, itemIndex) => {
              if (!item.isReadonly) {
                item.value = tempData[itemsIndex].columnData[itemIndex].value
                item.columnValue =
                  tempData[itemsIndex].columnData[itemIndex].columnValue
              }
              item.complex.forEach((detail, detailIndex) => {
                if (!detail.isReadonly) {
                  detail.value =
                    tempData[itemsIndex].columnData[itemIndex].complex[
                      detailIndex
                    ].value
                  detail.columnValue =
                    tempData[itemsIndex].columnData[itemIndex].complex[
                      detailIndex
                    ].columnValue
                }
              })
            })
          })
        }

        this.dataList = data
        this.id = id
        this.caseName = caseName
        this.landNumOption = landNumOption
        this.isMyData = isMyData
        this.caseOrganComboBox = caseOrganComboBox
        this.version = version
        this.mydatatxid = mydatatxid
        this.tx_id = tx_id
        this.dataFromMyData = dataFromMyData


        $(document).ready(() => {
          $('input[type="radio"]').on('click', function() {
            var other = $('input[name="' + $(this).attr('name') + '"]').not(
              $(this)
            )

            $(other).each(function() {
              this.dispatchEvent(new CustomEvent('uncheck'))
            })
          })

          $('input[type="checkbox"]').on('change', function() {
            var all = $('input[name="' + $(this).attr('name') + '"]')
            var other = all.not($(this))
            var chk = $.grep(all, (item, index) => item.checked).length > 0
            $(other).each(function() {
              this.dispatchEvent(
                new CustomEvent('othercheckchange', {
                  detail: { fireCheckbox: chk }
                })
              )
            })
          })
        })
      })
    },
    async callMyData() {
      //取得身份證字號
      let id = ''
      this.dataList.forEach(items => {
        items.columnData.forEach(item => {
          if (item.columnId == 'ID') {
            id = item.value
          }
        })
      })
      //call mydata需要身分證，故需要檢核是否已輸入身分證
      if (!id) {
        $('#inputIDModal').modal('show')
        return
      }

      this.gotoMyData(id)
    },

    goBack() {
      this.$router.push({ path: '/apply/step1/' + this.id })
    },
    async onInputIdMyData() {
      if (!(await this.$refs.observerID.validate())) {
        return
      }
      $('#inputIDModal').modal('hide')
      for (let i = 0; i < this.dataList.length; i++) {
        for (let m = 0; m < this.dataList[i].columnData.length; m++) {
          if (this.dataList[i].columnData[m].columnId == 'ID') {
            this.dataList[i].columnData[m].value = this.inputID
          }
        }
      }
      let id = this.inputID
      this.inputID = ''
      this.$refs.observerID.reset()
      this.gotoMyData(id)
    },
    async gotoMyData(inputID) {
      //取得MyDataURL
      var res = await this.$apiFillForm.getFillMyDataUrl(
        {
          birthday: '',
          caseSetId: this.id,
          pid: inputID
        },
        error => {
          this.msg = this.$common.errorRspMsg(error)
          this.$refs.modal.show()
        }
      )

      if (res) {
        const data = res.data
        const url = data ? data.url : ''
        if (url) {
          //將畫面上的資料暫存到sessionStorage
          sessionStorage.setItem('myDataTemp', JSON.stringify(this.dataList))
          document.location.href = url
        }
      }
    },
    async next() {
      if (!(await this.$refs.observer.validate())) {
        return
      }

      this.dataList.forEach(items => {
        items.columnData.forEach(item => {
          if (item.complex.length == 0) {
            if (!item.value || item.value == '') {
              item.value = ''
              item.columnValue = ''
            }
          } else {
            item.complex.forEach(detailItems => {
              detailItems.forEach(detail => {
                if (!detail.value || detail.value == '') {
                  detail.value = ''
                  detail.columnValue = ''
                }
              })
            })
          }
        })
      })
      //取檢核資料
      let validateList = []
      this.dataList.forEach(item => {
        item.columnData.forEach(item2 => {
          //let file = item2.columnType == 'Fil' ? [{fileBase64Str:item2.value,fileName:item2.columnValue}] : []
          let columnData = {
            columnId: item2.columnId,
            columnType: item2.columnType,
            //value: item2.value.replace('＠', ''),
            value: '',
            complex: [],
            files: []
          }
          if (item2.columnType != 'Fil') {
            item2.complex.forEach(details => {
              var complexs = []
              columnData.complex.push(complexs)
              details.forEach(detail => {
                if (detail.columnType != 'Fil') {
                  complexs.push({
                    columnId: detail.cColumnId,
                    columnType: detail.columnType,
                    value: detail.value.replace('＠', '')
                  })
                } else {
                  columnData.files.push({
                    fileBase64Str: detail.value,
                    fileName: detail.columnValue
                  })
                }
              })
            })
            columnData.value = item2.value.replace('＠', '')
          } else {
            columnData.files.push({
              fileBase64Str: item2.value,
              fileName: item2.columnValue
            })
            columnData.value = item2.columnValue
          }
          validateList.push(columnData)
        })
      })

      let postdata = {
        caseOrgan: '',
        caseSetId: this.id,
        txId: this.mydatatxid,
        version: this.version,
        columnData: validateList
      }
      this.$nuxt.$loading.start()
      const res = await this.$apiFillForm.validationAction(postdata, error => {
        this.msg = this.$common.errorRspMsg(error)
        this.$refs.modal.show()
      })
      this.$nuxt.$loading.finish()
      if (!res) return

      //顯示檢核未過之資料
      if (res.rtnCode == '0000') {
        let temMsg = ''
        if (res.data.validationMsg && res.data.validationMsg.length > 0) {
          for (let i = 0; i < res.data.validationMsg.length; i++) {
            temMsg =
              temMsg +
              res.data.validationMsg[i].validationMsg +
              res.data.validationMsg[i].columnName +
              '<br/>'
          }
          this.msg = temMsg
          this.$refs.modal.show()
          return
        }
        this.$router.push({
          name: 'apply-step4-id',
          params: {
            id: this.id,
            data: this.dataList,
            version: this.version,
            caseOrganComboBox: this.caseOrganComboBox,
            caseName: this.caseName,
            rootTitle: this.rootTitle,
            mydatatxid: this.mydatatxid,
            isMyData: this.isMyData,
            landNumOption: this.landNumOption,
            tx_id: this.tx_id
          }
        })
      } else {
        if (res.data.validationMsg) {
          this.msg = res.data.validationMsg.join('<br/>')
        } else {
          this.msg = res.msg
        }
        this.$refs.modal.show()
      }
    },
    goCaseList() {
      this.$router.push({
        path: '/apply/' + this.rootId,
        query: { type: this.rootType }
      })
    },
    async downloadFile(columnId, fileName) {
      //下載檔案
      if (fileName.toLocaleLowerCase().indexOf('pdf')) {
        const res = await this.$apiFillForm.getFillMyDataDownload({
          caseSetId: this.id,
          columnId: columnId,
          fileName: fileName,
          txId: this.mydatatxid,
          version: this.version
        })
        this.toPdf(res, fileName)
      }
    },
    cancelID() {
      this.inputID = ''
      this.$refs.observerID.reset()
    },
    toPdf(res, setFileName) {
      const file = res
      const blob = new Blob([file], { type: 'application/pdf' })
      saveAs(blob, setFileName)
    }
  }
}
</script>

