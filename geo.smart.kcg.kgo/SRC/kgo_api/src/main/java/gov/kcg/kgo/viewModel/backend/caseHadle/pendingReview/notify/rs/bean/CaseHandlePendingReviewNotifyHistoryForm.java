package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "後台案件處理-待審核匣-訊息通知 Form")
public class CaseHandlePendingReviewNotifyHistoryForm extends BaseViewForm {

    private List<CaseHandlePendingReviewNotifyHistoryDataGrid> caseHandlePendingReviewNotifyHistoryDataGrids;

    public List<CaseHandlePendingReviewNotifyHistoryDataGrid> getCaseHandlePendingReviewNotifyHistoryDataGrids() {
        return caseHandlePendingReviewNotifyHistoryDataGrids;
    }

    public void setCaseHandlePendingReviewNotifyHistoryDataGrids(List<CaseHandlePendingReviewNotifyHistoryDataGrid> caseHandlePendingReviewNotifyHistoryDataGrids) {
        this.caseHandlePendingReviewNotifyHistoryDataGrids = caseHandlePendingReviewNotifyHistoryDataGrids;
    }

}
