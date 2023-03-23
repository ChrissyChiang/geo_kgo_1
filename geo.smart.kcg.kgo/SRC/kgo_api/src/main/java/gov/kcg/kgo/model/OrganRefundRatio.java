package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the GEO_KGO_ORGAN_REFUND_RATIO database table.
 * 
 */
@Entity
@Table(name = "GEO_KGO_ORGAN_REFUND_RATIO")
public class OrganRefundRatio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "organ_refund_ratio_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long organRefundRatioId;
	
	@Column(name = "organ_id")
	private String organId;

	@Column(name = "refund_ratio")
	private Integer refundRatio;

	public Long getOrganRefundRatioId() {
		return organRefundRatioId;
	}

	public void setOrganRefundRatioId(Long organRefundRatioId) {
		this.organRefundRatioId = organRefundRatioId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getRefundRatio() {
		return refundRatio;
	}

	public void setRefundRatio(Integer refundRatio) {
		this.refundRatio = refundRatio;
	}


}