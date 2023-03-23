package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_MYDATA_COLUMN database table.
 * 
 */
@Entity
@Table(name = "KGO_MYDATA_COLUMN")
@NamedQuery(name = "KgoMydataColumn.findAll", query = "SELECT k FROM KgoMydataColumn k")
public class KgoMydataColumn implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoMydataColumnPK id;

	@Column(name = "IsHaveValue")
	private Boolean isHaveValue;

	@Column(name = "IsNumOrDate")
	private Boolean isNumOrDate;

	@Column(name = "Name")
	private String name;

	@Column(name = "Type", length = 30)
	private String type;

	@Column(name = "RestrictedSourceValue")
	private String restrictedSourceValue;

	@Column(name = "RestrictedLength")
	private Integer restrictedLength;

	@Column(name = "Format")
	private String format;

	@Column(name = "RestrictedColumnType")
	private String restrictedColumnType;

	@Column(name = "IsNotNull")
	private Boolean IsNotNull;

	@Column(name = "MyDataType", length = 30)
	private String myDataType;

	@Column(name = "FileName", length = 200)
	private String fileName;

	@Column(name = "HeaderRow")
	private Integer headerRow;

	@Column(name = "DataStartRow")
	private Integer dataStartRow;

	public KgoMydataColumn() {
	}

	public KgoMydataColumnPK getId() {
		return this.id;
	}

	public void setId(KgoMydataColumnPK id) {
		this.id = id;
	}

	public Boolean getIsHaveValue() {
		return this.isHaveValue;
	}

	public void setIsHaveValue(Boolean isHaveValue) {
		this.isHaveValue = isHaveValue;
	}

	public Boolean getIsNumOrDate() {
		return this.isNumOrDate;
	}

	public void setIsNumOrDate(Boolean isNumOrDate) {
		this.isNumOrDate = isNumOrDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRestrictedSourceValue() {
		return restrictedSourceValue;
	}

	public void setRestrictedSourceValue(String restrictedSourceValue) {
		this.restrictedSourceValue = restrictedSourceValue;
	}

	public Integer getRestrictedLength() {
		return restrictedLength;
	}

	public void setRestrictedLength(Integer restrictedLength) {
		this.restrictedLength = restrictedLength;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRestrictedColumnType() {
		return restrictedColumnType;
	}

	public void setRestrictedColumnType(String restrictedColumnType) {
		this.restrictedColumnType = restrictedColumnType;
	}

	public Boolean getIsNotNull() {
		return IsNotNull;
	}

	public void setIsNotNull(Boolean isNotNull) {
		IsNotNull = isNotNull;
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

}