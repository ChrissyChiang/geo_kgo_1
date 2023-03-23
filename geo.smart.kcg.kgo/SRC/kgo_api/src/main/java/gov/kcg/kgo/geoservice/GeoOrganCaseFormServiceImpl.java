package gov.kcg.kgo.geoservice;

import com.alibaba.fastjson.JSON;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.*;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.*;
import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geomodel.Geo1999ItemsMainModel;
import gov.kcg.kgo.geomodel.Geo1999ItemsSubsModel;
import gov.kcg.kgo.geoviewmodel.frontend.rq.MyDataGenDataRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataGenDataRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataModelRs;
import gov.kcg.kgo.georepository.GeoKgoBlackListRepository;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.*;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.LandNumRs;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.bean.DataModel;
import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;
import gov.kcg.kgo.service.impl.KgoBaseServiceImpl;
import gov.kcg.kgo.service.impl.helper.*;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.compoent.SelectListItem;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.*;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionCColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveFileViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.*;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.*;
import gov.kcg.kgo.viewModel.mydata.bo.ClientInfoBO;
import gov.kcg.kgo.viewModel.mydata.bo.ServiceDataBO;
import gov.kcg.kgo.viewModel.mydata.bo.ServiceDataFileBO;
import gov.kcg.kgo.viewModel.mydata.bo.UserInfoBO;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServiceDownloadRs;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServicePermissionTicketRq;
import gov.kcg.kgo.viewModel.mydata.vo.service.MyDataServicePermissionTicketRs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class GeoOrganCaseFormServiceImpl extends KgoBaseServiceImpl implements CaseFormService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoOrganCaseFormServiceImpl.class);
    // 算法/加密模式/數據填充方式
    private static final String ALG = "AES/CBC/PKCS5Padding";

    private final boolean isDebug = false;

    private final String KAPI_MYDATA_CLIENTID = "KAPI.Disability";

    @Value("${mydata.downloadfile.use.temp}")
    private boolean downloadFileUseTemp;

    @Value("${fontend.case.search.link}")
    private String frontendCaseSearchLink;

    @Value("${fontend.url}")
    private String frontendLinkUrl;

    /**
     * Repository
     */
    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoCasesetGroupRepository kgoCasesetGroupRepository;

    @Autowired
    private KgoCasesetColumnRepository kgoCasesetColumnRepository;

    @Autowired
    private KgoCasesetColumnChildRepository kgoCasesetColumnChildRepository;

    @Autowired
    private KgoCountyRepository kgoCountyRepository;

    @Autowired
    private KgoZipRepository kgoZipRepository;

    @Autowired
    private KgoCasesetUnitRepository kgoCasesetUnitRepository;

    @Autowired
    private KgoCasesetAreaRepository kgoCasesetAreaRepository;

    @Autowired
    private KgoOrganRepository kgoOrganRepository;

    @Autowired
    private KgoMydataServiceRepository kgoMydataServiceRepository;

    @Autowired
    private KgoMydataServiceResourceRepository kgoMydataServiceResourceRepository;

    @Autowired
    private KgoMydataColumnRepository kgoMydataColumnRepository;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private KgoCaseDetailRepository kgoCaseDetailRepository;

    @Autowired
    private KgoKcdRepository kgoKcdRepository;

    @Autowired
    private KgoApiLogRepository kgoApiLogRepository;

    @Autowired
    private KgoUserRepository kgoUserRepository;

    @Autowired
    private KgoCasesetCheckRepository kgoCasesetCheckRepository;

    /**
     * Service
     */
    @Autowired
    private MyDataService myDataService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private MailUtil mailUtil;

    @Value("${backend.url}")
    private String backendUrl;

    @Autowired
    private freemarker.template.Configuration freemarkerConfig;

    @Autowired
    private PushService pushService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CallKcgCityApiService callKcgCityApiService;
    @Autowired
    private TpiFlowTaskRepository tpiFlowTaskRepository;
    @Autowired
    GeoKgoBlackListRepository geoKgoBlackListRepository;

    /**
     * Helper
     */
    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();
    private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

    /**
     * MyData Helper
     */
    // 戶政國民身分證影像
    private NationalIdCardMyDataHelper nationalIdCardHelper = NationalIdCardMyDataHelper.getInstance();
    // 個人戶籍資料
    private PersonalHouseholdRegistrationMyDataHelper personalHouseholdRegistrationMyDataHelper = PersonalHouseholdRegistrationMyDataHelper.getInstance();
    // 現戶全戶戶籍資料
    private CurrentHouseholdRegistrationMyDataHelper currentHouseholdRegistrationMyDataHelper = CurrentHouseholdRegistrationMyDataHelper.getInstance();
    // 親屬關係資料
    private KinshipInfoMyDataHelper kinshipInfoMyDataHelper = KinshipInfoMyDataHelper.getInstance();
    // 車籍資料
    private CarInfoMyDataHelper carInfoMyDataHelper = CarInfoMyDataHelper.getInstance();
    // 駕籍資料
    private DriverLicenseMyDataHelper driverLicenseMyDataHelper = DriverLicenseMyDataHelper.getInstance();
    // 公司負責人、董監事與經理人之公司登記資料
    private CompanyManagersMyDataHelper companyManagersMyDataHelper = CompanyManagersMyDataHelper.getInstance();
    // 低收及中低收列冊資料
    private LowIncomeInfoMyDataHelper lowIncomeInfoMyDataHelper = LowIncomeInfoMyDataHelper.getInstance();
    // 地籍及實價資料
    private CadastralAndActualPriceMyDataHelper cadastralAndActualPriceMyDataHelper = CadastralAndActualPriceMyDataHelper.getInstance();
    // 綜所稅及房地合一稅繳納、欠稅及退稅紀錄
    private ComprehensiveTaxPayRecordsMyDataHelper comprehensiveTaxPayRecordsMyDataHelper = ComprehensiveTaxPayRecordsMyDataHelper.getInstance();
    // 個人所得資料
    private PersonalIncomeTaxMyDataHelper personalIncomeTaxMyDataHelper = PersonalIncomeTaxMyDataHelper.getInstance();
    // 個人投退保資料
    private PersonalInsuranceInfoMyDataHelper personalInsuranceInfoMyDataHelper = PersonalInsuranceInfoMyDataHelper.getInstance();
    // 勞工保險被保險人投保資料(明細)
    private LaborInsuranceInsuranceInfoMyDataHelper laborInsuranceInsuranceInfoMyDataHelper = LaborInsuranceInsuranceInfoMyDataHelper.getInstance();
    // 財產資料
    private PropertyInfoMyDataHelper propertyInfoMyDataHelper = PropertyInfoMyDataHelper.getInstance();
    // 國民年金
    private NationalPensionMyDataHelper nationalPensionMyDataHelper = NationalPensionMyDataHelper.getInstance();
    // 保費繳納
    private PaymentOfPremiumMyDataHelper paymentOfPremiumMyDataHelper = PaymentOfPremiumMyDataHelper.getInstance();
    // 中低收入老人生活津貼資料
    private LowIncomeOldPersonMyDataHelper lowIncomeOldPersonMyDataHelper = LowIncomeOldPersonMyDataHelper.getInstance();
    // 身心障礙資格
    private DisabilityIdentificationMyDataHelper disabilityIdentificationMyDataHelper = DisabilityIdentificationMyDataHelper.getInstance();
    // 身心障礙者生活補助資料
    private DisabilityAllowanceMyDataHelper disabilityAllowanceMyDataHelper = DisabilityAllowanceMyDataHelper.getInstance();

    /**
     * 城市資料平台
     */
    private LowIncomeInfoKgoCityApiHelper lowIncomeInfoKgoCityApiHelper = LowIncomeInfoKgoCityApiHelper.getInstance();
    private LowAndMiddleIncomeInfoKgoCityApiHelper lowAndMiddleIncomeInfoKgoCityApiHelper = LowAndMiddleIncomeInfoKgoCityApiHelper.getInstance();
    private PsychosomaticDisorderKgoCityApiHelper psychosomaticDisorderKgoCityApiHelper = PsychosomaticDisorderKgoCityApiHelper.getInstance();

    /**
     * GEO 20210817 add for 1999 service
     **/
    @Autowired
    GeoCityExtService geoKcgCityExtService;


    /**
     * GEO 20211120
     *
     * @param rq
     * @return
     */
    @Override
    public HomeActionRs homeAction(HomeActionRq rq) {
        HomeActionViewForm viewForm = new HomeActionViewForm();
        HomeActionRs rs = new HomeActionRs();
        try {
            KgoCaseset kgoCaseset = this.getKgoCaseset(rq.getCaseSetId());
            // 非府內線上服務
            if (kgoCaseset.getIsOpenForOrgan() == null || kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {
                rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
                return rs;
            }

            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED));
                return rs;
            }

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId());
            if (!checkLoginBoolean) {
                rs.setError(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED));
                return rs;
            }

            viewForm = this.generateHomeActionViewForm(rq.getCaseSetId());
            return new HomeActionRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("homeAction error " + e.getMessage(), e);
        }
    } //homeAction

    @Override
    public MyDataActionUrlRs myDataModel2ActionUrl(MyDataModel2ActionUrlRq rq) {
        return null;
    }

    /**
     * GEO20211120
     *
     * @param rq
     * @return
     */
    @Override
    public MyDataActionUrlRs myDataActionUrl(MyDataActionUrlRq rq) {
        MyDataActionUrlViewForm viewForm = new MyDataActionUrlViewForm();
        MyDataActionUrlRs rs = new MyDataActionUrlRs();
        try {

            if (StringUtils.isBlank(rq.getCaseSetId()) || StringUtils.isBlank(rq.getPid())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            KgoCaseset kgoCaseset = this.getKgoCaseset(rq.getCaseSetId());
            // 非府內線上服務
            if (kgoCaseset.getIsOpenForOrgan() == null || kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {
                rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
                return rs;
            }

            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED));
                return rs;
            }

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId());
            if (!checkLoginBoolean) {
                rs.setError(new ErrorResult(KgoBackEndApiError.PERMISSION_DENIED));
                return rs;
            }

            Integer version = this.getCasesetMaxVersion(kgoCaseset.getCaseSetId());
            List<CasesetMydataColumnDto> casesetColumns = this.getCasesetRealMyDataColumns(rq.getCaseSetId(), version);
            CasesetMydataColumnDto casesetColumn = getKgoCasesetColumnDtoOne(casesetColumns);
            List<String> resourceList = this.getCasesetUseMyDataIds(casesetColumns);

            String txId = generateTxId();
            viewForm.setUrl(this.getRedirectMyDataUrl(txId, rq.getPid(), casesetColumn.getMyDataClientId(), resourceList, rq.getCaseSetId()));
            viewForm.setTxId(txId);
            return new MyDataActionUrlRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataActionUrl error " + e.getMessage(), e);
        } // try
    } //myDataActionUrl

    /**
     * GEO 20211120
     *
     * @param pid
     * @param myDataClientId
     * @return
     */
    @Override
    public MyDataActionUrlRs myDataActionUrl(String pid, String myDataClientId) {
        MyDataActionUrlViewForm viewForm = new MyDataActionUrlViewForm();

        try {
            if (StringUtils.isBlank(pid) || StringUtils.isBlank(myDataClientId)) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            List<String> resourceList = kgoMydataServiceResourceRepository.findByIdClientId(myDataClientId).stream().map(x -> x.getId().getMyDataId()).collect(Collectors.toList());

            String txId = generateTxId();
            viewForm.setUrl(this.getRedirectMyDataUrl(txId, pid, myDataClientId, resourceList, ""));
            viewForm.setTxId(txId);
            return new MyDataActionUrlRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataHomeAction error " + e.getMessage(), e);
        }
    } //myDataActionUrl

    /**
     * GEO 20211120
     *
     * @param myDataClientId
     * @param encryptTxId
     * @return
     */
    @Override
    public String decryptMyDataTxId(String myDataClientId, String encryptTxId) {
        // TODO Auto-generated method stub
        try {
            return this.decryptTxId(myDataClientId, encryptTxId);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataHomeAction error " + e.getMessage(), e);
        }
    } //decryptMyDataTxId

    /**
     * GEO 20211120
     *
     * @param myDataClientId
     * @param txId
     * @return
     */
    @Override
    public String encryptMyDataTxId(String myDataClientId, String txId) {
        try {
            return this.encryptTxId(myDataClientId, txId);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataHomeAction error " + e.getMessage(), e);
        }
    } //encryptMyDataTxId

    /**
     * GEO 20211120
     *
     * @param rq
     * @return
     */
    @Override
    public MyDataHomeActionRs myDataHomeAction(MyDataHomeActionRq rq) {
        MyDataHomeActionViewForm viewForm = new MyDataHomeActionViewForm();
        MyDataHomeActionRs rs = new MyDataHomeActionRs();
        try {
            // BeanUtils.copyProperties(dest, orig);
            if (StringUtils.isBlank(rq.getMyDataTxId()) && StringUtils.isBlank(rq.getTxId())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            KgoCaseset kgoCaseset = this.getKgoCaseset(rq.getCaseSetId());
            // 非府內線上服務
            if (kgoCaseset.getIsOpenForOrgan() == null || kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {
                rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
                return rs;
            }

            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                rs.setError(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED));
                return rs;
            }

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId());
            if (!checkLoginBoolean) {
                rs.setError(new ErrorResult(KgoBackEndApiError.PERMISSION_DENIED));
                return rs;
            }

            viewForm = this.generateActionViewForm(rq.getCaseSetId(), rq.getTxId(), rq.getMyDataTxId(), rq.getPId());

            if (isDebug) {
                LOGGER.info("\n >>>>>>>>>>>>myDataHomeAction >>>>>>>>> \n");
                LOGGER.info(JsonUtil.toJSONString(viewForm));
            }

            return new MyDataHomeActionRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataHomeAction error " + e.getMessage(), e);
        } finally {
            removeTxIdSession(rq.getTxId());
        }
    } //myDataHomeAction

    /**
     * GEO 20211120
     *
     * @param txId
     * @param myDataClientId
     * @return
     */
    @Override
    public MyDataServiceDownloadRs downloadMyData(String txId, String myDataClientId) {
        try {
            return this.getMyDataTxidDownloadFile(txId, myDataClientId);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataHomeAction error " + e.getMessage(), e);
        } finally {
            removeTxIdSession(txId);
        }
    } //downloadMyData

    /**
     * GEO 20211120
     *
     * @param txId
     * @param myDataClientId
     * @return
     */
    @Override
    public MyDataDownloadMageViewFromRs downloadMyDataMerge(String txId, String myDataClientId) {
        MyDataDownloadMageViewFrom viewForm = new MyDataDownloadMageViewFrom();
        try {
            MyDataServiceDownloadRs dataServiceDownloadRs = this.getMyDataTxidDownloadFile(txId, myDataClientId);
            //LOGGER.info(JSON.toJSONString("downloadMyDataMerge:  " + dataServiceDownloadRs));

            Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
            for (Entry<String, ServiceDataBO> entry : dataServiceDownloadRs.getDpDataMap().entrySet()) {

                Map<String, Object> myDataMap = getResourceMergeMyDataColumnsVal(entry.getKey(), entry.getValue());
                Map<String, Object> rtnMap = new HashMap<String, Object>();
                for (ColumnModel column : this.getResourceMergeMyDataColumns(entry.getKey())) {
                    rtnMap.put(column.getColumnId(), myDataMap.get(column.getMyDataColumn()));
                }

                //LOGGER.info("\n" + "downloadMyDataMerge:  " + entry.getKey() + ": \n" + JsonUtil.toJSONString(rtnMap));
                data.put(entry.getKey(), rtnMap);
            } // for (Entry<String, ServiceDataBO>

            viewForm.setData(data);
            return new MyDataDownloadMageViewFromRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("myDataHomeAction error " + e.getMessage(), e);
        } finally {
            removeTxIdSession(txId);
        }
    } //downloadMyDataMerge

    /**
     * GEO 20211120
     *
     * @param rq
     * @return
     */
    @Override
    public DownloadMyDataAttachmentRs downloadMyDataAttachment(DownloadMyDataAttachmentRq rq) {
        try {
            if (StringUtils.isBlank(rq.getColumnId()) || StringUtils.isBlank(rq.getCaseSetId()) || ObjectUtils.isEmpty(rq.getVersion())) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }

            KgoCaseset kgoCaseset = this.getKgoCaseset(rq.getCaseSetId());
            // 非府內線上服務
            if (kgoCaseset.getIsOpenForOrgan() == null || kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
            }

            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED));
            }

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId());
            if (!checkLoginBoolean) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.PERMISSION_DENIED));
            }

            KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
            kgoCasesetColumnPK.setCaseSetId(rq.getCaseSetId());
            kgoCasesetColumnPK.setVersion(rq.getVersion());
            kgoCasesetColumnPK.setColumnId(rq.getColumnId());
            KgoCasesetColumn kgoCasesetColumn = KgoUtil.getKgoCasesetColumnFromCashe(kgoCasesetColumnPK);

            if (ObjectUtils.isNotEmpty(kgoCasesetColumn)) {

                // 下載 MyData
                setCaseSetAndMyDataResourceColumns(rq.getCaseSetId(), rq.getVersion(), rq.getTxId(), "");

                Map<String, Map<String, Object>> myDataDownloadMap = getResourceTxIdCasesetMergeMyDataColumnsVal(rq.getTxId());
                if (ObjectUtils.isNotEmpty(myDataDownloadMap)) {
                    if (myDataDownloadMap.containsKey(kgoCasesetColumn.getMyDataId())) {
                        Map<String, Object> map = myDataDownloadMap.get(kgoCasesetColumn.getMyDataId());
                        String key = String.format("%s_$s", kgoCasesetColumn.getMyDataColumn(), "FILE");
                        if (map.containsKey(key)) {
                            List<ServiceDataFileBO> fileBOs = (List<ServiceDataFileBO>) map.get(key);
                            Optional<ServiceDataFileBO> fileBO = fileBOs.stream().filter(x -> x.getFileName().equalsIgnoreCase(rq.getFileName())).findAny();
                            if (fileBO.isPresent()) {
                                String mimeType = "";
                                mimeType = kgoCasesetColumn.getMyDataColumn().equalsIgnoreCase("pdf") ? MediaType.APPLICATION_PDF_VALUE : "text/csv";

                                commonService.downloadFileAction(fileBO.get().getFileName(), fileBO.get().getFileData(), mimeType);
                            }
                        }
                    }
                }
            }
            return null;
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("downloadMyDataAttachment error " + e.getMessage(), e);
        } finally {
            removeTxIdSession(rq.getTxId());
        }
    } //downloadMyDataAttachment

    /**
     * GEO 20211120
     *
     * @param rq
     * @return
     */
    @Override
    public ValidationActionRs validationAction(ValidationActionRq rq) {

        ValidationActionViewForm viewForm = new ValidationActionViewForm();
        try {
            viewForm.setCaseSetId(rq.getCaseSetId());

            if (StringUtils.isBlank(rq.getCaseSetId()) || ObjectUtils.isEmpty(rq.getVersion())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            if (isDebug) {
                LOGGER.info(">>>>>>> validationAction Before >>>>>>");
                LOGGER.info(JsonUtil.toJSONString(rq));
            }
            KgoCaseset kgoCaseset = this.getKgoCaseset(rq.getCaseSetId());
            // 非府內線上服務
            if (kgoCaseset.getIsOpenForOrgan() == null || kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
            }
            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED));
            }

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId());
            if (!checkLoginBoolean) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.PERMISSION_DENIED));
            }

            List<KgoCasesetColumn> casesetColumns = getCasesetColumns(rq.getCaseSetId(), rq.getVersion());

            // 合併 MyData Id
            if (StringUtils.isNoneBlank(rq.getMyDataTxId())) {
                setCaseSetMyDataTxidMageSaveColumnVal(rq);
                if (isDebug) {
                    LOGGER.info(">>>>>>> After >>>>>>");
                    LOGGER.info(JsonUtil.toJSONString(rq));
                }
            }

            // 檢核
            ValidationActionViewForm validationFrom = this.saveValidation(casesetColumns, kgoCaseset, rq);
            viewForm.setValidationMsg(validationFrom.getValidationMsg());
            viewForm.setF3Name(validationFrom.getF3Name());

            return new ValidationActionRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("saveAction error " + e.getMessage(), e);
        } finally {
            removeTxIdSession(rq.getMyDataTxId());
        }

    } //validationAction

    /**
     * Geo 20211120
     *
     * @param rq
     * @return
     */
    @Override
    public SaveActionRs saveAction(SaveActionRq rq) {
        SaveActionViewForm viewForm = new SaveActionViewForm();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        String logCaseId = null;
        String caseSetName = null;
        try {
            //後台 、新增、申辦案件
            memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_A, BackendFunctionCodeEnum.CaseApply);
            if (StringUtils.isBlank(rq.getCaseSetId()) || ObjectUtils.isEmpty(rq.getVersion())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            if (isDebug) {
                LOGGER.info(">>>>>>> saveAction Before >>>>>>");
                LOGGER.info(JsonUtil.toJSONString(rq));
            }
            KgoCaseset kgoCaseset = this.getKgoCaseset(rq.getCaseSetId());
            // 非府內線上服務
            if (kgoCaseset.getIsOpenForOrgan() == null || kgoCaseset.getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SERVICE_IS_NOT_PROVIDED_FOR_ORGAN));
            }

            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.SERVICE_IS_NOT_PROVIDED));
            }
            caseSetName = kgoCaseset.getCaseSetName();

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId());
            if (!checkLoginBoolean) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED));
            }

            CaseTypeEnum caseType = CaseTypeEnum.getEnum(kgoCaseset.getCaseType());
            String caseId = KgoUtil.getNextCaseId(caseType);
            logCaseId = caseId;
            List<KgoCasesetColumn> casesetColumns = getCasesetColumns(rq.getCaseSetId(), rq.getVersion());

            // 合併 MyData Id
            if (StringUtils.isNoneBlank(rq.getMyDataTxId())) {
                setCaseSetMyDataTxidMageSaveColumnVal(rq);
                if (isDebug) {
                    LOGGER.info(">>>>>>> After >>>>>>");
                    LOGGER.info(JsonUtil.toJSONString(rq));
                }
            }

            // 檢核
            ValidationActionViewForm validationFrom = this.saveValidation(casesetColumns, kgoCaseset, rq);
            viewForm.setValidationMsg(validationFrom.getValidationMsg());

            if (validationFrom.getValidationMsg().size() == 0) {

                // 存擋
                KgoCaseMain kgoCaseMain = this.saveKgoCaseMain(caseId, kgoCaseset, rq, viewForm);
                this.saveKgoCaseDetail(kgoCaseMain, kgoCaseset, rq);

                /**
                 * Geo 20221010 add_Jim
                 * rename myData hash text file
                 */
                File file = new File("D:\\var\\" + rq.getTxId() + ".txt");
                File renameFile = new File("D:\\var\\" + caseId + ".txt");
                file.renameTo(renameFile);

                // 啟動案件
                String processId = this.activitiService.processCaseStart(kgoCaseMain, viewForm.getCaseOrganName());
                kgoCaseMain.setProcessId(processId);
                this.kgoCaseMainRepository.save(kgoCaseMain);

                // 申請人員
                String applyEmail = getColumnViewForm(rq.getColumnData(), "Email");

                // 發送email 、通知、KCG API共通作法
                doSaveCaseCommonNotify(kgoCaseMain, kgoCaseset, applyEmail, viewForm.getApplyDate());
            }
            // getNextCaseId
            return new SaveActionRs(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("saveAction error " + e.getMessage(), e);
        } finally {
            removeTxIdSession(rq.getMyDataTxId());
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(String.format("%s申請，案件編號：", caseSetName), logCaseId));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
    } //saveAction

    /**
     * GEO 20211120
     * 案件儲存共通作法 - 發送email 、通知、KCG API.
     *
     * @param kgoCaseMain the kgo case main
     * @param kgoCaseset  the kgo caseset
     * @param applyEmail  the apply email
     * @param applyDate   the apply date
     * @throws Exception the exception
     */
    @Override
    public void doSaveCaseCommonNotify(KgoCaseMain kgoCaseMain, KgoCaseset kgoCaseset, String applyEmail, String applyDate) throws Exception {
        String caseId = kgoCaseMain.getCaseId();
        // 一般民眾寄信
        // 申請人 email
        this.sendEmailApplyCase(caseId, kgoCaseset, applyEmail, applyDate);

        // 依流程類型發信給市府人員
        CaseFlowTypeEnum caseFlowTypeE = CaseFlowTypeEnum.getCaseFlowTypeEnum(kgoCaseset.getCaseFlowType());
        String empEmails = StringUtils.EMPTY;
        KgoUserRepository kgoUserRepository = SpringUtil.getBean(KgoUserRepository.class);
        // "B3", "由本平台完整申辦" 寄信
        if (CaseFlowTypeEnum.B3.equals(caseFlowTypeE)) {
            // 寄信給 承辦人 or 管理者
            AcceptSetEnum acceptSetEnum = AcceptSetEnum.getEnum(kgoCaseset.getAcceptSet());

            // 承辦人
            if (acceptSetEnum.equals(AcceptSetEnum.OFFICER)) {
                empEmails = kgoUserRepository.findOfficerEmailsByCaseSetId(kgoCaseset.getCaseSetId());

                // 機關分文
            } else if (acceptSetEnum.equals(AcceptSetEnum.UNIT)) {
                empEmails = kgoUserRepository.findUnitEmailsByCaseSetId(kgoCaseset.getCaseSetId());

                // 區機關
            } else if (acceptSetEnum.equals(AcceptSetEnum.AREA)) {
                empEmails = kgoUserRepository.findAreaEmailsByCaseSetId(kgoCaseset.getCaseSetId());
            }

            // A("A", "站外連結"), B1("B1", "案件暫存區(入案通知)"), B2("B2", "案件暫存區(自行取案)")
            // 寄信方式
        } else {
            // String email =
            // kgoUserRepository.findEmailsByOrgan(kgoCaseset.getOwnerOrgan());
            empEmails = kgoUserRepository.findEmailsByCaseSetId(kgoCaseset.getCaseSetId());
        }
        this.sendEmailSignDispatch(caseId, kgoCaseset.getCaseSetName(), empEmails);

        // 推播
        this.pushNotificationApplyCase(kgoCaseMain, kgoCaseset, kgoCaseset.getCaseSetName(), applyDate);

        // Call API
        this.callExternalApiApplyCase(kgoCaseMain, kgoCaseset);
    } //doSaveCaseCommonNotify

    @Override
    public GeoMyDataModelRs queryMyDataModel(String caseSetId) {
        return null;
    }

    @Override
    public GeoMyDataGenDataRs genEncryptedData(MyDataGenDataRq rq) throws Exception {
        return null;
    }

    /**
     * GEO 20211120
     *
     * @param rq
     * @return
     */
    @Override
    public KcgMydataRs getKcgMydata(KcgMydataRq rq) {
        KcgMydataViewForm viewForm = new KcgMydataViewForm();

        try {
            String socbuValue = "";
            KcgCityApiServiceType serviceType = KcgCityApiServiceType.getKcgCityApiServiceType(rq.getServiceId());
            if (ObjectUtils.isNotEmpty(serviceType)) {
                String jsonString = callKcgCityApiService.getKcgCityApiJsonStrWithMoicaLogin(serviceType, new HashMap<>());
                ServiceDataBO serviceDataBO = new ServiceDataBO();
                serviceDataBO.setJsonData(Arrays.asList(new ServiceDataFileBO(serviceType.getServiceId() + ".json", jsonString)));
                Map<String, Object> dataMap = getResourceMergeMyDataColumnsVal(serviceType.getServiceId(), serviceDataBO);

                for (Entry<String, Object> item : dataMap.entrySet()) {
                    if (!item.getKey().equalsIgnoreCase("CSV_FILE") && !item.getKey().equalsIgnoreCase("PDF_FILE")) {
                        socbuValue = StringUtils.defaultString((String) item.getValue(), "");
                    }
                }
            } else {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            viewForm.setSocbuValue(socbuValue);
            return new KcgMydataRs(viewForm);

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("saveAction error " + e.getMessage(), e);
        }

    } //getKcgMydata

    /**
     * Geo 20211120
     * 登入方式檢核
     *
     * @param caseSetId
     * @return
     */
    private Boolean getCaseSetLoginCheck(String caseSetId) {
//        if (this.isDevMode())
//            return true;
        Boolean checkBoolean = false;
        List<KgoCasesetCheck> checkTypeList = kgoCasesetCheckRepository.findAllByIdCaseSetId(caseSetId);
        BackendLoginUserInfo loginUserInfo = this.getLoginUserInfo();
        if (ObjectUtils.isNotEmpty(checkTypeList) && checkTypeList.size() > 0) {
            for (KgoCasesetCheck kgoCasesetCheck : checkTypeList) {
                CheckTypeEnum checkTypeEnum = CheckTypeEnum.getEnum(kgoCasesetCheck.getId().getCheckType());
                if (ObjectUtils.isNotEmpty(checkTypeEnum) && ObjectUtils.isNotEmpty(checkTypeEnum.getLoginAuthType())) {
                    if (ObjectUtils.isNotEmpty(loginUserInfo)) {
                        checkBoolean = loginUserInfo.getLoginAuthTokenType() == checkTypeEnum.getLoginAuthType();
                    }
                } else {
                    checkBoolean = true;
                }
                if (checkBoolean) return checkBoolean;
            } // for (KgoCasesetCheck kgoCasesetCheck : checkTypeList)
        } else {
            checkBoolean = true;
        }
        return checkBoolean;
    } //getCaseSetLoginCheck

    /**
     * Geo 20211120
     * 產生初始化頁面
     *
     * @param caseSetId
     * @return
     * @throws Exception
     */
    public MyDataHomeActionViewForm generateHomeActionViewForm(String caseSetId) throws Exception {

        return this.generateActionViewForm(caseSetId, null, "", "");
    } //generateHomeActionViewForm

    /**
     * Geo 20211120
     * 產生初始化頁面
     *
     * @param caseSetId
     * @return
     * @throws Exception
     */
    private MyDataHomeActionViewForm generateActionViewForm(String caseSetId, String encryptTxId, String mydataTxid, String encryptPId) throws Exception {
        MyDataHomeActionViewForm viewForm = new MyDataHomeActionViewForm();

        if (StringUtils.isBlank(caseSetId)) {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
        }

        KgoCaseset kgoCaseset = this.getKgoCaseset(caseSetId);
        viewForm.setCaseSetId(kgoCaseset.getCaseSetId());
        viewForm.setCaseSetName(kgoCaseset.getCaseSetName());
        viewForm.setCaseFlowType(kgoCaseset.getCaseFlowType());
        viewForm.setCaseType(kgoCaseset.getCaseType());
        viewForm.setAcceptSet(kgoCaseset.getAcceptSet());

        Integer version = this.getCasesetMaxVersion(kgoCaseset.getCaseSetId());
        viewForm.setVersion(version);

        List<CasesetMydataColumnDto> data = this.getCasesetMyDataColumns(caseSetId, version);
        viewForm.setIsMyData(data.stream().filter(x -> !x.getMyDataClientId().equalsIgnoreCase(KAPI_MYDATA_CLIENTID)).count() > 0);
        viewForm.setIsKgoCity(data.stream().filter(x -> x.getMyDataClientId().equalsIgnoreCase(KAPI_MYDATA_CLIENTID)).count() > 0);

        // MyData 前置作業
        if (StringUtils.isNotBlank(encryptTxId)) {
            Optional<CasesetMydataColumnDto> casesetColumn = data.stream().filter(x -> !x.getMyDataClientId().equals(KAPI_MYDATA_CLIENTID)).findAny();
            if (casesetColumn.isPresent()) {
                try {
                    mydataTxid = this.decryptTxId(casesetColumn.get().getMyDataClientId(), encryptTxId);
                } catch (Exception e) {
                    // LOGGER.error(" >>>>> decryptTxId >>>> " + e.getMessage(), e);
                } // try
            } // if (casesetColumn.isPresent())
        } // if (StringUtils.isNotBlank(encryptTxId))

        if (StringUtils.isNotBlank(mydataTxid)) {
            String pId = StringUtils.EMPTY;
            if (StringUtils.isNotBlank(encryptPId)) {
                Optional<CasesetMydataColumnDto> casesetColumn = data.stream().filter(x -> !x.getMyDataClientId().equals(KAPI_MYDATA_CLIENTID)).findAny();
                if (casesetColumn.isPresent()) {
                    try {
                        pId = this.decryptTxId(casesetColumn.get().getMyDataClientId(), encryptPId);
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("_id", pId);
                        setMyDataTxIdRequestVal(mydataTxid, dataMap);
                    } catch (Exception e) {
                        // LOGGER.error(" >>>>> decryptTxId >>>> " + e.getMessage(), e);
                    }
                }
            } //if (StringUtils.isNotBlank(encryptPId))

            MyDataServiceDownloadRs serviceDownloadRs = setCaseSetAndMyDataResourceColumns(caseSetId, version, mydataTxid, pId);
            viewForm.setTxId(mydataTxid);

            if (ObjectUtils.isNotEmpty(serviceDownloadRs)) {
                if (ObjectUtils.isEmpty(serviceDownloadRs.getDelaySeconds())) {
                    viewForm.setMessage(ErrorResult.getErrorDescI18n(KgoFrontEndApiError.MYDATA_DONLOAD_FAILUER.getErrorMsgKey()));
                } else {
                    viewForm.setDelaySeconds(NumberUtils.toInt(serviceDownloadRs.getDelaySeconds(), 60));
                    if (viewForm.getDelaySeconds() > 0) {
                        viewForm.setMessage(ErrorResult.getErrorDescI18n(KgoFrontEndApiError.MYDATA_WAIT_DELAY_SECONDS.getErrorMsgKey(), viewForm.getDelaySeconds()));
                    } else {
                        viewForm.setMessage("");
                    }
                } // if (ObjectUtils.isEmpty(serviceDownloadRs.getDelaySeconds()))
            } // if (ObjectUtils.isNotEmpty(serviceDownloadRs))
        } // if (StringUtils.isNotBlank(mydataTxid))

        List<OptionViewForm> options = new ArrayList<OptionViewForm>();
        viewForm.setGrid(this.getGroupViewForms(kgoCaseset.getCaseSetId(), version, mydataTxid, options));
        viewForm.setOptions(options);
        viewForm.setCaseOrganComboBox(this.getCaseOrganComboBox(kgoCaseset.getCaseSetId(), kgoCaseset.getAcceptSet()));
        return viewForm;
    } //generateActionViewForm

    /**
     * GEO 20211120
     * 取的申請案件表單設定資料
     *
     * @param caseSetId
     * @return
     * @throws KgoApiException
     */
    private KgoCaseset getKgoCaseset(String caseSetId) throws KgoApiException {
        Optional<KgoCaseset> data = this.kgoCasesetRepository.findById(caseSetId);
        if (data.isPresent()) {
            return data.get();
        } else {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_NOT_EXIST));
        }
    } //getKgoCaseset

    /**
     * GEO 20211120
     * 取得申請表單 Group 容清單
     *
     * @param caseSetId
     * @param version
     * @param options
     * @return
     */
    private List<GroupViewForm> getGroupViewForms(String caseSetId, Integer version, String txId, List<OptionViewForm> options) throws Exception {
        List<GroupViewForm> viewForms = new ArrayList<GroupViewForm>();
        List<KgoCasesetGroup> data = this.getCaseseGroups(caseSetId, version);
        for (KgoCasesetGroup x : data) {
            GroupViewForm viewForm = new GroupViewForm();
            viewForm.setCaseSetId(x.getId().getCaseSetId());
            viewForm.setGroupSeq(x.getId().getGroupSeq());
            viewForm.setVersion(x.getId().getVersion());
            viewForm.setMemo(x.getMemo());
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setColumnData(this.getColumnViewForms(caseSetId, x.getId().getGroupSeq(), version, txId, options));
            viewForm.setCheckFrequencyPeriod(x.getCheckFrequencyPeriod() == null ? "" : x.getCheckFrequencyPeriod());
            viewForms.add(viewForm);
        }
        return viewForms;
    } //getGroupViewForms

    /**
     * GEO 20211120
     * 取得申請表單 Group 的 Column 設定內容清單
     *
     * @param caseSetId
     * @param groupSeq
     * @param version
     * @param options
     * @return
     */
    private List<ColumnViewForm> getColumnViewForms(String caseSetId, int groupSeq, Integer version, String txId, List<OptionViewForm> options) throws Exception {
        List<ColumnViewForm> viewForms = new ArrayList<ColumnViewForm>();

        List<KgoCasesetColumn> data = this.getCasesetColumns(caseSetId, groupSeq, version);
        Map<String, CasesetMydataColumnDto> kApiMydataColumnDtoMap = getCasesetKApiMyDataColumns(caseSetId, version).stream().collect(Collectors.toMap(CasesetMydataColumnDto::getColumnId, c -> c));

        BackendLoginUserInfo loginUserInfo = getLoginUserInfo();

        if (isDebug) {
            LOGGER.info(">>>>>>loginUserInfo>>>>>>>");
            LOGGER.info(JsonUtil.toJSONString(loginUserInfo));
        }

        /** KGO 20210817 add for 1999 service **/
        boolean hadKd = false; //有派工欄位
        boolean hadNew = false; //有陳情欄位

        for (KgoCasesetColumn x : data) {
            ColumnViewForm viewForm = new ColumnViewForm();
            viewForm.setCaseSetId(x.getId().getCaseSetId());
            viewForm.setGroupSeq(x.getGroupSeq());
            viewForm.setVersion(x.getId().getVersion());
            viewForm.setColumnId(StringUtils.defaultString(x.getId().getColumnId(), ""));
            viewForm.setColumnName(StringUtils.defaultString(x.getColumnName(), ""));
            viewForm.setColumnType(StringUtils.defaultString(x.getColumnType(), ""));
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setLength(x.getLength());
            viewForm.setIsMustKey(x.getIsMustKey());
            viewForm.setIsReadonly(StringUtils.isNotBlank(x.getMyDataId()) && StringUtils.isNoneBlank(txId));
            viewForm.setOrderNum(x.getOrderNum());
            viewForm.setMemo(StringUtils.defaultString(x.getMemo(), ""));
            viewForm.setColumnValue(StringUtils.defaultString(x.getColumnValue(), ""));
            viewForm.setFileType(StringUtils.defaultString(x.getFileType(), ""));
            viewForm.setIsCheckFrequency(String.valueOf(x.getIsCheckFrequency()));

            if (ObjectUtils.isNotEmpty(kApiMydataColumnDtoMap) && kApiMydataColumnDtoMap.containsKey(x.getId().getColumnId())) {
                CasesetMydataColumnDto mydataColumnDto = kApiMydataColumnDtoMap.get(x.getId().getColumnId());
                viewForm.setCityApiServiceId(mydataColumnDto.getMyDataId());
            } else {
                viewForm.setCityApiServiceId("");
            }

            viewForm.setComplex(getComplex(caseSetId, version, txId, x.getId().getColumnId(), x.getColumnType(), options));

            setDefaultColumnValue(viewForm, loginUserInfo);
            viewForm.setValidationMsg("");

            if (StringUtils.isNotBlank(txId) && StringUtils.isNotBlank(x.getMyDataId()) && StringUtils.isNotBlank(x.getMyDataColumn())) {
                if (this.getResourceTxIdCasesetMergeMyDataColumnsVal(txId).containsKey(x.getMyDataId())) {
                    Map<String, Object> resourceMyDataMap = this.getResourceTxIdCasesetMergeMyDataColumnsVal(txId).get(x.getMyDataId());
                    if (resourceMyDataMap.containsKey(x.getId().getColumnId())) {
                        try {
                            viewForm.setValue(StringUtils.defaultString((String) resourceMyDataMap.get(x.getId().getColumnId()), ""));
                        } catch (Exception e) {
                            // TODO: handle exception
                            LOGGER.error("ColumnId:" + x.getId().getColumnId() + ", " + e.getMessage(), e);
                        }
                        viewForm.setValidationMsg(this.getValidationMsg(txId, x, viewForm.getValue()));
                    }
                }
            }

//			try {
//				if (x.getId().getColumnId().equalsIgnoreCase("Name") || x.getId().getColumnId().equalsIgnoreCase("Building") || x.getId().getColumnId().equalsIgnoreCase("Address2")
//						|| x.getId().getColumnId().equalsIgnoreCase("BAddr") || x.getId().getColumnId().equalsIgnoreCase("BAddress")) {
//					if (caseSetId.equalsIgnoreCase("S2020112100003") || caseSetId.equalsIgnoreCase("S2020112100002") || caseSetId.equalsIgnoreCase("S2020120300003")
//							|| caseSetId.equalsIgnoreCase("S2020120100002")) {
//
//						if (this.getResourceTxIdCasesetMergeMyDataColumnsVal(txId).containsKey("API.KvvyRZSc5K")) {
//							Map<String, Object> resourceMyDataMap = this.getResourceTxIdCasesetMergeMyDataColumnsVal(txId).get("API.KvvyRZSc5K");
//							if (resourceMyDataMap.containsKey(x.getId().getColumnId())) {
//								try {
//									viewForm.setValue(StringUtils.defaultString((String) resourceMyDataMap.get(x.getId().getColumnId()), ""));
//								} catch (Exception e) {
//									// TODO: handle exception
//									LOGGER.error("ColumnId:" + x.getId().getColumnId() + ", " + e.getMessage(), e);
//								}
//								viewForm.setValidationMsg(this.getValidationMsg(txId, x, viewForm.getValue()));
//							}
//
//						}
//					}
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}

            // this.setColumnOptions(x.getId().getColumnId(), x.getColumnType(),
            // x.getColumnValue(), options);
            // columnOptions


            /** KGO 20210817 add for 1999 service **/
            //LOGGER.info("CaseFormServiceImpl getColumnViewForms getColumnType: "+x.getColumnType());
            if (x.getColumnType().equals(ColumnTypeEnum.KD_1999.getValue())) hadKd = true;
            if (x.getColumnType().equals(ColumnTypeEnum.NEW_1999.getValue())) hadNew = true;


            viewForms.add(viewForm);
        } //for (KgoCasesetColumn x : data)

        /** KGO 20210817 change param for 1999 service **/
        this.setColumnOptions(options, hadKd, hadNew);

        return viewForms;
    } //getColumnViewForms

    /**
     * GEO 20211120
     * 產生表單複合欄位
     *
     * @param caseSetId
     * @param version
     * @param txId
     * @param columnId
     * @param columnType
     * @param options
     * @return
     */
    private List<List<CasesetComplexColumnData>> getComplex(String caseSetId, Integer version, String txId, String columnId, String columnType, List<OptionViewForm> options) {

        List<List<CasesetComplexColumnData>> complexDataList = new ArrayList<List<CasesetComplexColumnData>>();

        if (columnType.equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
            Map<Integer, List<KgoCasesetColumnChild>> dataMap = kgoCasesetColumnChildRepository.findByIdCaseSetIdAndIdVersionAndIdColumnIdOrderByOrderNumAsc(caseSetId, version, columnId).stream()
                    .collect(Collectors.groupingBy(KgoCasesetColumnChild::getRow, HashMap::new, Collectors.toCollection(LinkedList::new)));

            complexDataList = dataMap.keySet().stream().map(i -> {
                return dataMap.get(i).stream().map(cl -> {
                    CasesetComplexColumnData complexData = new CasesetComplexColumnData();
                    complexData.setbText(StringUtils.defaultString(cl.getBText(), ""));
                    complexData.setcColumnId(StringUtils.defaultString(cl.getId().getCColumnId(), ""));
                    complexData.setColumnType(StringUtils.defaultString(cl.getColumnType(), ""));
                    complexData.setColumnValue(StringUtils.defaultString(cl.getColumnValue(), ""));
                    complexData.setfText(StringUtils.defaultString(cl.getFText(), ""));
                    complexData.setLength(cl.getLength());
                    complexData.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(cl.getIsMustKey()).getValue());
                    complexData.setOrderNum(cl.getOrderNum());
                    complexData.setpColumnId(StringUtils.defaultString(cl.getPColumnId(), ""));
                    complexData.setRow(cl.getRow());
                    complexData.setvGroup(StringUtils.defaultString(String.valueOf(cl.getVGroup()), ""));
                    complexData.setIsCheckFrequency(cl.getIsCheckFrequency());
                    return complexData;
                }).collect(Collectors.toList());
            }).collect(Collectors.toList());
        }
        return complexDataList;
    } //getComplex

    /**
     * GEO 20211120
     * 取得登入資訊
     *
     * @return
     */
    private BackendLoginUserInfo getLoginUserInfo() {
        try {
            return KgoUtil.getBackendLoginUser();
        } catch (Exception e) {
            return null;
        }
    } //getLoginUserInfo

    /**
     * Geo 20211120
     * 設定 預設帶登入者資訊
     *
     * @param viewForm
     * @param loginUserInfo
     */
    private void setDefaultColumnValue(ColumnViewForm viewForm, BackendLoginUserInfo loginUserInfo) {
        viewForm.setValue("");
        if (ObjectUtils.isNotEmpty(loginUserInfo)) {
            KgoUser user = kgoUserRepository.getOne(loginUserInfo.getUserId());
            if (viewForm.getColumnId().equalsIgnoreCase("ID")) {
                switch (loginUserInfo.getLoginAuthTokenType()) {
                    case BASIC:
                        viewForm.setColumnValue(StringUtils.defaultString(loginUserInfo.getKcgUserBasicInfo().getAppUserTwSSn(), ""));
                        break;
                    case MOICA:
                        viewForm.setColumnValue(StringUtils.defaultString(loginUserInfo.getKcgMoicaCardInfo().getMoicaUserTwSsn(), ""));
                        break;
                    case HCA:
                        viewForm.setColumnValue(StringUtils.defaultString(loginUserInfo.getKcgHcaCardSsoInfo().getHcaUserTwSsn(), ""));
                        break;
                    case EGOV:
                        viewForm.setColumnValue(StringUtils.defaultString(loginUserInfo.getKcgEgovInfo().getEgovUserUid(), ""));
                        break;
                    case TW_FIDO:
                        viewForm.setColumnValue(StringUtils.defaultString(loginUserInfo.getKcgTwFidoSsoInfo().getTwfidoUserTwSSn(), ""));
                        break;
                }
            } //if (viewForm.getColumnId().equalsIgnoreCase("ID"))

            if (user != null) {
                if (viewForm.getColumnId().equalsIgnoreCase("Name"))
                    viewForm.setColumnValue(StringUtils.defaultString(user.getName(), ""));
                if (viewForm.getColumnId().equalsIgnoreCase("Email"))
                    viewForm.setColumnValue(StringUtils.defaultString(user.getEmail(), ""));
                if (viewForm.getColumnId().equalsIgnoreCase("CellPhone"))
                    viewForm.setColumnValue(StringUtils.defaultString(user.getTel(), ""));
            }  //if (user != null)

            if (viewForm.getColumnId().equalsIgnoreCase("ID") || viewForm.getColumnId().equalsIgnoreCase("Name")
                    || viewForm.getColumnId().equalsIgnoreCase("Email") || viewForm.getColumnId().equalsIgnoreCase("CellPhone")) {
                viewForm.setIsReadonly((ObjectUtils.isNotEmpty(loginUserInfo.getKcgMoicaCardInfo())) && StringUtils.isNoneBlank(viewForm.getColumnValue()));
            }
        } //  if (ObjectUtils.isNotEmpty(loginUserInfo))
    } //setDefaultColumnValue

    /**
     * 20211120
     * MyData Check
     *
     * @param txId
     * @param casesetColumn
     * @param vaule
     * @return
     */
    private String getValidationMsg(String txId, KgoCasesetColumn casesetColumn, String vaule) {

        if (StringUtils.isNotBlank(casesetColumn.getMyDataCheckType())) {
            KgoMydataColumnPK mydataColumnPK = new KgoMydataColumnPK();
            mydataColumnPK.setMyDataId(casesetColumn.getMyDataId());
            mydataColumnPK.setMyDataColumn(casesetColumn.getMyDataColumn());
            KgoMydataColumn kgoMydataColumn = this.getResourceMydataColumns(txId).get(mydataColumnPK);

            MyDataCheckTypeEnum checkTypeEnum = MyDataCheckTypeEnum.getEnum(casesetColumn.getMyDataCheckType());
            if (kgoMydataColumn.getType().equals("D")) {
                return dateValidation(casesetColumn.getMyDataCheckValue(), vaule, checkTypeEnum);
            }

            if (kgoMydataColumn.getType().equals("N")) {
                return numberValidation(casesetColumn.getMyDataCheckValue(), vaule, checkTypeEnum);
            }

            return stringValidation(casesetColumn.getMyDataCheckValue(), vaule, checkTypeEnum);
        } else {
            return "";
        }
    } //getValidationMsg

    /**
     * Geo 20211120
     * MyData Check By String 型別
     *
     * @param myDataCheckValue
     * @param columnValue
     * @param checkType
     * @return
     */
    private String stringValidation(String myDataCheckValue, String columnValue, MyDataCheckTypeEnum checkType) {
        Boolean checkBoolean = true;
        try {
            String checkValue = myDataCheckValue;
            String value = columnValue;

            if (ObjectUtils.isNotEmpty(checkType)) {
                switch (checkType) {
                    case E:
                        checkBoolean = value.equals(checkValue);
                        break;
                    case M:
                        checkBoolean = value.compareTo(value) > 0;
                        break;
                    case L:
                        checkBoolean = value.compareTo(value) < 0;
                        break;
                }
            }

        } catch (Exception e) {
            return this.getMessage(KgoBackEndApiError.DATA_FORMAT_ERROR.getErrorMsgKey());
        }

        return checkBoolean == true ? "" : this.getMessage(checkType.getMsg(), myDataCheckValue, columnValue);
    } //stringValidation

    /**
     * Geo 20211120
     * MyData Check By Date 型別
     *
     * @param myDataCheckValue
     * @param columnValue
     * @param checkType
     * @return
     */
    private String dateValidation(String myDataCheckValue, String columnValue, MyDataCheckTypeEnum checkType) {

        Boolean checkBoolean = true;
        try {
            Date checkValue = DateUtil.strToDate(myDataCheckValue, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
            Date value = DateUtil.strToDate(columnValue, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);

            switch (checkType) {
                case E:
                    checkBoolean = value.compareTo(checkValue) == 0;
                    break;
                case M:
                    checkBoolean = value.compareTo(checkValue) > 0;
                    break;
                case L:
                    checkBoolean = value.compareTo(checkValue) < 0;
                    break;
            }
        } catch (Exception e) {
            return this.getMessage(KgoBackEndApiError.DATA_FORMAT_ERROR.getErrorMsgKey());
        }

        return checkBoolean == true ? "" : this.getMessage(checkType.getMsg(), myDataCheckValue, columnValue);
    }

    /**
     * GEO 20211120
     * MyData Check By Number 型別
     *
     * @param myDataCheckValue
     * @param columnValue
     * @param checkType
     * @return
     */
    private String numberValidation(String myDataCheckValue, String columnValue, MyDataCheckTypeEnum checkType) {
        Boolean checkBoolean = true;

        if (!NumberUtils.isCreatable(myDataCheckValue) || !NumberUtils.isCreatable(columnValue)) {
            return this.getMessage(KgoBackEndApiError.DATA_FORMAT_ERROR.getErrorMsgKey());
        }
        Double checkValue = NumberUtils.toDouble(myDataCheckValue);
        Double value = NumberUtils.toDouble(columnValue);

        switch (checkType) {
            case E:
                checkBoolean = value == checkValue;
                break;
            case M:
                checkBoolean = value > checkValue;
                break;
            case L:
                checkBoolean = value < checkValue;
                break;
        }
        return checkBoolean == true ? "" : this.getMessage(checkType.getMsg(), myDataCheckValue, columnValue);
    } //numberValidation

    /**
     * GEO 20211120
     * 合併修正 MyData 資料，避免被串改
     *
     * @param rq
     * @throws Exception
     */
    public void setCaseSetMyDataTxidMageSaveColumnVal(SaveActionRq rq) throws Exception {
        LOGGER.info("合併修正 MyData 資料，避免被串改 start");

        // 下載 MyData
        setCaseSetAndMyDataResourceColumns(rq.getCaseSetId(), rq.getVersion(), rq.getMyDataTxId(), "");

        for (SaveActionColumnViewForm column : rq.getColumnData()) {
            KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
            kgoCasesetColumnPK.setCaseSetId(rq.getCaseSetId());
            kgoCasesetColumnPK.setVersion(rq.getVersion());
            kgoCasesetColumnPK.setColumnId(column.getColumnId());
            // KgoCasesetColumn kgoCasesetColumn =
            // KgoUtil.getKgoCasesetColumnFromCashe(kgoCasesetColumnPK);
            Optional<KgoCasesetColumn> okgoCasesetColumn = kgoCasesetColumnRepository.findById(kgoCasesetColumnPK);

            Map<String, Map<String, Object>> myDataDownloadMap = getResourceTxIdCasesetMergeMyDataColumnsVal(rq.getMyDataTxId());
            if (ObjectUtils.isNotEmpty(myDataDownloadMap) && okgoCasesetColumn.isPresent()) {
                KgoCasesetColumn kgoCasesetColumn = okgoCasesetColumn.get();
                if (myDataDownloadMap.containsKey(kgoCasesetColumn.getMyDataId())) {
                    Map<String, Object> map = myDataDownloadMap.get(kgoCasesetColumn.getMyDataId());
                    if (map.containsKey(column.getColumnId())) {
                        column.setValue((String) map.get(column.getColumnId()));

                        if (kgoCasesetColumn.getMyDataColumn().equals("PDF") || kgoCasesetColumn.getMyDataColumn().equals("CSV")) {

                            String key = String.format("%s_%s", kgoCasesetColumn.getMyDataColumn(), "FILE");
                            if (map.containsKey(key)) {
                                List<ServiceDataFileBO> fileBOs = (List<ServiceDataFileBO>) map.get(key);
                                column.setFiles(new ArrayList<SaveFileViewForm>());

                                for (ServiceDataFileBO fileBO : fileBOs) {
                                    SaveFileViewForm file = new SaveFileViewForm();
                                    file.setFileName(fileBO.getFileName());
                                    file.setFileBase64Str(fileBO.getFileData());
                                    column.getFiles().add(file);
                                }
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("合併修正 MyData 資料，避免被串改 end");
    } //setCaseSetMyDataTxidMageSaveColumnVal

    /**
     * 20210824 GEO Marked --> 看起來並沒被呼叫到...
     *
     * 設定 元件 options
     *
     * @param columnId
     * @param columnType
     * @param columnValue
     * @param options
     * @throws Exception
     */
	/*private void setColumnOptions(String columnId, String columnType, String columnValue, List<OptionViewForm> options) throws Exception {
		LOGGER.info("CaseFormServiceImpl setColumnOptions columnType: "+columnType);
		ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(columnType);

		switch (columnTypeEnum) {
			case ADDRESS:
				long count = options.stream().filter(x -> x.getColumnId() == "county").count();
				if (count == 0) {
					options.add(new OptionViewForm("addressCounty", this.getCountyOptions()));
					options.add(new OptionViewForm("addressZip", this.getZipOptionsForGroup()));
				}
				break;
			case DRP:
			case RADIO:
				// options.add(new OptionViewForm(columnId, this.getOptions(columnValue)));
				break;
			case LAND_NUM:
				landNumOption(options);
				break;
			default:
				break;
		}
	} //setColumnOptions*/

    /**
     * GEO 20211120
     * 設定 元件 options
     *
     * @param options
     * @param hadKd
     * @param hadNew
     * @throws Exception
     */
    public void setColumnOptions(List<OptionViewForm> options, boolean hadKd, boolean hadNew) throws Exception {
        //LOGGER.info("CaseFormServiceImpl setColumnOptions...");
        options.add(new OptionViewForm("addressCounty", this.getCountyOptions()));
        options.add(new OptionViewForm("addressZip", this.getZipOptionsForGroup()));
        options.add(new OptionViewForm("landNumKcnt", this.getLandNumOption()));
        // landNumOption(options);


        /** GEO 20210817 add DM_1999 NEW_1999 **/
        if (hadKd) {
            List<Geo1999ItemsMainModel> itemList = geoKcgCityExtService.sendGet1999KdApi();
            options.add(new OptionViewForm("kd1999", this.get1999Options(itemList)));
            options.add(new OptionViewForm("kd1999Sub", this.get1999OptionsForGroup(itemList)));
        }
        if (hadNew) {
            List<Geo1999ItemsMainModel> itemList = geoKcgCityExtService.sendGet1999NewApi();
            options.add(new OptionViewForm("new1999", this.get1999Options(itemList)));
            options.add(new OptionViewForm("new1999Sub", this.get1999OptionsForGroup(itemList)));
        }


    } //setColumnOptions

    /**
     * GEO 20211120
     * 設定 元件 段小段 options
     *
     * @return
     */
    private List<SelectListItem> getLandNumOption() {
        List<KgoKcdDto> itemList = this.kgoKcdRepository.findByAll();
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "KCNT", "KCD", "ZIP", defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //getLandNumOption

    /**
     * GEO 20211120
     * 設定 元件 段小段 options (即時API)
     *
     * @param options
     * @throws Exception
     */
    private void landNumOption(List<OptionViewForm> options) throws Exception {
        Map<String, String> paramsMap = new HashMap<String, String>();

        LandNumRs landNumRs = null;

        try {
            paramsMap.put("WSID", "UDB_0004");
            landNumRs = this.callKcgCityApiServiceHelper.getCityApiDatanNoMoicaLogin(KcgCityApiServiceType.LAND_NUM.getServiceId(), LandNumRs.class, paramsMap);
        } catch (Exception e) {
            LOGGER.error("landNumOption", e);
        }

        List<SelectListItem> kcntItems = new ArrayList<SelectListItem>();
        List<SelectListItem> krmkItems = new ArrayList<SelectListItem>();
        if (ObjectUtils.isNotEmpty(landNumRs)) {
            for (DataModel dataModel : landNumRs.getData()) {
                if (ObjectUtils.isNotEmpty(dataModel)) {
                    kcntItems.add(new SelectListItem(dataModel.getKcnt(), dataModel.getKcdE_2()));
                    krmkItems.add(new SelectListItem(dataModel.getKrmkc(), dataModel.getKrmk()));
                }
            }
        }

        options.add(new OptionViewForm("landNumKcnt", kcntItems));
        options.add(new OptionViewForm("landNumkrmk", krmkItems));
    } //landNumOption

    /**
     * GEO 20211120
     * columnValue 選單
     *
     * @param columnValue
     * @return
     */
    private List<SelectListItem> getOptions(String columnValue) {

        List<SelectListItem> options = new ArrayList<SelectListItem>();

        List<String> itemsList = Arrays.asList(columnValue.split(","));
        itemsList.forEach(itemStr -> {
            String[] item = itemStr.split("-");
            if (item.length > 0) {
                if (item.length > 1) {
                    options.add(new SelectListItem(item[1], item[0]));
                } else {
                    options.add(new SelectListItem(item[0], item[0]));
                }
            }
        });

        return options;
    } //getOptions

    /**
     * GEO 20211120
     * 縣市選單
     *
     * @return
     */
    private List<SelectListItem> getCountyOptions() {
        /** 選擇區下拉式選單 **/
        List<KgoCounty> itemList = kgoCountyRepository.findAllByOrderBySortAsc();
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "countyName", "countyId", defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //getCountyOptions

    /**
     * GEO 20211120
     * 鄉鎮市區選單
     *
     * @return
     */
    private List<SelectListItem> getZipOptionsForGroup() {
        /** 選擇區下拉式選單 **/
        List<KgoZip> itemList = kgoZipRepository.findAll();
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "ZIPName", "ZIP", "countyId", defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //getZipOptionsForGroup

    /**
     * GEO 20211120
     * 1999選單
     *
     * @return
     */
    private List<SelectListItem> get1999Options(List<Geo1999ItemsMainModel> itemList) {
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(itemList, "itemName", "itemId",
                defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //get1999Options

    /**
     * GEO 20211120
     * 1999子選單
     *
     * @return
     */
    private List<SelectListItem> get1999OptionsForGroup(List<Geo1999ItemsMainModel> itemList) {
        List<Geo1999ItemsSubsModel> subsList = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getSubsList() != null && itemList.get(i).getSubsList().size() > 0) {
                for (int j = 0; j < itemList.get(i).getSubsList().size(); j++)
                    subsList.add(itemList.get(i).getSubsList().get(j));
            }
        } //for (int i=0; i<itemList.size(); i++)
        String defaultValue = StringUtils.EMPTY;
        return commonServiceHelper.getComboBox(subsList, "itemName", "itemId", "groupId",
                defaultValue, ComboBoxStatusEnum.ALL.getCode(), false).getOptions();
    } //get1999OptionsForGroup

    /**
     * GEO 20211120
     *
     * @param caseSetId
     * @param acceptSet
     * @return
     */
    private ComboBox getCaseOrganComboBox(String caseSetId, String acceptSet) {
        AcceptSetEnum acceptSetEnum = AcceptSetEnum.getEnum(acceptSet);
        ComboBox comboBox = null;
        switch (acceptSetEnum) {
            case UNIT:
                List<AcceptSetUnitQueryDto> itemListforUnit = this.kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId);
                String defaultValueforUnit = StringUtils.EMPTY;
                comboBox = commonServiceHelper.getComboBox(itemListforUnit, "OrganName", "Organ", defaultValueforUnit, ComboBoxStatusEnum.ALL.getCode(), false);
                break;
            case AREA:
                comboBox = new ComboBox();
                List<AcceptSetAreaQueryDto> itemList = this.kgoCasesetAreaRepository.getAreaDataByCaseSetId(caseSetId);
                for (AcceptSetAreaQueryDto area : itemList) {
                    List<String> zipList = Arrays.asList(area.getZip().split(","));
                    List<KgoZip> zipItemList = this.kgoZipRepository.findByCountyId(area.getOrgan());
                    for (KgoZip zip : zipItemList) {
                        if (zipList.contains(zip.getZip())) {
                            comboBox.add(String.format("%s-%s", area.getOrganName(), zip.getZIPName()), area.getOrgan());
                        }
                    }
                }
                break;
            default:
                comboBox = new ComboBox();
                comboBox.setIsShow(false);
                break;
        }
        return comboBox;
    } //getCaseOrganComboBox

    /**
     * GEO 20211120
     * 取得轉址 MyData URL
     *
     * @param pid
     * @param myDataClientId
     * @param resourceList
     * @param caseSetId
     * @return
     * @throws Exception
     */
    private String getRedirectMyDataUrl(String pid, String myDataClientId, List<String> resourceList, String caseSetId) throws Exception {
        String txId = this.generateTxId();
        return this.getRedirectMyDataUrl(txId, pid, myDataClientId, resourceList, caseSetId);
    } //getRedirectMyDataUrl

    /**
     * GEO 20211120
     * 取得轉址 MyData URL
     *
     * @param txId
     * @param pid
     * @param myDataClientId
     * @param resourceList
     * @param caseSetId
     * @return
     * @throws Exception
     */
    private String getRedirectMyDataUrl(String txId, String pid, String myDataClientId, List<String> resourceList, String caseSetId) throws Exception {
        KgoMydataService kgoMydataService = this.getkgoMydataService(myDataClientId);

        UserInfoBO userInfoBO = this.getUserInfoBO(pid, null);
        ClientInfoBO clientInfoBO = this.generateClientInfoBO(kgoMydataService, resourceList);

        String clientSecretKey = clientInfoBO.getClientSecretKey() + clientInfoBO.getClientSecretKey();
        String personalIdEncrypt = Base64Utils.encodeToString(AESUtil.encrypt(userInfoBO.getPid(), clientInfoBO.getClientIv(), clientSecretKey.getBytes(), ALG));
        String spReturnUrl = String.format("%s?casesetid=%s&mydatatxid=%s&pid=%s", kgoMydataService.getReturnUrl(), caseSetId, txId, personalIdEncrypt);
        LOGGER.info("=====================getRedirectMyDataUrl==============");
        LOGGER.info("spReturnUrl:" + spReturnUrl);
        LOGGER.info("userInfoBO:" + JSON.toJSONString(userInfoBO));
        LOGGER.info("clientInfoBO:" + JSON.toJSONString(clientInfoBO));
        LOGGER.info("personalIdEncrypt:" + new String(AESUtil.decrypt(clientInfoBO.getClientIv(), ALG, Base64Utils.decodeFromString(personalIdEncrypt), clientSecretKey.getBytes())));

        String returnUrl = StringUtils.EMPTY;
//        if (this.isDevMode()) {
//            txId = getRedirectMyDataUrlMockTxId(txId, myDataClientId, caseSetId, personalIdEncrypt);
//            returnUrl = String.format("%sapply_form?code=200&tx_id=%s&casesetid=%s&mydatatxid=%s&pid=%s", frontendLinkUrl, this.encryptTxId(myDataClientId, txId), caseSetId, txId, personalIdEncrypt);
//        } else {
        returnUrl = this.myDataService.getRedirectMyDataUrl(txId, URLEncoder.encode(spReturnUrl, "UTF-8"), userInfoBO, clientInfoBO);
//        }

        LOGGER.info("txId:" + txId);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("_id", pid);
        setMyDataTxIdRequestVal(txId, dataMap);

        return returnUrl;
    } //getRedirectMyDataUrl

    /**
     * GEO 20211120
     *
     * @param txId
     * @param myDataClientId
     * @param caseSetId
     * @param personalIdEncrypt
     * @return
     * @throws Exception
     */
    private String getRedirectMyDataUrlMockTxId(String txId, String myDataClientId, String caseSetId, String personalIdEncrypt) throws Exception {

        Map<String, String> caseSetMap = new HashMap<>();
        caseSetMap.put("S2020112100001", "70fc3619-9b7e-4a14-bc36-199b7e8a1415");
        caseSetMap.put("S2020112100002", "6b961c41-613b-406f-961c-41613b406f47");
        caseSetMap.put("S2020112100003", "49bb2502-8e26-4499-bb25-028e2684991b");
        caseSetMap.put("S2020112200001", "1d45e23c-7f4c-4740-85e2-3c7f4c67407b");
        caseSetMap.put("S2020113000005", "ba7f15af-9331-45b6-bf15-af933125b6ef");
        caseSetMap.put("S2020120100001", "ff0928dc-c80f-4de3-8928-dcc80f6de3c1");
        caseSetMap.put("S2020120100002", "df455a0f-5325-41fc-855a-0f532551fcec");
        caseSetMap.put("S2020120100003", "3fcd6815-c024-4677-8d68-15c0243677c7");
        caseSetMap.put("S2020120100004", "3fcd6815-c024-4677-8d68-15c0243677c7");
        caseSetMap.put("S2020120100005", "fa9ee4ee-7c22-437b-9ee4-ee7c22c37b2a");
        caseSetMap.put("S2020120300001", "70fc3619-9b7e-4a14-bc36-199b7e8a1415");
        caseSetMap.put("S2020120300002", "70fc3619-9b7e-4a14-bc36-199b7e8a1415");
        caseSetMap.put("S2020120300003", "df455a0f-5325-41fc-855a-0f532551fcec");

        return caseSetMap.containsKey(caseSetId) ? caseSetMap.get(caseSetId) : txId;

    } //getRedirectMyDataUrlMockTxId

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位
     *
     * @param caseSetId
     * @return
     */
    private List<KgoCasesetColumn> getCasesetColumns(String caseSetId, int groupSeq, Integer version) {
        return kgoCasesetColumnRepository.findByIdCaseSetIdAndGroupSeqAndIdVersionOrderByOrderNumAsc(caseSetId, groupSeq, version);
    } //getCasesetColumns

    /**
     * Geo 20211120
     * 取得案件表單設定畫面群組
     *
     * @param caseSetId
     * @param version
     * @return
     */
    private List<KgoCasesetGroup> getCaseseGroups(String caseSetId, Integer version) {
        return kgoCasesetGroupRepository.findByIdCaseSetIdAndIdVersionOrderByOrderNumAsc(caseSetId, version);
    }

    /**
     * Geo 20211120
     * 取得案件表單設定最大版本序號
     *
     * @param caseSetId
     * @return
     */
    private Integer getCasesetMaxVersion(String caseSetId) {
        return kgoCasesetGroupRepository.findMaxVersionByCasesetId(caseSetId);
    } //getCasesetMaxVersion

    /**
     * 產生 Call MyData tx_id
     *
     * @return
     */
    private String generateTxId() {
        return RandomUtil.getUUID();
    }

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位
     *
     * @param caseSetId
     * @return
     */
    private List<KgoCasesetColumn> getCasesetColumns(String caseSetId, Integer version) {
        return kgoCasesetColumnRepository.findByIdCaseSetIdAndIdVersion(caseSetId, version);
    } //getCasesetColumns

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位 MyData 資料集清單
     *
     * @param caseSetId
     * @param version
     * @return
     */
    private List<CasesetMydataColumnDto> getCasesetMyDataColumns(String caseSetId, Integer version) {
        List<CasesetMydataColumnDto> casesetColumns = kgoCasesetColumnRepository.findMydataColumnByCaseSetAndVersion(caseSetId, version);
        return casesetColumns.stream().filter(x -> StringUtils.isNotBlank(x.getMyDataClientId()) && StringUtils.isNotBlank(x.getMyDataId()) && StringUtils.isNotBlank(x.getMyDataColumn()))
                .collect(Collectors.toList());
    } //getCasesetMyDataColumns

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位 MyData 資料集清單 By 國發會
     *
     * @param caseSetId
     * @param version
     * @return
     */
    private List<CasesetMydataColumnDto> getCasesetRealMyDataColumns(String caseSetId, Integer version) {
        List<CasesetMydataColumnDto> casesetColumns = getCasesetMyDataColumns(caseSetId, version);
        return casesetColumns.stream().filter(x -> !x.getMyDataClientId().equals(this.KAPI_MYDATA_CLIENTID)).collect(Collectors.toList());
    } //getCasesetRealMyDataColumns

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位 MyData 資料集清單 By 市府
     *
     * @param caseSetId
     * @param version
     * @return
     */
    private List<CasesetMydataColumnDto> getCasesetKApiMyDataColumns(String caseSetId, Integer version) {
        List<CasesetMydataColumnDto> casesetColumns = getCasesetMyDataColumns(caseSetId, version);
        return casesetColumns.stream().filter(x -> x.getMyDataClientId().equals(this.KAPI_MYDATA_CLIENTID)).collect(Collectors.toList());
    } //getCasesetKApiMyDataColumns

    /**
     * Geo 20211120
     * 設定 Class 變數 resourceCasesetMergeMyDataColumns
     *
     * @param casesetColumns
     */
    private void setResourceCasesetMergeMyDataColumns(String txId, List<CasesetMydataColumnDto> casesetColumns) {

        for (CasesetMydataColumnDto dto : casesetColumns) {
            if (StringUtils.isNotBlank(dto.getMyDataId()) && StringUtils.isNotBlank(dto.getMyDataColumn())) {
                List<ColumnModel> vaList = null;
                if (this.getResourceCasesetMergeMyDataColumns(txId).containsKey(dto.getMyDataId())) {
                    vaList = this.getResourceCasesetMergeMyDataColumns(txId).get(dto.getMyDataId());
                } else {
                    vaList = new ArrayList<ColumnModel>();
                }

                ColumnModel columnModel = new ColumnModel(dto.getColumnId(), dto.getMyDataColumn(), dto.getType(), dto.getMyDataType(), dto.getFileName(), dto.getHeaderRow(), dto.getDataStartRow());
                vaList.add(columnModel);

                this.getResourceCasesetMergeMyDataColumns(txId).put(dto.getMyDataId().trim(), vaList);

            }
        }
    } //setResourceCasesetMergeMyDataColumns

    /**
     * Geo 20211120
     * 取的 KgoCasesetColumn
     *
     * @param casesetMyDataColumns
     * @return
     */
    private KgoCasesetColumn getKgoCasesetColumnOne(List<KgoCasesetColumn> casesetMyDataColumns) {
        if (casesetMyDataColumns.size() > 0) {
            return casesetMyDataColumns.get(0);
        } else {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_NOT_EXIST));
        }
    } //getKgoCasesetColumnOne

    /**
     * Geo 20211120
     * 取的 CasesetMydataColumnDto
     *
     * @param casesetMyDataColumns
     * @return
     */
    private CasesetMydataColumnDto getKgoCasesetColumnDtoOne(List<CasesetMydataColumnDto> casesetMyDataColumns) {
        if (casesetMyDataColumns.size() > 0) {
            return casesetMyDataColumns.get(0);
        } else {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_NOT_EXIST));
        }
    } //getKgoCasesetColumnDtoOne

    /**
     * Geo 20211120
     * 取得申請 MyData 服務資料
     *
     * @param myDataClientId
     * @return
     */
    private KgoMydataService getkgoMydataService(String myDataClientId) {
        Optional<KgoMydataService> data = this.kgoMydataServiceRepository.findById(myDataClientId);
        if (data.isPresent()) {
            return data.get();
        } else {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_NOT_EXIST));
        }
    } //getkgoMydataService

    /**
     * Geo 20211120
     * 設定 Class 變數 resourceMydataColumns
     *
     * @param resourceList
     */
    private void setResourceMydataColumns(String txId, List<String> resourceList) {
        LOGGER.info("setResourceMydataColumns txId="+txId);
        List<KgoMydataColumn> data = this.kgoMydataColumnRepository.findByIdMyDataIdIn(resourceList);
        for (KgoMydataColumn kgoMydataColumn : data) {
            this.getResourceMydataColumns(txId).put(kgoMydataColumn.getId(), kgoMydataColumn);
        }
    } //setResourceMydataColumns

    /**
     * Geo 20211120
     * 取得設定 步驟 3、3.1 請求身分驗證 data
     *
     * @param birthday
     * @return
     */
    private UserInfoBO getUserInfoBO(String pid, String birthday) {
        UserInfoBO data = new UserInfoBO();
        data.setPid(pid);

        if (StringUtils.isBlank(data.getPid())) {
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
        }

        data.setBirthday(birthday);

        return data;
    } //getUserInfoBO

    /**
     * Geo 20211120
     * 產生 Call MyDataServeice 參數 ClientInfoBO
     *
     * @param service
     * @param resourceList
     * @return
     */
    private ClientInfoBO generateClientInfoBO(KgoMydataService service, List<String> resourceList) {
        return new ClientInfoBO(service.getClientId(), service.getCbcIv(), service.getClientSecret(), resourceList);
    } //generateClientInfoBO

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位 MyData 資料集清單
     *
     * @return
     */
    private List<String> getCasesetUseMyDataIds(String caseSetId, Integer version) {
        List<CasesetMydataColumnDto> casesetMyDataColumns = this.getCasesetMyDataColumns(caseSetId, version);
        return casesetMyDataColumns.stream().map(x -> x.getMyDataId()).distinct().collect(Collectors.toList());
    } //getCasesetUseMyDataIds

    /**
     * Geo 20211120
     * 取得案件表單設定畫面欄位 MyData 資料集清單
     *
     * @param casesetMyDataColumns
     * @return
     */
    private List<String> getCasesetUseMyDataIds(List<CasesetMydataColumnDto> casesetMyDataColumns) {
        return casesetMyDataColumns.stream().map(x -> x.getMyDataId()).distinct().collect(Collectors.toList());
    } //getCasesetUseMyDataIds

    /**
     * Geo 20211120
     * 取得 txId CaseSetId MyData 下載回來欄位資料
     *
     * @param txId
     * @return
     * @throws Exception
     */
    private MyDataServiceDownloadRs setResourceTxIdCasesetMergeMyDataColumnsVal(String caseSetId, Integer version, String txId, String pId) throws Exception {

        LOGGER.info("====================download MyData(" + caseSetId + ")================");
        Optional<CasesetMydataColumnDto> casesetColumnDto = getCasesetRealMyDataColumns(caseSetId, version).stream().findAny();

        MyDataServiceDownloadRs rs = new MyDataServiceDownloadRs();
        if (casesetColumnDto.isPresent()) {
            // 下載國發會 MyData
            rs = this.getMyDataTxidDownloadFile(txId, casesetColumnDto.get().getMyDataClientId());
        } else {
            rs.setTxId(txId);
            rs.setDelaySeconds("0");
            rs.setDpDataMap(new HashMap<>());
        }

        Map<String, Object> dataMap = getMyDataTxIdRequestVal(txId);
        if ((ObjectUtils.isNotEmpty(dataMap) || StringUtils.isNoneBlank(pId)) && NumberUtils.toInt(rs.getDelaySeconds(), 0) == 0) {
            if (ObjectUtils.isNotEmpty(dataMap)) {
                pId = (String) dataMap.get("_id");
            }
            // 下載市府 MyData
            Map<String, ServiceDataBO> kcgMap = getKcgMyDataDownloadFile(caseSetId, version, pId);
            if (ObjectUtils.isEmpty(rs.getDpDataMap()))
                rs.setDpDataMap(new HashMap<>());

            if (ObjectUtils.isNotEmpty(kcgMap))
                rs.getDpDataMap().putAll(kcgMap);
        }

        if (ObjectUtils.isNotEmpty(rs) && ObjectUtils.isNotEmpty(rs.getDpDataMap()) && !rs.getDpDataMap().isEmpty()) {
            for (Entry<String, ServiceDataBO> resource : rs.getDpDataMap().entrySet()) {
                Map<String, Object> value = this.getResourceMergeCasesetMyDataColumnsVal(txId, resource.getKey(), rs.getDpDataMap().get(resource.getKey()));
                if (isDebug) {
                    LOGGER.info(String.format("\n txId: %s, resourceId: %s, Merge ColumnsVal: %s", txId, resource.getKey(), JsonUtil.toJSONString(value)));
                }

                // this.getSpecialCSVResourceTxIdCasesetMergeMyDataColumnsVal(caseSetId,
                // resource.getKey(), resource.getValue(), value);
                this.getResourceTxIdCasesetMergeMyDataColumnsVal(txId).put(resource.getKey(), value);
            }
        }
        return rs;
    } //setResourceTxIdCasesetMergeMyDataColumnsVal

    /**
     * Geo 20211120
     * 取得 CaseSetId MyData 下載回來欄位資料 根據不同 resourceId 處理
     *
     * @param resourceId
     * @return
     * @throws Exception
     */
    private Map<String, Object> getResourceMergeCasesetMyDataColumnsVal(String txId, String resourceId, ServiceDataBO serviceDataBO) throws Exception {

        List<ColumnModel> columnNames = this.getResourceCasesetMergeMyDataColumns(txId).get(resourceId);
        return this.getResourceDataMergeMyDataColumnsVal(resourceId, serviceDataBO, columnNames);
    } //getResourceMergeCasesetMyDataColumnsVal

    /**
     * Geo 20211120
     * 取出 MyData 合併欄位值 By myDataId (測試用)
     *
     * @param resourceId
     * @param serviceDataBO
     * @return
     * @throws Exception
     */
    private Map<String, Object> getResourceMergeMyDataColumnsVal(String resourceId, ServiceDataBO serviceDataBO) throws Exception {
        List<ColumnModel> columnNames = this.getResourceMergeMyDataColumns(resourceId);
        return this.getResourceDataMergeMyDataColumnsVal(resourceId, serviceDataBO, columnNames);
    } //getResourceMergeMyDataColumnsVal

    /**
     * Geo 20211120
     * 取出 MyData 合併欄位值 By myDataId
     *
     * @param resourceId
     * @param serviceDataBO
     * @param columnNames
     * @return
     * @throws Exception
     */
    private Map<String, Object> getResourceDataMergeMyDataColumnsVal(String resourceId, ServiceDataBO serviceDataBO, List<ColumnModel> columnNames) throws Exception {
        switch (resourceId) {
            case "API.1VFIQjmY1G": // 綜所稅及房地合一稅繳納、欠稅及退稅紀錄
                this.comprehensiveTaxPayRecordsMyDataHelper.setMyDataFile(serviceDataBO);
                return comprehensiveTaxPayRecordsMyDataHelper.getColumnData(columnNames);

            case "API.60GGfgGX1A": // 車籍資料
                this.carInfoMyDataHelper.setMyDataFile(serviceDataBO);
                return carInfoMyDataHelper.getColumnData(columnNames);

            case "API.7QovE2Gev6": // 個人戶籍資料
                this.personalHouseholdRegistrationMyDataHelper.setMyDataFile(serviceDataBO);
                return personalHouseholdRegistrationMyDataHelper.getColumnData(columnNames);

            case "API.AbAYGIjoYw": // 低收及中低收列冊資料
                this.lowIncomeInfoMyDataHelper.setMyDataFile(serviceDataBO);
                return lowIncomeInfoMyDataHelper.getColumnData(columnNames);

            case "API.ea0klQl5Wp": // 公司負責人、董監事與經理人之公司登記資料
                this.companyManagersMyDataHelper.setMyDataFile(serviceDataBO);
                return companyManagersMyDataHelper.getColumnData(columnNames);

            case "API.idPhotoRev": // 戶政國民身分證影像
                this.nationalIdCardHelper.setMyDataFile(serviceDataBO);
                return nationalIdCardHelper.getColumnData(columnNames);

            case "API.Kr1C3b1ijJ": // 親屬關係資料
                this.kinshipInfoMyDataHelper.setMyDataFile(serviceDataBO);
                return kinshipInfoMyDataHelper.getColumnData(columnNames);

            case "API.KvvyRZSc5K": // 地籍及實價資料
                this.cadastralAndActualPriceMyDataHelper.setMyDataFile(serviceDataBO);
                return cadastralAndActualPriceMyDataHelper.getColumnData(columnNames);

            case "API.Mo23SDWhsn": // 財產資料
                this.propertyInfoMyDataHelper.setMyDataFile(serviceDataBO);
                return propertyInfoMyDataHelper.getColumnData(columnNames);

            case "API.n0c9qVZBQI": // 駕籍資料
                this.driverLicenseMyDataHelper.setMyDataFile(serviceDataBO);
                return driverLicenseMyDataHelper.getColumnData(columnNames);

            case "API.syWqjr4flJ": // 個人所得資料
                this.personalIncomeTaxMyDataHelper.setMyDataFile(serviceDataBO);
                return personalIncomeTaxMyDataHelper.getColumnData(columnNames);

            case "API.UDauDOLyZg": // 現戶全戶戶籍資料
                this.currentHouseholdRegistrationMyDataHelper.setMyDataFile(serviceDataBO);
                return currentHouseholdRegistrationMyDataHelper.getColumnData(columnNames);

            case "API.zH584wn59r": // 個人投退保資料
                this.personalInsuranceInfoMyDataHelper.setMyDataFile(serviceDataBO);
                return personalInsuranceInfoMyDataHelper.getColumnData(columnNames);

            case "API.UZQkKbsOpz": // 勞工保險被保險人投保資料(明細)
                this.laborInsuranceInsuranceInfoMyDataHelper.setMyDataFile(serviceDataBO);
                return laborInsuranceInsuranceInfoMyDataHelper.getColumnData(columnNames);

            case "API.EZ6XjKf1B4": // 國民年金
                LOGGER.info("getResourceDataMergeMyDataColumnsVal 國民年金");
                this.nationalPensionMyDataHelper.setMyDataFile(serviceDataBO);
                return nationalPensionMyDataHelper.getColumnData(columnNames);

            case "API.1qIr0nM0BT": // 保費繳納紀錄
                LOGGER.info("getResourceDataMergeMyDataColumnsVal 保費繳納紀錄");
                this.paymentOfPremiumMyDataHelper.setMyDataFile(serviceDataBO);
                return paymentOfPremiumMyDataHelper.getColumnData(columnNames);

            case "ba5ab006-321e-495c-be27-14386b5da9cc": // 低收入戶
                this.lowIncomeInfoKgoCityApiHelper.setMyDataFile(serviceDataBO);
                return lowIncomeInfoKgoCityApiHelper.getColumnData(columnNames);

            case "9d76ebff-a285-4451-980d-d916cc3a45e6": // 中低收入戶
                this.lowAndMiddleIncomeInfoKgoCityApiHelper.setMyDataFile(serviceDataBO);
                return lowAndMiddleIncomeInfoKgoCityApiHelper.getColumnData(columnNames);

            case "7d65cf3d-6030-446e-b86d-7c0fe59c5624": // 身心障礙
                this.psychosomaticDisorderKgoCityApiHelper.setMyDataFile(serviceDataBO);
                return psychosomaticDisorderKgoCityApiHelper.getColumnData(columnNames);

            case "API.ccuZXSsnGv": // 中低收入老人生活津貼資料
                LOGGER.info("getResourceDataMergeMyDataColumnsVal 中低收入老人生活津貼資料");
                this.lowIncomeOldPersonMyDataHelper.setMyDataFile(serviceDataBO);
                return lowIncomeOldPersonMyDataHelper.getColumnData(columnNames);

            case "API.nyhnLM1fve": // 身心障礙資格
                LOGGER.info("getResourceDataMergeMyDataColumnsVal 中低收入老人生活津貼資料");
                this.disabilityIdentificationMyDataHelper.setMyDataFile(serviceDataBO);
                return disabilityIdentificationMyDataHelper.getColumnData(columnNames);

            case "API.QOHFReaQFc": // 身心障礙者生活補助資料
                LOGGER.info("getResourceDataMergeMyDataColumnsVal 中低收入老人生活津貼資料");
                this.disabilityAllowanceMyDataHelper.setMyDataFile(serviceDataBO);
                return disabilityAllowanceMyDataHelper.getColumnData(columnNames);


            default:
                return null;
        } // switch (resourceId)
    } //getResourceDataMergeMyDataColumnsVal

    /**
     * GEO 20211120
     * 下載 MyData 檔案
     *
     * @param txId
     * @param myDataClientId
     * @return
     */
    private MyDataServiceDownloadRs getMyDataTxidDownloadFile(String txId, String myDataClientId) {

        MyDataServiceDownloadRs rs = null;
        if (StringUtils.isNoneBlank(myDataClientId) && StringUtils.isNoneBlank(txId)) {
            if (downloadFileUseTemp) {
                rs = getMyDataTxidDownloadFileTemp(txId, myDataClientId);
            } else {
                rs = getMyDataTxidDownloadFileReal(txId, myDataClientId);
            }
        }
        return rs;
    } //getMyDataTxidDownloadFile

    /**
     * GEO 20211120
     * 下載 MyData 檔案 實際從 MyData 拿資料
     *
     * @param txId
     * @param myDataClientId
     * @return
     */
    private MyDataServiceDownloadRs getMyDataTxidDownloadFileReal(String txId, String myDataClientId) {
        LOGGER.info("====================getMyDataTxidDownloadFile========");
        LOGGER.info("txId:" + txId);
        LOGGER.info("MyData ClientId:" + myDataClientId);
        MyDataServicePermissionTicketRs rs;
        try {
            MyDataServicePermissionTicketRq rq = new MyDataServicePermissionTicketRq();
            rq.setTxId(txId);
            rs = this.myDataService.getPermissionTicketAndSecretKey(rq);

            LOGGER.info("====================getPermissionTicketAndSecretKey========");
            LOGGER.info("rq:" + JSON.toJSONString(rq));
            LOGGER.info("rs:" + JSON.toJSONString(rs));
        } catch (Exception e) {
            // TODO: handle exception
            throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.DATA_NOT_EXIST));
        }

        KgoMydataService service = this.getkgoMydataService(myDataClientId);
        LOGGER.info("====================downloadMyData========");
        LOGGER.info("txId:" + txId);
        LOGGER.info("clientIv:" + service.getCbcIv());
        LOGGER.info("clientSecretKey:" + service.getClientSecret());
        LOGGER.info("permissionTicket:" + rs.getPermissionTicket());
        LOGGER.info("secretKey:" + rs.getSecretKey());

        LOGGER.info("====================sessionId=============");
        LOGGER.info("sessionId:" + getSessionId());

        return this.myDataService.downloadMyData(txId, service.getClientId(), service.getCbcIv(), service.getClientSecret(), rs.getPermissionTicket(), rs.getSecretKey());
    } //getMyDataTxidDownloadFileReal

    /**
     * GEO 20211120
     * 下載 MyData 檔案 從 MyData/temp 資料夾拿資料
     *
     * @param txId
     * @param myDataClientId
     * @return
     */
    private MyDataServiceDownloadRs getMyDataTxidDownloadFileTemp(String txId, String myDataClientId) {

        LOGGER.info("====================getMyDataTxidDownloadFile========");
        LOGGER.info("txId:" + txId);
        LOGGER.info("MyData ClientId:" + myDataClientId);

        KgoMydataService service = this.getkgoMydataService(myDataClientId);
        LOGGER.info("====================downloadMyData========");
        LOGGER.info("txId:" + txId);
        LOGGER.info("clientIv:" + service.getCbcIv());
        LOGGER.info("clientSecretKey:" + service.getClientSecret());

        return this.myDataService.downloadMyDataTemp(txId, service.getClientId(), service.getCbcIv(), service.getClientSecret(), "", "");
    } //getMyDataTxidDownloadFileTemp

    /**
     * GEO 20211120
     * 下載 市府 MyData 檔案
     *
     * @return
     */
    private Map<String, ServiceDataBO> getKcgMyDataDownloadFile(String caseSetId, Integer version, String Id) {
        List<CasesetMydataColumnDto> dtos = getCasesetKApiMyDataColumns(caseSetId, version);
        List<String> resourceList = getCasesetUseMyDataIds(dtos);
        Map<String, ServiceDataBO> rsMap = new HashMap<>();

        for (String resource : resourceList) {
            KcgCityApiServiceType serviceType = KcgCityApiServiceType.getKcgCityApiServiceType(resource);
            if (ObjectUtils.isNotEmpty(serviceType)) {
                try {
                    String jsonString = csllKcgMyDataService(serviceType, Id);

                    ServiceDataBO serviceDataBO = new ServiceDataBO();
                    serviceDataBO.setJsonData(Arrays.asList(new ServiceDataFileBO(serviceType.getServiceId() + ".json", jsonString)));
                    rsMap.put(resource, serviceDataBO);
                } catch (Exception e) {
                    LOGGER.error("getKcgMyDataDownloadFile >>>> " + e.getMessage(), e);
                }
            }
        }

        return rsMap;
    } //getKcgMyDataDownloadFile

    /**
     * GEO 20211120
     * 取的 市府 Mydata
     *
     * @param serviceType
     * @return
     * @throws Exception
     */
    private String csllKcgMyDataService(KcgCityApiServiceType serviceType, String id) throws Exception {
        // return csllKcgMyDataServiceReal(serviceType);
        if (this.isDevMode()) {
            return csllKcgMyDataServiceMock(serviceType, id);
        } else {
            return csllKcgMyDataServiceReal(serviceType, id);
        }
    } //csllKcgMyDataService

    /**
     * GEO 20211120
     * 取的 市府 Mydata 實際由城市資料平台拿取
     *
     * @param serviceType
     * @return
     * @throws Exception
     */
    private String csllKcgMyDataServiceReal(KcgCityApiServiceType serviceType, String id) throws Exception {
        // return callKcgCityApiService.getKcgCityApiJsonStrWithMoicaLogin(serviceType);

        try {
            // 身分證號
            Map<String, String> paramsMap = new HashMap<>();
//			paramsMap.put("id_", loginUser.getKcgMoicaCardInfo().getMoicaUserTwSsn());
            paramsMap.put("id_", id);
            LOGGER.info("<<<<<< csllKcgMyDataServiceReal:" + serviceType.getServiceId() + " >>>>");
            LOGGER.info("paramsMap:" + JsonUtil.toJSONString(paramsMap));
            return callKcgCityApiService.getKcgCityApiJsonStrWithMoicaLogin(serviceType, paramsMap);
//			}
        } catch (KgoApiException apiE) {
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("csllKcgMyDataServiceReal >>>> [" + serviceType.getServiceId() + " ]" + e.getMessage(), e);
            return null;
        }
    } //csllKcgMyDataServiceReal

    /**
     * GEO 20211120
     * 取的 市府 Mydata Mock 資料
     *
     * @param serviceType
     * @return
     */
    private String csllKcgMyDataServiceMock(KcgCityApiServiceType serviceType, String id) {

        LOGGER.info("<<<<<< csllKcgMyDataServiceMock:" + serviceType.getServiceId() + " >>>>");
        LOGGER.info("id:" + id);
        switch (serviceType) {
            case DISABILITY:
                return "{\n" + "    \"contentType\": \"application/json; charset=utf-8\",\n" + "    \"isImage\": false,\n" + "    \"data\": {\n" + "        \"Id\": \"S2xxx3906\",\n"
                        + "        \"Typename\": \"ICF二類中度\",\n" + "        \"Username\": \"黃林美惠\",\n" + "        \"Profile\": {\n" + "            \"Gender\": \"女\",\n" + "            \"Age\": 66,\n"
                        + "            \"Address\": \"彌陀區\"\n" + "        }\n" + "    },\n" + "    \"id\": \"0b5856e6-2ce9-4b05-a58c-f03fcda442c4\",\n" + "    \"success\": true\n" + "}";
            case LOW_MIDDLE_INCOME:
            case LOW_AND_MIDDLE_INCOME:
                return "{\n" + "    \"contentType\": \"application/json; charset=utf-8\",\n" + "    \"isImage\": false,\n" + "    \"data\": {\n" + "        \"Id\": \"E2xxx5508\",\n"
                        + "        \"Typename\": \"中低收\",\n" + "        \"Username\": \"陳湘其\",\n" + "        \"Profile\": {\n" + "            \"Gender\": \"女\",\n" + "            \"Age\": 15,\n"
                        + "            \"Address\": \"鹽埕區\"\n" + "        }\n" + "    },\n" + "    \"id\": \"62f02fa0-6079-40ca-9603-61a84919fcea\",\n" + "    \"success\": true\n" + "}";
            default:
                return "{\n" + "    \"contentType\": \"application/json; charset=utf-8\",\n" + "    \"isImage\": false,\n" + "    \"data\": {\n" + "        \"Id\": \"" + id + "\",\n"
                        + "        \"Typename\": \"無\",\n" + "        \"Username\": \"無\",\n" + "        \"Profile\": {\n" + "            \"Gender\": \"無\",\n" + "            \"Age\": -1,\n"
                        + "            \"Address\": \"無\"\n" + "        }\n" + "    },\n" + "    \"id\": \"" + serviceType.getServiceId() + "\",\n" + "    \"success\": true\n" + "}";
        }
    } //csllKcgMyDataServiceMock

    /**
     * GEO 20211120
     * 設定 Class 變數 For CaseSetColumn 跟 MyDataColumn
     *
     * @param caseSetId
     * @param version
     * @throws Exception
     */
    private MyDataServiceDownloadRs setCaseSetAndMyDataResourceColumns(String caseSetId, Integer version, String txId, String pId) throws Exception {
        LOGGER.info("下載 setCaseSetAndMyDataResourceColumns caseSetId"+caseSetId);
        LOGGER.info("下載 setCaseSetAndMyDataResourceColumns txId"+txId);
        LOGGER.info("下載 setCaseSetAndMyDataResourceColumns pId"+pId);
        List<KgoCasesetColumn> casesetColumns = getCasesetColumns(caseSetId, version);
        List<CasesetMydataColumnDto> dtos = getCasesetMyDataColumns(caseSetId, version);
        List<String> resourceList = getCasesetUseMyDataIds(dtos);

        // 設定 MyDataColumn （順序不能調動）
        this.setResourceMydataColumns(txId, resourceList);

        // 設定 CaseSetColumn （順序不能調動）
        this.setResourceCasesetMergeMyDataColumns(txId, dtos);

        // 設定 MyData 合併資料
        return this.setResourceTxIdCasesetMergeMyDataColumnsVal(caseSetId, version, txId, pId);
    } //setCaseSetAndMyDataResourceColumns

    /**
     * GEO 20211120
     *
     * @param myDataClientId
     * @param encryptStr
     * @return
     * @throws Exception
     */
    private String decryptTxId(String myDataClientId, String encryptStr) throws Exception {

        KgoMydataService kgoMydataService = this.getkgoMydataService(myDataClientId);
        String clientSecretKey = kgoMydataService.getClientSecret() + kgoMydataService.getClientSecret();

        byte[] source = Base64Utils.decodeFromString(encryptStr);
        byte[] encryptedData = Base64.getUrlDecoder().decode(source);

        byte[] data = AESUtil.decrypt(kgoMydataService.getCbcIv(), ALG, encryptedData, clientSecretKey.getBytes());
        return new String(data);
    } //decryptTxId

    /**
     * GEO 20211120
     *
     * @param myDataClientId
     * @param txId
     * @return
     * @throws Exception
     */
    private String encryptTxId(String myDataClientId, String txId) throws Exception {
        KgoMydataService kgoMydataService = this.getkgoMydataService(myDataClientId);
        String clientSecretKey = kgoMydataService.getClientSecret() + kgoMydataService.getClientSecret();

        byte[] data = AESUtil.encrypt(txId, kgoMydataService.getCbcIv(), clientSecretKey.getBytes(), ALG);
        return Base64Utils.encodeToString(data);
    } //encryptTxId

    /**
     * GEO 20211120
     *
     * @param keyName
     * @param args
     * @return
     */
    private String getMessage(String keyName, Object... args) {
        return messageUtil.getMessage(keyName, args);
    }

    /**
     * GEO 20211120
     *
     * @param casesetColumn
     * @param kgoCaseset
     * @param rq
     * @return
     * @throws Exception
     */
    public ValidationActionViewForm saveValidation(List<KgoCasesetColumn> casesetColumn, KgoCaseset kgoCaseset, SaveActionRq rq) throws Exception {

        // Map<String,KgoFrontEndApiError> msgMap = new
        // HashMap<String,KgoFrontEndApiError>();
        ValidationActionViewForm viewForm = new ValidationActionViewForm();
        List<ValidationViewForm> vMsg = new ArrayList<ValidationViewForm>();

        // 檢核一年內重複申請
        // duplicate apply when one year
        String applyUser = getColumnViewForm(rq.getColumnData(), "ID");
        if (StringUtils.isNoneBlank(applyUser)) {
            Date applyDate = DateUtil.modifyDate(DateUtil.getCurrentDate(), 0, -10, 0);
            //List<String> source = Arrays.asList(new String[]{"S2020121200004", "S2020112500002", "S2020112500003", "S2020112500004", "S2020122300002"});
            List<String> source = Arrays.asList(new String[]{"S2020112500002"});

            List<KgoCaseMain> kgoCaseMains = kgoCaseMainRepository.findByApplyDateAfterAndApplyUserAndCaseSetIdInAndCaseSetIdInList(applyDate, applyUser, source, rq.getCaseSetId());
            if (ObjectUtils.isNotEmpty(kgoCaseMains) && kgoCaseMains.size() > 0) {
                Optional<KgoCaseset> applyKgoCaseset = kgoCasesetRepository.findById(kgoCaseMains.get(0).getCaseSetId());
                if (applyKgoCaseset.isPresent()) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.REPEAT_APPIY_ONE_YEAR, applyKgoCaseset.get().getCaseSetName()));
                } else {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.REPEAT_APPIY_ONE_YEAR, kgoCaseset.getCaseSetName()));
                }
            }
            List<String> civilAffairsList = new ArrayList<>();
            civilAffairsList.add("S2020111010007");
            civilAffairsList.add("S2020111010008");
            civilAffairsList.add("S2020111010009");
            //檢核民政局黑名單身分證
            LOGGER.info("saveValidation applyUser="+applyUser);
            Boolean isExit = geoKgoBlackListRepository.existsByUserId(applyUser);
            if (isExit){
                for (String str:civilAffairsList){
                    if(str.equals(rq.getCaseSetId())) throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED, kgoCaseset.getCaseSetName()));
                } // for (String str:civilAffairsList)
            } //if (isExit)
        } // if (StringUtils.isNoneBlank(applyUser))

        List<KgoCasesetColumnChild> casesetColumnChilds = this.getCasesetColumnChild(rq.getCaseSetId(), rq.getVersion());
        Map<KgoCasesetColumnChildPK, KgoCasesetColumnChild> columnChildMap = this.getCasesetColumnChildMap(casesetColumnChilds);
        Map<String, Map<String, String>> columnPColumnMap = getCasesetColumnChildPColumnIdMap(casesetColumnChilds);
        Map<String, Map<String, String>> columnVGroupMap = this.getCasesetColumnChildVGroupMap(casesetColumnChilds);
        List<KgoZipF3AreaDto> f3List = this.kgoZipRepository.findByZipF3AreaList();

        for (SaveActionColumnViewForm caseColumn : rq.getColumnData()) {

            Map<String, String> pColumnMap = columnPColumnMap.get(caseColumn.getColumnId());
            Map<String, String> vGroupMap = columnVGroupMap.get(caseColumn.getColumnId());

            KgoCasesetColumnPK id = new KgoCasesetColumnPK();
            id.setCaseSetId(kgoCaseset.getCaseSetId());
            id.setVersion(rq.getVersion());
            id.setColumnId(caseColumn.getColumnId());
            KgoCasesetColumn kgoCasesetColumn = KgoUtil.getKgoCasesetColumnFromCashe(id);

            if (ObjectUtils.isNotEmpty(pColumnMap)) {
                if (pColumnMap.containsKey(caseColumn.getColumnId())) {
                    pColumnMap.put(caseColumn.getColumnId(), caseColumn.getValue());
                }
            }

            if (ObjectUtils.isNotEmpty(kgoCasesetColumn)) {

                ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(kgoCasesetColumn.getColumnType());
                // 必填檢查
                if (kgoCasesetColumn.getIsMustKey() && columnTypeEnum != ColumnTypeEnum.M) {
                    if (StringUtils.isBlank(caseColumn.getValue())) {
                        vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), kgoCasesetColumn.getColumnName(),
                                ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), "")));
                        continue;
                    }
                }

                // 長度限制檢查
