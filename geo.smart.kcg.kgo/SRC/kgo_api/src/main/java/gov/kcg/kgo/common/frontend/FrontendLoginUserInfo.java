package gov.kcg.kgo.common.frontend;

import java.util.Date;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean.KcgTwfidoSsoInfo;
import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.common.LoginUserInfo;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean.KcgFacebookSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.KcgGoogleSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean.KcgHcaCardSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean.KcgEgovInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean.KcgUserBasicInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean.LineInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean.KcgMoicaCardInfo;

/**
 * 前臺 - 使用者登入資訊.
 */
public class FrontendLoginUserInfo {
	/** 登入成功後 jwtToken */
	private String jwtToken;

	/** jwtToken 有效日期 */
	private Date jwtTokenExpDate;
	
	/** 登入成功後 查詢後的使用者資訊 jsonStr. */
	private String userLoginInfoJsonStr;

	/**
	 * 依據登入類型取得的 userSsoToken PUBLIC_APP_USER_SSO_TOKEN → 市府員工 用戶基本資訊 TOKEN,
	 * KCG_MOICA_USER_SSO_TOKEN → 一般民眾 自然人憑證登入資訊 TOKEN, KCG_HCA_USER_SSO_TOKEN →
	 * 一般民眾 健保卡登入資訊 TOKEN, KCG_EGOV_USER_SSO_TOKEN → 一般民眾 我的e 政府用戶基本資訊 TOKEN,
	 * KCG_GOOGLE_USER_SSO_TOKEN → 一般民眾 Google 用戶基本資訊 TOKEN,
	 * KCG_FACEBOOK_USER_SSO_TOKEN→ 一般民眾 Facebook 用戶基本資訊 TOKEN
	 */
	private String userSsoToken;

	/** 前臺登入 - 與其他系統介接交換資訊 (AES加密). */
	private String exchange;

	/** 登入方式類別 */
	private LoginAuthTokenType loginAuthTokenType;

	/** 前/後臺 - 市府員工登入 使用者資訊 */
	private KcgUserBasicInfo kcgUserBasicInfo;

	/** 前臺 - google方式登入使用者資訊 */
	private KcgGoogleSsoInfo kcgGoogleSsoInfo;

	/** 前臺 - facebook方式登入使用者資訊. */
	private KcgFacebookSsoInfo kcgFacebookSsoInfo;

	/** 前臺 - 自然人憑證方式登入使用者資訊 */
	private KcgMoicaCardInfo kcgMoicaCardInfo;

	/** 前臺 - hcaCard 方式登入使用者資訊 */
	private KcgHcaCardSsoInfo kcgHcaCardSsoInfo;

	/** 前臺 - 我的 e 政府方式登入使用者資訊 */
	private KcgEgovInfo kcgEgovInfo;

	/** 前臺 -LINE 方式登入使用者資訊 */
	private LineInfo lineInfo;

	/** 前臺 -TW FidO 方式登入使用者資訊 */
	private KcgTwfidoSsoInfo kcgTwFidoSsoInfo;

	/** 依登入類型取得使用者 姓名、帳號、身分證號、email. (依各種登入方式才可能會有對應資料) */
	/** 姓名 (市民科技會員姓名 有提供就帶, 否則就用單登姓名)*/
	private String name = StringUtils.EMPTY;
	/** 身分證號 */
	private String twSSn = StringUtils.EMPTY;
	/** email */
	private String email = StringUtils.EMPTY;
	/***/
	
	/** 市民科技會員帳號 依登入類型取得使用者帳號 .*/
	private String userAccount; 

	/** 市民科技會員 身分證號HashID(呼叫API 取得, 非登入資訊的身分證號) */
	private String memberHashID;

	/** 市民科技會員 城市幣(呼叫API 取得) */
	private String kCoinBalance;

