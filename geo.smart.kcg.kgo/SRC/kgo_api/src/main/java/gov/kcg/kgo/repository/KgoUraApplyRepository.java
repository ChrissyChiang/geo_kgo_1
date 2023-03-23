package gov.kcg.kgo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.UraCaseViewQueryDto;
import gov.kcg.kgo.model.KgoUraApply;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoUraApplyRepositoryCustom;

import java.util.List;

public interface KgoUraApplyRepository extends BaseRepository<KgoUraApply, String>, KgoUraApplyRepositoryCustom {

	/**
	 * 取得系統權限申請(URA)案件檢視資料
	 * 
	 * @param caseId
	 * @return
	 */
	@Query(value = "select new gov.kcg.kgo.dto.UraCaseViewQueryDto(ua, o, un, us) "
			+ "from KgoUraApply ua "
			+ "left join KgoOrgan o on ua.applyOrgan = o.organId "
			+ "left join KgoUnit un on ua.applyUnit = un.id.unitId and un.id.organId = o.organId "
			+ "left join KgoUser us on ua.applyUser = us.userId where ua.caseId = :caseId")
	public UraCaseViewQueryDto getUraCaseViewData(@Param("caseId") String caseId);

	List<KgoUraApply> findByProcessId(@Param("processId") String processId);

    List<KgoUraApply> findByApplyUserAndStatusIn(String loginUserId, List<String> statusList);

	List<KgoUraApply> findByManager1AndStatusIn(String manager1, List<String> statusList);

	List<KgoUraApply> findByManager2AndStatusIn(String manager2, List<String> statusList);
}