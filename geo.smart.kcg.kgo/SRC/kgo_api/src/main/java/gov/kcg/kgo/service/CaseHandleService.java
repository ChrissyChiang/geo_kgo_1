package gov.kcg.kgo.service;

import java.util.List;
import java.util.Map;

import gov.kcg.kgo.geomodel.CaseHandleSiteCaseViewQueryRq;
import gov.kcg.kgo.geomodel.CaseHandleSiteRentableExcelRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseHandlePaymentRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoRentableComboBoxRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseChangePaymentRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.RentableQueryComboBoxViewForm;
import gov.kcg.kgo.model.KgoCaseMain;
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
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.home.rq.CaseHandlePendingReviewNotifyHomeRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.home.rs.CaseHandlePendingReviewNotifyHomeRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rq.CaseHandlePendingReviewNotifyHistoryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rq.CaseHandlePendingReviewNotifyRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.CaseHandlePendingReviewNotifyHistoryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.notify.rs.CaseHandlePendingReviewNotifyRs;
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
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

public interface CaseHandleService {

	/**
	 * 後台案件處理-已審核匣-初始畫面
	 * 
	 */
	public CaseHandleReviewedHomeRs caseHandleReviewedHome();

	/**
	 * 後台案件處理-已審核匣-案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleReviewedQueryRs caseHandleReviewedQuery(CaseHandleReviewedQueryRq rq);

	/**
	 * 後台案件處理-案件檢視-初始畫面
	 * 
	 * @return
	 */
	public CaseHandleCaseViewHomeRs caseHandleCaseViewHome();

	/**
	 * 後台案件處理-案件檢視-案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewQueryRs caseHandleCaseViewQuery(CaseHandleCaseViewQueryRq rq);

	/**
	 * GEO 20211115 add for 跨機關檢視
	 * 後台案件處理-跨機關檢視-案件查詢
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewQueryRs caseHandleCrossViewQuery(CaseHandleCaseViewQueryRq rq);
	/**
	 * GEO 20221029 add for 場地案件檢視
	 * 後台案件處理-場地案件-案件查詢
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewQueryRs caseHandleSiteCaseViewQuery(CaseHandleSiteCaseViewQueryRq rq);
	/**
	 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewSaCaseHomeRs caseHandleCaseViewSaCaseHome(CaseHandleCaseViewSaCaseHomeRq rq);

	/**
	 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-初始畫面 複合欄位改寫成前台樣
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewSaCaseHomeRs caseHandleCaseViewSaCaseHome2(CaseHandleCaseViewSaCaseHomeRq rq);

	/**
	 * 後台案件處理-案件檢視-系統權限申請(URA)案件檢視-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewUraCaseHomeRs caseHandleCaseViewUraCaseHome(CaseHandleCaseViewUraCaseHomeRq rq);

	/**
	 * 後台案件處理-案件檢視-服務案件新增(SCA)案件檢視-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleCaseViewScaCaseHomeRs caseHandleCaseViewScaCaseHome(CaseHandleCaseViewScaCaseHomeRq rq);

	/**
	 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視- 檔案下載(myData資料、上傳附件).
	 *
	 * @param rq the rq
	 */
	public void caseHandleCaseViewScaCaseDownload(CaseHandleCaseViewSaCaseDownloadRq rq);

	/**
	 * 後台案件處理-待簽收匣-初始畫面
	 * 
	 * @return
	 */
	public CaseHandlePendingSignHomeRs caseHandlePendingSignHome();

	/**
	 * 後台案件處理-待簽收匣-案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingSignQueryRs caseHandlePendingSignQuery(CaseHandlePendingSignQueryRq rq);

	/**
	 * 後台案件處理-待簽收匣-簽收
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingSignAcceptRs caseHandlePendingSignAccept(CaseHandlePendingReviewAcceptRq rq);

	/**
	 * 後台案件處理-待簽收匣-批次簽收
	 * 
	 * @param rq
	 * @return
	 */
	public List<CaseHandlePendingSignAcceptRs> caseHandlePendingSignBatchAccept(CaseHandlePendingReviewBatchAcceptRq rq);

