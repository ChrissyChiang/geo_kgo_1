import { $axios } from '../../axios'
import {
  ACCOUNT_MANAGEMENT_HOME,
  ACCOUNT_MANAGEMENT_SEARCH,
  ACCOUNT_MANAGEMENT_EDIT_HOME,
  ACCOUNT_MANAGEMENT_EDIT_SAVE,
  ACCOUNT_DELETE
} from './constants'

/**畫面初始 */
export function getAccountManagementHomeData() {
  return $axios.post(ACCOUNT_MANAGEMENT_HOME, {})
}
/** 搜尋帳號 */
export function queryAccount(param) {
  return $axios.post(ACCOUNT_MANAGEMENT_SEARCH, param)
}
/** 帳號維護初始畫面 */
export function editAccountHomeData(param) {
  return $axios.post(ACCOUNT_MANAGEMENT_EDIT_HOME, param)
}
/** 帳號維護儲存 */
export function saveAccount(param) {
  return $axios.post(ACCOUNT_MANAGEMENT_EDIT_SAVE, param)
}
/** 帳號刪除 */
export function deleteAccount(param) {
  return $axios.post(ACCOUNT_DELETE, param)
}
