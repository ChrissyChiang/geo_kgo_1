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
import gov.kcg.kgo.service.BidServiceMenuService;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.home.rs.BidServiceMenuHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rq.BidServiceMenuQueryRq;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rs.BidServiceMenuQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/frontend/bidServiceMenu")
public class BidServiceMenuController extends BaseController {

	@Autowired
	BidServiceMenuService bidServiceMenuService;

	/**
	 * 申辦服務選單-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦服務選單-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BidServiceMenuHomeRs bidServiceMenuHomeAction() {
		BidServiceMenuHomeRs rs = bidServiceMenuService.bidServiceMenuHome();
		return rs;
	}

	/**
	 * 申辦服務選單-申辦案件數查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "申辦服務選單-申辦案件數查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BidServiceMenuQueryRs bidServiceMenuQueryAction(@RequestBody BidServiceMenuQueryRq rq,
			HttpServletRequest request) {
		BidServiceMenuQueryRs rs = bidServiceMenuService.bidServiceMenuQuery(rq);
		return rs;
	}

}
