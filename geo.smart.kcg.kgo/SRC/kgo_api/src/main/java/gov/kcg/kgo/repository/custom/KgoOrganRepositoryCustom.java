package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.CaseManagerOrganQueryDto;
import gov.kcg.kgo.dto.OrganUnitManagementQueryDto;
import gov.kcg.kgo.model.KgoOrgan;

public interface KgoOrganRepositoryCustom {

	/**
	 * 機關&科室資料搜尋
	 * 
	 * @param question
	 * @param publishDateStart
	 * @param publishDateEnd
	 * @return
	 */
	public List<OrganUnitManagementQueryDto> findOrganAndUnitData(String organId, String organName, String unitId);

	/**
	 * 機關查詢By使用者帳號
	 * 
	 * @param userId
	 * @return
	 */
	public List<KgoOrgan> findOrganByUserId(String userId);

	/**
	 * 服務案件清單-案件維護-初始畫面-機關分類下拉選單
	 *
	 * @param organId
	 * @return
	 */
	public List<CaseManagerOrganQueryDto> findOrganByOrgan(String organId);

}
