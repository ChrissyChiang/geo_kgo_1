package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211015 add
 * 線上預約臨櫃承辦人
 */
@Entity
@DynamicInsert
@IdClass(GeoKgoAppointmentContactUserPK.class)
@Table(name = "GEO_KGO_APPOINTMENT_CONTACT_USER", schema = "dbo")
public class GeoKgoAppointmentContactUser implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String userId; //人員id 
    private String appointmentMainId; //線上預約臨櫃主檔id 
    private String organId; //機關id 
    private String unitId; //科室id 
    private String userName; //承辦人姓名 
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間

    @Id 
    @Column(name = "user_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getUserId() { 
        return userId; 
    } 

    public void setUserId(String userId) { 
        this.userId = userId; 
    } 

    @Id 
    @Column(name = "appointment_main_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getAppointmentMainId() { 
        return appointmentMainId; 
    } 

    public void setAppointmentMainId(String appointmentMainId) { 
        this.appointmentMainId = appointmentMainId; 
    } 

    @Basic 
    @Column(name = "organ_id", columnDefinition = "varchar(50) NULL") 
    public String getOrganId() { 
        return organId; 
    } 

    public void setOrganId(String organId) { 
        this.organId = organId; 
    } 

    @Basic 
    @Column(name = "unit_id", columnDefinition = "varchar(50) NULL") 
    public String getUnitId() { 
        return unitId; 
    } 

    public void setUnitId(String unitId) { 
        this.unitId = unitId; 
    } 

    @Basic 
    @Column(name = "user_name", columnDefinition = "nvarchar(50) NULL") 
    public String getUserName() { 
        return userName; 
    } 

    public void setUserName(String userName) { 
        this.userName = userName; 
    } 

    @Basic 
    @Column(name = "edit_organ", columnDefinition = "varchar(50) NULL") 
    public String getEditOrgan() { 
        return editOrgan; 
    } 

    public void setEditOrgan(String editOrgan) { 
        this.editOrgan = editOrgan; 
    } 

    @Basic 
    @Column(name = "edit_user", columnDefinition = "varchar(50) NULL") 
    public String getEditUser() { 
        return editUser; 
    } 

    public void setEditUser(String editUser) { 
        this.editUser = editUser; 
    } 

    @Basic 
    @Column(name = "edit_time", columnDefinition = "datetime2(0) NULL") 
    public Timestamp getEditTime() { 
        return editTime; 
    } 

    public void setEditTime(Timestamp editTime) { 
        this.editTime = editTime; 
    } 

} 
