package gov.kcg.kgo.geoentity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * GEO 20211015 add
 * 線上預約臨櫃細節
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_DETAIL", schema = "dbo")
public class GeoKgoAppointmentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentDetailId; //線上預約臨櫃細節id 
    private String appointmentMainId; //線上預約臨櫃主檔id 
    private String location; //線上預約臨櫃地點 
    private Timestamp appointmentDetailDate; //預約日期 
    private String earliestTime; //最早可預約時間 HH:mm 
    private String latestTime; //最晚可預約時間 HH:mm 
    private Integer earliestDay; //最早可預約天數  
    private Integer latestDay; //最晚可預約天數 
    private Integer isEnable; //是否提供線上預約臨櫃服務(GeoBooleanType) 
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間

    @Id
    @Column(name = "appointment_detail_id", columnDefinition = "varchar(50) NOT NULL")
    public String getAppointmentDetailId() {
        return appointmentDetailId;
    }

    public void setAppointmentDetailId(String appointmentDetailId) {
        this.appointmentDetailId = appointmentDetailId;
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
    @Column(name = "location", columnDefinition = "nvarchar(150) NULL")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "appointment_detail_date", columnDefinition = "datetime2(0) NULL")
    public Timestamp getAppointmentDetailDate() {
        return appointmentDetailDate;
    }

    public void setAppointmentDetailDate(Timestamp appointmentDetailDate) {
        this.appointmentDetailDate = appointmentDetailDate;
    }

    @Basic
    @Column(name = "earliest_time", columnDefinition = "varchar(30) NULL")
    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }

    @Basic
    @Column(name = "latest_time", columnDefinition = "varchar(30) NULL")
    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    @Basic
    @Column(name = "earliest_day", columnDefinition = "int NULL")
    public Integer getEarliestDay() {
        return earliestDay;
    }

    public void setEarliestDay(Integer earliestDay) {
        this.earliestDay = earliestDay;
    }

    @Basic
    @Column(name = "latest_day", columnDefinition = "int NULL")
    public Integer getLatestDay() {
        return latestDay;
    }

    public void setLatestDay(Integer latestDay) {
        this.latestDay = latestDay;
    }

    @Basic
    @Column(name = "is_enable", columnDefinition = "int NULL")
    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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
