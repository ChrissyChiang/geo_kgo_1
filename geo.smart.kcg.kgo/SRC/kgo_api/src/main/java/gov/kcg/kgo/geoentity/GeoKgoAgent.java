package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211026 add
 * 代理人機制
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_AGENT", schema = "dbo")
public class GeoKgoAgent implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String agentId; //代理人機制id 
    private String principalUserId; //被代理人id 
    private String agentUserId; //代理人id 
    private Timestamp startTime; //代理起始時間 
    private Timestamp endTime; //代理結束時間 
    private Integer isSigned; //是否已簽核 
    private Integer isDelete; //是否已刪除
    private String editOrgan; //編輯單位
    private String editUser; //編輯人員
    private Timestamp editTime; //編輯時間

    @Id 
    @Column(name = "agent_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getAgentId() { 
        return agentId; 
    } 

    public void setAgentId(String agentId) { 
        this.agentId = agentId; 
    } 

    @Basic 
    @Column(name = "principal_user_id", columnDefinition = "varchar(50)") 
    public String getPrincipalUserId() { 
        return principalUserId; 
    } 

    public void setPrincipalUserId(String principalUserId) { 
        this.principalUserId = principalUserId; 
    } 

    @Basic 
    @Column(name = "agent_user_id", columnDefinition = "varchar(50) NULL") 
    public String getAgentUserId() { 
        return agentUserId; 
    } 

    public void setAgentUserId(String agentUserId) { 
        this.agentUserId = agentUserId; 
    } 

    @Basic 
    @Column(name = "start_time", columnDefinition = "datetime2(0) NULL") 
    public Timestamp getStartTime() { 
        return startTime; 
    } 

    public void setStartTime(Timestamp startTime) { 
        this.startTime = startTime; 
    } 

    @Basic 
    @Column(name = "end_time", columnDefinition = "datetime2(0) NULL") 
    public Timestamp getEndTime() { 
        return endTime; 
    } 

    public void setEndTime(Timestamp endTime) { 
        this.endTime = endTime; 
    } 

    @Basic 
    @Column(name = "is_signed", columnDefinition = "int NULL") 
    public Integer getIsSigned() { 
        return isSigned; 
    } 

    public void setIsSigned(Integer isSigned) { 
        this.isSigned = isSigned; 
    } 

    @Basic 
    @Column(name = "is_delete", columnDefinition = "int NULL") 
    public Integer getIsDelete() { 
        return isDelete; 
    } 

    public void setIsDelete(Integer isDelete) { 
        this.isDelete = isDelete; 
    }

    @Basic
    @Column(name = "edit_organ", columnDefinition = "varchar(50)")
    public String getEditOrgan() {
        return editOrgan;
    }

    public void setEditOrgan(String editOrgan) {
        this.editOrgan = editOrgan;
    }

    @Basic
    @Column(name = "edit_user", columnDefinition = "varchar(50)")
    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    @Basic
    @Column(name = "edit_time", columnDefinition = "datetime2(0)")
    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

} 
