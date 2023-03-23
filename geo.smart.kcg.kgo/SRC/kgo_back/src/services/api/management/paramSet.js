import { $axios } from '../../axios'

/** 畫面初始 */
export function getParamSetHomeData() {
  return $axios.post('/backend/paramSet/homeAction', {})
}

/** 參數儲存 */
export function saveParamSetData(param) {
  return $axios.post('/backend/paramSet/saveAction', param)
}
