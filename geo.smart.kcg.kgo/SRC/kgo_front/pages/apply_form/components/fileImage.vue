<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm">{{ fText }}</span>
    <div class="col-sm-8">
      <file-uploader
        v-if="!disabled"
        id="importExcel"
        ref="importExcel"
        v-model="valueV"
        accept=".png,.jpg"
        :ext-name="'png'"
        :fName.sync="fileName"
      ></file-uploader>
      <button
        v-if="disabled"
        title="請上傳圖片"
        class="btn-gray-outline"
        @click="downloadFile"
      >
        {{ value }}
      </button>
    </div>
    <span v-if="bText != ''" class="col-sm">{{ bText }}</span>
  </div>
</template>

<script>
export default {
  name: 'File',
  data() {
    var name = ''
    if (this.showvalue) {
      name = this.showvalue
    }
    return {
      valueV: '',
      fileName: name
    }
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    },
    disabled: {
      type: Boolean,
      default: false
    },
    id: {
      type: String,
      default: ''
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    },
    datavalue: {
      type: [String, Number],
      default: ''
    }
  },
  watch: {
    fileName() {
      if (this.fileName == '') {
        this.$emit('update:showvalue', '')
        this.$emit('update:datavalue', '')
      }
    },
    async valueV() {
      var files = this.valueV
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
        this.$emit('update:showvalue', fileData.name.replace(/,/g, ''))
        this.$emit('update:datavalue', base64)
        this.fileName = fileData.name.replace(/,/g, '')
        //console.log(this.fileName)
      }
    }
  },
  methods: {
    downloadFile() {
      this.$emit('download-file', this.id, this.value)
    }
  }
}
</script>
