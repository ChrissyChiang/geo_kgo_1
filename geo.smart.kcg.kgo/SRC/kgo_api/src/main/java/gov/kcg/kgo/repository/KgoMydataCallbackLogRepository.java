package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoMydataCallbackLog;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataCallbackLogRepository extends BaseRepository<KgoMydataCallbackLog, Integer> {

    KgoMydataCallbackLog findByTxId(String txId);

}