package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCasesetRentDate;
import gov.kcg.kgo.geoentity.GeoKgoCasesetRentTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GeoKgoCasesetRentTimeRepository extends JpaRepository<GeoKgoCasesetRentTime,String> {

    @Query(value = "SELECT * FROM GEO_KGO_CASESET_RENT_TIME WHERE rent_date_id = :rentDateId AND is_abandon IS null " ,nativeQuery = true)
    List<GeoKgoCasesetRentTime> getTimeByRentDateId(@Param("rentDateId")String RentDateId);

    @Modifying
    @Query(value="UPDATE GEO_KGO_CASESET_RENT_TIME set is_abandon = 1 WHERE rent_date_id = :rentDateId ",nativeQuery = true)
    void updateAbandonTimeByDateId(String rentDateId);
    /** create table id */
    @Query(value = "SELECT SUBSTRING(MAX(rent_time_id),:indexOf,5) + 1 FROM GEO_KGO_CASESET_RENT_TIME WHERE rent_time_id like CONCAT('%', :keyWord,'%') ",nativeQuery = true)
    String getIdCountStr(@Param("indexOf")int indexOf ,@Param("keyWord")String keyWord);

    List<GeoKgoCasesetRentTime> findByIsAbandon(Integer isAbandon);
}

