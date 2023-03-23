import { $axios } from '../../axios'
import {
  SIGN_WAITING_HONE,
  SIGN_WAITING_CASE_SEARCH,
  SIGN_WAITING_CASE_SIGN,
  SIGN_WAITING_CASE_SIGN_MANY,
  SIGN_WAITING_CASE_DISPATCH_MANY
} from './constants'

/**畫面初始 */
export function getSingWaitingHomeData() {
  return $axios.post(SIGN_WAITING_HONE, {})
}
/** 案件查詢 */
export function getSingWaitingCaseSearch(param) {
  return $axios.post(SIGN_WAITING_CASE_SEARCH, param)
}
/** 案件簽收 */
export function getSingWaitingCaseSign(param) {
  return $axios.post(SIGN_WAITING_CASE_SIGN, param)
}
/** 案件批次簽收 */
export function getSingWaitingCaseSignMany(param) {
  return $axios.post(SIGN_WAITING_CASE_SIGN_MANY, param)
}
/** 案件批次分文 */
export function getSingWaitingCaseShareMany(param) {
  return $axios.post(SIGN_WAITING_CASE_DISPATCH_MANY, param)
}
