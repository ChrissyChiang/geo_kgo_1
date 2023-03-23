package gov.kcg.kgo.controller.backend.auth;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.geoentity.GeoKgoSwitchUserLog;
import gov.kcg.kgo.georepository.GeoSwitchUserLogRepos;
import gov.kcg.kgo.geoservice.GeoUserQueryService;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoCityCoinMembershipRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoUserQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.service.AuthService;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.PushService;
import gov.kcg.kgo.service.impl.helper.AuthServiceHelper;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.util.SsoUtil;
import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.BackendGetSysMenuRs;
import gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs.BackendGetLoginUserInfoRs;
import gov.kcg.kgo.viewModel.backend.auth.login.rq.BackendCityMemberLoginRq;
import gov.kcg.kgo.viewModel.backend.auth.login.rq.BackendLoginRq;
import gov.kcg.kgo.viewModel.backend.auth.login.rs.BackendLoginRs;
import gov.kcg.kgo.viewModel.backend.auth.loginTest.rq.BackendLoginTestRq;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Controller
@RequestMapping("/backend/auth")
@Api(tags = {"後台 登入/授權"})
public class BackendAuthController extends BaseController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendAuthController.class);
    static JSONArray ORGAN_AND_UNIT;

    @Autowired
    private AuthService authService;
    @Autowired
    KgoUserRepository kgoUserRepository;
    @Autowired
    GeoUserQueryService geoUserQueryService;
    @Autowired
    KgoOrganRepository kgoOrganRepository;
    @Autowired
    KgoUnitRepository kgoUnitRepository;
    @Autowired
    CityCoinAPIService cityCoinAPIService;
    @Autowired
    MessageUtil messageUtil;
    @Autowired
    PushService pushService;
    @Autowired
    GeoSwitchUserLogRepos geoSwitchUserLogRepos;

    /**
     * 後臺登出 url.
     */
    @Value("${backend.logout.url}")
    private String backendLogoutUrl;

    /**
     * 後臺 登入使用者資訊 Object SESSION KEY.
     */
    public final static String BACKEND_USER_INO_KEY = "backendUserInfo";

    /**
     * 後臺登入作業.
     *
     * @param rq      the rq
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = {"/loginAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
//	@ApiOperation(value = "後臺登入作業")
    public BackendLoginRs loginAction(@RequestBody BackendLoginRq rq, HttpServletRequest request) {
        BackendLoginRs rs = authService.doBackendLoginAction(rq.getJwt());

//		//正式區測試使用
//        BackendLoginRs rs = null;
//        String clientIp = KgoUtil.getClientIp();
//        if (clientIp.equals("125.228.171.119") || clientIp.equals("180.176.98.143")) {
//            rs = authService.doBackendLoginAction(rq.getJwt());
//        } else {
//            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
//        }
        return rs;
    } //loginAction

    /**
     * GEO 20211115 add 非市府員工登入後臺
     *
     * @param rq
     * @param request
     * @return
     */
    @RequestMapping(value = {"/cityMemberLoginAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
//	@ApiOperation(value = "非市府員工後臺登入作業")
    public BackendLoginRs cityMemberLoginAction(@RequestBody BackendCityMemberLoginRq rq, HttpServletRequest request) {
        // 後臺登入作業.
        LOGGER.info("BackendAuthController cityMemberLoginAction...");
        BackendLoginRs rs = authService.doBackendCityMemberLoginAction(rq.getJwt(), rq.getExchange());
        return rs;
    } //cityMemberLoginAction

    /**
     * 後臺測試登入.
     *
     * @param rq      the rq
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = {"/loginTestAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
//	@ApiOperation(value = "後臺測試登入")
    public BackendLoginRs loginTestAction(@RequestBody BackendLoginTestRq rq, HttpServletRequest request) {
        // 後臺登入作業.
        LOGGER.info("BackendAuthController loginTestAction...");
        BackendLoginRs rs = authService.doBackendTestLoginAction(rq);
        return rs;
    } //loginTestAction

    /**
     * GEO 20211115 add 非市府員工登入後臺
     * 非市府員工 後臺測試登入.
     *
     * @param rq      the rq
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = {"/cityMemberLoginTestAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
//	@ApiOperation(value = "* 非市府員工 後臺測試登入.")
    public BackendLoginRs cityMemberLoginTestAction(@RequestBody BackendLoginTestRq rq, HttpServletRequest request) {
        // 後臺登入作業.
        LOGGER.info("BackendAuthController loginTestAction...");
        BackendLoginRs rs = authService.doCityMemberBackendTestLoginAction(rq);
        return rs;
    } //loginTestAction

    /**
     * 後臺測試登入.
     *
     * @return the string
     */
//	@RequestMapping(value = { "/loginTestActionGet/{userId}" }, method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	@ApiOperation(value = "後臺測試登入")
    public BackendLoginRs loginTestActionGet(@PathVariable("userId") String userId) {
        BackendLoginTestRq rq = new BackendLoginTestRq();

        rq.setUserId(userId);
        // 後臺登入作業.
        BackendLoginRs rs = authService.doBackendTestLoginAction(rq);
        return rs;
    }

    /**
     * 取得取得後台 登入使用者資訊.
     *
     * @param rq      the rq
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = {"/getLoginUserInfoAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "取得登入者資訊")
    public BackendGetLoginUserInfoRs getLoginUserInfoAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
        // 取得後台 登入使用者資訊.
        BackendGetLoginUserInfoRs rs = authService.getBackendLoginUserInfoAction();
        return rs;
    }

    /**
     * 取得功能選單.
     *
     * @param rq      the rq
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = {"/getSysMenuAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "取得功能選單")
    public BackendGetSysMenuRs getSysMenuAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
        // 後台 - 取得 角色系統選單.
        BackendGetSysMenuRs rs = authService.getGetSysMenuAction();
        return rs;
    }

    /**
     * 登出.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//	@ApiOperation(value = "登出")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("BackendAuthController logout...");
        String userId = "";
        // 後台 - 登出(紀錄操作log).
        authService.doBackendLogoutAction();

        BackendLoginUserInfo userInfo = (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY);
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        // 清除session
        Enumeration<String> em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        request.getSession().invalidate();
        LOGGER.info(">>> logout userId : " + userId);

        /** 後臺登出 url. */
        // dev: backend.logout.url=/mockLogin
        // prod:
        // backend.logout.url=https://accounts.kcg.gov.tw/index_app.php?app_id=KCG_4583
        String logoutUrl = backendLogoutUrl;
        try {
            response.sendRedirect(logoutUrl);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(">>> logout Exception userId: " + userId);
            throw e;
        }
    } //logout

//    /**
//     * 1.代碼: WS-Z01-A0-01: 進行應用系統進行身分驗證.
//     *
//     * @param rq the rq
//     * @param request the request
//     * @return the string
//     */
//    @RequestMapping(value = { "/appBasicAuthAction" }, method = { RequestMethod.GET, RequestMethod.POST }, 
//    		consumes = {MediaType.APPLICATION_JSON_VALUE },
//    		produces = {MediaType.APPLICATION_JSON_VALUE })
//    @ResponseBody
//	public static void appBasicAuthAction() {
//    	
//        String kcg_accounts_api_app_private_id     = Config.getInstance().getProperty("kcg_accounts_api_app_private_id");
//        String kcg_accounts_api_app_private_passwd = Config.getInstance().getProperty("kcg_accounts_api_app_private_passwd");
//        
//        System.out.println("kcg_accounts_api_app_private_id = " + kcg_accounts_api_app_private_id);   	
//        System.out.println("kcg_accounts_api_app_private_passwd = " + kcg_accounts_api_app_private_passwd);  
//    	
//    	Api_Client ac  = new Api_Client();
//    	String rs = ac.app_request_basic_authentication(kcg_accounts_api_app_private_id, kcg_accounts_api_app_private_passwd);
//    	System.out.println("rs = " + rs);
//    	kcgsso2 ks = kcgsso2.getInstance();
//    	ks.
//	}
//    
//    public static void main(String[] args) {
//    	appBasicAuthAction();
//	}


    /**
     * 系統管理者改成其他user登入
     */
    @RequestMapping(value = {"/switchUser"}, method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "系統管理者改成其他user登入")
    public boolean switchUser(@RequestParam String userId, HttpServletRequest request) {
        boolean result = false;
        LOGGER.info("BackendAuthController admin改成其他user...");
        BackendLoginUserInfo userInfo = KgoUtil.getBackendLoginUser();
        String originUserId = userInfo.getUserId();
//        boolean isAdmin = userInfo.getRoles().contains(new String(KgoRoleEnum.ADMIN.getValue()));
        boolean isAdmin = userInfo.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase);
        boolean isSwitched = Strings.isNotBlank(userInfo.getOriginUserId());
        LOGGER.info("BackendAuthController switchUser from " + userInfo.getUserId() + " to " + userId);

        if (isAdmin && !isSwitched) {
            //clean current session			
            Enumeration<String> em = request.getSession().getAttributeNames();
            while (em.hasMoreElements()) {
                request.getSession().removeAttribute(em.nextElement().toString());
            }

            //set new profiles & roles into session
            try {
                KgoUserRepository kgoUserRepository = SpringUtil.getDao(KgoUserRepository.class);
                Optional<KgoUser> optional = kgoUserRepository.findById(userId);
                BackendLoginUserInfo beloginUser = new BackendLoginUserInfo();
                if (optional.isPresent()) {
                    KgoUser kgoUser = optional.get();
                    beloginUser.setUserId(userId);
                    beloginUser.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
                    beloginUser.setName(kgoUser.getName());
                    beloginUser.setOrgan(kgoUser.getOrgan());
                    beloginUser.setUnit(kgoUser.getUnit());

                    AuthServiceHelper auh = new AuthServiceHelper();
                    beloginUser.setRoles(auh.getUserRoles(userId));
                    beloginUser.setOriginUserId(originUserId);
                    WebUtils.setSessionAttribute(request, BACKEND_USER_INO_KEY, beloginUser);
                    geoSwitchUserLogRepos.save(new GeoKgoSwitchUserLog(request, "switchUser", originUserId, userId));
                    result = true;
                    LOGGER.info("BackendAuthController switchUser from " + userInfo.getUserId() + " to " + userId + "success");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(">>> switchUser: " + userId);
                throw e;
            }
        }
        return result;
    }

    @RequestMapping(value = {"/switchStatus"}, method = {RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "身分切換狀態查詢")
    public JSONObject switchStatus(HttpServletRequest request) {
        BackendLoginUserInfo userInfo = (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY);
//        boolean isAdmin = userInfo.getRoles().contains(new String(KgoRoleEnum.ADMIN.getValue()));
        boolean isAdmin = userInfo.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase);
        String originUserId = userInfo.getOriginUserId();
        JSONObject result = new JSONObject();
        boolean switchToOrigin = false;
        boolean switchUser = false;
        if (isAdmin) {
            if (originUserId != null)
                switchToOrigin = true;
            else
                switchUser = true;
        } else if (originUserId != null)
            switchToOrigin = true;
        result.put("switchToOrigin", switchToOrigin);
        result.put("switchUser", switchUser);
        return result;
    }


    /**
     * 改回原來系統管理者登入
     */
    @RequestMapping(value = {"/switchToOrigin"}, method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "改回原來系統管理者登入")
    public boolean switchToOrigin(HttpServletRequest request) {
        boolean result = false;
        LOGGER.info("BackendAuthController switchToOrigin...");
        BackendLoginUserInfo userInfo = (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY);
        String originUserId = userInfo.getOriginUserId();
        LOGGER.info("BackendAuthController switchToOrigin from " + userInfo.getUserId() + " to " + originUserId);
        if (originUserId != null) {
            //clean current session			
            Enumeration<String> em = request.getSession().getAttributeNames();
            while (em.hasMoreElements()) {
                request.getSession().removeAttribute(em.nextElement());
            }
            try {
                //set new profiles & roles into session
                KgoUserRepository kgoUserRepository = SpringUtil.getDao(KgoUserRepository.class);
                Optional<KgoUser> optional = kgoUserRepository.findById(originUserId);
                BackendLoginUserInfo beloginUser = new BackendLoginUserInfo();
                if (optional.isPresent()) {
                    KgoUser kgoUser = optional.get();
                    beloginUser.setUserId(originUserId);
                    beloginUser.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
                    beloginUser.setName(kgoUser.getName());
                    beloginUser.setOrgan(kgoUser.getOrgan());
                    beloginUser.setUnit(kgoUser.getUnit());

                    AuthServiceHelper auh = new AuthServiceHelper();
                    beloginUser.setRoles(auh.getUserRoles(originUserId));

                    WebUtils.setSessionAttribute(request, BACKEND_USER_INO_KEY, beloginUser);
                    geoSwitchUserLogRepos.save(new GeoKgoSwitchUserLog(request, "switchToOrigin", userInfo.getUserId(), originUserId));
                    result = true;
                    LOGGER.info("BackendAuthController switchToOrigin from " + userInfo.getUserId() + " to " + originUserId + " success");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(">>> switchToOrigin: " + originUserId);
                throw e;
            }
        }
        return result;
    }

    /**
     * Geo 20221014 add_Jim
     * 切換使用者搜尋
     *
     * @param organ   organ
     * @param unit    unit
     * @param account account
     */
    @RequestMapping(value = {"/getUserInfo"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "取得使用者資訊")
    public GeoUserQueryRs getUserInfo(HttpServletRequest request,
                                      @RequestParam(name = "organ", required = false) String organ,
                                      @RequestParam(name = "unit", required = false) String unit,
                                      @RequestParam(name = "account", required = false) String account) {
        if (Strings.isEmpty(organ) && Strings.isEmpty(unit)) {
            BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
            String userId = loginUser.getUserId();
            KgoUser kgoUser = kgoUserRepository.findByUserId(userId);
            organ = kgoUser.getOrgan();
            unit = kgoUser.getUnit();
        }
        GeoUserQueryRs rs = geoUserQueryService.queryUsers(organ, unit, account);
        return rs;
    }

    /**
     * Geo 20221014 add_Jim
     */
    @RequestMapping(value = {"/getOrganAndUnit"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "取得機關和科室")
    public ResponseEntity<?> getOrganAndUnit() {
        if (ORGAN_AND_UNIT == null)
            organAndUnitHandler();
        return new ResponseEntity<>(ORGAN_AND_UNIT, HttpStatus.OK);
    }

    private void organAndUnitHandler() {
        JSONArray result = new JSONArray();
        List<KgoOrgan> kgoOrganList = kgoOrganRepository.findAll();
        kgoOrganList.forEach(kgoOrgan -> {
            if (Strings.isNotBlank(kgoOrgan.getOrganId()) && Strings.isNotBlank(kgoOrgan.getOrganName())) {
                List<KgoUnit> kgoUnitList = kgoUnitRepository.findAllByIdOrganId(kgoOrgan.getOrganId());
                if (!kgoUnitList.isEmpty()) {
                    JSONObject organ = new JSONObject();
                    organ.put("organId", kgoOrgan.getOrganId());
                    organ.put("organName", kgoOrgan.getOrganName());
                    JSONArray jsonArray = new JSONArray();
                    organ.put("units", jsonArray);
                    for (KgoUnit u : kgoUnitList) {
                        JSONObject unit = new JSONObject();
                        unit.put("unitId", u.getId().getUnitId());
                        unit.put("unitName", u.getUnitName());
                        jsonArray.put(unit);
                    }
                    result.put(organ);
                }else{
                    JSONObject organ = new JSONObject();
                    organ.put("organId", kgoOrgan.getOrganId());
                    organ.put("organName", kgoOrgan.getOrganName());
                    organ.put("units", new JSONArray());
                    result.put(organ);
                }
            }
        });
        ORGAN_AND_UNIT = result;
    }

    /**
     * Geo 20221104 add_Jim
     */
    @RequestMapping(value = {"/checkMemberShip/{twSsn}"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "市民科技會員確認")
    public GeoCityCoinMembershipRs checkMemberShipByTwSsn(@PathVariable("twSsn") String twSsn) throws Exception {
        GeoCityCoinMembershipRs rs = new GeoCityCoinMembershipRs();
        GeoCityCoinMembershipViewForm form = cityCoinAPIService.checkMemberShipByTWSsn(twSsn);
        rs.setData(form);
        return rs;
    }

    @RequestMapping(value = {"/checkMemberShipByLoginType"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "市民科技會員確認")
    public GeoCityCoinMembershipRs checkMemberShipByLoginType() throws Exception {
        GeoCityCoinMembershipRs rs = new GeoCityCoinMembershipRs();
        GeoCityCoinMembershipViewForm form = cityCoinAPIService.checkMemberShipByLoginType();
//        GeoCityCoinMembershipViewForm form = cityCoinAPIService.checkMemberShipByTWSsn("S224266937");
        form.setRealName(true);
        rs.setData(form);
        return rs;
    }
}
