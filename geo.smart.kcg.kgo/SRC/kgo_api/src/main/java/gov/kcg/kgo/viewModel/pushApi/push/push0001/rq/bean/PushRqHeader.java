package gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PUCH API Header
 * 
 * @author joelee
 *
 */
public class PushRqHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("txSn")
	private String txSn;

	@JsonProperty("txDate")
	private String txDate;

	@JsonProperty("txID")
	private String txID;

	@JsonProperty("dept")
	private String dept;

	public String getTxSn() {
		return txSn;
	}

	public void setTxSn(String txSn) {
		this.txSn = txSn;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getTxID() {
		return txID;
	}

	public void setTxID(String txID) {
		this.txID = txID;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}
