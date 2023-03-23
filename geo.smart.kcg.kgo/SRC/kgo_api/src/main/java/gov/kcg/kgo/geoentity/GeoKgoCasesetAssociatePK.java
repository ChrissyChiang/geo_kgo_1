package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * GEO 20211019 add
 * 案件關聯服務PK
 */
@Embeddable
public class GeoKgoCasesetAssociatePK implements Serializable {


    @Column(name = "caseset_id", columnDefinition = "varchar(30) NOT NULL")
    private String casesetId; //服務id


    @Column(name = "associate_caseset_id", columnDefinition = "varchar(30) NOT NULL")
    private String associateCasesetId; //關聯服務id

    public GeoKgoCasesetAssociatePK() {
    }

    public GeoKgoCasesetAssociatePK(String casesetId, String associateCasesetId) {
        this.casesetId = casesetId;
        this.associateCasesetId = associateCasesetId;
    }

    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }

    public String getAssociateCasesetId() {
        return associateCasesetId;
    }

    public void setAssociateCasesetId(String associateCasesetId) {
        this.associateCasesetId = associateCasesetId;
    }
}
