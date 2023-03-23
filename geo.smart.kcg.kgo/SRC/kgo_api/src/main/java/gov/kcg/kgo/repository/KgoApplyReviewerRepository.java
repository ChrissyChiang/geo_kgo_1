package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoApplyReviewer;
import gov.kcg.kgo.model.KgoApplyReviewerPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoApplyReviewerRepositoryCustom;

public interface KgoApplyReviewerRepository
		extends BaseRepository<KgoApplyReviewer, KgoApplyReviewerPK>, KgoApplyReviewerRepositoryCustom {

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	public int deleteByIdCaseId(String caseId);
}
