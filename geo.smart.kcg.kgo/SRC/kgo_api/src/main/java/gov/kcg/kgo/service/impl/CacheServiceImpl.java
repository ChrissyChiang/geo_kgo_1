package gov.kcg.kgo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumn;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnPK;
import gov.kcg.kgo.georepository.GeoKgoAppointmentColumnRepository;
import gov.kcg.kgo.util.SpringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetColumnPK;
import gov.kcg.kgo.model.KgoMydataColumn;
import gov.kcg.kgo.model.KgoMydataColumnPK;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.repository.KgoCasesetColumnRepository;
import gov.kcg.kgo.repository.KgoMydataColumnRepository;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.service.CacheService;

/**
 * The 暫存Cache serviceImpl.
 */
@Transactional(rollbackFor = Exception.class)
@Service("CacheService")
public class CacheServiceImpl implements CacheService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	@Autowired
	KgoCasesetColumnRepository kgoCasesetColumnRepository;
	
	@Autowired
	KgoMydataColumnRepository kgoMydataColumnRepository;
	
	@Autowired
	KgoOrganRepository kgoOrganRepository;


	@Autowired
	GeoKgoAppointmentColumnRepository geoKgoAppointmentColumnRepository;

	/**
	 * 取得表單欄位資料Map Cache.
	 *
	 * @return the all R case set column
	 */
	@Cacheable(cacheNames = "allCaseSetColumn", key = "#root.method.name", unless = "#result == null or #result.size() == 0")
	@Override
	public Map<KgoCasesetColumnPK, KgoCasesetColumn> getAllCaseSetColumn() {
		Map<KgoCasesetColumnPK, KgoCasesetColumn> allCaseSetColumnMap = new HashMap<>();
		try {
			List<KgoCasesetColumn> list = kgoCasesetColumnRepository.findAll();
			if(CollectionUtils.isNotEmpty(list)) {
				allCaseSetColumnMap = list.stream().collect(Collectors.toMap(KgoCasesetColumn :: getId, c -> c));
			}
			return allCaseSetColumnMap;
			
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("getAllRCaseSetColumn error " + e.getMessage(), e);
		}
	}

	/**
	 * 取得預約表單欄位資料Map Cache.
	 *
	 */
	@Cacheable(cacheNames = "allAppointmentColumns", key = "#root.method.name", unless = "#result == null or #result.size() == 0")
	public Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> getAllAppointmentColumn() {
		Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> allCaseSetColumnMap = new HashMap<>();
		try {
			List<GeoKgoAppointmentColumn> list = geoKgoAppointmentColumnRepository.findAll();
			if(CollectionUtils.isNotEmpty(list)) {
				allCaseSetColumnMap = list.stream().collect(Collectors.toMap(GeoKgoAppointmentColumn :: getId, c -> c));
			} //if(CollectionUtils.isNotEmpty(list))
			return allCaseSetColumnMap;
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("getAllAppointmentColumn error " + e.getMessage(), e);
		} //try catch
	} //getAllAppointmentColumn

	/**
	 * 取得MyData表單欄位資料Map Cache.
	 *
	 * @return the all my data column
	 */
	@Cacheable(cacheNames = "allMyDataColumn", key = "#root.method.name", unless = "#result == null or #result.size() == 0")
	@Override
	public Map<KgoMydataColumnPK, KgoMydataColumn> getAllMyDataColumn() {
		Map<KgoMydataColumnPK, KgoMydataColumn> allMyDataColumn = new HashMap<>();
		try {
			List<KgoMydataColumn> list = kgoMydataColumnRepository.findAll();
			if(CollectionUtils.isNotEmpty(list)) {
				allMyDataColumn = list.stream().collect(Collectors.toMap(KgoMydataColumn :: getId, c -> c));
			}
			return allMyDataColumn;
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("getAllRCaseSetColumn error " + e.getMessage(), e);
		}
	}

	/**
	 * 取得機關Map Cache.
	 *
	 * @return the all organ map
	 */
	@Cacheable(cacheNames = "allOrganMap", key = "#root.method.name", unless = "#result == null or #result.size() == 0")
	@Override
	public Map<String, KgoOrgan> getAllOrganMap() {
		Map<String, KgoOrgan> allOrganMap = new HashMap<>();
		try {
			List<KgoOrgan> list = kgoOrganRepository.findAll();
			if(CollectionUtils.isNotEmpty(list)) {
				allOrganMap = list.stream().collect(Collectors.toMap(KgoOrgan :: getOrganId, o -> o));
			}
			return allOrganMap;
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("getAllOrganMap error " + e.getMessage(), e);
		}
	}
}
