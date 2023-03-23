package gov.kcg.kgo.viewModel.compoent;

/**
 * 下拉選單 - 項目元件.
 */
public class SelectListItem {

	private static final long serialVersionUID = 1369330698127358281L;

	/** The label. */
	private String label = "";

	/** The value. */
	private String value = "";

	/** The group key. */
	private String groupKey = "";

	/** The selected. */
	private Boolean selected = false;

	public SelectListItem() {
	}

	/**
	 * Instantiates a new combo item.
	 *
	 * @param sLabel the s label
	 * @param sValue the s value
	 */
	public SelectListItem(String sLabel, String sValue) {
		this.label = sLabel;
		this.value = sValue;
	}

	public SelectListItem(String sLabel, String sValue, String groupKey) {
		this.label = sLabel;
		this.value = sValue;
		this.groupKey = groupKey;
	}

	/**
	 * Instantiates a new option item.
	 *
	 * @param sLabel   the s label
	 * @param sValue   the s value
	 * @param selected the selected
	 */
	public SelectListItem(String sLabel, String sValue, Boolean selected) {
		this.label = sLabel;
		this.value = sValue;
		this.selected = selected;
	}

	public SelectListItem(String label, String value, String groupKey, Boolean selected) {
		super();
		this.label = label;
		this.value = value;
		this.groupKey = groupKey;
		this.selected = selected;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
