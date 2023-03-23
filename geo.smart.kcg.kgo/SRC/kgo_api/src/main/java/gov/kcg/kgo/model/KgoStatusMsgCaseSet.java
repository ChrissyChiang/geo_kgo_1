package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_STATUS_MSG_CASESET database table.
 */
@Entity
@Table(name = "KGO_STATUS_MSG_CASESET")
public class KgoStatusMsgCaseSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seq", unique = true, nullable = false)
    private Integer seq;

    @Column(name = "StatusMsgSeq", unique = true, nullable = false)
    private Integer statusMsgSeq;

    @Column(name = "CaseSetId", length = 50, nullable = false)
    private String caseSetId;

    @Column(name = "IsEnable", length = 5, nullable = false)
    private String isEnable;

    @Column(name = "UpdateTime", nullable = false)
    private Timestamp updateTime;

    @Column(name = "UpdateUser", length = 50, nullable = false)
    private String updateUser;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatusMsgSeq() {
        return statusMsgSeq;
    }

    public void setStatusMsgSeq(Integer statusMsgSeq) {
        this.statusMsgSeq = statusMsgSeq;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}