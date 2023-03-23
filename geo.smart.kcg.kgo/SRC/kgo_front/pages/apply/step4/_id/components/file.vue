<template>
  <div class="row">
    <span v-if="fText != ''" class="col-sm">{{ fText }}</span>
    <div class="col-sm-8">
      <!-- <a :href="datavalue" :download="showvalue">{{ showvalue }}</a> -->
     {{disabled ? value : showvalue}}
    </div>
    <span v-if="bText != ''" class="col-sm">{{ bText }}</span>
  </div>
</template>

<script>
export default {
  name: 'File',
  data() {
    return {
      valueV: ''
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
        this.$emit('update:showvalue', fileData.name)
        this.$emit('update:datavalue', base64)
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
