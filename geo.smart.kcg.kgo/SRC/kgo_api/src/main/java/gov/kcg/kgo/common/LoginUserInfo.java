package gov.kcg.kgo.common;

import java.util.Date;

import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean.KcgTwfidoSsoInfo;
import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean.KcgFacebookSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.KcgGoogleSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean.KcgHcaCardSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean.KcgEgovInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean.KcgUserBasicInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean.LineInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean.KcgMoicaCardInfo;

/**
 * 使用者登入資訊rs.
 */
public class LoginUserInfo {
	/** 登入成功後 jwtToken */
	private String jwtToken;

	/** jwtToken 有效日期 */
	private Date jwtTokenExpDate;

	/** 登入時間 */
	private Date loginTime;

	/**
	 * 依據登入類型取得的 userSsoToken PUBLIC_APP_USER_SSO_TOKEN → 市府員工 用戶基本資訊 TOKEN,
	 * KCG_MOICA_USER_SSO_TOKEN → 一般民眾 自然人憑證登入資訊 TOKEN, KCG_HCA_USER_SSO_TOKEN →
	 * 一般民眾 健保卡登入資訊 TOKEN, KCG_EGOV_USER_SSO_TOKEN → 一般民眾 我的e 政府用戶基本資訊 TOKEN,
	 * KCG_GOOGLE_USER_SSO_TOKEN → 一般民眾 Google 用戶基本資訊 TOKEN,
	 * KCG_FACEBOOK_USER_SSO_TOKEN→ 一般民眾 Facebook 用戶基本資訊 TOKEN
	 */
	private String userSsoToken;

	/** 登入成功後 查詢後的使用者資訊 jsonStr. */
	private String userLoginInfoJsonStr;

	/** 各系統介接 exange資料 (AES加密). */
	private String exchange;

	/** 市民科技會員 身分證號HashID(呼叫API 取得) */
	private String memberHashID = StringUtils.EMPTY;
	
	/** 市民科技會員 姓名(呼叫API 取得) */
	private String memberName = StringUtils.EMPTY;

	/** 市民科技會員 城市幣(呼叫API 取得) */
	private String kCoinBalance = "0";
	
	/** 依登入類型取得使用者帳號 .*/
	private String userAccount; 

	/** 登入方式類別 */
	private LoginAuthTokenType loginAuthTokenType;

	/** 前/後臺 - 市府員工登入 使用者資訊 */
	private KcgUserBasicInfo kcgUserBasicInfo;

	/** 前臺 - google方式登入使用者資訊 */
	private KcgGoogleSsoInfo kcgGoogleSsoInfo;

	/** 前臺 - facebook用戶資料. */
	private KcgFacebookSsoInfo kcgFacebookSsoInfo;

	/** 前臺 - 自然人憑證方式登入使用者資訊 */
	private KcgMoicaCardInfo kcgMoicaCardInfo;

	/** 前臺 - 我的 e 政府方式登入使用者資訊 */
	private KcgEgovInfo kcgEgovInfo;

	/** 前臺 - hcaCard 方式登入使用者資訊 */
	private KcgHcaCardSsoInfo kcgHcaCardSsoInfo;

	/** 前臺 -LINE 方式登入使用者資訊 */
	private LineInfo lineInfo;

	/** 前臺 - TW FidO */
	private KcgTwfidoSsoInfo kcgTwFidoSsoInfo;

	
	public LoginUserInfo() {}

	/**
	 * 依登入類型取得使用者帳號 (與市民科技界接用).
	 *
	 * @param loginUserInfo the login user info
	 * @return the user account by info
	 */
	public String setUserAccountByInfo() {
		if (loginAuthTokenType != null) {
			if (LoginAuthTokenType.GOOGLE.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgGoogleSsoInfo.getGoogleUserAccount();
			}

			if (LoginAuthTokenType.MOICA.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgMoicaCardInfo.getMoicaUserTwSsn();
			}

			if (LoginAuthTokenType.HCA.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgHcaCardSsoInfo.getHcaUserTwSsn();
			}

			if (LoginAuthTokenType.EGOV.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgEgovInfo.getEgovUserAccount();
			}

			if (LoginAuthTokenType.FACEBOOK.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgFacebookSsoInfo.getFacebookUserAccount();
			}

			if (LoginAuthTokenType.LINE.equals(loginAuthTokenType)) {
				this.userAccount = this.lineInfo.getLineUserId();
			}

			if (LoginAuthTokenType.TW_FIDO.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgTwFidoSsoInfo.getTwfidoUserTwSSn();
			}

			if (LoginAuthTokenType.BASIC.equals(loginAuthTokenType)) {
				this.userAccount = this.kcgUserBasicInfo.getAppUserTwSSn();
			}
		}
		return this.userAccount;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
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

	public KcgMoicaCardInfo getKcgMoicaCardInfo() {
		return kcgMoicaCardInfo;
	}

	public void setKcgMoicaCardInfo(KcgMoicaCardInfo kcgMoicaCardInfo) {
		this.kcgMoicaCardInfo = kcgMoicaCardInfo;
	}

	public KcgEgovInfo getKcgEgovInfo() {
		return kcgEgovInfo;
	}

	public KcgFacebookSsoInfo getKcgFacebookSsoInfo() {
		return kcgFacebookSsoInfo;
	}

	public void setKcgFacebookSsoInfo(KcgFacebookSsoInfo kcgFacebookSsoInfo) {
		this.kcgFacebookSsoInfo = kcgFacebookSsoInfo;
	}

	public void setKcgEgovInfo(KcgEgovInfo kcgEgovInfo) {
		this.kcgEgovInfo = kcgEgovInfo;
	}

	public String getUserLoginInfoJsonStr() {
		return userLoginInfoJsonStr;
	}

	public void setUserLoginInfoJsonStr(String userLoginInfoJsonStr) {
		this.userLoginInfoJsonStr = userLoginInfoJsonStr;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public KcgHcaCardSsoInfo getKcgHcaCardSsoInfo() {
		return kcgHcaCardSsoInfo;
	}

	public void setKcgHcaCardSsoInfo(KcgHcaCardSsoInfo kcgHcaCardSsoInfo) {
		this.kcgHcaCardSsoInfo = kcgHcaCardSsoInfo;
	}

	public LineInfo getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberHashID() {
		return memberHashID;
	}

	public void setMemberHashID(String memberHashID) {
		this.memberHashID = memberHashID;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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

	public KcgTwfidoSsoInfo getKcgTwFidoSsoInfo() {
		return kcgTwFidoSsoInfo;
	}

	public void setKcgTwFidoSsoInfo(KcgTwfidoSsoInfo kcgTwFidoSsoInfo) {
		this.kcgTwFidoSsoInfo = kcgTwFidoSsoInfo;
	}
}
