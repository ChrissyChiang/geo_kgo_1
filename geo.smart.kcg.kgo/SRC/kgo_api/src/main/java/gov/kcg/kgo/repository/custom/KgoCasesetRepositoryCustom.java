package gov.kcg.kgo.repository.custom;

import java.util.List;



import gov.kcg.kgo.geomodel.GeoBidServiceMenuQueryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gov.kcg.kgo.dto.BidCaseListLinkQueryDto;
import gov.kcg.kgo.dto.BidServiceMenuQueryDto;
import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.dto.CaseManagerOrganQueryDto;
import gov.kcg.kgo.dto.CasesetMemoHotSearchDto;
import gov.kcg.kgo.model.KgoCaseset;

public interface KgoCasesetRepositoryCustom {

	/**
	 * 申辦案件清單-申辦案件資料查詢
	 *
	 * @param mainType
	 * @param value
	 * @return
	 */
	List<BidCaseListLinkQueryDto> getBidCaseListData(String mainType, String value);

	/**
	 * 服務案件清單 - 案件搜尋 (for 畫面初始)
	 */
	List<CaseManagementQueryDto> findAllCase();

	/**
	 * 服務案件清單 - 案件搜尋 (for 案件排序)
	 */
	List<CaseManagementQueryDto> findAllCase(Integer organSeq);

	/**
	 *
	 * @param organId 機關分類代碼 from KGO_GROUP_LEVEL.seq
	 * @param ownerOrgan 權責機關代碼
	 * @param caseSetId 案件種類ID
	 * @param caseSetName 案件名稱
	 * @param userId 管理者ID
	 * @return
	 */
	List<CaseManagementQueryDto> findAllCase(Integer organSeq, String ownerOrgan, String caseSetId, String caseSetName,
											 String userId);

	/**
	 * 申辦服務選單-初始畫面-申辦案件數查詢
	 *
	 * @param status
	 * @return
	 */
	List<BidServiceMenuQueryDto> getBidServiceMenuCaseCountData(String mainType, String status);

	/**
	 * 熱門搜尋-查詢
	 *
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<CasesetMemoHotSearchDto> findByKeywordPaged(String keyword, Pageable pageable);

	/**
	 * 服務申請-初始畫面-KgoCaseset案件搜尋
	 *
	 * @param caseSetName
	 * @param caseType
	 * @return
	 */
	List<KgoCaseset> findByCaseTypeAndCaseSetName(String caseSetName, String caseType);

	/**
	 *服務案件清單-初始畫面-機關分類下拉選單
	 *
	 * @param organId
	 * @return
	 */
	List<CaseManagerOrganQueryDto> findCasesetOrganByOrgan(String organId);
}
