package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *  GEO 20211109 add 機關審核表單
 * 	機關審核表單 案件資料檔 pk
 */
@Embeddable
public class GeoKgoCaseDetailOrganPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CaseId", unique = true, nullable = false, length = 50)
    private String caseId;

    @Column(name = "ColumnId", unique = true, nullable = false, length = 50)
    private String columnId;

    @Column(name = "Version", unique = true, nullable = false)
    private Integer organFormVersion;

    @Column(name = "CColumnId", unique = true, nullable = false, length = 50)
    private String CColumnId;

    @Column(name = "CaseFormVersion", unique = true, nullable = false)
    private Integer caseFormVersion;

    @Column(name = "CommentId", unique = true, nullable = false, length = 64)
    private String commentId;

    public GeoKgoCaseDetailOrganPK() {
    }

    public String getCaseId() {
        return this.caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public Integer getOrganFormVersion() {
        return this.organFormVersion;
    }

    public void setOrganFormVersion(Integer version) {
        this.organFormVersion = version;
    }

    public String getCColumnId() {
        return CColumnId;
    }

    public void setCColumnId(String cColumnId) {
        CColumnId = cColumnId;
    }

    public Integer getCaseFormVersion() {
        return caseFormVersion;
    }

    public void setCaseFormVersion(Integer caseFormVersion) {
        this.caseFormVersion = caseFormVersion;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoCaseDetailOrganPK)) {
            return false;
        }
        GeoKgoCaseDetailOrganPK castOther = (GeoKgoCaseDetailOrganPK) other;
        return this.caseId.equals(castOther.caseId) && this.columnId.equals(castOther.columnId)
                && (this.organFormVersion == castOther.organFormVersion) && this.CColumnId.equals(castOther.CColumnId)
                && (this.caseFormVersion == castOther.caseFormVersion) && this.commentId.equals(castOther.commentId);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.caseId.hashCode();
        hash = hash * prime + this.columnId.hashCode();
        hash = hash * prime + this.organFormVersion;
        hash = hash * prime + this.CColumnId.hashCode();
        hash = hash * prime + this.caseFormVersion;
        hash = hash * prime + this.commentId.hashCode();

        return hash;
    }
}
