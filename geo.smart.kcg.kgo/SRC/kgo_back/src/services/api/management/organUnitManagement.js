import { $axios } from '../../axios'
import {
  ORGAN_UNIT_MANAGEMENT_HOME,
  ORGAN_UNIT_MANAGEMENT_SEARCH,
  ORGAN_UNIT_MANAGEMENT_EDIT_HOME,
  ORGAN_UNIT_MANAGEMENT_EDIT
} from './constants'

/**畫面初始 */
export function getOrganUnitManagementHomeData() {
  return $axios.post(ORGAN_UNIT_MANAGEMENT_HOME, {})
}
/** 機關科室查詢 */
export function queryOrganUnit(param) {
  return $axios.post(ORGAN_UNIT_MANAGEMENT_SEARCH, param)
}
/** 新增＆維護機關科室初始畫面 */
export function editOrganUnitManagementHomeData(param) {
  return $axios.post(ORGAN_UNIT_MANAGEMENT_EDIT_HOME, param)
}
/** 新增＆維護機關科室 */
export function editOrganUnitManagement(param) {
  return $axios.post(ORGAN_UNIT_MANAGEMENT_EDIT, param)
}
