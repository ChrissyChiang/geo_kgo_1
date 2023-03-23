package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * MyData驗證設定(KGO_CASESET_COLUMN.MyDataCheckType)集合
 * 
 * @author TPIuser
 *
 */
public enum MyDataCheckTypeEnum {

	/** 等於 */
	E("E", "等於", "kgo.front.end.caseset.column.mydata.check.equal"),
	/** 大於 */
	M("M", "大於", "kgo.front.end.caseset.column.mydata.check.more"),
	/** 大於 */
	L("L", "小於", "kgo.front.end.caseset.column.mydata.check.less");

	/** 代碼 */
	private String value;

	/** 顯示值 */
	private String label;

	/** 顯示訊息 */
	private String msg;

	private MyDataCheckTypeEnum(String value, String label, String msg) {
		this.value = value;
		this.label = label;
		this.msg = msg;
	}

	public static MyDataCheckTypeEnum getEnum(String value) {
		for (MyDataCheckTypeEnum cEnum : values()) {
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
