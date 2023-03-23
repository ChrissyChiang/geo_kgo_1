package gov.kcg.kgo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.dto.CaseMainCaseSetIdCountDto;
import gov.kcg.kgo.dto.KgoUseLogLoginTypeCountDto;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoUserLogRepositoryCustom;

public interface KgoUserLogRepository extends BaseRepository<KgoUseLog, String>, KgoUserLogRepositoryCustom {

	/**
	 * 依系統別、新增時間查詢.
	 *
	 * @param systemType the system type
	 * @param functionCode the function code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Query(value = "Select l from KgoUseLog l where l.systemType =:systemType and l.createTime>=:startDate and l.createTime<=:endDate order by l.createTime")
	public List<KgoUseLog> findLogbySystemTypeAndCreateTime(String systemType, Timestamp startDate, Timestamp endDate);
	
	/**
	 * 依系統別、新增時間查詢、建立者(依機關權限).
	 *
	 * @param systemType the system type
	 * @param functionCode the function code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Query(value = "Select l from KgoUseLog l where l.systemType =:systemType "
			+ "and l.createTime>=:startDate and l.createTime<=:endDate and l.createUser in :organUserIdList order by l.createTime")
	public List<KgoUseLog> findLogbySystemTypeAndCreateTimeAndCreateUsers(String systemType, Timestamp startDate, Timestamp endDate, List<String> organUserIdList);
	
	
	 
	/**
	 * 軌跡紀錄 - 身分證驗證統計.
	 *
	 * @param systemType the system type
	 * @param functionCode the function code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the int
	 */
	@Query(value = "Select new gov.kcg.kgo.dto.KgoUseLogLoginTypeCountDto(l.loginType, Count(l.loginType) as Count) " + 
			"	from "
			+ "		KgoUseLog l " + 
			"	where "
			+ "		l.systemType =:systemType "
			+ "and "
			+ "		l.functionCode =:functionCode "
			+ "and "
			+ "		l.createTime>=:startDate and l.createTime<=:endDate " + 
			"	group by "
			+ "		l.loginType ")
	public List<KgoUseLogLoginTypeCountDto> findLoginTypeCount(String systemType, String functionCode, Timestamp startDate, Timestamp endDate);


}
