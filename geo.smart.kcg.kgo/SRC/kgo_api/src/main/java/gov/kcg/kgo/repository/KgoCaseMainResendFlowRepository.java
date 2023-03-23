package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCaseMainResendFlow;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCaseMainResendFlowRepository extends BaseRepository<KgoCaseMainResendFlow, Integer> {
	
	/**
	 * CaseId 查找重送異動歷程.
	 *
	 * @param caseId the case id
	 * @return the list
	 */
	List<KgoCaseMainResendFlow> findByCaseId(String caseId);
}
