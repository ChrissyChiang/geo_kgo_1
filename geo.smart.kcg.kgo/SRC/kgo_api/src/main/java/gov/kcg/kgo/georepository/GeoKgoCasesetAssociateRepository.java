package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociate;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociatePK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;

/**
 * GEO 20211019 add
 * Repository for 案件關聯服務
 */
@Transactional
public interface GeoKgoCasesetAssociateRepository extends JpaRepository<GeoKgoCasesetAssociate, GeoKgoCasesetAssociatePK> {

    GeoKgoCasesetAssociate findGeoKgoCasesetAssociateById(GeoKgoCasesetAssociatePK pk);
    void deleteById_CasesetId(String caseSetId);
    void deleteById_AssociateCasesetId(String caseSetId);
    List<GeoKgoCasesetAssociate> findAllById_CasesetId(String caseSetId);
}