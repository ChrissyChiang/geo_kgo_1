package gov.kcg.kgo.service.impl.helper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.model.KgoCounty;
import gov.kcg.kgo.model.KgoZip;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.compoent.radioBox.RadioBox;

public class BackEndCommonServiceHelper extends KgoServiceHelper {

	private static final String COUNTY_COMBOBOX_LABEL_FIELD_STR = "countyName";
	private static final String COUNTY_COMBOBOX_VALUE_FIELD_STR = "countyId";

	private static final String ZIP_CHECKBOX_LABEL_FIELD_STR = "ZIPName";
	private static final String ZIP_CHECKBOX_VALUE_FIELD_STR = "zip";

	/**
	 * Instantiates a new calss management service helper.
	 */
	public BackEndCommonServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final BackEndCommonServiceHelper instance = new BackEndCommonServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static BackEndCommonServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 
	 * @param list
	 * @param countyId
	 * @return
	 */
	public ComboBox getCountyComboBox(List<KgoCounty> list, String countyId) {
		return getComboBox(list, COUNTY_COMBOBOX_LABEL_FIELD_STR, COUNTY_COMBOBOX_VALUE_FIELD_STR, countyId,
				ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 
	 * @param kgoZipList
	 * @return
	 */
	public List<CheckBox> getZipCheckBoxList(List<KgoZip> kgoZipList) {
		return getCheckBox(kgoZipList, ZIP_CHECKBOX_LABEL_FIELD_STR, ZIP_CHECKBOX_VALUE_FIELD_STR);
	}

}
