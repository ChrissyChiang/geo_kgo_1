package gov.kcg.kgo.service;

import gov.kcg.kgo.geoviewmodel.frontend.rq.MyDataGenDataRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataGenDataRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataModelRs;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.*;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.*;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServiceDownloadRs;

public interface CaseFormService {


    public HomeActionRs homeAction(HomeActionRq rq);

    MyDataActionUrlRs myDataModel2ActionUrl(MyDataModel2ActionUrlRq rq) throws Exception;

    public MyDataActionUrlRs myDataActionUrl(MyDataActionUrlRq rq);

    public MyDataActionUrlRs myDataActionUrl(String pid, String myDataClientId);

    public String decryptMyDataTxId(String myDataClientId, String encryptTxId);

    public MyDataHomeActionRs myDataHomeAction(MyDataHomeActionRq rq);

    public MyDataServiceDownloadRs downloadMyData(String txId, String myDataClientIdt);

    public MyDataDownloadMageViewFromRs downloadMyDataMerge(String txId, String myDataClientId);

    public DownloadMyDataAttachmentRs downloadMyDataAttachment(DownloadMyDataAttachmentRq rq);

    public SaveActionRs saveAction(SaveActionRq rq);

    public ValidationActionRs validationAction(ValidationActionRq rq);

    public String encryptMyDataTxId(String myDataClientId, String txId);

    public KcgMydataRs getKcgMydata(KcgMydataRq rq);

    /**
     * 案件儲存共通作法 - 發送email 、通知、KCG API
     *
     * @param kgoCaseMain the kgo case main
     * @param kgoCaseset  the kgo caseset
     * @param applyEmail  the apply email
     * @param applyDate   the apply date
     * @throws Exception the exception
     */
    public void doSaveCaseCommonNotify(KgoCaseMain kgoCaseMain, KgoCaseset kgoCaseset, String applyEmail, String applyDate) throws Exception;


    /**
     * MyData 查詢myData模式
     *
     * @param caseSetId
     * @return
     */
    GeoMyDataModelRs queryMyDataModel(String caseSetId);

    /**
     * MyData模式二 - 3.1
     *
     * @param rq
     * @return
     */
    GeoMyDataGenDataRs genEncryptedData(MyDataGenDataRq rq) throws Exception;
}
