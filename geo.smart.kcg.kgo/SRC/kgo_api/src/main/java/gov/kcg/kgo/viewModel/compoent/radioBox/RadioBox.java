package gov.kcg.kgo.viewModel.compoent.radioBox;

import java.util.ArrayList;
import java.util.List;

import gov.kcg.kgo.viewModel.compoent.ElementProperty;
import gov.kcg.kgo.viewModel.compoent.radioBox.bean.RadioBoxItem;

/**
 * RadioBox.
 */
public class RadioBox extends ElementProperty {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The options. */
	private List<RadioBoxItem> options = new ArrayList<>();

	/**
	 * RadioBox 建構子
	 */
	public RadioBox() {

	}

	/**
	 * Adds the.
	 *
	 * @param sLabel the s label.
	 * @param sValue the s value.
	 */
	public void add(String sLabel, String sValue) {
		RadioBoxItem item = new RadioBoxItem(sLabel, sValue);
		add(item);
	}

	/**
	 * Adds the.
	 *
	 * @param sLabel   the s label
	 * @param sValue   the s value
	 * @param selected the selected
	 */
	public void add(String sLabel, String sValue, Boolean selected) {
		RadioBoxItem item = new RadioBoxItem(sLabel, sValue, selected);
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
	 * @param groupKey the groupKey
	 * @param selected the selected
	 */
	public void add(String sLabel, String sValue, String groupKey, Boolean selected) {
		RadioBoxItem item = new RadioBoxItem(sLabel, sValue, groupKey, selected);
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
	public void add(RadioBoxItem item) {
		options.add(item);
	}

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public List<RadioBoxItem> getOptions() {
		return options;
	}

	/**
	 * Sets the options.
	 *
	 * @param options the new options
	 */
	public void setOptions(List<RadioBoxItem> options) {
		this.options = options;
	}

}
