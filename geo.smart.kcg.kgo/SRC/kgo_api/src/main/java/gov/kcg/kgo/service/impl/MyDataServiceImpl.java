package gov.kcg.kgo.service.impl;

import com.thoughtworks.xstream.XStream;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCaseMainZipHash;
import gov.kcg.kgo.georepository.GeoKgoCaseMainZipHashRepository;
import gov.kcg.kgo.model.KgoMydataCallbackLog;
import gov.kcg.kgo.model.KgoMydataFile;
import gov.kcg.kgo.model.KgoMydataTx;
import gov.kcg.kgo.repository.KgoMydataCallbackLogRepository;
import gov.kcg.kgo.repository.KgoMydataFileRepository;
import gov.kcg.kgo.repository.KgoMydataLogRepository;
import gov.kcg.kgo.repository.KgoMydataTxRepository;
import gov.kcg.kgo.service.CallMyDataService;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.MyDataService;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.mydata.bo.*;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.GetServiceDataRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.GetSpSignatureParameterRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.SpApplyRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.VerifySpIdRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.GetServiceDataRs;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.GetSpSignatureParameterRs;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.VerifySpIdRs;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServiceDownloadRs;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServicePermissionTicketRq;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServicePermissionTicketRs;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class MyDataServiceImpl extends KgoBaseServiceImpl implements MyDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyDataServiceImpl.class);

    // 算法/加密模式/數據填充方式
    private static final String ALG = "AES/CBC/PKCS5Padding";

    @Value("${mydata.downloadfile.use.temp}")
    private boolean downloadFileUseTemp;

    @Value("${mydata.api.url}")
    private String myDataUrl;

    @Value("${mydata.jwefile.temp.path}")
    private String MyDataJweTempPath;

    private static final String JSON_FILE = ".json";

    private static final String PDF_FILE = ".pdf";

    private static final String CSV_FILE = ".csv";

    private static final String ZIP_FILE = ".zip";

    private static final String MANIFEST_LIST_TAG = "files";

    private static final String MANIFEST_VALUE_TAG = "file";

    private static final String META_INFO = "META-INFO";

    private static final String MANIFEST_FILE = "manifest.xml";

    private static String MY_DATA_CERTIFICATE_SHA256 = Strings.EMPTY;

    @Autowired
    private CallMyDataService callMyDataService;

    @Autowired
    private KgoMydataTxRepository kgoMydataTxRepository;

    @Autowired
    private KgoMydataCallbackLogRepository kgoMydataCallbackLogRepository;

    @Autowired
    private KgoMydataFileRepository kgoMydataFileRepository;
    @Autowired
    MyDataService myDataService;
    @Autowired
    CommonService commonService;
    @Autowired
    GeoKgoCaseMainZipHashRepository geoKgoCaseMainZipHashRepository;

    /**
     * GEO 20221009 add_Jim
     * getMyDataCertificateSha256
     *
     * @return
     */

    @Override
    public String getMyDataCertificateSha256() throws Exception {
        if (Strings.isBlank(MY_DATA_CERTIFICATE_SHA256)) {
            MY_DATA_CERTIFICATE_SHA256 =
                    commonService.getFileSHAEncoding(URLDecoder.decode(new File(this.getClass().getResource("/MyDataCertificate.cer").getPath()).getAbsolutePath(), UTF_8.name()), "SHA-256");
        }
        return MY_DATA_CERTIFICATE_SHA256;
    }

    @Override
    public String getRedirectMyDataModel2Url(String txId, String spReturnUrl, UserInfoBO userInfoBO, ClientInfoBO clientInfoBO) {
        LOGGER.info("getRedirectMyDataUrl Start get redirect myData url");

        try {
            checkField(txId, clientInfoBO);

            // Check spReturnUrl
            if (spReturnUrl.isEmpty())
                throw new KgoApiException("getRedirectMyDataUrl SpReturnUrl is null or empty");

            // Check resource list is not empty
            if (clientInfoBO.getResourceList().isEmpty())
                throw new KgoApiException("getRedirectMyDataUrl Resource list is empty");

            String resourceListStr = clientInfoBO.getResourceList().toString().replace("[", "").replace("]", "").replaceAll(", ", ":");
            String clientSecretKey = clientInfoBO.getClientSecretKey() + clientInfoBO.getClientSecretKey();
            String personalIdEncrypt = Base64Utils.encodeToString(AESUtil.encrypt(userInfoBO.getPid(), clientInfoBO.getClientIv(), clientSecretKey.getBytes(), ALG));
            // myDataUrl +
            // service/spsignature/{client_id}/{resource_id_base64encoded_string}/{tx_id}?returnUrl={sp_return_url}
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(myDataUrl);
            stringBuilder.append("service/spsignature/");
            stringBuilder.append(clientInfoBO.getClientId());
            stringBuilder.append("/");
            stringBuilder.append(Base64Utils.encodeToString(resourceListStr.getBytes("UTF-8")));
            stringBuilder.append("/");
            stringBuilder.append(txId);
            stringBuilder.append("?returnUrl=");
            stringBuilder.append(spReturnUrl);
            stringBuilder.append("&pid=");
            stringBuilder.append(personalIdEncrypt);
            return stringBuilder.toString();
        } catch (KgoApiException ex) {
            LOGGER.error("Get KgoApiException from isCertifiedId ...", ex);
        } catch (Exception ex) {
            LOGGER.error("Get can't handler exception from isCertifiedId ...", ex);
        }

        return "";
    }

    @Override
    public String getRedirectMyDataUrl(String txId, String spReturnUrl, UserInfoBO userInfoBO, ClientInfoBO clientInfoBO) {
        LOGGER.info("getRedirectMyDataUrl Start get redirect myData url");

        try {
            checkField(txId, clientInfoBO);

            // Check spReturnUrl
            if (spReturnUrl.isEmpty())
                throw new KgoApiException("getRedirectMyDataUrl SpReturnUrl is null or empty");

            // Check user personId
            if (userInfoBO.getPid().isEmpty())
                throw new KgoApiException("getRedirectMyDataUrl User personId is null or empty");

            // Check resource list is not empty
            if (clientInfoBO.getResourceList().isEmpty())
                throw new KgoApiException("getRedirectMyDataUrl Resource list is empty");

            String resourceListStr = clientInfoBO.getResourceList().toString().replace("[", "").replace("]", "").replaceAll(", ", ":");

            String clientSecretKey = clientInfoBO.getClientSecretKey() + clientInfoBO.getClientSecretKey();

            String personalIdEncrypt = Base64Utils.encodeToString(AESUtil.encrypt(userInfoBO.getPid(), clientInfoBO.getClientIv(), clientSecretKey.getBytes(), ALG));

            // myDataUrl +
            // service/{client_id}/{resource_id_base64encoded_string}/{tx_id}?returnUrl={sp_return_url}&pid={personalId}
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(myDataUrl);
            stringBuilder.append("service/");
            stringBuilder.append(clientInfoBO.getClientId());
            stringBuilder.append("/");
            stringBuilder.append(Base64Utils.encodeToString(resourceListStr.getBytes("UTF-8")));
            stringBuilder.append("/");
            stringBuilder.append(txId);
            stringBuilder.append("?returnUrl=");
            stringBuilder.append(spReturnUrl);
            stringBuilder.append("&pid=");
            stringBuilder.append(personalIdEncrypt);

            return stringBuilder.toString();
        } catch (KgoApiException ex) {
            LOGGER.error("Get KgoApiException from isCertifiedId ...", ex);
        } catch (Exception ex) {
            LOGGER.error("Get can't handler exception from isCertifiedId ...", ex);
        }

        return "";
    }

    @Override
    public boolean isCertifiedId(String txId, UserInfoBO userInfoBO, ClientInfoBO clientInfoBO, String pkcs7) {
        LOGGER.info("Start certified txId=" + txId);

        boolean isCertifiedId;

        try {
            checkField(txId, clientInfoBO);

            // Check user info
            if (userInfoBO.getPid().isEmpty() || userInfoBO.getBirthday().isEmpty())
                throw new KgoApiException("User pid or birthday is null or empty");

            // Check pkcs7
            if (pkcs7.isEmpty())
                throw new KgoApiException("Pkcs7 is null or empty");

            GetSpSignatureParameterRq getSpSignatureParameterRq = new GetSpSignatureParameterRq();
            getSpSignatureParameterRq.setTxId(txId);

            GetSpSignatureParameterRs getSpSignatureParameterRes = callMyDataService.getSignatureParameterByTxId(getSpSignatureParameterRq);

            if (getSpSignatureParameterRes.getHttpStatus() != HttpStatus.OK.value())
                throw new KgoApiException("Get sp signature parameter from MyData error, HttpStatus is " + getSpSignatureParameterRes.getHttpStatus());

            userInfoBO.setSalt(getSpSignatureParameterRes.getSalt());

            String clientSecretKey = clientInfoBO.getClientSecretKey() + clientInfoBO.getClientSecretKey();

            String userInfoJsonData = JsonUtil.toJSONString(userInfoBO);

            // user json data do aes hash
            byte[] plainTextEncrypt = AESUtil.encrypt(userInfoJsonData, clientInfoBO.getClientIv(), clientSecretKey.getBytes(), ALG);

            VerifySpIdRq verifySpIdRq = new VerifySpIdRq();
            verifySpIdRq.setTxId(txId);
            verifySpIdRq.setData(Base64Utils.encodeToString(plainTextEncrypt));
            verifySpIdRq.setPkcs7(pkcs7);

            VerifySpIdRs verifySpIdRs = callMyDataService.verifyByTxIdAndDataAndPkcs7File(verifySpIdRq);

            if (verifySpIdRs.getHttpStatus() != HttpStatus.OK.value())
                throw new KgoApiException("Verify sp id from MyData error, HttpStatus is " + getSpSignatureParameterRes.getHttpStatus());

            isCertifiedId = true;
        } catch (KgoApiException ex) {
            LOGGER.error("Get KgoApiException from isCertifiedId ...", ex);

            isCertifiedId = false;
        } catch (Exception ex) {
            LOGGER.error("Get can't handler exception from isCertifiedId ...", ex);

            isCertifiedId = false;
        }

        return isCertifiedId;
    }

    @Override
    public void myDataNotifySp(SpApplyRq spApplyRq) {
        LOGGER.info("myDataNotifySp...");
        KgoMydataCallbackLog kgoMydataCallbackLog = new KgoMydataCallbackLog();
        kgoMydataCallbackLog.setCreateTime(DateUtil.getCurrentTimestamp());
        LOGGER.info("myDataNotifySp 通知sp時開始記錄kgoMydataCallbackLog Start myData notify sp can download data...");
        kgoMydataCallbackLog.setTxId(spApplyRq.getTxId());
        kgoMydataCallbackLog.setPermissionTicket(spApplyRq.getPermissionTicket());
        kgoMydataCallbackLog.setSecretKey(spApplyRq.getSecretKey());
        LOGGER.info("myDataNotifySp spApplyRq.getUnableToDeliver() :" + spApplyRq.getUnableToDeliver());
        if (spApplyRq.getUnableToDeliver() != null && !spApplyRq.getUnableToDeliver().isEmpty()) {
            kgoMydataCallbackLog.setUnableToDeliver(spApplyRq.getUnableToDeliver().toString());
            LOGGER.info("myDataNotifySp spApplyRq.getUnableToDeliver().toString() :" + spApplyRq.getUnableToDeliver().toString());
        }
        kgoMydataCallbackLog.setUpdateTime(DateUtil.getCurrentTimestamp());
        LOGGER.info("myDataNotifySp kgoMydataCallbackLog :" + kgoMydataCallbackLog);
        kgoMydataCallbackLogRepository.save(kgoMydataCallbackLog);
        LOGGER.info("myDataNotifySp 通知sp時開始記錄kgoMydataCallbackLog Start myData notify sp can download data...end");
    }

    @Override
    public MyDataServicePermissionTicketRs getPermissionTicketAndSecretKey(MyDataServicePermissionTicketRq myDataServicePermissionTicketRq) {
        LOGGER.info("getPermissionTicketAndSecretKey ...start");
        KgoMydataCallbackLog kgoMydataCallbackLog = kgoMydataCallbackLogRepository.findByTxId(myDataServicePermissionTicketRq.getTxId());
        if (kgoMydataCallbackLog == null)
            throw new KgoApiException("Can't find permissionTicket by txId = " + myDataServicePermissionTicketRq.getTxId());

        MyDataServicePermissionTicketRs myDataServicePermissionTicketRs = new MyDataServicePermissionTicketRs();
        myDataServicePermissionTicketRs.setPermissionTicket(kgoMydataCallbackLog.getPermissionTicket());
        myDataServicePermissionTicketRs.setSecretKey(kgoMydataCallbackLog.getSecretKey());
        LOGGER.info("getPermissionTicketAndSecretKey ...kgoMydataCallbackLog.getSecretKey()=" + kgoMydataCallbackLog.getSecretKey());
        LOGGER.info("getPermissionTicketAndSecretKey ...kgoMydataCallbackLog.getPermissionTicket()()=" + kgoMydataCallbackLog.getPermissionTicket());
        LOGGER.info("getPermissionTicketAndSecretKey ...end");
        return myDataServicePermissionTicketRs;
    }

    @Override
    public MyDataServiceDownloadRs downloadMyData(String txId, String clientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey) {
        LOGGER.info(">>>>>下載MyData處理完成資料  downloadMyData setSession(" + txId + ") >>>>> ");
        MyDataServiceDownloadRs serviceDownloadRs = this.getSession(txId);
        if (ObjectUtils.isEmpty(serviceDownloadRs)) {
            serviceDownloadRs = downloadMyDataService(txId, clientId, clientIv, clientSecretKey, permissionTicket, secretKey);
            if (ObjectUtils.isNotEmpty(serviceDownloadRs.getDpDataMap()) && !serviceDownloadRs.getDpDataMap().isEmpty()) {
                LOGGER.info(">>>>>下載MyData處理完成資料  downloadMyData setSession(" + txId + ") >>>>> ");
                setSession(txId, serviceDownloadRs);
            }
        } else {
            LOGGER.info(">>>>> 下載MyData處理完成資料 downloadMyData getSession(" + txId + ") >>>>> ");
        }
        return serviceDownloadRs;
    }

    @Override
    public MyDataServiceDownloadRs downloadMyDataDataBase(String txId, String clientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey) {
        LOGGER.info("downloadMyDataDataBase start txId=" + txId + ",clientId=" + clientId);
        MyDataServiceDownloadRs serviceDownloadRs = this.getSession(txId);
        if (ObjectUtils.isEmpty(serviceDownloadRs)) {
            serviceDownloadRs = downloadMyDataServiceFile(txId, clientId, clientIv, clientSecretKey, permissionTicket, secretKey);
            if (ObjectUtils.isNotEmpty(serviceDownloadRs.getDpDataMap()) && !serviceDownloadRs.getDpDataMap().isEmpty()) {
                LOGGER.info(">>>>> downloadMyDataDataBase setSession(" + txId + ") >>>>> ");
                setSession(txId, serviceDownloadRs);
            }
        } else {
            LOGGER.info(">>>>> downloadMyDataDataBase getSession(" + txId + ") >>>>> ");
        }
        return serviceDownloadRs;
    }

    private MyDataServiceDownloadRs downloadMyDataService(String txId, String clientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey) {
        LOGGER.info("downloadMyData Service Start download MyData data txId=" + txId + ",clientId=" + clientId + ",clientIv=" + clientIv + ",clientSecretKey=" + clientSecretKey + ",permissionTicket=" + permissionTicket + ",secretKey=" + secretKey);

        MyDataServiceDownloadRs myDataServiceDownloadRs = new MyDataServiceDownloadRs();
        myDataServiceDownloadRs.setTxId(txId);

        try {
            GetServiceDataRq getServiceDataRq = new GetServiceDataRq();
            getServiceDataRq.setTxId(txId);
            getServiceDataRq.setPermissionTicket(permissionTicket);
            getServiceDataRq.setClientId(clientId);

            GetServiceDataRs getServiceDataRs = callMyDataService.getServiceDataByTxIdAndPermissionTicket(getServiceDataRq);

            if (getServiceDataRs == null || (getServiceDataRs.getHttpStatus() != HttpStatus.OK.value() && getServiceDataRs.getHttpStatus() != HttpStatus.CREATED.value()
                    && getServiceDataRs.getHttpStatus() != HttpStatus.TOO_MANY_REQUESTS.value()))
                throw new KgoApiException("downloadMyDataService Download data from MyData error, HttpStatus is " + getServiceDataRs.getHttpStatus());

            if (org.apache.commons.lang3.StringUtils.isNotBlank(getServiceDataRs.getJwe())) {
                String encryptKey = clientSecretKey + clientSecretKey;

                byte[] jweSecretKey = AESUtil.decrypt(clientIv, ALG, Base64Utils.decode(secretKey.getBytes()), encryptKey.getBytes());

                Map<String, ServiceDataBO> map = getAllDpData(getServiceDataRs.getJwe(), new String(jweSecretKey), txId, permissionTicket);

                myDataServiceDownloadRs.setDelaySeconds(getServiceDataRs.getDelaySeconds());

                if (!map.isEmpty()) {
                    myDataServiceDownloadRs.setDpDataMap(map);
                }

            } else {
                myDataServiceDownloadRs.setDelaySeconds(getServiceDataRs.getDelaySeconds());
            }
        } catch (KgoApiException ex) {
            LOGGER.error("downloadMyDataService Get KgoApiException from downloadMyData ...", ex);

        } catch (Exception ex) {
            LOGGER.error("downloadMyDataService Get can't handler exception from downloadMyData ...", ex);

        }

        return myDataServiceDownloadRs;
    }

    private String getSessionKey(String txId) {
        return String.format("%s_%s_%s", "SESSION", "MYDATE", txId);
    }

    private void setSession(String txId, MyDataServiceDownloadRs data) {
        String sessionName = getSessionKey(txId);
        super.saveToSession(sessionName, data);
    }

    private MyDataServiceDownloadRs getSession(String txId) {
        String sessionName = getSessionKey(txId);
        LOGGER.info("getSession sessionName=" + sessionName);
        return super.loadFromSession(sessionName, MyDataServiceDownloadRs.class);
    }

    private void checkField(String txId, ClientInfoBO clientInfoBO) {
        LOGGER.info("checkField txId=" + txId + ",clientInfoBO=" + clientInfoBO);
        // Check txId
        if (txId.isEmpty())
            throw new KgoApiException("checkField TxId is null or empty");

        // Check client info
        if (clientInfoBO.getClientId().isEmpty() || clientInfoBO.getClientIv().isEmpty() || clientInfoBO.getClientSecretKey().isEmpty())
            throw new KgoApiException("checkField Client info is null or empty");

        // Check txId duplicate
        KgoMydataTx kgoMydataTx = kgoMydataTxRepository.findByTxId(txId);
        if (kgoMydataTx != null)
            throw new KgoApiException("TxId is duplicate");
    }

    private Map<String, ServiceDataBO> getAllDpData(String jwe, String secretKey, String txId, String permissionTicket) {
        LOGGER.info("getAllDpData...jwe=" + jwe + ",secretKey=" + secretKey + ",txId=" + txId + ",permissionTicket=" + permissionTicket);
        File JweTempDir = null;

        try {
            // Decode and Vaild JWE Data
            JSONObject jweJsonData = getJweDecodeData(jwe, secretKey);

            // Create and Unzip JWE Data
            JweTempDir = getJweUnZip(txId, permissionTicket, secretKey, jweJsonData);

            // Get MyData Manifest to DpFileList
            List<MyDataManifestBO> myDataManifest = getMyDataManifestBos(JweTempDir);

            // Unzip Dp.zip and check Dp Manifest save to DB
            return getDpData(JweTempDir, myDataManifest, txId, permissionTicket);
//            getDpData(JweTempDir, mydataManifest, txId, permissionTicket, resourceId);
        } catch (Exception e) {
            LOGGER.error("Save MyData To DB Error", e);
        } finally {
            delTempJwe(JweTempDir);
        }

        return new HashMap<>();
    }

    /**
     * 檢驗Manifest.xml 將code為200 的DP整理成list
     *
     * @param targetDir
     * @return
     * @throws IOException
     */
    private List<MyDataManifestBO> getMyDataManifestBos(File targetDir) throws IOException {
        LOGGER.info("getMyDataManifestBos 將code為200 的DP整理成list start");
        XStream xStream = getXStream();
        xStream.alias(MANIFEST_VALUE_TAG, MyDataManifestBO.class);
        String xmlStr = new String(Files.readAllBytes(getManifestPath(targetDir)), UTF_8);
        List<MyDataManifestBO> myDataManifestList = (List<MyDataManifestBO>) xStream.fromXML(xmlStr, new MyDataManifestBO());
        myDataManifestList = myDataManifestList.stream().filter(e -> "200".equals(e.getCode())).collect(Collectors.toList());
        LOGGER.info("getMyDataManifestBos 將code為200 的DP整理成list end, myDataManifestList.size()=" + myDataManifestList.size());
        return myDataManifestList;
    }

    /**
     * 將JWE轉成Zip檔並解壓縮
     *
     * @param jweJsonData
     * @return
     * @throws KgoApiException
     */
    private File getJweUnZip(String txId, String permissionTicket, String secretKey, JSONObject jweJsonData) throws KgoApiException {
        try {
            LOGGER.info("getJweUnZip 將JWE轉成Zip檔並解壓縮 start..");
            KgoMydataFile kgoMydataFile = new KgoMydataFile();
            kgoMydataFile.setTxId(txId);
            kgoMydataFile.setPermissionTicket(permissionTicket);
            kgoMydataFile.setCreateTime(DateUtil.getCurrentTimestamp());
//            String filename = jweJsonData.getAsString("filename");
            String filename = txId + ZIP_FILE;
            String data = jweJsonData.getAsString("data");

            data = data.substring("application/zip;data:".length());
            kgoMydataFile.setMyDataFile(data);
            byte[] encryptedData = Base64.getUrlDecoder().decode(data);

            //kgoMydataFile.setMyDataFile(new String(encryptedData));

            File targetDir = getJweZipFile(txId, filename, encryptedData);

            kgoMydataFileRepository.save(kgoMydataFile);
            LOGGER.info("getJweUnZip 將JWE轉成Zip檔並解壓縮 end..");
            return targetDir;
        } catch (Exception e) {
            LOGGER.error("Jwe UnZip Error");
            throw new KgoApiException(e);
        }
    }

    public File getJweUnZip(String txId, String permissionTicket) throws KgoApiException {
        LOGGER.info("getJweUnZip txId=" + txId + ",permissionTicket=" + permissionTicket);
        return getJweUnZip(txId, permissionTicket, "");
    }

    private File getJweUnZip(String txId, String permissionTicket, String secretKey) throws KgoApiException {
        LOGGER.info("getJweUnZip txId=" + txId + ",permissionTicket=" + permissionTicket + ",secretKey=" + secretKey);
        try {
            String filename = txId + ZIP_FILE;
            KgoMydataFile kgoMydataFile = kgoMydataFileRepository.findByTxIdAndPermissionTicket(txId, permissionTicket);

            if (kgoMydataFile != null) {
                // Base64Utils.encodeToString(AESUtil.encrypt(new String(encryptedData), ALG,
                // secretKey.getBytes()))
                byte[] encryptedData = Base64.getUrlDecoder().decode(kgoMydataFile.getMyDataFile());
                //byte[] encryptedData = kgoMydataFile.getMyDataFile().getBytes(UTF_8);
                return getJweZipFile(txId, filename, encryptedData);
            } else {
                throw new KgoApiException(String.format("無 KgoMydataFile txid:%s, permissionTicket:%s", txId, permissionTicket));
            }
        } catch (Exception e) {
            LOGGER.error("Jwe UnZip Error \n" + e.getMessage(), e);
            throw new KgoApiException(e);
        }
    }

    private MyDataServiceDownloadRs downloadMyDataServiceFile(String txId, String ClientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey) {
        LOGGER.info("Start download MyData data");

        MyDataServiceDownloadRs myDataServiceDownloadRs = new MyDataServiceDownloadRs();
        myDataServiceDownloadRs.setTxId(txId);

        try {
            GetServiceDataRq getServiceDataRq = new GetServiceDataRq();
            getServiceDataRq.setTxId(txId);
            getServiceDataRq.setPermissionTicket(permissionTicket);

            String encryptKey = clientSecretKey + clientSecretKey;

            byte[] jweSecretKey = AESUtil.decrypt(clientIv, ALG, Base64Utils.decode(secretKey.getBytes()), encryptKey.getBytes());

            Map<String, ServiceDataBO> map = getAllDpData(new String(jweSecretKey), txId, permissionTicket);

            myDataServiceDownloadRs.setDelaySeconds("0");

            if (!map.isEmpty()) {
                myDataServiceDownloadRs.setDpDataMap(map);
            }
        } catch (KgoApiException ex) {
            LOGGER.error("Get KgoApiException from downloadMyData ...", ex);

        } catch (Exception ex) {
            LOGGER.error("Get can't handler exception from downloadMyData ...", ex);

        }

        return myDataServiceDownloadRs;
    }

    private Map<String, ServiceDataBO> getAllDpData(String secretKey, String txId, String permissionTicket) {
        File JweTempDir = null;

        try {
            LOGGER.info("getAllDpData ..start");
            // Create and Unzip JWE Data
            JweTempDir = getJweUnZip(txId, permissionTicket, secretKey);

            // Get MyData Manifest to DpFileList
            List<MyDataManifestBO> myDataManifest = getMyDataManifestBos(JweTempDir);

            // Unzip Dp.zip and check Dp Manifest save to DB
            return getDpData(JweTempDir, myDataManifest, txId, permissionTicket);
//            getDpData(JweTempDir, mydataManifest, txId, permissionTicket, resourceId);
        } catch (Exception e) {
            LOGGER.error("Save MyData To DB Error", e);
        } finally {
            delTempJwe(JweTempDir);
        }

        return null;
    }

    private File getJweZipFile(String txId, String filename, byte[] encryptedData) throws IOException {
        LOGGER.info("getJweZipFile start");
        try {
            File targetDir = new File(MyDataJweTempPath + StringUtils.stripFilenameExtension(filename) + "/");

            FileUtil.createFile(targetDir, filename, encryptedData);

            LOGGER.info("Path: " + Paths.get(targetDir + File.separator + filename).toFile());
            File jweZipFile = Paths.get(targetDir + File.separator + filename).toFile();
            /**
             * Geo 20221010 add_Jim
             * save myData zip hash
             */
            saveZipHashWithMyDataCertificateSha256(txId, jweZipFile);

            FileUtil.unzipFile(jweZipFile, targetDir, null);
            FileUtils.forceDelete(jweZipFile);
            LOGGER.info("getJweZipFile end");
            return targetDir;
        } catch (Exception e) {
            LOGGER.error("Jwe UnZip Error");
            throw new KgoApiException(e);
        }

    }

    /**
     * GEO 20221009 add_Jim
     * Get mix sha256 from MyData Zip and MyDataCertificate
     */
    public void saveZipHashWithMyDataCertificateSha256(String txId, File jweZipFile) throws Exception {
        String myDataCertificateSha256 = myDataService.getMyDataCertificateSha256();
        String zipFileSha256 = commonService.getFileSHAEncoding(jweZipFile.getAbsolutePath(), "SHA-256");
        String mixSha256 = commonService.getStringSHAEncoding(myDataCertificateSha256 + zipFileSha256, "SHA-256");
        geoKgoCaseMainZipHashRepository.save(new GeoKgoCaseMainZipHash(txId, zipFileSha256, mixSha256));
        File file = new File("D:\\var\\" + txId + ".txt");
        if (!file.exists())
            file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(zipFileSha256);
        printWriter.close();
    }

    /**
     * 解碼及認證JWE Data
     *
     * @param jweData
     * @param secretKey
     * @return
     * @throws KgoApiException
     */
    private JSONObject getJweDecodeData(String jweData, String secretKey) throws KgoApiException {
        LOGGER.info("getJweDecodeData 解碼及認證JWE Data");
        if (JweUtil.checkJweAuthTag(secretKey, jweData)) {
            return JweUtil.decode(secretKey, jweData).toJSONObject();
        } else
            throw new KgoApiException("Check Jwe AuthTag Failed");
    }

    /**
     * 將 DP FileList 檢查 驗證
     *
     * @param JweTempDir
     * @param
     * @return
     */
    private Map<String, ServiceDataBO> getDpData(File JweTempDir, List<MyDataManifestBO> myDataManifestList, String txId, String permissionTicket) {
        LOGGER.info("getDpData  將 DP FileList 檢查 驗證 start");
        Map<String, ServiceDataBO> map = new HashMap<>();

        XStream dpXStream = getXStream();

        dpXStream.alias(MANIFEST_VALUE_TAG, DpManifestBO.class);

        myDataManifestList.stream().forEach(myDataManifestBO -> {
            try {
                String dpResourceId = myDataManifestBO.getResource_id();

                File dpFilePath = new File(JweTempDir, dpResourceId);

                try {
                    FileUtil.unzipFile(new File(JweTempDir, myDataManifestBO.getFilename()), dpFilePath, null);
                } catch (IOException e) {
                    LOGGER.error("unzipFile error: " + e.getMessage());
                }

                // 驗證 manifest.xml 的完整性
                if (verifySignature(dpFilePath)) {

                    String manifestPath = String.format("%s%s%s%s%s", dpFilePath, File.separator, META_INFO, File.separator, MANIFEST_FILE);
                    String dpXmlStr = new String(Files.readAllBytes(new File(manifestPath).toPath()), UTF_8);

                    // String dpXmlStr = new String(
//							Files.readAllBytes(new File(dpFilePath + "\\META-INFO\\" + MANIFEST_FILE).toPath()), UTF_8);

                    LOGGER.info("myDataManifestBO = {}", JsonUtil.toJSONString(myDataManifestBO));

                    LOGGER.info("dpXmlStr = {}", dpXmlStr);

                    List<DpManifestBO> dpFileList = (List<DpManifestBO>) dpXStream.fromXML(dpXmlStr, new DpManifestBO());

                    ServiceDataBO serviceDataBO = new ServiceDataBO();

                    // 驗證各別的資料檔案的完整性
                    if (dpFileList.stream().allMatch(DpManifestBO -> FileUtil.checkFileSha256(new File(dpFilePath, DpManifestBO.getFilename()), DpManifestBO.getDigest()))) {
                        Files.list(Paths.get(dpFilePath.getPath())).forEach(path -> {
                            try {
                                String fileExtension = "";

                                String dpJsonString;

                                byte[] dpPdfData;

                                byte[] dpCsvData;

                                if (path.toString().endsWith(JSON_FILE))
                                    fileExtension = JSON_FILE;

                                if (path.toString().endsWith(PDF_FILE))
                                    fileExtension = PDF_FILE;

                                if (path.toString().endsWith(CSV_FILE))
                                    fileExtension = CSV_FILE;

                                File file = new File(path.toString());
//								File file = new File(
//										dpFilePath + "\\" + myDataManifestBO.getResource_name() + fileExtension);

                                if (JSON_FILE.equals(fileExtension)) {
                                    dpJsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                                    serviceDataBO.getJsonData().add(new ServiceDataFileBO(file.getName(), dpJsonString));
                                }

                                if (PDF_FILE.equals(fileExtension)) {
                                    dpPdfData = Base64.getEncoder().encode(FileUtils.readFileToByteArray(file));
                                    serviceDataBO.getPdfFile().add(new ServiceDataFileBO(file.getName(), new String(dpPdfData)));
                                }

                                if (CSV_FILE.equals(fileExtension)) {
                                    dpCsvData = Base64.getEncoder().encode(FileUtils.readFileToByteArray(file));
                                    serviceDataBO.getCvsFile().add(new ServiceDataFileBO(file.getName(), new String(dpCsvData)));
                                }
                            } catch (Exception ex) {
                                LOGGER.error("Get dp file error", ex);
                            }
                        });
                    }

                    map.put(dpResourceId, serviceDataBO);
                } else {
                    LOGGER.debug("verify Dp Signature Failed", dpResourceId);
                }
            } catch (IOException ex) {
                LOGGER.error("Get dp file error", ex);
            } catch (Exception ex) {
                LOGGER.error("Get dp file other error", ex);
            }
        });

        return map;
    }

    /**
     * 驗證數位簽章是否合法
     *
     * @param dpPackDir
     * @return
     */
    public boolean verifySignature(File dpPackDir) {
        LOGGER.info("verifySignature start");
        try {
            File metaInfoDir = new File(dpPackDir, "META-INFO");

            // 憑證檔
            File certFile = new File(metaInfoDir, "certificate.cer");

            // 從憑證檔中取出憑證內容
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(new FileInputStream(certFile));

            // 從憑證檔中取出公鑰
            PublicKey publicKey = cert.getPublicKey();
            LOGGER.info(" 從憑證檔中取出公鑰 verifySignature publicKey=" + publicKey);
            // 數位簽章檔
            File signatureFile = new File(metaInfoDir, "manifest.sha256withrsa");

            // 數位簽章檔不存在或檔名錯誤
            if (!signatureFile.exists()) {
                LOGGER.info("The digital signature file does not exist or the file name is wrong. {}", signatureFile.getPath());
                return false;
            }
            byte[] signedData = Files.readAllBytes(signatureFile.toPath());

            // 被驗證的原始檔 manifest.xml
            File manifestFile = new File(metaInfoDir, "manifest.xml");

            // manifest不存在或檔名錯誤
            if (!manifestFile.exists()) {
                LOGGER.info("manifest does not exist or the file name is wrong.");
                return false;
            }

            byte[] bs = Files.readAllBytes(manifestFile.toPath());

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(bs);
            LOGGER.info("verifySignature end");
            return signature.verify(signedData);
        } catch (Exception ex) {
            LOGGER.error("Get verifySignature Error ...", ex);
        }

        return false;
    }

    /**
     * XML轉換
     *
     * @return
     */
    private XStream getXStream() {
        LOGGER.info("getXStream XML轉換");
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{List.class, DpManifestBO.class, MyDataManifestBO.class});
        xStream.alias(MANIFEST_LIST_TAG, List.class);
        xStream.autodetectAnnotations(true);
        return xStream;
    }

    /**
     * 取得Manifest.xml檔案路徑
     *
     * @param FilePath
     * @return
     */
    private Path getManifestPath(File FilePath) {
        LOGGER.info("getManifestPath 取得Manifest.xml檔案路徑");
        File metainfo = new File(FilePath, META_INFO);
        File manifest = new File(metainfo, MANIFEST_FILE);

        return manifest.toPath();
    }

    /**
     * 刪除JWE ZIP 解壓資料夾
     *
     * @param JweTempDir
     */
    private void delTempJwe(File JweTempDir) {
        LOGGER.info("delTempJwe  刪除JWE ZIP");
        try {
            if (JweTempDir != null)
                FileUtils.deleteDirectory(JweTempDir);
        } catch (IOException e) {
            LOGGER.error("delTempJwe Error", e);
        }
    }

    @Override
    public MyDataServiceDownloadRs downloadMyDataTemp(String txId, String ClientId, String clientIv, String clientSecretKey, String permissionTicket, String secretKey) {
        return downloadMyDataTemp(txId);
    }

    @Override
    public MyDataServiceDownloadRs downloadMyDataTemp(String txId) {
        LOGGER.info("downloadMyDataTemp ..start txId=" + txId);
        MyDataServiceDownloadRs serviceDownloadRs = this.getSession(txId);
        if (ObjectUtils.isEmpty(serviceDownloadRs)) {
            serviceDownloadRs = downloadMyDataService(txId);
            if (ObjectUtils.isNotEmpty(serviceDownloadRs.getDpDataMap()) && !serviceDownloadRs.getDpDataMap().isEmpty()) {
                LOGGER.info(">>>>> setSession(" + txId + ") >>>>> ");
                setSession(txId, serviceDownloadRs);
            }
        } else {
            LOGGER.info(">>>>> getSession(" + txId + ") >>>>> ");
        }
        return serviceDownloadRs;

    }

    private MyDataServiceDownloadRs downloadMyDataService(String txId) {
        LOGGER.info("downloadMyDataService Start download MyData data,txId=" + txId);

        GetServiceDataRs getServiceDataRs = new GetServiceDataRs();
        getServiceDataRs.setHttpStatus(HttpStatus.OK.value());

        if (getServiceDataRs == null || (getServiceDataRs.getHttpStatus() != HttpStatus.OK.value() && getServiceDataRs.getHttpStatus() != HttpStatus.CREATED.value()
                && getServiceDataRs.getHttpStatus() != HttpStatus.TOO_MANY_REQUESTS.value()))
            throw new KgoApiException("downloadMyDataService Download data from MyData error, HttpStatus is " + getServiceDataRs.getHttpStatus());

        MyDataServiceDownloadRs myDataServiceDownloadRs = new MyDataServiceDownloadRs();
        myDataServiceDownloadRs.setTxId(txId);

        try {
            if (getServiceDataRs.getHttpStatus() == HttpStatus.TOO_MANY_REQUESTS.value()) {
                myDataServiceDownloadRs.setDelaySeconds(getServiceDataRs.getDelaySeconds());
                LOGGER.info("downloadMyDataService getServiceDataRs.getDelaySeconds()=" + getServiceDataRs.getDelaySeconds());
            } else {
                GetServiceDataRq getServiceDataRq = new GetServiceDataRq();
                getServiceDataRq.setTxId(txId);
                getServiceDataRq.setPermissionTicket("");

                Map<String, ServiceDataBO> map = getAllDpData(txId);

                if (ObjectUtils.isNotEmpty(map)) {
                    myDataServiceDownloadRs.setDelaySeconds("0");
                    if (!map.isEmpty()) {
                        myDataServiceDownloadRs.setDpDataMap(map);
                    }
                }
                LOGGER.info("downloadMyDataService myDataServiceDownloadRs.getDelaySeconds()=" + myDataServiceDownloadRs.getDelaySeconds());
            }
        } catch (KgoApiException ex) {
            LOGGER.error("Get KgoApiException from downloadMyData ...", ex);

        } catch (Exception ex) {
            LOGGER.error("Get can't handler exception from downloadMyData ...", ex);

        }

        return myDataServiceDownloadRs;
    }

    private Map<String, ServiceDataBO> getAllDpData(String txId) {
        LOGGER.info("getAllDpData start...");
        File JweTempDir = null;

        try {
            String filename = txId + ZIP_FILE;
            LOGGER.info("getAllDpData filename=" + filename);
            JweTempDir = new File(MyDataJweTempPath + StringUtils.stripFilenameExtension(filename) + "/");
            LOGGER.info("getAllDpData JweTempDir.getName()=" + JweTempDir.getName());
            // Get MyData Manifest to DpFileList
            List<MyDataManifestBO> myDataManifest = getMyDataManifestBos(JweTempDir);

            // Unzip Dp.zip and check Dp Manifest save to DB
            return getDpData(JweTempDir, myDataManifest, txId, "");
//            getDpData(JweTempDir, mydataManifest, txId, permissionTicket, resourceId);
        } catch (Exception e) {
            LOGGER.error("Save MyData To DB Error", e);
        } finally {
            delTempJwe(JweTempDir);
        }

        return null;
    }

}