/** 常用詞庫設定 */
/** 初始畫面 */
export const VOCABULARY_SETTING_HOME = '/backend/thesaurusMaintain/homeAction'
/** 查詢詞彙 */
export const VOCABULARY_SETTING_SEARCH =
  '/backend/thesaurusMaintain/queryAction'
/** 新增＆維護詞彙初始畫面 */
export const VOCABULARY_SETTING_EDIT_HOME =
  '/backend/thesaurusMaintain/editHomeAction'
/** 新增＆維護詞彙 */
export const VOCABULARY_SETTING_EDIT = '/backend/thesaurusMaintain/editAction'
/** 刪除詞彙 */
export const VOCABULARY_SETTING_DELETE =
  '/backend/thesaurusMaintain/deleteAction'
/** 待簽收匣 */
/** 初始畫面 */
export const SIGN_WAITING_HONE = '/backend/caseHandle/pendingSign/homeAction'
/** 案件查詢 */
export const SIGN_WAITING_CASE_SEARCH = '/backend/caseHandle/pendingSign/query'
/** 簽收 */
export const SIGN_WAITING_CASE_SIGN = '/backend/caseHandle/pendingSign/accept'
/** 批次簽收 */
export const SIGN_WAITING_CASE_SIGN_MANY =
  '/backend/caseHandle/pendingSign/batchAccept'
/** 批次分文 */
export const SIGN_WAITING_CASE_DISPATCH_MANY =
  '/backend/caseHandle/pendingSign/dispatch'
/** 待審核匣 */
/** 初始畫面 */
export const REVIEW_WAITING_HOME =
  '/backend/caseHandle/pendingReview/homeAction'
/** 案件查詢 */
export const REVIEW_WAITING_CASE_SEARCH =
  '/backend/caseHandle/pendingReview/query'
/** 結案 */
export const REVIEW_WAITING_END_CASE =
  '/backend/caseHandle/pendingReview/complete'
/** 取消簽收-初始畫面 */
export const REVIEW_WAITING_CANCEL_HOME =
  '/backend/caseHandle/pendingReview/cancelAccept/homeAction'
/** 取消簽收 */
export const REVIEW_WAITING_CANCEL =
  '/backend/caseHandle/pendingReview/cancelAccept'
/** 補正作業-初始畫面 */
export const REVIEW_WAITING_CORRECTION_HOME =
  '/backend/caseHandle/pendingReview/correctHome'
/** 補正作業 */
export const REVIEW_WAITING_CORRECTION =
  '/backend/caseHandle/pendingReview/correctUpdate'
/** 訊息發送 */
export const REVIEW_WAITING_SENDING_MESSAGE =
  '/backend/caseHandleNotify/pendingReview/notify'
/** 訊息發送-歷程 */
export const REVIEW_WAITING_SENDING_MESSAGE_HISTORY =
  '/backend/caseHandleNotify/pendingReview/notifyHistory'
/** 新增參考文件及備註 */
export const REVIEW_WAITING_ADD_FILE =
  '/backend/caseHandle/pendingReview/addNote'
/** 刪除參考文件及備註 */
export const REVIEW_WAITING_DELETE_FILE =
  '/backend/caseHandle/pendingReview/deleteNote'
/** 檢核-初始畫面 */
export const REVIEW_WAITING_CASE_SIGN_HOME =
  '/backend/caseHandle/pendingReview/signSaCase/homeAction'
/** 檢核-檔案下載 */
export const REVIEW_WAITING_CASE_DOWNLOAD =
  '/backend/caseHandle/caseView/saCase/downloadAction'
/** 附件上傳 */
export const REVIEW_WAITING_FILE_UPLOAD =
  '/backend/caseHandle/pendingReview/uploadFile'
/** 附件下載 */
export const REVIEW_WAITING_FILE_DOWNLOAD =
  '/backend/caseHandle/pendingReview/downloadFile'
/** 附件刪除 */
export const REVIEW_WAITING_FILE_DELETE =
  '/backend/caseHandle/pendingReview/deleteFile'
/** 已審核匣 */
/** 初始畫面 */
export const REVIEW_ALREADY_HOME = '/backend/caseHandle/reviewed/homeAction'
/** 查詢案件 */
export const REVIEW_ALREADY_SEARCH = '/backend/caseHandle/reviewed/queryAction'
/** 案件檢視 */
/** 初始畫面 */
export const CASE_VIEW_HOME = '/backend/caseHandle/caseView/homeAction'
/** 案件查詢 */
export const CASE_VIEW_SEARCH = '/backend/caseHandle/caseView/queryAction'
/** 服務申辦(SA)案件檢視-初始畫面 */
export const CASE_VIEW_SA_HOME =
  '/backend/caseHandle/caseView/saCase/homeAction2'
/** 服務案件新增(SCA)案件檢視-初始畫面 */
export const CASE_VIEW_SCA_HOME =
  '/backend/caseHandle/caseView/scaCase/homeAction'
/** 系統權限申請(URA)案件檢視-初始畫面 */
export const CASE_VIEW_URA_HOME =
  '/backend/caseHandle/caseView/uraCase/homeAction'
/** 改變狀態 */
export const CASE_VIEW_CHANGE_TYPE =
  '/backend/caseHandle/caseView/saCase/updateStatus'
/** 案件異動管理 */
/** 案件查詢 */
export const CASE_CHANGE_SEARCH = '/backend/caseHandle/caseUpdate/queryAction'
/** 變更承辦人 */
export const CASE_CHANGE_TAKER = '/backend/caseHandle/caseUpdate/updateAction'
/** 權限申請 */
/** 權限申請-檢視 */
export const AUTHORITY_APPLY_VIEW =
  '/backend/serviceApply/ura/pendingReview/view'
/** 待審核匣-主管一同意到主管二 */
export const AUTHORITY_APPLY_M1_APPROVE_TO_M2 =
  '/backend/serviceApply/ura/pendingReview/m1ApproveToM2'
/** 待審核匣-主管一同意到機關管理者 */
export const AUTHORITY_APPLY_M1_APPROVE_TO_OM =
  '/backend/serviceApply/ura/pendingReview/m1ApproveToOM'
/** 待審核匣-主管二同意結束 */
export const AUTHORITY_APPLY_M2_APPROVE_TO_END =
  '/backend/serviceApply/ura/pendingReview/m2ApproveEnd'
/** 待審核匣-主管二同意到機關管理者 */
export const AUTHORITY_APPLY_M2_APPROVE_TO_OM =
  '/backend/serviceApply/ura/pendingReview/m2ApproveToOM'
/** 待審核匣-不同意 */
export const AUTHORITY_APPLY_NOT_APPROVE =
  '/backend/serviceApply/ura/pendingReview/notApprove'
/** 待審核匣-機關管理者同意結束 */
export const AUTHORITY_APPLY_OM_APPROVE_TO_END =
  '/backend/serviceApply/ura/pendingReview/omApproveEnd'
