package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoMyDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoMyDataModelRepos extends JpaRepository<GeoKgoMyDataModel, String> {

    GeoKgoMyDataModel findByCaseSetId(String caseSetId);
}
