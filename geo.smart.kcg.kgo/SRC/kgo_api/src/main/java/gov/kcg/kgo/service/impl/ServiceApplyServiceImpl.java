package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.ServiceApplyService;
import gov.kcg.kgo.service.ServiceApplyUraService;
import gov.kcg.kgo.service.impl.helper.CaseManagementServiceHelper;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.service.impl.helper.ServiceApplyServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.bean.OrganComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rq.UnitComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.UnitComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.bean.UnitComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.home.rs.ServiceApplyHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.home.rs.bean.ServiceApplyHomeViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq.ServiceApplyPermissionActiveHomeRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq.ServiceApplyPermissionUserComboBoxActionRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.ServiceApplyPermissionActiveHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.ServiceApplyPermissionUserComboBoxActionRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.bean.ServiceApplyPermissionActiveHomeViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.bean.ServiceApplyPermissionUserComboBoxActionViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rq.ServiceApplyPermissionActiveSaveRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rs.ServiceApplyPermissionActiveSaveRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rs.bean.ServiceApplyPermissionActiveSaveViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rq.ServiceApplyQueryRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.ServiceApplyQueryRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.bean.ServiceApplyQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.bean.ServiceApplyQueryViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rq.ServiceApplyServiceCaseEditRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rs.ServiceApplyServiceCaseEditRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rs.bean.ServiceApplyServiceCaseEditViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rq.ServiceApplyServiceCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rs.ServiceApplyServiceCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rs.bean.ServiceApplyServiceCaseHomeViewForm;
import gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rq.ServiceApplyUraPendingReviewM1ApproveToOMRq;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service("ServiceApplyService")
public class ServiceApplyServiceImpl extends KgoBackEndServiceImpl implements ServiceApplyService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceApplyServiceImpl.class);

	/** commonServiceHelper **/
	private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

	/** commonServiceHelper **/
	private ServiceApplyServiceHelper serviceApplyServiceHelper = ServiceApplyServiceHelper.getInstance();

	/** caseManagementServiceHelper **/
	private CaseManagementServiceHelper caseManagementServiceHelper = CaseManagementServiceHelper.getInstance();

	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
			.getInstance();

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private KgoUserRepository kgoUserRepository;

	@Autowired
	private KgoRoleRepository kgoRoleRepository;

	@Autowired
	private KgoCasesetColumnRepository kgoCasesetColumnRepository;

	@Autowired
	private KgoCasesetGroupRepository kgoCasesetGroupRepository;

	@Autowired
	private KgoUraApplyRepository kgoUraApplyRepository;

	@Autowired
	private KgoCaseDetailRepository kgoCaseDetailRepository;

	@Autowired
	private KgoApplyReviewerRepository kgoApplyReviewerRepository;

	@Autowired
	private KgoCasesetManagerRepository kgoCasesetManagerRepository;

	@Autowired
	private KgoCasesetTypeRepository kgoCasesetTypeRepository;

	@Autowired
	private KgoCodeRepository kgoCodeRepository;

	@Autowired
	private KgoOrganRepository kgoOrganRepository;

	@Autowired
	private KgoScaApplyRepository kgoScaApplyRepository;

	@Autowired
	private KgoGroupLevelRepository kgoGroupLevelRepository;

	@Autowired
	private ServiceApplyUraService serviceApplyUraService;

	@Autowired
	private KgoUserRoleRepository kgoUserRoleRepository;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private TaskService taskService;

	private static final String COMBOX_REVIEW_USER_LABEL_STR = "name";
	private static final String COMBOX_REVIEW_USER_VALUE_STR = "userId";

	private static final String SPLIT_SYMBOLE = ",";

	/**
	 * 服務申請-初始畫面
	 * 
	 */
	@Override
	public ServiceApplyHomeRs serviceApplyHome() {
		ServiceApplyHomeViewForm viewForm = new ServiceApplyHomeViewForm();
		ServiceApplyHomeRs rs = new ServiceApplyHomeRs();
		try {
			viewForm.setCaseTypeComboBox(serviceApplyServiceHelper.getCustomCaseTypeComboBox());
			viewForm.setGrid(queryCasesetDataByCaseSetName(StringUtils.EMPTY, StringUtils.EMPTY));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("ServiceApplyHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 服務申請-申請案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public ServiceApplyQueryRs serviceApplyQuery(ServiceApplyQueryRq rq) {
		ServiceApplyQueryViewForm viewForm = new ServiceApplyQueryViewForm();
		ServiceApplyQueryRs rs = new ServiceApplyQueryRs();
		try {
			String caseSetName = rq.getCaseSetName();
			String caseType = rq.getCaseType();
			viewForm.setGrid(queryCasesetDataByCaseSetName(caseSetName, caseType));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("ServiceApplyQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 服務申請-權限開通申請--初始畫面
	 * 
	 * @param rq
	 * @return ServiceApplyPermissionActiveHomeRs
	 */
	@Override
	public ServiceApplyPermissionActiveHomeRs serviceApplyPermissionActiveHome(ServiceApplyPermissionActiveHomeRq rq) {
		ServiceApplyPermissionActiveHomeViewForm viewForm = new ServiceApplyPermissionActiveHomeViewForm();
		ServiceApplyPermissionActiveHomeRs rs = new ServiceApplyPermissionActiveHomeRs();
		AccountManagementQueryDto accountDto = null;
		ComboBox reviewerComboBox = null;
		ComboBox reviewer2ComboBox = null;
		try {
			BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
			String applyUser = backendLoginUser.getUserId();
			String applyOrgan = backendLoginUser.getOrgan();
			String applyUnit = backendLoginUser.getUnit();

			/** GEO 20211115 add 非市府員工登入後台 */
			if (backendLoginUser.getLoginAuthTokenType().equals(LoginAuthTokenType.BASIC)) {
				/* 登入者資訊 **/
				List<AccountManagementQueryDto> accountDtoList = kgoUserRepository.findAccountQueryData(applyOrgan,
						applyUnit, StringUtils.EMPTY, applyUser, StringUtils.EMPTY);
				accountDto = accountDtoList.get(0);

				/* review user comboBox **/
				List<KgoUser> kgoUsers = kgoUserRepository.findByRoleIdAndOrgan(KgoRoleEnum.UNIT_M.getValue(), applyOrgan);
				reviewerComboBox = commonServiceHelper.getComboBox(kgoUsers, COMBOX_REVIEW_USER_LABEL_STR,
						COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);

				List<KgoUser> kgoUsers2 = kgoUserRepository.findByRoleId(KgoRoleEnum.ADMIN.getValue());
				List<KgoUser> kgoUsers3 = kgoUserRepository.findByRoleId(KgoRoleEnum.ADMIN.getValue().toUpperCase());
				kgoUsers2.addAll(kgoUsers3);
				reviewer2ComboBox = commonServiceHelper.getComboBox(kgoUsers2, COMBOX_REVIEW_USER_LABEL_STR,
						COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);

			} else {
				/* 登入者資訊 **/
				List<AccountManagementQueryDto> accountDtoList = kgoUserRepository.findAccountQueryDataByUserId(applyUser);
				accountDto = accountDtoList.get(0);

				/* review user comboBox **/
				reviewerComboBox = new ComboBox();
				List<KgoUser> kgoUsers2 = kgoUserRepository.findByRoleId(KgoRoleEnum.ADMIN.getValue());
				List<KgoUser> kgoUsers3 = kgoUserRepository.findByRoleId(KgoRoleEnum.ADMIN.getValue().toUpperCase());
				kgoUsers2.addAll(kgoUsers3);
				reviewer2ComboBox = commonServiceHelper.getComboBox(kgoUsers2, COMBOX_REVIEW_USER_LABEL_STR,
						COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
			}

			/* role checkbox list **/
			List<KgoUserRole> kgoUserRoles = kgoUserRoleRepository.findAllByIdUserId(applyUser);
			Map<String, KgoUserRole> map = kgoUserRoles.stream().collect(Collectors.toMap(c -> c.getId().getRoleId(), c -> c));
			List<KgoRole> roleList = kgoRoleRepository.findAll();
			List<CheckBox> applyRoleCheckBox = roleList.stream()
				.filter(item -> !KgoRoleEnum.ADMIN.getValue().equalsIgnoreCase(item.getRoleId())
						&& !KgoRoleEnum.UNIT_U.getValue().equals(item.getRoleId()))
				.map(l -> commonServiceHelper.getCheckBox(l.getRoleName(), l.getRoleId(), map.get(l.getRoleId()) != null))
				.collect(Collectors.toList());

			viewForm.setOrgan(applyOrgan);
			viewForm.setOrganName(accountDto.getOrganName());
			viewForm.setUnit(applyUnit);
			viewForm.setUnitName(accountDto.getUnitName());
			viewForm.setApplyUser(applyUser);
			viewForm.setApplyUserName(accountDto.getName());
			viewForm.setApplyRoleCheckBox(applyRoleCheckBox);
			viewForm.setReviewerComboBox(reviewerComboBox);
			viewForm.setReviewer2ComboBox(reviewer2ComboBox);
			/** GEO 20211115 add 非市府員工登入後台 */
			if (backendLoginUser.getLoginAuthTokenType().equals(LoginAuthTokenType.BASIC)) viewForm.setLoginType(LoginAuthTokenType.BASIC.getType());
			else viewForm.setLoginType(Objects.requireNonNull(LoginAuthTokenType.getLoginAuthType(accountDto.getLoginType())).getType());
			viewForm.setEmail(StringUtils.isBlank(accountDto.getEmail()) ? StringUtils.EMPTY : accountDto.getEmail() );
			viewForm.setPublicEmail(StringUtils.isBlank(accountDto.getPublicEmail()) ? StringUtils.EMPTY : accountDto.getPublicEmail() );
			viewForm.setPhoneNumber(StringUtils.isBlank(accountDto.getPhoneNumber()) ? StringUtils.EMPTY : accountDto.getPhoneNumber() );
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("serviceApplyPermissionActive error " + e.getMessage(), e);
		}

		return rs;
	} //serviceApplyPermissionActiveHome

	/**
	 * GEO 20211115 add 非市府員工登入後台
	 * 服務申請-權限開通申請-初始畫面-審核者列表
	 * @param rq
	 * @return
	 */
	@Override
	public ServiceApplyPermissionUserComboBoxActionRs userComboBoxAction(ServiceApplyPermissionUserComboBoxActionRq rq) {
		ServiceApplyPermissionUserComboBoxActionViewForm viewForm = new ServiceApplyPermissionUserComboBoxActionViewForm();
		ServiceApplyPermissionUserComboBoxActionRs rs = new ServiceApplyPermissionUserComboBoxActionRs();
		try {
			List<KgoUser> kgoUsers = kgoUserRepository.findByRoleIdAndOrgan(KgoRoleEnum.UNIT_M.getValue(), rq.getOrganId());
			ComboBox reviewerComboBox = commonServiceHelper.getComboBox(kgoUsers, COMBOX_REVIEW_USER_LABEL_STR,
					COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);

			List<KgoUser> kgoUsers2 = kgoUserRepository.findByRoleId(KgoRoleEnum.ADMIN.getValue());
			List<KgoUser> kgoUsers3 = kgoUserRepository.findByRoleId(KgoRoleEnum.ADMIN.getValue().toUpperCase());
			kgoUsers2.addAll(kgoUsers3);
			ComboBox reviewer2ComboBox = commonServiceHelper.getComboBox(kgoUsers2, COMBOX_REVIEW_USER_LABEL_STR,
					COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
			viewForm.setReviewerComboBox(reviewerComboBox);
			viewForm.setReviewer2ComboBox(reviewer2ComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("userComboBoxAction error " + e.getMessage(), e);
		}

		return rs;
	} //userComboBoxAction

	/**
	 * 服務申請-權限開通申請-儲存
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public ServiceApplyPermissionActiveSaveRs serviceApplyPermissionActiveSave(ServiceApplyPermissionActiveSaveRq rq) {
		ServiceApplyPermissionActiveSaveViewForm viewForm = new ServiceApplyPermissionActiveSaveViewForm();
		ServiceApplyPermissionActiveSaveRs rs = new ServiceApplyPermissionActiveSaveRs();
		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			//後台、新增、權限開通申請
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_A, BackendFunctionCodeEnum.URA);
			BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
			String loginUserId = backendLoginUser.getUserId();
			String caseId = KgoUtil.getNextCaseId(CaseTypeEnum.URA);
			Timestamp currentTime = DateUtil.getCurrentTimestamp();
			String applyRole = rq.getApplyRole();

			// activiti start
			String reviewer = rq.getReviewer();
			LOGGER.info("caseId: {} reviewer: {} ", caseId, reviewer);
			List<String> roles = Arrays.asList(applyRole.split(","));

			String processId;
			if (roles.contains(KgoRoleEnum.UNIT_M.getValue())) {
				processId = activitiService.processStart(caseId, reviewer, "1");
			} else {
				processId = activitiService.processStart(caseId, reviewer, "2");
			}
			KgoOrgan kgoOrgan = null;
			if (StringUtils.isNotBlank(backendLoginUser.getOrgan())) kgoOrgan = kgoOrganRepository.getOne(backendLoginUser.getOrgan());
			else  kgoOrgan = kgoOrganRepository.getOne(rq.getOrgan());
			String organName = kgoOrgan == null ? "" : kgoOrgan.getOrganName();
			KgoUser kgoUserReviewer = kgoUserRepository.getOne(reviewer);
			String name = StringUtils.isEmpty(kgoUserReviewer.getName()) ? "" : kgoUserReviewer.getName();
			activitiService.addComment(loginUserId, processId, ApplyCaseStatusEnum.R.getLabel(), "起案", organName, name, CaseStatusEnum.START);
			/* 1. 新增或更新系統權限申請檔 **/
			// KgoUraApply
			KgoUraApply kgoUraApply = new KgoUraApply();
			kgoUraApply.setCaseId(caseId);
			kgoUraApply.setApplyOrgan(rq.getOrgan());
			kgoUraApply.setApplyRole(applyRole);
			kgoUraApply.setApplyUnit(rq.getUnit());
			kgoUraApply.setApplyUser(rq.getApplyUser());
			kgoUraApply.setEmail(rq.getEmail());
			kgoUraApply.setPhone(rq.getPhone());
			kgoUraApply.setStatus(ApplyCaseStatusEnum.R.getValue());
			kgoUraApply.setCreateTime(currentTime);
			kgoUraApply.setCreateUser(loginUserId);
			kgoUraApply.setUpdateTime(currentTime);
			kgoUraApply.setUpdateUser(loginUserId);
			kgoUraApply.setProcessId(processId);
			kgoUraApply.setManager1(rq.getReviewer());
			kgoUraApplyRepository.save(kgoUraApply);

			/**  GEO 20211115 add 非市府員工登入後台 */
			if (!LoginAuthTokenType.BASIC.equals(backendLoginUser.getLoginAuthTokenType())) {
				KgoUser user = kgoUserRepository.findByUserId(backendLoginUser.getUserId());
				if (user != null) {
					user.setEmail(rq.getEmail());
					user.setTel(rq.getPhone());
					user.setName(rq.getApplyUserName());
					kgoUserRepository.save(user);
				}
			}

			if (StringUtils.isNotEmpty(rq.getReviewer())) {
				/* 3. 將審核主管存入案件審核人員檔 **/
				kgoApplyReviewerRepository.saveData(caseId, CaseTypeEnum.URA.getValue(), rq.getReviewer(), loginUserId, currentTime,
						loginUserId, currentTime);
			}


//			if (StringUtils.isNotEmpty(applyRole)) {
//				List<String> roleList = Arrays.asList(applyRole.split(","));
//				if (roleList.contains(KgoRoleEnum.CASE_M.getValue())) {
//					Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//					ServiceApplyUraPendingReviewM1ApproveToOMRq serviceApplyUraPendingReviewM1ApproveToOMRq = new ServiceApplyUraPendingReviewM1ApproveToOMRq();
//					serviceApplyUraPendingReviewM1ApproveToOMRq.setTaskId(task.getId());
//					serviceApplyUraService.serviceApplyUraPendingReviewM1ApproveToOM(serviceApplyUraPendingReviewM1ApproveToOMRq);
//				}
//			}
			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			viewForm.setCaseId(caseId);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
//			e.printStackTrace();
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			error =  new KgoApiException("serviceApplyPermissionActiveSave error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			String caseId=rs.getData().getCaseId();
			memoList.add(new OperationColumn("案件編號", caseId));
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
	 * 服務申請-服務案件申請-初始畫面
	 *
	 * @param rq
	 * @return ServiceApplyServiceCaseHomeRs
	 */
	@Override
	public ServiceApplyServiceCaseHomeRs serviceApplyServiceCaseHome(ServiceApplyServiceCaseHomeRq rq) {
		ServiceApplyServiceCaseHomeViewForm viewForm = new ServiceApplyServiceCaseHomeViewForm();
		ServiceApplyServiceCaseHomeRs rs = new ServiceApplyServiceCaseHomeRs();
		try {
			BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
			String applyUser = backendLoginUser.getUserId();
			String applyOrgan = backendLoginUser.getOrgan();
			String applyUnit = backendLoginUser.getUnit();

			/** 登入者資訊 **/
			List<AccountManagementQueryDto> dtoList = kgoUserRepository.findAccountQueryData(applyOrgan, applyUnit,
					StringUtils.EMPTY, applyUser, StringUtils.EMPTY);
			AccountManagementQueryDto dto = dtoList.get(0);
			String applyUserName = dto.getName();
			String applyOrganName = dto.getOrganName();
			String applyUnitName = dto.getUnitName();

			/** 審核主管 **/
			List<KgoUser> kgoUsers = kgoUserRepository.findByOrgan(applyOrgan);
			kgoUsers = kgoUsers.stream().filter(item -> !item.getUserId().equals(applyUser)).collect(Collectors.toList());
			ComboBox reviewerComboBox = commonServiceHelper.getComboBox(kgoUsers, COMBOX_REVIEW_USER_LABEL_STR,
					COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);

			/** combo box and check box **/
			ComboBox serviceToComboBox = commonServiceHelper.getComboBoxWithEnum(ServiceToEnum.class);
			ComboBox caseTypeComboBox = caseManagementServiceHelper.getCodeTypeComboBox(CodeTypeEnum.FLOW.getValue());
			ComboBox caseFlowTypeComboBox = commonServiceHelper.getComboBoxWithEnum(CaseFlowTypeEnum.class);
			ComboBox organComboBox = caseManagementServiceHelper.getGroupLevelOrganComboBox(StringUtils.EMPTY);
			ComboBox linkTypeComboBox = commonServiceHelper.getComboBoxWithEnum(LinkTypeEnum.class);
			ComboBox roleComboBox = caseManagementServiceHelper.getGroupLevelRoleComboBox(StringUtils.EMPTY);
			ComboBox serviceComboBox = caseManagementServiceHelper.getGroupLevelServiceComboBox(StringUtils.EMPTY);
			ComboBox ownerOrganComboBox = organUnitManagementServiceHelper.getOrganComboBox();
			List<CheckBox> serviceCheckBox = caseManagementServiceHelper.getApplyTypeCheckBox();

			/** login info **/
			viewForm.setApplyOrgan(applyOrgan);
			viewForm.setApplyOrganName(applyOrganName);
			viewForm.setApplyUnit(applyUnit);
			viewForm.setApplyUnitName(applyUnitName);
			viewForm.setApplyUser(applyUser);
			viewForm.setApplyUserName(applyUserName);

			viewForm.setReviewerComboBox(reviewerComboBox);
			viewForm.setServiceToComboBox(serviceToComboBox); // 案件服務對象
			viewForm.setCaseTypeComboBox(caseTypeComboBox); // 作業流程
			viewForm.setCaseFlowTypeComboBox(caseFlowTypeComboBox); // 作業流程分類
			viewForm.setLinkTypeComboBox(linkTypeComboBox); // 站外連結方式
			viewForm.setOrganComboBox(organComboBox); // 機關分類
			viewForm.setRoleComboBox(roleComboBox); // 角色分類
			viewForm.setServiceComboBox(serviceComboBox); // 服務分類
			viewForm.setCaseSetTypeCheckList(serviceCheckBox); // 服務啟用
			viewForm.setOwnerOrganComboBox(ownerOrganComboBox); // 權責機關
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("serviceApplyServiceCaseHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 服務申請-服務案件申請-編輯
	 *
	 * @param rq
	 * @return
	 */
	@Override
	public ServiceApplyServiceCaseEditRs serviceApplyServiceCaseEdit(ServiceApplyServiceCaseEditRq rq) {
		ServiceApplyServiceCaseEditViewForm viewForm = new ServiceApplyServiceCaseEditViewForm();
		ServiceApplyServiceCaseEditRs rs = new ServiceApplyServiceCaseEditRs();
		KgoApiException error = null;
		OperationApiMemo memo = null;

		KgoBackEndApiError kgoBackEndApiError = KgoBackEndApiError.UNKNOWN_EXCEPTION;
		String msg = SuccessMsg.UNKNOW.getMsg();
		try {
			//後台、新增、服務案件申請
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_A, BackendFunctionCodeEnum.SCA);
			BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
			String loginUserId = backendLoginUser.getUserId();
			Timestamp currentTime = DateUtil.getCurrentTimestamp();

			kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
			msg = SuccessMsg.INSERT.getMsg();
			String caseId = KgoUtil.getNextCaseId(CaseTypeEnum.SCA);
			String caseSetType = rq.getCaseSetType();
			List<String> caseSetTypeList = null;
			if (StringUtils.isNotEmpty(caseSetType)) {
				caseSetTypeList = Arrays.asList(caseSetType.split(SPLIT_SYMBOLE));
			}

			// activiti start
			String reviewer = rq.getReviewer();
			String assignGroup = "CASE_M-" + backendLoginUser.getOrgan();
			KgoOrgan kgoOrgan = kgoOrganRepository.getOne(backendLoginUser.getOrgan());
			LOGGER.info("caseId: {} reviewer: {} assignGroup: {}", caseId, reviewer, assignGroup);
//			String processId = activitiService.processStart(caseId, reviewer, assignGroup, CaseTypeEnum.SCA);
			KgoUser kgoUser = kgoUserRepository.getOne(reviewer);
			String name = kgoUser == null ? "" : kgoUser.getName();
			String organName = kgoOrgan == null ? "" : kgoOrgan.getOrganName();
//			activitiService.addComment(loginUserId, processId, ApplyCaseStatusEnum.R.getLabel(), "起案", organName, name, CaseStatusEnum.START);
			/** 1. save kgoScaApply data **/
			KgoScaApply kgoScaApply = new KgoScaApply();
			kgoScaApply.setCaseId(caseId);
			kgoScaApply.setCaseSetName(rq.getCaseSetName());
			kgoScaApply.setStatus(ApplyCaseStatusEnum.R.getValue());
			kgoScaApply.setApplyOrgan(rq.getApplyOrgan());
			kgoScaApply.setApplyUnit(rq.getApplyUnit());
			kgoScaApply.setApplyUser(rq.getApplyUser());
			kgoScaApply.setPhone(rq.getPhone());
			kgoScaApply.setEmail(rq.getEmail());
//		    kgoScaApply.setProcessId(processId);
			kgoScaApply.setOwnerOrgan(rq.getOwnerOrgan());
			kgoScaApply.setCreateUser(loginUserId);
			kgoScaApply.setCreateTime(currentTime);
			kgoScaApply.setUpdateUser(loginUserId);
			kgoScaApply.setUpdateTime(currentTime);
			kgoScaApply.setResult(StringUtils.EMPTY);
			kgoScaApply.setCaseType(rq.getCaseType());
			kgoScaApply.setCaseFlowType(rq.getCaseFlowType());
			kgoScaApply.setLinkType(rq.getLinkType());
			kgoScaApply.setLinkUrl(rq.getLinkUrl());
			kgoScaApply.setOrgan(rq.getOrgan());
			kgoScaApply.setRole(rq.getRole());
			kgoScaApply.setService(rq.getService());
			kgoScaApplyRepository.save(kgoScaApply);

			/** 2. insert KgoCaseSetManager data **/
			List<KgoCasesetManager> kgoCasesetManagerList = new LinkedList<>();
			for (String manager : Arrays.asList(rq.getManager().split(","))) {
				KgoCasesetManagerPK id = new KgoCasesetManagerPK();
				id.setCaseSetId(caseId);
				id.setManager(manager);
				KgoCasesetManager kgoCasesetManager = new KgoCasesetManager();
				kgoCasesetManager.setId(id);
				kgoCasesetManagerList.add(kgoCasesetManager);
			}
			kgoCasesetManagerRepository.saveAll(kgoCasesetManagerList);

			/** 3. insert KgoApplyReviewer data **/
			kgoApplyReviewerRepository.saveData(caseId, CaseTypeEnum.SCA.getValue(), reviewer, loginUserId, currentTime,
					loginUserId, currentTime);

			/** 3. insert KgoCasesetType data **/
			if (!CollectionUtils.isEmpty(caseSetTypeList)) {
				List<KgoCasesetType> kgoCasesetTypeList = caseSetTypeList.stream().map(s -> {
					KgoCasesetType kgoCasesetType = new KgoCasesetType();
					KgoCasesetTypePK KgoCasesetTypePK = new KgoCasesetTypePK();
					KgoCasesetTypePK.setApplyType(s);
					KgoCasesetTypePK.setCaseSetId(caseId);
					kgoCasesetType.setId(KgoCasesetTypePK);
					return kgoCasesetType;
				}).collect(Collectors.toList());
				kgoCasesetTypeRepository.saveAll(kgoCasesetTypeList);
			}
			viewForm.setMsg(msg);
			viewForm.setCaseId(caseId);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(kgoBackEndApiError.getErrorMsgKey());
			error= new KgoApiException("serviceApplyServiceCaseEdit error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
					memoList.add(new OperationColumn("案件編號", rs.getData().getCaseId()));
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
	 * save process id in KgoScaApply
	 * 
	 * @param caseId
	 * @param processId
	 */
	public void serviceApplyServiceCaseProcessIdSave(String caseId, String processId) {
		try {
			KgoScaApply kgoScaApply = kgoScaApplyRepository.getOne(caseId);
			kgoScaApply.setProcessId(processId);
			kgoScaApplyRepository.save(kgoScaApply);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("serviceApplyServiceCaseProcessIdSave error " + e.getMessage(), e);
		}
	}

	/**
	 * 申請案件查詢 By CasesetName
	 * 
	 * @param caseSetName
	 * @return
	 */
	private List<ServiceApplyQueryDataGrid> queryCasesetDataByCaseSetName(String caseSetName, String caseType) {
		List<KgoCaseset> caseSetList = kgoCasesetRepository.findByCaseTypeAndCaseSetName(caseSetName, caseType);
		return caseSetList.stream().map(temp -> {
			return new ServiceApplyQueryDataGrid(temp.getCaseSetId(), temp.getCaseType(), temp.getCaseSetName());
		}).collect(Collectors.toList());
	}
}
