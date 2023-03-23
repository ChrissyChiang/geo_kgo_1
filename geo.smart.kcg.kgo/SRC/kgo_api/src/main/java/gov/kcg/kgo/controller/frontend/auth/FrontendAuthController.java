package gov.kcg.kgo.controller.frontend.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.kcg.kgo.geoenum.GeoValidateType;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoValidateLoginTypeRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoVailDataLoginTypeRs;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rq.ServiceAnnounceHomeRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Lists;

import gov.kcg.kgo.common.LoginUserInfo;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.constant.SecurityConstant;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.service.AuthService;
import gov.kcg.kgo.service.impl.helper.AuthServiceHelper;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.util.SsoUtil;
import gov.kcg.kgo.viewModel.backend.auth.login.rq.BackendLoginRq;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.FrontendGetParamSetRs;
import gov.kcg.kgo.viewModel.frontend.auth.getUserInfo.rs.FrontendGetLoginUserInfoRs;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.CityCardUserRs;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.FrontendGetValidateCodeRs;
import gov.kcg.kgo.viewModel.frontend.auth.login.rq.FrontendLoginRequest;
import gov.kcg.kgo.viewModel.frontend.auth.login.rs.FrontendLoginResponse;
import gov.kcg.kgo.viewModel.frontend.auth.loginTest.rq.FrontendLoginTestRq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前臺 登入/授權 controller.
 */
