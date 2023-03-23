package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetail;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃細節
 */
@Component
@ApiModel(description = "線上預約臨櫃細節")
public class GeoKgoAppointmentDetailInsertModel implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(notes = "線上預約臨櫃細節id")
    private String appointmentDetailId;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "線上預約臨櫃地點")
    private String location;

    @ApiModelProperty(notes = "預約日期 yyyy-MM-dd")
    private String appointmentDetailDate;

    @ApiModelProperty(notes = "當日為星期Ｎ 一二三四五六日-1234560 不填")
    private Integer Week;

    @ApiModelProperty(notes = "當日為 當月第幾週 不填")
    private Integer WeekInMonth;

    @ApiModelProperty(notes = "當月 不填")
    private Integer month;

    @ApiModelProperty(notes = "最早可預約時間 HH:mm")
    private String earliestTime;

    @ApiModelProperty(notes = "最晚可預約時間 HH:mm")
    private String latestTime;

    @ApiModelProperty(notes = "最早可預約天數")
    private Integer earliestDay;

    @ApiModelProperty(notes = "最晚可預約天數")
    private Integer latestDay;

    @ApiModelProperty(notes = "是否提供線上預約臨櫃服務(GeoBooleanType)", required = true)
    private Integer isEnable;

    @ApiModelProperty(notes = "預約時段清單")
    private List<GeoKgoAppointmentDetailTimeInsertModel> detailTimeList;

    public String getAppointmentDetailId() {
        return appointmentDetailId;
    }

    public void setAppointmentDetailId(String appointmentDetailId) {
        this.appointmentDetailId = appointmentDetailId;
    }

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppointmentDetailDate() {
        return appointmentDetailDate;
    }

    public void setAppointmentDetailDate(String appointmentDetailDate) {
        this.appointmentDetailDate = appointmentDetailDate;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public Integer getEarliestDay() {
        return earliestDay;
    }

    public void setEarliestDay(Integer earliestDay) {
        this.earliestDay = earliestDay;
    }

    public Integer getLatestDay() {
        return latestDay;
    }

    public void setLatestDay(Integer latestDay) {
        this.latestDay = latestDay;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public List<GeoKgoAppointmentDetailTimeInsertModel> getDetailTimeList() {
        return detailTimeList;
    }

    public void setDetailTimeList(List<GeoKgoAppointmentDetailTimeInsertModel> detailTimeList) {
        this.detailTimeList = detailTimeList;
    }

    public Integer getWeek() {
        return Week;
    }

    public void setWeek(Integer week) {
        Week = week;
    }

    public Integer getWeekInMonth() {
        return WeekInMonth;
    }

    public void setWeekInMonth(Integer weekInMonth) {
        WeekInMonth = weekInMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public static GeoKgoAppointmentDetailInsertModel changeToModel(GeoKgoAppointmentDetail entity) {
        GeoKgoAppointmentDetailInsertModel model = new GeoKgoAppointmentDetailInsertModel();
        model.setAppointmentDetailId(entity.getAppointmentDetailId());
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setLocation(entity.getLocation());
        model.setAppointmentDetailDate(DateUtil.dateToString(entity.getAppointmentDetailDate(), DateUtil.PATTEN_YEAR_MONTH_DAY));
        model.setEarliestTime(entity.getEarliestTime());
        model.setLatestTime(entity.getLatestTime());
        model.setEarliestDay(entity.getEarliestDay());
        model.setLatestDay(entity.getLatestDay());
        model.setIsEnable(entity.getIsEnable());
        model.setWeek(DateUtil.getWeekDay(entity.getAppointmentDetailDate()));
        model.setWeekInMonth(DateUtil.getWeekInMonth(entity.getAppointmentDetailDate()));
        model.setMonth(DateUtil.getMonth(entity.getAppointmentDetailDate()));
        return model;
    } //changeToModel

    public static List<GeoKgoAppointmentDetailInsertModel> changeListToModel(List<GeoKgoAppointmentDetail> entityList) {
        List<GeoKgoAppointmentDetailInsertModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentDetailInsertModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel

    public static GeoKgoAppointmentDetailInsertModel changeToQueryDateModel(GeoKgoAppointmentDetail entity) {
        GeoKgoAppointmentDetailInsertModel model = new GeoKgoAppointmentDetailInsertModel();
        model.setAppointmentDetailId(entity.getAppointmentDetailId());
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setAppointmentDetailDate(DateUtil.dateToString(entity.getAppointmentDetailDate(), DateUtil.PATTEN_YEAR_MONTH_DAY));
        return model;
    } //changeToQueryDateModel

    public static List<GeoKgoAppointmentDetailInsertModel> changeListToQueryDateModel(List<GeoKgoAppointmentDetail> entityList) {
        List<GeoKgoAppointmentDetailInsertModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentDetailInsertModel model = changeToQueryDateModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToQueryDateModel
} 
