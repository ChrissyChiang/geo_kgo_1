package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class GeoKgoAppointmentContactUserPK implements Serializable {

    private String userId; //人員id
    private String appointmentMainId; //線上預約臨櫃主檔id

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

    /** 多 PK 的 class 須繼承以下 method **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoKgoAppointmentContactUserPK that = (GeoKgoAppointmentContactUserPK) o;
        return Objects.equals(userId, that.userId) && Objects.equals(appointmentMainId, that.appointmentMainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, appointmentMainId);
    }
}
