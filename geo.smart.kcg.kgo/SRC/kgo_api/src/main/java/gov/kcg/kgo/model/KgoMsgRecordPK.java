package gov.kcg.kgo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class KgoMsgRecordPK implements Serializable {

    @Column(name = "CaseId", unique = true, nullable = false, length = 50)
    private String caseId;

    @Column(name = "Seq", unique = true, nullable = false, length = 36)
    private String seq;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}