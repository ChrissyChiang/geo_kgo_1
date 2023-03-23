package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.BackEndParamSetService;
import gov.kcg.kgo.viewModel.backend.paramSet.home.rs.ParamSetHomeActionRs;
import gov.kcg.kgo.viewModel.backend.paramSet.save.rq.ParamSetSaveActionRq;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiOperation;

/**
 * 查詢參數設定 controller.
 */
@Controller
@RequestMapping("/backend/paramSet")
public class ParamSetController extends BaseController {

	@Autowired
	BackEndParamSetService backEndParamSetService;

	/**
	 * 查詢參數設定 - 畫面初始 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查詢參數設定 - 畫面初始")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ParamSetHomeActionRs homeAction(HttpServletRequest request) {
		ParamSetHomeActionRs rs = backEndParamSetService.homeAction();
		return rs;
	}
	
	/**
	 * 查詢參數設定 - 儲存 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查詢參數設定 - 儲存")
	@RequestMapping(value = { "/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> homeAction(HttpServletRequest request, @RequestBody ParamSetSaveActionRq rq) {
		ApiBaseResponse<BaseViewForm> rs = backEndParamSetService.saveAction(rq);
		return rs;
	}
}
