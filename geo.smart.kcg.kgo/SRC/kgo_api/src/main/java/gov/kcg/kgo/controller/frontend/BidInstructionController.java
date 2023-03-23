package gov.kcg.kgo.controller.frontend;

import gov.kcg.kgo.aop.annotion.ValidCode;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.BidInstructionService;
import gov.kcg.kgo.service.impl.BidInstructionServiceImpl;
import gov.kcg.kgo.service.impl.helper.CallKcgCityApiServiceHelper;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.download.rq.BidInstructionDownloadRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rq.BidInstructionGetLinkRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rs.BidInstructionGetLinkRs;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rq.BidInstructionHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.BidInstructionHomeRs;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/frontend/bidInstruction")
public class BidInstructionController extends BaseController {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BidInstructionController.class);

	@Autowired
	private BidInstructionServiceImpl bidInstructionService;

	/**
	 * 申辦說明頁-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦說明頁-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BidInstructionHomeRs bidInstructionHomeAction(@RequestBody BidInstructionHomeRq rq,
			HttpServletRequest request) {
		BidInstructionHomeRs rs = bidInstructionService.bidInstructionHome(rq);
		return rs;
	}

	/**
	 * 申辦說明頁-檔案下載
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦說明頁-檔案下載")
	@RequestMapping(value = { "/downloadAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public void bidInstructionQueryAction(@RequestBody BidInstructionDownloadRq rq, HttpServletRequest request) {
		bidInstructionService.bidInstructionDownload(rq);
	}

	/**
	 * 申辦說明頁-取得站外連結
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦說明頁-取得站外連結")
	@RequestMapping(value = { "/getLinkAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ValidCode
	public BidInstructionGetLinkRs bidInstructionGetLinkAction(@RequestBody BidInstructionGetLinkRq rq,
			HttpServletRequest request) {
		BidInstructionGetLinkRs rs = bidInstructionService.bidInstructionGetLink(rq);
		LOGGER.info("BidInstructionController bidInstructionGetLinkAction rs url: "+rs.getData().getLinkUrl()); //GEO 20210815 add
		return rs;
	}

    /**
     * 申辦說明頁-檢查案件是否上架
     *
     * @return
     */
    @ApiOperation(value = "申辦說明頁-檢查案件是否上架")
    @RequestMapping(value = { "/checkStatusIsOn" }, method = RequestMethod.POST)
    @ResponseBody
    public boolean checkStatusIsOn(@RequestParam(value = "caseSetId") String caseSetId) {
        return bidInstructionService.checkStatusIsOn(caseSetId);
    }

}
