package gov.kcg.kgo.repository.custom;

import gov.kcg.kgo.dto.CasesetManagerQueryByCaseSetIdDto;

public interface KgoCasesetManagerRepositoryCustom {

	/**
	 * 服務案件清單-案件維護-初始畫面-案件管理者資料查詢
	 * 
	 * @param caseSetId
	 * @return
	 */
	CasesetManagerQueryByCaseSetIdDto getManagerInfoByCasesetId(String caseSetId);

}
