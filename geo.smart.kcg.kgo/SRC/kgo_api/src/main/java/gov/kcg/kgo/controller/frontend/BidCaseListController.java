package gov.kcg.kgo.controller.frontend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.BidCaseListService;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rq.BidCaseListHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.BidCaseListHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rq.BidCaseListQueryRq;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rs.BidCaseListQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/frontend/bidCaseList")
public class BidCaseListController extends BaseController {

	@Autowired
	BidCaseListService bidCaseListService;

	/**
	 * 申辦案件清單-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦案件清單-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BidCaseListHomeRs bidCaseListHomeAction(@RequestBody BidCaseListHomeRq rq, HttpServletRequest request) {
		BidCaseListHomeRs rs = bidCaseListService.bidCaseListHome(rq);
		return rs;
	}

	/**
	 * 申辦案件清單-申辦案件資料查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦案件清單-申辦案件資料查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BidCaseListQueryRs bidCaseListQueryAction(@RequestBody BidCaseListQueryRq rq, HttpServletRequest request) {
		BidCaseListQueryRs rs = bidCaseListService.bidCaseListQuery(rq);
		return rs;
	}

}
