/** 分類維護 */
/** 初始畫面 */
export const CLASSIFY_MANAGEMENT_HOME = '/backend/classManagement/homeAction'
/** 查詢分類 */
export const CLASSIFY_MANAGEMENT_SEARCH = '/backend/classManagement/queryAction'
/** 新增＆維護分類初始畫面 */
export const CLASSIFY_MANAGEMENT_ADD_HOME =
  '/backend/classManagement/editHomeAction'
/** 新增&維護分類 */
export const CLASSIFY_MANAGEMENT_ADD = '/backend/classManagement/editAction'
/** 刪除分類 */
export const CLASSIFY_MANAGEMENT_DELETE =
  '/backend/classManagement/deleteAction'
/** 分類上下架 */
export const CLASSIFY_MANAGEMENT_SHOW_OR_NOT =
  '/backend/classManagement/onOffAction'
/** 取得次類別 */
export const CLASSIFY_GET_SUBCLASS =
  '/backend/classManagement/subClassQueryAction'

/** 帳號權限管理 */
/** 初始畫面 */
export const ACCOUNT_MANAGEMENT_HOME = '/backend/accountManagement/homeAction'
/** 搜尋帳號 */
export const ACCOUNT_MANAGEMENT_SEARCH =
  '/backend/accountManagement/queryAction'
/** 帳號維護初始畫面 */
export const ACCOUNT_MANAGEMENT_EDIT_HOME =
  '/backend/accountManagement/editHomeAction'
/** 帳號維護儲存 */
export const ACCOUNT_MANAGEMENT_EDIT_SAVE =
  '/backend/accountManagement/editAction'
/** 刪除帳號 */
export const ACCOUNT_DELETE = '/backend/accountManagement/deleteAction'

/** 常見問題管理 */
/** 初始畫面 */
export const QUESTION_MANAGEMENT_HOME = '/backend/questionMaintain/homeAction'
/** 搜尋問題 */
export const QUESTION_MANAGEMENT_SEARCH =
  '/backend/questionMaintain/queryAction'
/** 問題維護初始畫面 */
export const QUESTION_MANAGEMENT_EDIT_HOME =
  '/backend/questionMaintain/editHomeAction'
/** 問題維護儲存 */
export const QUESTION_MANAGEMENT_EDIT_SAVE =
  '/backend/questionMaintain/editAction'
/** 問題上下架 */
export const QUESTION_MANAGEMENT_SHOW_OR_NOT =
  '/backend/questionMaintain/onOffAction'
/** 問題刪除 */
export const QUESTION_DELETE = '/backend/questionMaintain/deleteAction'

/** 機關科室管理 */
/** 初始畫面 */
export const ORGAN_UNIT_MANAGEMENT_HOME =
  '/backend/organUnitManagement/homeAction'
/** 機關科室查詢 */
export const ORGAN_UNIT_MANAGEMENT_SEARCH =
  '/backend/organUnitManagement/queryAction'
/** 新增＆維護機關科室初始畫面 */
export const ORGAN_UNIT_MANAGEMENT_EDIT_HOME =
  '/backend/organUnitManagement/editHomeAction'
/** 新增＆維護機關科室 */
export const ORGAN_UNIT_MANAGEMENT_EDIT =
  '/backend/organUnitManagement/editAction'

/** 服務案件清單 */
/** 初始畫面 */
export const CASE_MANAGEMENT_HOME = '/backend/CaseManagement/homeAction'
/** 權責機關取服務管理者 */
export const CASE_MANAGEMENT_GET_SERVICE_MANAGER =
  '/backend/CaseManagement/caseManagerComboBoxQueryAction'
/** 服務案件清單-案件排序-初始畫面 */
export const CASE_MANAGEMENT_ORDER_HOME =
  '/backend/CaseManagement/caseOrderHomeAction'
/** 服務案件清單-案件排序-修改 */
export const CASE_MANAGEMENT_ORDER_SAVE =
  '/backend/CaseManagement/caseOrderSaveAction'
/** 服務案件清單-案件維護-儲存 */
export const CASE_MANAGEMENT_CASE_SAVE =
  '/backend/CaseManagement/caseSaveAction'
/** 服務案件清單-案件刪除 */
export const CASE_MANAGEMENT_CASE_DELETE =
  '/backend/CaseManagement/deleteAction'
/** 服務案件清單-案件維護-初始畫 */
export const CASE_MANAGEMENT_CASE_EDIT =
  '/backend/CaseManagement/editHomeAction'
/** 後台案件維護管理-案件查詢 */
export const CASE_MANAGEMENT_CASE_QUERY = '/backend/CaseManagement/queryAction'
/** 服務案件清單-案件狀態更改 (上架/下架/立即受理) */
export const CASE_MANAGEMENT_CASE_STATUS_UPDATE =
  '/backend/CaseManagement/statusUpdateAction'

