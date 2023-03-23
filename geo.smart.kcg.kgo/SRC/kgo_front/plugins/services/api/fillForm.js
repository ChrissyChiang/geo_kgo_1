import { CheckData, Validation_Action, SAVE_ACTION, FILL_FORM_HOME_DATA, FILL_FORM_GET_MYDATA_URL, FILL_FORM_GET_MYDATA_DATA, MYDATA_FILE_DOWNLOAD } from './constants'
export default ($axios, inject) => {
  inject('apiFillForm', {
    async getFillHomeData(param, onError) {
      try {
        return CheckData(await $axios.post(FILL_FORM_HOME_DATA, param))
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }

    },
    async getFillMyDataUrl(param, onError) {
      try {
        return await $axios.post(FILL_FORM_GET_MYDATA_URL, param)
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }
    },
    async getFillMyData(param, onError) {
      try {
        return CheckData(await $axios.post(FILL_FORM_GET_MYDATA_DATA, param))
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }
    },
    getFillMyDataDownload(param) {
      return $axios.post(MYDATA_FILE_DOWNLOAD, param)
    },
    async saveAction(param, onError) {
      try {
        return await $axios.post(SAVE_ACTION, param)
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }
    },
    async validationAction(param, onError) {
      try {
        return CheckData(await $axios.post(Validation_Action, param))
      } catch (error) {
        if (onError)
          onError(error)
        return null
      }
    }
  })
}
