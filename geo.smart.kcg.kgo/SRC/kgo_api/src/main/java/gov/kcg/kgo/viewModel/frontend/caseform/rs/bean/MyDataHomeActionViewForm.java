package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 取得 MyData 資料")
public class MyDataHomeActionViewForm extends HomeActionViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "交易識別值")
	private String txId;

	@ApiModelProperty(notes = "下載檔案延遲秒數")
	private Integer delaySeconds;

	@ApiModelProperty(notes = "下載檔案訊息")
	private String message;

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public Integer getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(Integer delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
