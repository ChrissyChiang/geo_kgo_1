package gov.kcg.kgo.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.OrganUnitManagementQueryDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.backend.OrganUnitEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUnitPK;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.service.OrganUnitManagementService;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rq.OrganUnitManagementUnitComboBoxRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rs.OrganUnitManagementUnitComboBoxRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rs.bean.OrganUnitManagementUnitComboBoxViewForm;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rq.OrganUnitManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rs.OrganUnitManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rs.bean.OrganUnitManagementDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rq.OrganUnitManagementEditRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rs.OrganUnitManagementEditRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rs.bean.OrganUnitManagementEditViewForm;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rq.OrganUnitManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rs.OrganUnitManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rs.bean.OrganUnitManagementEditHomeViewForm;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.OrganUnitManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.bean.OrganUnitManagementHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.bean.OrganUnitManagementHomeViewForm;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rq.OrganUnitManagementOrganQueryRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rs.OrganUnitManagementOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rs.bean.OrganUnitManagementOrganQueryViewForm;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rq.OrganUnitManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.OrganUnitManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.bean.OrganUnitManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.bean.OrganUnitManagementQueryViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

@Transactional(rollbackFor = Exception.class)
@Service("OrganUnitManagementService")
public class OrganUnitManagementServiceImpl extends KgoBackEndServiceImpl implements OrganUnitManagementService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganUnitManagementServiceImpl.class);

	@Autowired
	private KgoOrganRepository kgoOrganRepository;

	@Autowired
	private KgoUnitRepository kgoUnitRepository;

	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
			.getInstance();

	private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

	private enum EditType {
		INSERT_ORGAN, INSERT_UNIT, UPDATE_ORGAN, UPDATE_UNIT, UNKONW
	};

	/**
	 * 機關科室管理-初始畫面
	 * 
	 */
	@Override
	public OrganUnitManagementHomeRs organUnitManagementHome() {
		OrganUnitManagementHomeViewForm viewForm = new OrganUnitManagementHomeViewForm();
		OrganUnitManagementHomeRs rs = new OrganUnitManagementHomeRs();

		try {
			BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();

			// 機關下拉式選單
			ComboBox organComboBox = organUnitManagementServiceHelper
					.getOrganComboBoxWithUserRoleLimit(loginUser.getUserId());

			// 科室下拉式選單
			ComboBox unitComboBox = organUnitManagementServiceHelper
					.getUnitComboBoxByOrganId(organComboBox.getSelectedVal(), null, ComboBoxStatusEnum.ALL.getCode());

			// Admin:機關科室所有資料, 其餘角色僅取得自己所在機關資料
			boolean hasAdminPermission = loginUser.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase);
			List<OrganUnitManagementQueryDto> dtolist = kgoOrganRepository
					.findOrganAndUnitData(hasAdminPermission ? null : loginUser.getOrgan(), null, null);

			List<OrganUnitManagementHomeDataGrid> dataGrid = new LinkedList<OrganUnitManagementHomeDataGrid>();
			dtolist.forEach(l -> {
				OrganUnitManagementHomeDataGrid dg = new OrganUnitManagementHomeDataGrid();
				dg.setOrganId(l.getId().getOrganId());
				dg.setOrganName(l.getOrganName());
				dg.setUnitId(l.getId().getUnitId());
				dg.setUnitName(l.getUnitName());
				dataGrid.add(dg);
			});

			viewForm.setOrganComboBox(organComboBox);
			viewForm.setUnitComboBox(unitComboBox);
			viewForm.setGrid(dataGrid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("OrganUnitManagementUpdateHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 機關科室管理-取得單位下拉式選單
	 * 
	 */
	@Override
	public OrganUnitManagementUnitComboBoxRs unitComboBoxByOrganId(OrganUnitManagementUnitComboBoxRq rq) {
		OrganUnitManagementUnitComboBoxViewForm viewForm = new OrganUnitManagementUnitComboBoxViewForm();
		OrganUnitManagementUnitComboBoxRs rs = new OrganUnitManagementUnitComboBoxRs();

		try {
			// 科室下拉式選單
			ComboBox unitComboBox = organUnitManagementServiceHelper.getUnitComboBoxByOrganId(rq.getOrganId(), null,
					ComboBoxStatusEnum.ALL.getCode());
			viewForm.setUnitComboBox(unitComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("OrganUnitManagementUpdateHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 機關科室管理-機關科室查詢
	 */
	@Override
	public OrganUnitManagementQueryRs organUnitManagementQuery(OrganUnitManagementQueryRq rq) {
		OrganUnitManagementQueryViewForm viewForm = new OrganUnitManagementQueryViewForm();
		OrganUnitManagementQueryRs rs = new OrganUnitManagementQueryRs();
		List<OrganUnitManagementQueryDataGrid> gridList = new ArrayList<OrganUnitManagementQueryDataGrid>();

		try {

			List<OrganUnitManagementQueryDto> dtolist = kgoOrganRepository.findOrganAndUnitData(rq.getOrganId(),
					rq.getOrganName(), rq.getUnitId());

			dtolist.forEach(l -> {
				OrganUnitManagementQueryDataGrid grid = new OrganUnitManagementQueryDataGrid();
				grid.setOrganId(l.getId().getOrganId());
				grid.setOrganName(l.getOrganName());
				grid.setUnitId(l.getId().getUnitId());
				grid.setUnitName(l.getUnitName());
				gridList.add(grid);
			});

			viewForm.setGrid(gridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("OrganUnitManagementQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 機關科室管理-機關科室查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public OrganUnitManagementOrganQueryRs organUnitManagementOrganQuery(OrganUnitManagementOrganQueryRq rq) {
		OrganUnitManagementOrganQueryViewForm viewForm = new OrganUnitManagementOrganQueryViewForm();
		OrganUnitManagementOrganQueryRs rs = new OrganUnitManagementOrganQueryRs();

		try {
			String organId = rq.getOrganId();
			String organName = rq.getOrganName();

			KgoOrgan kgoOrgan = kgoOrganRepository.findByOrganNameOrOrganId(organName, organId);

			if (ObjectUtils.isEmpty(kgoOrgan)) {
				organId = StringUtils.EMPTY;
				organName = StringUtils.EMPTY;
			} else {
				organId = kgoOrgan.getOrganId();
				organName = kgoOrgan.getOrganName();
			}
			viewForm.setOrganId(organId);
			viewForm.setOrganName(organName);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organUnitManagementOrganQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 機關科室管理-機關科室維護(新增/修改)初始畫面
	 * 
	 */
	@Override
	public OrganUnitManagementEditHomeRs organUnitManagementEditHome(OrganUnitManagementEditHomeRq rq) {
		OrganUnitManagementEditHomeRs rs = new OrganUnitManagementEditHomeRs();
		OrganUnitManagementEditHomeViewForm viewForm = new OrganUnitManagementEditHomeViewForm();
		BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
		try {
			// 類別下拉式選單
			ComboBox organUnitComboBox = null;
			// 上層機關下拉式選單
			ComboBox parentOrganComboBox = null;
			// 機關下拉式選單
			ComboBox organComboBox = null;

			String type = rq.getType();
			String organId = rq.getOrganId();
			String organName = StringUtils.EMPTY;
			String unitId = rq.getUnitId();
			String unitName = StringUtils.EMPTY;

			EditType editTypeEnum = getEditType(organId, unitId, type);
			switch (editTypeEnum) {
			case INSERT_ORGAN: // 新增-機關
				parentOrganComboBox = organUnitManagementServiceHelper.getParentOrganComboBox(organId);
				organUnitComboBox = organUnitManagementServiceHelper
						.getOrganUnitComboBox(OrganUnitEnum.ORGAN.getValue());
				break;
			case INSERT_UNIT: // 新增-單位
				organUnitComboBox = organUnitManagementServiceHelper
						.getOrganUnitComboBox(OrganUnitEnum.UNIT.getValue());
				// 依登入者取得機關下拉選單
				organComboBox = organUnitManagementServiceHelper
						.getOrganComboBoxWithUserRoleLimit(loginUser.getUserId());
				break;
			case UPDATE_ORGAN: // 機關維護
				organUnitComboBox = organUnitManagementServiceHelper
						.getOrganUnitComboBox(OrganUnitEnum.ORGAN.getValue());
				organComboBox = organUnitManagementServiceHelper.getOrganComboBox(organId,
						ComboBoxStatusEnum.ONE.getCode());
				parentOrganComboBox = organUnitManagementServiceHelper.getParentOrganComboBox(organId);
				Optional<KgoOrgan> organ = kgoOrganRepository.findById(organId);
				if (organ.isPresent()) {
					organName = organ.get().getOrganName();
				}
				break;
			case UPDATE_UNIT: // 單位維護
				KgoUnit kgoUnit = kgoUnitRepository.findByIdOrganIdAndIdUnitId(organId, unitId);
				if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(kgoUnit)) {
					unitName = kgoUnit.getUnitName();
					organId = kgoUnit.getId().getOrganId();
					Optional<KgoOrgan> kgoOrgan = kgoOrganRepository.findById(organId);
					if (kgoOrgan.isPresent()) {
						organName = kgoOrgan.get().getOrganName();
					}
				}

				organUnitComboBox = organUnitManagementServiceHelper
						.getOrganUnitComboBox(OrganUnitEnum.UNIT.getValue());
				// 依登入者取得機關下拉選單, 並給定機關預設值
				organComboBox = organUnitManagementServiceHelper.getOrganComboBoxByUserRole(loginUser.getUserId(),
						organId);
				break;
			default:
				throw new KgoApiException("organUnitManagementEdit error : getEditTypeEnum can not find match one!");
			}

			viewForm.setOrganUnitComboBox(organUnitComboBox);
			viewForm.setParentOrganComboBox(parentOrganComboBox);
			viewForm.setOrganComboBox(organComboBox);
			viewForm.setOrganId(organId);
			viewForm.setOrganName(organName);
			viewForm.setUnitId(unitId);
			viewForm.setUnitName(unitName);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
			throw new KgoApiException("OrganUnitManagementHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 機關科室管理-機關科室維護(新增/修改)
	 * 
	 */
	@Override
	public OrganUnitManagementEditRs organUnitManagementEdit(OrganUnitManagementEditRq rq) {
		OrganUnitManagementEditViewForm viewForm = new OrganUnitManagementEditViewForm();
		OrganUnitManagementEditRs rs = new OrganUnitManagementEditRs();
		KgoBackEndApiError kgoBackEndApiError = KgoBackEndApiError.UNKNOWN_EXCEPTION;

		// TODO:待修正
		String creater = KgoUtil.getBackendLoginUser().getUserId();
		String updater = KgoUtil.getBackendLoginUser().getUserId();
		String executeResult = SuccessMsg.UNKNOW.getMsg();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		String name = null;
		String organOrUnit = null;
		try {
			SysLogExeType sysLogExeType = null;
			KgoOrgan organEntity = null;
			KgoUnit unitEntity = null;

			String parentOrganId = rq.getParentOrganId();
			String organId = rq.getOrganId();
			String organName = rq.getOrganName();
			String unitId = rq.getUnitId();
			String unitName = rq.getUnitName();

			EditType editTypeEnum = getEditType(organId, unitId,
					StringUtils.isBlank(organName) ? OrganUnitEnum.UNIT.getValue() : OrganUnitEnum.ORGAN.getValue());

			switch (editTypeEnum) {
			case INSERT_ORGAN: // 新增-機關
				sysLogExeType = SysLogExeType.TYPE_A;
				organOrUnit = "機關";
				name = organName;

				organEntity = new KgoOrgan();
				organEntity.setOrganId(organId);
				organEntity.setOrganName(organName);
				organEntity.setParentOrganId(parentOrganId);
				organEntity.setCreateTime(DateUtil.getCurrentTimestamp());
				organEntity.setCreateUser(creater);
				organEntity.setUpdateTime(DateUtil.getCurrentTimestamp());
				organEntity.setUpdateUser(updater);
				kgoOrganRepository.save(organEntity);
				executeResult = SuccessMsg.INSERT.getMsg();
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
				break;
			case INSERT_UNIT: // 新增-單位
				sysLogExeType = SysLogExeType.TYPE_A;
				organOrUnit = "單位";
				name = unitName;

				unitEntity = new KgoUnit();
				KgoUnitPK newUnitPK = new KgoUnitPK();
				newUnitPK.setOrganId(organId);
				newUnitPK.setUnitId(unitId);
				unitEntity.setId(newUnitPK);
				unitEntity.setUnitName(unitName);
				unitEntity.setCreateTime(DateUtil.getCurrentTimestamp());
				unitEntity.setCreateUser(creater);
				unitEntity.setUpdateTime(DateUtil.getCurrentTimestamp());
				unitEntity.setUpdateUser(updater);
				kgoUnitRepository.save(unitEntity);
				executeResult = SuccessMsg.INSERT.getMsg();
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
				break;
			case UPDATE_ORGAN: // 維護-機關
				sysLogExeType = SysLogExeType.TYPE_U;
				organOrUnit = "機關";
				name = organName;

				organEntity = kgoOrganRepository.getOne(organId);
				organEntity.setOrganName(organName);
				organEntity.setParentOrganId(parentOrganId);
				organEntity.setUpdateTime(DateUtil.getCurrentTimestamp());
				organEntity.setUpdateUser(updater);
				kgoOrganRepository.save(organEntity);
				executeResult = SuccessMsg.UPDATE.getMsg();
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
				break;
			case UPDATE_UNIT: // 維護-單位
				sysLogExeType = SysLogExeType.TYPE_U;
				organOrUnit = "單位";
				name = unitName;

				unitEntity = kgoUnitRepository.findByIdUnitId(unitId);
				kgoUnitRepository.updateOrganId(organId, unitEntity.getId().getOrganId(), unitId, unitName,
						DateUtil.getCurrentTimestamp(), updater);
				executeResult = SuccessMsg.UPDATE.getMsg();
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
				break;
			default:
				error = new KgoApiException("organUnitManagementEdit error : getEditTypeEnum can not find match one!");
			}
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.OrganM);
			viewForm.setMsg(executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(kgoBackEndApiError.getErrorMsgKey());
			error = new KgoApiException("OrganUnitManagementUpdate error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn(organOrUnit, name));
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
	 * 機關科室管理-機關科室刪除
	 */
	@Override
	public OrganUnitManagementDeleteRs organUnitManagementDelete(OrganUnitManagementDeleteRq rq) {
		OrganUnitManagementDeleteRs rs = new OrganUnitManagementDeleteRs();
		OrganUnitManagementDeleteViewForm viewForm = new OrganUnitManagementDeleteViewForm();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		String name = null;
		String organOrUnit = null;
		try {
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.OrganM);
			if (StringUtils.isNotBlank(rq.getUnitId())) {
				name = kgoUnitRepository.findByIdUnitId(rq.getUnitId()).getUnitName();
				organOrUnit = "單位";
			} else {
				name = kgoOrganRepository.findById(rq.getOrganId()).get().getOrganName();
				organOrUnit = "機關";
			}
			String organId = rq.getOrganId();
			String unitId = rq.getUnitId();
			String executeResult = SuccessMsg.DELETE.getMsg();
			if (StringUtils.isNotBlank(unitId)) {
				// 刪除單位資料
				KgoUnitPK unitPK = new KgoUnitPK();
				unitPK.setOrganId(organId);
				unitPK.setUnitId(unitId);
				kgoUnitRepository.deleteById(unitPK);

				executeResult = "單位" + executeResult;
			} else if (StringUtils.isNotBlank(organId)) {
				// 刪除機關資料
				kgoOrganRepository.deleteById(organId);
				kgoUnitRepository.deleteByIdOrganId(organId);

				executeResult = "機關" + executeResult;
			}

			viewForm.setMsg(executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			error = new KgoApiException("OrganUnitManagementDelete error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			// 回饋活動名稱
			List<OperationColumn> memoList = new ArrayList<>();

			memoList.add(new OperationColumn(organOrUnit, name));
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
	 * decide to decision process
	 * 
	 * 1. 新增-機關 (INSERT_ORGAN) 2.新增-單位 (INSERT_UNIT) 3. 維護-機關 (UPDATE_ORGAN) 4.
	 * 維護-單位 (UPDATE_UNIT)
	 * 
	 * @param organId
	 * @param unitId
	 * @param typeName
	 * @return
	 */
	private EditType getEditType(String organId, String unitId, String typeName) {
		boolean organIsNotExit = !kgoOrganRepository.existsById(organId);
		boolean unitIsNotExit = ObjectUtils.isEmpty(kgoUnitRepository.findByIdOrganIdAndIdUnitId(organId, unitId));

		return StringUtils.equalsAnyIgnoreCase(typeName, OrganUnitEnum.ORGAN.getValue())
				? (organIsNotExit ? EditType.INSERT_ORGAN : EditType.UPDATE_ORGAN)
				: (unitIsNotExit ? EditType.INSERT_UNIT : EditType.UPDATE_UNIT);
	}

}
