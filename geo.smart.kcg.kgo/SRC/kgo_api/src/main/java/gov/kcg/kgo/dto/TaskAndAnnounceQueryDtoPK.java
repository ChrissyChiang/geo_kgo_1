package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
@ApiModel(description = "任務及公告管理-任務以及公告查詢Key")
public class TaskAndAnnounceQueryDtoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "序號")
	@Column(name = "SEQ")
	private Integer seq;

	@ApiModelProperty(notes = "發布對象(F:前台B:後台)")
	@Column(name = "TYPE")
	private String type;

	@ApiModelProperty(notes = "發布功能(AnnounceM:公告; TaskM:任務)")
	@Column(name = "FUN_CODE")
	private String funCode;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskAndAnnounceQueryDtoPK)) {
			return false;
		}
		TaskAndAnnounceQueryDtoPK castOther = (TaskAndAnnounceQueryDtoPK) other;
		return this.seq.equals(castOther.seq) && this.type.equals(castOther.type)
				&& this.funCode.equals(castOther.funCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.seq.hashCode();
		hash = hash * prime + this.type.hashCode();
		hash = hash * prime + this.funCode.hashCode();

		return hash;
	}

}
