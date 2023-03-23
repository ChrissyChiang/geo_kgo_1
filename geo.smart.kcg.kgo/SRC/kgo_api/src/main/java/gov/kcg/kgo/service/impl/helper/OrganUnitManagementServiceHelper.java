package gov.kcg.kgo.service.impl.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.backend.OrganCaseOfficerEnum;
import gov.kcg.kgo.enums.backend.OrganUnitEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRoleRepository;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

public class OrganUnitManagementServiceHelper extends KgoServiceHelper {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganUnitManagementServiceHelper.class);

	private static final String KGOORGAN_LABEL_FIELD_NAME = "organName";
	private static final String KGOORGAN_VALUE_FIELD_NAME = "organId";
	private static final String KGOUNIT_LABEL_FIELD_NAME = "unitName";
	private static final String KGOUNIT_VALUE_FIELD_NAME = "unitId";

	/**
	 * Instantiates a new organ unit management service helper.
	 */
	public OrganUnitManagementServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final OrganUnitManagementServiceHelper instance = new OrganUnitManagementServiceHelper();
	}

	/**
	 * Gets the single instance of OrganUnitManagementServiceHelper.
	 *
	 * @return single instance of OrganUnitManagementServiceHelper
	 */
	public static OrganUnitManagementServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 取得只有[機關/單位]選項的 comboBox
	 * 
	 * @return ComboBox
	 */
	public ComboBox getOrganUnitComboBox(String typeValue) {

		ComboBox comboBox = new ComboBox();

		for (OrganUnitEnum ou : OrganUnitEnum.values()) {
			if (StringUtils.equalsIgnoreCase(typeValue, ou.getValue())) {
				comboBox.add(ou.getLabel(), ou.getValue(), true);
			} else {
				comboBox.add(ou.getLabel(), ou.getValue(), false);
			}
		}

		return comboBox;
	}

	/**
	 * 取得上層機關的comboBox
	 * 
	 * organId is null -> 新增機關，給所有organ資料 organId is not null ->
	 * 維護機關，給所有organ資料，但設定該機關ID所找到的上層機關ID
	 * 
	 * @param organId
	 * @return
	 */
	public ComboBox getParentOrganComboBox(String organId) {
		KgoOrganRepository kgoOrganRepository = SpringUtil.getDao(KgoOrganRepository.class);
		List<KgoOrgan> kgoOrganList = kgoOrganRepository.findAll();
		String parentOrganId = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(organId)) {
			KgoOrgan kgoOrgan = kgoOrganRepository.getOne(organId);
			parentOrganId = kgoOrgan.getParentOrganId();
			kgoOrganList.remove(kgoOrgan); // 從上層機關清單中移除自身機關
		}

		return super.getComboBox(kgoOrganList, KGOORGAN_LABEL_FIELD_NAME, KGOORGAN_VALUE_FIELD_NAME, parentOrganId,
				ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 取得單一機關下拉式選單
	 * 
	 * @param organId
	 * @return
	 */
	public ComboBox getOneOrganComboBox(String organId) {
		return getOrganComboBox(organId, ComboBoxStatusEnum.ONE.getCode());
	}

	/**
	 * 取得所有機關下拉式選單
	 * 
	 * @param organId
	 * @return
	 */
	public ComboBox getOrganComboBox() {
		return getOrganComboBox(StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode());
	}

	/**
	 * 取得所有機關下拉式選單
	 * 
	 * @param defaultValue
	 * @param ComboBoxStatusEnum
	 * @param hasDefaultOption
	 * @return ComboBox
	 */
	public ComboBox getOrganComboBox(String defaultValue, Integer ComboBoxStatusEnum) {
		KgoOrganRepository kgoOrganRepository = SpringUtil.getDao(KgoOrganRepository.class);

		// 排除 OrganName = NUll
		List<KgoOrgan> kgoOrganList = kgoOrganRepository.findAll().stream()
				.filter(x -> StringUtils.isNotBlank(x.getOrganName())).collect(Collectors.toList());
		defaultValue = StringUtils.isBlank(defaultValue) ? kgoOrganList.get(0).getOrganId() : defaultValue;
		return super.getComboBox(kgoOrganList, KGOORGAN_LABEL_FIELD_NAME, KGOORGAN_VALUE_FIELD_NAME, defaultValue,
				ComboBoxStatusEnum, false);
	}

	/**
	 * 取得使用者所屬機關的下拉式選單
	 * 
	 * 條件1：若是機關管理者、案件管理者需限制僅帶出登入者底下的機關。 20201126 - 調整為除了系統管理者外，其餘使用者皆是取得自己所屬機關的下拉式選單
	 * 
	 * @param userId
	 * @return
	 */
	public ComboBox getOrganComboBoxWithUserRoleLimit(String userId) {
		KgoOrganRepository kgoOrganRepository = SpringUtil.getDao(KgoOrganRepository.class);
		KgoUserRoleRepository kgoUserRoleRepository = SpringUtil.getDao(KgoUserRoleRepository.class);

		String roleStr = kgoUserRoleRepository.getUserRoleStr(userId);
		if (StringUtils.isNotEmpty(roleStr) && !roleStr.contains(KgoRoleEnum.ADMIN.getValue()) && !roleStr.contains(KgoRoleEnum.ADMIN.getValue().toLowerCase())) {
			List<KgoOrgan> kgoOrganList = kgoOrganRepository.findOrganByUserId(userId);
			return super.getComboBox(kgoOrganList, KGOORGAN_LABEL_FIELD_NAME, KGOORGAN_VALUE_FIELD_NAME,
					kgoOrganList.get(0).getOrganId(), ComboBoxStatusEnum.ALL.getCode(), false);
		}
		return getOrganComboBox();
	}

	/**
	 * 取得使用者所屬機關的下拉式選單, 若為系統管理者, 則取得所有機關清單
	 * 
	 * @param userId
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getOrganComboBoxByUserRole(String userId, String defaultValue) {
		KgoOrganRepository kgoOrganRepository = SpringUtil.getDao(KgoOrganRepository.class);
		KgoUserRoleRepository kgoUserRoleRepository = SpringUtil.getDao(KgoUserRoleRepository.class);

		String roleStr = kgoUserRoleRepository.getUserRoleStr(userId);
		if (StringUtils.isNotEmpty(roleStr) && !roleStr.contains(KgoRoleEnum.ADMIN.getValue())&& !roleStr.contains(KgoRoleEnum.ADMIN.getValue().toLowerCase())) {
			List<KgoOrgan> kgoOrganList = kgoOrganRepository.findOrganByUserId(userId);
			return super.getComboBox(kgoOrganList, KGOORGAN_LABEL_FIELD_NAME, KGOORGAN_VALUE_FIELD_NAME, defaultValue,
					ComboBoxStatusEnum.ALL.getCode(), false);
		}
		return getOrganComboBox(defaultValue, ComboBoxStatusEnum.ALL.getCode());
	}

	/**
	 * 根據給的機關代碼來取得對應的單位下拉式選單
	 * 
	 * @param defaultValue
	 * @param ComboBoxStatusEnum
	 * @param hasDefaultOption
	 * @return ComboBox
	 */
	public ComboBox getUnitComboBoxByOrganId(String organId, String defaultValue, Integer ComboBoxStatusEnum) {
		KgoUnitRepository kgoUnitRepository = SpringUtil.getDao(KgoUnitRepository.class);
		List<KgoUnit> kgoUnitList = kgoUnitRepository.findAllByIdOrganId(organId);

		return super.getComboBox(kgoUnitList, KGOUNIT_LABEL_FIELD_NAME, KGOUNIT_VALUE_FIELD_NAME, defaultValue,
				ComboBoxStatusEnum, false);
	}

	/**
	 * 取得只有[機關/承辦]選項的 comboBox
	 * 
	 * @return ComboBox
	 */
	public ComboBox getOrganCaseOfficerComboBox(String typeValue) {

		ComboBox comboBox = new ComboBox();

		for (OrganCaseOfficerEnum oc : OrganCaseOfficerEnum.values()) {
			if (StringUtils.equalsIgnoreCase(typeValue, oc.getValue())) {
				comboBox.add(oc.getLabel(), oc.getValue(), true);
			} else {
				comboBox.add(oc.getLabel(), oc.getValue(), false);
			}
		}

		return comboBox;
	}
}
