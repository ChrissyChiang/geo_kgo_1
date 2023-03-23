package gov.kcg.kgo.viewModel.base.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.exception.ErrorResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ApiResponse 共用object.
 */
@ApiModel(description = "共通回應資料")
public class ApiBaseResponse<T extends BaseViewForm> implements Serializable  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 回應代碼 成功代碼: 0000. */
	protected String rtnCode = Constants.SUCCESS_CODE;
	
	/** 錯誤訊息. */
	@ApiModelProperty(dataType = "string")
	protected String msg = "";
	
	/** 物件資料. */
	protected T data;
	
	
	// TODO validate

	/**
	 * Instantiates a new api response.
	 */
	public ApiBaseResponse() {
	}
	
	public ApiBaseResponse(T object) {
		this.data = object;
	}
	
	public ApiBaseResponse(String rtnCode, String msg) {
		this.rtnCode = rtnCode;
		this.msg = msg;
	}

	/**
	 * 檢查是否有錯誤.
	 *
	 * @return true, if successful
	 */
	public boolean hasError() {	
		return !StringUtils.equals(Constants.SUCCESS_CODE, this.rtnCode);
	}
	
	/**
	 * 設置錯誤訊息.
	 *
	 * @param errorResult the new error
	 */
	public void setError(ErrorResult errorResult) {
		this.rtnCode = errorResult.getErrorCode();
		this.msg = errorResult.getErrorDesc();
	}
	
	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
