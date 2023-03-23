import { $axios } from '../../axios'
import { REVIEW_ALREADY_HOME, REVIEW_ALREADY_SEARCH } from './constants'

/**畫面初始 */
export function getReviewAlreadyHomeData() {
  return $axios.post(REVIEW_ALREADY_HOME, {})
}
/** 搜尋案件 */
export function queryReviewAlreadyCase(param) {
  return $axios.post(REVIEW_ALREADY_SEARCH, param)
}
