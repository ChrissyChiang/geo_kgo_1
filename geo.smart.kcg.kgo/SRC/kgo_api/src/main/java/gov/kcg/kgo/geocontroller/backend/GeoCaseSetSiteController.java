package gov.kcg.kgo.geocontroller.backend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoSiteEditUserComboxQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.bean.CaseManagerComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import gov.kcg.kgo.geoservice.GeoCaseSetSiteService;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.SiteManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rq.TaskMaintainSaveRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rs.TaskMaintainSaveRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * *後台線上場地租借 API Controller. Roy
 */
@Controller
@RequestMapping("/backend/CaseSetSite")
@Api(tags = {"geo-caseSetsite-controller 後台-線上場地租借-場地建立"})
public class GeoCaseSetSiteController {


    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetSiteController.class);

    @Autowired
    GeoCaseSetSiteService geoCaseSetSiteService;


	/**
	 ** 場地初始資訊
	 */
	@ApiOperation(value = "場地-初始資訊")
	@RequestMapping(value = { "/siteMgt/homeAction" }, method = { RequestMethod.POST })
	@ResponseBody
	public SiteManagementHomeRs HomeAction() {
		SiteManagementHomeRs rs = geoCaseSetSiteService.siteManagementHome();
		return rs;
	}

	@ApiOperation(value = "場地-初始畫面場地-取得建立者下拉選單")
	@RequestMapping(value = { "/siteMgt/editUserComboBox" }, method = { RequestMethod.POST })
	@ResponseBody
	public GeoSiteEditUserComboBoxQueryRs UnitBoxForEditUser(GeoSiteEditUserComboBoxQueryRq rq) {
		GeoSiteEditUserComboBoxQueryRs rs = geoCaseSetSiteService.getEditUserComboBox(rq);
		return rs ;
	}

	/**
	 ** 場地初始資訊
	 */
	@ApiOperation(value = "場地-新增或編輯")
	@RequestMapping(value = { "/siteMgt/siteEdit" }, method = { RequestMethod.POST })
	@ResponseBody
	public GeoSiteEditRs EditAction(@RequestBody GeoSiteEditRq rq) {
		GeoSiteEditRs rs = geoCaseSetSiteService.siteEdit(rq);
		return rs;
	}

	/**
	 ** 新增或修改場地
	 */
	@ApiOperation(value = "場地-儲存")
	@RequestMapping(value = { "/siteMgt/siteSave" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })	
	@ResponseBody
	public GeoSiteSaveRs siteSaveAction(@RequestBody GeoSiteSaveRq rq) {
		GeoSiteSaveRs rs = geoCaseSetSiteService.siteSave(rq);
		return rs;
	}

	/**
	 ** 查詢場地
	 */
	@ApiOperation(value = "場地-查詢場地")
	@RequestMapping(value = { "/siteMgt/siteQuery" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SiteQueryRs siteQuery(@RequestBody GeoSiteQueryRq rq) {
		LOGGER.info("GeoCaseSetSiteController siteQuery...");
		SiteQueryRs rs = geoCaseSetSiteService.siteQuery(rq);
		return rs;
	}


	/**
     ** 場地上下架
     */
	@ApiOperation(value = "場地-上下架")
	@RequestMapping(value = { "/siteMgt/siteStatusSave" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GeoSiteStatusUpdateRs siteStatusSave(@RequestBody GeoSiteStatusRq rq) {
		LOGGER.info("GeoCaseSetSiteController siteStatusSave...");
		GeoSiteStatusUpdateRs rs = geoCaseSetSiteService.siteStatusSave(rq);
		return rs;
	}	
	
    /**
     ** 場地刪除
     */	
	@ApiOperation(value = "場地-刪除")
	@RequestMapping(value = { "/siteMgt/siteDelete" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GeoSiteDeleteRs siteDelete(@RequestBody GeoSiteDeleteRq rq) {
		LOGGER.info("GeoCaseSetSiteController siteStatusSave...");
		GeoSiteDeleteRs rs = geoCaseSetSiteService.siteDelete(rq);
		return rs;
	}

	/**
	 ** 場地案件 附件刪除
	 */
	@ApiOperation(value = "# 場地案件檢視 - 附件刪除")
	@RequestMapping(value = { "/siteMgt/deleteFile" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GeoSiteDeleteRs siteDeleteFile(@RequestBody GeoSiteDeleteFileRq rq) {
		LOGGER.info("GeoCaseSetSiteController siteDeleteFile...");
		GeoSiteDeleteRs rs = geoCaseSetSiteService.siteDeleteFile(rq);
		return rs;
	}


}
