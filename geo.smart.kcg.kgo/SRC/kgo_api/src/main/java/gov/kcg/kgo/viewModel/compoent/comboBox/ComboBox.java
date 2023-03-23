package gov.kcg.kgo.viewModel.compoent.comboBox;

import gov.kcg.kgo.viewModel.compoent.ElementProperty;
import gov.kcg.kgo.viewModel.compoent.SelectListExtraInfoItem;
import gov.kcg.kgo.viewModel.compoent.SelectListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉選單.
 */
public class ComboBox extends ElementProperty {

	private static final long serialVersionUID = 1L;

	/** The options. */
	private List<SelectListItem> options = new ArrayList<SelectListItem>();

	/** The Constant DEFAULT_LABEL. */
	private static final String DEFAULT_LABEL = "-----請選擇-----";

	/** The Constant DEFAULT_VALUE. */
	private static final String DEFAULT_VALUE = "none";

	public ComboBox() {
	}

	/**
	 * 已棄用, 前端會自行組請選擇的項目, 後端不需要再加入這項目
	 * 新增預設的ComboItem.
	 */
	@Deprecated
	public void addDefaultComboItem() {

//		this.add(DEFAULT_LABEL, DEFAULT_VALUE);
	}

	/**
	 * Adds the.
	 *
	 * @param sLabel the s label.
	 * @param sValue the s value.
	 */
	public void add(String sLabel, String sValue) {
		SelectListItem item = new SelectListItem(sLabel, sValue);
		add(item);
	}

	/**
	 * Adds the.
	 *
	 * @param sLabel   the s label.
	 * @param sValue   the s value.
	 * @param groupKey the s groupKey.
	 */
	public void add(String sLabel, String sValue, String groupKey) {
		SelectListItem item = new SelectListItem(sLabel, sValue, groupKey);
		add(item);
	}

	/**
	 *
	 * @param sLabel
	 * @param sValue
	 * @param selected
	 * @param type
	 * @param isNumOrDate
	 * @param isHaveValue
	 * @param restrictedSourceValue
	 * @param restrictedLength
	 * @param isNotNull
	 */
	public void add(String sLabel, String sValue, Boolean selected,
					String type, String isNumOrDate,
					String isHaveValue, String restrictedSourceValue,
					String restrictedLength, String isNotNull) {
		SelectListExtraInfoItem item = new SelectListExtraInfoItem();
		item.setLabel(sLabel);
		item.setValue(sValue);
		item.setSelected(selected);
		item.setType(type);
		item.setIsNumOrDate(isNumOrDate);
		item.setIsHaveValue(isHaveValue);
		item.setRestrictedSourceValue(restrictedSourceValue);
		item.setRestrictedLength(restrictedLength);
		item.setIsNotNull(isNotNull);
		add(item);
		// 設置下拉選單選中值
		if (selected) {
			super.selectedVal = sValue;
		}
	}

	/**
	 *
	 * @param sLabel
	 * @param sValue
	 * @param groupKey
	 * @param selected
	 * @param type
	 * @param isNumOrDate
	 * @param isHaveValue
	 * @param restrictedSourceValue
	 * @param restrictedLength
	 * @param isNotNull
	 */
	public void add(String sLabel, String sValue, String groupKey, Boolean selected,
					String type, String isNumOrDate,
					String isHaveValue, String restrictedSourceValue,
					String restrictedLength, String isNotNull) {
		SelectListExtraInfoItem item = new SelectListExtraInfoItem();
		item.setLabel(sLabel);
		item.setValue(sValue);
		item.setGroupKey(groupKey);
		item.setSelected(selected);
		item.setType(type);
		item.setIsNumOrDate(isNumOrDate);
		item.setIsHaveValue(isHaveValue);
		item.setRestrictedSourceValue(restrictedSourceValue);
		item.setRestrictedLength(restrictedLength);
		item.setIsNotNull(isNotNull);
		add(item);
		// 設置下拉選單選中值
		if (selected) {
			super.selectedVal = sValue;
		}
	}

	/**
	 * Adds the.
	 *
	 * @param sLabel   the s label
	 * @param sValue   the s value
	 * @param selected the selected
	 */
	public void add(String sLabel, String sValue, Boolean selected) {
		SelectListItem item = new SelectListItem(sLabel, sValue, selected);
		add(item);
		// 設置下拉選單選中值
		if (selected) {
			super.selectedVal = sValue;
		}
	}

	public void add(String sLabel, String sValue, String groupKey, Boolean selected) {
		SelectListItem item = new SelectListItem(sLabel, sValue, groupKey, selected);
		add(item);
		// 設置下拉選單選中值
		if (selected) {
			super.selectedVal = sValue;
		}
	}

	/**
	 * Adds the.
	 * 
	 * @param item the item.
	 */
	public void add(SelectListItem item) {
		options.add(item);
	}

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public List<SelectListItem> getOptions() {
		return options;
	}

	/**
	 * Sets the options.
	 *
	 * @param options the new options
	 */
	public void setOptions(List<SelectListItem> options) {
		this.options = options;
	}

	public String getSelectedVal() {
		return selectedVal;
	}

	public void setSelectedVal(String selectedVal) {
		this.selectedVal = selectedVal;
	}

	@Override
	public String toString() {
		return "ComboBox{" +
				"selectedVal='" + selectedVal + '\'' +
				", options=" + options +
				'}';
	}
}
