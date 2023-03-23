package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

/**
 * JWT 元素 enum
 * 
 * header, body, signature, all
 * 
 */
public enum JwtParseType {

	HEADER("header"), BODY("body"), SIGNATURE("signature"), ALL("all"), UNKNOW("UNKNOW");

	private String parseType;

	private JwtParseType(String parseType) {
		this.parseType = parseType;
	}

	public static JwtParseType getParseType(String type) {
		for (JwtParseType jwtParseType : values()) {
			if (StringUtils.equals(jwtParseType.getParseType(), type)) {
				return jwtParseType;
			}
		}
		return UNKNOW;
	}

	public String getParseType() {
		return parseType;
	}

	public void setParseType(String parseType) {
		this.parseType = parseType;
	}

}
