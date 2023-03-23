package gov.kcg.kgo.geoentity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "GEO_KGO_CASESET_RENT_DATE")
public class GeoKgoCasesetRentDate {
    private String rentDateId;
    private String caseRentId;
    private Timestamp detailDate;
    private Integer earliestDay;
    private String earliestTime;
    private Integer lastiestDay;
    private String lastiestTime;
    private Integer isEnable;
    private Integer isAllDay;
    private Timestamp editTime;

    @Id
    @Column(name = "rent_date_id", columnDefinition = "Bigint NOT NULL")
    public String getRentDateId() {
        return rentDateId;
    }

    public void setRentDateId(String rentDateId) {
        this.rentDateId = rentDateId;
    }
    @Column(name = "case_rent_id", columnDefinition = "Bigint NOT NULL")
    public String getCaseRentId() {
        return caseRentId;
    }

    public void setCaseRentId(String caseRentId) {
        this.caseRentId = caseRentId;
    }
    @Column(name = "detail_date", columnDefinition = "datetime2(0) NOT NULL")
    public Timestamp getDetailDate() {
        return detailDate;
    }

    public void setDetailDate(Timestamp detailDate) {
        this.detailDate = detailDate;
    }
    @Column(name = "earliest_day", columnDefinition = "int NULL")
    public Integer getEarliestDay() {
        return earliestDay;
    }

    public void setEarliestDay(Integer earliestDay) {
        this.earliestDay = earliestDay;
    }
    @Column(name = "earliest_time", columnDefinition = "varchar(30) NULL")
    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }
    @Column(name = "lastiest_day", columnDefinition = "int NULL")
    public Integer getLastiestDay() {
        return lastiestDay;
    }

    public void setLastiestDay(Integer lastiestDay) {
        this.lastiestDay = lastiestDay;
    }
    @Column(name = "lastiest_time", columnDefinition = "varchar(30) NULL")
    public String getLastiestTime() {
        return lastiestTime;
    }

    public void setLastiestTime(String lastiestTime) {
        this.lastiestTime = lastiestTime;
    }
    @Column(name = "is_enable", columnDefinition = "int NULL")
    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
    @Column(name = "is_all_day", columnDefinition = "int NULL")
    public Integer getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(Integer isAllDay) {
        this.isAllDay = isAllDay;
    }

    @Column(name = "edit_time", columnDefinition = "datetime2(0) NOT NULL")
    public Timestamp getEditTime() {
        return editTime;
    }
    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }



}
