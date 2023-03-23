package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.CasesetMydataQueryDto;

public interface KgoCasesetMyDataRepositoryCustom {

	/**
	 * 根據CasesetId查詢對應的MYDATA資料集合
	 * 
	 * @param caseSetId
	 * @return
	 */
	List<CasesetMydataQueryDto> countMydataIdAndNameByCaseSetId(String caseSetId);

}
