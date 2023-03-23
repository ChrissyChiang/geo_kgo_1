import { $axios } from '../../axios'
import {
  VOCABULARY_SETTING_HOME,
  VOCABULARY_SETTING_SEARCH,
  VOCABULARY_SETTING_EDIT_HOME,
  VOCABULARY_SETTING_EDIT,
  VOCABULARY_SETTING_DELETE
} from './constants'

/**畫面初始 */
export function getVocabularySettingHomeData() {
  return $axios.post(VOCABULARY_SETTING_HOME, {})
}
/** 搜尋詞彙 */
export function queryVocabulary(param) {
  return $axios.post(VOCABULARY_SETTING_SEARCH, param)
}
/** 新增＆編輯詞彙初始畫面 */
export function getEditVocabularySettingHomeData(param) {
  return $axios.post(VOCABULARY_SETTING_EDIT_HOME, param)
}
/** 新增＆編輯詞彙 */
export function editVocabulary(param) {
  return $axios.post(VOCABULARY_SETTING_EDIT, param)
}
/** 刪除詞彙 */
export function delVocabulary(param) {
  return $axios.post(VOCABULARY_SETTING_DELETE, param)
}
