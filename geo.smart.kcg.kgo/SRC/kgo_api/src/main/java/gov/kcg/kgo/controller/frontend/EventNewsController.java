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
import gov.kcg.kgo.service.EventNewsService;
import gov.kcg.kgo.viewModel.frontend.eventNews.download.rq.DownloadActionRq;
import gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.EventNewsHomeRs;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rq.EventNewsQueryRq;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rs.EventNewsQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/frontend/eventNews")
public class EventNewsController extends BaseController {

	@Autowired
	EventNewsService eventNewsService;

	/**
	 * 活動消息-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "活動消息-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public EventNewsHomeRs eventNewsHomeAction() {
		EventNewsHomeRs rs = eventNewsService.eventNewsHome();
		return rs;
	}

	/**
	 * 活動消息-任務消息查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "活動消息-任務消息查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public EventNewsQueryRs eventNewsQueryAction(@RequestBody EventNewsQueryRq rq, HttpServletRequest request) {
		EventNewsQueryRs rs = eventNewsService.eventNewsQuery(rq);
		return rs;
	}

	@ApiOperation(value = "活動消息-下載檔案")
	@RequestMapping(value = { "/downloadAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void downloadAction(@RequestBody DownloadActionRq rq) {
		eventNewsService.downloadAction(rq);
	}

}
