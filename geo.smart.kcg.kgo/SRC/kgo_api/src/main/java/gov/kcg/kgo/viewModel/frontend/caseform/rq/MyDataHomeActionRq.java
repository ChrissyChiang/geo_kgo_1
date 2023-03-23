package gov.kcg.kgo.viewModel.frontend.caseform.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 案件表單 MyData 資料")
public class MyDataHomeActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "交易識別值")
	private String txId;

	@ApiModelProperty(notes = "交易識別值(解密)")
	private String myDataTxId;

	@ApiModelProperty(notes = "申請人ID")
	private String PId;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getMyDataTxId() {
		return myDataTxId;
	}

	public void setMyDataTxId(String myDataTxId) {
		this.myDataTxId = myDataTxId;
	}

	public String getPId() {
		return PId;
	}

	public void setPId(String pId) {
		PId = pId;
	}

}
