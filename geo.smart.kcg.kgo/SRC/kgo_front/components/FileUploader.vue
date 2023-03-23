<template>
  <div>
    <div class="custom-file">
      <div>
        <!-- <input type="button" @click="inputclick" value="選擇檔案" />
        <span id="fileName">{{ fName }}</span> -->
        <div class="form-text" v-if="fName != ''">
          {{ fName }}
          <a class="dele-file" href="#" @click.prevent="ondelete">
            <i class="fa fa-trash-o" aria-hidden="true"></i>
          </a>
        </div>
        <button
          v-if="fName == ''"
          name=""
          @click="inputclick"
          class="btn btn-gray"
          type="button"
          title="新增附件按鈕"
        >
          <i class="fa fa-plus" aria-hidden="true"></i>
          新增附件
        </button>
      </div>

      <input
        :id="$attrs.id || 'fileUploader'"
        ref="input"
        v-bind="$attrs"
        type="file"
        :files="value"
        @change="handleChange"
        title="122"
        style="display: none"
      />
      <!-- <label
        :for="$attrs.id || 'fileUploader'"
        class="custom-file-label selected"
        :data-browse="'請選擇檔案'"
      >
        {{ fileName }}
      </label> -->
    </div>
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
export default {
  name: 'FileUploader',
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
    },
    fName: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      fileName: ''
    }
  },
  watch: {
    value(newValue) {
      if (newValue == null || newValue.length == 0) {
        this.$refs.input.value = ''
        this.fileName = ''
      }
    }
  },
  methods: {
    ondelete() {
      this.$refs.input.value = ''

      this.fileName = ''
      this.$emit('update:fName', '')
      this.$emit('update:value', '')
    },
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
      this.fileName = ''
      this.handleChange({ target: this.$refs.input })
    },
    inputclick() {
      this.$refs.input.click()
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
