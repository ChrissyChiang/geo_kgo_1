package gov.kcg.kgo.service;

import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.commonApi.correctStatus.rq.CorrectStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.correctStatus.rs.CorrectStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rq.FlowB22GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.b22.getCaseInfoAction.rs.FlowB22GetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c22.getCaseInfoAction.rq.FlowC22GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c22.getCaseInfoAction.rs.FlowC22GetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rq.FlowC23GetCaseInfoActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c23.getCaseInfoListAction.rs.FlowC23GetCaseInfoListActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rq.FlowC3AddCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.flow.c3.addCaseAction.rs.FlowC3AddCaseActionRs;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.GenGeneralCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rs.GenGeneralCaseActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rq.GetCaseIdActionRq;
import gov.kcg.kgo.viewModel.commonApi.getCaseId.rs.GetCaseIdActionRs;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rq.GetCaseLogJsonActionRq;
import gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rs.GetCaseLogJsonActionRs;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rq.GetGeneralColumnActionRq;
import gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rs.GetGeneralColumnActionRs;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rq.GetUserLogJsonActionRq;
import gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rs.GetUserLogJsonActionRs;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rq.GetCaseStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.getstatus.rs.GetCaseStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.notify.rq.QueryStatusActionRq;
import gov.kcg.kgo.viewModel.commonApi.notify.rs.QueryStatusActionRs;
import gov.kcg.kgo.viewModel.commonApi.updateCaseStatus.rq.UpdateCaseStatusActionRq;

/**
 * 對外API
 * 高雄程式資料平台 API Service.
 */
public interface KcgCityExtService {
	
	/**
	 * 提供既有案件服務撈取案件資料API，B-2流程_2.
	 *
	 * @param rq the rq
	 * @return the flow B 22 get case info action rs
	 */
	FlowB22GetCaseInfoActionRs flowB22GetCaseInfoAction(FlowB22GetCaseInfoActionRq rq);
	
	/**
	 * 提供既有案件服務撈取案件資料API，C-2流程_2.
	 *
	 * @param rq the rq
	 * @return the flow B 22 get case info action rs
	 */
	FlowC22GetCaseInfoActionRs flowC22GetCaseInfoAction(FlowC22GetCaseInfoActionRq rq);
	
	/**
	 * 提供單一案件類型下所有案件編號給稅捐，C-2_3稅捐.
	 *
	 * @param rq the rq
	 * @return the flow C 23 get case info list action rs
	 */
	FlowC23GetCaseInfoListActionRs flowC23GetCaseInfoAction(FlowC23GetCaseInfoActionRq rq);
	
	/**
	 * 提供平台案件處理區呼叫使用，C-3流程 新增案件.
	 *
	 * @param rq the rq
	 * @return the flow C 3 add case action rs
	 */
	FlowC3AddCaseActionRs flowC3AddCaseAction(FlowC3AddCaseActionRq rq);
	
	/**
	 * 通用型入案作業.
	 *
	 * @param rq the rq
	 * @return the generic column action
	 */
	GenGeneralCaseActionRs genGeneralCaseAction(GenGeneralCaseActionRq rq);
	
	/**
	 * 通用型查詢欄位作業.
	 *
	 * @param rq the rq
	 * @return the generic column action
	 */
	GetGeneralColumnActionRs getGeneralColumnAction(GetGeneralColumnActionRq rq);
	
	/**
	 * 查詢案件狀態.
	 *
	 * @param rq the rq
	 * @return the notify action rs
	 */
	QueryStatusActionRs queryStatusAction(QueryStatusActionRq rq);
	
	/**
	 * 寫入補正狀態.
	 *
	 * @param rq the rq
	 * @return the correct status action rs
	 */
	CorrectStatusActionRs correctStatusAction(CorrectStatusActionRq rq);

	/**
	 * 更新案件狀態
	 * 
	 * @return QuestionMaintainRs
	 */
	public ApiBaseResponse<BaseViewForm> updateCaseStatusAction(UpdateCaseStatusActionRq rq);
	
	/**
	 * 案件進度狀態查詢.
	 *
	 * @param rq the rq
	 * @return the case status action
	 */
	public GetCaseStatusActionRs getCaseStatusAction(GetCaseStatusActionRq rq);
	
	/**
	 * 取得案件編號.
	 *
	 * @return the case status action
	 */
	public GetCaseIdActionRs getCaseIdAction(GetCaseIdActionRq rq);
	
	/**
	 * 提供操作前/後臺 軌跡紀錄json log.
	 *
	 * @param rq the rq
	 * @param stystemType the stystem type
	 * @return the user log json action
	 */
	public GetUserLogJsonActionRs getUserLogJsonAction(GetUserLogJsonActionRq rq, SystemTypeEnum systemTypeEnum);
	
	/**
	 * 提供案件軌跡紀錄json log.
	 *
	 * @param rq the rq
	 * @return the case log json action
	 */
	public GetCaseLogJsonActionRs getCaseLogJsonAction(GetCaseLogJsonActionRq rq);

}
