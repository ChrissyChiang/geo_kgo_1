package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * GEO 20211015 add
 * Model for 單日預約資料
 */
@Component
@ApiModel(description = "單日預約資料")
public class GeoKgoAppointmentInfoQueryByDayModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "線上預約臨櫃預約時段id")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "線上預約臨櫃地點")
    private String location;

    @ApiModelProperty(notes = "預約日期 yyyy-MM-dd")
    private String appointmentDetailDate;

    @ApiModelProperty(notes = "最早可預約時間 yyyy-MM-dd HH:mm")
    private String earliestTime;

    @ApiModelProperty(notes = "最晚可預約時間 yyyy-MM-dd HH:mm")
    private String latestTime;

    @ApiModelProperty(notes = "預約時段起始 hh:mm")
    private String startTime;

    @ApiModelProperty(notes = "預約時段結束 hh:mm")
    private String endTime;

    @ApiModelProperty(notes = "剩餘名額")
    private Integer availableNumbers;

    @ApiModelProperty(notes = "鎖定案件不提供預約")
    private Boolean lock;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    }

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    }

    public String getAppointmentDetailDate() {
        return appointmentDetailDate;
    }

    public void setAppointmentDetailDate(Timestamp appointmentDetailDate) {
        this.appointmentDetailDate = DateUtil.dateToString(appointmentDetailDate, DateUtil.PATTEN_YEAR_MONTH_DAY);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(Timestamp appointmentDate, Integer earliestDay, String earliestTimeStr) {
        if (earliestDay == 0) {
            this.earliestTime = StringUtils.EMPTY;
        }else {
            this.earliestTime = DateUtil.getBeforeDate(appointmentDate, earliestDay, DateUtil.PATTEN_YEAR_MONTH_DAY) + " " + earliestTimeStr;
        }
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Timestamp appointmentDate, Integer earliestDay, String earliestTimeStr) {
        if (earliestDay == 0) {
            this.latestTime = StringUtils.EMPTY;
        } else {
            this.latestTime = DateUtil.getBeforeDate(appointmentDate, -earliestDay, DateUtil.PATTEN_YEAR_MONTH_DAY) + " " + earliestTimeStr;
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = DateUtil.timestampToString(startTime, DateUtil.PATTEN_HOUR_MINUTE);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = DateUtil.timestampToString(endTime, DateUtil.PATTEN_HOUR_MINUTE);
    }

    public Integer getAvailableNumbers() {
        return availableNumbers;
    }

    public void setAvailableNumbers(Integer availableNumbers) {
        this.availableNumbers = availableNumbers;
    }

    public Boolean getLock() {return lock;}

    public void setLock(Boolean lock) {this.lock = lock;}
}
