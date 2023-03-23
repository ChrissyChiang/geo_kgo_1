<template>
  <div>
    <editor
      :init="init"
      :value="value"
      :maxlength="max"
      :max-size="maxSize"
      @input="$emit('input', $event)"
    />
  </div>
</template>

<script>
import 'tinymce/tinymce'
import Editor from '@tinymce/tinymce-vue'
import 'tinymce/themes/silver'
import 'tinymce/plugins/image'
import 'tinymce/plugins/anchor'
import 'tinymce/plugins/charmap'
import 'tinymce/plugins/code'
import 'tinymce/plugins/codesample'
import 'tinymce/plugins/link'
import 'tinymce/plugins/preview'
import 'tinymce/plugins/table'
import 'tinymce/plugins/advlist'
import 'tinymce/plugins/lists'
import store from '@/store'
export default {
  name: 'AppEditor',
  components: {
    Editor
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    max: {
      type: String,
      default: '10'
    },
    maxSize: {
      type: Number,
      default: 2000
    },
    enableTalbe: {
      type: Boolean,
      default: true
    },
    height: {
      type: Number,
      default: 300
    },
    showLink: {
      type: Boolean,
      default: true
    },
    showUploadImage: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      init: {
        images_upload_handler: async (blobInfo, success, failure) => {
          const formData = new FormData()
          formData.append('img', blobInfo.blob())
          console.log(blobInfo.blob().size)
          const image_size = blobInfo.blob().size / 1024 / 1024 //mb
          if (image_size > 1) {
            failure('圖片大小限制1M(含)以內，請確認！')
          } else {
            this.$api.common
              .uploadImage(formData)
              .then(res => {
                if (res.data.rtnCode == 0) {
                  let file = res.data.data
                  success(file.imgUrl)
                  return
                }
                failure('上傳失敗')
              })
              .catch(e => {
                failure('上傳錯誤' + e)
              })
          }
        },
        setup: ed => {
          const allowedKeys = [8, 37, 38, 39, 40, 46] // backspace, delete and cursor keys
          ed.on('keydown', e => {
            const contentContentLenght = this.getContentLength()
            if (allowedKeys.indexOf(e.keyCode) != -1) return true
            if (contentContentLenght > this.maxSize - 1) {
              e.preventDefault()
              e.stopPropagation()
              return false
            } else {
              return true
            }
          })
          ed.on('paste', e => {
            const contentContentLenght = this.getContentLength()
            let pasteContent = ''
            if (e.clipboardData) {
              pasteContent = e.clipboardData.getData('Text')
            } else if (window.clipboardData) {
              pasteContent = window.clipboardData.getData('Text')
            }
            if (pasteContent.length > this.maxSize - contentContentLenght) {
              return false
            } else {
              return true
            }
          })
          ed.on('keyup', e => {
            this.$emit('update:total', this.getContentLength())
          })
          ed.on('SetContent', e => {
            this.$emit('update:total', this.getContentLength())
          })
        },
        body_class: 'vue-tiny',
        inline_boundaries: false,
        content_css: '/tinymce/css/style.css',
        content_style: 'body { font-family: 微軟正黑體; }',
        height: this.height + 'px',
        width: '100%',
        plugins: [
          'anchor',
          'charmap',
          this.showUploadImage ? 'image' : '',
          'code',
          this.showLink ? 'link' : '',
          'preview',
          'table',
          'lists',
          'advlist'
        ],
        theme: 'silver',
        branding: false,
        elementpath: false,
        statusbar: false,
        menubar: false,
        toolbar1:
          'charmap | bold italic underline | forecolor backcolor | alignleft aligncenter alignright alignjustify | indent bullist numlist | link image jbimages | fontsizeselect | preview',
        language: this.getLang(),
        language_url: '/tinymce/langs/zh_TW.js',
        skin: 'oxide',
        skin_url: '/tinymce/skins/ui/oxide',
        font_formats:
          'Arial=arial,helvetica,sans-serif;' +
          '微軟正黑體=微軟正黑體;' +
          '新細明體=新細明體,serif;' +
          '標楷體=標楷體,DFKai-sb;' +
          '思源體=Noto Sans TC;' +
          '仿宋體=cwTeXFangSong,serif;' +
          '明體=cwTeXMing, serif;' +
          '圓體=cwTeXYen, sans-serif;' +
          'Andale Mono=andale mono,monospace;' +
          'Arial Black=arial black,sans-serif;' +
          'Book Antiqua=book antiqua,palatino,serif;' +
          'Comic Sans MS=comic sans ms,sans-serif;' +
          'Courier New=courier new,courier,monospace;' +
          'Georgia=georgia,palatino,serif;' +
          'Helvetica=helvetica,arial,sans-serif;' +
          'Impact=impact,sans-serif;' +
          'Symbol=symbol;' +
          'Tahoma=tahoma,arial,helvetica,sans-serif;' +
          'Terminal=terminal,monaco,monospace;' +
          'Times New Roman=times new roman,times,serif;' +
          'Trebuchet MS=trebuchet ms,geneva,sans-serif;' +
          'Verdana=verdana,geneva,sans-serif;' +
          'Webdings=webdings;' +
          'Wingdings=wingdings,zapf dingbats'
      }
    }
  },
  mounted() {
    this.initTinymce()
    $(document).on('focusin', function(e) {
      if (
        $(e.target).closest(
          '.tox-tinymce-aux, .-window, .tam-assetmanager-root'
        ).length
      ) {
        e.stopImmediatePropagation()
      }
    })
  },
  methods: {
    getImageWidthHeight(blobData) {
      return new Promise(function(resolve, reject) {
        let image = new Image()
        image.src = URL.createObjectURL(blobData)
        image.onload = () =>
          resolve({ height: image.height, width: image.width })
        image.onerror = reject
      })
    },
    getLang() {
      return store.state.lang == 'tw' ? 'zh_TW' : 'en'
    },
    initTinymce() {
      window.tinymce.init({
        ...this.config,
        content_style: 'img {max-width:100% !important }', // 初始化赋值
        init_instance_callback: editor => {
          if (this.content) {
            editor.setContent(this.content)
          }
          this.finishInit = true
          editor.on('NodeChange Change SetContent KeyUp', () => {
            this.hasChanged = true
          })
        }
      })
    },
    tinymce_updateCharCounter(el, len) {
      $('#' + el.id)
        .prev()
        .find('.char_count')
        .text(len + '/' + el.settings.max_chars)
    },
    insertContent(content) {
      if (this.getContentLength() + content.length < this.maxSize) {
        tinymce.activeEditor.execCommand('mceInsertContent', false, content)
      }
    },
    getContentLength() {
      let htmlContent = tinyMCE.activeEditor.getContent()
      htmlContent = htmlContent
        ? htmlContent.replace(/&nbsp;/gi, '')
        : htmlContent
      htmlContent = htmlContent ? htmlContent.replace(/\s*/g, '') : htmlContent
      htmlContent = htmlContent
        ? htmlContent.replace(/<[^>]+>/g, '')
        : htmlContent
      const contentContentLenght = htmlContent.length
      //const contentContentLenght = tinyMCE.get(tinymce.activeEditor.id).contentDocument.body.innerText.length
      return contentContentLenght
    }
  }
}
</script>
<style lang="scss" scoped>
* {
  touch-action: none;
}
</style>