	public FrontendLoginUserInfo(LoginUserInfo loginUser) {
		this.jwtToken = loginUser.getJwtToken();
		this.jwtTokenExpDate = loginUser.getJwtTokenExpDate();
		this.userLoginInfoJsonStr = loginUser.getUserLoginInfoJsonStr();
		this.userSsoToken = loginUser.getUserSsoToken();
		this.exchange = loginUser.getExchange();
		this.kcgUserBasicInfo = loginUser.getKcgUserBasicInfo();
		this.loginAuthTokenType = loginUser.getLoginAuthTokenType();
		this.kcgGoogleSsoInfo = loginUser.getKcgGoogleSsoInfo();
		this.kcgFacebookSsoInfo = loginUser.getKcgFacebookSsoInfo();
		this.kcgMoicaCardInfo = loginUser.getKcgMoicaCardInfo();
		this.kcgHcaCardSsoInfo = loginUser.getKcgHcaCardSsoInfo();
		this.kcgEgovInfo = loginUser.getKcgEgovInfo();
		this.lineInfo = loginUser.getLineInfo();
		this.kcgTwFidoSsoInfo = loginUser.getKcgTwFidoSsoInfo();
		this.name = loginUser.getMemberName();
		this.memberHashID = loginUser.getMemberHashID();
		this.kCoinBalance = loginUser.getkCoinBalance();
		this.userAccount = loginUser.getUserAccount();

		// 依登入類型取得使用者姓名、身分證、email
		setInfoByLoginType();
	}

