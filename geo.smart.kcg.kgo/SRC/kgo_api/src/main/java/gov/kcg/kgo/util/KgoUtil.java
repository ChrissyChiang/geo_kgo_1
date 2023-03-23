package gov.kcg.kgo.util;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.constant.SecurityConstant;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumn;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnPK;
import gov.kcg.kgo.georepository.GeoKgoAppointmentColumnRepository;
import gov.kcg.kgo.georepository.custom.GeoKgoAppointmentMainReposCustom;
import gov.kcg.kgo.georepository.custom.GeoKgoCasesetOrganGroupReposCustom;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.impl.AuthServiceImpl;
import gov.kcg.kgo.service.impl.CacheServiceImpl;
import gov.kcg.kgo.viewModel.backend.auth.loginTest.rq.BackendLoginTestRq;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author TPIuser
 */
public class KgoUtil {
    public static final Integer DEFAULT_VERSION_NUMBER = 1;
    public static final Integer DEFAULT_ORDER_NUMBER = 1;
    public static final String SPILIT_DOT = "\\.";
    public static final String VERSION_DOT = ".";
    public static final String MYDATA_BASIC_GROUP_NAME = "基本設定";
    private static final String CASESET_ID_PREFIX_CHAR = "S";

    /**
     * 附件上傳路徑替換參數名稱-CASESET_ID
     **/
    public final static String ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASESET_ID = "{CaseSetId}";

    /**
     * 附件上傳路徑替換參數名稱-CASESET_TYPE
     **/
    public final static String ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASESET_TYPE = "{CaseSetType}";

    /**
     * 附件上傳路徑替換參數名稱-CASE_ID
     **/
    public final static String ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASE_ID = "{CaseId}";

    /**
     * 附件上傳路徑替換參數名稱-functionCode
     **/
    public final static String ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_FUNCTION_CODE = "{FunctionCode}";

    /**
     * 附件上傳路徑替換參數名稱-FSEQ
     **/
    public final static String ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_FSEQ = "{fSeq}";

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KgoUtil.class);

    @Autowired
    private GeoKgoAppointmentColumnRepository geoKgoAppointmentColumnRepository;

    /**
     * 取得後臺 登入使用者資訊.
     *
     * @return the back end login user
     */
    public static BackendLoginUserInfo getBackendLoginUser() {
        LOGGER.info("KgoUtil getBackendLoginUser..."); //登入階段這裡會呼叫好幾次(大概七八次)
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


            //GEO 20210814 add for testing
            Enumeration headerNames = request.getHeaderNames();
            int k = 0;
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                LOGGER.info("KgoUtil getBackendLoginUser header k/key/value: " + k + "/" + key + " / " + value);
                k++;
            }
            Enumeration<String> sessionNames = request.getSession().getAttributeNames();
            int i = 0;
            while (sessionNames.hasMoreElements()) {
                LOGGER.info("KgoUtil getBackendLoginUser session i/key: " + i + "/" + sessionNames.nextElement());
                i++;
            }

            BackendLoginUserInfo userInfo = (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY);

            if (userInfo == null) {
                // 使用者未登入.
                    LOGGER.info("userInfo == null 塞假資料");
                    userInfo = new BackendLoginUserInfo();
                    userInfo.setUserId("chance");
                    userInfo.setOrgan("397140100P");
                    userInfo.setName("朱科安");
                    userInfo.setUnit("A890");
                    List<String> roles = new ArrayList<>();
                    roles.add("ADMIN");
                    roles.add("CASE_M");
                    roles.add("UNIT_A");
                    roles.add("UNIT_M");
                    roles.add("UNIT_U");
                    roles.add(SecurityConstant.BACKEND);
                    userInfo.setRoles(roles);
                    userInfo.setJwtToken("");
                    userInfo.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
                    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    Date date = new Date();
                    sdFormat.format(date);
                    userInfo.setLoginTime(date);

            }
            return userInfo;
        } catch (Exception e) {
            // 使用者未登入.
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
        }
    } //getBackendLoginUser

    /**
     * 取得前台臺 登入使用者資訊.
     *
     * @return the back end login user
     */
    public static FrontendLoginUserInfo getFrontendLoginUser() {
        LOGGER.info("KgoUtil getFrontendLoginUser...");
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
            if (userInfo == null) {
                // 使用者未登入.
                throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
            }
            return userInfo;
        } catch (Exception e) {
            // 使用者未登入.
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
        }
    } //getFrontendLoginUser

    public static FrontendLoginUserInfo getFrontendLoginUser(boolean isException) {
        try {
            return getFrontendLoginUser();
        } catch (KgoApiException e) {
            if (isException) {
                throw e;
            } else {
                return null;
            }
        }
    }

    /**
     * 前台臺 登入使用者是否為自然人憑證登入(一般民眾).
     *
     * @return true, if is moica login
     */
    public static boolean isMoicaLogin() {
        return LoginAuthTokenType.MOICA.equals(getFrontendLoginUser().getLoginAuthTokenType());
    }

    /**
     * 測試登入 - 後台市府員工登入.
     *
     * @param userId the user id
     */
    public static void backendTestLoginUser(String userId) {
        AuthServiceImpl authServiceImpl = (AuthServiceImpl) SpringUtil.getBean("AuthService");
        BackendLoginTestRq rq = new BackendLoginTestRq();
        rq.setUserId(userId);
        // 測試登入 - 後台市府員工登入.
        authServiceImpl.doBackendTestLoginAction(rq);
    }

    /**
     * 依案件申辦類型, 取得下一個流水編號
     *
     * @return
     */
    public static String getNextCaseId(CaseTypeEnum caseType) {
//		LOGGER.info("KgoUtil getNextCaseId...");
        String caseId = "";
        String currentDateStr = DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);

        // SA 服務申辦流程 ,A 開頭
