package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.CasesetGroupQueryDataMaxVersionDto;

public interface KgoCasesetGroupRepositoryCustom {

	/**
	 * 新增資料
	 * 
	 * @param caseSetId
	 * @param version
	 * @param memo
	 * @param orderNum
	 * @return
	 */
	/** GEO 20211203 add 重複檢核 */
	Integer saveData(String caseSetId, int version, String memo, int orderNum, String checkFrequencyPeriod);

	/**
	 * 找該案件最大 version
	 * 
	 * @param caseSetId
	 * @return
	 */
	Integer findMaxVersionByCasesetId(String caseSetId);

	/**
	 * 找尋該案件ID底下所有版本最大號的群組資料
	 * 
	 * @param caseSetId
	 * @return
	 */
	List<CasesetGroupQueryDataMaxVersionDto> findMaxVersionGroupData(String caseSetId, String memo);

}
