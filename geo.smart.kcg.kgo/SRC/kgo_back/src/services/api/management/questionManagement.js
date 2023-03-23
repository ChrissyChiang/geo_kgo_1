import { $axios } from '../../axios'
import {
  QUESTION_MANAGEMENT_HOME,
  QUESTION_MANAGEMENT_SEARCH,
  QUESTION_MANAGEMENT_EDIT_HOME,
  QUESTION_MANAGEMENT_EDIT_SAVE,
  QUESTION_MANAGEMENT_SHOW_OR_NOT,
  QUESTION_DELETE
} from './constants'

/**畫面初始 */
export function getQuestionManagementHomeData() {
  return $axios.post(QUESTION_MANAGEMENT_HOME, {})
}
/** 搜尋問題 */
export function queryQuestion(param) {
  return $axios.post(QUESTION_MANAGEMENT_SEARCH, param)
}
/** 新增＆維護問題初始畫面 */
export function getEditQuestionManagementHomeData(param) {
  return $axios.post(QUESTION_MANAGEMENT_EDIT_HOME, param)
}
/** 新增＆維護問題 */
export function editQuestion(param) {
  return $axios.post(QUESTION_MANAGEMENT_EDIT_SAVE, param)
}
/** 問題上下架 */
export function questionOnOrOff(param) {
  return $axios.post(QUESTION_MANAGEMENT_SHOW_OR_NOT, param)
}
/** 刪除問題 */
export function deleteQuestion(param) {
  return $axios.post(QUESTION_DELETE, param)
}
