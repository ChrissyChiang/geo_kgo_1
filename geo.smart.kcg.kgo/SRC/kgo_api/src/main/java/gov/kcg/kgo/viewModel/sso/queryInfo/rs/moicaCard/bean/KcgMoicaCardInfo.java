package gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean;

/**
 * 	自然人憑證 KCG_MOICA_CARD_INFO rs.
 */

/**
	用戶認證位址		AUTH_FROM_ADDRESS
	用戶認證時間		AUTH_DATE
	憑證身份證字號	MOICA_USER_TW_SSN
	憑證用戶名稱		MOICA_USER_NAME
	憑證序號		    MOICA_CERT_SN
	
	sample:
	"AUTH_FROM_ADDRESS": "101.12.240.31",
	"AUTH_DATE": "20160316083748+0800",
	"MOICA_USER_TW_SSN": "A123456789",
	"MOICA_USER_NAME": "李永正",
	"MOICA_CERT_SN": "E23029C0A56A1206E23029C0A56A1206"
 * */
public class KcgMoicaCardInfo {
	
	/** 用戶認證位址 ex:"61.216.190.109". */
	private String authFromAddress;
	
	/** 用戶認證時間 ex:"20200925115529". */
	private String authDate;
	
	/** 憑證身份證字號. */
	private String moicaUserTwSsn;
	
	/** 憑證用戶名稱 */
	private String moicaUserName;
	
	/** 憑證序號 */
	private String moicaCertSn;

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

	public String getMoicaUserTwSsn() {
		return moicaUserTwSsn;
	}

	public void setMoicaUserTwSsn(String moicaUserTwSsn) {
		this.moicaUserTwSsn = moicaUserTwSsn;
	}

	public String getMoicaUserName() {
		return moicaUserName;
	}

	public void setMoicaUserName(String moicaUserName) {
		this.moicaUserName = moicaUserName;
	}

	public String getMoicaCertSn() {
		return moicaCertSn;
	}

	public void setMoicaCertSn(String moicaCertSn) {
		this.moicaCertSn = moicaCertSn;
	}
}
