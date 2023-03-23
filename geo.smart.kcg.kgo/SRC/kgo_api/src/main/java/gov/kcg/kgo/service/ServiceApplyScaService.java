package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.serviceApplySca.pendingReview.rq.*;
import gov.kcg.kgo.viewModel.backend.serviceApplySca.pendingReview.rs.*;

public interface ServiceApplyScaService {
    ServiceApplyScaPendingReviewM1ApproveToM2Rs serviceApplyScaPendingReviewM1ApproveToM2(ServiceApplyScaPendingReviewM1ApproveToM2Rq rq);

    ServiceApplyScaPendingReviewM1ApproveToOMRs serviceApplyScaPendingReviewM1ApproveToOM(ServiceApplyScaPendingReviewM1ApproveToOMRq rq);

    ServiceApplyScaPendingReviewM2ApproveToOMRs serviceApplyScaPendingReviewM2ApproveToOM(ServiceApplyScaPendingReviewM2ApproveToOMRq rq);

    ServiceApplyScaPendingReviewM2ApproveEndRs serviceApplyScaPendingReviewM2ApproveEnd(ServiceApplyScaPendingReviewM2ApproveEndRq rq);

    ServiceApplyScaPendingReviewNotApproveRs serviceApplyScaPendingReviewNotApprove(ServiceApplyScaPendingReviewNotApproveRq rq);

    ServiceApplyScaPendingReviewOMApproveEndRs serviceApplyScaPendingReviewOMApproveEnd(ServiceApplyScaPendingReviewOMApproveEndRq rq);
}
