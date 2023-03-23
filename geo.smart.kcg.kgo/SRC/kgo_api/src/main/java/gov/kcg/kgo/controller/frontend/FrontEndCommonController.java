package gov.kcg.kgo.controller.frontend;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.kcg.kgo.controller.base.BaseController;

import gov.kcg.kgo.service.CommonService;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;

@Controller
@RequestMapping("/frontend/common")
public class FrontEndCommonController extends BaseController {

	@Autowired
	CommonService commonService;

	/**
	 * 能力設定 範本下載
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation("能力設定 範本下載")
	@RequestMapping(value = { "/downloadTemplAction" }, method = { RequestMethod.POST })
	public void downloadTempl(Model model) {
		File file = null;
		commonService.downloadFileAction(file);
	}

}
