package gov.kcg.kgo.controller.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;

import gov.kcg.kgo.service.SystemAnnounceService;
import gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.SystemAnnounceHomeRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/systemAnnounce")
public class SystemAnnounceController extends BaseController {

	@Autowired
	SystemAnnounceService systemAnnounceService;

	/**
	 * 系統公告-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "系統公告-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SystemAnnounceHomeRs accountHomeAction() {
		SystemAnnounceHomeRs rs = systemAnnounceService.systemAnnouncetHome();
		return rs;
	}

}
