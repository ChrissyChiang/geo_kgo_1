package gov.kcg.kgo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class KgoTaskAchievePK implements Serializable {

    @Column(name = "CaseId")
    private String caseId;

    @Column(name = "ActivitySeq")
    private int activitySeq;

    @Column(name = "CaseSetId")
    private String caseSetId;


    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public int getActivitySeq() {
        return activitySeq;
    }

    public void setActivitySeq(int activitySeq) {
        this.activitySeq = activitySeq;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

}
