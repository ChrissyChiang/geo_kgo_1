package gov.kcg.kgo.viewModel.compoent.checkBox;

import gov.kcg.kgo.viewModel.compoent.SelectListItem;

/**
 * SelectList.
 */
public class CheckBox extends SelectListItem {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -323186269499347609L;

	public CheckBox() {}

	/**
	 * Instantiates a new checkbox.
	 *
	 * @param sLabel the s label
	 * @param sValue the s value
	 */
	public CheckBox(String sLabel, String sValue) {
		super(sLabel, sValue);
	}

	/**
	 * Instantiates a new SelectList.
	 *
	 * @param sLabel   the s label
	 * @param sValue   the s value
	 * @param selected the selected
	 */
	public CheckBox(String sLabel, String sValue, Boolean selected) {
		super(sLabel, sValue, selected);
	}

}