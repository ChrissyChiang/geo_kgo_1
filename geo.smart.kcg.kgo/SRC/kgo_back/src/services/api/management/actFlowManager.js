import { $axios } from '../../axios'

/** 畫面初始 */
export function getActFlowHomeData() {
  return $axios.post('/backend/actFlowManager/homeAction', {})
}

/** 動態流程管理 - 儲存 */
export function saveActFlow(param) {
  return $axios.post('/backend/actFlowManager/saveAction', param)
}

/** 動態流程管理 - 重送流程 */
export function resendFlowActFlow(param) {
  return $axios.post('/backend/actFlowManager/resendFlowAction', param)
}

/** 動態流程管理 - 刪除流程 */
export function deleteFlowActFlow(param) {
  return $axios.post('/backend/actFlowManager/deleteAction', param)
}

/** 動態流程管理 - 取得任務明細 */
export function getTaskDetail(param) {
  return $axios.post('/backend/actFlowManager/taskDetailAction', param)
}
