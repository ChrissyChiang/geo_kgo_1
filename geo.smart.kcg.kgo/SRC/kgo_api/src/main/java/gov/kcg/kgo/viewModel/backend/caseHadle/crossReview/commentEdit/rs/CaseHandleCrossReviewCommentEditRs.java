package gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rs;

import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.bean.CaseHandleCrossReviewCommentViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211115 add for 跨機關檢視
 * 後台案件處理-跨機關檢視-新增/編輯備註 rs
 */
@ApiModel(description = "後台案件處理-跨機關檢視-新增/編輯備註 rs")
public class CaseHandleCrossReviewCommentEditRs extends ApiBaseResponse<CaseHandleCrossReviewCommentViewForm> {

    private static final long serialVersionUID = 1L;
}
