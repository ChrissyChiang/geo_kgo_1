package gov.kcg.kgo.service.impl.helper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.MainTypeEnum;
import gov.kcg.kgo.model.KgoCode;
import gov.kcg.kgo.repository.KgoCaseManagerRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoCodeRepository;
import gov.kcg.kgo.repository.KgoGroupLevelRepository;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

public class CaseManagementServiceHelper extends KgoServiceHelper {

	private static final String CASE_MANAGER_COMBOBOX_LABEL_FIELD = "name";
	private static final String CASE_MANAGER_COMBOBOX_VALUE_FIELD = "manager";
	private static final String CODE_TYPE_COMBOBOX_LABEL_FIELD = "codeName";
	private static final String CODE_TYPE_COMBOBOX_VALUE_FIELD = "codeId";
	private static final String GROUP_LEVEL_ORGAN_COMBOBOX_LABEL_FIELD = "name";
	private static final String GROUP_LEVEL_ORGAN_COMBOBOX_VALUE_FIELD = "seq";
	private static final String CASESET_ORGAN_COMBOX_LABEL_FIELD = "organName";
	private static final String CASESET_ORGAN_COMBOX_VALUE_FIELD = "organSeq";


	/**
	 * Instantiates a new calss management service helper.
	 */
	public CaseManagementServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CaseManagementServiceHelper instance = new CaseManagementServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static CaseManagementServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 管理者清單的 ComboBox
	 * 
	 * @return ComboBox
	 */
	public ComboBox getCaseManagerBox(String organId) {
		KgoCaseManagerRepository kgoCaseManagerRepository = SpringUtil.getDao(KgoCaseManagerRepository.class);
		return getComboBox(kgoCaseManagerRepository.findCaseManagerByOrganId(organId), CASE_MANAGER_COMBOBOX_LABEL_FIELD,
				CASE_MANAGER_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 根據不同 CodeType 所產生的 ComboBox
	 * 20201209 move to CommonServiceHelper.getCodeTypeComboBox() to share with other function by Jay
	 * @return ComboBox
	 */
	@Deprecated
	public ComboBox getCodeTypeComboBox(String codeType) {
		KgoCodeRepository kgoCodeRepository = SpringUtil.getDao(KgoCodeRepository.class);
		List<KgoCode> kgoCodeList = kgoCodeRepository.findByIdCodeType(codeType);
		String defaultValue = (ObjectUtils.isEmpty(kgoCodeList) || kgoCodeList.size() == 0) ? StringUtils.EMPTY
				: kgoCodeList.get(0).getCodeName();
		return getComboBox(kgoCodeList, CODE_TYPE_COMBOBOX_LABEL_FIELD, CODE_TYPE_COMBOBOX_VALUE_FIELD, defaultValue,
				ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 服務啟用的 CheckBox
	 * 
	 * @return ComboBox
	 */
	public List<CheckBox> getApplyTypeCheckBox() {
		List<CheckBox> checkBoxList = new LinkedList<CheckBox>();
		Arrays.asList(ApplyTypeEnum.values()).forEach(l -> {
			checkBoxList.add(getCheckBox(l.getLabel(), l.getValue()));
		});
		return checkBoxList;
	}

	// public ComboBox getGroupLevelOrganComboBoxByUser() {
	//
	// }

	/**
	 * 機關下拉式選單
	 * 
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getGroupLevelOrganComboBox(String defaultValue) {
		return getGroupLevelComboBox(MainTypeEnum.ORGAN.getValue(), defaultValue);
	}

	/**
	 * 角色下拉式選單
	 * 
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getGroupLevelRoleComboBox(String defaultValue) {
		return getGroupLevelComboBox(MainTypeEnum.ROLE.getValue(), defaultValue);
	}

	/**
	 * 服務下拉式選單
	 * 
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getGroupLevelServiceComboBox(String defaultValue) {
		return getGroupLevelComboBox(MainTypeEnum.SERVICE.getValue(), defaultValue);
	}

	/**
	 * 
	 * @param mainType
	 * @return
	 */
	private ComboBox getGroupLevelComboBox(String mainType, String defaultValue) {
		KgoGroupLevelRepository kgoGroupLevelRepository = SpringUtil.getDao(KgoGroupLevelRepository.class);
		return getComboBox(kgoGroupLevelRepository.findAllByMainType(mainType), GROUP_LEVEL_ORGAN_COMBOBOX_LABEL_FIELD,
				GROUP_LEVEL_ORGAN_COMBOBOX_VALUE_FIELD, defaultValue, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 服務案件清單-初始畫面-機關分類下拉選單
	 * 
	 * @param organId
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getCasesetOrganByLoginUserOrgan(String organId, String defaultValue) {
		KgoCasesetRepository kgoCasesetRespository = SpringUtil.getDao(KgoCasesetRepository.class);
		return getComboBox(kgoCasesetRespository.findCasesetOrganByOrgan(organId), CASESET_ORGAN_COMBOX_LABEL_FIELD,
				CASESET_ORGAN_COMBOX_VALUE_FIELD, defaultValue, ComboBoxStatusEnum.ALL.getCode(), false);

	}
	
	/**
	 *服務案件清單-案件維護-初始畫面-機關分類下拉選單
	 * 
	 * @param organId
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getOrganByLoginUserOrgan(String organId, String defaultValue) {
		KgoOrganRepository kgoOrganRepository = SpringUtil.getDao(KgoOrganRepository.class);
		return getComboBox(kgoOrganRepository.findOrganByOrgan(organId), CASESET_ORGAN_COMBOX_LABEL_FIELD,
				CASESET_ORGAN_COMBOX_VALUE_FIELD, defaultValue, ComboBoxStatusEnum.ALL.getCode(), false);

	}

}
