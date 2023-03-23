package gov.kcg.kgo.util;


import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gov.kcg.kgo.activiti.constant.TpiFlowConst;
import gov.kcg.kgo.activiti.entity.TpiFlowDto;
import gov.kcg.kgo.activiti.entity.TpiFlowTaskDto;
import gov.kcg.kgo.enums.backend.ActTaskSubTypeEnum;

/**
 * Activiti基礎工具
 */
@Component
public class BaseFlowUtils {

    private static final Logger log = LoggerFactory.getLogger(BaseFlowUtils.class);

    private static final String DEFAULT_FONT = "新細明體";

    private static RepositoryService repositoryService;

    public BaseFlowUtils(RepositoryService inputRepositoryService) {
        repositoryService = inputRepositoryService;
    }

    /**
     * 開始節點
     *
     * @return
     */
    public static StartEvent createStartEvent(String id) {
        StartEvent startEvent = new StartEvent();
        startEvent.setId(id);
        return startEvent;
    }

    /**
     * 結束節點
     *
     * @return
     */
    public static EndEvent createEndEvent(String id) {
        EndEvent endEvent = new EndEvent();
        endEvent.setId(id);
        return endEvent;
    }

    /**
     * 連線
     *
     * @param from
     * @param to
     * @param name
     * @param conditionExpression
     * @return
     */
    public static SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);
        if (StringUtils.isNotEmpty(conditionExpression)) {
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    /**
     * 會簽入口/匯聚
     *
     * @param id
     * @param name
     * @return
     */
    public static ParallelGateway createParallelGateway(String id, String name) {
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(id);
        gateway.setName(name);
        return gateway;
    }

    /**
     * 決策(XOR)入口
     *
     * @param id
     * @param name
     * @return
     */
    public static ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }

    /**
     * 任務節點-使用者
     *
     * @param id
     * @param name
     * @param userPkno
     * @return
     */
    public static UserTask createUserTask(String id, String name, String userPkno) {
        List<String> candidateUsers = new ArrayList<String>();
        candidateUsers.add(userPkno);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateUsers(candidateUsers);
        return userTask;
    }
    
    /**
     * 任務節點-使用者
     *
     * @param id
     * @param name
     * @param userPkno
     * @return
     */
    public static UserTask createFirstUserTask(String id, String name, String userPkno) {
    	List<String> candidateUsers = new ArrayList<String>();
    	candidateUsers.add(userPkno);
    	UserTask userTask = new UserTask();
    	userTask.setFormKey("FirstTask");
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setCandidateUsers(candidateUsers);
    	return userTask;
    }
    
    /**
     * 任務節點-使用者
     *
     * @param id
     * @param name
     * @param userPkno
     * @return
     */
    public static UserTask createFirstAndLastUserTask(String id, String name, String userPkno) {
    	List<String> candidateUsers = new ArrayList<String>();
    	candidateUsers.add(userPkno);
    	UserTask userTask = new UserTask();
    	userTask.setFormKey("FirstTask_LastTask");
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setCandidateUsers(candidateUsers);
    	return userTask;
    }
    
    /**
     * 任務節點-使用者
     *
     * @param id
     * @param name
     * @param userPkno
     * @return
     */
    public static UserTask createLastUserTask(String id, String name, String userPkno) {
    	List<String> candidateUsers = new ArrayList<String>();
    	candidateUsers.add(userPkno);
    	UserTask userTask = new UserTask();
    	userTask.setFormKey("LastTask");
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setCandidateUsers(candidateUsers);
    	return userTask;
    }

    /**
     * 任務節點-角色群組
     *
     * @param id
     * @param name
     * @param candidateGroup
     * @return
     */
    public static UserTask createGroupTask(String id, String name, String candidateGroup) {
        List<String> candidateGroups = new ArrayList<String>();
        candidateGroups.add(candidateGroup);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateGroups(candidateGroups);
        return userTask;
    }
    
    /**
     * 任務節點-角色群組
     *
     * @param id
     * @param name
     * @param candidateGroup
     * @return
     */
    public static UserTask createFirstGroupTask(String id, String name, String candidateGroup) {
    	List<String> candidateGroups = new ArrayList<String>();
    	candidateGroups.add(candidateGroup);
    	UserTask userTask = new UserTask();
    	userTask.setFormKey("FirstTask");
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setCandidateGroups(candidateGroups);
    	return userTask;
    }
    
    /**
     * 任務節點-角色群組
     *
     * @param id
     * @param name
     * @param candidateGroup
     * @return
     */
    public static UserTask createFirstAndLastGroupTask(String id, String name, String candidateGroup) {
    	List<String> candidateGroups = new ArrayList<String>();
    	candidateGroups.add(candidateGroup);
    	UserTask userTask = new UserTask();
    	userTask.setFormKey("FirstTask_LastTask");
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setCandidateGroups(candidateGroups);
    	return userTask;
    }
    
    /**
     * 任務節點-角色群組
     *
     * @param id
     * @param name
     * @param candidateGroup
     * @return
     */
    public static UserTask createLastGroupTask(String id, String name, String candidateGroup) {
    	List<String> candidateGroups = new ArrayList<String>();
    	candidateGroups.add(candidateGroup);
    	UserTask userTask = new UserTask();
    	userTask.setFormKey("LastTask");
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setCandidateGroups(candidateGroups);
    	return userTask;
    }

    /**
     * 任務節點-綁定使用者
     *
     * @param id
     * @param name
     * @param assignee
     * @return
     */
    public static UserTask createAssigneeTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    public static String addFlowDeployment(TpiFlowDto tpiFlow) {

        log.info("addFlowDeployment start");

        // 1. 建立模型
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        createModel(model, process, tpiFlow);

        // 2. 生成的圖形信息
        new BpmnAutoLayout(model).execute();

        // 3. 部署流程
        Deployment deployment = repositoryService.createDeployment().addBpmnModel(process.getId() + TpiFlowConst.FLOW_FILE_NAME_SUFFIX, model).name(process.getId() + TpiFlowConst.FLOW_DEPLOY_NAME_SUFFIX).deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).latestVersion().singleResult();
        String processDefinitionId = processDefinition.getId();
        log.debug("processDefinitionId:{}", processDefinitionId);

        log.debug("addFlowDeployment end");

        return processDefinitionId;
    }

    /**
     * 建立模型
     *
     * @param model
     * @param process
     * @param tpiFlow
     */
    private static void createModel(BpmnModel model, Process process, TpiFlowDto tpiFlow) {

        model.addProcess(process);
        process.setId(tpiFlow.getFlowId());
        process.setName(tpiFlow.getFlowName());
        process.setDocumentation(tpiFlow.getFlowDesc());

        // 增加流程元素
        addFlowElement(model, process, tpiFlow);
    }

    /**
     * 增加流程元素
     *
     * @param model
     * @param process
     * @param tpiFlow
     */
    private static void addFlowElement(BpmnModel model, Process process, TpiFlowDto tpiFlow) {

        int taskDigit = String.valueOf(tpiFlow.getFlowTasks().size()).length();

        // 增加開始節點
        process.addFlowElement(createStartEvent(TpiFlowConst.PROCESS_START_EVENT_ID));

        // 增加任務(或 入口/匯聚)節點
        addTaskOrParallelGateway(process, tpiFlow, taskDigit);

        // 增加結束節點
        process.addFlowElement(createEndEvent(TpiFlowConst.PROCESS_END_EVENT_ID));

        // 增加連線
        createSequenceFlow(process, tpiFlow, taskDigit);
    }

    /**
     * 增加任務(或 入口/匯聚)節點
     *
     * @param process
     * @param tpiFlow
     * @param taskDigit
     */
    private static void addTaskOrParallelGateway(Process process, TpiFlowDto tpiFlow, int taskDigit) {

    	int lastTasks = tpiFlow.getFlowTasks().size() - 1;
        for (int i = 0; i < tpiFlow.getFlowTasks().size(); i++) {

        	TpiFlowTaskDto task = tpiFlow.getFlowTasks().get(i);
            String taskIdSuffix = StringUtils.leftPad(String.valueOf(i), taskDigit, TpiFlowConst.PAD_CHAR);
            log.debug("{}...task={}", taskIdSuffix, task.toString());

            // 判斷是否會簽
            if (isParallelTaskType(task.getTaskType().intValue())) {

                // 加入會簽入口
                process.addFlowElement(createParallelGateway(TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_NAME_PREFIX + taskIdSuffix));

                // 增加會簽-分支的使用者審核節點
                if (StringUtils.isNotBlank(task.getTaskAssignees())) {
                    // 獲取所有使用者
                    String[] assignees = StringUtils.split(task.getTaskAssignees(), TpiFlowConst.ASSIGN_SEPARATOR_CHAR);
                    int userDigit = String.valueOf(assignees.length).length();
                    for (int u = 0; u < assignees.length; u++) {
                        String userSeq = StringUtils.leftPad(String.valueOf(u), userDigit, TpiFlowConst.PAD_CHAR);
                        process.addFlowElement(createUserTask(TpiFlowConst.PARALLEL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix + userSeq, task.getTaskName() + taskIdSuffix + userSeq, assignees[u]));
                    }
                }

                // 增加會簽-分支的角色群組審核節點
                if (StringUtils.isNotBlank(task.getTaskGroups())) {
                    // 獲取所有角色群組
                    String[] groups = StringUtils.split(task.getTaskGroups(), TpiFlowConst.ASSIGN_SEPARATOR_CHAR);
                    int groupDigit = String.valueOf(groups.length).length();
                    for (int u = 0; u < groups.length; u++) {
                        String groupSeq = StringUtils.leftPad(String.valueOf(u), groupDigit, TpiFlowConst.PAD_CHAR);
                        process.addFlowElement(createGroupTask(TpiFlowConst.PARALLEL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix + groupSeq, task.getTaskName() + taskIdSuffix + groupSeq, groups[u]));
                    }
                }

                // 加入會簽匯聚
                process.addFlowElement(createParallelGateway(TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_NAME_PREFIX + taskIdSuffix));

            } else {
                // 普通節點

                // 增加使用者審核節點
                if (StringUtils.isNotBlank(task.getTaskAssignees())) {
                	if(i==0 && i == lastTasks) {
                		process.addFlowElement(createFirstAndLastUserTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskAssignees()));
                	}else if(i==0) {
                		process.addFlowElement(createFirstUserTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskAssignees()));
                	}else if(i == lastTasks){
                		process.addFlowElement(createLastUserTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskAssignees()));
                	}else {
                		process.addFlowElement(createUserTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskAssignees()));
                	}
                    
                }

                // 增加角色群組審核節點
                else if (StringUtils.isNotBlank(task.getTaskGroups())) {
                	if(i==0 && i == lastTasks) {
                		process.addFlowElement(createFirstAndLastGroupTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskGroups()));
                	}if(i == 0) {
                		process.addFlowElement(createFirstGroupTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskGroups()));
                	}else if(i == lastTasks){
                		process.addFlowElement(createLastGroupTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskGroups()));
                	}else {
                		process.addFlowElement(createGroupTask(TpiFlowConst.NORMAL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix, task.getTaskName() + taskIdSuffix, task.getTaskGroups()));
                	}
                    
                }

                // 回退節點
                // process.addFlowElement(createUserTask("repulse"+i,"回退節點"+i,"${startUserId}"));
            }
        }
    }

    /**
     * 增加連線
     *
     * @param process
     * @param tpiFlow
     * @param taskDigit
     */
    private static void createSequenceFlow(Process process, TpiFlowDto tpiFlow, int taskDigit) {

        for (int y = 0; y < tpiFlow.getFlowTasks().size(); y++) {

        	TpiFlowTaskDto task = tpiFlow.getFlowTasks().get(y);
            String taskIdSuffix = StringUtils.leftPad(String.valueOf(y), taskDigit, TpiFlowConst.PAD_CHAR);

            // 判斷是否會簽
            if (isParallelTaskType(task.getTaskType().intValue())) {

                // 判斷是否第一個節點
                if (y == 0) {
                    // 開始節點和會簽入口連線
                    process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_START_EVENT_ID, TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_START_TO_PARALLELGATEWAY_FORK_NAME_PREFIX + taskIdSuffix, ""));
                } else {

                    String previouTaskIdSuffix = StringUtils.leftPad(String.valueOf(y - 1), taskDigit, TpiFlowConst.PAD_CHAR);

                    // 審核節點或者會簽匯聚到會簽入口
                    // 判斷上一個節點是否是會簽
                    if (isParallelTaskType(tpiFlow.getFlowTasks().get(y - 1).getTaskType().intValue())) {
                        process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX + previouTaskIdSuffix, TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_TO_PARALLELGATEWAY_FORK_NAME_PREFIX + taskIdSuffix, ""));
                    } else {
                        String previousTaskIdPrefix = TpiFlowConst.NORMAL_TASK + (StringUtils.isNotBlank(tpiFlow.getFlowTasks().get(y - 1).getTaskAssignees()) ? TpiFlowConst.TASK_USER_ID_PREFIX : TpiFlowConst.TASK_GROUP_ID_PREFIX);
                        process.addFlowElement(createSequenceFlow(previousTaskIdPrefix + previouTaskIdSuffix, TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_TASK_TO_PARALLELGATEWAY_FORK_NAME_PREFIX + taskIdSuffix, "${approve=='true'}"));
                    }
                }

                // 會簽入口和會簽使用者審核節點連線，會簽使用者審核節點和會簽匯聚連線
                if (StringUtils.isNotBlank(task.getTaskAssignees())) {
                    // 獲取所有使用者
                    String[] assignees = StringUtils.split(task.getTaskAssignees(), TpiFlowConst.ASSIGN_SEPARATOR_CHAR);
                    int userDigit = String.valueOf(assignees.length).length();
                    for (int u = 0; u < assignees.length; u++) {
                        String userSeq = StringUtils.leftPad(String.valueOf(u), userDigit, TpiFlowConst.PAD_CHAR);
                        process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX + taskIdSuffix, TpiFlowConst.PARALLEL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix + userSeq, TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_TO_USER_NAME_PREFIX + taskIdSuffix + userSeq, ""));
                        process.addFlowElement(createSequenceFlow(TpiFlowConst.PARALLEL_TASK + TpiFlowConst.TASK_USER_ID_PREFIX + taskIdSuffix + userSeq, TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_USER_TO_PARALLELGATEWAY_JOIN_NAME_PREFIX, ""));
                    }
                }

                // 會簽入口和會簽角色群組審核節點連線，會簽角色群組審核節點和會簽匯聚連線
                if (StringUtils.isNotBlank(task.getTaskGroups())) {
                    // 獲取所有角色群組
                    String[] groups = StringUtils.split(task.getTaskGroups(), TpiFlowConst.ASSIGN_SEPARATOR_CHAR);
                    int groupDigit = String.valueOf(groups.length).length();
                    for (int u = 0; u < groups.length; u++) {
                        String groupSeq = StringUtils.leftPad(String.valueOf(u), groupDigit, TpiFlowConst.PAD_CHAR);
                        process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX + taskIdSuffix, TpiFlowConst.PARALLEL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix + groupSeq, TpiFlowConst.PROCESS_PARALLELGATEWAY_FORK_TO_GROUP_NAME_PREFIX + taskIdSuffix + groupSeq, ""));
                        process.addFlowElement(createSequenceFlow(TpiFlowConst.PARALLEL_TASK + TpiFlowConst.TASK_GROUP_ID_PREFIX + taskIdSuffix + groupSeq, TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_GROUP_TO_PARALLELGATEWAY_JOIN_NAME_PREFIX, ""));
                    }
                }

                // 最後壹個節點  並行網關-匯聚到結束節點
                if (y == (tpiFlow.getFlowTasks().size() - 1)) {
                    process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX + taskIdSuffix, TpiFlowConst.PROCESS_END_EVENT_ID, TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_TO_END_NAME_PREFIX, ""));
                }
            } else {

                String taskIdPrefix = TpiFlowConst.NORMAL_TASK + (StringUtils.isNotBlank(task.getTaskAssignees()) ? TpiFlowConst.TASK_USER_ID_PREFIX : TpiFlowConst.TASK_GROUP_ID_PREFIX);

                // 第一個節點
                if (y == 0) {
                    // 開始節點和審核節點1
                    process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_START_EVENT_ID, taskIdPrefix + taskIdSuffix, TpiFlowConst.PROCESS_START_TO_TASK_NAME_PREFIX + taskIdSuffix, ""));
                } else {

                    String previousTaskIdSuffix = StringUtils.leftPad(String.valueOf(y - 1), taskDigit, TpiFlowConst.PAD_CHAR);

                    // 判斷上一個節點是否會簽
                    if (isParallelTaskType(tpiFlow.getFlowTasks().get(y - 1).getTaskType())) {
                        // 並行網關-匯聚到審核節點
                        process.addFlowElement(createSequenceFlow(TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX + previousTaskIdSuffix, taskIdPrefix + taskIdSuffix, TpiFlowConst.PROCESS_PARALLELGATEWAY_JOIN_TO_TASK_NAME_PREFIX + taskIdSuffix, ""));
                    } else {
                        String previousTaskIdPrefix = TpiFlowConst.NORMAL_TASK + (StringUtils.isNotBlank(tpiFlow.getFlowTasks().get(y - 1).getTaskAssignees()) ? TpiFlowConst.TASK_USER_ID_PREFIX : TpiFlowConst.TASK_GROUP_ID_PREFIX);
                        // 普通
                        process.addFlowElement(createSequenceFlow(previousTaskIdPrefix + previousTaskIdSuffix, taskIdPrefix + taskIdSuffix, TpiFlowConst.PROCESS_TASK_NAME_PREFIX + previousTaskIdSuffix + TpiFlowConst.PROCESS_TASK_NAME_MIDDLE + taskIdSuffix, "${approve=='true'}"));
                        process.addFlowElement(createSequenceFlow(taskIdPrefix + taskIdSuffix, previousTaskIdPrefix + previousTaskIdSuffix, TpiFlowConst.PROCESS_RETURN_TASK_NAME_PREFIX + taskIdSuffix, "${approve=='false'}"));
                    }
                }

                // 是否最後壹個節點
                if (y == (tpiFlow.getFlowTasks().size() - 1)) {
                    // 審核節點到結束節點
                    process.addFlowElement(createSequenceFlow(taskIdPrefix + taskIdSuffix, TpiFlowConst.PROCESS_END_EVENT_ID, TpiFlowConst.PROCESS_TASK_NAME_PREFIX + taskIdSuffix + TpiFlowConst.PROCESS_TO_END_NAME_SUFFIX, "${approve=='true'}"));
                }

                // 審核節點到回退節點
                // process.addFlowElement(createSequenceFlow("repulse" + taskIdSuffix, "task" + taskIdSuffix, "回退節點到審核節點" + taskIdSuffix, ""));
            }
        }
    }

    /**
     * 取得流程圖Bytes
     *
     * @param flowDefId
     * @return
     */
    public static byte[] getProcessImgBytes(String flowDefId) {

        InputStream imgStream = null;

        try {
            imgStream = getProcessImgStream(flowDefId);
            return IOUtils.toByteArray(imgStream);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            IOUtils.closeQuietly(imgStream);
        }
    }

    /**
     * 取得流程圖InputStream<br/>
     * ps.需注意關閉Stream
     *
     * @param flowDefId
     */
    public static InputStream getProcessImgStream(String flowDefId) {

        log.debug("getProcessImgStream start");

        log.debug("flowDefId:{}", flowDefId);

        // 獲取流程圖
        BpmnModel bpmnModel = repositoryService.getBpmnModel(flowDefId);

        // 這個類在5.22.0往上的版本中才有
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();

        // 繪制bpmnModel代表的流程的流程圖
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, new ArrayList<String>(), new ArrayList<String>(), DEFAULT_FONT, DEFAULT_FONT, DEFAULT_FONT);

        log.debug("getProcessImgStream complete");

        return inputStream;
    }

    /**
     * 是否已啟用流程
     *
     * @param flowEnable
     * @return true: 已啟用, false: 其他
     */
    public static boolean isEnableFlow(String flowEnable) {
        return StringUtils.equals(TpiFlowConst.FLOW_ENABLE, flowEnable);
    }

    /**
     * 是否未啟用流程
     *
     * @param flowDisable
     * @return true: 未啟用, false: 其他
     */
    public static boolean isDisableFlow(String flowDisable) {
        return StringUtils.equals(TpiFlowConst.FLOW_DISABLE, flowDisable);
    }

    /**
     * 是否為會簽任務
     *
     * @param taskType
     * @return true: 會簽, false: 其他
     */
    public static boolean isParallelTaskType(int taskType) {
        return TpiFlowConst.TASK_TYPE_Parallel == taskType;
    }

    /**
     * 是否為普通任務
     *
     * @param taskType
     * @return true: 普通, false: 其他
     */
    public static boolean isCommonTaskType(int taskType) {
        return TpiFlowConst.TASK_TYPE_COMMON == taskType;
    }

    /**
     * 資料檢查<br/>
     * 1.必填：flowId流程編號<br/>
     * 2.必填：flowName流程中文名稱<br/>
     * 3.必填：flowTasks流程定義步驟(明細)<br/>
     * 4.條件：flowEnable流程啟用狀態 = Y or N<br/>
     * 5.必填：taskOrder節點順序<br/>
     * 6.必填：taskName節點名稱<br/>
     * 7.必填：taskType節點類型<br/>
     * 8.必填：taskAssignees節點指派使用者 或 taskGroups節點指派角色群組<br/>
     * 9.條件：taskType節點類型 = 1 or 2<br/>
     * 10.條件：taskType=1 => (taskAssignees.size + taskGroups.size) > 1<br/>
     * 11.條件：taskType=2 => (taskAssignees.size + taskGroups.size) = 1
     *
     * @param tpiFlow
     * @throws InvalidParameterException
     */
    public static void verifyTpiFlow(TpiFlowDto tpiFlow) throws InvalidParameterException {

        StringBuilder nullParams = new StringBuilder();
        StringBuilder failParams = new StringBuilder();

        // 必填：flowId流程編號
        if (StringUtils.isBlank(tpiFlow.getFlowId())) {
            nullParams.append("[flowId]");
        }

        // 必填：flowName流程中文名稱
        if (StringUtils.isBlank(tpiFlow.getFlowName())) {
            nullParams.append("[flowName]");
        }

        // 必填：flowTasks流程定義步驟(明細)
        if (CollectionUtils.isEmpty(tpiFlow.getFlowTasks())) {
            nullParams.append("[flowTasks]");
        }

        // 條件：flowEnable流程啟用狀態 = Y or N
        if (!(isEnableFlow(tpiFlow.getFlowEnable()) || isDisableFlow(tpiFlow.getFlowEnable()))) {
            failParams.append("[flowEnable=").append(tpiFlow.getFlowEnable()).append("]");
        }

        if (CollectionUtils.isNotEmpty(tpiFlow.getFlowTasks())) {

            for (TpiFlowTaskDto task : tpiFlow.getFlowTasks()) {

                String order = null != task.getTaskOrder() ? String.valueOf(task.getTaskOrder()) : "?";

                // 必填：taskOrder節點順序
                if (null == task.getTaskOrder()) {
                    nullParams.append("[flowTasks(").append(order).append(").taskOrder]");
                }

                // 必填：taskName節點名稱
                if (StringUtils.isBlank(task.getTaskName())) {
                    nullParams.append("[flowTasks(").append(order).append(").taskName]");
                }

                // 必填：taskType節點類型
                if (null == task.getTaskType()) {
                    nullParams.append("[flowTasks(").append(order).append(").taskType]");
                }

                // 必填：taskAssignees節點指派使用者 或 taskGroups節點指派角色群組
                if (StringUtils.isBlank(task.getTaskAssignees()) && StringUtils.isBlank(task.getTaskGroups())) {
                    nullParams.append("[flowTasks(").append(order).append(").taskAssignees & taskGroups]");
                }

                // 條件：taskType節點類型 = 1 or 2
                if (!(isParallelTaskType(task.getTaskType()) || isCommonTaskType(task.getTaskType()))) {
                    failParams.append("[flowTasks(").append(order).append(").taskType=").append(task.getTaskType()).append("]");
                }

                String[] assignees = StringUtils.split(task.getTaskAssignees(), TpiFlowConst.ASSIGN_SEPARATOR_CHAR);
                String[] groups = StringUtils.split(task.getTaskGroups(), TpiFlowConst.ASSIGN_SEPARATOR_CHAR);
                int assigneesAndGroupsTotal = (ArrayUtils.isNotEmpty(assignees) ? assignees.length : 0) + (ArrayUtils.isNotEmpty(groups) ? groups.length : 0);

                // 條件：taskType=1 => (taskAssignees.size + taskGroups.size) > 1
                if (isParallelTaskType(task.getTaskType())) {
                    if (assigneesAndGroupsTotal <= 1) {
                        failParams.append("[flowTasks(").append(order).append(").taskType=").append(task.getTaskType()).append(" => assignees and groups total=").append(assigneesAndGroupsTotal).append("]");
                    }
                }

                // 條件：taskType=2 => (taskAssignees.size + taskGroups.size) = 1
                if (isCommonTaskType(task.getTaskType())) {
                    if (assigneesAndGroupsTotal != 1) {
                        failParams.append("[flowTasks(").append(order).append(").taskType=").append(task.getTaskType()).append(" => assignees and groups total=").append(assigneesAndGroupsTotal).append("]");
                    }
                }
            }
        }

        // 參數檢查未通過，拋出參數錯誤
        if (nullParams.length() > 0 || failParams.length() > 0) {

            StringBuilder errorMsg = new StringBuilder();

            if (nullParams.length() > 0) {
                errorMsg.append("Null Parameter:").append(nullParams.toString());
            }

            if (failParams.length() > 0) {

                if (errorMsg.length() > 0) {
                    errorMsg.append(", ");
                }

                errorMsg.append("Fail Parameter:").append(failParams);
            }

            throw new InvalidParameterException(errorMsg.toString());
        }
    }
    
    /**
     * 取得原taskSubType
     * 會簽任務節點: 1(會簽)
     * 一般任務節點:子類別 D(分文)、A(陳核)、F(結案) enum.
     * 
     *  format: taskSubTypeName_taskName
     * 2021.01.07 modify
     *
     * @param taskName the task name
     * @return the task sub type by name
     */
    public static String getTaskSubTypeByName(String taskName) {
    	String taskSubType = StringUtils.EMPTY;
    	if(taskName.contains("_")) {
    		taskSubType = StringUtils.split(taskName, "_")[0];
    	}
    	return taskSubType;
    }
    
    /**
     * 取得原taskSubTypeName
     * 會簽任務節點: 1(會簽)
     * 一般任務節點:子類別 D(分文)、A(陳核)、F(結案) enum.
     * 
     *  format: taskSubTypeName_taskName
     * 2021.01.07 modify
     *
     * @param taskName the task name
     * @return the task sub type by name
     */
    public static String getTaskSubTypeNameByName(String taskName) {
    	String taskSubTypeName = StringUtils.EMPTY;
    	if(taskName.contains("_")) {
    		
    		ActTaskSubTypeEnum taskSubTypeE = ActTaskSubTypeEnum.getActTaskSubType(getTaskSubTypeByName(taskName));
    		taskSubTypeName = taskSubTypeE.getSubTypeName();
    	} else {
    		// 避免髒資料-> 預設陳核
    		taskSubTypeName = "陳核.";
    	}
    	
    	return taskSubTypeName;
    }
}

