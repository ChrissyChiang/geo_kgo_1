import { $axios } from '../../axios'
import {
  POPULAR_SEARCH_MANAGEMENT_HOME,
  POPULAR_SEARCH_DELETE,
  POPULAR_SEARCH_EDIT_SAVE,
  POPULAR_SEARCH_ORDER_SAVE
} from './constants'

/**畫面初始 */
export function getPopularSearchManagementHomeData() {
  return $axios.post(POPULAR_SEARCH_MANAGEMENT_HOME, {})
}
/** 刪除熱門搜尋 */
export function delPopularSearch(param) {
  return $axios.post(POPULAR_SEARCH_DELETE, param)
}
/** 熱門搜尋編輯儲存 */
export function savePopularSearch(param) {
  return $axios.post(POPULAR_SEARCH_EDIT_SAVE, param)
}
/** 熱門搜尋排序儲存 */
export function savePopularSearchOrder(param) {
  return $axios.post(POPULAR_SEARCH_ORDER_SAVE, param)
}
