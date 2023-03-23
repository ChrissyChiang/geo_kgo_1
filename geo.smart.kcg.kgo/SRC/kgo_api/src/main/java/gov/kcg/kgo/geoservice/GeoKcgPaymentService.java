package gov.kcg.kgo.geoservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCasePaymentRecord;
import gov.kcg.kgo.geoenum.GeoKgoPaymentExtCaseStatusEnum;
import gov.kcg.kgo.geoenum.GeoKgoPaymentExtRsEnum;
import gov.kcg.kgo.geoenum.GeoPaymentTypeEnum;
import gov.kcg.kgo.geoenum.RentStatusEnum;
import gov.kcg.kgo.geomodel.ktcpay.*;
import gov.kcg.kgo.georepository.GeoKgoPaymentRecordRepository;
import gov.kcg.kgo.georepository.custom.GeoKgoPaymentReposCustom;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoFrontendPaymentRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.impl.helper.CallKcgCityApiServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.util.KgoUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeoKcgPaymentService {
    private static final Logger log = LoggerFactory.getLogger(GeoKcgPaymentService.class);

    private static final String GEO_2_CITY_CACHE_NAME = "geo2CityCache";
    private static final String GEO_TEST_UUID_STR = "GEO_TEST_UUID";
    private static final String PAYMENT_SUFFIX = "_P";
    private static final String REFUND_SUFFIX = "_R";
    @Value("${payment.domain.url}")
    private String paymentDomainUrl;
    @Value("${payment.pay.url}")
    private String paymentPayUrl;
    @Value("${payment.refund.url}")
    private String paymentRefundUrl;
    @Value("${payment.record.url}")
    private String paymentRecordUrl;
    @Autowired
    private CityCoinAPIService cityCoinAPIService;
    @Autowired
    private GeoCaseSetRentService geoCaseSetRentService;
    @Autowired
    private GeoKgoPaymentRecordRepository geoKgoPaymentRecordRepository;
    @Autowired
    private GeoKgoPaymentReposCustom geoKgoPaymentReposCustom;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CacheManager cacheManager;

    private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

    /**
     * 取得繳費內容
     *
     * @param rqModel
     * @return
     */
    public GeoKgoPaymentExtRsModel getPaymentListByCaseId(GeoKgoPaymentExtQueryRqModel rqModel) {
        log.info("取得繳費內容:{}", rqModel);
        // 檢核共用參數
        GeoKgoPaymentExtRsEnum rsEnum = this.validUniversalParams(rqModel, true);
        // 組裝回應
        GeoKgoPaymentExtRsModel paymentExtRsModel = new GeoKgoPaymentExtRsModel(rsEnum);
        if (GeoKgoPaymentExtRsEnum.SUCCESS == rsEnum) {
            GeoKgoPaymentExtQueryRsModel rsModel = new GeoKgoPaymentExtQueryRsModel();
            rsModel.setPaymentList(geoKgoPaymentReposCustom.getPaymentListByCaseId(rqModel.getCaseId()));
            paymentExtRsModel.setData(rsModel);
        }
        return paymentExtRsModel;
    }

    /**
     * 儲存繳退費紀錄
     *
     * @param rqModel
     * @return
     */
    @Transactional
    public GeoKgoPaymentExtRsModel savePaymentRecord(GeoKgoPaymentExtModifyRqModel rqModel) throws JsonProcessingException {
        log.info("紀錄繳費請求:{}", objectMapper.writeValueAsString(rqModel));
        // 檢核共用參數
        GeoKgoPaymentExtRsEnum rsEnum = this.validUniversalParams(rqModel, false);
        GeoKgoPaymentExtRsModel geoKgoPaymentExtRsModel = new GeoKgoPaymentExtRsModel(rsEnum);
        if (GeoKgoPaymentExtRsEnum.SUCCESS != rsEnum) {
            return geoKgoPaymentExtRsModel;
        }

        GeoPaymentTypeEnum geoPaymentTypeEnum = GeoPaymentTypeEnum.CITY_PAY;
        Arrays.stream(rqModel.getUpdateCasePayAndRefundResult()).forEach(data -> {
            final String caseId = data.getCaseId();
            final String caseStatus = data.getCaseStatus();
            GeoKgoPaymentExtCaseStatusEnum geoKgoPaymentExtCaseStatusEnum = GeoKgoPaymentExtCaseStatusEnum.getEnum(caseStatus);
            RentStatusEnum rentStatusEnum = geoKgoPaymentExtCaseStatusEnum.getRentStatusEnum();
            if (RentStatusEnum.FIN == rentStatusEnum) {
                // 修改狀態
                geoCaseSetRentService.updateRentRentalPayment(
                        rentStatusEnum,
                        geoPaymentTypeEnum,
                        caseId,
                        new Timestamp(System.currentTimeMillis()),
                        data.getPaymentNo(),
                        Strings.concat(geoPaymentTypeEnum.getPayTypeCode(), PAYMENT_SUFFIX));
                geoCaseSetRentService.updateRentRelation(RentStatusEnum.SUS, caseId);

                // 紀錄繳費
                this.savePaymentRecord(caseId, BigDecimal.valueOf(data.getAmount()), geoKgoPaymentExtCaseStatusEnum, "Y");
            }
        });
        // 組裝回應物件
        return geoKgoPaymentExtRsModel;
    }

    /**
     * 儲存繳退費紀錄
     *
     * @param caseId
     * @param amount
     * @param statusEnum
     */
    public void savePaymentRecord(String caseId, BigDecimal amount, GeoKgoPaymentExtCaseStatusEnum statusEnum, String isExternal) {
        final Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        GeoKgoCasePaymentRecord geoKgoCasePaymentRecord = new GeoKgoCasePaymentRecord();
        geoKgoCasePaymentRecord.setCaseId(caseId);
        geoKgoCasePaymentRecord.setPaymentType(statusEnum.getType());
        geoKgoCasePaymentRecord.setPaymentStatus(statusEnum.getStatus());
        geoKgoCasePaymentRecord.setAmount(amount);
        geoKgoCasePaymentRecord.setIsExternal(isExternal);
        geoKgoCasePaymentRecord.setCreateDate(nowTime);
        geoKgoPaymentRecordRepository.save(geoKgoCasePaymentRecord);
    }

    /**
     * 生成繳費物件
     *
     * @param caseId
     * @return
     */
    @Cacheable(value = GEO_2_CITY_CACHE_NAME, key = "#p1")
    public GeoFrontendPaymentRs genPaymentModel(String caseId, String certification) throws Exception {
        GeoFrontendPaymentRs rs = new GeoFrontendPaymentRs();
        GeoKgoPaymentModel model = new GeoKgoPaymentModel();
        FrontendLoginUserInfo frontendLoginUser = KgoUtil.getFrontendLoginUser(false);
        if (frontendLoginUser == null) {
            log.warn("未登入");
            rs.setMsg("請先登入再進行繳費");
            rs.setRtnCode("9998");
            return rs;
        }

        log.info("開始生成繳費物件, caseId:{}", caseId);
        GeoCityCoinMembershipViewForm geoCityCoinMembershipViewForm = new GeoCityCoinMembershipViewForm();
        try {
            geoCityCoinMembershipViewForm = cityCoinAPIService.checkMemberShipByLoginType();
            log.info("繳費請求會員物件:{}", objectMapper.writeValueAsString(geoCityCoinMembershipViewForm));
        } catch (KgoApiException ex) {
            log.warn("未登入");
            rs.setMsg("請先登入再進行繳費");
            rs.setRtnCode("9998");
            return rs;
        }

        if (!geoCityCoinMembershipViewForm.isMembership() || !geoCityCoinMembershipViewForm.isRealName()) {
            log.warn("已經登入, 非實名");
            rs.setMsg("使用者不是市民科技實名會員");
            rs.setRtnCode("9999");
            return rs;
        }

        if(!geoCityCoinMembershipViewForm.isHaveKcgBankAccount()){
            log.warn("已經登入, 市民科技會員非高銀綁定帳號");
            rs.setMsg("使用者非高銀綁定帳號");
            rs.setRtnCode("9997");
            return rs;
        }

        geoCaseSetRentService.updateCaseWithCityMember(geoCityCoinMembershipViewForm,caseId);
        log.warn("組裝資料...");
        // 組裝資料
        final GeoKgoPaymentData data = new GeoKgoPaymentData(certification, caseId, geoCityCoinMembershipViewForm.getUuid());
        model.setData(data);
        model.setActionUrl(paymentDomainUrl.concat(paymentPayUrl));
        data.setAtoken(certification);
        rs.setRtnCode(Constants.SUCCESS_CODE);
        rs.setData(model);
        return rs;
    }

    /**
     * 退款申請: 將原案caseId給市民科技(欄位為paymentNo)
     *
     * @param caseId
     * @param refundAmount
     * @param refundKcoin
     * @param paymentNo
     * @throws JsonProcessingException
     */
    public void doRefund(String caseId, Integer refundAmount, String refundKcoin, String paymentNo) throws JsonProcessingException {
        // 組裝請求
        GeoKgoRefundRq rq = new GeoKgoRefundRq(paymentNo, String.valueOf(refundAmount), refundKcoin);
        String requestStr = objectMapper.writeValueAsString(rq);
        log.info("退費請求:{}", requestStr);

        //城市資料平台呼叫市民科技退費api
        String result = this.getResult(callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_REFEND.getServiceId(),this.headerParams(requestStr),requestStr));
        log.info("退費申請結果:{}", result);
        GeoKgoRefundRs rs = objectMapper.readValue(result, GeoKgoRefundRs.class);
        if (String.valueOf(HttpStatus.SC_OK).equals(rs.getReturnCode())) {
            GeoPaymentTypeEnum geoPaymentTypeEnum = GeoPaymentTypeEnum.CITY_PAY;
            GeoKgoPaymentExtCaseStatusEnum paymentExtCaseStatusEnum = GeoKgoPaymentExtCaseStatusEnum.RF2;
            Timestamp finishTime;
            try{
                finishTime = DateUtil.strToTimestamp(rs.getFinishDt(),"yyyyMMddHHmmss");
            }catch (ParseException e) {
                log.info("response time ParseException: FISISH_DT -"+rs.getFinishDt());
                finishTime = DateUtil.getCurrentTimestamp();
            }

            // 修改狀態
            geoCaseSetRentService.updateRentRentalPayment(
                    paymentExtCaseStatusEnum.getRentStatusEnum(),
                    geoPaymentTypeEnum,
                    caseId,
                    finishTime,
                    paymentNo,
                    Strings.concat(geoPaymentTypeEnum.getPayTypeCode(), REFUND_SUFFIX));

            geoCaseSetRentService.cancelAfterRefound(caseId);

            // 紀錄退費
            this.savePaymentRecord(caseId, BigDecimal.valueOf(refundAmount), paymentExtCaseStatusEnum, "Y");
        } else {
            throw new KgoApiException("退費申請失敗");
        }
    }

    /**
     * todo 繳費入帳查詢
     *
     * @param entryDate
     * @param payDate
     * @return
     * @throws JsonProcessingException
     */
    public List<GeoKgoPaymentRecordData> getPaymentRecordDataList(String entryDate, String payDate) throws JsonProcessingException {
        if (StringUtils.isAllBlank(entryDate, payDate)) {
            throw new RuntimeException();
        }

        // 組裝請求
        GeoKgoPaymentRecordRq rq = new GeoKgoPaymentRecordRq();
        rq.setEntryDate(entryDate);
        rq.setPayDate(payDate);
        String requestStr = objectMapper.writeValueAsString(rq);

        // 發送api
        String result =
                this.getResult(callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_CITY_ERTRYDATE_LIST.getServiceId(),this.headerParams(requestStr),requestStr));
