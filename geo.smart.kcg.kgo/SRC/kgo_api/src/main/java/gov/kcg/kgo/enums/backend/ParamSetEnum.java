package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 參數設定 enum.
 * 
 *  TS-案件暫存區保存期限 
 *  FQ_前台案件查詢區間 
 *  TO_單登閒置時間 
 *  SUI-智能客服開關(主動) 
 *  SUP-智能客服開關(被動)
 *  
 * Y-年
 * M-月
 * D-天
 * H-小時
 * N-分鐘
 * B-布林

 */
public enum ParamSetEnum {
	

	/** 案件暫存區保存期限. */
	TS("TS", "kgo.backend.paramset.ts", false, new ParamSetTypeEnum[] {ParamSetTypeEnum.Y, ParamSetTypeEnum.M, ParamSetTypeEnum.D}),
	
	/** 前台案件查詢區間 **/
	FQ("FQ", "kgo.backend.paramset.fq", false, new ParamSetTypeEnum[] {ParamSetTypeEnum.Y, ParamSetTypeEnum.M, ParamSetTypeEnum.D}),
	
	/** 單登閒置時間 **/
	TO("TO", "kgo.backend.paramset.to", false, new ParamSetTypeEnum[] {ParamSetTypeEnum.N}),
	
	/** 智能客服開關(主動) **/
//	SUI("SUI", "kgo.backend.paramset.sui", true),
	
	/** 智能客服開關 **/
	SUP("SUP", "kgo.backend.paramset.sup", true, new ParamSetTypeEnum[] {ParamSetTypeEnum.ON, ParamSetTypeEnum.OFF}),
	
	/** A流程檢核驗證. */
	AC("AC", "kgo.backend.paramset.ac", true, new ParamSetTypeEnum[] {ParamSetTypeEnum.VERIFY, ParamSetTypeEnum.NO_VERIFY}),

	/** 站外連結保存期限. */
	AQ("AQ", "kgo.backend.paramset.aq", false, new ParamSetTypeEnum[] {ParamSetTypeEnum.Y, ParamSetTypeEnum.M, ParamSetTypeEnum.D}),

	/** 表單附件檔案類型. 資料源從KGO_CODE來 @kgoCode*/
	FT("FT", "kgo.backend.paramset.ft", false, new ParamSetTypeEnum[] {});
	
	/** 參數設定 **/
	private String type;

	/** 參數設定名稱 **/
	private String typeNameI18n;
	
	/** 參數設定類型. */
	private ParamSetTypeEnum[] psType;
	
	/** 是否為布林參數 enum. */
	private Boolean isBolean;

	private ParamSetEnum(String type, String typeNameI18n, Boolean isBolean, ParamSetTypeEnum[] psType) {
		this.type = type;
		this.typeNameI18n = typeNameI18n;
		this.isBolean = isBolean;
		this.psType = psType;
	}

	/**
	 * 取得對應參數設定.
	 *
	 */
	public static ParamSetEnum getParamSetEnum(String value) {
		for (ParamSetEnum aEnum : values()) {
			if (StringUtils.equals(aEnum.getType(), value)) {
				return aEnum;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public String getTypeNameI18n() {
		return typeNameI18n;
	}

	public Boolean getIsBolean() {
		return isBolean;
	}

	public ParamSetTypeEnum[] getPsType() {
		return psType;
	}
}
