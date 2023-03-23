package gov.kcg.kgo.viewModel.frontend.caseform.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModelProperty;

public class MyDataTestRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "myDataClientId  測試使用")
	private String myDataClientId;

	@ApiModelProperty(notes = "統號")
	private String pid;

	@ApiModelProperty(notes = "MyDataTxId")
	private String txId;

	public String getMyDataClientId() {
		return myDataClientId;
	}

	public void setMyDataClientId(String myDataClientId) {
		this.myDataClientId = myDataClientId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

}
