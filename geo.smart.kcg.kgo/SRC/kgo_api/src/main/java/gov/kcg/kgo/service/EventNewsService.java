package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.frontend.eventNews.download.rq.DownloadActionRq;
import gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.EventNewsHomeRs;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rq.EventNewsQueryRq;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rs.EventNewsQueryRs;

public interface EventNewsService {

	/**
	 * 活動消息-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	EventNewsHomeRs eventNewsHome();

	/**
	 * 活動消息-任務消息查詢
	 * 
	 * @param rq
	 * @return
	 */
	EventNewsQueryRs eventNewsQuery(EventNewsQueryRq rq);

	void downloadAction(DownloadActionRq rq);

}
