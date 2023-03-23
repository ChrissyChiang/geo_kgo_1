package gov.kcg.kgo.repository;

import gov.kcg.kgo.dto.KgoCorrectRecordDto;
import gov.kcg.kgo.model.KgoCode;
import gov.kcg.kgo.model.KgoCorrectRecord;
import gov.kcg.kgo.repository.base.BaseRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KgoCorrectRecordRepository extends BaseRepository<KgoCorrectRecord, String> {

    List<KgoCorrectRecord> findByCaseId(@Param("caseId") String caseId);
}
