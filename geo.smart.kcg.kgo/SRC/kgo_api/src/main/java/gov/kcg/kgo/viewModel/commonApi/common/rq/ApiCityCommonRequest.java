package gov.kcg.kgo.viewModel.commonApi.common.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * 城市資料平台 共通 rq 物件.
 * ApiCityCommonRequest rq object.
 */
public class ApiCityCommonRequest extends ApiRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "交易序號 (格式:yyyyMMddHHmmssSSS, ex: 20201114123531343)", example="20201114123531343")
	protected String txId;
	
	@ApiModelProperty(notes = "機關單位代碼", example="397007000Q")
	protected String orgId;

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
