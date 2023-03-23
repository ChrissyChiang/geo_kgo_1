package gov.kcg.kgo.controller.commonApi;

import javax.servlet.http.HttpServletRequest;

import gov.kcg.kgo.service.impl.KcgCityExtServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.service.KcgCityExtService;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.commonApi.correctStatus.rq.CorrectStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.correctStatus.rs.CorrectStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rq.FlowB22GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rs.FlowB22GetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c22.getCaseInfoAction.rq.FlowC22GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c22.getCaseInfoAction.rs.FlowC22GetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rq.FlowC23GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rs.FlowC23GetCaseInfoListActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rq.FlowC3AddCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rs.FlowC3AddCaseActionRs;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.GenGeneralCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rs.GenGeneralCaseActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rq.GetCaseIdActionRq;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rs.GetCaseIdActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rq.GetCaseLogJsonActionRq;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rs.GetCaseLogJsonActionRs;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rq.GetGeneralColumnActionRq;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rs.GetGeneralColumnActionRs;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rq.GetUserLogJsonActionRq;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rs.GetUserLogJsonActionRs;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rq.GetCaseStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rs.GetCaseStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.notify.rq.QueryStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.notify.rs.QueryStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq.UpdateCaseStatusActionRq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 對外API
 * 高雄城市資料平台 API Controller.
 */
