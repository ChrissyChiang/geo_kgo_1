package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.frontend.bidInstruction.download.rq.BidInstructionDownloadRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rq.BidInstructionGetLinkRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rs.BidInstructionGetLinkRs;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rq.BidInstructionHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.BidInstructionHomeRs;

public interface BidInstructionService {

	/**
	 * 申辦服務選單-初始畫面
	 * 
	 * @return
	 */
	public BidInstructionHomeRs bidInstructionHome(BidInstructionHomeRq rq);

	/**
	 * 申辦說明頁-檔案下載
	 * 
	 * @param rq
	 * @return
	 */
	public void bidInstructionDownload(BidInstructionDownloadRq rq);

	/**
	 * 申辦說明頁-取得站外連結
	 * 
	 * @param rq
	 * @return
	 */
	BidInstructionGetLinkRs bidInstructionGetLink(BidInstructionGetLinkRq rq);

    /**
     * 申辦說明頁-檢查案件是否上架
     *
     * @param caseSetId
     * @return
     */
    boolean checkStatusIsOn(String caseSetId);
}
