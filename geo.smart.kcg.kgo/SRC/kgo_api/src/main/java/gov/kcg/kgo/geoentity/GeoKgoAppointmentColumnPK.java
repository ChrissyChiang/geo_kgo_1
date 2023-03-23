package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GeoKgoAppointmentColumnPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentMainId; //預約主檔ID
    private Integer version; //預約表單版本號
    private String columnId; //欄位ID

    @Id
    @Column(name = "AppointmentMainId", columnDefinition = "varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String AppointmentMainId) {
        this.appointmentMainId = AppointmentMainId;
    }

    @Id
    @Column(name = "Version", columnDefinition = "int  NOT NULL")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer Version) {
        this.version = Version;
    }

    @Id
    @Column(name = "ColumnId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String ColumnId) {
        this.columnId = ColumnId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoAppointmentColumnPK)) {
            return false;
        }
        GeoKgoAppointmentColumnPK castOther = (GeoKgoAppointmentColumnPK) other;
        return this.appointmentMainId.equals(castOther.appointmentMainId) && this.columnId.equals(castOther.columnId)
                && (this.version == castOther.version);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.appointmentMainId.hashCode();
        hash = hash * prime + this.version;
        hash = hash * prime + this.columnId.hashCode();
        return hash;
    }
}
