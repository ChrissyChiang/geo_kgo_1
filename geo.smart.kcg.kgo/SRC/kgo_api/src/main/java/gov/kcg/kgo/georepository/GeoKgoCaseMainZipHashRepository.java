package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCaseMainZipHash;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * GEO 20221009 add_Jim
 * MyDataZipHash
 */
@Transactional
public interface GeoKgoCaseMainZipHashRepository extends JpaRepository<GeoKgoCaseMainZipHash, String> {
    GeoKgoCaseMainZipHash findByTxId(String txId);
}
