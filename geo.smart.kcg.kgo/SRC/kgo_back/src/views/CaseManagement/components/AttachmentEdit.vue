<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="counterApplyEditObserver">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  標題
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <validate-container :rules="'required'">
                    <input
                      v-model="form.title"
                      type="text"
                      class="form-control formItem_control"
                      placeholder=""
                    />
                  </validate-container>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-6 form-group col-md-12">
                  <label for="input_event" class="col-sm-2 control-label">
                    說明內容
                  </label>
                  <div class="col-sm-8">
                    <validate-container :rules="'required'">
                      <app-editor
                        v-model="form.contentHtml"
                        :max-size="5000"
                        :height="250"
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
                    附件
                  </label>
                  <div class="col-sm-8">
                    {{ this.form.fileName }}
                    <button
                      v-if="this.form.fileName != ''"
                      class="icon btn-delete"
                      @click="deleteFile"
                    >
                      <img
                        src="@/assets/img/btn-delete.png"
                        class="img-responsive"
                      />
                    </button>
                    <file-uploader
                      ref="importPdf"
                      v-model="form.uploadFile"
                      accept=".png, .jpg, .jpeg, .pdf, .odf"
                      :ext-name="'pdf, png, jpg, jpeg, odf'"
                    ></file-uploader>
                  </div>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled ">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        data-dismiss="modal"
                      >
                        <i class="fa " aria-hidden="true"></i>
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="saveFinish"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        確定
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
export default {
  name: 'AttachmentEdit',
  data() {
    return {
      form: {
        index: null,
        contentHtml: '',
        title: '',
        fileName: '',
        uploadFile: null,
        seq: ''
      }
    }
  },
  methods: {
    initData(data) {
      this.form.contentHtml = data.contentHtml || ''
      this.form.title = data.title || ''
      this.form.fileName = data.fileName || ''
      this.form.index = data.index
      this.form.seq = data.seq || ''
    },
    async saveFinish() {
      if (!(await this.$refs.counterApplyEditObserver.validate())) {
        return
      }
      this.$emit('confirm-edit', { ...this.form })
    },
    clear() {
      this.$refs.counterApplyEditObserver.reset()
      this.form.contentHtml = ''
      this.form.title = ''
      this.form.fileName = ''
      this.form.index = null
      this.form.uploadFile = null
      this.form.seq = ''
    },
    deleteFile() {
      this.form.fileName = ''
      this.form.uploadFile = null
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
