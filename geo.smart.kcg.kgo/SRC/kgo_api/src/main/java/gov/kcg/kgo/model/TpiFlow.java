package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the TPI_FLOW database table.
 * 
 */
@Entity
@Table(name="TPI_FLOW")
@NamedQuery(name="TpiFlow.findAll", query="SELECT t FROM TpiFlow t")
public class TpiFlow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(generator  = "generator")
//	@GenericGenerator(name = "generator", strategy = "guid")
	@Column(name="FlowId", unique=true, nullable=false, length=1)
	private String flowId;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="FlowDefId", nullable=false, length=100)
	private String flowDefId;

	@Column(name="FlowDesc", nullable=false, length=4000)
	private String flowDesc;
	
	@Column(name="OrganId", nullable=false)
	private String organId;
	
	@Column(name="IsDefault", nullable=false)
	private Boolean isDefault;
	
	@Column(name="JsonData", nullable=false)
	private String jsonData;

	@Column(name="FlowEnable", nullable=false, length=5)
	private String flowEnable;

	@Column(name="FlowName", nullable=false, length=200)
	private String flowName;
	
	@Column(name="FlowType", nullable=false, length=1)
	private String flowType;

	@Column(name="UpdateTime", nullable=false)
	private Timestamp updateTime;

	public TpiFlow() {
	}

	public String getFlowId() {
		return this.flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getFlowDefId() {
		return this.flowDefId;
	}

	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
	}

	public String getFlowDesc() {
		return this.flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	public String getFlowEnable() {
		return this.flowEnable;
	}

	public void setFlowEnable(String flowEnable) {
		this.flowEnable = flowEnable;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getFlowName() {
		return this.flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}