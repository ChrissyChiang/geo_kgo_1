package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.aop.annotion.ValidCode;
import gov.kcg.kgo.geoservice.GeoOrganCaseFormService;
import gov.kcg.kgo.geoservice.GeoOrganCaseFormServiceImpl;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.download.rq.BidInstructionDownloadRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rq.BidInstructionGetLinkRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rs.BidInstructionGetLinkRs;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rq.BidInstructionHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.BidInstructionHomeRs;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.*;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.*;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rq.ServiceAnnounceHomeRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * GEO 20211120 add 府內線上服務
 * 後台-府內線上服務 API Controller.
 */
@Controller
@RequestMapping("/backend/organCase")
@Api(tags = {"geo-organ-case-controller 後台-府內線上服務 表單申請"})
public class GeoOrganCaseController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoOrganCaseController.class);

    @Autowired
    GeoOrganCaseFormServiceImpl geoOrganCaseFormServiceImpl;

    @Autowired
    GeoOrganCaseFormService geoOrganCaseFormService;


    @ApiOperation(value = "申辦說明頁-檢查案件是否上架")
    @RequestMapping(value = { "/bidInstruction/checkStatusIsOn" }, method = RequestMethod.POST)
    @ResponseBody
    public boolean checkStatusIsOn(@RequestParam(value = "caseSetId") String caseSetId) {
        return geoOrganCaseFormService.checkStatusIsOn(caseSetId);
    }

    @ApiOperation(value = "申辦說明頁-初始畫面")
    @RequestMapping(value = { "/bidInstruction/homeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public BidInstructionHomeRs bidInstructionHomeAction(@RequestBody BidInstructionHomeRq rq, HttpServletRequest request) {
        BidInstructionHomeRs rs = geoOrganCaseFormService.bidInstructionHome(rq);
        return rs;
    }

    @ApiOperation(value = "申辦說明頁-檔案下載")
    @RequestMapping(value = { "/bidInstruction/downloadAction" }, method = { RequestMethod.POST }, consumes = {MediaType.APPLICATION_JSON_VALUE })
    public void bidInstructionQueryAction(@RequestBody BidInstructionDownloadRq rq, HttpServletRequest request) {
        geoOrganCaseFormService.bidInstructionDownload(rq);
    }

    @ApiOperation(value = "申辦說明頁-取得站外連結")
    @RequestMapping(value = { "/getLinkAction" }, method = { RequestMethod.POST }, consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ValidCode
    public BidInstructionGetLinkRs bidInstructionGetLinkAction(@RequestBody BidInstructionGetLinkRq rq, HttpServletRequest request) {
        BidInstructionGetLinkRs rs = geoOrganCaseFormService.bidInstructionGetLink(rq);
        LOGGER.info("bidInstructionGetLinkAction rs url: "+rs.getData().getLinkUrl());
        return rs;
    }

    @ApiOperation(value = "服務宣告-初始畫面")
    @RequestMapping(value = { "/serviceAnnounce/homeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ServiceAnnounceHomeRs serviceAnnounceHomeAction(@RequestBody ServiceAnnounceHomeRq rq, HttpServletRequest request) {
        ServiceAnnounceHomeRs rs = geoOrganCaseFormService.serviceAnnounceHome(rq);
        return rs;
    }


    @ApiOperation(value = "網路申辦說明頁-stap3-初始畫面")
    @RequestMapping(value = { "/serviceAnnounce/caseForm/homeAction" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public HomeActionRs homeAction(@RequestBody HomeActionRq rq) {
        return geoOrganCaseFormServiceImpl.homeAction(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap3-取得轉址MyData網址")
    @RequestMapping(value = { "/serviceAnnounce/myDataActionUrl" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public MyDataActionUrlRs myDataActionUrl(@RequestBody MyDataActionUrlRq rq) {
        return geoOrganCaseFormServiceImpl.myDataActionUrl(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap3-取得 MyData 資料")
    @RequestMapping(value = { "/serviceAnnounce/myDataHomeAction" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public MyDataHomeActionRs myDataHomeAction(@RequestBody MyDataHomeActionRq rq) {
        return geoOrganCaseFormServiceImpl.myDataHomeAction(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap3-下載 MyData 附件資料")
    @RequestMapping(value = { "/serviceAnnounce/downloadMyDataAttachment" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public DownloadMyDataAttachmentRs downloadMyDataAttachment(@RequestBody DownloadMyDataAttachmentRq rq) {
        return geoOrganCaseFormServiceImpl.downloadMyDataAttachment(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap4-案件申請表單-檢核")
    @RequestMapping(value = { "/serviceAnnounce/caseForm/validationAction" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ValidationActionRs validationAction(@RequestBody ValidationActionRq rq) {
        return geoOrganCaseFormServiceImpl.validationAction(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap5-案件申請表單-存擋")
    @RequestMapping(value = { "/serviceAnnounce/saveAction" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public SaveActionRs saveAction(@RequestBody SaveActionRq rq) {
        return geoOrganCaseFormServiceImpl.saveAction(rq);
    }

    /**
     * GEO 20211202 add
     * 重複檢核檢查
     */
    @ApiOperation(value = "網路申辦說明頁-stap5-案件申請表單-存擋前重複檢核")
    @RequestMapping(value = { "/checkAction" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CheckActionRs checkAction(@RequestBody SaveActionRq rq) {
        return this.geoOrganCaseFormServiceImpl.checkAction(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap3-取得市府MyData")
    @RequestMapping(value = { "/serviceAnnounce/getKcgMydata" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public KcgMydataRs getKcgMydata(@RequestBody KcgMydataRq rq) {
        return geoOrganCaseFormServiceImpl.getKcgMydata(rq);
    }
}
