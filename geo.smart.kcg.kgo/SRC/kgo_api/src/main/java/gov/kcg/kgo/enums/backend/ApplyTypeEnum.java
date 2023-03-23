package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 啟用服務
 * 
 * E-線上,C-臨櫃,L-書表
 *
 */
public enum ApplyTypeEnum {

	/** 線上 */
	E("E", "線上"),
	/** 臨櫃 */
	C("C", "臨櫃"),
	/** 書表 */
	L("L", "書表");
	/** 外部服務案件 "X" (額外處理不新增類別)*/
	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	/**
	 * 取得申請類型 E-線上,C-臨櫃,L-書表.
	 */
	public static ApplyTypeEnum getApplyTypeEnum(String formType) {
		for (ApplyTypeEnum type : values()) {
			if (StringUtils.equals(type.getValue(), formType)) {
				return type;
			}
		}
		return null;
	}

	private ApplyTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
