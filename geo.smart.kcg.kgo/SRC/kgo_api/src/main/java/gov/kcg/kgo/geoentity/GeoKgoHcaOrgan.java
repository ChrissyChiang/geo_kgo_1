package gov.kcg.kgo.geoentity; 

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table; 

/** 
 * 健保卡所屬機關
 */
@Entity
@Table(name = "GEO_KGO_HCA_ORGAN")
public class GeoKgoHcaOrgan implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hca_organ_id")
    private Long hcaOrganId; 
    
    @Column(name = "hca_card_number")
    private String hcaCardNumber;
    
    @Column(name = "organ_id")
    private String organId;

	public Long getHcaOrganId() {
		return hcaOrganId;
	}

	public void setHcaOrganId(Long hcaOrganId) {
		this.hcaOrganId = hcaOrganId;
	}

	public String getHcaCardNumber() {
		return hcaCardNumber;
	}

	public void setHcaCardNumber(String hcaCardNumber) {
		this.hcaCardNumber = hcaCardNumber;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}



} 
