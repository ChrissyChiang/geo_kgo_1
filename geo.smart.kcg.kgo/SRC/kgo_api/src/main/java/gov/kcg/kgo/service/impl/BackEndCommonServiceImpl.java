package gov.kcg.kgo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCounty;
import gov.kcg.kgo.model.KgoZip;
import gov.kcg.kgo.repository.KgoCountyRepository;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.repository.KgoZipRepository;
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.service.impl.helper.BackEndCommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rq.AreaOrganSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rs.AreaOrganSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rs.bean.AreaOrganSelectHomeViewForm;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rq.AreaOrganSelectZipHomeRq;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rs.AreaOrganSelectZipHomeRs;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rs.bean.AreaOrganSelectZipHomeViewForm;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rq.OrganComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.bean.OrganComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rq.OrganSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rs.OrganSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rs.bean.OrganSelectHomeViewForm;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rq.OrganSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.OrganSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.bean.OrganSelectQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.bean.OrganSelectQueryViewForm;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rq.OrganUnitUserSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rs.OrganUnitUserSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rs.bean.OrganUnitUserSelectHomeViewForm;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rq.OrganUnitUserSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rs.OrganUnitUserSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rs.bean.OrganUnitUserSelectQueryViewForm;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rq.UnitQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rs.UnitQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rs.bean.UnitQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rs.bean.UnitQueryViewForm;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rq.UnitComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.UnitComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.bean.UnitComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