//				if (StringUtils.isNoneBlank(caseColumn.getValue())) {
//					if (caseColumn.getValue().length() > ObjectUtils.defaultIfNull(kgoCasesetColumn.getLength(), 999999)) {
//						vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), kgoCasesetColumn.getColumnName(),
//								ErrorResult.getErrorDescI18n(KgoFrontEndApiError.DATA_FORMAT_ERROR.getErrorMsgKey())));
//						continue;
//					}
//				}

                if ((columnTypeEnum == ColumnTypeEnum.ADDRESSTEXT) && ObjectUtils.isNotEmpty(f3List)) {
                    String f3Name = this.commonServiceHelper.getF3Name(caseColumn.getValue(), f3List);
                    if (StringUtils.isNoneBlank(f3Name))
                        viewForm.setF3Name(f3Name);
                }
                saveValidationComplex(kgoCasesetColumn, caseColumn, columnChildMap, pColumnMap, vGroupMap, vMsg);

            }
        }

        viewForm.setValidationMsg(vMsg.stream().distinct().collect(Collectors.toList()));
        return viewForm;
    } //saveValidation

    /**
     * GEO 20211120
     *
     * @param kgoCasesetColumn
     * @param caseColumn
     * @param columnChildMap
     * @param pColumnMap
     * @param vGroupMap
     * @param vMsg
     */
    private void saveValidationComplex(KgoCasesetColumn kgoCasesetColumn, SaveActionColumnViewForm caseColumn, Map<KgoCasesetColumnChildPK, KgoCasesetColumnChild> columnChildMap,
                                       Map<String, String> pColumnMap, Map<String, String> vGroupMap, List<ValidationViewForm> vMsg) {

        Map<String, Map<String, String>> pColumnDetailsMap = new HashMap<>();
        for (List<SaveActionCColumnViewForm> complexList : caseColumn.getComplex()) {
            for (SaveActionCColumnViewForm complex : complexList) {
                if (StringUtils.isBlank(complex.getColumnId())) {
                    vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), kgoCasesetColumn.getColumnName(), ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), "")));
                    return;
                }
                KgoCasesetColumnChildPK kgoCasesetColumnChildPK = new KgoCasesetColumnChildPK();
                kgoCasesetColumnChildPK.setCaseSetId(kgoCasesetColumn.getId().getCaseSetId());
                kgoCasesetColumnChildPK.setVersion(kgoCasesetColumn.getId().getVersion());
                kgoCasesetColumnChildPK.setColumnId(kgoCasesetColumn.getId().getColumnId());
                kgoCasesetColumnChildPK.setCColumnId(complex.getColumnId());

                if (columnChildMap.containsKey(kgoCasesetColumnChildPK)) {

                    KgoCasesetColumnChild childColumn = columnChildMap.get(kgoCasesetColumnChildPK);
                    if (ObjectUtils.isNotEmpty(childColumn)) {

                        if (ObjectUtils.isNotEmpty(pColumnMap)) {
                            if (pColumnMap.containsKey(complex.getColumnId())) {
                                if (StringUtils.isBlank(pColumnMap.get(complex.getColumnId()))) {
                                    pColumnMap.put(complex.getColumnId(), complex.getValue());
                                }
                            }

                            if (pColumnMap.containsKey(childColumn.getPColumnId()) && StringUtils.isNoneBlank(childColumn.getPColumnId())) {
                                Map<String, String> map = new HashMap<>();
                                map.put(StringUtils.defaultIfBlank(childColumn.getFText(), childColumn.getBText()), complex.getValue());
                                pColumnDetailsMap.put(childColumn.getPColumnId(), map);
                            }
                        }

                        if (childColumn.getIsMustKey() && StringUtils.isBlank(childColumn.getVGroup()) && StringUtils.isBlank(complex.getValue())) {
                            vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), StringUtils.defaultIfBlank(childColumn.getFText(), childColumn.getBText()),
                                    ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), "")));
                            return;
                        }

                        if (ObjectUtils.isNotEmpty(vGroupMap)) {
                            if (vGroupMap.containsKey(childColumn.getVGroup())) {
                                if (StringUtils.isBlank(vGroupMap.get(childColumn.getVGroup()))) {
                                    vGroupMap.put(childColumn.getVGroup(), complex.getValue());
                                }
                            }
                        }

