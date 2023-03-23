package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class GeoKgoAppointmentColumnChildPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cColumnId; //複合欄位ID
    private String appointmentMainId; //預約主檔ID
    private Integer version; //預約表單版本號
    private String columnId; //欄位ID
    private Integer orderNum; //顯示順序

    @Id
    @Column(name = "CColumnId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL")
    public String getcColumnId() {
        return cColumnId;
    }

    public void setcColumnId(String cColumnId) {
        this.cColumnId = cColumnId;
    }

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

    @Id
    @Column(name = "OrderNum", columnDefinition = "int  NOT NULL")
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer OrderNum) {
        this.orderNum = OrderNum;
    }


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeoKgoAppointmentColumnChildPK)) {
            return false;
        }
        GeoKgoAppointmentColumnChildPK castOther = (GeoKgoAppointmentColumnChildPK) other;
        return this.cColumnId.equals(castOther.cColumnId) && this.appointmentMainId.equals(castOther.appointmentMainId)
                && (this.version == castOther.version) && this.columnId.equals(castOther.columnId)
                && (this.orderNum == castOther.orderNum);
    }

    public int hashCode() {
        final Integer prime = 31;
        int hash = 17;
        hash = hash * prime + this.cColumnId.hashCode();
        hash = hash * prime + this.appointmentMainId.hashCode();
        hash = hash * prime + this.version;
        hash = hash * prime + this.columnId.hashCode();
        hash = hash * prime + this.orderNum;
        return hash;
    }
}
