import { $axios } from '../../axios'
import {
  CLASSIFY_MANAGEMENT_HOME,
  CLASSIFY_MANAGEMENT_SEARCH,
  CLASSIFY_MANAGEMENT_ADD_HOME,
  CLASSIFY_MANAGEMENT_ADD,
  CLASSIFY_MANAGEMENT_DELETE,
  CLASSIFY_MANAGEMENT_SHOW_OR_NOT,
  CLASSIFY_GET_SUBCLASS
} from './constants'

/**畫面初始 */
export function getClassifyManagementHomeData() {
  return $axios.post(CLASSIFY_MANAGEMENT_HOME, {})
}
/** 查詢分類 */
export function queryClassify(param) {
  return $axios.post(CLASSIFY_MANAGEMENT_SEARCH, param)
}
/** 新增＆維護分類初始畫面 */
export function getAddClassifyManagementHomeData(param) {
  return $axios.post(CLASSIFY_MANAGEMENT_ADD_HOME, param)
}
/** 新增&維護分類 */
export function addClassify(param) {
  return $axios.post(CLASSIFY_MANAGEMENT_ADD, param)
}
/** 刪除分類 */
export function deleteClassify(param) {
  return $axios.post(CLASSIFY_MANAGEMENT_DELETE, param)
}
/** 上下架分類 */
export function ShowClassify(param) {
  return $axios.post(CLASSIFY_MANAGEMENT_SHOW_OR_NOT, param)
}
/** 取得次分類 */
export function getSubClass(param) {
  return $axios.post(CLASSIFY_GET_SUBCLASS, param)
}