@Controller
@RequestMapping("/frontend/auth")
@Api(tags = {"frontend-auth-controller 前臺 登入/授權"})
public class FrontendAuthController extends BaseController {
	/** 前臺 登入使用者資訊 Object SESSION KEY. */
	public final static String FRONTEND_USER_INO_KEY = "frontendUserInfo";
	
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontendAuthController.class);

	@Autowired
	private AuthService ssoLoginService;

	@Autowired
	private AuthService authService;

	/**
	 *  後臺測試登入.
	 *
	 * @param rq the rq
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = { "/loginTestAction" }, method = { RequestMethod.POST },
			consumes = {MediaType.APPLICATION_JSON_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
//	@ApiOperation(value = "* 前臺測試登入")
	public FrontendLoginResponse loginTestAction(@RequestBody FrontendLoginTestRq rq, HttpServletRequest request) {
		// 前臺登入作業.
		LOGGER.info("FrontendAuthController loginTestAction...");
		FrontendLoginResponse rs = authService.doFrontendTestLoginAction(rq);
		return rs;
	} //loginTestAction


	/**
	 *  後臺測試登入get method.
	 *
	 * @return the string
	 */
	@RequestMapping(value = { "/loginTestActionGet/{userId}" }, method = { RequestMethod.GET },
			produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
//	@ApiOperation(value = "前臺測試登入")
	public FrontendLoginResponse loginTestActionGet(@PathVariable("userId") String userId) {
		// 前臺登入作業.
		FrontendLoginTestRq rq = new FrontendLoginTestRq();
		rq.setUserId(userId);
		FrontendLoginResponse rs = authService.doFrontendTestLoginAction(rq);
		return rs;
	}


	/**
     *  前臺登入作業.
     *
     * @param rq the rq
     * @param request the request
     * @return the string
     */
	@ApiOperation(value = "前臺登入作業")
    @RequestMapping(value = { "/loginAction" }, method = { RequestMethod.POST },
    		consumes = {MediaType.APPLICATION_JSON_VALUE },
    		produces = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
	public FrontendLoginResponse loginAction(@RequestBody FrontendLoginRequest rq, HttpServletRequest request) {
    	// 前臺登入作業.
		LOGGER.info("FrontendAuthController loginAction...");
    	FrontendLoginResponse rs = ssoLoginService.doFrontendLoginAction(rq.getJwt(), rq.getExchange());
    	return rs;
	} //loginAction

//	@ApiOperation(value = "市府員工前臺登入作業")
	@RequestMapping(value = { "/staffLoginAction" }, method = { RequestMethod.POST },
			consumes = {MediaType.APPLICATION_JSON_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FrontendLoginResponse staffLoginAction(@RequestBody BackendLoginRq rq, HttpServletRequest request) {
		// 前臺登入作業.
		LOGGER.info("FrontendAuthController staffLoginAction...");
		FrontendLoginResponse rs = ssoLoginService.doFrontendStaffLoginAction(rq.getJwt());
		return rs;
	} //loginAction
    
	/**
	 * 取得前台 登入使用者資訊.
	 *
	 * @param rq      the rq
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = { "/getLoginUserInfoAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
//	@ApiOperation(value = "取得前台登入者資訊")
	public FrontendGetLoginUserInfoRs getLoginUserInfoAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
		// 取得後台 登入使用者資訊.
		FrontendGetLoginUserInfoRs rs = ssoLoginService.getFrontendLoginUserInfoAction();
		return rs;
	}
	
	/**
	 * 取得參數設定值.
	 *
	 * @param rq      the rq
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = { "/getParamSetAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
//	@ApiOperation(value = "取得參數設定值")
	public FrontendGetParamSetRs getParamSetAction(HttpServletRequest request) {
		// 取得參數設定值.
		FrontendGetParamSetRs rs = authService.getParamSetAction();
		return rs;
	}
	
	/**
	 * 前臺 - 登出.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/logout", method = {RequestMethod.POST,RequestMethod.GET})
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("FrontendAuthController logout...");
		String userAccount = StringUtils.EMPTY;
		// 前臺 - 登出(紀錄操作log).
		authService.doFrontendLogoutAction();

		FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
		if(userInfo != null ) {
			userAccount = userInfo.getUserAccount();
		}
		// 清除session
		Enumeration<String> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		request.getSession().invalidate();
		LOGGER.info(">>> 前臺 - 登出  logout userAccount : " + userAccount);

		// return new ApiBaseResponse<BaseViewForm>();
	} //logout
	
	/**
	 * 取得驗證碼數字(四碼).
	 *
	 * @param rq      the rq.0
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = { "/getValidateCodeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
//	@ApiOperation(value = "取得驗證碼數字(四碼)")
	public FrontendGetValidateCodeRs getValidateCodeAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
		// 取得驗證碼數字(四碼).
		FrontendGetValidateCodeRs rs = authService.getValidateCodeAction();
		return rs;
	}

	/**
	 * 服務申辦-驗證符合登入身份
	 *
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "服務申辦-驗證符合登入身份")
	@RequestMapping(value = { "/getValidateLoginTypeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GeoVailDataLoginTypeRs getValidateLoginTypeAction(@RequestBody GeoValidateLoginTypeRq rq,
															 HttpServletRequest request) {
		GeoVailDataLoginTypeRs rs = authService.getValidateLoginTypeAction(rq.getVailDataId(),rq.getVailDataType());
		return rs;
	}

	/**
	 **經由市民卡登入連結至本網站
	 */
	@RequestMapping(value = { "/cityCardUserToken" }, method = { RequestMethod.POST })
	@ApiOperation(value = "經由市民卡登入連結至本網站")
	@ResponseBody
	public CityCardUserRs CityCardUserToken(@RequestParam String access_token, HttpServletRequest request) {
		CityCardUserRs rs = authService.getCityCardUser(access_token);
		if(rs != null) {
				LoginUserInfo loginUser = new LoginUserInfo();
				//loginUser.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
				FrontendLoginUserInfo feUserInfo = new FrontendLoginUserInfo(loginUser);			
			try {
				feUserInfo.setName(rs.getData().getName());
				feUserInfo.setTwSSn(rs.getData().getLicense());
				feUserInfo.setEmail(rs.getData().getEmail());
				feUserInfo.setMemberHashID(rs.getData().getSub());
				feUserInfo.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
	
				
				WebUtils.setSessionAttribute(request, FRONTEND_USER_INO_KEY, feUserInfo);
				// 給予前臺登入者API 使用權限 預設角色			
				List<GrantedAuthority> grantAuthorities = Lists.newArrayList();
				for (String role : Arrays.asList(SecurityConstant.FRONTEND_ROLE)) {
					grantAuthorities.add(new SimpleGrantedAuthority(role));
				}
				Authentication authentication = new UsernamePasswordAuthenticationToken(feUserInfo.getTwSSn(), "", grantAuthorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(">>> CityCardUserToken: " + rs.getData().getName());
				throw e;
			}				
		}	
		return rs;  
	}	
}
