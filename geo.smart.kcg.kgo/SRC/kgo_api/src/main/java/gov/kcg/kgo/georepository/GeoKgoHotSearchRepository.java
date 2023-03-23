package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAgent;
import gov.kcg.kgo.geoentity.GeoKgoHotSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/** 
 * GEO 20220729 add
 * Repository for 搜尋引擎切換
 */
@Transactional
public interface GeoKgoHotSearchRepository extends JpaRepository<GeoKgoHotSearch, Integer> {

    @Query(nativeQuery = true, value = "SELECT Top 1 * FROM GEO_KGO_HOT_SEARCH C ORDER BY C.hot_search_seq DESC")
    GeoKgoHotSearch findByHotSearchSeqMax();
} 
