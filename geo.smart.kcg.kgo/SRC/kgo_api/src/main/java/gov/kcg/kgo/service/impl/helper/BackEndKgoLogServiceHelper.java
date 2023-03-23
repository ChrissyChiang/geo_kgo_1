package gov.kcg.kgo.service.impl.helper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.KgoUseLogFunctionCodeCountDto;
import gov.kcg.kgo.dto.KgoUseLogIpCountDto;
import gov.kcg.kgo.dto.KgoUseLogLoginTypeCountDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.common.sso.LoginAuthType;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.repository.KgoUserLogRepository;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogFuncOrServiceCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogLoginLogoutCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogLoginTypeCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogLoninIpCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LoginLogoutData;

/**
 * 後台 - 軌跡紀錄 ServiceHelper.
 */
public class BackEndKgoLogServiceHelper extends KgoServiceHelper {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackEndKgoLogServiceHelper.class);
	
	/**
	 * 取得 軌跡紀錄 - 身分證驗證統計清單.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the login type count dto list
	 */
	public List<LogLoginTypeCount> getLoginTypeCountDtoList(String systemType, String funcCode, Timestamp startDate, Timestamp endDate) {
		List<LogLoginTypeCount> loginTypeCountList = new ArrayList<>();
		KgoUserLogRepository kgoUserLogRepository = SpringUtil.getBean(KgoUserLogRepository.class);
		// 軌跡紀錄 - 身分證驗證統計
		List<KgoUseLogLoginTypeCountDto> loginTypeCountDtoList = kgoUserLogRepository.findLoginTypeCount(systemType, funcCode, startDate, endDate);
		for (KgoUseLogLoginTypeCountDto dto : loginTypeCountDtoList) {
			LogLoginTypeCount loginTypeCount = new LogLoginTypeCount();
			
			LoginAuthType loginAuthType = LoginAuthType.getLoginAuthTypeByTypeName(dto.getLoginType());
			loginTypeCount.setLoginType(loginAuthType != null ? loginAuthType.getAuthName() : StringUtils.EMPTY);
			loginTypeCount.setCount(dto.getCount());
			loginTypeCountList.add(loginTypeCount);
		}
		return loginTypeCountList;
	}

	/**
	 * 取得 登入/登出時間統計物件.
	 *
	 * @param systemType the system type
	 * @param logList the log list
	 * @return the login logout data
	 */
	public LoginLogoutData getLoginLogoutData(String systemType, Timestamp startDate, Timestamp endDate) {
		KgoUserLogRepository kgoUserLogRepository = SpringUtil.getBean(KgoUserLogRepository.class);
		// 依系統別、新增時間查詢
		List<KgoUseLog> logList = kgoUserLogRepository.findLogbySystemTypeAndCreateTime(systemType, startDate, endDate);
		
		SystemTypeEnum systemTypeEnum = SystemTypeEnum.getSystemTypeEnum(systemType);
		// function = Login
		String loginFuncCode = SystemTypeEnum.B.equals(systemTypeEnum) ? BackendFunctionCodeEnum.Login.getFunctionCode() : FrontendFunctionCodeEnum.Login.getFunctionCode();
		
		// 登入/登出時間統計物件
		LoginLogoutData loginLogoutData = new LoginLogoutData();

		// 取得登入 計算清單
		List<LogLoginLogoutCount> loginCountList = getLogLoginLogoutCountList(logList, loginFuncCode);
		
		// function = Logout
		String logoutFuncCode = SystemTypeEnum.B.equals(systemTypeEnum) ? BackendFunctionCodeEnum.Logout.getFunctionCode() : FrontendFunctionCodeEnum.Logout.getFunctionCode();
		
		// 取得登出 計算清單
		List<LogLoginLogoutCount> logLogoutCountList = getLogLoginLogoutCountList(logList, logoutFuncCode);
		
		loginLogoutData.setLoginCountList(loginCountList);
		loginLogoutData.setLogoutCountList(logLogoutCountList);
		return loginLogoutData;
	}
	
	/**
	 * 取得 登入/登出 計算清單.
	 *
	 * @param logList the log list
	 * @return the log login logout count list
	 */
	private List<LogLoginLogoutCount> getLogLoginLogoutCountList (List<KgoUseLog> logList , String funcCode) {
		// 依據條件過濾logList
		List<KgoUseLog> filterList =  filterLogList(logList, funcCode);
		
		// 組成 得登入/登出 次數 map
		Map<String, Integer> countByDateMap = new HashMap<>();
		for(KgoUseLog log : filterList) {
			String logDate = DateUtil.timestampToString(log.getCreateTime(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
			
			// 計算區間內 登入/登出 次數
			if(countByDateMap.get(logDate) == null) {
				countByDateMap.put(logDate, 1);
			} else {
				Integer count = countByDateMap.get(logDate) + 1;
				countByDateMap.put(logDate, count);
			}
		}

		List<LogLoginLogoutCount> countList = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : countByDateMap.entrySet()) {
			LogLoginLogoutCount logoutCount = new LogLoginLogoutCount();
			logoutCount.setLogDate(entry.getKey());
			logoutCount.setCount(entry.getValue());
			countList.add(logoutCount);
		}
		// 依日期排序 (遠 -> 近)
		Collections.sort(countList, (r1, r2) -> r1.getLogDate().compareTo(r2.getLogDate()));
		return countList;
	}
	
	/**
	 * 使用者軌跡紀錄 - 取得承辦前10筆登入IP清單.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the ip count list
	 */
	public List<LogLoninIpCount> getTop10IpCountList(String systemType, String funcCode, Timestamp startDate, Timestamp endDate){
		KgoUserLogRepository kgoUserLogRepository = SpringUtil.getBean(KgoUserLogRepository.class);
		// 使用者軌跡紀錄(前10筆) - 承辦登入IP
		List<KgoUseLogIpCountDto> ipCountDtoList = kgoUserLogRepository.queryTop10KgoUseLogIpCount(systemType, funcCode, startDate, endDate);
		
		List<LogLoninIpCount> ipCountList = new ArrayList<>();
		for(KgoUseLogIpCountDto dto : ipCountDtoList) {
			LogLoninIpCount vo = new LogLoninIpCount(dto);
			ipCountList.add(vo);
		}
		return ipCountList;
	}
	
	/**
	 * 使用者軌跡紀錄 - 取得前10筆 功能使用分析清單.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the ip count list
	 */
	public List<LogFuncOrServiceCount> getTop10FunctionCountList(String systemType,  Timestamp startDate, Timestamp endDate){
		KgoUserLogRepository kgoUserLogRepository = SpringUtil.getBean(KgoUserLogRepository.class);
		// 使用者軌跡紀錄(前10筆) - 功能使用
		List<KgoUseLogFunctionCodeCountDto> functionCountDtoList = kgoUserLogRepository.queryTop10KgoUseLogFunctionCodeCount(systemType, startDate, endDate);
		
		List<LogFuncOrServiceCount> list = new ArrayList<>();
		for(KgoUseLogFunctionCodeCountDto dto : functionCountDtoList) {
			LogFuncOrServiceCount vo = new LogFuncOrServiceCount(dto);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 依據 功能過濾logList.
	 *
	 * @param logList the log list
	 * @param systemType the system type
	 * @param funcCode the func code
	 * @return the list
	 */
	private List<KgoUseLog> filterLogList(List<KgoUseLog> logList, String funcCode) {
		return logList.stream().filter(l -> StringUtils.equals(l.getFunctionCode(), funcCode)).collect(Collectors.toList());
	}
	
	/**
	 * Instantiates a new common service helper.
	 */
	public BackEndKgoLogServiceHelper() {

	}

	private static class Loader {
		/** The Constant instance. */
		private static final BackEndKgoLogServiceHelper instance = new BackEndKgoLogServiceHelper();
	}

	/**
	 * Gets the single instance of CommonServiceHelper.
	 *
	 * @return single instance of CommonServiceHelper
	 */
	public static BackEndKgoLogServiceHelper getInstance() {
		return Loader.instance;
	}
}
