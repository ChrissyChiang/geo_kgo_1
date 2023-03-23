package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioSaveRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
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

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.InternetApplyService;
import gov.kcg.kgo.viewModel.backend.common.fileUpload.rs.UploadFileActionRs;
import gov.kcg.kgo.viewModel.backend.counterApply.fileUpload.rq.CounterApplyFileUploadRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rq.InternetApplyAcceptDateHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rs.InternetApplyAcceptDateHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rq.InternetApplyAcceptSetAreaQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.InternetApplyAcceptSetAreaQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq.InternetApplyAcceptSetAreaSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rs.InternetApplyAcceptSetAreaSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rq.InternetApplyAcceptSetHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rs.InternetApplyAcceptSetHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rq.InternetApplyAcceptSetOfficerQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rs.InternetApplyAcceptSetOfficerQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rq.InternetApplyAcceptSetOfficerSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rs.InternetApplyAcceptSetOfficerSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rq.InternetApplyAcceptSetUnitQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rs.InternetApplyAcceptSetUnitQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rq.InternetApplyAcceptSetUnitSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rs.InternetApplyAcceptSetUnitSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rq.InternetApplyDescriptionDeleteRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rs.InternetApplyDescriptionDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rq.InternetApplyDescriptionHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.InternetApplyDescriptionHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rq.InternetApplyDescriptionSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rs.InternetApplyDescriptionSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.update.rq.InternetApplyDescriptionUpdateRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.update.rs.InternetApplyDescriptionUpdateRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rq.InternetApplyFormSetColumnHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rs.InternetApplyFormSetColumnHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.InternetApplyFormSetGroupColumnSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.InternetApplyFormSetOrganGroupColumnSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rs.InternetApplyFormSetGroupColumnSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rq.InternetApplyFormSetGroupHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rs.InternetApplyFormSetGroupHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rq.InternetApplyFormSetHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.InternetApplyFormSetHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rq.InternetApplyFormSetMyDataColumnComboBoxRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rs.InternetApplyFormSetMyDataColumnComboBoxRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rq.InternetApplyFormSetMydataComboBoxRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rs.InternetApplyFormSetMydataComboBoxRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rq.InternetApplyFormSetMydataDeleteRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rs.InternetApplyFormSetMydataDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rq.InternetApplyFormSetMydataHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.InternetApplyFormSetMydataHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rq.InternetApplyFormSetMydataSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rs.InternetApplyFormSetMydataSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rq.InternetApplyIdentityVerifyHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.InternetApplyIdentityVerifyHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rq.InternetApplyLimitPeriodHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rs.InternetApplyLimitPeriodHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rq.InternetApplySaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rq.OrganDiscountRatioQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.InternetApplySaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rq.InternetApplyServiceHtmlHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rs.InternetApplyServiceHtmlHomeRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/internetApply")
@Api(tags = {"internet-apply-controller 網路申辦"})
public class InternetApplyController extends BaseController {

	/** GEO 20210814 add **/
	private static final Logger LOGGER = LoggerFactory.getLogger(InternetApplyController.class);

	@Autowired
	InternetApplyService internetApplyService;

	@Autowired
	CommonService commonService;

