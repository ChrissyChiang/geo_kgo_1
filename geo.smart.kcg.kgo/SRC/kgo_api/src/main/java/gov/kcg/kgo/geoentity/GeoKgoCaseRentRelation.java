package gov.kcg.kgo.geoentity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "GEO_KGO_CASE_RENT_RELATION")
public class GeoKgoCaseRentRelation implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[case_rent_relation_id]")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long caseRentRelationId;
	
	@Column(name = "case_id")
	private String caseId;

	@Column(name = "caseset_id")
	private String casesetId;

	@Column(name = "[rent_time_id]")
	private String rentTimeId;

	@Column(name = "rent_status",columnDefinition = "varchar(10) null")
	private String rentStatus;

	@Column(name = "site_main_id" ,columnDefinition = "[varchar](50) NOT NULL")
	private String siteMainId;

	@Column(name="start_time",columnDefinition = "datetime2(0) not null")
	private Timestamp startTime;

	@Column(name="end_time",columnDefinition = "datetime2(0) not null")
	private Timestamp endTime;

	@Column(name="edit_time",columnDefinition = "datetime2(0) not null")
	private Timestamp editTime;

	@Column(name="create_time",columnDefinition = "datetime2(0) not null")
	private Timestamp createTime;
	@Transient
	private String caseSetCategory;

	public Long getCaseRentRelationId() {
		return caseRentRelationId;
	}

	public void setCaseRentRelationId(Long caseRentRelationId) {
		this.caseRentRelationId = caseRentRelationId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getRentTimeId() {
		return rentTimeId;
	}

	public void setRentTimeId(String rentTimeId) {
		this.rentTimeId = rentTimeId;
	}

	public String getCaseSetCategory() {
		return caseSetCategory;
	}

	public void setCaseSetCategory(String caseSetCategory) {
		this.caseSetCategory = caseSetCategory;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getRentStatus() {return rentStatus;}

	public Timestamp getStartTime() {return startTime;}

	public void setStartTime(Timestamp startTime) {this.startTime = startTime;}

	public Timestamp getEndTime() {return endTime;}

	public void setEndTime(Timestamp endTime) {this.endTime = endTime;}

	public void setRentStatus(String rentStatus) {this.rentStatus = rentStatus;}

	public String getCasesetId() {return casesetId;}

	public void setCasesetId(String casesetId) {this.casesetId = casesetId;}

	public String getSiteMainId() {return siteMainId;}

	public void setSiteMainId(String siteMainId) {this.siteMainId = siteMainId;}
}
