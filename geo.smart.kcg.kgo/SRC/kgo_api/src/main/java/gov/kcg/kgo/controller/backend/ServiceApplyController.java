package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.ServiceApplyService;
import gov.kcg.kgo.viewModel.backend.serviceApply.home.rs.ServiceApplyHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq.ServiceApplyPermissionActiveHomeRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq.ServiceApplyPermissionUserComboBoxActionRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.ServiceApplyPermissionActiveHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.ServiceApplyPermissionUserComboBoxActionRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rq.ServiceApplyPermissionActiveSaveRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rs.ServiceApplyPermissionActiveSaveRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rq.ServiceApplyQueryRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.ServiceApplyQueryRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rq.ServiceApplyServiceCaseEditRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rs.ServiceApplyServiceCaseEditRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rq.ServiceApplyServiceCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rs.ServiceApplyServiceCaseHomeRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/backend/serviceApply")
@Api(tags = {"service-apply-controller 服務申請/系統公告"})
public class ServiceApplyController extends BaseController {

	@Autowired
	ServiceApplyService serviceApplyService;

	/**
	 * 系統公告-初始畫面
	 * 
	 * @return
	 */
	@ApiOperation(value = "系統公告-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyHomeRs serviceApplyHomeAction() {
		ServiceApplyHomeRs rs = serviceApplyService.serviceApplyHome();
		return rs;
	}

	/**
	 * 服務申請-申請案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = "服務申請-申請案件查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyQueryRs serviceApplyQueryAction(@RequestBody ServiceApplyQueryRq rq) {
		ServiceApplyQueryRs rs = serviceApplyService.serviceApplyQuery(rq);
		return rs;
	}

	/**
	 * 服務申請-權限開通申請-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = "* 服務申請-權限開通申請-初始畫面 ")
	@RequestMapping(value = { "/permissionActive/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyPermissionActiveHomeRs serviceApplyPermissionActiveHomeAction(@RequestBody ServiceApplyPermissionActiveHomeRq rq) {
		ServiceApplyPermissionActiveHomeRs rs = serviceApplyService.serviceApplyPermissionActiveHome(rq);
		return rs;
	}

	/**
	 * 服務申請-權限開通申請-儲存
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = "* 服務申請-權限開通申請-儲存 ")
	@RequestMapping(value = { "/permissionActive/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyPermissionActiveSaveRs serviceApplyPermissionActiveSaveAction(@RequestBody ServiceApplyPermissionActiveSaveRq rq) {
		ServiceApplyPermissionActiveSaveRs rs = serviceApplyService.serviceApplyPermissionActiveSave(rq);
		return rs;
	}

	/**
	 * GEO 20211115 add 非市府員工登入後台
	 * 服務申請-權限開通申請-初始畫面-審核者列表
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = "* 服務申請-權限開通申請-初始畫面-審核者列表")
	@RequestMapping(value = { "/permissionActive/userComboBoxAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyPermissionUserComboBoxActionRs serviceApplyPermissionUserComboBoxAction(@RequestBody ServiceApplyPermissionUserComboBoxActionRq rq) {
		ServiceApplyPermissionUserComboBoxActionRs rs = serviceApplyService.userComboBoxAction(rq);
		return rs;
	}

	/**
	 * 服務申請-服務案件申請-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = "服務申請-服務案件申請-初始畫面 ")
	@RequestMapping(value = { "/serviceCase/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyServiceCaseHomeRs serviceApplyServiceCaseHomeAction(@RequestBody ServiceApplyServiceCaseHomeRq rq) {
		ServiceApplyServiceCaseHomeRs rs = serviceApplyService.serviceApplyServiceCaseHome(rq);
		return rs;
	}

	/**
	 * 服務申請-服務案件申請-編輯
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation(value = " 服務申請-服務案件申請-編輯 ")
	@RequestMapping(value = { "/serviceCase/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ServiceApplyServiceCaseEditRs serviceApplyServiceCaseEditAction(@RequestBody ServiceApplyServiceCaseEditRq rq) {
		ServiceApplyServiceCaseEditRs rs = serviceApplyService.serviceApplyServiceCaseEdit(rq);
		return rs;
	}

}
