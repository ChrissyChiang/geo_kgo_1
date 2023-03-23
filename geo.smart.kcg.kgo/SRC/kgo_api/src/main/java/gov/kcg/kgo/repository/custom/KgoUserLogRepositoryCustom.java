package gov.kcg.kgo.repository.custom;

import java.sql.Timestamp;
import java.util.List;

import gov.kcg.kgo.dto.KgoUseLogFunctionCodeCountDto;
import gov.kcg.kgo.dto.KgoUseLogIpCountDto;

public interface KgoUserLogRepositoryCustom {
	/**
	 * 使用者軌跡紀錄(前10筆) - 承辦登入IP
	 * */
	public List<KgoUseLogIpCountDto> queryTop10KgoUseLogIpCount(String systemType, String functionCode, Timestamp startDate, Timestamp endDate);
	
	
	/**
	 * 使用者軌跡紀錄 - (前10筆) 功能使用分析.
	 *
	 * @param systemType the system type
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	public List<KgoUseLogFunctionCodeCountDto> queryTop10KgoUseLogFunctionCodeCount(String systemType, Timestamp startDate, Timestamp endDate);

}
