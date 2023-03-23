package gov.kcg.kgo.service;

import gov.kcg.kgo.activiti.entity.TpiFlowDto;

/**
 * <p>
 *  服務介面
 * </p>
 *
 */
public interface TpiFlowService {

	String addFlowDeployment(TpiFlowDto tpiFlowDto);

    byte[] getProcessImgBytes(String flowId);

	boolean isTaskCanReject(String taskId);
}
