package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.SystemAnnounceHomeRs;

public interface SystemAnnounceService {

	/**
	 * 系統公告-初始畫面
	 * 
	 * @param rq
	 * @return SystemAnnounceHomeRs
	 */
	public SystemAnnounceHomeRs systemAnnouncetHome();

}
