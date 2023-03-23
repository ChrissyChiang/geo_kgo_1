<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-10">
                <label for="input_category" class="col-sm-2 control-label">
                  案件狀態
                </label>
                <div
                  v-if="
                    msgType == 'W' ||
                      msgType == 'P' ||
                      msgType == 'C' ||
                      msgType == 'F3' ||
                      msgType == 'J3' ||
                      msgType == 'B'
                  "
                  class="col-sm-10"
                  style="padding-top: 7px"
                >
                  {{ messageType }}
                </div>
                <div v-else class="col-sm-10">
                  <validate-container :rules="'required'">
                    <base-input
                      v-model="messageType"
                      :placeholder="'輸入案件狀態名稱'"
                      input-length="20"
                    ></base-input>
                  </validate-container>
                </div>
              </div>
            </div>
            <!--<div class="row">
              <div class="col-xs-6 form-group col-md-10">
                <label for="input_category" class="col-sm-2 control-label">
                  發送推播
                </label>
                <div class="col-sm-10" style="padding-top: 7px">
                  <base-radio
                    v-for="item in optionList"
                    :key="item.value"
                    v-model="sendOrNot"
                    :item-name="'send'"
                    :label="item.label"
                    :value="item.value"
                    :select="sendOrNot"
                  ></base-radio>
                </div>
              </div>
            </div>-->
            <div class="row">
              <div class="col-xs-6 form-group col-md-10">
                <label for="input_category" class="col-sm-2 control-label">
                  發送主旨
                </label>
                <div class="col-sm-10" style="padding-top: 7px">
                  <validate-container :rules="'required'">
                    <base-input v-model="messageTitle"></base-input>
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-10">
                <label for="input_category" class="col-sm-2 control-label">
                  推播文字
                </label>
                <div class="col-sm-8" style="padding-top: 7px">
                  <validate-container :rules="'required'">
                    <base-input
                      v-model="messageBody"
                      style="padding-right: 7px;width:100%"
                    ></base-input>
                  </validate-container>
                </div>
                <div style="padding-top: 14px">
                  <span
                    style="padding-top:14px;text-decoration:underline;color:blue"
                    @click="addMessageBody"
                  >
                    案件名稱
                  </span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-10">
                <label for="input_category" class="col-sm-2 control-label">
                  推播詳細內容
                </label>
                <span
                  type
                  style="padding-top:7px;padding-right:10px;text-decoration:underline;color:blue"
                  @click="addParams(1)"
                >
                  案件名稱
                </span>
                <span
                  style="padding-top:7px;padding-right:10px;text-decoration:underline;color:blue"
                  @click="addParams(2)"
                >
                  案件編號
                </span>
                <span
                  style="padding-top:7px;text-decoration:underline;color:blue"
                  @click="addParams(3)"
                >
                  申請時間
                </span>
                <div class="col-sm-10" style="padding-top: 7px">
                  <!--<app-editor v-model="messageContent" :show-link="false" :show-upload-image="false"></app-editor>-->
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-10">
                <label
                  for="input_category"
                  class="col-sm-2 control-label"
                ></label>
                <div class="col-sm-10" style="padding-top: 7px">
                  <validate-container :rules="'required'">
                    <app-editor
                      ref="editor"
                      v-model="messageContent"
                      :show-link="false"
                      :show-upload-image="false"
                    ></app-editor>
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button type="submit" class="btn btn-fsm" @click="goBack">
                        <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                        返回
                      </button>
                      <button
                        type="submit"
                        class="btn btn-fsm"
                        @click="saveMessage"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        儲存
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </validation-observer>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Editor from '@tinymce/tinymce-vue'
import 'tinymce/tinymce'
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      messageType: '',
      messageTitle: '',
      messageBody: '',
      messageContent: '',
      tableData: [],
      optionList: [],
      sendOrNot: 'true',
      msgType: '',
      userId: '',
      organId: ''
    }
  },
  watch: {
    messageBody() {
      if (this.messageBody != '') {
        this.messageBody = this.messageBody.replace(/\{0}/g, '${案件名稱}')
      }
    },
    messageContent() {
      if (this.messageContent != '') {
        this.messageContent = this.messageContent.replace(
          /\{0}/g,
          '${案件名稱}'
        )
        this.messageContent = this.messageContent.replace(
          /\{1}/g,
          '${案件編號}'
        )
        this.messageContent = this.messageContent.replace(
          /\{2}/g,
          '${申請時間}'
        )
      }
    }
  },
  async mounted() {
    this.msgType = this.$route.params.caseType
    this.userId = this.$route.params.userId
    this.organId = this.$route.params.organId
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      //加入初始化api
      if (this.msgType != 'null') {
        await this.loadingContainer(async () => {
          let request = {
            organId: this.organId,
            status: this.msgType,
            userId: this.userId
          }
          const res = await this.$api.management.getPushMsgManagementEditHomeData(
            request
          )
          this.messageContent = res.data.data.clickDetail
          this.messageBody = res.data.data.msgBody
          this.messageTitle = res.data.data.msgTitle
          this.sendOrNot = res.data.data.isEnable
          this.messageType = res.data.data.caseStatusName
        })
      }

      this.optionList = [
        { label: '開啟', value: 'true' },
        { label: '關閉', value: 'false' }
      ]

      if (this.msgType == 'W') {
        this.messageType = '民眾入案通知'
      } else if (this.msgType == 'P') {
        this.messageType = '承辦簽收通知'
      } else if (this.msgType == 'C') {
        this.messageType = '民眾補正通知'
      } else if (this.msgType == 'F3') {
        this.messageType = '結案不通過通知'
      } else if (this.msgType == 'J3') {
        this.messageType = '結案通過通知'
      } else if (this.msgType == 'B') {
        this.messageType = '補正完成通知'
      }
    },
    goBack() {
      this.$router.push(`/messageManagement`)
    },
    async saveMessage() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let content = this.messageContent.replace(/\${案件名稱}/g, '{0}')
      content = content.replace(/\${案件編號}/g, '{1}')
      content = content.replace(/\${申請時間}/g, '{2}')
      let messageBody = this.messageBody.replace(/\${案件名稱}/g, '{0}')
      let rtnCode = ''
      let today = new Date()
      let msgType =
        this.msgType == 'null'
          ? 'A' +
            today.getFullYear() +
            today.getMonth() +
            today.getMonth() +
            today.getDate() +
            today.getHours() +
            today.getMinutes() +
            today.getSeconds() +
            today.getMilliseconds()
          : this.msgType
      let request = {
        clickDetail: content,
        isEnable: 'true',
        msgBody: messageBody,
        msgTitle: this.messageTitle,
        organId: this.organId,
        status: msgType,
        caseStatusName: this.messageType,
        userId: this.userId
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.savePushMsgManagementEdit(
          request
        )
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        let msg =
          this.msgType == 'null' ? '新增推播訊息成功！' : '更新推播訊息成功！'
        this.notifySuccess(msg)
      }
      this.goBack()
    },
    addMessageBody() {
      this.messageBody = this.messageBody + '${案件名稱}'
    },
    addParams(param) {
      let data = ''
      if (param == 1) {
        data = '${案件名稱}'
      } else if (param == 2) {
        data = '${案件編號}'
      } else {
        data = '${申請時間}'
      }
      this.$refs.editor.insertContent(data)
    }
  }
}
</script>
