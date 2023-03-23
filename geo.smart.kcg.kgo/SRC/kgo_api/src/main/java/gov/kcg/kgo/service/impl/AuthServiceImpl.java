package gov.kcg.kgo.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.kcg.kgo.common.LoginUserInfo;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.KgoSysMenuDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.CheckTypeEnum;
import gov.kcg.kgo.enums.backend.ParamSetEnum;
import gov.kcg.kgo.enums.common.SysType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentCheck;
import gov.kcg.kgo.geoentity.GeoKgoCityMember;
import gov.kcg.kgo.geoenum.GeoValidateType;
import gov.kcg.kgo.georepository.GeoCityMemberRepos;
import gov.kcg.kgo.georepository.GeoKgoAppointmentCheckRepository;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoVailDateLoginTypeViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoVailDataLoginTypeRs;
import gov.kcg.kgo.model.KgoCasesetCheck;
import gov.kcg.kgo.model.KgoParamSet;
import gov.kcg.kgo.repository.KgoCasesetCheckRepository;
import gov.kcg.kgo.repository.KgoParamSetRepository;
import gov.kcg.kgo.repository.KgoRoleFunctionRepository;
import gov.kcg.kgo.service.AuthService;
import gov.kcg.kgo.service.CallKcgCityApiService;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.impl.helper.AuthServiceHelper;
import gov.kcg.kgo.service.impl.helper.InternetApplyServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.util.helper.SsoUtilHelper;
import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.BackendGetSysMenuRs;
import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.bean.BackendGetSysMenuViewForm;
import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.bean.SysMenu;
import gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs.BackendGetLoginUserInfoRs;
import gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs.bean.BackendGetLoginUserInfoViewForm;
import gov.kcg.kgo.viewModel.backend.auth.login.rs.BackendLoginRs;
import gov.kcg.kgo.viewModel.backend.auth.login.rs.bean.BackendLoginAuthInfo;
import gov.kcg.kgo.viewModel.backend.auth.loginTest.rq.BackendLoginTestRq;
import gov.kcg.kgo.viewModel.common.bean.ApiLoginUserInfo;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rq.FrontendGetCityMemberInfoRq;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean.CityMemberInfo;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean.FrontendGetCityMemberInfoRs;
import gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.FrontendGetParamSetRs;
import gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.bean.FrontendGetParamSetViewForm;
import gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.bean.FrontendGetParamSetVo;
import gov.kcg.kgo.viewModel.frontend.auth.getUserInfo.rs.FrontendGetLoginUserInfoRs;
import gov.kcg.kgo.viewModel.frontend.auth.getUserInfo.rs.bean.FrontendGetLoginUserInfoViewForm;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.CityCardUserRs;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.FrontendGetValidateCodeRs;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.bean.CityCardUserViewForm;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.bean.FrontendGetValidateCodeViewForm;
import gov.kcg.kgo.viewModel.frontend.auth.login.rs.FrontendLoginResponse;
import gov.kcg.kgo.viewModel.frontend.auth.login.rs.bean.FrontendLoginAuthInfo;
import gov.kcg.kgo.viewModel.frontend.auth.loginTest.rq.FrontendLoginTestRq;
import gov.kcg.kgo.viewModel.sso.queryInfo.rs.base.QueryInfoRs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 前/後台 授權相關ServiceImpl.
 */
