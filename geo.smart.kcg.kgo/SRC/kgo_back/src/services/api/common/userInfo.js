import { $axios } from '../../axios'
import { USER_INFO, CHANGE_LOCALE, UPLOAD_IMAGE } from './constants'

/** 取得 使用者資訊 */
export function getUserInfo() {
  return $axios.post(USER_INFO, {})
}

/** 上傳圖片 API */
export function uploadImage(param) {
  return $axios.post(UPLOAD_IMAGE, param)
}
