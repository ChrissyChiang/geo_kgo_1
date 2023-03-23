package gov.kcg.kgo.service.impl.transaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessTransactionImpl implements ProcessTransaction {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessTransactionImpl.class);

    @Override
    public String showProcessImg(String processInstanceId) {
        HistoricProcessInstance processInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        BpmnModel bpmnModel;
        ProcessInstance processInstanceQuery = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (null == processInstance) {
            if (null != processInstanceQuery) {
                bpmnModel = repositoryService.getBpmnModel(processInstanceQuery.getProcessDefinitionId());
            } else {
                return "";
            }
        } else {
            // 获取流程图
            bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        }
        //取得當前的節點
        List<String> activeActivityIds = new ArrayList();
        if(isEnd(processInstanceId)) {
            //取得最後一個節點
            HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
            List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery.processInstanceId(processInstanceId).list();
            for(HistoricActivityInstance hi:historicActivityInstanceList) {


                String taskKey=hi.getActivityId();
                if("endEvent".equals(hi.getActivityType())) {
//                    System.out.println("hi.getActivityType()===" + hi.getActivityType());
                    activeActivityIds.add(taskKey);
                }
            }
        }else {
            //取得當前的節點
            activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        }
        List<HistoricActivityInstance> hisActIntList = historyService.createHistoricActivityInstanceQuery()
                                                        .processInstanceId(processInstanceId)
                                                        .list();
        // Flows 紀錄
        List<String> highLightedFlows = getExecutedFlows(bpmnModel, hisActIntList);
        //这个类在5.22.0往上的版本中才有
        DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();
        //绘制bpmnModel代表的流程的流程图
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, activeActivityIds, highLightedFlows, "新細明體", "新細明體", "新細明體");
        String encoded = null;
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            LOGGER.error("showProcessImg error ", e);
        }
        return encoded;
    }


    private List<String> getExecutedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstanceList){
        List<String> executedFlowIdList = new ArrayList<>();
        for (int i = 0; i < historicActivityInstanceList.size() - 1; i++) {
            HistoricActivityInstance his = historicActivityInstanceList.get(i);
            FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(his.getActivityId());
            List<SequenceFlow> sequenceFlowList = flowNode.getOutgoingFlows();
            if (sequenceFlowList.size() > 1){
                HistoricActivityInstance nextHis = historicActivityInstanceList.get(i + 1);
                sequenceFlowList.forEach(sequenceFlow -> {
                    if (sequenceFlow.getTargetFlowElement().getId().equals(nextHis.getActivityId())){
                        executedFlowIdList.add(sequenceFlow.getId());
                    }
                });
            } else if (sequenceFlowList.size() == 1){
                executedFlowIdList.add(sequenceFlowList.get(0).getId());
            }
        }
        return executedFlowIdList;
    }


    @Override
    public boolean isEnd(String processId){
        ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if(process==null){
            LOGGER.info("執行完畢");
            return true;
        }else{
            LOGGER.info("正在執行");
            return false;
        }
    }
    
    @Override
    public boolean isLastTask(String taskId){
    	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    	if(StringUtils.isNotEmpty(task.getFormKey())) {
    		if(task.getFormKey().indexOf("LastTask") != -1) {
        		LOGGER.info("taskId : {} 已經是最後一個節點", taskId);
        		return true;
        	}else {
        		LOGGER.info("taskId : {} 還在審核中", taskId);
        		return false;
        	}
    	}
    	return false;
    	
    }
}
