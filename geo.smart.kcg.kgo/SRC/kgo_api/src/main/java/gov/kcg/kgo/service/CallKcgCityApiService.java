package gov.kcg.kgo.service;

import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.LandNumRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rq.CompletedRq;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rs.CompletedRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rs.InProgressRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.SearchRs;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.Push0001Rq;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rs.Push0001Rs;

import java.util.Map;

/**
 * 呼叫城市資料平臺 Service
 */
public interface CallKcgCityApiService {

    /**
     * (前臺) 自然人憑證登入 - 呼叫社會局 城市資料平臺 service 是否符合. 1. 低收、2. 中低、3. 身心障礙 身分
     */
    public boolean socbuKcgCityApiValidate(KcgCityApiServiceType serviceType);

    /**
     * (前臺) 自然人憑證登入 - 呼叫城市資料平臺 service 回傳response jsons string.
     *
     * @param serviceType the service type
     * @return the string
     */
    public String getKcgCityApiJsonStrWithMoicaLogin(KcgCityApiServiceType serviceType, Map<String, String> paramsMap);

    /**
     * (前臺) 非自然人憑證登入 - 呼叫城市資料平臺 service 回傳response Object.
     *
     * @param <T>         the generic type
     * @param <S>         the generic type
     * @param serviceType the service type
     * @param rsClazz     the rs clazz
     * @param rq          the rq
     * @return the kcg city api json str without moica login
     */
    public <T, S> T getKcgCityApiJsonStrNoMoicaLogin(KcgCityApiServiceType serviceType, Class<T> rsClazz, S rq) throws Exception;

    /**
     * 段小段
     *
     * @return
     */
    public LandNumRs landNumKcgCityApi();

    /**
     * 城市幣 - 取得任務詳細資訊
     *
     * @param taskSeq 任務 ID
     * @return
     */
    public SearchRs getCoinSearch(Integer taskSeq);

    /**
     * 城市幣 - 完成任務
     *
     * @param organizerCode 機關代碼
     * @return
     */
    public CompletedRs getCoinCompleted(CompletedRq rq);

    /**
     * 推播
     *
     * @param rq
     * @return
     */
    public Push0001Rs pushNotificationByParams(Push0001Rq rq, Map<String,String> params);

}
