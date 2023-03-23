import { COMMON_QUESTION } from './constants'
export default ($axios, inject) => {
    inject('apiCommonQuestion', {
      getCommonQuestion() {
        return $axios.post(COMMON_QUESTION,{})
      }
    })
  }