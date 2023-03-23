package gov.kcg.kgo.activiti.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * TpiFlowTask.
 * </p>
 *
 * @author
 * @since 2020-10-30
 */
public class TpiFlowTaskDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "task_seq", type = IdType.AUTO)
    private Integer taskSeq;

    private Integer taskOrder;

    private String flowId;

    private String taskName;

    private Integer taskType;

    private String taskAssignees;
    
    private String taskGroups;

//    @JsonFormat(pattern = DateUtils.DATE_MILL_TIME_PATTERN)
//    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    private Timestamp createTime;

//    @JsonFormat(pattern = DateUtils.DATE_MILL_TIME_PATTERN)
//    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    private Timestamp updateTime;

	public Integer getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(Integer taskSeq) {
		this.taskSeq = taskSeq;
	}

	public Integer getTaskOrder() {
		return taskOrder;
	}

	public void setTaskOrder(Integer taskOrder) {
		this.taskOrder = taskOrder;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskAssignees() {
		return taskAssignees;
	}

	public void setTaskAssignees(String taskAssignees) {
		this.taskAssignees = taskAssignees;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getTaskGroups() {
		return taskGroups;
	}

	public void setTaskGroups(String taskGroups) {
		this.taskGroups = taskGroups;
	}

    @Override
    public String toString() {
        return "TpiFlowTask{" +
        "taskSeq=" + taskSeq +
        ", taskOrder=" + taskOrder +
        ", flowId=" + flowId +
        ", taskName=" + taskName +
        ", taskType=" + taskType +
        ", taskAssignees=" + taskAssignees +
        ", taskGroups=" + taskGroups +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
