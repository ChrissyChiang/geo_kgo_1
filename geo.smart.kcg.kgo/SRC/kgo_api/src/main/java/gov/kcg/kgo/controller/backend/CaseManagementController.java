package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import gov.kcg.kgo.geoviewmodel.backend.rs.GeoOrganDiscountComboBoxRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq.CaseManagerComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.CaseManagementOrganSelectQueryRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
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
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.service.CaseManagementService;

import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rq.CaseManagementCaseOrderHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.CaseManagementCaseOrderHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.CaseManagementCaseOrderSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rs.CaseManagementCaseOrderSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rq.CaseManagementCaseSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.CaseManagementCaseSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rq.CaseManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.CaseManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rq.CaseManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.CaseManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.CaseManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rq.CaseManagementStatusUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rs.CaseManagementStatusUpdateRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/CaseManagement")
@Api(tags = {"case-management-controller 系統設定/服務管理"})
public class CaseManagementController extends BaseController {

	/** GEO 20210814 add **/
	private static final Logger LOGGER = LoggerFactory.getLogger(CaseManagementController.class);

	@Autowired
	CaseManagementService caseManagementService;

	@Autowired
	BackEndCommonService backEndCommonService;

	/**
	 * 系統設定/服務管理-初始畫面(GEO 20210814 changed)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務案件清單-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementHomeRs CaseManagementHomeAction() {
		CaseManagementHomeRs rs = caseManagementService.caseManagementHome();
		//LOGGER.info("CaseManagementController CaseManagementHomeAction...");
		return rs;
	}

	/**
	 * 服務案件管理-服務管理者下拉式選單查詢
	 *
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = "服務案件管理-服務管理者下拉式選單查詢")
	@RequestMapping(value = { "/caseManagerComboBoxQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagerComboBoxQueryRs CaseManagerComboBoxQueryAction(@RequestBody CaseManagerComboBoxQueryRq rq){
		return caseManagementService.caseManagerComboBoxQuery(rq);
	}


	/**
	 * 服務案件清單-案件查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "後台案件維護管理-案件查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementQueryRs CaseManagementQueryAction(@RequestBody CaseManagementQueryRq rq,
			HttpServletRequest request) {
		CaseManagementQueryRs rs = caseManagementService.caseManagementQuery(rq);
		return rs;
	}

	/**
	 * 服務案件清單-案件狀態更改 (上架/下架/立即受理)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務案件清單-案件狀態更改 (上架/下架/立即受理)")
	@RequestMapping(value = { "/statusUpdateAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementStatusUpdateRs StatusUpdateGetByOrganIdAction(@RequestBody CaseManagementStatusUpdateRq rq,
			HttpServletRequest request) {
		CaseManagementStatusUpdateRs rs = caseManagementService.statusUpdate(rq);
		return rs;
	}

	/**
	 * 服務案件清單-案件刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務案件清單-案件刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementDeleteRs classDeleteAction(@RequestBody CaseManagementDeleteRq rq,
			HttpServletRequest request) {
		CaseManagementDeleteRs rs = caseManagementService.caseManagementDelete(rq);
		return rs;
	}

	/**
	 * 服務案件清單-案件排序-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務案件清單-案件排序-初始畫面")
	@RequestMapping(value = { "/caseOrderHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementCaseOrderHomeRs CaseManagementCaseOrderHomeAction(
			@RequestBody CaseManagementCaseOrderHomeRq rq, HttpServletRequest request) {
		CaseManagementCaseOrderHomeRs rs = caseManagementService.caseManagementCaseOrderHome(rq);
		return rs;
	}

	/**
	 * 服務案件清單-案件排序-修改
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務案件清單-案件排序-修改")
	@RequestMapping(value = { "/caseOrderSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementCaseOrderSaveRs CaseManagementCaseOrderSaveAction(
			@RequestBody CaseManagementCaseOrderSaveRq rq, HttpServletRequest request) {
		CaseManagementCaseOrderSaveRs rs = caseManagementService.caseManagementCaseOrderSave(rq);
		return rs;
	}

	/**
	 * 服務案件清單-案件維護-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 服務案件清單-案件維護-初始畫面")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementEditHomeRs CaseManagementEditHomeAction(@RequestBody CaseManagementEditHomeRq rq,
			HttpServletRequest request) {
		CaseManagementEditHomeRs rs = caseManagementService.caseManagementEditHome(rq);
		return rs;
	}

	/**
	 * 服務案件清單-案件維護-儲存
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 服務案件清單-案件維護-儲存")
	@RequestMapping(value = { "/caseSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementCaseSaveRs CaseManagementCaseSaveAction(@RequestBody CaseManagementCaseSaveRq rq,
			HttpServletRequest request) {
		LOGGER.info("CaseManagementController CaseManagementCaseSaveAction...");
		CaseManagementCaseSaveRs rs = caseManagementService.caseManagementCaseSave(rq);
		return rs;
	}

	/**
	 * GEO 20211115 add for 跨機關檢視
	 * 服務案件清單-取得所有機關選單
	 *
	 * @return
	 */
	@ApiOperation(value = "* 服務案件清單-取得所有機關選單 for 跨機關檢視")
	@RequestMapping(value = { "/organSelectComboBox" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseManagementOrganSelectQueryRs accountHomeAction(@RequestBody ApiRequest rq) {
		CaseManagementOrganSelectQueryRs rs = caseManagementService.getOrganSelectComboBox();
		return rs;
	}


	/**
	 * GEO 20221107
	 * 服務案件編輯-機關繳費折扣下拉
	 *
	 * @return
	 */
	@ApiOperation(value = "* 服務案件編輯-機關繳費折扣下拉")
	@RequestMapping(value = { "/discountComboBox" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GeoOrganDiscountComboBoxRs discountComboBox(@RequestBody CaseManagementEditHomeRq rq) {
		GeoOrganDiscountComboBoxRs rs = caseManagementService.getDiscountComboBox(rq);
		return rs;
	}

}
