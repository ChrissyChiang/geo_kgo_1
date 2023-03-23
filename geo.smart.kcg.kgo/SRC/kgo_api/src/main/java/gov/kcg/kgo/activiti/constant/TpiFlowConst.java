package gov.kcg.kgo.activiti.constant;

public class TpiFlowConst {

    public static final String PROCESS_START_EVENT_ID = "startEvent";
    public static final String PROCESS_END_EVENT_ID = "endEvent";
    public static final String PROCESS_PARALLELGATEWAY_FORK_ID_PREFIX = "parallelGateway-fork";
    public static final String PROCESS_PARALLELGATEWAY_JOIN_ID_PREFIX = "parallelGateway-join";

    public static final String PROCESS_PARALLELGATEWAY_FORK_NAME_PREFIX = "會簽入口";
    public static final String PROCESS_PARALLELGATEWAY_JOIN_NAME_PREFIX = "會簽匯聚";

    public static final String PROCESS_START_TO_PARALLELGATEWAY_FORK_NAME_PREFIX = "開始節點-會簽入口";
    public static final String PROCESS_PARALLELGATEWAY_JOIN_TO_PARALLELGATEWAY_FORK_NAME_PREFIX = "會簽匯聚-會簽入口";
    public static final String PROCESS_TASK_TO_PARALLELGATEWAY_FORK_NAME_PREFIX = "上一個審核節點-會簽入口";
    public static final String PROCESS_PARALLELGATEWAY_FORK_TO_USER_NAME_PREFIX = "會簽入口-使用者審核節點";
    public static final String PROCESS_USER_TO_PARALLELGATEWAY_JOIN_NAME_PREFIX = "使用者審核節點-會簽匯聚";
    public static final String PROCESS_PARALLELGATEWAY_FORK_TO_GROUP_NAME_PREFIX = "會簽入口-角色群組審核節點";
    public static final String PROCESS_GROUP_TO_PARALLELGATEWAY_JOIN_NAME_PREFIX = "角色群組審核節點-會簽匯聚";
    public static final String PROCESS_PARALLELGATEWAY_JOIN_TO_END_NAME_PREFIX = "會簽匯聚-結束節點";
    public static final String PROCESS_START_TO_TASK_NAME_PREFIX = "開始節點-審核節點";
    public static final String PROCESS_PARALLELGATEWAY_JOIN_TO_TASK_NAME_PREFIX = "會簽匯聚-審核節點";
    public static final String PROCESS_TASK_NAME_PREFIX = "審核節點";
    public static final String PROCESS_TASK_NAME_MIDDLE = "-審核節點";
    public static final String PROCESS_RETURN_TASK_NAME_PREFIX = "審核不通過-退回";
    public static final String PROCESS_TO_END_NAME_SUFFIX = "-結束節點";

    // public static final String TASK_ID_PREFIX = "task";
    public static final String NORMAL_TASK = "normal_";
    public static final String PARALLEL_TASK = "parallel_";
    public static final String TASK_USER_ID_PREFIX = "userTask";
    public static final String TASK_GROUP_ID_PREFIX = "groupTask";

    public static final String TASK_USER_NAME_PREFIX = "會簽分支使用者審核節點";
    public static final String TASK_GROUP_NAME_PREFIX = "會簽分支角色群組審核節點";

    public static final String FLOW_ENABLE = "Y";
    public static final String FLOW_DISABLE = "N";

    public static final int TASK_TYPE_Parallel = 1;
    public static final int TASK_TYPE_COMMON = 2;

    public static final String ASSIGN_SEPARATOR_CHAR = ",";
    public static final String PAD_CHAR = "0";
    public static final String FLOW_FILE_NAME_SUFFIX = ".bpmn";
    public static final String FLOW_DEPLOY_NAME_SUFFIX = "_deployment";

}
