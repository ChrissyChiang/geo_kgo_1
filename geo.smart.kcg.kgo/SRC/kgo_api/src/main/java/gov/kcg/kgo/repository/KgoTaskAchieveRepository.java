package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoTaskAchieve;
import gov.kcg.kgo.model.KgoTaskAchievePK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoTaskAchieveRepository extends BaseRepository<KgoTaskAchieve, KgoTaskAchievePK> {

    int deleteByIdCaseSetId(String caseSetId);
}
