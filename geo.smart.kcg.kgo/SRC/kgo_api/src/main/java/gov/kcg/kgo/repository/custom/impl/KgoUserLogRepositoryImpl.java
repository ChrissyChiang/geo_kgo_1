package gov.kcg.kgo.repository.custom.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import gov.kcg.kgo.dto.KgoUseLogFunctionCodeCountDto;
import gov.kcg.kgo.dto.KgoUseLogIpCountDto;
import gov.kcg.kgo.repository.custom.KgoUserLogRepositoryCustom;
import gov.kcg.kgo.repository.custom.condition.config.QueryParamSql;

@Repository("kgoUserLogRepositoryCustom")
public class KgoUserLogRepositoryImpl implements KgoUserLogRepositoryCustom {

	@PersistenceContext
	public EntityManager entityManager;
	
	/**
	 * 使用者軌跡紀錄(前10筆) - 承辦登入IP.
	 *
	 * @param systemType the system type
	 * @param functionCode the function code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Override
	public List<KgoUseLogIpCountDto> queryTop10KgoUseLogIpCount(String systemType, String functionCode, Timestamp startDate, Timestamp endDate) {
		// 取得使用者軌跡紀錄 - 承辦登入IP(前10筆) 查詢SQL物件
		QueryParamSql querySql = this.queryTop10KgoUseLogIpCountSql(systemType, functionCode, startDate, endDate);
		// 取得查詢SQL物件
		String sql = querySql.getSql();
		Map<String, Object> param = querySql.getParamMap();

		Query query = entityManager.createNativeQuery(sql, KgoUseLogIpCountDto.class);

		for (Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<KgoUseLogIpCountDto> queryList = query.getResultList();
		return queryList;
	}

	/**
	 * 取得使用者軌跡紀錄 - 承辦登入IP(前10筆) 查詢SQL物件.
	 * 
	 * @param condition  the condition
	 * @param queryCount the query count.
	 * @return the sql by condition
	 */
	private QueryParamSql queryTop10KgoUseLogIpCountSql(String systemType, String functionCode, Timestamp startDate, Timestamp endDate) {
		StringBuilder sb = new StringBuilder();
		QueryParamSql querySql = new QueryParamSql();

		sb.append(" Select ");
		sb.append(" 	top 10 IP,Count(IP) as Count ");
		sb.append("	from ");
		sb.append("		KGO_USE_LOG where SystemType=:systemType ");
		sb.append(" and ");
		sb.append("		FunctionCode =:functionCode ");
		sb.append("	and ");
		sb.append("		CreateTime>=:startDate and CreateTime <=:endDate");
		sb.append("	group by ");
		sb.append("		IP Order by Count desc");
		
		querySql.addParam("systemType", systemType);
		querySql.addParam("functionCode", functionCode);
		querySql.addParam("startDate", startDate);
		querySql.addParam("endDate", endDate);
		
		querySql.setSql(sb.toString());
		return querySql;
	}
	
	/**
	 * 使用者軌跡紀錄(前10筆) - 功能使用分析.
	 *
	 * @param systemType the system type
	 * @param functionCode the function code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Override
	public List<KgoUseLogFunctionCodeCountDto> queryTop10KgoUseLogFunctionCodeCount(String systemType, Timestamp startDate, Timestamp endDate) {
		// 取得使用者軌跡紀錄 - 功能使用(前10筆) 查詢SQL物件
		QueryParamSql querySql = this.queryTop10KgoUseLogFunctionCodeCountSql(systemType, startDate, endDate);
		// 取得查詢SQL物件
		String sql = querySql.getSql();
		Map<String, Object> param = querySql.getParamMap();

		Query query = entityManager.createNativeQuery(sql, KgoUseLogFunctionCodeCountDto.class);

		for (Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<KgoUseLogFunctionCodeCountDto> queryList = query.getResultList();
		return queryList;
	}
	
	/**
	 * 使用者軌跡紀錄(前10筆) - 功能使用分析 SQL.
	 *
	 * @param systemType the system type
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the query param sql
	 */
	private QueryParamSql queryTop10KgoUseLogFunctionCodeCountSql(String systemType, Timestamp startDate, Timestamp endDate) {
		StringBuilder sb = new StringBuilder();
		QueryParamSql querySql = new QueryParamSql();

		sb.append(" Select ");
		sb.append(" 	top 10 FunctionCode,Count(FunctionCode) as Count ");
		sb.append("	from ");
		sb.append("		KGO_USE_LOG where SystemType=:systemType ");
		sb.append("	and ");
		sb.append("		CreateTime>=:startDate and CreateTime <=:endDate");
		sb.append("	group by ");
		sb.append("		FunctionCode Order by Count desc");
		
		querySql.addParam("systemType", systemType);
		querySql.addParam("startDate", startDate);
		querySql.addParam("endDate", endDate);

		querySql.setSql(sb.toString());
		return querySql;
	}
}
