package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.service.CaseHandleService;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rq.CaseHandlePendingReviewNotifyHistoryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rq.CaseHandlePendingReviewNotifyRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.CaseHandlePendingReviewNotifyHistoryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.CaseHandlePendingReviewNotifyRs;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backend/caseHandleNotify")
public class CaseHandleNotifyController {

    @Autowired
    private CaseHandleService caseHandleService;

    /**
     * 後台案件處理-待審核匣-訊息通知
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-訊息通知")
    @RequestMapping(
            value = {"/pendingReview/notify"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CaseHandlePendingReviewNotifyRs caseHandlePendingSignHomeAction(@RequestBody CaseHandlePendingReviewNotifyRq caseHandlePendingReviewNotifyRq) {
        return caseHandleService.caseHandlePendingReviewNotify(caseHandlePendingReviewNotifyRq);
    }

    /**
     * 後台案件處理-待審核匣-訊息通知歷程
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-訊息通知歷程")
    @RequestMapping(
            value = {"/pendingReview/notifyHistory"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CaseHandlePendingReviewNotifyHistoryRs caseHandlePendingSignHomeAction(@RequestBody CaseHandlePendingReviewNotifyHistoryRq caseHandlePendingReviewNotifyHistoryRq) {
        return caseHandleService.caseHandlePendingReviewNotifyHistory(caseHandlePendingReviewNotifyHistoryRq);
    }

}
