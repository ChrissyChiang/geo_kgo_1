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
import gov.kcg.kgo.service.AccountManagementService;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rq.AccountManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rs.AccountManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rq.AccountManagementEditRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rs.AccountManagementEditRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rq.AccountManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rs.AccountManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountHome.rs.AccountManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rq.AccountManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.AccountManagementQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/accountManagement")
@Api(tags = {"account-management-controller 帳號權限管理"})
public class AccountManagementController extends BaseController {

	@Autowired
	AccountManagementService accountManagementService;

	/**
	 * 帳號權限管理-畫面初始
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 帳號權限管理-畫面初始")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AccountManagementHomeRs accountHomeAction() {
		AccountManagementHomeRs rs = accountManagementService.accountManagementHome();
		return rs;
	}

	/**
	 * 帳號權限管理-帳號搜尋
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 帳號權限管理-帳號搜尋")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AccountManagementQueryRs accountQueryAction(@RequestBody AccountManagementQueryRq rq,
			HttpServletRequest request) {
		AccountManagementQueryRs rs = accountManagementService.accountManagementQuery(rq);
		return rs;
	}

	/**
	 * 帳號權限管理-帳號維護(新增/修改)-畫面初始
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 帳號權限管理-帳號維護(新增/修改)-畫面初始")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AccountManagementEditHomeRs accountUpdateAction(@RequestBody AccountManagementEditHomeRq rq,
			HttpServletRequest request) {
		AccountManagementEditHomeRs rs = accountManagementService.accountManagementEditHome(rq);
		return rs;
	}

	/**
	 * 帳號權限管理-帳號維護(新增/修改)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "* 帳號權限管理-帳號維護(新增/修改)")
	@RequestMapping(value = { "/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AccountManagementEditRs accountUpdateAction(@RequestBody AccountManagementEditRq rq,
			HttpServletRequest request) {
		AccountManagementEditRs rs = accountManagementService.accountManagementEdit(rq);
		return rs;
	}

	/**
	 * 帳號權限管理-帳號刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "帳號權限管理-帳號刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AccountManagementDeleteRs classDeleteAction(@RequestBody AccountManagementDeleteRq rq,
			HttpServletRequest request) {
		AccountManagementDeleteRs rs = accountManagementService.accountManagementDelete(rq);
		return rs;
	}

}
