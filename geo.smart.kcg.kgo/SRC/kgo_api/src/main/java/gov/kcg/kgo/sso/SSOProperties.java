package gov.kcg.kgo.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 用component注入方式從ssoConfig.properties取得SSO相關設定
 * 
 * @author TPIuser
 *
 */
@Component
@PropertySource("classpath:ssoConfig.properties")
public class SSOProperties {

	@Deprecated
	@Value("${user_jwt_str}")
	private String userJwtStr;

	// 後台應用系統APP ID
	@Value("${BE_kcg_accounts_api_app_private_id}")
	private String BEkcgAccountsApiAppPrivateId;
	// 後台應用系統APP PWD
	@Value("${BE_kcg_accounts_api_app_private_passwd}")
	private String BEkcgAccountsApiAppPrivatePasswd;
	// 前台應用系統APP ID
	@Value("${FE_kcg_accounts_api_app_private_id}")
	private String FEkcgAccountsApiAppPrivateId;
	// 前台應用系統APP PWD
	@Value("${FE_kcg_accounts_api_app_private_passwd}")
	private String FEkcgAccountsApiAppPrivatePasswd;

	@Value("${kcg_ikpd_identity_service_id}")
	private String kcgIkpdIdentityServiceID;

	@Value("${kcg_ikpd_organ_service_id}")
	private String kcgIkpdOrganServiceID;

	@Value("${kcg_ikpd_org}")
	private String kcgIkpdOrg;
	
	@Deprecated
	@Value("${idno}")
	private String idno;

	public String getUserJwtStr() {
		return userJwtStr;
	}

	public void setUserJwtStr(String userJwtStr) {
		this.userJwtStr = userJwtStr;
	}
	
	public String getBEkcgAccountsApiAppPrivateId() {
		return BEkcgAccountsApiAppPrivateId;
	}

	public void setBEkcgAccountsApiAppPrivateId(String bEkcgAccountsApiAppPrivateId) {
		BEkcgAccountsApiAppPrivateId = bEkcgAccountsApiAppPrivateId;
	}

	public String getBEkcgAccountsApiAppPrivatePasswd() {
		return BEkcgAccountsApiAppPrivatePasswd;
	}

	public void setBEkcgAccountsApiAppPrivatePasswd(String bEkcgAccountsApiAppPrivatePasswd) {
		BEkcgAccountsApiAppPrivatePasswd = bEkcgAccountsApiAppPrivatePasswd;
	}

	public String getFEkcgAccountsApiAppPrivateId() {
		return FEkcgAccountsApiAppPrivateId;
	}

	public void setFEkcgAccountsApiAppPrivateId(String fEkcgAccountsApiAppPrivateId) {
		FEkcgAccountsApiAppPrivateId = fEkcgAccountsApiAppPrivateId;
	}

	public String getFEkcgAccountsApiAppPrivatePasswd() {
		return FEkcgAccountsApiAppPrivatePasswd;
	}

	public void setFEkcgAccountsApiAppPrivatePasswd(String fEkcgAccountsApiAppPrivatePasswd) {
		FEkcgAccountsApiAppPrivatePasswd = fEkcgAccountsApiAppPrivatePasswd;
	}

	public String getKcgIkpdIdentityServiceID() {
		return kcgIkpdIdentityServiceID;
	}

	public void setKcgIkpdIdentityServiceID(String kcgIkpdIdentityServiceID) {
		this.kcgIkpdIdentityServiceID = kcgIkpdIdentityServiceID;
	}

	public String getKcgIkpdOrganServiceID() {
		return kcgIkpdOrganServiceID;
	}

	public void setKcgIkpdOrganServiceID(String kcgIkpdOrganServiceID) {
		this.kcgIkpdOrganServiceID = kcgIkpdOrganServiceID;
	}

	public String getKcgIkpdOrg() {
		return kcgIkpdOrg;
	}

	public void setKcgIkpdOrg(String kcgIkpdOrg) {
		this.kcgIkpdOrg = kcgIkpdOrg;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}
}
