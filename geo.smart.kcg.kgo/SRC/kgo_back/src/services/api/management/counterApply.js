import { $axios } from '../../axios'
import { jsonToFormData } from '@/utils/form'
/** 臨櫃申請-畫面初始 */
export function getCounterApplyHomeData(param) {
  return $axios.post('/backend/counterApply/homeAction', param)
}

/** 臨櫃申請-附件上傳 */
export function attachmentUploadAction(param) {
  return $axios.post(
    '/backend/counterApply/attachmentUploadAction',
    jsonToFormData(param, ['file'])
  )
}

/** 臨櫃申請-申請說明資料儲存 */
export function saveCounterApply(param) {
  return $axios.post('/backend/counterApply/saveAction', param)
}
