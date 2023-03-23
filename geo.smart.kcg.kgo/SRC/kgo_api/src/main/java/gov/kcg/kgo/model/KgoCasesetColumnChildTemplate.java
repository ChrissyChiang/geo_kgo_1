package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the KGO_CASESET_COLUMN_CHILD database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_COLUMN_CHILD_TEMPLATE")
@NamedQuery(name="KgoCasesetColumnChildTemplate.findAll", query="SELECT k FROM KgoCasesetColumnChildTemplate k")
public class KgoCasesetColumnChildTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Seq", unique=true, nullable=false)
	private Integer seq;

	@Column(name="ColumnSeq")
	private Integer columnSeq;

	@Column(unique = true, nullable = false, length = 50)
	private String CColumnId;

	@Column(name = "ColumnId", unique = true, nullable = false, length = 50)
	private String columnId;

	private String BText;

	@Column(name="ColumnType", length=30)
	private String columnType;

	@Column(name="ColumnValue", length=500)
	private String columnValue;

	private String FText;

	@Column(name="IsMustKey")
	private Boolean isMustKey;

	@Column(name="Length")
	private Integer length;

	@Column(name="Memo")
	private String memo;

	@Column(name="OrderNum")
	private Integer orderNum;

	@Column(length=50)
	private String PColumnId;

	@Column(name="Row")
	private Integer row;

	@Column(length=50)
	private String VGroup;

	@Column(name = "Suspend", length = 2)
	private String suspend;

	/**GEO 20211019 add */
	@Column(name = "IsCheckFrequency")
	private Integer isCheckFrequency;

	/** GEO 20211102 add 欄位勾選*/
	@Column(name = "IsFieldCheck")
	private Integer isFieldCheck;

	public KgoCasesetColumnChildTemplate() {
	}

	public String getBText() {
		return this.BText;
	}

	public void setBText(String BText) {
		this.BText = BText;
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

	public String getFText() {
		return this.FText;
	}

	public void setFText(String FText) {
		this.FText = FText;
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

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getPColumnId() {
		return this.PColumnId;
	}

	public void setPColumnId(String PColumnId) {
		this.PColumnId = PColumnId;
	}

	public Integer getRow() {
		return this.row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getVGroup() {
		return this.VGroup;
	}

	public void setVGroup(String VGroup) {
		this.VGroup = VGroup;
	}

	public String getCColumnId() {
		return CColumnId;
	}

	public void setCColumnId(String CColumnId) {
		this.CColumnId = CColumnId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Boolean getMustKey() {
		return isMustKey;
	}

	public void setMustKey(Boolean mustKey) {
		isMustKey = mustKey;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getColumnSeq() {
		return columnSeq;
	}

	public void setColumnSeq(Integer columnSeq) {
		this.columnSeq = columnSeq;
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