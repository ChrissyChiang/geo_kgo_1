package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.SaCaseColumnDetailDto;
import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetColumnPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCasesetColumnRepositoryCustom;

public interface KgoCasesetColumnRepository
		extends BaseRepository<KgoCasesetColumn, KgoCasesetColumnPK>, KgoCasesetColumnRepositoryCustom {

	/**
	 * 
	 * @param caseSetId
	 * @param groupSeq
	 * @return
	 */
	public List<KgoCasesetColumn> findByIdCaseSetIdAndGroupSeqOrderByOrderNumAsc(String caseSetId, int groupSeq);

	/**
	 *
	 * @param caseSetId
	 * @param groupSeq
	 * @return
	 */
	public List<KgoCasesetColumn> findByIdCaseSetIdAndGroupSeqOrderByOrderNumDesc(String caseSetId, int groupSeq);

	/**
	 * 計算該資料集ID與該案件欄位設定掛勾的比數
	 * 
	 * @param caseSetId
	 * @param mydataId
	 * @return
	 */
	@Query(value = "select Count(*) COLUMN_COUNT from KGO_CASESET_COLUMN where CaseSetId=:caseSetId and MyDataId=:myDataId", nativeQuery = true)
	public int countCasesetMydataColumn(@Param("caseSetId") String caseSetId, @Param("myDataId") String myDataId);

	/**
	 * 刪除案件
	 * 
	 * @param caseSetId
	 * @return
	 */
	public int deleteByIdCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 * @param groupSeq
	 * @param version
	 * @return
	 */
	public List<KgoCasesetColumn> findByIdCaseSetIdAndGroupSeqAndIdVersion(String caseSetId, int groupSeq, int version);

	public List<KgoCasesetColumn> findByIdCaseSetIdAndGroupSeqAndIdVersionOrderByOrderNumAsc(String caseSetId,
			int groupSeq, int version);

	/**
	 * 取得服務申辦流程(SA) -> KGO_CASE_MAIN 欄位資料&填入的資料
	 *
	 * @param caseId the case id
	 * @return the sa case extra data
	 */
	@Query("select new gov.kcg.kgo.dto.SaCaseColumnDetailDto(cc, cd, kcm) from KgoCasesetColumn cc left join "
			+ "	KgoCaseMain kcm on cc.id.caseSetId = kcm.caseSetId right join "
			+ "	KgoCaseDetail cd on cd.id.caseId = kcm.caseId and cc.id.columnId = cd.id.columnId and cc.id.version = cd.id.version "
			+ " where cc.id.version in ("
			+ "				select max(cc.id.version) as maxVersion from KgoCaseMain kcm left join KgoCasesetColumn cc on kcm.caseSetId = cc.id.caseSetId where kcm.caseId = :caseId "
			+ ") and kcm.caseId = :caseId  ")
	public List<SaCaseColumnDetailDto> getSaCaseExtraData(@Param("caseId") String caseId);

	/**
	 * 取得服務申辦流程(SA) -> KGO_CASE_MAIN 欄位資料&填入的資料 by 案件清單
	 *
	 * @param caseId the case id
	 * @return the sa case extra data
	 */
	@Query("select new gov.kcg.kgo.dto.SaCaseColumnDetailDto(cc, cd, kcm) from KgoCasesetColumn cc  left join "
			+ "	KgoCaseMain kcm on cc.id.caseSetId = kcm.caseSetId right join "
			+ "	KgoCaseDetail cd on cd.id.caseId = kcm.caseId and cc.id.columnId = cd.id.columnId and cc.id.version = cd.id.version "
			+ " where cc.id.version in ("
			+ "				select max(cc.id.version) as maxVersion from KgoCaseMain kcm left join KgoCasesetColumn cc on kcm.caseSetId = cc.id.caseSetId where kcm.caseId in :caseIdList "
			+ ") and kcm.caseId in :caseIdList  ")
	public List<SaCaseColumnDetailDto> getSaCaseExtraDataByCaseIdList(@Param("caseIdList") List<String> caseIdList);

	/**
	 * 取得案件欄位設定清單
	 * 
	 * @param caseSetId
	 * @param version
	 * @return
	 */
	public List<KgoCasesetColumn> findByIdCaseSetIdAndIdVersion(String caseSetId, int version);

	/**
	 * 撈取最大版本.
	 *
	 * @param caseSetId the case set id
	 * @param columnId  the column id
	 * @return the max verson by case set id and column id
	 */
	@Query("SELECT max(kc.id.version) FROM KgoCasesetColumn kc where kc.id.caseSetId =:caseSetId ")
	public Integer getMaxVersonByCaseSetId(String caseSetId);
}
