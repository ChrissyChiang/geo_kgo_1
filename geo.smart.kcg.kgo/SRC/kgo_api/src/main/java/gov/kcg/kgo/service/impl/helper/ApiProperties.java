package gov.kcg.kgo.service.impl.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 用component注入方式從api.properties取得API相關設定
 * 
 * @author TPIuser
 *
 */
@Component
@PropertySource("classpath:api.properties")
public class ApiProperties {

	// 城市資料平台 service url, https://api.kcg.gov.tw/api/service
	@Value("${api.kcg.service.url}")
	private String kcgServiceUrl;

	@Value("${api.kcg.appId}")
	private String kcgAppId;

	@Value("${api.kcg.token}")
	private String kcgToken;

	@Value("${api.kcg.service.key}")
	private String kcgServiceKey;

	public String getKcgServiceUrl() {
		return kcgServiceUrl;
	}

	public void setKcgServiceUrl(String kcgServiceUrl) {
		this.kcgServiceUrl = kcgServiceUrl;
	}

	public String getKcgAppId() {
		return kcgAppId;
	}

	public void setKcgAppId(String kcgAppId) {
		this.kcgAppId = kcgAppId;
	}

	public String getKcgToken() {
		return kcgToken;
	}

	public void setKcgToken(String kcgToken) {
		this.kcgToken = kcgToken;
	}

	public String getKcgServiceKey() {
		return kcgServiceKey;
	}

	public void setKcgServiceKey(String kcgServiceKey) {
		this.kcgServiceKey = kcgServiceKey;
	}

}