/** 網路申辦 */
/** 網路申辦-受理期間設定-初始畫面 */
export const INTERNET_APPLY_ACCEPT_DATE_HOME =
  '/backend/internetApply/acceptDate/homeAction'
/** 網路申辦-受理機關設定-受理區機關查詢 */
export const INTERNET_APPLY_ACCEPT_SET_AREA_QUERY =
  '/backend/internetApply/acceptSet/areaQueryAction'
/** 網路申辦-受理機關設定-受理區機關新增 */
export const INTERNET_APPLY_ACCEPT_SET_AREA_SAVE =
  '/backend/internetApply/acceptSet/areaSaveAction'
/** 網路申辦-受理機關設定-初始畫面 */
export const INTERNET_APPLY_ACCEPT_SET_HOME =
  '/backend/internetApply/acceptSet/homeAction'
/** 網路申辦-受理機關設定-承辦人查詢 */
export const INTERNET_APPLY_ACCEPT_SET_OFFICER_QUERY =
  '/backend/internetApply/acceptSet/officerQueryAction'
/** 網路申辦-受理機關設定-承辦人新增 */
export const INTERNET_APPLY_ACCEPT_SET_OFFICER_SAVE =
  '/backend/internetApply/acceptSet/officerSaveAction'
/** 網路申辦-受理機關設定-受理機關查詢 */
export const INTERNET_APPLY_ACCEPT_SET_UNIT_QUERY =
  '/backend/internetApply/acceptSet/unitQueryAction'
/** 網路申辦-受理機關設定-受理機關新增 */
export const INTERNET_APPLY_ACCEPT_SET_UNIT_SAVE =
  '/backend/internetApply/acceptSet/unitSaveAction'
/** 網路申辦-說明附件上傳 */
export const INTERNET_APPLY_DESCRIPTION_ATTACHMENT_UPLOAD =
  '/backend/internetApply/description/attachmentUploadAction'
/** 網路申辦-申請說明資料刪除 */
export const INTERNET_APPLY_DESCRIPTION_DELETE =
  '/backend/internetApply/description/deleteAction'
/** 網路申辦-網路申請說明-初始畫面 */
export const INTERNET_APPLY_DESCRIPTION_HOME =
  '/backend/internetApply/description/homeAction'
/** 網路申辦-申請說明資料儲存 */
export const INTERNET_APPLY_DESCRIPTION_SAVE =
  '/backend/internetApply/description/saveAction'
/** 網路申辦-表單設定-欄位維護-初始畫面 */
export const INTERNET_APPLY_FORM_SET_COLUMN_HOME =
  '/backend/internetApply/formSet/columnHomeAction'
/** 網路申辦-表單設定-欄位群組維護-進版儲存 */
export const INTERNET_APPLY_FORM_SET_GROUP_COLUMN_SAVE =
  '/backend/internetApply/formSet/groupColumnSaveAction'
/** 網路申辦-表單設定-群組維護-初始畫面 */
export const INTERNET_APPLY_FORM_SET_GROUP_HOME =
  '/backend/internetApply/formSet/groupHomeAction'
/** 網路申辦-表單設定-初始畫面 */
export const INTERNET_APPLY_FORM_SET_HOME =
  '/backend/internetApply/formSet/homeAction'
/** 網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單 */
export const INTERNET_APPLY_FORM_SET_MY_DATA_COLUMN_COMBO_BOX =
  '/backend/internetApply/formSet/myDataColumnComboBoxAction'
/** 網路申辦-表單設定-MYDAYA維護-刪除 */
export const INTERNET_APPLY_FORM_SET_MY_DATA_DELETE =
  '/backend/internetApply/formSet/mydataDeleteAction'
/** 網路申辦-表單設定-MYDAYA維護-刪除確認 */
export const INTERNET_APPLY_FORM_SET_MY_DATA_DELETE_CHECK =
  '/backend/internetApply/formSet/mydataDeleteCheckAction'
/** 網路申辦-表單設定-MYDAYA維護-初始畫面 */
export const INTERNET_APPLY_FORM_SET_MY_DATA_HOME =
  '/backend/internetApply/formSet/mydataHomeAction'
/** 網路申辦-表單設定-MYDAYA維護-資料集新增 */
export const INTERNET_APPLY_FORM_SET_MY_DATA_SAVE =
  '/backend/internetApply/formSet/mydataSaveAction'
/** 網路申辦-初始畫面 */
export const INTERNET_APPLY_HOME = '/backend/internetApply/homeAction'
/** 網路申辦-身分驗證設定-初始畫面 */
export const INTERNET_APPLY_IDENTITY_VERIFY_HOME =
  '/backend/internetApply/identityVerify/homeAction'
/** 網路申辦-限辦期限設定-初始畫面 */
export const INTERNET_APPLY_LIMIT_PERIOD_HOME =
  '/backend/internetApply/limitPeriod/homeAction'
