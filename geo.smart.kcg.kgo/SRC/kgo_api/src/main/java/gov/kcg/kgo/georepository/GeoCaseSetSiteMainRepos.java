package gov.kcg.kgo.georepository; 

import java.util.List;


import gov.kcg.kgo.geoentity.GeoKgoCaseSetSiteMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoCasesetSiteMain;


public interface GeoCaseSetSiteMainRepos extends JpaRepository<GeoKgoCaseSetSiteMain, String> {
	

	@Query(value="select * from GEO_KGO_CASESET_SITE_MAIN a"+
			     " inner join GEO_KGO_CASESET_SITE_DETAIL b on a.site_main_id = b.site_main_id"+
			     " where b.case_set_id = :casesetId", nativeQuery = true)
	List<GeoKgoCaseSetSiteMain> findByCaseset(@Param("casesetId") String casesetId);

	List<KgoCasesetSiteMain> findAllBy();
} 
