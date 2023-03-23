package gov.kcg.kgo.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_CASESET_COLUMN database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_COLUMN")
@NamedQuery(name = "KgoCasesetColumn.findAll", query = "SELECT k FROM KgoCasesetColumn k")
public class KgoCasesetColumn implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetColumnPK id;

	@Column(name = "ColumnName")
	private String columnName;

	@Column(name = "ColumnType", length = 30)
	private String columnType;

	@Column(name = "ColumnValue", length = 500)
	private String columnValue;

	@Column(name = "GroupSeq", nullable = false)
	private Integer groupSeq;

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

	/**GEO 20211019 add */
	@Column(name = "IsCheckFrequency")
	private Integer isCheckFrequency;

	/** GEO 20211102 add 欄位勾選*/
	@Column(name = "IsFieldCheck")
	private Integer isFieldCheck;

	public KgoCasesetColumn() {
	}

	public KgoCasesetColumnPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetColumnPK id) {
		this.id = id;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnValue() {
		return this.columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public Integer getGroupSeq() {
		return this.groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public Boolean getIsMustKey() {
		return this.isMustKey;
	}

	public void setIsMustKey(Boolean isMustKey) {
		this.isMustKey = isMustKey;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMyDataCheckType() {
		return this.myDataCheckType;
	}

	public void setMyDataCheckType(String myDataCheckType) {
		this.myDataCheckType = myDataCheckType;
	}

	public String getMyDataCheckValue() {
		return this.myDataCheckValue;
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
		return this.myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public String getMyDataId() {
		return this.myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataType() {
		return this.myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public Integer getOrderNum() {
		return this.orderNum;
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