package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@ApiModel(description = "後台案件處理-URA案件檢視-額外設定資料")
@Entity
public class UraCaseViewExtraDataQueryDto {

	@EmbeddedId
	private UraCaseViewExtraDataQueryDtoPK id;

	/** 區塊說明 */
	@ApiModelProperty(notes = "區塊說明")
	@Column(name = "MEMO")
	private String memo;

	/** 欄位名稱 */
	@ApiModelProperty(notes = "欄位名稱")
	@Column(name = "COLUMN_NAME")
	private String columnName;

	/** 欄位型態 **/
	@ApiModelProperty(notes = "欄位型態")
	@Column(name = "COLUMN_TYPE")
	private String columnType;

	/** 欄位長度 */
	@ApiModelProperty(notes = "欄位長度")
	@Column(name = "LENGTH")
	private Integer length;

	/** 是否必填 */
	@ApiModelProperty(notes = "是否必填")
	@Column(name = "IS_MUST_KEY")
	private Boolean isMustKey;

	/** 欄位值 */
	@ApiModelProperty(notes = "欄位值")
	@Column(name = "COLUMN_VALUE")
	private String columnValue;

	public UraCaseViewExtraDataQueryDto() {
	}

	public UraCaseViewExtraDataQueryDtoPK getId() {
		return id;
	}

	public void setId(UraCaseViewExtraDataQueryDtoPK id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(Boolean isMustKey) {
		this.isMustKey = isMustKey;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

}
