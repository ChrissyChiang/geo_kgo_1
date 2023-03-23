package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCasesetRentMain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface GeoKgoCasesetRentMainRepository extends JpaRepository<GeoKgoCasesetRentMain,String> {

    @Query(value = "SELECT * FROM GEO_KGO_CASESET_RENT_MAIN  WHERE service_id = :serviceId AND case_set_id = :caseSetId ",nativeQuery = true)
    GeoKgoCasesetRentMain getRentMainByServiceId(@Param("serviceId") String serviceId,@Param("caseSetId")String caseSetId );

    /** create table id */
    @Query(value = "SELECT SUBSTRING(MAX(case_rent_id),:indexOf,5) + 1 FROM GEO_KGO_CASESET_RENT_MAIN WHERE case_rent_id like CONCAT('%', :keyWord,'%')  ",nativeQuery = true)
    String getIdCountStr(@Param("indexOf")int indexOf ,@Param("keyWord")String keyWord);

    List<GeoKgoCasesetRentMain> findAllByCaseSetId(String CaseSetId);
}
