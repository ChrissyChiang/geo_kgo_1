package gov.kcg.kgo.viewModel.compoent;

/**
 * 下拉選單 - 項目元件.
 */
public class SelectListExtraInfoItem extends SelectListItem{

	private static final long serialVersionUID = 1369330698127358281L;

	/**
     * 欄位型態
	 */
	private String type;

	/**
     * MyData驗證設定
	 */
	private String isNumOrDate;

	/**
	 * MyData驗證設定
	 */
	private String isHaveValue;

	/**
	 * 欄位型態（來源，後方輸入框）
	 */
	private String restrictedSourceValue;

	/**
     * 欄位長度
	 */
	private String restrictedLength;

	/**
     * 是否必填
	 */
	private String isNotNull;

	public SelectListExtraInfoItem() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsNumOrDate() {
		return isNumOrDate;
	}

	public void setIsNumOrDate(String isNumOrDate) {
		this.isNumOrDate = isNumOrDate;
	}

	public String getIsHaveValue() {
		return isHaveValue;
	}

	public void setIsHaveValue(String isHaveValue) {
		this.isHaveValue = isHaveValue;
	}

	public String getRestrictedSourceValue() {
		return restrictedSourceValue;
	}

	public void setRestrictedSourceValue(String restrictedSourceValue) {
		this.restrictedSourceValue = restrictedSourceValue;
	}

	public String getRestrictedLength() {
		return restrictedLength;
	}

	public void setRestrictedLength(String restrictedLength) {
		this.restrictedLength = restrictedLength;
	}

	public String getIsNotNull() {
		return isNotNull;
	}

	public void setIsNotNull(String isNotNull) {
		this.isNotNull = isNotNull;
	}
}
