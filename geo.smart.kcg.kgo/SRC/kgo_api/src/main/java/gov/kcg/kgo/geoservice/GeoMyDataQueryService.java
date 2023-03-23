package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.geoviewmodel.frontend.rq.MyDataQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataQueryRs;

/**
 * GEO 20221008 add_Jim
 * 前台-MyData案件查詢 API Service.
 */
public interface GeoMyDataQueryService {
    GeoMyDataQueryRs queryMyDataCase(MyDataQueryRq rq);
}