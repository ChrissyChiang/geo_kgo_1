<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ title }}</h2>
        <a
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          class="accesskey"
          accesskey="C"
          name="C"
        >
          :::
        </a>
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <app-navigation page-title="案件進度查詢" />
      <!-- breadcrumb end -->

      <div v-if="!isError" class="apply-inner">
        <div class="states">
          <div class="date">申辦日期 {{ applyDate }}</div>
          <h3>
            {{ caseName }}
            <span
              :style="
                ['C3', 'C'].includes(statusCode) ? '' : 'background-color:green'
              "
            >
              {{ status }}
            </span>
          </h3>
          <div class="apply-number">
            <span>申請編號</span>
            {{ id }}
          </div>
        </div>
        <div class="row">
          <div class="col-md-9">
            <validation-observer ref="observer">
              <fieldset v-for="(item, index) in grpupsData" :key="index">
                <legend
                  style="
                    font-weight: bold;
                    padding: 0 15px 5px 15px;
                    margin-bottom: 0;
                    color: #333;
                    font-size: 1.2em;
                  "
                >
                  {{ item.groupName }}
                </legend>
                <div
                  class="form-group row"
                  v-for="(dataItem,
                  dataIndex) in item.caseProcessSearchDetailCasesetColumns"
                  :key="'columnData' + dataIndex"
                >
                  <label
                    class="col-sm-3 col-form-label"
                    :for="dataItem.columnId"
                  >
                    {{ dataItem.columnName }}
                    <span
                      v-if="
                        ['C3', 'C'].includes(statusCode) &&
                          loginType == 'MOICA' &&
                          dataItem.isMustKey == '1' &&
                          dataItem.isCorrect == 'Y'
                      "
                      style="
                        font-size: 0.8rem;
                        color: #e37b00;
                        padding-left: 0.4em;
                      "
                    >
                      必填
                    </span>
                  </label>
                  <div class="col-sm-9" v-if="dataItem.columnType != 'M'">
                    <component
                      :is="
                        ['C3', 'C'].includes(statusCode) &&
                        loginType == 'MOICA' &&
                        dataItem.isCorrect == 'Y'
                          ? dataItem.columnType
                          : 'Read' + dataItem.columnType
                      "
                      :columnType="dataItem.columnType"
                      :value="
                        dataItem.columnType == 'Radio' ||
                        dataItem.columnType == 'Drp' ||
                        dataItem.columnType == 'Checkbox'
                          ? dataItem.columnValue
                          : dataItem.columnDetailValue
                      "
                      :datavalue.sync="dataItem.columnDetailValue"
                      :disabled="!isLogin || (isLogin && loginType != 'MOICA')"
                      :showvalue.sync="dataItem.columnValue"
                      :required="
                        ['C3', 'C'].includes(statusCode) &&
                          dataItem.isMustKey == '1' &&
                          loginType == 'MOICA' &&
                          dataItem.isCorrect == 'Y'
                      "
                      :inputLength="dataItem.length"
                      :id="dataItem.columnId"
                    ></component>
                    <span
                      v-if="
                        ['C3', 'C'].includes(statusCode) &&
                          loginType == 'MOICA' &&
                          dataItem.isCorrect == 'Y' &&
                          dataItem.correctMemo &&
                          dataItem.correctMemo != ''
                      "
                      style="
                        font-size: 0.8rem;
                        color: #e37b00;
                        padding-left: 0.4em;
                      "
                    >
                      {{ dataItem.correctMemo }}
                    </span>
                  </div>
                  <div v-else class="col-sm-9">
                    <div
                      class="form-row"
                      v-for="(detailItem,
                      detailIndex) in dataItem.caseProcessSearchDetailCasesetColumnChildren"
                      :key="'detail' + detailIndex"
                    >
                      <div
                        v-for="(detailOrderItem,
                        detailOrderIndex) in detailItem"
                        :key="'detailOrder' + detailOrderIndex"
                      >
                        <div style="padding-bottom: 10px">
                          <component
                            :is="
                              ['C3', 'C'].includes(statusCode) &&
                              loginType == 'MOICA' &&
                              detailOrderItem.isCorrect == 'Y'
                                ? detailOrderItem.columnType
                                : 'Read' + detailOrderItem.columnType
                            "
                            :columnType="detailOrderItem.columnType"
                            :id="detailOrderItem.cColumnId"
                            :value="
                              detailOrderItem.columnType == 'Drp'
                                ? detailOrderItem.columnValue
                                : detailOrderItem.columnDetailValue
                            "
                            :f-text="detailOrderItem.fText"
                            :b-text="detailOrderItem.bText"
                            :group="detailOrderItem.vGroup"
                            :required="
                              ['C3', 'C'].includes(statusCode) &&
                                loginType == 'MOICA' &&
                                detailOrderItem.isMustKey == '1' &&
                                detailOrderItem.isCorrect == 'Y'
                            "
                            :disabled="
                              !isLogin || (isLogin && loginType != 'MOICA')
                            "
                            :datavalue.sync="detailOrderItem.columnDetailValue"
                            :showvalue.sync="detailOrderItem.columnValue"
                            :inputLength="detailOrderItem.length"
                          ></component>
                        </div>
                      </div>
                    </div>
                    <span
                      v-if="
                        ['C3', 'C'].includes(statusCode) &&
                          dataItem.isCorrect == 'Y' &&
                          loginType == 'MOICA' &&
                          dataItem.correctMemo &&
                          dataItem.correctMemo != ''
                      "
                      style="
                        font-size: 0.8rem;
                        color: #e37b00;
                        padding-left: 0.4em;
                      "
                    >
                      {{ dataItem.correctMemo }}
                    </span>
                  </div>
                </div>
              </fieldset>
            </validation-observer>
            <div class="form-group text-center btn-even">
              <button
                class="btn btn-lg btn-org"
                @click="save"
                type="button"
                title="儲存案件按鈕"
              >
                {{
                  ['C3', 'C'].includes(statusCode) &&
                  isLogin &&
                  loginType == 'MOICA'
                    ? '補正儲存'
                    : '返回'
                }}
              </button>
            </div>
          </div>
          <div class="col-md-3">
            <div
              v-if="history && history.length > 0"
              class="historySet arrow-up"
            >
              <h4>處理歷程</h4>
              <div
                v-for="(data, index) in history"
                :key="index"
                :class="index == 0 ? 'box start' : 'box'"
              >
                <h5>{{ data.status }}</h5>
                <p v-if="index == 0">{{ data.organ }}</p>
                <small>{{ data.dealTime }}</small>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else>請由案件查詢頁重新進入</div>
    </div>
    <!--modal start-->
    <system-confirm :msg="msg" ref="modal" />
    <!--modal end-->
    <system-confirm ref="loginModal" :msg="'請用自然人憑證登入才可補正！'" />
  </div>
