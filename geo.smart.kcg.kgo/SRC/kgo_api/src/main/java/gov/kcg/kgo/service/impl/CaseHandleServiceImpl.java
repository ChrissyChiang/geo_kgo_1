package gov.kcg.kgo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.*;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.*;
import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.CaseHandleApiError;
import gov.kcg.kgo.enums.error.CaseViewUpdateStatusApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geoenum.*;
import gov.kcg.kgo.geomodel.CaseHandleSiteCaseViewQueryRq;
import gov.kcg.kgo.geomodel.CaseHandleSiteRentableExcelRq;
import gov.kcg.kgo.geomodel.GeoCaseColumnModel;
import gov.kcg.kgo.geomodel.GeoCaseViewFieldCheckModel;
import gov.kcg.kgo.geomodel.dto.SiteCaseMainQueryViewListDto;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.*;
import gov.kcg.kgo.geoservice.GeoAgentService;
import gov.kcg.kgo.geoservice.GeoCaseSetRentService;
import gov.kcg.kgo.geoservice.GeoKcgPaymentService;
import gov.kcg.kgo.geoservice.GeoTaskCommentService;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseHandlePaymentRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoRentableComboBoxRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseChangePaymentRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseChangePaymentForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.RentableQueryComboBoxViewForm;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.repository.custom.impl.KgoCaseDetailRepositoryCustomImpl;
import gov.kcg.kgo.service.*;
import gov.kcg.kgo.service.bean.csv.GeoCaseHandleCsvVo;
import gov.kcg.kgo.service.bean.excel.GeoCaseHandleExcelVo;
import gov.kcg.kgo.service.bean.excel.GeoCaseHandleJsonVo;
import gov.kcg.kgo.service.bean.excel.GeoCaseHandleSiteExcelVo;
import gov.kcg.kgo.service.bean.excel.GeoCaseRentalCaseExcelVo;
import gov.kcg.kgo.service.impl.helper.*;
import gov.kcg.kgo.service.impl.transaction.ProcessTransaction;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.backend.caseHadle.addNote.rq.CaseHandleAddNoteRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rq.CaseHandleCaseUpdateQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rs.CaseHandleCaseUpdateQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rs.bean.CaseHandleCaseUpdateQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rq.CaseHandleCaseUpdateUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rq.bean.CaseHandleCaseUpdateUpdateColumn;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rs.CaseHandleCaseUpdateUpdateRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.home.rs.CaseHandleCaseViewHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.home.rs.bean.CaseHandleCaseViewHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq.CaseByOrganQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq.CaseHandleCaseViewQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.CaseHandleCaseViewQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.PregnantOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.QueryCaseByOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean.CaseHandleCaseViewQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean.PregnantOrganQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean.QueryCaseByOrganViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rq.CaseHandleCaseViewQueryOrganFormRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rs.CaseHandleCaseViewQueryOrganFormRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rs.bean.CaseHandleCaseViewQueryOrganFormViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.download.rq.CaseHandleCaseViewSaCaseDownloadRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rq.CaseHandleCaseViewSaCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.CaseHandleCaseViewSaCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.*;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.updateStatus.rq.CaseHandleCaseViewSaCaseUpdateStatusRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.updateStatus.rs.CaseHandleCaseViewScaCaseUpdateStatueRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rq.CaseHandleCaseViewScaCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rs.CaseHandleCaseViewScaCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rs.bean.CaseHandleCaseViewScaCaseHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rq.CaseHandleCaseViewUraCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rs.CaseHandleCaseViewUraCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rs.bean.CaseHandleCaseViewUraCaseHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rq.CaseHandleCrossReviewCommentEditRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rs.CaseHandleCrossReviewCommentEditRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rs.DiscountCountRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rq.CaseHandleCrossReviewCommentQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.CaseHandleCrossReviewCommentQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.bean.CaseHandleCrossReviewCommentViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.bean.DiscountCountViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.delNote.rq.CaseHandleDelNoteRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rq.CaseHandlePendingReviewCancelAcceptRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rs.CaseHandlePendingReviewCancelAcceptRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rq.CaseHandlePendingReviewCancelAcceptHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rs.CaseHandlePendingReviewCancelAcceptHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rs.bean.CaseHandlePendingReviewCancelAcceptHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.CaseHandlePendingReviewCorrectHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.CaseHandlePendingReviewCorrectUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.bean.CaseHandlePendingReviewCorrectUpdateDataGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rs.CaseHandlePendingReviewCorrectHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rs.CaseHandlePendingReviewCorrectUpdateRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq.CaseHandlePendingReviewDeleteFileRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq.CaseHandlePendingReviewDownloadFileRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq.CaseHandlePendingReviewUploadFileRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rs.CaseHandlePendingReviewDeleteFileRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rs.CaseHandlePendingReviewUploadFileRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rs.bean.CaseHandlePendingReviewUploadFileForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.CaseHandlePendingReviewHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean.CaseHandlePendingReviewCorrectHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean.CaseHandlePendingReviewCorrectHomeViewRecordForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean.CaseHandlePendingReviewHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean.CaseHandleSignSaCaseHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.home.rq.CaseHandlePendingReviewNotifyHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.home.rs.CaseHandlePendingReviewNotifyHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rq.CaseHandlePendingReviewNotifyHistoryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rq.CaseHandlePendingReviewNotifyRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.CaseHandlePendingReviewNotifyHistoryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.CaseHandlePendingReviewNotifyRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.bean.CaseHandlePendingReviewNotifyHistoryDataGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.bean.CaseHandlePendingReviewNotifyHistoryForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.query.rq.CaseHandlePendingReviewQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.query.rs.CaseHandlePendingReviewQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.query.rs.bean.CaseHandlePendingReviewQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.accept.rq.CaseHandlePendingReviewAcceptRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.accept.rs.CaseHandlePendingSignAcceptRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.batchAaccept.rq.CaseHandlePendingReviewBatchAcceptRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.batchAaccept.rq.bean.CaseHandlePendingReviewBatchAcceptForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rq.CaseHandlePendingSignCompleteRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rq.DiscountCountRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rs.CaseHandlePendingSignCompleteRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rs.bean.CaseHandlePendingSignCompleteHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rq.CaseHandlePendingReviewBatchSignForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rq.CaseHandlePendingSignDispatchRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rs.CaseHandlePendingSignDispatchRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.doDynamicFlow.rq.CaseHandlePendingSignDoDynamicFlowRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.home.rs.CaseHandlePendingSignHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.home.rs.bean.CaseHandlePendingSignHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.query.rq.CaseHandlePendingSignQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.query.rs.CaseHandlePendingSignQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.query.rs.bean.CaseHandlePendingSignQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.review.rq.CaseHandlePendingSignReviewRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.review.rs.CaseHandlePendingSignReviewRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.review.rs.bean.CaseHandlePendingSignReviewViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.home.rs.CaseHandleReviewedHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.home.rs.bean.CaseHandleReviewedHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rq.CaseHandleReviewedQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rs.CaseHandleReviewedQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rs.bean.CaseHandleReviewedQueryViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.signSaCase.home.rq.CaseHandleSignSaCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.signSaCase.home.rs.CaseHandleSignSaCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetOrganComplexColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetHomeOrganDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetOrganGroupColumnDataGrid;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionCColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveFileViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.OptionViewForm;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gov.kcg.kgo.enums.backend.CaseMainStatusEnum.WO;
import static gov.kcg.kgo.enums.common.ColumnTypeEnum.FIL;
import static gov.kcg.kgo.geoenum.CaseSetCategoryEnum.ORGAN;
import static gov.kcg.kgo.util.DateUtil.PATTEN_YEAR_MONTH_YEAR_SLASH;
import static java.util.stream.Collectors.toList;

@Transactional(rollbackFor = Exception.class)
@Service("CaseHandleService")
public class CaseHandleServiceImpl extends KgoBackEndServiceImpl implements CaseHandleService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CaseHandleServiceImpl.class);

    private CaseHandleServiceHelper caseHandleServiceHelper = CaseHandleServiceHelper.getInstance();

    private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

    @Autowired
    private KgoUserRoleRepository kgoUserRoleRepository;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private KgoCaseDetailRepository kgoCaseDetailRepository;

    @Autowired
    private KgoCaseFwdRepository kgoCaseFwdRepository;

    @Autowired
    private KgoUraApplyRepository kgoUraApplyRepository;

    @Autowired
    private KgoCasesetColumnRepository kgoCasesetColumnRepository;

    @Autowired
    private KgoScaApplyRepository kgoScaApplyRepository;

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private PushService pushService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private KgoOrganRepository kgoOrganRepository;

    @Autowired
    private KgoUserRepository kgoUserRepository;

    @Autowired
    private CityCoinAPIService cityCoinAPIService;

    @Autowired
    private ExcelTempExportService excelTempExportService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    GeoKgoCasesetOrganGroupReposCustom geoKgoCasesetOrganGroupReposCustom;

    @Autowired
    private GeoKgoCasesetColumnOrganRepository geoKgoCasesetColumnOrganRepository;

    @Autowired
    private GeoKgoCasesetColumnChildOrganRepository geoKgoCasesetColumnChildOrganRepository;

    @Autowired
    GeoKgoCasesetDetailOrganReposCustom geoKgoCasesetDetailOrganReposCustom;

    @Autowired
    GeoKgoCaseDetailOrganRepository geoKgoCaseDetailOrganRepository;

    @Autowired
    CaseFormServiceImpl caseFormService;

    @Autowired
    GeoKgoCaseCrossReviewCommentRepository geoKgoCaseCrossReviewCommentRepository;

    @Autowired
    GeoKgoCaseQueryReposCustom geoKgoCaseQueryReposCustom;

    @Autowired
    GeoCaseCrossReviewCommentCustom geoCaseCrossReviewCommentCustom;

    @Autowired
    GeoKgoRentPaymentRepos geoKgoRentPaymentRepos;
    @Autowired
    GeoCaseRentRelationRepos geoCaseRentRelationRepos;
    @Autowired
    GeoCaseSetSiteTimeReposCustom geoCaseSetSiteTimeReposCustom;
    @Autowired
    GeoCaseSetRentService geoCaseSetRentService;
    @Autowired
    GeoKgoCasesetRentTimeRepository geoKgoCasesetRentTimeRepository;

    @Autowired
    KgoCasesetRefundRatioRepository refundRatioRepository;
    @Autowired
    GeoKcgPaymentService geoKcgPaymentService;
    @Autowired
    GeoCaseSetRentReposCustom geoCaseSetRentReposCustom;
    @Autowired
    GeoCaseSetSiteRepos geoCaseSetSiteRepos;

    @Autowired
    KgoApiLogRepository kgoApiLogRepository;
    @Autowired
    private GeoKgoCasesetAllowOrganCustom geoKgoCasesetAllowOrganCustom;

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper.getInstance();

    private KgoServiceHelper kgoServiceHelper = KgoServiceHelper.getInstance();

    private static final int APPLY_START_DATE_INDEX = 0;

    private static final int APPLY_END_DATE_INDEX = 1;

    @Autowired
    private KgoCorrectRecordRepository kgoCorrectRecordRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessTransaction processTransaction;

    @Autowired
    private KgoHolidayRepository kgoHolidayRepository;

    @Autowired
    private KgoUnitRepository kgoUnitRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private KgoMsgRecordRepository kgoMsgRecordRepository;

    @Autowired
    private KgoCaseMainResendFlowRepository kgoCaseMainResendFlowRepository;

    @Autowired
    private TpiFlowService tpiFlowService;

    @Autowired
    private GeoAgentService geoAgentService;

    @Autowired
    private GeoKgoAgentRepository geoKgoAgentRepository;

    @Autowired
    GeoKgoTaskCommentRepository geoKgoTaskCommentRepository;

    @Autowired
    private GeoTaskCommentService geoTaskCommentService;


    @Autowired
    GeoOrganDiscountRatioRepository geoOrganDiscountRatioRepository;

    @Autowired
    private GeoKgoHcaOrganRepository geoKgoHcaOrganRepository;

    @Value("${fontend.case.search.link.login}")
    private String frontendCaseSearchLinkLogin;

    @Value("${fontend.case.search.link}")
    private String frontendCaseSearchLink;

