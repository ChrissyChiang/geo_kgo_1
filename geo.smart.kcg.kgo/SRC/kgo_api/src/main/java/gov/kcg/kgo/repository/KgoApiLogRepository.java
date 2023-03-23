package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoApiLog;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoApiLogRepository extends BaseRepository<KgoApiLog, String> {

	List<KgoApiLog> findByReCountLessThanEqualAndIsSuccessFalse(Integer reCount);

}
