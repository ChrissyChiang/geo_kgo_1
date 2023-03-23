package gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.bean;
import java.util.List;

import javax.persistence.Column;

import com.alibaba.fastjson.annotation.JSONField;

import gov.kcg.kgo.viewModel.backend.actFlowManager.common.rs.bean.TpiFlowTaskBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 後台 - 動態流程管理 - TpiFlow bean
 * </p>
 *
 * @author
 * @since 2020-10-30
 */
@ApiModel(description = "後台 - 動態流程管理 - TpiFlow bean")
public class TpiFlowBean {

	@ApiModelProperty(value = "流程編號", position = 0)
    private String flowId;

	@ApiModelProperty(value = "流程中文名稱", position = 1)
    private String flowName;

	@ApiModelProperty(value = "流程描述", position = 2)
    private String flowDesc;
	
	@ApiModelProperty(value = "機關/單位代碼", position = 3)
	private String organId;
	
	@ApiModelProperty(value = "是否預設", position = 4)
	private Boolean isDefault;
	
	@ApiModelProperty(value = "流程資料Josn", position = 5)
	@JSONField(jsonDirect=true)
    private String jsonData;

	@ApiModelProperty(value = "流程定義步驟(明細)檔", position = 6)
    private List<TpiFlowTaskBean> flowTasks;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
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

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<TpiFlowTaskBean> getFlowTasks() {
		return flowTasks;
	}

	public void setFlowTasks(List<TpiFlowTaskBean> flowTasks) {
		this.flowTasks = flowTasks;
	}
}
