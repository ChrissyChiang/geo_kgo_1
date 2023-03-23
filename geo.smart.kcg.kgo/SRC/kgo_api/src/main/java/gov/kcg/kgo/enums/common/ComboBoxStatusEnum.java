package gov.kcg.kgo.enums.common;

public enum ComboBoxStatusEnum {

	/** 全部 */
	ALL(0),
	/** 單一 */
	ONE(1),
	/** 預設 */
	INIT(2);

	/** 狀態代碼 */
	private Integer code;

	private ComboBoxStatusEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
