package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.repository.KgoUserLogRepository;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationMemo;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

public class GeoBaseService {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoBaseService.class);

    @Autowired
    protected KgoUserLogRepository kgoUserLogRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    @Qualifier("kgoNoAuthRestTemplate")
    private RestTemplate kgoNoAuthRestTemplate;

    /**
     * 設置 成功/失敗訊息
     *
     * @param rq   the rq
     * @param rs    the rs
     * @param error the error
     */
    protected <D extends ApiRequest, T extends ApiBaseResponse<?>> void setResultMessage(D rq, T rs, KgoApiException error) {
        try {
            if (rq!=null) {
                LOGGER.info(">>>>> rq :" + JsonUtil.toJSONString(rq));
            } else {
                LOGGER.info(">>>>> rq : {}");
            }
        } catch (Exception e) {
            LOGGER.error(">>>>> rq pasing toJSONString error ");
        } //try

        if (error == null) {
            // 成功
            rs.setMsg(messageUtil.getMessage("kcg.city.receive.result.success"));
        } else {
            // 失敗
            rs.setError(error.getErrorResult());
        } //if (error == null)
    } //setResultMessage

    /**
     * 以 GET 方式自第三方 API 取得資料
     *
     * @param apiUrl
     */
    protected String sendGetApi(String apiUrl) {
        LOGGER.info("GeoKcgBaseService sendGetApi url = " + apiUrl);
        try {
            ResponseEntity<String> responseEntity = kgoNoAuthRestTemplate.getForEntity(apiUrl, String.class);
            if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
                String jsonStr = responseEntity.getBody();
                if (!jsonStr.isEmpty()) {
                    //LOGGER.info("GeoKcgBaseService sendGetApi response: "+jsonStr);
                    return jsonStr;
                }
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("GeoKcgBaseService sendGetApi exception: "+e.toString());
            return null;
        }
    } //sendGetApi

    /**
     * 以 POST 方式自第三方 API 取得資料
     *
     * @param apiUrl
     */
    protected String sendPostApi(String apiUrl) {
        LOGGER.info("GeoKcgBaseService sendPostApi url = " + apiUrl);
        try {
            String dataJson = "{}";
            HttpEntity<String> httpEntity = new HttpEntity<>(dataJson, getHttpHeader());
            kgoNoAuthRestTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                    if (connection instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) connection).setHostnameVerifier(DO_NOT_VERIFY);
                    }
                    super.prepareConnection(connection, httpMethod);
                }
            });

            ResponseEntity<String> responseEntity = kgoNoAuthRestTemplate.postForEntity(apiUrl, httpEntity, String.class);
            if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
                String jsonStr = responseEntity.getBody();
                if (!jsonStr.isEmpty()) {
                    //LOGGER.info("GeoKcgBaseService sendPostApi response: "+jsonStr);
                    return jsonStr;
                }
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("GeoKcgBaseService sendPostApi exception: "+e.toString());
            return null;
        }
    } //sendPostApi

    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("platform", "WEB");
        headers.set("Host", "soweb.kcg.gov.tw");
        return headers;
    }

    protected static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 取得操作記錄MEMO.
     *
     * @param <T>   the generic type
     * @param rq    the rq.
     * @param clazz the clazz.
     * @return the T @FrontendFunctionCodeEnum 、@BackendFunctionCodeEnum
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T extends Enum<?>> OperationApiMemo genOperationMemo(SystemTypeEnum systemType, SysLogExeType sysLogExeType, T funcEnum) {
        OperationApiMemo opMemo = new OperationApiMemo();
        opMemo.setSystemType(systemType);
        opMemo.setSysLogType(sysLogExeType);

        String funcCode = StringUtils.EMPTY;

        /** 前後臺功能名稱 */
        if (funcEnum instanceof FrontendFunctionCodeEnum) {
            funcCode = ((FrontendFunctionCodeEnum) funcEnum).getFunctionCode();
        }

        if (funcEnum instanceof BackendFunctionCodeEnum) {
            funcCode = ((BackendFunctionCodeEnum) funcEnum).getFunctionCode();
        }
        opMemo.setFunctionCode(funcCode);

        return opMemo;
    }

    /**
     * 儲存操作紀錄.
     *
     * @param <T>  the generic type
     * @param memo the memo
     */
    protected <T extends OperationMemo> void saveOperationLog(T memo) {
        try {
            if (memo != null) {
                KgoUseLog userLog = new KgoUseLog();
                // 後台
                if (SystemTypeEnum.B.equals(memo.getSystemType())) {
                    BackendLoginUserInfo beUser = KgoUtil.getBackendLoginUser();
                    if (beUser != null) {
                        List<String> authMethodArr = beUser.getKcgUserBasicInfo().getAuthMethod();
                        if (CollectionUtils.isNotEmpty(authMethodArr)) {
                            String authMethod = authMethodArr.get(0);
                            // @LoginAuthType 登入認證類型
                            LoginAuthType loginAuthType = LoginAuthType.getLoginAuthType(authMethod);

                            userLog.setLoginType(loginAuthType != null ? loginAuthType.getTypeName() : StringUtils.EMPTY);
                        }
                        userLog.setCreateUser(beUser.getUserId());
                    }
                }

                // 前台
                if (SystemTypeEnum.F.equals(memo.getSystemType())) {
                    FrontendLoginUserInfo feUser = KgoUtil.getFrontendLoginUser(false);
                    if (feUser != null) {
                        // @LoginAuthType 登入認證類型
                        userLog.setLoginType(feUser.getLoginAuthTokenType().getAuthType()[0].getTypeName());
                        String name = StringUtils.EMPTY;
                        if (StringUtils.isNotBlank(feUser.getName())) {
                            name = String.format(" (%s)", feUser.getName());
                        }
                        userLog.setCreateUser(feUser.getUserAccount() + name);
                    }
                }
                userLog.setSystemType(memo.getSystemType().getSystemType());
                userLog.setFunctionCode(memo.getFunctionCode());
                userLog.setIp(KgoUtil.getClientIp());
                userLog.setCreateTime(DateUtil.getCurrentTimestamp());
                userLog.setAction(memo.getSysLogType() != null ? messageUtil.getMessage(memo.getSysLogType().getCodeMsgKey()) : "");
                // 客製log display
                userLog.setMemo(memo.getDisplayMemo());
                kgoUserLogRepository.save(userLog);
            } else {
                // 儲存操作紀錄錯誤
                throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_LOG_ERROR));
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>Exception 儲存操作紀錄錯誤", e);
            // throw e;
        }
    }
}
