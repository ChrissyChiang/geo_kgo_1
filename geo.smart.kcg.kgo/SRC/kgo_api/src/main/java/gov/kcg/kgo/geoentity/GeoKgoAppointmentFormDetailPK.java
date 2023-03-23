package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class GeoKgoAppointmentFormDetailPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentId; //預約單ID
    private String columnId; //欄位ID
    private Integer version; //預約表單版本號
    private String cColumnId; //複合欄位ID

    @Id
    @Column(name = "AppointmentId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String AppointmentId) {
        this.appointmentId = AppointmentId;
    }

    @Id
    @Column(name = "ColumnId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String ColumnId) {
        this.columnId = ColumnId;
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
    @Column(name = "CColumnId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getCColumnId() {
        return cColumnId;
    }

    public void setCColumnId(String CColumnId) {
        this.cColumnId = CColumnId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoAppointmentFormDetailPK)) {
            return false;
        }
        GeoKgoAppointmentFormDetailPK castOther = (GeoKgoAppointmentFormDetailPK) other;
        return this.appointmentId.equals(castOther.appointmentId) && this.columnId.equals(castOther.columnId)
                && (this.version == castOther.version) && this.cColumnId.equals(castOther.cColumnId);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.appointmentId.hashCode();
        hash = hash * prime + this.version;
        hash = hash * prime + this.columnId.hashCode();
        hash = hash * prime + this.cColumnId.hashCode();
        return hash;
    }
}
