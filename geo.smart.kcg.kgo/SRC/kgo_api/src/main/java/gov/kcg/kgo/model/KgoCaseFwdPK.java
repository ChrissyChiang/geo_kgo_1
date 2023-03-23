package gov.kcg.kgo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the KGO_CASE_FWD database table.
 */
@Embeddable
public class KgoCaseFwdPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CaseId", unique = true, nullable = false, length = 50)
    private String caseId;

    @Column(name = "Seq", unique = true, nullable = false, length = 36)
    private String seq;

    public KgoCaseFwdPK() {
    }

    public String getCaseId() {
        return this.caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getSeq() {
        return this.seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}