	/**
	 * 網路申辦-服務宣告設定-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-服務宣告設定-初始畫面")
	@RequestMapping(value = { "/serviceHtml/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyServiceHtmlHomeRs internetApplyServiceHtmlHomeAction(
			@RequestBody InternetApplyServiceHtmlHomeRq rq, HttpServletRequest request) {
		InternetApplyServiceHtmlHomeRs rs = internetApplyService.internetApplyServiceHtmlHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-身分驗證設定-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-身分驗證設定-初始畫面")
	@RequestMapping(value = { "/identityVerify/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyIdentityVerifyHomeRs internetApplyIdentityVerifyHomeAction(
			@RequestBody InternetApplyIdentityVerifyHomeRq rq, HttpServletRequest request) {
		InternetApplyIdentityVerifyHomeRs rs = internetApplyService.internetApplyIdentityVerifyHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-初始畫面")
	@RequestMapping(value = { "/acceptSet/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetHomeRs internetApplyAcceptSetHomeAction(@RequestBody InternetApplyAcceptSetHomeRq rq,
			HttpServletRequest request) {
		InternetApplyAcceptSetHomeRs rs = internetApplyService.internetApplyAcceptSetHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理機關查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-受理機關查詢")
	@RequestMapping(value = { "/acceptSet/unitQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetUnitQueryRs internetApplyAcceptSetUnitQueryAction(
			@RequestBody InternetApplyAcceptSetUnitQueryRq rq, HttpServletRequest request) {
		InternetApplyAcceptSetUnitQueryRs rs = internetApplyService.internetApplyAcceptSetUnitQuery(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-承辦人查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-承辦人查詢")
	@RequestMapping(value = { "/acceptSet/officerQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetOfficerQueryRs internetApplyAcceptSetOfficerQueryAction(
			@RequestBody InternetApplyAcceptSetOfficerQueryRq rq, HttpServletRequest request) {
		InternetApplyAcceptSetOfficerQueryRs rs = internetApplyService.internetApplyAcceptSetOfficerQuery(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理區機關查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-受理區機關查詢")
	@RequestMapping(value = { "/acceptSet/areaQueryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetAreaQueryRs internetApplyAcceptSetAreaQueryAction(
			@RequestBody InternetApplyAcceptSetAreaQueryRq rq, HttpServletRequest request) {
		InternetApplyAcceptSetAreaQueryRs rs = internetApplyService.internetApplyAcceptSetAreaQuery(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理機關新增
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-受理機關新增")
	@RequestMapping(value = { "/acceptSet/unitSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetUnitSaveRs internetApplyAcceptSetUnitSaveAction(
			@RequestBody InternetApplyAcceptSetUnitSaveRq rq, HttpServletRequest request) {
		InternetApplyAcceptSetUnitSaveRs rs = internetApplyService.internetApplyAcceptSetUnitSave(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-承辦人新增
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-承辦人新增")
	@RequestMapping(value = { "/acceptSet/officerSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetOfficerSaveRs internetApplyAcceptSetOfficerSaveAction(
			@RequestBody InternetApplyAcceptSetOfficerSaveRq rq, HttpServletRequest request) {
		InternetApplyAcceptSetOfficerSaveRs rs = internetApplyService.internetApplyAcceptSetOfficerSave(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理區機關新增
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理機關設定-受理區機關新增")
	@RequestMapping(value = { "/acceptSet/areaSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptSetAreaSaveRs internetApplyAcceptSetAreaSaveAction(
			@RequestBody InternetApplyAcceptSetAreaSaveRq rq, HttpServletRequest request) {
		InternetApplyAcceptSetAreaSaveRs rs = internetApplyService.internetApplyAcceptSetAreaSave(rq);
		return rs;
	}

	/**
	 * 網路申辦-受理期間設定-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-受理期間設定-初始畫面")
	@RequestMapping(value = { "/acceptDate/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyAcceptDateHomeRs internetApplyAcceptDateHomeAction(
			@RequestBody InternetApplyAcceptDateHomeRq rq, HttpServletRequest request) {
		InternetApplyAcceptDateHomeRs rs = internetApplyService.internetApplyAcceptDateHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-限辦期限設定-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-限辦期限設定-初始畫面")
	@RequestMapping(value = { "/limitPeriod/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyLimitPeriodHomeRs internetApplyLimitPeriodHomeAction(
			@RequestBody InternetApplyLimitPeriodHomeRq rq, HttpServletRequest request) {
		InternetApplyLimitPeriodHomeRs rs = internetApplyService.internetApplyLimitPeriodHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-網路申請說明-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "網路申辦-網路申請說明-初始畫面")
	@RequestMapping(value = { "/description/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyDescriptionHomeRs internetApplyDescriptionHomeAction(
			@RequestBody InternetApplyDescriptionHomeRq rq, HttpServletRequest request) {
		InternetApplyDescriptionHomeRs rs = internetApplyService.internetApplyDescriptionHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-說明附件上傳
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-說明附件上傳")
	@RequestMapping(value = { "/description/attachmentUploadAction" }, method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UploadFileActionRs attachUploadAction(@RequestParam(value = "file", required = true) MultipartFile file,
			CounterApplyFileUploadRq rq, HttpServletRequest request) {
		String caseSetId = rq.getCaseSetId();
		String attachmentUploadBasePath = commonService.getAttachmentUploadBasePath(caseSetId,
				ApplyTypeEnum.E.getValue());
		UploadFileActionRs rs = commonService.uploadFileAction(file, attachmentUploadBasePath);
		return rs;
	}

	/**
	 * 網路申辦-申請說明資料儲存
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-申請說明資料儲存")
	@RequestMapping(value = { "/description/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyDescriptionSaveRs internetApplyDescriptionSaveAction(
			@RequestBody InternetApplyDescriptionSaveRq rq, HttpServletRequest request) {
		InternetApplyDescriptionSaveRs rs = internetApplyService.internetApplyDescriptionSave(rq);
		return rs;
	}

	/**
	 * 網路申辦-申請說明資料-資料更新
	 *
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-申請說明資料-資料更新")
	@RequestMapping(value = { "/description/updateAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyDescriptionUpdateRs internetApplyDescriptionUpdateAction(
			@RequestBody InternetApplyDescriptionUpdateRq rq, HttpServletRequest request) {
		InternetApplyDescriptionUpdateRs rs = internetApplyService.internetApplyDescriptionUpdate(rq);
		return rs;
	}

	/**
	 * 網路申辦-申請說明資料刪除
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-申請說明資料刪除")
	@RequestMapping(value = { "/description/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyDescriptionDeleteRs internetApplyDescriptionDeleteAction(
			@RequestBody InternetApplyDescriptionDeleteRq rq, HttpServletRequest request) {
		InternetApplyDescriptionDeleteRs rs = internetApplyService.internetApplyDescriptionDelete(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-初始畫面
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("* 網路申辦-表單設定-初始畫面")
	@RequestMapping(value = { "/formSet/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetHomeRs internetApplyFormSetHomeAction(@RequestBody InternetApplyFormSetHomeRq rq,
			HttpServletRequest request) {
		LOGGER.info("InternetApplyController internetApplyFormSetHomeAction...");
		InternetApplyFormSetHomeRs rs = internetApplyService.internetApplyFormSetHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-群組維護-初始畫面
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation(" 網路申辦-表單設定-群組維護-初始畫面")
	@RequestMapping(value = { "/formSet/groupHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetGroupHomeRs internetApplyFormSetGroupHomeAction(
			@RequestBody InternetApplyFormSetGroupHomeRq rq, HttpServletRequest request) {
		InternetApplyFormSetGroupHomeRs rs = internetApplyService.internetApplyFormSetGroupHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-欄位維護-初始畫面
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("* 網路申辦-表單設定-欄位維護-初始畫面")
	@RequestMapping(value = { "/formSet/columnHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetColumnHomeRs internetApplyFormSetColumnHomeAction(
			@RequestBody InternetApplyFormSetColumnHomeRq rq, HttpServletRequest request) {
		InternetApplyFormSetColumnHomeRs rs = internetApplyService.internetApplyFormSetColumnHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單")
	@RequestMapping(value = { "/formSet/myDataColumnComboBoxAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetMyDataColumnComboBoxRs internetApplyFormSetMyDataColumnComboBoxAction(
			@RequestBody InternetApplyFormSetMyDataColumnComboBoxRq rq, HttpServletRequest request) {
		InternetApplyFormSetMyDataColumnComboBoxRs rs = internetApplyService
				.internetApplyFormSetMyDataColumnComboBox(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-欄位群組維護-進版儲存
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("* 網路申辦-表單設定-欄位群組維護-進版儲存")
	@RequestMapping(value = { "/formSet/groupColumnSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetGroupColumnSaveRs internetApplyFormSetGroupColumnSaveAction(
			@RequestBody InternetApplyFormSetGroupColumnSaveRq rq, HttpServletRequest request) {
		LOGGER.info("InternetApplyController internetApplyFormSetGroupColumnSaveAction...");
		InternetApplyFormSetGroupColumnSaveRs rs = internetApplyService.internetApplyFormSetGroupColumnSave(rq);
		return rs;
	}

	/**
	 *  GEO 20211109 add 機關審核表單
	 * 	網路申辦-表單設定- 機關審核表單 欄位群組維護-進版儲存
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation("* 網路申辦-表單設定- 機關審核表單欄位群組維護-進版儲存")
	@RequestMapping(value = { "/formSet/organ/groupColumnSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetGroupColumnSaveRs internetApplyFormSetOrganGroupColumnSaveAction(
			@RequestBody InternetApplyFormSetOrganGroupColumnSaveRq rq, HttpServletRequest request) {
		LOGGER.info("InternetApplyController internetApplyFormSetOrganGroupColumnSaveAction...");
		InternetApplyFormSetGroupColumnSaveRs rs = internetApplyService.internetApplyFormSetOrganGroupColumnSave(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-初始畫面
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-表單設定-MYDAYA維護-初始畫面")
	@RequestMapping(value = { "/formSet/mydataHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetMydataHomeRs internetApplyFormSetMydataHomeAction(
			@RequestBody InternetApplyFormSetMydataHomeRq rq, HttpServletRequest request) {
		InternetApplyFormSetMydataHomeRs rs = internetApplyService.internetApplyFormSetMydataHome(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-取得Mydata下拉式選單資料
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-表單設定-MYDAYA維護-取得Mydata下拉式選單資料")
	@RequestMapping(value = { "/formSet/mydataComboBoxAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetMydataComboBoxRs internetApplyFormSetMydataComboBoxAction(
			@RequestBody InternetApplyFormSetMydataComboBoxRq rq, HttpServletRequest request) {
		InternetApplyFormSetMydataComboBoxRs rs = internetApplyService.internetApplyFormSetMydataComboBox(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-資料集刪除
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-表單設定-MYDAYA維護-刪除")
	@RequestMapping(value = { "/formSet/mydataDeleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetMydataDeleteRs internetApplyFormSetMydataDeleteAction(
			@RequestBody InternetApplyFormSetMydataDeleteRq rq, HttpServletRequest request) {
		InternetApplyFormSetMydataDeleteRs rs = internetApplyService.internetApplyFormSetMydataDelete(rq);
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-資料集新增
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-表單設定-MYDAYA維護-資料集新增")
	@RequestMapping(value = { "/formSet/mydataSaveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplyFormSetMydataSaveRs internetApplyFormSetMydataSaveAction(
			@RequestBody InternetApplyFormSetMydataSaveRq rq, HttpServletRequest request) {
		InternetApplyFormSetMydataSaveRs rs = internetApplyService.internetApplyFormSetMydataSave(rq);
		return rs;
	}

	/**
	 * 網路申辦-案件儲存
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("網路申辦-案件儲存")
	@RequestMapping(value = { "/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InternetApplySaveRs internetApplySaveAction(@RequestBody InternetApplySaveRq rq,
			HttpServletRequest request) {
		InternetApplySaveRs rs = internetApplyService.internetApplySave(rq);
		return rs;
	}


	/**
	 * 機關退費比率儲存畫面-機關下拉
	 */
	@ApiOperation("機關優惠折扣比率儲存畫面-機關下拉")
	@RequestMapping(value = { "/organDiscountComboBox" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganComboBoxQueryRs getDisocuntOrganComboBox(@RequestBody OrganDiscountRatioQueryRq rq) {
		OrganComboBoxQueryRs rs = internetApplyService.getDisocuntOrganComboBox(rq);
		return rs;
	}

	/**
	 * 機關退費比率儲存
	 */
	@ApiOperation("機關優惠折扣比率儲存")
	@RequestMapping(value = { "/organDiscountRatioSave" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganDiscountRatioSaveRs organDiscountRatioSave(@RequestBody OrganDiscountRatioQueryRq rq) {
		OrganDiscountRatioSaveRs rs = internetApplyService.organDiscountRatioSave(rq);
		return rs;
	}


	/**
	 * 機關退費比率刪除
	 */
	@ApiOperation("機關優惠折扣比率刪除")
	@RequestMapping(value = { "/organDiscountRatioDelete" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganDiscountRatioDeleteRs organDiscountRatioDelete(@RequestBody OrganDiscountRatioQueryRq rq) {
		OrganDiscountRatioDeleteRs rs = internetApplyService.organDiscountRatioDelete(rq);
		return rs;
	}

	/**
	 * 機關優惠折扣查詢
	 */
	@ApiOperation("機關優惠折扣查詢")
	@RequestMapping(value = { "/organDiscountRatioQuery" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrganDiscountRatioQueryRs organDiscountRatioQuery(@RequestBody OrganDiscountRatioQueryRq rq) {
		OrganDiscountRatioQueryRs rs = internetApplyService.organDiscountRatioQuery(rq);
		return rs;
	}	
}
