import { $axios } from '../../axios'
import { jsonToFormData } from '@/utils/form'
import {
  CASE_VIEW_HOME,
  CASE_VIEW_SEARCH,
  CASE_VIEW_SA_HOME,
  CASE_VIEW_SCA_HOME,
  CASE_VIEW_URA_HOME,
  REVIEW_WAITING_CASE_DOWNLOAD,
  REVIEW_WAITING_FILE_UPLOAD,
  REVIEW_WAITING_FILE_DOWNLOAD,
  REVIEW_WAITING_FILE_DELETE,
  CASE_VIEW_CHANGE_TYPE
} from './constants'

/**畫面初始 */
export function getCaseViewHomeData() {
  return $axios.post(CASE_VIEW_HOME, {})
}
/** 搜尋案件 */
export function queryCaseView(param) {
  return $axios.post(CASE_VIEW_SEARCH, param)
}
/** 服務申辦(SA)案件檢視-初始畫面 */
export function getCaseViewSaHomeData(param) {
  return $axios.post(CASE_VIEW_SA_HOME, param)
}

/** 動態流程進關 */
export function doDynamicFlow(param) {
  return $axios.post(
    '/backend/caseHandle/pendingReview/doDynamicFlowAction',
    param
  )
}

/** 服務案件新增(SCA)案件檢視-初始畫面 */
export function getCaseViewScaHomeData(param) {
  return $axios.post(CASE_VIEW_SCA_HOME, param)
}
/** 系統權限申請(URA)案件檢視-初始畫面 */
export function getCaseViewUraHomeData(param) {
  return $axios.post(CASE_VIEW_URA_HOME, param)
}
/** 改變狀態 */
export function getCaseViewChangeType(param) {
  return $axios.post(CASE_VIEW_CHANGE_TYPE, param)
}
/** 檔案下載 */
export function getDownloadFile(param) {
  return $axios.post(REVIEW_WAITING_CASE_DOWNLOAD, param, {
    responseType: 'arraybuffer'
  })
}
/** 附件上傳 */
export function getFileUpload(param) {
  return $axios.post(
    REVIEW_WAITING_FILE_UPLOAD,
    jsonToFormData(param, ['file'])
  )
}
/** 附件下載 */
export function getFileDownload(param) {
  return $axios.post(REVIEW_WAITING_FILE_DOWNLOAD, param, {
    responseType: 'arraybuffer'
  })
}
/** 附件刪除 */
export function getFileDelete(param) {
  return $axios.post(REVIEW_WAITING_FILE_DELETE, param)
}
