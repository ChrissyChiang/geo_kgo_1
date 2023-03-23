package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rq.ServiceAnnounceHomeRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;

public interface ServiceAnnounceService {

	/**
	 * 服務宣告-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	public ServiceAnnounceHomeRs serviceAnnounceHome(ServiceAnnounceHomeRq rq);

}
