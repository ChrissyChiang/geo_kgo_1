package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rq.AreaOrganSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rs.AreaOrganSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rq.AreaOrganSelectZipHomeRq;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rs.AreaOrganSelectZipHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rq.OrganComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rq.OrganSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rs.OrganSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rq.OrganSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.OrganSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rq.OrganUnitUserSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rs.OrganUnitUserSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rq.OrganUnitUserSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rs.OrganUnitUserSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rq.UnitQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rs.UnitQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rq.UnitComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.UnitComboBoxQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/common")
@Api(tags = {"backend-common-controller 共用"})
public class BackEndCommonController extends BaseController {

	@Autowired
	BackEndCommonService backEndCommonService;

	/**
	 * 共用-人員清單Popup-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-人員清單Popup-初始畫面")
	@RequestMapping(value = { "/organUnitUserSelectHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitUserSelectHomeRs organUnitUserSelectHomeAction(@RequestBody OrganUnitUserSelectHomeRq rq,
			HttpServletRequest request) {
		OrganUnitUserSelectHomeRs rs = backEndCommonService.organUnitUserSelectHomeAction(rq);
		return rs;
	}

	/**
	 * 共用-人員清單Popup-查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-人員清單Popup-查詢")
	@RequestMapping(value = { "/organUnitUserSelectQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganUnitUserSelectQueryRs organUnitUserSelectQueryAction(@RequestBody OrganUnitUserSelectQueryRq rq,
			HttpServletRequest request) {
		OrganUnitUserSelectQueryRs rs = backEndCommonService.organUnitUserSelectQueryAction(rq);
		return rs;
	}

	/**
	 * 共用-人員清單Popup - 單位下拉式選單查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 共用-人員清單Popup - 單位下拉式選單查詢")
	@RequestMapping(value = { "/unitComboBoxQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UnitComboBoxQueryRs unitComboBoxQueryAction(@RequestBody UnitComboBoxQueryRq rq,
			HttpServletRequest request) {
		UnitComboBoxQueryRs rs = backEndCommonService.unitComboBoxQueryAction(rq);
		return rs;
	}
	
	/**
	 * 共用-機關單位下拉選單
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 共用-機關單位下拉選單")
	@RequestMapping(value = { "/organComboBoxQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganComboBoxQueryRs organComboBoxQueryAction(@RequestBody OrganComboBoxQueryRq rq,
			HttpServletRequest request) {
		OrganComboBoxQueryRs rs = backEndCommonService.organComboBoxQueryAction(rq);
		return rs;
	}

	/**
	 * 共用-機關列表Popup-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-機關列表Popup-初始畫面")
	@RequestMapping(value = { "/organSelectHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganSelectHomeRs organSelectHomeAction(@RequestBody OrganSelectHomeRq rq, HttpServletRequest request) {
		OrganSelectHomeRs rs = backEndCommonService.organSelectHomeAction(rq);
		return rs;
	}
	
	/**
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-機關列表Popup-查詢")
	@RequestMapping(value = { "/organSelectQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganSelectQueryRs organSelectQueryAction(@RequestBody OrganSelectQueryRq rq, HttpServletRequest request) {
		OrganSelectQueryRs rs = backEndCommonService.organSelectQueryAction(rq);
		return rs;
	}

	/**
	 * 共用-機關科室查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-機關科室查詢")
	@RequestMapping(value = { "/unitQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UnitQueryRs unitQueryAction(@RequestBody UnitQueryRq rq, HttpServletRequest request) {
		UnitQueryRs rs = backEndCommonService.unitQueryAction(rq);
		return rs;
	}

	/**
	 * 共用-區機關選擇-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-區機關選擇-初始畫面")
	@RequestMapping(value = { "/areaOrganSelect/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AreaOrganSelectHomeRs areaOrganSelectHomeAction(@RequestBody AreaOrganSelectHomeRq rq,
			HttpServletRequest request) {
		AreaOrganSelectHomeRs rs = backEndCommonService.areaOrganSelectHomeAction(rq);
		return rs;
	}

	/**
	 * 共用-區機關選擇-鄉鎮選擇-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "共用-區機關選擇-鄉鎮選擇-初始畫面")
	@RequestMapping(value = { "/areaOrganSelect/zipHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AreaOrganSelectZipHomeRs areaOrganSelectZipHomeAction(@RequestBody AreaOrganSelectZipHomeRq rq,
			HttpServletRequest request) {
		AreaOrganSelectZipHomeRs rs = backEndCommonService.areaOrganSelectZipHomeAction(rq);
		return rs;
	} //areaOrganSelectZipHomeAction

}
