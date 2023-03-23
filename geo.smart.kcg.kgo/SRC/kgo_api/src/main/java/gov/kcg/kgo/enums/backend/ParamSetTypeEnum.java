package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 參數設定類型 enum.
 *  
 * Y-年
 * M-月
 * D-天
 * H-小時
 * N-分鐘
 * B-布林

 */
public enum ParamSetTypeEnum {

	/** Y-年 **/
	Y("Y","kgo.backend.paramset.type.y"),
	/** M-月 **/
	M("M","kgo.backend.paramset.type.m"),
	/** D-天 **/
	D("D","kgo.backend.paramset.type.d"),
	/** H-小時 **/
	H("H","kgo.backend.paramset.type.h"),
	/** N-分鐘 **/
	N("N","kgo.backend.paramset.type.n"),
	/** B-布林*/
	B("B","kgo.backend.paramset.type.b"),
	
	/** 布林顯示 */
	/** ON 主動 **/
	ON("1","kgo.backend.paramset.sup.on"),
	/** OFF 被動*/
	OFF("0","kgo.backend.paramset.sup.off"),
	
	/** 驗證 **/
	VERIFY("1","kgo.backend.paramset.verify"),
	/** 不驗證*/
	NO_VERIFY("0","kgo.backend.paramset.noverify");
	
	

	/** 參數設定類型代碼 **/
	private String code;
	
	/** 參數設定類型名稱i18n **/
	private String codeI18n;

	private ParamSetTypeEnum(String code, String codeI18n) {
		this.code = code;
		this.codeI18n = codeI18n;
	}

	/**
	 * 取得對應狀態類別.
	 *
	 */
	public static ParamSetTypeEnum getKgoRoleEnum(String value) {
		for (ParamSetTypeEnum aEnum : values()) {
			if (StringUtils.equals(aEnum.getCode(), value)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getCodeI18n() {
		return codeI18n;
	}
}