/** 網路申辦-案件儲存 */
export const INTERNET_APPLY_SAVE = '/backend/internetApply/saveAction'
/** 網路申辦-服務宣告設定-初始畫面 */
export const INTERNET_APPLY_SERVICE_HTML_HOME =
  '/backend/internetApply/serviceHtml/homeAction'

/** 服務申請 */
/** 初始畫面 */
export const SERVICE_APPLICATION_HOME = '/backend/serviceApply/homeAction'
/** 服務查詢 */
export const SERVICE_APPLICATION_SEARCH = '/backend/serviceApply/queryAction'
/** 服務案件申請初始畫面 */
export const SERVICE_APPLICATION_ADD_HOME =
  '/backend/serviceApply/serviceCase/homeAction'
/** 服務案件申請儲存 */
export const SERVICE_APPLICATION_ADD_SAVE =
  '/backend/serviceApply/serviceCase/editAction'
/** 權限開通申請初始畫面 */
export const AUTHORITY_APPLICATION_HOME =
  '/backend/serviceApply/permissionActive/homeAction'
/** 權限開通申請儲存 */
export const AUTHORITY_APPLICATION_SAVE =
  '/backend/serviceApply/permissionActive/saveAction'

/** 熱門搜尋 */
/** 初始畫面 */
export const POPULAR_SEARCH_MANAGEMENT_HOME = '/backend/hotSearch/homeAction'
/** 刪除熱門搜尋 */
export const POPULAR_SEARCH_DELETE = '/backend/hotSearch/deleteAction'
/** 熱門搜尋編輯儲存 */
export const POPULAR_SEARCH_EDIT_SAVE = '/backend/hotSearch/saveAction'
/** 熱門搜尋排序儲存 */
export const POPULAR_SEARCH_ORDER_SAVE = '/backend/hotSearch/saveAllAction'

/** 任務及公告管理 */
/** 初始畫面 */
export const ANNOUNCEMENT_MANAGEMENT_HOME = '/backend/taskMaintain/homeAction'
/** 任務及公告查詢 */
export const ANNOUNCEMENT_SEARCH = '/backend/taskMaintain/queryAction'
/** 新增＆維護任務及公告初始畫面 */
export const ANNOUNCEMENT_MANAGEMENT_ADD_HOME =
  '/backend/taskMaintain/editHomeAction'
/** 新增＆維護任務及公告 */
export const ANNOUNCEMENT_MANAGEMENT_ADD_SAVE =
  '/backend/taskMaintain/saveAction'
/** 任務及公告上下架 */
export const ANNOUNCEMENT_MANAGEMENT_SHOW_OR_NOT =
  '/backend/taskMaintain/onOffAction'
/** 刪除任務及公告 */
export const ANNOUNCEMENT_DELETE = '/backend/taskMaintain/deleteAction'
/** 機關帶出案件 */
export const ORGAN_GET_CASE = '/backend/taskMaintain/organCaseAction'
/** 活動項目帶出城市幣 */
export const ACTIVITY_GET_POINT = '/backend/taskMaintain/cityCoinSearchAction'
/** 附件下載 */
export const ANNOUNCEMENT_MANAGEMENT_DOWNLOAD_FILE =
  '/backend/taskMaintain/downloadAction'

/**推播訊息管理*/
/** 初始畫面 */
export const PUSH_MSG_MANAGEMENT_HOME = '/backend/pushMsgManagement/homeAction'
/** 編輯初始畫面 */
export const PUSH_MSG_MANAGEMENT_EDIT_HOME =
  '/backend/pushMsgManagement/queryAction'
/** 儲存推播訊息 */
export const PUSH_MSG_MANAGEMENT_SAVE_EDIT =
  '/backend/pushMsgManagement/editAction'
/** 刪除推播訊息 */
export const PUSH_MSG_MANAGEMENT_DELETE =
  '/backend/pushMsgManagement/deleteAction'
/** 服務管理-顯示推播訊息 */
export const PUSH_MSG_SHOW = '/backend/pushMsgManagement/queryCaseSetAction'
/** 服務管理-設定開關 */
export const PUSH_MSG_EDIT_OPEN = '/backend/pushMsgManagement/editCaseSetAction'

/** 表單管理 */
/** 查詢表單 */
export const TEMPLATE_MANAGEMENT_QUERY = '/backend/template/queryAction'
/** 新增表單 */
export const TEMPLATE_MANAGEMENT_ADD = '/backend/template/addAction'
/** 編輯表單 */
export const TEMPLATE_MANAGEMENT_EDIT = '/backend/template/updateAction'
/** 刪除表單 */
export const TEMPLATE_MANAGEMENT_DELETE = '/backend/template/deleteAction'
/** 表單明細 */
export const TEMPLATE_MANAGEMENT_DETAIL = '/backend/template/viewAction'
