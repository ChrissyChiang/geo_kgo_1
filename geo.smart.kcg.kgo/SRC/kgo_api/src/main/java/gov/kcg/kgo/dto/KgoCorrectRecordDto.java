package gov.kcg.kgo.dto;

import gov.kcg.kgo.model.KgoCasesetArea;
import gov.kcg.kgo.model.KgoCorrectRecord;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class KgoCorrectRecordDto {

	@Id
	@ApiModelProperty(notes = "流水號")
	@Column(name = "SEQ")
	private Integer seq;

	@ApiModelProperty(notes = "狀態")
	@Column(name = "STATUS")
	private String status;

	@ApiModelProperty(notes = "經辦人")
	@Column(name = "HANDLER")
	private String handler;

	@ApiModelProperty(notes = "經辦人名稱")
	@Column(name = "HANDLER_NAME")
	private String handlerName;

	@ApiModelProperty(notes = "備註")
	@Column(name = "MEMO")
	private String memo;

	@ApiModelProperty(notes = "修改時間")
	@Column(name = "HANDLE_TIME")
	private Timestamp handleTime;

	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CASE_ID")
	private String caseId;

	public KgoCorrectRecordDto() {
	}

	public KgoCorrectRecordDto(KgoCorrectRecord kcr, KgoUser ku) {
		if (null != kcr) {
			this.seq = kcr.getSeq();
			this.status = kcr.getStatus();
			this.handler = kcr.getHandler();
			this.memo = kcr.getMemo();
			this.handleTime = kcr.getHandleTime();
			this.caseId = kcr.getCaseId();
		}
		if (null != ku) {
			this.handlerName = ku.getName();
		}
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Timestamp getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Timestamp handleTime) {
		this.handleTime = handleTime;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
}
