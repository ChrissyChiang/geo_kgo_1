package gov.kcg.kgo.security.config;

import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.viewModel.base.response.ApiFailResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        LOGGER.info("CustomAccessDeniedHandler");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ApiFailResponse apiFailResponse = new ApiFailResponse(new ErrorResult(KgoCommonApiError.UNAUTHORIZED));
        JSONObject json = new JSONObject(apiFailResponse);
        response.getWriter().print(json.toString());
        response.setStatus(HttpServletResponse.SC_OK);

    }

}
