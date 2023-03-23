package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
@ApiModel(description = "後台案件處理-URA案件檢視資料")
public class UraCaseViewExtraDataQueryDtoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 群組代碼 */
	@ApiModelProperty(notes = "群組代碼")
	@Column(name = "GROUP_SEQ")
	private Integer groupSeq;

	/** 版本 **/
	@ApiModelProperty(notes = "版本")
	@Column(name = "VERSION")
	private Integer version;

	/** 欄位 */
	@ApiModelProperty(notes = "欄位")
	@Column(name = "COLUMN_ID")
	private String columnId;

	public UraCaseViewExtraDataQueryDtoPK() {
	}

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

}
