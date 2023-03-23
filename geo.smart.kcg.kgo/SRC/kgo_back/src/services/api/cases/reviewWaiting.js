import { $axios } from '../../axios'
import {
  REVIEW_WAITING_HOME,
  REVIEW_WAITING_CASE_SEARCH,
  REVIEW_WAITING_CASE_SIGN_HOME,
  REVIEW_WAITING_END_CASE,
  REVIEW_WAITING_CANCEL,
  REVIEW_WAITING_CORRECTION_HOME,
  REVIEW_WAITING_CORRECTION,
  REVIEW_WAITING_SENDING_MESSAGE,
  REVIEW_WAITING_SENDING_MESSAGE_HISTORY,
  REVIEW_WAITING_ADD_FILE,
  REVIEW_WAITING_DELETE_FILE
} from './constants'

/** 畫面初始 */
export function getReviewWaitingHomeData() {
  return $axios.post(REVIEW_WAITING_HOME, {})
}
/** 案件查詢 */
export function getReviewWaitingCaseSearch(param) {
  return $axios.post(REVIEW_WAITING_CASE_SEARCH, param)
}
/**  結案 */
export function getReviewWaitingEndCase(param) {
  return $axios.post(REVIEW_WAITING_END_CASE, param)
}
/** 案件簽核-初始畫面 */
export function getReviewWaitingSignHomeData(param) {
  return $axios.post(REVIEW_WAITING_CASE_SIGN_HOME, param)
}
/** 取消簽收 */
export function getReviewWaitingSignCaseCancel(param) {
  return $axios.post(REVIEW_WAITING_CANCEL, param)
}
/** 補正作業-初始畫面 */
export function getReviewWaitingSignCaseCorrection(param) {
  return $axios.post(REVIEW_WAITING_CORRECTION_HOME, param)
}
/** 補正通知 */
export function getReviewWaitingSignCaseNoticeCorrection(param) {
  return $axios.post(REVIEW_WAITING_CORRECTION, param)
}
/** 訊息發送 */
export function getReviewWaitingSignSendingMessage(param) {
  return $axios.post(REVIEW_WAITING_SENDING_MESSAGE, param)
}
/** 訊息發送-歷程 */
export function getReviewWaitingSignSendingMessageHistory(param) {
  return $axios.post(REVIEW_WAITING_SENDING_MESSAGE_HISTORY, param)
}
/** 新增參考文件及備註 */
export function getReviewWaitingSignAddFile(param) {
  return $axios.post(REVIEW_WAITING_ADD_FILE, param)
}
/** 刪除參考文件及備註 */
export function getReviewWaitingSignDelFile(param) {
  return $axios.post(REVIEW_WAITING_DELETE_FILE, param)
}
