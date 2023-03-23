/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 * (C) Copyright IBM Corp. 2007.
 * ===========================================================================
 */
package gov.kcg.kgo.exception;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.error.ActivitiApiError;
import gov.kcg.kgo.enums.error.CaseHandleApiError;
import gov.kcg.kgo.enums.error.CaseProcessSearchApiError;
import gov.kcg.kgo.enums.error.CaseViewUpdateStatusApiError;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.util.SpringUtil;

/**
 * 異常訊息 共通exception處裡.
 */
public class ErrorResult implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 交易回應代碼. */
	private String errorCode = "9999";

	/** 狀態等級. */
	private SeverityType severity = SeverityType.ERROR;

	/** 狀態描述. */
	private String errorDesc = StringUtils.EMPTY;

	/** i18n 對應訊息處裡util. */
	private static MessageUtil messageUtil = (MessageUtil) SpringUtil.getBean(MessageUtil.class);

	/**
	 * Constructor.
	 */
	public ErrorResult() {
		super();
		MessageUtil messageUtil = (MessageUtil) SpringUtil.getBean(MessageUtil.class);
		// 預設訊息 : 系統錯誤
		this.errorDesc = messageUtil.getMessage(KgoCommonApiError.COMMON_SYSTEM_ERROR.getErrorMsgKey());
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(KgoCommonApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(KgoCommonApiError error) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey());
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(KgoFrontEndApiError error) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey());
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(KgoBackEndApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(KgoBackEndApiError error) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey());
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(KgoFrontEndApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(CityApiError error) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey());
	}

	public ErrorResult(ActivitiApiError error) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey());
	}

	/**
	 * Constructor.
	 * 
	 * @param error
	 * @param errorParams
	 */
	public ErrorResult(CaseProcessSearchApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param error
	 * @param errorParams
	 */
	public ErrorResult(CaseViewUpdateStatusApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param error
	 * @param errorParams
	 */
	public ErrorResult(CaseHandleApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param code the code.
	 */
	public ErrorResult(CityApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	public ErrorResult(ActivitiApiError error, Object... errorParams) {
		this.errorCode = error.getErrorCode();
		this.errorDesc = getErrorDescI18n(error.getErrorMsgKey(), errorParams);
	}

	/**
	 * 取得錯誤描述 I18n.
	 *
	 * @param budgetError the budget error
	 * @return the error desc
	 */
	public static String getErrorDescI18n(String errorMsgKey) {

		if (errorMsgKey == null) {
			// 未知錯誤訊息代碼.
			return messageUtil.getMessage(Constants.UNKNOWN_EXCEPTION_CODE);
		}
		// 取得錯誤描述 I18n
		String errorDesc = messageUtil.getMessage(errorMsgKey);
		return errorDesc;
	}

	/**
	 * 取得錯誤描述 I18n多語系.
	 *
	 * @param budgetError the budget error
	 * @param errorParams the error params
	 * @return the error desc
	 */
	public static String getErrorDescI18n(String errorMsgKey, Object... errorMsgKeyParams) {
		MessageUtil messageUtil = (MessageUtil) SpringUtil.getBean(MessageUtil.class);
		if (errorMsgKey == null) {
			return messageUtil.getMessage(Constants.UNKNOWN_EXCEPTION_CODE);
		}
		// 取得錯誤描述 I18n
		String errorDesc = messageUtil.getMessage(errorMsgKey, errorMsgKeyParams);
		return errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public SeverityType getSeverity() {
		return severity;
	}

	public void setSeverity(SeverityType severity) {
		this.severity = severity;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
