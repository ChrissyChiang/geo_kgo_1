package gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean;

import java.util.List;

/**
 * 	市府員工 - 用戶基本資訊 KCG_USER_BASIC_INFO.
 */

/**
	租戶公司代碼		APP_COMPANY_ID
	租戶帳號名稱		APP_USER_LOGIN_ID
	租戶認證位址		AUTH_FROM_ADDRESS
	租戶認證時間		AUTH_DATE
	租戶認證方式		AUTH_METHOD
		租戶認證額外資料		為 JSON 元件，請參考如下
		內容格式是根據 租戶認證方式(AUTH_METHOD) 
		內容可以是空值
		租戶身份證字號		APP_USER_TW_SSN

	sample:
	{
	"ERROR_CODE": "0",
	"PUBLISH_INFO_LAST_UPDATE_TIME": "2014-04-01 16:03:33",
	"PUBLISH_INFO_LAST_UPDATE_TAG": "8b944f1b9aa368351452f561bb52c5c3",
	"PUBLISH_INFO_CONTENT": {
		"KCG_USER_BASIC_INFO": {
			"APP_COMPANY_ID": "kcg",
			"APP_USER_LOGIN_ID": "jacky@kcg.gov.tw",
			"AUTH_FROM_ADDRESS": "101.12.240.31",
			"AUTH_DATE": "20160316083748+0800",
            "AUTH_TYPE": ["pass_moica_auth"],
			"AUTH_EXTRA_INFO": {
				"MOICA_USER_NAME": "李永正",
				"MOICA_CERT_SN": "E23029C0A56A1206E23029C0A56A1206"
			}
		}
	}
}

 * */
public class KcgUserBasicInfo {
	
	/** 租戶公司代碼 */
	private String appCompanyId;
	
	/** 租戶帳號名稱	 */
	private String appUserLoginId;
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 租戶身份證字號 */
	private String appUserTwSSn;
	
	/** 認證方式. */
	private List<String> authMethod;
	
	// TODO AUTH_EXTRA_INFO 文件沒有提到

	public String getAppCompanyId() {
		return appCompanyId;
	}

	public void setAppCompanyId(String appCompanyId) {
		this.appCompanyId = appCompanyId;
	}

	public String getAppUserLoginId() {
		return appUserLoginId;
	}

	public void setAppUserLoginId(String appUserLoginId) {
		this.appUserLoginId = appUserLoginId;
	}

	public String getAuthFromAddress() {
		return authFromAddress;
	}

	public void setAuthFromAddress(String authFromAddress) {
		this.authFromAddress = authFromAddress;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getAppUserTwSSn() {
		return appUserTwSSn;
	}

	public void setAppUserTwSSn(String appUserTwSSn) {
		this.appUserTwSSn = appUserTwSSn;
	}

	public List<String> getAuthMethod() {
		return authMethod;
	}

	public void setAuthMethod(List<String> authMethod) {
		this.authMethod = authMethod;
	}
}