@Transactional(rollbackFor = Exception.class)
@Service("AuthService")
public class AuthServiceImpl extends KgoBaseServiceImpl implements AuthService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AuthServiceHelper helper = AuthServiceHelper.getInstance();

    @Autowired
    private KgoRoleFunctionRepository kgoRoleFunctionRepository;

    @Autowired
    private KgoParamSetRepository kgoParamSetRepository;

    @Autowired
    private CallKcgCityApiService callKcgCityApiService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    // 後台Mock登入密碼
    @Value("${backend.mockLogin.password}")
    private String backendMockLoginPassword;

    @Autowired
    InternetApplyServiceHelper internetApplyServiceHelper;
    @Autowired
    CityCoinAPIService cityCoinAPIService;
    @Autowired
    GeoCityMemberRepos geoCityMemberRepos;


    /**
     * 後台市府員工登入.
     *
     * @return the backend login response
     */
    @Override
    public BackendLoginRs doBackendLoginAction(String jwtToken) {
        LOGGER.info("AuthServiceImpl doBackendTestLoginAction...");
        BackendLoginRs rs = new BackendLoginRs();
        BackendLoginAuthInfo info = new BackendLoginAuthInfo();
        rs.setData(info);
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 後台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Login);
            if (StringUtils.isBlank(jwtToken)) {
                // 登入失敗
                throw new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
            }
            // 前/後臺登入 統一處裡方法
            LoginUserInfo loginUserInfo = SsoUtil.doBackendLogin(jwtToken);

            info.setLoginAuthTokenType(loginUserInfo.getLoginAuthTokenType().name());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("doBackendLoginAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            // 回饋活動名稱
            List<OperationColumn> memoList = new ArrayList<>();
            String userId = KgoUtil.getBackendLoginUser().getUserId();
            memoList.add(new OperationColumn("公務帳號", userId));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */
            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //doBackendLoginAction

    /**
     * GEO 20211115 add 非市府員工登入後臺
     *
     * @param jwtToken
     * @param exchange
     * @return
     */
    @Override
    public BackendLoginRs doBackendCityMemberLoginAction(String jwtToken, String exchange) {
        LOGGER.info("AuthServiceImpl doBackendCityMemberLoginAction...");
        BackendLoginRs rs = new BackendLoginRs();
        BackendLoginAuthInfo info = new BackendLoginAuthInfo();
        rs.setData(info);
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 後台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.CityMemberLogin);
            if (StringUtils.isBlank(jwtToken) && StringUtils.isBlank(exchange)) {
                // 登入失敗
                throw new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
            }
            // 前/後臺登入 統一處裡方法
            LoginUserInfo loginUserInfo = SsoUtil.doBackendCityMemberLogin(jwtToken, exchange);

            info.setLoginAuthTokenType(loginUserInfo.getLoginAuthTokenType().name());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("doBackendLoginAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            // 回饋活動名稱
            List<OperationColumn> memoList = new ArrayList<>();
            String userId = KgoUtil.getBackendLoginUser().getUserId();
            memoList.add(new OperationColumn("非市府員工登入 帳號", userId));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //doBackendCityMemberLoginAction

    /**
     * 測試登入 - 後台市府員工登入.
     *
     * @return the backend login response
     */
    @Override
    public <T extends QueryInfoRs> BackendLoginRs doBackendTestLoginAction(BackendLoginTestRq rq) {
        LOGGER.info("AuthServiceImpl doBackendTestLoginAction...");
        BackendLoginRs rs = new BackendLoginRs();
        BackendLoginAuthInfo info = new BackendLoginAuthInfo();
        rs.setData(info);
        SsoUtilHelper ssoHelper = SsoUtilHelper.getInstance();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 後台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Login);
            if ((helper.isDevMode() == false) && StringUtils.isNoneBlank(backendMockLoginPassword) && !rq.getPassword().equals(backendMockLoginPassword)) {
                error = new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
            }

            // 後台測試登入者 jsonStr.
            String userInfoJsonStr = getBackendLoginJsonStr(rq.getUserId());

            LoginAuthTokenType loginAuthTokenType = LoginAuthTokenType.BASIC;

            // 取得使用者登入物件.
            T rsLoginUserInfo = ssoHelper.getLoginUerInfoObject(loginAuthTokenType, userInfoJsonStr);

            // 設置後台市府員工登入 使用者資訊 (測試案例 jwtToken為空)
            SsoUtil.setLoginUserToSession("", userInfoJsonStr, loginAuthTokenType, rsLoginUserInfo, SysType.BACK, userInfoJsonStr);

            info.setLoginAuthTokenType(loginAuthTokenType.name());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            error = new KgoApiException("doBackendTestLoginAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            // 回饋活動名稱
            List<OperationColumn> memoList = new ArrayList<>();
            String userId = KgoUtil.getBackendLoginUser().getUserId();
            memoList.add(new OperationColumn("公務帳號", userId));
            memo.setMemoList(memoList);
//			super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //doBackendTestLoginAction

    /**
     * GEO 20211115 add 非市府員工登入後臺
     * 測試登入 - 後台市府員工登入.
     *
     * @return the backend login response
     */
    @Override
    public <T extends QueryInfoRs> BackendLoginRs doCityMemberBackendTestLoginAction(BackendLoginTestRq rq) {
        LOGGER.info("AuthServiceImpl doCityMemberBackendTestLoginAction...");
        BackendLoginRs rs = new BackendLoginRs();
        BackendLoginAuthInfo info = new BackendLoginAuthInfo();
        rs.setData(info);
        SsoUtilHelper ssoHelper = SsoUtilHelper.getInstance();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 後台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.CityMemberLogin);
            if ((helper.isDevMode() == false) && StringUtils.isNoneBlank(backendMockLoginPassword) && !rq.getPassword().equals(backendMockLoginPassword)) {
                error = new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
            }

            // 後台測試登入者 jsonStr.
            LoginAuthTokenType loginAuthTokenType = LoginAuthTokenType.getLoginAuthType(rq.getLoginType());
            String userInfoJsonStr = getBackendTestCityMemberLoginJsonStr(rq.getUserId(), loginAuthTokenType);

            // 取得使用者登入物件.
            T rsLoginUserInfo = ssoHelper.getLoginUerInfoObject(loginAuthTokenType, userInfoJsonStr);

            // 設置後台市府員工登入 使用者資訊 (測試案例 jwtToken為空)
            SsoUtil.setLoginUserToSession("", userInfoJsonStr, loginAuthTokenType, rsLoginUserInfo, SysType.BACK, userInfoJsonStr);

            info.setLoginAuthTokenType(loginAuthTokenType.name());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            error = new KgoApiException("doBackendTestLoginAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            // 回饋活動名稱
            List<OperationColumn> memoList = new ArrayList<>();
            String userId = KgoUtil.getBackendLoginUser().getUserId();
            memoList.add(new OperationColumn("公務帳號", userId));
            memo.setMemoList(memoList);
//			super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //doBackendTestLoginAction

    /**
     * 取得後台 登入使用者資訊 (給前端資訊).
     *
     * @return the login user info action
     */
    @Override
    public BackendGetLoginUserInfoRs getBackendLoginUserInfoAction() {
        LOGGER.info("AuthServiceImpl getBackendLoginUserInfoAction...");
        BackendGetLoginUserInfoRs rs = new BackendGetLoginUserInfoRs();
        BackendGetLoginUserInfoViewForm viewForm = new BackendGetLoginUserInfoViewForm();
        rs.setData(viewForm);
        try {
            // 取得後臺 登入使用者資訊.
            BackendLoginUserInfo beloginUser = KgoUtil.getBackendLoginUser();

            ApiLoginUserInfo userInfo = new ApiLoginUserInfo();

            // 用戶登入認證類型
            userInfo.setUserId(beloginUser.getUserId());
            userInfo.setLoginAuthTokenType(beloginUser.getLoginAuthTokenType().name());
            userInfo.setName(beloginUser.getName());
            userInfo.setOrganId(beloginUser.getOrgan());
            userInfo.setRoles(beloginUser.getRoles());

            Optional<KgoParamSet> kgoParamSet = kgoParamSetRepository.findById(ParamSetEnum.TO.getType());
            if (kgoParamSet.isPresent()) {
                String timeoutVal = kgoParamSet.get().getValue();
                viewForm.setTimeout(timeoutVal);
            }

            // TODO 其他使用者登入資訊
            viewForm.setUserInfo(userInfo);
        } catch (KgoApiException apiE) {
            //LOGGER.error(apiE.getMessage());
            throw new KgoApiException(apiE.getErrorResult());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("getLoginUserInfoAction error" + e.getMessage(), e);
        }
        return rs;
    } //getBackendLoginUserInfoAction

    /**
     * 後台 - 取得 角色系統選單.
     *
     * @return the gets the sys menu action
     */
    @Override
    public BackendGetSysMenuRs getGetSysMenuAction() {
        BackendGetSysMenuRs rs = new BackendGetSysMenuRs();
        BackendGetSysMenuViewForm viewForm = new BackendGetSysMenuViewForm();
        List<SysMenu> menuList = new ArrayList<>();
        rs.setData(viewForm);
        try {
            // 取得後臺 登入使用者資訊.
            BackendLoginUserInfo beloginUser = KgoUtil.getBackendLoginUser();
            List<String> roles = beloginUser.getRoles();
//			List<String> roles = new ArrayList<>();
//			roles.add(KgoRoleEnum.DEFAULT.getValue());
            if (CollectionUtils.isNotEmpty(roles)) {
                // 取得角色權限 系統選單.
                List<KgoSysMenuDto> sysMenuDtoList = kgoRoleFunctionRepository.getKgoSysMenu(roles);
                Map<Integer, KgoSysMenuDto> dtoMap = new HashMap<>();
                // 過濾相同Seq menu
                for (KgoSysMenuDto dto : sysMenuDtoList) {
                    if (dtoMap.get(dto.getSeq()) == null) {
                        dtoMap.put(dto.getSeq(), dto);
                    }
                }

                List<KgoSysMenuDto> filterSameSeqList = new ArrayList<KgoSysMenuDto>(dtoMap.values());

                // 取得系統樹狀選單
                menuList = helper.getMenuList(filterSameSeqList);
                viewForm.setMenuList(menuList);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("getGetSysMenuAction error" + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 後台 - 登出(紀錄操作log).
     */
    @Override
    public void doBackendLogoutAction() {
        LOGGER.info("AuthServiceImpl doBackendLogoutAction...");
        KgoApiException error = null;
        OperationApiMemo memo = null;
        BackendLoginUserInfo userInfo = (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY);
        try {
            LOGGER.info(">>>>> doBackEndLogoutAction ()");
            // 後台、、登出
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Logout);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("doBackEndLogoutAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            String userId = StringUtils.EMPTY;
            if (userInfo != null) {
                userId = userInfo.getUserId();
                memoList.add(new OperationColumn("公務帳號", userId));
                memo.setMemoList(memoList);
                super.saveOperationLog(memo);
            }
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }

        }
    } //doBackendLogoutAction

    /**
     * 前台 一般民眾登入. jwt 前臺登入 - 前臺SSO單登登入 jwt token exchange 前臺登入 - 與其他系統介接交換資訊
     *
     * @param jwtToken the jwt token
     * @return the frontend login response
     */
    @Override
    public FrontendLoginResponse doFrontendLoginAction(String jwtToken, String exchange) {
        LOGGER.info("AuthServiceImpl doFrontendLoginAction...");
        FrontendLoginResponse rs = new FrontendLoginResponse();
        FrontendLoginAuthInfo info = new FrontendLoginAuthInfo();
        rs.setData(info);
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 前台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.Login);
            LOGGER.info("jwtToken = {} , exchange = {}", jwtToken, exchange);

            if (StringUtils.isBlank(jwtToken) && StringUtils.isBlank(exchange)) {
                // 登入失敗
                throw new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
            }
            // 前臺登入 統一處裡方法
            LoginUserInfo loginUserInfo = SsoUtil.doFrontendLogin(jwtToken, exchange);

            // 設置登入方式類別
            LoginAuthTokenType loginAuthTokenType = loginUserInfo.getLoginAuthTokenType();
            info.setLoginAuthTokenType(loginAuthTokenType.name());
            // TODO 其他使用者資訊
            info.setSessionToken(request.getSession().getId());

        } catch (KgoApiException apiE) {
            apiE.printStackTrace();
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            error = new KgoApiException("doBackendLoginAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            String title = null;
            String value = null;
            FrontendLoginUserInfo feUser = null;
            String memberHashID = StringUtils.EMPTY;
            List<OperationColumn> memoList = new ArrayList<>();

            try {
                feUser = KgoUtil.getFrontendLoginUser();
                memberHashID = feUser.getMemberHashID();
                title = ObjectUtils.isNotEmpty(memberHashID) ? "(實名制)證號" : "(非實名制)取得的TOKEN";
                value = ObjectUtils.isNotEmpty(memberHashID) ? memberHashID : feUser.getJwtToken();
            } catch (Exception e) {
                LOGGER.info(">>>>> 前臺 儲存操作紀錄 使用者未登入");
            }

            memoList.add(new OperationColumn(title, value));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //doFrontendLoginAction

    @Override
    public FrontendLoginResponse doFrontendStaffLoginAction(String jwtToken) {
        FrontendLoginResponse rs = new FrontendLoginResponse();
        FrontendLoginAuthInfo info = new FrontendLoginAuthInfo();
        rs.setData(info);
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 後台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.StaffLogin);
            if (StringUtils.isBlank(jwtToken)) {
                // 登入失敗
                throw new KgoApiException(new ErrorResult(KgoCommonApiError.LOGIN_ERROR));
            }
            // 前/後臺登入 統一處裡方法
            LoginUserInfo loginUserInfo = SsoUtil.doFrontendStaffLogin(jwtToken);

            // 設置登入方式類別
            LoginAuthTokenType loginAuthTokenType = loginUserInfo.getLoginAuthTokenType();
            info.setLoginAuthTokenType(loginAuthTokenType.name());
            // TODO 其他使用者資訊
            info.setSessionToken(request.getSession().getId());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("doBackendLoginAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            String title = null;
            String value = null;
            FrontendLoginUserInfo feUser = null;
            String memberHashID = StringUtils.EMPTY;
            List<OperationColumn> memoList = new ArrayList<>();

            try {
                feUser = KgoUtil.getFrontendLoginUser();
                memberHashID = feUser.getMemberHashID();
                title = ObjectUtils.isNotEmpty(memberHashID) ? "(實名制)證號" : "(非實名制)取得的TOKEN";
                value = ObjectUtils.isNotEmpty(memberHashID) ? memberHashID : feUser.getJwtToken();
            } catch (Exception e) {
                LOGGER.info(">>>>> 前臺 儲存操作紀錄 使用者未登入");
            }

            memoList.add(new OperationColumn(title, value));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    }

    /**
     * 前台 - 取得登入使用者資訊..
     *
     * @return the frontend login user info action
     */
    @Override
    public FrontendGetLoginUserInfoRs getFrontendLoginUserInfoAction() {
        LOGGER.info("AuthServiceImpl getFrontendLoginUserInfoAction...");
        FrontendGetLoginUserInfoRs rs = new FrontendGetLoginUserInfoRs();
        FrontendGetLoginUserInfoViewForm viewForm = new FrontendGetLoginUserInfoViewForm();
        rs.setData(viewForm);
        try {
            // 取得前臺 登入使用者資訊.
            FrontendLoginUserInfo feloginUser = KgoUtil.getFrontendLoginUser();

            // 前端 - 登入使用者資訊
            ApiLoginUserInfo userInfo = new ApiLoginUserInfo();

            // 用戶登入認證類型
            userInfo.setLoginAuthTokenType(feloginUser.getLoginAuthTokenType().name());
            userInfo.setName(feloginUser.getName());
            userInfo.setExchange(feloginUser.getExchange());
            userInfo.setkCoinBalance(feloginUser.getkCoinBalance());

            Optional<KgoParamSet> kgoParamSet = kgoParamSetRepository.findById(ParamSetEnum.TO.getType());
            if (kgoParamSet.isPresent()) {
                String timeoutVal = kgoParamSet.get().getValue();
                viewForm.setTimeout(timeoutVal);
            }

            // TODO 其他使用者登入資訊
            viewForm.setUserInfo(userInfo);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("getLoginUserInfoAction error" + e.getMessage(), e);
        }
        return rs;
    } //getFrontendLoginUserInfoAction

    /**
     * 取得參數設定值.
     *
     * @return the param set action
     */
    @Override
    public FrontendGetParamSetRs getParamSetAction() {
        FrontendGetParamSetRs rs = new FrontendGetParamSetRs();
        FrontendGetParamSetViewForm viewForm = new FrontendGetParamSetViewForm();
        List<FrontendGetParamSetVo> voList = new ArrayList<>();
        viewForm.setParamSetList(voList);
        rs.setData(viewForm);
        try {
            List<KgoParamSet> paramSetList = kgoParamSetRepository.findAll();
            if (CollectionUtils.isNotEmpty(paramSetList)) {
                for (KgoParamSet ps : paramSetList) {
                    FrontendGetParamSetVo vo = new FrontendGetParamSetVo(ps.getType(), ps.getValue());
                    voList.add(vo);
                }
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("getParamSetAction error" + e.getMessage(), e);
        }
        return rs;
    }

    @Override
    public FrontendLoginResponse doFrontendTestLoginAction(FrontendLoginTestRq rq) {
        LOGGER.info("AuthServiceImpl doFrontendTestLoginAction...");
        FrontendLoginResponse rs = new FrontendLoginResponse();
        FrontendLoginAuthInfo info = new FrontendLoginAuthInfo();
        rs.setData(info);
        SsoUtilHelper ssoHelper = SsoUtilHelper.getInstance();
        try {

            LoginAuthTokenType loginAuthTokenType = LoginAuthTokenType.getLoginAuthType(rq.getLoginType());
            loginAuthTokenType = loginAuthTokenType == null ? LoginAuthTokenType.MOICA : loginAuthTokenType;
            // 前台測試登入者 jsonStr.
            String userInfoJsonStr = getFrontendTestLoginJsonStr(rq.getUserId(), loginAuthTokenType);

            // 取得使用者登入物件.
            QueryInfoRs rsLoginUserInfo = ssoHelper.getLoginUerInfoObject(loginAuthTokenType, userInfoJsonStr);

            // 設置前台市府員工登入 使用者資訊 (測試案例 jwtToken為空)
            SsoUtil.setLoginUserToSession("", userInfoJsonStr, loginAuthTokenType, rsLoginUserInfo, SysType.FRONT, userInfoJsonStr);

            info.setLoginAuthTokenType(loginAuthTokenType.name());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("doBackendTestLoginAction error" + e.getMessage(), e);
        }
        return rs;
    } //doFrontendTestLoginAction

    /**
     * (前臺登入) 呼叫市民科技 取得會員資訊, 確認是否為會員 (是會員回傳 身分證號、姓名 城市幣).
     *
     * @param rq the rq
     * @return the city member info
     */
    @Override
    public FrontendGetCityMemberInfoRs getCityMemberInfo(FrontendGetCityMemberInfoRq rq) {
        FrontendGetCityMemberInfoRs rs = null;
        try {
            if (ObjectUtils.isNotEmpty(rq)) {
                LOGGER.info(">>>>> 呼叫市民科技 確認是否為會員 rq: {}", JsonUtil.toJSONString(rq));
                GeoCityCoinMembershipViewForm geoCityCoinMembershipViewForm = cityCoinAPIService.checkMemberShipByTWSsn(rq.getHashId());
                if (!geoCityCoinMembershipViewForm.isMembership())
                    geoCityCoinMembershipViewForm = cityCoinAPIService.checkMemberShipByLoginType();
                if (geoCityCoinMembershipViewForm.isMembership()) {
                    CityMemberInfo cityMemberInfo = new CityMemberInfo();
                    cityMemberInfo.setAccount(geoCityCoinMembershipViewForm.getUuid());
                    cityMemberInfo.setIsRealName(String.valueOf(geoCityCoinMembershipViewForm.isRealName()));
                    Optional<GeoKgoCityMember> cityMember = geoCityMemberRepos.findById(UUID.fromString(geoCityCoinMembershipViewForm.getUuid()));
                    if (!cityMember.isPresent() && geoCityCoinMembershipViewForm.isRealName())
                        geoCityMemberRepos.save(new GeoKgoCityMember(geoCityCoinMembershipViewForm.getUuid().toLowerCase(), rq.getHashId(), geoCityCoinMembershipViewForm.isRealName()));
                    rs.setResult(cityMemberInfo);
                }
                LOGGER.info(">>>>> 呼叫市民科技 確認是否為會員 rs: {}", JsonUtil.toJSONString(rs));
            }
        } catch (Exception e) {
            LOGGER.error(">>>>> 取得市民科技會員有誤 : rq:{}", JsonUtil.toJSONString(rq), e);
            // LOGGER.error(e.getMessage(), e.fillInStackTrace());
        }
        return rs;
    }

    /**
     * 前台 - 登出(紀錄操作log).
     */
    @Override
    public void doFrontendLogoutAction() {
        LOGGER.info("AuthServiceImpl doFrontendLogoutAction...");
        KgoApiException error = null;
        OperationApiMemo memo = null;
        FrontendLoginUserInfo userInfo = null;
        try {
            userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
            LOGGER.info(">>>>> doFrontendLogoutAction ()");
            // 前台、、登出
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.Logout);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("doFrontendLogoutAction error" + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            if (userInfo != null) {
                String title = ObjectUtils.isNotEmpty(userInfo.getMemberHashID()) ? "(實名制)證號" : "(非實名制)取得的TOKEN";
                String value = ObjectUtils.isNotEmpty(userInfo.getMemberHashID()) ? userInfo.getMemberHashID() : userInfo.getJwtToken();
                memoList.add(new OperationColumn(title, value));
                memo.setMemoList(memoList);
                super.saveOperationLog(memo);
            }

            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }

        }
    } //doFrontendLogoutAction

    /**
     * 取得驗證碼數字(四碼).
     *
     * @return the validate code action
     */
    @Override
    public FrontendGetValidateCodeRs getValidateCodeAction() {
        LOGGER.info("AuthServiceImpl getValidateCodeAction...");
        FrontendGetValidateCodeRs rs = new FrontendGetValidateCodeRs();
        FrontendGetValidateCodeViewForm viewForm = new FrontendGetValidateCodeViewForm();
        rs.setData(viewForm);
        try {
            String randomCode = RandomUtil.getFourRandom();
            // 隨機產生四位數字.
            viewForm.setValidateCode(randomCode);
            // 驗證碼token
            viewForm.setValidateCodeToken(jwtUtil.genToken(randomCode));

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new KgoApiException("getValidateCodeAction error" + e.getMessage(), e);
        }
        return rs;
    } //getValidateCodeAction

    /**
     * 後台測試登入者 jsonStr(自然人憑證).
     *
     * @return the test login json str
     */
    private String getBackendLoginJsonStr(String userId) {
        LOGGER.info("AuthServiceImpl getBackendLoginJsonStr...");
        return "{" + "    \"ERROR_CODE\": \"0\"," + "    \"PUBLISH_INFO_LAST_UPDATE_TIME\": \"20201119173729+0800\"," + "    \"PUBLISH_INFO_LAST_UPDATE_TAG\": \"fb13f97565867386228a5464e5d83714\","
                + "    \"PUBLISH_INFO_CONTENT\": {" + "        \"KCG_USER_BASIC_INFO\": {" + "            \"APP_COMPANY_ID\": \"KCG\"," + "            \"APP_USER_LOGIN_ID\": \"" + userId + "\","
                + "            \"AUTH_DATE\": \"20201119173729\"," + "            \"APP_USER_TW_SSN\": \"\"," + "            \"AUTH_METHOD\": [" + "                \"pass_idpass_auth\""
                + "            ]" + "        }" + "    }" + "}";
    } //getBackendLoginJsonStr

    /**
     * GEO 20211115 add 非市府員工登入後臺
     * 後台測試登入者 jsonStr
     *
     * @return the test login json str
     */
    private String getBackendTestCityMemberLoginJsonStr(String userId, LoginAuthTokenType loginAuthTokenType) {
        LOGGER.info("AuthServiceImpl getBackendTestCityMemberLoginJsonStr...");
        String loginInfoJsonStr = "";
        switch (loginAuthTokenType) {
            case BASIC:
                loginInfoJsonStr = "\"KCG_USER_BASIC_INFO\": {\n" +
                        "      \"APP_COMPANY_ID\": \"kcg\",\n" +
                        "      \"APP_USER_LOGIN_ID\": \"jacky@kcg.gov.tw\",\n" +
                        "      \"AUTH_FROM_ADDRESS\": \"101.12.240.31\",\n" +
                        "      \"AUTH_DATE\": \"20160316083748+0800\",\n" +
                        "      \"AUTH_TYPE\": [\n" +
                        "        \"pass_moica_auth\"\n" +
                        "      ],\n" +
                        "      \"AUTH_EXTRA_INFO\": {\n" +
                        "        \"MOICA_USER_NAME\": \"李永正\",\n" +
                        "        \"MOICA_CERT_SN\": \"E23029C0A56A1206E23029C0A56A1206\"\n" +
                        "      }\n" +
                        "    }";
                break;
            case MOICA:
                loginInfoJsonStr = "\"KCG_MOICA_CARD_INFO\": {\"AUTH_FROM_ADDRESS\":\"61.216.190.109\",\"AUTH_DATE\": \"20201023144029+0800\", \"MOICA_USER_TW_SSN\": \"" + userId
                        + "\",\"MOICA_USER_NAME\": \"後臺測試人員MOICA\",\"MOICA_CERT_SN\": \"367A7C01B2CBB289DC5338AA5FD9C54E\"}";
                break;
            case HCA:
                loginInfoJsonStr = "\"KCG_HCA_CARD_INFO\": {\"AUTH_FROM_ADDRESS\": \"101.12.240.31\",\"AUTH_DATE\": \"20160316083748+0800\", \"HCA_USER_TW_SSN\": \"" + userId
                        + "\",\"HCA_USER_NAME\": \"後臺測試人員HCA\",\"HCA_CARD_NUMBER\": \"0000123456789\"}";
                break;
            case EGOV:
                loginInfoJsonStr = "\"KCG_EGOV_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"EGOV_AUTH_TYPE\":\"自然人憑證 \",\"EGOV_USER_ACCOUNT\":\"" + userId
                        + "\", \n" + "\"EGOV_USER_CN\":\"後臺測試人員EGOV\",\"EGOV_USER_UID\":\"" + userId
                        + "\",\"EGOV_USER_CERKEY_STR\":\"\",\"EGOV_USER_MAIL\":\"dominic.yishinglee@gmail.com\",\"EGOV_USER_TYPE\":\"民眾 \"}";
                break;
            case GOOGLE:
                loginInfoJsonStr = "\"KCG_GOOGLE_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"GOOGLE_USER_ACCOUNT\":\"" + userId
                        + "\",\"GOOGLE_USER_NAME\":\"後臺測試人員GOOGLE\"}";
                break;
            case FACEBOOK:
                loginInfoJsonStr = "\"KCG_FACEBOOK_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"FACEBOOK_USER_ACCOUNT\":\"" + userId
                        + "\",\"FACEBOOK_USER_NAME\":\"後臺測試人員FACEBOOK\"}";
                break;
            case LINE:
                loginInfoJsonStr = "\"KCG_LINE_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"LINE_USER_ID\":\"" + userId
                        + "\",\"LINE_USER_DISPLAYNAME\":\"後臺測試人員LINE\"}";
                break;
            case TW_FIDO:
                loginInfoJsonStr = "\"KCG_TWFIDO_SSO_INFO\": {\n" +
                        "\"AUTH_FROM_ADDRESS\": \"101.12.240.31\",\n" +
                        "\"AUTH_DATE\": \"20160316083748+0800\",\n" +
                        "\"TWFIDO_USER_TW_SSN\": \"A987654321\"}";
                break;
            default:
                loginInfoJsonStr = "\"KCG_MOICA_CARD_INFO\": {\"AUTH_FROM_ADDRESS\":\"61.216.190.109\",\"AUTH_DATE\": \"20201023144029+0800\",\"MOICA_USER_TW_SSN\": \"" + userId
                        + "\",\"MOICA_USER_NAME\": \"後臺測試人員\",\"MOICA_CERT_SN\": \"367A7C01B2CBB289DC5338AA5FD9C54E\"}";
                break;
        }

        return String.format(
                "{\"ERROR_CODE\": \"0\",\"PUBLISH_INFO_LAST_UPDATE_TIME\": \"2014-04-01 16:03:33\",\"PUBLISH_INFO_LAST_UPDATE_TAG\": \"8b944f1b9aa368351452f561bb52c5c3\",\"PUBLISH_INFO_CONTENT\": {%s}}",
                loginInfoJsonStr);
    } //getBackendTestCityMemberLoginJsonStr

    /**
     * 前台測試登入者 jsonStr(自然人憑證).
     *
     * @return the test login json str
     */
    private String getFrontendTestLoginJsonStr(String userId, LoginAuthTokenType loginAuthTokenType) {
        LOGGER.info("AuthServiceImpl getFrontendTestLoginJsonStr...");
//		return "{" + 
//			   "	\"ERROR_CODE\": \"0\"," +
//			   "	\"PUBLISH_INFO_LAST_UPDATE_TIME\": \"20201023144030+0800\"," + 
//			   "	\"PUBLISH_INFO_LAST_UPDATE_TAG\": \"9e090c5d788402f705b6cf7b347acf07\","+ 
//			   "	\"PUBLISH_INFO_CONTENT\": {" + 
//			   "		\"KCG_MOICA_CARD_INFO\": {" + 
//			   "			\"AUTH_FROM_ADDRESS\": \"61.216.190.109\"," + 
//			   "			\"AUTH_DATE\": \"20201023144029+0800\"," + 
//			   "			\"MOICA_USER_TW_SSN\": \"" + userId + "\"," + 
//			   "			\"MOICA_USER_NAME\": \"前臺測試人員\"," + 
//			   "			\"MOICA_CERT_SN\": \"367A7C01B2CBB289DC5338AA5FD9C54E\""+ 
//			   "		}" + 
//			   "	}" + 
//			   "}";
        String loginInfoJsonStr = "";
        switch (loginAuthTokenType) {
            case BASIC:
                loginInfoJsonStr = "\"KCG_USER_BASIC_INFO\": {\n" +
                        "      \"APP_COMPANY_ID\": \"kcg\",\n" +
                        "      \"APP_USER_LOGIN_ID\": \"jacky@kcg.gov.tw\",\n" +
                        "      \"AUTH_FROM_ADDRESS\": \"101.12.240.31\",\n" +
                        "      \"AUTH_DATE\": \"20160316083748+0800\",\n" +
                        "      \"AUTH_TYPE\": [\n" +
                        "        \"pass_moica_auth\"\n" +
                        "      ],\n" +
                        "      \"AUTH_EXTRA_INFO\": {\n" +
                        "        \"MOICA_USER_NAME\": \"李永正\",\n" +
                        "        \"MOICA_CERT_SN\": \"E23029C0A56A1206E23029C0A56A1206\"\n" +
                        "      }\n" +
                        "    }";
                break;
            case MOICA:
                loginInfoJsonStr = "\"KCG_MOICA_CARD_INFO\": {\"AUTH_FROM_ADDRESS\":\"61.216.190.109\",\"AUTH_DATE\": \"20201023144029+0800\", \"MOICA_USER_TW_SSN\": \"" + userId
                        + "\",\"MOICA_USER_NAME\": \"前臺測試人員MOICA\",\"MOICA_CERT_SN\": \"367A7C01B2CBB289DC5338AA5FD9C54E\"}";
                break;
            case HCA:
                loginInfoJsonStr = "\"KCG_HCA_CARD_INFO\": {\"AUTH_FROM_ADDRESS\": \"101.12.240.31\",\"AUTH_DATE\": \"20160316083748+0800\", \"HCA_USER_TW_SSN\": \"" + userId
                        + "\",\"HCA_USER_NAME\": \"前臺測試人員HCA\",\"HCA_CARD_NUMBER\": \"0000123456789\"}";
                break;
            case EGOV:
                loginInfoJsonStr = "\"KCG_EGOV_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"EGOV_AUTH_TYPE\":\"自然人憑證 \",\"EGOV_USER_ACCOUNT\":\"" + userId
                        + "\", \n" + "\"EGOV_USER_CN\":\"前臺測試人員EGOV\",\"EGOV_USER_UID\":\"" + userId
                        + "\",\"EGOV_USER_CERKEY_STR\":\"\",\"EGOV_USER_MAIL\":\"dominic.yishinglee@gmail.com\",\"EGOV_USER_TYPE\":\"民眾 \"}";
                break;
            case GOOGLE:
                loginInfoJsonStr = "\"KCG_GOOGLE_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"GOOGLE_USER_ACCOUNT\":\"" + userId
                        + "\",\"GOOGLE_USER_NAME\":\"前臺測試人員GOOGLE\"}";
                break;
            case FACEBOOK:
                loginInfoJsonStr = "\"KCG_FACEBOOK_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"FACEBOOK_USER_ACCOUNT\":\"" + userId
                        + "\",\"FACEBOOK_USER_NAME\":\"前臺測試人員FACEBOOK\"}";
                break;
            case LINE:
                loginInfoJsonStr = "\"KCG_LINE_SSO_INFO\":{\"AUTH_FROM_ADDRESS\":\"101.12.240.31\",\"AUTH_DATE\":\"20160316083748+0800\",\"LINE_USER_ID\":\"" + userId
                        + "\",\"LINE_USER_DISPLAYNAME\":\"前臺測試人員LINE\"}";
                break;
            case TW_FIDO:
                loginInfoJsonStr = "\"KCG_TWFIDO_SSO_INFO\": {\n" +
                        "\"AUTH_FROM_ADDRESS\": \"101.12.240.31\",\n" +
                        "\"AUTH_DATE\": \"20160316083748+0800\",\n" +
                        "\"TWFIDO_USER_TW_SSN\": \"A987654321\"}";
                break;
            default:
                loginInfoJsonStr = "\"KCG_MOICA_CARD_INFO\": {\"AUTH_FROM_ADDRESS\":\"61.216.190.109\",\"AUTH_DATE\": \"20201023144029+0800\",\"MOICA_USER_TW_SSN\": \"" + userId
                        + "\",\"MOICA_USER_NAME\": \"前臺測試人員\",\"MOICA_CERT_SN\": \"367A7C01B2CBB289DC5338AA5FD9C54E\"}";
                break;
        }

        return String.format(
                "{\"ERROR_CODE\": \"0\",\"PUBLISH_INFO_LAST_UPDATE_TIME\": \"2014-04-01 16:03:33\",\"PUBLISH_INFO_LAST_UPDATE_TAG\": \"8b944f1b9aa368351452f561bb52c5c3\",\"PUBLISH_INFO_CONTENT\": {%s}}",
                loginInfoJsonStr);
    } //getFrontendTestLoginJsonStr

    /**
     * *市民卡界接至本站.
     */
    @Override
    public CityCardUserRs getCityCardUser(String access_token) {
        final String clientId = SpringUtil.getProperty("citycard.connection.clienid");
        final String clientSecret = SpringUtil.getProperty("citycard.connection.clientsecret");
        CityCardUserRs rs = new CityCardUserRs();
        CityCardUserViewForm viewForm = new CityCardUserViewForm();
        String responseJson = "unknow user";
        LOGGER.info("access_token--->" + access_token);
        LOGGER.info("clientId--->" + clientId);
        LOGGER.info("clientSecret--->" + clientSecret);

        try {
            URL url = new URL(SpringUtil.getProperty("citycard.connection.url"));
            LOGGER.info("url--->" + url);
            String authEncoded = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
            String param = "access_token=" + access_token;

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Authorization", "Basic " + authEncoded);
            httpConn.connect();

            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();

            if (HttpURLConnection.HTTP_OK == httpConn.getResponseCode()) {
                StringBuffer sb = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                LOGGER.info("--->" + sb.toString());

                responseJson = sb.toString();
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                viewForm = mapper.readValue(responseJson, CityCardUserViewForm.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(">>> getCityCardUser: " + responseJson);
        }
        rs.setData(viewForm);
        return rs;

    }

    /**
     * 服務申辦-驗證符合登入身份
     *
     * @param id
     * @return the validate code action
     */
    @Override
    public GeoVailDataLoginTypeRs getValidateLoginTypeAction(String id, int vailData) {
        GeoVailDataLoginTypeRs rs = new GeoVailDataLoginTypeRs();
        GeoVailDateLoginTypeViewForm viewForm = new GeoVailDateLoginTypeViewForm();
        LOGGER.info("getValidateLoginTypeAction vailData=" + vailData);
        try {
            //獲取該服務身份驗證
            viewForm.setLogin(false); //預設不可登入
            FrontendLoginUserInfo userInfo = getFrontendLoginUser();
            List<CheckBox> identityVerifyCheckBox = new ArrayList<>();
            //Mydata查詢 只能實名制方式
            if (vailData == GeoValidateType.MY_DATA_QUERY.getValidateTypeCode()) {
                //先預設需要實名
                identityVerifyCheckBox = getVerifyCheckBox(id, vailData);
                for (CheckBox c : identityVerifyCheckBox) {
                    if (c.getValue().equals(LoginAuthTokenType.MOICA.getType()) || c.getValue().equals(LoginAuthTokenType.TW_FIDO.getType())) {
                        c.setSelected(true);
                    } //if (c.getValue().equals
                } // for (CheckBox c: identityVerifyCheckBox)
                if (userInfo != null) {
                    if (userInfo.getLoginAuthTokenType() == LoginAuthTokenType.MOICA || (userInfo.getLoginAuthTokenType() == LoginAuthTokenType.TW_FIDO)) {
                        viewForm.setLogin(true);
                    }
                    for (CheckBox c : identityVerifyCheckBox) {
                        if (c.getValue().equals(LoginAuthTokenType.MOICA.getType()) || c.getValue().equals(LoginAuthTokenType.TW_FIDO.getType())) {
                            c.setSelected(false);
                        } //if (c.getValue().equals
                    } //for (CheckBox c: identityVerifyCheckBox)
                }
            } else {
                //獲取設定的驗證方式
                identityVerifyCheckBox = getVerifyCheckBox(id, vailData);

                //有設定驗證方式時的判斷
                if (userInfo != null) {
                    for (CheckBox c : identityVerifyCheckBox) {
                        if (c.getValue().equals(userInfo.getLoginAuthTokenType().getType())) {
                            if (c.getSelected()) {
                                viewForm.setLogin(true);
                                break;
                            } //if (c.getSelected())
                        } //if (c.getValue().equals

                        if (c.getValue().equals("NAN")) {
                            if (c.getSelected()) {
                                viewForm.setLogin(true);
                            } //if (c.getSelected())
                        } //if (c.getValue().equals("NAN")

                    } // for (CheckBox c: identityVerifyCheckBox)
                } else {
                    for (CheckBox c : identityVerifyCheckBox) {
                        if (c.getValue().equals("NAN")) {
                            if (c.getSelected()) {
                                viewForm.setLogin(true);
                                break;
                            } //if (c.getSelected())
                        } //if (c.getValue().equals
                    } // for (CheckBox c: identityVerifyCheckBox)
                } //if (userInfo!=null)

                // 未設定驗證的方式的判斷
                Boolean isDefault = true;
                for (CheckBox c : identityVerifyCheckBox) {
                    if (c.getSelected()) {
                        isDefault = false;
                    } //if ( c.getSelected())
                } //for (CheckBox c: identityVerifyCheckBox)

                if (isDefault) {
                    LOGGER.info("getValidateLoginTypeAction if isDefault=" + isDefault);
                    viewForm.setLogin(true);
                    for (CheckBox c : identityVerifyCheckBox) {
                        if (c.getLabel().equals("免驗證")) {
                            c.setSelected(true);
                        } //if ( c.getSelected())
                    } //for (CheckBox c: identityVerifyCheckBox)
                } //if (isDefault)
            }
            rs.setData(viewForm);
            viewForm.setIdentityVerifyCheckBox(identityVerifyCheckBox);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("bidServiceMenuHome error " + e.getMessage(), e);
        }
        return rs;
    }//getValidateLoginTypeAction

    //身份驗證
    public List<CheckBox> getVerifyCheckBox(String id, int vailDataType) {
        List<String> casesetChecks = new ArrayList<>();
        if (vailDataType == GeoValidateType.APPOINTMENT.getValidateTypeCode()) {
            GeoKgoAppointmentCheckRepository geoKgoAppointmentCheckRepository = SpringUtil.getDao(GeoKgoAppointmentCheckRepository.class);
            List<GeoKgoAppointmentCheck> geoKgoAppointmentCheckList = geoKgoAppointmentCheckRepository.findAllByIdAppointmentMainId(id);
            casesetChecks = geoKgoAppointmentCheckList.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType()).collect(Collectors.toList());
        } else if (vailDataType == GeoValidateType.CASE_SET.getValidateTypeCode()) {
            KgoCasesetCheckRepository kgoCasesetCheckRepository = SpringUtil.getDao(KgoCasesetCheckRepository.class);
            List<KgoCasesetCheck> kgoCasesetChecks = kgoCasesetCheckRepository.findAllByIdCaseSetId(id);
            casesetChecks = kgoCasesetChecks.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType()).collect(Collectors.toList());
        } //if (vailDataType==GeoValidateType.APPOINTMENT.getValidateTypeCode())

        List<CheckBox> identityVerifyCheckBox = new LinkedList<CheckBox>();
        CheckBox checkBox = null;
        for (CheckTypeEnum checkType : CheckTypeEnum.values()) {
            if (casesetChecks.contains(checkType.getValue())) {
                checkBox = internetApplyServiceHelper.getCheckBox(checkType.getLabel(), checkType.getValue(), true);
            } else {
                checkBox = internetApplyServiceHelper.getCheckBox(checkType.getLabel(), checkType.getValue());
            } //if (casesetChecks.contains(checkType.getValue()))
            identityVerifyCheckBox.add(checkBox);
        } //for (CheckTypeEnum checkType : CheckTypeEnum.values())
        return identityVerifyCheckBox;
    } //getVerifyCheckBox

    //獲取前臺登入者資訊
    public static FrontendLoginUserInfo getFrontendLoginUser() {
        LOGGER.info("KgoUtil getFrontendLoginUser...");
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
            return userInfo;
        } catch (Exception e) {
            // 使用者未登入.
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
        } //getFrontendLoginUser
    } //getFrontendLoginUser
}
