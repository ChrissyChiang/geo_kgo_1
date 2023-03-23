package gov.kcg.kgo.service;

import gov.kcg.kgo.dto.PushMsgDto;

import java.util.List;

public interface PushService {

    /**
     * PUSH0001: pushMessage 對使用者推播訊息(單則對單人、多則對多人)
     *
     * @return
     */
    void pushMessage(List<PushMsgDto> pushDataList, String organCode);

    void pushMessage(String custNum, String status, String caseId, String caseSetName, String applyDate);

    void pushAsyncMessage(List<PushMsgDto> pushDataList, String organCode);

}