//	@Value("${case.handle.pending.review.upload.file.path}")
//	private String caseHandlePendingReviewUploadFilePath;

    @Value("${case.handle.pending.review.upload.file.size.limit}")
    private int caseHandlePendingReviewUploadFileSizeLimit;

    @Value("${case.handle.pending.review.upload.file.extension}")
    private List<String> caseHandlePendingReviewUploadFileExtension;

    @Value("${backend.url}")
    private String backendUrl;

    @Autowired
    private GeoCaseSetAssociateReposCustom geoCaseSetAssociateReposCustom;

    /**
     * 後台案件處理-待簽收匣-初始畫面
     *
     * @return
     */
    @Override
    public CaseHandlePendingSignHomeRs caseHandlePendingSignHome() {
        CaseHandlePendingSignHomeViewForm viewForm = new CaseHandlePendingSignHomeViewForm();
        CaseHandlePendingSignHomeRs rs = new CaseHandlePendingSignHomeRs();
        // 從 session 抓取 ogranId, userId
        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        String ogranId = backendLoginUser.getOrgan();
        String loginUserId = backendLoginUser.getUserId();
        try {
            List<Map<String, Object>> processIdMap = activitiService.getPendingCaseProcessIds(ogranId, loginUserId, backendLoginUser,
                    Arrays.asList(ActivitiTaskNameEnum.DG.getLabel(), ActivitiTaskNameEnum.CHG.getLabel()));
            List<String> processIds = processIdMap.stream().map(item -> (String) item.get("processId")).collect(Collectors.toList());
            List<PendingSignCaseQueryDto> queryResult = kgoCaseMainRepository.getPendingSignCase(processIds);
            List<CaseHandleCaseQueryDataGrid> grid = new ArrayList<>();
            for (PendingSignCaseQueryDto item : queryResult) {
                String statusType = item.getStatus();
                if (statusType.equalsIgnoreCase(CaseMainStatusEnum.W3.getValue()) || statusType.equalsIgnoreCase(CaseMainStatusEnum.A3.getValue())) {
                    CaseHandleCaseQueryDataGrid dg = new CaseHandleCaseQueryDataGrid();
                    dg.setApplyDate(DateUtil.dateToString(item.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                    dg.setApplicant(KgoUtil.hideName(item.getApplyUserName()));
                    dg.setCaseId(item.getCaseId());
                    dg.setCaseName(item.getCaseSetName());
                    dg.setLimitDate(DateUtil.dateToString(item.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                    dg.setStatusName(CaseMainStatusEnum.getCaseMainStatusEnum(statusType).getLabel());
                    dg.setStatusType(statusType);
                    dg.setType(item.getType());
                    Map<String, Object> processId = processIdMap.stream().filter(innerItem -> innerItem.get("processId").equals(item.getProcessId())).findAny().orElse(new HashMap<>());
                    Task task = (Task) processId.get("task");
                    if (null != task) {
                        dg.setTaskId(task.getId());
                    }
                    grid.add(dg);
                }
            }
            // sort desc
//			grid.sort(
//					Collections.reverseOrder((entity1, entity2) -> entity1.getUpdateTime().compareTo(entity2.getUpdateTime())));
            viewForm.setGrid(grid);
            rs.setOrganId(ogranId);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandlePendingSignHome error " + e.getMessage(), e);
        }

        return rs;
    }

    @Autowired
    private KgoCasesetUnitRepository kgoCasesetUnitRepository;

    @Autowired
    private KgoCasesetAreaRepository kgoCasesetAreaRepository;

    /**
     * 後台案件處理-待簽收匣-案件查詢
     *
     * @return
     */
    @Override
    public CaseHandlePendingSignQueryRs caseHandlePendingSignQuery(CaseHandlePendingSignQueryRq rq) {
        CaseHandlePendingSignQueryViewForm viewForm = new CaseHandlePendingSignQueryViewForm();
        CaseHandlePendingSignQueryRs rs = new CaseHandlePendingSignQueryRs();

        try {
            // 從 session 抓取 unitId, userId
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String organId = KgoUtil.getBackendLoginUser().getOrgan();
            String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
            String caseId = rq.getCaseId();
            String applyUserName = rq.getApplicant();
            String caseSetName = rq.getCaseName();
            String[] applyDate = rq.getApplyDate();
            String dateStart = StringUtils.EMPTY;
            String dateEnd = StringUtils.EMPTY;

            if (ObjectUtils.isNotEmpty(applyDate)) {
                dateStart = applyDate[0];
                dateEnd = applyDate[1];
            }

            List<CaseHandleCaseQueryDataGrid> gridList = getCaseHandlePendingSignQueryDataGrid(organId, loginUserId,
                    backendLoginUser.getRoles(), applyUserName, dateStart, dateEnd, caseSetName, caseId);

            /** GEO 20211026 add*/
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent g : principalList) {
                    List<CaseHandleCaseQueryDataGrid> principalGridList = getCaseHandlePendingSignQueryDataGrid(organId, g.getPrincipalUserId(),
                            getUserRoleList(g.getPrincipalUserId()), applyUserName, dateStart, dateEnd, caseSetName, caseId);
                    gridList.addAll(principalGridList);
                }
            } //if (principalList != null

            // sort desc
//			gridList.sort(
//					Collections.reverseOrder((entity1, entity2) -> entity1.getUpdateTime().compareTo(entity2.getUpdateTime())));
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandlePendingSignQuery error " + e.getMessage(), e);
        }

        return rs;
    } //caseHandlePendingSignQuery


    /**
     * GEO 20211026 add
     */
    private List<CaseHandleCaseQueryDataGrid> getCaseHandlePendingSignQueryDataGrid(String organId, String userId, List<String> roles,
                                                                                    String applyUserName, String dateStart, String dateEnd, String caseSetName, String caseId) {

        List<Map<String, Object>> processIdMap = activitiService.getPendingCaseProcessIdsSign(organId, userId,
                Arrays.asList(ActivitiTaskNameEnum.DG.getLabel(), ActivitiTaskNameEnum.CHG.getLabel()), roles);
        List<String> processIds = processIdMap.stream().map(item -> (String) item.get("processId")).collect(Collectors.toList());
        List<CaseMainQueryDto> caseDtoList = new LinkedList<>();
        if (!CollectionUtils.isEmpty(processIds)) {
            List<CaseMainQueryDto> caseMainQueryDtos = kgoCaseMainRepository.pendingSignSAQuery(processIds, applyUserName, dateStart, dateEnd, caseSetName, caseId);
            caseDtoList.addAll(caseMainQueryDtos);
        }
        return getRsGridData(caseDtoList, processIdMap);
    } //getCaseHandlePendingSignQueryDataGrid

    /**
     * 後台案件處理-待簽收匣-案件簽收
     *
     * @return
     */
    @Override
    public CaseHandlePendingSignAcceptRs caseHandlePendingSignAccept(CaseHandlePendingReviewAcceptRq rq) {
        String caseId = rq.getCaseId();
        String taskId = rq.getTaskId();
        CaseHandlePendingSignAcceptRs rs = new CaseHandlePendingSignAcceptRs();
        KgoCaseMain queryMain = kgoCaseMainRepository.findByCaseId(caseId);
//		String processId = queryMain.getProcessId();
        rs.setProcessId(queryMain.getProcessId());
        rs.setCaseId(caseId);
        rs.setTaskId(rq.getTaskId());
        // update status
        String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
        queryMain.setCaseOfficer(loginUserId);
        queryMain.setStatus(CaseMainStatusEnum.P3.getValue());
        kgoCaseMainRepository.save(queryMain);

        /** GEO 20211026 add */
        GeoKgoAgent agentData = null;
        List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
        if (principalList != null && principalList.size() > 0) {
            for (GeoKgoAgent agent : principalList) {
                List<Task> taskList = getPrincipalTaskList(agent, rq.getTaskId());
                if (taskList != null && taskList.size() > 0) agentData = agent;
            }
        } //if (principalList != null &&
        // add comment
        String loginUserName = KgoUtil.getBackendLoginUser().getName();
        String caseOrganId = queryMain.getCaseOrgan();
        String caseStatus = CaseStatusEnum.SIGN.getLabel();// 案件狀態
        String comment = String.format("由%s%s", loginUserName, caseStatus); // 內容
        KgoOrgan organItem = kgoOrganRepository.findByOrganId(caseOrganId);
        String organName = organItem.getOrganName(); // 承辦的機關名稱

        /** GEO 20211026 add */
        if (agentData != null) {
            KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
            comment = String.format("由%s代理%s%s", loginUserName, user.getName(), caseStatus);
            agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
            geoKgoAgentRepository.save(agentData);
        }
        /** GEO 20211101 add 增加簽核意見*/
        Comment commentEntity = activitiService.addCommentByTaskId(loginUserId, taskId, caseStatus, comment, organName, loginUserName, CaseStatusEnum.SIGN);
        geoTaskCommentService.saveTaskComment(commentEntity.getId(), rq.getTaskComment(), caseOrganId, loginUserId);

        /** GEO 20211224 add 機關審核表單*/
        if (rq.getColumnData() != null && rq.getColumnData().size() > 0) {
            try {
                saveKgoCaseDetail(rq.getCaseId(), rq.getColumnData(), commentEntity.getId());
            } catch (ParseException e) {
                throw new KgoApiException("caseHandlePendingSignComplete error " + e.getMessage(), e);
            }
        }

        // next flow
        activitiService.nextFlowByTaskIdApprove(taskId, loginUserId);
        // send email and broadcast
//		String caseSetId = queryMain.getCaseSetId();
//		sendMailAndBroadcast(caseSetId, caseId);

        KgoApiException error = null;
        OperationApiMemo memo = null;
        // 後台、、簽收
        try {
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Csign);
            doNotifySign(queryMain.getCaseId(), queryMain);
        } catch (KgoApiException apiE) {
            // TODO Auto-generated catch block
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingSignAccept error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件簽收，案件編號", rq.getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */
            if (error != null) {
                throw error;
            }
        }

        return rs;
    }

    private void doNotifySign(String caseId, KgoCaseMain kgoCaseMain) {
        // 發送mail及推播
        String caseSetId = kgoCaseMain.getCaseSetId();
        Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
        if (kgoCaseset.isPresent()) {
            try {
                // 寄出email
                List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "Email");
                Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
                if (max.isPresent()) {
                    String columnValue = max.get().getColumnValue();
                    if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
                        sendEmailApplyCorrectSign(kgoCaseset.get(), columnValue, caseId);
                    } else {
                        LOGGER.info("doNotifySign doNotify no email to send empty String");
                    }
                } else {
                    LOGGER.info("doNotifySign doNotify no email to send empty");
                }
            } catch (Exception e) {
                LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
            }
            try {
                // 寄出推播
                List<KgoCaseDetail> kgoCaseDetailsID = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "ID");
                Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
                if (maxID.isPresent()) {
                    String columnValue = maxID.get().getColumnValue();
                    if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
                        pushNotificationApplySign(kgoCaseMain, columnValue, kgoCaseset.get().getCaseSetName());
                    } else {
                        LOGGER.info("caseProcessSearch detailSaveAction doNotify no notification to send empty String");
                    }
                } else {
                    LOGGER.info("caseProcessSearch detailSaveAction doNotify no notification to send empty");
                }
            } catch (Exception e) {
                LOGGER.error(KgoBackEndApiError.FAIL_TO_NOTIFY.getErrorMsgKey());
            }
        }
    }

    private void pushNotificationApplySign(KgoCaseMain kgoCaseMain, String id, String caseSetName) {
//		List<PushMsgDto> pushDataList = new ArrayList<>();
//		PushMsgDto pushMsg = new PushMsgDto();
//		pushMsg.setCustNum(id);
//		pushMsg.setMsgTitle(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.msgTitle"));
//		pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.msgBody"),
//				kgoCaseset.getCaseSetName()));
//		pushMsg.setClickDetail(
//				String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.clickDetail"),
//						kgoCaseset.getCaseSetName()));
//		pushDataList.add(pushMsg);
//		this.pushService.pushMessage(pushDataList, kgoCaseset.getOwnerOrgan());
        pushService.pushMessage(id, PushCaseStatusEnum.P.getValue(), kgoCaseMain.getCaseId(), caseSetName, String.valueOf(kgoCaseMain.getApplyDate()));
    }

    private void sendEmailApplyCorrectSign(KgoCaseset kgoCaseset, String email, String caseId) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("casesetName", kgoCaseset.getCaseSetName());
        model.put("caseUrl", String.format(frontendCaseSearchLink, caseId));
        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.sign.mail.title"), model, "signCase.html");
    }

    /**
     * 後台案件處理-待簽收匣-案件批次簽收
     *
     * @return
     */
    @Override
    public List<CaseHandlePendingSignAcceptRs> caseHandlePendingSignBatchAccept(CaseHandlePendingReviewBatchAcceptRq rq) {
        KgoApiException error = null;
        OperationApiMemo memo = null;
        // 後台、、批次簽收
        List<CaseHandlePendingReviewBatchAcceptForm> forms = rq.getForms();
        List<String> caseIds = forms.stream().map(CaseHandlePendingReviewBatchAcceptForm::getCaseId).collect(Collectors.toList());
        List<KgoCaseMain> kgoCaseMainList = kgoCaseMainRepository.findByCaseIdIn(caseIds);
        List<CaseHandlePendingSignAcceptRs> rsList = new ArrayList<>();
        try {
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Cassign);
            kgoCaseMainList.forEach(item -> {
                String status = item.getStatus();
                String caseId = item.getCaseId();
                if (status.equalsIgnoreCase(CaseMainStatusEnum.W.getValue()) || status.equalsIgnoreCase(CaseMainStatusEnum.W3.getValue())) {
                    CaseHandlePendingReviewBatchAcceptForm caseHandlePendingReviewBatchAcceptForm = forms.stream().filter(innerItem -> innerItem.getCaseId().equals(item.getCaseId())).findAny()
                            .orElse(null);
                    if (null != caseHandlePendingReviewBatchAcceptForm && StringUtils.isNotEmpty(caseHandlePendingReviewBatchAcceptForm.getTaskId())) {
                        CaseHandlePendingReviewAcceptRq subrq = new CaseHandlePendingReviewAcceptRq();
                        subrq.setCaseId(caseId);
                        subrq.setTaskId(caseHandlePendingReviewBatchAcceptForm.getTaskId());
                        CaseHandlePendingSignAcceptRs itemRs = caseHandlePendingSignAccept(subrq);
                        rsList.add(itemRs);
                    }
                }
            });
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingSignBatchAccept error " + e.getMessage(), e);
        } finally {
//            /** === 儲存操作紀錄 === */
//            List<OperationColumn> memoList = new ArrayList<>();
//            for (CaseHandlePendingReviewBatchAcceptForm AcceptForm : rq.getForms()) {
//                memoList.add(new OperationColumn("案件批次簽收,案件編號", AcceptForm.getCaseId()));
//            }
//            memo.setMemoList(memoList);
//
//            super.saveOperationLog(memo);
//            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rsList;
    }

    @Autowired
    private TaskService taskService;

    /**
     * 後台案件處理-待簽收匣-案件分案/批次分文
     *
     * @return
     */
    @Override
    public List<CaseHandlePendingSignDispatchRs> caseHandlePendingSignDispatch(CaseHandlePendingSignDispatchRq rq) {
        KgoApiException error = null;
        OperationApiMemo memo = null;

        List<String> caseIdList = rq.getForms().stream().map(item -> item.getCaseId()).collect(Collectors.toList());
        List<String> taskIds = rq.getForms().stream().map(item -> item.getTaskId()).collect(Collectors.toList());
        String acceptor = rq.getAcceptor();
        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        String loginUserId = backendLoginUser.getUserId();
        List<CaseHandlePendingSignDispatchRs> rsList = new ArrayList<>();
        try {
            // 後台、、分文
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Cassign);

            List<KgoCaseMain> kgoCaseMainList = kgoCaseMainRepository.findByCaseIdIn(caseIdList);
            String accetpType = rq.getAcceptType();
            List<KgoCaseMain> filterKgoCaseMainList = new ArrayList<>();
            kgoCaseMainList.forEach(item -> {
                String status = item.getStatus();
                if (status.equalsIgnoreCase(CaseMainStatusEnum.A3.getValue())) {
                    filterKgoCaseMainList.add(item);
                }
            });
            Timestamp currentTime = DateUtil.getCurrentTimestamp();
            filterKgoCaseMainList.forEach(item -> {
                if (accetpType.equalsIgnoreCase("OFFICER")) { //
                    item.setCaseOfficer(acceptor);
                    item.setStatus(CaseMainStatusEnum.P3.getValue());
                } else { // accetpType=UNIT
                    item.setCaseOrgan(acceptor);
                    item.setStatus(CaseMainStatusEnum.A3.getValue());
                }
                item.setUpdateUser(loginUserId);
                item.setUpdateTime(currentTime);
            });
            // send email and broadcast
            String caseStatus = CaseStatusEnum.DP.getLabel();// 案件狀態
            String loginUserOrganId = backendLoginUser.getOrgan();
            KgoOrgan loginUserOrganItem = kgoOrganRepository.findByOrganId(loginUserOrganId);
            String loginUserOrganName = loginUserOrganItem.getOrganName();
            String loginUserName = backendLoginUser.getName();
            List<Map<String, Object>> processInstanceIdList = new ArrayList<>();

            taskIds.forEach(item -> {
                Task task = taskService.createTaskQuery().taskId(item).singleResult();
                String processInstanceId = task.getProcessInstanceId();
                Map<String, Object> map = new HashMap<>();
                map.put("processId", processInstanceId);
                map.put("task", task);
                processInstanceIdList.add(map);
            });
            filterKgoCaseMainList.forEach(item -> {
//			String caseSetId = item.getCaseSetId();
//			String caseId = item.getCaseId();
                // activiti
//			String processId = item.getProcessId();
                // add comment
                Map<String, Object> processIdMap;
                Task task;
                if (accetpType.equalsIgnoreCase("OFFICER")) {
                    Optional<KgoUser> kgoUser = kgoUserRepository.findById(acceptor);
                    String assigneeName = "";
                    if (kgoUser.isPresent()) {
                        assigneeName = kgoUser.get().getName();
                    }
                    String comment = String.format("由%s分案給%s", loginUserName, assigneeName);
                    processIdMap = processInstanceIdList.stream().filter(innerItem -> item.getProcessId().equals(innerItem.get("processId"))).findAny().orElse(null);
                    task = (Task) processIdMap.get("task");
                    /** GEO 20211026 add */
                    GeoKgoAgent agentData = null;
                    List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
                    if (principalList != null && principalList.size() > 0) {
                        for (GeoKgoAgent agent : principalList) {
                            List<Task> taskList = getPrincipalTaskList(agent, task.getId());
                            if (taskList != null && taskList.size() > 0) {
                                agentData = agent;
                            }
                        }
                    } //if (principalList != null &&
                    if (agentData != null) {
                        KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
                        comment = String.format("由%s代理%s 分案給%s", loginUserName, user.getName(), assigneeName);
                        agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
                        geoKgoAgentRepository.save(agentData);
                    }
                    /** GEO 20211101 add 增加簽核意見*/
                    Comment commentEntity = activitiService.addCommentByTaskId(loginUserId, task.getId(), caseStatus, comment, loginUserOrganName, loginUserName, CaseStatusEnum.DP);
                    geoTaskCommentService.saveTaskComment(commentEntity.getId(), rq.getTaskComment(), loginUserOrganId, loginUserId);

                    activitiService.nextFlowByTaskIdApprove(task.getId(), acceptor);
                } else {
                    KgoOrgan assigneeOrganItem = kgoOrganRepository.findByOrganId(acceptor);
                    String assigneeOrganName = assigneeOrganItem.getOrganName();
                    String comment = String.format("由%s分文給%s", loginUserName, assigneeOrganName);
                    processIdMap = processInstanceIdList.stream().filter(innerItem -> item.getProcessId().equals(innerItem.get("processId"))).findAny().orElse(null);
                    task = (Task) processIdMap.get("task");
                    /** GEO 20211026 add */
                    LOGGER.info("caseHandlePendingSignDispatch task.getId() :" + task.getId());
                    GeoKgoAgent agentData = null;
                    List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
                    if (principalList != null && principalList.size() > 0) {
                        for (GeoKgoAgent agent : principalList) {
                            List<Task> taskList = getPrincipalTaskList(agent, task.getId());
                            if (taskList != null && taskList.size() > 0) agentData = agent;
                        }
                    } //if (principalList != null &&
                    if (agentData != null) {
                        KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
                        comment = String.format("由%s代理%s 分文給%s", loginUserName, user.getName(), assigneeOrganName);
                        agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
                        geoKgoAgentRepository.save(agentData);
                    }
                    activitiService.dispatchFlowByTaskId(task.getId(), loginUserOrganId, acceptor, accetpType);

                    /** GEO 20211101 add 增加簽核意見*/
                    Comment commentEntity = activitiService.addCommentByTaskId(loginUserId, task.getId(), caseStatus, comment, loginUserOrganName, loginUserName, CaseStatusEnum.DP);
                    geoTaskCommentService.saveTaskComment(commentEntity.getId(), rq.getTaskComment(), loginUserOrganId, loginUserId);
                }

                CaseHandlePendingSignDispatchRs rs = new CaseHandlePendingSignDispatchRs();
                rs.setCaseId(item.getCaseId());
                rs.setProcessId(item.getProcessId());
                rs.setTaskId(task.getId());
                rsList.add(rs);
                // sendMailAndBroadcast(caseSetId, caseId);

                List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseId(item.getCaseId());

                // Can't find any detail in KGO_CASE_DETAIL
                if (!kgoCaseDetails.isEmpty()) {
                    // Get max version email
                    Optional<KgoCaseDetail> kgoCaseDetailEmail = kgoCaseDetails.stream().filter(k -> BaseColumnEnum.EMAIL.getColumnId().equals(k.getId().getColumnId()))
                            .max(Comparator.comparing(k -> k.getId().getVersion()));

                    if (kgoCaseDetailEmail.isPresent()) {
                        try {
                            LOGGER.info("Start send mail to {}", kgoCaseDetailEmail.get().getColumnValue());
                            Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(item.getCaseSetId());
                            String casesetName = StringUtils.EMPTY;
                            if (kgoCaseset.isPresent())
                                casesetName = kgoCaseset.get().getCaseSetName();
                            sendEmailSignDispatch(item.getCaseId(), casesetName, kgoCaseDetailEmail.get().getColumnValue());
                        } catch (Exception e) {
                            LOGGER.error("Send email review notify error", e);
                        }
                    }
                }
            });
            // update DB
            if (!filterKgoCaseMainList.isEmpty()) {
                kgoCaseMainRepository.saveAll(filterKgoCaseMainList);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingSignDispatch error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            for (CaseHandlePendingReviewBatchSignForm SignForm : rq.getForms()) {
                memoList.add(new OperationColumn("案件分文，案件編號", SignForm.getCaseId()));
            }
            memo.setMemoList(memoList);

            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rsList;
    }

    /**
     * 後台案件處理-已審核匣-初始畫面
     */
    @Override
    public CaseHandleReviewedHomeRs caseHandleReviewedHome() {
        CaseHandleReviewedHomeViewForm viewForm = new CaseHandleReviewedHomeViewForm();
        CaseHandleReviewedHomeRs rs = new CaseHandleReviewedHomeRs();

        try {
            String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
            List<String> statusList = new ArrayList<>();
            statusList.add(CaseMainStatusEnum.F3.getValue());
            statusList.add(CaseMainStatusEnum.J3.getValue());
            statusList.add(CaseMainStatusEnum.S3.getValue());
            List<KgoCaseMain> caseMainList = kgoCaseMainRepository.findByReviewerAndStatusIn(loginUserId, statusList);

            List<String> processIds = caseMainList.stream().map(m -> m.getProcessId()).collect(Collectors.toList());

            List<ReviewedCaseQueryDto> queryResult = kgoCaseMainRepository.getReviewedSACase(processIds);
            List<CaseHandleCaseQueryDataGrid> saGrid = new ArrayList<>();
            queryResult.forEach(item -> {
                CaseHandleCaseQueryDataGrid dg = new CaseHandleCaseQueryDataGrid();
                dg.setCaseId(item.getCaseId());
                dg.setCaseName(item.getCaseSetName());
                dg.setApplyDate(DateUtil.dateToString(item.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                dg.setLimitDate(DateUtil.dateToString(item.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                dg.setApplicant(KgoUtil.hideName(item.getApplyUser()));
                String statusType = item.getStatus();
                dg.setStatusType(statusType);
                dg.setStatusName(CaseMainStatusEnum.getCaseMainStatusEnum(statusType).getLabel());
                dg.setType(item.getType());
                dg.setUpdateTime(DateUtil.dateToString(item.getUpdateTime(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                saGrid.add(dg);
            });
            viewForm.setGrid(saGrid);

            // TODO SCA, URA

            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandleReviewedHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 後台案件處理-已審核匣-案件查詢
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleReviewedQueryRs caseHandleReviewedQuery(CaseHandleReviewedQueryRq rq) {
        CaseHandleReviewedQueryViewForm viewForm = new CaseHandleReviewedQueryViewForm();
        CaseHandleReviewedQueryRs rs = new CaseHandleReviewedQueryRs();

        try {
            List<String> applyDate = rq.getApplyDate();
            String applyUserName = rq.getApplicant() == null ? "" : rq.getApplicant();
            String caseId = rq.getCaseId() == null ? "" : rq.getCaseId();
            String caseSetName = rq.getCaseName() == null ? "" : rq.getCaseName();
            String dateStart = StringUtils.EMPTY;
            String dateEnd = StringUtils.EMPTY;

            // 驗證參數
            this.validateParameterReviewed(rq);

            if (APPLY_START_DATE_INDEX < applyDate.size()) {
                dateStart = applyDate.get(APPLY_START_DATE_INDEX);
            }
            if (APPLY_END_DATE_INDEX < applyDate.size()) {
                dateEnd = applyDate.get(APPLY_END_DATE_INDEX);
            }
            String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
            LOGGER.info("caseHandleReviewedQuery: getBackendLoginUser done");
            String cStatus = rq.getCompleteStatus();
            LOGGER.info("caseHandleReviewedQuery cStatus: " + cStatus);
            List<String> statusList = new ArrayList<>();
            if (StringUtils.isNotEmpty(cStatus)) {
                List<String> strings = Arrays.asList(cStatus.split(","));
                for (String string : strings) {
                    CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(string);
                    if (null != caseMainStatusEnum) {
                        statusList.add(caseMainStatusEnum.getValue());
                    }
                }
            }
            if (statusList.contains(CaseMainStatusEnum.F3.getValue())) {
                statusList.add(ApplyCaseStatusEnum.F.getValue());
            }
            if (statusList.contains(CaseMainStatusEnum.J3.getValue())) {
                statusList.add(ApplyCaseStatusEnum.J.getValue());
            }
            List<KgoCaseMain> caseMainList = kgoCaseMainRepository.findByReviewerAndStatusIn(loginUserId, statusList);
            LOGGER.info("caseHandleReviewedQuery caseMainList: " + caseMainList.size());
            List<KgoUraApply> uraList = kgoUraApplyRepository.findByApplyUserAndStatusIn(loginUserId, statusList);
            LOGGER.info("caseHandleReviewedQuery uraList: " + uraList.size());
            List<KgoUraApply> uraList1 = kgoUraApplyRepository.findByManager1AndStatusIn(loginUserId, statusList);
            LOGGER.info("caseHandleReviewedQuery uraList1: " + uraList1.size());
            List<KgoUraApply> uraList2 = kgoUraApplyRepository.findByManager2AndStatusIn(loginUserId, statusList);
            LOGGER.info("caseHandleReviewedQuery uraList2: " + uraList2.size());
            uraList.addAll(uraList1);
            uraList.addAll(uraList2);
            uraList = uraList.stream().distinct().collect(Collectors.toList());
            List<String> processIds = caseMainList.stream().map(m -> m.getProcessId()).collect(Collectors.toList());
            List<String> processIds2 = uraList.stream().map(item -> item.getProcessId()).collect(Collectors.toList());
            processIds.addAll(processIds2);
            if (!CollectionUtils.isEmpty(processIds)) {
                List<CaseMainQueryReviewedDto> caseDtoList = new LinkedList<>();
                List<CaseMainQueryReviewedDto> dto = kgoCaseMainRepository.reviewedSAQuery(processIds, applyUserName, dateStart, dateEnd, caseSetName, caseId);
                LOGGER.info("caseHandleReviewedQuery dto: " + dto.size());
                caseDtoList.addAll(dto);
                List<CaseHandleCaseQueryDataGrid> gridList = getRsGridDataReviewed(caseDtoList, null);
                LOGGER.info("caseHandleReviewedQuery gridList: " + gridList.size());

                viewForm.setGrid(gridList);
            }
            rs.setData(viewForm);

            // TODO

            // List<CaseMainQueryDto> caseDtoList = new LinkedList<CaseMainQueryDto>();
            // for (String s : getUserRoleList(loginUserId)) {
            // if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s)) {
            // caseDtoList.addAll(reviewedSAQueryWithUnitU(loginUserId, applyUserName,
            // dateStart, dateEnd,
            // caseSetName, caseId));
            // } else if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(s)) {
            // caseDtoList.addAll(reviewedUraScaQueryWithUnitM(loginUserId, applyUserName,
            // dateStart, dateEnd,
            // caseSetName, caseId));
            // }
            // }
            //
            // List<CaseHandleCaseQueryDataGrid> gridList = getRsGridData(caseDtoList);

//			rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandleReviewedQuery error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 後台案件處理-案件檢視-初始畫面
     *
     * @return
     */
    @Override
    public CaseHandleCaseViewHomeRs caseHandleCaseViewHome() {
        CaseHandleCaseViewHomeViewForm viewForm = new CaseHandleCaseViewHomeViewForm();
        CaseHandleCaseViewHomeRs rs = new CaseHandleCaseViewHomeRs();

        try {
            ComboBox comboBox = new ComboBox();
            Map<String, List<CaseMainStatusEnum>> collect = Arrays.stream(CaseMainStatusEnum.values())
                    .collect(Collectors.groupingBy(CaseMainStatusEnum::getLabel, LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<String, List<CaseMainStatusEnum>> stringListEntry : collect.entrySet()) {
                String values = stringListEntry.getValue().stream().map(item -> item.getValue()).collect(Collectors.joining(","));
                comboBox.add(stringListEntry.getKey(), values);
            }
            ComboBox comboBoxFlow = new ComboBox();
            Arrays.stream(CaseFlowTypeEnum.values()).forEach(item -> {
                comboBoxFlow.add(item.getLabel(), item.getValue());
            });
            viewForm.setFlowTypeComboBox(comboBoxFlow);
            viewForm.setStatusComboBox(comboBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandleReviewedHome error " + e.getMessage(), e);
        }

        return rs;
    }

    @Autowired
    private KgoParamSetRepository kgoParamSetRepository;

    /**
     * 後台案件處理-案件檢視-案件查詢
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleCaseViewQueryRs caseHandleCaseViewQuery(CaseHandleCaseViewQueryRq rq) {
        CaseHandleCaseViewQueryViewForm viewForm = new CaseHandleCaseViewQueryViewForm();
        CaseHandleCaseViewQueryRs rs = new CaseHandleCaseViewQueryRs();
        try {
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String loginUserId = backendLoginUser.getUserId();
            String loginOrganId = backendLoginUser.getOrgan();

            // 驗證參數
            this.validateParameter(rq);

            String applicant = rq.getApplicant();
            List<String> applyDate = rq.getApplyDate();
            String applyStartDate = null;
            String applyEndDate = null;

            if (!CollectionUtils.isEmpty(applyDate)) {
                if (APPLY_START_DATE_INDEX < applyDate.size()) {
                    applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                }
                if (APPLY_END_DATE_INDEX < applyDate.size()) {
                    applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                }
            }
            String caseId = rq.getCaseId();
            String caseName = rq.getCaseName();
            String status = rq.getStatus();
            String caseFlowType = rq.getCaseFlowType();
            List<String> statusList;
            if (StringUtils.isNotEmpty(status)) {
                statusList = Arrays.asList(status.split(","));
            } else {
                statusList = new ArrayList<>();
            }

            List<CaseMainQueryCaseViewListDto> caseDtoList = new LinkedList<>();
            List<String> userRoleList = getUserRoleList(loginUserId);

            boolean hasAdminRole = userRoleList.stream().anyMatch(s -> s.trim().equalsIgnoreCase(KgoRoleEnum.ADMIN.getValue()));

            if (hasAdminRole) {
                /** 系統管理者(ADMIN)若為系統管理者已具有最大權限，則不需要判斷其他角色 **/
//                List<CaseMainQueryCaseViewDto> caseMainQueryDtos = kgoCaseMainRepository.caseViewUraScaQuery(false, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName, caseId, status);
//                caseDtoList.addAll(caseMainQueryDtos); // URA、SCA
                List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, applicant, applyStartDate,
                        applyEndDate, caseName, caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                /** 20221109 增加場地活動租借案件*/
                List<CaseMainQueryCaseViewListDto> caseMainSiteQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                        caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), null, null, null, rq.getVersion());
                Map<String, String> siteCaseMap = caseMainSiteQueryDtosSA.stream().map(CaseMainQueryCaseViewListDto::getCaseId).distinct().collect(Collectors.toMap(id -> id, id -> id));
                //避免重複案件ID
                List<CaseMainQueryCaseViewListDto> allCase = caseMainQueryDtosSA.stream().filter(m -> !siteCaseMap.containsKey(m.getCaseId())).collect(Collectors.toList());
                allCase.addAll(caseMainSiteQueryDtosSA);
                caseDtoList.addAll(allCase);// SA
            } else {
                // URA、SCA
//                List<CaseMainQueryCaseViewDto> caseMainQueryDtos = kgoCaseMainRepository.caseViewUraScaQuery(true, loginUserId, applicant,
//                        applyStartDate, applyEndDate, caseName, caseId, status);
//                caseDtoList.addAll(caseMainQueryDtos);

                // SA
                userRoleList = getUserRoleList(loginUserId);
                boolean notProcessed = true; // 用於避免在同時有 UNIT_M & UNIT_A 的身分下重複添加資料集合
                for (String s : userRoleList) {
                    List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = new ArrayList<>();
                    List<CaseMainQueryCaseViewListDto> caseMainSiteQueryDtosSA = new ArrayList<>();
                    if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s)) {
                        // 機關承辦人(UNIT_U)
                        caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(KgoRoleEnum.UNIT_U.getValue(), loginUserId, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                        LOGGER.info("案件檢視");
                        LOGGER.info("caseMainQueryDtosSA" + JSON.toJSONString(caseMainQueryDtosSA));
                        caseMainSiteQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(KgoRoleEnum.UNIT_U.getValue(), loginUserId, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), null, null, null,rq.getVersion());
                        LOGGER.info("caseMainSiteQueryDtosSA" + JSON.toJSONString(caseMainSiteQueryDtosSA));
                    } else if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(s)) {
                        // 案件管理者 (CASE_M)
                        caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(KgoRoleEnum.CASE_M.getValue(), loginUserId, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                        caseMainSiteQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(KgoRoleEnum.CASE_M.getValue(), loginUserId, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), null, null, null, rq.getVersion());
                    } else if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(s) || KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(s)) {
                        // 機關分文、機關管理者(UNIT_A 、UNIT_M)
                        if (notProcessed) {
                            caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(s, StringUtils.EMPTY, loginOrganId, applicant, applyStartDate, applyEndDate, caseName, caseId, statusList,
                                    caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                            caseMainSiteQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(s, StringUtils.EMPTY, loginOrganId, applicant, applyStartDate, applyEndDate, caseName, caseId, statusList,
                                    caseFlowType, rq.getId(), rq.getCellPhone(), null, null, null, rq.getVersion());
                            notProcessed = false;
                        }
                    } //if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s))
                    /** GEO 20211026 add 修正 舊有案件檢視清單 篩選重複問題 */
                    if (caseMainQueryDtosSA != null && caseMainQueryDtosSA.size() > 0) {
                        Map<String, String> siteCaseMap = caseMainSiteQueryDtosSA.stream().collect(Collectors.toMap(m -> m.getCaseId(), m -> m.getCaseSetId()));
                        for (CaseMainQueryCaseViewListDto data : caseMainQueryDtosSA) {
                            boolean isRepeat = false;
                            for (CaseMainQueryCaseViewListDto dtoData : caseDtoList) {
                                if (dtoData.getCaseId().equals(data.getCaseId()) || siteCaseMap.containsKey(dtoData.getCaseId()))
                                    isRepeat = true;
                            }
                            if (!isRepeat) caseDtoList.add(data);
                        }
                    } //if (caseMainQueryDtosSA != null
                    //加入場地活動CASEID
                    caseDtoList.addAll(caseMainSiteQueryDtosSA);
                } //for (String s : userRoleList)
            } //if (hasAdminRole)
            List<CaseHandleCaseQueryDataGrid> gridList = getRsGridDataCaseView(caseDtoList, null);
//            LOGGER.info("gridList"+JSON.toJSONString(gridList));

            /** GEO 20211026 add */
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            LOGGER.info("principalList" + JSON.toJSONString(principalList));
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent g : principalList) {
                    List<CaseMainQueryCaseViewListDto> principalCaseDtoList = new LinkedList<>();
                    if (!hasAdminRole) {
                        userRoleList = getUserRoleList(loginUserId);
                        boolean notProcessed = true; // 用於避免在同時有 UNIT_M & UNIT_A 的身分下重複添加資料集合
                        boolean userHasUnitMAndUnitA = false;
                        for (String role : userRoleList) {
                            if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(role) || KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(role)) {
                                userHasUnitMAndUnitA = true;
                                break;
                            }
                        } // for (String role : userRoleList)
                        for (String s : userRoleList) {
                            List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = new ArrayList<>();
                            if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s)) { // 機關承辦人(UNIT_U)
                                caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(KgoRoleEnum.UNIT_U.getValue(), g.getPrincipalUserId(), StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                        caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                                LOGGER.info("caseMainQueryDtosSA" + JSON.toJSONString(caseMainQueryDtosSA));
                            } else if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(s)) { // 案件管理者 (CASE_M)
                                caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(KgoRoleEnum.CASE_M.getValue(), g.getPrincipalUserId(), StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                        caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                            } else if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(s) || KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(s)) { // 機關分文、機關管理者(UNIT_A 、UNIT_M)
                                if (!userHasUnitMAndUnitA && notProcessed) {
                                    caseMainQueryDtosSA = kgoCaseMainRepository.caseViewSAQuery(s, StringUtils.EMPTY, loginOrganId, applicant, applyStartDate, applyEndDate, caseName, caseId, statusList,
                                            caseFlowType, rq.getId(), rq.getCellPhone(), rq.getVersion());
                                    notProcessed = false;
                                } //if (!userHasUnitMAndA...
                            } // if (KgoRoleEnum.UNIT_U...
                            principalCaseDtoList.addAll(caseMainQueryDtosSA);
                            LOGGER.info("principalCaseDtoList" + JSON.toJSONString(principalCaseDtoList));
                        } // for (String s : userRoleList)
                        List<CaseHandleCaseQueryDataGrid> principalGridList = getRsGridDataCaseView(principalCaseDtoList, null);
                        LOGGER.info("principalGridList" + JSON.toJSONString(principalGridList));
                        if (principalGridList != null && principalGridList.size() > 0) {
                            for (CaseHandleCaseQueryDataGrid data : principalGridList) {
                                boolean isRepeat = false;
                                for (CaseHandleCaseQueryDataGrid gridData : gridList) {
                                    if (gridData.getCaseId().equals(data.getCaseId())) {
                                        isRepeat = true;
                                    }
                                }
                                if (!isRepeat) gridList.add(data);
                            } //for (CaseHandleCaseQueryDataGrid data
                        } //if (principalGridList != null
                    } //if (!hasAdminRole)
                } //for (GeoKgoAgent g: principalList)
            } //if (principalList != null
            LOGGER.info("gridList" + JSON.toJSONString(gridList));
            /** GEO 20211102 add 申請人登入方式，欄位勾選 */
            List<GeoCaseViewFieldCheckModel> fieldCheckList = new ArrayList<>();
            boolean isOnlyOneCaseSetType = false;
            if (gridList != null && gridList.size() > 0) {
                Set<String> caseSetIdSet = new LinkedHashSet<>();
                Set<Integer> caseSetVersion = new LinkedHashSet<>();
                for (CaseHandleCaseQueryDataGrid g : gridList) {
                    caseSetIdSet.add(g.getCasesetId());
                    caseSetVersion.add(g.getVersion());
                }
                LOGGER.info("caseSetIdSet" + JSON.toJSONString(caseSetIdSet));
                LOGGER.info("caseSetVersion" + JSON.toJSONString(caseSetVersion));
                if (caseSetIdSet.size() == 1 && caseSetVersion.size() == 1) {
                    isOnlyOneCaseSetType = true;
                    for (CaseHandleCaseQueryDataGrid g : gridList) {
                        GeoCaseViewFieldCheckModel model = new GeoCaseViewFieldCheckModel();
                        model.setCaseId(g.getCaseId());
                        List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyDataGrids = getFieldCheck(g.getCaseId());
                        model.setApplyDataGrids(applyDataGrids);
                        fieldCheckList.add(model);
                    }
                } //if (caseSetIdSet.size() == 1
            } //	if (gridList != null && gridList.size() > 0)
            LOGGER.info("fieldCheckList" + JSON.toJSONString(fieldCheckList));
            viewForm.setFieldCheckList(fieldCheckList);
            LOGGER.info("isOnlyOneCaseSetType" + JSON.toJSONString(isOnlyOneCaseSetType));
            viewForm.setOnlyOneCaseSetType(isOnlyOneCaseSetType);
            LOGGER.info("gridList" + JSON.toJSONString(gridList));
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("caseHandleCaseViewdQuery error " + e.getMessage(), e);
        }

        return rs;
    }

    @Override
    public CaseHandleCaseViewQueryRs caseHandleSiteCaseViewQuery(CaseHandleSiteCaseViewQueryRq rq) {
        CaseHandleCaseViewQueryViewForm viewForm = new CaseHandleCaseViewQueryViewForm();
        CaseHandleCaseViewQueryRs rs = new CaseHandleCaseViewQueryRs();
        try {
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String loginUserId = backendLoginUser.getUserId();
            String loginOrganId = backendLoginUser.getOrgan();

            // 驗證參數
            this.validateParameter(rq);

            String applicant = rq.getApplicant();
            List<String> applyDate = rq.getApplyDate();
            String applyStartDate = null;
            String applyEndDate = null;

            if (!CollectionUtils.isEmpty(applyDate)) {
                if (APPLY_START_DATE_INDEX < applyDate.size()) {
                    applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                }
                if (APPLY_END_DATE_INDEX < applyDate.size()) {
                    applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                }
            }
            String caseId = rq.getCaseId();
            String caseName = rq.getCaseName();
            String status = rq.getStatus();
            String caseFlowType = rq.getCaseFlowType();
            List<String> statusList;
            if (StringUtils.isNotEmpty(status)) {
                statusList = Arrays.asList(status.split(","));
            } else {
                statusList = new ArrayList<>();
            }

            List<CaseMainQueryCaseViewListDto> caseDtoList = new LinkedList<>();
            List<String> userRoleList = getUserRoleList(loginUserId);

            boolean hasAdminRole = userRoleList.stream().anyMatch(s -> s.trim().equalsIgnoreCase(KgoRoleEnum.ADMIN.getValue()));

            if (hasAdminRole) {
                /** 系統管理者(ADMIN)若為系統管理者已具有最大權限，則不需要判斷其他角色 **/
                List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, applicant, applyStartDate,
                        applyEndDate, caseName, caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                caseDtoList.addAll(caseMainQueryDtosSA);
            } else {
                // SA
                userRoleList = getUserRoleList(loginUserId);
                boolean notProcessed = true; // 用於避免在同時有 UNIT_M & UNIT_A 的身分下重複添加資料集合
                for (String s : userRoleList) {
                    List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = new ArrayList<>();
                    if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s)) {
                        // 機關承辦人(UNIT_U)
                        caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(KgoRoleEnum.UNIT_U.getValue(), loginUserId, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                    } else if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(s)) {
                        // 案件管理者 (CASE_M)
                        caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(KgoRoleEnum.CASE_M.getValue(), loginUserId, StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                    } else if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(s) || KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(s)) {
                        // 機關分文、機關管理者(UNIT_A 、UNIT_M)
                        if (notProcessed) {
                            caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(s, StringUtils.EMPTY, loginOrganId, applicant, applyStartDate, applyEndDate, caseName, caseId, statusList,
                                    caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                            notProcessed = false;
                        }
                    } //if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s))
                    /** GEO 20211026 add 修正 舊有案件檢視清單 篩選重複問題 */
                    if (caseMainQueryDtosSA != null && caseMainQueryDtosSA.size() > 0) {
                        for (CaseMainQueryCaseViewListDto data : caseMainQueryDtosSA) {
                            boolean isRepeat = false;
                            for (CaseMainQueryCaseViewListDto dtoData : caseDtoList) {
                                if (dtoData.getCaseId().equals(data.getCaseId())) isRepeat = true;
                            }
                            if (!isRepeat) caseDtoList.add(data);
                        }
                    } //if (caseMainQueryDtosSA != null
                } //for (String s : userRoleList)
            } //if (hasAdminRole)
            List<CaseHandleCaseQueryDataGrid> gridList = getRsGridDataCaseView(caseDtoList, null);

            /** GEO 20211026 add */
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent g : principalList) {
                    List<CaseMainQueryCaseViewListDto> principalCaseDtoList = new LinkedList<>();
                    if (!hasAdminRole) {
                        userRoleList = getUserRoleList(loginUserId);
                        boolean notProcessed = true; // 用於避免在同時有 UNIT_M & UNIT_A 的身分下重複添加資料集合
                        boolean userHasUnitMAndUnitA = false;
                        for (String role : userRoleList) {
                            if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(role) || KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(role)) {
                                userHasUnitMAndUnitA = true;
                                break;
                            }
                        } // for (String role : userRoleList)
                        for (String s : userRoleList) {
                            List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = new ArrayList<>();
                            if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(s)) { // 機關承辦人(UNIT_U)
                                caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(KgoRoleEnum.UNIT_U.getValue(), g.getPrincipalUserId(), StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                        caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                            } else if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(s)) { // 案件管理者 (CASE_M)
                                caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(KgoRoleEnum.CASE_M.getValue(), g.getPrincipalUserId(), StringUtils.EMPTY, applicant, applyStartDate, applyEndDate, caseName,
                                        caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                            } else if (KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(s) || KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(s)) { // 機關分文、機關管理者(UNIT_A 、UNIT_M)
                                if (!userHasUnitMAndUnitA && notProcessed) {
                                    caseMainQueryDtosSA = geoCaseSetSiteTimeReposCustom.caseViewSAQuery(s, StringUtils.EMPTY, loginOrganId, applicant, applyStartDate, applyEndDate, caseName, caseId, statusList,
                                            caseFlowType, rq.getId(), rq.getCellPhone(), rq.getTimeStart(), rq.getTimeEnd(), rq.getSiteName(), null);
                                    notProcessed = false;
                                } //if (!userHasUnitMAndA...
                            } // if (KgoRoleEnum.UNIT_U...
                            principalCaseDtoList.addAll(caseMainQueryDtosSA);
                        } // for (String s : userRoleList)
                        List<CaseHandleCaseQueryDataGrid> principalGridList = getRsGridDataCaseView(principalCaseDtoList, null);
                        if (principalGridList != null && principalGridList.size() > 0) {
                            for (CaseHandleCaseQueryDataGrid data : principalGridList) {
                                boolean isRepeat = false;
                                for (CaseHandleCaseQueryDataGrid gridData : gridList) {
                                    if (gridData.getCaseId().equals(data.getCaseId())) {
                                        isRepeat = true;
                                    }
                                }
                                if (!isRepeat) gridList.add(data);
                            } //for (CaseHandleCaseQueryDataGrid data
                        } //if (principalGridList != null
                    } //if (!hasAdminRole)
                } //for (GeoKgoAgent g: principalList)
            } //if (principalList != null

            /** GEO 20211102 add 申請人登入方式，欄位勾選 */
            List<GeoCaseViewFieldCheckModel> fieldCheckList = new ArrayList<>();
            boolean isOnlyOneCaseSetType = false;
            if (gridList != null && gridList.size() > 0) {
                Set<String> caseSetIdSet = new LinkedHashSet<>();
                Set<Integer> caseSetVersion = new LinkedHashSet<>();
                for (CaseHandleCaseQueryDataGrid g : gridList) {
                    caseSetIdSet.add(g.getCasesetId());
                    caseSetVersion.add(g.getVersion());
                }
                if (caseSetIdSet.size() == 1 && caseSetVersion.size() == 1) {
                    isOnlyOneCaseSetType = true;
                    for (CaseHandleCaseQueryDataGrid g : gridList) {
                        GeoCaseViewFieldCheckModel model = new GeoCaseViewFieldCheckModel();
                        model.setCaseId(g.getCaseId());
                        List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyDataGrids = getFieldCheck(g.getCaseId());
                        model.setApplyDataGrids(applyDataGrids);
                        fieldCheckList.add(model);
                    }
                } //if (caseSetIdSet.size() == 1
            } //	if (gridList != null && gridList.size() > 0)
            viewForm.setFieldCheckList(fieldCheckList);
            viewForm.setOnlyOneCaseSetType(isOnlyOneCaseSetType);
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("caseHandleCaseViewdQuery error " + e.getMessage(), e);
        }

        return rs;
    }

    private void validateParameterReviewed(CaseHandleReviewedQueryRq rq) {
        List<String> applyDate = rq.getApplyDate();
        if (!CollectionUtils.isEmpty(applyDate)) {
            if (APPLY_START_DATE_INDEX < applyDate.size()) {
                String applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                if (!org.springframework.util.StringUtils.isEmpty(applyStartDate)) {
                    boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", applyStartDate, Locale.TAIWAN);
                    if (!validFormat) {
                        throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseView.queryAction.applyStartDate")));
                    }
                }
            }
            if (APPLY_END_DATE_INDEX < applyDate.size()) {
                String applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                if (!org.springframework.util.StringUtils.isEmpty(applyEndDate)) {
                    boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", applyEndDate, Locale.TAIWAN);
                    if (!validFormat) {
                        throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseView.queryAction.applyEndDate")));
                    }
                }
            }
        }
    }

    private void validateParameter(CaseHandleCaseViewQueryRq rq) {
        List<String> applyDate = rq.getApplyDate();
        if (!CollectionUtils.isEmpty(applyDate)) {
            if (APPLY_START_DATE_INDEX < applyDate.size()) {
                String applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                if (!org.springframework.util.StringUtils.isEmpty(applyStartDate)) {
                    boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", applyStartDate, Locale.TAIWAN);
                    if (!validFormat) {
                        throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseView.queryAction.applyStartDate")));
                    }
                }
            }
            if (APPLY_END_DATE_INDEX < applyDate.size()) {
                String applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                if (!org.springframework.util.StringUtils.isEmpty(applyEndDate)) {
                    boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", applyEndDate, Locale.TAIWAN);
                    if (!validFormat) {
                        throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseView.queryAction.applyEndDate")));
                    }
                }
            }
        }
    }

    /**
     * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    @Transactional
    public CaseHandleCaseViewSaCaseHomeRs caseHandleCaseViewSaCaseHome(CaseHandleCaseViewSaCaseHomeRq rq) {
        CaseHandleCaseViewSaCaseHomeViewForm viewForm = new CaseHandleCaseViewSaCaseHomeViewForm();
        CaseHandleCaseViewSaCaseHomeRs rs = new CaseHandleCaseViewSaCaseHomeRs();

        try {
            String caseId = rq.getCaseId();
            if (StringUtils.isNotBlank(caseId)) {
                SaCaseViewQueryDto dto = kgoCaseMainRepository.getSaCaseViewData(caseId);
                if (null != dto) {
                    String caseSetName = dto.getCaseSetName();
                    String status = CaseMainStatusEnum.getCaseMainStatusEnum(dto.getStatus()).getLabel();
                    CaseMainStateEnum caseMainStateEnum = CaseMainStateEnum.getCaseMainStateEnum(dto.getState());
                    String state = null;
                    if (null != caseMainStateEnum) {
                        state = caseMainStateEnum.getLabel();
                    }
                    String applyDate = DateUtil.dateToString(dto.getApplyDate(), DateUtil.PATTEN_FULL_TIME_SLASH);
                    Integer limitedPeriod = dto.getLimitedPeriod();
                    String deadLineDate = DateUtil.dateToString(dto.getDeadlineDate(), DateUtil.PATTEN_FULL_TIME_SLASH);
                    Integer correctDeadline = dto.getCorrectDeadline();
                    String correctDate = DateUtil.dateToString(dto.getCorrectDate(), DateUtil.PATTEN_FULL_TIME_SLASH);
                    if (correctDeadline == null) {
                        correctDeadline = limitedPeriod;
                        correctDate = deadLineDate;
                    }


                    /** ========= 申請案件 ========= **/
                    List<SaCaseViewDetailColumnQueryDto> dtoList = kgoCaseDetailRepository.getSaCaseDetailData(caseId);
                    for (SaCaseViewDetailColumnQueryDto saCaseViewDetailColumnQueryDto : dtoList) {
                        entityManager.detach(saCaseViewDetailColumnQueryDto);
                    }
                    List<SaCaseViewDetailColumnQueryDto> filterDto = dtoList.stream().filter(item -> !"M".equals(item.getSetColumnType())).collect(Collectors.toList());
                    Map<String, List<SaCaseViewDetailColumnQueryDto>> dtoMap = dtoList.stream().filter(item -> "M".equals(item.getSetColumnType()))
                            .collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getSetColumnId));
                    for (Map.Entry<String, List<SaCaseViewDetailColumnQueryDto>> stringListEntry : dtoMap.entrySet()) {
                        List<SaCaseViewDetailColumnQueryDto> value = stringListEntry.getValue();
                        String joinedColumnValue = value.stream().sorted(Comparator.nullsLast(Comparator.comparingInt(item -> item.getCcolumnOrderNum() == null ? 0 : item.getCcolumnOrderNum())))
                                .map(item -> {
                                    String result = "";
                                    String ftext = item.getFtext();
                                    if (StringUtils.isNotEmpty(ftext)) {
                                        result += ftext;
                                    }
                                    ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
                                    String realColumnValue = item.getRealColumnValue();
                                    if (new Integer(1).equals(item.getIsSource())
                                            && (columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.RADIO || columnTypeEnum == ColumnTypeEnum.DRP)) {
                                        realColumnValue = caseHandleServiceHelper.getColumnMappingValue(item.getSetCcolumnValue(), realColumnValue);
                                    }

                                    if (StringUtils.isNotEmpty(realColumnValue)) {
                                        result += realColumnValue;
                                    }
                                    String btext = item.getBtext();
                                    if (StringUtils.isNotEmpty(btext)) {
                                        result += btext;
                                    }
                                    return result;
                                }).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining(""));
                        String joinedCorrectBValue = value.stream().sorted(Comparator.nullsLast(Comparator.comparingInt(item -> item.getCcolumnOrderNum() == null ? 0 : item.getCcolumnOrderNum())))
                                .map(item -> {
                                    String result = "";
                                    String ftext = item.getFtext();
                                    if (StringUtils.isNotEmpty(ftext)) {
                                        result += ftext;
                                    }
                                    ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
                                    String realColumnValue = item.getCorrectBValue();

                                    if (new Integer(1).equals(item.getIsSource())
                                            && (columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.RADIO || columnTypeEnum == ColumnTypeEnum.DRP)) {
                                        realColumnValue = caseHandleServiceHelper.getColumnMappingValue(item.getSetCcolumnValue(), realColumnValue);
                                    }

                                    if (StringUtils.isNotEmpty(realColumnValue)) {
                                        result += realColumnValue;
                                    }
                                    String btext = item.getBtext();
                                    if (StringUtils.isNotEmpty(btext)) {
                                        result += btext;
                                    }
                                    return result;
                                }).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining(""));
                        SaCaseViewDetailColumnQueryDto saCaseViewDetailColumnQueryDto = value.get(0);
                        saCaseViewDetailColumnQueryDto.setRealColumnValue(joinedColumnValue);
                        saCaseViewDetailColumnQueryDto.setCorrectBValue(joinedCorrectBValue);
                        filterDto.add(saCaseViewDetailColumnQueryDto);
                    }
                    filterDto = filterDto.stream().sorted(Comparator.comparingInt(SaCaseViewDetailColumnQueryDto::getColumnOrderNum)).collect(Collectors.toList());
                    Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.TS.getType());
                    List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyData = filterDto.stream().map(l -> {
                        CaseHandleCaseViewSaCaseApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                        String columnValue = l.getRealColumnValue();
                        String correctBValue = l.getCorrectBValue();
                        String columnName = l.getColumnName();
                        String setColumnValue = l.getSetColumnValue();
                        // 取得KGO_CASE_DETAIL columnValue 對應到 KGO_CASESET 的對應資料轉換.
                        String columnType = l.getSetColumnType();
                        ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(columnType);
                        if (new Integer(1).equals(l.getIsSource()) && (columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.RADIO || columnTypeEnum == ColumnTypeEnum.DRP)) {
                            columnValue = caseHandleServiceHelper.getColumnMappingValue(setColumnValue, columnValue);
                            correctBValue = caseHandleServiceHelper.getColumnMappingValue(setColumnValue, correctBValue);
                        }
                        dg.setColumnName(columnName);
                        if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.F3.getValue().equals(dto.getStatus())
                                || CaseMainStatusEnum.J3.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.S3.getValue().equals(dto.getStatus()))
                                && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId())) && !CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            Timestamp fdate = l.getFdate();
                            if (null != fdate) {
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        if ("Name".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideName(columnValue);
                                        } else if ("ID".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideID(columnValue);
                                        }
                                    }
                                }
                            }
                        } else if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId()))
                                && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            Date applyDateTemp = dto.getApplyDate();
                            if (null != applyDateTemp) {
                                Instant instant = applyDateTemp.toInstant();
                                ZoneId zoneId = ZoneId.systemDefault();
                                LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        if ("Name".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideName(columnValue);
                                        } else if ("ID".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideID(columnValue);
                                        }
                                    }
                                }
                            }
                        }

                        List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexDataList = new ArrayList<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>>();
                        if (dtoMap.containsKey(l.getSetColumnId())) {
                            List<SaCaseViewDetailColumnQueryDto> value = dtoMap.get(l.getSetColumnId());
                            Map<Integer, List<SaCaseViewDetailColumnQueryDto>> dataMap = value.stream().filter(x -> StringUtils.isNoneBlank(x.getSetCcolumnId()))
                                    .collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
                            complexDataList = dataMap.keySet().stream().map(i -> {
                                return dataMap.get(i).stream().map(cl -> {
                                    CaseHandleCaseViewSaCaseApplyDataDataGridComplex complexData = new CaseHandleCaseViewSaCaseApplyDataDataGridComplex();
                                    complexData.setbText(StringUtils.defaultString(cl.getBtext(), ""));
                                    complexData.setcColumnId(StringUtils.defaultString(cl.getSetCcolumnId(), ""));
                                    // 2021/01/18 判斷複合欄位型態
                                    complexData.setColumnType(StringUtils.defaultString(cl.getSetCcolumnType(), ""));
                                    String ccolumnValue = cl.getRealColumnValue();
                                    if (StringUtils.isNotBlank(ccolumnValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                                        ccolumnValue = caseHandleServiceHelper.getColumnMappingValue(cl.getSetCcolumnValue(), ccolumnValue);
                                    }

                                    String ccorrectBValue = cl.getCorrectBValue();
                                    if (StringUtils.isNotBlank(ccorrectBValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                                        ccorrectBValue = caseHandleServiceHelper.getColumnMappingValue(cl.getSetCcolumnValue(), ccorrectBValue);
                                    }
                                    complexData.setColumnValue(StringUtils.defaultString(cl.getSetCcolumnValue(), ""));
                                    complexData.setfText(StringUtils.defaultString(cl.getFtext(), ""));
                                    complexData.setOrderNum(cl.getCcolumnOrderNum());
                                    complexData.setRow(cl.getCcolumnRowNum());
                                    complexData.setValue(StringUtils.defaultString(ccolumnValue, ""));
                                    complexData.setCorrectBValue(StringUtils.defaultString(ccorrectBValue, ""));
                                    return complexData;
                                }).collect(Collectors.toList());
                            }).collect(Collectors.toList());
                        }

                        dg.setIsCorrect(ObjectUtils.defaultIfNull(l.getIsCorrect(), 0) == 1);
                        dg.setComplex(complexDataList);
                        dg.setColumnValue(columnValue);
                        dg.setColumnId(l.getSetColumnId());
                        dg.setCorrectBValue(correctBValue);
                        dg.setCorrectMemo(l.getCorrectMemo());
                        dg.setColumnType(columnType);
                        return dg;
                    }).collect(Collectors.toList());

                    if (CollectionUtils.isEmpty(applyData)) {
                        if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            String applyUserName = dto.getApplyUserName();
                            Date applyDateTemp = dto.getApplyDate();
                            if (null != applyDateTemp) {
                                Instant instant = applyDateTemp.toInstant();
                                ZoneId zoneId = ZoneId.systemDefault();
                                LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        applyUserName = KgoUtil.hideName(dto.getApplyUserName());
                                    }
                                }
                            }
                            CaseHandleCaseViewSaCaseApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                            dg.setColumnName(messageUtil.getMessage("kcg.common.name"));
                            dg.setColumnValue(applyUserName);
                            if (null != applyData) {
                                applyData.add(dg);
                            } else {
                                applyData = Arrays.asList(dg);
                            }
                        }
                    }

                    /** ========= 上傳附件 ========= **/
                    List<KgoCaseFwd> kgoCaseFwdList = kgoCaseFwdRepository.findByIdCaseId(caseId);
                    List<CaseHandleCaseViewSaCaseDownloadDataDataGrid> downloadData = kgoCaseFwdList.stream().map(l -> {
                        CaseHandleCaseViewSaCaseDownloadDataDataGrid dg = new CaseHandleCaseViewSaCaseDownloadDataDataGrid();
                        dg.setSeq(l.getId().getSeq());
                        dg.setTitle(l.getTitle());
                        dg.setFileName(l.getFileName());
                        return dg;
                    }).collect(Collectors.toList());

                    /** ========= 處理歷程 ========= **/
                    List<String> processIds = new ArrayList<>();
                    /** 2020.12.11 處理歷程新增案件流程重送 歷程記錄 */
                    String nowProcessId = dto.getProcessId();
                    processIds.add(nowProcessId);

                    // 新增案件流程重送 歷程記錄
                    List<KgoCaseMainResendFlow> resendFlowList = kgoCaseMainResendFlowRepository.findByCaseId(dto.getCaseId());
                    processIds.addAll(resendFlowList.stream().map(r -> r.getProcessId()).distinct().collect(Collectors.toList()));

                    List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(processIds);
                    List<CaseHandleCaseViewCaseHistoryDataGrid> historyData = castToHistoryDataGrid(historyDataDto, null);

                    String image = "";

                    // 歷程圖
                    if (!StringUtils.isEmpty(dto.getProcessId()))
                        image = processTransaction.showProcessImg(dto.getProcessId());

                    viewForm.setApplyDate(applyDate);
                    viewForm.setCaseId(caseId);
                    viewForm.setCaseName(caseSetName);
                    viewForm.setLimitDay(limitedPeriod);
                    viewForm.setLimitTime(deadLineDate);
                    viewForm.setCorrectDeadline(correctDeadline);
                    viewForm.setCorrectDate(correctDate);
                    viewForm.setStatus(status);
                    viewForm.setState(state);
                    viewForm.setApplyData(applyData);
                    viewForm.setDownloadData(downloadData);
                    viewForm.setHistoryData(historyData);
                    viewForm.setImage(image);
                    viewForm.setCaseFlowType(dto.getCaseFlowType());

                    /** === 動態流程 === */
                    /** 是否為動態流程 */
                    KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
                    KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(kgoCaseMain.getCaseSetId());
                    viewForm.setIsDynamicFlow(StringUtils.isNotBlank(kgoCaseset.getFlowId()) ? true : false);

                    // 檢視、結案不會有taskId
                    if (StringUtils.isNotBlank(rq.getTaskId())) {
                        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
                        if (null != task) {
                            /** 是否為結案 */
                            Boolean isEnd = processTransaction.isLastTask(task.getId());
                            viewForm.setIsEnd(isEnd);

                            /** 2020.12.11 是否允許退回上一關. */
                            Boolean isCanReject = tpiFlowService.isTaskCanReject(task.getId());
                            viewForm.setIsCanReject(isCanReject);

                            /** 流程按鈕顯示名稱 (分文、會簽 or陳核) */
                            String btnDisplayName = BaseFlowUtils.getTaskSubTypeNameByName(task.getName());
                            viewForm.setBtnDisplayName(btnDisplayName);
                        }
                    }
                    viewForm.setFlowId(kgoCaseset.getFlowId());

                    /** === 動態流程 === */
                }
            }
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
//			e.printStackTrace();
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
            throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    @Transactional
    public CaseHandleCaseViewSaCaseHomeRs caseHandleCaseViewSaCaseHome2(CaseHandleCaseViewSaCaseHomeRq rq) {
        CaseHandleCaseViewSaCaseHomeViewForm viewForm = new CaseHandleCaseViewSaCaseHomeViewForm();
        CaseHandleCaseViewSaCaseHomeRs rs = new CaseHandleCaseViewSaCaseHomeRs();

        try {
            String caseId = rq.getCaseId();
            if (StringUtils.isNotBlank(caseId)) {
                SaCaseViewQueryDto dto = kgoCaseMainRepository.getSaCaseViewData(caseId);
                if (null != dto) {
                    String caseSetName = dto.getCaseSetName();
                    CaseMainStateEnum caseMainStateEnum = CaseMainStateEnum.getCaseMainStateEnum(dto.getState());
                    String state = null;
                    if (null != caseMainStateEnum) {
                        state = caseMainStateEnum.getLabel();
                    }
                    String applyDate = DateUtil.dateToString(dto.getApplyDate(), DateUtil.PATTEN_FULL_TIME_SLASH);
                    Integer limitedPeriod = dto.getLimitedPeriod();
                    String deadLineDate = DateUtil.dateToString(dto.getDeadlineDate(), DateUtil.PATTEN_FULL_TIME_SLASH);
                    Integer correctDeadline = dto.getCorrectDeadline();
                    String correctDate = DateUtil.dateToString(dto.getCorrectDate(), DateUtil.PATTEN_FULL_TIME_SLASH);
                    if (correctDeadline == null) {
                        correctDeadline = limitedPeriod;
                        correctDate = deadLineDate;
                    }

                    CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(dto.getStatus());
                    String status = StringUtils.EMPTY;
                    if (ObjectUtils.isNotEmpty(caseMainStatusEnum)) {
                        if (caseMainStatusEnum == CaseMainStatusEnum.O) {
                            status = String.format("%s (%s)", caseMainStatusEnum.getLabel(), dto.getStatusDesc());
                        } else {
                            status = caseMainStatusEnum.getLabel();
                        }
                    } else {
                        status = CaseMainStatusEnum.OTHERS.getLabel();
                    }

                    /** ========= 申請案件 ========= **/
                    List<SaCaseViewDetailColumnQueryDto> dtoList = kgoCaseDetailRepository.getSaCaseDetailData(caseId);
                    for (SaCaseViewDetailColumnQueryDto saCaseViewDetailColumnQueryDto : dtoList) {
                        entityManager.detach(saCaseViewDetailColumnQueryDto);
                    }
                    List<SaCaseViewDetailColumnQueryDto> filterDto = dtoList.stream().filter(item -> ObjectUtils.isEmpty(item.getCcolumnRowNum())).collect(Collectors.toList());
                    Map<String, List<SaCaseViewDetailColumnQueryDto>> dtoMap = dtoList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getCcolumnRowNum()))
                            .collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getSetColumnId));
