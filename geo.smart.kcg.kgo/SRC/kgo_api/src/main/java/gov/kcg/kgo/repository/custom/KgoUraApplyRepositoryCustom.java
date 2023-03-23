package gov.kcg.kgo.repository.custom;

public interface KgoUraApplyRepositoryCustom {

	/**
	 * 查詢下一個 CaseId
	 * 
	 * @param caseIdPrefix
	 * @return
	 */
	String findNextCaseId(String caseIdPrefix);

}
