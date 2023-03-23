package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_STATUS_MSG database table.
 */
@Entity
@Table(name = "KGO_STATUS_MSG")
public class KgoStatusMsg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seq", unique = true, nullable = false)
    private Integer seq;

    @Column(name = "OrganId", length = 50, nullable = false)
    private String organId;

    @Column(name = "Status", length = 30, nullable = false)
    private String status;

    @Column(name = "CaseStatusName", length = 50)
    private String caseStatusName;

    @Column(name = "MsgTitle", length = 20, nullable = false)
    private String msgTitle;

    @Column(name = "MsgBody", length = 100)
    private String msgBody;

    @Column(name = "ClickDetail", length = 200)
    private String clickDetail;

    @Column(name = "IsEnable", length = 5, nullable = false)
    private String isEnable;

    @Column(name = "IsDefault", length = 5, nullable = false)
    private String isDefault;

    @Column(name = "CreateTime", nullable = false)
    private Timestamp createTime;

    @Column(name = "CreateUser", length = 50, nullable = false)
    private String createUser;

    @Column(name = "UpdateTime")
    private Timestamp updateTime;

    @Column(name = "UpdateUser", length = 50)
    private String updateUser;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getClickDetail() {
        return clickDetail;
    }

    public void setClickDetail(String clickDetail) {
        this.clickDetail = clickDetail;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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