package gov.kcg.kgo.viewModel.cityCoinApi.base.response;

import gov.kcg.kgo.exception.ErrorResult;

/**
 * ApiFailResponse 共用object.
 * @param <T>
 */
public class CityCoinApiFailResponse extends  CityCoinApiBaseResponse<CityCoinBaseViewForm>  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new api response.
	 */
	public CityCoinApiFailResponse() {
	}

	// 異常訊息 設置
	public CityCoinApiFailResponse(ErrorResult errorResult) {
		super.rtnCode = errorResult.getErrorCode();
		super.rtnMessage = errorResult.getErrorDesc();
	}
}
