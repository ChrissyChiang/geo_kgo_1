package gov.kcg.kgo.dto.kgoEmp;

import java.io.Serializable;


/**
 * 市府機關單位資料DTO
 *
 */
public class KgoEmpDto implements Serializable{
	/**
	 * {"B01IDNO":"E122520953"}
	 * Sample: 
	[
		{
		"B01NAME": "朱科安",
		"B02SORCOD": "397220100A",
		"B02UNICOD": "A610",
		"B02UNIT": "網路服務科",
		"B02TITCOD": "1860",
		"B02TITLE": "高級分析師",
		"B02POLDAT": "",
		"B02EMAIL": "chance@kcg.gov.tw"
		}
	]
	 {"B01IDNO":"S122239341"}
	 [
		 {
		 "B01NAME": "余尚桓",
		 "B02SORCOD": "397220100A",
		 "B02UNICOD": "A600",
		 "B02UNIT": "系統發展科",
		 "B02TITCOD": "1861",
		 "B02TITLE": "分析師",
		 "B02POLDAT": "",
		 "B02EMAIL": "shyu96@kcg.gov.tw"
		 }
	 ]
	 * */
	
	/** 姓名 */
	private String B01NAME;
	
	/** 機關代碼 */
	private String B02SORCOD;
	
	/** 單位代碼 */
	private String B02UNICOD;
	
	/** 單位名稱 */
	private String B02UNIT;
	
	/** 職稱名稱 */
	private String B02TITLE;
	
	/** 離職日期 */
	private String B02POLDAT;
	
	/** 墊子郵件信箱 */
	private String B02EMAIL;

	public String getB01NAME() {
		return B01NAME;
	}

	public void setB01NAME(String b01name) {
		B01NAME = b01name;
	}

	public String getB02SORCOD() {
		return B02SORCOD;
	}

	public void setB02SORCOD(String b02sorcod) {
		B02SORCOD = b02sorcod;
	}

	public String getB02UNICOD() {
		return B02UNICOD;
	}

	public void setB02UNICOD(String b02unicod) {
		B02UNICOD = b02unicod;
	}

	public String getB02UNIT() {
		return B02UNIT;
	}

	public void setB02UNIT(String b02unit) {
		B02UNIT = b02unit;
	}

	public String getB02TITLE() {
		return B02TITLE;
	}

	public void setB02TITLE(String b02title) {
		B02TITLE = b02title;
	}

	public String getB02POLDAT() {
		return B02POLDAT;
	}

	public void setB02POLDAT(String b02poldat) {
		B02POLDAT = b02poldat;
	}

	public String getB02EMAIL() {
		return B02EMAIL;
	}

	public void setB02EMAIL(String b02email) {
		B02EMAIL = b02email;
	}
}