	/**
	 * 後台案件處理-待簽收匣-分案/批次分文
	 * 
	 * @param rq
	 * @return
	 */
	public List<CaseHandlePendingSignDispatchRs> caseHandlePendingSignDispatch(CaseHandlePendingSignDispatchRq rq);

	/**
	 * 後台案件處理-待簽收匣-檢視
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingSignReviewRs caseHandlePendingSignReview(CaseHandlePendingSignReviewRq rq);

	/**
	 * 後台案件處理-待審核匣-初始畫面
	 * 
	 */
	public CaseHandlePendingReviewHomeRs caseHandlePendingReviewHome();

	/**
	 * 後台案件處理-待審核匣-案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingReviewQueryRs caseHandlePendingReviewQuery(CaseHandlePendingReviewQueryRq rq);

	/**
	 * 後台案件處理-待審核匣-取消簽收-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingReviewCancelAcceptHomeRs caseHandlePendingReviewCancelAcceptHome(CaseHandlePendingReviewCancelAcceptHomeRq rq);

	/**
	 * 後台案件處理-待審核匣-取消簽收
	 * 
	 * @param rq
	 * @return
	 */
	public List<CaseHandlePendingReviewCancelAcceptRs> caseHandlePendingReviewCancelAccept(List<CaseHandlePendingReviewCancelAcceptRq> rq);

	/**
	 * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandleSignSaCaseHomeRs caseHandleSignSaCaseHome(CaseHandleSignSaCaseHomeRq rq);

	/**
	 * 後台案件處理-待審核匣-簽核-新增參考文件及備註
	 * 
	 * @param rq
	 * @return
	 */
	public void caseHandleAddNote(CaseHandleAddNoteRq rq);

	/**
	 * 後台案件處理-待審核匣-簽核-刪除參考文件及備註
	 * 
	 * @param rq
	 * @return
	 */
	public void caseHandleDeleteNote(CaseHandleDelNoteRq rq);

	/**
	 * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingReviewCorrectHomeRs caseHandleSignSaCaseCorrectHome(CaseHandlePendingReviewCorrectHomeRq rq);

	/**
	 * 後台案件處理-待審核匣-結案
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingSignCompleteRs caseHandlePendingSignComplete(CaseHandlePendingSignCompleteRq rq);

	public ApiBaseResponse<BaseViewForm> doDynamicFlowAction(CaseHandlePendingSignDoDynamicFlowRq rq);

	@SuppressWarnings("unchecked")
	void doNotifyComplete(String caseId, KgoCaseMain kgoCaseMain, Map<String, Object> payment);

	CaseHandleCaseUpdateQueryRs caseHandleCaseUpdateQuery(CaseHandleCaseUpdateQueryRq rq);

	CaseHandleCaseUpdateUpdateRs caseHandleCaseUpdateUpdate(CaseHandleCaseUpdateUpdateRq rq);

	/**
	 * 後台案件處理-待審核匣-訊息通知
	 * 
	 * @param rq
	 * @return
	 */
	public CaseHandlePendingReviewNotifyHomeRs caseHandlePendingReviewNotifyHome(CaseHandlePendingReviewNotifyHomeRq rq);

	/**
	 * 後台案件處理-已審核匣-檢視
	 * 
	 * @param rq
	 * @return
	 */
//	public CaseHandleReviewedViewRs caseHandleReviewedView(CaseHandleReviewedViewRq rq);

	/**
	 * 後台案件處理-待審核匣-服務申辦(SA)案件簽核-補正作業-補正通知
	 * 
	 * @param rq
	 * @return
	 */
	CaseHandlePendingReviewCorrectUpdateRs caseHandleSignSaCaseCorrectUpdate(CaseHandlePendingReviewCorrectUpdateRq rq);

	/**
	 * 後台案件處理-待審核匣-訊息通知
	 * 
	 * @param rq
	 * @return
	 */
	CaseHandlePendingReviewNotifyRs caseHandlePendingReviewNotify(CaseHandlePendingReviewNotifyRq rq);

