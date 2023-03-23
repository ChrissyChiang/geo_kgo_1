package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoUserQueryRs;

/**
 * GEO 20221014 add_Jim
 * 後台-查詢使用者資訊 API Service.
 */
public interface GeoUserQueryService {
    GeoUserQueryRs queryUsers(String organ, String unit, String account);
}