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
import gov.kcg.kgo.enums.backend.ActFlowEnableEnum;
import gov.kcg.kgo.service.BackEndFlowManagerService;
import gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rq.ActFlowManagerDeleteRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rs.ActFlowManagerDeleteRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.ActFlowManagerHomeActionRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.resendFlow.rq.ActFlowManagerResendFlowRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.ActFlowManagerSaveRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rs.ActFlowManagerSaveFlowRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rq.ActFlowManagerTaskDetailActionRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rs.ActFlowManagerTaskDetailActionRs;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiOperation;

/**
 * 動態流程管理 controller.
 */
@Controller
@RequestMapping("/backend/actFlowManager")
public class ActFlowManagerController extends BaseController {

	@Autowired
	BackEndFlowManagerService backEndFlowManagerService;

	/**
	 * 動態流程管理 - 畫面初始 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "動態流程管理 - 畫面初始")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ActFlowManagerHomeActionRs homeAction(HttpServletRequest request) {
		ActFlowManagerHomeActionRs rs = backEndFlowManagerService.homeAction();
		return rs;
	}
	
	/**
	 * 動態流程管理 - 取得任務明細 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "動態流程管理 - 取得任務明細 ")
	@RequestMapping(value = { "/taskDetailAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ActFlowManagerTaskDetailActionRs taskDetailAction(HttpServletRequest request, @RequestBody ActFlowManagerTaskDetailActionRq rq) {
		ActFlowManagerTaskDetailActionRs rs = backEndFlowManagerService.taskDetailAction(rq);
		return rs;
	}

	/**
	 * 動態流程管理 - 儲存 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "動態流程管理 - 儲存")
	@RequestMapping(value = { "/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ActFlowManagerSaveFlowRs homeAction(HttpServletRequest request, @RequestBody ActFlowManagerSaveRq rq) {
		// ActFlowEnableEnum.Y always 啟用流程 (服務表單能看見)
		ActFlowManagerSaveFlowRs rs = backEndFlowManagerService.saveAction(rq, ActFlowEnableEnum.Y);
		return rs;
	}
	
	/**
	 * 動態流程管理 - 修改重送流程(待處理匣案件修改重送流程)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "動態流程管理 - 修改重送流程(待處理匣案件修改重送流程)")
	@RequestMapping(value = { "/resendFlowAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> resendFlowAction(@RequestBody ActFlowManagerResendFlowRq rq) {
		ApiBaseResponse<BaseViewForm> rs = backEndFlowManagerService.resendFlowAction(rq);
		return rs;
	}
	
	/**
	 * 動態流程管理 - 刪除
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "動態流程管理 - 刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ActFlowManagerDeleteRs deleteAction(@RequestBody ActFlowManagerDeleteRq rq) {
		ActFlowManagerDeleteRs rs = backEndFlowManagerService.deleteAction(rq);
		return rs;
	}
}
