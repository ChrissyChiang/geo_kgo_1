package gov.kcg.kgo.controller.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.CommonQuestionService;
import gov.kcg.kgo.viewModel.frontend.commonQuestion.home.rs.CommonQuestionHomeRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/frontend/commonQuestion")
public class CommonQuestionController extends BaseController {

	@Autowired
	CommonQuestionService commonQuestionService;

	/**
	 * 常見問題-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CommonQuestionHomeRs commonQuestionHomeAction() {
		CommonQuestionHomeRs rs = commonQuestionService.commonQuestionHome();
		return rs;
	}

}
