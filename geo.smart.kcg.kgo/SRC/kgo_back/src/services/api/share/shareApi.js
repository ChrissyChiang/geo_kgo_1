import { $axios } from '../../axios'
import {
  ORGAN_GET_UNIT,
  ORGAN_AND_UNIT_GET_TAKER,
  AREA_ORGAN,
  ZIP_HOME,
  ORGAN_SELECT_HOME,
  ORGAN_UNIT_USER_SELECT
} from './constants'

/** 機關取得科室 */
export function getUnit(param) {
  return $axios.post(ORGAN_GET_UNIT, param)
}
/** 機關＆科室取得承辦人 */
export function getTaker(param) {
  return $axios.post(ORGAN_AND_UNIT_GET_TAKER, param)
}
/** 區機關列表-區機關選擇-初始畫面 */
export function getAreaOrgan(param) {
  return $axios.post(AREA_ORGAN, param)
}
/** 區機關列表-區機關選擇-鄉鎮選擇-初始畫面 */
export function getZipHome(param) {
  return $axios.post(ZIP_HOME, param)
}

/** 機關列表 */
export function getOrganSelectHome(param) {
  return $axios.post(ORGAN_SELECT_HOME, param)
}

/** 取得所有機關列表 */
export function getAllOrganSelectHome(param) {
  return $axios.post('/backend/common/organComboBoxQueryAction', param)
}

/** 人員清單Popup -查詢 */
export function getOrganUintUserSelectHome(param) {
  return $axios.post(ORGAN_UNIT_USER_SELECT, param)
}
