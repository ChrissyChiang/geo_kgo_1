<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <validation-observer ref="observer">
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  任務名稱
                </label>
                <div class="col-sm-8" style="width:900px">
                  <validate-container :rules="'required'">
                    <base-input
                      v-model="activityName"
                      :placeholder="'輸入任務名稱'"
                      input-length="200"
                    ></base-input>
                  </validate-container>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  任務內容
                </label>
                <div class="col-sm-8" style="width:720px">
                  <validate-container :rules="'required'">
                    <app-editor
                      ref="editor"
                      v-model="activityContent"
                      :enable-talbe="false"
                      :max-size="2000"
                    ></app-editor>
                  </validate-container>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  附件上傳
                </label>
                <div class="col-sm-8" style="width:700px">
                  <div v-if="fileList.length > 0">
                    <div
                      v-for="(item, index) in fileList"
                      :key="index"
                      style="display: flex; width: 450px"
                    >
                      <a
                        style="cursor: pointer; text-decoration: underline;color:blue"
                        @click="downloadFile(index)"
                      >
                        {{ item.name }}
                      </a>
                      <button
                        class="icon btn-delete"
                        @click="deleteFile(index)"
                      >
                        <img
                          src="@/assets/img/btn-delete.png"
                          class="img-responsive"
                        />
                      </button>
                    </div>
                  </div>
                  <div style="display: flex; width: 800px">
                    <file-uploader
                      ref="importPdf"
                      v-model="uploadFile"
                      accept=".png, .jpg, .jpeg, .pdf, .odf, .docx, .doc"
                      :ext-name="'pdf, png, jpg, jpeg, odf, docx, doc'"
                    ></file-uploader>

                    <button
                      type="button"
                      class="btn btn-fsm"
                      style="height:40px"
                      @click="addFile"
                    >
                      <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  任務日期
                </label>
                <div class="col-sm-8" style="width:700px">
                  <validate-container :rules="'input_date'">
                    <base-date
                      v-model="pfDate"
                      placeholder="請選擇任務日期區間"
                      :is-range="true"
                      :value="pfDate"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  所屬機關
                </label>
                <div class="col-sm-8" style="width:700px">
                  <validate-container :rules="'required'">
                    <base-select
                      v-model="organName"
                      :options="organNameOptions.options"
                      search
                      required
                      :select="organName"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  任務項目
                </label>
                <div class="col-sm-8" style="width:700px">
                  <validate-container :rules="'required'">
                    <base-select
                      v-model="activityItem"
                      :options="activityItemOptions.options"
                      search
                      :select="activityItem"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
          </div>

          <div class="fsm-form">
            <div class="row">
              <label for="input_event" class="col-sm-2 control-label">
                K幣總數量
              </label>
              <div class="col-sm-8">
                <label>{{ totalPoint }}</label>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <label for="input_event" class="col-sm-2 control-label">
                每次核發數量
              </label>
              <div class="col-sm-8">
                <label>{{ point }}</label>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  服務案件
                </label>
                <div class="col-sm-8" style="width:700px;height: 100px">
                  <validate-container :rules="'required'">
                    <multi-select
                      v-model="caseName"
                      :value="caseName"
                      :options="caseNameOptions.options"
                      :show-selected-limit="20"
                    />
                  </validate-container>
                </div>
              </div>
            </div>
          </div>
          <div class="fsm-form">
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="saveTask()"
                    >
                      <i class="fa fa-save" aria-hidden="true"></i>
                      儲存
                    </button>
                    <button type="button" class="btn btn-fsm" @click="goBack()">
                      <i class="fa fsm-icon-refresh" aria-hidden="true"></i>
                      返回
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
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      uploadFile: '',
      activityName: '',
      activityContent: '',
      id: '',
      release: '',
      pfDate: [],
      activityItemOptions: {},
      activityItem: '',
      totalPoint: '',
      point: '',
      organName: '',
      organNameOptions: {},
      caseName: [],
      caseNameId: '',
      caseNameOptions: [],
      fileList: []
    }
  },
  watch: {
    organName() {
      this.getServiceCase()
    },
    activityItem() {
      this.getPoint()
    }
  },
  mounted() {
    this.id = this.$route.params.id == 'null' ? null : this.$route.params.id
    this.release =
      this.$route.params.release == 'null' ? 'F' : this.$route.params.release
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      let request = {
        seq: this.id,
        releaseObject: this.release,
        functionCode: 'TaskM'
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.editAnnouncementHomeData(request)
        this.activityName = res.data.data.activityName
        this.activityContent = res.data.data.activityContent
        this.pfDate = res.data.data.pfDate
        this.activityItemOptions = res.data.data.activityItemOptions
        this.activityItem = res.data.data.activityItemOptions.selectedVal
        this.totalPoint = res.data.data.totalPoint
        this.point = res.data.data.point
        this.organNameOptions = res.data.data.organNameOptions
        this.organName = res.data.data.organNameOptions.selectedVal
        this.caseNameOptions = res.data.data.caseNameOptions
        this.fileList =
          res.data.data.files === undefined ? [] : res.data.data.files
        let tem = this.caseNameOptions.options.filter(
          item => item.selected == true
        )
        if (tem.length != 0) {
          this.caseName = tem
        }
        if (this.id == null) {
          this.totalPoint = ''
          this.point = ''
        }
        this.caseNameId = res.data.data.caseNameOptions.selectedVal
      })
    },
    async saveTask() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let caseSetIdList = []
      this.caseName.forEach(item => {
        caseSetIdList.push(item.value)
      })
      let request = {
        seq: this.id,
        activityContent: this.activityContent,
        activityName: this.activityName,
        caseSetId: caseSetIdList,
        files: this.fileList,
        functionCode: 'TaskM',
        pfDate: this.pfDate,
        releaseObject: 'F',
        taskSeq: this.activityItem
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.saveAnnouncement(request)
        this.notifySuccess(res.data.data.msg)
      })
      this.goBack()
    },
    async addFile() {
      let files = this.uploadFile
      if (files) {
        var fileData = files[0]
        const toBase64 = file =>
          new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.readAsDataURL(file)
            reader.onload = () => resolve(reader.result)
            reader.onerror = error => reject(error)
          })
        var base64 = await toBase64(fileData)
        this.fileName = fileData.name
        this.fileList.push({ name: fileData.name, key: '', base64Str: base64 })
      }
      this.uploadFile = ''
    },
    deleteFile(index) {
      this.fileList.splice(index, 1)
    },
    async downloadFile(index) {
      if (this.fileList[index].key == '') {
        let tem = this.fileList[index].base64Str.split(';')
        let tem2 = tem[0].split(':')
        let tem3 = this.fileList[index].base64Str.split(',')
        this.$f.toBase64ToFile(tem3[1], this.fileList[index].name, tem2[1])
      } else {
        let request = {
          fileKey: this.fileList[index].key,
          functionCode: 'TaskM',
          releaseObject: this.release,
          seq: this.id
        }
        await this.loadingContainer(async () => {
          const res = await this.$api.management.getAnnouncementManagementDownloadFile(
            request
          )
          if (
            this.fileList[index].name.toLocaleLowerCase().indexOf('pdf') >= 0
          ) {
            this.$f.toPdf(res, this.fileList[index].name)
          } else if (
            this.fileList[index].name.toLocaleLowerCase().indexOf('png') >= 0
          ) {
            this.$f.toPng(res, this.fileList[index].name)
          } else if (
            this.fileList[index].name.toLocaleLowerCase().indexOf('jpg') >= 0 ||
            name.toLocaleLowerCase().indexOf('jpeg') >= 0
          ) {
            this.$f.toJpg(res, this.fileList[index].name)
          } else {
            this.$f.toOdt(res, this.fileList[index].name)
          }
        })
      }
    },
    goBack() {
      this.$router.push(`/announcementManagement`)
    },
    async getServiceCase() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.organGetServiceCase({
          organId: this.organName
        })
        this.caseNameOptions = res.data.data.caseNameOptions
        this.activityItemOptions = res.data.data.activityItemOptions
      })
    },
    async getPoint() {
      await this.loadingContainer(async () => {
        const res = await this.$api.management.activityGetPoint({
          taskSeq: this.activityItem
        })
        this.totalPoint = res.data.data.totalPoint
        this.point = res.data.data.point
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.icon {
  border: 0 !important;
  padding: 0 !important;
  background-color: transparent !important;
  cursor: pointer !important;
}

.icon:focus {
  outline: 0 !important;
}
</style>