	/**
	 * 依登入類型取得使用者姓名、身分證、email.
	 * 姓名 -> 預設城市資料平台會員姓名, 若無則帶入單登姓名
	 *
	 * @param loginUserInfo the login user info
	 * @return the user account by info
	 */
	public void setInfoByLoginType() {
		if (loginAuthTokenType != null) {
			if (LoginAuthTokenType.GOOGLE.equals(loginAuthTokenType)) {
				this.name = StringUtils.isNotBlank(this.name) ? this.name : StringUtils.defaultString(this.kcgGoogleSsoInfo.getGoogleUserName(), StringUtils.EMPTY);
				this.email = StringUtils.defaultString(this.kcgGoogleSsoInfo.getGoogleUserAccount(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.MOICA.equals(loginAuthTokenType)) {
				this.name =  StringUtils.isNotBlank(this.name) ? this.name : StringUtils.defaultString(this.kcgMoicaCardInfo.getMoicaUserName(), StringUtils.EMPTY);
				this.twSSn = StringUtils.defaultString(this.kcgMoicaCardInfo.getMoicaUserTwSsn(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.HCA.equals(loginAuthTokenType)) {
				this.name =  StringUtils.isNotBlank(this.name) ? this.name : StringUtils.defaultString(this.kcgHcaCardSsoInfo.getHcaUserName(), StringUtils.EMPTY);
				this.twSSn = StringUtils.defaultString(this.kcgHcaCardSsoInfo.getHcaUserTwSsn(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.EGOV.equals(loginAuthTokenType)) {
				this.name =  StringUtils.isNotBlank(this.name) ? this.name : StringUtils.defaultString(this.kcgEgovInfo.getEgovUserCn(), StringUtils.EMPTY);
				this.twSSn = StringUtils.defaultString(this.kcgEgovInfo.getEgovUserUid(), StringUtils.EMPTY);
				this.email = StringUtils.defaultString(this.kcgEgovInfo.getEgovUserMail(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.FACEBOOK.equals(loginAuthTokenType)) {
				this.name =  StringUtils.isNotBlank(this.name) ? this.name : StringUtils.defaultString(this.kcgFacebookSsoInfo.getFacebookUserName(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.LINE.equals(loginAuthTokenType)) {
				this.name =  StringUtils.isNotBlank(this.name) ? this.name : StringUtils.defaultString(this.lineInfo.getLineUserDisplayname(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.TW_FIDO.equals(loginAuthTokenType)) {
				this.twSSn = StringUtils.defaultString(this.kcgTwFidoSsoInfo.getTwfidoUserTwSSn(), StringUtils.EMPTY);
			}

			if (LoginAuthTokenType.BASIC.equals(loginAuthTokenType)) {
				this.name = StringUtils.defaultString(this.kcgUserBasicInfo.getAppUserLoginId(), StringUtils.EMPTY);
			}
		}
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getUserLoginInfoJsonStr() {
		return userLoginInfoJsonStr;
	}

	public void setUserLoginInfoJsonStr(String userLoginInfoJsonStr) {
		this.userLoginInfoJsonStr = userLoginInfoJsonStr;
	}

	public Date getJwtTokenExpDate() {
		return jwtTokenExpDate;
	}

	public void setJwtTokenExpDate(Date jwtTokenExpDate) {
		this.jwtTokenExpDate = jwtTokenExpDate;
	}

	public String getUserSsoToken() {
		return userSsoToken;
	}

	public void setUserSsoToken(String userSsoToken) {
		this.userSsoToken = userSsoToken;
	}

	public LoginAuthTokenType getLoginAuthTokenType() {
		return loginAuthTokenType;
	}

	public void setLoginAuthTokenType(LoginAuthTokenType loginAuthTokenType) {
		this.loginAuthTokenType = loginAuthTokenType;
	}

	public KcgUserBasicInfo getKcgUserBasicInfo() {
		return kcgUserBasicInfo;
	}

	public void setKcgUserBasicInfo(KcgUserBasicInfo kcgUserBasicInfo) {
		this.kcgUserBasicInfo = kcgUserBasicInfo;
	}

	public KcgGoogleSsoInfo getKcgGoogleSsoInfo() {
		return kcgGoogleSsoInfo;
	}

	public void setKcgGoogleSsoInfo(KcgGoogleSsoInfo kcgGoogleSsoInfo) {
		this.kcgGoogleSsoInfo = kcgGoogleSsoInfo;
	}

	public KcgFacebookSsoInfo getKcgFacebookSsoInfo() {
		return kcgFacebookSsoInfo;
	}

	public void setKcgFacebookSsoInfo(KcgFacebookSsoInfo kcgFacebookSsoInfo) {
		this.kcgFacebookSsoInfo = kcgFacebookSsoInfo;
	}

	public KcgMoicaCardInfo getKcgMoicaCardInfo() {
		return kcgMoicaCardInfo;
	}

	public void setKcgMoicaCardInfo(KcgMoicaCardInfo kcgMoicaCardInfo) {
		this.kcgMoicaCardInfo = kcgMoicaCardInfo;
	}

	public KcgHcaCardSsoInfo getKcgHcaCardSsoInfo() {
		return kcgHcaCardSsoInfo;
	}

	public void setKcgHcaCardSsoInfo(KcgHcaCardSsoInfo kcgHcaCardSsoInfo) {
		this.kcgHcaCardSsoInfo = kcgHcaCardSsoInfo;
	}

	public KcgEgovInfo getKcgEgovInfo() {
		return kcgEgovInfo;
	}

	public void setKcgEgovInfo(KcgEgovInfo kcgEgovInfo) {
		this.kcgEgovInfo = kcgEgovInfo;
	}

	public KcgTwfidoSsoInfo getKcgTwFidoSsoInfo() {
		return kcgTwFidoSsoInfo;
	}

	public void setKcgTwFidoSsoInfo(KcgTwfidoSsoInfo kcgTwFidoSsoInfo) {
		this.kcgTwFidoSsoInfo = kcgTwFidoSsoInfo;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getMemberHashID() {
		return memberHashID;
	}

	public void setMemberHashID(String memberHashID) {
		this.memberHashID = memberHashID;
	}

	public LineInfo getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTwSSn() {
		return twSSn;
	}

	public void setTwSSn(String twSSn) {
		this.twSSn = twSSn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getkCoinBalance() {
		return kCoinBalance;
	}

	public void setkCoinBalance(String kCoinBalance) {
		this.kCoinBalance = kCoinBalance;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}
