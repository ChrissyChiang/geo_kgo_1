package gov.kcg.kgo.geoenum;

import org.apache.commons.lang3.StringUtils;

/**
 * 網路申請，服務類型
 *
 */
public enum CaseSetCategoryEnum {

	COMMON("common", "一般服務"),
	SITE("site", "場地租借"),
	ACTIVITY("activity", "活動預約"),
	REFUND("refund","退費服務"),
	ORGAN("organ","跨機關類別");
	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;


	public static CaseSetCategoryEnum getApplyTypeEnum(String formType) {
		for (CaseSetCategoryEnum type : values()) {
			if (StringUtils.equals(type.getValue(), formType)) {
				return type;
			}
		}
		return null;
	}

	private CaseSetCategoryEnum(String value, String label) {
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
