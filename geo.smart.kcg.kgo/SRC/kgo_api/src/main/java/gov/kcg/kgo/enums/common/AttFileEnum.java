package gov.kcg.kgo.enums.common;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;

public enum AttFileEnum {
	TaskM("T", BackendFunctionCodeEnum.TaskM), AnnounceM("A", BackendFunctionCodeEnum.AnnounceM);

	/** 代碼 */
	private String value;

	/** */
	private BackendFunctionCodeEnum functionCode;

	private AttFileEnum(String value, BackendFunctionCodeEnum functionCode) {
		this.value = value;
		this.functionCode = functionCode;
	}

	public static AttFileEnum getEnum(String value) {
		for (AttFileEnum cEnum : values()) {
			if (StringUtils.equalsIgnoreCase(cEnum.getValue(), value)) {
				return cEnum;
			}
		}
		return null;
	}

	public static AttFileEnum getEnum(BackendFunctionCodeEnum functionCode) {
		for (AttFileEnum cEnum : values()) {
			if (cEnum.functionCode == functionCode) {
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

	public BackendFunctionCodeEnum getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(BackendFunctionCodeEnum functionCode) {
		this.functionCode = functionCode;
	}

}
