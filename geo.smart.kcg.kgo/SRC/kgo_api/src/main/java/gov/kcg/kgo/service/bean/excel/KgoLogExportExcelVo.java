package gov.kcg.kgo.service.bean.excel;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.common.sso.LoginAuthType;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.util.DateUtil;

/**
 * 軌跡統計 ExcelVo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KgoLogExportExcelVo {
	// 序號
	private Integer order;
	
	// 時間
	private String createTime;
	
	/** 後台軌跡紀錄 data */
	// 上層機關名稱
	private String parOrg;	
	// 機關名稱
	private String org;
	// 姓名
	private String name;
	/** 後台軌跡紀錄  data*/
	
	/** 前臺台軌跡紀錄 data */
	// 使用者資訊(依據前臺登入方式 抓取對應資訊 goolge、fb...)
	private String userInfo;
	
	/** 前臺台軌跡紀錄 data*/
	
	/** 前/後臺 memo */
	private String memo;
	
	// 功能名稱
	private String funcName;
	
	// 登入方式
	private String loginType;
	
	// 身分驗證(是/否)
	private String isVer;
	
	// IP
	private String ip;
	
	public KgoLogExportExcelVo() {}
	
	/**
	 * 後臺建構子.
	 *
	 * @param order the order
	 * @param funcName the func name
	 * @param isVer the is ver
	 * @param log the log
	 */
	public KgoLogExportExcelVo(int order, String parOrg, String org, String name, String funcName, String isVer, KgoUseLog log) {
		this.order = order;
		this.createTime = DateUtil.timestampToString(log.getCreateTime(), DateUtil.PATTEN_FULL_TIME_SLASH);
		this.parOrg = parOrg;
		this.org = org;
		this.name = name;
		this.funcName = funcName;
		this.memo = log.getMemo();
		
		LoginAuthType loginType = LoginAuthType.getLoginAuthTypeByTypeName(log.getLoginType());
		if(loginType != null) {
			this.loginType = loginType.getAuthName();
		} else {
			this.loginType = LoginAuthType.BASIC_ID.getAuthName();
		}
//		this.loginType = loginType != null ? loginType.getTypeName() : StringUtils.EMPTY;
		this.isVer = isVer; 
		this.ip = log.getIp();
	}
	
	/**
	 * 前臺建構子.
	 *
	 * @param order the order
	 * @param funcName the func name
	 * @param isVer the is ver
	 * @param log the log
	 */
	public KgoLogExportExcelVo(int order, String funcName, String isVer, KgoUseLog log) {
		this.order = order;
		this.userInfo = log.getCreateUser();
		this.createTime = DateUtil.timestampToString(log.getCreateTime(), DateUtil.PATTEN_FULL_TIME_SLASH);
		this.funcName = funcName;
		this.memo = log.getMemo();
		
		LoginAuthType loginAuthType = LoginAuthType.getLoginAuthTypeByTypeName(log.getLoginType());
		this.loginType = loginAuthType != null ? loginAuthType.getAuthName() : StringUtils.EMPTY;
		this.isVer = isVer; 
		this.ip = log.getIp();
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getIsVer() {
		return isVer;
	}

	public void setIsVer(String isVer) {
		this.isVer = isVer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getParOrg() {
		return parOrg;
	}

	public void setParOrg(String parOrg) {
		this.parOrg = parOrg;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

}
