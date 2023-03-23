package gov.kcg.kgo.activiti.entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * TpiFlow.
 * </p>
 *
 * @author
 * @since 2020-10-30
 */
public class TpiFlowDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String flowId;

    private String flowName;

    private String flowDesc;

    private String flowEnable;

    private String flowDefId;

//    @JsonFormat(pattern = DateUtils.DATE_MILL_TIME_PATTERN)
//    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    private Timestamp createTime;

//    @JsonFormat(pattern = DateUtils.DATE_MILL_TIME_PATTERN)
//    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    private Timestamp updateTime;

    private List<TpiFlowTaskDto> flowTasks;

    public String getFlowId() {
        return flowId;
    }

    public TpiFlowDto setFlowId(String flowId) {
        this.flowId = flowId;
        return this;
    }
  

    public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	public String getFlowEnable() {
		return flowEnable;
	}

	public void setFlowEnable(String flowEnable) {
		this.flowEnable = flowEnable;
	}

	public String getFlowDefId() {
		return flowDefId;
	}

	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
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

	public List<TpiFlowTaskDto> getFlowTasks() {
		return flowTasks;
	}

	public void setFlowTasks(List<TpiFlowTaskDto> flowTasks) {
		this.flowTasks = flowTasks;
	}

	@Override
    public String toString() {
        return "TpiFlow{" +
        "flowId=" + flowId +
        ", flowName=" + flowName +
        ", flowDesc=" + flowDesc +
        ", flowEnable=" + flowEnable +
        ", flowDefId=" + flowDefId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