</template>
<script>
import Readorgcheck from '../../../apply/step4/_id/components/textBox'
import ReadTextBox from '../../../apply/step4/_id/components/textBox'
import ReadTextId from '../../../apply/step4/_id/components/textId.vue'
import ReadTextArea from '../../../apply/step4/_id/components/textArea.vue'
import ReadTextEmail from '../../../apply/step4/_id/components/textEmail'
import ReadTextTel from '../../../apply/step4/_id/components/textTel'
import ReadTextTaxId from '../../../apply/step4/_id/components/textTaxId'
import ReadTextPhone from '../../../apply/step4/_id/components/textPhone'
import ReadNum from '../../../apply/step4/_id/components/textNum'
import ReadDrp from '../../../apply/step4/_id/components/dropDown.vue'
import ReadAddress from '../../../apply/step4/_id/components/address.vue'
import ReadRadio from '../../../apply/step4/_id/components/baseRadio.vue'
import ReadSRadio from '../../../apply/step4/_id/components/baseSingleRadio'
import ReadCheckbox from '../../../apply/step4/_id/components/checkBox.vue'
import ReadDate from '../../../apply/step4/_id/components/date.vue'
import ReadTime from '../../../apply/step4/_id/components/dateTime.vue'
import ReadDateSE from '../../../apply/step4/_id/components/dateRange.vue'
import ReadTimeSE from '../../../apply/step4/_id/components/dateRangeTime.vue'
import ReadLandNum from '../../../apply/step4/_id/components/landNum.vue'
import ReadTextLabel from '../../../apply/step4/_id/components/textLabel.vue'
import ReadSCheckbox from '../../../apply/step4/_id/components/checkSingleBox.vue'
import ReadFil from '../../../apply/step4/_id/components/file.vue'
import ReadAddressTextBox from '../../../apply/step4/_id/components/addressTextBox.vue'

