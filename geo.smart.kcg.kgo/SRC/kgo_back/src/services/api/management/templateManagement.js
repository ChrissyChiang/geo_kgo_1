import { $axios } from '../../axios'
import {
  TEMPLATE_MANAGEMENT_QUERY,
  TEMPLATE_MANAGEMENT_ADD,
  TEMPLATE_MANAGEMENT_EDIT,
  TEMPLATE_MANAGEMENT_DELETE,
  TEMPLATE_MANAGEMENT_DETAIL
} from './constants'

/** 查詢表單 */
export function getTemplateManagementQuery(param) {
  return $axios.post(TEMPLATE_MANAGEMENT_QUERY, param)
}
/** 新增表單 */
export function getTemplateManagementAdd(param) {
  return $axios.post(TEMPLATE_MANAGEMENT_ADD, param)
}
/** 編輯表單 */
export function getTemplateManagementEdit(param) {
  return $axios.post(TEMPLATE_MANAGEMENT_EDIT, param)
}
/** 刪除表單 */
export function getTemplateManagementDelete(param) {
  return $axios.post(TEMPLATE_MANAGEMENT_DELETE, param)
}
/** 表單明細 */
export function getTemplateManagementDetail(param) {
  return $axios.post(TEMPLATE_MANAGEMENT_DETAIL, param)
}
