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
import gov.kcg.kgo.service.QuestionMaintainService;
import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rq.QuestionMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rs.QuestionMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rq.QuestionMaintainEditRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rs.QuestionMaintainEditRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rq.QuestionMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rs.QuestionMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rq.QuestionMaintainOnOffRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rs.QuestionMaintainOnOffRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rq.QuestionMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.QuestionMaintainQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/questionMaintain")
public class QuestionMaintainController extends BaseController {

	@Autowired
	QuestionMaintainService questionMaintainService;

	/**
	 * 常見問題維護-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題維護-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QuestionMaintainQueryRs questionMaintainHomeAction() {
		QuestionMaintainQueryRs rs = questionMaintainService.questionMaintainHome();
		return rs;
	}

	/**
	 * 常見問題維護-問題查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題維護-問題查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QuestionMaintainQueryRs questionMaintainQueryAction(@RequestBody QuestionMaintainQueryRq rq,
			HttpServletRequest request) {
		QuestionMaintainQueryRs rs = questionMaintainService.questionMaintainQuery(rq);
		return rs;
	}

	/**
	 * 常見問題維護-問題維護(新增/修改)初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題維護-問題維護(新增/修改)初始畫面")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QuestionMaintainEditHomeRs questionMaintainEditHomeAction(@RequestBody QuestionMaintainEditHomeRq rq,
			HttpServletRequest request) {
		QuestionMaintainEditHomeRs rs = questionMaintainService.questionMaintainEditHome(rq);
		return rs;
	}

	/**
	 * 常見問題維護-問題維護(新增/修改)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題維護-問題維護(新增/修改)")
	@RequestMapping(value = { "/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QuestionMaintainEditRs questionMaintainEditAction(@RequestBody QuestionMaintainEditRq rq,
			HttpServletRequest request) {
		QuestionMaintainEditRs rs = questionMaintainService.questionMaintainEdit(rq);
		return rs;
	}

	/**
	 * 常見問題維護-問題上下架
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題維護-問題上下架")
	@RequestMapping(value = { "/onOffAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QuestionMaintainOnOffRs questionMaintainOnOffAction(@RequestBody QuestionMaintainOnOffRq rq,
			HttpServletRequest request) {
		QuestionMaintainOnOffRs rs = questionMaintainService.questionMaintainOnOff(rq);
		return rs;
	}

	/**
	 * 常見問題維護-問題刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見問題維護-問題刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public QuestionMaintainDeleteRs questionMaintainEditAction(@RequestBody QuestionMaintainDeleteRq rq,
			HttpServletRequest request) {
		QuestionMaintainDeleteRs rs = questionMaintainService.questionMaintainDelete(rq);
		return rs;
	}

}
