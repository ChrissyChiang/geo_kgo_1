import { $axios } from '../../axios'
import {
  PUSH_MSG_MANAGEMENT_HOME,
  PUSH_MSG_MANAGEMENT_EDIT_HOME,
  PUSH_MSG_MANAGEMENT_SAVE_EDIT,
  PUSH_MSG_MANAGEMENT_DELETE,
  PUSH_MSG_SHOW,
  PUSH_MSG_EDIT_OPEN
} from './constants'

/** 初始畫面 */
export function getPushMsgManagementHomeData() {
  return $axios.post(PUSH_MSG_MANAGEMENT_HOME, {})
}
/** 編輯初始畫面 */
export function getPushMsgManagementEditHomeData(param) {
  return $axios.post(PUSH_MSG_MANAGEMENT_EDIT_HOME, param)
}
/** 儲存推播訊息 */
export function savePushMsgManagementEdit(param) {
  return $axios.post(PUSH_MSG_MANAGEMENT_SAVE_EDIT, param)
}
/** 刪除推播訊息 */
export function deletePushMsgManagement(param) {
  return $axios.post(PUSH_MSG_MANAGEMENT_DELETE, param)
}
/** 服務管理-顯示推播訊息 */
export function showPushMsg(param) {
  return $axios.post(PUSH_MSG_SHOW, param)
}
/** 服務管理-設定開關 */
export function editPushMsgOpenOrNot(param) {
  return $axios.post(PUSH_MSG_EDIT_OPEN, param)
}
