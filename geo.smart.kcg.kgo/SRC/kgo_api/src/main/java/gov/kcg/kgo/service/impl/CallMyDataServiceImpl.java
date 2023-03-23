package gov.kcg.kgo.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import gov.kcg.kgo.service.CallMyDataService;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.GetServiceDataRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.GetSpSignatureParameterRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.VerifySpIdRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.GetServiceDataRs;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.GetSpSignatureParameterRs;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.VerifySpIdRs;

@Service
public class CallMyDataServiceImpl implements CallMyDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CallMyDataServiceImpl.class);

	@Value("${mydata.api.url}")
	private String myDataUrl;

	@Autowired
	@Qualifier("myDataRestTemplate")
	private RestTemplate myDataRestTemplate;

	@Override
	public GetSpSignatureParameterRs getSignatureParameterByTxId(GetSpSignatureParameterRq getSpSignatureParameterRq) {
		GetSpSignatureParameterRs getSpSignatureParameterRes = null;
		LOGGER.info("getSignatureParameterByTxId start...");
		try {
			String dataJson = JsonUtil.toJSONString(getSpSignatureParameterRq);

			LOGGER.info("getSignatureParameterByTxId GetSpSignature request is = {}", dataJson);

			HttpEntity<String> httpEntity = new HttpEntity<>(dataJson, getHttpHeader());

			ResponseEntity<String> responseEntity = myDataRestTemplate.postForEntity(myDataUrl, httpEntity, String.class);

			if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value())
				getSpSignatureParameterRes.setHttpStatus(responseEntity.getStatusCodeValue());
			else if (!responseEntity.getBody().isEmpty()) {
				getSpSignatureParameterRes = JsonUtil.getObject(responseEntity.getBody(), GetSpSignatureParameterRs.class);
				getSpSignatureParameterRes.setJsonData(responseEntity.getBody());
			}
			LOGGER.info("getSignatureParameterByTxId end...");
		} catch (Exception ex) {
			LOGGER.error("Get can't handler exception from getSignatureParameterByTxId ...", ex);

			getSpSignatureParameterRes.setHttpStatus(999);
		}

		return getSpSignatureParameterRes;
	}

	@Override
	public VerifySpIdRs verifyByTxIdAndDataAndPkcs7File(VerifySpIdRq verifySpIdRq) {
		LOGGER.info("verifyByTxIdAndDataAndPkcs7File start,verifySpIdRq="+verifySpIdRq);
		VerifySpIdRs verifySpIdRs = null;

		try {
			String dataJson = JsonUtil.toJSONString(verifySpIdRq);

			LOGGER.info("verifyByTxIdAndDataAndPkcs7File VerifySpId request is = {}", dataJson);

			HttpEntity<String> httpEntity = new HttpEntity<>(dataJson, getHttpHeader());

			ResponseEntity<String> responseEntity = myDataRestTemplate.postForEntity(myDataUrl, httpEntity, String.class);

			if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value())
				verifySpIdRs.setHttpStatus(responseEntity.getStatusCodeValue());
			else if (!responseEntity.getBody().isEmpty()) {
				verifySpIdRs = JsonUtil.getObject(responseEntity.getBody(), VerifySpIdRs.class);
				verifySpIdRs.setJsonData(responseEntity.getBody());
			}
			LOGGER.info("verifyByTxIdAndDataAndPkcs7File end");
		} catch (Exception ex) {
			LOGGER.error("Get can't handler exception from verifyByTxIdAndDataAndPkcs7File ...", ex);

			verifySpIdRs.setHttpStatus(999);
		}

		return verifySpIdRs;
	}

	@Override
	public GetServiceDataRs getServiceDataByTxIdAndPermissionTicket(GetServiceDataRq getServiceData) {
		LOGGER.info("getServiceDataByTxIdAndPermissionTicket ...start");
		GetServiceDataRs getServiceDataRs = new GetServiceDataRs();
		ResponseEntity<String> responseEntity = null;
		HttpStatus statusCode = null;
		HttpHeaders hs = null;

		try {
			LOGGER.info("PermissionTicket is = {}", getServiceData.getPermissionTicket());

			HttpHeaders httpHeaders = getHttpHeader();
			httpHeaders.set("permission_ticket", getServiceData.getPermissionTicket());

			HttpEntity httpEntity = new HttpEntity(httpHeaders);

			String apiUrl = myDataUrl + "service/data";
			LOGGER.info("getServiceDataByTxIdAndPermissionTicket MyData Url:" + apiUrl);
			LOGGER.info("getServiceDataByTxIdAndPermissionTicket Headers:" + JsonUtil.toJSONString(httpHeaders));

			try {
				responseEntity = myDataRestTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, String.class);
				statusCode = responseEntity.getStatusCode();
			} catch (HttpStatusCodeException exception) {
				statusCode = exception.getStatusCode();
				hs = exception.getResponseHeaders();
			}

			LOGGER.info("getServiceDataByTxIdAndPermissionTicket StatusCode:" + statusCode.toString());
			getServiceDataRs.setHttpStatus(statusCode.value());

			if (HttpStatus.TOO_MANY_REQUESTS == statusCode) {
				Integer retryAfter = 60;
				if (ObjectUtils.isNotEmpty(hs) && hs.containsKey("Retry-After")) {
					retryAfter = Integer.valueOf(hs.get("Retry-After").get(0));
					LOGGER.info("MyData回覆429  Retry-After -> {}", retryAfter);
				} else {
					LOGGER.warn("MyData回覆429  但無 Retry-After 值！");
				}
				getServiceDataRs.setDelaySeconds(String.valueOf(retryAfter));
			} else if (HttpStatus.OK == statusCode || HttpStatus.CREATED == statusCode) {
				if (!responseEntity.getBody().isEmpty()) {
					getServiceDataRs.setJwe(responseEntity.getBody());
					getServiceDataRs.setJsonData(responseEntity.getBody());
				}
			} else
				getServiceDataRs.setHttpStatus(responseEntity.getStatusCodeValue());

		} catch (Exception ex) {
			LOGGER.error("Get can't handler exception from getServiceDataByTxIdAndPermissionTicket ...", ex);

			getServiceDataRs.setHttpStatus(999);
		}
		LOGGER.info("getServiceDataByTxIdAndPermissionTicket ...end");
		return getServiceDataRs;
	}

	private HttpHeaders getHttpHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.ALL.APPLICATION_JSON);

		return httpHeaders;
	}

}