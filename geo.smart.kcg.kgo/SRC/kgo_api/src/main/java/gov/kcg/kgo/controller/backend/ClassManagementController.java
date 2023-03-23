package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import gov.kcg.kgo.controller.frontend.auth.FrontendAuthController;
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
import gov.kcg.kgo.service.ClassManagementService;
import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rq.ClassManagementClassDeleteRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rs.ClassManagementClassDeleteRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classHome.rs.ClassManagementClassHomeRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rq.ClassManagementClassOnOffRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rs.ClassManagementClassOnOffRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rq.ClassManagementClassQueryRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.ClassManagementClassQueryRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rq.ClassManagementClassUpdateRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rs.ClassManagementClassUpdateRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rq.ClassManagementClassUpdateHomeRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rs.ClassManagementClassUpdateHomeRs;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rq.ClassManagementSubClassQueryRq;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rs.ClassManagementSubClassQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/classManagement")
public class ClassManagementController extends BaseController {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassManagementController.class);

	@Autowired
	ClassManagementService classManagementService;

	/**
	 * 分類維護-主畫面初始
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護-查詢功能")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementClassHomeRs classHomeAction() {
		LOGGER.info("ClassManagementController classHomeAction...");
		ClassManagementClassHomeRs rs = classManagementService.classManagementClassHome();
		return rs;
	} //classHomeAction

	/**
	 * 分類維護-主畫面搜尋
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護-查詢功能")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementClassQueryRs classQueryAction(@RequestBody ClassManagementClassQueryRq rq,
			HttpServletRequest request) {
		ClassManagementClassQueryRs rs = classManagementService.classManagementClassQuery(rq);
		return rs;
	}

	/**
	 * 分類維護功能-類別新增/維護-畫面初始
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護功能-類別新增/維護-主畫面")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementClassUpdateHomeRs classUpdateHomeAction(@RequestBody ClassManagementClassUpdateHomeRq rq,
			HttpServletRequest request) {
		ClassManagementClassUpdateHomeRs rs = classManagementService.classManagementClassUpdateHome(rq);
		return rs;
	}

	/**
	 * 分類維護功能-類別新增/維護
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護功能-類別新增/維護")
	@RequestMapping(value = { "/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementClassUpdateRs classUpdateAction(@RequestBody ClassManagementClassUpdateRq rq,
			HttpServletRequest request) {
		ClassManagementClassUpdateRs rs = classManagementService.classManagementClassUpdate(rq);
		return rs;
	}

	/**
	 * 分類維護功能-類別刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護功能-類別刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementClassDeleteRs classDeleteAction(@RequestBody ClassManagementClassDeleteRq rq,
			HttpServletRequest request) {
		ClassManagementClassDeleteRs rs = classManagementService.classManagementClassDelete(rq);
		return rs;
	}

	/**
	 * 分類維護功能-上下架
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護功能-上下架")
	@RequestMapping(value = { "/onOffAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementClassOnOffRs classOnOffAction(@RequestBody ClassManagementClassOnOffRq rq,
			HttpServletRequest request) {
		ClassManagementClassOnOffRs rs = classManagementService.classManagementClassOnOff(rq);
		return rs;
	}

	/**
	 * 分類維護功能-取得次分類名稱下拉式選單
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分類維護功能-取得次分類名稱下拉式選單")
	@RequestMapping(value = { "/subClassQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ClassManagementSubClassQueryRs subClassQueryAction(@RequestBody ClassManagementSubClassQueryRq rq,
			HttpServletRequest request) {
		ClassManagementSubClassQueryRs rs = classManagementService.classManagementSubClassQuery(rq);
		return rs;
	}

}
