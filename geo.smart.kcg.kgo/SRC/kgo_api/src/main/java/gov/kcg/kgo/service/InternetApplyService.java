package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rq.InternetApplyAcceptDateHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rs.InternetApplyAcceptDateHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rq.InternetApplyAcceptSetAreaQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.InternetApplyAcceptSetAreaQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq.InternetApplyAcceptSetAreaSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rs.InternetApplyAcceptSetAreaSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rq.InternetApplyAcceptSetHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rs.InternetApplyAcceptSetHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rq.InternetApplyAcceptSetOfficerQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rs.InternetApplyAcceptSetOfficerQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rq.InternetApplyAcceptSetOfficerSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rs.InternetApplyAcceptSetOfficerSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rq.InternetApplyAcceptSetUnitQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rs.InternetApplyAcceptSetUnitQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rq.InternetApplyAcceptSetUnitSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rs.InternetApplyAcceptSetUnitSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rq.InternetApplyDescriptionDeleteRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rs.InternetApplyDescriptionDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rq.InternetApplyDescriptionHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.InternetApplyDescriptionHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rq.InternetApplyDescriptionSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rs.InternetApplyDescriptionSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.update.rq.InternetApplyDescriptionUpdateRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.update.rs.InternetApplyDescriptionUpdateRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rq.InternetApplyFormSetColumnHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rs.InternetApplyFormSetColumnHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.InternetApplyFormSetGroupColumnSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.InternetApplyFormSetOrganGroupColumnSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rs.InternetApplyFormSetGroupColumnSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rq.InternetApplyFormSetGroupHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rs.InternetApplyFormSetGroupHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rq.InternetApplyFormSetHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.InternetApplyFormSetHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rq.InternetApplyFormSetMyDataColumnComboBoxRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rs.InternetApplyFormSetMyDataColumnComboBoxRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rq.InternetApplyFormSetMydataComboBoxRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rs.InternetApplyFormSetMydataComboBoxRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rq.InternetApplyFormSetMydataDeleteRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rs.InternetApplyFormSetMydataDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rq.InternetApplyFormSetMydataHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.InternetApplyFormSetMydataHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rq.InternetApplyFormSetMydataSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rs.InternetApplyFormSetMydataSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rq.InternetApplyIdentityVerifyHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.InternetApplyIdentityVerifyHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rq.InternetApplyLimitPeriodHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rs.InternetApplyLimitPeriodHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rq.InternetApplySaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rq.OrganDiscountRatioQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.InternetApplySaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rq.InternetApplyServiceHtmlHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rs.InternetApplyServiceHtmlHomeRs;

public interface InternetApplyService {

	/**
	 * 網路申辦-服務宣告設定-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyServiceHtmlHomeRs internetApplyServiceHtmlHome(InternetApplyServiceHtmlHomeRq rq);

	/**
	 * 網路申辦-身分驗證設定-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyIdentityVerifyHomeRs internetApplyIdentityVerifyHome(InternetApplyIdentityVerifyHomeRq rq);

	/**
	 * 網路申辦-受理機關設定-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyAcceptSetHomeRs internetApplyAcceptSetHome(InternetApplyAcceptSetHomeRq rq);

	/**
	 * 網路申辦-受理機關設定-受理機關查詢
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyAcceptSetUnitQueryRs internetApplyAcceptSetUnitQuery(InternetApplyAcceptSetUnitQueryRq rq);

	/**
	 * 網路申辦-受理機關設定-承辦人查詢
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyAcceptSetOfficerQueryRs internetApplyAcceptSetOfficerQuery(
			InternetApplyAcceptSetOfficerQueryRq rq);

	/**
	 * 網路申辦-受理機關設定-受理機關新增
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyAcceptSetUnitSaveRs internetApplyAcceptSetUnitSave(InternetApplyAcceptSetUnitSaveRq rq);

	/**
	 * 網路申辦-受理機關設定-承辦人新增
	 * 
	 * @param rq
	 * @return
	 */
	public InternetApplyAcceptSetOfficerSaveRs internetApplyAcceptSetOfficerSave(
			InternetApplyAcceptSetOfficerSaveRq rq);

	/**
	 * 網路申辦-受理期間設定-初始畫面
	 */
	public InternetApplyAcceptDateHomeRs internetApplyAcceptDateHome(InternetApplyAcceptDateHomeRq rq);

	/**
	 * 網路申辦-限辦期限設定-初始畫面
	 */
	InternetApplyLimitPeriodHomeRs internetApplyLimitPeriodHome(InternetApplyLimitPeriodHomeRq rq);

