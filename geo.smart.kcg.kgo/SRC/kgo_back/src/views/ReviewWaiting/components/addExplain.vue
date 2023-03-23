<template>
  <div>
    <validation-observer ref="observer">
      <div class="fsm-form">
        <div class="row">
          <div class="col-xs-6 form-group col-md-6">
            <label for="input_event" class="col-sm-4 control-label">標題</label>
            <div class="col-sm-8">
              <validate-container :rules="'required'">
                <base-input
                  v-model="form.title"
                  :placeholder="'輸入標題'"
                  input-length="50"
                ></base-input>
              </validate-container>
            </div>
          </div>
        </div>
      </div>
      <div class="fsm-form">
        <div class="row">
          <div class="col-xs-6 form-group col-md-6">
            <label for="input_event" class="col-sm-4 control-label">
              上傳附件
            </label>
            <div class="col-sm-8">
              <validate-container :rules="'required'">
                <file-uploader
                  ref="importPdf"
                  v-model="form.uploadFile"
                  accept=".png, .jpg, .jpeg, .pdf, .odf"
                  :ext-name="'.pdf, .png, .jpg, .jpeg, .odf'"
                ></file-uploader>
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
                <button type="button" class="btn btn-primary" @click="saveFile">
                  儲存
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </validation-observer>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'AddExplain',
  mixins: [page],
  data() {
    return {
      form: {
        title: '',
        uploadFile: null
      }
    }
  },
  methods: {
    async saveFile() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      this.$emit('save-file', this.form)
    },
    clear() {
      this.title = ''
      this.uploadFile = ''
    }
  }
}
</script>
