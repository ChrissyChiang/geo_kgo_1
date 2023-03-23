package gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.callcity.rs.bean;

public class CallCityRsData {

	private static final long serialVersionUID = 1L;
	
	private String rtnCode;
	
	private String msg;

    private CallCityGetCaseIdRsData data;
    
    private String id;


	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public CallCityGetCaseIdRsData getData() {
		return data;
	}

	public void setData(CallCityGetCaseIdRsData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
