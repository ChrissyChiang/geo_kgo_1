package gov.kcg.kgo.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.viewModel.base.response.ApiFailResponse;

@ControllerAdvice
public class CustomExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

	/**
	 * Default api error handler.
	 *
	 * @param req the rq
	 * @param e   the e
	 * @return the string
	 * @throws Exception the exception
	 */
	@ExceptionHandler(value = KgoApiException.class)
	@ResponseBody
	public ResponseEntity<ApiFailResponse> defaultApiErrorHandler(HttpServletRequest req, KgoApiException e) throws Exception {
		Writer writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		if (e.getErrorResult().getErrorCode().equalsIgnoreCase("9999")) {
			LOGGER.error("KgoApiException ERROR: " + writer.toString(), e);
		} else {
			LOGGER.error("KgoApiException ERROR: " + writer.toString());
		}

		return new ResponseEntity<ApiFailResponse>(new ApiFailResponse(e.getErrorResult()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseEntity<ApiFailResponse> apiErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		Writer writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		LOGGER.error("Exception ERROR: " + writer.toString(), e);

		return new ResponseEntity<ApiFailResponse>(new ApiFailResponse(new ErrorResult(KgoCommonApiError.COMMON_UNKNOWN_EXCEPTION)), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
