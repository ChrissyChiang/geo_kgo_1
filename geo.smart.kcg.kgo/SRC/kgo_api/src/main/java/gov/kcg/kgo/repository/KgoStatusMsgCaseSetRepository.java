package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoStatusMsgCaseSet;
import gov.kcg.kgo.repository.base.BaseRepository;

import java.util.List;

public interface KgoStatusMsgCaseSetRepository extends BaseRepository<KgoStatusMsgCaseSet, Integer> {

    List<KgoStatusMsgCaseSet> findAllByCaseSetId(String caseSetId);

    KgoStatusMsgCaseSet findByCaseSetIdAndStatusMsgSeq(String caseSetId, Integer statusMsgSeq);

}
