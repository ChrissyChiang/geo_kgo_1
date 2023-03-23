package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the KGO_CASE_MAIN database table.
 */
@Entity
@Table(name = "KGO_CASE_MAIN")
@NamedQuery(name = "KgoCaseMain.findAll", query = "SELECT k FROM KgoCaseMain k")
public class KgoCaseMain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CaseId", unique = true, nullable = false, length = 50)
    private String caseId;

    @Column(name = "ApplyDate")
    private Date applyDate;

    @Column(name = "ApplyUser", length = 50)
    private String applyUser;

    @Column(name = "ApplyUserName")
    private String applyUserName;

    @Column(name = "CaseOfficer", length = 50)
    private String caseOfficer;

    @Column(name = "CaseOrgan", length = 50)
    private String caseOrgan;

    @Column(name = "CaseSetId", nullable = false, length = 30)
    private String caseSetId;

    @Column(name = "CorrectDate")
    private Timestamp correctDate;

    @Column(name = "CorrectDeadline")
    private Integer correctDeadline;

    @Column(name = "CreateTime")
    private Timestamp createTime;

    @Column(name = "CreateUser", length = 50)
    private String createUser;

    @Column(name = "DeadlineDate")
    private Date deadlineDate;

    @Column(name = "GetDate")
    private Timestamp getDate;

    @Column(name = "IP", length = 500)
    private String ip;

    @Column(name = "IsLate")
    private Boolean isLate;

    @Column(name = "MyDataTxId", length = 500)
    private String myDataTxId;

    @Column(name = "ProcessId", length = 200)
    private String processId;

    @Column(name = "Result")
    private String result;

    @Column(name = "Reviewer", length = 50)
    private String reviewer;

    @Column(name = "State", length = 30)
    private String state;

    @Column(name = "Status", length = 30)
    private String status;

    @Column(name = "UpdateTime")
    private Timestamp updateTime;

    @Column(name = "UpdateUser", length = 50)
    private String updateUser;

    @Column(name = "OCaseId", length = 50)
    private String oCaseId;

    @Column(name = "statusDesc")
    private String statusDesc;

    @Column(name = "FDate")
    private Timestamp fDate;

    @Column(name = "IdCheck", length = 30)
    private String idCheck;

    @Column(name = "RedirectTime")
    private Timestamp redirectTime;

    @Column(name = "IdSHA256", length = 500)
    private String idSHA256;

    @Column(name = "CellPhone", length = 30)
    private String cellPhone;

    @Column(name = "Account", length = 100)
    private String account;

    /**
     * GEO 20211102 add 申請人登入方式
     */
    @Column(name = "ApplyUserLoginType")
    private String applyUserLoginType;

    @Column(name = "IsOpenOrganForm")
    private Integer isOpenOrganForm;

    @Column(name = "CityCoin")
    private Integer cityCoin;

    public KgoCaseMain() {
    }

    public String getCaseId() {
        return this.caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyUser() {
        return this.applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyUserName() {
        return this.applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getCaseOfficer() {
        return this.caseOfficer;
    }

    public void setCaseOfficer(String caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public String getCaseOrgan() {
        return this.caseOrgan;
    }

    public void setCaseOrgan(String caseOrgan) {
        this.caseOrgan = caseOrgan;
    }

    public String getCaseSetId() {
        return this.caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Timestamp getCorrectDate() {
        return this.correctDate;
    }

    public void setCorrectDate(Timestamp correctDate) {
        this.correctDate = correctDate;
    }

    public Integer getCorrectDeadline() {
        return this.correctDeadline;
    }

    public void setCorrectDeadline(Integer correctDeadline) {
        this.correctDeadline = correctDeadline;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Timestamp getGetDate() {
        return this.getDate;
    }

    public void setGetDate(Timestamp getDate) {
        this.getDate = getDate;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getIsLate() {
        return this.isLate;
    }

    public void setIsLate(Boolean isLate) {
        this.isLate = isLate;
    }

    public String getMyDataTxId() {
        return myDataTxId;
    }

    public void setMyDataTxId(String myDataTxId) {
        this.myDataTxId = myDataTxId;
    }

    public String getProcessId() {
        return this.processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReviewer() {
        return this.reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getoCaseId() {
        return oCaseId;
    }

    public void setoCaseId(String oCaseId) {
        this.oCaseId = oCaseId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Timestamp getfDate() {
        return fDate;
    }

    public void setfDate(Timestamp fDate) {
        this.fDate = fDate;
    }

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public Timestamp getRedirectTime() {
        return redirectTime;
    }

    public void setRedirectTime(Timestamp redirectTime) {
        this.redirectTime = redirectTime;
    }

    public String getIdSHA256() {
        return idSHA256;
    }

    public void setIdSHA256(String idSHA256) {
        this.idSHA256 = idSHA256;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApplyUserLoginType() {
        return applyUserLoginType;
    }

    public void setApplyUserLoginType(String applyUserLoginType) {
        this.applyUserLoginType = applyUserLoginType;
    }

    public Integer getIsOpenOrganForm() {
        return isOpenOrganForm;
    }

    public void setIsOpenOrganForm(Integer isOpenOrganForm) {
        this.isOpenOrganForm = isOpenOrganForm;
    }

    public Boolean getLate() {
        return isLate;
    }

    public void setLate(Boolean late) {
        isLate = late;
    }

    public Integer getCityCoin() {
        return cityCoin;
    }

    public void setCityCoin(Integer cityCoin) {
        this.cityCoin = cityCoin;
    }

    @Override
    public String toString() {
        return "KgoCaseMain{" +
                "caseId='" + caseId + '\'' +
                ", applyDate=" + applyDate +
                ", applyUser='" + applyUser + '\'' +
                ", applyUserName='" + applyUserName + '\'' +
                ", caseOfficer='" + caseOfficer + '\'' +
                ", caseOrgan='" + caseOrgan + '\'' +
                ", caseSetId='" + caseSetId + '\'' +
                ", correctDate=" + correctDate +
                ", correctDeadline=" + correctDeadline +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", deadlineDate=" + deadlineDate +
                ", getDate=" + getDate +
                ", ip='" + ip + '\'' +
                ", isLate=" + isLate +
                ", myDataTxId='" + myDataTxId + '\'' +
                ", processId='" + processId + '\'' +
                ", result='" + result + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                ", oCaseId='" + oCaseId + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", fDate=" + fDate +
                ", idCheck='" + idCheck + '\'' +
                ", redirectTime=" + redirectTime +
                ", idSHA256='" + idSHA256 + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", account='" + account + '\'' +
                ", applyUserLoginType='" + applyUserLoginType + '\'' +
                ", isOpenOrganForm=" + isOpenOrganForm +
                ", cityCoin=" + cityCoin +
                '}';
    }
}