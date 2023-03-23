export const USER_INFO = '/auth/getLoginUserInfoAction'
/** jwt登入 */
export const LOGIN = '/auth/loginAction'
export const BID_SERVICE_MENU = 'bidServiceMenu/queryAction'
export const BID_CASE_LIST = 'bidCaseList/homeAction'
export const GET_PARAM_SET = 'auth/getParamSetAction'
/** 申辦說明頁 *//** 初始畫面 */
export const BID_INSTRUCTION = 'bidInstruction/homeAction'
/** 檔案下載 */
export const BID_DOWNLOAD = 'bidInstruction/downloadAction'
/** 取得站外連結 */
export const BID_INSTRUCTION_GET_LINK = 'bidInstruction/getLinkAction'
/** 檢核案件是否有下架 */
export const BID_INSTRUCTION_CHECK_STATUS_IS_ON = 'bidInstruction/checkStatusIsOn'
/** 服務宣告 *//** 初始畫面 */
export const SERVICE_ANNOUNCEMENT = 'serviceAnnounce/homeAction'
/** 活動消息 *//** 初始畫面 */
export const ACTIVITY_MESSAGE = 'eventNews/homeAction'
/** 活動內容 */
export const ACTIVITY_MESSAGE_DETAIL = 'eventNews/queryAction'
/** 活動內容-附件下載 */
export const ACTIVITY_MESSAGE_FILE_DOWNLOAD = 'eventNews/downloadAction'
/** 常見問題*//** 初始畫面 */
export const COMMON_QUESTION = 'commonQuestion/homeAction'
/** 填寫表單 *//** 初始畫面 */
export const FILL_FORM_HOME_DATA = 'caseform/homeAction'
/** MyData轉址網址 */
export const FILL_FORM_GET_MYDATA_URL = 'caseform/myDataActionUrl'
/** 確認申請內容 送出表單*/
export const SAVE_ACTION = 'caseform/saveAction'
/** 取得MyData資料 */
export const FILL_FORM_GET_MYDATA_DATA = 'caseform/myDataHomeAction'
/** MyData檔案下載 */
export const MYDATA_FILE_DOWNLOAD = 'caseform/downloadMyDataAttachment'
/** 網路申辦說明頁-stap4-案件申請表單-檢核*/
export const Validation_Action = 'caseform/validationAction'

export const QUERY_ACTION = 'hotSearch/queryAction'

export const DETAIL_ACTION = 'caseProcessSearch/detailAction'
/** 熱門搜尋 - 初始畫面*/
export const HOT_SEARCH = 'hotSearch/homeAction'

/** 案件進度查詢-明細-保存*/
export const DETAIL_SAVE = 'caseProcessSearch/detailSaveAction'

/** 案件進度查詢-明細-保存*/
export const PROCESS_SEARCH = 'caseProcessSearch/queryAction'

/** 案件進度查詢-首頁*/
export const CASE_HOME_ACTION = 'caseProcessSearch/homeAction'

/** 案件進度查詢-檢核*/
export const DETAIL_CHECK = 'caseProcessSearch/checkInfo'

export const LOG_OUT_LOG = 'auth/logout'

export const CheckData = res => {


  if (res.rtnCode == '0000')
    return res
  throw {
    response: {
      data: {
        rtnCode: res.rtnCode,
        msg: res.msg
      }
    }
  }

}



