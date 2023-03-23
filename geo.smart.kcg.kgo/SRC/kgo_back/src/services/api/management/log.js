import { $axios } from '../../axios'

/** 查詢前/後台Log */
export function queryLog(param) {
  return $axios.post('/backend/kgoLog/queryAction', param)
}

/** 匯出前/後台Log Excel */
export function exportLogExcel(param) {
  return $axios.post('/backend/kgoLog/exportExcelAction', param, {
    responseType: 'arraybuffer'
  })
}

/** 匯出前/後台Log Pdf */
export function exportLogPdf(param) {
  return $axios.post('/backend/kgoLog/exportPdfAction', param, {
    responseType: 'arraybuffer'
  })
}

/** 後台 - 案件軌跡統計 - 查詢 */
export function queryCaseLog(param) {
  return $axios.post('/backend/kgoCaseLog/queryAction', param)
}

/** 後台 - 案件軌跡統計 - 匯出excel */
export function exportCaseLogExcel(param) {
  return $axios.post('/backend/kgoCaseLog/exportExcelAction', param, {
    responseType: 'arraybuffer'
  })
}
