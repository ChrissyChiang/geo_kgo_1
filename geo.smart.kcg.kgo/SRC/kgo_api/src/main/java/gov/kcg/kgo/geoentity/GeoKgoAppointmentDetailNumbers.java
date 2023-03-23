package gov.kcg.kgo.geoentity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * GEO 20211015 add
 * 線上預約臨櫃細節-號碼牌
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_DETAIL_NUMBERS", schema = "dbo")
public class GeoKgoAppointmentDetailNumbers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentDetailNumbersId; //線上預約臨櫃細節-號碼牌id 
    private String appointmentDetailTimeId; //線上預約臨櫃細節-預約時段id 
    private String numberName; //號碼牌名稱 
    private Integer isUsed; //是否已使用(GeoBooleanType)

    @Id
    @Column(name = "appointment_detail_numbers_id", columnDefinition = "varchar(50) NOT NULL")
    public String getAppointmentDetailNumbersId() {
        return appointmentDetailNumbersId;
    }

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) {
        this.appointmentDetailNumbersId = appointmentDetailNumbersId;
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
    @Column(name = "number_name", columnDefinition = "nvarchar(100) NULL")
    public String getNumberName() {
        return numberName;
    }

    public void setNumberName(String numberName) {
        this.numberName = numberName;
    }

    @Basic
    @Column(name = "is_used", columnDefinition = "int NULL")
    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

} 
