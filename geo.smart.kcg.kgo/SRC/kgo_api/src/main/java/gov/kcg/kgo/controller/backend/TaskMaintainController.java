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
import gov.kcg.kgo.service.TaskMaintainService;
import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rq.TaskMaintainCityCoinSearchRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rs.TaskMaintainCityCoinSearchRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rq.TaskMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rs.TaskMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.download.rq.TaskMaintainDownloadRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rq.TaskMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.TaskMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.home.rs.TaskMaintainHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rq.TaskMaintainOnOffRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rs.TaskMaintainOnOffRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rq.TaskMaintainOrganCaseRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rs.TaskMaintainOrganCaseRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rq.TaskMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.TaskMaintainQueryRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rq.TaskMaintainSaveRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rs.TaskMaintainSaveRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/taskMaintain")
public class TaskMaintainController extends BaseController {

	@Autowired
	TaskMaintainService taskMaintainService;

	/**
	 * 任務及公告管理-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainHomeRs taskMaintainHomeAction() {
		TaskMaintainHomeRs rs = taskMaintainService.taskMaintainHome();
		return rs;
	}

	/**
	 * 任務及公告管理-任務以及公告查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理-任務以及公告查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainQueryRs taskMaintainQueryAction(@RequestBody TaskMaintainQueryRq rq,
			HttpServletRequest request) {
		TaskMaintainQueryRs rs = taskMaintainService.taskMaintainQuery(rq);
		return rs;
	}

	/**
	 * 任務及公告管理-任務維護(新增/修改)初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理-任務維護(新增/修改)初始畫面")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainEditHomeRs thesaurusMaintainEditHomeAction(@RequestBody TaskMaintainEditHomeRq rq,
			HttpServletRequest request) {
		TaskMaintainEditHomeRs rs = taskMaintainService.taskMaintainEditHome(rq);
		return rs;
	}

	/**
	 * 任務及公告管理-任務維護儲存
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理-任務維護儲存")
	@RequestMapping(value = { "/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainSaveRs taskMaintainSaveAction(@RequestBody TaskMaintainSaveRq rq, HttpServletRequest request) {
		TaskMaintainSaveRs rs = taskMaintainService.taskMaintainSave(rq);
		return rs;
	}

	/**
	 * 任務及公告管理-任務刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理-任務刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainDeleteRs taskMaintainDeleteAction(@RequestBody TaskMaintainDeleteRq rq,
			HttpServletRequest request) {
		TaskMaintainDeleteRs rs = taskMaintainService.taskMaintainDelete(rq);
		return rs;
	}

	/**
	 * 任務及公告管理-任務上下架
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務維護-任務上下架")
	@RequestMapping(value = { "/onOffAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainOnOffRs taskMaintainOnOffAction(@RequestBody TaskMaintainOnOffRq rq,
			HttpServletRequest request) {
		TaskMaintainOnOffRs rs = taskMaintainService.taskMaintainOnOff(rq);
		return rs;
	}

	/**
	 * 任務及公告管理– 機關帶出案件
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理– 機關帶出案件")
	@RequestMapping(value = { "/organCaseAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainOrganCaseRs taskMaintainOrganCaseAction(@RequestBody TaskMaintainOrganCaseRq rq,
			HttpServletRequest request) {
		TaskMaintainOrganCaseRs rs = taskMaintainService.taskMaintainOrganCase(rq);
		return rs;
	}

	/**
	 * 任務及公告管理– 城市幣任務查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "任務及公告管理– 城市幣任務查詢")
	@RequestMapping(value = { "/cityCoinSearchAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TaskMaintainCityCoinSearchRs taskMaintainCityCoinSearchAction(@RequestBody TaskMaintainCityCoinSearchRq rq,
			HttpServletRequest request) {
		TaskMaintainCityCoinSearchRs rs = taskMaintainService.taskMaintainCityCoinSearch(rq);
		return rs;
	}

	@ApiOperation(value = "任務及公告管理-附件下載")
	@RequestMapping(value = { "/downloadAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void taskMaintainDownload(@RequestBody TaskMaintainDownloadRq rq) {
		taskMaintainService.taskMaintainDownload(rq);
	}

}
