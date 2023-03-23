package gov.kcg.kgo.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CASESET_COLUMN_CHILD database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_COLUMN_CHILD")
@NamedQuery(name="KgoCasesetColumnChild.findAll", query="SELECT k FROM KgoCasesetColumnChild k")
public class KgoCasesetColumnChild implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetColumnChildPK id;

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

	/**GEO 20211019 add */
	@Column(name = "IsCheckFrequency")
	private Integer isCheckFrequency;

	/** GEO 20211102 add 欄位勾選*/
	@Column(name = "IsFieldCheck")
	private Integer isFieldCheck;

	public KgoCasesetColumnChild() {
	}

	public KgoCasesetColumnChildPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetColumnChildPK id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "KgoCasesetColumnChild{" +
				"id=" + id +
				", BText='" + BText + '\'' +
				", columnType='" + columnType + '\'' +
				", columnValue='" + columnValue + '\'' +
				", FText='" + FText + '\'' +
				", isMustKey=" + isMustKey +
				", length=" + length +
				", memo='" + memo + '\'' +
				", orderNum=" + orderNum +
				", PColumnId='" + PColumnId + '\'' +
				", row=" + row +
				", VGroup='" + VGroup + '\'' +
				", isCheckFrequency=" + isCheckFrequency +
				", isFieldCheck=" + isFieldCheck +
				'}';
	}
}