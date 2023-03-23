import { $axios } from '../../axios'
import {
  AUTHORITY_APPLY_VIEW,
  AUTHORITY_APPLY_M1_APPROVE_TO_M2,
  AUTHORITY_APPLY_M1_APPROVE_TO_OM,
  AUTHORITY_APPLY_M2_APPROVE_TO_END,
  AUTHORITY_APPLY_M2_APPROVE_TO_OM,
  AUTHORITY_APPLY_NOT_APPROVE,
  AUTHORITY_APPLY_OM_APPROVE_TO_END
} from './constants'

/** 權限申請-檢視 */
export function getAuthorityApplyHomeData(param) {
  return $axios.post(AUTHORITY_APPLY_VIEW, param)
}
/** 待審核匣-主管一同意到主管二 */
export function getAuthorityApplyM1ApproveToM2(param) {
  return $axios.post(AUTHORITY_APPLY_M1_APPROVE_TO_M2, param)
}
/** 待審核匣-主管一同意到機關管理者 */
export function getAuthorityApplyM1ApproveToOm(param) {
  return $axios.post(AUTHORITY_APPLY_M1_APPROVE_TO_OM, param)
}
/** 待審核匣-主管二同意結束 */
export function getAuthorityApplyM2ApproveToEnd(param) {
  return $axios.post(AUTHORITY_APPLY_M2_APPROVE_TO_END, param)
}
/** 待審核匣-主管二同意到機關管理者 */
export function getAuthorityApplyM2ApproveToOm(param) {
  return $axios.post(AUTHORITY_APPLY_M2_APPROVE_TO_OM, param)
}
/** 待審核匣-不同意 */
export function getAuthorityApplyNotApprove(param) {
  return $axios.post(AUTHORITY_APPLY_NOT_APPROVE, param)
}
/** 待審核匣-機關管理者同意結束 */
export function getAuthorityApplyOmApproveToEnd(param) {
  return $axios.post(AUTHORITY_APPLY_OM_APPROVE_TO_END, param)
}
