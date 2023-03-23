package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * GEO 20211109 add 機關審核表單
 * 機關審核表單 案件設定群組 pk
 */
@Embeddable
public class GeoKgoCasesetGroupOrganPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
    private String caseSetId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupSeq", unique = true, nullable = false)
    private Integer groupSeq;

    @Column(name = "Version", unique = true, nullable = false)
    private Integer version;

    @Column(name = "CaseFormVersion", unique = true, nullable = false)
    private Integer caseFormVersion;

    public GeoKgoCasesetGroupOrganPK() {
    }

    public String getCaseSetId() {
        return this.caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Integer getGroupSeq() {
        return this.groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCaseFormVersion() {
        return caseFormVersion;
    }

    public void setCaseFormVersion(Integer caseFormVersion) {
        this.caseFormVersion = caseFormVersion;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoCasesetGroupOrganPK)) {
            return false;
        }
        GeoKgoCasesetGroupOrganPK castOther = (GeoKgoCasesetGroupOrganPK) other;
        return this.caseSetId.equals(castOther.caseSetId) && (this.groupSeq == castOther.groupSeq)
                && (this.version == castOther.version)  && (this.caseFormVersion == castOther.caseFormVersion);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.caseSetId.hashCode();
        hash = hash * prime + this.groupSeq;
        hash = hash * prime + this.version;
        hash = hash * prime + this.caseFormVersion;

        return hash;
    }
}
