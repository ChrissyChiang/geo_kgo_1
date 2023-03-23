package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the GEO_KGO_CASESET_REFUND_RATIO database table.
 * 
 */
@Entity
@Table(name = "GEO_KGO_CASESET_REFUND_RATIO")
public class KgoCasesetRefundRatio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_refund_ratio_id")
	private Long serviceRefundRatioId;

	@Column(name = "case_set_id")
	private String casesetId;

	@Column(name = "from_days")
	private Integer fromDays;

	@Column(name = "end_days")
	private Integer endDays;
	
	@Column(name = "refund_ratio")
	private Integer refundRatio;

	public Long getServiceRefundRatioId() {return serviceRefundRatioId;}

	public void setServiceRefundRatioId(Long serviceRefundRatioId) {this.serviceRefundRatioId = serviceRefundRatioId;}

	public String getCasesetId() {
		return casesetId;
	}

	public void setCasesetId(String casesetId) {
		this.casesetId = casesetId;
	}

	public Integer getFromDays() {
		return fromDays;
	}

	public void setFromDays(Integer fromDays) {
		this.fromDays = fromDays;
	}

	public Integer getEndDays() {
		return endDays;
	}

	public void setEndDays(Integer endDays) {
		this.endDays = endDays;
	}

	public Integer getRefundRatio() {
		return refundRatio;
	}

	public void setRefundRatio(Integer refundRatio) {
		this.refundRatio = refundRatio;
	}
}
