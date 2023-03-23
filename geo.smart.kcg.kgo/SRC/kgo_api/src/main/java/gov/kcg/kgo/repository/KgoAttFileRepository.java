package gov.kcg.kgo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoAttFile;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoAttFileRepository extends BaseRepository<KgoAttFile, String> {

	List<KgoAttFile> findByfSeqAndTypeAndStatus(Integer fSeq, String type, String status);

	@Modifying
	@Query(value = "UPDATE KgoAttFile SET status = :status, updateUser = :userId, updateTime = :updateTime  WHERE fSeq = :fSeq and type=:type")
	public int updateStatus(@Param("status") String status, @Param("userId") String userId,
			@Param("updateTime") Timestamp updateTime, @Param("fSeq") Integer fSeq, @Param("type") String type);
}
