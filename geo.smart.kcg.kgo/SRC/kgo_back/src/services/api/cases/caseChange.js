import { $axios } from '../../axios'
import { CASE_CHANGE_SEARCH, CASE_CHANGE_TAKER } from './constants'

/** 案件查詢 */
export function queryCaseChangeCase(param) {
  return $axios.post(CASE_CHANGE_SEARCH, param)
}
/** 變更承辦人 */
export function getChangeTaker(param) {
  return $axios.post(CASE_CHANGE_TAKER, param)
}
