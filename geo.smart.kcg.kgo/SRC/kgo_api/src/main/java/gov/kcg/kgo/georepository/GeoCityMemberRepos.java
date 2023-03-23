package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCaseSetSiteMain;
import gov.kcg.kgo.geoentity.GeoKgoCityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GeoCityMemberRepos extends JpaRepository<GeoKgoCityMember, UUID> {

    @Modifying
    @Query(value = "SELECT * FROM GEO_KGO_CITY_MEMBER WHERE realName = 1 " , nativeQuery = true)
    List<GeoKgoCityMember> findGeoKgoCityMemberWithRealName();

}
