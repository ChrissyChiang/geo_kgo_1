package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211015 add
 * 線上預約臨櫃細節-預約時段
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_DETAIL_TIME", schema = "dbo")
public class GeoKgoAppointmentDetailTime implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String appointmentDetailTimeId; //線上預約臨櫃細節-預約時段id
    private String appointmentDetailId; //線上預約臨櫃細節id 
    private Timestamp startTime; //預約時段起始 
    private Timestamp endTime; //預約時段結束 
    private Integer availableUserQuota; //可預約人數 

    @Id 
    @Column(name = "appointment_detail_time_id", columnDefinition = "varchar(50) DEFAULT 1 NOT NULL")
    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    } 

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    } 

    @Basic 
    @Column(name = "appointment_detail_id", columnDefinition = "varchar(50) NOT NULL") 
    public String getAppointmentDetailId() { 
        return appointmentDetailId; 
    } 

    public void setAppointmentDetailId(String appointmentDetailId) { 
        this.appointmentDetailId = appointmentDetailId; 
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
    @Column(name = "available_user_quota", columnDefinition = "int NULL") 
    public Integer getAvailableUserQuota() { 
        return availableUserQuota; 
    } 

    public void setAvailableUserQuota(Integer availableUserQuota) { 
        this.availableUserQuota = availableUserQuota; 
    } 

} 
