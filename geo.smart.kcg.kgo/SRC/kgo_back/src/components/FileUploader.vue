<template>
  <div>
    <div class="custom-file">
      <input
        :id="$attrs.id || 'fileUploader'"
        ref="input"
        v-bind="$attrs"
        type="file"
        class="custom-file-input"
        :files="value"
        @change="handleChange"
      />
      <!-- <label
        :for="$attrs.id || 'fileUploader'"
        class="custom-file-label selected"
        :data-browse="'請選擇檔案'"
      >
        {{ fileName }}
      </label> -->
    </div>
    <span class="formInput_description">
      檔案大小應小於: {{ limitSize }}MB(含)以內，類型:{{ extName }}
    </span>
  </div>
  <!-- <div class="formInput">
    <input
      :id="$attrs.id || 'fileUploader'"
      ref="input"
      v-bind="$attrs"
      type="file"
      class="form-control formItem_control"
      :files="value"
      @change="handleChange"
      placeholder="請選擇檔案"
    />
    <span class="error_label"></span>
    <span class="formInput_description">檔案大小應小於{{ limitSize }}KB(含){{ extName }}</span>
  </div> -->
</template>

<script>
import { notify } from '@/mixins'
export default {
  name: 'FileUploader',
  mixins: [notify],
  inheritAttrs: false,
  props: {
    inputClass: {
      type: String,
      default: ''
    },
    value: {
      type: null,
      default: null
    },
    limitSize: {
      type: Number,
      default: 20
    },
    extName: {
      type: String,
      default: 'xlsx'
    }
  },
  data() {
    return {
      fileName: '請選擇檔案'
    }
  },
  watch: {
    value(newValue) {
      if (newValue == null || newValue.length == 0) {
        this.$refs.input.value = ''
        this.fileName = '請選擇檔案'
      }
    }
  },
  methods: {
    handleChange(e) {
      const { files } = e.target
      this.$emit('input', files)

      if (files.length > 0) {
        this.loadFile(files)
      }
    },
    async loadFile(files) {
      if (files) {
        const fileData = files[0]
        this.fileName = fileData.name
        const filesize = (fileData.size / 1024 / 1024).toFixed(0) // MB
        if (filesize > this.limitSize) {
          this.notifyFail(`檔案大小應小於: ${this.limitSize}MB(含)以內`)
          this.reset()
        }
      }
    },
    toBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => resolve(reader.result)
        reader.onerror = error => reject(error)
      })
    },
    reset() {
      this.$refs.input.value = ''
      this.fileName = '請選擇檔案'
      this.handleChange({ target: this.$refs.input })
    }
  }
}
</script>

<style lang="scss">
.upload-label {
  width: '100%';
  overflow: hidden;
}
</style>
