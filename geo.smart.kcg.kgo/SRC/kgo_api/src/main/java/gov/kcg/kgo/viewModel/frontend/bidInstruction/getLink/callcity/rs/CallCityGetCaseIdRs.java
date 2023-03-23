package gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.callcity.rs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.callcity.rs.bean.CallCityRsData;

/**
 * 呼叫程式資料平台 - 取得案件編號 rs
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallCityGetCaseIdRs implements Serializable {
	
	private String contentType;
	
	private String isImage;
	
	/** 呼叫程式資料平台 - 取得案件編號  Data. */
	private CallCityRsData data;
	
	private String id;
	
	private String success;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getIsImage() {
		return isImage;
	}

	public void setIsImage(String isImage) {
		this.isImage = isImage;
	}

	public CallCityRsData getData() {
		return data;
	}

	public void setData(CallCityRsData data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
