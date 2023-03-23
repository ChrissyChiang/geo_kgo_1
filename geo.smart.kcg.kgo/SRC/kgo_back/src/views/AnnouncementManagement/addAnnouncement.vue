<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <validation-observer ref="observer">
          <div class="fsm-form">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  發布對象
                </label>
                <div class="col-sm-8" style="width:900px">
                  <validate-container :rules="'required'">
                    <base-select
                      v-model="releaseObject"
                      :options="releaseObjectOptions"
                      :select="releaseObject"
                      required
                    ></base-select>
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  <span style="color:red;size:18px">*</span>
                  標題名稱
                </label>
                <div class="col-sm-8" style="width:900px">
                  <validate-container :rules="'required'">
                    <base-input
                      v-model="announcementName"
                      :placeholder="'輸入標題名稱'"
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
                  內容
                </label>
                <div class="col-sm-8" style="width:720px">
                  <validate-container :rules="'required'">
                    <app-editor
                      ref="editor"
                      v-model="announcementContent"
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
            <table class="table table-unstyled">
              <tbody>
                <tr colspan="4" align="center">
                  <td>
                    <button
                      type="button"
                      class="btn btn-fsm"
                      @click="saveAnnouncement()"
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
      id: '',
      announcementName: '',
      announcementContent: '',
      releaseObject: '',
      releaseObjectOptions: [],
      fileList: [],
      uploadFile: ''
    }
  },
  mounted() {
    this.id = this.$route.params.id == 'null' ? null : this.$route.params.id
    this.getHomeData()
    this.releaseObjectOptions = [
      { label: '前台', value: 'F' },
      { label: '後台', value: 'B' }
    ]
  },
  methods: {
    async getHomeData() {
      if (this.id != null) {
        let request = {
          seq: this.id,
          releaseObject: 'B',
          functionCode: 'AnnounceM'
        }
        await this.loadingContainer(async () => {
          const res = await this.$api.management.editAnnouncementHomeData(
            request
          )
          this.announcementName = res.data.data.activityName
          this.announcementContent = res.data.data.activityContent
          this.fileList =
            res.data.data.files === undefined ? [] : res.data.data.files
        })
      }
    },
    async saveAnnouncement() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      let request = {
        seq: this.id,
        activityContent: this.announcementContent,
        activityName: this.announcementName,
        caseSetId: [],
        files: this.fileList,
        functionCode: 'AnnounceM',
        pfDate: [],
        releaseObject: this.releaseObject,
        taskSeq: ''
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.saveAnnouncement(request)
        this.notifySuccess(res.data.data.msg)
      })
      this.goBack()
    },
    goBack() {
      this.$router.push(`/announcementManagement`)
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
    async downloadFile(index) {
      if (this.fileList[index].key == '') {
        let tem = this.fileList[index].base64Str.split(';')
        let tem2 = tem[0].split(':')
        let tem3 = this.fileList[index].base64Str.split(',')
        this.$f.toBase64ToFile(tem3[1], this.fileList[index].name, tem2[1])
      } else {
        let request = {
          fileKey: this.fileList[index].key,
          functionCode: 'AnnounceM',
          releaseObject: this.releaseObject,
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
          } else if (fileName.toLocaleLowerCase().indexOf('png') >= 0) {
            this.$f.toPng(res, this.fileList[index].name)
          } else if (
            fileName.toLocaleLowerCase().indexOf('jpg') >= 0 ||
            name.toLocaleLowerCase().indexOf('jpeg') >= 0
          ) {
            this.$f.toJpg(res, this.fileList[index].name)
          } else {
            this.$f.toOdt(res, this.fileList[index].name)
          }
        })
      }
    },
    deleteFile(index) {
      this.fileList.splice(index, 1)
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