//        HttpRequest httpRequest = new HttpRequest();
//                this.getResult(httpRequest.sendPost(paymentDomainUrl.concat(paymentRecordUrl), requestStr, this.headerParams(requestStr)));


        GeoKgoPaymentRecordRs rs = objectMapper.readValue(result, GeoKgoPaymentRecordRs.class);

        return rs.getData();
    }

    /** =================================================== private ================================================== */

    /**
     * 取得header
     *
     * @param requestStr
     * @return
     */
    private Map<String, String> headerParams(String requestStr) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-length", "" + requestStr.getBytes().length);
        headers.put("Content-Type", "" + "application/json; utf-8");
        headers.put("Accept", "application/json");
        headers.put("Connection", "Keep-Alive");
        headers.put("Charset", "UTF-8");
        return headers;
    }

    /**
     * todo 檢核回應
     *
     * @param responseResult
     * @return
     */
    private String getResult(String responseResult) {
        if (Strings.EMPTY.equals(responseResult)) {
            throw new KgoApiException("申請失敗, 請聯繫開發人員");
        }
        return responseResult;
    }

    /**
     * 檢核共用參數
     *
     * @param model
     * @return
     */
    private GeoKgoPaymentExtRsEnum validUniversalParams(GeoKgoPaymentExtModelBase model, boolean isCertificationRequired) {
        final String certification = model.getCertification();
        return StringUtils.isBlank(model.getOrgId())
                ? GeoKgoPaymentExtRsEnum.CITY0009
                : StringUtils.isBlank(model.getTxId())
                ? GeoKgoPaymentExtRsEnum.CITY0002
                : isCertificationRequired && StringUtils.isBlank(certification)
                ? GeoKgoPaymentExtRsEnum.CITY0011
                : this.validCertificationNotExists(certification, isCertificationRequired)
                ? GeoKgoPaymentExtRsEnum.CITY0012
                : GeoKgoPaymentExtRsEnum.SUCCESS;
    }

    /**
     * 檢核cache是否有值
     *
     * @param certification
     * @return
     */
    private boolean validCertificationNotExists(String certification, boolean isCertificationRequired) {
        if (!isCertificationRequired || GEO_TEST_UUID_STR.equals(certification)) {
            return false;
        }

        Cache cache = cacheManager.getCache(GEO_2_CITY_CACHE_NAME);
        return cache == null || cache.get(certification) == null;
    }
}
