package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoMsgRecord;
import gov.kcg.kgo.model.KgoMsgRecordPK;
import gov.kcg.kgo.repository.base.BaseRepository;

import java.util.List;

public interface KgoMsgRecordRepository extends BaseRepository<KgoMsgRecord, KgoMsgRecordPK> {

    List<KgoMsgRecord> findAllByIdCaseId(String caseId);

}
