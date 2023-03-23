package gov.kcg.kgo.geoentity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "GEO_KGO_APPOINTMENT_CHECK")
@NamedQuery(name = "GeoKgoAppointmentCheck.findAll", query = "SELECT k FROM GeoKgoAppointmentCheck k")
public class GeoKgoAppointmentCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GeoKgoAppointmentCheckPK id;

    public GeoKgoAppointmentCheckPK getId() {
        return id;
    }

    public void setId(GeoKgoAppointmentCheckPK id) {
        this.id = id;
    }
}
