import { $axios } from '../../axios'
import { jsonToFormData } from '@/utils/form'
import {
  INTERNET_APPLY_ACCEPT_DATE_HOME,
  INTERNET_APPLY_ACCEPT_SET_AREA_QUERY,
  INTERNET_APPLY_ACCEPT_SET_AREA_SAVE,
  INTERNET_APPLY_ACCEPT_SET_OFFICER_QUERY,
  INTERNET_APPLY_ACCEPT_SET_OFFICER_SAVE,
  INTERNET_APPLY_ACCEPT_SET_UNIT_QUERY,
  INTERNET_APPLY_ACCEPT_SET_UNIT_SAVE,
  INTERNET_APPLY_DESCRIPTION_ATTACHMENT_UPLOAD,
  INTERNET_APPLY_DESCRIPTION_DELETE,
  INTERNET_APPLY_DESCRIPTION_HOME,
  INTERNET_APPLY_DESCRIPTION_SAVE,
  INTERNET_APPLY_FORM_SET_COLUMN_HOME,
  INTERNET_APPLY_FORM_SET_GROUP_COLUMN_SAVE,
  INTERNET_APPLY_FORM_SET_GROUP_HOME,
  INTERNET_APPLY_FORM_SET_HOME,
  INTERNET_APPLY_FORM_SET_MY_DATA_COLUMN_COMBO_BOX,
  INTERNET_APPLY_FORM_SET_MY_DATA_DELETE,
  INTERNET_APPLY_FORM_SET_MY_DATA_DELETE_CHECK,
  INTERNET_APPLY_FORM_SET_MY_DATA_HOME,
  INTERNET_APPLY_FORM_SET_MY_DATA_SAVE,
  INTERNET_APPLY_HOME,
  INTERNET_APPLY_IDENTITY_VERIFY_HOME,
  INTERNET_APPLY_LIMIT_PERIOD_HOME,
  INTERNET_APPLY_SAVE,
  INTERNET_APPLY_SERVICE_HTML_HOME,
  INTERNET_APPLY_ACCEPT_SET_HOME
} from './constants'

/** 網路申辦-受理期間設定-初始畫面 */
export function getInternetApplyAcceptDateHome(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_DATE_HOME, param)
}

/** 網路申辦-受理機關設定-受理區機關查詢 */
export function queryInternetApplyAcceptSetArea(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_AREA_QUERY, param)
}

/** 網路申辦-受理機關設定-受理區機關新增 */
export function saveInternetApplyAcceptSetArea(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_AREA_SAVE, param)
}

/** 網路申辦-受理機關設定-初始畫面 */
export function getInternetApplyAcceptSetHome(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_HOME, param)
}

/** 網路申辦-受理機關設定-承辦人查詢 */
export function queryInternetApplyAcceptSetOfficer(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_OFFICER_QUERY, param)
}

/** 網路申辦-受理機關設定-承辦人新增 */
export function saveInternetApplyAcceptSetOfficer(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_OFFICER_SAVE, param)
}

/** 網路申辦-受理機關設定-受理機關查詢 */
export function queryInternetApplyAcceptSetUnit(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_UNIT_QUERY, param)
}

/** 網路申辦-受理機關設定-受理機關新增 */
export function saveInternetApplyAcceptSetUnit(param) {
  return $axios.post(INTERNET_APPLY_ACCEPT_SET_UNIT_SAVE, param)
}

/** 網路申辦-說明附件上傳 */
export function uploadInternetApplyDescriptionAttachment(param) {
  return $axios.post(
    INTERNET_APPLY_DESCRIPTION_ATTACHMENT_UPLOAD,
    jsonToFormData(param, ['file'])
  )
}

/** 網路申辦-申請說明資料刪除 */
export function deleteInternetApplyDescription(param) {
  return $axios.post(INTERNET_APPLY_DESCRIPTION_DELETE, param)
}

/** 網路申辦-網路申請說明-初始畫面 */
export function getInternetApplyDescriptionHome(param) {
  return $axios.post(INTERNET_APPLY_DESCRIPTION_HOME, param)
}

/** 網路申辦-申請說明資料儲存 */
export function saveInternetApplyDescription(param) {
  return $axios.post(INTERNET_APPLY_DESCRIPTION_SAVE, param)
}

/** 網路申辦-表單設定-欄位維護-初始畫面 */
export function getInternetApplyApplyFormSetColumnHome(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_COLUMN_HOME, param)
}

/** 網路申辦-表單設定-欄位群組維護-進版儲存 */
export function saveInternetApplyApplyFormSetGroupColumn(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_GROUP_COLUMN_SAVE, param)
}

/** 網路申辦-表單設定-群組維護-初始畫面 */
export function getInternetApplyApplyFormSetGroupHome(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_GROUP_HOME, param)
}

/** 網路申辦-表單設定-初始畫面 */
export function getInternetApplyFormSetHome(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_HOME, param)
}

/** 網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單 */
export function getInternetApplyFormSetMyDataColumnCombobox(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_MY_DATA_COLUMN_COMBO_BOX, param)
}

/** 網路申辦-表單設定-MYDAYA維護-刪除 */
export function deleteInternetApplyFormSetMyData(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_MY_DATA_DELETE, param)
}

/** 網路申辦-表單設定-MYDAYA維護-刪除確認 */
export function deletecheckInternetApplyFormSetMyData(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_MY_DATA_DELETE_CHECK, param)
}

/** 網路申辦-表單設定-MYDAYA維護-初始畫面 */
export function getInternetApplyFormSetMyDataHome(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_MY_DATA_HOME, param)
}

/** 取得Mydata下拉式選單資料 */
export function getMydataComboBox(param) {
  return $axios.post(
    '/backend/internetApply/formSet/mydataComboBoxAction',
    param
  )
}

/** 網路申辦-表單設定-MYDAYA維護-資料集新增 */
export function saveInternetApplyFormSetMyDataHome(param) {
  return $axios.post(INTERNET_APPLY_FORM_SET_MY_DATA_SAVE, param)
}

/** 網路申辦-初始畫面 */
export function getInternetApplyFormHome(param) {
  return $axios.post(INTERNET_APPLY_HOME, param)
}

/** 網路申辦-身分驗證設定-初始畫面 */
export function getInternetApplyIdentityVerifyHome(param) {
  return $axios.post(INTERNET_APPLY_IDENTITY_VERIFY_HOME, param)
}

/** 網路申辦-限辦期限設定-初始畫面 */
export function getInternetApplyLimitPeriodHome(param) {
  return $axios.post(INTERNET_APPLY_LIMIT_PERIOD_HOME, param)
}

/** 網路申辦-案件儲存 */
export function saveInternetApply(param) {
  return $axios.post(INTERNET_APPLY_SAVE, param)
}

/** 網路申辦-服務宣告設定-初始畫面 */
export function getInternetApplyServiceHtml(param) {
  return $axios.post(INTERNET_APPLY_SERVICE_HTML_HOME, param)
}
