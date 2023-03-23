package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoFrontendUser;
import gov.kcg.kgo.geomodel.GeoKgoFrontendUserModel;
import gov.kcg.kgo.georepository.GeoKgoFrontendUserRepository;
import gov.kcg.kgo.georepository.custom.GeoAgentReposCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoFrontendUserRegisterRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoFrontendUserInfoQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoFrontendUserInfoQueryViewForm;
import gov.kcg.kgo.util.KgoUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * GEO 20211113 add 前台使用者註冊
 * 前台-使用者帳號 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoFrontendUserService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoFrontendUserService.class);

    @Autowired
    private GeoAgentReposCustom geoAgentReposCustom;

    @Autowired
    private GeoKgoFrontendUserRepository geoKgoFrontendUserRepository;
    public GeoFrontendUserInfoQueryRs queryInfo() {
        GeoFrontendUserInfoQueryRs rs = new GeoFrontendUserInfoQueryRs();
        GeoFrontendUserInfoQueryViewForm viewForm = new GeoFrontendUserInfoQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoFrontendUserModel model = new GeoKgoFrontendUserModel();
//            boolean isRegister = false;
            boolean isLogin = false;
            FrontendLoginUserInfo loginUserInfo = KgoUtil.getFrontendLoginUser(false);
            if (loginUserInfo != null) {
                isLogin = true;
                model.setUserLoginType(loginUserInfo.getLoginAuthTokenType().getTypeName());
                model.setName(Strings.isNotBlank(loginUserInfo.getName())?loginUserInfo.getName():Strings.EMPTY);
                model.setIdentity(Strings.isNotBlank(loginUserInfo.getTwSSn())?loginUserInfo.getTwSSn():Strings.EMPTY);
//                String loginType = loginUserInfo.getLoginAuthTokenType().getType();
//                String validateInfo = getValidateInfo(loginUserInfo);
//                List<GeoKgoFrontendUser> user = geoKgoFrontendUserRepository.findByUserLoginTypeAndUserValidate(loginType, validateInfo);
//                if (user != null && user.size() == 1) {
//                    isRegister = true;
//                    model = GeoKgoFrontendUserModel.changeToModel(user.get(0));
//                    model.setUserLoginType(LoginAuthTokenType.getLoginAuthType(loginType).getTypeName());
//                }
            } //if (loginUserInfo != null)
            viewForm.setUser(model);
            viewForm.setLogin(isLogin);
//            viewForm.setRegister(isRegister);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //queryInfo

    private String getValidateInfo(FrontendLoginUserInfo loginUserInfo) {
        String str = null;
        switch (loginUserInfo.getLoginAuthTokenType()) {
            case BASIC:
                str = loginUserInfo.getKcgUserBasicInfo().getAppUserLoginId() +
                        loginUserInfo.getKcgUserBasicInfo().getAppUserTwSSn();
                break;
            case HCA:
                str = loginUserInfo.getKcgHcaCardSsoInfo().getHcaUserName() +
                        loginUserInfo.getKcgHcaCardSsoInfo().getHcaUserTwSsn();
                break;
            case EGOV:
                str = loginUserInfo.getKcgEgovInfo().getEgovUserCn() +
                        loginUserInfo.getKcgEgovInfo().getEgovUserUid();
                break;
            case LINE:
                str = loginUserInfo.getLineInfo().getLineUserId();
                break;
            case MOICA:
                str = loginUserInfo.getKcgMoicaCardInfo().getMoicaUserName() +
                        loginUserInfo.getKcgMoicaCardInfo().getMoicaUserTwSsn();
                break;
            case GOOGLE:
                str = loginUserInfo.getKcgGoogleSsoInfo().getGoogleUserAccount();
                break;
            case TW_FIDO:
                str = loginUserInfo.getKcgTwFidoSsoInfo().getTwfidoUserTwSSn();
                break;
            case FACEBOOK:
                str = loginUserInfo.getKcgFacebookSsoInfo().getFacebookUserAccount();
                break;
        }
        return str;
    }

    public GeoFrontendUserInfoQueryRs editUser(GeoFrontendUserRegisterRq rq) {
        GeoFrontendUserInfoQueryRs rs = new GeoFrontendUserInfoQueryRs();
        GeoFrontendUserInfoQueryViewForm viewForm = new GeoFrontendUserInfoQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoFrontendUserModel model = new GeoKgoFrontendUserModel();
            boolean isRegister = false;
            boolean isLogin = true;
            FrontendLoginUserInfo loginUserInfo = KgoUtil.getFrontendLoginUser();;
            if (StringUtils.isBlank(rq.getEmail()) || StringUtils.isBlank(rq.getIdentity()) ||
                    StringUtils.isBlank(rq.getName()) || StringUtils.isBlank(rq.getPhone()) || loginUserInfo == null) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }
            String loginType = loginUserInfo.getLoginAuthTokenType().getType();
            String validateInfo = getValidateInfo(loginUserInfo);
            GeoKgoFrontendUser user = geoKgoFrontendUserRepository.findByUserLoginTypeAndUserValidateAndUserId(loginType, validateInfo,rq.getUserId());
            if (user == null ) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.FAIL_TO_EDIT));
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            GeoKgoFrontendUser entity = user;
            entity.setEmail(rq.getEmail());
            entity.setUserIdentity(rq.getIdentity());
            entity.setPhone(rq.getPhone());
            entity.setName(rq.getName());
            entity.setEditUser(loginUserInfo.getName());
            entity.setEditTime(now);
            geoKgoFrontendUserRepository.save(entity);
            model = GeoKgoFrontendUserModel.changeToModel(entity);
            model.setUserLoginType(LoginAuthTokenType.getLoginAuthType(loginType).getTypeName());
            viewForm.setUser(model);
            viewForm.setLogin(isLogin);
            viewForm.setRegister(isRegister);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoFrontEndApiError.FAIL_TO_SAVE), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //insertUser

    public GeoFrontendUserInfoQueryRs insertUser(GeoFrontendUserRegisterRq rq) {
        GeoFrontendUserInfoQueryRs rs = new GeoFrontendUserInfoQueryRs();
        GeoFrontendUserInfoQueryViewForm viewForm = new GeoFrontendUserInfoQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoKgoFrontendUserModel model = new GeoKgoFrontendUserModel();
            boolean isRegister = false;
            boolean isLogin = true;
            FrontendLoginUserInfo loginUserInfo = KgoUtil.getFrontendLoginUser();;
            if (StringUtils.isBlank(rq.getEmail()) || StringUtils.isBlank(rq.getIdentity()) ||
                    StringUtils.isBlank(rq.getName()) || StringUtils.isBlank(rq.getPhone())) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }
            String loginType = loginUserInfo.getLoginAuthTokenType().getType();
            String validateInfo = getValidateInfo(loginUserInfo);
            List<GeoKgoFrontendUser> user = geoKgoFrontendUserRepository.findByUserLoginTypeAndUserValidate(loginType, validateInfo);
            if (user != null && user.size() == 1) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.FAIL_TO_ADD));
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            GeoKgoFrontendUser entity = new GeoKgoFrontendUser();
            entity.setUserLoginType(loginUserInfo.getLoginAuthTokenType().getType());
            entity.setUserValidate(validateInfo);
            entity.setEmail(rq.getEmail());
            entity.setUserIdentity(rq.getIdentity());
            entity.setPhone(rq.getPhone());
            entity.setName(rq.getName());
            entity.setUserId(geoAgentReposCustom.getNextTableId(
                    GeoStringUtil.FRONTEND_USER_PREFIX_CHAR, "GEO_KGO_FRONTEND_USER", "UserId"));
            entity.setEditUser(loginUserInfo.getName());
            entity.setEditTime(now);
            geoKgoFrontendUserRepository.save(entity);
            model = GeoKgoFrontendUserModel.changeToModel(entity);
            model.setUserLoginType(LoginAuthTokenType.getLoginAuthType(loginType).getTypeName());
            viewForm.setUser(model);
            viewForm.setLogin(isLogin);
            viewForm.setRegister(isRegister);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoFrontEndApiError.FAIL_TO_SAVE), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //editUser
}
