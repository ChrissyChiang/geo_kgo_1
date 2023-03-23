package gov.kcg.kgo.service.impl;

import freemarker.template.Configuration;
import gov.kcg.kgo.dto.PushMsgDto;
import gov.kcg.kgo.enums.backend.PushCaseStatusEnum;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.CityApi;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.georepository.custom.GeoMyDataCaseQueryReposCustom;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoStatusMsg;
import gov.kcg.kgo.model.KgoStatusMsgCaseSet;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoStatusMsgCaseSetRepository;
import gov.kcg.kgo.repository.KgoStatusMsgRepository;
import gov.kcg.kgo.service.CallKcgCityApiService;
import gov.kcg.kgo.service.PushService;
import gov.kcg.kgo.util.CryptUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.Push0001Rq;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.KGData;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.Push0001Body;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.PushMsgData;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.PushRqHeader;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rs.Push0001Rs;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Service("PushService")
public class PushServiceImpl extends PushAPICommonServiceImpl implements PushService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

    @Autowired
    private CallKcgCityApiService callKcgCityApiService;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private KgoStatusMsgRepository kgoStatusMsgRepository;

    @Autowired
    private KgoStatusMsgCaseSetRepository kgoStatusMsgCaseSetRepository;

    @Autowired
    private Configuration freemarkerConfig;
    @Autowired
    GeoMyDataCaseQueryReposCustom geoMyDataCaseQueryReposCustom;

    /**
     * Fixed parameters of request
     */
    private static final String SYS_UID_DEFAULT = "A001";
    private static final String SEND_TIME_TYPE_DEFAULT = "01"; // "01":即時發送 "02":自訂時間發送 "03":定時(下午4點)發送
    private static final String PUSH_NAME_DEFAULT = "一次GO";
    private static final String MSG_ITEM_DEFAULT = "P0405";
    private static final String MSG_CON_TYPE_DEFAULT = "01";

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void pushMessage(List<PushMsgDto> pushDataList, String organCode) {
        try {
            CityApi cityApi = CityApi.PUSH0001;
            List<KGData> kgDataList = new LinkedList<KGData>();

            /**
             * 轉換成推播出去的資料集合
             */
            pushDataList.forEach(l -> {
                KGData kgData = new KGData();
                if (Strings.isNotBlank(l.getAccount()))
                    kgData.setAccount(l.getAccount());
                if(Strings.isNotBlank(l.getCustNum()))
                    kgData.setCustNum(CryptUtil.SHA256Encrypt(l.getCustNum())); // SHA256加密

                // edit by bob 2020/11/20 確認後為不需填
//				kgData.setMuteMode(true); // default: TRUE 靜音

                PushMsgData pushMsg = new PushMsgData();
                pushMsg.setMsgItem(MSG_ITEM_DEFAULT); // 固定"P0401"
                pushMsg.setMsgConType(MSG_CON_TYPE_DEFAULT); // 固定"01"
                pushMsg.setMsgTitle(l.getMsgTitle());
                pushMsg.setMsgBody(l.getMsgBody());
                pushMsg.setClickDetail(l.getClickDetail());
                kgData.setPushMsg(pushMsg);
                kgDataList.add(kgData);
            });

            // TODO : 須改 Call 城市資料平台，目前等 推播API 上版
            PushRqHeader header = getHeader(cityApi, organCode);

            Push0001Body body = new Push0001Body(SYS_UID_DEFAULT, SEND_TIME_TYPE_DEFAULT, StringUtils.EMPTY,
                    StringUtils.EMPTY, PUSH_NAME_DEFAULT, StringUtils.EMPTY, kgDataList);

            Push0001Rq rq = new Push0001Rq(header, body);
            Map<String,String> params = new HashMap<>();
            if (Strings.isNotBlank(pushDataList.get(0).getAccount())) {
                params.put("account", pushDataList.get(0).getAccount());
            } else {//twsn
                params.put("idn", pushDataList.get(0).getCustNum());
            }
            Push0001Rs rs = callKcgCityApiService.pushNotificationByParams(rq, params);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEND.getErrorMsgKey());
            throw new KgoApiException("addUser error " + e.getMessage(), e);
        }
    }

    @Override
    public void pushMessage(String custNum, String status, String caseId, String caseSetName, String applyDate) {
        LOGGER.info("custNum = {}, status = {}, caseId = {}, caseSetName = {}, applyDate = {}", custNum, status, caseId,
                caseSetName, applyDate);

        try {
            String msgTitle = PushCaseStatusEnum.getDefaultMsgTitleByValue(status);
            String msgBody = PushCaseStatusEnum.getDefaultMsgBodyByValue(status);
            String clickDetail = PushCaseStatusEnum.getDefaultClickDetailByValue(status);
            boolean isEnable = true;

            if (StringUtils.isNotBlank(caseId)) {
                KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);

                KgoStatusMsg kgoStatusMsg = kgoStatusMsgRepository.findByOrganIdAndStatus(kgoCaseMain.getCaseOrgan(),
                        status);

                if (kgoStatusMsg != null) {
                    msgTitle = kgoStatusMsg.getMsgTitle();
                    msgBody = StringUtils.isNotBlank(kgoStatusMsg.getMsgBody())
                            ? MessageFormat.format(kgoStatusMsg.getMsgBody(), caseSetName)
                            : "";
                    clickDetail = StringUtils.isNotBlank(kgoStatusMsg.getClickDetail()) ? MessageFormat.format(
                            kgoStatusMsg.getClickDetail(), StringUtils.isNotBlank(caseSetName) ? caseSetName : "",
                            StringUtils.isNotBlank(caseId) ? caseId : "",
                            StringUtils.isNotBlank(applyDate) ? applyDate : "") : "";
                    isEnable = Boolean.parseBoolean(kgoStatusMsg.getIsEnable());

                    KgoStatusMsgCaseSet kgoStatusMsgCaseSet = kgoStatusMsgCaseSetRepository.findByCaseSetIdAndStatusMsgSeq(caseId, kgoStatusMsg.getSeq());

                    if (kgoStatusMsgCaseSet != null)
                        isEnable = Boolean.parseBoolean(kgoStatusMsgCaseSet.getIsEnable());
                } else {
                    msgTitle = MessageFormat.format(msgTitle, "");
                    msgBody = MessageFormat.format(msgBody, "");
                    clickDetail = MessageFormat.format(clickDetail, "");
                }

                if (isEnable) {
                    CityApi cityApi = CityApi.PUSH0001;

                    List<KGData> kgDataList = new ArrayList<>();

                    KGData kgData = new KGData();
                    kgData.setCustNum(CryptUtil.SHA256Encrypt(custNum));

                    PushMsgData pushMsg = new PushMsgData();
                    pushMsg.setMsgItem(MSG_ITEM_DEFAULT); // 固定"P0401"
                    pushMsg.setMsgConType(MSG_CON_TYPE_DEFAULT); // 固定"01"
                    pushMsg.setMsgTitle(msgTitle);
                    pushMsg.setMsgBody(msgBody);
                    pushMsg.setClickDetail(clickDetail);

                    kgData.setPushMsg(pushMsg);

                    kgDataList.add(kgData);

                    PushRqHeader header = getHeader(cityApi, kgoCaseMain.getCaseOrgan());

                    Push0001Body body = new Push0001Body(SYS_UID_DEFAULT, SEND_TIME_TYPE_DEFAULT, StringUtils.EMPTY,
                            StringUtils.EMPTY, PUSH_NAME_DEFAULT, StringUtils.EMPTY, kgDataList);

                    Push0001Rq rq = new Push0001Rq(header, body);

                    Map<String,String> param = new HashMap<>();
                    param.put("idn",custNum);
                    Push0001Rs rs = callKcgCityApiService.pushNotificationByParams(rq, param);


                    LOGGER.info("Push0001Rs is {}", JsonUtil.toJSONString(rs));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Push message error, CaseId is {}", ex, caseId);
        }
    }

    @Override
    @Async
    public void pushAsyncMessage(List<PushMsgDto> pushDataList, String organCode) {
        //LOGGER.info("PushServiceImpl pushAsyncMessage start...");
        pushMessage(pushDataList, organCode);
        //LOGGER.info("PushServiceImpl pushAsyncMessage end...");
    }

}
