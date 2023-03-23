package gov.kcg.kgo.service;

import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.viewModel.mydata.bo.ClientInfoBO;
import gov.kcg.kgo.viewModel.mydata.bo.UserInfoBO;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.SpApplyRq;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServiceDownloadRs;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServicePermissionTicketRq;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServicePermissionTicketRs;

import java.io.File;

public interface MyDataService {

    void saveZipHashWithMyDataCertificateSha256(String txId, File jweZipFile) throws Exception;

    /**
     * GEO 20221008 add_Jim
     * getMyDataCertificateSha256
     */
    String getMyDataCertificateSha256() throws Exception;

    /**
     * MyData模式ㄧ - 取得MyData導向Url
     *
     * @param txId         - 交易識別(version 4 UUID)
     * @param spReturnUrl  - MyData完成服務的指定網址
     * @param userInfoBO   - 用戶資料
     * @param clientInfoBO - Sp用戶資料
     * @return
     */
    String getRedirectMyDataUrl(String txId, String spReturnUrl, UserInfoBO userInfoBO, ClientInfoBO clientInfoBO);

    /**
     * MyData模式二 - 取得MyData導向Url
     *
     * @param txId         - 交易識別(version 4 UUID)
     * @param spReturnUrl  - MyData完成服務的指定網址
     * @param clientInfoBO - Sp用戶資料
     * @return
     */
    String getRedirectMyDataModel2Url(String txId, String spReturnUrl, UserInfoBO userInfoBO, ClientInfoBO clientInfoBO);

    /**
     * MyData模式二 - 身份驗證
     *
     * @param txId         - 交易識別(version 4 UUID)
     * @param userInfoBO   - 用戶資料
     * @param clientInfoBO - Sp用戶資料
     * @param pkcs7        - 自然人憑證
     * @return
     */
    boolean isCertifiedId(String txId, UserInfoBO userInfoBO, ClientInfoBO clientInfoBO, String pkcs7);

    /**
     * MyData處理完成後，通知SP可以下載資料
     *
     * @param
     * @return
     */
    void myDataNotifySp(SpApplyRq spApplyRq);

    /**
     * 取得交易鍵值和密鑰
     *
     * @param
     * @return
     */
    MyDataServicePermissionTicketRs getPermissionTicketAndSecretKey(MyDataServicePermissionTicketRq myDataServicePermissionTicketRq);

    /**
     * 下載MyData處理完成資料
     *
     * @param txId             - 交易識別(version 4 UUID)
     * @param clientIv         - client IV值
     * @param clientSecretKey  - client密鑰
     * @param permissionTicket - 交易鍵值
     * @param secretKey        - JWE密鑰
     * @return
     */
    MyDataServiceDownloadRs downloadMyData(String txId, String clientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey);

    MyDataServiceDownloadRs downloadMyDataTemp(String txId, String clientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey);

    MyDataServiceDownloadRs downloadMyDataTemp(String txId);

    MyDataServiceDownloadRs downloadMyDataDataBase(String txId, String clientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey);

    File getJweUnZip(String txId, String permissionTicket) throws KgoApiException;
}
