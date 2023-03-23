package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoMydataTx;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataTxRepository extends BaseRepository<KgoMydataTx, Integer> {

    KgoMydataTx findByTxId(String txId);

}