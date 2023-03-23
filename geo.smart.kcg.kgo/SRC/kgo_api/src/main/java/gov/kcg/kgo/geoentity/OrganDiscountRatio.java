package gov.kcg.kgo.geoentity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the GEO_KGO_ORGAN_DISCOUNT_RATIO database table.
 * 
 */
@Entity
@Table(name = "GEO_KGO_ORGAN_DISCOUNT_RATIO")
public class OrganDiscountRatio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "organ_discount_ratio_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long organDiscountRatioId;
	
	@Column(name = "organ_id")
	private String organId;

	@Column(name = "discount_ratio")
	private Integer discountRatio;

	@Column(name = "edit_user" )
	private String editUser;

	@Column(name = "edit_time" )
	private Timestamp editTime;

	public Long getOrganDiscountRatioId() {
		return organDiscountRatioId;
	}

	public void setOrganDiscountRatioId(Long organDiscountRatioId) {
		this.organDiscountRatioId = organDiscountRatioId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getDiscountRatio() {
		return discountRatio;
	}

	public void setDiscountRatio(Integer discountRatio) {
		this.discountRatio = discountRatio;
	}

	public String getEditUser() {return editUser;}

	public void setEditUser(String editUser) {this.editUser = editUser;}

	public Timestamp getEditTime() {return editTime;}

	public void setEditTime(Timestamp editTime) {this.editTime = editTime;}
}
