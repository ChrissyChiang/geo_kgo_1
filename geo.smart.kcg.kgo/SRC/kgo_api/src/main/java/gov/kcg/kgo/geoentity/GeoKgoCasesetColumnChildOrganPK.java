package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * GEO 20211109 add 機關審核表單
 * 機關審核表單 服務設定子欄位 pk
 */
@Embeddable
public class GeoKgoCasesetColumnChildOrganPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false, length = 50)
    private String CColumnId;

    @Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
    private String caseSetId;

    @Column(name = "Version", unique = true, nullable = false)
    private Integer version;

    @Column(name = "ColumnId", unique = true, nullable = false, length = 50)
    private String columnId;

    @Column(name = "CaseFormVersion", unique = true, nullable = false)
    private Integer caseFormVersion;

    public GeoKgoCasesetColumnChildOrganPK() {
    }

    public String getCColumnId() {
        return this.CColumnId;
    }

    public void setCColumnId(String CColumnId) {
        this.CColumnId = CColumnId;
    }

    public String getCaseSetId() {
        return this.caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
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
        if (!(other instanceof GeoKgoCasesetColumnChildOrganPK)) {
            return false;
        }
        GeoKgoCasesetColumnChildOrganPK castOther = (GeoKgoCasesetColumnChildOrganPK) other;
        return this.CColumnId.equals(castOther.CColumnId) && this.caseSetId.equals(castOther.caseSetId)
                && (this.version == castOther.version) && this.columnId.equals(castOther.columnId)
                && (this.caseFormVersion == castOther.caseFormVersion);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.CColumnId.hashCode();
        hash = hash * prime + this.caseSetId.hashCode();
        hash = hash * prime + this.version;
        hash = hash * prime + this.columnId.hashCode();
        hash = hash * prime + this.caseFormVersion;

        return hash;
    }
}
