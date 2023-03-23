package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoSwitchUserLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeoSwitchUserLogRepos extends JpaRepository<GeoKgoSwitchUserLog, UUID> {

}
