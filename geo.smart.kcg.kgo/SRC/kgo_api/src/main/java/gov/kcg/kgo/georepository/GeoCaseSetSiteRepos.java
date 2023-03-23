package gov.kcg.kgo.georepository; 

import java.sql.Timestamp;
import java.util.List;

import gov.kcg.kgo.geoentity.GeoKgoCaseSetSiteMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface GeoCaseSetSiteRepos extends JpaRepository<GeoKgoCaseSetSiteMain, String> {
	
    //場地上下架
	@Modifying
	@Query(value = "UPDATE GEO_KGO_CASESET_SITE_MAIN SET site_status = :siteStatus, edit_user = :loginUserId, edit_time = :now  WHERE site_main_id = :siteMainId" , nativeQuery = true)
	int siteStatusUpdate(@Param("siteMainId") String siteMainId, @Param("siteStatus") Integer siteStatus, @Param("loginUserId") String loginUserId, @Param("now") Timestamp now);

	//刪除場地
	@Modifying
	@Query(value = "UPDATE GEO_KGO_CASESET_SITE_MAIN SET is_delete =1 , edit_user = :loginUserId, edit_time = :now WHERE site_main_id = :siteMainId", nativeQuery = true)
	int deleteBySiteMainId(@Param("siteMainId") String siteMainId, @Param("loginUserId") String loginUserId, @Param("now") Timestamp now);

	@Query(value = "SELECT * FROM GEO_KGO_CASESET_SITE_MAIN  WHERE is_delete IS NULL  AND unit_id = :unitId AND organ_id = :organId AND site_type = :categroy " , nativeQuery = true)
	List<GeoKgoCaseSetSiteMain> getSiteByUserAndUnit(@Param("unitId") String unitId , @Param("organId")String organId, @Param("categroy")String categroy);

	@Query(value = "SELECT TOP 1 site_main_id from GEO_KGO_CASESET_SITE_MAIN where is_delete is null AND site_status = 1 AND edit_user = :user ORDER BY create_time ASC " , nativeQuery = true)
	String getFisrtSiteByUser(@Param("user")String user);

	@Query(value = "SELECT * FROM GEO_KGO_CASESET_SITE_MAIN WHERE site_status = 1 AND is_delete IS NULL AND site_main_id = :siteMainId ",nativeQuery = true)
	GeoKgoCaseSetSiteMain getActiveSiteById(@Param("siteMainId")String siteMainId);

	@Query(value ="SELECT * FROM GEO_KGO_CASESET_SITE_MAIN  WHERE is_delete IS NULL  AND unit_id = :unitId ",nativeQuery = true)
	List<GeoKgoCaseSetSiteMain> findAllByUnitId(String unitId);

}
