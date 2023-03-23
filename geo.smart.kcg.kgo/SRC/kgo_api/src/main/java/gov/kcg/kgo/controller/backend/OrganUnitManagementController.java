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
import gov.kcg.kgo.service.OrganUnitManagementService;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rq.OrganUnitManagementUnitComboBoxRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rs.OrganUnitManagementUnitComboBoxRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rq.OrganUnitManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rs.OrganUnitManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rq.OrganUnitManagementEditRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rs.OrganUnitManagementEditRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rq.OrganUnitManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rs.OrganUnitManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.OrganUnitManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rq.OrganUnitManagementOrganQueryRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rs.OrganUnitManagementOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rq.OrganUnitManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.OrganUnitManagementQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/organUnitManagement")
public class OrganUnitManagementController extends BaseController {

	@Autowired
	OrganUnitManagementService organUnitManagementService;

	/**
	 * 機關科室管理-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementHomeRs organUnitManagementHomeAction() {
		OrganUnitManagementHomeRs rs = organUnitManagementService.organUnitManagementHome();
		return rs;
	}

	/**
	 * 機關科室管理-取得單位下拉式選單
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-取得單位下拉式選單")
	@RequestMapping(value = { "/unitComboBoxGetByOrganIdAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementUnitComboBoxRs unitComboBoxGetByOrganIdAction(
			@RequestBody OrganUnitManagementUnitComboBoxRq rq, HttpServletRequest request) {
		OrganUnitManagementUnitComboBoxRs rs = organUnitManagementService.unitComboBoxByOrganId(rq);
		return rs;
	}

	/**
	 * 機關科室管理-機關科室查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-機關科室查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementQueryRs organUnitManagementQueryAction(@RequestBody OrganUnitManagementQueryRq rq,
			HttpServletRequest request) {
		OrganUnitManagementQueryRs rs = organUnitManagementService.organUnitManagementQuery(rq);
		return rs;
	}

	/**
	 * 機關科室管理-機關科室機關查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-機關科室機關查詢")
	@RequestMapping(value = { "/organQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementOrganQueryRs organUnitManagementOrganQueryAction(
			@RequestBody OrganUnitManagementOrganQueryRq rq, HttpServletRequest request) {
		OrganUnitManagementOrganQueryRs rs = organUnitManagementService.organUnitManagementOrganQuery(rq);
		return rs;
	}

	/**
	 * 機關科室管理-機關科室維護(新增/修改)初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-機關科室維護(新增/修改)初始畫面")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementEditHomeRs organUnitManagementEditHomeAction(
			@RequestBody OrganUnitManagementEditHomeRq rq, HttpServletRequest request) {
		OrganUnitManagementEditHomeRs rs = organUnitManagementService.organUnitManagementEditHome(rq);
		return rs;
	}

	/**
	 * 機關科室管理-機關科室維護(新增/修改)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-機關科室維護(新增/修改)")
	@RequestMapping(value = { "/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementEditRs organUnitManagementEditAction(@RequestBody OrganUnitManagementEditRq rq,
			HttpServletRequest request) {
		OrganUnitManagementEditRs rs = organUnitManagementService.organUnitManagementEdit(rq);
		return rs;
	}

	/**
	 * 機關科室管理-機關科室刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "機關科室管理-機關科室刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitManagementDeleteRs classDeleteAction(@RequestBody OrganUnitManagementDeleteRq rq,
			HttpServletRequest request) {
		OrganUnitManagementDeleteRs rs = organUnitManagementService.organUnitManagementDelete(rq);
		return rs;
	}
}
