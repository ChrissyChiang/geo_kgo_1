package gov.kcg.kgo.geoentity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "GEO_KGO_CASESET_RENT_TIME")
public class GeoKgoCasesetRentTime implements Serializable {

    private String rentTimeId;
    private String rentDateId;
    private Integer unitPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer availableUserQouta;
    private Integer usedUsersNum;
    private Integer isLocked;
    private Integer isAbandon;
    private Timestamp editTime;
    private String editUser;

    @Id
    @Column(name = "rent_time_id", columnDefinition = "varchar(50) NOT NULL")
    public String getRentTimeId() {
        return rentTimeId;
    }
    public void setRentTimeId(String rentTimeId) {
        this.rentTimeId = rentTimeId;
    }

    @Column(name = "rent_date_id", columnDefinition = "varchar(50) NOT NULL")
    public String getRentDateId() {
        return rentDateId;
    }
    public void setRentDateId(String rentDateId) {
        this.rentDateId = rentDateId;
    }

    @Column(name = "unit_price", columnDefinition = "int  NULL")
    public Integer getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "start_time", columnDefinition = "datetime2(0)  NULL")
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", columnDefinition = "datetime2(0)  NULL")
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "available_user_qouta", columnDefinition = "int NULL")
    public Integer getAvailableUserQouta() {
        return availableUserQouta;
    }
    public void setAvailableUserQouta(Integer availableUserQouta) {
        this.availableUserQouta = availableUserQouta;
    }

    @Column(name = "used_users_num", columnDefinition = "int NULL")
    public Integer getUsedUsersNum() {
        return usedUsersNum;
    }
    public void setUsedUsersNum(Integer usedUser) {
        this.usedUsersNum = usedUser;
    }

    @Column(name = "is_locked", columnDefinition = "int NULL")
    public Integer getIsLocked() {return isLocked;}
    public void setIsLocked(Integer isLocked) {this.isLocked = isLocked;}

    @Column(name = "edit_time", columnDefinition = "datetime2(0) NOT NULL")
    public Timestamp getEditTime() {return editTime; }
    public void setEditTime(Timestamp editTime) {this.editTime = editTime;}

    @Column(name = "edit_user", columnDefinition = "varchar(50) NOT NULL")
    public String getEditUser() { return editUser; }
    public void setEditUser(String editUser) {this.editUser = editUser; }

    @Column(name = "is_abandon", columnDefinition = "int NULL")
    public Integer getIsAbandon() {return isAbandon;}
    public void setIsAbandon(Integer isAbandon) {this.isAbandon = isAbandon;}
}
