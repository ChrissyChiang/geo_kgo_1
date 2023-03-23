package gov.kcg.kgo.viewModel.cityCoinApi.base.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.exception.ErrorResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * CityCoinApiResponse 共用object.
 */
@ApiModel(description = "城市幣任務回應資料")
public class CityCoinApiBaseResponse<T extends CityCoinBaseViewForm> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 回應代碼 成功代碼: 0000. */
	protected String rtnCode = Constants.SUCCESS_CODE;

	/** 錯誤訊息. */
	@ApiModelProperty(dataType = "string")
	protected String rtnMessage = "";

	/** 物件資料. */
	protected T result;

	// TODO validate

	/**
	 * Instantiates a new api response.
	 */
	public CityCoinApiBaseResponse() {
	}

	public CityCoinApiBaseResponse(T result) {
		this.result = result;
	}

	public CityCoinApiBaseResponse(String rtnCode, String rtnMessage) {
		this.rtnCode = rtnCode;
		this.rtnMessage = rtnMessage;
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
	 * @param error the new error
	 */
	public void setError(ErrorResult errorResult) {
		this.rtnCode = errorResult.getErrorCode();
		this.rtnMessage = errorResult.getErrorDesc();
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
