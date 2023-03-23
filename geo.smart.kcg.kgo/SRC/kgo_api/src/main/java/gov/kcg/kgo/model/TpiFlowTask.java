package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TPI_FLOW_TASK database table.
 * 
 */
@Entity
@Table(name="TPI_FLOW_TASK")
@NamedQuery(name="TpiFlowTask.findAll", query="SELECT t FROM TpiFlowTask t")
public class TpiFlowTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TaskSeq", unique=true, nullable=false)
	private Integer taskSeq;

	@Column(name="CreateTime", nullable=false)
	private Timestamp createTime;

	@Column(name="FlowId", nullable=false, length=50)
	private String flowId;

	@Column(name="TasAssignees", length=200)
	private String tasAssignees;

	@Column(name="TaskGroups", length=200)
	private String taskGroups;

	@Column(name="TaskName", nullable=false, length=200)
	private String taskName;

	@Column(name="TaskOrder", nullable=false)
	private Integer taskOrder;
	
	@Column(name="NodeId")
	private String nodeId;
	
	@Column(name="TaskType", nullable=false)
	private String taskType;

	@Column(name="UpdateTime", nullable=false)
	private Timestamp updateTime;

	public TpiFlowTask() {
	}

	public Integer getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(Integer taskSeq) {
		this.taskSeq = taskSeq;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getTasAssignees() {
		return tasAssignees;
	}

	public void setTasAssignees(String tasAssignees) {
		this.tasAssignees = tasAssignees;
	}

	public String getTaskGroups() {
		return taskGroups;
	}

	public void setTaskGroups(String taskGroups) {
		this.taskGroups = taskGroups;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getTaskOrder() {
		return taskOrder;
	}

	public void setTaskOrder(Integer taskOrder) {
		this.taskOrder = taskOrder;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}