package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoMydataFile;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataFileRepository extends BaseRepository<KgoMydataFile, Integer> {

	public KgoMydataFile findByTxIdAndPermissionTicket(String txId, String permissionTicket);
}