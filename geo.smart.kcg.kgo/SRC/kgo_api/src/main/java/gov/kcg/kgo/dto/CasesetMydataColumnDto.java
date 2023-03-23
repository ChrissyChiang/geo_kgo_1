package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetMydata;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class CasesetMydataColumnDto {

	@Id
	@Column(name = "ColumnId", unique = true, nullable = false, length = 50)
	private String columnId;

	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "Version", unique = true, nullable = false)
	private Integer version;

	@Column(name = "MyDataClientId", length = 30)
	private String myDataClientId;

	/** MYDATA資料集代碼 */
	@ApiModelProperty(notes = "MYDATA資料集代碼")
	@Column(name = "MyDataId", length = 50)
	private String myDataId;

	@Column(name = "MyDataColumn", length = 50)
	private String myDataColumn;

	@Column(name = "Type", length = 30)
	private String type;

	@Column(name = "MyDataType", length = 30)
	private String myDataType;

	@Column(name = "FileName", length = 200)
	private String fileName;

	@Column(name = "HeaderRow")
	private Integer headerRow;

	@Column(name = "DataStartRow")
	private Integer dataStartRow;

	public CasesetMydataColumnDto() {

	}

	public CasesetMydataColumnDto(KgoCasesetColumn kgoCasesetColumn, KgoCasesetMydata KgoCasesetMydata) {
		this.columnId = kgoCasesetColumn.getId().getColumnId();
		this.caseSetId = kgoCasesetColumn.getId().getCaseSetId();
		this.version = kgoCasesetColumn.getId().getVersion();
		this.myDataId = kgoCasesetColumn.getMyDataId();
		this.myDataColumn = kgoCasesetColumn.getMyDataColumn();
		this.myDataClientId = KgoCasesetMydata.getMyDataClientId();
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMyDataClientId() {
		return myDataClientId;
	}

	public void setMyDataClientId(String myDataClientId) {
		this.myDataClientId = myDataClientId;
	}

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataColumn() {
		return myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(Integer headerRow) {
		this.headerRow = headerRow;
	}

	public Integer getDataStartRow() {
		return dataStartRow;
	}

	public void setDataStartRow(Integer dataStartRow) {
		this.dataStartRow = dataStartRow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
