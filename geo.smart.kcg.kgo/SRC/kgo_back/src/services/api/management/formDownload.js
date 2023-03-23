import { $axios } from '../../axios'
import { jsonToFormData } from '@/utils/form'
/** 書表下載-畫面初始 */
export function getFormDownloadHomeData(param) {
  return $axios.post('/backend/formDownload/homeAction', param)
}

/** 書表下載-編輯 */
export function editFormDownload(param) {
  return $axios.post('/backend/formDownload/editAction', param)
}

/** 書表下載-刪除 */
export function deleteFormDownload(param) {
  return $axios.post('/backend/formDownload/deleteAction', param)
}

/** 書表下載-範本上傳 */
export function uploadFormDownload(param) {
  return $axios.post(
    '/backend/formDownload/uploadAction',
    jsonToFormData(param, ['file'])
  )
}

/** 書表下載-檔案下載 */
export function downloadFormDownload(param) {
  return $axios.post('/backend/formDownload/downloadAction', param, {
    responseType: 'arraybuffer'
  })
}
