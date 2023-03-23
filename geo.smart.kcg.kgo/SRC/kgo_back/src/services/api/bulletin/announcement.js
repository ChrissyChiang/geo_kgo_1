import { $axios } from '../../axios'
import { ANNOUNCEMENT_HOME } from './constants'

/**畫面初始 */
export function getAnnouncementHomeData() {
  return $axios.post(ANNOUNCEMENT_HOME, {})
}
