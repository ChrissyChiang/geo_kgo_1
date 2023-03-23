package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_CASE_MAIN_RESEND_FLOW database table.
 * 
 */
@Entity
@Table(name="KGO_CASE_MAIN_RESEND_FLOW")
@NamedQuery(name="KgoCaseMainResendFlow.findAll", query="SELECT k FROM KgoCaseMainResendFlow k")
public class KgoCaseMainResendFlow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Seq", unique=true, nullable=false, length=50)
	private Integer seq;
	
	@Column(name="CaseId", unique=true, nullable=false, length=50)
	private String caseId;

	@Column(name="ProcessId", unique=true, nullable=false, length=200)
	private String processId;
	
	@Column(name="FlowId", unique=true, nullable=false, length=50)
	private String flowId;
	
	@Column(name="FlowDefId")
	private String flowDefId;

	@Column(name="CreateTime", nullable=false)
	private Timestamp createTime;

	public KgoCaseMainResendFlow() {
	}

	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public String getCaseId() {
		return caseId;
	}


	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}


	public String getProcessId() {
		return processId;
	}


	public void setProcessId(String processId) {
		this.processId = processId;
	}


	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowDefId() {
		return flowDefId;
	}

	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}