//		if (CaseTypeEnum.SA.equals(caseType)) {
//			KgoCaseMainRepository kgoCaseMainRepository = SpringUtil.getDao(KgoCaseMainRepository.class);
//			String suffixStr = kgoCaseMainRepository.findNextCaseIdSuffixStr(caseType.getPrefix(), currentDateStr);
//			String nextId = StringUtils.isBlank(suffixStr) ? String.format("%05d", Long.parseLong("1"))
//					: String.format("%05d", Long.parseLong(suffixStr));
//			caseId = caseType.getPrefix() + currentDateStr + nextId;
//		}

        // URA 服務案件新增, U開頭
        // SCA 系統權限申請, C開頭
        if (CaseTypeEnum.SCA.equals(caseType)) {
            KgoScaApplyRepository kgoScaApplyRepository = SpringUtil.getDao(KgoScaApplyRepository.class);
            caseId = kgoScaApplyRepository.findNextCaseId(caseType.getPrefix() + currentDateStr);
        } else if (CaseTypeEnum.URA.equals(caseType)) {
            KgoUraApplyRepository kgoUraApplyRepository = SpringUtil.getDao(KgoUraApplyRepository.class);
            caseId = kgoUraApplyRepository.findNextCaseId(caseType.getPrefix() + currentDateStr);

            // SA 服務申辦流程 ,A 開頭 或是動態流程作業
        } else {
            KgoCaseMainRepository kgoCaseMainRepository = SpringUtil.getDao(KgoCaseMainRepository.class);
            String suffixStr = kgoCaseMainRepository.findNextCaseIdSuffixStr(CaseTypeEnum.SA.getPrefix(), currentDateStr);
            String nextId = StringUtils.isBlank(suffixStr) ? String.format("%05d", Long.parseLong("1")) : String.format("%05d", Long.parseLong(suffixStr));
            LOGGER.info(">>>>> nextId: " + nextId);
            caseId = CaseTypeEnum.SA.getPrefix() + currentDateStr + nextId;

            LOGGER.info(">>>>> caseId: " + caseId + " currentDateStr: " + currentDateStr);
        }

        return caseId;
    } //getNextCaseId

    /**
     * 取得下一個流水編號
     *
     * @return
     */
    public static String getNextCaseSetId() {
//		LOGGER.info("KgoUtil getNextCaseSetId...");
        KgoCasesetRepository kgoCasesetRepository = SpringUtil.getDao(KgoCasesetRepository.class);
        String currentDateStr = DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
        String suffixStr = kgoCasesetRepository.findNextCaseSetIdSuffixStr(currentDateStr);
        String nextId = StringUtils.isBlank(suffixStr) ? String.format("%05d", Long.parseLong("1")) : String.format("%05d", Long.parseLong(suffixStr));
        return CASESET_ID_PREFIX_CHAR + currentDateStr + nextId;
    } //getNextCaseSetId

    /**
     * 取得申請說明附件上傳路徑
     *
     * @param caseSetId
     * @param applyType
     * @return
     */
    public static String getAttachmentUploadBasePath(String caseSetId, String applyType) {
        String path = StringUtils.EMPTY;
        String attachmentUploadBasePath = SpringUtil.getProperty("attachment.upload.path");
        path = attachmentUploadBasePath.replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASESET_ID, caseSetId).replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASESET_TYPE, applyType);
        return path;
    }

    /**
     * 取得書表下載功能中的檔案上傳路徑
     *
     * @param caseSetId
     * @return
     */
    public static String getFormDownloadUploadBasePath(String caseSetId) {
        String path = StringUtils.EMPTY;

        String formDownloadUploadBasePath = SpringUtil.getProperty("form.download.upload.path");
        path = formDownloadUploadBasePath.replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASESET_ID, caseSetId);
        return path;
    }

    /**
     * 案件附件 檔案上傳路徑
     *
     * @param caseId
     * @return
     */
    public static String getCaseDownloadUploadBasePath(String caseId) {
        String path = StringUtils.EMPTY;

        String formDownloadUploadBasePath = SpringUtil.getProperty("case.download.upload.path");
        path = formDownloadUploadBasePath.replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASE_ID, caseId);
        return path;
    }

    /**
     * 案件附件 檔案上傳路徑
     *
     * @param caseId
     * @return
     */
    public static String getCaseDownloadUploadBasePathCorrect(String caseId) {
        String path = StringUtils.EMPTY;

        String formDownloadUploadBasePath = SpringUtil.getProperty("case.download.upload.path.correct");
        path = formDownloadUploadBasePath.replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_CASE_ID, caseId);
        return path;
    }

    /**
     * 案件附件 檔案上傳路徑
     *
     * @param functionCode
     * @param fSeq
     * @return
     */
    public static String getFunctionCodeDownloadUploadBasePath(BackendFunctionCodeEnum functionCode, Integer fSeq) {
        String path = StringUtils.EMPTY;

        String formDownloadUploadBasePath = SpringUtil.getProperty("backend.function.download.upload.path");
        path = formDownloadUploadBasePath.replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_FUNCTION_CODE, functionCode.getFunctionCode()).replace(ATTACHMENT_UPLOAD_BASE_PATH_REPLACE_STRING_FSEQ,
                "" + fSeq);
        return path;
    }

    /**
     * 案件附件 檔案上傳路徑
     *
     * @param caseId
     * @return
     */
    public static String getCaseHandlePendingRevieweDownloadUploadBasePath(String caseId) {
        String path = StringUtils.EMPTY;
        String formDownloadUploadBasePath = SpringUtil.getProperty("case.handle.pending.review.upload.file.path");
        path = MessageFormat.format(formDownloadUploadBasePath, caseId);
        return path;
    }

    /**
     * 場地附件  Roy
     */
    public static String getCaseSetSiteDownloadUploadBasePath(String caseSetSiteMainFileId) {
        String path = StringUtils.EMPTY;
        String formDownloadUploadBasePath = SpringUtil.getProperty("caseSet.site.upload.file.path");
        path = MessageFormat.format(formDownloadUploadBasePath, caseSetSiteMainFileId);
        return path;
    }

    /**
     * 取得表單欄位資料Map Cache.
     *
     * @return the all R case set column map
     */
    public static Map<KgoCasesetColumnPK, KgoCasesetColumn> getAllCaseSetColumnMap() {
        CacheManager cm = (CacheManager) SpringUtil.getBean(CacheManager.class);
        Cache cache = cm.getCache("allCaseSetColumn");
        Map<KgoCasesetColumnPK, KgoCasesetColumn> casesetColumnMap = new HashMap<>();
        if (cache != null) {
            casesetColumnMap = (Map<KgoCasesetColumnPK, KgoCasesetColumn>) cache.get("getAllCaseSetColumn").get();
            if (MapUtils.isNotEmpty(casesetColumnMap)) {
                return casesetColumnMap;
            }
        }

        CacheServiceImpl cacheServiceImpl = (CacheServiceImpl) SpringUtil.getBean("CacheService");
        casesetColumnMap = cacheServiceImpl.getAllCaseSetColumn();
        return casesetColumnMap;
    }

    /**
     * GEO 20211230 add  取得預約表單欄位資料Map Cache.
     *
     * @return the all R case set column map
     */
    public static Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> getAllAppointmentColumnMap() {
        CacheManager cm = (CacheManager) SpringUtil.getBean(CacheManager.class);
        Cache cache = cm.getCache("allAppointmentColumns");
        Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> appointmentColumnMap = new HashMap<>();
        if (cache != null) {
            appointmentColumnMap = (Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn>) cache.get("getAllAppointmentColumn").get();
            if (MapUtils.isNotEmpty(appointmentColumnMap)) {
                return appointmentColumnMap;
            }
        } //if (cache != null
        CacheServiceImpl cacheServiceImpl = (CacheServiceImpl) SpringUtil.getBean("CacheService");
        appointmentColumnMap = cacheServiceImpl.getAllAppointmentColumn();
        return appointmentColumnMap;
    }

    /**
     * 取得表單欄位資料 KgoCasesetColumn Cache.
     *
     * @param kgoCasesetColumnPK the kgo caseset column PK
     * @return the kgo caseset column from cashe
     */
    public static KgoCasesetColumn getKgoCasesetColumnFromCashe(KgoCasesetColumnPK kgoCasesetColumnPK) {
        Map<KgoCasesetColumnPK, KgoCasesetColumn> casesetColumnMap = getAllCaseSetColumnMap();
        KgoCasesetColumn column = casesetColumnMap.get(kgoCasesetColumnPK);
//		if (column != null)LOGGER.info("column = " + column.getColumnName());
        return column;
    }

    /**
     * GEO 20211230 add 取得預約表單欄位資料 AppointmentColumn Cache.
     */
    public static GeoKgoAppointmentColumn getAppointmentColumnFromCashe(GeoKgoAppointmentColumnPK appointmentColumnPK) {
        Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> appointmentColumnMap = getAllAppointmentColumnMap();
        GeoKgoAppointmentColumn column = appointmentColumnMap.get(appointmentColumnPK);
        return column;
    }

    /**
     * 取得MyData表單欄位資料Map Cache.
     *
     * @return the all my data column map
     */
    public static Map<KgoMydataColumnPK, KgoMydataColumn> getAllMyDataColumnMap() {
        CacheManager cm = (CacheManager) SpringUtil.getBean(CacheManager.class);
        Cache cache = cm.getCache("allMyDataColumn");
        Map<KgoMydataColumnPK, KgoMydataColumn> mydataColumnMap = new HashMap<>();
        if (cache != null) {
            mydataColumnMap = (Map<KgoMydataColumnPK, KgoMydataColumn>) cache.get("getAllMyDataColumn").get();
            if (MapUtils.isNotEmpty(mydataColumnMap)) {
                return mydataColumnMap;
            }
        }

        CacheServiceImpl cacheServiceImpl = (CacheServiceImpl) SpringUtil.getBean("CacheService");
        mydataColumnMap = cacheServiceImpl.getAllMyDataColumn();
        return mydataColumnMap;
    }

    /**
     * 取得MyData表單欄位資料 KgoMydataColumn Cache.
     *
     * @param kgoMydataColumnPK the kgo mydata column PK
     * @return the kgo caseset column from cashe
     */
    public static KgoMydataColumn getKgoMydataColumnFromCashe(KgoMydataColumnPK kgoMydataColumnPK) {
        Map<KgoMydataColumnPK, KgoMydataColumn> myDataColumnMap = getAllMyDataColumnMap();
        KgoMydataColumn column = myDataColumnMap.get(kgoMydataColumnPK);
        return column;
    }

    /**
     * 取得機關Map Cache.
     *
     * @return the all my data column map
     */
    public static Map<String, KgoOrgan> getAllOrganMap() {
        CacheManager cm = (CacheManager) SpringUtil.getBean(CacheManager.class);
        Cache cache = cm.getCache("allOrganMap");
        Map<String, KgoOrgan> allOrganMap = new HashMap<>();
        if (cache != null) {
            allOrganMap = (Map<String, KgoOrgan>) cache.get("getAllOrganMap").get();
            if (MapUtils.isNotEmpty(allOrganMap)) {
                return allOrganMap;
            }
        }

        CacheServiceImpl cacheServiceImpl = (CacheServiceImpl) SpringUtil.getBean("CacheService");
        allOrganMap = cacheServiceImpl.getAllOrganMap();
        return allOrganMap;
    }

    /**
     * 取得機關名稱 from Cache.
     *
     * @param organId
     * @return the kgo caseset column from cashe
     */
    public static String getOrganName(String organId) {
        if (StringUtils.isBlank(organId)) {
            return StringUtils.EMPTY;
        }
        Map<String, KgoOrgan> allOrganMap = getAllOrganMap();
        KgoOrgan organ = allOrganMap.get(organId);

        return organ != null ? organ.getOrganName() : StringUtils.EMPTY;
    }

    // FIXME 寫法需要再檢視是否需修正
    public static String getLoginUserId() {
        BackendLoginUserInfo loginUserInfo = getBackendLoginUser();
        if (loginUserInfo != null && !StringUtils.isEmpty(loginUserInfo.getUserId())) {
            return loginUserInfo.getUserId();
        } else {
            return "A123456789";
        }

    }

    // TODO:測試
    @Deprecated
    public static String getTempCreateUser() {
        return "建立者";
    }

    // TODO:測試
    @Deprecated
    public static String getTempUpdateUser() {
        return "更新者";
    }

    // TODO:測試
    @Deprecated
    public static String getTempLoginUserOrganId() {
        return "o00001";
    }

    // TODO:測試
    @Deprecated
    public static String getTempLoginUserUnitId() {
        return "u00001";
    }

    /**
     * 取得下一個版本號
     *
     * @param caseSetId
     * @return
     */
    public static int getNextVersionValue(String caseSetId) {

        KgoCasesetGroupRepository kgoCasesetGroupRepository = SpringUtil.getDao(KgoCasesetGroupRepository.class);
        Integer maxVersion = kgoCasesetGroupRepository.findMaxVersionByCasesetId(caseSetId);
        int nextVersion = ObjectUtils.isEmpty(maxVersion) ? DEFAULT_VERSION_NUMBER : maxVersion.intValue() + 1;

        return nextVersion;
    }

    /**
     * 取得機關審核表單下一個版本號
     *
     * @param caseSetId
     * @return
     */
    public static int getOrganFormGroupNextVersionValue(String caseSetId, Integer caseFormMaxVersion) {
        GeoKgoCasesetOrganGroupReposCustom kgoCasesetGroupRepository = SpringUtil.getDao(GeoKgoCasesetOrganGroupReposCustom.class);
        Integer maxVersion = kgoCasesetGroupRepository.findMaxVersionByCasesetId(caseSetId, caseFormMaxVersion);
        int nextVersion = ObjectUtils.isEmpty(maxVersion) ? DEFAULT_VERSION_NUMBER : maxVersion.intValue() + 1;
        return nextVersion;
    } //getOrganFormGroupNextVersionValue

    /**
     * 取得預約表單下一個版本號
     *
     * @param appointmentMainId
     * @return
     */
    public static int getAppointmentMainNextVersionValue(String appointmentMainId) {
        GeoKgoAppointmentMainReposCustom geoKgoCasesetOrganGroupReposCustom = SpringUtil.getDao(GeoKgoAppointmentMainReposCustom.class);
        Integer maxVersion = geoKgoCasesetOrganGroupReposCustom.findMaxVersionByAppointmentMainId(appointmentMainId);
        int nextVersion = ObjectUtils.isEmpty(maxVersion) ? DEFAULT_VERSION_NUMBER : maxVersion.intValue() + 1;
        return nextVersion;
    } //getAppointmentMainNextVersionValue

    public static String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    /**
     * 簡易隱碼 <br/>
     * 1.字首 字尾顯示就好<br/>
     * 2.一個字的話就不隱碼 <br/>
     * 3.兩個字的話就字尾隱<br/>
     *
     * @param userName
     * @return
     */
    public static String hideName(String userName) {
        if (userName == null || userName.isEmpty()) {
            return "";
        }
        int namelen = userName.length();
        if (namelen < 2) {
            return userName;
        }
        if (namelen < 3) {
            return userName.replace(userName.substring(1), "Ｏ");
        }
        char[] charArray = userName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < namelen; i++) {
            if (i == 0 || i == namelen - 1) {
                sb.append(charArray[i]);
            } else {
                sb.append("O");
            }
        }
        return sb.toString();
    }

    /**
     * 簡易隱碼 <br/>
     *
     * @param id
     * @return
     */
    public static String hideID(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return mask(id, 3, 4, 'Ｏ');
        } else {
            return "";
        }
    }

    private static String mask(String text, int start, int length, char maskSymbol) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (start < 0) {
            start = 0;
        }
        if (length < 1) {
            return text;
        }

        StringBuilder sb = new StringBuilder();
        char[] cc = text.toCharArray();
        for (int i = 0; i < cc.length; i++) {
            if (i >= start && i < (start + length)) {
                sb.append(maskSymbol);
            } else {
                sb.append(cc[i]);
            }
        }
        return sb.toString();
    }

}
