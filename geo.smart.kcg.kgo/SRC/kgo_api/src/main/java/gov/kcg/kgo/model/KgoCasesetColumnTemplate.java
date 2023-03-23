package gov.kcg.kgo.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the KGO_CASESET_COLUMN database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_COLUMN_TEMPLATE")
@NamedQuery(name = "KgoCasesetColumnTemplate.findAll", query = "SELECT k FROM KgoCasesetColumnTemplate k")
public class KgoCasesetColumnTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Seq", unique=true, nullable=false)
	private Integer seq;

	@Column(name = "ColumnId", unique = true, nullable = false, length = 50)
	private String columnId;

	@Column(name = "ColumnName")
	private String columnName;

	@Column(name = "ColumnType", length = 30)
	private String columnType;

	@Column(name = "ColumnValue", length = 500)
	private String columnValue;

	@Column(name = "IsMustKey")
	private Boolean isMustKey;

	@Column(name = "Length")
	private Integer length;

	@Column(name = "Memo")
	private String memo;

	@Column(name = "MyDataCheckType", length = 30)
	private String myDataCheckType;

	@Column(name = "MyDataCheckValue")
	private String myDataCheckValue;

	@Column(name = "MyDataClientId", length = 30)
	private String myDataClientId;

	@Column(name = "MyDataColumn", length = 50)
	private String myDataColumn;

	@Column(name = "MyDataId", length = 50)
	private String myDataId;

	@Column(name = "MyDataType", length = 30)
	private String myDataType;

	@Column(name = "OrderNum")
	private Integer orderNum;

	@Column(name = "FileType", length = 200)
	private String fileType;

	@Column(name = "TemplateSeq")
	private Integer templateSeq;

	@Column(name = "Suspend", length = 2)
	private String suspend;

	/**GEO 20211019 add */
	@Column(name = "IsCheckFrequency")
	private Integer isCheckFrequency;

	/** GEO 20211102 add 欄位勾選*/
	@Column(name = "IsFieldCheck")
	private Integer isFieldCheck;

	public KgoCasesetColumnTemplate() {
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
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

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public Boolean getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(Boolean mustKey) {
		isMustKey = mustKey;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMyDataCheckType() {
		return myDataCheckType;
	}

	public void setMyDataCheckType(String myDataCheckType) {
		this.myDataCheckType = myDataCheckType;
	}

	public String getMyDataCheckValue() {
		return myDataCheckValue;
	}

	public void setMyDataCheckValue(String myDataCheckValue) {
		this.myDataCheckValue = myDataCheckValue;
	}

	public String getMyDataClientId() {
		return myDataClientId;
	}

	public void setMyDataClientId(String myDataClientId) {
		this.myDataClientId = myDataClientId;
	}

	public String getMyDataColumn() {
		return myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getTemplateSeq() {
		return templateSeq;
	}

	public void setTemplateSeq(Integer templateSeq) {
		this.templateSeq = templateSeq;
	}

	public String getSuspend() {
		return suspend;
	}

	public void setSuspend(String suspend) {
		this.suspend = suspend;
	}

	public Integer getIsCheckFrequency() {
		return isCheckFrequency;
	}

	public void setIsCheckFrequency(Integer isCheckFrequency) {
		this.isCheckFrequency = isCheckFrequency;
	}

	public Integer getIsFieldCheck() {
		return isFieldCheck;
	}

	public void setIsFieldCheck(Integer isFieldCheck) {
		this.isFieldCheck = isFieldCheck;
	}
}