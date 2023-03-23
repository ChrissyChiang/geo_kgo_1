package gov.kcg.kgo.aop;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.util.JwtUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.frontend.base.FrontendValidateCodeRequest;


/**
 * 前臺驗證碼 驗證AOP.
 */
@Component
@Aspect
@Order(2)
public class FrontendValidateCodeAspect {
	// 前臺API controller 需驗證碼驗證API
    private static final String ENTRY_LAYER ="gov.kcg.kgo.aop.pointcut.PointcutDefinition.forntendValidateCode()";
    
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontendValidateCodeAspect.class);

	@Autowired
	private JwtUtil jwtUtil;
	/**
	 * 前臺驗證碼 驗證.
	 *
	 * @param joinPoint the join point
	 * @throws Throwable the throwable
	 */
	@Before(ENTRY_LAYER)
	public void frontendValidateCode(JoinPoint joinPoint) throws Throwable {
		LOGGER.info(">>>>> 前臺驗證碼 驗證 frontendValidateCode() ");
		
		Object[] args = joinPoint.getArgs();
		for (Object obj : args) {
			if (obj instanceof FrontendValidateCodeRequest) {
				FrontendValidateCodeRequest rq = (FrontendValidateCodeRequest) obj;
				String validateCode = rq.getValidateCode();
				String validateCodeToken = rq.getValidateCodeToken();
				
				boolean needValidate = true;
				// 前臺使用者登入不需驗證碼
				try {
					KgoUtil.getFrontendLoginUser();
					if (StringUtils.isBlank(validateCode) && StringUtils.isBlank(validateCodeToken)) {
						needValidate = false;
					} else {
						needValidate = true;
					}
				// 前臺使用者未登入 需驗證碼
				} catch (Exception e) {
					LOGGER.info(">>>>> 前臺使用者未登入 需驗證碼 ");
					needValidate = true;
				}

				if (needValidate) {
					if (StringUtils.isBlank(validateCode) || StringUtils.isBlank(validateCodeToken)) {
						LOGGER.error(">>>>> 前臺驗證碼錯誤, 驗證碼: {}, token: {}", validateCode, validateCodeToken);
						// 驗證碼錯誤
						throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.VALIDATE_CODE_ERROR));
					}

					// 驗證token
					try {
						if (!jwtUtil.validateToken(validateCodeToken, validateCode)) {
							throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.VALIDATE_CODE_ERROR));
						}
					} catch (Exception e) {
						LOGGER.info(">>>>> 前臺驗證碼比對錯誤 驗證碼: {}, token: {}", validateCode, validateCodeToken);
						throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.VALIDATE_CODE_ERROR));
					}

					LOGGER.info(">>>>> 前臺驗證碼 驗證成功 驗證碼: {}, token: {}", validateCode, validateCodeToken);
				}
			}
		}
	}
}