	/**
	 * 網路申辦-網路申請說明-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyDescriptionHomeRs internetApplyDescriptionHome(InternetApplyDescriptionHomeRq rq);

	/**
	 * 網路申辦-網路申請說明-儲存
	 */
	InternetApplyDescriptionSaveRs internetApplyDescriptionSave(InternetApplyDescriptionSaveRq rq);

	/**
	 * 網路申辦-申請說明資料-資料更新
	 */
	InternetApplyDescriptionUpdateRs internetApplyDescriptionUpdate(InternetApplyDescriptionUpdateRq rq);

	/**
	 * 網路申辦-申請說明資料刪除
	 */
	InternetApplyDescriptionDeleteRs internetApplyDescriptionDelete(InternetApplyDescriptionDeleteRq rq);

	/**
	 * 網路申辦-表單設定-初始畫面
	 */
	InternetApplyFormSetHomeRs internetApplyFormSetHome(InternetApplyFormSetHomeRq rq);

	/**
	 * 網路申辦-表單設定-群組維護-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetGroupHomeRs internetApplyFormSetGroupHome(InternetApplyFormSetGroupHomeRq rq);

	/**
	 * 網路申辦-表單設定-MYDAYA維護-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetMydataHomeRs internetApplyFormSetMydataHome(InternetApplyFormSetMydataHomeRq rq);

	/**
	 * 網路申辦-表單設定-MYDAYA維護-刪除
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetMydataDeleteRs internetApplyFormSetMydataDelete(InternetApplyFormSetMydataDeleteRq rq);

	/**
	 * 網路申辦-表單設定-MYDAYA維護-資料集新增
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetMydataSaveRs internetApplyFormSetMydataSave(InternetApplyFormSetMydataSaveRq rq);

	/**
	 * 網網路申辦-案件儲存
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplySaveRs internetApplySave(InternetApplySaveRq rq);

	/**
	 * 網路申辦-表單設定-欄位維護-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetColumnHomeRs internetApplyFormSetColumnHome(InternetApplyFormSetColumnHomeRq rq);

	/**
	 * 網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetMyDataColumnComboBoxRs internetApplyFormSetMyDataColumnComboBox(
			InternetApplyFormSetMyDataColumnComboBoxRq rq);

	/**
	 * 網路申辦-表單設定-欄位群組維護-進版儲存
	 * 
	 * 群組 欄位 整包一起依照下一個版號來儲存
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetGroupColumnSaveRs internetApplyFormSetGroupColumnSave(InternetApplyFormSetGroupColumnSaveRq rq);


	/**
	 * GEO 20211109 add 機關審核表單
	 * 網路申辦-表單設定-欄位群組維護-機關審核表單 進版儲存
	 * 群組 欄位 整包一起依照下一個版號來儲存
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetGroupColumnSaveRs internetApplyFormSetOrganGroupColumnSave(InternetApplyFormSetOrganGroupColumnSaveRq rq);

	/**
	 * 網路申辦-受理機關設定-受理區機關查詢
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyAcceptSetAreaQueryRs internetApplyAcceptSetAreaQuery(InternetApplyAcceptSetAreaQueryRq rq);

	/**
	 * 網路申辦-受理機關設定-受理區機關新增
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyAcceptSetAreaSaveRs internetApplyAcceptSetAreaSave(InternetApplyAcceptSetAreaSaveRq rq);

	/**
	 * 網路申辦-表單設定-MYDAYA維護-取得Mydata資料集下拉式選單資料
	 * 
	 * @param rq
	 * @return
	 */
	InternetApplyFormSetMydataComboBoxRs internetApplyFormSetMydataComboBox(InternetApplyFormSetMydataComboBoxRq rq);

	/**
	 * 機關退費比率儲存
	 * @param rq
	 */	
	public OrganDiscountRatioSaveRs organDiscountRatioSave(OrganDiscountRatioQueryRq rq);

	/**
	 * 機關折扣新增頁面下拉選單
	 * @param rq
	 */	
	public OrganComboBoxQueryRs getDisocuntOrganComboBox(OrganDiscountRatioQueryRq rq );
	
	
	/**
	 * 機關優惠折扣查詢
	 */	
	OrganDiscountRatioQueryRs organDiscountRatioQuery(OrganDiscountRatioQueryRq rq);

	/**
	 * 機關優惠折扣刪除
	 */
	OrganDiscountRatioDeleteRs organDiscountRatioDelete (OrganDiscountRatioQueryRq rq);

}