	/**
	 * 後台案件處理-待審核匣-訊息通知歷程
	 * 
	 * @param rq
	 * @return
	 */
	CaseHandlePendingReviewNotifyHistoryRs caseHandlePendingReviewNotifyHistory(CaseHandlePendingReviewNotifyHistoryRq rq);

	/**
	 * 後台案件處理-待審核匣-上傳附件
	 * 
	 * @param rq
	 * @return
	 */
	CaseHandlePendingReviewUploadFileRs caseHandlePendingReviewUploadFile(CaseHandlePendingReviewUploadFileRq rq);

	/**
	 * 後台案件處理-待審核匣-刪除附件
	 * 
	 * @param rq
	 * @return
	 */
	CaseHandlePendingReviewDeleteFileRs caseHandlePendingReviewDeleteFile(CaseHandlePendingReviewDeleteFileRq rq);

	/**
	 * 後台案件處理-待審核匣-下載附件
	 * 
	 * @param rq
	 * @return
	 */
	void caseHandlePendingReviewDownloadFile(CaseHandlePendingReviewDownloadFileRq rq);

	CaseHandleCaseViewScaCaseUpdateStatueRs caseHandleCaseViewScaCaseUpdateStatus(CaseHandleCaseViewSaCaseUpdateStatusRq rq);

	/**
	 * GEO 20211105 add
	 * 後台案件處理-案件檢視-excel匯出
	 *
	 */
	public void exportExcelAction(CaseHandleCaseViewQueryRq rq);
	/**
	 * GEO 20221101 add
	 * 後台案件處理-場地案件檢視-excel匯出
	 */
	public void siteExportExcelAction(CaseHandleSiteCaseViewQueryRq rq);

	/**
	 * GEO 20211109 add
	 * 後台案件處理-案件檢視-Json匯出
	 *
	 */
	public void exportJsonAction(CaseHandleCaseViewQueryRq rq);

    /**
     * GEO 20211129 add
     * 後台案件處理-案件檢視-Google匯出
     *
     */
    public void exportGoogleAction(CaseHandleCaseViewQueryRq rq);

	/**
	 * GEO 20211109 add 機關審核表單
	 * 後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單
	 *
	 * @param rq
	 * @return CaseHandleCaseViewUraCaseHomeRs
	 */
	public CaseHandleCaseViewQueryOrganFormRs caseViewQueryOrganForm(CaseHandleCaseViewQueryOrganFormRq rq);

	/**
	 * GEO 20211109 add 機關審核表單
	 * 後台案件處理-跨機關檢視-取得備註
	 * @param rq
	 * @return
	 */
    CaseHandleCrossReviewCommentQueryRs queryComment(CaseHandleCrossReviewCommentQueryRq rq);

	/**
	 * GEO 20211115 add for 跨機關檢視
	 * 後台案件處理-跨機關檢視-新增/編輯備註
	 * @param rq
	 * @return
	 */
	CaseHandleCrossReviewCommentEditRs editComment(CaseHandleCrossReviewCommentEditRq rq);


	 /*
	 **後台案件處理-結案通過之優惠折扣
	 */
	public DiscountCountRs discountRentCase(DiscountCountRq rq);


	/*
	**後台 案件檢視 - 變更繳費狀態
	 */
	public GeoCaseChangePaymentRs changePayment(GeoCaseHandlePaymentRq rq);


	 /*
	 ** 案件查詢 - 好孕行得通 roy
	 */
	QueryCaseByOrganQueryRs queryCaseByOrgan(CaseByOrganQueryRq rq);

	/*
	 ** 案件查詢 - 好孕行得通roy
	 */
	public PregnantOrganQueryRs pregnantOrganQuery();

	/*
	 **  場地案件查詢 - 可預約時段EXCEL匯出
	 */
	public void exportRentableExcel(CaseHandleSiteRentableExcelRq rq);

	/*
	 **  場地案件查詢 - 可預約時段查詢下拉
	 */
	public ApiBaseResponse<RentableQueryComboBoxViewForm> rentableQueryComboBox(GeoRentableComboBoxRq rq);
}
