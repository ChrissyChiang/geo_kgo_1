package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCasePaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface GeoKgoPaymentRecordRepository extends JpaRepository<GeoKgoCasePaymentRecord, Long> {


}
