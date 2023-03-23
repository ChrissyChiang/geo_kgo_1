package gov.kcg.kgo.repository.custom;

import gov.kcg.kgo.dto.ScaCaseViewQueryDto;

public interface KgoScaApplyRepositoryCustom {

	/**
	 * 查詢下一個 CaseId
	 * 
	 * @param caseIdPrefix
	 * @return
	 */
	String findNextCaseId(String caseIdPrefix);

	/**
	 * 後台案件處理-案件檢視-服務案件新增(SCA)案件檢視
	 * 
	 * 取得 服務案件新增(SCA)案件 檢視資料
	 * 
	 * @param caseId
	 * @return
	 */
	ScaCaseViewQueryDto getScaCaseViewData(String caseId);

}
