package gov.kcg.kgo.service.impl.helper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.compoent.radioBox.RadioBox;

public class ServiceApplyServiceHelper extends KgoServiceHelper {

	private static final String CASE_TYPE_COMBOBOX_LABEL_FIELD = "label";
	private static final String CASE_TYPE_COMBOBOX_VALUE_FIELD = "value";

	/**
	 * Instantiates a new calss management service helper.
	 */
	public ServiceApplyServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final ServiceApplyServiceHelper instance = new ServiceApplyServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static ServiceApplyServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 根據 KgoCasesetColumn 所產生的下拉式選單
	 * 
	 * @param list
	 * @return
	 */
	public ComboBox getComboBox(List<?> list) {
		return getComboBox(list, Constants.ENUM_COMBOBOX_LABEL_FIELD, Constants.ENUM_COMBOBOX_VALUE_FIELD,
				StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 根據 KgoCasesetColumn 所產生的RadioBox
	 * 
	 * @param list
	 * @return
	 */
	public RadioBox getRadioBox(List<?> list) {
		return getRadioBox(list, Constants.ENUM_COMBOBOX_LABEL_FIELD, Constants.ENUM_COMBOBOX_VALUE_FIELD);
	}

	/**
	 * 
	 * @return
	 */
	public ComboBox getCustomCaseTypeComboBox() {
		return getComboBox(Arrays.asList(new CaseTypeEnum[] { CaseTypeEnum.SCA, CaseTypeEnum.URA }),
				CASE_TYPE_COMBOBOX_LABEL_FIELD, CASE_TYPE_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY,
				ComboBoxStatusEnum.ALL.getCode(), false);
	}
}
