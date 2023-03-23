package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoStatusMsg;
import gov.kcg.kgo.repository.base.BaseRepository;

import java.util.List;

public interface KgoStatusMsgRepository extends BaseRepository<KgoStatusMsg, Integer> {

    KgoStatusMsg findByOrganIdAndStatus(String organId, String status);

    List<KgoStatusMsg> findAllByOrganId(String organId);

}
