package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCasesetRentDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface GeoKgoCasesetRentDateRepository extends JpaRepository<GeoKgoCasesetRentDate,String> {
    @Query(value ="SELECT  * FROM GEO_KGO_CASESET_RENT_DATE  WHERE rent_date_id = :rentDateId",nativeQuery = true)
    GeoKgoCasesetRentDate queryByRentDateId(@Param("rentDateId")String rentMainId);

    @Query(value ="SELECT * FROM  GEO_KGO_CASESET_RENT_DATE  " +
                    "WHERE case_rent_id = :caseRentId AND detail_date >= convert(datetime,:startTime) and detail_date <= convert(datetime,:endTime) "+
                    "ORDER BY detail_date ",nativeQuery = true)
    List<GeoKgoCasesetRentDate> getWeekList(@Param("caseRentId")String rentMainId, String startTime, String endTime);

    GeoKgoCasesetRentDate findByCaseRentIdAndDetailDate(String caseRentId , Timestamp detailDate);
    /** create table id */
    @Query(value = "SELECT SUBSTRING(MAX(rent_date_id),:indexOf,5) + 1 FROM GEO_KGO_CASESET_RENT_DATE WHERE rent_date_id like CONCAT('%', :keyWord,'%') ",nativeQuery = true)
    String getIdCountStr(@Param("indexOf")int indexOf ,@Param("keyWord")String keyWord);
}

