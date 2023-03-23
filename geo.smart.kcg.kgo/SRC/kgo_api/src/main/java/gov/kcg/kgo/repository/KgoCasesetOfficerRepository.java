package gov.kcg.kgo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.kcg.kgo.dto.AcceptSetOfficerQueryDto;
import gov.kcg.kgo.model.KgoCasesetOfficer;
import gov.kcg.kgo.model.KgoCasesetOfficerPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetOfficerRepository extends BaseRepository<KgoCasesetOfficer, KgoCasesetOfficerPK> {

	/**
	 * delete data by caseSetId
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Modifying
	@Query(value = "Delete From KgoCasesetOfficer Where CaseSetId IN :caseSetId")
	public int deleteByCaseSetId(@Param("caseSetId") String caseSetId);

	public List<KgoCasesetOfficer> findByIdCaseSetId(String caseSetId);

	/**
	 * 取得該案件底下所有承辦人資料
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Query(value = "select new gov.kcg.kgo.dto.AcceptSetOfficerQueryDto(us, co, o) \r\n" + "from KgoUser us\r\n"
			+ "right join KgoCasesetOfficer co on co.id.caseOfficer = us.id.userId \r\n"
			+ "left join KgoOrgan o on us.organ = o.id.organId \r\n" + "where co.id.caseSetId = :caseSetId \r\n")
	// Remove left join KgoUnit by Jay 20201125
	public List<AcceptSetOfficerQueryDto> getCasesetOfficerDataByCaseSetId(@Param("caseSetId") String caseSetId);
}
