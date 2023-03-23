package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCaseSetApplyCount;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * GEO 20211005 add
 * <p>
 * Repository for 服務申辦統計
 */

@Transactional
public interface GeoKgoCaseSetApplyRepository extends JpaRepository<GeoKgoCaseSetApplyCount, String> {

} 
