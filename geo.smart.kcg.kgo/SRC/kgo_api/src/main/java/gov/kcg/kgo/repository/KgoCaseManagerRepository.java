package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.CaseManagerNameQueryDto;
import gov.kcg.kgo.model.KgoCaseManager;
import gov.kcg.kgo.model.KgoCaseManagerPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCaseManagerRepositoryCustom;

public interface KgoCaseManagerRepository
		extends BaseRepository<KgoCaseManager, KgoCaseManagerPK>, KgoCaseManagerRepositoryCustom {

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	public int deleteByIdCaseId(String caseId);

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	@Query(value = "select new gov.kcg.kgo.dto.CaseManagerNameQueryDto(cm, u)\r\n" + "from KgoCaseManager cm \r\n"
			+ "left join KgoUser u on cm.id.manager = u.id.userId\r\n" + "where cm.id.caseId = :caseId\r\n")
	public List<CaseManagerNameQueryDto> findManagerNameByIdCaseId(@Param("caseId") String caseId);
}
