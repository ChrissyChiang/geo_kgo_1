package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.service.ServiceApplyUraService;
import gov.kcg.kgo.viewModel.backend.serviceApplySca.pendingReview.rq.ServiceApplyScaPendingReviewM1ApproveToM2Rq;
import gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rq.*;
import gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rs.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend/serviceApply/ura")
public class ServiceApplyUraController {

    @Autowired
    private ServiceApplyUraService serviceApplyUraService;

    /**
     * 後台案件處理-待審核匣-主管一同意到主管二
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-主管一同意到主管二")
    @RequestMapping(value = { "/pendingReview/m1ApproveToM2" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewM1ApproveToM2Rs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewM1ApproveToM2Rq rq) {
        ServiceApplyUraPendingReviewM1ApproveToM2Rs rs = serviceApplyUraService.serviceApplyUraPendingReviewM1ApproveToM2(rq);
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-主管一同意到機關管理者
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-主管一同意到機關管理者")
    @RequestMapping(value = { "/pendingReview/m1ApproveToOM" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewM1ApproveToOMRs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewM1ApproveToOMRq rq) {
        ServiceApplyUraPendingReviewM1ApproveToOMRs rs = serviceApplyUraService.serviceApplyUraPendingReviewM1ApproveToOM(rq);
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-主管二同意到機關管理者
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-主管二同意到機關管理者")
    @RequestMapping(value = { "/pendingReview/m2ApproveToOM" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewM2ApproveToOMRs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewM2ApproveToOMRq rq) {
        ServiceApplyUraPendingReviewM2ApproveToOMRs rs = serviceApplyUraService.serviceApplyUraPendingReviewM2ApproveToOM(rq);
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-主管二同意結束
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-主管二同意結束")
    @RequestMapping(value = { "/pendingReview/m2ApproveEnd" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewM2ApproveEndRs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewM2ApproveEndRq rq) {
        ServiceApplyUraPendingReviewM2ApproveEndRs rs = serviceApplyUraService.serviceApplyUraPendingReviewM2ApproveEnd(rq);
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-主管二同意結束
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-機關管理者同意結束")
    @RequestMapping(value = { "/pendingReview/omApproveEnd" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewOMApproveEndRs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewOMApproveEndRq rq) {
        ServiceApplyUraPendingReviewOMApproveEndRs rs = serviceApplyUraService.serviceApplyUraPendingReviewOMApproveEnd(rq);
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-不同意
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-不同意")
    @RequestMapping(value = { "/pendingReview/notApprove" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewNotApproveRs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewNotApproveRq rq) {
        ServiceApplyUraPendingReviewNotApproveRs rs = serviceApplyUraService.serviceApplyUraPendingReviewNotApprove(rq);
        return rs;
    }


    /**
     * 後台案件處理-待審核匣-不同意
     *
     * @return
     */
    @ApiOperation(value = "後台案件處理-待審核匣-檢視")
    @RequestMapping(value = { "/pendingReview/view" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public ServiceApplyUraPendingReviewViewRs caseHandlePendingReviewApproveToM1(@RequestBody ServiceApplyUraPendingReviewViewRq rq) {
        ServiceApplyUraPendingReviewViewRs rs = serviceApplyUraService.serviceApplyUraPendingReviewView(rq);
        return rs;
    }
}
