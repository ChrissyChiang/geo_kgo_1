package gov.kcg.kgo.viewModel.pushApi.push.push0001.rs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.viewModel.pushApi.push.push0001.rs.bean.PushRsHeader;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Push0001Rs {

	PushRsHeader header;

	Object body;

	public PushRsHeader getHeader() {
		return header;
	}

	public void setHeader(PushRsHeader header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
