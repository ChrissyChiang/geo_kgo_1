import { $axios } from '../../axios'
import {
  ANNOUNCEMENT_MANAGEMENT_HOME,
  ANNOUNCEMENT_SEARCH,
  ANNOUNCEMENT_MANAGEMENT_ADD_HOME,
  ANNOUNCEMENT_MANAGEMENT_ADD_SAVE,
  ANNOUNCEMENT_MANAGEMENT_SHOW_OR_NOT,
  ANNOUNCEMENT_DELETE,
  ORGAN_GET_CASE,
  ACTIVITY_GET_POINT,
  ANNOUNCEMENT_MANAGEMENT_DOWNLOAD_FILE
} from './constants'

/** 畫面初始 */
export function getAnnouncementManagementHomeData() {
  return $axios.post(ANNOUNCEMENT_MANAGEMENT_HOME, {})
}
/** 任務及公告查詢 */
export function queryAnnouncement(param) {
  return $axios.post(ANNOUNCEMENT_SEARCH, param)
}
/** 新增＆維護任務及公告初始畫面 */
export function editAnnouncementHomeData(param) {
  return $axios.post(ANNOUNCEMENT_MANAGEMENT_ADD_HOME, param)
}
/** 新增＆維護任務及公告 */
export function saveAnnouncement(param) {
  return $axios.post(ANNOUNCEMENT_MANAGEMENT_ADD_SAVE, param)
}
/** 任務及公告上下架 */
export function showAnnouncement(param) {
  return $axios.post(ANNOUNCEMENT_MANAGEMENT_SHOW_OR_NOT, param)
}
/** 刪除任務及公告 */
export function deleteAnnouncement(param) {
  return $axios.post(ANNOUNCEMENT_DELETE, param)
}
/** 機關帶出案件 */
export function organGetServiceCase(param) {
  return $axios.post(ORGAN_GET_CASE, param)
}
/** 活動項目帶出城市幣 */
export function activityGetPoint(param) {
  return $axios.post(ACTIVITY_GET_POINT, param)
}
/** 附件下載 */
export function getAnnouncementManagementDownloadFile(param) {
  return $axios.post(ANNOUNCEMENT_MANAGEMENT_DOWNLOAD_FILE, param, {
    responseType: 'arraybuffer'
  })
}
