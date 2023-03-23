package gov.kcg.kgo.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.repository.KgoUserLogRepository;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationMemo;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.util.SpringUtil;

/**
 * Kgo 共通service.
 */
public abstract class KgoBaseServiceImpl {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoBaseServiceImpl.class);

	@Autowired
	protected KgoUserLogRepository kgoUserLogRepository;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected MessageUtil messageUtil;

	// TODO 正式機demo用 不顯示 服務已下架，請選擇其他服務。 demo完要移除
	/** 正式機demo用 不顯示 服務已下架，請選擇其他服務。 demo完要移除 */
//	protected Boolean showPublishState() {
//		if (StringUtils.isBlank(SpringUtil.getProperty("demo.showPublishState"))) {
//			return true;
//		}
//		Boolean isShow = Boolean.valueOf(SpringUtil.getProperty("demo.showPublishState"));
//
//		return isShow == null ? false : isShow;
//	}

	/**
	 * 目前是否為開發環境
	 * 
	 * @return
	 */
	protected Boolean isDevMode() {
		String value = SpringUtil.getProperty("dev.mode");
		return Boolean.parseBoolean(value);
	}

	/**
	 * 取得操作記錄MEMO.
	 * 
	 * @param <T>   the generic type
	 * @param rq    the rq.
	 * @param clazz the clazz.
	 * @return the T @FrontendFunctionCodeEnum 、@BackendFunctionCodeEnum
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected <T extends Enum<?>> OperationApiMemo genOperationMemo(SystemTypeEnum systemType, SysLogExeType sysLogExeType, T funcEnum) {
		OperationApiMemo opMemo = new OperationApiMemo();
		opMemo.setSystemType(systemType);
		opMemo.setSysLogType(sysLogExeType);

		String funcCode = StringUtils.EMPTY;

		/** 前後臺功能名稱 */
		if (funcEnum instanceof FrontendFunctionCodeEnum) {
			funcCode = ((FrontendFunctionCodeEnum) funcEnum).getFunctionCode();
		}

		if (funcEnum instanceof BackendFunctionCodeEnum) {
			funcCode = ((BackendFunctionCodeEnum) funcEnum).getFunctionCode();
		}
		opMemo.setFunctionCode(funcCode);

		return opMemo;
	}

	/**
	 * 儲存操作紀錄.
	 *
	 * @param <T>  the generic type
	 * @param memo the memo
	 */
	protected <T extends OperationMemo> void saveOperationLog(T memo) {
		try {
			if (memo != null) {
				KgoUseLog userLog = new KgoUseLog();
				// 後台
				if (SystemTypeEnum.B.equals(memo.getSystemType())) {
					BackendLoginUserInfo beUser = KgoUtil.getBackendLoginUser();
					if (beUser != null) {
						LOGGER.info("beUser:");
						LOGGER.info(JSON.toJSONString(beUser));
						List<String> authMethodArr = beUser.getKcgUserBasicInfo().getAuthMethod();
						if (CollectionUtils.isNotEmpty(authMethodArr)) {
							String authMethod = authMethodArr.get(0);
							// @LoginAuthType 登入認證類型
							LoginAuthType loginAuthType = LoginAuthType.getLoginAuthType(authMethod);

							userLog.setLoginType(loginAuthType != null ? loginAuthType.getTypeName() : StringUtils.EMPTY);
						}
						userLog.setCreateUser(beUser.getUserId());
					}
				}

				// 前台
				if (SystemTypeEnum.F.equals(memo.getSystemType())) {
					FrontendLoginUserInfo feUser = null;
					try {
						feUser = KgoUtil.getFrontendLoginUser();
					} catch (Exception e) {
						LOGGER.error(">>>>> 儲存操作紀錄 saveOperationLog() 前台使用者未登入");
					}
					if (feUser != null) {
						// @LoginAuthType 登入認證類型
						userLog.setLoginType(feUser.getLoginAuthTokenType().getAuthType()[0].getTypeName());
						String name = StringUtils.EMPTY;
						if (StringUtils.isNotBlank(feUser.getName())) {
							name = String.format(" (%s)", feUser.getName());
						}
						userLog.setCreateUser(feUser.getUserAccount() + name);
					}
				}
				userLog.setSystemType(memo.getSystemType().getSystemType());
				userLog.setFunctionCode(memo.getFunctionCode());
				userLog.setIp(KgoUtil.getClientIp());
				userLog.setCreateTime(DateUtil.getCurrentTimestamp());
				userLog.setAction(memo.getSysLogType() != null ? messageUtil.getMessage(memo.getSysLogType().getCodeMsgKey()) : "");
				// 客製log display
				userLog.setMemo(memo.getDisplayMemo());
				kgoUserLogRepository.save(userLog);
			} else {
				// 儲存操作紀錄錯誤
				throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_LOG_ERROR));
			}
		} catch (Exception e) {
			LOGGER.error(">>>>>Exception 儲存操作紀錄錯誤", e);
			// throw e;
		}
	}

	/**
	 * 儲存物件至 session.
	 *
	 * @param <D>         the generic type
	 * @param sessionName the session name
	 * @param object      the object
	 */
	protected <T> void saveToSession(String sessionName, T object) {
		WebUtils.setSessionAttribute(request, sessionName, object);

	}

	/**
	 * 從session 取出物件.
	 *
	 * @param <D>         the generic type
	 * @param sessionName the session name
	 * @param clazz       the clazz
	 */
	protected <T> T loadFromSession(String sessionName, Class<T> clazz) {
		T object = (T) WebUtils.getSessionAttribute(request, sessionName);
		return object;
	}

	protected String getSessionId() {
		try {
			return request != null ? request.getSession().getId() : StringUtils.EMPTY;
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 移除 Session
	 * 
	 * @param sessionName
	 */
	protected void removeSession(String sessionName) {
		request.getSession().removeAttribute(sessionName);
	}
}
