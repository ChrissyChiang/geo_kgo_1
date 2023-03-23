package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GeoKgoAppointmentCheckPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="appointment_main_id",unique = true, nullable = false, length = 30)
    private String appointmentMainId;

    @Column(name="check_type",unique = true, nullable = false, length = 30)
    private String checkType;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
}
