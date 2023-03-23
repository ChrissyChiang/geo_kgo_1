package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211015 add
 * 線上預約臨櫃-預約主檔
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT", schema = "dbo")
public class GeoKgoAppointment implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String appointmentId; //預約單id 
    private String appointmentMainId; //線上預約臨櫃主檔id 
    private String appointmentDetailTimeId; //線上預約臨櫃細節-預約時段id 
    private String appointmentDetailNumbersId; //線上預約臨櫃細節-號碼牌id 
    private String appointmentIdentity; //預約者身分證字號 
    private String appointmentName; //預約者姓名 
    private String appointmentEmail; //預約者電子信箱 
    private String appointmentPhone; //預約者聯絡電話 
    private Integer isAvailable; //是否有效(GeoBooleanType)，取消無效 
    private Timestamp editTime; //編輯時間 
    private Integer isOnline; //是否線上預約(GeoBooleanType)
    @Id 
    @Column(name = "appointment_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getAppointmentId() { 
        return appointmentId; 
    } 

    public void setAppointmentId(String appointmentId) { 
        this.appointmentId = appointmentId; 
    } 

    @Basic 
    @Column(name = "appointment_main_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getAppointmentMainId() { 
        return appointmentMainId; 
    } 

    public void setAppointmentMainId(String appointmentMainId) { 
        this.appointmentMainId = appointmentMainId; 
    } 

    @Basic 
    @Column(name = "appointment_detail_time_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getAppointmentDetailTimeId() { 
        return appointmentDetailTimeId; 
    } 

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) { 
        this.appointmentDetailTimeId = appointmentDetailTimeId; 
    } 

    @Basic 
    @Column(name = "appointment_detail_numbers_id", columnDefinition = "varchar(50) NULL") 
    public String getAppointmentDetailNumbersId() { 
        return appointmentDetailNumbersId; 
    } 

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) { 
        this.appointmentDetailNumbersId = appointmentDetailNumbersId; 
    } 

    @Basic 
    @Column(name = "appointment_identity", columnDefinition = "varchar(50) NULL") 
    public String getAppointmentIdentity() { 
        return appointmentIdentity; 
    } 

    public void setAppointmentIdentity(String appointmentIdentity) { 
        this.appointmentIdentity = appointmentIdentity; 
    } 

    @Basic 
    @Column(name = "appointment_name", columnDefinition = "nvarchar(100) NULL") 
    public String getAppointmentName() { 
        return appointmentName; 
    } 

    public void setAppointmentName(String appointmentName) { 
        this.appointmentName = appointmentName; 
    } 

    @Basic 
    @Column(name = "appointment_email", columnDefinition = "varchar(100) NULL") 
    public String getAppointmentEmail() { 
        return appointmentEmail; 
    } 

    public void setAppointmentEmail(String appointmentEmail) { 
        this.appointmentEmail = appointmentEmail; 
    } 

    @Basic 
    @Column(name = "appointment_phone", columnDefinition = "varchar(50) NULL") 
    public String getAppointmentPhone() { 
        return appointmentPhone; 
    } 

    public void setAppointmentPhone(String appointmentPhone) { 
        this.appointmentPhone = appointmentPhone; 
    } 

    @Basic 
    @Column(name = "is_available", columnDefinition = "int") 
    public Integer getIsAvailable() { 
        return isAvailable; 
    } 

    public void setIsAvailable(Integer isAvailable) { 
        this.isAvailable = isAvailable; 
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
    @Column(name = "is_online", columnDefinition = "int NULL") 
    public Integer getIsOnline() { 
        return isOnline; 
    } 

    public void setIsOnline(Integer isOnline) { 
        this.isOnline = isOnline; 
    }
} 
