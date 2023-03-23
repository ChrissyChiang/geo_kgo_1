package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq;

import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.bean.CaseHandlePendingReviewCorrectUpdateDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台案件處理-待審核匣-簽核-補正通知 rq")
public class CaseHandlePendingReviewCorrectUpdateRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "補正期限")
    private Integer correctDeadLine;

    @ApiModelProperty(value = "補正清單")
    private List<CaseHandlePendingReviewCorrectUpdateDataGrid> caseHandlePendingReviewCorrectUpdateDataGridList;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Integer getCorrectDeadLine() {
        return correctDeadLine;
    }

    public void setCorrectDeadLine(Integer correctDeadLine) {
        this.correctDeadLine = correctDeadLine;
    }

    public List<CaseHandlePendingReviewCorrectUpdateDataGrid> getCaseHandlePendingReviewCorrectUpdateDataGridList() {
        return caseHandlePendingReviewCorrectUpdateDataGridList;
    }

    public void setCaseHandlePendingReviewCorrectUpdateDataGridList(List<CaseHandlePendingReviewCorrectUpdateDataGrid> caseHandlePendingReviewCorrectUpdateDataGridList) {
        this.caseHandlePendingReviewCorrectUpdateDataGridList = caseHandlePendingReviewCorrectUpdateDataGridList;
    }
}