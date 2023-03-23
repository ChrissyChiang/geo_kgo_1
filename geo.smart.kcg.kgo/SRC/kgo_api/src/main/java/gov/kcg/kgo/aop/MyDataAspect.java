package gov.kcg.kgo.aop;

import gov.kcg.kgo.model.KgoMydataLog;
import gov.kcg.kgo.model.KgoMydataTx;
import gov.kcg.kgo.repository.KgoMydataLogRepository;
import gov.kcg.kgo.repository.KgoMydataTxRepository;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.viewModel.mydata.vo.ec.MyDataBaseRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.MyDataBaseRs;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServiceModeOneRq;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServiceModeOneRs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MyDataAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyDataAspect.class);

    private final String MY_DATA_LAYER = "gov.kcg.kgo.aop.pointcut.PointcutDefinition.myDataLayer()";

    private final String CALL_MY_DATA_LAYER = "gov.kcg.kgo.aop.pointcut.PointcutDefinition.callMyDataLayer()";

    @Autowired
    private KgoMydataTxRepository kgoMydataTxRepository;

    @Autowired
    private KgoMydataLogRepository kgoMydataLogRepository;

    @Around(MY_DATA_LAYER)
    public Object handleMyDataService(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();

        KgoMydataTx kgoMydataTx = new KgoMydataTx();

        Arrays.stream(args).forEach(arg -> {
            if (arg instanceof MyDataServiceModeOneRq) {
                MyDataServiceModeOneRq req = (MyDataServiceModeOneRq) arg;

                kgoMydataTx.setTxId(req.getTxId());
                kgoMydataTx.setClientId(req.getClientId());
                kgoMydataTx.setClientIv(req.getClientIv());
                kgoMydataTx.setClientSecretKey(req.getClientSecretKey());
                kgoMydataTx.setResourceId(req.getResourceList().toString());
                kgoMydataTx.setModeType("1");
                kgoMydataTx.setRequestData(JsonUtil.toJSONString(arg));
                kgoMydataTx.setCreateTime(DateUtil.getCurrentTimestamp());
            }
        });

        Object responseObj = pjp.proceed();

        if (responseObj instanceof MyDataServiceModeOneRs) {
            MyDataServiceModeOneRs res = (MyDataServiceModeOneRs) responseObj;

            if (!"".equals(res.getRedirectMyDataUrl())) {
                kgoMydataTx.setResponseData(JsonUtil.toJSONString(responseObj));

                kgoMydataTxRepository.save(kgoMydataTx);
            }
        }

        return responseObj;
    }

    @Around(CALL_MY_DATA_LAYER)
    public Object handleCallMyDataService(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();

        KgoMydataLog kgoMydataLog = new KgoMydataLog();

        Arrays.stream(args).forEach(arg -> {
            if (arg instanceof MyDataBaseRq) {
                kgoMydataLog.setTxId(((MyDataBaseRq) arg).getTxId());
                kgoMydataLog.setRequestData(JsonUtil.toJSONString(arg));
                kgoMydataLog.setServiceName(pjp.getSignature().getName());
                kgoMydataLog.setCreateTime(DateUtil.getCurrentTimestamp());
            }
        });

        Object responseObj = pjp.proceed();

        if (responseObj instanceof MyDataBaseRs) {
            kgoMydataLog.setResponseData(((MyDataBaseRs) responseObj).getJsonData());
            kgoMydataLog.setReturnCode(String.valueOf(((MyDataBaseRs) responseObj).getHttpStatus()));
            kgoMydataLog.setUpdateTime(DateUtil.getCurrentTimestamp());
            kgoMydataLog.setResponseNewData(((MyDataBaseRs) responseObj).getJsonData());
        }

        kgoMydataLogRepository.save(kgoMydataLog);
        return responseObj;
    }

}
