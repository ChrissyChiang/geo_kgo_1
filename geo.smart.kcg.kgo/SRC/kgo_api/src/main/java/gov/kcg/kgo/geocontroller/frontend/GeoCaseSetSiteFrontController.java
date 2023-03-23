package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geoservice.GeoCaseSetRentService;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.frontend.rq.*;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseRentServiceComboBoxRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseSetRentMainSearchRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseSetSearchDateRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseSetSiteDetailRs;
import gov.kcg.kgo.service.impl.CaseFormServiceImpl;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.geoservice.GeoCaseSetSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * *前台線上場地租借 API Controller.
 */
@Controller
@RequestMapping("/frontend/caseSetSite")
@Api(tags = {"geo-caseSetsite-controller 前台-線上場地租借"})
public class GeoCaseSetSiteFrontController {


    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetSiteFrontController.class);

    @Autowired
    GeoCaseSetSiteService geoCaseSetSiteService;

	@Autowired
	GeoCaseSetRentService geoCaseSetRentService;

	@Autowired
	private CaseFormServiceImpl caseFormService;

	/**
	 ** 前台-線上預約功能-場地預約查詢
	 */
	@ApiOperation(value = "前台-線上預約臨櫃:搜尋線上租借服務清單")
	@RequestMapping(value = {"/rentMainSearch"}, method = {RequestMethod.POST}, consumes = {
			MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public CaseSetRentMainSearchRs rentMainSearch( ApiRequest rq, HttpServletRequest request) {
		CaseSetRentMainSearchRs rs = geoCaseSetRentService.rentMainSearch(rq, CaseSetCategoryEnum.SITE);
		return rs;
	}

	/**
	 ** 前台-線上預約功能-活動預約查詢
	 */
	@ApiOperation(value = "前台-線上預約臨櫃:搜尋線上租借服務清單")
	@RequestMapping(value = {"/rentActivitySearch"}, method = {RequestMethod.POST}, consumes = {
			MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public CaseSetRentMainSearchRs rentActivitySearch(ApiRequest rq, HttpServletRequest request) {
		CaseSetRentMainSearchRs rs = geoCaseSetRentService.rentMainSearch(rq, CaseSetCategoryEnum.ACTIVITY);
		return rs;
	}

	/**
	 ** 服務案件提供場地下拉選單
	 */
	@ApiOperation(value = "線上場地租借-場地下拉選單")
	@RequestMapping(value = { "/siteComboBox" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseRentServiceComboBoxRs caseSetSiteComBox(@RequestBody CaseSetSiteComboBoxRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController find Site by CaseSetId");
		CaseRentServiceComboBoxRs rs = geoCaseSetSiteService.caseSetSiteComBox(rq);
		return rs;
	}


	/**
	 ** 查詢場地細節
	 */
	@ApiOperation(value = "查詢場地細節")
	@RequestMapping(value = { "/detailQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseSetSiteDetailRs siteDetailSaveAction(@RequestBody GeoSiteDetailQueryRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController SiteDetailSaveAction...");
		CaseSetSiteDetailRs rs = geoCaseSetSiteService.siteDetailQuery(rq);
		return rs;
	}

	/**
	 * *新增或修改場地-下載附件"
	 */
	@ApiOperation(value = "新增或修改場地-下載附件")
	@RequestMapping(value = "/siteDownloadFile", method = RequestMethod.POST)
	@ResponseBody
	public void caseHandlePendingReviewUploadFile(@RequestBody CaseSiteFileDownloadRq rq) {
		geoCaseSetSiteService.siteDownloadFile(rq);
	}

	/**
	 ** 單月每日案件查詢，後續透過單日案件ID查詢預約時段
	 */
	@ApiOperation(value = "線上場地租借-日曆單月每日案件查詢")
	@RequestMapping(value = { "/searchDateAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseSetSearchDateRs searchSiteDate(@RequestBody CaseSetSearchDateRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController searchsiteDate...");
		CaseSetSearchDateRs rs = geoCaseSetRentService.searchSiteDate(rq);
		return rs;
	}


	/**
	 ** 場地時段查詢
	 */
	@ApiOperation(value = "線上場地租借-日曆時段查詢(單日)")
	@RequestMapping(value = { "/caseSetRentTimeQuery" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CaseSetSiteTimeQueryRs caseSetRentTimeQuery(@RequestBody CaseSetRentTimeQueryRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController CaseSetSiteTimeQuery...");
		CaseSetSiteTimeQueryRs rs = geoCaseSetRentService.caseSetRentTimeQuery(rq);
		return rs;
	}


	/**
	 * 前台 - 線上場地/活動 取消預約前檢核彈出確認訊息
	 */
	@ApiOperation(value = "前台 - 線上場地/活動 取消預約前檢核，彈出確認訊息")
	@RequestMapping(value = { "/cancelConfirm" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CancelAppointmentRs cancelConfirm(@RequestBody CancelAppointmentRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController cancelConfirm...");
		CancelAppointmentRs rs = geoCaseSetSiteService.cancelConfirm(rq);
		return rs;
	}

	/**
	 ** 前台 - 線上場地/活動 取消預約
	 */
	@ApiOperation(value = "前台 - 線上場地/活動 取消預約")
	@RequestMapping(value = { "/cancelAppointment" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CancelAppointmentRs cancelAppointment(@RequestBody CancelAppointmentRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController cancelAppointment...");
		CancelAppointmentRs rs = geoCaseSetSiteService.cancelAppointment(rq);
		return rs;
	}
	
	/**
	 ** 查詢場地
	 */
	@ApiOperation(value = "查詢場地")
	@RequestMapping(value = { "/siteQuery" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SiteQueryRs siteQuery(@RequestBody GeoSiteQueryRq rq) {
		LOGGER.info("GeoCaseSetSiteFrontController siteQuery...");
		SiteQueryRs rs = geoCaseSetSiteService.siteQuery(rq);
		return rs;
	}
	
//	/**
//	 ** 查詢服務
//	 */
//	@ApiOperation(value = "查詢服務")
//	@RequestMapping(value = { "/caseSetQuery" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public CaseSetQueryRs caseSetQuery(@RequestBody caseSetQueryRq rq) {
//		LOGGER.info("GeoCaseSetSiteFrontController siteQuery...");
//		CaseSetQueryRs rs = geoCaseSetSiteService.caseSetQuery(rq);
//		return rs;
//	}
	
//	/**
//	 ** 查詢服務之場地
//	 */
//	@ApiOperation(value = "查詢服務之場地")
//	@RequestMapping(value = { "/caseSetSiteQuery" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public CaseSetSiteQueryRs caseSetSiteQuery(@RequestBody caseSetSiteQueryRq rq) {
//		LOGGER.info("GeoCaseSetSiteFrontController caseSetSiteQuery...");
//		CaseSetSiteQueryRs rs = geoCaseSetSiteService.caseSetSiteQuery(rq);
//		return rs;
//	}
//

	
//	/**
//	 ** 提供個人預約繳費資訊清單 roy
//	 */
//	@ApiOperation(value = "提供個人預約繳費資訊清單")
//	@RequestMapping(value = { "/payment/personalPayQuery" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public PersonalPayQueryRs personalPayQuery(@RequestBody personalPayQueryRq rq) {
//		LOGGER.info("personalPay List Query...");
//		PersonalPayQueryRs rs = geoCaseSetSiteService.personalPayQuery(rq);
//		return rs;
//	}
}
