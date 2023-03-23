package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 【書表維護分類】DocType 對應到 KGO_CASESET_FORM.type 
 *  代碼至實際類別名稱
 *  D書證表單 T填寫範例 O其他說明
 * 
 * @author TPIuser
 *
 */
public enum FormType {
	/** 【書表維護分類】DocType **/

	// 書證表單
	D("D"),

	// 填寫範例
	T("T"),

	// 其他說明
	O("O"),
	
	// 未知
	UNKNOW("UNKNOW");

	/** 書表維護分類 **/
	private String formType;

	/**
	 * 取得對應書表維護分類.
	 */
	public static FormType getFormType(String formType) {
		for (FormType type : values()) {
			if (StringUtils.equals(type.getFormType(), formType)) {
				return type;
			}
		}
		return UNKNOW;
	}

	private FormType(String formType) {
		this.formType = formType;
	}

	public String getFormType() {
		return formType;
	}
}
