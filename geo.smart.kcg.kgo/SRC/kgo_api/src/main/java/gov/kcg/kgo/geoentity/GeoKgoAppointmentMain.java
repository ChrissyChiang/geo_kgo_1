package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211015 add
 * 線上預約臨櫃主檔
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_MAIN", schema = "dbo")
public class GeoKgoAppointmentMain implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentMainId; //線上預約臨櫃主檔id
    private String organId; //機關id 
    private String unitId; //科室id 
    private String appointmentName; //線上預約臨櫃名稱 
    private String appointmentStatus; //啟用狀態（on off delete） 
    private String mydataUrl; //myData連結網址
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間
    private Boolean isServiceHtml; //是否開啟同意說明
    private String serviceHtmlContent; //說明頁內容

    @Id
    @Column(name = "appointment_main_id", columnDefinition = "varchar(50) NOT NULL")
    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    @Basic
    @Column(name = "organ_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getOrganId() { 
        return organId; 
    } 

    public void setOrganId(String organId) { 
        this.organId = organId; 
    } 

    @Basic 
    @Column(name = "unit_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getUnitId() { 
        return unitId; 
    } 

    public void setUnitId(String unitId) { 
        this.unitId = unitId; 
    } 

    @Basic 
    @Column(name = "appointment_name", columnDefinition = "nvarchar(100)") 
    public String getAppointmentName() { 
        return appointmentName; 
    } 

    public void setAppointmentName(String appointmentName) { 
        this.appointmentName = appointmentName; 
    } 

    @Basic 
    @Column(name = "appointment_status", columnDefinition = "varchar(30)") 
    public String getAppointmentStatus() { 
        return appointmentStatus; 
    } 

    public void setAppointmentStatus(String appointmentStatus) { 
        this.appointmentStatus = appointmentStatus; 
    } 

    @Basic 
    @Column(name = "mydata_url", columnDefinition = "varchar(500)")
    public String getMydataUrl() {
        return mydataUrl;
    } 

    public void setMydataUrl(String madataUrl) {
        this.mydataUrl = madataUrl;
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

    @Basic
    @Column(name = "is_service_html", columnDefinition = "int ")
    public Boolean getServiceHtml() { return isServiceHtml;}

    public void setServiceHtml(Boolean serviceHtml) {isServiceHtml = serviceHtml;}

    @Basic
    @Column(name = "service_html", columnDefinition = "nvarchar")
    public String getServiceHtmlContent() {return serviceHtmlContent;}

    public void setServiceHtmlContent(String serviceHtmlContent) {this.serviceHtmlContent = serviceHtmlContent;}


}
