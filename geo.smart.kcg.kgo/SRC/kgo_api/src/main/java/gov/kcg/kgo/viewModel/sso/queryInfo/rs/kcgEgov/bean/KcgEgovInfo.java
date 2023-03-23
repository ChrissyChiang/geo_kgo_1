package gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean;

/**
 * 	我的 e 政府 KCG_EGOV_SSO_INFO rs.
/**
	用戶認證位址		AUTH_FROM_ADDRESS	
	用戶認證時間		AUTH_DATE	自我的 e 政府成功取回資料
	用戶認證方式		EGOV_AUTH_METHOD	欄位內容：帳號密碼 (IDPassword)自然人憑證這個有可能會取得不到
	用戶帳號			EGOV_USER_ACCOUNT	未申請則是空白20190910 目前未申請
	用戶中文姓名		EGOV_USER_CN	未申請則是空白
	用戶身份證字號	EGOV_USER_UID	未申請則是空白
	用戶憑證序號		EGOV_USER_CERKEY_STR	未申請則是空白
	用戶電子郵件		EGOV_USER_MAIL	未申請則是空白20190910 目前未申請
	用戶人員分類：民眾/公務員		EGOV_USER_TYPE	不知道

	sample:
	"AUTH_FROM_ADDRESS":"101.12.240.31",
	"AUTH_DATE":"20160316083748+0800",
	"EGOV_AUTH_TYPE":"自然人憑證",
	"EGOV_USER_ACCOUNT":"yishinglee",
	"EGOV_USER_CN":"李永正",
	"EGOV_USER_UID":"A123456789",
	"EGOV_USER_CERKEY_STR":"",
	"EGOV_USER_MAIL":"dominic.yishinglee@gmail.com",
	"EGOV_USER_TYPE":"民眾"

 * */
public class KcgEgovInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 用戶認證方式 */
	private String egovAuthType;
	
	/** 用戶帳號 */
	private String egovUserAccount;
	
	/** 用戶中文姓名 */
	private String egovUserCn;
	
	/** 用戶身份證字號 */
	private String egovUserUid;
	
	/** 用戶憑證序號 */
	private String egovUserCerkeyStr;
	
	/** 用戶電子郵件 */
	private String egovUserMail;
	
	/** 用戶人員分類：民眾/公務員 */
	private String egovUserType;
	
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

	public String getEgovAuthType() {
		return egovAuthType;
	}

	public void setEgovAuthType(String egovAuthType) {
		this.egovAuthType = egovAuthType;
	}

	public String getEgovUserAccount() {
		return egovUserAccount;
	}

	public void setEgovUserAccount(String egovUserAccount) {
		this.egovUserAccount = egovUserAccount;
	}

	public String getEgovUserCn() {
		return egovUserCn;
	}

	public void setEgovUserCn(String egovUserCn) {
		this.egovUserCn = egovUserCn;
	}

	public String getEgovUserUid() {
		return egovUserUid;
	}

	public void setEgovUserUid(String egovUserUid) {
		this.egovUserUid = egovUserUid;
	}

	public String getEgovUserCerkeyStr() {
		return egovUserCerkeyStr;
	}

	public void setEgovUserCerkeyStr(String egovUserCerkeyStr) {
		this.egovUserCerkeyStr = egovUserCerkeyStr;
	}

	public String getEgovUserMail() {
		return egovUserMail;
	}

	public void setEgovUserMail(String egovUserMail) {
		this.egovUserMail = egovUserMail;
	}

	public String getEgovUserType() {
		return egovUserType;
	}

	public void setEgovUserType(String egovUserType) {
		this.egovUserType = egovUserType;
	}
}
