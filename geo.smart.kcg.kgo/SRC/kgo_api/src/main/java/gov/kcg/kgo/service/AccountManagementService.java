package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rq.AccountManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rs.AccountManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rq.AccountManagementEditRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rs.AccountManagementEditRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rq.AccountManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rs.AccountManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountHome.rs.AccountManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rq.AccountManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.AccountManagementQueryRs;

public interface AccountManagementService {

	/**
	 * 帳號權限管理-畫面初始
	 * 
	 * @param rq
	 * @return AccountManagementHomeRs
	 */
	public AccountManagementHomeRs accountManagementHome();

	/**
	 * 帳號權限管理-帳號搜尋
	 * 
	 * @param rq
	 * @return AccountManagementQueryRs
	 */
	public AccountManagementQueryRs accountManagementQuery(AccountManagementQueryRq rq);

	/**
	 * 帳號權限管理-帳號維護(新增/修改)-畫面初始
	 * 
	 * @param rq
	 * @return AccountManagementEditRs
	 */
	public AccountManagementEditHomeRs accountManagementEditHome(AccountManagementEditHomeRq rq);

	/**
	 * 帳號權限管理-帳號維護(新增/修改)
	 * 
	 * @param rq
	 * @return AccountManagementEditRs
	 */
	public AccountManagementEditRs accountManagementEdit(AccountManagementEditRq rq);

	/**
	 * 帳號權限管理-帳號刪除
	 * 
	 * @param rq
	 * @return AccountManagementDeleteRs
	 */
	public AccountManagementDeleteRs accountManagementDelete(AccountManagementDeleteRq rq);

}
