package gov.kcg.kgo.service;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.backend.CaseStatusEnum;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.model.KgoCaseMain;
import org.activiti.engine.task.Comment;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author TPIuser
 *
 */
public interface ActivitiService {

	/**
	 * 流程啟動
	 *
	 * @param caseId
	 * @return
	 */
	String processCaseStart(String caseId);

	String processCaseStart(String caseId, String caseOrganName);

	String processCaseStart(KgoCaseMain kgoCaseMain);

	String processCaseStart(KgoCaseMain kgoCaseMain, String caseOrganName);

	/**
	 * 流程啟動
	 *
	 * @param caseId
	 * @return
	 */
	String processStart(String caseId, String manager,  String scenario);

	/**
	 * Activiti流程部屬.
	 */
	void deployFlow();

	List<Map<String, Object>> getPendingCaseProcessIdsForCaseUpdate(List<String> userIds, List<String> taskNames);

	/**
	 * 
	 * @param organId, userId, taskName
	 * @return processIds
	 */
	List<Map<String, Object>> getPendingCaseProcessIds(String organId, String loginUserId, String taskName1, String taskName2);

	/**
	 * 
	 * @param userId, taskName
	 * @return processIds
	 */
	List<Map<String, Object>> getPendingCaseProcessIds(String loginUserId, String taskName1, String taskName2);

	/**
	 * 
	 * @param userId, taskName
	 * @return processIds
	 */
	List<Map<String, Object>> getPendingCaseProcessIds(String loginUserId, String taskName);

	/**
	 * 
	 * @param
	 * @return
	 */
	public void nextFlow(String processId, String asignee);

    void nextFlowByTaskIdApprove(String taskId, String asigner);

    void nextFlowByTaskId(String taskId, String asigner);

	void dispatchFlowByTaskId(String taskId, String assignor, String assignee, String acceptType);

	/**
	 * 
	 * @param
	 * @return
	 */
	public void dispatchFlow(String processId, String assignor, String assignee, String acceptType);

    void nextFlowCorrect(String processId, boolean isApprove, String rejectTo);

    /**
	 * 
	 * @param
	 * @return
	 */
	public void nextFlow(String processId, boolean isApprove, String rejectTo);

	public void nextFlowNew(String processId, String asigner);

	void previousFlowByTaskId(String taskId, String rejectTo);

	/**
	 * 
	 * @param
	 * @return
	 */
	public void previousFlow(String processId, String rejectTo);

	/**
	 * 
	 * @param
	 * @return
	 */
	public void completeFlow(String processId);

	/**
	 * 
	 * 取得歷史資料
	 * 
	 * @param processId
	 * @return
	 */
	public List<HistoryDataDto> getHistoryData(String processId);

	List<HistoryDataDto> getHistoryDataByTaskId(String taskId);

	/**
	 * 
	 * 取得歷史資料
	 * 
	 * @param processId
	 * @return
	 */
	public List<HistoryDataDto> getHistoryData(List<String> processIdList);

	/**
	 * 
	 * 新增案件歷程
	 * 
	 * @param loginUserId:   當前登入者
	 * @param processId
	 * @param caseStatus:    案件狀態
	 * @param caseComment:   案件內容
	 * @param caseOrganName: 承辦的機關名稱(Optional)
	 * @param caseOfficer:   承辦人(Optional)
	 * @return
	 */
	public void addComment(String loginUserId, String processId, String caseStatus, String caseComment,
			String caseOrganName, String caseOfficer);

	void completeFlowByTaskId(String taskId);

	void completeFlowByTaskIdApproveAndPass(String taskId, boolean approve, boolean pass);

	void completeFlowByTaskIdApprove(String taskId, String approve);

	void completeFlowByTaskIdApproveAndPassManager2(String taskId, boolean approve, boolean pass, String manager2);

	void completeFlowByTaskIdNotApprove(String taskId);

    void correctWithTaskId(String taskId);

    /**
	 * 
	 * 新增案件歷程
	 * 
	 * @param loginUserId: 當前登入者
	 * @param processId
	 * @param caseStatus: 案件狀態
	 * @param caseComment: 案件內容
	 * @param caseOrganName: 承辦的機關名稱(Optional)
	 * @param caseOfficer: 承辦人(Optional)
	 * @param taskNode: 任務節點名稱
	 * @return
	 */
	public void addComment(String loginUserId, String processId, String caseStatus, String caseComment,
			String caseOrganName, String caseOfficer, CaseStatusEnum taskNode);

	/** GEO 20211101 add 增加簽核意見*/
	Comment addCommentByTaskId(String loginUserId, String taskId, String caseStatus, String caseComment,
							   String caseOrganName, String caseOfficer, CaseStatusEnum taskNode);

	List<Map<String, Object>> getPendingCaseProcessIdsSign(String organId, String loginUserId, BackendLoginUserInfo backendLoginUser, List<String> taskNames);

	List<Map<String, Object>> getPendingCaseProcessIdsSign(String organId, String loginUserId, List<String> taskNames ,List<String> roles);

	List<Map<String, Object>> getPendingCaseProcessIds(String organId, String loginUserId, BackendLoginUserInfo backendLoginUser, List<String> taskNames);

	List<Map<String, Object>> getPendingCaseProcessIds(String organId, String loginUserId, List<String> taskNames ,List<String> roles);

	void completeFlowByTaskIdApprove(String taskId, boolean approve);
}
