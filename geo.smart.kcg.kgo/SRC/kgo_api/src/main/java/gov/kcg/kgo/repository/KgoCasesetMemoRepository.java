package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.CasesetMemoAndCasesetQueryDto;
import gov.kcg.kgo.model.KgoCasesetMemo;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCasesetMemoRepositoryCustom;

public interface KgoCasesetMemoRepository
		extends BaseRepository<KgoCasesetMemo, Integer>, KgoCasesetMemoRepositoryCustom {

	/**
	 * 取得該案件的申辦說明資料
	 * 
	 * @param caseSetId
	 * @param applyType
	 * @return
	 */
	public List<KgoCasesetMemo> findAllByCaseSetIdAndApplyType(String caseSetId, String applyType);

	/**
	 * 刪除該案件的所有申辦說明資料
	 * 
	 * @param caseSetId
	 * @param applyType
	 * @return
	 */
	@Modifying
	public int deleteByCaseSetId(String caseSetId);

	/**
	 * 搜尋該案件所有的申辦說明資料
	 *
	 * @param currentDateStr
	 * @return
	 */
	public List<KgoCasesetMemo> findByCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	// modify 2020.12.24 調整sql語句 過濾無勾選的申辦類型
	@Query(value = "select new gov.kcg.kgo.dto.CasesetMemoAndCasesetQueryDto(cm, c) "
			+ " from KgoCasesetMemo cm left join KgoCaseset c on cm.caseSetId = c.caseSetId "
			+ " inner join KgoCasesetType ct on ct.id.caseSetId = cm.caseSetId and ct.id.applyType = cm.applyType "
			+ " where cm.caseSetId = :caseSetId")
	public List<CasesetMemoAndCasesetQueryDto> getCaseSetMemoAndCasesetData(@Param("caseSetId") String caseSetId);
}
