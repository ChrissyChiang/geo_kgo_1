import { SERVICE_ANNOUNCEMENT, CheckData } from './constants'

export default ($axios, inject) => {

  inject('apiApplyConsent', {
    async getServiceConsent(param, onError) {
      try {
        return CheckData(await $axios.post(SERVICE_ANNOUNCEMENT, param))
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }
    }
  })
}
