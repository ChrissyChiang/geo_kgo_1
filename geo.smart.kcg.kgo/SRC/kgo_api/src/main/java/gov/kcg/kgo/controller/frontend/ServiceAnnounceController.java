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
import gov.kcg.kgo.service.ServiceAnnounceService;

import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rq.ServiceAnnounceHomeRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/frontend/serviceAnnounce")
public class ServiceAnnounceController extends BaseController {

	@Autowired
	ServiceAnnounceService serviceAnnounceService;

	/**
	 * 服務宣告-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務宣告-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceAnnounceHomeRs serviceAnnounceHomeAction(@RequestBody ServiceAnnounceHomeRq rq,
			HttpServletRequest request) {
		ServiceAnnounceHomeRs rs = serviceAnnounceService.serviceAnnounceHome(rq);
		return rs;
	}

}
