package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCaseDetail;
import gov.kcg.kgo.model.KgoCaseDetailPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCaseDetailRepositoryCustom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;

public interface KgoCaseDetailRepository
		extends BaseRepository<KgoCaseDetail, KgoCaseDetailPK>, KgoCaseDetailRepositoryCustom {

	/**
	 * 找尋 caseId 所有資料
	 * 
	 * @return
	 */
	public List<KgoCaseDetail> findByIdCaseId(String caseId);

	List<KgoCaseDetail> findByIdCaseIdAndColumnValue(String caseId,String columnValue);

	/**
	 * 找尋 caseId 所有資料
	 *
	 * @return
	 */
	public List<KgoCaseDetail> findByIdCaseIdAndIdColumnId(String caseId, String columnId);

	/**
	 * 刪除 caseId 所有資料
	 * 
	 * @param caseId
	 * @return
	 */
	public int deleteByIdCaseId(String caseId);


	/**
	 * 用 caseId 、version 找資料
	 *
	 * @return
	 */
	public List<KgoCaseDetail> findByIdCaseIdAndIdVersion(String caseId,Integer Version);

	/**
	 * 退費案件專用查詢 專用欄位 refurnCaseId
	 * */
	@Query(value="SELECT TOP 1 *  FROM  KGO_CASE_DETAIL WHERE ColumnId = :columnId  AND CaseId = :caseId  ORDER BY  Version DESC ",nativeQuery = true)
	KgoCaseDetail findRefurnIdByCaseId(@Param("caseId")String caseId,@Param("columnId")String columnId);

	@Query(value="SELECT TOP 1 Version  FROM KGO_CASE_DETAIL WHERE CaseId =:caseId  AND ColumnId = 'ID'  ORDER BY Version DESC " ,nativeQuery = true)
	Integer findVersionByCaseId(@Param("caseId")String caseId);
}
