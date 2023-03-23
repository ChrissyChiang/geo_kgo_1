package gov.kcg.kgo.service.impl.helper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.model.KgoGroupLevel;
import gov.kcg.kgo.repository.KgoGroupLevelRepository;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

public class ClassManagementServiceHelper extends KgoServiceHelper {

	private static final String SUBTYPE_COMBOBOX_LABEL_FIELD = "name";
	private static final String SUBTYPE_COMBOBOX_VALUE_FIELD = "seq";

	/**
	 * Instantiates a new calss management service helper.
	 */
	public ClassManagementServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final ClassManagementServiceHelper instance = new ClassManagementServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static ClassManagementServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 
	 * @param mainType
	 * @return
	 */
	public ComboBox getSubTypeComboBox(String mainType) {
		KgoGroupLevelRepository kgoGroupLevelRepository = SpringUtil.getDao(KgoGroupLevelRepository.class);
		List<KgoGroupLevel> subTypeList = kgoGroupLevelRepository.findByMainTypeOrderByPublishTimeDesc(mainType);
		return getComboBox(subTypeList, SUBTYPE_COMBOBOX_LABEL_FIELD, SUBTYPE_COMBOBOX_LABEL_FIELD, StringUtils.EMPTY,
				ComboBoxStatusEnum.ALL.getCode(), false);
	}

}
