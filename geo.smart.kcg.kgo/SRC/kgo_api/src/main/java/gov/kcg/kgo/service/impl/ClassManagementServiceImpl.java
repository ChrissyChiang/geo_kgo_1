package gov.kcg.kgo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import gov.kcg.kgo.dto.ClassManagementClassQueryDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.MainTypeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoGroupLevel;
import gov.kcg.kgo.repository.KgoGroupLevelRepository;
import gov.kcg.kgo.service.ClassManagementService;
import gov.kcg.kgo.service.impl.helper.ClassManagementServiceHelper;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rq.ClassManagementClassDeleteRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rs.ClassManagementClassDeleteRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rs.bean.ClassManagementClassDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.classManagement.classHome.rs.ClassManagementClassHomeRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classHome.rs.bean.ClassManagementClassHomeViewForm;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rq.ClassManagementClassOnOffRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rs.ClassManagementClassOnOffRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rs.bean.ClassManagementClassOnOffViewForm;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rq.ClassManagementClassQueryRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.ClassManagementClassQueryRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.bean.ClassManagementClassQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.bean.ClassManagementClassQueryViewForm;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rq.ClassManagementClassUpdateRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rs.ClassManagementClassUpdateRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rs.bean.ClassManagementClassUpdateViewForm;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rq.ClassManagementClassUpdateHomeRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rs.ClassManagementClassUpdateHomeRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rs.bean.ClassManagementClassUpdateHomeViewForm;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rq.ClassManagementSubClassQueryRq;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rs.ClassManagementSubClassQueryRs;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rs.bean.ClassManagementSubClassQueryViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

