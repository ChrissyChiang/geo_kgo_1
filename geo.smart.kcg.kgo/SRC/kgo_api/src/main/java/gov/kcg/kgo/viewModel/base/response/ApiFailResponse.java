package gov.kcg.kgo.viewModel.base.response;

import gov.kcg.kgo.exception.ErrorResult;

/**
 * ApiFailResponse 共用object.
 * @param <T>
 */
public class ApiFailResponse extends ApiBaseResponse<BaseViewForm>  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new api response.
	 */
	public ApiFailResponse() {
	}

	// 異常訊息 設置
	public ApiFailResponse(ErrorResult errorResult) {
		super.rtnCode = errorResult.getErrorCode();
		super.msg = errorResult.getErrorDesc();
	}
}
