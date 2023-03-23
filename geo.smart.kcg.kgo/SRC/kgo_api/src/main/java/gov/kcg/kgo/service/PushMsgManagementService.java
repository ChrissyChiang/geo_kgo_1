package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgManagementEditRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementEditRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementQueryRs;

public interface PushMsgManagementService {

    PushMsgManagementHomeRs getUserIdAndOrganId();

    PushMsgManagementQueryRs queryPushMsgData(PushMsgManagementQueryRq pushMsgManagementQueryRq);

    PushMsgManagementEditRs editPushMsgData(PushMsgManagementEditRq pushMsgManagementEditRq);

    PushMsgManagementDeleteRs deletePushMsgData(PushMsgManagementDeleteRq pushMsgManagementDeleteRq);

}