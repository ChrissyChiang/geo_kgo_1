package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.AcceptSetUnitQueryDto;
import gov.kcg.kgo.model.KgoCasesetUnit;
import gov.kcg.kgo.model.KgoCasesetUnitPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetUnitRepository extends BaseRepository<KgoCasesetUnit, KgoCasesetUnitPK> {

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Modifying
	@Query(value = "Delete From KgoCasesetUnit Where CaseSetId = :caseSetId")
	public int deleteByCaseSetId(@Param("caseSetId") String caseSetId);

	/**
	 * 取得該案件底下所有受理機關資料
	 * 
	 * @param caseSetId
	 * @return List<AcceptSetUnitQueryDto>
	 */
	@Query(value = "select new gov.kcg.kgo.dto.AcceptSetUnitQueryDto(u, o) "
			+ "from KgoCasesetUnit u left join KgoOrgan o on u.id.organ = o.id.organId "
			+ "where u.id.caseSetId = :caseSetId")
	public List<AcceptSetUnitQueryDto> findByIdCaseSetId(@Param("caseSetId") String caseSetId);

}
