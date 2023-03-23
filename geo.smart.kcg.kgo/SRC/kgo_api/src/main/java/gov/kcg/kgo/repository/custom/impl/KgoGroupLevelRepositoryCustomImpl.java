package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.ClassManagementClassQueryDto;
import gov.kcg.kgo.repository.custom.KgoGroupLevelRepositoryCustom;

public class KgoGroupLevelRepositoryCustomImpl implements KgoGroupLevelRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoGroupLevelRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 分類維護-主畫面初始化
	 *
	 */
	@Override
	public List<ClassManagementClassQueryDto> findKgoGroupLevelData() {
		return findKgoGroupLevelData(null, null, null, null, null);
	}

	/**
	 * 分類維護-主畫面搜尋 搜尋組合條件 1. 主類別 2.主類別+次類別 3. 主類別 + 上下架時間 4. 主類別+次類別 + 上下架時間 5.上下架時間
	 * 6. 無任何參數
	 * 
	 * @param seq              次類別代碼
	 * @param mainType         主類別代碼
	 * @param publishTimeStart 上下架時間區間起日
	 * @param publishTimeEnd   上下架時間區間訖日
	 */
	@Override
	public List<ClassManagementClassQueryDto> findKgoGroupLevelData(Integer seq, String name, String mainType,
			String publishTimeStart, String publishTimeEnd) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"Select Seq as SEQ, Name as DETAIL_NM, MainType as MAIN_TYPE, convert(varchar, PublishTime, 112) as PUBLISH_TIME, [state] as [STATE] ");
		sb.append("From KGO_GROUP_LEVEL ");
		sb.append("Where 1=1 ");
		if (ObjectUtils.isNotEmpty(seq)) {
			sb.append("and Seq = :seq ");
		}
		if (ObjectUtils.isNotEmpty(name)) {
			sb.append("and name like CONCAT('%', :name,'%') ");
		}
		if (ObjectUtils.isNotEmpty(mainType)) {
			sb.append("and MainType = :mainType ");
		}

		if (StringUtils.isNotBlank(publishTimeStart)) {
			sb.append("and convert(varchar, PublishTime, 112) >= :publishTimeStart ");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			sb.append("and convert(varchar, PublishTime, 112) <= :publishTimeEnd ");
		}
		sb.append("Order by UpdateTime desc ");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), ClassManagementClassQueryDto.class);
		if (ObjectUtils.isNotEmpty(seq)) {
			query.setParameter("seq", seq);
		}
		if (ObjectUtils.isNotEmpty(name)) {
			query.setParameter("name", name);
		}
		if (ObjectUtils.isNotEmpty(mainType)) {
			query.setParameter("mainType", mainType);
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			query.setParameter("publishTimeStart", publishTimeStart);
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			query.setParameter("publishTimeEnd", publishTimeEnd);
		}

		return query.getResultList();
	}

}
