package gov.kcg.kgo.geoenum;

import org.apache.commons.lang3.StringUtils;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 * 民政局兵役類案件 Enum
 */
public enum GeoCivilAffarsiServiceEnum {

	MILITARY_SERVICE_01("d6a6fc78-ed57-4c60-aeb0-b2e7928a7a0e", "替代備役退役證明書補（換）發"),
	MILITARY_SERVICE_02("f2ce0fab-91e4-4af4-bd23-6105613c9571", "義務役服兵役證明書(役男在營證明書)"),
	MILITARY_SERVICE_03("cbd83a70-e1d3-4261-86ab-ccbb84141d55", "免役證明書遺失補發"),
	SOCIAL_AFFAIRS_01("328dbabd-1c06-48c9-8cfa-382232536f7f", "低收入戶生活補助證明書"),
	SOCIAL_AFFAIRS_02("b9c84b0a-9f89-4bd5-b093-9d88b0fea5f9", "中低收入戶生活補助證明書");


	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	private GeoCivilAffarsiServiceEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得案件設定欄位型態.
	 *
	 */
	public static GeoCivilAffarsiServiceEnum getColumnTypeEnum(String value) {
		for (GeoCivilAffarsiServiceEnum cEnum : values()) {
			if (StringUtils.equalsIgnoreCase(cEnum.getValue(), value)) {
				return cEnum;
			}
		}
		return null;
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
