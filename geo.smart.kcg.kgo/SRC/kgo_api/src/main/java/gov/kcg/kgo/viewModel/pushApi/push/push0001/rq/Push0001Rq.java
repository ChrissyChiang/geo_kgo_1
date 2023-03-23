package gov.kcg.kgo.viewModel.pushApi.push.push0001.rq;

import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.Push0001Body;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.PushRqHeader;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "pushMessage rq")
public class Push0001Rq extends BasePushRq<Push0001Body> {

	public Push0001Rq() {

	}

	public Push0001Rq(PushRqHeader header) {
		super();
		super.setHeader(header);
	}

	public Push0001Rq(PushRqHeader header, Push0001Body body) {
		super();
		super.setHeader(header);
		super.setBody(body);
	}
}