import TextBox from '../../../apply_form/components/textBox'
import TextId from '../../../apply_form/components/textId.vue'
import TextArea from '../../../apply_form/components/textArea.vue'
import TextEmail from '../../../apply_form/components/textEmail'
import TextTel from '../../../apply_form/components/textTel'
import TextTaxId from '../../../apply_form/components/textTaxId'
import TextPhone from '../../../apply_form/components/textPhone'
import Num from '../../../apply_form/components/textNum'
import Drp from '../../../apply_form/components/dropDown.vue'
import Address from '../../../apply_form/components/textBox'
import Radio from '../../../apply_form/components/baseRadio.vue'
import SRadio from '../../../apply_form/components/baseSingleRadio'
import Checkbox from '../../../apply_form/components/checkBox.vue'
import SCheckbox from '../../../apply_form/components/checkSingleBox'
import Date from '../../../apply_form/components/date.vue'
import Time from '../../../apply_form/components/dateTime.vue'
import DateSE from '../../../apply_form/components/dateRange.vue'
import TimeSE from '../../../apply_form/components/dateRangeTime'
import LandNum from '../../../apply_form/components/textBox.vue'
import TextLabel from '../../../apply_form/components/textLabel'
import Fil from '../../../apply_form/components/file.vue'
import AddressTextBox from '../../../apply_form/components/addressTextBox.vue'
import { saveAs } from 'file-saver'
import orgcheck from '../../../apply_form/components/orgcheck'
import page from '~/mixins/page.js'

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
    AddressTextBox,
    orgcheck,
    ReadNum,
    ReadTextBox,
    ReadTextId,
    ReadTextArea,
    ReadTextEmail,
    ReadTextTel,
    ReadTextTaxId,
    ReadTextPhone,
    ReadDrp,
    ReadAddress,
    ReadRadio,
    ReadSRadio,
    ReadCheckbox,
    ReadSCheckbox,
    ReadDate,
    ReadDateSE,
    ReadTimeSE,
    ReadTime,
    ReadLandNum,
    ReadTextLabel,
    ReadFil,
    Readorgcheck,
    ReadAddressTextBox
  },
  mixins: [page],
  data() {
    return {
      msg: '',
      id: '',
      applyDate: '',
      caseName: '',
      identity: '1234',
      status: '',
      statusCode: '',
      type: 0,
      grpupsData: [],
      history: [],
      isError: false,
      title: '案件進度查詢'
    }
  },
  head() {
    return {
      title: this.title
    }
  },
  async mounted() {
    var params = this.$route.params
    this.id = this.$route.params.id
    this.identity = this.$route.params.identity
    this.type = this.$route.params.type
    if (this.type == undefined || (!this.identity && this.type != 3)) {
      this.isError = true
      return
    }

    this.$nextTick(async () => {
      var post = {
        gstrCaseId: this.id,
        validateCode: this.validateCode,
        validateCodeToken: this.validateToken
      }
      switch (parseInt(this.type)) {
        case 0:
          post.idCheck = this.identity
          break
        case 1:
          post.idCheckFull = this.identity
          break
        case 2:
          post.phone = this.identity
          break
      }

      this.$nuxt.$loading.start()

      const res = await this.$apiCaseList.getCaseDetail(post, error => {
        this.msg = this.$common.errorRspMsg(error)
        this.$refs.modal.show()
      })
      this.$nuxt.$loading.finish()
      if (!res) return

      this.id = res.data.caseProcessSearchDetailCaseset.caseId
      this.applyDate = res.data.caseProcessSearchDetailCaseset.applyDate
      this.caseName = res.data.caseProcessSearchDetailCaseset.casesetName
      this.status = res.data.caseProcessSearchDetailCaseset.statusDscr
      this.statusCode = res.data.caseProcessSearchDetailCaseset.status
      if (this.loginType != 'MOICA' && ['C3', 'C'].includes(this.statusCode)) {
        this.$refs.loginModal.show()
      }
      this.grpupsData = res.data.caseProcessSearchDetailCasesetGroups
      this.history = res.data.historyData

      this.grpupsData.forEach(item => {
        item.caseProcessSearchDetailCasesetColumns.forEach(item2 => {
          if (
            item2.columnType != 'Radio' &&
            item2.columnType != 'Drp' &&
            item2.columnType != 'Checkbox' &&
            item2.columnType != 'M'
          ) {
            item2.columnValue = item2.columnDetailValue
          }
          if (
            item2.caseProcessSearchDetailCasesetColumnChildren !== undefined &&
            item2.caseProcessSearchDetailCasesetColumnChildren !== null
          ) {
            item2.caseProcessSearchDetailCasesetColumnChildren.forEach(
              item3 => {
                item3.forEach(item4 => {
                  if (
                    item4.columnType != 'SRadio' &&
                    item4.columnType != 'SCheckbox' &&
                    item4.columnType != 'Drp'
                  ) {
                    item4.columnValue = item4.columnDetailValue
                  }
                })
              }
            )
          }
        })
      })

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
  methods: {
    async save() {
      if (!(await this.$refs.observer.validate())) {
        return
      }

      if (
        ['C3', 'C'].includes(this.statusCode) &&
        this.isLogin &&
        this.loginType == 'MOICA'
      ) {
        var post = {
          caseId: this.id,
          caseProcessSearchDetailSaveColumnList: []
        }

        this.grpupsData.forEach(group => {
          group.caseProcessSearchDetailCasesetColumns.forEach(item => {
            if (item.isCorrect == 'Y' && item.columnType != 'M') {
              post.caseProcessSearchDetailSaveColumnList.push({
                columnId: item.columnId,
                columnValue:
                  item.columnType == 'Fil'
                    ? item.columnValue
                    : item.columnDetailValue,
                fileBase64Str:
                  item.columnType == 'Fil' ? item.columnDetailValue : ''
              })
            }
            if (item.caseProcessSearchDetailCasesetColumnChildren) {
              item.caseProcessSearchDetailCasesetColumnChildren.forEach(
                detail => {
                  if (detail.isCorrect == 'Y') {
                    post.caseProcessSearchDetailSaveColumnList.push({
                      columnId: item.columnId,
                      ccolumnId: detail.cColumnId,
                      columnValue:
                        detail.columnType == 'Fil'
                          ? detail.columnValue
                          : detail.columnDetailValue,
                      fileBase64Str:
                        detail.columnType == 'Fil'
                          ? detail.columnDetailValue
                          : ''
                    })
                  }
                }
              )
            }
          })
        })
        this.$nuxt.$loading.start()
        var res = await this.$apiCaseList.detailSave(post, error => {
          this.msg = this.$common.errorRspMsg(error)
          this.$refs.modal.show()
        })
        this.$nuxt.$loading.finish()
        if (!res) return

        if (res.rtnCode == '0000') {
          this.msg = '成功'
          this.$refs.modal.show(() => {
            this.$router.back(-1)
          })
        }
      } else {
        this.$router.back(-1)
      }
    }
  }
}
</script>