//						if (StringUtils.isNoneBlank(complex.getValue()) && complex.getValue().length() > ObjectUtils.defaultIfNull(childColumn.getLength(), 999999)) {
//							vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), kgoCasesetColumn.getColumnName(),
//									ErrorResult.getErrorDescI18n(KgoFrontEndApiError.DATA_FORMAT_ERROR.getErrorMsgKey())));
//							return;
//						}
                    }
                }
            }
        }

        if (ObjectUtils.isNotEmpty(vGroupMap)) {
            for (Entry<String, String> item : vGroupMap.entrySet()) {
                if (StringUtils.isBlank(item.getValue())) {
                    vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), kgoCasesetColumn.getColumnName(), ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), "")));
                    return;
                }
            }
        }

        if (ObjectUtils.isNotEmpty(pColumnMap)) {
            for (Entry<String, String> item : pColumnMap.entrySet()) {
                if (StringUtils.isNoneBlank(item.getValue())) {
                    if (pColumnDetailsMap.containsKey(item.getKey())) {
                        for (Entry<String, String> details : pColumnDetailsMap.get(item.getKey()).entrySet()) {
                            if (StringUtils.isBlank(details.getValue())) {
                                vMsg.add(new ValidationViewForm(caseColumn.getColumnId(), details.getKey(), ErrorResult.getErrorDescI18n(KgoFrontEndApiError.INPUT_IS_EMPTY.getErrorMsgKey(), "")));
                                return;
                            }
                        }
                    }
                }
            }
        }

    } //saveValidationComplex

    /**
     * GEO 20211120
     *
     * @param caseId
     * @param kgoCaseset
     * @param rq
     * @param viewForm
     * @return
     * @throws Exception
     */
    private KgoCaseMain saveKgoCaseMain(String caseId, KgoCaseset kgoCaseset, SaveActionRq rq, SaveActionViewForm viewForm) throws Exception {

        // 固定欄位 ID、Name、Email
        KgoCaseMain kgoCaseMain = new KgoCaseMain();

        kgoCaseMain.setIsOpenOrganForm(kgoCaseset.getIsOpenOrganForm());

        // 案件編號
        kgoCaseMain.setCaseId(caseId);
        viewForm.setCaseId(caseId);

        // 申請人員
        String applyUser = getColumnViewForm(rq.getColumnData(), "ID");
        kgoCaseMain.setApplyUser(applyUser);
        kgoCaseMain.setIdSHA256(CryptUtil.SHA256Encrypt(applyUser));

        // viewForm.setApplyUser(applyUser);
        // 申請人員姓名
        String applyUserName = getColumnViewForm(rq.getColumnData(), "Name");
        kgoCaseMain.setApplyUserName(applyUserName);
        viewForm.setApplyUserName(applyUserName);

        // 申請電話
        String cellPhone = getColumnViewForm(rq.getColumnData(), "CellPhone", false);
        kgoCaseMain.setCellPhone(cellPhone);

        // 受理機關，承辦人員
        AcceptSetEnum acceptSetEnum = AcceptSetEnum.getEnum(kgoCaseset.getAcceptSet());
        if (acceptSetEnum == AcceptSetEnum.OFFICER) {
            kgoCaseMain.setCaseOrgan(kgoCaseset.getOwnerOrgan());
        } else {
            // 受理機關(若受理設定為機關或區機關，則存入機關代號)
            kgoCaseMain.setCaseOrgan(rq.getCaseOrgan());
        }

        viewForm.setCaseOrganId(kgoCaseMain.getCaseOrgan());
        Optional<KgoOrgan> kgoOrgan = kgoOrganRepository.findById(viewForm.getCaseOrganId());
        if (kgoOrgan.isPresent()) {
            viewForm.setCaseOrganName(kgoOrgan.get().getOrganName());
        }
        // 申請日期
        Date applayDate = DateUtil.getCurrentTimestamp();
        kgoCaseMain.setApplyDate(applayDate);
        viewForm.setApplyDate(DateUtil.dateToString(applayDate, DateUtil.PATTEN_FULL_TIME_SLASH));
        // 限辦日期（申請日期＋案件設定的限辦期限(KGO_CASESET.
        // LimitedPeriod)的日期，需排除六日及例假日，例假日可參考假日檔(KGO_HOLIDAY)）
        Date deadlineDate = commonServiceHelper.getDeadlineDate(applayDate, kgoCaseset.getLimitedPeriod());
        kgoCaseMain.setDeadlineDate(deadlineDate);
        kgoCaseMain.setIsLate(false);

        // 案件種類ID
        kgoCaseMain.setCaseSetId(kgoCaseset.getCaseSetId());
        viewForm.setCaseSetId(kgoCaseset.getCaseSetId());
        viewForm.setCaseSetName(kgoCaseset.getCaseSetName());

        /** 動態流程 狀態回壓處理中(P) 、caseOffer */
        // 案件狀態 動態流程傳 flowId
        kgoCaseMain.setStatus(this.getStates(kgoCaseset.getCaseFlowType(), kgoCaseset.getAcceptSet(), kgoCaseset.getFlowId()));

        List<TpiFlowTask> flowTasks = tpiFlowTaskRepository.findByFlowIdOrderByTaskOrder(kgoCaseset.getFlowId());
        if (CollectionUtils.isNotEmpty(flowTasks)) {
            TpiFlowTask fFlowTask = flowTasks.get(0);
            String caseOfficer = fFlowTask.getTasAssignees();
            kgoCaseMain.setCaseOfficer(caseOfficer);
        }
        /** 動態流程 狀態回壓處理中(P) 、caseOffer */

        // IP
        kgoCaseMain.setIp(KgoUtil.getClientIp());
        // ＭyData TxId
        kgoCaseMain.setMyDataTxId(rq.getMyDataTxId());
        // 新增人員
        String loginUserId = getLoginUserId(applyUser);
        kgoCaseMain.setCreateUser(loginUserId);
        // 新增日期
        kgoCaseMain.setCreateTime(DateUtil.getCurrentTimestamp());
        // 修改人員
        kgoCaseMain.setUpdateUser(loginUserId);
        // 修改日期
        kgoCaseMain.setUpdateTime(DateUtil.getCurrentTimestamp());

        try {
            BackendLoginUserInfo backendLoginUserInfo = KgoUtil.getBackendLoginUser();
            if (ObjectUtils.isNotEmpty(backendLoginUserInfo)) {
                if (!StringUtils.isEmpty(backendLoginUserInfo.getUserId())) {
                    String userAccount = backendLoginUserInfo.getUserId();
                    kgoCaseMain.setAccount(userAccount);
                }
                /** GEO 20211102 add 申請人登入方式 */
                LoginAuthTokenType type = backendLoginUserInfo.getLoginAuthTokenType();
                kgoCaseMain.setApplyUserLoginType(type == null ? StringUtils.EMPTY : type.getType());
            }
        } catch (Exception ex) {
            LOGGER.error("case form User not logged in...", ex);
        }

        this.kgoCaseMainRepository.save(kgoCaseMain);

        return kgoCaseMain;
    } //saveKgoCaseMain

    /**
     * GEO 20211120
     *
     * @param kgoCaseMain
     * @param kgoCaseset
     * @param rq
     * @throws ParseException
     */
    private void saveKgoCaseDetail(KgoCaseMain kgoCaseMain, KgoCaseset kgoCaseset, SaveActionRq rq) throws ParseException {

        for (SaveActionColumnViewForm columnViewForm : rq.getColumnData()) {
            KgoCaseDetail kgoCaseDetail = new KgoCaseDetail();

            KgoCaseDetailPK id = new KgoCaseDetailPK();
            id.setCaseId(kgoCaseMain.getCaseId());
            id.setColumnId(columnViewForm.getColumnId());
            id.setCColumnId("");
            id.setVersion(rq.getVersion());

            kgoCaseDetail.setId(id);
            kgoCaseDetail.setColumnValue(columnViewForm.getValue());
            kgoCaseDetail.setIsSource(getIsSource(columnViewForm.getColumnType()));

            kgoCaseDetailRepository.save(kgoCaseDetail);

            saveKgoCaseDetail(kgoCaseMain.getCaseId(), rq.getVersion(), kgoCaseset, columnViewForm.getColumnId(), columnViewForm.getComplex());

            if (ObjectUtils.isNotEmpty(columnViewForm.getCasePdf()) && StringUtils.isNoneBlank(columnViewForm.getCasePdf().getFileBase64Str())) {
                columnViewForm.getCasePdf().setFileName(String.format("%s_%s.pdf", kgoCaseMain.getCaseId(), kgoCaseMain.getApplyUser()));
                columnViewForm.getFiles().add(columnViewForm.getCasePdf());
            }
            saveUploadFile(kgoCaseMain.getCaseId(), kgoCaseset, columnViewForm.getFiles());
        }
    } //saveKgoCaseDetail

    /**
     * GEO 2021112-
     *
     * @param caseId
     * @param version
     * @param kgoCaseset
     * @param columnId
     * @param complexViewForm
     * @throws ParseException
     */
    private void saveKgoCaseDetail(String caseId, Integer version, KgoCaseset kgoCaseset, String columnId, List<List<SaveActionCColumnViewForm>> complexViewForm) throws ParseException {

        if (ObjectUtils.isNotEmpty(complexViewForm)) {
            if (complexViewForm.size() > 0) {
                for (List<SaveActionCColumnViewForm> complexList : complexViewForm) {
                    for (SaveActionCColumnViewForm complex : complexList) {
                        KgoCaseDetail kgoCaseDetail = new KgoCaseDetail();

                        KgoCaseDetailPK id = new KgoCaseDetailPK();
                        id.setCaseId(caseId);
                        id.setColumnId(columnId);
                        id.setCColumnId(complex.getColumnId());
                        id.setVersion(version);

                        kgoCaseDetail.setId(id);
                        kgoCaseDetail.setColumnValue(complex.getValue());
                        kgoCaseDetail.setIsSource(getIsSource(complex.getColumnType()));

                        kgoCaseDetailRepository.save(kgoCaseDetail);
                    }
                }
            }
        }
    } //saveKgoCaseDetail

    /**
     * GEO 20211202 add
     * 重複檢核檢查
     */
    public CheckActionRs checkAction(SaveActionRq rq) {
        CheckActionRs rs = new CheckActionRs();
        CheckActionViewForm viewForm = new CheckActionViewForm();
        rs.setData(viewForm);
        String msg = CheckFrequencyMsg.PASS.getMsg();
        try {
            if (StringUtils.isBlank(rq.getCaseSetId()) || ObjectUtils.isEmpty(rq.getVersion())) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }
            List<CaseMainCheckFrequencyDto> columnIdList = kgoCaseMainRepository.findCheckColumn(rq.getCaseSetId(), Integer.parseInt(IsCHeckFrequencyEnum.YES.getValue()), rq.getVersion());
            boolean checkFrequency = checkFrequency(rq, columnIdList);
            if (!checkFrequency) msg = CheckFrequencyMsg.NOT_PASS.getMsg();
            viewForm.setMsg(msg);
            viewForm.setCanApply(checkFrequency);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("checkAction error " + e.getMessage(), e);
        } //try
        return rs;
    } //checkAction

    /**
     * GEO 20211019 add
     * 重複檢核檢查
     */
    private boolean checkFrequency(SaveActionRq rq, List<CaseMainCheckFrequencyDto> columnIdList) throws Exception {
        if (columnIdList == null || columnIdList.size() <= 0) return true;

        List<String> preCaseSetIdList = null; //比對後資料相同的case list
        Timestamp now = DateUtil.getCurrentTimestamp();
        String type = columnIdList.get(0).getCheckFrequencyPeriod();
        CheckFrequencyEnum period = CheckFrequencyEnum.getCheckFrequencyEnum(type == null ? "" : type);
        Timestamp checkTime = getCheckFrequencyPeriodTime(period);
        for (CaseMainCheckFrequencyDto dto : columnIdList) {
            for (SaveActionColumnViewForm f : rq.getColumnData()) {
                if (f.getComplex() != null && f.getComplex().size() > 0) {
                    if (f.getColumnId().equals(dto.getColumnId())) {
                        for (List<SaveActionCColumnViewForm> columnList : f.getComplex()) {
                            if (columnList != null && columnList.size() > 0) {
                                for (SaveActionCColumnViewForm cc : columnList) {
                                    if (cc.getColumnId().equals(dto.getCColumnId())) {
                                        dto.setValue(cc.getValue());
                                        break;
                                    }
                                }
                            } //if (columnList != null
                        } // for (List<SaveActionCColumnViewForm>
                    } // if (f.getColumnId()
                } else if (f.getColumnId().equals(dto.getColumnId())) {
                    dto.setValue(f.getValue());
                } // if (f.getComplex() != null
            } // for (SaveActionColumnViewForm f
        } //for (int i = 0; i < columnIdList.size(); i++)

        for (int i = 0; i < columnIdList.size(); i++) {
            LOGGER.info("checkFrequency formList.get(i).getColumnId(): " + i + ". -->"
                    + columnIdList.get(i).getColumnId() + " CColumnId: "
                    + columnIdList.get(i).getCColumnId() + " Value : " + columnIdList.get(i).getValue());
            if (preCaseSetIdList == null) { //首次迴圈
                preCaseSetIdList = new ArrayList<>();
                List<String> tempPreCaseSetIdList = kgoCaseMainRepository.findCaseIdCheckFrequencyColumn(
                        columnIdList.get(i).getColumnId(), columnIdList.get(i).getValue(), rq.getVersion(), null, now, checkTime, columnIdList.get(i).getCColumnId());
                if (tempPreCaseSetIdList != null && tempPreCaseSetIdList.size() > 0)
                    preCaseSetIdList = tempPreCaseSetIdList;
            } else if (preCaseSetIdList.size() > 0) { //首次迴圈後，若有同樣的配對
                List<String> tempList = new ArrayList<>();
                for (String id : preCaseSetIdList) {
                    List<String> sList = kgoCaseMainRepository.findCaseIdCheckFrequencyColumn(
                            columnIdList.get(i).getColumnId(), columnIdList.get(i).getValue(), rq.getVersion(), id, now, checkTime, columnIdList.get(i).getCColumnId());
                    if (sList != null && sList.size() > 0) tempList.add(id);
                }
                preCaseSetIdList = tempList;
            } // if(i==0)
            LOGGER.info("checkFrequency preCaseSetIdList: " + i + ". -->" + preCaseSetIdList.size());
        } //for (int i = 0;i <formList.size(); i++)
        return preCaseSetIdList.size() <= 0;
    } //checkFrequency

    /**
     * GEO 20211019 add
     */
    private Timestamp getCheckFrequencyPeriodTime(CheckFrequencyEnum period) throws ParseException {
        Timestamp checkTime;
        Timestamp now = DateUtil.getCurrentTimestamp();
        String nowStr = DateUtil.timestampToString(now, DateUtil.PATTEN_YEAR_MONTH_DAY);
        now = DateUtil.strToTimestamp(nowStr, DateUtil.PATTEN_YEAR_MONTH_DAY);
        switch (period) {
            case YEAR:
                checkTime = DateUtil.modifyDate(now, -1, 0, 0);
                break;
            case MONTH:
                checkTime = DateUtil.modifyDate(now, 0, -1, 0);
                break;
            case WEEK:
                checkTime = DateUtil.modifyDate(now, 0, 0, -7);
                break;
            default:
                checkTime = null;
        }
        return checkTime;
    }// getCheckFrequencyPeriodTime

    /**
     * GEO 20211120
     * 儲存實體附件
     *
     * @param caseId
     * @param kgoCaseset
     * @param files
     */
    private void saveUploadFile(String caseId, KgoCaseset kgoCaseset, List<SaveFileViewForm> files) {
        if (files == null) {
            return;
        }

        File fileFolder = new File(KgoUtil.getCaseDownloadUploadBasePath(caseId));
        for (SaveFileViewForm form : files) {
            saveFile(fileFolder, form.getFileBase64Str(), form.getFileName());
        }
    } //saveUploadFile

    /**
     * GEO 20211120
     *
     * @param fileFolder
     * @param fileBase64Str
     * @param fileName
     */
    private void saveFile(File fileFolder, String fileBase64Str, String fileName) {
        try {
            if (StringUtils.isNoneBlank(fileBase64Str)) {
                String base64Str = fileBase64Str;

                String[] dataString = fileBase64Str.split(",");
                if (dataString.length > 1) {
                    base64Str = dataString[1];
                }
                byte[] decoder = Base64.getDecoder().decode(base64Str);
                FileUtil.createFile(fileFolder, fileName, decoder);
            }
        } catch (Exception e) {
            LOGGER.error("\n >>>>>>>saveFile>>> " + e.getMessage(), e);
        }
    } //saveFile

    /**
     * GEO 20211120
     * 寄信給一般民眾.
     *
     * @param caseId     the case id
     * @param kgoCaseset the kgo caseset
     * @param applyDate  the apply date
     * @throws Exception the exception
     */
    public void sendEmailApplyCase(String caseId, KgoCaseset kgoCaseset, String applyEmail, String applyDate) throws
            Exception {
        try {
            if (StringUtils.isNoneBlank(applyEmail)) {
                Map<String, Object> model = new HashMap<String, Object>();

                model.put("casesetName", kgoCaseset.getCaseSetName());
                model.put("caseId", caseId);
                model.put("applyDate", applyDate);

                String frontendCaseSearchLink = SpringUtil.getProperty("fontend.case.search.link");
                model.put("caseUrl", String.format(frontendCaseSearchLink, caseId));
//				String templateString = FreeMarkerTemplateUtils
//						.processTemplateIntoString(freemarkerConfig.getTemplate("applyCase.html"), model);

//				mailUtil.sendMail(new String[] { emsil }, null, "便民一路通訊息通知", templateString);

                // 11.18 modify: 改用util 寄送mail
                MailUtil mailUtil = SpringUtil.getBean(MailUtil.class);
                MessageUtil messageUtil = SpringUtil.getBean(MessageUtil.class);
                mailUtil.sendTemplateMail(new String[]{applyEmail}, null, messageUtil.getMessage("kgo.backend.case.handle.review.notification.msgTitle"), model, "applyCase.html");
            }

        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error(String.format("sendEmailApplyCase caseId: %s", caseId), e);
        }
    } //sendEmailApplyCase


    /**
     * GEO20211120
     * 寄信給管理者.
     *
     * @param caseId the case id
     * @throws Exception the exception
     */
    private void sendEmailSignDispatch(String caseId, String casesetName, String emails) throws Exception {
        String[] sendTo = new String[]{};

        if (StringUtils.isNoneBlank(emails)) {
            // 多筆
            if (StringUtils.contains(emails, ",")) {
                sendTo = StringUtils.split(emails, ",");
                // 單筆
            } else {
                sendTo = new String[]{emails};
            }

            Map<String, Object> model = new HashMap<>();
            model.put("caseId", caseId);
            model.put("backendUrl", backendUrl);
            model.put("casesetName", casesetName);

            MailUtil mailUtil = SpringUtil.getBean(MailUtil.class);
            MessageUtil messageUtil = SpringUtil.getBean(MessageUtil.class);
            mailUtil.sendTemplateMail(sendTo, null, messageUtil.getMessage("kgo.backend.case.handle.pending.sign.dispatch.title"), model, "pendingSignDispatch.html");
        }
    } //sendEmailSignDispatch

    /**
     * GEO20211120
     *
     * @param kgoCaseMain
     * @param kgoCaseset
     * @param caseSetName
     * @param applyDate
     */
    private void pushNotificationApplyCase(KgoCaseMain kgoCaseMain, KgoCaseset kgoCaseset, String caseSetName, String applyDate) {
        try {
            pushService.pushMessage(kgoCaseMain.getApplyUser(), PushCaseStatusEnum.W.getValue(), kgoCaseMain.getCaseId(), caseSetName, applyDate);
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error(String.format("pushNotificationApplyCase caseId: %s", kgoCaseMain.getCaseId()), e);
        }
    } //pushNotificationApplyCase

    /**
     * GEO20211120
     *
     * @param kgoCaseMain
     * @param kgoCaseset
     * @throws Exception
     */
    private void callExternalApiApplyCase(KgoCaseMain kgoCaseMain, KgoCaseset kgoCaseset) throws Exception {
        String request = "";
        String response = "";
        String caseId = kgoCaseMain.getCaseId();
        try {
            CaseFlowTypeEnum caseFlowType = CaseFlowTypeEnum.getCaseFlowTypeEnum(kgoCaseset.getCaseFlowType());

            switch (caseFlowType) {
                case B1:
                case B2:

                    // TODO : 案件流程變動需確認
                    Map<String, String> paramsMap = new HashMap<String, String>();
                    paramsMap.put("caseId", caseId);
                    request = JsonUtil.toJSONString(paramsMap);
                    response = this.callKcgCityApiServiceHelper.getCityApiDatanNoMoicaLogin(kgoCaseset.getLinkUrl(), paramsMap);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

            saveKgoApiLog(caseId, kgoCaseMain.getCaseOrgan(), kgoCaseset, request, response);
            LOGGER.error(String.format("callExternalApiApplyCase caseId: %s", caseId), e);
        }
    } //callExternalApiApplyCase

    /**
     * GEO20211120
     *
     * @param caseId
     * @param caseOrganId
     * @param kgoCaseset
     * @param request
     * @param response
     */
    private void saveKgoApiLog(String caseId, String caseOrganId, KgoCaseset kgoCaseset, String request, String response) {
        try {
            KgoApiLog item = new KgoApiLog();
            item.setCaseId(caseId);
            item.setOrganId(caseOrganId);
            item.setReCount(0);
            item.setSuccess(false);
            item.setUrl(kgoCaseset.getLinkUrl());
            item.setRequest(request);
            item.setResponse(response);
            item.setCreateTime(DateUtil.getCurrentTimestamp());
            item.setUpdateTime(DateUtil.getCurrentTimestamp());

            kgoApiLogRepository.save(item);
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error(String.format("saveKgoApiLog caseId: %s", caseId), e);
        }
    } //saveKgoApiLog

    private String getColumnViewForm(List<SaveActionColumnViewForm> columns, String columnId) {
        return getColumnViewForm(columns, columnId, false);
    }

    /**
     * GEO 20211120
     *
     * @param columns
     * @param columnId
     * @param isThrowEx
     * @return
     */
    private String getColumnViewForm(List<SaveActionColumnViewForm> columns, String columnId, Boolean isThrowEx) {
        Optional<SaveActionColumnViewForm> viewForm = columns.stream().filter(x -> x.getColumnId().equalsIgnoreCase(columnId)).findFirst();

        if (viewForm.isPresent()) {
            if (viewForm.get().getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
                List<SaveActionCColumnViewForm> cColumns = viewForm.get().getComplex().get(0);
                Optional<SaveActionCColumnViewForm> cColumn = cColumns.stream().findFirst();
                if (cColumn.isPresent()) {
                    return cColumn.get().getValue();
                } else {
                    if (isThrowEx) {
                        throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
                    } else {
                        return null;
                    }
                } // if (cColumn.isPresent())
            } else {
                return viewForm.get().getValue();
            } //if (viewForm.get().getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue()))
        } else {
            if (isThrowEx) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            } else {
                return null;
            }
        } // if (viewForm.isPresent())
    } //getColumnViewForm

    /**
     * GEO 20211120
     *
     * @param defValue
     * @return
     */
    private String getLoginUserId(String defValue) {
        BackendLoginUserInfo loginUserInfo = this.getLoginUserInfo();
        if (ObjectUtils.isEmpty(loginUserInfo))
            return "";
        return StringUtils.isBlank(loginUserInfo.getUserId()) ? defValue : loginUserInfo.getUserId();
    } //getLoginUserId

    private String getStates(String caseFlowType, String acceptSet, String flowId) {
        CaseFlowTypeEnum caseFlowTypeEnum = CaseFlowTypeEnum.getCaseFlowTypeEnum(caseFlowType);
        /** ==== 2020.12.13 動態流程 有flowId 狀態回傳 P3 處理中 === */
        if (StringUtils.isNotBlank(flowId)) {
            if (StringUtils.equals(caseFlowType, CaseFlowTypeEnum.B3.getValue()))
                return CaseMainStatusEnum.P3.getValue();
//			if (StringUtils.equals(caseFlowType, CaseFlowTypeEnum.B1.getValue()) || StringUtils.equals(caseFlowType, CaseFlowTypeEnum.B2.getValue()))
//				return CaseMainStatusEnum.P.getValue();
        }

        switch (caseFlowTypeEnum) {

            case B1:
            case B2:

                return "W";
            case B3:
                AcceptSetEnum acceptSetEnum = AcceptSetEnum.getEnum(acceptSet);
                switch (acceptSetEnum) {
                    case OFFICER:
                        return "W3";
                    case UNIT:
                    case AREA:
                        return "A3";
                }
            default:
                return "W";
        }
    } //getStates

    /**
     * GEO 20211120
     *
     * @param columnType
     * @return
     */
    private Boolean getIsSource(String columnType) {
        ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(columnType);
        switch (columnTypeEnum) {
            case ADDRESS:
            case DRP:
            case RADIO:
            case LAND_NUM:
            case CHECKBOX:
            case S_CHECKBOX:
                return true;
            default:
                return false;
        }
    } //getIsSource

    /**
     * GEO 20211120
     *
     * @param list
     * @return
     */
    private Map<String, Map<String, String>> getCasesetColumnChildVGroupMap(List<KgoCasesetColumnChild> list) {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        list.stream().map(column -> column.getId().getColumnId()).distinct().forEach(columnId -> {
            Map<String, String> cMap = list.stream().filter(x -> StringUtils.equalsIgnoreCase(columnId, x.getId().getColumnId())).filter(x -> StringUtils.isNotBlank(x.getVGroup()) && x.getIsMustKey())
                    .map(x -> x.getVGroup()).distinct().collect(Collectors.toMap(x -> x, c -> ""));
            map.put(columnId, cMap);
        });
        return map;
    } //getCasesetColumnChildVGroupMap

    /**
     * GEO 20211120
     *
     * @param list
     * @return
     */
    private Map<String, Map<String, String>> getCasesetColumnChildPColumnIdMap(List<KgoCasesetColumnChild> list) {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

        list.stream().map(column -> column.getId().getColumnId()).distinct().forEach(columnId -> {
            Map<String, String> cMap = list.stream().filter(x -> StringUtils.equalsIgnoreCase(columnId, x.getId().getColumnId())).filter(x -> StringUtils.isNotBlank(x.getPColumnId()))
                    .map(x -> x.getPColumnId()).distinct().collect(Collectors.toMap(x -> x, c -> ""));
            map.put(columnId, cMap);
        });

        return map;
    } //getCasesetColumnChildPColumnIdMap

    /**
     * GEO 20211120
     *
     * @param list
     * @return
     */
    private Map<KgoCasesetColumnChildPK, KgoCasesetColumnChild> getCasesetColumnChildMap(List<KgoCasesetColumnChild> list) {
        return list.stream().collect(Collectors.toMap(x -> x.getId(), c -> c));
    } //getCasesetColumnChildMap

    /**
     * GEO 20211120
     *
     * @param caseSetId
     * @param version
     * @return
     */
    private List<KgoCasesetColumnChild> getCasesetColumnChild(String caseSetId, Integer version) {
        return kgoCasesetColumnChildRepository.findByIdCaseSetIdAndIdVersion(caseSetId, version);
    } //getCasesetColumnChild

    /**
     * GEO 20211120
     *
     * @param txId
     */
    private void removeTxIdSession(String txId) {

        try {
            if (StringUtils.isNoneBlank(txId)) {
                this.removeSession(getResourceMydataColumnsKey(txId));
                this.removeSession(getResourceCasesetMergeMyDataColumnsKey(txId));
                this.removeSession(getResourceTxIdCasesetMergeMyDataColumnsValKey(txId));
            }
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error(String.format("removeTxIdSession TxId: %s", txId), e);
        }

    } //removeTxIdSession

    /**
     * GEO 20211120
     *
     * @param txId
     * @return
     */
    private String getResourceMydataColumnsKey(String txId) {
        return String.format("resourceMydataColumns_%s", txId);
    } //getResourceMydataColumnsKey

    /**
     * GEO 20211120
     *
     * @param txId
     * @return
     */
    private Map<KgoMydataColumnPK, KgoMydataColumn> getResourceMydataColumns(String txId) {
        LOGGER.info("getResourceMydataColumns txId="+txId);
        String sessionName = getResourceMydataColumnsKey(txId);
        Map<KgoMydataColumnPK, KgoMydataColumn> data = this.loadFromSession(sessionName, HashMap.class);
        if (data == null) {
            data = new HashMap<KgoMydataColumnPK, KgoMydataColumn>();
            this.saveToSession(sessionName, data);
        }
        return data;
    } //getResourceMydataColumns

    /**
     * GEO 20211120
     * CaseSet Id MyData 合併欄位 sessionName
     *
     * @param txId
     * @return
     */
    private String getResourceCasesetMergeMyDataColumnsKey(String txId) {
        return String.format("resourceCasesetMergeMyDataColumns_%s", txId);
    }

    /**
     * GEO 20211120
     * session 取出 CaseSet Id MyData 合併欄位
     *
     * @param txId
     * @return
     */
    private Map<String, List<ColumnModel>> getResourceCasesetMergeMyDataColumns(String txId) {
        String sessionName = getResourceCasesetMergeMyDataColumnsKey(txId);
        Map<String, List<ColumnModel>> data = this.loadFromSession(sessionName, HashMap.class);
        if (data == null) {
            data = new HashMap<String, List<ColumnModel>>();
            this.saveToSession(sessionName, data);
        }

        return data;
    } //getResourceCasesetMergeMyDataColumns

    /**
     * GEO 20211120
     * 取出 MyData 合併欄位 By myDataId (測試用)
     *
     * @param myDataId
     * @return
     */
    private List<ColumnModel> getResourceMergeMyDataColumns(String myDataId) {
        List<KgoMydataColumn> myDataColumns = kgoMydataColumnRepository.findByIdMyDataId(myDataId);

        List<ColumnModel> columnNames = myDataColumns.stream()
                .map(x -> new ColumnModel(x.getId().getMyDataColumn(), x.getId().getMyDataColumn(), x.getType(), x.getMyDataType(), x.getFileName(), x.getHeaderRow(), x.getDataStartRow()))
                .collect(Collectors.toList());
        return columnNames;
    } //getResourceMergeMyDataColumns

    /**
     * GEO 20211120
     * CaseSet Id MyData 合併欄位值 sessionName
     *
     * @param txId
     * @return Map<MydataId, Map < String, Object>>
     */
    private String getResourceTxIdCasesetMergeMyDataColumnsValKey(String txId) {
        return String.format("resourceTxIdCasesetMergeMyDataColumnsVal_%s", txId);
    } //getResourceTxIdCasesetMergeMyDataColumnsValKey

    /**
     * GEO 20211120
     * session 取出 CaseSet Id MyData 合併欄位值
     *
     * @param txId
     * @return
     */
    private Map<String, Map<String, Object>> getResourceTxIdCasesetMergeMyDataColumnsVal(String txId) {
        String sessionName = getResourceTxIdCasesetMergeMyDataColumnsValKey(txId);
        Map<String, Map<String, Object>> data = this.loadFromSession(sessionName, HashMap.class);
        if (data == null) {
            data = new HashMap<String, Map<String, Object>>();
            this.saveToSession(sessionName, data);
        }
        return data;
    } //getResourceTxIdCasesetMergeMyDataColumnsVal

    /**
     * GEO 20211120
     *
     * @param txId
     * @return
     */
    private String getMyDataTxIdRequestKey(String txId) {
        return String.format("MyDataTxIdRequest_%s", txId);
    }

    private Map<String, Object> getMyDataTxIdRequestVal(String txId) {
        String sessionName = getMyDataTxIdRequestKey(txId);
        Map<String, Object> data = this.loadFromSession(sessionName, HashMap.class);

        return data;
    } //getMyDataTxIdRequestKey

    private void setMyDataTxIdRequestVal(String txId, Map<String, Object> data) {
        String sessionName = getMyDataTxIdRequestKey(txId);
        this.saveToSession(sessionName, data);
    } //setMyDataTxIdRequestVal

}