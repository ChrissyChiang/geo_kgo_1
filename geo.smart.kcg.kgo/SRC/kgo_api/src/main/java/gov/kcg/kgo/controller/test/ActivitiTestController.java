package gov.kcg.kgo.controller.test;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.service.ActivitiService;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test/activiti")
public class ActivitiTestController extends BaseController {

	@Autowired
	ActivitiService activitiService;

	@Autowired
	private RuntimeService runtimeService;

	/**
	 * 帳號權限管理-帳號搜尋
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "帳號權限管理-帳號搜尋")
	@RequestMapping(value = { "/processStart" }, method = { RequestMethod.POST })
	@ResponseBody
	public void accountQueryAction(HttpServletRequest request) {
		activitiService.deployFlow();
//		activitiService.processStart("U2020102300001", "", "", CaseTypeEnum.SCA);
	}

}
