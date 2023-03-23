package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * *後台角色(KGO_ROLE)
 * 
 * 1. 系統管理者-ADMIN 2. 機關管理者-UNIT_M 3. 機關案件管理者_CASE_M 4. (預設)機關承辦員_UNIT_U
 *
 * 
 */
public enum KgoRoleEnum {

	/** 系統管理者 **/
	ADMIN("ADMIN", "系統管理者"),
	/** 機關管理者 **/
	UNIT_M("UNIT_M", "機關管理者"),
	/** 機關案件管理者 **/
	CASE_M("CASE_M", "機關服務管理者"),
	/** 機關分文 **/
	UNIT_A("UNIT_A", "機關分文"),
	/** 機關承辦員 (預設) **/
	UNIT_U("UNIT_U", "機關承辦員"),
	
	DEFAULT("DEFAULT", "預設角色");

	/** 角色代碼 **/
	private String value;

	/** 角色名稱 **/
	private String label;

	private KgoRoleEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static KgoRoleEnum getKgoRoleEnum(String value) {
		for (KgoRoleEnum aEnum : values()) {
			if (StringUtils.equalsIgnoreCase(aEnum.getValue(), value)) {
				return aEnum;
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
