package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 案件作業流程集合 服務申辦流程(SA) -> KGO_CASE_MAIN 服務案件新增(URA) → KGO_URA_APPLY 系統權限申請(SCA)
 * → KGO_SCA_APPLY
 *
 * (GEO 20210814 add note : 對應資料庫 --> KGO_CASESET 的 CaseType)
 * 
 * @author TPIuser
 *
 */
public enum CaseTypeEnum {

	/** 服務申辦流程 */
	SA("SA", "服務申辦流程", "A"),
	/** 服務案件新增流程 */
	@Deprecated
	SCA("SCA", "服務案件新增流程", "C"),
	/** 系統權限申請流程 */
	@Deprecated
	URA("URA", "系統權限申請流程", "U"),
	
	/** 動態流程 */
	D("D", "動態流程", "D");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	/** 簡稱 */
	private String prefix;

	private CaseTypeEnum(String value, String label, String prefix) {
		this.value = value;
		this.label = label;
		this.prefix = prefix;
	}

	public static CaseTypeEnum getEnum(String value) {
		for (CaseTypeEnum cEnum : values()) {
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

	public String getPrefix() {
		return prefix;
	}

}
