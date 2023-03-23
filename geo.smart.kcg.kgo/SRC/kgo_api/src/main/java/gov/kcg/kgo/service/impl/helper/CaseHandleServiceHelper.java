package gov.kcg.kgo.service.impl.helper;

import java.util.List;
import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;

public class CaseHandleServiceHelper extends KgoServiceHelper {

	/**
	 * Instantiates a new calss management service helper.
	 */
	public CaseHandleServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CaseHandleServiceHelper instance = new CaseHandleServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static CaseHandleServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 根據 ENUM 資料來產生相對應的 CheckBox List
	 * 
	 * @param list
	 * @return
	 */
	public List<CheckBox> getCheckBoxListWithEnum(List<?> list) {
		return getCheckBox(list, Constants.ENUM_COMBOBOX_LABEL_FIELD, Constants.ENUM_COMBOBOX_VALUE_FIELD);
	}

}