@Controller
@RequestMapping("/common")
@Api(tags ="KcgCityExtController")
public class KcgCityExtController extends BaseController {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KcgCityExtController.class);

	@Autowired
	KcgCityExtService kcgCityExtService;
	
	/**
	 * 提供既有案件服務撈取案件資料API，B-2流程_2 done.
	 *
	 * @param rq      the rq
	 * @param request the request
	 * @return the string
	 */
	@ApiOperation(value = "B-2流程_2，提供既有案件服務撈取案件資料API")
	@RequestMapping(value = { "/flow/b2/2/getCaseInfoAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FlowB22GetCaseInfoActionRs flowB22GetCaseInfoAction(@RequestBody FlowB22GetCaseInfoActionRq rq) {
		// 通知既有案件服務有新進案件，B-2流程_1
		FlowB22GetCaseInfoActionRs rs = kcgCityExtService.flowB22GetCaseInfoAction(rq);
		return rs;
	}
	
	/**
	 * 提供既有案件服務撈取案件資料API，C-2流程_2.
	 *
	 * @param rq the rq
	 * @return the flow B 22 get case info action rs
	 */
	@ApiOperation(value = "C-2流程_2，提供各機關單位依案件編號取得申辦案件資料(含MyData提供檔案)")
	@RequestMapping(value = { "/flow/c2/2/getCaseInfoAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FlowC22GetCaseInfoActionRs flowC22GetCaseInfoAction(@RequestBody FlowC22GetCaseInfoActionRq rq) {
		// 提供既有案件服務撈取案件資料API，C-2流程_2
		FlowC22GetCaseInfoActionRs rs = kcgCityExtService.flowC22GetCaseInfoAction(rq);
		return rs;
	}
	
	/**
	 * 提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐.
	 *
	 * @param rq the rq
	 * @return the FlowC23GetCaseInfoListActionRs rs
	 */
	@ApiOperation(value = "提供C-2_3 流程依服務編號取得待處理案件清單(含MyData提供檔案)")
	@RequestMapping(value = { "/flow/c2/3/getCaseInfoListAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FlowC23GetCaseInfoListActionRs flowC23GetCaseInfoListAction(@RequestBody FlowC23GetCaseInfoActionRq rq, HttpServletRequest request) {
		// 提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐
		FlowC23GetCaseInfoListActionRs rs = kcgCityExtService.flowC23GetCaseInfoAction(rq);
		return rs;
	}
	
	/**
	 * 提供平台案件處理區呼叫使用，C-3流程 新增案件 done.
	 *
	 * @param rq the rq
	 * @return FlowC3AddCaseActionRs rs
	 */
	@ApiOperation(value = "提供平台案件處理區呼叫使用，C-3流程 新增案件")
	@RequestMapping(value = { "/flow/c3/addCaseAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FlowC3AddCaseActionRs flowC3AddCaseAction(@RequestBody FlowC3AddCaseActionRq rq, HttpServletRequest request) {
		// 提供平台案件處理區呼叫使用，C-3流程 新增案件
		synchronized (this) {
			FlowC3AddCaseActionRs rs = kcgCityExtService.flowC3AddCaseAction(rq);
			return rs;
		}
	}
	
	/**
	 * 通用型入案作業 done.
	 *
	 * @param rq the rq
	 * @return GetGenericColumnActionRs rs
	 */
	@ApiOperation(value = "通用型入案作業")
	@RequestMapping(value = { "/genGeneralCaseAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GenGeneralCaseActionRs genGeneralCaseAction(@RequestBody GenGeneralCaseActionRq rq,
			HttpServletRequest request) {
		// 通用型入案作業
		GenGeneralCaseActionRs rs = kcgCityExtService.genGeneralCaseAction(rq);
		return rs;
	}
	
	/**
	 * 通用型查詢欄位作業.
	 *
	 * @param rq the rq
	 * @return GetGenericColumnActionRs rs
	 */
	@ApiOperation(value = "通用型查詢欄位作業 - 依服務編號取得線上服務表單的欄位資訊")
	@RequestMapping(value = { "/getGeneralColumnAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GetGeneralColumnActionRs getGeneralColumnAction(@RequestBody GetGeneralColumnActionRq rq,
			HttpServletRequest request) {
		// 通用型查詢欄位作業
		GetGeneralColumnActionRs rs = kcgCityExtService.getGeneralColumnAction(rq);
		return rs;
	}
	
	/**
	 * 查詢案件狀態.
	 *
	 * @param rq the rq
	 * @return NotifyActionRs rs
	 */
	@ApiOperation(value = "查詢案件狀態")
	@RequestMapping(value = { "/queryStatusAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QueryStatusActionRs queryStatusAction(@RequestBody QueryStatusActionRq rq,
			HttpServletRequest request) {
		LOGGER.info("call queryStatusAction 查詢案件狀態...");
		// 查詢案件狀態，自動通知API
		QueryStatusActionRs rs = kcgCityExtService.queryStatusAction(rq);
		return rs;
	}
	
	/**
	 * 更新案件狀態.
	 *
	 * @param rq      the rq
	 * @param request the request
	 * @return the string
	 */
	@ApiOperation(value = "更新案件狀態")
	@RequestMapping(value = { "/updateCaseStatusAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> updateCaseStatusAction(@RequestBody UpdateCaseStatusActionRq rq) {
		LOGGER.info("call updateCaseStatusAction 更新案件狀態...");
		// 更新案件狀態
		ApiBaseResponse<BaseViewForm> rs = kcgCityExtService.updateCaseStatusAction(rq);
		return rs;
	}
	
	/**
	 * 寫入補正狀態.
	 *
	 * @param rq the rq
	 * @return CorrectStatusActionRs rs
	 */
	@ApiOperation(value = "寫入補正狀態")
	@RequestMapping(value = { "/correctStatusAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CorrectStatusActionRs correctStatusAction(@RequestBody CorrectStatusActionRq rq, HttpServletRequest request) {
		// 查詢案件狀態，自動通知API
		CorrectStatusActionRs rs = kcgCityExtService.correctStatusAction(rq);
		return rs;
	}
	
	/**
	 * 案件進度狀態查詢.
	 *
	 * @param rq the rq
	 * @return CorrectStatusActionRs rs
	 */
	@ApiOperation(value = "提供各機關透過案件編號查詢案件進度狀態")
	@RequestMapping(value = { "/getCaseStatusAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GetCaseStatusActionRs getCaseStatusAction(@RequestBody GetCaseStatusActionRq rq, HttpServletRequest request) {
		// 案件進度狀態查詢
		GetCaseStatusActionRs rs = kcgCityExtService.getCaseStatusAction(rq);
		return rs;
	}
	
	/**
	 * 提供各機關案件編號.
	 *
	 * @param rq the rq
	 * @return CorrectStatusActionRs rs
	 */
	@ApiOperation(value = "提供各機關案件編號")
	@RequestMapping(value = { "/getCaseIdAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GetCaseIdActionRs getCaseStatusAction(@RequestBody GetCaseIdActionRq rq, HttpServletRequest request) {
		// 案件進度狀態查詢
		synchronized (this) {
			GetCaseIdActionRs rs = kcgCityExtService.getCaseIdAction(rq);
			return rs;
		}
	}
	
	/**
	 * 提供前臺民眾操作軌跡紀錄json log.
	 *
	 * @param rq the rq
	 * @return CorrectStatusActionRs rs
	 */
	@ApiOperation(value = "提供前臺民眾操作軌跡紀錄json log")
	@RequestMapping(value = { "/getFeUserLogJsonAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GetUserLogJsonActionRs getFeUserLogJsonAction(@RequestBody GetUserLogJsonActionRq rq, HttpServletRequest request) {
		// 提供操作軌跡紀錄json log
		GetUserLogJsonActionRs rs = kcgCityExtService.getUserLogJsonAction(rq, SystemTypeEnum.F);
		return rs;
	}
	
	/**
	 * 提供前臺民眾操作軌跡紀錄json log.
	 *
	 * @param rq the rq
	 * @return CorrectStatusActionRs rs
	 */
	@ApiOperation(value = "提供後台人員操作軌跡紀錄json log")
	@RequestMapping(value = { "/getBeUserLogJsonAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GetUserLogJsonActionRs getBeUserLogJsonAction(@RequestBody GetUserLogJsonActionRq rq, HttpServletRequest request) {
		// 提供操作軌跡紀錄json log
		GetUserLogJsonActionRs rs = kcgCityExtService.getUserLogJsonAction(rq, SystemTypeEnum.B);
		return rs;
	}
	
	/**
	 * 提供案件軌跡紀錄json log.
	 *
	 * @param rq the rq
	 * @return CorrectStatusActionRs rs
	 */
	@ApiOperation(value = "提供案件軌跡紀錄json log")
	@RequestMapping(value = { "/getCaseLogJsonAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GetCaseLogJsonActionRs getCaseLogJsonAction(@RequestBody GetCaseLogJsonActionRq rq, HttpServletRequest request) {
		// 提供案件軌跡紀錄json log
		GetCaseLogJsonActionRs rs = kcgCityExtService.getCaseLogJsonAction(rq);
		return rs;
	}
}
