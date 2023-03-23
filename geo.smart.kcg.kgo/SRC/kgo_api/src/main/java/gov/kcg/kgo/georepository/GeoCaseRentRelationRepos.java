package gov.kcg.kgo.georepository; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.geoentity.GeoKgoCaseRentRelation;


/** 
 * Repository for 民眾預約場地時段
 */

public interface GeoCaseRentRelationRepos extends JpaRepository<GeoKgoCaseRentRelation, Long> {

	@Query(value="SELECT * FROM GEO_KGO_CASE_RENT_RELATION "+
			     "WHERE CHARINDEX(  :rentTimeId  , rent_time_id ) > 0 ", nativeQuery = true)
	List<GeoKgoCaseRentRelation> checkBookedRentTime(@Param("rentTimeId") String rentTimeId );

	GeoKgoCaseRentRelation findByCaseId(String caseId);

	List<GeoKgoCaseRentRelation> findBySiteMainId(String siteMainId);

} 
