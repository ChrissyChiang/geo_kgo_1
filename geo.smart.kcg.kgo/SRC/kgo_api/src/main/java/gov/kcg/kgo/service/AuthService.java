package gov.kcg.kgo.service;

import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoValidateLoginTypeRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoVailDataLoginTypeRs;
import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.BackendGetSysMenuRs;
import gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs.BackendGetLoginUserInfoRs;
import gov.kcg.kgo.viewModel.backend.auth.login.rs.BackendLoginRs;
import gov.kcg.kgo.viewModel.backend.auth.loginTest.rq.BackendLoginTestRq;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rq.FrontendGetCityMemberInfoRq;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean.FrontendGetCityMemberInfoRs;
import gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.FrontendGetParamSetRs;
import gov.kcg.kgo.viewModel.frontend.auth.getUserInfo.rs.FrontendGetLoginUserInfoRs;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.CityCardUserRs;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.FrontendGetValidateCodeRs;
import gov.kcg.kgo.viewModel.frontend.auth.login.rs.FrontendLoginResponse;
import gov.kcg.kgo.viewModel.frontend.auth.loginTest.rq.FrontendLoginTestRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
/**
 *  前/後台 授權相關Service.
 */
public interface AuthService {
	/**
	 * 後台 - 市府員工登入.
	 *
	 * @param jwtToken the jwt token
	 * @return the backend login response
	 */
	public BackendLoginRs doBackendLoginAction(String jwtToken);

	/**
	 * GEO 20211115 add 非市府員工登入後臺
	 * @param jwtToken
	 * @param exchange
	 * @return
	 */
	public BackendLoginRs doBackendCityMemberLoginAction(String jwtToken, String exchange);
	
	/**
	 * 後台 - 市府員工測試登入.
	 * @return the backend login rs
	 */
	public <T extends QueryInfoRs> BackendLoginRs doBackendTestLoginAction(BackendLoginTestRq rq);

	/**
	 * GEO 20211115 add 非市府員工登入後臺
	 * 後台 - 非市府員工測試登入.
	 * @return the backend login rs
	 */
	public <T extends QueryInfoRs> BackendLoginRs doCityMemberBackendTestLoginAction(BackendLoginTestRq rq);
	
	/**
	 * 後台 - 取得登入使用者資訊.
	 *
	 * @return the login user info action
	 */
	public BackendGetLoginUserInfoRs getBackendLoginUserInfoAction();
	
	/**
	 * 後台 - 取得 角色系統選單.
	 *
	 * @return the gets the sys menu action
	 */
	public BackendGetSysMenuRs getGetSysMenuAction();
	
	/**
	 * 後台 - 登出.
	 *
	 * @return the gets the sys menu action
	 */
	public void doBackendLogoutAction();
	
	/**
	 * 前台 一般民眾登入.
	 *
	 * @param jwtToken the jwt token
	 * @return the frontend login response
	 */
	public FrontendLoginResponse doFrontendLoginAction(String jwtToken, String exchange);

	public FrontendLoginResponse doFrontendStaffLoginAction(String jwtToken);
	
	/**
	 * 前台 - 取得登入使用者資訊.
	 *
	 * @return the frontend login user info action
	 */
	public FrontendGetLoginUserInfoRs getFrontendLoginUserInfoAction();

	public FrontendLoginResponse doFrontendTestLoginAction(FrontendLoginTestRq rq);
    
    /**
     * 取得參數設定值.
     *
     * @return the param set action
     */
	public FrontendGetParamSetRs getParamSetAction();
	
	/**
	 * (前臺登入) 呼叫市民科技 確認是否為會員 (是會員回傳 身分證號、姓名 城市幣).
	 *
	 * @return the city member info
	 */
	public FrontendGetCityMemberInfoRs getCityMemberInfo(FrontendGetCityMemberInfoRq rq);
    
	/**
	  * 前台 - 登出.
	 *
	 * @return the gets the sys menu action
	 */
	public void doFrontendLogoutAction();
	
	/**
	 * 取得驗證碼數字(四碼).
	 *
	 * @return the validate code action
	 */
	public FrontendGetValidateCodeRs getValidateCodeAction();

	/**
	 **市民卡界接至本站
	 */
	public CityCardUserRs getCityCardUser(String access_token);

	/**
	 * 服務申辦-驗證符合登入身份.
	 *
	 * @return the validate code action
	 */
	public GeoVailDataLoginTypeRs getValidateLoginTypeAction(String id ,int validateTypeCode);
}
