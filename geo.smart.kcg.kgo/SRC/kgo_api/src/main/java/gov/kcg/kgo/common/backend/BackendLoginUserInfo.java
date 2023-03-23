package gov.kcg.kgo.common.backend;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import gov.kcg.kgo.common.LoginUserInfo;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.facebook.bean.KcgFacebookSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.google.bean.KcgGoogleSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.hca.bean.KcgHcaCardSsoInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgEgov.bean.KcgEgovInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.kcgUserBasic.bean.KcgUserBasicInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.line.bean.LineInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.moicaCard.bean.KcgMoicaCardInfo;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.twfido.bean.KcgTwfidoSsoInfo;

/**
 * 後臺 - 使用者登入資訊 .
 */
public class BackendLoginUserInfo {
	/** Logger. */
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BackendLoginUserInfo.class);

	/** 市府員工帳號. */
	private String userId;

	/** 姓名. */
	private String name;

	/** 機關. */
	private String organ;

	/** 單位. */
	private String unit;
	
	/** 角色權限清單 @KgoRoleEnum */
	private List<String> roles;
	
	/** 登入成功後 jwtToken  */
	private String jwtToken;
	
	/** jwtToken 有效日期 */
	private Date jwtTokenExpDate;
	
	/** 依據登入類型取得的 userSsoToken 
	 * PUBLIC_APP_USER_SSO_TOKEN 	→ 	市府員工 用戶基本資訊 TOKEN,
     * KCG_MOICA_USER_SSO_TOKEN 	→ 	一般民眾 自然人憑證登入資訊 TOKEN,
     * KCG_HCA_USER_SSO_TOKEN 	→ 	一般民眾 健保卡登入資訊 TOKEN,
     * KCG_EGOV_USER_SSO_TOKEN 	→ 	一般民眾 我的e 政府用戶基本資訊 TOKEN,
     * KCG_GOOGLE_USER_SSO_TOKEN 	→ 	一般民眾 Google 用戶基本資訊 TOKEN, 
     * KCG_FACEBOOK_USER_SSO_TOKEN→ 	一般民眾 Facebook 用戶基本資訊 TOKEN
	 * */
	private String userSsoToken;
	
	/** 登入方式類別 */
	private LoginAuthTokenType loginAuthTokenType;
	
	/** GEO 20211115 add 後臺 - 市府員工登入 使用者資訊  */
	private KcgUserBasicInfo kcgUserBasicInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 google方式登入使用者資訊 */
	private KcgGoogleSsoInfo kcgGoogleSsoInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 facebook方式登入使用者資訊. */
	private KcgFacebookSsoInfo kcgFacebookSsoInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 自然人憑證方式登入使用者資訊 */
	private KcgMoicaCardInfo kcgMoicaCardInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 方式登入使用者資訊 */
	private KcgHcaCardSsoInfo kcgHcaCardSsoInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 我的 e 政府方式登入使用者資訊 */
	private KcgEgovInfo kcgEgovInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 LINE 方式登入使用者資訊 */
	private LineInfo lineInfo;

	/** GEO 20211115 add 後臺 - 非市府員工 TW FidO 方式登入使用者資訊 */
	private KcgTwfidoSsoInfo kcgTwFidoSsoInfo;

	/** 登入時間 */
	private Date loginTime;
	
	/**原登入帳號**/
	private String originUserId;
	
	public BackendLoginUserInfo() {}

	public BackendLoginUserInfo(LoginUserInfo loginUser) {
		this.jwtToken = loginUser.getJwtToken();
		this.jwtTokenExpDate = loginUser.getJwtTokenExpDate();
		this.userSsoToken = loginUser.getUserSsoToken();
		this.loginAuthTokenType = loginUser.getLoginAuthTokenType();
		if (loginAuthTokenType.equals(LoginAuthTokenType.BASIC)) this.userId = loginUser.getKcgUserBasicInfo().getAppUserLoginId();
		this.kcgUserBasicInfo = loginUser.getKcgUserBasicInfo();
		this.kcgGoogleSsoInfo = loginUser.getKcgGoogleSsoInfo();
		this.kcgFacebookSsoInfo = loginUser.getKcgFacebookSsoInfo();
		this.kcgMoicaCardInfo = loginUser.getKcgMoicaCardInfo();
		this.kcgHcaCardSsoInfo = loginUser.getKcgHcaCardSsoInfo();
		this.kcgEgovInfo = loginUser.getKcgEgovInfo();
		this.lineInfo = loginUser.getLineInfo();
		this.kcgTwFidoSsoInfo = loginUser.getKcgTwFidoSsoInfo();
		this.loginTime = loginUser.getLoginTime();
//		LOGGER.info("BackendLoginUserInfo : " + this.toString());
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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

	public LineInfo getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}

	public KcgTwfidoSsoInfo getKcgTwFidoSsoInfo() {
		return kcgTwFidoSsoInfo;
	}

	public void setKcgTwFidoSsoInfo(KcgTwfidoSsoInfo kcgTwFidoSsoInfo) {
		this.kcgTwFidoSsoInfo = kcgTwFidoSsoInfo;
	}	

	public String getOriginUserId() {
		return originUserId;
	}

	public void setOriginUserId(String originUserId) {
		this.originUserId = originUserId;
	}

	@Override
	public String toString() {
		return "BackendLoginUserInfo{" +
				"userId='" + userId + '\'' +
				", name='" + name + '\'' +
				", organ='" + organ + '\'' +
				", unit='" + unit + '\'' +
				", roles=" + roles +
				", jwtToken='" + jwtToken + '\'' +
				", jwtTokenExpDate=" + jwtTokenExpDate +
				", userSsoToken='" + userSsoToken + '\'' +
				", loginAuthTokenType=" + loginAuthTokenType +
				", kcgUserBasicInfo=" + kcgUserBasicInfo +
				", kcgGoogleSsoInfo=" + kcgGoogleSsoInfo +
				", kcgFacebookSsoInfo=" + kcgFacebookSsoInfo +
				", kcgMoicaCardInfo=" + kcgMoicaCardInfo +
				", kcgHcaCardSsoInfo=" + kcgHcaCardSsoInfo +
				", kcgEgovInfo=" + kcgEgovInfo +
				", lineInfo=" + lineInfo +
				", kcgTwFidoSsoInfo=" + kcgTwFidoSsoInfo +
				", loginTime=" + loginTime +
				'}';
	}
}
