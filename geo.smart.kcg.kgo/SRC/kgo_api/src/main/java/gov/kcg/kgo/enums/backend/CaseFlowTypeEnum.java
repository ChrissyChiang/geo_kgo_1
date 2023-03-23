package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 作業流程分類
 * (GEO 20210814 add note : 選單/系統設定/服務管理/整合流程分類，對應資料庫 --> KGO_CASESET 的 CaseFlowType )
 */
public enum CaseFlowTypeEnum {

	A("A", "站外連結"), 
	B1("B1", "案件暫存區(入案通知)"), 
	B2("B2", "案件暫存區(自行取案)"), 
	B3("B3", "由本平台完整申辦");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	public static CaseFlowTypeEnum getCaseFlowTypeEnum(String value) {
		for (CaseFlowTypeEnum aEnum : values()) {
			if (StringUtils.equalsIgnoreCase(aEnum.getValue(), value)) {
				return aEnum;
			}
		}
		return null;
	}

	private CaseFlowTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
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
