package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.springframework.data.annotation.Transient;

import gov.kcg.kgo.model.KgoCaseDetail;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCasesetColumn;

/**
 * 服務申辦流程(SA) -> KGO_CASE_MAIN 欄位資料、detail 值
 */
@Entity
public class SaCaseColumnDetailDto implements Serializable {

	@EmbeddedId
	private SaCaseColumnDetailDtoPK id;

	@Column(name = "CaseSetId")
	private String caseSetId;

	/** KgoCasesetColumn 欄位名稱 */
	@Column(name = "ColumnName")
	private String columnName;

	@Column(name = "ColumnType", length = 30)
	private String columnType;

	@Column(name = "IsSource")
	private Boolean isSource;

	/** 驗證my Data 欄位 */
	@Column(name = "MyDataClientId", length = 30)
	private String myDataClientId;

	@Column(name = "MyDataColumn", length = 50)
	private String myDataColumn;

	@Column(name = "MyDataId", length = 50)
	private String myDataId;
	/** 驗證my Data 欄位 */

	/** KgoCaseDetail欄位值 */
	@Transient
	private String detailColumnValue;

	/** KgoCasesetColumn欄位值 */
	@Transient
	private String casesetColumnValue;

	public SaCaseColumnDetailDto() {
	}

	/**
	 * Instantiates a new sa case column detail dto.
	 *
	 * @param cc  the cc
	 * @param cg  the cg
	 * @param cd  the cd
	 * @param kcm the kcm
	 */
	public SaCaseColumnDetailDto(KgoCasesetColumn cc, KgoCaseDetail cd, KgoCaseMain kcm) {
		if (cc != null && cd != null && kcm != null) {
			SaCaseColumnDetailDtoPK id = new SaCaseColumnDetailDtoPK();
			id.setCaseId(kcm.getCaseId());
			id.setColumnId(cd.getId().getColumnId());
			id.setVersion(cd.getId().getVersion());

			this.id = id;
			this.caseSetId = kcm.getCaseSetId();
			this.detailColumnValue = cd.getColumnValue();
			this.isSource = cd.getIsSource() == null ? false : cd.getIsSource();

			this.columnName = cc.getColumnName();
			this.columnType = cc.getColumnType();
			this.casesetColumnValue = cc.getColumnValue();
			this.myDataClientId = cc.getMyDataClientId();
			this.myDataColumn = cc.getMyDataColumn();
			this.myDataId = cc.getMyDataId();
		}
	}

	public SaCaseColumnDetailDtoPK getId() {
		return id;
	}

	public void setId(SaCaseColumnDetailDtoPK id) {
		this.id = id;
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

	public Boolean getIsSource() {
		return isSource;
	}

	public void setIsSource(Boolean isSource) {
		this.isSource = isSource;
	}

	public String getCasesetColumnValue() {
		return casesetColumnValue;
	}

	public void setCasesetColumnValue(String casesetColumnValue) {
		this.casesetColumnValue = casesetColumnValue;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getDetailColumnValue() {
		return detailColumnValue;
	}

	public void setDetailColumnValue(String detailColumnValue) {
		this.detailColumnValue = detailColumnValue;
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

}
