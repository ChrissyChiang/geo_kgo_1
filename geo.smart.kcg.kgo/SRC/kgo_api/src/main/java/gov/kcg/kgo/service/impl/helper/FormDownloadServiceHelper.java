package gov.kcg.kgo.service.impl.helper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.enums.backend.CodeTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.model.KgoCode;
import gov.kcg.kgo.repository.KgoCodeRepository;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

public class FormDownloadServiceHelper extends KgoServiceHelper {

	public static final String CODE_TYPE_COMBOBOX_LABEL_FIELD = "codeName";
	public static final String CODE_TYPE_COMBOBOX_VALUE_FIELD = "codeId";

	/**
	 * Instantiates a new calss management service helper.
	 */
	public FormDownloadServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final FormDownloadServiceHelper instance = new FormDownloadServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static FormDownloadServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 根據 CodeType 值從 KGO_CODE 中產生相對應的下拉式選單
	 * 
	 * @param list
	 * @return
	 */
	public ComboBox getFormTypeComboBox() {
		KgoCodeRepository kgoCodeRepository = SpringUtil.getDao(KgoCodeRepository.class);
		List<KgoCode> kgoCodeList = kgoCodeRepository.findByIdCodeType(CodeTypeEnum.DOC_TYPE.getValue());
		return getComboBox(kgoCodeList, CODE_TYPE_COMBOBOX_LABEL_FIELD, CODE_TYPE_COMBOBOX_VALUE_FIELD,
				StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
	}

}
