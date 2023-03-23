package gov.kcg.kgo.repository.custom;

import java.sql.Timestamp;
import java.util.List;

import gov.kcg.kgo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.repository.custom.condition.KgoCaseMainQueryCondition;

public interface KgoCaseMainRepositoryCustom {

	List<CaseProcessSearchDto> findByEmail(String email, String caseId, String caseSetName, String applyDateStart, String applyDateEnd,
										   List<String> status);


	List<CaseProcessSearchDto> findByUserAccount(String userAccount, String caseId, String caseSetName, String applyDateStart, String applyDateEnd,
										   List<String> status);

	/**
	 * [待簽收匣]
	 * 
	 * 查詢該UNIT_A (機關登入者)身分底下所擁有的[待簽收] 案件
	 * 
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	public List<CaseMainQueryDto> pendingSignSAQuery(List<String> processIds, String userName, String dateStart, String dateEnd,
			String caseSetName, String caseId);
	
	/**
	 * [待簽收匣]
	 * 
	 * 簽收
	 * 
	 * @param caseId
	 * @return
	 */
	public void pendingSignSAAccept(String caseId);
	
	/**
	 * [待審核匣]
	 * 
	 * 查詢
	 * 
	 * @param caseId
	 * @return
	 */
	public List<PendingReviewCaseMainQueryDto> pendingReviewSAQuery(List<String> processIds, String userName,
			String dateStart, String dateEnd, String caseSetName, String caseId);
	
	/**
	 * [已審核匣]
	 * 
	 * 查詢該登入者身分底下所擁有的[已審核] SA案件
	 * 
	 * @param processIds
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	 public List<CaseMainQueryReviewedDto> reviewedSAQuery(List<String> processIds, String userName,
			String dateStart, String dateEnd, String caseSetName, String caseId);

	/**
	 * [已審核匣]
	 * 
	 * 查詢該UNIT_U (機關承辦人)身分底下所擁有的[已審核] 案件
	 * 
	 * @param reviewer
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	List<CaseMainQueryDto> reviewedSAQueryWithUnitU(String reviewer, String userName, String dateStart, String dateEnd,
			String caseSetName, String caseId, String status);

	/**
	 * [已審核匣]
	 * 
	 * 查詢 UNIT_M (機關管理者) 身分底下所擁有的[已審核]案件
	 * 
	 * @param reviewer
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	List<CaseMainQueryDto> reviewedUraScaQueryWithUnitM(String reviewer, String userName, String dateStart,
			String dateEnd, String caseSetName, String caseId);

	/**
	 * [案件檢視]
	 * 
	 * 服務案件新增、系統權限申請(URA、SCA) - 不限角色
	 * 
	 * @param isNotAdmin
	 * @param applyUser
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	List<CaseMainQueryCaseViewDto> caseViewUraScaQuery(boolean isNotAdmin, String applyUser, String userName, String dateStart,
													   String dateEnd, String caseSetName, String caseId, String status);

	/**
	 * [案件檢視] 服務申辦流程(SA)
	 * 
	 * common columns: userName, dateStart, dateEnd, caseSetName, caseId, status
	 * 
	 * ADMIN : role = "", loginUserId = "", organId = "";
	 * 
	 * UNIT_U : role = "UNIT_U", loginUserId = caseOfficer = "XX", organId = "";
	 * 
	 * CASE_M : role = "CASE_M", loginUserId = Manager = "XX", organId = "";
	 * 
	 * UNIT_A & UNIT_M : role = "UNIT_A" or "UNIT_M", loginUserId = "", organId =
	 * "XX";
	 * 
	 * @param role
	 * @param loginUserId
	 * @param organId
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	List<CaseMainQueryCaseViewListDto> caseViewSAQuery(String role, String loginUserId, String organId, String userName,
			String dateStart, String dateEnd, String caseSetName, String caseId, List<String> status, String caseFlowType, String id, String cellPhone, Integer version);

	List<CaseMainQueryReviewDto> caseViewSAQueryForUpdateReviewer(String organId, String dateStart, String dateEnd, String caseSetName, String caseId, List<String> statuses, String caseOfficer, List<String> processIds);

	/**
	 * GEO 20211115 add for 跨機關檢視
	 * 後台案件處理-跨機關檢視-案件查詢
	 * @param role
	 * @param loginUserId
	 * @param organId
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @param caseFlowType
	 * @param id
	 * @param cellPhone
	 * @return
	 */
	List<CaseMainQueryCaseViewListDto> CrossReviewQuery(String role, String loginUserId, String organId, String userName,
													   String dateStart, String dateEnd, String caseSetName, String caseId, List<String> status, String caseFlowType, String id, String cellPhone);
	/**
	 * [案件查詢]
	 *
	 * 案件查詢by userId
	 *
	 * @param userId
	 * @return
	 */
	List<CaseProcessSearchDto> findByUserId(String userId, String caseId, String caseSetName, String applyDateStart, String applyDateEnd, List<String> status);
	
	/**
	 * 找出前兩名 服務編號.
	 *
	 * @return the list
	 */
	List<CaseMainCaseSetIdCountDto> queryTop2CaseSetIdByDate(Timestamp startDate, Timestamp endDate, String organId);
	
	
    /**
      * 案件軌跡紀錄 - 承辦人受理案件數.
     *
     * @param systemType   the system type
     * @param functionCode the function code
     * @param startDate    the start date
     * @param endDate      the end date
     * @return the int
     */
    public List<CaseMainCaseSetCountDto> queryCaseSetCount(Timestamp startDate, Timestamp endDate, String organId);
	/**
	 * 案件狀態統計分析Top5 (isLate:是否逾期).
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param isLate the is late
	 * @return the list
	 */
	List<CaseMainCaseStatusIsLateCountDto> queryTop5CaseStatusIsLateByDate(Timestamp startDate, Timestamp endDate, Boolean isLate, String organId);

	/**
	 * 案件狀態統計分析Top10
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param isLate the is late
	 * @return the list
	 */
	List<CaseMainCaseStatusCountDto> queryTop10CaseStatusByDate(Timestamp startDate, Timestamp endDate, List<String> statusList, String organId);
	
	/**
	 * 動態查詢 by查詢條件.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param isLate the is late
	 * @return the list
	 */
	List<KgoCaseMain> findByCondition(KgoCaseMainQueryCondition condition);

	/**
	 * GEO 20211019 add
	 * 重複檢核案件查詢
	 * @param columnId
	 * @param columnValue
	 * @param version
	 * @param caseId
	 * @param time
	 * @return
	 */
	List<String> findCaseIdCheckFrequencyColumn(String columnId, String columnValue, Integer version, String caseId, Timestamp time,Timestamp checkTime, String cColumnId);

	/**
	 * GEO 20211019 add
	 * 找尋需檢合欄位
	 * @param isCheckFrequency
	 * @return
	 */
	List<CaseMainCheckFrequencyDto> findCheckColumn(String caseSetId, Integer isCheckFrequency, Integer version);



	/**
	 ** 好孕行得通
	 */	
	List<CaseMainQueryCaseViewListDto> caseByOrganQuery(String loginUserId, String organId, String userName,
			String dateStart, String dateEnd, String caseSetName, String caseId, List<String> status,
			String caseFlowType, String id, String cellPhone, String caseSet,String version);
}
