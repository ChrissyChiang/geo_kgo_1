package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.geomodel.CaseHandleSiteCaseViewQueryRq;
import gov.kcg.kgo.geomodel.CaseHandleSiteRentableExcelRq;
import gov.kcg.kgo.georepository.custom.GeoKgoCasesetAllowOrganCustom;
import gov.kcg.kgo.geoservice.GeoCaseSetSiteService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseHandlePaymentRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoRentableComboBoxRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseChangePaymentRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.RentableQueryComboBoxViewForm;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.CaseHandleService;
import gov.kcg.kgo.viewModel.backend.caseHadle.addNote.rq.CaseHandleAddNoteRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rq.CaseHandleCaseUpdateQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rs.CaseHandleCaseUpdateQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rq.CaseHandleCaseUpdateUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rs.CaseHandleCaseUpdateUpdateRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.home.rs.CaseHandleCaseViewHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq.CaseByOrganQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq.CaseHandleCaseViewQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.CaseHandleCaseViewQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.PregnantOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.QueryCaseByOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rq.CaseHandleCaseViewQueryOrganFormRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rs.CaseHandleCaseViewQueryOrganFormRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.download.rq.CaseHandleCaseViewSaCaseDownloadRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rq.CaseHandleCaseViewSaCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.CaseHandleCaseViewSaCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.updateStatus.rq.CaseHandleCaseViewSaCaseUpdateStatusRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.updateStatus.rs.CaseHandleCaseViewScaCaseUpdateStatueRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rq.CaseHandleCaseViewScaCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.scaCase.home.rs.CaseHandleCaseViewScaCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rq.CaseHandleCaseViewUraCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rs.CaseHandleCaseViewUraCaseHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rq.CaseHandleCrossReviewCommentEditRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rs.CaseHandleCrossReviewCommentEditRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentEdit.rs.DiscountCountRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rq.CaseHandleCrossReviewCommentQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.CaseHandleCrossReviewCommentQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.delNote.rq.CaseHandleDelNoteRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rq.CaseHandlePendingReviewCancelAcceptRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAccept.rs.CaseHandlePendingReviewCancelAcceptRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rq.CaseHandlePendingReviewCancelAcceptHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rs.CaseHandlePendingReviewCancelAcceptHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.CaseHandlePendingReviewCorrectHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.CaseHandlePendingReviewCorrectUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rs.CaseHandlePendingReviewCorrectHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rs.CaseHandlePendingReviewCorrectUpdateRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq.CaseHandlePendingReviewDeleteFileRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq.CaseHandlePendingReviewDownloadFileRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq.CaseHandlePendingReviewUploadFileRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rs.CaseHandlePendingReviewDeleteFileRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rs.CaseHandlePendingReviewUploadFileRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.CaseHandlePendingReviewHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.query.rq.CaseHandlePendingReviewQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.query.rs.CaseHandlePendingReviewQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.accept.rq.CaseHandlePendingReviewAcceptRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.accept.rs.CaseHandlePendingSignAcceptRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.batchAaccept.rq.CaseHandlePendingReviewBatchAcceptRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rq.CaseHandlePendingSignCompleteRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rq.DiscountCountRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.complete.rs.CaseHandlePendingSignCompleteRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rq.CaseHandlePendingSignDispatchRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.dispatch.rs.CaseHandlePendingSignDispatchRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.doDynamicFlow.rq.CaseHandlePendingSignDoDynamicFlowRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.home.rs.CaseHandlePendingSignHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.query.rq.CaseHandlePendingSignQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.query.rs.CaseHandlePendingSignQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.review.rq.CaseHandlePendingSignReviewRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.review.rs.CaseHandlePendingSignReviewRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.home.rs.CaseHandleReviewedHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rq.CaseHandleReviewedQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.reviewed.query.rs.CaseHandleReviewedQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.signSaCase.home.rq.CaseHandleSignSaCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.signSaCase.home.rs.CaseHandleSignSaCaseHomeRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/backend/caseHandle")
@Api(tags = {"case-handle-controller 後台案件處理"})
public class CaseHandleController extends BaseController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CaseHandleController.class);
  @Autowired
  public CaseHandleService caseHandleService;
  @Autowired
  private ActivitiService activitiService;
  @Autowired
  GeoCaseSetSiteService geoCaseSetSiteService;
  @Autowired
  private GeoKgoCasesetAllowOrganCustom geoKgoCasesetAllowOrganCustom;

  /**
   * 後台案件處理-待簽收匣-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待簽收匣-初始畫面 ")
  @RequestMapping(value = {"/pendingSign/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingSignHomeRs caseHandlePendingSignHomeAction() {
    CaseHandlePendingSignHomeRs rs = caseHandleService.caseHandlePendingSignHome();
    return rs;
  }

  /**
   * 後台案件處理-待簽收匣-案件查詢
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待簽收匣-案件查詢")
  @RequestMapping(value = {"/pendingSign/query"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingSignQueryRs caseHandlePendingSignQuery(@RequestBody CaseHandlePendingSignQueryRq rq) {
    CaseHandlePendingSignQueryRs rs = caseHandleService.caseHandlePendingSignQuery(rq);
    return rs;
  }

  /**
   * 後台案件處理-待簽收匣-簽收
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待簽收匣-簽收")
  @RequestMapping(value = {"/pendingSign/accept"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingSignAcceptRs caseHandlePendingSignAccept(@RequestBody CaseHandlePendingReviewAcceptRq rq) {
    CaseHandlePendingSignAcceptRs rs = caseHandleService.caseHandlePendingSignAccept(rq);
    return rs;
  }

  /**
   * 後台案件處理-待簽收匣-批次簽收
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待簽收匣-批次簽收")
  @RequestMapping(value = {"/pendingSign/batchAccept"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public List<CaseHandlePendingSignAcceptRs> caseHandlePendingSignBatchAccept(@RequestBody CaseHandlePendingReviewBatchAcceptRq rq) {
    return caseHandleService.caseHandlePendingSignBatchAccept(rq);
  }

  /**
   * 後台案件處理-待簽收匣-批次簽收
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待簽收匣-分案/批次分文dispatch")
  @RequestMapping(value = {"/pendingSign/dispatch"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public List<CaseHandlePendingSignDispatchRs> caseHandlePendingSignDispatch(@RequestBody CaseHandlePendingSignDispatchRq rq) {
    return caseHandleService.caseHandlePendingSignDispatch(rq);
  }

  /**
   * 後台案件處理-待簽收匣-檢視
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待簽收匣-檢視")
  @RequestMapping(value = {"/pendingSign/review"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingSignReviewRs caseHandlePendingSignReview(@RequestBody CaseHandlePendingSignReviewRq rq) {
    return caseHandleService.caseHandlePendingSignReview(rq);
  }

  /**
   * 後台案件處理-待審核匣-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-初始畫面 ")
  @RequestMapping(value = {"/pendingReview/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingReviewHomeRs caseHandlePendingReviewHomeAction() {
    CaseHandlePendingReviewHomeRs rs = caseHandleService.caseHandlePendingReviewHome();
    return rs;
  }

  /**
   * 後台案件處理-待審核匣-案件查詢
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待審核匣-案件查詢")
  @RequestMapping(value = {"/pendingReview/query"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ResponseBody
  public CaseHandlePendingReviewQueryRs caseHandlePendingReviewQuery(@RequestBody CaseHandlePendingReviewQueryRq rq) {
    CaseHandlePendingReviewQueryRs rs = caseHandleService.caseHandlePendingReviewQuery(rq);
    return rs;
  }

  /**
   * 後台案件處理-待審核匣-取消簽收-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-取消簽收-初始畫面")
  @RequestMapping(value = {"/pendingReview/cancelAccept/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingReviewCancelAcceptHomeRs caseHandlePendingReviewCancelAcceptHomeAction(@RequestBody CaseHandlePendingReviewCancelAcceptHomeRq rq) {
    CaseHandlePendingReviewCancelAcceptHomeRs rs = caseHandleService.caseHandlePendingReviewCancelAcceptHome(rq);
    return rs;
  }

  /**
   * 後台案件處理-待審核匣-取消簽收
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待審核匣-取消簽收")
  @RequestMapping(value = {"/pendingReview/cancelAccept"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public List<CaseHandlePendingReviewCancelAcceptRs> caseHandlePendingReviewCancelAccept(@RequestBody List<CaseHandlePendingReviewCancelAcceptRq> rq) {
    return caseHandleService.caseHandlePendingReviewCancelAccept(rq);
  }

  /**
   * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-服務申辦(SA)案件簽核-初始畫面")
  @RequestMapping(value = {"/pendingReview/signSaCase/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleSignSaCaseHomeRs caseHandleSignSaCaseHome(@RequestBody CaseHandleSignSaCaseHomeRq rq) {
    CaseHandleSignSaCaseHomeRs rs = caseHandleService.caseHandleSignSaCaseHome(rq);
    return rs;
  }

  /**
   * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-新增參考文件及備註
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-服務申辦(SA)案件簽核-新增參考文件及備註")
  @RequestMapping(value = {"/pendingReview/addNote"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void caseHandleAddNote(@RequestBody CaseHandleAddNoteRq rq) {
    caseHandleService.caseHandleAddNote(rq);
  }

  /**
   * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-刪除參考文件及備註
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-服務申辦(SA)案件簽核-刪除參考文件及備註")
  @RequestMapping(value = {"/pendingReview/deleteNote"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void caseHandleDeleteNote(@RequestBody CaseHandleDelNoteRq rq) {
    caseHandleService.caseHandleDeleteNote(rq);
  }

  /**
   * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-初始畫面
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-初始畫面")
  @RequestMapping(value = {"/pendingReview/correctHome"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingReviewCorrectHomeRs caseHandleSignSaCaseCorrectHome(@RequestBody CaseHandlePendingReviewCorrectHomeRq rq) {
    CaseHandlePendingReviewCorrectHomeRs rs = caseHandleService.caseHandleSignSaCaseCorrectHome(rq);
    return rs;
  }

  /**
   * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-補正通知
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-補正通知")
  @RequestMapping(value = {"/pendingReview/correctUpdate"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingReviewCorrectUpdateRs caseHandleSignSaCaseCorrectUpdate(@RequestBody CaseHandlePendingReviewCorrectUpdateRq rq) {
    CaseHandlePendingReviewCorrectUpdateRs rs = caseHandleService.caseHandleSignSaCaseCorrectUpdate(rq);
    return rs;
  }

  /**
   * 後台案件處理-待審核匣-結案
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待審核匣-結案")
  @RequestMapping(value = {"/pendingReview/complete"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandlePendingSignCompleteRs caseHandlePendingSignComplete(@RequestBody CaseHandlePendingSignCompleteRq rq) {
    return caseHandleService.caseHandlePendingSignComplete(rq);
  }

  /**
   * 後台案件處理-待審核匣-動態流程作業
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-待審核匣-動態流程作業 同意/不同意")
  @RequestMapping(value = {"/pendingReview/doDynamicFlowAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public ApiBaseResponse<BaseViewForm> doDynamicFlowAction(@RequestBody CaseHandlePendingSignDoDynamicFlowRq rq) {
    return caseHandleService.doDynamicFlowAction(rq);
  }

  /**
   * 後台案件處理-已審核匣-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-已審核匣-初始畫面 ")
  @RequestMapping(value = {"/reviewed/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleReviewedHomeRs caseHandleReviewedHomeAction() {
    CaseHandleReviewedHomeRs rs = caseHandleService.caseHandleReviewedHome();
    return rs;
  }

  /**
   * 後台案件處理-已審核匣-案件查詢
   *
   * @param rq
   * @param request
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-已審核匣-案件查詢(Geo:如要搜尋 非結案 的案子，completeStatus勿給限制) ")
  @RequestMapping(value = {"/reviewed/queryAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleReviewedQueryRs caseHandleReviewedQueryAction(@RequestBody CaseHandleReviewedQueryRq rq, HttpServletRequest request) {
    CaseHandleReviewedQueryRs rs = caseHandleService.caseHandleReviewedQuery(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件檢視-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-案件檢視-初始畫面  ")
  @RequestMapping(value = {"/caseView/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewHomeRs caseHandleCaseViewHomeAction() {
    CaseHandleCaseViewHomeRs rs = caseHandleService.caseHandleCaseViewHome();
    return rs;
  }

  /**
   * 後台案件處理-案件檢視-案件查詢
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-案件檢視-案件查詢")
  @RequestMapping(value = {"/caseView/queryAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewQueryRs caseHandleCaseViewQueryAction(@RequestBody CaseHandleCaseViewQueryRq rq) {
    CaseHandleCaseViewQueryRs rs = caseHandleService.caseHandleCaseViewQuery(rq);
    return rs;
  }

  /**
   * 後台案件處理-場地案件檢視-案件查詢
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "# 後台案件處理-場地案件檢視-案件查詢")
  @RequestMapping(value = {"/CaseView/siteQueryAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewQueryRs caseHandleSiteCaseViewQueryAction(@RequestBody CaseHandleSiteCaseViewQueryRq rq) {
    CaseHandleCaseViewQueryRs rs = caseHandleService.caseHandleSiteCaseViewQuery(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面
   *
   * @param rq
   * @return CaseHandleCaseViewSaCaseHomeRs
   */
  @ApiOperation(value = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面")
  @RequestMapping(value = {"/caseView/saCase/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewSaCaseHomeRs caseHandleCaseViewSaCaseHomeAction(@RequestBody CaseHandleCaseViewSaCaseHomeRq rq) {
    CaseHandleCaseViewSaCaseHomeRs rs = caseHandleService.caseHandleCaseViewSaCaseHome(rq);
    return rs;
  }

  @ApiOperation(value = "* 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面2")
  @RequestMapping(value = {"/caseView/saCase/homeAction2"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewSaCaseHomeRs caseHandleCaseViewSaCaseHomeAction2(@RequestBody CaseHandleCaseViewSaCaseHomeRq rq) {
    CaseHandleCaseViewSaCaseHomeRs rs = caseHandleService.caseHandleCaseViewSaCaseHome2(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件檢視-系統權限申請(URA)案件檢視-初始畫面
   *
   * @param rq
   * @return CaseHandleCaseViewUraCaseHomeRs
   */
  @ApiOperation(value = "後台案件處理-案件檢視-系統權限申請(URA)案件檢視-初始畫面")
  @RequestMapping(value = {"/caseView/uraCase/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewUraCaseHomeRs caseHandleCaseViewUraCaseHomeAction(@RequestBody CaseHandleCaseViewUraCaseHomeRq rq) {
    CaseHandleCaseViewUraCaseHomeRs rs = caseHandleService.caseHandleCaseViewUraCaseHome(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面
   *
   * @param rq
   * @return CaseHandleCaseViewScaCaseHomeRs
   */
  @ApiOperation(value = "後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面")
  @RequestMapping(value = {"/caseView/scaCase/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewScaCaseHomeRs caseHandleCaseViewScaCaseHomeAction(@RequestBody CaseHandleCaseViewScaCaseHomeRq rq) {
    CaseHandleCaseViewScaCaseHomeRs rs = caseHandleService.caseHandleCaseViewScaCaseHome(rq);
    return rs;
  }

  /**
   * GEO 20211109 add 機關審核表單
   * 後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單
   *
   * @param rq
   * @return CaseHandleCaseViewUraCaseHomeRs
   */
  @ApiOperation(value = "* 後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單")
  @RequestMapping(value = {"/caseView/QueryOrganForm"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewQueryOrganFormRs caseHandleCaseViewQueryOrganForm(@RequestBody CaseHandleCaseViewQueryOrganFormRq rq) {
    CaseHandleCaseViewQueryOrganFormRs rs = caseHandleService.caseViewQueryOrganForm(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-檔案下載(myData資料、上傳附件)
   *
   * @param rq
   * @return CaseHandleCaseViewSaCaseHomeRs
   */
  @ApiOperation(value = "後台案件處理-案件檢視-服務申辦(SA)案件檢視- 檔案下載(myData資料、上傳附件)")
  @RequestMapping(value = {"/caseView/saCase/downloadAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void caseHandleCaseViewSaCaseDownloadAction(@RequestBody CaseHandleCaseViewSaCaseDownloadRq rq) {
    caseHandleService.caseHandleCaseViewScaCaseDownload(rq);
  }

  @ApiOperation(value = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-改變狀態")
  @RequestMapping(value = {"/caseView/saCase/updateStatus"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewScaCaseUpdateStatueRs caseHandleCaseViewSaCaseUpdateStatus(@RequestBody CaseHandleCaseViewSaCaseUpdateStatusRq rq) {
    CaseHandleCaseViewScaCaseUpdateStatueRs rs = caseHandleService.caseHandleCaseViewScaCaseUpdateStatus(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件異動-案件查詢
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "後台案件處理-案件異動-案件查詢")
  @RequestMapping(value = {"/caseUpdate/queryAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseUpdateQueryRs caseHandleCaseUpdateQueryAction(@RequestBody CaseHandleCaseUpdateQueryRq rq) {
    CaseHandleCaseUpdateQueryRs rs = caseHandleService.caseHandleCaseUpdateQuery(rq);
    return rs;
  }

  /**
   * 後台案件處理-案件異動-變更承辦人
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "後台案件處理-案件異動-變更承辦人")
  @RequestMapping(value = {"/caseUpdate/updateAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseUpdateUpdateRs caseHandleCaseUpdateUpdateAction(@RequestBody CaseHandleCaseUpdateUpdateRq rq) {
    CaseHandleCaseUpdateUpdateRs rs = caseHandleService.caseHandleCaseUpdateUpdate(rq);
    return rs;
  }

  @RequestMapping(value = {"/getHistoryData"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public List<HistoryDataDto> getHistoryData(@RequestBody CaseHandleSignSaCaseHomeRq rq) {
    String processId = rq.getCaseId();
    List<HistoryDataDto> historyList = activitiService.getHistoryData(processId);
    return historyList;
  }

  /**
   * 後台案件處理-已審核匣-案件檢視
   *
   * @param rq
   * @param request
   * @return
   */
//	@ApiOperation(value = "後台案件處理-已審核匣-案件檢視 ")
//	@RequestMapping(value = { "/reviewed/view" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public CaseHandleReviewedViewRs caseHandleReviewedVAction(@RequestBody CaseHandleReviewedViewRq rq,
//			HttpServletRequest request) {
//		CaseHandleReviewedViewRs rs = caseHandleService.caseHandleReviewedView(rq);
//		return rs;
//	}

  /**
   * 後台案件處理-待審核匣-上傳附件
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-上傳附件")
  @RequestMapping(value = "/pendingReview/uploadFile", method = RequestMethod.POST)
  @ResponseBody
  public CaseHandlePendingReviewUploadFileRs caseHandlePendingReviewUploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
                                                                               @RequestParam(value = "caseId", required = false) String caseId, @RequestParam(value = "title", required = false) String title) {
    CaseHandlePendingReviewUploadFileRq caseHandlePendingReviewUploadFileRq = new CaseHandlePendingReviewUploadFileRq();
    caseHandlePendingReviewUploadFileRq.setCaseId(caseId);
    caseHandlePendingReviewUploadFileRq.setTitle(title);
    caseHandlePendingReviewUploadFileRq.setFile(file);

    return caseHandleService.caseHandlePendingReviewUploadFile(caseHandlePendingReviewUploadFileRq);
  }

  /**
   * 後台案件處理-待審核匣-刪除附件
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-刪除附件")
  @RequestMapping(value = "/pendingReview/deleteFile", method = RequestMethod.POST)
  @ResponseBody
  public CaseHandlePendingReviewDeleteFileRs caseHandlePendingReviewUploadFile(@RequestBody CaseHandlePendingReviewDeleteFileRq caseHandlePendingReviewDeleteFileRq) {
    return caseHandleService.caseHandlePendingReviewDeleteFile(caseHandlePendingReviewDeleteFileRq);
  }

  /**
   * 後台案件處理-待審核匣-下載附件
   *
   * @return
   */
  @ApiOperation(value = "後台案件處理-待審核匣-下載附件")
  @RequestMapping(value = "/pendingReview/downloadFile", method = RequestMethod.POST)
  @ResponseBody
  public void caseHandlePendingReviewUploadFile(@RequestBody CaseHandlePendingReviewDownloadFileRq caseHandlePendingReviewDeleteFileRq) {
    caseHandleService.caseHandlePendingReviewDownloadFile(caseHandlePendingReviewDeleteFileRq);
  }

  @RequestMapping(value = "/caseIdDecode", method = RequestMethod.POST)
  @ResponseBody
  public String caseIdDecode(@RequestParam(value = "caseId", required = false) String caseId) {
    return new String(Base64Utils.decode(caseId.getBytes()));
  }

  /**
   * GEO 20211105 add
   * 後台案件處理-案件檢視-excel匯出
   *
   * @param rq
   * @param request
   * @return
   */
  @ApiOperation(value = "*後台-案件檢視:excel匯出")
  @RequestMapping(value = {"/caseView/exportExcelAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void exportExcelAction(@RequestBody CaseHandleCaseViewQueryRq rq, HttpServletRequest request) {
    //todo 測試邏輯分流
    if ("A111222333".equalsIgnoreCase(rq.getId())) {
      LOGGER.info("分流測試, 測試原本失敗的邏輯");
      CaseHandleSiteCaseViewQueryRq q = new CaseHandleSiteCaseViewQueryRq();
      q.setCaseSetId(rq.getCaseSetId());
      q.setCaseId(rq.getCaseId());
      q.setApplicant(rq.getApplicant());
      q.setApplyDate(rq.getApplyDate());
      q.setCaseName(rq.getCaseName());
      q.setStatus(rq.getStatus());
      q.setCaseFlowType(rq.getCaseFlowType());
      q.setId(rq.getId());
      q.setCellPhone(rq.getCellPhone());
      q.setVersion(rq.getVersion());
      q.setOrganId(rq.getOrganId());
      this.siteExportExcelAction(q, request);
    } else {
      // 此處為原邏輯
      caseHandleService.exportExcelAction(rq);
    }
  }

  /**
   * GEO 20221101 add
   * 後台案件處理-場地案件檢視-excel匯出
   *
   * @param rq
   * @param request
   * @return
   */
  @ApiOperation(value = "# 後台-場地案件檢視:excel匯出")
  @RequestMapping(value = {"/caseView/siteCase/exportExcelAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void siteExportExcelAction(@RequestBody CaseHandleSiteCaseViewQueryRq rq, HttpServletRequest request) {
    caseHandleService.siteExportExcelAction(rq);
  }

  /**
   * GEO 20211109 add
   * 後台案件處理-案件檢視-json匯出
   *
   * @param rq
   * @param request
   * @return
   */
  @ApiOperation(value = "*後台-案件檢視:json匯出")
  @RequestMapping(value = {"/caseView/exportJsonAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void exportJsonAction(@RequestBody CaseHandleCaseViewQueryRq rq, HttpServletRequest request) {
    caseHandleService.exportJsonAction(rq);
  }

  /**
   * GEO 20211129 add
   * 後台案件處理-案件檢視-google匯出
   *
   * @param rq
   * @param request
   * @return
   */
  @ApiOperation(value = "*後台-案件檢視:google匯出")
  @RequestMapping(value = {"/caseView/exportGoogleAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void exportGoogleAction(@RequestBody CaseHandleCaseViewQueryRq rq, HttpServletRequest request) {
    caseHandleService.exportGoogleAction(rq);
  }

  /**
   * GEO 20211115 add for 跨機關檢視
   * 後台案件處理-跨機關檢視-初始畫面
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-跨機關檢視-初始畫面")
  @RequestMapping(value = {"/crossReView/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewHomeRs caseHandleCrossReViewHomeAction(@RequestBody ApiRequest rq) {
    CaseHandleCaseViewHomeRs rs = caseHandleService.caseHandleCaseViewHome();
    return rs;
  }

  /**
   * GEO 20211115 add for 跨機關檢視
   * 後台案件處理-跨機關檢視-案件查詢
   *
   * @param rq
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-跨機關檢視-案件查詢")
  @RequestMapping(value = {"/crossReView/queryAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCaseViewQueryRs caseHandleCrossReViewQueryAction(@RequestBody CaseHandleCaseViewQueryRq rq) {
    CaseHandleCaseViewQueryRs rs = caseHandleService.caseHandleCrossViewQuery(rq);
    return rs;
  }

  /**
   * GEO 20211115 add for 跨機關檢視
   * 後台案件處理-跨機關檢視-取得備註
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-跨機關檢視-取得備註")
  @RequestMapping(value = {"/crossReView/commentQueryAction"}, method = {RequestMethod.POST}, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCrossReviewCommentQueryRs crossReviewCommentQueryAction(@RequestBody CaseHandleCrossReviewCommentQueryRq rq) {
    CaseHandleCrossReviewCommentQueryRs rs = caseHandleService.queryComment(rq);
    return rs;
  }

  /**
   * GEO 20211115 add for 跨機關檢視
   * 後台案件處理-跨機關檢視-新增/編輯備註
   *
   * @return
   */
  @ApiOperation(value = "* 後台案件處理-跨機關檢視-新增/編輯備註")
  @RequestMapping(value = {"/crossReView/commentEdit"}, method = {RequestMethod.POST}, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public CaseHandleCrossReviewCommentEditRs crossReviewCommentEdit(@RequestBody CaseHandleCrossReviewCommentEditRq rq) {
    CaseHandleCrossReviewCommentEditRs rs = caseHandleService.editComment(rq);
    return rs;
  }

  /**
   * 後台案件處理-結案功能:檢核是否需要提供繳費金額審核及折扣下拉
   */
  @ApiOperation(value = "# 後台案件處理-結案功能:檢核是否需要提供繳費金額審核及折扣下拉 ")
  @RequestMapping(value = {"/pendingReview/discountRentCase"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public DiscountCountRs discountRentCase(@RequestBody DiscountCountRq rq) {
    return caseHandleService.discountRentCase(rq);
  }


  /**
   * 後台案件處理-案件檢視/已處理檢視 : 變更繳費狀態
   */
  @ApiOperation(value = "# 後台案件處理-案件檢視/已處理檢視 : 變更繳費狀態 ")
  @RequestMapping(value = {"/pendingReview/changePayment"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public GeoCaseChangePaymentRs changePayment(@RequestBody GeoCaseHandlePaymentRq rq) {
    return caseHandleService.changePayment(rq);
  }


  /**
   * 案件查詢 - 好孕行得通
   */
  @ApiOperation(value = "案件查詢 - 好孕行得通")
  @RequestMapping(value = {"/caseView/queryCaseByOrgan"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public QueryCaseByOrganQueryRs queryCaseByOrgan(@RequestBody CaseByOrganQueryRq rq) {
    QueryCaseByOrganQueryRs rs = caseHandleService.queryCaseByOrgan(rq);
    return rs;
  }

  /**
   * * 機關查詢 - 好孕行得通
   */
  @ApiOperation(value = "機關查詢 - 好孕行得通")
  @RequestMapping(value = {"/caseView/pregnantOrganQuery"}, method = {RequestMethod.POST})
  @ResponseBody
  public PregnantOrganQueryRs pregnantOrganQuery() {

    PregnantOrganQueryRs rs = caseHandleService.pregnantOrganQuery();
    return rs;
  }

  /**
   * 案件檢視 - 匯出可預約時間Excel
   */
  @ApiOperation(value = "# 後台-場地案件檢視 - 可預約時間Excel 匯出")
  @RequestMapping(value = {"/caseView/siteCase/exportRentableExcelAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public void exportRentableExcelAction(@RequestBody CaseHandleSiteRentableExcelRq rq, HttpServletRequest request) {
    caseHandleService.exportRentableExcel(rq);
  }

  /**
   * 後台-場地案件檢視 - 可預約時間搜尋下拉選單
   */
  @ApiOperation(value = "# 後台-場地案件檢視 - 可預約時間搜尋下拉選單")
  @RequestMapping(value = {"/caseView/siteCase/rentableComboBox"}, method = {RequestMethod.POST}, consumes = {
          MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public ApiBaseResponse<RentableQueryComboBoxViewForm> rentableQueryComboBox(@RequestBody GeoRentableComboBoxRq rq, HttpServletRequest request) {
    return caseHandleService.rentableQueryComboBox(rq);
  }

}
