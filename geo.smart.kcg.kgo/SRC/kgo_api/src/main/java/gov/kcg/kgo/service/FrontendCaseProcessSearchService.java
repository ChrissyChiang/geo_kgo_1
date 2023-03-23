package gov.kcg.kgo.service;

import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.*;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.*;

public interface FrontendCaseProcessSearchService {
    CaseProcessSearchHomeRs homeAction();

    CaseProcessSearchValidateRs validateAction(CaseProcessSearchValidateRq rq);

    CaseProcessSearchQueryRs queryAction(CaseProcessSearchQueryRq rq);

    CaseProcessSearchDetailSaveRs detailSaveAction(CaseProcessSearchDetailSaveRq rq) throws Exception;

    CaseProcessSearchDetailRs detailAction(CaseProcessSearchDetailRq rq);

	/**
	 * 發送推播.
	 *
	 * @param caseId the case id
	 * @param kgoCaseMain the kgo case main
	 */
	void doNotify(String caseId, KgoCaseMain kgoCaseMain);

    CaseProcessSearchDetailCheckRs detailCheckAction(CaseProcessSearchDetailCheckRq rq);
}
