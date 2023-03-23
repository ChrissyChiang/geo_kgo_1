package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 人事資料的取得管道 enums
 * 
 * GET_IKPD_FIRST → 先抓IKPD，如抓不到，再抓TEMP
 * GET_IKPD_AND_TEMP → IKPD和TEMP都抓，都要有抓到人事資料
 * GET_IKPD_ONLY → 只抓取IKPD
 * GET_TEMP_ONLY → 只抓取TEMP
 */
public enum Option {

	GET_IKPD_FIRST("GET_IKPD_FIRST", "先抓IKPD，如抓不到，再抓TEMP"),
	GET_IKPD_AND_TEMP("GET_IKPD_AND_TEMP", "IKPD和TEMP都抓，都要有抓到人事資料"),
	GET_IKPD_ONLY("GET_IKPD_ONLY", "只抓取IKPD"),
	GET_TEMP_ONLY("GET_TEMP_ONLY", "只抓取TEMP"), 
	UNKNOW("UNKNOW", "未知");

	private String code;
	private String message;

	private Option(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static Option getOption(String code) {
		for (Option option : values()) {
			if (StringUtils.equals(option.getCode(), code)) {
				return option;
			}
		}
		return UNKNOW;
	}
	
	public boolean isEqual(Option option) {
		return StringUtils.equalsIgnoreCase(option.getCode(), this.code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
