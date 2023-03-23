package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 系統類別(前/後台)
 * 
 * @author TPIuser
 *
 */
public enum SystemTypeEnum {

	/**
	 * 後台
	 */
	B("B"),
	/**
	 * 前台
	 */
	F("F");

	/** 系統類別 */
	private String systemType;


	private SystemTypeEnum(String systemType) {
		this.systemType = systemType;
	}

	/**
	 * 取得對應系統類別(前/後台)
	 *
	 */
	public static SystemTypeEnum getSystemTypeEnum(String systemType) {
		for (SystemTypeEnum e : values()) {
			if (StringUtils.equals(e.getSystemType(), systemType)) {
				return e;
			}
		}
		return null;
	}

	public String getSystemType() {
		return systemType;
	}

}
