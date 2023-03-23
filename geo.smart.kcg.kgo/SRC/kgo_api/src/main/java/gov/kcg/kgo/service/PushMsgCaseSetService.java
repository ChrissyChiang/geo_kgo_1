package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgCaseSetEditRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgCaseSetQueryRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgCaseSetEditRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgCaseSetQueryRs;

public interface PushMsgCaseSetService {

    PushMsgCaseSetQueryRs queryPushMsgCaseSetData(PushMsgCaseSetQueryRq pushMsgCaseSetQueryRq);

    PushMsgCaseSetEditRs editPushMsgCaseSetData(PushMsgCaseSetEditRq pushMsgCaseSetEditRq);

}