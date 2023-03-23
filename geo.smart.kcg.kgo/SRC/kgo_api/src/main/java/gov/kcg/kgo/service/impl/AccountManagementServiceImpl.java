package gov.kcg.kgo.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.model.KgoUserRole;
import gov.kcg.kgo.model.KgoUserRolePK;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.repository.KgoUserRoleRepository;
import gov.kcg.kgo.service.AccountManagementService;
import gov.kcg.kgo.service.impl.helper.AccountManagementServiceHelper;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rq.AccountManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rs.AccountManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountDelete.rs.bean.AccountManagementDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rq.AccountManagementEditRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rs.AccountManagementEditRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEdit.rs.bean.AccountManagementEditViewForm;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rq.AccountManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rs.AccountManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountEditHome.rs.bean.AccountManagementEditHomeViewForm;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountHome.rs.AccountManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountHome.rs.bean.AccountManagementHomeViewForm;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rq.AccountManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.AccountManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.bean.AccountManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.bean.AccountManagementQueryViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

@Transactional(rollbackFor = Exception.class)
@Service("AccountManagementService")
public class AccountManagementServiceImpl extends KgoBackEndServiceImpl implements AccountManagementService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

	@Autowired
	private KgoUserRepository kgoUserRepository;

	@Autowired
	private KgoUserRoleRepository kgoUserRoleRepository;

	@Autowired
	private KgoUnitRepository kgoUnitRepository;

	private AccountManagementServiceHelper accountManagementServicehelper = AccountManagementServiceHelper
			.getInstance();

	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper.getInstance();

	/**
	 * 帳號權限管理-畫面初始
	 */
	@Override
	public AccountManagementHomeRs accountManagementHome() {
		AccountManagementHomeRs rs = new AccountManagementHomeRs();
		AccountManagementHomeViewForm viewForm = new AccountManagementHomeViewForm();
		BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();

		try {
			// get all data with AccountManagementQueryDto formate
			// 調整只撈登入者同機關人員 20201203 by Jay
			List<AccountManagementQueryDto> kgoUserList = kgoUserRepository.findAccountQueryData(backendLoginUser.getOrgan(), null, null, null,  null);

			// change AccountManagementQueryDto to AccountManagementQueryDataGrid
			List<AccountManagementQueryDataGrid> dgLits = new ArrayList<AccountManagementQueryDataGrid>();
			kgoUserList.forEach(g -> {
				AccountManagementQueryDataGrid dg = new AccountManagementQueryDataGrid();
				dg.setName(g.getName());
				dg.setOrganName(g.getOrganName());
				dg.setUnitName(g.getUnitName());
				dg.setUserId(g.getUserId());
				/** GEO 20211125 add 跨機關檢視 */
				dg.setAvailableCrossReview(g.getAvailableCrossReview() != null && g.getAvailableCrossReview());
				dgLits.add(dg);
			});

			// set viewForm data
			viewForm.setGrid(dgLits);
			//調整除了系統管理者，其他角色僅可檢視自己機關 20201203 BY JAY
			viewForm.setOrganComboBox(organUnitManagementServiceHelper.getOrganComboBoxWithUserRoleLimit(backendLoginUser.getUserId()));
			viewForm.setUnitComboBox(accountManagementServicehelper.getDefaultUnitComboBox());
			viewForm.setRoleComboBox(accountManagementServicehelper.getRoleComboBoxByUserRoles(backendLoginUser.getRoles()));

			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("accountManagementHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 帳號權限管理-帳號搜尋
	 */
	@Override
	public AccountManagementQueryRs accountManagementQuery(AccountManagementQueryRq rq) {
		AccountManagementQueryRs rs = new AccountManagementQueryRs();
		AccountManagementQueryViewForm viewForm = new AccountManagementQueryViewForm();

		try {
			// get all data with AccountManagementQueryDto formate
			List<AccountManagementQueryDto> kgoUserList = kgoUserRepository.findAccountQueryData(rq.getOrganId(),
					rq.getUnitId(), rq.getName(), rq.getUserId(), rq.getRoleId());

			// change AccountManagementQueryDto to AccountManagementQueryDataGrid
			List<AccountManagementQueryDataGrid> dgLits = new ArrayList<AccountManagementQueryDataGrid>();
			kgoUserList.forEach(g -> {
				AccountManagementQueryDataGrid dg = new AccountManagementQueryDataGrid();
				dg.setName(g.getName());
				dg.setOrganName(g.getOrganName());
				dg.setUnitName(g.getUnitName());
				dg.setUserId(g.getUserId());
				/** GEO 20211125 add 跨機關檢視 */
				dg.setAvailableCrossReview(g.getAvailableCrossReview() != null && g.getAvailableCrossReview());
				dgLits.add(dg);
			});

			// set viewForm data
			viewForm.setGrid(dgLits);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("accountManagementQuery error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 帳號權限管理-帳號維護(新增/修改)-畫面初始
	 * 
	 */
	@Override
	public AccountManagementEditHomeRs accountManagementEditHome(AccountManagementEditHomeRq rq) {
		AccountManagementEditHomeViewForm viewForm = new AccountManagementEditHomeViewForm();
		AccountManagementEditHomeRs rs = new AccountManagementEditHomeRs();
		BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
		boolean isAdmin = backendLoginUser.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase);
		try {
			/** initial comboBox, checkBox **/
			ComboBox organComboBox = null;
			ComboBox unitComboBox = null;
			CheckBox checkBox = null;

			/** initial checkBox with false state **/
			Map<String, CheckBox> checkBoxMap = new HashMap<>();

			//只有系統管理者才可以看到系統管理者選項 20201203 by Jay
			for (KgoRoleEnum e : KgoRoleEnum.values()) {
				//only Admin can see Admin
				checkBox = null;
				if(e.getValue().equalsIgnoreCase(KgoRoleEnum.ADMIN.getValue())){
					if(isAdmin){
						checkBox = organUnitManagementServiceHelper.getCheckBox(e.getLabel(), e.getValue());
					}
				}else{
					checkBox = organUnitManagementServiceHelper.getCheckBox(e.getLabel(), e.getValue());
				}
				if(checkBox != null) checkBoxMap.put(e.getValue(), checkBox);
			}

			/** initial String value **/
			String userId = rq.getUserId();
			String name = null;
			String email = null;
			String publicEmail = null;
			String tel = null;
			Boolean isAvailableCrossReview = null;

			/** Start to set view value & checkbox state & comboBox **/
			if (StringUtils.isBlank(userId)) {
				/** 新增階段 **/

				// get organComboBox
				organComboBox = organUnitManagementServiceHelper.getOrganComboBoxWithUserRoleLimit(backendLoginUser.getUserId());

				// get unit comboBox with default
				unitComboBox = accountManagementServicehelper.getDefaultUnitComboBox();
			} else {
				/** 維護階段 **/

				// Get user info by userId
				KgoUser kgoUser = kgoUserRepository.getOne(rq.getUserId());

				// set view value
				name = kgoUser.getName();
				email = kgoUser.getEmail();
				publicEmail = kgoUser.getPublicEmail();
				tel = kgoUser.getTel();
				/**GEO 20211115 add 跨機關檢視*/
				isAvailableCrossReview = kgoUser.getAvailableCrossReview()!= null && kgoUser.getAvailableCrossReview();

				// get organComboBox
				organComboBox = organUnitManagementServiceHelper.getOrganComboBox(kgoUser.getOrgan(),
						ComboBoxStatusEnum.ONE.getCode());

				// get unit comboBox with custom data list
				List<KgoUnit> unitList = kgoUnitRepository.findUnitIdAndUnitNameByIdOrganId(kgoUser.getOrgan());
				unitComboBox = accountManagementServicehelper.getCustomUnitComboBox(unitList, kgoUser.getUnit());

				// set all checkBox state
				List<KgoUserRole> list = kgoUserRoleRepository.findAllByIdUserId(userId);
				LOGGER.debug("User[{}], Role[{}]",kgoUser.getUserId(), list);
				list.forEach(l -> {
					checkBoxMap.computeIfPresent(l.getId().getRoleId(), (k, v) -> {
						v.setSelected(true);
						return v;
					} );
				});
			}

			/** ComboBox **/
			viewForm.setOrganComboBox(organComboBox);
			viewForm.setUnitComboBox(unitComboBox);

			/** CheckBox **/
			viewForm.setCheckBoxList(checkBoxMap.values().stream().collect(Collectors.toList()));

			/** view data **/
			viewForm.setUserId(userId);
			viewForm.setName(name);
			viewForm.setEmail(email);
			viewForm.setPublicEmail(publicEmail);
			viewForm.setTel(tel);
			/**GEO 20211115 add 跨機關檢視*/
			viewForm.setAvailableCrossReview(isAvailableCrossReview);

			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("accountManagementEditHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 分類維護功能-類別新增/維護
	 * 
	 */
	@Override
	public AccountManagementEditRs accountManagementEdit(AccountManagementEditRq rq) {
		AccountManagementEditViewForm viewForm = new AccountManagementEditViewForm();
		AccountManagementEditRs rs = new AccountManagementEditRs();
		KgoFrontEndApiError kgoFrontEndApiError = KgoFrontEndApiError.UNKNOWN_EXCEPTION;
		KgoUser userEntity = null;
		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			// 後台、新增或編輯、帳號權限
			SysLogExeType sysLogExeType =kgoUserRepository.existsById(rq.getUserId()) ? SysLogExeType.TYPE_U:SysLogExeType.TYPE_A;
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.AccountM);
		
			
			
			String executeResult = SuccessMsg.UNKNOW.getMsg();

			/** set normal data **/
			if (kgoUserRepository.existsById(rq.getUserId())) {
				userEntity = kgoUserRepository.getOne(rq.getUserId());
				kgoFrontEndApiError = KgoFrontEndApiError.FAIL_TO_EDIT;
				executeResult = SuccessMsg.UPDATE.getMsg();
			} else {
				userEntity = new KgoUser();
				kgoFrontEndApiError = KgoFrontEndApiError.FAIL_TO_ADD;
				userEntity.setCreateUser(KgoUtil.getTempCreateUser()); // ... 待修正
				userEntity.setCreateTime(DateUtil.getCurrentTimestamp());
				executeResult = SuccessMsg.INSERT.getMsg();
			}
			userEntity.setUserId(rq.getUserId());
//			userEntity.setOrgan(rq.getOrganId());
//			userEntity.setUnit(rq.getUnitId());
			userEntity.setEmail(rq.getEmail());
			userEntity.setPublicEmail(rq.getPublicEmail());
			userEntity.setName(rq.getName());
			userEntity.setTel(rq.getTel());
			userEntity.setUpdateUser(KgoUtil.getTempUpdateUser()); // ... 待修正
			userEntity.setUpdateTime(DateUtil.getCurrentTimestamp());
			/**GEO 20211115 add 跨機關檢視*/
			userEntity.setAvailableCrossReview(rq.getAvailableCrossReview());
			kgoUserRepository.save(userEntity);

			/** set role data **/
			kgoUserRoleRepository.deleteByIdUserId(rq.getUserId());
			for (String roleId : rq.getRoleId()) {
				KgoUserRole entity = new KgoUserRole();
				KgoUserRolePK id = new KgoUserRolePK();
				id.setUserId(rq.getUserId());
				id.setRoleId(roleId);
				entity.setId(id);
				kgoUserRoleRepository.save(entity);
			}
			viewForm.setMsg(executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(kgoFrontEndApiError.getErrorMsgKey());
			error= new KgoApiException("accountManagementEdit error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
					memoList.add(new OperationColumn("帳號", rq.getUserId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */
			
			if (error != null) {
				throw error;
			}
		}

		return rs;
	}

	/**
	 * 帳號權限管理-帳號刪除
	 */
	@Override
	public AccountManagementDeleteRs accountManagementDelete(AccountManagementDeleteRq rq) {
		AccountManagementDeleteRs rs = new AccountManagementDeleteRs();
		AccountManagementDeleteViewForm viewForm = new AccountManagementDeleteViewForm();
		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			// 後台、刪除、帳號權限
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.AccountM);
			
			kgoUserRepository.deleteById(rq.getUserId());
			kgoUserRoleRepository.deleteByIdUserId(rq.getUserId());
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			error= new KgoApiException("accountManagementDelete error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
					memoList.add(new OperationColumn("帳號", rq.getUserId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */
			
			if (error != null) {
				throw error;
			}
		}
		return rs;
	}

}
