package gov.kcg.kgo.geoentity; 

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient; 

/** 
 * 某服務允許特定機關進入
 */
@Entity
@Table(name = "GEO_KGO_CASESET_ALLOW_ORGAN")
public class GeoKgoCasesetAllowOrgan implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseset_allow_organ_id")
    private Long casesetAllowOrganId; 
    
    @Column(name = "caseset_id")
    private String casesetId;
    
    @Column(name = "organ_id")
    private String organId;
    
    @Transient
    private String organName;

	public Long getCasesetAllowOrganId() {
		return casesetAllowOrganId;
	}

	public void setCasesetAllowOrganId(Long casesetAllowOrganId) {
		this.casesetAllowOrganId = casesetAllowOrganId;
	}

	public String getCasesetId() {
		return casesetId;
	}

	public void setCasesetId(String casesetId) {
		this.casesetId = casesetId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	} 
    
    

} 
