<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="formDownloadEditObserver">
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
                    分類
                  </label>
                  <div class="col-sm-8">
                    <validate-container :rules="'required'">
                      <base-native-select
                        v-model="form.type"
                        :options="formTypeOptions"
                        :select="form.type"
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
  name: 'FormDownloadEdit',
  props: {
    formTypeOptions: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      form: {
        seq: null,
        type: '',
        title: '',
        fileName: '',
        uploadFile: null
      }
    }
  },
  methods: {
    initData(data) {
      if (data != null) {
        this.form.seq = data.seq
        this.form.type = data.type
        this.form.title = data.title
        this.form.fileName = data.fileName
      }
    },
    async saveFinish() {
      if (!(await this.$refs.formDownloadEditObserver.validate())) {
        return
      }
      this.$emit('confirm-edit', this.form)
    },
    clear() {
      this.$refs.formDownloadEditObserver.reset()
      this.form.seq = null
      this.form.type = ''
      this.form.title = ''
      this.form.fileName = ''
      this.form.uploadFile = null
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