@Transactional(rollbackFor = Exception.class)
@Service("ClassManagementService")
public class ClassManagementServiceImpl extends KgoBackEndServiceImpl implements ClassManagementService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassManagementServiceImpl.class);

	@Autowired
	private KgoGroupLevelRepository kgoGroupLevelRepository;

	private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();
	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
			.getInstance();
	private ClassManagementServiceHelper classManagementServiceHelper = ClassManagementServiceHelper.getInstance();

	/**
	 * 分類維護功能-畫面初始
	 */
	@Override
	public ClassManagementClassHomeRs classManagementClassHome() {
		ClassManagementClassHomeRs rs = new ClassManagementClassHomeRs();
		ClassManagementClassHomeViewForm viewForm = new ClassManagementClassHomeViewForm();
		String defaultMainType = MainTypeEnum.ORGAN.getValue();
		try {
			/** group comboBox **/
			ComboBox mainTypeComboBox = commonServiceHelper.getComboBoxWithEnum(MainTypeEnum.class);
			mainTypeComboBox.setSelectedVal(defaultMainType);

			/** subType comboBox **/
			ComboBox subTypeComboBox = classManagementServiceHelper.getSubTypeComboBox(defaultMainType);

			/** grid data **/
			List<ClassManagementClassQueryDto> dtoList = kgoGroupLevelRepository.findKgoGroupLevelData();

			List<ClassManagementClassQueryDataGrid> classManagementClassQueryDataGridLits = dtoList.stream().map(l -> {

				String mainTypeNm = MainTypeEnum.getMainTypeEnum(l.getMainType()).getLabel();
				String state = PublishStateEnum.getPublishStateEnum(l.getState()).getLabel();

				ClassManagementClassQueryDataGrid dg = new ClassManagementClassQueryDataGrid();
				dg.setMainType(l.getMainType());
				dg.setMainTypeNM(mainTypeNm);
				dg.setDetailNM(l.getDetailNM());
				dg.setPublishTime(l.getPublishTime());
				dg.setSeq(l.getSeq());
				dg.setState(state);

				return dg;
			}).collect(Collectors.toList());

			viewForm.setMainTypeComboBox(mainTypeComboBox);
			viewForm.setSubTypeComboBox(subTypeComboBox);
			viewForm.setGrid(classManagementClassQueryDataGridLits);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("classManagementClassHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 分類維護功能-主畫面搜尋
	 */
	@Override
	public ClassManagementClassQueryRs classManagementClassQuery(ClassManagementClassQueryRq rq) {
		ClassManagementClassQueryViewForm viewForm = new ClassManagementClassQueryViewForm();
		ClassManagementClassQueryRs rs = new ClassManagementClassQueryRs();

		try {
			String mainType = rq.getMainType();
			String name = rq.getName();
			String[] publishTime = rq.getPublishTime();
			String publishTimeArrayStart = null; // 上下架時間起日
			String publishTimeArrayEnd = null; // 上下架時間訖日

			if (!ObjectUtils.isEmpty(publishTime)) {
				publishTimeArrayStart = rq.getPublishTime()[0];
				publishTimeArrayEnd = rq.getPublishTime()[1];
			}

			List<ClassManagementClassQueryDto> dtolist = kgoGroupLevelRepository.findKgoGroupLevelData(null, name,
					mainType, publishTimeArrayStart, publishTimeArrayEnd);

			List<ClassManagementClassQueryDataGrid> gridList = dtolist.stream().map(l -> {
				ClassManagementClassQueryDataGrid grid = new ClassManagementClassQueryDataGrid();
				String mainTypeNm = MainTypeEnum.getMainTypeEnum(l.getMainType()).getLabel();
				String state = PublishStateEnum.getPublishStateEnum(l.getState()).getLabel();
				grid.setMainType(l.getMainType());
				grid.setMainTypeNM(mainTypeNm);
				grid.setDetailNM(l.getDetailNM());
				grid.setPublishTime(l.getPublishTime());
				grid.setSeq(l.getSeq());
				grid.setState(state);
				return grid;
			}).collect(Collectors.toList());

			viewForm.setGrid(gridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("classManagementClassQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 分類維護功能-類別新增/維護-畫面初始
	 * 
	 */
	@Override
	public ClassManagementClassUpdateHomeRs classManagementClassUpdateHome(ClassManagementClassUpdateHomeRq rq) {
		ClassManagementClassUpdateHomeViewForm viewForm = new ClassManagementClassUpdateHomeViewForm();
		ClassManagementClassUpdateHomeRs rs = new ClassManagementClassUpdateHomeRs();

		try {
			Integer seq = rq.getSeq();
			ComboBox mainTypeComboBox = commonServiceHelper.getComboBoxWithEnum(MainTypeEnum.class); // 隸屬主類別下拉式選單
			ComboBox organComboBox = organUnitManagementServiceHelper.getOrganComboBox(); // 機關下拉式選單
			String name = StringUtils.EMPTY; // 分類名稱

			if (ObjectUtils.isEmpty(seq)) {
				// 新增
				mainTypeComboBox.setSelectedVal(MainTypeEnum.ORGAN.getValue());
			} else {
				// 維護
				KgoGroupLevel entity = kgoGroupLevelRepository.getOne(seq);
				String mainType = entity.getMainType();

				mainTypeComboBox.setSelectedVal(mainType);
				if (mainType.equalsIgnoreCase(MainTypeEnum.ORGAN.getValue())) {
					organComboBox.setSelectedVal(entity.getOrganId());
				}
				name = entity.getName();
			}
			viewForm.setName(name);
			viewForm.setMainTypeComboBox(mainTypeComboBox);
			viewForm.setOrganComboBox(organComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("classManagementClassUpdateHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 分類維護功能-類別新增/維護
	 * 
	 */
	@Override
	public ClassManagementClassUpdateRs classManagementClassUpdate(ClassManagementClassUpdateRq rq) {
		ClassManagementClassUpdateViewForm viewForm = new ClassManagementClassUpdateViewForm();
		ClassManagementClassUpdateRs rs = new ClassManagementClassUpdateRs();
		KgoBackEndApiError kgoBackEndApiError = KgoBackEndApiError.UNKNOWN_EXCEPTION;
		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			// 後台、新增或修改、分類維護功能
			
			SysLogExeType sysLogExeType=ObjectUtils.isEmpty(rq.getSeq()) ? SysLogExeType.TYPE_A:SysLogExeType.TYPE_U;
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.TypeM);
			

			String msg = SuccessMsg.UNKNOW.getMsg();

			Integer seq = rq.getSeq();
			String mainType = rq.getMainType();
			String name = rq.getName();
			String organId = rq.getOrganId();
			boolean flag = true;

			if (StringUtils.isBlank(organId)) {
				if (kgoGroupLevelRepository.countByName(name) > 0) {
					flag = false;
				}
			} else {
				if (kgoGroupLevelRepository.countByOrganId(organId) > 0) {
					flag = false;
				}
			}

			if (flag) {
				KgoGroupLevel entity = null;
				if (ObjectUtils.isEmpty(seq)) {
					// insert
					kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
					msg = SuccessMsg.INSERT.getMsg();

					entity = new KgoGroupLevel();
					entity.setCreateTime(DateUtil.getCurrentTimestamp());
					entity.setCreateUser(KgoUtil.getTempCreateUser()); // TODO:....待修正
					entity.setMainType(mainType);
					entity.setName(name);
					entity.setOrganId(organId);
					entity.setState(PublishStateEnum.OFF.getValue());
					entity.setUpdateTime(DateUtil.getCurrentTimestamp());
					entity.setUpdateUser(KgoUtil.getTempUpdateUser()); // TODO:....待修正
				} else {
					// update
					kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
					msg = SuccessMsg.UPDATE.getMsg();

					entity = kgoGroupLevelRepository.getOne(seq);
					entity.setName(name);
					entity.setMainType(rq.getMainType());
					entity.setOrganId(organId);
					entity.setUpdateTime(DateUtil.getCurrentTimestamp());
					entity.setUpdateUser(KgoUtil.getTempUpdateUser()); // TODO:....待修正
				}

				kgoGroupLevelRepository.save(entity);
			} else {
				msg = String.format("%s[%s]已被使用過", MainTypeEnum.getMainTypeEnum(mainType).getLabel(), name);
			}

			viewForm.setMsg(msg);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(kgoBackEndApiError.getErrorMsgKey());
			error= new KgoApiException("classManagementClassUpdate error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			
			memoList.add(new OperationColumn("分類", String.format("%s - %s",MainTypeEnum.getMainTypeEnum(rq.getMainType()).getLabel(),rq.getName())));
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
	 * 分類維護功能-類別刪除
	 */
	@Override
	public ClassManagementClassDeleteRs classManagementClassDelete(ClassManagementClassDeleteRq rq) {
		ClassManagementClassDeleteRs rs = new ClassManagementClassDeleteRs();
		ClassManagementClassDeleteViewForm viewForm = new ClassManagementClassDeleteViewForm();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		String typeName = null;
		try {
			// 後台、刪除、分類維護功能
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.TypeM);
			typeName=kgoGroupLevelRepository.findById(rq.getSeq()).get().getName();
			kgoGroupLevelRepository.deleteById(rq.getSeq());
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			error= new KgoApiException("classManagementClassDelete error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("分類",typeName));

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
	 * 分類維護功能-上下架
	 */
	@Override
	public ClassManagementClassOnOffRs classManagementClassOnOff(ClassManagementClassOnOffRq rq) {

		ClassManagementClassOnOffRs rs = new ClassManagementClassOnOffRs();
		ClassManagementClassOnOffViewForm viewForm = new ClassManagementClassOnOffViewForm();

		try {
			int seq = rq.getSeq();
			String state = rq.getState();
			String executeResult = SuccessMsg.UPDATE.getMsg();
			String updateUser = KgoUtil.getTempUpdateUser(); // TODO: ....待修正

			KgoGroupLevel KgoGroupLevel = kgoGroupLevelRepository.getOne(seq);
			KgoGroupLevel.setState(rq.getState());
			KgoGroupLevel.setUpdateTime(DateUtil.getCurrentTimestamp());
			KgoGroupLevel.setUpdateUser(updateUser);
			KgoGroupLevel.setPublishTime(DateUtil.getCurrentTimestamp());

			kgoGroupLevelRepository.save(KgoGroupLevel);

			viewForm.setMsg(PublishStateEnum.getPublishStateEnum(state).getLabel() + executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("classManagementClassOnOff error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 次分類名稱下拉式選單
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public ClassManagementSubClassQueryRs classManagementSubClassQuery(ClassManagementSubClassQueryRq rq) {
		ClassManagementSubClassQueryRs rs = new ClassManagementSubClassQueryRs();
		ClassManagementSubClassQueryViewForm viewForm = new ClassManagementSubClassQueryViewForm();
		try {
			String mainType = rq.getMainType();
			viewForm.setSubTypeComboBox(classManagementServiceHelper.getSubTypeComboBox(mainType));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("classManagementSubClassQuery error " + e.getMessage(), e);
		}
		return rs;
	}

}
