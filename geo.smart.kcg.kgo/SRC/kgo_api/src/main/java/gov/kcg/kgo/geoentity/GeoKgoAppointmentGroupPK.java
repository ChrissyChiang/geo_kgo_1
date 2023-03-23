package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class GeoKgoAppointmentGroupPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentMainId; //預約主檔ID
    private Integer groupSeq; //群組序號
    private Integer version; //預約表單版本號

    @Id
    @Column(name = "AppointmentMainId", columnDefinition = "varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String AppointmentMainId) {
        this.appointmentMainId = AppointmentMainId;
    }

    @Id
    @Column(name = "GroupSeq", columnDefinition = "int  IDENTITY(1,1) NOT NULL")
    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer GroupSeq) {
        this.groupSeq = GroupSeq;
    }

    @Id
    @Column(name = "Version", columnDefinition = "int  NOT NULL")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer Version) {
        this.version = Version;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoAppointmentGroupPK)) {
            return false;
        }
        GeoKgoAppointmentGroupPK castOther = (GeoKgoAppointmentGroupPK) other;
        return this.appointmentMainId.equals(castOther.appointmentMainId) && (this.groupSeq == castOther.groupSeq)
                && (this.version == castOther.version);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.appointmentMainId.hashCode();
        hash = hash * prime + this.groupSeq;
        hash = hash * prime + this.version;
        return hash;
    }
}
