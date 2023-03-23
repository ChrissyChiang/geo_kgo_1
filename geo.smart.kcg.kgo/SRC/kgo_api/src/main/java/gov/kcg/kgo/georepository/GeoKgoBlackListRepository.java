package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * GEO 20221028 add
 * 更新 民政局黑名單資料 來源 城市資料平台
 */
@Transactional
public interface GeoKgoBlackListRepository extends JpaRepository<GeoKgoBlackList, Integer> {

    Boolean existsByUserId(String userId);
} 