/**
 * 
 * Back End Common Service
 * 
 * @author TPIuser
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("BackEndCommonService")
public class BackEndCommonServiceImpl extends KgoBackEndServiceImpl implements BackEndCommonService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackEndCommonServiceImpl.class);

	@Autowired
	private KgoUserRepository kgoUserRepository;

	@Autowired
	private KgoOrganRepository kgoOrganRepository;

	@Autowired
	private KgoUnitRepository kgoUnitRepository;

	@Autowired
	private KgoCountyRepository kgoCountyRepository;

	@Autowired
	private KgoZipRepository kgoZipRepository;

	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper =
			OrganUnitManagementServiceHelper.getInstance();

	private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

	private BackEndCommonServiceHelper backEndCommonServiceHelper = BackEndCommonServiceHelper.getInstance();

	/**
	 * 共用-人員清單Popup 初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public OrganUnitUserSelectHomeRs organUnitUserSelectHomeAction(OrganUnitUserSelectHomeRq rq) {
		OrganUnitUserSelectHomeViewForm viewForm = new OrganUnitUserSelectHomeViewForm();
		OrganUnitUserSelectHomeRs rs = new OrganUnitUserSelectHomeRs();

		try {

			String organId = rq.getOrganId();
			String unitId = rq.getUnitId();
			String roleId = rq.getRoleId();

			ComboBox organComboBox =
					organUnitManagementServiceHelper.getOrganComboBox(organId, ComboBoxStatusEnum.ALL.getCode());
			ComboBox unitComboBox = StringUtils.isBlank(organId) ? commonServiceHelper.getDefaultComboBox()
					: organUnitManagementServiceHelper.getUnitComboBoxByOrganId(organId, StringUtils.EMPTY,
							ComboBoxStatusEnum.ALL.getCode());

			if (StringUtils.isNotBlank(unitId)) {
				unitComboBox.setSelectedVal(unitId);
			}

			List<AccountManagementQueryDto> dtoList =
					kgoUserRepository.findAccountQueryData(organId, unitId, null, null, roleId);

			viewForm.setGrid(dtoList);
			viewForm.setOrganComboBox(organComboBox);
			viewForm.setUnitComboBox(unitComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organUnitUserSelectHomeAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 共用-人員清單Popup-查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public OrganUnitUserSelectQueryRs organUnitUserSelectQueryAction(OrganUnitUserSelectQueryRq rq) {
		OrganUnitUserSelectQueryViewForm viewForm = new OrganUnitUserSelectQueryViewForm();
		OrganUnitUserSelectQueryRs rs = new OrganUnitUserSelectQueryRs();

		try {
			String organId = rq.getOrganId();
			String unitId = rq.getUnitId();
			String roleId = rq.getRoleId();

			List<AccountManagementQueryDto> dtoList =
					kgoUserRepository.findAccountQueryData(organId, unitId, null, null, roleId);
			viewForm.setGrid(dtoList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organUnitUserSelectQueryAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 共用-人員清單Popup - 單位下拉式選單查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public UnitComboBoxQueryRs unitComboBoxQueryAction(UnitComboBoxQueryRq rq) {
		UnitComboBoxQueryViewForm viewForm = new UnitComboBoxQueryViewForm();
		UnitComboBoxQueryRs rs = new UnitComboBoxQueryRs();

		try {
			String organId = rq.getOrganId();
			ComboBox unitComboBox = organUnitManagementServiceHelper.getUnitComboBoxByOrganId(organId,
					StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode());
			viewForm.setUnitComboBox(unitComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("commonOrganUnitUserSelectQueryAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 共用-機關單位下拉選單
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public OrganComboBoxQueryRs organComboBoxQueryAction(OrganComboBoxQueryRq rq) {
		OrganComboBoxQueryViewForm viewForm = new OrganComboBoxQueryViewForm();
		OrganComboBoxQueryRs rs = new OrganComboBoxQueryRs();

		try {
			ComboBox organComboBox = organUnitManagementServiceHelper.getOrganComboBox();
			organComboBox.setSelectedVal(rq.getOrganId());
			viewForm.setOrganComboBox(organComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organComboBoxAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 共用-機關列表Popup-初始畫面
	 */
	@Override
	public OrganSelectHomeRs organSelectHomeAction(OrganSelectHomeRq rq) {
		OrganSelectHomeViewForm viewForm = new OrganSelectHomeViewForm();
		OrganSelectHomeRs rs = new OrganSelectHomeRs();
		String loginUserId = KgoUtil.getLoginUserId();
		try {
			List<OrganSelectQueryDataGrid> grid = new LinkedList<OrganSelectQueryDataGrid>();
			String organId = rq.getOrganId();
			ComboBox organComboBox = organUnitManagementServiceHelper.getOrganComboBoxWithUserRoleLimit(loginUserId);

			if (StringUtils.isNotBlank(organId)) {
				organComboBox.setSelectedVal(organId);
				grid = queryOrganOrParentOrganData(organId);
			}

			viewForm.setGrid(grid);
			viewForm.setOrganComboBox(organComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organSelectHomeAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 機關列表Popup-查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public OrganSelectQueryRs organSelectQueryAction(OrganSelectQueryRq rq) {
		OrganSelectQueryViewForm viewForm = new OrganSelectQueryViewForm();
		OrganSelectQueryRs rs = new OrganSelectQueryRs();
		try {
			viewForm.setGrid(queryOrganOrParentOrganData(rq.getOrganId()));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organSelectQueryAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 共用-區機關選擇-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public AreaOrganSelectHomeRs areaOrganSelectHomeAction(AreaOrganSelectHomeRq rq) {
		AreaOrganSelectHomeViewForm viewForm = new AreaOrganSelectHomeViewForm();
		AreaOrganSelectHomeRs rs = new AreaOrganSelectHomeRs();
		String loginUserId = KgoUtil.getLoginUserId();
		BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
		if (backendLoginUser != null && StringUtils.isNotBlank(backendLoginUser.getUserId())) {
			loginUserId = backendLoginUser.getUserId();
		} else {
			LOGGER.warn("Could not get LoginUser from Session! use default userId [A12xxxx789]");
		}

		try {
			String organId = rq.getOrganId();
			/** 選擇機關下拉式選單 **/
			ComboBox organComboBox = organUnitManagementServiceHelper.getOrganComboBoxWithUserRoleLimit(loginUserId);

			/** 選擇區下拉式選單 **/
			List<KgoCounty> kgoCountyList = kgoCountyRepository.findAll();
			String countyId = StringUtils.EMPTY;
			if (ObjectUtils.isNotEmpty(kgoCountyList)) {
				countyId = kgoCountyList.get(0).getCountyId();
			}
			ComboBox countyComboBox = backEndCommonServiceHelper.getCountyComboBox(kgoCountyList, countyId);

			/** 選擇區域清單 **/
			List<OrganSelectQueryDataGrid> grid = new LinkedList<OrganSelectQueryDataGrid>();

			if (StringUtils.isNotBlank(rq.getOrganId())) {
				organComboBox.setSelectedVal(organId);
				grid = queryOrganOrParentOrganData(organId);
			}

			viewForm.setOrganComboBox(organComboBox);
			viewForm.setCountyComboBox(countyComboBox);
			viewForm.setGrid(grid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("areaOrganSelectHomeAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 共用-區機關選擇-鄉鎮選擇-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public AreaOrganSelectZipHomeRs areaOrganSelectZipHomeAction(AreaOrganSelectZipHomeRq rq) {
		AreaOrganSelectZipHomeViewForm viewForm = new AreaOrganSelectZipHomeViewForm();
		AreaOrganSelectZipHomeRs rs = new AreaOrganSelectZipHomeRs();

		try {
			String countyId = rq.getCountyId();
			List<KgoZip> kgoZipList = kgoZipRepository.findByCountyId(countyId);
			List<CheckBox> zipCheckBox = backEndCommonServiceHelper.getZipCheckBoxList(kgoZipList);
			viewForm.setZipCheckBox(zipCheckBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("areaOrganSelectZipHomeAction error " + e.getMessage(), e);
		}

		return rs;
	} //areaOrganSelectZipHomeAction

	/**
	 * unit query by organId
	 * 
	 * @param organId
	 * @return
	 */
	private List<UnitQueryDataGrid> unitQueryByOrganId(String organId) {
		return kgoUnitRepository.findAllByIdOrganId(organId).stream().map(l -> {
			UnitQueryDataGrid dg = new UnitQueryDataGrid();
			dg.setUnitId(l.getId().getUnitId());
			dg.setUnitName(l.getUnitName());
			return dg;
		}).collect(Collectors.toList());
	}

	/**
	 * 共用-機關科室查詢
	 */
	@Override
	public UnitQueryRs unitQueryAction(UnitQueryRq rq) {
		UnitQueryViewForm viewForm = new UnitQueryViewForm();
		UnitQueryRs rs = new UnitQueryRs();

		try {
			String organId = rq.getOrganId();
			List<UnitQueryDataGrid> unitQueryDataGridList = unitQueryByOrganId(organId);
			viewForm.setGrid(unitQueryDataGridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organUnitSelectHomeAction error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 
	 * @param organId
	 * @return
	 */
	private List<OrganSelectQueryDataGrid> queryOrganOrParentOrganData(String organId) {
		return kgoOrganRepository.findByOrganIdOrParentOrganId(organId, organId).stream().map(l -> {
			OrganSelectQueryDataGrid dg = new OrganSelectQueryDataGrid();
			dg.setOrganId(l.getOrganId());
			dg.setOrganName(l.getOrganName());
			return dg;
		}).collect(Collectors.toList());
	}
}
