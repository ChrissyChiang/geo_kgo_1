package gov.kcg.kgo.enums.backend;

public enum IsPublishEnum {

	/** 下架 **/
	OFF(false, "下架"),
	/** 上架 **/
	ON(true, "上架");

	private String label;
	private boolean value;

	private IsPublishEnum(boolean value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static IsPublishEnum getIsPublishEnum(boolean value) {
		for (IsPublishEnum aEnum : values()) {
			if (aEnum.getValue() == value) {
				return aEnum;
			}
		}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

}
