package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoMydataLog;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataLogRepository extends BaseRepository<KgoMydataLog, Integer> {

    KgoMydataLog findByTxId(String txId);

}