//					for (Map.Entry<String, List<SaCaseViewDetailColumnQueryDto>> stringListEntry : dtoMap.entrySet()) {
//						List<SaCaseViewDetailColumnQueryDto> value = stringListEntry.getValue();
//
//					}
                    filterDto = filterDto.stream().sorted(Comparator.comparingInt(SaCaseViewDetailColumnQueryDto::getColumnOrderNum)).collect(Collectors.toList());
                    Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.TS.getType());
                    List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyData = filterDto.stream().map(l -> {
                        CaseHandleCaseViewSaCaseApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                        String columnValue = l.getRealColumnValue();
                        String columnName = l.getColumnName();
                        String setColumnValue = l.getSetColumnValue();
                        // 取得KGO_CASE_DETAIL columnValue 對應到 KGO_CASESET 的對應資料轉換.
                        // 2021/01/18 fix columnType 比對 ColumnTypeEnum getLable -> getValue
                        String columnType = l.getSetColumnType();
                        if (new Integer(1).equals(l.getIsSource()) && StringUtils.isNotBlank(columnValue) && (columnType.equalsIgnoreCase(ColumnTypeEnum.CHECKBOX.getValue())
                                || columnType.equalsIgnoreCase(ColumnTypeEnum.RADIO.getValue()) || columnType.equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                            columnValue = caseHandleServiceHelper.getColumnMappingValue(setColumnValue, columnValue);
                        }
                        dg.setColumnName(columnName);
                        if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.F3.getValue().equals(dto.getStatus())
                                || CaseMainStatusEnum.J3.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.S3.getValue().equals(dto.getStatus()))
                                && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId())) && !CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            Timestamp fdate = l.getFdate();
                            if (null != fdate) {
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        if ("Name".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideName(columnValue);
                                        } else if ("ID".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideID(columnValue);
                                        }
                                    }
                                }
                            }
                        } else if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId()))
                                && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            Date applyDateTemp = dto.getApplyDate();
                            if (null != applyDateTemp) {
                                Instant instant = applyDateTemp.toInstant();
                                ZoneId zoneId = ZoneId.systemDefault();
                                LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        if ("Name".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideName(columnValue);
                                        } else if ("ID".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideID(columnValue);
                                        }
                                    }
                                }
                            }
                        }

                        List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexDataList = new ArrayList<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>>();
                        if (dtoMap.containsKey(l.getSetColumnId())) {
                            List<SaCaseViewDetailColumnQueryDto> value = dtoMap.get(l.getSetColumnId());
                            Map<Integer, List<SaCaseViewDetailColumnQueryDto>> dataMap = value.stream()
                                    .collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
                            complexDataList = dataMap.keySet().stream().map(i -> {
                                return dataMap.get(i).stream().map(cl -> {
                                    CaseHandleCaseViewSaCaseApplyDataDataGridComplex complexData = new CaseHandleCaseViewSaCaseApplyDataDataGridComplex();
                                    complexData.setbText(StringUtils.defaultString(cl.getBtext(), ""));
                                    complexData.setcColumnId(StringUtils.defaultString(cl.getSetCcolumnId(), ""));
                                    // 2021/01/18 判斷複合欄位型態
                                    complexData.setColumnType(StringUtils.defaultString(cl.getSetCcolumnType(), ""));
                                    String CcolumnValue = cl.getRealColumnValue();
                                    if (StringUtils.isNotBlank(CcolumnValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                                        CcolumnValue = caseHandleServiceHelper.getColumnMappingValue(cl.getSetCcolumnValue(), CcolumnValue);
                                    }
                                    complexData.setColumnValue(StringUtils.defaultString(cl.getSetCcolumnValue(), ""));
                                    complexData.setfText(StringUtils.defaultString(cl.getFtext(), ""));
                                    complexData.setOrderNum(cl.getCcolumnOrderNum());
                                    complexData.setRow(cl.getCcolumnRowNum());
                                    complexData.setValue(StringUtils.defaultString(CcolumnValue, ""));
                                    return complexData;
                                }).collect(Collectors.toList());
                            }).collect(Collectors.toList());
                        }

                        dg.setComplex(complexDataList);
                        dg.setColumnValue(columnValue);
                        dg.setColumnId(l.getSetColumnId());
                        dg.setCorrectBValue(l.getCorrectBValue());
                        dg.setCorrectMemo(l.getCorrectMemo());
                        dg.setColumnType(columnType);
                        return dg;
                    }).collect(Collectors.toList());

                    if (CollectionUtils.isEmpty(applyData)) {
                        if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            String applyUserName = dto.getApplyUserName();
                            Date applyDateTemp = dto.getApplyDate();
                            if (null != applyDateTemp) {
                                Instant instant = applyDateTemp.toInstant();
                                ZoneId zoneId = ZoneId.systemDefault();
                                LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        applyUserName = KgoUtil.hideName(dto.getApplyUserName());
                                    }
                                }
                            }
                            CaseHandleCaseViewSaCaseApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                            dg.setColumnName(messageUtil.getMessage("kcg.common.name"));
                            dg.setColumnValue(applyUserName);
                            if (null != applyData) {
                                applyData.add(dg);
                            } else {
                                applyData = Arrays.asList(dg);
                            }
                        }
                    }

                    /** ========= 上傳附件 ========= **/
                    List<KgoCaseFwd> kgoCaseFwdList = kgoCaseFwdRepository.findByIdCaseId(caseId);
                    List<CaseHandleCaseViewSaCaseDownloadDataDataGrid> downloadData = kgoCaseFwdList.stream().map(l -> {
                        CaseHandleCaseViewSaCaseDownloadDataDataGrid dg = new CaseHandleCaseViewSaCaseDownloadDataDataGrid();
                        dg.setSeq(l.getId().getSeq());
                        dg.setTitle(l.getTitle());
                        dg.setFileName(l.getFileName());
                        return dg;
                    }).collect(Collectors.toList());

                    /** ========= 處理歷程 ========= **/
                    List<String> processIds = new ArrayList<>();
                    /** 2020.12.11 處理歷程新增案件流程重送 歷程記錄 */
                    String nowProcessId = dto.getProcessId();
                    processIds.add(nowProcessId);

                    /** GEO 20211109 add 機關審核表單 */
                    /** ========= 機關審核表單 ========= **/
                    List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid> organFormData = new ArrayList<>();
                    List<SaCaseViewOrganFormDetailColumnQueryDto> organDtoList = geoKgoCasesetDetailOrganReposCustom.getSaCaseDetailData(caseId);
                    if (organDtoList != null && organDtoList.size() > 0) {
                        for (SaCaseViewOrganFormDetailColumnQueryDto saCaseViewDetailColumnQueryDto : organDtoList) {
                            entityManager.detach(saCaseViewDetailColumnQueryDto);
//                            LOGGER.info("** saCaseViewDetailColumnQueryDto : " + saCaseViewDetailColumnQueryDto);
                        } //for (SaCaseViewOrganFormDetailColumnQueryDto

                        List<SaCaseViewOrganFormDetailColumnQueryDto> organFilterDto = organDtoList.stream().filter(item -> ObjectUtils.isEmpty(item.getCcolumnRowNum())).collect(Collectors.toList());
                        Map<String, List<SaCaseViewOrganFormDetailColumnQueryDto>> organDtoMap = organDtoList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getCcolumnRowNum()))
                                .collect(Collectors.groupingBy(SaCaseViewOrganFormDetailColumnQueryDto::getSetColumnId));
                        organFilterDto = organFilterDto.stream().sorted(Comparator.comparingInt(SaCaseViewOrganFormDetailColumnQueryDto::getColumnOrderNum)).collect(Collectors.toList());
                        organFormData = organFilterDto.stream().map(l -> {
                            CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid();
                            String columnValue = l.getRealColumnValue();
                            String columnName = l.getColumnName();
                            String setColumnValue = l.getSetColumnValue();

                            String columnType = l.getSetColumnType();
                            if (new Integer(1).equals(l.getIsSource()) && StringUtils.isNotBlank(columnValue) && (columnType.equalsIgnoreCase(ColumnTypeEnum.CHECKBOX.getValue())
                                    || columnType.equalsIgnoreCase(ColumnTypeEnum.RADIO.getValue()) || columnType.equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                                columnValue = caseHandleServiceHelper.getColumnMappingValue(setColumnValue, columnValue);
                            }
                            dg.setColumnName(columnName);
                            if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.F3.getValue().equals(dto.getStatus())
                                    || CaseMainStatusEnum.J3.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.S3.getValue().equals(dto.getStatus()))
                                    && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId())) && !CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                                Timestamp fdate = l.getFdate();
                                if (null != fdate) {
                                    LocalDateTime localDateTime = null;
                                    if (kgoParamSetOptional.isPresent()) {
                                        KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                        String value = kgoParamSet.getValue();
                                        String detailType = kgoParamSet.getDetailType();
                                        LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
                                        if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                        } //if (detailType.equals(ParamSetTypeEnum.Y.getCode()))...
                                        if (localDateTime.isBefore(LocalDateTime.now())) {
                                            if ("Name".equals(l.getSetColumnId())) {
                                                columnValue = KgoUtil.hideName(columnValue);
                                            } else if ("ID".equals(l.getSetColumnId())) {
                                                columnValue = KgoUtil.hideID(columnValue);
                                            }
                                        } //if (localDateTime.isBefore(LocalDateTime.now()))
                                    } //if (kgoParamSetOptional.isPresent())
                                } // if (null != fdate)
                            } else if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId()))
                                    && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                                Date applyDateTemp = dto.getApplyDate();
                                if (null != applyDateTemp) {
                                    Instant instant = applyDateTemp.toInstant();
                                    ZoneId zoneId = ZoneId.systemDefault();
                                    LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                    LocalDateTime localDateTime = null;
                                    if (kgoParamSetOptional.isPresent()) {
                                        KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                        String value = kgoParamSet.getValue();
                                        String detailType = kgoParamSet.getDetailType();
                                        LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                        if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                        } //if (detailType.equals(ParamSetTypeEnum.Y.getCode()))

                                        if (localDateTime.isBefore(LocalDateTime.now())) {
                                            if ("Name".equals(l.getSetColumnId())) {
                                                columnValue = KgoUtil.hideName(columnValue);
                                            } else if ("ID".equals(l.getSetColumnId())) {
                                                columnValue = KgoUtil.hideID(columnValue);
                                            }
                                        } //if (localDateTime.isBefore(LocalDateTime.now()))
                                    } // if (kgoParamSetOptional.isPresent())
                                } //if (null != applyDateTemp)
                            } //if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus())...

                            List<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>> organComplexDataList = new ArrayList<List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex>>();
                            if (organDtoMap.containsKey(l.getSetColumnId())) {
                                List<SaCaseViewOrganFormDetailColumnQueryDto> value = organDtoMap.get(l.getSetColumnId());
                                Map<Integer, List<SaCaseViewOrganFormDetailColumnQueryDto>> organDataMap = value.stream()
                                        .collect(Collectors.groupingBy(SaCaseViewOrganFormDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
                                organComplexDataList = organDataMap.keySet().stream().map(i -> {
                                    return organDataMap.get(i).stream().map(cl -> {
                                        CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex complexData = new CaseHandleCaseViewSaCaseOrganFormApplyDataDataGridComplex();
                                        complexData.setbText(StringUtils.defaultString(cl.getBtext(), ""));
                                        complexData.setcColumnId(StringUtils.defaultString(cl.getSetCcolumnId(), ""));
                                        complexData.setColumnType(StringUtils.defaultString(cl.getSetCcolumnType(), ""));
                                        String CcolumnValue = cl.getRealColumnValue();
                                        if (StringUtils.isNotBlank(CcolumnValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
                                                || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue())
                                                || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                                            CcolumnValue = caseHandleServiceHelper.getColumnMappingValue(cl.getSetCcolumnValue(), CcolumnValue);
                                        }
                                        complexData.setColumnValue(StringUtils.defaultString(cl.getSetCcolumnValue(), ""));
                                        complexData.setfText(StringUtils.defaultString(cl.getFtext(), ""));
                                        complexData.setOrderNum(cl.getCcolumnOrderNum());
                                        complexData.setRow(cl.getCcolumnRowNum());
                                        complexData.setValue(StringUtils.defaultString(CcolumnValue, ""));
                                        return complexData;
                                    }).collect(Collectors.toList()); //return organDataMap.get(i).stream().map(cl
                                }).collect(Collectors.toList()); //organComplexDataList = organDataMap.keySet().stream().map
                            } //if (organDtoMap.containsKey(l.getSetColumnId()))

                            dg.setComplex(organComplexDataList);
                            dg.setColumnValue(columnValue);
                            dg.setColumnId(l.getSetColumnId());
                            dg.setCorrectBValue(l.getCorrectBValue());
                            dg.setCorrectMemo(l.getCorrectMemo());
                            dg.setCommentId(l.getCommentId());
                            dg.setColumnType(columnType);
                            return dg;
                        }).collect(Collectors.toList()); //organFormData = organFilterDto.stream().map(l

                        if (CollectionUtils.isEmpty(organFormData)) {
                            if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                                String applyUserName = dto.getApplyUserName();
                                Date applyDateTemp = dto.getApplyDate();
                                if (null != applyDateTemp) {
                                    Instant instant = applyDateTemp.toInstant();
                                    ZoneId zoneId = ZoneId.systemDefault();
                                    LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                    LocalDateTime localDateTime = null;
                                    if (kgoParamSetOptional.isPresent()) {
                                        KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                        String value = kgoParamSet.getValue();
                                        String detailType = kgoParamSet.getDetailType();
                                        LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                        if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                        } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                            localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                        }
                                        if (localDateTime.isBefore(LocalDateTime.now())) {
                                            applyUserName = KgoUtil.hideName(dto.getApplyUserName());
                                        }
                                    } // if (kgoParamSetOptional.isPresent())
                                } //if (null != applyDateTemp)
                                CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid();
                                dg.setColumnName(messageUtil.getMessage("kcg.common.name"));
                                dg.setColumnValue(applyUserName);
                                if (null != organFormData) {
                                    organFormData.add(dg);
                                } else {
                                    organFormData = Arrays.asList(dg);
                                }
                            }
                        } // if (CollectionUtils.isEmpty(organFormData))
                    } // if (organDtoList != null && organDtoList.size() > 0)

                    // 新增案件流程重送 歷程記錄
                    List<KgoCaseMainResendFlow> resendFlowList = kgoCaseMainResendFlowRepository.findByCaseId(dto.getCaseId());
                    processIds.addAll(resendFlowList.stream().map(r -> r.getProcessId()).distinct().collect(Collectors.toList()));
                    List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(processIds);
                    List<CaseHandleCaseViewCaseHistoryDataGrid> historyData = castToHistoryDataGrid(historyDataDto, organFormData);

                    String image = "";

                    // 歷程圖
                    if (!StringUtils.isEmpty(dto.getProcessId()))
                        image = processTransaction.showProcessImg(dto.getProcessId());

                    viewForm.setApplyDate(applyDate);
                    viewForm.setCaseId(caseId);
                    viewForm.setCaseName(caseSetName);
                    viewForm.setLimitDay(limitedPeriod);
                    viewForm.setLimitTime(deadLineDate);
                    viewForm.setCorrectDeadline(correctDeadline);
                    viewForm.setCorrectDate(correctDate);
                    viewForm.setStatus(status);
                    viewForm.setState(state);
                    viewForm.setApplyData(applyData);
                    viewForm.setDownloadData(downloadData);
                    viewForm.setHistoryData(historyData);
                    viewForm.setImage(image);
                    viewForm.setCaseFlowType(dto.getCaseFlowType());

                    /** === 動態流程 === */
                    /** 是否為動態流程 */
                    KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
                    KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(kgoCaseMain.getCaseSetId());
                    viewForm.setIsDynamicFlow(StringUtils.isNotBlank(kgoCaseset.getFlowId()) ? true : false);

                    /** 案件預約/繳費狀態*/
                    String category = kgoCaseset.getCasesetCategory();
                    if (CaseSetCategoryEnum.SITE == CaseSetCategoryEnum.getApplyTypeEnum(category) || CaseSetCategoryEnum.ACTIVITY == CaseSetCategoryEnum.getApplyTypeEnum(category)) {
                        GeoKgoCaseRentRelation rentRelation = geoCaseRentRelationRepos.findByCaseId(caseId);
                        GeoKgoCaseRentPayment payment = geoKgoRentPaymentRepos.findByCaseId(caseId);
                        String rentStatus = rentRelation.getRentStatus() != null ? rentRelation.getRentStatus() : RentStatusEnum.PROC.getLabel();
                        if(kgoCaseset.getNeedPay() == null ? false : kgoCaseset.getNeedPay()){
                            RentStatusEnum paymentStatus = payment == null ? RentStatusEnum.WAIT : RentStatusEnum.getRentStatusEnum(payment.getPaymentStatus());
                            Map<String, String> payMap = new HashMap<>();
                            payMap.put("label", paymentStatus.getLabel());
                            payMap.put("value", paymentStatus.getValue());
                            viewForm.setPayment_status(payMap);
                        }
                        if( payment != null ){
                            if( payment.getPayTime() != null ){
                                applyData.add(new CaseHandleCaseViewSaCaseApplyDataDataGrid("當時繳費金額",payment.getPayAmount()+"元"));
                            }
                            if( payment.getRefundTime() != null){
                                String refundAmount =( payment.getPayAmount() * payment.getDeductPercent() / 100) + "元";
                                String refundDiscount = payment.getRefundDiscount() == -1 ? "不使用優惠折扣" : payment.getRefundDiscount()+"%";

                                applyData.add(new CaseHandleCaseViewSaCaseApplyDataDataGrid("服務退費比例",payment.getDeductPercent()+"%"));
                                applyData.add(new CaseHandleCaseViewSaCaseApplyDataDataGrid("預計退費金額",refundAmount));
                                applyData.add(new CaseHandleCaseViewSaCaseApplyDataDataGrid("退費優惠折扣",refundDiscount));
                                applyData.add(new CaseHandleCaseViewSaCaseApplyDataDataGrid("實際退費金額",payment.getActualRefundAmount()+"元"));

                            }
                        }


                        viewForm.setRent_status(rentStatus);

                    }


                    // 檢視、結案不會有taskId
                    if (StringUtils.isNotBlank(rq.getTaskId())) {
                        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
                        if (null != task) {
                            /** 是否為結案 */
                            Boolean isEnd = processTransaction.isLastTask(task.getId());
                            viewForm.setIsEnd(isEnd);

                            /** 2020.12.11 是否允許退回上一關. */
                            Boolean isCanReject = tpiFlowService.isTaskCanReject(task.getId());
                            viewForm.setIsCanReject(isCanReject);

                            /** 流程按鈕顯示名稱 (分文、會簽 or陳核) */
                            String btnDisplayName = BaseFlowUtils.getTaskSubTypeNameByName(task.getName());
                            viewForm.setBtnDisplayName(btnDisplayName);
                        }
                    }
                    viewForm.setFlowId(kgoCaseset.getFlowId());

                    /** === 動態流程 === */
                }
            }
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
//			e.printStackTrace();
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
            throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 後台案件處理-案件檢視-服務申辦(SA)案件檢視- 檔案下載(myData資料、上傳附件).
     *
     * @param rq the rq
     */
    @Override
    public void caseHandleCaseViewScaCaseDownload(CaseHandleCaseViewSaCaseDownloadRq rq) {
        String caseId = rq.getCaseId();
        String fileName = rq.getFileName();
        String folderPath = Strings.EMPTY;
        try {
            if (StringUtils.isBlank(caseId) || StringUtils.isBlank(fileName)) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD));
            }
            // 案件附件 檔案上傳路徑
            folderPath = KgoUtil.getCaseDownloadUploadBasePath(caseId);
            File file = new File(folderPath + fileName);
            LOGGER.info("download path 1st check file.exists():" + file.exists());
            if (!file.exists()) {
                String myDataId = Strings.EMPTY;
                List<KgoCaseDetail> caseDetails = kgoCaseDetailRepository.findByIdCaseIdAndColumnValue(caseId, fileName);
                LOGGER.info("download path caseDetails:" + JSON.toJSONString(caseDetails));
                KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);
                LOGGER.info("download path caseMain:" + JSON.toJSONString(caseMain));
                if (!caseDetails.isEmpty() && caseMain != null) {
                    List<KgoCasesetColumn> casesetColumns = kgoCasesetColumnRepository.findByIdCaseSetIdAndIdVersion(caseMain.getCaseSetId(), caseDetails.get(0).getId().getVersion());
                    List<KgoCasesetColumn> casesetColumnFilter = casesetColumns.stream().filter(e -> caseDetails.get(0).getId().getColumnId().equalsIgnoreCase(e.getId().getColumnId())).collect(toList());
                    myDataId = casesetColumnFilter.get(0).getMyDataId();
                }
                LOGGER.info("download path myDataId:" + myDataId);
                if (Strings.isNotBlank(myDataId)) {
                    StringBuilder formDownloadUploadBasePath = new StringBuilder();
                    formDownloadUploadBasePath.append(SpringUtil.getProperty("mydata.jwefile.temp.path"));
                    formDownloadUploadBasePath.append(caseMain.getMyDataTxId());
                    formDownloadUploadBasePath.append("/");
                    formDownloadUploadBasePath.append(myDataId);
                    formDownloadUploadBasePath.append("/");
                    formDownloadUploadBasePath.append(fileName);
                    folderPath = formDownloadUploadBasePath.toString();
                    file = new File(folderPath);
                }
            }
            if (!file.exists()) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD));
            } else {
                // 檔案下載
                commonService.downloadFileAction(file);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD);
            LOGGER.error("caseHandleCaseViewScaCaseDownload: {}", errorResult.getErrorDesc());
            throw new KgoApiException(errorResult);
        } finally {
            LOGGER.info("download path");
            LOGGER.info(folderPath);
        }
    }

    /**
     * 後台案件處理-案件檢視-系統權限申請(URA)案件檢視-初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleCaseViewUraCaseHomeRs caseHandleCaseViewUraCaseHome(CaseHandleCaseViewUraCaseHomeRq rq) {
        CaseHandleCaseViewUraCaseHomeViewForm viewForm = new CaseHandleCaseViewUraCaseHomeViewForm();
        CaseHandleCaseViewUraCaseHomeRs rs = new CaseHandleCaseViewUraCaseHomeRs();

        try {
            String caseId = rq.getCaseId();

//			String image = showProcessImg(null);
//			System.out.println(image);
            /** 案件資料 **/
            UraCaseViewQueryDto dto = kgoUraApplyRepository.getUraCaseViewData(caseId);
            if (null != dto) {
                /** ========= 處理歷程 ========= **/
                List<CaseHandleCaseViewCaseHistoryDataGrid> historyData = new LinkedList<>();
                List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(dto.getProcessId());
                historyDataDto.forEach(item -> {
                    CaseHandleCaseViewCaseHistoryDataGrid caseHandleCaseViewCaseHistoryDataGrid = new CaseHandleCaseViewCaseHistoryDataGrid();
                    caseHandleCaseViewCaseHistoryDataGrid.setStatus(item.getStatus());
                    caseHandleCaseViewCaseHistoryDataGrid.setContent(item.getContent());
                    caseHandleCaseViewCaseHistoryDataGrid.setDealTime(item.getDealTime());
                    caseHandleCaseViewCaseHistoryDataGrid.setTaker(item.getOfficer());
                    caseHandleCaseViewCaseHistoryDataGrid.setOrgan(item.getOrganName());
                    historyData.add(caseHandleCaseViewCaseHistoryDataGrid);
                });
                // 歷程圖
                String image = processTransaction.showProcessImg(dto.getProcessId());
//                LOGGER.info("image === >> " + image);
                viewForm.setCaseId(caseId);
                viewForm.setApplicant(KgoUtil.hideName(dto.getApplyUser()));
                viewForm.setOrganName(dto.getApplyOrgan());
                viewForm.setUnitName(dto.getApplyUnit());
                viewForm.setEmail(dto.getEmail());
                viewForm.setPhone(dto.getPhone());
                viewForm.setApplyRole(dto.getApplyRole());
                viewForm.setImage(image);
                viewForm.setHistoryData(historyData);
                rs.setData(viewForm);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * 後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleCaseViewScaCaseHomeRs caseHandleCaseViewScaCaseHome(CaseHandleCaseViewScaCaseHomeRq rq) {
        CaseHandleCaseViewScaCaseHomeViewForm viewForm = new CaseHandleCaseViewScaCaseHomeViewForm();
        CaseHandleCaseViewScaCaseHomeRs rs = new CaseHandleCaseViewScaCaseHomeRs();

        try {
            String caseId = rq.getCaseId();

            /** 案件資料 **/
            ScaCaseViewQueryDto dto = kgoScaApplyRepository.getScaCaseViewData(caseId);

            if (null != dto) {
                caseId = dto.getCaseId();
                String applicant = dto.getApplyUser();
                String caseName = dto.getCasesetName();
                String email = dto.getEmail();
                String linkType = LinkTypeEnum.getLinkTypeEnum(dto.getLinkType()).getLabel();
                String linkUrl = dto.getLinkUrl();
                String managerName = dto.getManagerName();
                String organName = dto.getApplyOrgan();
                String unitName = dto.getApplyUnit();
                String ownerOrgan = dto.getOwnerOrgan();
                String phone = dto.getPhone();
                String processClassification = CaseFlowTypeEnum.getCaseFlowTypeEnum(dto.getCaseFlowType()).getLabel();
                String roleClassify = dto.getRole();
                String organClassify = dto.getOrgan();
                String serviceClassify = dto.getService();
                String applyType = dto.getApplyType();
                String supervisor = dto.getApplyReviewer();
                List<CheckBox> checkBoxList = caseHandleServiceHelper.getCheckBoxListWithEnum(Arrays.asList(ApplyTypeEnum.values()));
                checkBoxList.forEach(c -> {
                    if (StringUtils.isNotEmpty(applyType)) {
                        if (applyType.contains(c.getValue())) {
                            c.setSelected(true);
                        }
                    }
                });

                /** ========= 處理歷程 ========= **/
                List<CaseHandleCaseViewCaseHistoryDataGrid> historyData = new LinkedList<>();
                List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(dto.getProcessId());
                historyDataDto.forEach(item -> {
                    CaseHandleCaseViewCaseHistoryDataGrid caseHandleCaseViewCaseHistoryDataGrid = new CaseHandleCaseViewCaseHistoryDataGrid();
                    caseHandleCaseViewCaseHistoryDataGrid.setStatus(item.getStatus());
                    caseHandleCaseViewCaseHistoryDataGrid.setContent(item.getContent());
                    caseHandleCaseViewCaseHistoryDataGrid.setDealTime(item.getDealTime());
                    caseHandleCaseViewCaseHistoryDataGrid.setTaker(item.getOfficer());
                    caseHandleCaseViewCaseHistoryDataGrid.setOrgan(item.getOrganName());
                    historyData.add(caseHandleCaseViewCaseHistoryDataGrid);
                });

                String image = null;

                if (dto.getProcessId() != null)
                    image = processTransaction.showProcessImg(dto.getProcessId());

                LOGGER.info("image === >> " + image);

                viewForm.setApplicant(KgoUtil.hideName(applicant));
                viewForm.setCaseId(caseId);
                viewForm.setCaseName(caseName);
                viewForm.setEmail(email);
                viewForm.setLinkType(linkType);
                viewForm.setLinkUrl(linkUrl);
                viewForm.setManagerName(managerName);
                viewForm.setOrganClassify(organClassify);
                viewForm.setOrganName(organName);
                viewForm.setOwnerOrgan(ownerOrgan);
                viewForm.setPhone(phone);
                viewForm.setProcessClassification(processClassification);
                viewForm.setRoleClassify(roleClassify);
                viewForm.setServiceCalssify(serviceClassify);
                viewForm.setSupervisor(supervisor);
                viewForm.setUnitName(unitName);
                viewForm.setCheckBoxList(checkBoxList);
                viewForm.setHistoryData(historyData);
                viewForm.setImage(image);
            }
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandleCaseViewScaCaseHome error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * get role list of user
     *
     * @param userId
     * @return
     */
    private List<String> getUserRoleList(String userId) {
        return kgoUserRoleRepository.findAllByIdUserId(userId).stream().map(l -> {
            return l.getId().getRoleId();
        }).collect(Collectors.toList());
    }

    /**
     * dto -> grid
     *
     * @param caseDtoList
     * @return
     */
    private List<CaseHandleCaseQueryDataGrid> getRsGridData(List<CaseMainQueryDto> caseDtoList, List<Map<String, Object>> processIdMap) {
        return caseDtoList.stream().map(l -> {
            // 取得 status 顯示字串
            String status = l.getStatus();
            if (status == null) {
                status = CaseMainStatusEnum.OTHERS.getValue();
            }
            String statusName = StringUtils.EMPTY;
            if (CaseMainStatusEnum.getCaseMainStatusEnum(status) == null) {
                if (ApplyCaseStatusEnum.getApplyCaseStatusEnum(l.getStatus()) != null) {
                    statusName = ApplyCaseStatusEnum.getApplyCaseStatusEnum(status).getLabel();
                }
            } else {
                CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(l.getStatus());
                if (null != caseMainStatusEnum) {
                    statusName = caseMainStatusEnum.getLabel();
                }
            }

            CaseHandleCaseQueryDataGrid grid = new CaseHandleCaseQueryDataGrid();
            grid.setApplyDate(DateUtil.dateToString(l.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            grid.setApplicant(KgoUtil.hideName(l.getApplyUser()));
            grid.setCaseId(l.getCaseId());
            grid.setCaseName(l.getCaseSetName());
            grid.setLimitDate(DateUtil.dateToString(l.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            grid.setStatusType(status);
            grid.setStatusName(statusName);
            grid.setOfficer(l.getCaseOfficer());
            grid.setType(l.getType());
            /** GEO 20211102 add 申請人登入方式 */
            String typeName = messageUtil.getMessage("kgo.login.type.unknow");
            if (StringUtils.isNotBlank(l.getApplyUserLoginType()))
                typeName = LoginAuthTokenType.getLoginAuthType(l.getApplyUserLoginType()).getTypeName();
            grid.setApplyUserLoginType(typeName);

            if (!CollectionUtils.isEmpty(processIdMap)) {
                Map<String, Object> processId = processIdMap.stream().filter(item -> item.get("processId").equals(l.getProcessId())).findAny().orElse(new HashMap<>());
                Task task = (Task) processId.get("task");
                if (null != task) {
                    grid.setTaskId(task.getId());
                }
            }
            String acceptSet = l.getAcceptSet();
            String caseSetId = l.getCaseSetId();
            ComboBox comboBox = new ComboBox();
            String organ = KgoUtil.getBackendLoginUser().getOrgan();
            if ("UNIT".equals(acceptSet)) {
                List<AcceptSetUnitQueryDto> acceptSetUnitQueryDtos = kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId);
                acceptSetUnitQueryDtos.stream().filter(itemDto -> {
                    if (StringUtils.isNotEmpty(organ)) {
                        return !organ.equals(itemDto.getOrgan());
                    } else {
                        return true;
                    }
                }).forEach(innerItem -> {
                    comboBox.add(innerItem.getOrganName(), innerItem.getOrgan(), false);
                });
            } else if ("AREA".equals(acceptSet)) {
                List<AcceptSetAreaQueryDto> acceptSetAreaQueryDtos = kgoCasesetAreaRepository.getAreaDataByCaseSetId(caseSetId);
                acceptSetAreaQueryDtos.stream().filter(itemDto -> {
                    if (StringUtils.isNotEmpty(organ)) {
                        return !organ.equals(itemDto.getOrgan());
                    } else {
                        return true;
                    }
                }).forEach(innerItem -> {
                    comboBox.add(innerItem.getOrganName(), innerItem.getOrgan(), false);
                });
            }
            grid.setComboBox(comboBox);
            return grid;
        }).collect(Collectors.toList());
    }

    /**
     * dto -> grid
     *
     * @param caseDtoList
     * @return
     */
    private List<CaseHandleCaseQueryDataGrid> getRsGridDataReviewed(List<CaseMainQueryReviewedDto> caseDtoList, List<Map<String, Object>> processIdMap) {
        Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.TS.getType());
        return caseDtoList.stream().map(l -> {
            // 取得 status 顯示字串
            String status = l.getStatus();
            if (status == null) {
                status = CaseMainStatusEnum.OTHERS.getValue();
            }
            String statusName = StringUtils.EMPTY;
            if (CaseMainStatusEnum.getCaseMainStatusEnum(status) == null) {
                if (ApplyCaseStatusEnum.getApplyCaseStatusEnum(l.getStatus()) != null) {
                    statusName = ApplyCaseStatusEnum.getApplyCaseStatusEnum(status).getLabel();
                }
            } else {
                CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(l.getStatus());
                if (null != caseMainStatusEnum) {
                    statusName = caseMainStatusEnum.getLabel();
                }
            }
            if ("SCA".equals(l.getType()) || "URA".equals(l.getType())) {
                ApplyCaseStatusEnum applyCaseStatusEnum = ApplyCaseStatusEnum.getApplyCaseStatusEnum(statusName);
                if (null != applyCaseStatusEnum) {
                    statusName = applyCaseStatusEnum.getValue();
                }
            }

            CaseHandleCaseQueryDataGrid grid = new CaseHandleCaseQueryDataGrid();
            grid.setApplyDate(DateUtil.dateToString(l.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            Timestamp fdate = l.getFdate();
            if (null != fdate) {
                LocalDateTime localDateTime = null;
                if (kgoParamSetOptional.isPresent()) {
                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                    String value = kgoParamSet.getValue();
                    String detailType = kgoParamSet.getDetailType();
                    LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                    }
                    if (localDateTime.isBefore(LocalDateTime.now())) {
                        grid.setApplicant(KgoUtil.hideName(l.getApplyUser()));
                    } else {
                        grid.setApplicant(l.getApplyUser());
                    }
                } else {
                    grid.setApplicant(l.getApplyUser());
                }
            } else {
                grid.setApplicant(l.getApplyUser());
            }
            grid.setApplicant(KgoUtil.hideName(l.getApplyUser()));
            grid.setCaseId(l.getCaseId());
            grid.setCaseName(l.getCaseSetName());
            grid.setLimitDate(DateUtil.dateToString(l.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            grid.setStatusType(status);
            grid.setStatusName(statusName);
            grid.setOfficer(l.getCaseOfficer());
            grid.setType(l.getType());
            /** GEO 20211102 add 申請人登入方式 */
            String typeName = messageUtil.getMessage("kgo.login.type.unknow");
            if (StringUtils.isNotBlank(l.getApplyUserLoginType()))
                typeName = LoginAuthTokenType.getLoginAuthType(l.getApplyUserLoginType()).getTypeName();
            grid.setApplyUserLoginType(typeName);

            if (!CollectionUtils.isEmpty(processIdMap)) {
                Map<String, Object> processId = processIdMap.stream().filter(item -> item.get("processId").equals(l.getProcessId())).findAny().orElse(new HashMap<>());
                Task task = (Task) processId.get("task");
                if (null != task) {
                    grid.setTaskId(task.getId());
                }
            }
            String acceptSet = l.getAcceptSet();
            String caseSetId = l.getCaseSetId();
            ComboBox comboBox = new ComboBox();
            if ("UNIT".equals(acceptSet)) {
                List<AcceptSetUnitQueryDto> acceptSetUnitQueryDtos = kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId);
                acceptSetUnitQueryDtos.forEach(innerItem -> {
                    comboBox.add(innerItem.getOrganName(), innerItem.getOrgan(), false);
                });
            } else if ("AREA".equals(acceptSet)) {
                List<AcceptSetAreaQueryDto> acceptSetAreaQueryDtos = kgoCasesetAreaRepository.getAreaDataByCaseSetId(caseSetId);
                acceptSetAreaQueryDtos.forEach(innerItem -> {
                    comboBox.add(innerItem.getOrganName(), innerItem.getOrgan(), false);
                });
            }
            grid.setComboBox(comboBox);
            return grid;
        }).collect(Collectors.toList());
    }

    /**
     * dto -> grid
     *
     * @param caseDtoList
     * @return
     */
    private List<CaseHandleCaseQueryDataGrid> getRsGridDataCaseView(List<CaseMainQueryCaseViewListDto> caseDtoList, List<Map<String, Object>> processIdMap) {
        Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.TS.getType());
        Optional<KgoParamSet> kgoParamSetOptionalAQ = kgoParamSetRepository.findById(ParamSetEnum.AQ.getType());
        return caseDtoList.stream().sorted(Comparator.comparing(CaseMainQueryCaseViewListDto::getCaseId).reversed()).map(l -> {
            // 取得 status 顯示字串
            String status = l.getStatus();
            if (status == null) {
                status = CaseMainStatusEnum.OTHERS.getValue();
            }
            String statusName = StringUtils.EMPTY;
            if (CaseMainStatusEnum.getCaseMainStatusEnum(status) == null) {
                if (ApplyCaseStatusEnum.getApplyCaseStatusEnum(l.getStatus()) != null) {
                    statusName = ApplyCaseStatusEnum.getApplyCaseStatusEnum(status).getLabel();
                }
            } else {
                CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(l.getStatus());
                if (null != caseMainStatusEnum) {
                    statusName = caseMainStatusEnum.getLabel();
                }
            }

            CaseHandleCaseQueryDataGrid grid = new CaseHandleCaseQueryDataGrid();
            Timestamp applyDate = l.getApplyDate();
            grid.setApplyDate(DateUtil.dateToString(applyDate, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            if (CaseFlowTypeEnum.A.getValue().equals(l.getCaseFlowType())) {
                if (CaseMainStatusEnum.A.getValue().equals(l.getStatus())) {
                    if (null != applyDate) {
                        LocalDateTime localDateTime = null;
                        if (kgoParamSetOptionalAQ.isPresent()) {
                            KgoParamSet kgoParamSet = kgoParamSetOptionalAQ.get();
                            String value = kgoParamSet.getValue();
                            String detailType = kgoParamSet.getDetailType();
                            LocalDateTime fDateLocalDateTime = applyDate.toLocalDateTime();
                            if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                            }
                            if (localDateTime.isBefore(LocalDateTime.now())) {
                                grid.setApplicant(KgoUtil.hideName(l.getApplyUser()));
                            } else {
                                grid.setApplicant(l.getApplyUser());
                            }
                        } else {
                            grid.setApplicant(l.getApplyUser());
                        }
                    } else {
                        grid.setApplicant(l.getApplyUser());
                    }
                } else {
                    grid.setApplicant(l.getApplyUser());
                }
            } else {
                if ((CaseMainStatusEnum.F.getValue().equals(l.getStatus()) || CaseMainStatusEnum.F3.getValue().equals(l.getStatus()) || CaseMainStatusEnum.J3.getValue().equals(l.getStatus())
                        || CaseMainStatusEnum.S3.getValue().equals(l.getStatus()))) {
                    Timestamp fdate = l.getFdate();
                    if (null != fdate) {
                        LocalDateTime localDateTime = null;
                        if (kgoParamSetOptional.isPresent()) {
                            KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                            String value = kgoParamSet.getValue();
                            String detailType = kgoParamSet.getDetailType();
                            LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
                            if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                            } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                            }
                            if (localDateTime.isBefore(LocalDateTime.now())) {
                                grid.setApplicant(KgoUtil.hideName(l.getApplyUser()));
                            } else {
                                grid.setApplicant(l.getApplyUser());
                            }
                        } else {
                            grid.setApplicant(l.getApplyUser());
                        }
                    } else {
                        grid.setApplicant(l.getApplyUser());
                    }
                } else {
                    grid.setApplicant(l.getApplyUser());
                }
            }
            grid.setCaseId(l.getCaseId());
            grid.setCaseName(l.getCaseSetName());
            grid.setLimitDate(DateUtil.dateToString(l.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            grid.setStatusType(status);
            grid.setStatusName(statusName);
            grid.setOfficer(l.getCaseOfficer());
            grid.setType(l.getType());
            /** GEO 20211102 add 申請人登入方式 */
            String typeName = messageUtil.getMessage("kgo.login.type.unknow");
            if (StringUtils.isNotBlank(l.getApplyUserLoginType()))
                typeName = LoginAuthTokenType.getLoginAuthType(l.getApplyUserLoginType()).getTypeName();
            grid.setApplyUserLoginType(typeName);
            grid.setCasesetId(l.getCaseSetId());
            grid.setVersion(l.getVersion());
            if (!CollectionUtils.isEmpty(processIdMap)) {
                Map<String, Object> processId = processIdMap.stream().filter(item -> item.get("processId").equals(l.getProcessId())).findAny().orElse(new HashMap<>());
                Task task = (Task) processId.get("task");
                if (null != task) {
                    grid.setTaskId(task.getId());
                }
            }
            String acceptSet = l.getAcceptSet();
            String caseSetId = l.getCaseSetId();
            ComboBox comboBox = new ComboBox();
            if ("UNIT".equals(acceptSet)) {
                List<AcceptSetUnitQueryDto> acceptSetUnitQueryDtos = kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId);
                acceptSetUnitQueryDtos.forEach(innerItem -> {
                    comboBox.add(innerItem.getOrganName(), innerItem.getOrgan(), false);
                });
            } else if ("AREA".equals(acceptSet)) {
                List<AcceptSetAreaQueryDto> acceptSetAreaQueryDtos = kgoCasesetAreaRepository.getAreaDataByCaseSetId(caseSetId);
                acceptSetAreaQueryDtos.forEach(innerItem -> {
                    comboBox.add(innerItem.getOrganName(), innerItem.getOrgan(), false);
                });
            }
            if (l instanceof SiteCaseMainQueryViewListDto) {
                grid.setAmount(((SiteCaseMainQueryViewListDto) l).getAmount());
                grid.setSiteName(((SiteCaseMainQueryViewListDto) l).getSiteName());
//                Integer version = kgoCaseDetailRepository.findVersionByCaseId(l.getCaseId());
//                grid.setVersion(version);
                //預約狀態
                RentStatusEnum rentStatus = RentStatusEnum.getRentStatusEnum(((SiteCaseMainQueryViewListDto) l).getRentStatus());
                grid.setRentStatus(rentStatus == null ? RentStatusEnum.PROC.getLabel() : rentStatus.getLabel());
                //判斷繳費狀態
                GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(l.getCaseId());
                if (paymentEntity != null) {
                    grid.setPaymentStatus(RentStatusEnum.getRentStatusEnum(paymentEntity.getPaymentStatus()).getLabel());
                    //繳費金額最終核定
                    grid.setAmount(paymentEntity.getPayAmount());
                } else {
                    if( RentStatusEnum.SUS.equals(rentStatus) ){
                        grid.setPaymentStatus(RentStatusEnum.FREE.getLabel());
                    }else{
                        grid.setPaymentStatus(RentStatusEnum.WAIT.getLabel());
                    }
                }
                Timestamp startTime = ((SiteCaseMainQueryViewListDto) l).getStartTime();
                grid.setStartTime(DateUtil.dateToString(startTime, DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
            }
            grid.setComboBox(comboBox);
            return grid;
        }).collect(Collectors.toList());
    }

    /**
     * dto -> grid
     *
     * @param caseDtoList
     * @return
     */
    private List<CaseHandleCaseQueryDataGrid> getPRRsGridData(List<PendingReviewCaseMainQueryDto> caseDtoList, List<Map<String, Object>> processIdMap) {
        return caseDtoList.stream().map(l -> {
            // 取得 status 顯示字串
            String status = l.getStatus();
            if (status == null) {
                status = CaseMainStatusEnum.OTHERS.getValue();
            }
            String statusName = StringUtils.EMPTY;
            if (CaseMainStatusEnum.getCaseMainStatusEnum(status) == null) {
                if (ApplyCaseStatusEnum.getApplyCaseStatusEnum(l.getStatus()) != null) {
                    statusName = ApplyCaseStatusEnum.getApplyCaseStatusEnum(status).getLabel();
                }
            } else {
                statusName = CaseMainStatusEnum.getCaseMainStatusEnum(l.getStatus()).getLabel();
            }

            CaseHandleCaseQueryDataGrid grid = new CaseHandleCaseQueryDataGrid();
            grid.setApplyDate(DateUtil.dateToString(l.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            grid.setApplicant(KgoUtil.hideName(l.getApplyUser()));
            grid.setCaseId(l.getCaseId());
            grid.setCaseName(l.getCaseSetName());
            grid.setLimitDate(DateUtil.dateToString(l.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
            grid.setStatusType(status);
            grid.setStatusName(statusName);
            grid.setType(l.getType());
            grid.setAcceptSet(l.getAcceptSet());
            /** GEO 20211102 add 申請人登入方式 */
            String typeName = messageUtil.getMessage("kgo.login.type.unknow");
            if (StringUtils.isNotBlank(l.getApplyUserLoginType()))
                typeName = LoginAuthTokenType.getLoginAuthType(l.getApplyUserLoginType()).getTypeName();
            grid.setApplyUserLoginType(typeName);

            Map<String, Object> processId = processIdMap.stream().filter(item -> item.get("processId").equals(l.getProcessId())).findAny().orElse(new HashMap<>());
            Task task = (Task) processId.get("task");
            if (null != task) {
                grid.setTaskId(task.getId());
                grid.setUraStage(task.getName());
            }
            return grid;
        }).collect(Collectors.toList());
    }

    /**
     * [待簽收] 服務申辦流程 案件 - UnitA
     *
     * 畫面初始
     *
     * @return
     */
//	private List<CaseMainQueryDto> pendingSignSAQuery(List<String> processIds) {
//		return pendingSignSAQuery(processIds, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
//				StringUtils.EMPTY, StringUtils.EMPTY);
//	}

    /**
     * [待簽收] 查詢
     * <p>
     * 查詢
     *
     * @param userName
     * @param dateStart
     * @param dateEnd
     * @param caseSetName
     * @param caseId
     * @return
     */
    private List<CaseMainQueryDto> pendingSignSAQuery(List<String> processIds, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {
        return kgoCaseMainRepository.pendingSignSAQuery(processIds, userName, dateStart, dateEnd, caseSetName, caseId);
    }

    /**
     * [已審核] 服務申辦流程 案件 - UnitU
     * <p>
     * 畫面初始
     *
     * @param reviewer
     * @return
     */
//    private List<CaseMainQueryDto> reviewedSAQueryWithUnitU(String reviewer) {
//        return reviewedSAQueryWithUnitU(reviewer, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
//                StringUtils.EMPTY, StringUtils.EMPTY);
//    }

    /**
     * [已審核] 服務申辦流程 案件 - UnitU
     * <p>
     * 查詢
     *
     * @param reviewer
     * @param userName
     * @param dateStart
     * @param dateEnd
     * @param caseSetName
     * @param caseId
     * @return
     */
//    private List<CaseMainQueryDto> reviewedSAQueryWithUnitU(String reviewer, String userName, String dateStart,
//                                                            String dateEnd, String caseSetName, String caseId) {
//        return kgoCaseMainRepository.reviewedSAQueryWithUnitU(reviewer, CaseMainStatusEnum.F3.getValue(), userName,
//                dateStart, dateEnd, caseSetName, caseId);
//    }

    /**
     * [已審核] 服務案件新增、系統權限申請 案件 - UnitM
     * <p>
     * 畫面初始
     *
     * @param reviewer
     * @return
     */
    private List<CaseMainQueryDto> reviewedUraScaQueryWithUnitM(String reviewer) {
        return reviewedUraScaQueryWithUnitM(reviewer, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }

    /**
     * [已審核] 服務案件新增、系統權限申請 案件 - UnitM
     * <p>
     * 查詢
     *
     * @param reviewer
     * @param userName
     * @param dateStart
     * @param dateEnd
     * @param caseSetName
     * @param caseId
     * @return
     */
    private List<CaseMainQueryDto> reviewedUraScaQueryWithUnitM(String reviewer, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {
        return kgoCaseMainRepository.reviewedUraScaQueryWithUnitM(reviewer, userName, dateStart, dateEnd, caseSetName, caseId);
    }

    private void sendMailAndBroadcast(String caseSetId, String caseId) {
        Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
        try { // send mail
            List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "Email");
            Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
            if (max.isPresent()) {
                sendEmailApplyCase(kgoCaseset.get(), max.get().getColumnValue());
            }
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
        }
        // try {// broadcast
        // List<KgoCaseDetail> kgoCaseDetailsID =
        // kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "ID");
        // Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream()
        // .max(Comparator.comparing(item -> item.getId().getVersion()));
        // if (maxID.isPresent()) {
        // sendEmailApplyCase(kgoCaseset.get(), maxID.get().getColumnValue());
        // pushNotificationApplyCase(kgoCaseset.get(), maxID.get().getColumnValue());
        // }
        // } catch (Exception e) {
        // LOGGER.error(KgoBackEndApiError.FAIL_TO_NOTIFY.getErrorMsgKey());
        // }

    }

    private void sendEmailApplyCase(KgoCaseset kgoCaseset, String email) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("casesetName", kgoCaseset.getCaseSetName());
        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.frontend.case.process.search.mail.title"), model, "correctCase.html");
    }

    private void pushNotificationApplyCase(KgoCaseset kgoCaseset, String id) {
        List<PushMsgDto> pushDataList = new ArrayList<>();
        PushMsgDto pushMsg = new PushMsgDto();
        pushMsg.setCustNum(id);
        pushMsg.setMsgTitle(messageUtil.getMessage("kgo.frontend.case.process.search.notification.msgTitle"));
        pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.frontend.case.process.search.notification.msgBody"), kgoCaseset.getCaseSetName()));
        pushMsg.setClickDetail(String.format(messageUtil.getMessage("kgo.frontend.case.process.search.notification.clickDetail"), kgoCaseset.getCaseSetName()));
        pushDataList.add(pushMsg);
        this.pushService.pushMessage(pushDataList, kgoCaseset.getOwnerOrgan());
    }

    /**
     * 後台案件處理-待審核匣-初始畫面
     *
     * @return
     */
    @Override
    public CaseHandlePendingReviewHomeRs caseHandlePendingReviewHome() {
        CaseHandlePendingReviewHomeViewForm viewForm = new CaseHandlePendingReviewHomeViewForm();
        CaseHandlePendingReviewHomeRs rs = new CaseHandlePendingReviewHomeRs();
        // 從 session 抓取 ogranId, userId
        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        String ogranId = backendLoginUser.getOrgan();
        String loginUserId = backendLoginUser.getUserId();
        try {
            List<Map<String, Object>> processIdMap = activitiService.getPendingCaseProcessIds(ogranId, loginUserId, backendLoginUser,
                    Arrays.asList(ActivitiTaskNameEnum.CF.getLabel(), ActivitiTaskNameEnum.CO.getLabel()));
            List<String> processIds = processIdMap.stream().map(item -> (String) item.get("processId")).collect(Collectors.toList());
            List<CaseHandleCaseQueryDataGrid> saGrid = new ArrayList<>();
            if (!CollectionUtils.isEmpty(processIds)) {
                List<PendingSignCaseQueryDto> queryResult = kgoCaseMainRepository.getPendingReviewSACase(processIds);
                queryResult.forEach(item -> {
                    String status = item.getStatus();
                    if (status.equalsIgnoreCase(CaseMainStatusEnum.P3.getValue())) {
                        CaseHandleCaseQueryDataGrid dg = new CaseHandleCaseQueryDataGrid();
                        dg.setCaseId(item.getCaseId());
                        dg.setCaseName(item.getCaseSetName());
                        dg.setApplyDate(DateUtil.dateToString(item.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                        dg.setLimitDate(DateUtil.dateToString(item.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                        dg.setApplicant(KgoUtil.hideName(item.getApplyUserName()));
                        String statusType = item.getStatus();
                        dg.setStatusType(statusType);
                        dg.setStatusName(CaseMainStatusEnum.getCaseMainStatusEnum(statusType).getLabel());
                        dg.setAcceptSet(item.getAcceptSet());
                        Map<String, Object> processId = processIdMap.stream().filter(innerItem -> innerItem.get("processId").equals(item.getProcessId())).findAny().orElse(new HashMap<>());
                        Task task = (Task) processId.get("task");
                        if (null != task) {
                            dg.setTaskId(task.getId());
                        }
                        saGrid.add(dg);
                    }
                });
            }
            // sort desc
//			saGrid.sort(
//					Collections.reverseOrder((entity1, entity2) -> entity1.getUpdateTime().compareTo(entity2.getUpdateTime())));
            viewForm.setGrid(saGrid);
            rs.setData(viewForm);
            // SCA URA TODO

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandlePendingReviewHome error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-案件查詢
     *
     * @return
     */
    @Override
    public CaseHandlePendingReviewQueryRs caseHandlePendingReviewQuery(CaseHandlePendingReviewQueryRq rq) {
        CaseHandlePendingReviewQueryViewForm viewForm = new CaseHandlePendingReviewQueryViewForm();
        CaseHandlePendingReviewQueryRs rs = new CaseHandlePendingReviewQueryRs();
        try {
            // 從 session 抓取 unitId, userId
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String organId = backendLoginUser.getOrgan();
            String loginUserId = backendLoginUser.getUserId();
            String caseId = rq.getCaseId();
            String applyUserName = rq.getApplicant();
            String caseSetName = rq.getCaseName();
            String[] applyDate = rq.getApplyDate();

            String dateStart = ArrayUtils.isEmpty(applyDate) ? StringUtils.EMPTY : applyDate[0];
            String dateEnd = ArrayUtils.isEmpty(applyDate) ? StringUtils.EMPTY : applyDate[1];

            /** GEO 20211026 add */
            //getCaseHandleCaseQueryDataGrid => 搜尋這個登入者的待處理案件
            List<CaseHandleCaseQueryDataGrid> gridList = getCaseHandleCaseQueryDataGrid(organId, loginUserId,
                    backendLoginUser.getRoles(), applyUserName, dateStart, dateEnd, caseSetName, caseId);
            if (gridList != null && gridList.size() > 0) {
                for (CaseHandleCaseQueryDataGrid g : gridList) {
                    g.setAgent(false);
                }//for (CaseHandleCaseQueryDataGrid g : gridList)
            }//if (gridList != null && gridList.size() > 0)
            //getPrincipalList => 搜尋這個登入者的所代理的人
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent g : principalList) {//被代理人的處理案件
                    List<CaseHandleCaseQueryDataGrid> principalGridList = getCaseHandleCaseQueryDataGrid(organId, g.getPrincipalUserId(),
                            getUserRoleList(g.getPrincipalUserId()), applyUserName, dateStart, dateEnd, caseSetName, caseId);
                    principalGridList.size();
                    if (principalGridList != null && principalGridList.size() > 0) {
                        for (CaseHandleCaseQueryDataGrid dataGrid : principalGridList) {
                            dataGrid.setAgent(true);
                        }//for (CaseHandleCaseQueryDataGrid dataGrid : principalGridList)
                    }//if (principalGridList != null && principalGridList.size() > 0)
                    gridList.addAll(principalGridList);
                }
            } //if (principalList
            // sort
//			gridList.sort(
//					Collections.reverseOrder((entity1, entity2) -> entity1.getUpdateTime().compareTo(entity2.getUpdateTime())));
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandlePendingSignQuery error " + e.getMessage(), e);
        }

        return rs;
    } //caseHandlePendingReviewQuery

    /**
     * GEO 20211026 add
     */
    private List<CaseHandleCaseQueryDataGrid> getCaseHandleCaseQueryDataGrid(String organId, String userId,
                                                                             List<String> roles, String applyUserName, String dateStart, String dateEnd, String caseSetName, String caseId) {
        List<Map<String, Object>> processIdMap = activitiService.getPendingCaseProcessIds(
                organId, userId, roles, Arrays.asList(ActivitiTaskNameEnum.DG.getLabel(), ActivitiTaskNameEnum.CHG.getLabel()));
//        LOGGER.info("CaseHandleServiceImpl caseHandlePendingReviewQuery processIdMap.size(): "+processIdMap.size());
        List<String> processIds = processIdMap.stream().map(item -> (String) item.get("processId")).collect(Collectors.toList());
        List<PendingReviewCaseMainQueryDto> caseDtoList = new LinkedList<>();
        //processIds.subList(fromIndex, toIndex)
//        LOGGER.info("CaseHandleServiceImpl caseHandlePendingReviewQuery processIds.size(): "+processIds.size());
        if (!CollectionUtils.isEmpty(processIds)) {
            if (processIds.size() > 500) {
                for (int i = 0; i < processIds.size(); i = i + 499) {
                    int indEnd = i + 499 < processIds.size() ? i + 499 : processIds.size();
                    List<PendingReviewCaseMainQueryDto> pendingReviewCaseMainQueryDtos = kgoCaseMainRepository.pendingReviewSAQuery(processIds.subList(i, indEnd), applyUserName, dateStart, dateEnd, caseSetName, caseId);
                    caseDtoList.addAll(pendingReviewCaseMainQueryDtos);
                }
            } else {
                List<PendingReviewCaseMainQueryDto> pendingReviewCaseMainQueryDtos = kgoCaseMainRepository.pendingReviewSAQuery(processIds, applyUserName, dateStart, dateEnd, caseSetName, caseId);
                caseDtoList.addAll(pendingReviewCaseMainQueryDtos);
            }
        }
        List<CaseHandleCaseQueryDataGrid> gridList = getPRRsGridData(caseDtoList, processIdMap);
        return gridList;
    } //getCaseHandleCaseQueryDataGrid

    /**
     * [待審核] 查詢
     *
     * @param processIds
     * @param userName
     * @param dateStart
     * @param dateEnd
     * @param caseSetName
     * @param caseId
     * @return
     */
//    private List<PendingReviewCaseMainQueryDto> pendingReviewSAQuery(List<String> processIds, String userName,
//                                                                     String dateStart, String dateEnd, String caseSetName, String caseId) {
//        return kgoCaseMainRepository.pendingReviewSAQuery(processIds, userName, dateStart, dateEnd, caseSetName,
//                caseId);
//    }

    /**
     * 後台案件處理-待審核匣-取消簽收 初始畫面
     *
     * @return
     */
    @Override
    public CaseHandlePendingReviewCancelAcceptHomeRs caseHandlePendingReviewCancelAcceptHome(CaseHandlePendingReviewCancelAcceptHomeRq rq) {
        CaseHandlePendingReviewCancelAcceptHomeRs rs = new CaseHandlePendingReviewCancelAcceptHomeRs();
        CaseHandlePendingReviewCancelAcceptHomeViewForm viewForm = new CaseHandlePendingReviewCancelAcceptHomeViewForm();
        try {
            // 類別下拉式選單
            String type = rq.getType();
            ComboBox organCaseOfficerComboBox = null;
            if (type.equalsIgnoreCase(OrganCaseOfficerEnum.ORGAN.getValue())) {
                organCaseOfficerComboBox = organUnitManagementServiceHelper.getOrganCaseOfficerComboBox(OrganCaseOfficerEnum.ORGAN.getValue());
            } else {
                organCaseOfficerComboBox = organUnitManagementServiceHelper.getOrganCaseOfficerComboBox(OrganCaseOfficerEnum.CASEOFFICER.getValue());
            }
            viewForm.setOrganCaseOfficerComboBox(organCaseOfficerComboBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandlePendingSignQuery error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-取消簽收
     *
     * @return
     */
    @Override
    public List<CaseHandlePendingReviewCancelAcceptRs> caseHandlePendingReviewCancelAccept(List<CaseHandlePendingReviewCancelAcceptRq> rq) {
        KgoApiException error = null;
        OperationApiMemo memo = null;

        List<CaseHandlePendingReviewCancelAcceptRs> rsList = new ArrayList<>();
        rq.forEach(item -> {
            CaseHandlePendingReviewCancelAcceptRs rs = new CaseHandlePendingReviewCancelAcceptRs();
            String caseId = item.getCaseId();
            KgoCaseMain queryMain = kgoCaseMainRepository.findByCaseId(caseId);
            rs.setCaseId(caseId);

            // add comment
            String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
            String loginUserName = KgoUtil.getBackendLoginUser().getName();
            String caseStatus = CaseStatusEnum.CSIGN.getLabel();// 案件狀態
            String comment = String.format("由%s%s", loginUserName, CaseStatusEnum.CSIGN.getLabel()); // 內容
            String loginUserOrganId = KgoUtil.getBackendLoginUser().getOrgan();
            KgoOrgan organItem = kgoOrganRepository.findByOrganId(loginUserOrganId);
            String organName = organItem.getOrganName(); // 承辦的機關名稱

            /** GEO 20211026 add */
            GeoKgoAgent agentData = null;
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent agent : principalList) {
                    List<Task> taskList = getPrincipalTaskList(agent, item.getTaskId());
                    if (taskList != null && taskList.size() > 0) agentData = agent;
                }
            } //if (principalList != null &&
            if (agentData != null) {
                KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
                comment = String.format("由%s代理%s%s", loginUserName, user.getName(), CaseStatusEnum.CSIGN.getLabel());
                agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
                geoKgoAgentRepository.save(agentData);
            }
            /** GEO 20211101 add 增加簽核意見*/
            Comment commentEntity = activitiService.addCommentByTaskId(loginUserId, item.getTaskId(), caseStatus, comment, organName, loginUserName, CaseStatusEnum.CSIGN);
            geoTaskCommentService.saveTaskComment(commentEntity.getId(), item.getTaskComment(), loginUserOrganId, loginUserId);
            /** GEO 20211109 add 機關審核表單*/
            if (item.getColumnData() != null && item.getColumnData().size() > 0) {
                try {
                    saveKgoCaseDetail(item.getCaseId(), item.getColumnData(), commentEntity.getId());
                } catch (ParseException e) {
                    throw new KgoApiException("caseHandlePendingReviewCancelAccept error " + e.getMessage(), e);
                }
            }

            // return flow
            String rejectTo = item.getRejectTo();

            // == modify: 2021/05/05 案件服務受理機關設 髒資料卡控 ==
            // 機關分文 & 區機關 不會設定caseoffer, 除非caseSet 從承辦 被改成 機關分文 || 區機關
            if ("UNIT".equals(rejectTo) || "AREA".equals(rejectTo)) {

                // 此案件服務已從機關分文或區機關被改成 承辦
                if (StringUtils.isNotBlank(queryMain.getCaseOfficer())) {
//					error = new KgoApiException();
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.CASESET_ACCECPT_SETTING_HAS_BEEN_CHANGE, new Object[]{"承辦", "機關分文或區機關"}));
                }

                queryMain.setStatus(CaseMainStatusEnum.A3.getValue());
                activitiService.previousFlowByTaskId(item.getTaskId(), "1");

            } else if ("OFFICER".equals(rejectTo)) {
                // 此案件服務已從承辦被改成 機關分文或區機關
                if (StringUtils.isBlank(queryMain.getCaseOfficer())) {
//					error = new KgoApiException();
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.CASESET_ACCECPT_SETTING_HAS_BEEN_CHANGE, new Object[]{"機關分文或區機關", "承辦"}));
                }

                queryMain.setStatus(CaseMainStatusEnum.W3.getValue());
                activitiService.previousFlowByTaskId(item.getTaskId(), "2");
            }
            // == modify: 2021/05/05 多髒資料卡控 ==

            rsList.add(rs);

            /** == modify: 2021/05/05 "承辦"類型的才須計送信件 且謹寄給當初設定的人員 ， 因服務人員設定會隨時被異動 == */
            String casesetName = StringUtils.EMPTY;
            if ("OFFICER".equals(rejectTo)) {
                Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(queryMain.getCaseSetId());
                if (kgoCaseset.isPresent()) {
                    casesetName = kgoCaseset.get().getCaseSetName();
                }

                Task task = taskService.createTaskQuery().taskId(caseId).singleResult();

                String assigneesStr = task.getAssignee();

                List<String> caseOffers = new ArrayList<>();
                // 多個承辦
                if (StringUtils.contains(assigneesStr, ",")) {
                    caseOffers = Arrays.asList(StringUtils.split(assigneesStr, ","));

                    List<KgoUser> kgoUsers = kgoUserRepository.findAllById(caseOffers);
                    if (kgoUsers != null && !kgoUsers.isEmpty()) {
                        for (KgoUser k : kgoUsers) {
                            if (k != null && StringUtils.isNotBlank(k.getEmail())) {
                                try {
                                    if (kgoCaseset.isPresent()) {
                                        // 寄信
                                        sendEmailReviewCancelAccept(caseId, casesetName, k.getEmail());
                                    }
                                } catch (Exception e) {
                                    LOGGER.error("Send review cancel accept email error, CaseId is {}, UserId is {}", e, caseId, k.getUserId());
                                }
                            }
                        }
                    }
                    // 單一承辦
                } else {
                    Optional<KgoUser> kgoUserOp = kgoUserRepository.findById(assigneesStr);
                    if (kgoUserOp.isPresent()) {
                        KgoUser k = kgoUserOp.get();
                        try {
                            sendEmailReviewCancelAccept(caseId, casesetName, k.getEmail());
                        } catch (Exception e) {
                            LOGGER.error("Send review cancel accept email error, CaseId is {}, UserId is {}", e, caseId, k.getUserId());
                        }
                    }
                }
                /** == modify: 2021/05/05 "承辦"類型的才須計送信件 且謹寄給當初設定的人員 ， 因服務人員設定會隨時被異動 == */
            }
        });
        try {
            // 後台、、取消簽收
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Creview);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingReviewCancelAccept error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */

            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("取消簽收，案件編號", rq.get(0).getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rsList;
    }

    /**
     * [已審核匣] 查詢
     *
     * @param processIds
     * @param userName
     * @param dateStart
     * @param dateEnd
     * @param caseSetName
     * @param caseId
     * @return
     */
//	private List<CaseMainQueryDto> reviewedSAQuery(List<String> processIds, String userName, String dateStart,
//			String dateEnd, String caseSetName, String caseId) {
//		List<CaseMainQueryDto> dto = kgoCaseMainRepository.reviewedSAQuery(processIds, userName, dateStart, dateEnd,
//				caseSetName, caseId);
//		return dto;
//	}

    /**
     * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleSignSaCaseHomeRs caseHandleSignSaCaseHome(CaseHandleSignSaCaseHomeRq rq) {
        CaseHandleCaseViewSaCaseHomeRq subrq = new CaseHandleCaseViewSaCaseHomeRq();
        subrq.setCaseId(rq.getCaseId());
        subrq.setTaskId(rq.getTaskId());
        CaseHandleCaseViewSaCaseHomeRs subrs = caseHandleCaseViewSaCaseHome(subrq);
        CaseHandleCaseViewSaCaseHomeViewForm data = subrs.getData();
        CaseHandleSignSaCaseHomeViewForm viewForm = new CaseHandleSignSaCaseHomeViewForm();
        viewForm.setApplyData(data.getApplyData());
        viewForm.setApplyDate(data.getApplyDate());
        viewForm.setCaseId(data.getCaseId());
        viewForm.setCaseName(data.getCaseName());
        viewForm.setDownloadData(data.getDownloadData());
        viewForm.setHistoryData(data.getHistoryData());
        viewForm.setLimitDay(data.getLimitDay());
        viewForm.setLimitTime(data.getLimitTime());
        viewForm.setStatus(data.getStatus());
        CaseHandleSignSaCaseHomeRs rs = new CaseHandleSignSaCaseHomeRs();
        rs.setData(viewForm);
        rs.setMsg(subrs.getMsg());
        rs.setRtnCode(subrs.getRtnCode());
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-簽核-新增參考文件及備註
     *
     * @param rq
     * @return
     */
    @Override
    public void caseHandleAddNote(CaseHandleAddNoteRq rq) {
        KgoCaseFwd fwd = new KgoCaseFwd();
        KgoCaseFwdPK fwdPK = new KgoCaseFwdPK();
        fwdPK.setCaseId(rq.getCaseId());
        fwd.setId(fwdPK);
        fwd.setTitle(rq.getTitle());
        fwd.setFileName(rq.getFileName());
        kgoCaseFwdRepository.save(fwd);
    }

    /**
     * 後台案件處理-待審核匣-簽核-刪除參考文件及備註
     *
     * @param rq
     * @return
     */
    @Override
    public void caseHandleDeleteNote(CaseHandleDelNoteRq rq) {
//        KgoCaseFwd fwd = new KgoCaseFwd();
//        KgoCaseFwdPK fwdPK = new KgoCaseFwdPK();
//        fwdPK.setCaseId(rq.getCaseId());
//        fwdPK.setSeq(rq.getSeq());
//        fwd.setId(fwdPK);
//        fwd.setTitle(rq.getTitle());
//        fwd.setFileName(rq.getFileName());
//        kgoCaseFwdRepository.delete(fwd);
    }

    /**
     * GEO 20211109 add 機關審核表單
     */
    public List<CaseHandleCaseViewCaseHistoryDataGrid> castToHistoryDataGrid(List<HistoryDataDto> historyDataDto, List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid> organApplyData) {
        List<CaseHandleCaseViewCaseHistoryDataGrid> histories = new ArrayList<>();
        historyDataDto.forEach(item -> {
            CaseHandleCaseViewCaseHistoryDataGrid grid = new CaseHandleCaseViewCaseHistoryDataGrid();
            grid.setStatus(item.getStatus());
            grid.setOrgan(item.getOrganName());
            grid.setContent(item.getContent());
            grid.setTaker(item.getOfficer());
            grid.setDealTime(item.getDealTime());
            /** GEO 20211101 add 增加簽核意見*/
            GeoKgoTaskComment taskComment = geoKgoTaskCommentRepository.findByCommentId(item.getCommentId());
            String commentText = StringUtils.EMPTY;
            if (taskComment != null && StringUtils.isNotBlank(taskComment.getCommentText()))
                commentText = taskComment.getCommentText();
            grid.setTaskComment(commentText);

            /** GEO 20211109 add 機關審核表單 */
            if (organApplyData != null) {
                List<CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid> list = new ArrayList<>();
                for (CaseHandleCaseViewSaCaseOrganFormApplyDataDataGrid dataDataGrid : organApplyData) {
                    LOGGER.info("castToHistoryDataGrid dataDataGrid.getCommentId() / item.getCommentId() :" + dataDataGrid.getCommentId() + "/" + item.getCommentId());
                    if (dataDataGrid.getCommentId() != null && dataDataGrid.getCommentId().equals(item.getCommentId())) {
                        list.add(dataDataGrid);
                    }
                }
                grid.setOrganFromDataGrid(list);
            } // if (organApplyData != null)

            histories.add(grid);
        });
        // sort by dealTime(DESC)
        histories.sort(Collections.reverseOrder((entity1, entity2) -> entity1.getDealTime().compareTo(entity2.getDealTime())));
        return histories;
    } //castToHistoryDataGrid

    /**
     * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandlePendingReviewCorrectHomeRs caseHandleSignSaCaseCorrectHome(CaseHandlePendingReviewCorrectHomeRq rq) {
        List<KgoCorrectRecord> kgoCorrectRecords = kgoCorrectRecordRepository.findByCaseId(rq.getCaseId());
        ArrayList<CaseHandlePendingReviewCorrectHomeViewRecordForm> forms = new ArrayList<>();
        kgoCorrectRecords.forEach(item -> {
            CaseHandlePendingReviewCorrectHomeViewRecordForm caseHandlePendingReviewCorrectHomeViewRecordForm = new CaseHandlePendingReviewCorrectHomeViewRecordForm();
            caseHandlePendingReviewCorrectHomeViewRecordForm.setHandler(item.getHandler());
            caseHandlePendingReviewCorrectHomeViewRecordForm.setHandleTime(DateUtil.timestampToString(item.getHandleTime(), DateUtil.PATTEN_FULL_TIME_SLASH));
            caseHandlePendingReviewCorrectHomeViewRecordForm.setMemo(item.getMemo());
            caseHandlePendingReviewCorrectHomeViewRecordForm.setStatusName(CaseMainStateEnum.getCaseMainStateEnum(item.getStatus()).getLabel());
            caseHandlePendingReviewCorrectHomeViewRecordForm.setStatus(item.getStatus());
            caseHandlePendingReviewCorrectHomeViewRecordForm.setHandlerName(item.getHandler());
            forms.add(caseHandlePendingReviewCorrectHomeViewRecordForm);
        });
        CaseHandleCaseViewSaCaseHomeRq subrq = new CaseHandleCaseViewSaCaseHomeRq();
        subrq.setCaseId(rq.getCaseId());
        subrq.setTaskId(rq.getTaskId());
        CaseHandleCaseViewSaCaseHomeRs subrs = caseHandleCaseViewSaCaseHome(subrq);
        CaseHandleCaseViewSaCaseHomeViewForm data = subrs.getData();
        CaseHandlePendingReviewCorrectHomeViewForm viewForm = new CaseHandlePendingReviewCorrectHomeViewForm();
        viewForm.setApplyData(data.getApplyData());
        viewForm.setApplyDate(data.getApplyDate());
        viewForm.setCaseId(data.getCaseId());
        viewForm.setCaseName(data.getCaseName());
        viewForm.setDownloadData(data.getDownloadData());
        viewForm.setHistoryData(data.getHistoryData());
        viewForm.setLimitDay(data.getLimitDay());
        viewForm.setLimitTime(data.getLimitTime());
        viewForm.setCorrectDeadline(data.getCorrectDeadline());
        viewForm.setCorrectDate(data.getCorrectDate());
        viewForm.setStatus(data.getStatus());
        viewForm.setState(data.getState());
        viewForm.setRecordForms(forms);
        CaseHandlePendingReviewCorrectHomeRs rs = new CaseHandlePendingReviewCorrectHomeRs();
        rs.setData(viewForm);
        rs.setMsg(subrs.getMsg());
        rs.setRtnCode(subrs.getRtnCode());
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-補正通知
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandlePendingReviewCorrectUpdateRs caseHandleSignSaCaseCorrectUpdate(CaseHandlePendingReviewCorrectUpdateRq rq) {
        CaseHandlePendingReviewCorrectUpdateRs caseHandlePendingReviewCorrectUpdateRs = new CaseHandlePendingReviewCorrectUpdateRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 後台、、補正
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Creview);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String userId = KgoUtil.getBackendLoginUser().getUserId();
            KgoCaseMain kgoCaseMain = kgoCaseMainRepository.getOne(rq.getCaseId());
            Integer correctDeadLine = rq.getCorrectDeadLine();
            kgoCaseMain.setCorrectDeadline(correctDeadLine);
            Timestamp correctDate = getCorrectDate(correctDeadLine);
            kgoCaseMain.setCorrectDate(correctDate);
            kgoCaseMain.setState("2");
            kgoCaseMain.setStatus(CaseMainStatusEnum.C3.getValue());
            kgoCaseMain.setUpdateUser(userId);
            kgoCaseMain.setUpdateTime(timestamp);
            kgoCaseMainRepository.save(kgoCaseMain);

            List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseId(rq.getCaseId());
            kgoCaseDetails.forEach(item -> {
                item.setIsCorrect(false);
            });
            List<CaseHandlePendingReviewCorrectUpdateDataGrid> caseHandlePendingReviewCorrectUpdateDataGridList = rq.getCaseHandlePendingReviewCorrectUpdateDataGridList();
            List<String> kgoCasesetColumnList = new ArrayList<>();
            List<Map<String, Object>> correctList = new ArrayList<>();
            for (CaseHandlePendingReviewCorrectUpdateDataGrid caseHandlePendingReviewCorrectUpdateDataGrid : caseHandlePendingReviewCorrectUpdateDataGridList) {
                if ("1".equals(caseHandlePendingReviewCorrectUpdateDataGrid.getIsCorrect())) {
                    kgoCaseDetails.forEach(item -> {
                        if (item.getId().getColumnId().equals(caseHandlePendingReviewCorrectUpdateDataGrid.getColumnId())) {
                            item.setIsCorrect(true);
                            item.setCorrectMemo(caseHandlePendingReviewCorrectUpdateDataGrid.getCorrectMemo());
                            KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
                            kgoCasesetColumnPK.setColumnId(item.getId().getColumnId());
                            kgoCasesetColumnPK.setCaseSetId(kgoCaseMain.getCaseSetId());
                            kgoCasesetColumnPK.setVersion(item.getId().getVersion());
                            Optional<KgoCasesetColumn> kgoCasesetColumnOptional = kgoCasesetColumnRepository.findById(kgoCasesetColumnPK);
                            Map<String, Object> correctMap = new HashMap<>();
                            if (kgoCasesetColumnOptional.isPresent()) {
                                String columnName = kgoCasesetColumnOptional.get().getColumnName();
                                if (!kgoCasesetColumnList.stream().anyMatch(column -> column.equals(columnName))) {
                                    kgoCasesetColumnList.add(columnName);
                                    correctMap.put("columnName", columnName);
                                    correctMap.put("columnMemo", caseHandlePendingReviewCorrectUpdateDataGrid.getCorrectMemo());
                                    correctList.add(correctMap);
                                }
                            }
                        }
                    });
                }
            }
            kgoCaseDetailRepository.saveAllBatch(kgoCaseDetails);
            KgoCorrectRecord kgoCorrectRecord = new KgoCorrectRecord();
            kgoCorrectRecord.setStatus(CaseMainStateEnum.P.getValue());
            KgoUser kgoUser = kgoUserRepository.getOne(userId);
            String collect = kgoCasesetColumnList.stream().collect(Collectors.joining(","));
            kgoCorrectRecord.setMemo(String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.memo"), kgoUser.getName(), collect));
            kgoCorrectRecord.setHandleTime(timestamp);
            kgoCorrectRecord.setHandler(kgoUser.getName());
            kgoCorrectRecord.setCaseId(rq.getCaseId());
            kgoCorrectRecordRepository.save(kgoCorrectRecord);
            String organ = kgoUser.getOrgan();
            KgoOrgan kgoOrgan = kgoOrganRepository.getOne(organ);
            String organName = null;
            if (null != kgoOrgan) {
                organName = kgoOrgan.getOrganName();
            }

            // modify 2021.01.22 取第一筆task來寫備註即可
//			activitiService.addComment(userId, kgoCaseMain.getProcessId(), CaseMainStatusEnum.C3.getLabel(), messageUtil.getMessage("kgo.backend.case.handle.correct.caseComment"), organName,
//			kgoUser.getName(), CaseStatusEnum.CO);

            // 增加流程備註
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(kgoCaseMain.getProcessId()).list();
            // 正常應該會取到至少一筆，取第一筆task來寫備註即可(請做好錯誤判斷，如果取不到，表示流程已經結束了)
            if (!CollectionUtils.isEmpty(taskList)) {
                activitiService.addCommentByTaskId(userId, taskList.get(0).getId(), CaseMainStatusEnum.C3.getLabel(), messageUtil.getMessage("kgo.backend.case.handle.correct.caseComment"), organName,
                        kgoUser.getName(), CaseStatusEnum.CO);
            }

            /** === 2020.12.15 補正作業 不用跑下個流程 === */
            String caseId = kgoCaseMain.getCaseId();
            List<KgoCaseMainResendFlow> resendFlowList = kgoCaseMainResendFlowRepository.findByCaseId(caseId);
            Optional<KgoCaseset> casesetOpt = kgoCasesetRepository.findById(kgoCaseMain.getCaseSetId());
            KgoCaseset kgoCaseset = casesetOpt.get();
            // 是否為動態流程
            boolean isDynamic = false;
            if (StringUtils.isNotBlank(kgoCaseset.getFlowId()) || !CollectionUtils.isEmpty(resendFlowList)) {
                isDynamic = true;
            }

            // 動態流程 不跑下個flow
            if (!isDynamic) {
                activitiService.nextFlowCorrect(kgoCaseMain.getProcessId(), false, "3");
            }
            /** === 2020.12.15 補正作業 不用跑下個流程 === */

            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String tsStr = sdf.format(correctDate);
            doNotify(kgoCaseMain.getCaseId(), kgoCaseMain, tsStr, correctList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandleSignSaCaseCorrectUpdate error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("通知補件補件，案件編號", rq.getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return caseHandlePendingReviewCorrectUpdateRs;
    }

    private void doNotify(String caseId, KgoCaseMain kgoCaseMain, String correctDeadlineDate, List<Map<String, Object>> correctList) {
        // 發送mail及推播
        String caseSetId = kgoCaseMain.getCaseSetId();
        Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
        if (kgoCaseset.isPresent()) {
            try {
                // 寄出email
                List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "Email");
                Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
                if (max.isPresent()) {
                    String columnValue = max.get().getColumnValue();
                    if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
                        sendEmailApplyCorrect(kgoCaseset.get(), columnValue, correctDeadlineDate, correctList, caseId);
                    } else {
                        LOGGER.info("caseProcessSearch detailSaveAction doNotify no email to send empty String");
                    }
                } else {
                    LOGGER.info("caseProcessSearch detailSaveAction doNotify no email to send empty");
                }
            } catch (Exception e) {
                LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
            }
            try {
                // 寄出推播
                List<KgoCaseDetail> kgoCaseDetailsID = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "ID");
                Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
                if (maxID.isPresent()) {
                    String columnValue = maxID.get().getColumnValue();
                    if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
                        pushNotificationApplyCorrect(kgoCaseset.get(), columnValue, kgoCaseMain);
                    } else {
                        LOGGER.info("caseProcessSearch detailSaveAction doNotify no notification to send empty String");
                    }
                } else {
                    LOGGER.info("caseProcessSearch detailSaveAction doNotify no notification to send empty");
                }
            } catch (Exception e) {
                LOGGER.error(KgoBackEndApiError.FAIL_TO_NOTIFY.getErrorMsgKey());
            }
        }
    }

    private void sendEmailApplyCorrect(KgoCaseset kgoCaseset, String email, String correctDeadlineDate, List<Map<String, Object>> correctList, String caseId) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("casesetName", kgoCaseset.getCaseSetName());
        model.put("correctDeadlineDate", correctDeadlineDate);
        model.put("correctList", correctList);
        model.put("caseUrl", String.format(frontendCaseSearchLinkLogin, caseId));
        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.correct.mail.title"), model, "notifyCorrectCase.html");
    }

    private void pushNotificationApplyCorrect(KgoCaseset kgoCaseset, String id, KgoCaseMain kgoCaseMain) {
//        List<PushMsgDto> pushDataList = new ArrayList<>();
//        PushMsgDto pushMsg = new PushMsgDto();
//        pushMsg.setCustNum(id);
//        pushMsg.setMsgTitle(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.msgTitle"));
//        pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.msgBody"),
//                kgoCaseset.getCaseSetName()));
//        pushMsg.setClickDetail(
//                String.format(messageUtil.getMessage("kgo.backend.case.handle.correct.notification.clickDetail"),
//                        kgoCaseset.getCaseSetName(), correctDeadlineDate));
//        pushDataList.add(pushMsg);

        pushService.pushMessage(id, PushCaseStatusEnum.C.getValue(), kgoCaseMain.getCaseId(), kgoCaseset.getCaseSetName(), String.valueOf(kgoCaseMain.getApplyDate()));

//        this.pushService.pushMessage(pushDataList, kgoCaseset.getOwnerOrgan());
    }

    private void pushNotificationApplyComplete(KgoCaseset kgoCaseset, String id, KgoCaseMain kgoCaseMain) {
//        List<PushMsgDto> pushDataList = new ArrayList<>();
//        PushMsgDto pushMsg = new PushMsgDto();
//        pushMsg.setCustNum(id);
//        pushMsg.setMsgTitle(messageUtil.getMessage("kgo.backend.case.handle.complete.notification.msgTitle"));
//        pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.backend.case.handle.complete.notification.msgBody"),
//                kgoCaseset.getCaseSetName()));
//        pushMsg.setClickDetail(
//                String.format(messageUtil.getMessage("kgo.backend.case.handle.complete.notification.clickDetail"),
//                        kgoCaseset.getCaseSetName()));
//        pushDataList.add(pushMsg);

        pushService.pushMessage(id, kgoCaseMain.getStatus(), kgoCaseMain.getCaseId(), kgoCaseset.getCaseSetName(), String.valueOf(kgoCaseMain.getApplyDate()));

//        this.pushService.pushMessage(pushDataList, kgoCaseset.getOwnerOrgan());
    }

    private Timestamp getCorrectDate(Integer correctDeadLine) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate today = LocalDate.now();
        ZonedDateTime zdt = today.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        LocalDate firstDeadLine = today.plusDays(correctDeadLine);
        ZonedDateTime zdt2 = firstDeadLine.atStartOfDay(zoneId);
        Date date2 = Date.from(zdt2.toInstant());
        List<KgoHoliday> holidayList = kgoHolidayRepository.findHolidayList(date, date2);
        if (!CollectionUtils.isEmpty(holidayList)) {
            firstDeadLine = firstDeadLine.plusDays(holidayList.size());
        }
        Timestamp correctDate = Timestamp.valueOf(firstDeadLine.atStartOfDay());
        return correctDate;
    }

    /**
     * 後台案件處理-待簽收匣-檢視
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandlePendingSignReviewRs caseHandlePendingSignReview(CaseHandlePendingSignReviewRq rq) {
        CaseHandleCaseViewSaCaseHomeRq subrq = new CaseHandleCaseViewSaCaseHomeRq();
        subrq.setCaseId(rq.getCaseId());
        subrq.setTaskId(rq.getTaskId());
        CaseHandleCaseViewSaCaseHomeRs subrs = caseHandleCaseViewSaCaseHome(subrq);
        CaseHandleCaseViewSaCaseHomeViewForm data = subrs.getData();
        CaseHandlePendingSignReviewViewForm viewForm = new CaseHandlePendingSignReviewViewForm();
        viewForm.setApplyData(data.getApplyData());
        viewForm.setApplyDate(data.getApplyDate());
        viewForm.setCaseId(data.getCaseId());
        viewForm.setCaseName(data.getCaseName());
        viewForm.setDownloadData(data.getDownloadData());
        viewForm.setHistoryData(data.getHistoryData());
        viewForm.setLimitDay(data.getLimitDay());
        viewForm.setLimitTime(data.getLimitTime());
        viewForm.setCorrectDeadline(data.getCorrectDeadline());
        viewForm.setLimitTime(data.getLimitTime());
        viewForm.setStatus(data.getStatus());
        CaseHandlePendingSignReviewRs rs = new CaseHandlePendingSignReviewRs();
        rs.setData(viewForm);
        rs.setMsg(subrs.getMsg());
        rs.setRtnCode(subrs.getRtnCode());
        return rs;
    }

    /**
     * 後台案件處理-待審核匣-結案
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandlePendingSignCompleteRs caseHandlePendingSignComplete(CaseHandlePendingSignCompleteRq rq) {
        KgoApiException error = null;
        OperationApiMemo memo = null;
        CaseHandlePendingSignCompleteRs rs = new CaseHandlePendingSignCompleteRs();
        String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
        String caseId = rq.getCaseId();
        String status = rq.getStatus();
        String description = rq.getDescription();
        Timestamp currentTime = DateUtil.getCurrentTimestamp();
        try {
            // 後台、、結案
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Cend);

            KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);
            Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseMain.getCaseSetId());
            String caseStatus = CaseStatusEnum.COMPLETE.getLabel();// 案件狀態

            /** GEO 20211026 add */
//			List<Task> tt = taskService.createTaskQuery().taskCandidateOrAssigned(loginUserId).taskId(task.getId()).list();
            GeoKgoAgent agentData = null;
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent agent : principalList) {
                    List<Task> taskList = getPrincipalTaskList(agent, rq.getTaskId());
                    if (taskList != null && taskList.size() > 0) agentData = agent;
                }
            } //if (principalList != null &&

            // add comment
            String loginUserName = KgoUtil.getBackendLoginUser().getName();
            String comment = String.format("由%s%s", loginUserName, caseStatus); // 內容
            String loginUserOrganId = KgoUtil.getBackendLoginUser().getOrgan();
            KgoOrgan organItem = kgoOrganRepository.findByOrganId(loginUserOrganId);
            String organName = organItem.getOrganName(); // 承辦的機關名稱
            /** GEO 20211026 add */
            if (agentData != null) {
                KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
                comment = String.format("由%s代理%s%s", loginUserName, user.getName(), caseStatus);
                agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
                geoKgoAgentRepository.save(agentData);
            }
            /** GEO 20211101 add 增加簽核意見*/
            Comment commentEntity = activitiService.addCommentByTaskId(loginUserId, rq.getTaskId(), caseStatus, comment, organName, loginUserName, CaseStatusEnum.COMPLETE);
            geoTaskCommentService.saveTaskComment(commentEntity.getId(), rq.getTaskComment(), loginUserOrganId, loginUserId);

            /** GEO 20211109 add 機關審核表單*/
            if (rq.getColumnData() != null && rq.getColumnData().size() > 0) {
                try {
                    saveKgoCaseDetail(rq.getCaseId(), rq.getColumnData(), commentEntity.getId());
                } catch (ParseException e) {
                    throw new KgoApiException("caseHandlePendingSignComplete error " + e.getMessage(), e);
                }
            }
            // complete activiti
//            activitiService.completeFlowByTaskId(rq.getTaskId());
//            if (CaseMainStatusEnum.C3.getValue().equals(caseMain.getStatus())) {
//                activitiService.completeFlow(caseMain.getProcessId());
//            }
            Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
            String casesetCategory = null;
            boolean needPay = false;
            Integer deadLineDays = 0;
            boolean isCrossOrgan = false;
            if (kgoCaseset.isPresent()) {
                KgoCaseset kgoCaseset1 = kgoCaseset.get();
                if (CaseTypeEnum.D.getValue().equals(kgoCaseset1.getCaseType())) {
                    runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "結案");
                } else {
                    if (CaseMainStatusEnum.C3.getValue().equals(caseMain.getStatus())) {
                        activitiService.completeFlowByTaskIdApprove(rq.getTaskId(), "false");
                    } else {
                        activitiService.completeFlowByTaskIdApprove(rq.getTaskId(), "true");
                    }
                }
                //20221109 新增服務種類判斷欄位及繳費判斷
                CaseSetCategoryEnum categoryEnum = CaseSetCategoryEnum.getApplyTypeEnum(kgoCaseset1.getCasesetCategory());
                casesetCategory = categoryEnum == null ? "common" : categoryEnum.getValue();
                needPay = kgoCaseset1.getNeedPay() == null ? false : kgoCaseset1.getNeedPay();
                deadLineDays = kgoCaseset1.getPayDeadline() == null ? 0 : kgoCaseset1.getPayDeadline();
                LOGGER.error("casesetCategory :" + casesetCategory + " needPay :" + needPay);

                //跨機關API
                isCrossOrgan = ORGAN.getValue().equalsIgnoreCase(kgoCaseset1.getCasesetCategory());
                if (isCrossOrgan) {
                    callCrossOrganAPI(caseId);
                    status = WO.getValue();
                    LOGGER.info(String.format("call cross organ api : %s", caseId));
                }
            }


            caseMain.setStatus(status);
            caseMain.setResult(description);
            caseMain.setfDate(currentTime);
            caseMain.setUpdateTime(currentTime);
            caseMain.setUpdateUser(loginUserId);
            caseMain.setReviewer(loginUserId);
            kgoCaseMainRepository.save(caseMain);
            //任務得點 發城市幣
//            if (!isCrossOrgan)
//                cityCoinAPIService.completed(caseMain);

            // 信件參數
            Map<String, Object> noticeMap = new HashMap<>();
            //建立繳費主檔 結案狀態後變更繳費與預約狀態
            if ("site".equals(casesetCategory) || "activity".equals(casesetCategory)) {
                LOGGER.error("CaseMain Completed Start to check Site and Activity case ..");
                if (status.equals("F3")) {//結案情況
                    LOGGER.error("Casestatus F3 結案:start ");
                    if (needPay) {//需要繳費
                        geoCaseSetRentService.updateRentRelation(RentStatusEnum.PROC, caseId);
                        GeoKgoCaseRentRelation relationEntity = geoCaseRentRelationRepos.findByCaseId(caseId);
                        GeoKgoCaseRentPayment paymentEntity = geoCaseSetRentService.createRentRentalPayment(deadLineDays, rq.getPayAmount(), caseId, relationEntity.getRentTimeId(), rq.getDiscountPercent(), loginUserId);
                        String discount = paymentEntity.getPaymentDiscount() == -1 ? "無使用優惠折扣": paymentEntity.getPaymentDiscount()+"%";
                        noticeMap.put("payAmount",paymentEntity.getSysPayAmount()+" 元");
                        noticeMap.put("paymentDiscount",discount);
                        noticeMap.put("actualAmount",paymentEntity.getPayAmount()+" 元");

                    } else {//不須繳費 完成預約
                        geoCaseSetRentService.updateRentRelation(RentStatusEnum.SUS, caseId);
                    }
                    //產生繳費barcode
                    String fileName = caseId + ".png";
                    String barcodeClasspath = SpringUtil.getProperty("site.email.payment.barcode");
                    String linkDomain = SpringUtil.getProperty("site.email.payment.barcode.domain");
                    String linkUrl = linkDomain + "/reserve/search/info?id=" + caseId;
                    genQRCode(linkUrl, barcodeClasspath, fileName);
                    noticeMap.put("barcodeClasspath", barcodeClasspath + File.separator + fileName);
                    noticeMap.put("linkUrl", linkUrl);
                    noticeMap.put("delete","Y");
                    noticeMap.put("rentCase","Y");
                } else if (status.equals("J3") || status.equals("S3")) {
                    LOGGER.error("Casestatus J3 結案不通過 S3 結案存查 :start ");
                    geoCaseSetRentService.updateRentRelation(RentStatusEnum.FAIL, caseId);
                }

            }

            /** GEO 20221110 退費程序限定流程 **繳退費驗證ID = S2022110900004 */
            String refundCaseset = SpringUtil.getProperty("applyRefund.caseset");
            if (refundCaseset.equals(caseMain.getCaseSetId()) && status.equals("F3")) {
                Timestamp applyDate = new Timestamp(caseMain.getApplyDate().getTime());
                //取申請退費refurnCaseId
                LOGGER.info("caseId :" + caseId + " process refundService start ...");
                KgoCaseDetail kgoCaseDetail = kgoCaseDetailRepository.findRefurnIdByCaseId(caseId, "refurnCaseId");
                String refurnCaseId = kgoCaseDetail.getColumnValue();
                Integer cityCoin = caseMain.getCityCoin() == null ? 0 : caseMain.getCityCoin();
                geoCaseSetRentService.applyRefundService(caseId, refurnCaseId, cityCoin, applyDate, rq.getDiscountPercent(), rq.getPayAmount(),loginUserId);
                GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(refurnCaseId);
                String refundDiscount = paymentEntity.getRefundDiscount() == -1 ? "無使用優惠折扣" : paymentEntity.getRefundDiscount() + "%";
                noticeMap.put("refundCase","Y");
                noticeMap.put("payAmount",paymentEntity.getPayAmount());
                noticeMap.put("deductPercent",paymentEntity.getDeductPercent()+"%");
                noticeMap.put("refundAmount",paymentEntity.getPayAmount() * paymentEntity.getDeductPercent() / 100 );
                noticeMap.put("refundDiscount",refundDiscount);
                noticeMap.put("actualrefundAmount",paymentEntity.getActualRefundAmount());

            }


            //發送通知信內含QRCode
            if (!isCrossOrgan)
                doNotifyComplete(caseMain.getCaseId(), caseMain, noticeMap);

            CaseHandlePendingSignCompleteHomeViewForm viewForm = new CaseHandlePendingSignCompleteHomeViewForm();
            viewForm.setCaseId(caseId);
            viewForm.setReviewer(loginUserId);

            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingSignComplete error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件結案，案件編號", rq.getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //caseHandlePendingSignComplete

    private void callCrossOrganAPI(String caseId) throws Exception {
        KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);
        if (caseMain != null) {
            JSONObject jsonObject = new JSONObject();
            //跨機關限定要加上高雄市
            jsonObject.put("notifyDescription", Strings.EMPTY);
            List<KgoCaseDetail> caseDetail = kgoCaseDetailRepository.findByIdCaseId(caseId);
            for (KgoCaseDetail kgoCaseDetail : caseDetail) {
                switch (kgoCaseDetail.getId().getColumnId().toLowerCase()) {
                    case "所屬行政區":
                        Boolean isContain = kgoCaseDetail.getColumnValue().contains("6400");
                        StringBuilder code = new StringBuilder(kgoCaseDetail.getColumnValue());
                        if (isContain) {
                            code.insert(4, "00");
                        } else {
                            code.insert(2, "00");
                        }
                        code.delete(7, code.length() - 1);
                        int codeInt = Integer.parseInt(code.toString());
                        GeoDistrictOfficeType geoDistrictOfficeType = GeoDistrictOfficeType.valueOfCode(codeInt);
                        jsonObject.put("town", geoDistrictOfficeType.getLabel().replace("公所", ""));
                        break;
                    case "請選擇辦理人員":
                        String agent = kgoCaseDetail.getColumnValue();
                        int agentInt = 0;
                        switch (agent.toLowerCase()) {
                            case "本人":
                                agentInt = 1;
                                break;
                            case "配偶":
                                agentInt = 2;
                                break;
                            case "直系血親":
                                agentInt = 3;
                                break;
                            case "他人":
                                agentInt = 4;
                                break;
                        }
                        if (agentInt != 0)
                            jsonObject.put("agentSeq", agentInt);
                        break;
                    case "申請異動事項":
                        List<Integer> categorySeqs = new ArrayList<>();
                        if (kgoCaseDetail.getColumnValue().toLowerCase().contains("姓名"))
                            categorySeqs.add(1);
                        if (kgoCaseDetail.getColumnValue().toLowerCase().contains("戶籍地址"))
                            categorySeqs.add(2);
                        if (kgoCaseDetail.getColumnValue().toLowerCase().contains("通訊地址"))
                            categorySeqs.add(3);
                        if (kgoCaseDetail.getColumnValue().toLowerCase().contains("國民身份證"))
                            categorySeqs.add(4);
                        if (!categorySeqs.isEmpty())
                            jsonObject.put("categorySeqs", categorySeqs.stream().toArray());
                        break;
                    case "name":
                        jsonObject.put("name", kgoCaseDetail.getColumnValue());
                        break;
                    case "原姓名":
                        jsonObject.put("oldName", kgoCaseDetail.getColumnValue());
                        break;
                    case "id":
                        jsonObject.put("identityNumber", kgoCaseDetail.getColumnValue());
                        break;
                    case "(原)國民身分證統一編號":
                        jsonObject.put("oldIdentityNumber", kgoCaseDetail.getColumnValue());
                        break;
                    case "cellphone":
                        jsonObject.put("phone", kgoCaseDetail.getColumnValue());
                        break;
                    case "聯絡電話":
                        jsonObject.put("tel", kgoCaseDetail.getColumnValue());
                        break;
                    case "變更戶籍地址":
                        jsonObject.put("residentAddress", kgoCaseDetail.getColumnValue());
                        break;
                    case "變更通訊地址":
                        jsonObject.put("mailingAddress", kgoCaseDetail.getColumnValue());
                        break;
                    case "異動email":
                        jsonObject.put("peopleEmail", kgoCaseDetail.getColumnValue());
                        break;
                    case "通報機關":
                        jsonObject.put("institutions",kgoCaseDetail.getColumnValue());
                        LOGGER.info("institutions kgoCaseDetail.getColumnValue()="+kgoCaseDetail.getColumnValue());
                }
            }
            jsonObject.put("caseId",caseId);
            LOGGER.info("跨機關\n" + jsonObject.toString());
            JSONObject resJson = callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_POST_CROSS_ORGAN.getServiceId(), jsonObject);
            KgoApiLog item = new KgoApiLog();
            item.setCaseId(caseId);
            item.setOrganId("跨機關");
            item.setReCount(0);
            item.setRequest(jsonObject.toString());
            item.setResponse(resJson.toString());
            item.setCreateTime(DateUtil.getCurrentTimestamp());
            item.setUpdateTime(DateUtil.getCurrentTimestamp());
            kgoApiLogRepository.save(item);
            LOGGER.info("跨機關回傳" + resJson);
        }
    }

    private List<Task> getPrincipalTaskList(GeoKgoAgent agent, String taskId) {
        KgoUser user = kgoUserRepository.findByUserId(agent.getPrincipalUserId());
        LOGGER.info("caseHandlePendingSignDispatch agent :" + agent.getPrincipalUserId());
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(agent.getPrincipalUserId()).taskId(taskId).list();
        List<Task> assignTaskList = taskService.createTaskQuery().taskAssignee(agent.getPrincipalUserId()).taskId(taskId).list();
        List<String> formattedRolesScaUra = getUserRoleList(agent.getPrincipalUserId()).stream().map(items -> String.format("%s-%s", items, user.getOrgan())).collect(Collectors.toList());
        List<String> formattedRoles = getUserRoleList(agent.getPrincipalUserId()).stream().map(items -> String.format("%s;%s", items, user.getOrgan())).collect(Collectors.toList());
        formattedRoles.addAll(formattedRolesScaUra);
        List<Task> groupTask = taskService.createTaskQuery().taskCandidateGroupIn(formattedRoles).taskId(taskId).list();
        taskList.addAll(assignTaskList);
        taskList.addAll(groupTask);
        return taskList;
    }

    /**
     * 動態流程同意/ 不同意 action.
     *
     * @param rq the rq
     * @return the api base response
     */
    @Override
    public ApiBaseResponse<BaseViewForm> doDynamicFlowAction(CaseHandlePendingSignDoDynamicFlowRq rq) {
        Boolean isAgree = rq.getIsAgree();
        ApiBaseResponse<BaseViewForm> rs = new ApiBaseResponse<>();
        try {
            String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
            String displayStatus = StringUtils.EMPTY;
            CaseStatusEnum caseStatusEnum = null;

            Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
            if (isAgree) {
                // modify 2021.01.07 一般流程: 分文(D)、陳核(A) or 會簽(1) , 結案會另外走結案流程
                String taskSubType = BaseFlowUtils.getTaskSubTypeByName(task.getName());
                ActTaskSubTypeEnum taskSubTypeE = ActTaskSubTypeEnum.getActTaskSubType(taskSubType);
                if (taskSubTypeE != null) {
                    caseStatusEnum = CaseStatusEnum.getApplyTypeEnum(taskSubTypeE.getTaskType().getType());

                    // 舊activiti 資料沒有taskSubType 預設為 一般
                } else {
                    // 2 一般類型: D(分文)、A(陳核)、F(結案)
                    caseStatusEnum = CaseStatusEnum.TASK_TYPE_2;
                }

                // modify 2021.01.07 D(分文)、A(陳核) or 會簽
                displayStatus = BaseFlowUtils.getTaskSubTypeNameByName(task.getName());

                // 退回上一關
            } else {
                caseStatusEnum = CaseStatusEnum.RETURN;
                displayStatus = CaseStatusEnum.RETURN.getLabel();
            }

            /** GEO 20211026 add */
//			List<Task> tt = taskService.createTaskQuery().taskCandidateOrAssigned(loginUserId).taskId(task.getId()).list();
            GeoKgoAgent agentData = null;
            List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(loginUserId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
            if (principalList != null && principalList.size() > 0) {
                for (GeoKgoAgent agent : principalList) {
                    List<Task> taskList = getPrincipalTaskList(agent, rq.getTaskId());
                    if (taskList != null && taskList.size() > 0) agentData = agent;
                }
            } //if (principalList != null &&

            // add comment
            String loginUserName = KgoUtil.getBackendLoginUser().getName();
            // 內容
            String comment = String.format("%s%s", loginUserName, displayStatus);
            String loginUserOrganId = KgoUtil.getBackendLoginUser().getOrgan();
            String organName = KgoUtil.getOrganName(loginUserOrganId); // 承辦的機關名稱
            /** GEO 20211026 add */
            if (agentData != null) {
                KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
                comment = String.format("%s代理%s%s", loginUserName, user.getName(), displayStatus);
                agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
                geoKgoAgentRepository.save(agentData);
            }
            /** GEO 20211101 add 增加簽核意見*/
            Comment commentEntity = activitiService.addCommentByTaskId(loginUserId, rq.getTaskId(), displayStatus, comment, organName, loginUserName, caseStatusEnum);
            geoTaskCommentService.saveTaskComment(commentEntity.getId(), rq.getTaskComment(), loginUserOrganId, loginUserId);

            /** GEO 20211109 add 機關審核表單*/
            if (rq.getColumnData() != null && rq.getColumnData().size() > 0) {
                try {
                    saveKgoCaseDetail(rq.getCaseId(), rq.getColumnData(), commentEntity.getId());
                } catch (ParseException e) {
                    throw new KgoApiException("caseHandlePendingSignComplete error " + e.getMessage(), e);
                }
            }

            if (isAgree) {
                activitiService.completeFlowByTaskIdApprove(rq.getTaskId(), "true");
                LOGGER.info("同意 : %s", isAgree);
            } else {
                LOGGER.info("退回上一關 : %s", isAgree);
                activitiService.completeFlowByTaskIdApprove(rq.getTaskId(), "false");
            }

            /** GEO 20211026 add caseMain 新增待會簽狀態*/
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            //LOGGER.info(String.format("getProcessDefinitionName : %s", processInstance.getProcessDefinitionName()));
            List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
            //if (NextTasks.size() >=1) for (Task t : nextTasks) LOGGER.info(String.format("doDynamicFlowAction nextTask : %s / %s", t.getId(), t.getTaskDefinitionKey()));
            KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(rq.getCaseId());
            CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.P3;
            if (nextTasks != null && nextTasks.size() > 0
                    && nextTasks.get(0).getTaskDefinitionKey().startsWith("parallel_")) {
                caseMainStatusEnum = CaseMainStatusEnum.TYPE13;
            }
            caseMain.setStatus(caseMainStatusEnum.getValue());
            kgoCaseMainRepository.save(caseMain);

            // 成功訊息 : {display}成功
            rs.setMsg(messageUtil.getMessage("kgo.back.end.display.flow.status", new String[]{displayStatus}));

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("caseHandlePendingSignComplete error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * GEO 20211109 add 機關審核表單
     * 儲存機關審核表單
     *
     * @param CaseId
     * @param columnViewFormList
     * @param commentId
     * @throws ParseException
     */
    private void saveKgoCaseDetail(String CaseId, List<SaveActionColumnViewForm> columnViewFormList, String commentId) throws ParseException {
        KgoCaseset kgoCaseset = null;
        KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(CaseId);
        String caseSetId = caseMain.getCaseSetId();
        Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(caseSetId);
        if (kgoCasesetOptional.isPresent()) kgoCaseset = kgoCasesetOptional.get();
        List<KgoCaseDetail> detailList = kgoCaseDetailRepository.findByIdCaseId(CaseId);
        Integer version = detailList.get(0).getId().getVersion();
        List<CasesetOrganGroupQueryDataMaxVersionDto> groupData = geoKgoCasesetOrganGroupReposCustom.findGroupData(caseSetId, StringUtils.EMPTY, version);
        Integer organVersion = groupData.get(0).getOrganFormVersion();

        for (SaveActionColumnViewForm columnViewForm : columnViewFormList) {
            GeoKgoCaseDetailOrgan kgoCaseDetail = new GeoKgoCaseDetailOrgan();

            GeoKgoCaseDetailOrganPK id = new GeoKgoCaseDetailOrganPK();
            id.setCaseId(CaseId);
            id.setColumnId(columnViewForm.getColumnId());
            id.setCColumnId("");
            id.setCaseFormVersion(version);
            id.setOrganFormVersion(organVersion);
            id.setCommentId(commentId);

            kgoCaseDetail.setId(id);
            kgoCaseDetail.setColumnValue(columnViewForm.getValue());

            geoKgoCaseDetailOrganRepository.save(kgoCaseDetail);

            saveKgoCaseDetail(CaseId, version, kgoCaseset, columnViewForm.getColumnId(), columnViewForm.getComplex(), organVersion, commentId);

            if (ObjectUtils.isNotEmpty(columnViewForm.getCasePdf()) && StringUtils.isNoneBlank(columnViewForm.getCasePdf().getFileBase64Str())) {
                columnViewForm.getCasePdf().setFileName(String.format("%s_%s.pdf", CaseId, caseMain.getApplyUser()));
                columnViewForm.getFiles().add(columnViewForm.getCasePdf());
            }
            saveUploadFile(CaseId, kgoCaseset, columnViewForm.getFiles());
        }
    }

    /**
     * GEO 20211109 add 機關審核表單
     */
    private void saveKgoCaseDetail(String caseId, Integer version, KgoCaseset kgoCaseset, String columnId, List<List<SaveActionCColumnViewForm>> complexViewForm, Integer organVersion, String commentId) throws ParseException {

        if (ObjectUtils.isNotEmpty(complexViewForm)) {
            if (complexViewForm.size() > 0) {
                for (List<SaveActionCColumnViewForm> complexList : complexViewForm) {
                    for (SaveActionCColumnViewForm complex : complexList) {
                        GeoKgoCaseDetailOrgan kgoCaseDetail = new GeoKgoCaseDetailOrgan();

                        GeoKgoCaseDetailOrganPK id = new GeoKgoCaseDetailOrganPK();
                        id.setCaseId(caseId);
                        id.setColumnId(columnId);
                        id.setCColumnId(complex.getColumnId());
                        id.setCaseFormVersion(version);
                        id.setOrganFormVersion(organVersion);
                        id.setCommentId(commentId);

                        kgoCaseDetail.setId(id);
                        kgoCaseDetail.setColumnValue(complex.getValue());

                        geoKgoCaseDetailOrganRepository.save(kgoCaseDetail);
                    }
                }
            }
        }
    }

    /**
     * GEO 20211109 add 機關審核表單
     */
    private void saveUploadFile(String caseId, KgoCaseset kgoCaseset, List<SaveFileViewForm> files) {
        if (files == null) {
            return;
        }

        File fileFolder = new File(KgoUtil.getCaseDownloadUploadBasePath(caseId));
        for (SaveFileViewForm form : files) {
            saveFile(fileFolder, form.getFileBase64Str(), form.getFileName());
        }
    }

    /**
     * GEO 20211109 add 機關審核表單
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
    }


    @SuppressWarnings("unchecked")
    @Override
    public void doNotifyComplete(String caseId, KgoCaseMain kgoCaseMain, Map<String, Object> noticeMap) {
        // 發送mail及推播
        String caseSetId = kgoCaseMain.getCaseSetId();
        Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
        if (kgoCaseset.isPresent()) {
            try {
                // 寄出email
                List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "Email");
                Optional<KgoCaseDetail> max = kgoCaseDetails.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
                if (max.isPresent()) {
                    String columnValue = max.get().getColumnValue();
                    if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
                        if(noticeMap.isEmpty()){
                            sendEmailComplete(kgoCaseset.get(), columnValue, CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus()).getLabel(), kgoCaseMain.getResult(), caseId, noticeMap);
                        }else{
                            sentRentCaseEmail(kgoCaseset.get(), columnValue, CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus()).getLabel(), kgoCaseMain.getResult(), caseId, noticeMap);
                        }
                    } else {
                        LOGGER.info("caseProcessSearch doNotifyComplete no email to send empty String");
                    }
                } else {
                    LOGGER.info("caseProcessSearch doNotifyComplete no email to send empty");
                }
            } catch (Exception e) {
                LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
            }

            try {
                // 發送推播
                List<KgoCaseDetail> kgoCaseDetailsID = kgoCaseDetailRepository.findByIdCaseIdAndIdColumnId(caseId, "ID");
                Optional<KgoCaseDetail> maxID = kgoCaseDetailsID.stream().max(Comparator.comparing(item -> item.getId().getVersion()));
                if (maxID.isPresent()) {
                    String columnValue = maxID.get().getColumnValue();
                    if (!org.springframework.util.StringUtils.isEmpty(columnValue)) {
                        pushNotificationApplyComplete(kgoCaseset.get(), columnValue, kgoCaseMain);
                    } else {
                        LOGGER.info("caseHandlePendingSignComplete doNotify no notification to send empty String");
                    }
                } else {
                    LOGGER.info("caseHandlePendingSignComplete doNotify no notification to send empty");
                }
            } catch (Exception e) {
                LOGGER.error(KgoBackEndApiError.FAIL_TO_NOTIFY.getErrorMsgKey());
            }
        }
    }

    private void sendEmailComplete(KgoCaseset kgoCaseset, String email, String status, String result, String caseId, Map<String, Object> noticeMap) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("caseSetName", kgoCaseset.getCaseSetName());
        model.put("caseStatus", status);
        model.put("caseId", caseId);
        model.put("caseDescription", StringUtils.defaultIfBlank(result, ""));
        model.put("caseUrl", String.format(frontendCaseSearchLink, caseId));
        String templatePath = "caseComplete.html";
        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.complete.mail.title"), model, templatePath);
    }

    final int chunkSize = 500;

    @Override
    public CaseHandleCaseUpdateQueryRs caseHandleCaseUpdateQuery(CaseHandleCaseUpdateQueryRq rq) {
        CaseHandleCaseUpdateQueryViewForm viewForm = new CaseHandleCaseUpdateQueryViewForm();
        CaseHandleCaseUpdateQueryRs rs = new CaseHandleCaseUpdateQueryRs();

        try {
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String loginUserId = backendLoginUser.getUserId();
            String loginOrganId = backendLoginUser.getOrgan();

            // 驗證參數
            this.validateParameter(rq);

            String officer = rq.getOfficer();
            List<String> applyDate = rq.getApplyDate();
            String applyStartDate = null;
            String applyEndDate = null;
            if (!CollectionUtils.isEmpty(applyDate)) {
                if (APPLY_START_DATE_INDEX < applyDate.size()) {
                    applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                }
                if (APPLY_END_DATE_INDEX < applyDate.size()) {
                    applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                }
            }
            String caseId = rq.getCaseId();
            String caseName = rq.getCaseName();

            List<CaseMainQueryReviewDto> caseDtoList = new LinkedList<>();
            List<String> userRoleList = getUserRoleList(loginUserId);

            boolean hasAdminRole = userRoleList.stream().anyMatch(s -> s.trim().equalsIgnoreCase(KgoRoleEnum.ADMIN.getValue()));
            List<Map<String, Object>> processIdMap = new ArrayList<>();

            if (hasAdminRole) {
                List<String> userIds = kgoUserRepository.findAll().stream().map(KgoUser::getUserId).collect(Collectors.toList());
                final AtomicInteger counter = new AtomicInteger();
                final Collection<List<String>> result = userIds.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();
                for (List<String> strings : result) {
                    List<Map<String, Object>> processIdMapTemp = activitiService.getPendingCaseProcessIdsForCaseUpdate(strings, Arrays.asList(ActivitiTaskNameEnum.CF.getLabel()));
                    processIdMap.addAll(processIdMapTemp);
                }
                List<String> processIds = processIdMap.stream().map(item -> (String) item.get("processId")).collect(Collectors.toList());
                /** 系統管理者(ADMIN)若為系統管理者已具有最大權限，則不需要判斷其他角色 **/
                // caseDtoList.addAll(kgoCaseMainRepository.caseViewUraScaQuery(false,
                // StringUtils.EMPTY, null, applyStartDate, applyEndDate, caseName, caseId,
                // null, officer)); // URA、SCA
                if (!CollectionUtils.isEmpty(processIds)) {
                    final Collection<List<String>> tempResult = processIds.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();
                    for (List<String> strings : tempResult) {
                        List<CaseMainQueryReviewDto> caseMainQueryReviewDtos = kgoCaseMainRepository.caseViewSAQueryForUpdateReviewer(null, applyStartDate, applyEndDate, caseName, caseId,
                                Arrays.asList(CaseMainStatusEnum.P3.getValue(), CaseMainStatusEnum.P.getValue()), officer, strings);
                        caseDtoList.addAll(caseMainQueryReviewDtos);// SA
                    }
                }
            } else {
                if (userRoleList.contains(KgoRoleEnum.UNIT_M.getValue())) {
                    List<String> userIds = kgoUserRepository.findByOrgan(loginOrganId).stream().map(KgoUser::getUserId).collect(Collectors.toList());
                    final AtomicInteger counter = new AtomicInteger();
                    final Collection<List<String>> result = userIds.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();
                    for (List<String> strings : result) {
                        List<Map<String, Object>> processIdMapTemp = activitiService.getPendingCaseProcessIdsForCaseUpdate(strings, Arrays.asList(ActivitiTaskNameEnum.CF.getLabel()));
                        processIdMap.addAll(processIdMapTemp);
                    }
                    List<String> processIds = processIdMap.stream().map(item -> (String) item.get("processId")).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(processIds)) {
                        final Collection<List<String>> tempResult = processIds.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();
                        for (List<String> strings : tempResult) {
                            List<CaseMainQueryReviewDto> caseMainQueryReviewDtos = kgoCaseMainRepository.caseViewSAQueryForUpdateReviewer(loginOrganId, applyStartDate, applyEndDate, caseName, caseId,
                                    Arrays.asList(CaseMainStatusEnum.P3.getValue(), CaseMainStatusEnum.P.getValue()), officer, strings);
                            caseDtoList.addAll(caseMainQueryReviewDtos);
                        }

                    }
                }
            }
            List<Map<String, Object>> finalProcessIdMap = processIdMap;
            List<CaseHandleCaseQueryDataGrid> gridList = caseDtoList.stream().map(l -> {
                // 取得 status 顯示字串
                String status = l.getStatus();
                if (status == null) {
                    status = CaseMainStatusEnum.OTHERS.getValue();
                }
                String statusName = StringUtils.EMPTY;
                if (CaseMainStatusEnum.getCaseMainStatusEnum(status) == null) {
                    if (ApplyCaseStatusEnum.getApplyCaseStatusEnum(l.getStatus()) != null) {
                        statusName = ApplyCaseStatusEnum.getApplyCaseStatusEnum(status).getLabel();
                    }
                } else {
                    CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(l.getStatus());
                    if (null != caseMainStatusEnum) {
                        statusName = caseMainStatusEnum.getLabel();
                    }
                }

                CaseHandleCaseQueryDataGrid grid = new CaseHandleCaseQueryDataGrid();
                grid.setApplyDate(DateUtil.dateToString(l.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                grid.setApplicant(l.getApplyUser());
                grid.setCaseId(l.getCaseId());
                grid.setCaseName(l.getCaseSetName());
                grid.setLimitDate(DateUtil.dateToString(l.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                grid.setStatusType(status);
                grid.setStatusName(statusName);
                grid.setOfficer(l.getCaseOfficer());
                grid.setType(l.getType());
                grid.setOrganId(l.getOrganId());
                grid.setOfficerName(l.getCaseOfficerName());
                if (!CollectionUtils.isEmpty(finalProcessIdMap)) {
                    Map<String, Object> processId = finalProcessIdMap.stream().filter(item -> item.get("processId").equals(l.getProcessId())).findAny().orElse(new HashMap<>());
                    Task task = (Task) processId.get("task");
                    if (null != task) {
                        grid.setTaskId(task.getId());
                    }
                }
                return grid;
            }).collect(Collectors.toList());
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseHandleCaseViewdQuery error " + e.getMessage(), e);
        }

        return rs;
    }

    private void validateParameter(CaseHandleCaseUpdateQueryRq rq) {
        List<String> applyDate = rq.getApplyDate();
        if (!CollectionUtils.isEmpty(applyDate)) {
            if (APPLY_START_DATE_INDEX < applyDate.size()) {
                String applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                if (!org.springframework.util.StringUtils.isEmpty(applyStartDate)) {
                    boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", applyStartDate, Locale.TAIWAN);
                    if (!validFormat) {
                        throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseView.queryAction.applyStartDate")));
                    }
                }
            }
            if (APPLY_END_DATE_INDEX < applyDate.size()) {
                String applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                if (!org.springframework.util.StringUtils.isEmpty(applyEndDate)) {
                    boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", applyEndDate, Locale.TAIWAN);
                    if (!validFormat) {
                        throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseView.queryAction.applyEndDate")));
                    }
                }
            }
        }
    }

    @Override
    public CaseHandleCaseUpdateUpdateRs caseHandleCaseUpdateUpdate(CaseHandleCaseUpdateUpdateRq rq) {
        CaseHandleCaseUpdateUpdateRs caseHandleCaseUpdateUpdateRs = new CaseHandleCaseUpdateUpdateRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        ArrayList<String> caseIdList = new ArrayList<String>();
        ArrayList<String> caseList = new ArrayList<String>();
        List<OperationColumn> caseLists = new ArrayList<>();
        for (CaseHandleCaseUpdateUpdateColumn cases : rq.getCaseHandleCaseUpdateUpdateColumnList()) {
            caseIdList.add(cases.getCaseId());
            for (KgoCaseMain IdBatch : kgoCaseMainRepository.findAllByIdBatch(caseIdList)) {
                caseLists.add(new OperationColumn("案件編號", IdBatch.getCaseId()));
                caseLists.add(new OperationColumn("原承辦", IdBatch.getCaseOfficer()));
                caseLists.add(new OperationColumn("新承辦", cases.getOfficer()));
            }
        }

        try {
            // 後台、、案件異動
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Ctransfer);

            List<CaseHandleCaseUpdateUpdateColumn> caseHandleCaseUpdateUpdateColumnList = rq.getCaseHandleCaseUpdateUpdateColumnList();
            if (CollectionUtils.isEmpty(caseHandleCaseUpdateUpdateColumnList)) {
                throw new KgoApiException(
                        new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseUpdate.queryAction.caseHandleCaseUpdateUpdateColumnList")));
            }
            List<KgoCaseMain> kgoCaseMains = new ArrayList<>();
            List<String> caseIds = caseHandleCaseUpdateUpdateColumnList.stream().map(CaseHandleCaseUpdateUpdateColumn::getCaseId).collect(Collectors.toList());
            List<KgoCaseMain> kgoCaseMainList = kgoCaseMainRepository.findAllByIdBatch(caseIds);
            if (caseHandleCaseUpdateUpdateColumnList.size() != kgoCaseMainList.size()) {
                throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, messageUtil.getMessage("kgo.backend.caseHandle.caseUpdate.queryAction.caseId")));
            }
            for (CaseHandleCaseUpdateUpdateColumn caseHandleCaseUpdateUpdateColumn : caseHandleCaseUpdateUpdateColumnList) {
                Optional<KgoCaseMain> any = kgoCaseMainList.stream().filter(item -> item.getCaseId().equals(caseHandleCaseUpdateUpdateColumn.getCaseId())).findAny();
                if (any.isPresent()) {
                    KgoCaseMain kgoCaseMain = any.get();
                    kgoCaseMain.setCaseOfficer(caseHandleCaseUpdateUpdateColumn.getOfficer());
                    kgoCaseMains.add(kgoCaseMain);
                }
                activitiService.dispatchFlowByTaskId(caseHandleCaseUpdateUpdateColumn.getTaskId(), null, caseHandleCaseUpdateUpdateColumn.getOfficer(), AcceptSetEnum.OFFICER.getValue());
            }

            kgoCaseMains = kgoCaseMainRepository.saveAll(kgoCaseMains);
            LOGGER.info("caseHandleCaseUpdateUpdate success size: {} ", kgoCaseMains.size());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
            error = new KgoApiException("caseHandleCaseViewdQuery error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            for (OperationColumn o : caseLists) {
                memoList.add(o);
            }

            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return caseHandleCaseUpdateUpdateRs;
    }

    @Override
    public CaseHandlePendingReviewNotifyHomeRs caseHandlePendingReviewNotifyHome(CaseHandlePendingReviewNotifyHomeRq rq) {
        // TODO Auto-generated method stub
        return null;
    }

//	@Override
//	public CaseHandleReviewedViewRs caseHandleReviewedView(CaseHandleReviewedViewRq rq) {
//		CaseHandleReviewedViewViewForm viewForm = new CaseHandleReviewedViewViewForm();
//		CaseHandleReviewedViewRs rs = new CaseHandleReviewedViewRs();
//		try {
//			String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
//			String caseId = rq.getCaseId();
//			KgoCaseMain kgoMain = kgoCaseMainRepository.findByCaseId(caseId);
//			String processId = kgoMain.getProcessId();
//			CaseMainQueryDto dto = reviewedSAQuery(processId);
//			List<CaseMainQueryDto> caseDtoList = new LinkedList<>();
//			caseDtoList.add(dto);
//			List<CaseHandleCaseQueryDataGrid> gridList = getRsGridData(caseDtoList);
//			CaseHandleCaseQueryDataGrid grid = gridList.get(0);
//			viewForm.setGrid(grid);
//			rs.setData(viewForm);
//		} catch (KgoApiException apiE) {
//			LOGGER.error(apiE.getMessage());
//			throw apiE;
//		} catch (Exception e) {
//			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
//			throw new KgoApiException("caseHandleReviewedView error " + e.getMessage(), e);
//		}
//		return rs;
//	}

    /**
     * [已審核匣] 查詢
     *
     * @return
     */
//    private CaseMainQueryDto reviewedSAQuery(String processId) {
//        List<String> processIds = new ArrayList<>();
//        processIds.add(processId);
//        List<CaseMainQueryDto> dtoList = kgoCaseMainRepository.reviewedSAQuery(processIds, "", "", "", "", "");
//        if (!dtoList.isEmpty()) {
//            CaseMainQueryDto dto = dtoList.get(0);
//            return dto;
//        }
//        return new CaseMainQueryDto();
//
//    }
    @Override
    public CaseHandlePendingReviewNotifyRs caseHandlePendingReviewNotify(CaseHandlePendingReviewNotifyRq rq) {
        String caseId = rq.getCaseId();
        KgoApiException error = null;
        OperationApiMemo memo = null;

        KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
        // 後台、、審核
        try {
            memo = super.genOperationMemo(SystemTypeEnum.B, null, BackendFunctionCodeEnum.Creview);

            // Can't find this case in KGO_CASE_MAIN
            if (kgoCaseMain == null)
                throw new KgoApiException("Can't find this case from KGO_CASE_MAIN, caseId is = " + caseId);

            String caseSetName = "";

            Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(kgoCaseMain.getCaseSetId());

            if (kgoCaseset.isPresent())
                caseSetName = kgoCaseset.get().getCaseSetName();

            // Can't find this case in KGO_CASE_MAIN
            if (kgoCaseMain == null)
                throw new KgoApiException("Can't find this case from KGO_CASE_MAIN, caseId is = " + caseId);

            List<KgoCaseDetail> kgoCaseDetails = kgoCaseDetailRepository.findByIdCaseId(caseId);

            // Can't find any detail in KGO_CASE_DETAIL
            if (kgoCaseDetails.isEmpty())
                throw new KgoApiException("Can't find detail from KGO_CASE_DETAIL, caseId is = " + caseId);

            // Get max version email
            Optional<KgoCaseDetail> kgoCaseDetailEmail = kgoCaseDetails.stream().filter(k -> BaseColumnEnum.EMAIL.getColumnId().equals(k.getId().getColumnId()))
                    .max(Comparator.comparing(k -> k.getId().getVersion()));

            // Get max version cellPhone
            Optional<KgoCaseDetail> kgoCaseDetailCellPhone = kgoCaseDetails.stream().filter(k -> BaseColumnEnum.CELL_PHONE.getColumnId().equals(k.getId().getColumnId()))
                    .max(Comparator.comparing(k -> k.getId().getVersion()));

            // Get max version id
            KgoCaseDetail kgoCaseDetailId = kgoCaseDetails.stream().filter(k -> BaseColumnEnum.ID.getColumnId().equals(k.getId().getColumnId())).max(Comparator.comparing(k -> k.getId().getVersion()))
                    .orElse(null);

            if (!kgoCaseDetailEmail.isPresent() && !kgoCaseDetailCellPhone.isPresent())
                throw new KgoApiException("Can't find detail from KGO_CASE_DETAIL, caseId is = " + caseId);

            if (kgoCaseDetailEmail.isPresent()) {
                try {
                    LOGGER.info("Start send mail to {}", kgoCaseDetailEmail.get().getColumnValue());

                    sendEmailReviewNotify(caseId, caseSetName, rq.getContent(), kgoCaseDetailEmail.get().getColumnValue());
                } catch (Exception e) {
                    LOGGER.error("Send email review notify error", e);
                }
            }

            if (kgoCaseDetailCellPhone.isPresent() && kgoCaseset.isPresent()) {
                try {
                    LOGGER.info("Start push notification to {}", kgoCaseDetailCellPhone.get().getColumnValue());

                    pushNotificationReviewNotify(caseSetName, kgoCaseDetailId != null ? kgoCaseDetailId.getColumnValue() : "", rq.getContent(), kgoCaseset.get().getOwnerOrgan());
                } catch (Exception e) {
                    LOGGER.error("Send push review notify error", e);
                }
            }

            String userId = KgoUtil.getBackendLoginUser().getUserId();

            KgoUser kgoUser = kgoUserRepository.getOne(userId);

            KgoMsgRecordPK kgoMsgRecordPK = new KgoMsgRecordPK();
            kgoMsgRecordPK.setCaseId(caseId);
            kgoMsgRecordPK.setSeq(RandomUtil.getUUID());

            KgoMsgRecord kgoMsgRecord = new KgoMsgRecord();
            kgoMsgRecord.setId(kgoMsgRecordPK);
            kgoMsgRecord.setHandler(kgoUser.getName());
            kgoMsgRecord.setMemo(rq.getContent());
            kgoMsgRecord.setStatus(kgoCaseMain.getStatus());
            kgoMsgRecord.setHandleTime(DateUtil.getCurrentTimestamp());

            kgoMsgRecordRepository.save(kgoMsgRecord);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>> caseHandlePendingReviewNotify >>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingReviewNotify error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("發送訊息，案件編號", rq.getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return new CaseHandlePendingReviewNotifyRs();
    }

    private void pushNotificationReviewNotify(String caseSetName, String id, String content, String organCode) {
        List<PushMsgDto> pushDataList = new ArrayList<>();

        PushMsgDto pushMsg = new PushMsgDto();
        pushMsg.setCustNum(id);
        pushMsg.setMsgTitle(messageUtil.getMessage("kgo.backend.case.handle.review.notification.msgTitle"));
        pushMsg.setMsgBody(String.format(messageUtil.getMessage("kgo.backend.case.handle.review.notification.msgBody"), caseSetName));
        pushMsg.setClickDetail(content);

        pushDataList.add(pushMsg);

        this.pushService.pushMessage(pushDataList, organCode);
    }

    private void sendEmailReviewNotify(String caseId, String caseSetName, String content, String email) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("caseSetName", caseSetName);
        model.put("content", content);
        model.put("caseUrl", String.format(frontendCaseSearchLink, caseId));

        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.review.notification.msgTitle"), model, "pendingReviewCase.html");
    }

    private void sendEmailSignDispatch(String caseId, String caseSetName, String email) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("caseId", caseId);
        model.put("casesetName", caseSetName);
        model.put("backendUrl", backendUrl);

        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.pending.sign.dispatch.title"), model, "pendingSignDispatch.html");
    }

    private void sendEmailReviewCancelAccept(String caseId, String caseSetName, String email) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("caseId", caseId);
        model.put("casesetName", caseSetName);

        mailUtil.sendTemplateMail(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.pending.review.cancel.accept.title"), model, "pendingReviewCancelAccept.html");
    }

    @Override
    @Transactional
    public CaseHandlePendingReviewUploadFileRs caseHandlePendingReviewUploadFile(CaseHandlePendingReviewUploadFileRq rq) {
        String caseId = rq.getCaseId();
        String title = rq.getTitle();
        MultipartFile file = rq.getFile();

        if (caseId.isEmpty())
            throw new KgoApiException("CaseId is empty");
        if (file == null)
            throw new KgoApiException("File is empty");

        KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(rq.getCaseId());

        // Can't find this case in KGO_CASE_MAIN
        if (kgoCaseMain == null)
            throw new KgoApiException("Can't find this case from KGO_CASE_MAIN, caseId is = " + caseId);

        CaseHandlePendingReviewUploadFileRs caseHandlePendingReviewUploadFileRs = new CaseHandlePendingReviewUploadFileRs();

        try {
            String filePath = KgoUtil.getCaseHandlePendingRevieweDownloadUploadBasePath(caseId);

            File directory = new File(filePath);

            if (!directory.exists())
                directory.mkdir();

            String seq = RandomUtil.getUUID();

            CaseHandlePendingReviewUploadFileForm caseHandlePendingReviewUploadFileForm = new CaseHandlePendingReviewUploadFileForm();
            caseHandlePendingReviewUploadFileForm.setSeq(seq);

            caseHandlePendingReviewUploadFileRs.setData(caseHandlePendingReviewUploadFileForm);
            commonService.uploadFileAction(file, filePath, seq, caseHandlePendingReviewUploadFileSizeLimit, caseHandlePendingReviewUploadFileExtension);

            KgoCaseFwdPK kgoCaseFwdPK = new KgoCaseFwdPK();
            kgoCaseFwdPK.setSeq(seq);
            kgoCaseFwdPK.setCaseId(caseId);

            KgoCaseFwd kgoCaseFwd = new KgoCaseFwd();
            kgoCaseFwd.setId(kgoCaseFwdPK);
            kgoCaseFwd.setTitle(title);
            kgoCaseFwd.setFileName(file.getOriginalFilename());

            kgoCaseFwdRepository.save(kgoCaseFwd);
        } catch (Exception ex) {
            LOGGER.error("Upload file error", ex);

            throw new KgoApiException("Upload file error, caseId is = " + caseId);
        }

        return caseHandlePendingReviewUploadFileRs;
    }

    @Override
    public CaseHandlePendingReviewDeleteFileRs caseHandlePendingReviewDeleteFile(CaseHandlePendingReviewDeleteFileRq rq) {
        String caseId = rq.getCaseId();
        String seq = rq.getSeq();

        String filePath = KgoUtil.getCaseHandlePendingRevieweDownloadUploadBasePath(caseId);

        try {
            if (kgoCaseFwdRepository.findByIdCaseId(caseId).isEmpty())
                throw new KgoApiException("Can't find this case file from KGO_CASE_FWD, caseId is = " + caseId);

            Stream<Path> pathStream = Files.list(new File(filePath).toPath()).filter(f -> f.getFileName().toString().contains(seq));

            if (!pathStream.findFirst().get().toFile().delete())
                throw new KgoApiException("Delete file error, caseId is = " + caseId + ", Seq is = " + seq);

            kgoCaseFwdRepository.deleteByIdSeq(seq);
        } catch (IOException e) {
            LOGGER.error("Delete file error", e);

            throw new KgoApiException("Delete file error, caseId is = " + caseId + ", Seq is = " + seq);
        }

        return new CaseHandlePendingReviewDeleteFileRs();
    }

    @Override
    public void caseHandlePendingReviewDownloadFile(CaseHandlePendingReviewDownloadFileRq rq) {
        String caseId = rq.getCaseId();
        String seq = rq.getSeq();

        String filePath = KgoUtil.getCaseHandlePendingRevieweDownloadUploadBasePath(caseId);
        KgoApiException error = null;
        OperationApiMemo memo = null;

        try {
            // 後台、下載、待審核匣
            memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_L, BackendFunctionCodeEnum.Creview);

            if (kgoCaseFwdRepository.findByIdCaseId(caseId).isEmpty())
                throw new KgoApiException("Can't find this case file from KGO_CASE_FWD, caseId is = " + caseId);

            Stream<Path> pathStream = Files.list(new File(filePath).toPath()).filter(f -> f.getFileName().toString().contains(seq));

            File file = pathStream.findFirst().get().toFile();

            commonService.downloadFileAction(file);
        } catch (IOException e) {
            LOGGER.error("Download file error", e);

            error = new KgoApiException("Download file error, caseId is = " + caseId + ", Seq is = " + seq);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>> caseHandlePendingReviewDownloadFile >>>>>" + e.getMessage(), e);
            error = new KgoApiException("caseHandlePendingReviewDownloadFile error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件下載，案件編號", rq.getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
    }

    @Override
    public CaseHandlePendingReviewNotifyHistoryRs caseHandlePendingReviewNotifyHistory(CaseHandlePendingReviewNotifyHistoryRq rq) {
        String caseId = rq.getCaseId();

        CaseHandlePendingReviewNotifyHistoryRs caseHandlePendingReviewNotifyHistoryRs = new CaseHandlePendingReviewNotifyHistoryRs();

        KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);

        // Can't find this case in KGO_CASE_MAIN
        if (kgoCaseMain == null)
            throw new KgoApiException("Can't find this case from KGO_CASE_MAIN, caseId is = " + caseId);

        List<KgoMsgRecord> kgoMsgRecords = kgoMsgRecordRepository.findAllByIdCaseId(caseId);

        if (!kgoMsgRecords.isEmpty()) {
            List<CaseHandlePendingReviewNotifyHistoryDataGrid> caseHandlePendingReviewNotifyHistoryDataGrids = new ArrayList<>();

            kgoMsgRecords.forEach(k -> {
                CaseHandlePendingReviewNotifyHistoryDataGrid caseHandlePendingReviewNotifyHistoryDataGrid = new CaseHandlePendingReviewNotifyHistoryDataGrid();
                caseHandlePendingReviewNotifyHistoryDataGrid.setHandler(k.getHandler());
                caseHandlePendingReviewNotifyHistoryDataGrid.setMemo(k.getMemo());
                caseHandlePendingReviewNotifyHistoryDataGrid.setHandleTime(k.getHandleTime());
                caseHandlePendingReviewNotifyHistoryDataGrid.setStatus(k.getStatus());

                caseHandlePendingReviewNotifyHistoryDataGrids.add(caseHandlePendingReviewNotifyHistoryDataGrid);
            });

            CaseHandlePendingReviewNotifyHistoryForm caseHandlePendingReviewNotifyHistoryForm = new CaseHandlePendingReviewNotifyHistoryForm();
            caseHandlePendingReviewNotifyHistoryForm.setCaseHandlePendingReviewNotifyHistoryDataGrids(caseHandlePendingReviewNotifyHistoryDataGrids);

            caseHandlePendingReviewNotifyHistoryRs.setData(caseHandlePendingReviewNotifyHistoryForm);
        }

        return caseHandlePendingReviewNotifyHistoryRs;
    }

    @Override
    public CaseHandleCaseViewScaCaseUpdateStatueRs caseHandleCaseViewScaCaseUpdateStatus(CaseHandleCaseViewSaCaseUpdateStatusRq rq) {
        try {
            if (StringUtils.isEmpty(rq.getCaseId())) {
                throw new KgoApiException(new ErrorResult(CaseViewUpdateStatusApiError.PROCESS_VALIDATE_ERROR, "caseId"));
            }
            if (StringUtils.isEmpty(rq.getStatus())) {
                throw new KgoApiException(new ErrorResult(CaseViewUpdateStatusApiError.PROCESS_VALIDATE_ERROR, "status"));
            }
            KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(rq.getCaseId());
            if (null != kgoCaseMain) {
                Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(kgoCaseMain.getCaseSetId());
                if (kgoCasesetOptional.isPresent()) {
                    if (Arrays.asList(CaseFlowTypeEnum.A.getValue(), CaseFlowTypeEnum.B1.getValue(), CaseFlowTypeEnum.B2.getValue()).contains(kgoCasesetOptional.get().getCaseFlowType())) {
                        kgoCaseMain.setStatus(rq.getStatus());
                    } else {
                        throw new KgoApiException(new ErrorResult(CaseViewUpdateStatusApiError.PROCESS_VALIDATE_ERROR, "caseId"));
                    }
                } else {
                    throw new KgoApiException(new ErrorResult(CaseViewUpdateStatusApiError.PROCESS_VALIDATE_ERROR, "caseId"));
                }
            } else {
                throw new KgoApiException(new ErrorResult(CaseViewUpdateStatusApiError.PROCESS_VALIDATE_ERROR, "caseId"));
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("caseHandleCaseViewScaCaseUpdateStatus error " + e.getMessage(), e);
        }
        return new CaseHandleCaseViewScaCaseUpdateStatueRs();
    }

    /**
     * 取得勾選欄位
     *
     * @return
     */
    @Transactional
    public List<CaseHandleCaseViewSaCaseApplyDataDataGrid> getFieldCheck(String caseId) {
        List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyData = null;
        try {
            if (StringUtils.isNotBlank(caseId)) {
                SaCaseViewQueryDto dto = kgoCaseMainRepository.getSaCaseViewData(caseId);
                if (null != dto) {
                    /** ========= 申請案件 ========= **/
                    List<SaCaseViewDetailColumnQueryDto> dtoList = kgoCaseDetailRepository.getDetailData(caseId);
                    for (SaCaseViewDetailColumnQueryDto saCaseViewDetailColumnQueryDto : dtoList) {
                        entityManager.detach(saCaseViewDetailColumnQueryDto);
                    }
                    List<SaCaseViewDetailColumnQueryDto> filterDto = dtoList.stream().filter(item -> ObjectUtils.isEmpty(item.getCcolumnRowNum())).collect(Collectors.toList());
                    Map<String, List<SaCaseViewDetailColumnQueryDto>> dtoMap = dtoList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getCcolumnRowNum()))
                            .collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getSetColumnId));
//					for (Map.Entry<String, List<SaCaseViewDetailColumnQueryDto>> stringListEntry : dtoMap.entrySet()) {
//						List<SaCaseViewDetailColumnQueryDto> value = stringListEntry.getValue();
//
//					}
                    filterDto = filterDto.stream().sorted(Comparator.comparingInt(SaCaseViewDetailColumnQueryDto::getColumnOrderNum)).collect(Collectors.toList());
                    Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.TS.getType());
                    applyData = filterDto.stream().map(l -> {
                        CaseHandleCaseViewSaCaseApplyDataDataGrid dg = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                        String columnValue = l.getRealColumnValue();
                        String columnName = l.getColumnName();
                        String setColumnValue = l.getSetColumnValue();
                        // 取得KGO_CASE_DETAIL columnValue 對應到 KGO_CASESET 的對應資料轉換.
                        // 2021/01/18 fix columnType 比對 ColumnTypeEnum getLable -> getValue
                        String columnType = l.getSetColumnType();
                        if (new Integer(1).equals(l.getIsSource()) && StringUtils.isNotBlank(columnValue) && (columnType.equalsIgnoreCase(ColumnTypeEnum.CHECKBOX.getValue())
                                || columnType.equalsIgnoreCase(ColumnTypeEnum.RADIO.getValue()) || columnType.equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                            columnValue = caseHandleServiceHelper.getColumnMappingValue(setColumnValue, columnValue);
                        }
                        dg.setColumnName(columnName);
                        if ((CaseMainStatusEnum.F.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.F3.getValue().equals(dto.getStatus())
                                || CaseMainStatusEnum.J3.getValue().equals(dto.getStatus()) || CaseMainStatusEnum.S3.getValue().equals(dto.getStatus()))
                                && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId())) && !CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            Timestamp fdate = l.getFdate();
                            if (null != fdate) {
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = fdate.toLocalDateTime();
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        if ("Name".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideName(columnValue);
                                        } else if ("ID".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideID(columnValue);
                                        }
                                    }
                                }
                            }
                        } else if (CaseMainStatusEnum.A.getValue().equals(dto.getStatus()) && ("ID".equals(l.getSetColumnId()) || "Name".equals(l.getSetColumnId()))
                                && CaseFlowTypeEnum.A.getValue().equals(dto.getCaseFlowType())) {
                            Date applyDateTemp = dto.getApplyDate();
                            if (null != applyDateTemp) {
                                Instant instant = applyDateTemp.toInstant();
                                ZoneId zoneId = ZoneId.systemDefault();
                                LocalDateTime applyDateTempLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
                                LocalDateTime localDateTime = null;
                                if (kgoParamSetOptional.isPresent()) {
                                    KgoParamSet kgoParamSet = kgoParamSetOptional.get();
                                    String value = kgoParamSet.getValue();
                                    String detailType = kgoParamSet.getDetailType();
                                    LocalDateTime fDateLocalDateTime = applyDateTempLocalDateTime;
                                    if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value));
                                    } else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
                                        localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value));
                                    }
                                    if (localDateTime.isBefore(LocalDateTime.now())) {
                                        if ("Name".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideName(columnValue);
                                        } else if ("ID".equals(l.getSetColumnId())) {
                                            columnValue = KgoUtil.hideID(columnValue);
                                        }
                                    }
                                }
                            }
                        }

                        List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexDataList = new ArrayList<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>>();
                        if (dtoMap.containsKey(l.getSetColumnId())) {
                            List<SaCaseViewDetailColumnQueryDto> value = dtoMap.get(l.getSetColumnId());
                            Map<Integer, List<SaCaseViewDetailColumnQueryDto>> dataMap = value.stream()
                                    .collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
                            complexDataList = dataMap.keySet().stream().map(i -> {
                                return dataMap.get(i).stream().map(cl -> {
                                    CaseHandleCaseViewSaCaseApplyDataDataGridComplex complexData = new CaseHandleCaseViewSaCaseApplyDataDataGridComplex();
                                    complexData.setbText(StringUtils.defaultString(cl.getBtext(), ""));
                                    complexData.setcColumnId(StringUtils.defaultString(cl.getSetCcolumnId(), ""));
                                    // 2021/01/18 判斷複合欄位型態
                                    complexData.setColumnType(StringUtils.defaultString(cl.getSetCcolumnType(), ""));
                                    String CcolumnValue = cl.getRealColumnValue();
                                    if (StringUtils.isNotBlank(CcolumnValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue())
                                            || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
                                        CcolumnValue = caseHandleServiceHelper.getColumnMappingValue(cl.getSetCcolumnValue(), CcolumnValue);
                                    }
                                    complexData.setColumnValue(StringUtils.defaultString(cl.getSetCcolumnValue(), ""));
                                    complexData.setfText(StringUtils.defaultString(cl.getFtext(), ""));
                                    complexData.setOrderNum(cl.getCcolumnOrderNum());
                                    complexData.setRow(cl.getCcolumnRowNum());
                                    complexData.setValue(StringUtils.defaultString(CcolumnValue, ""));
                                    return complexData;
                                }).collect(Collectors.toList());
                            }).collect(Collectors.toList());
                        }

                        dg.setComplex(complexDataList);
                        dg.setColumnValue(columnValue);
                        dg.setColumnId(l.getSetColumnId());
                        dg.setCorrectBValue(l.getCorrectBValue());
                        dg.setCorrectMemo(l.getCorrectMemo());
                        dg.setColumnType(columnType);
                        return dg;
                    }).collect(Collectors.toList());
                    return applyData;
                }
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
//			e.printStackTrace();
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
            throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
        }

        return applyData;
    }

    /**
     * GEO 20211105 add
     * <p>
     * 後台-案件處理-案件檢視:excel匯出
     */
    @Override
    public void exportExcelAction(CaseHandleCaseViewQueryRq rq) {
        try {
            String fileName = messageUtil.getMessage("kgo.backend.caseView.excel.fileName");
            String sheetName = messageUtil.getMessage("kgo.backend.caseView.excel.sheet");
            Map<String, Object> data = new HashMap<>();
            data.put("ORDER", messageUtil.getMessage("kgo.backend.caseView.excel.header.order"));
            data.put("CASE_ID", messageUtil.getMessage("kgo.backend.caseView.excel.header.caseId"));
            data.put("APPLY_DATE", messageUtil.getMessage("kgo.backend.caseView.excel.header.applyDate"));
            data.put("APPLICANT", messageUtil.getMessage("kgo.backend.caseView.excel.header.applicant"));
            data.put("CASE_NAME", messageUtil.getMessage("kgo.backend.caseView.excel.header.caseName"));
            data.put("DEADLINE_DATE", messageUtil.getMessage("kgo.backend.caseView.excel.header.deadlineDate"));
            data.put("STATUS", messageUtil.getMessage("kgo.backend.caseView.excel.header.status"));
            data.put("CASE_OFFICER", messageUtil.getMessage("kgo.backend.caseView.excel.header.caseOfficer"));
            data.put("LOGIN", messageUtil.getMessage("kgo.backend.caseView.excel.header.login"));

            List<GeoCaseHandleExcelVo> excelVoList = new ArrayList<>();
            List<GeoCaseHandleExcelVo> excelVosGridView = new ArrayList<>();

            /**
             * 20221101 Ruby add 好孕案件檢視和一般案件匯出分開處理 好孕改成全部匯出表單欄位(限同版本，不同版本只能匯出固定欄位)
             * */
            if (rq.getCaseSetId()!=null && rq.getCaseSetId().equals("pregnant")) {
                CaseByOrganQueryRq caseByOrganQueryRq = new CaseByOrganQueryRq();
                caseByOrganQueryRq.setCaseId(rq.getCaseId());
                caseByOrganQueryRq.setCaseName(rq.getCaseName());
                caseByOrganQueryRq.setOrganId(rq.getOrganId());
                caseByOrganQueryRq.setApplyDate(rq.getApplyDate());
                caseByOrganQueryRq.setStatus("");
                caseByOrganQueryRq.setApplicant(rq.getApplicant());
                caseByOrganQueryRq.setCaseFlowType("");
                caseByOrganQueryRq.setCellPhone(rq.getCellPhone());
                caseByOrganQueryRq.setId(rq.getId());
                String version = "";
                if (rq.getVersion() != null && rq.getVersion() != 0) version = String.valueOf(rq.getVersion());
                caseByOrganQueryRq.setVersion(version);
                QueryCaseByOrganQueryRs queryCaseByOrgan = queryCaseByOrgan(caseByOrganQueryRq);
                if (queryCaseByOrgan != null) {
                    //獲取固定欄位
                    excelVoList = getLogDataList(queryCaseByOrgan);
                } //if (queryCaseByOrgan!=null)
                LOGGER.info("exportExcelAction excelVoList.size()=" + excelVoList.size());
                LOGGER.info("exportExcelAction version=" + version);
                String pregnantCaseSetId = SpringUtil.getProperty("pregnantTraffic.caseset");
                if (excelVoList.size() > 0 && !version.equals("")) {
                    //找出該服務案件所有動態表單欄位
                    KgoCaseDetailRepositoryCustomImpl kgoCaseDetailRepositoryCustom = new KgoCaseDetailRepositoryCustomImpl();
                    List<GeoCaseByOrganModel> geoCaseByOrganModels = geoCaseSetAssociateReposCustom.getCaseDetailColumnByCaseSetId(pregnantCaseSetId, rq.getVersion());
                    //整理動態欄位清單
                    for (GeoCaseHandleExcelVo vo : excelVoList) {
                        List<CaseHandleCaseViewSaCaseApplyDataDataGrid> gridList = vo.getDataGrids();
                        if (gridList == null) gridList = new ArrayList<>();
                        for (GeoCaseByOrganModel model : geoCaseByOrganModels) {
                            if (model.getCaseId().equals(vo.getCaseId())) {
                                CaseHandleCaseViewSaCaseApplyDataDataGrid grid = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                                grid.setColumnName(model.getColumnName());
                                LOGGER.info("gridList.getColumnName=" + model.getColumnName());
                                grid.setCaseSetId(model.getCaseSetId());
                                grid.setColumnValue(model.getColumnValue());
                                LOGGER.info("gridList.getColumnValue=" + model.getColumnValue());
                                grid.setColumnId(model.getColumnId());
                                grid.setCaseId(model.getCaseId());
                                gridList.add(grid);
                            }//if (model.getCaseId().equals(vo.getCaseId()))
                        } // for (GeoCaseByOrganModel model:geoCaseByOrganModels)
                        LOGGER.info("gridList.size=" + gridList.size());
                        vo.setDataGrids(gridList);
                    } //for(GeoCaseHandleExcelVo vo :excelVoList)
                }//if(excelVoList.size()>0 && !version.equals(""))
                excelVosGridView = excelVoList;
            } else {
                //一般案件
                CaseHandleCaseViewQueryRs caseViewQueryRs = caseHandleCaseViewQuery(rq);
                excelVoList = getLogDataList(caseViewQueryRs);
                if (caseViewQueryRs.getData().isOnlyOneCaseSetType() && caseViewQueryRs.getData().getFieldCheckList().size() > 0) {
                    excelVosGridView = getColumnNameAndColumnValue(caseViewQueryRs, excelVoList);
                } else {
                    excelVosGridView = excelVoList;
                } //if else
            } //if (rq.getCaseSetId()!=null && rq.getCaseSetId().equals("S2020112500004"))...
            data.put("DATA_LIST", excelVosGridView);
            excelTempExportService.exportCaseViewExcel(fileName, sheetName, data, excelVosGridView);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    }//CaseHandleCaseViewQueryRq

    /**
     * GEO 20221101 Oberyn
     * 後台-案件處理-場地案件檢視:excel匯出
     */
    @Override
    public void siteExportExcelAction(CaseHandleSiteCaseViewQueryRq rq) {
        try {
            String fileName = messageUtil.getMessage("kgo.backend.caseView.site.excel.fileName");
            String sheetName = messageUtil.getMessage("kgo.backend.caseView.site.excel.sheet");
            Map<String, Object> data = new HashMap<>();
            data.put("ORDER", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.order"));
            data.put("CASE_ID", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.caseId"));
            data.put("APPLY_DATE", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.applyDate"));
            data.put("APPLICANT", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.applicant"));
            data.put("CASE_NAME", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.caseName"));
            data.put("SITE_NAME", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.siteName"));
            data.put("START_TIME", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.startTime"));
            data.put("AMOUNT", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.amount"));
            data.put("STATUS", messageUtil.getMessage("kgo.backend.caseView.site.excel.header.status"));
            data.put("CASE_OFFICER", messageUtil.getMessage("kgo.backend.caseView.excel.header.caseOfficer"));
            data.put("LOGIN", messageUtil.getMessage("kgo.backend.caseView.excel.header.login"));

            List<GeoCaseHandleExcelVo> excelVosGridView = new ArrayList<>();
            CaseHandleCaseViewQueryRs caseViewQueryRs = caseHandleSiteCaseViewQuery(rq);
            AtomicInteger order = new AtomicInteger(1);
            List<GeoCaseHandleExcelVo> excelVoList = caseViewQueryRs.getData().getGrid().stream()
                    .map(grid -> GeoCaseHandleSiteExcelVo.chanceDtoDataToVo(grid, order.getAndIncrement())).collect(Collectors.toList());
            for (GeoCaseHandleExcelVo vo : excelVoList) {
                List<CaseHandleCaseViewSaCaseApplyDataDataGrid> gridList = vo.getDataGrids();
                if (gridList == null) gridList = new ArrayList<>();
                List<GeoCaseByOrganModel> detailColumnList = geoCaseSetRentReposCustom.getCaseDetailColumnByCaseSetId(vo.getCaseId());
                for (GeoCaseByOrganModel model : detailColumnList) {
                    if (model.getCaseId().equals(vo.getCaseId())) {
                        CaseHandleCaseViewSaCaseApplyDataDataGrid grid = new CaseHandleCaseViewSaCaseApplyDataDataGrid();
                        grid.setColumnName(model.getColumnName());
                        LOGGER.info("gridList.getColumnName=" + model.getColumnName());
                        grid.setCaseSetId(model.getCaseSetId());
                        grid.setColumnValue(model.getColumnValue());
                        LOGGER.info("gridList.getColumnValue=" + model.getColumnValue());
                        grid.setColumnId(model.getColumnId());
                        grid.setCaseId(model.getCaseId());
                        gridList.add(grid);
                    }//if (model.getCaseId().equals(vo.getCaseId()))
                } // for (GeoCaseByOrganModel model:geoCaseByOrganModels)
                LOGGER.info("gridList.size=" + gridList.size());
                vo.setDataGrids(gridList);
            } //for(GeoCaseHandleExcelVo vo :excelVoList)
            if (caseViewQueryRs.getData().isOnlyOneCaseSetType() && caseViewQueryRs.getData().getFieldCheckList().size() > 0) {
                excelVosGridView = getColumnNameAndColumnValue(caseViewQueryRs, excelVoList);
            } else {
                excelVosGridView = excelVoList;
            } //if else
            data.put("DATA_LIST", excelVosGridView);
            // 匯出excel
            excelTempExportService.exportCaseSiteExcel(fileName, sheetName, data, excelVoList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    }//exportExcelAction

    /**
     * GEO 20211105 add
     * <p>
     * 後台-案件處理-案件檢視:excel固定欄位
     */
    public List<GeoCaseHandleExcelVo> getLogDataList(CaseHandleCaseViewQueryRs caseViewQueryRs) {
        List<CaseHandleCaseQueryDataGrid> caseQueryDataGrids = caseViewQueryRs.getData().getGrid();
        List<GeoCaseHandleExcelVo> geoCaseHandleExcelVos = new ArrayList<>();
        Integer order = 1;
        for (CaseHandleCaseQueryDataGrid dataGrid : caseQueryDataGrids) {
            GeoCaseHandleExcelVo geoCaseHandleExcelVo = new GeoCaseHandleExcelVo();
            geoCaseHandleExcelVo.setOrder(order);
            geoCaseHandleExcelVo.setCaseId(dataGrid.getCaseId());
            geoCaseHandleExcelVo.setCaseName(dataGrid.getCaseName());
            geoCaseHandleExcelVo.setCaseOfficer(dataGrid.getOfficer());
            geoCaseHandleExcelVo.setDeadlineDate(dataGrid.getLimitDate());
            geoCaseHandleExcelVo.setApplyUser(dataGrid.getApplicant());
            geoCaseHandleExcelVo.setApplyDate(dataGrid.getApplyDate());
            geoCaseHandleExcelVo.setStatus(dataGrid.getStatusName());
            geoCaseHandleExcelVo.setLogin(dataGrid.getApplyUserLoginType());
            geoCaseHandleExcelVos.add(geoCaseHandleExcelVo);
            order++;
        } //for (CaseHandleCaseQueryDataGrid dataGrid : caseQueryDataGrids)
        return geoCaseHandleExcelVos;
    } //getLogDataList

    /**
     * GEO 20221103 add
     * <p>
     * 後台-案件處理-好孕案件檢視:excel固定欄位
     */
    public List<GeoCaseHandleExcelVo> getLogDataList(QueryCaseByOrganQueryRs queryCaseByOrganQueryRs) {
        List<CaseMainQueryCaseViewListDto> caseViewListDtos = queryCaseByOrganQueryRs.getData().getGrid();
        List<GeoCaseHandleExcelVo> geoCaseHandleExcelVos = new ArrayList<>();
        Integer order = 1;
        for (CaseMainQueryCaseViewListDto dataGrid : caseViewListDtos) {
            GeoCaseHandleExcelVo geoCaseHandleExcelVo = new GeoCaseHandleExcelVo();
            geoCaseHandleExcelVo.setOrder(order);
            geoCaseHandleExcelVo.setCaseId(dataGrid.getCaseId());
            geoCaseHandleExcelVo.setCaseName(dataGrid.getCaseSetName());
            geoCaseHandleExcelVo.setCaseOfficer(dataGrid.getCaseOfficer());
            String version = "";
            if (dataGrid.getVersion() != null && dataGrid.getVersion() != 0)
                version = String.valueOf(dataGrid.getVersion());
            geoCaseHandleExcelVo.setVersion(version);
            String deadlineDate = "";
            if (dataGrid.getDeadlineDate() != null)
                deadlineDate = DateUtil.timestampToString(dataGrid.getDeadlineDate(), PATTEN_YEAR_MONTH_YEAR_SLASH);
            geoCaseHandleExcelVo.setDeadlineDate(deadlineDate);
            geoCaseHandleExcelVo.setApplyUser(dataGrid.getApplyUser());
            String applyDate = "";
            if (dataGrid.getApplyDate() != null)
                applyDate = DateUtil.timestampToString(dataGrid.getApplyDate(), PATTEN_YEAR_MONTH_YEAR_SLASH);
            geoCaseHandleExcelVo.setApplyDate(applyDate);
            geoCaseHandleExcelVo.setStatus(dataGrid.getStatus());
            String typeName = messageUtil.getMessage("kgo.login.type.unknow");
            if (StringUtils.isNotBlank(dataGrid.getApplyUserLoginType()))
                typeName = LoginAuthTokenType.getLoginAuthType(dataGrid.getApplyUserLoginType()).getTypeName();
            geoCaseHandleExcelVo.setLogin(typeName);
            geoCaseHandleExcelVos.add(geoCaseHandleExcelVo);
            order++;
        } //for (CaseHandleCaseQueryDataGrid dataGrid : caseQueryDataGrids)
        return geoCaseHandleExcelVos;
    } //getLogDataList

    /**
     * GEO 20211105 add
     * 後台-案件處理-案件檢視:獲取excel動態欄位資料
     */
    public List<GeoCaseHandleExcelVo> getColumnNameAndColumnValue(CaseHandleCaseViewQueryRs caseViewQueryRs, List<GeoCaseHandleExcelVo> excelVoList) {
        for (GeoCaseHandleExcelVo excelVo : excelVoList) {
            for (GeoCaseViewFieldCheckModel model : caseViewQueryRs.getData().fieldCheckList) {
                if (excelVo.getCaseId().equals(model.getCaseId())) {
                    excelVo.setDataGrids(model.getApplyDataGrids());
                    break;
                } //if(excelVo.getCaseId().equals(model.getCaseId()))
            } //for (GeoCaseViewFieldCheckModel mode
        } //for (GeoCaseHandleExcelVo excelVo:excelVoList)
        return excelVoList;
    } //getColumnNameAndColumnValue

    /**
     * GEO 20211109 add
     * 後台-案件處理-案件檢視:Json匯出
     */
    @Override
    public void exportJsonAction(CaseHandleCaseViewQueryRq rq) {
        try {
            List<GeoCaseHandleJsonVo> geoCaseHandleJsonVos = new ArrayList<>();
            CaseHandleCaseViewQueryRs caseViewQueryRs = caseHandleCaseViewQuery(rq);
            geoCaseHandleJsonVos = getJsonDataList(caseViewQueryRs);
            if (caseViewQueryRs.getData().isOnlyOneCaseSetType() && caseViewQueryRs.getData().getFieldCheckList().size() > 0) {
                geoCaseHandleJsonVos = getColumnNameAndColumnValueToJson(caseViewQueryRs, geoCaseHandleJsonVos);
            } else {
                geoCaseHandleJsonVos = geoCaseHandleJsonVos;
            } //if else
            String path = "/templates/kgo/json/kgoCaseView/Json.json";
            exportJson(response, geoCaseHandleJsonVos, path);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    } //exportJsonAction

    /**
     * GEO 20211109 add
     * <p>
     * 後台-案件處理-案件檢視:Json匯出格式
     */
    public List<GeoCaseHandleJsonVo> getJsonDataList(CaseHandleCaseViewQueryRs caseViewQueryRs) {
        List<CaseHandleCaseQueryDataGrid> caseQueryDataGrids = caseViewQueryRs.getData().getGrid();
        List<GeoCaseHandleJsonVo> geoCaseHandleExcelVos = new ArrayList<>();
        for (CaseHandleCaseQueryDataGrid dataGrid : caseQueryDataGrids) {
            GeoCaseHandleJsonVo geoCaseHandleJsonVo = new GeoCaseHandleJsonVo();
            geoCaseHandleJsonVo.setCaseId(dataGrid.getCaseId());
            geoCaseHandleJsonVo.setCaseName(dataGrid.getCaseName());
            geoCaseHandleJsonVo.setCaseOfficer(dataGrid.getOfficer());
            geoCaseHandleJsonVo.setDeadlineDate(dataGrid.getLimitDate());
            geoCaseHandleJsonVo.setApplyUser(dataGrid.getApplicant());
            geoCaseHandleJsonVo.setApplyDate(dataGrid.getApplyDate());
            geoCaseHandleJsonVo.setStatus(dataGrid.getStatusName());
            geoCaseHandleExcelVos.add(geoCaseHandleJsonVo);
        } // for (CaseHandleCaseQueryDataGrid dataGrid : caseQueryDataGrids)
        return geoCaseHandleExcelVos;
    } //getJsonDataList

    /**
     * GEO 20211109 add
     * 後台-案件處理-案件檢視:獲取動態欄位資料放入Json格式
     */
    public List<GeoCaseHandleJsonVo> getColumnNameAndColumnValueToJson(CaseHandleCaseViewQueryRs caseViewQueryRs,
                                                                       List<GeoCaseHandleJsonVo> geoCaseHandleJsonVos) {
        List<HashMap> mapList = new ArrayList<>();
        for (GeoCaseViewFieldCheckModel model : caseViewQueryRs.getData().getFieldCheckList()) {
            HashMap hashMap = new HashMap();
            hashMap.put("caseId", model.getCaseId());
            for (CaseHandleCaseViewSaCaseApplyDataDataGrid grid : model.getApplyDataGrids()) {
                hashMap.put(grid.getColumnName(), grid.getColumnValue());
            } //for (GeoCaseViewFieldCheckModel model : caseViewQueryRs.getData().getFieldCheckList())
            mapList.add(hashMap);
        } //for (CaseHandleCaseViewSaCaseApplyDataDataGrid grid : model.getApplyDataGrids())

        for (GeoCaseHandleJsonVo jsonVo : geoCaseHandleJsonVos) {
            for (HashMap hashMap : mapList) {
                if (jsonVo.getCaseId().equals(hashMap.get("caseId"))) {
                    jsonVo.setColumnNameAndValue(hashMap);
                    break;
                } //if(excelVo.getCaseId().equals(model.getCaseId()))
            } //for (GeoCaseViewFieldCheckModel mode
        } //for (GeoCaseHandleExcelVo excelVo:excelVoList)
        return geoCaseHandleJsonVos;
    } //getColumnNameAndColumnValue

    /**
     * GEO 20211109 add
     * <p>
     * Json檔案產生
     */
    public void exportJson(HttpServletResponse response, Object obj, String path) {
        try {
            String jsonString = JSON.toJSONString(obj,
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
            InputStream file = new ClassPathResource(path).getInputStream();

            Writer write = new OutputStreamWriter(new FileOutputStream(String.valueOf(file)), StandardCharsets.UTF_8);
            write.write(jsonString);
            write.flush();
            write.close();

            FileInputStream fis = new FileInputStream(String.valueOf(file));
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("Json.json", "UTF-8"))));
            response.setCharacterEncoding("utf-8");

            OutputStream os = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }//while
            fis.close();
            os.close();
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_DOWNLOAD.getErrorMsgKey(), e);
            throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
        } //try catch
    } //exportJson

    /**
     * GEO 20211129 add
     * 後台-案件處理-案件檢視:Google匯出(CSV)
     */
    @Override
    public void exportGoogleAction(CaseHandleCaseViewQueryRq rq) {
        try {
            List<GeoCaseHandleCsvVo> geoCaseHandleCsvVos = new ArrayList<>();
            CaseHandleCaseViewQueryRs caseViewQueryRs = caseHandleCaseViewQuery(rq);
            geoCaseHandleCsvVos = getCsvDataList(caseViewQueryRs);
            String path = "/templates/kgo/csv/Google.csv";
            exportCSV(response, geoCaseHandleCsvVos, path);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    } //exportGoogleAction

    /**
     * GEO 20211129 add
     * <p>
     * csv檔案產生
     */
    public void exportCSV(HttpServletResponse response, List<GeoCaseHandleCsvVo> geoCaseHandleCsvVoList, String path) {
        try {
            //title欄位-依照google規範
            ArrayList<String> title = new ArrayList<>();
            title.add("Subject");
            title.add("Start Date");
            title.add("End Date");
            title.add("Location");

            InputStream file = new ClassPathResource(path).getInputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(String.valueOf(file)), StandardCharsets.UTF_8);

            //title放入csv
            for (String str : title) {
                outputStreamWriter.write(str);
                outputStreamWriter.write(",");
            } //for (String  str: title)

            //寫完title換行
            outputStreamWriter.write("\r\n");

            //案件資料放入欄位
            for (GeoCaseHandleCsvVo csvVo : geoCaseHandleCsvVoList) {
                if (csvVo.getSubject() != null && csvVo.getStartDate() != null) {
                    outputStreamWriter.write(csvVo.getSubject());
                    outputStreamWriter.write(",");
                    outputStreamWriter.write(csvVo.getStartDate());
                    outputStreamWriter.write(",");
                    outputStreamWriter.write(csvVo.getEndDate());
                    outputStreamWriter.write(",");
                    if (csvVo.getLocation() == null || csvVo.getLocation().isEmpty()) {
                        outputStreamWriter.write("");
                    } else {
                        outputStreamWriter.write(csvVo.getLocation());
                    }
                    outputStreamWriter.write("\r\n");
                } // if (csvVo.getSubject() !=null
            } //for (GeoCaseHandleCsvVo csvVo :geoCaseHandleCsvVoList)

            outputStreamWriter.flush();
            outputStreamWriter.close();

            FileInputStream fis = new FileInputStream(String.valueOf(file));
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("Google.csv", "UTF-8"))));
            response.setCharacterEncoding("utf-8");
            OutputStream os = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            //os.write(0xef) os.write(0xbb) os.write(0xbf) 解決亂碼問題
            while ((len = fis.read(buf)) != -1) {
                os.write(0xef);
                os.write(0xbb);
                os.write(0xbf);
                os.write(buf, 0, len);
            }//while
            fis.close();
            os.close();
        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
            throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
        } //try catch
    } //exportCvs

    /**
     * GEO 20211129 add
     * <p>
     * 後台-案件處理-案件檢視:csv匯出格式
     */
    public List<GeoCaseHandleCsvVo> getCsvDataList(CaseHandleCaseViewQueryRs caseViewQueryRs) {
        List<CaseHandleCaseQueryDataGrid> caseQueryDataGrids = caseViewQueryRs.getData().getGrid();
        List<GeoCaseHandleCsvVo> voList = new ArrayList<>();
        //案件與欄位清單結合
        for (CaseHandleCaseQueryDataGrid grid : caseQueryDataGrids) {
            GeoCaseHandleCsvVo csvVo = new GeoCaseHandleCsvVo();
            csvVo.setSubject(grid.getCaseName());
            List<GeoCaseColumnModel> modelList
                    = geoKgoCaseQueryReposCustom.getCaseColumnList(grid.getCasesetId(), grid.getCaseId());
            boolean isFirst = true;
            for (GeoCaseColumnModel model : modelList) {
                if (isFirst && (grid.getCaseId().equals(model.getCaseId()))) {
                    csvVo.setCaseId(model.getCaseId());
                    isFirst = false;
                } //if (isFirst && (grid.getCaseId()

                if ((model.getColumnType().equals(ColumnTypeEnum.TEXT.getValue()) && model.getColumnName().equals("地點"))) {
                    csvVo.setLocation(model.getColumnValue());
                } //if ((model.getColumnType()

                if ((model.getColumnType().equals(ColumnTypeEnum.DATE_SE.getValue()))) {
                    try {
                        String[] dataStr = model.getColumnValue().split("-");
                        csvVo.setStartDate(DateUtil.strDateFormat(dataStr[0], DateUtil.PATTEN_YEAR_MONTH_YEAR_SLASH, DateUtil.PATTEN_DAY_MONTH_YEAR));
                        csvVo.setEndDate(DateUtil.strDateFormat(dataStr[1], DateUtil.PATTEN_YEAR_MONTH_YEAR_SLASH, DateUtil.PATTEN_DAY_MONTH_YEAR));
                    } catch (Exception e) {
                        LOGGER.error(KgoBackEndApiError.WRONG_PARAMETER.getErrorMsgKey(), e);
                        throw new KgoApiException("caseHandleCaseViewSaCaseHome error " + e.getMessage(), e);
                    } //try
                } // if ((model.getColumnType()
            } // for (GeoCaseColumnModel model:modelList)
            voList.add(csvVo);
        } // for (CaseHandleCaseQueryDataGrid grid
        return voList;
    } //getCsvDataList

    /**
     * GEO 20211109 add 機關審核表單
     * 後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單
     *
     * @param rq
     * @return CaseHandleCaseViewUraCaseHomeRs
     */
    @Override
    public CaseHandleCaseViewQueryOrganFormRs caseViewQueryOrganForm(CaseHandleCaseViewQueryOrganFormRq rq) {
        CaseHandleCaseViewQueryOrganFormViewForm viewForm = new CaseHandleCaseViewQueryOrganFormViewForm();
        CaseHandleCaseViewQueryOrganFormRs rs = new CaseHandleCaseViewQueryOrganFormRs();
        try {
            List<OptionViewForm> options = new ArrayList<OptionViewForm>();
            List<InternetApplyFormSetHomeOrganDataGrid> organFormDataGridList = new LinkedList<InternetApplyFormSetHomeOrganDataGrid>();
            Integer organVersion = null;
            KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(rq.getCaseId());
            String caseSetId = caseMain.getCaseSetId();
            List<KgoCaseDetail> detailList = kgoCaseDetailRepository.findByIdCaseId(rq.getCaseId());
            Integer version = detailList.get(0).getId().getVersion();
            boolean hadKd = false; //有派工欄位
            boolean hadNew = false; //有陳情欄位

            if (caseMain.getIsOpenOrganForm() != null && caseMain.getIsOpenOrganForm() == GeoBooleanType.ENABLED.getCode()) {
                List<CasesetOrganGroupQueryDataMaxVersionDto> organDtoList = geoKgoCasesetOrganGroupReposCustom.findGroupData(caseSetId, StringUtils.EMPTY, version);
                if (organDtoList != null) {
                    LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome dtoList.size(): " + organDtoList.size());
                } else {
                    LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome dtoList.size(): " + organDtoList);
                }
                organVersion = ObjectUtils.isEmpty(organDtoList) ? KgoUtil.DEFAULT_VERSION_NUMBER : organDtoList.get(0).getOrganFormVersion();
                if (ObjectUtils.isNotEmpty(organDtoList)) {
                    Integer finalVersion = version;
                    Integer finalOrganVersion = organVersion;
                    organDtoList.forEach(l -> {
                        List<GeoKgoCasesetColumnOrgan> KgoCasesetOrganColumnList = geoKgoCasesetColumnOrganRepository
                                .findByIdCaseSetIdAndGroupSeqOrderByOrderNumAsc(caseSetId, l.getGroupSeq());
                        LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome KgoCasesetOrganColumnList.size(): " + KgoCasesetOrganColumnList.size());

                        List<InternetApplyFormSetOrganGroupColumnDataGrid> internetApplyFormSetGroupColumnQueryDtoList =
                                KgoCasesetOrganColumnList.stream().map(dl -> {
                                    List<List<CasesetOrganComplexColumnData>> complexDataList = null;
                                    if (dl.getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
                                        Map<Integer, List<GeoKgoCasesetColumnChildOrgan>> dataMap = geoKgoCasesetColumnChildOrganRepository
                                                .findByIdCaseSetIdAndIdVersionAndIdColumnIdAndIdCaseFormVersionOrderByOrderNumAsc(caseSetId, finalVersion, dl.getId().getColumnId(), finalOrganVersion)
                                                .stream().collect(Collectors.groupingBy(GeoKgoCasesetColumnChildOrgan::getRow, HashMap::new, Collectors.toCollection(LinkedList::new)));
                                        complexDataList = dataMap.keySet().stream().map(i -> {
                                            return dataMap.get(i).stream().map(cl -> {
                                                String vGroup = StringUtils.isBlank(cl.getVGroup()) ? StringUtils.EMPTY : cl.getVGroup();
                                                CasesetOrganComplexColumnData complexData = new CasesetOrganComplexColumnData();
                                                complexData.setbText(cl.getBText());
                                                complexData.setcColumnId(cl.getId().getCColumnId());
                                                complexData.setColumnType(cl.getColumnType());
                                                complexData.setColumnValue(cl.getColumnValue());
                                                complexData.setfText(cl.getFText());
                                                complexData.setLength(cl.getLength());
                                                complexData.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(cl.getIsMustKey()).getValue());
                                                complexData.setIsCheckFrequency(cl.getIsCheckFrequency());
                                                complexData.setIsFieldCheck(cl.getIsFieldCheck());
                                                complexData.setOrderNum(cl.getOrderNum());
                                                complexData.setpColumnId(cl.getPColumnId());
                                                complexData.setRow(cl.getRow());
                                                complexData.setCaseFormVersion(cl.getId().getCaseFormVersion());
                                                complexData.setvGroup(vGroup);
                                                return complexData;
                                            }).collect(Collectors.toList()); //return dataMap.get(i).stream()
                                        }).collect(Collectors.toList()); //complexDataList = dataMap.keySet()
                                    } //if (dl.getColumnType()...

                                    InternetApplyFormSetOrganGroupColumnDataGrid dg = new InternetApplyFormSetOrganGroupColumnDataGrid();
                                    dg.setColumnId(dl.getId().getColumnId());
                                    dg.setColumnName(dl.getColumnName());
                                    dg.setColumnType(dl.getColumnType());
                                    dg.setColumnValue(dl.getColumnValue());
                                    dg.setColumnTypeName(ColumnTypeEnum.getColumnTypeEnum(dl.getColumnType()).getLabel());

                                    IsMustKeyEnum aEnum = IsMustKeyEnum.getIsMustKeyEnum(dl.getIsMustKey());
                                    dg.setIsMustKey(aEnum.getValue());
                                    dg.setIsMustKeyStr(aEnum.getLabel());

                                    IsCHeckFrequencyEnum bEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(dl.getIsCheckFrequency());
                                    dg.setIsCheckFrequency(bEnum.getValue());
                                    dg.setIsCheckFrequencyStr(bEnum.getLabel());

                                    IsFieldCheckEnum fieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(dl.getIsFieldCheck());
                                    dg.setIsFieldCheck(fieldCheckEnum.getValue());
                                    dg.setIsFieldCheckStr(fieldCheckEnum.getLabel());

                                    dg.setLength(dl.getLength());
                                    dg.setMemo(dl.getMemo());
                                    dg.setOrderNum(dl.getOrderNum());
                                    if (FIL.getValue().equals(dl.getColumnType())) dg.setFileType(dl.getFileType());
                                    dg.setComplex(complexDataList);
                                    return dg;
                                }).collect(Collectors.toList()); //List<InternetApplyFormSetOrganGroupColumnDataGrid>

                        InternetApplyFormSetHomeOrganDataGrid dataGrid = new InternetApplyFormSetHomeOrganDataGrid();
                        dataGrid.setColumnData(internetApplyFormSetGroupColumnQueryDtoList);
                        dataGrid.setGroupSeq(l.getGroupSeq());
                        dataGrid.setGroupName(l.getMemo());
                        dataGrid.setOrderNum(l.getOrderNum());
                        dataGrid.setIsShow(l.getIsShow());
                        organFormDataGridList.add(dataGrid);
                    }); //organDtoList.forEach
                } //if (ObjectUtils.isNotEmpty(organDtoList))

            } //if (caseSet.getIsOpenOrganForm()

            caseFormService.setColumnOptions(options, hadKd, hadNew);
            viewForm.setVersion(version);
            viewForm.setCaseSetId(caseSetId);
            viewForm.setOrganFormGrid(organFormDataGridList);
            viewForm.setOrganFormVersion(organVersion);
            viewForm.setOptions(options);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("internetApplyFormSetHome error " + e.getMessage(), e);
        }
        return rs;
    } //caseViewQueryOrganForm

    /**
     * GEO 20211115 add for 跨機關檢視
     * 後台案件處理-跨機關檢視-案件查詢
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleCaseViewQueryRs caseHandleCrossViewQuery(CaseHandleCaseViewQueryRq rq) {
        CaseHandleCaseViewQueryViewForm viewForm = new CaseHandleCaseViewQueryViewForm();
        CaseHandleCaseViewQueryRs rs = new CaseHandleCaseViewQueryRs();
        try {
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String loginUserId = backendLoginUser.getUserId();
            String loginOrganId = backendLoginUser.getOrgan();

            // 驗證參數
            this.validateParameter(rq);

            String applicant = rq.getApplicant();
            List<String> applyDate = rq.getApplyDate();
            String applyStartDate = null;
            String applyEndDate = null;

            if (!CollectionUtils.isEmpty(applyDate)) {
                if (APPLY_START_DATE_INDEX < applyDate.size()) {
                    applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                }
                if (APPLY_END_DATE_INDEX < applyDate.size()) {
                    applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                }
            }
            String caseId = rq.getCaseId();
            String caseName = rq.getCaseName();
            String status = rq.getStatus();
            String caseFlowType = rq.getCaseFlowType();
            List<String> statusList;
            if (StringUtils.isNotEmpty(status)) {
                statusList = Arrays.asList(status.split(","));
            } else {
                statusList = new ArrayList<>();
            }

            List<CaseMainQueryCaseViewListDto> caseDtoList = new LinkedList<>();
            List<String> userRoleList = getUserRoleList(loginUserId);
            List<CaseMainQueryCaseViewListDto> caseMainQueryDtosSA = kgoCaseMainRepository.CrossReviewQuery(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, applicant, applyStartDate,
                    applyEndDate, caseName, caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone());
            caseDtoList.addAll(caseMainQueryDtosSA);// SA
            List<CaseHandleCaseQueryDataGrid> gridList = getRsGridDataCaseView(caseDtoList, null);

            /** GEO 20211102 add 申請人登入方式，欄位勾選 */
            List<GeoCaseViewFieldCheckModel> fieldCheckList = new ArrayList<>();
            boolean isOnlyOneCaseSetType = false;
            if (gridList != null && gridList.size() > 0) {
                Set<String> caseSetIdSet = new LinkedHashSet<>();
                Set<Integer> caseSetVersion = new LinkedHashSet<>();
                for (CaseHandleCaseQueryDataGrid g : gridList) {
                    caseSetIdSet.add(g.getCasesetId());
                    caseSetVersion.add(g.getVersion());
                }
                if (caseSetIdSet.size() == 1 && caseSetVersion.size() == 1) {
                    isOnlyOneCaseSetType = true;
                    for (CaseHandleCaseQueryDataGrid g : gridList) {
                        GeoCaseViewFieldCheckModel model = new GeoCaseViewFieldCheckModel();
                        model.setCaseId(g.getCaseId());
                        List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyDataGrids = getFieldCheck(g.getCaseId());
                        model.setApplyDataGrids(applyDataGrids);
                        fieldCheckList.add(model);
                    }
                } //if (caseSetIdSet.size() == 1
            } //	if (gridList != null && gridList.size() > 0)
            viewForm.setFieldCheckList(fieldCheckList);
            viewForm.setOnlyOneCaseSetType(isOnlyOneCaseSetType);
            viewForm.setGrid(gridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("caseHandleCrossViewQuery error " + e.getMessage(), e);
        }

        return rs;
    } //caseHandleCrossViewQuery

    /**
     * GEO 20211115 add for 跨機關檢視
     * 後台案件處理-跨機關檢視-取得備註
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleCrossReviewCommentQueryRs queryComment(CaseHandleCrossReviewCommentQueryRq rq) {
        CaseHandleCrossReviewCommentViewForm viewForm = new CaseHandleCrossReviewCommentViewForm();
        CaseHandleCrossReviewCommentQueryRs rs = new CaseHandleCrossReviewCommentQueryRs();
        GeoKgoCaseCrossReviewComment comment = null;
        try {
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String userId = rq.getUserId();
            List<String> roles = backendLoginUser.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                if (!roles.contains(KgoRoleEnum.CASE_M.getValue())
                        && !roles.stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)
                        && (StringUtils.isNotBlank(userId))) {
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.PERMISSION_DENIED));
                }
            }
            if (StringUtils.isBlank(userId)) {
                userId = backendLoginUser.getUserId();
            }
            List<GeoKgoCaseCrossReviewComment> commentResult = geoKgoCaseCrossReviewCommentRepository.findByCaseIdAndUserId(rq.getCaseId(), userId);
            if (commentResult != null && commentResult.size() > 0) {
                comment = commentResult.get(0);
            } else {
                comment = new GeoKgoCaseCrossReviewComment();

            }
            viewForm.setCrossReviewCommentId(comment.getCrossReviewCommentId() == null ? StringUtils.EMPTY : comment.getCrossReviewCommentId());
            viewForm.setComment(comment.getComment() == null ? StringUtils.EMPTY : comment.getComment());
            viewForm.setCaseId(comment.getCaseId() == null ? StringUtils.EMPTY : comment.getCaseId());
            viewForm.setUserId(comment.getUserId() == null ? StringUtils.EMPTY : comment.getUserId());
            String commentUserId = (comment.getUserId() == null) ? StringUtils.EMPTY : comment.getUserId();
            viewForm.setEdit(rq.getUserId().isEmpty() || commentUserId.equals(backendLoginUser.getUserId()));

            if (viewForm.getEdit()) {
                viewForm.setOrganId(comment.getOrganId() == null ? backendLoginUser.getOrgan() : comment.getOrganId());
                viewForm.setUnitId(comment.getUnitId() == null ? backendLoginUser.getUnit() : comment.getUnitId());
            } else {
                viewForm.setOrganId(comment.getOrganId() == null ? StringUtils.EMPTY : comment.getOrganId());
                viewForm.setUnitId(comment.getUnitId() == null ? StringUtils.EMPTY : comment.getUnitId());
            }

            String organName = KgoUtil.getOrganName(viewForm.getOrganId());
            organName = StringUtils.isEmpty(organName) ? StringUtils.EMPTY : organName;
            viewForm.setOrganName(organName);
            KgoUnit unit = null;
            KgoUnitPK pk = new KgoUnitPK();
            pk.setOrganId(viewForm.getOrganId());
            pk.setUnitId(viewForm.getUnitId());
            Optional<KgoUnit> optional = kgoUnitRepository.findById(pk);
            if (optional.isPresent()) unit = optional.get();
            viewForm.setUnitName(unit == null ? StringUtils.EMPTY : unit.getUnitName());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("queryComment error " + e.getMessage(), e);
        } //try
        return rs;
    } //queryComment

    /**
     * GEO 20211115 add for 跨機關檢視
     * 後台案件處理-跨機關檢視-新增/編輯備註
     *
     * @param rq
     * @return
     */
    @Override
    public CaseHandleCrossReviewCommentEditRs editComment(CaseHandleCrossReviewCommentEditRq rq) {
        CaseHandleCrossReviewCommentViewForm viewForm = new CaseHandleCrossReviewCommentViewForm();
        CaseHandleCrossReviewCommentEditRs rs = new CaseHandleCrossReviewCommentEditRs();
        GeoKgoCaseCrossReviewComment comment = null;
        try {
            String crossReviewCommentId = rq.getCrossReviewCommentId();
            if (StringUtils.isBlank(crossReviewCommentId)) {
                comment = new GeoKgoCaseCrossReviewComment();
                crossReviewCommentId = geoCaseCrossReviewCommentCustom
                        .getNextTableId(GeoStringUtil.CASE_CROSS_REVIEW_PREFIX_CHAR, "GEO_KGO_CASE_CROSS_REVIEW_COMMENT", "CrossReviewCommentId");
                LOGGER.info("CaseHandleServiceImpl editComment");
                comment.setCrossReviewCommentId(crossReviewCommentId);
            } else {
                comment = geoKgoCaseCrossReviewCommentRepository.getOne(crossReviewCommentId);
                if (comment == null) throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
            }
            comment.setCaseId(rq.getCaseId());
            comment.setOrganId(rq.getOrganId());
            comment.setUnitId(rq.getUnitId());
            comment.setUserId(rq.getUserId());
            comment.setComment(rq.getComment());
            geoKgoCaseCrossReviewCommentRepository.save(comment);

            viewForm.setCrossReviewCommentId(comment.getCrossReviewCommentId());
            viewForm.setComment(comment.getComment());
            viewForm.setOrganId(comment.getOrganId());
            viewForm.setUnitId(comment.getUnitId());
            viewForm.setCaseId(comment.getCaseId());
            viewForm.setUserId(comment.getUserId());
            viewForm.setEdit(comment.getUserId().equals(KgoUtil.getBackendLoginUser().getUserId()));
            String organName = KgoUtil.getOrganName(comment.getOrganId());
            organName = StringUtils.isEmpty(organName) ? StringUtils.EMPTY : organName;
            viewForm.setOrganName(organName);

            KgoUnit unit = null;
            KgoUnitPK pk = new KgoUnitPK();
            pk.setOrganId(comment.getOrganId());
            pk.setUnitId(comment.getUnitId());
            Optional<KgoUnit> optional = kgoUnitRepository.findById(pk);
            if (optional.isPresent()) unit = optional.get();
            viewForm.setUnitName(unit == null ? StringUtils.EMPTY : unit.getUnitName());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("queryComment error " + e.getMessage(), e);
        } //try
        return rs;
    } //editComment


    @Override
    public DiscountCountRs discountRentCase(DiscountCountRq rq) {
        LOGGER.info("disocunt default query start ... ");
        DiscountCountViewForm viewForm = new DiscountCountViewForm();
        DiscountCountRs rs = new DiscountCountRs();
        String caseId = rq.getCaseId();
        String organId = rq.getOrganId();
        try {
            KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);
            KgoCaseset caseset = kgoCasesetRepository.getById(caseMain.getCaseSetId());
            if(caseMain.getCaseSetId().equals(SpringUtil.getProperty("applyRefund.caseset"))){//退費結案
                KgoCaseDetail kgoCaseDetail = kgoCaseDetailRepository.findRefurnIdByCaseId(caseId, "refurnCaseId");
                String refundCaseId = kgoCaseDetail.getColumnValue();
                GeoKgoCaseRentPayment refundPayment = geoKgoRentPaymentRepos.findByCaseId(refundCaseId);
                KgoCaseMain refundCaseMain = kgoCaseMainRepository.findByCaseId(refundCaseId);
                //計算退費天數折價
                GeoKgoCaseRentRelation refundRelation = geoCaseRentRelationRepos.findByCaseId(refundCaseId);
                Timestamp firstRentDate = refundRelation.getStartTime();

                //距離預約時間剩餘天數
                Long remaining = ( firstRentDate.getTime() - caseMain.getApplyDate().getTime() ) / ( 1000 * 60 * 60 * 24 );
                Integer percent = refundRatioRepository.findRefundPercent(refundRelation.getCasesetId(),remaining.intValue());
                if( percent == null) percent = 100;
                LOGGER.info("remaining days :" + remaining + " - discount percent :" + percent );
                String msg = String.format("<span>※退費金額上限為元繳費金額<br /> 依申請日期計算退費比率為 %d %%</span>",percent);
                if (StringUtils.isBlank(organId)) {
                    organId = refundCaseMain.getCaseOrgan();
                }
                List<OrganDiscountRatio> lmodel = geoOrganDiscountRatioRepository.findAllByOrganIdOrderByDiscountRatio(organId);
                ComboBox combox = KgoServiceHelper.getInstance().getComboBox(lmodel, "discountRatio", "discountRatio", null, ComboBoxStatusEnum.ALL.getCode(), false);

                viewForm.setAmount(refundPayment.getPayAmount());//實際繳費金額
                viewForm.setDeductPercent(percent);//退費折價百分比
                viewForm.setDiscountComboBox(combox);
                viewForm.setInfoMsg(msg);
                viewForm.setRentalCase(2);

            } else if (caseset.getNeedPay() != null && caseset.getNeedPay()) {//繳費結案
                GeoKgoCaseRentRelation relationEntity = geoCaseRentRelationRepos.findByCaseId(caseId);
                AtomicInteger payPraice = new AtomicInteger(0);
                String[] timeIds = relationEntity.getRentTimeId().split(",");
                Arrays.stream(timeIds).forEach(id -> {
                    GeoKgoCasesetRentTime rentTime = geoKgoCasesetRentTimeRepository.getById(id);
                    if (rentTime != null) payPraice.addAndGet(rentTime.getUnitPrice());
                });

                String msg = String.format("<span>※最後核定金額 = 租借金額*優惠折扣<br /> 租借金額為 %d 元</span>",payPraice.get());
                if (StringUtils.isBlank(organId)) {
                    organId = caseMain.getCaseOrgan();
                }
                List<OrganDiscountRatio> lmodel = geoOrganDiscountRatioRepository.findAllByOrganIdOrderByDiscountRatio(organId);
                ComboBox combox = KgoServiceHelper.getInstance().getComboBox(lmodel, "discountRatio", "discountRatio", null, ComboBoxStatusEnum.ALL.getCode(), false);

                viewForm.setAmount(payPraice.get());
                viewForm.setInfoMsg(msg);
                viewForm.setDiscountComboBox(combox);
                viewForm.setRentalCase(1);
            } else {
                viewForm.setRentalCase(0);
            }
            rs.setMsg(SuccessMsg.UNKNOW.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("find discount combobx and price error " + e.getMessage(), e);
        } //try

        return rs;
    }

    /*
     **後台 案件檢視 - 變更繳費狀態
     */
    @Override
    public GeoCaseChangePaymentRs changePayment(GeoCaseHandlePaymentRq rq) {
        LOGGER.info("CaseHandleService  changePayment  start ... ");
        GeoCaseChangePaymentRs rs = new GeoCaseChangePaymentRs();
        GeoCaseChangePaymentForm viewForm = new GeoCaseChangePaymentForm();
        rs.setData(viewForm);
        String caseId = rq.getCaseId();
        String paymentStatus = rq.getPaymentStatus();
        Timestamp now = DateUtil.getCurrentTimestamp();
        try {
            if (RentStatusEnum.YET.getValue().equals(paymentStatus)) {
                viewForm.setRentalCase(0);
                viewForm.setResultMsg("無法變更繳費狀態為未繳費。");
                rs.setMsg("無法變更繳費狀態為未繳費。");
                return rs;
            }
            GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(caseId);
            if (paymentEntity == null) {
                viewForm.setResultMsg("本案件尚未通過審查階段，無法變更繳費狀態。");
                viewForm.setRentalCase(0);
                rs.setMsg("本案件未通過審查階段，無法變更繳費狀態。");
            } else if(RentStatusEnum.YET.getValue().equals(paymentEntity.getPaymentStatus())){
                geoCaseSetRentService.updateRentRentalPayment(RentStatusEnum.getRentStatusEnum(paymentStatus), GeoPaymentTypeEnum.COMMON, caseId, now,null, KgoUtil.getLoginUserId());
                geoCaseSetRentService.updateRentRelation(RentStatusEnum.SUS, caseId);
                //金流紀錄
                Integer payAmount = paymentEntity.getPayAmount();
                geoKcgPaymentService.savePaymentRecord(caseId, new BigDecimal(payAmount), GeoKgoPaymentExtCaseStatusEnum.PY2, "N");
                viewForm.setRentalCase(1);
                rs.setMsg(SuccessMsg.UPDATE.getMsg());
            }else{
                viewForm.setRentalCase(0);
                viewForm.setResultMsg("本案件非繳費階段，無法更改狀態。");
                rs.setMsg("本案件非繳費階段，無法更改狀態。");
                return rs;
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("caseHadleController  changePayment  error " + e.getMessage(), e);
        }
        return rs;
    }


    public static void genQRCode(String barcodeContent, String barcodeClasspath, String fileName)
            throws WriterException, IOException {
        LOGGER.info("GenQRcdoe start ... fileName = " + fileName);
        // Create the ByteMatrix for the QR-Code that encodes the given String
        int size = 200;
        //File qrFile = new File(path);
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(barcodeContent, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        File fileDir = new File(barcodeClasspath);
        if (!fileDir.exists() || !fileDir.isDirectory()) fileDir.mkdirs();
        ImageIO.write(image, "png", new File(barcodeClasspath + File.separator + fileName));
    }

/**
 public static void main(String[] args) throws WriterException, IOException {
 genQRCode("http://www.google.com","test.png");
 System.out.println("DONE");
 }
 **/

    /**
     * * 案件查詢 - 好孕行得通 roy
     */
    @Override
    public QueryCaseByOrganQueryRs queryCaseByOrgan(CaseByOrganQueryRq rq) {
        QueryCaseByOrganViewForm viewForm = new QueryCaseByOrganViewForm();
        QueryCaseByOrganQueryRs rs = new QueryCaseByOrganQueryRs();
        LOGGER.info("#案件查詢:" + StringUtils.defaultIfBlank(rq.getOrganId(),"") + "organ");
        try {
            BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
            String loginUserId = backendLoginUser.getUserId();

            String applicant = rq.getApplicant();
            List<String> applyDate = rq.getApplyDate();
            String applyStartDate = null;
            String applyEndDate = null;

            if (!CollectionUtils.isEmpty(applyDate)) {
                if (APPLY_START_DATE_INDEX < applyDate.size()) {
                    applyStartDate = applyDate.get(APPLY_START_DATE_INDEX);
                }
                if (APPLY_END_DATE_INDEX < applyDate.size()) {
                    applyEndDate = applyDate.get(APPLY_END_DATE_INDEX);
                }
            }
            String organId = rq.getOrganId();
            String caseId = rq.getCaseId();
            String caseName = rq.getCaseName();
            String status = rq.getStatus();
            String caseFlowType = rq.getCaseFlowType();
            String caseSet = SpringUtil.getProperty("pregnantTraffic.caseset");
            String version = "";
            if (rq.getVersion() != null && !rq.getVersion().equals("")) {
                if (!rq.getVersion().equals("0")) version = rq.getVersion();
            }
            List<String> statusList;
            if (StringUtils.isNotEmpty(status)) {
                statusList = Arrays.asList(status.split(","));
            } else {
                statusList = new ArrayList<>();
            }
            List<CaseMainQueryCaseViewListDto> caseMainQueryDtList = new ArrayList<>();
            caseMainQueryDtList = kgoCaseMainRepository.caseByOrganQuery(StringUtils.EMPTY, organId, applicant, applyStartDate,
                    applyEndDate, caseName, caseId, statusList, caseFlowType, rq.getId(), rq.getCellPhone(), caseSet, version);
            viewForm.setGrid(caseMainQueryDtList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("queryCaseByOrgan error " + e.getMessage(), e);
        }

        return rs;
    }

    /**
     * * 機關查詢 - 好孕行得通 roy
     */
    @Override
    public PregnantOrganQueryRs pregnantOrganQuery() {
        PregnantOrganQueryViewForm viewForm = new PregnantOrganQueryViewForm();
        PregnantOrganQueryRs rs = new PregnantOrganQueryRs();

        BackendLoginUserInfo backendLoginUserinfo = KgoUtil.getBackendLoginUser();
        String loginUserId = backendLoginUserinfo.getUserId();
        LOGGER.info("#myOrgan:" + backendLoginUserinfo.getOrgan());


        String myOrgan = backendLoginUserinfo.getOrgan();
        LOGGER.info("#判斷是市府或非市府機關2:" + myOrgan);
        //市府員工
        Boolean belongKgo = false;
        if (myOrgan != null && StringUtils.isNotBlank(myOrgan)) {
            viewForm.setMyOrgan(myOrgan);
            KgoOrgan KgoOrgan = kgoOrganRepository.findByOrganId(myOrgan);
            if (KgoOrgan != null) {
                belongKgo = KgoOrgan.getBelongKgo();
                if (belongKgo == null) {
                    belongKgo = true;
                }
            }
            //非市府員工
        } else {
            if (LoginAuthTokenType.HCA == backendLoginUserinfo.getLoginAuthTokenType()) {
                String cardNumber = backendLoginUserinfo.getKcgHcaCardSsoInfo().getHcaCardNumber();
                LOGGER.info("#設定非市府員工登入之機關2:" + cardNumber);
                if (cardNumber != null) {
                    List<String> myOrgans = geoKgoHcaOrganRepository.findByHcaCardNumber(cardNumber).stream().map(x -> x.getOrganId()).collect(Collectors.toList());
                    if (myOrgans.size() != 0) {
                        viewForm.setMyOrgan(myOrgans.get(0));
                        myOrgan = myOrgans.get(0);
                        LOGGER.info("#設定非市府員工登入之機關2:" + myOrgans.get(0));
                    } else {
                        viewForm.setMyOrgan("unknown");
                    }
                }
            }
        }
        viewForm.setBelongToKgo(belongKgo);

        String caseSet = SpringUtil.getProperty("pregnantTraffic.caseset");
        List<GeoKgoCasesetAllowOrgan> listOrgans = geoKgoCasesetAllowOrganCustom.findAllowOrganByCasesetId(caseSet, myOrgan, belongKgo);
        viewForm.setGrid(listOrgans);

        rs.setData(viewForm);

        return rs;
    }
    @Override
    public void exportRentableExcel(CaseHandleSiteRentableExcelRq rq){
        try {
            String periodFrom = rq.getPeriodFrom();
            String periodTo = rq.getPeriodTo();
            if(StringUtils.isNotBlank(periodFrom) && StringUtils.isNotBlank(periodTo)){
                boolean validFormat = DateUtil.isValidFormat("yyyyMMdd", periodFrom, Locale.TAIWAN) ?
                                        DateUtil.isValidFormat("yyyyMMdd", periodTo, Locale.TAIWAN) ? true : false
                                      : false;
                if (!validFormat) {
                    throw new KgoApiException(new ErrorResult(CaseHandleApiError.PROCESS_VALIDATE_ERROR, "搜尋日期區間格式不符"));
                }
            }

            String fileName = messageUtil.getMessage("kgo.backend.caseView.rental.excel.fileName");
            String[] sheetNames = new String[]{
                    messageUtil.getMessage("kgo.backend.caseView.rental.excel.sheet1"),
                    messageUtil.getMessage("kgo.backend.caseView.rental.excel.sheet2")
            };

            Map<String, Object> data = new HashMap<>();
            data.put("ORDER", messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.order"));
            data.put("CASESET_ID", messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.casesetId"));
            data.put("CASESET_NAME",messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.casesetName"));
            data.put("ORGAN_NAME", messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.organName"));
            data.put("UNIT_NAME",messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.unitName"));
            data.put("SITE_NAME", messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.siteName"));
            data.put("RENT_DATE", messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.rentDate"));
            data.put("RENT_TITLE", messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.rentTime"));
            data.put("RENTED_TITLE",messageUtil.getMessage("kgo.backend.caseView.rental.excel.header.rentedTime"));
            List<GeoCaseRentalCaseExcelVo> rentableExcelVoList = geoCaseSetRentService.findCaseRentableData(rq);
            List<GeoCaseRentalCaseExcelVo> rentedExcelVoList = geoCaseSetRentService.findCaseRentedData(rq);
            data.put("DATA_LIST", rentableExcelVoList);
            data.put("DATA2_LIST", rentedExcelVoList);
            // 匯出excel
            excelTempExportService.exportCaseRentableExcel(fileName, sheetNames, data);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    }//exportExcelAction

    @Override
    public ApiBaseResponse<RentableQueryComboBoxViewForm> rentableQueryComboBox(GeoRentableComboBoxRq rq){
        RentableQueryComboBoxViewForm viewForm = new RentableQueryComboBoxViewForm();
        ApiBaseResponse<RentableQueryComboBoxViewForm> rs = new ApiBaseResponse<>();
        String organId = rq.getOrganId();
        String unitId = rq.getUnitId();
        String siteMainId = rq.getSiteMainId();
        String siteType = rq.getSiteType();
        try {
            List<KgoOrgan> kgoOrganList = kgoOrganRepository.findAll().stream()
                    .filter(x -> StringUtils.isNotBlank(x.getOrganName())).collect(Collectors.toList());
            ComboBox  organComboBox =  organUnitManagementServiceHelper.getComboBox(kgoOrganList, "organName", "organId", organId,
                                       ComboBoxStatusEnum.ALL.getCode(), true);
            viewForm.setOrganComboBox(organComboBox);

            List<KgoUnit> kgoUnitList = kgoUnitRepository.findAllByIdOrganId(organId);
            ComboBox  unitComboBox =  organUnitManagementServiceHelper.getComboBox(kgoUnitList, "unitName", "unitId", unitId,
                    ComboBoxStatusEnum.ALL.getCode(), true);
            viewForm.setUnitComboBox(unitComboBox);

            List<GeoKgoCaseSetSiteMain> siteMainList = geoCaseSetSiteRepos.findAllByUnitId(unitId);
            ComboBox siteCombobBox = kgoServiceHelper.getComboBox(siteMainList, "siteName", "siteMainId", siteMainId, ComboBoxStatusEnum.ALL.getCode(), true);
            viewForm.setSiteComboBox(siteCombobBox);

            List<CaseSetCategoryEnum> list = Arrays.asList(new CaseSetCategoryEnum[]{CaseSetCategoryEnum.ACTIVITY,CaseSetCategoryEnum.SITE});
            ComboBox categroyComboBox = KgoServiceHelper.getInstance().getComboBox(list, "label", "value", siteType, ComboBoxStatusEnum.ALL.getCode(), true);
            categroyComboBox.setSelectedVal(rq.getSiteType());

            viewForm.setCategoryComboBox(categroyComboBox);
            rs.setData(viewForm);
            rs.setMsg(SuccessMsg.UNKNOW.getMsg());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("rentableQueryComboBox error " + e.getMessage(), e);
        }

        return rs;
    }

    private void sentRentCaseEmail(KgoCaseset kgoCaseset, String email, String status, String result, String caseId, Map<String, Object> noticeMap) {

        try {
            // 寄出email
                Map<String, Object> model = new HashMap<>();
                List filePath = new ArrayList<>();
                boolean deleteFile = "Y".equals(noticeMap.get("delete"));
                model.put("caseSetName", kgoCaseset.getCaseSetName());
                model.put("caseId", caseId);
                model.put("caseUrl", String.format(frontendCaseSearchLink, caseId));
                model.put("caseDescription", StringUtils.defaultIfBlank(result, "完成申請"));
                String templateFile = null;
                if(noticeMap.containsKey("rentCase")){
                    model.put("caseStatus", status);
                    model.put("linkUrl", noticeMap.get("linkUrl"));
                    model.put("payAmount",noticeMap.get("payAmount"));
                    model.put("paymentDiscount",noticeMap.get("paymentDiscount"));
                    model.put("actualAmount",noticeMap.get("actualAmount"));
                    filePath.add(noticeMap.get("barcodeClasspath").toString());
                    templateFile = "caseRentalEmail.html";
                }
                if(noticeMap.containsKey("refundCase")){
                    model.put("payAmount",noticeMap.get("payAmount"));
                    model.put("deductPercent",noticeMap.get("deductPercent"));
                    model.put("refundAmount",noticeMap.get("refundAmount"));
                    model.put("refundDiscount",noticeMap.get("refundDiscount"));
                    model.put("actualrefundAmount",noticeMap.get("actualrefundAmount"));
                    templateFile = "caseRefundEmail.html";
                }

                mailUtil.sendTemplateMailwithImg(new String[]{email}, null, messageUtil.getMessage("kgo.backend.case.handle.complete.mail.title"), model, templateFile, filePath, deleteFile);
                LOGGER.info("caseHandleController complete  doNotifyComplete no email to send empty String");
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_EMAIL.getErrorMsgKey());
        }
    }

}
