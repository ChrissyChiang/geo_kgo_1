package gov.kcg.kgo.service.impl;


import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.activiti.entity.TpiFlowDto;
import gov.kcg.kgo.activiti.entity.TpiFlowTaskDto;
import gov.kcg.kgo.service.TpiFlowService;
import gov.kcg.kgo.util.BaseFlowUtils;

/**
 * <p>
 * 服務實作
 * </p>
 *
 * @author
 * @since 2020-10-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpiFlowServiceImpl implements TpiFlowService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TpiFlowServiceImpl.class);

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;


    /**
     * 執行流程deploy.
     *
     * @param tpiFlowDto the tpi flow dto
     * @throws InvalidParameterException the invalid parameter exception
     */
    @Override
    public String addFlowDeployment(TpiFlowDto tpiFlowDto) throws InvalidParameterException {
    	LOGGER.info(">>>>> addFlowDeployment start");
    	 String flowDefId =StringUtils.EMPTY;
        try {
	          // 資料檢查
	          BaseFlowUtils.verifyTpiFlow(tpiFlowDto);

	          // TpiFlowTasks需依照task_order由小到大排序
	          Collections.sort(tpiFlowDto.getFlowTasks(), new Comparator<TpiFlowTaskDto>() {
	              @Override
	              public int compare(TpiFlowTaskDto o1, TpiFlowTaskDto o2) {
	                  return o1.getTaskOrder() - o2.getTaskOrder();
	              }
	          });

          flowDefId = BaseFlowUtils.addFlowDeployment(tpiFlowDto);
          
          LOGGER.info(">>>>> flowDefId = {}" , flowDefId);

        } catch (InvalidParameterException e) {
        	LOGGER.error(e.getMessage());
            throw e;
        }
        LOGGER.info(">>>>> addFlowDeployment end");
        return flowDefId;
    }

    @Override
    public byte[] getProcessImgBytes(String flowDefId) {
        return BaseFlowUtils.getProcessImgBytes(flowDefId);
    }
    
	/**
	 * 如果前一關是會簽節點，就不能顯示【退回上一關】的按鈕.
	 *
	 * @param taskId the task id
	 * @return true, if is task can reject
	 */
    @Override
	public boolean isTaskCanReject(String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		LOGGER.info("task.getTaskDefinitionKey()=" + task.getTaskDefinitionKey());

		if(StringUtils.isNotEmpty(task.getFormKey())) {
    		if(task.getFormKey().indexOf("FirstTask") != -1) {
	    		LOGGER.info("是第一個節點");
	    		return false;
    		}
    	}
		
		if (task.getTaskDefinitionKey().startsWith("parallel_")) { // 調整過後用這個條件判斷
			 LOGGER.info("並行節點就不能退回");
			return false;
		}

		List<HistoricActivityInstance> hisActIntList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).list();

		if (hisActIntList.size() > 2) {
			HistoricActivityInstance haInst = (HistoricActivityInstance) hisActIntList.get(hisActIntList.size() - 2);
			if ("parallelGateway".equals(haInst.getActivityType())) {
				 LOGGER.info("前一個節點是parallelGateway(表示前面是會簽)");
				return false;
			}
		}

		return true;
	}
}
