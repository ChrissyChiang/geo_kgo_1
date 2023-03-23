package gov.kcg.kgo.viewModel.pushApi.push.push0001.rq;

import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.PushRqHeader;

public class BasePushRq<T> {

	private PushRqHeader header;

	private T body;

	public PushRqHeader getHeader() {
		return header;
	}

	public void setHeader(PushRqHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
