package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * GEO 20211115 add 跨機關檢視
 * 跨機關檢視權限 機關服務對應 pk
 */
@Embeddable
public class GeoKgoCasesetCrossReviewPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CaseSetId", unique = true, nullable = false, length = 50)
    private String caseSetId;

    @Column(name = "OrganId", unique = true, nullable = false, length = 50)
    private String organId;

    public GeoKgoCasesetCrossReviewPK() {
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoCasesetCrossReviewPK)) {
            return false;
        }
        GeoKgoCasesetCrossReviewPK castOther = (GeoKgoCasesetCrossReviewPK) other;
        return this.caseSetId.equals(castOther.caseSetId) && (this.organId.equals(castOther.organId));
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.caseSetId.hashCode();
        hash = hash * prime + this.organId.hashCode();

        return hash;
    }
}
