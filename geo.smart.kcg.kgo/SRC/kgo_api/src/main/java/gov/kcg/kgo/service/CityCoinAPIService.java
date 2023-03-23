package gov.kcg.kgo.service;

import com.mashape.unirest.http.HttpResponse;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rs.InProgressRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.SearchRs;

public interface CityCoinAPIService {

    /**
     * 取得單筆城市幣任務資料
     *
     * @param taskSeq
     * @return
     */
    SearchRs search(Integer taskSeq);

    GeoCityCoinMembershipViewForm checkMemberShipByLoginType() throws Exception;

    GeoCityCoinMembershipViewForm checkMemberShipByTWSsn(String twSsn) throws Exception;

    String cityCoinCheckMemberShipAPI(String subType, String sub) throws Exception;

    String cityCoinAPI(String subType, String sub) throws Exception;

    /**
     * 城市幣任務完成
     *
     * @param caseId    案件編號
     * @param applyUser 申請人員統號
     * @param caseSetId 案件設定編號
     */
    void completed(String caseId, String applyUser, String caseSetId) throws Exception;

    /**
     * 城市幣任務完成
     *
     * @param kgoCaseMain
     */
    void completed(KgoCaseMain kgoCaseMain) throws Exception;

}
