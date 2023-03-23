package gov.kcg.kgo.service.operationmemo;

import java.io.Serializable;
import java.util.List;

import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.util.SpringUtil;

/**
 * 共通操作紀錄.
 */
public abstract class OperationMemo implements Serializable {

	private static final long serialVersionUID = -4148385908901394701L;

	/** 操作紀錄 . */
	protected List<OperationColumn> memoList;
	
	/** 系統類別 B、F(前/後台). */
	protected SystemTypeEnum systemType;

	/** log類型(新增、刪除、修改). */
	protected SysLogExeType sysLogType;
	
	/** 功能代碼. */
	protected String functionCode;
	
	/** 多語系util. */
	protected static MessageUtil messageUtil = SpringUtil.getBean(MessageUtil.class);

	public OperationMemo() {super();}
	
	public OperationMemo(SysLogExeType sysLogExeType) {}

	/**
	 * 作業內容(各功能實作內容).
	 *
	 * @return the display memo
	 */
	public abstract String getDisplayMemo();
	

	public SystemTypeEnum getSystemType() {
		return systemType;
	}

	public void setSystemType(SystemTypeEnum systemType) {
		this.systemType = systemType;
	}

	public void setSysLogType(SysLogExeType sysLogType) {
		this.sysLogType = sysLogType;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public SysLogExeType getSysLogType() {
		return sysLogType;
	}

	public List<OperationColumn> getMemoList() {
		return memoList;
	}

	public void setMemoList(List<OperationColumn> memoList) {
		this.memoList = memoList;
	}
}
