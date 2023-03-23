package gov.kcg.kgo.security.config;

import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.viewModel.base.response.ApiFailResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		LOGGER.info("CustomAuthenticationEntryPoint commence..."); //GEO 20210911 change log print
		
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ApiFailResponse apiFailResponse = new ApiFailResponse(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
		JSONObject json = new JSONObject(apiFailResponse);
		response.getWriter().print(json.toString());
		response.setStatus(HttpServletResponse.SC_OK);

	}
}
