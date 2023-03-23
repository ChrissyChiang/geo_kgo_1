package gov.kcg.kgo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "KGO_CORRECT_RECORD")
@NamedQuery(name = "KgoCorrectRecord.findAll", query = "SELECT k FROM KgoCorrectRecord k")
public class KgoCorrectRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seq", unique = true, nullable = false, length = 30)
    private Integer seq;

    @Column(name = "Status", length = 30)
    private String status;

    @Column(name = "Handler", length = 50)
    private String handler;

    @Column(name = "Memo", length = 500)
    private String memo;

    @Column(name = "HandleTime")
    private Timestamp handleTime;

    @Column(name = "CaseId")
    private String caseId;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Timestamp getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
