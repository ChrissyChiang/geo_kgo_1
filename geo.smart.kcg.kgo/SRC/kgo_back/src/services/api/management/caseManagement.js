import { $axios } from '../../axios'
import {
  CASE_MANAGEMENT_HOME,
  CASE_MANAGEMENT_GET_SERVICE_MANAGER,
  CASE_MANAGEMENT_ORDER_HOME,
  CASE_MANAGEMENT_ORDER_SAVE,
  CASE_MANAGEMENT_CASE_QUERY,
  CASE_MANAGEMENT_CASE_SAVE,
  CASE_MANAGEMENT_CASE_DELETE,
  CASE_MANAGEMENT_CASE_EDIT,
  CASE_MANAGEMENT_CASE_STATUS_UPDATE
} from './constants'

/** 畫面初始 */
export function getCassManagementHomeData() {
  return $axios.post(CASE_MANAGEMENT_HOME, {})
}
/** 權責機關取服務管理者 */
export function getCaseManagementGetServiceManager(param) {
  return $axios.post(CASE_MANAGEMENT_GET_SERVICE_MANAGER, param)
}
/**服務案件清單-案件排序-初始畫面 */
export function getCassManagementOrderData(param) {
  return $axios.post(CASE_MANAGEMENT_ORDER_HOME, param)
}

/** 服務案件清單-案件排序-修改 */
export function saveCassManagementOrder(param) {
  return $axios.post(CASE_MANAGEMENT_ORDER_SAVE, param)
}

/** 後台案件維護管理-案件查詢 */
export function queryCassManagementOrder(param) {
  return $axios.post(CASE_MANAGEMENT_CASE_QUERY, param)
}

/** 服務案件清單-案件維護-儲存 */
export function saveCassManagement(param) {
  return $axios.post(CASE_MANAGEMENT_CASE_SAVE, param)
}

/** 服務案件清單-案件刪除 */
export function deleteCassManagement(param) {
  return $axios.post(CASE_MANAGEMENT_CASE_DELETE, param)
}

/** 服務案件清單-案件維護-初始畫面 */
export function editCassManagementHome(param) {
  return $axios.post(CASE_MANAGEMENT_CASE_EDIT, param)
}

/** 服務案件清單-案件狀態更改 (上架/下架/立即受理) */
export function cassManagementStatusUpdate(param) {
  return $axios.post(CASE_MANAGEMENT_CASE_STATUS_UPDATE, param)
}
