import { $axios } from '../../axios'
import { MENU } from './constants'

/** 取得 主選單 */
export function getMenu() {
  return $axios.post(MENU, {})
}
