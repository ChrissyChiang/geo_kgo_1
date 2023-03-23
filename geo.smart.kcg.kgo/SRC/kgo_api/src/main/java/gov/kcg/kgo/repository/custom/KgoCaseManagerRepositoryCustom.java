package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.CaseManagerNameQueryDto;

public interface KgoCaseManagerRepositoryCustom {
	/**
	 * 服務案件清單-管理者搜尋
	 * 
	 * @return List<CaseManagerQueryDto>
	 */
	public List<CaseManagerNameQueryDto> findCaseManagerByOrganId(String organId);


}
