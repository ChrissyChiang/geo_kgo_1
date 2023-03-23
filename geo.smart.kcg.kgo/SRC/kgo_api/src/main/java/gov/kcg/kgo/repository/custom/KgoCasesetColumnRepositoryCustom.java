package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.CasesetMydataColumnDto;
import gov.kcg.kgo.dto.UraCaseViewExtraDataQueryDto;

public interface KgoCasesetColumnRepositoryCustom {

	/**
	 * 查詢下一個 CaseId
	 * 
	 * @param caseIdPrefix
	 * @return
	 */
	List<UraCaseViewExtraDataQueryDto> getUraCaseExtraData(String caseId);

	public List<CasesetMydataColumnDto> findMydataColumnByCaseSetAndVersion(String caseSetId, int version);

}
