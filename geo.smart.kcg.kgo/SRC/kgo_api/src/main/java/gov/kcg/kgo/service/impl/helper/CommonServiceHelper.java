package gov.kcg.kgo.service.impl.helper;

import java.util.Arrays;
import java.util.List;

import gov.kcg.kgo.model.KgoCode;
import gov.kcg.kgo.repository.KgoCodeRepository;
import gov.kcg.kgo.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.springframework.util.ObjectUtils;

public class CommonServiceHelper extends KgoServiceHelper {

	private static final String CODE_TYPE_COMBOBOX_LABEL_FIELD = "codeName";
	private static final String CODE_TYPE_COMBOBOX_VALUE_FIELD = "codeId";

	/**
	 * Instantiates a new common service helper.
	 */
	public CommonServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CommonServiceHelper instance = new CommonServiceHelper();
	}

	/**
	 * Gets the single instance of CommonServiceHelper.
	 *
	 * @return single instance of CommonServiceHelper
	 */
	public static CommonServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 取得只有預設選項的 comboBox
	 * 
	 * @return ComboBox
	 */
	public ComboBox getDefaultComboBox() {
		return super.getComboBox(null, null, null, null, ComboBoxStatusEnum.ONE.getCode(), true);
	}

	/**
	 * 取得 資料來源為 List<String> 的 comboBox
	 * 
	 * @param data
	 * @return
	 */
	public ComboBox getComboBoxWithStrList(List<String> data) {
		ComboBox groupBox = new ComboBox();
		data.forEach(s -> {
			groupBox.add(s, s);
		});
		return groupBox;
	}

	/**
	 * 根據 ENUM 內容來產生相對應的 ComboBox
	 * 
	 * @param <T>
	 * @param aEnum
	 * @return
	 */
	public <T extends Enum<T>> ComboBox getComboBoxWithEnum(Class<T> aEnum) {
		return getComboBoxWithEnum(aEnum, StringUtils.EMPTY);
	}

	/**
	 * 根據 ENUM 內容來產生相對應的 ComboBox
	 * 
	 * @param <T>
	 * @param aEnum
	 * @param defaultValue
	 * @return
	 */
	public <T extends Enum<T>> ComboBox getComboBoxWithEnum(Class<T> aEnum, String defaultValue) {
		List<T> enumList = Arrays.asList(aEnum.getEnumConstants());
		return getComboBox(enumList, Constants.ENUM_COMBOBOX_LABEL_FIELD, Constants.ENUM_COMBOBOX_VALUE_FIELD,
				defaultValue, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 根據不同 CodeType 所產生的 ComboBox
	 *
	 * @return ComboBox
	 */
	public ComboBox getCodeTypeComboBox(String codeType) {
		KgoCodeRepository kgoCodeRepository = SpringUtil.getDao(KgoCodeRepository.class);
		List<KgoCode> kgoCodeList = kgoCodeRepository.findByIdCodeType(codeType);
		String defaultValue = (ObjectUtils.isEmpty(kgoCodeList) || kgoCodeList.size() == 0) ? StringUtils.EMPTY
				: kgoCodeList.get(0).getCodeName();
		return getComboBox(kgoCodeList, CODE_TYPE_COMBOBOX_LABEL_FIELD, CODE_TYPE_COMBOBOX_VALUE_FIELD, defaultValue,
				ComboBoxStatusEnum.ALL.getCode(), false);
	}

}
