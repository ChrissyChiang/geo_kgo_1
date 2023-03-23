<template>
  <div class="col d-flex align-items-center captcha">
    <s-identify :identifyCode="identifyCode"></s-identify>
    <a href="" title="語音播放驗證碼" @click.prevent="play">
      <audio src="" id="eventAudio1"></audio>
      <audio src="" id="eventAudio2"></audio>
      <audio src="" id="eventAudio3"></audio>
      <audio src="" id="eventAudio4"></audio>
      <i class="fa fa-volume-up fa-lg m-2" aria-hidden="true"></i>
    </a>
    <a href="" @click.prevent="refreshCode" title="更換圖形驗證碼">
      <i class="fa fa-refresh fa-lg m-2" aria-hidden="true"></i>
    </a>
  </div>
</template>
<script>
import audio0 from '~/static/audio/0.mp3'
import audio1 from '~/static/audio/1.mp3'
import audio2 from '~/static/audio/2.mp3'
import audio3 from '~/static/audio/3.mp3'
import audio4 from '~/static/audio/4.mp3'
import audio5 from '~/static/audio/5.mp3'
import audio6 from '~/static/audio/6.mp3'
import audio7 from '~/static/audio/7.mp3'
import audio8 from '~/static/audio/8.mp3'
import audio9 from '~/static/audio/9.mp3'
export default {
  name: 'Captcha',
  props: {
    code: {
      type: String,
      default: ''
    },
    token: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      identifyCode: '',
      identifyCodes: '0123456789',
      identifyApiToken: ''
    }
  },
  async mounted() {
   // console.log(this.validateCode.code);
    this.$nextTick(() => {
      this.refreshCode()
    })
  },
  methods: {
    async play() {
      let all = [
        audio0,
        audio1,
        audio2,
        audio3,
        audio4,
        audio5,
        audio6,
        audio7,
        audio8,
        audio9
      ]

      var data1 = all[this.identifyCode[0]]
      let buttonAudio1 = document.getElementById('eventAudio1')
      buttonAudio1.setAttribute('src', data1)
      buttonAudio1.play()

      setTimeout(() => {
        var data2 = all[this.identifyCode[1]]
        let buttonAudio2 = document.getElementById('eventAudio2')
        buttonAudio2.setAttribute('src', data2)
        buttonAudio2.play()
      }, 1000)
      setTimeout(() => {
        var data3 = all[this.identifyCode[2]]
        let buttonAudio3 = document.getElementById('eventAudio3')
        buttonAudio3.setAttribute('src', data3)
        buttonAudio3.play()
      }, 2000)
      setTimeout(() => {
        var data4 = all[this.identifyCode[3]]
        let buttonAudio4 = document.getElementById('eventAudio4')
        buttonAudio4.setAttribute('src', data4)
        buttonAudio4.play()
      }, 3000)
    },
    async refreshCode() {
      this.identifyCode = ''
      this.validateCode = ''
      this.validateToken = ''
      //this.makeCode(this.identifyCodes, 4)
      try {
        const res = await this.$apiUserAuth.getValidateCode()
        const data = res.data
        if (data) {
          this.identifyApiToken = data.validateCodeToken
          this.identifyCode = data.validateCode
        }
      } catch (error) {
        console.log(error)
      }
      this.$emit('update:code', this.identifyCode)
      this.$emit('update:token', this.identifyApiToken)
    },
    randomNum(min, max) {
      max = max + 1
      return Math.floor(Math.random() * (max - min) + min)
    },
    // 隨機生成驗證碼字符串
    makeCode(data, len) {
      for (let i = 0; i < len; i++) {
        this.identifyCode += data[this.randomNum(0, data.length - 1)]
      }
    }
  }
}
</script>
