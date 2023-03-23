package gov.kcg.kgo.service;

import java.util.Map;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumn;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnPK;
import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetColumnPK;
import gov.kcg.kgo.model.KgoMydataColumn;
import gov.kcg.kgo.model.KgoMydataColumnPK;
import gov.kcg.kgo.model.KgoOrgan;

/**
 * 暫存Cache service.
 */
public interface CacheService {
	
	/**
	 * 取得表單欄位資料Map Cache.
	 *
	 * @return the all R case set column
	 */
	Map<KgoCasesetColumnPK, KgoCasesetColumn>getAllCaseSetColumn();
	
	/**
	 * 取得MyData表單欄位資料Map Cache.
	 *
	 * @return the all R case set column
	 */
	Map<KgoMydataColumnPK, KgoMydataColumn>getAllMyDataColumn();

	
	/**
	 * 取得機關Map Cache.
	 *
	 * @return the all organ map
	 */
	Map<String, KgoOrgan> getAllOrganMap();

	/**
	 * 取得預約表單欄位資料Map Cache.
	 *
	 * @return the all R case set column
	 */
	Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> getAllAppointmentColumn();
}
