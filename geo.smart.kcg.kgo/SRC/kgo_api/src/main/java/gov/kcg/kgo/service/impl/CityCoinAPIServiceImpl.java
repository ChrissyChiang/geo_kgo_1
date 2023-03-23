package gov.kcg.kgo.service.impl;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.PushMsgDto;
import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.georepository.custom.GeoMyDataCaseQueryReposCustom;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.model.KgoApiLog;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.repository.KgoApiLogRepository;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.service.AuthService;
import gov.kcg.kgo.service.CallKcgCityApiService;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.PushService;
import gov.kcg.kgo.service.impl.helper.CallKcgCityApiServiceHelper;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.SearchRs;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rq.FrontendGetCityMemberInfoRq;
import gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean.FrontendGetCityMemberInfoRs;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service("CityCoinService")
public class CityCoinAPIServiceImpl implements CityCoinAPIService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CityCoinAPIServiceImpl.class);

    @Autowired
    private CallKcgCityApiService callKcgCityApiService;

    @Autowired
    private KgoApiLogRepository kgoApiLogRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    PushService pushService;
    @Autowired
    MessageUtil messageUtil;
    @Autowired
    GeoMyDataCaseQueryReposCustom geoMyDataCaseQueryReposCustom;
    @Autowired
    KgoCaseMainRepository kgoCaseMainRepository;

    private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

    private static final String CITY_COIN_API_METHOD_SEARCH = "data";
    private static final String CITY_COIN_API_METHOD_IN_PROGRESS = "InProgress";
    private static final String SYSTEM_FROM_DEFAULT_VALUE = "OTG";

    /**
     * 取得單筆城市幣任務資料
     *
     * @param taskSeq
     * @return
     */
    @Override
    public SearchRs search(Integer taskSeq) {
        return this.callKcgCityApiService.getCoinSearch(taskSeq);
    }

    @Override
    public GeoCityCoinMembershipViewForm checkMemberShipByLoginType() throws Exception {
        LOGGER.info("start checkMemberShipByLoginType:");
        GeoCityCoinMembershipViewForm geoCityCoinMembershipViewForm = new GeoCityCoinMembershipViewForm();
        String subType = Strings.EMPTY;
        String sub = Strings.EMPTY;
        FrontendLoginUserInfo frontendLoginUser = KgoUtil.getFrontendLoginUser();
        LoginAuthTokenType loginAuthTokenType = frontendLoginUser.getLoginAuthTokenType();
        LOGGER.info("checkMemberShipByLoginType - frontendLoginUser:" + JSON.toJSONString(frontendLoginUser));
        if (loginAuthTokenType != null) {
            if (LoginAuthTokenType.MOICA.equals(loginAuthTokenType)) {
                subType = "6";
                sub = frontendLoginUser.getKcgMoicaCardInfo().getMoicaCertSn();
            }
            if (LoginAuthTokenType.FACEBOOK.equals(loginAuthTokenType)) {
                subType = "4";
                sub = frontendLoginUser.getKcgFacebookSsoInfo().getFacebookUserAccount();
            }
            if (LoginAuthTokenType.GOOGLE.equals(loginAuthTokenType)) {
                subType = "5";
                sub = frontendLoginUser.getKcgGoogleSsoInfo().getGoogleUserAccount();
            }
            if (LoginAuthTokenType.LINE.equals(loginAuthTokenType)) {
                subType = "3";
                sub = frontendLoginUser.getLineInfo().getLineUserId();
            }
            if (LoginAuthTokenType.TW_FIDO.equals(loginAuthTokenType)) {
                subType = "7";
                sub = frontendLoginUser.getKcgTwFidoSsoInfo().getTwfidoUserTwSSn();
            }
            boolean continueProcess = Strings.isNotBlank(subType) && Strings.isNotBlank(sub);
            if (continueProcess) {
                String response = cityCoinCheckMemberShipAPI(subType, sub);
                if (StringUtils.isNotBlank(response)) {
                    JSONObject jsonObject = new JSONObject(response);
                    geoCityCoinMembershipViewForm.setMembership(true);
                    geoCityCoinMembershipViewForm.setSubType(subType);
                    geoCityCoinMembershipViewForm.setSub(sub);
                    geoCityCoinMembershipViewForm.setUuid(jsonObject.getString("sub"));
                    if (jsonObject.getBoolean("isRealName"))
                        geoCityCoinMembershipViewForm.setRealName(true);
                    if (jsonObject.getBoolean("isKcgBankAccount"))
                        geoCityCoinMembershipViewForm.setHaveKcgBankAccount(true);
                    if (jsonObject.has("name"))
                        geoCityCoinMembershipViewForm.setName(jsonObject.getString("name"));
                    if (jsonObject.has("license"))
                        geoCityCoinMembershipViewForm.setLicense(jsonObject.getString("license"));
                    if (jsonObject.has("cityPoint")) {
                        if (jsonObject.get("cityPoint") instanceof JSONArray) {

                        }
                        JSONArray jsonArray = jsonObject.getJSONArray("cityPoint");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject inner = jsonArray.getJSONObject(i);
                                if (inner.has("name") && "K幣".equalsIgnoreCase(inner.getString("name"))) {
                                    geoCityCoinMembershipViewForm.setCityCoin(inner.getString("point"));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("checkMemberShipByTWSsn result:" + JSON.toJSONString(geoCityCoinMembershipViewForm));
        return geoCityCoinMembershipViewForm;
    }

    @Override
    public GeoCityCoinMembershipViewForm checkMemberShipByTWSsn(String twSsn) throws Exception {
        LOGGER.info("start checkMemberShipByTWSsn:" + twSsn);
        GeoCityCoinMembershipViewForm geoCityCoinMembershipViewForm = new GeoCityCoinMembershipViewForm();
        String subType = "2";
        String sub = twSsn;
        String response = cityCoinCheckMemberShipAPI(subType, sub);
        if (StringUtils.isNotBlank(response)) {
            JSONObject jsonObject = new JSONObject(response);
            geoCityCoinMembershipViewForm.setMembership(true);
            geoCityCoinMembershipViewForm.setSubType(subType);
            geoCityCoinMembershipViewForm.setSub(sub);
            geoCityCoinMembershipViewForm.setUuid(jsonObject.getString("sub"));
            if (jsonObject.getBoolean("isRealName"))
                geoCityCoinMembershipViewForm.setRealName(true);
            if (jsonObject.has("name"))
                geoCityCoinMembershipViewForm.setName(jsonObject.getString("name"));
            if (jsonObject.has("license"))
                geoCityCoinMembershipViewForm.setLicense(jsonObject.getString("license"));
            if (jsonObject.has("cityPoint")) {
                JSONArray jsonArray = jsonObject.getJSONArray("cityPoint");
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject inner = jsonArray.getJSONObject(i);
                        if (inner.has("name") && "K幣".equalsIgnoreCase(inner.getString("name"))) {
                            geoCityCoinMembershipViewForm.setCityCoin(inner.getString("point"));
                            break;
                        }
                    }
                }
            }
        }
        LOGGER.info("checkMemberShipByTWSsn result:" + JSON.toJSONString(geoCityCoinMembershipViewForm));
        return geoCityCoinMembershipViewForm;
    }

    @Override
    public String cityCoinCheckMemberShipAPI(String subType, String sub) throws Exception {
        LOGGER.info("cityCoinCheckMemberShipAPI - subType:" + subType);
        LOGGER.info("cityCoinCheckMemberShipAPI - sub:" + sub);
        final String clientId = SpringUtil.getProperty("citycoin.membership.connection.clienid");
        final String clientSecret = SpringUtil.getProperty("citycoin.membership.connection.clientsecret");
        String authEncoded = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
//        String url = SpringUtil.getProperty("citycoin.membership.connection.url");
//        Unirest.setTimeouts(0, 0);
//        HttpResponse<String> response = Unirest.post(url)
//                .header("Authorization", "Basic " + authEncoded)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .field("subType", subType)
//                .field("sub", sub)
//                .asString();
//        LOGGER.info("cityCoinCheckMemberShipAPI response code:" + response.getStatus());
//        LOGGER.info("cityCoinCheckMemberShipAPI response body:" + response.getBody());

        //城市資料平台寫法
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("AuthorizationKTC", "Basic " + authEncoded);
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Connection", "Keep-Alive");
        headerMap.put("Charset", "UTF-8");
        StringBuilder requestSb = new StringBuilder();
        requestSb.append("subType=").append(subType).append("&")
                 .append("sub=").append(sub);

        String response = callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.CITIZEN_MEMBER_INFO.getServiceId(),headerMap,requestSb.toString());
        return response;
    }

    @Override
    public String cityCoinAPI(String subType, String sub) throws Exception {
        final String clientId = SpringUtil.getProperty("citycoin.connection.clienid");
        final String clientSecret = SpringUtil.getProperty("citycoin.connection.clientsecret");
        final String taskID = SpringUtil.getProperty("citycoin.connection.taskID");
        String url = SpringUtil.getProperty("citycoin.connection.url");
        String authEncoded = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        Unirest.setTimeouts(0, 0);
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + authEncoded);
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        StringBuilder requestSb = new StringBuilder();
        requestSb.append("taskID=").append(taskID).append("&")
                 .append("subType=").append(subType).append("&")
                 .append("subData=").append(sub);

        return callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.CITY_EXTERNAL_TASK.getServiceId(),headerMap,requestSb.toString());
    }

    @Override
    public void completed(String caseId, String applyUser, String caseSetId) throws Exception {
        LOGGER.info("case completed _ case id:" + caseId);
        Optional<KgoCaseset> kgoCaseSet = kgoCasesetRepository.findById(caseSetId);
        LOGGER.info("case completed _ kgoCaseSet.isPresent():" + kgoCaseSet.isPresent());
        if (kgoCaseSet.isPresent()) {
            GeoCityCoinMembershipViewForm geoCityCoinMembershipViewForm = checkMemberShipByTWSsn(applyUser);
            LOGGER.info("case completed and check membership:" + JSON.toJSONString(geoCityCoinMembershipViewForm));
            LOGGER.info("case completed _ kgoCaseSet CityCoin:" + kgoCaseSet.get().getCityCoin());
            if (geoCityCoinMembershipViewForm.isMembership() && geoCityCoinMembershipViewForm.isRealName()) {
                if (Strings.isNotBlank(geoCityCoinMembershipViewForm.getSubType()) &&
                        Strings.isNotBlank(geoCityCoinMembershipViewForm.getSub()) &&
                        kgoCaseSet.get().getCityCoin()) {
                    JSONObject jsonObject = new JSONObject();
                    String url = SpringUtil.getProperty("citycoin.connection.url");
                    String req = Strings.EMPTY;
                    String res = Strings.EMPTY;
                    boolean completed = false;
                    try {
                        req = "taskID: " + SpringUtil.getProperty("citycoin.connection.clienid") +
                                "; subType: " + geoCityCoinMembershipViewForm.getSubType() +
                                "; subData: " + geoCityCoinMembershipViewForm.getSub();
                        res = cityCoinAPI(geoCityCoinMembershipViewForm.getSubType(), geoCityCoinMembershipViewForm.getSub());
                        LOGGER.info("case completed _ res:" + JSON.toJSONString(res));
                        if (StringUtils.isNotBlank(res)) {
                            LOGGER.info("cityCoin:completed");
                            jsonObject = new JSONObject(res);
                            if ("true".equalsIgnoreCase(jsonObject.getString("success")))
                                completed = true;
                        }
                        if (completed) {
                            LOGGER.info("case completed start push message");
                            kgoCaseMainRepository.updateCaseMainCityCoin(caseId, 5);
                            List<PushMsgDto> pushDataList = new ArrayList<>();
                            PushMsgDto pushMsg = new PushMsgDto();
                            pushMsg.setAccount(geoCityCoinMembershipViewForm.getUuid());
                            pushMsg.setCustNum(applyUser);
                            pushMsg.setMsgTitle(messageUtil.getMessage("kgo.backend.case.handle.complete.citycoin.title"));
                            pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.backend.case.handle.complete.notification.msgBody"), "5"));
                            pushMsg.setClickDetail(String.format(messageUtil.getMessage("kgo.backend.case.handle.complete.notification.clickDetail"), "5"));
                            pushDataList.add(pushMsg);
                            pushService.pushMessage(pushDataList, kgoCaseSet.get().getOwnerOrgan());
                            LOGGER.info("case completed push message done");
                        } else {
                            completed = false;
                            LOGGER.error("push message error:" + JSON.toJSONString(jsonObject));
                            throw new Exception(JSON.toJSONString(jsonObject));
                        }
                    } catch (Exception ex) {
                        StringWriter stringWriter = new StringWriter();
                        ex.printStackTrace(new PrintWriter(stringWriter));
                        res = stringWriter.toString();
                        LOGGER.error("push message error response:" + res);
                    } finally {
                        saveKgoApiLog(caseId, completed, url, req, res);
                    }
                }
            }
        }
    }

    @Override
    public void completed(KgoCaseMain kgoCaseMain) throws Exception {
        if (ObjectUtils.isNotEmpty(kgoCaseMain)) {
            completed(kgoCaseMain.getCaseId(), kgoCaseMain.getApplyUser(), kgoCaseMain.getCaseSetId());
        }
    }

    /**
     * 呼叫市民科技 取得會員資訊.
     *
     * @param applyUser the apply user
     * @return the member account by twssn
     */
    private String getMemberAccountByTwssn(String applyUser) {
        // modify 2020.12.28 改呼叫城市資料平台 取得會員資訊
        String account = StringUtils.EMPTY;
        FrontendGetCityMemberInfoRq rq = new FrontendGetCityMemberInfoRq();
        rq.setHashId(CryptUtil.SHA256Encrypt(applyUser));
        FrontendGetCityMemberInfoRs rs = authService.getCityMemberInfo(rq);

        if (rs != null) {
            account = rs.getResult().getAccount();
        }
        return account;
//		return callKcgCityApiService.getMemberAccountByTwssn(applyUser);
    }

    private void saveKgoApiLog(String caseId, boolean success, String url, String request, String response) {

        try {
            KgoApiLog item = new KgoApiLog();
            item.setCaseId(caseId);
            item.setOrganId("cityCoin");
            item.setReCount(0);
            item.setSuccess(success);
            item.setUrl(url);
            item.setRequest(request);
            item.setResponse(response);
            item.setCreateTime(DateUtil.getCurrentTimestamp());
            item.setUpdateTime(DateUtil.getCurrentTimestamp());

            kgoApiLogRepository.save(item);
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error(String.format("saveKgoApiLog caseId: %s", caseId), e);
        }

    }
}
