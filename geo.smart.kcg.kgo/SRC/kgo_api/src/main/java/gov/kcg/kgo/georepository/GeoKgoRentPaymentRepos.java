package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCaseRentPayment;
import gov.kcg.kgo.geoentity.GeoKgoCaseRentRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeoKgoRentPaymentRepos extends JpaRepository<GeoKgoCaseRentPayment, Long> {
    GeoKgoCaseRentPayment findByCaseId(String caseId);

    @Query(value = "SELECT SUBSTRING(MAX(rent_payment_id),:indexOf,5) + 1 FROM GEO_KGO_CASE_RENT_PAYMENT WHERE rent_payment_id like CONCAT('%', :keyWord,'%')  ",nativeQuery = true)
    String getIdCountStr(@Param("indexOf")int indexOf , @Param("keyWord")String keyWord);

    @Query(value="SELECT * FROM GEO_KGO_CASE_RENT_PAYMENT " +
                 "WHERE payment_status = 'YET' AND GETDATE() > pay_deadLine  ",nativeQuery = true)
    List<GeoKgoCaseRentPayment> findRentCaseOverPayDeadLine();
}
