package gov.kcg.kgo.viewModel.backend.actFlowManager.common.rs.bean;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 後台 - 動態流程管理 - 流程定義步驟(明細)檔 bean.
 * </p>
 *
 * @author
 * @since 2020-10-30
 */
@ApiModel(description = "後台 - 動態流程管理 -流程定義步驟(明細)檔 Bean")
public class TpiFlowTaskBean {

	@ApiModelProperty(value = "節點序號", position = 0)
    private Integer taskSeq;

	@ApiModelProperty(value = "節點順序", position = 1)
    private Integer taskOrder;

	@ApiModelProperty(value = "節點名稱", position = 2)
    private String taskName;

	@ApiModelProperty(value = "節點類型。1:會簽節點 2:普通節點(預設)-> D(分文)、A(陳核)、F(結案)", position = 3)
    private String taskType;
	
	@ApiModelProperty(value = "節點ID", position = 4)
    private String nodeId = StringUtils.EMPTY;
	
	@ApiModelProperty(value = "節點指派使用者", position = 5)
    private String taskAssignees = StringUtils.EMPTY;

	@ApiModelProperty(value = "節點指派使用者, 多使用者下拉選單 userId、name", position = 6)
    private ComboBox taskAssigneesCombox = new ComboBox();

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getTaskAssignees() {
		return taskAssignees;
	}

	public void setTaskAssignees(String taskAssignees) {
		this.taskAssignees = taskAssignees;
	}

	public ComboBox getTaskAssigneesCombox() {
		return taskAssigneesCombox;
	}

	public void setTaskAssigneesCombox(ComboBox taskAssigneesCombox) {
		this.taskAssigneesCombox = taskAssigneesCombox;
	}
}
