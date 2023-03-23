package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.AcceptSetAreaQueryDto;
import gov.kcg.kgo.model.KgoCasesetArea;
import gov.kcg.kgo.model.KgoCasesetAreaPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetAreaRepository extends BaseRepository<KgoCasesetArea, KgoCasesetAreaPK> {

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Query(value = "select new gov.kcg.kgo.dto.AcceptSetAreaQueryDto(ca, o)\r\n" + "from KgoCasesetArea ca\r\n"
			+ "left join KgoOrgan o on ca.id.organ = o.organId\r\n" + "where ca.id.caseSetId = :caseSetId")
	public List<AcceptSetAreaQueryDto> getAreaDataByCaseSetId(@Param("caseSetId") String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 */
	public void deleteByIdCaseSetId(String caseSetId);
}
