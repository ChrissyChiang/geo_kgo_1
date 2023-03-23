package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoScaApply;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoScaApplyRepositoryCustom;

import java.util.List;

public interface KgoScaApplyRepository extends BaseRepository<KgoScaApply, String>, KgoScaApplyRepositoryCustom {

    List<KgoScaApply> findByApplyUserAndStatusIn(String loginUserId, List<String> f);
}