package gov.kcg.kgo.georepository; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.kcg.kgo.geoentity.GeoKgoCaseSetSiteMainFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GeoCaseSetSiteMainFileRepos extends JpaRepository<GeoKgoCaseSetSiteMainFile, Long> {

	@Query(value="SELECT * FROM GEO_KGO_CASESET_SITE_MAIN_FILE WHERE site_main_id = :siteMainId AND isDelete is null ",nativeQuery = true)
	List<GeoKgoCaseSetSiteMainFile> findAllBySiteMainId(@Param("siteMainId")String siteMainId);

	List<GeoKgoCaseSetSiteMainFile> findByIsDelete(Boolean isDelete);

		
} 
