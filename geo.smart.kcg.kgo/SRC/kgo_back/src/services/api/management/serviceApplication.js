import { $axios } from '../../axios'
import {
  SERVICE_APPLICATION_HOME,
  SERVICE_APPLICATION_SEARCH,
  SERVICE_APPLICATION_ADD_HOME,
  SERVICE_APPLICATION_ADD_SAVE,
  AUTHORITY_APPLICATION_HOME,
  AUTHORITY_APPLICATION_SAVE
} from './constants'

/**畫面初始 */
export function getServiceApplicationHomeData() {
  return $axios.post(SERVICE_APPLICATION_HOME, {})
}
/** 搜尋問題 */
export function queryServiceApplication(param) {
  return $axios.post(SERVICE_APPLICATION_SEARCH, param)
}
/** 服務案件申請初始畫面 */
export function addServiceApplication(param) {
  return $axios.post(SERVICE_APPLICATION_ADD_HOME, param)
}
/** 服務案件申請儲存 */
export function saveServiceApplication(param) {
  return $axios.post(SERVICE_APPLICATION_ADD_SAVE, param)
}
/** 權限開通申請初始畫面 */
export function addAuthorityApplication(param) {
  return $axios.post(AUTHORITY_APPLICATION_HOME, param)
}
/** 權限開通申請儲存 */
export function saveAuthorityApplication(param) {
  return $axios.post(AUTHORITY_APPLICATION_SAVE, param)
}
