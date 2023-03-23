package gov.kcg.kgo.aop;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.ParamSetEnum;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoParamSet;
import gov.kcg.kgo.repository.KgoParamSetRepository;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.SsoUtil;

/**
 * 後台 - 判斷系統參數設定登出時間.
 */
// TODO 由前端判斷登出
//@Aspect
//@Component
public class BackendSessionTimeoutValidateAspect {

	@Autowired
	protected HttpServletRequest request;
	
	// 後臺API controller
    private static final String ENTRY_LAYER ="gov.kcg.kgo.aop.pointcut.PointcutDefinition.backendControllerLayer()";
    
	private static final Logger LOGGER = LoggerFactory.getLogger(BackendSessionTimeoutValidateAspect.class);
	
	@Autowired
	private KgoParamSetRepository kgoParamSetRepository;
	
	/**
	 * 後台 - 判斷系統參數設定登出時間.
	 *
	 * @param joinPoint the join point
	 * @throws Throwable the throwable
	 */
	@Before(ENTRY_LAYER)
	public void validateUserApiAdmin(JoinPoint jp) throws Throwable {
		LOGGER.info("BackendSessionTimeoutValidateAspect validateUserApiAdmin...");
		BackendLoginUserInfo beUserInfo = KgoUtil.getBackendLoginUser();
		String userId = beUserInfo.getUserId();
		
		if(beUserInfo != null) {
			Date now = new Date();
			Date loginTime = beUserInfo.getLoginTime();

			// 單登閒置時間值
			Optional<KgoParamSet> kgoParamSet = kgoParamSetRepository.findById(ParamSetEnum.TO.getType());
			if(kgoParamSet.isPresent()) {
				String timeoutVal = kgoParamSet.get().getValue();
				// 單入時間 + time out 參數值
				if(NumberUtils.isDigits(timeoutVal)) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(loginTime);
					cal.add(Calendar.MINUTE, Integer.valueOf(timeoutVal));
					Date validateDate = cal.getTime();
					
					if(now.after(validateDate)) {
						LOGGER.info(">>> user loginTime : {} ", DateUtil.dateToString(loginTime, DateUtil.PATTEN_FULL_TIME_SLASH));
						// timeout 清除session
						Enumeration<String> em = request.getSession().getAttributeNames();
						while (em.hasMoreElements()) {
							request.getSession().removeAttribute(em.nextElement().toString());
						}
						request.getSession().invalidate();
						LOGGER.info(">>> session timeout userId : {} ", userId);
						
						// 閒置時間過長，您已被登出系統 .
						throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_SESSION_TIMEOUT));
					} else {
						// 更新後臺使用者 登入時間
						beUserInfo.setLoginTime(now);
						WebUtils.setSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY, beUserInfo);
						LOGGER.info(">>> user new loginTime : {} ", DateUtil.dateToString(now, DateUtil.PATTEN_FULL_TIME_SLASH));
					}
				}
			}
		}
	} //validateUserApiAdmin
	
}