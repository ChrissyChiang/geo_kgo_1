package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoCasesetRentDate;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GEO 20221019
 * 後台-場地/活動線上預約當日細節
 */
@ApiModel(description = "後台-場地/活動線上預約當日細節")
public class GeokgoRentDateInsertModel implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(notes = "預約當日ID")
    private String rentDateId;

    @ApiModelProperty(notes = "預約主檔id")
    private String caseRentId;

    @ApiModelProperty(notes = "預約日期 yyyy-MM-dd")
    private String detailDate;

    @ApiModelProperty(notes = "當日為星期Ｎ 一二三四五六日-1234560")
    private Integer Week;

    @ApiModelProperty(notes = "當日為 當月第幾週 ")
    private Integer WeekInMonth;

    @ApiModelProperty(notes = "當月 ")
    private Integer month;

    @ApiModelProperty(notes = "最早可預約時間 HH:mm:ss")
    private String earliestTime;

    @ApiModelProperty(notes = "最晚可預約時間 HH:mm:ss")
    private String lastiestTime;

    @ApiModelProperty(notes = "最早可預約天數")
    private Integer earliestDay;

    @ApiModelProperty(notes = "最晚可預約天數")
    private Integer lastiestDay;

    @ApiModelProperty(notes = "是否提供線上預約-是:1", required = true)
    private Integer isEnable;

    @ApiModelProperty(notes = "是否預約全天-是:1", required = true)
    private Integer isAllDay;

    @ApiModelProperty(notes = "預約時段清單")
    private List<GeokgoRentTimeInsertModel> detailTimeList;

    public String getRentDateId() {
        return rentDateId;
    }

    public void setRentDateId(String rentDateId) {
        this.rentDateId = rentDateId;
    }

    public String getCaseRentId() {
        return caseRentId;
    }

    public void setCaseRentId(String caseRentId) {
        this.caseRentId = caseRentId;
    }

    public String getDetailDate() {
        return detailDate;
    }

    public void setDetailDate(String detailDate) {
        this.detailDate = detailDate;
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

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }

    public String getLastiestTime() {
        return lastiestTime;
    }

    public void setLastiestTime(String lastiestTime) {
        this.lastiestTime = lastiestTime;
    }

    public Integer getEarliestDay() {
        return earliestDay;
    }

    public void setEarliestDay(Integer earliestDay) {
        this.earliestDay = earliestDay;
    }

    public Integer getLastiestDay() {
        return lastiestDay;
    }

    public void setLastiestDay(Integer lastiestDay) {
        this.lastiestDay = lastiestDay;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(Integer isAllDay) {
        this.isAllDay = isAllDay;
    }

    public List<GeokgoRentTimeInsertModel> getDetailTimeList() {
        return detailTimeList;
    }

    public void setDetailTimeList(List<GeokgoRentTimeInsertModel> detailTimeList) {
        this.detailTimeList = detailTimeList;
    }

    public static GeokgoRentDateInsertModel changeToModel(GeoKgoCasesetRentDate entity) {
        GeokgoRentDateInsertModel model = new GeokgoRentDateInsertModel();
        model.setRentDateId(entity.getRentDateId());
        model.setCaseRentId(entity.getCaseRentId());
        model.setDetailDate(DateUtil.dateToString(entity.getDetailDate(), DateUtil.PATTEN_YEAR_MONTH_DAY));
        model.setEarliestTime(entity.getEarliestTime());
        model.setLastiestTime(entity.getLastiestTime());
        model.setEarliestDay(entity.getEarliestDay());
        model.setLastiestDay(entity.getLastiestDay());
        model.setIsEnable(entity.getIsEnable());
        model.setWeek(DateUtil.getWeekDay(entity.getDetailDate()));
        model.setWeekInMonth(DateUtil.getWeekInMonth(entity.getDetailDate()));
        model.setMonth(DateUtil.getMonth(entity.getDetailDate()));
        model.setIsAllDay(entity.getIsAllDay());
        return model;
    }

    public static GeoKgoCasesetRentDate changeToEntity(GeokgoRentDateInsertModel model) throws ParseException {
        GeoKgoCasesetRentDate entity = new GeoKgoCasesetRentDate();
        entity.setCaseRentId(model.getCaseRentId());
        entity.setRentDateId(model.getRentDateId());
        entity.setDetailDate(DateUtil.strToTimestamp(model.getDetailDate(),"yyyy-MM-dd"));
        entity.setIsAllDay(model.getIsAllDay());
        entity.setIsEnable(model.getIsEnable());
        entity.setLastiestDay(model.getLastiestDay());
        entity.setEarliestDay(model.getEarliestDay());
        entity.setEarliestTime(model.getEarliestTime());
        entity.setLastiestTime(model.getLastiestTime());
        return entity;
    }

    public static List<GeokgoRentDateInsertModel> changeListToModel(List<GeoKgoCasesetRentDate> entityList) {
        if (entityList != null) {
            return entityList.stream().map(entity -> changeToModel(entity)).collect(Collectors.toList());
        }
        return null;
    }

//    public static GeoKgoAppointmentDetailInsertModel changeToQueryDateModel(GeoKgoAppointmentDetail entity) {
//        GeoKgoAppointmentDetailInsertModel model = new GeoKgoAppointmentDetailInsertModel();
//        model.setAppointmentDetailId(entity.getAppointmentDetailId());
//        model.setAppointmentMainId(entity.getAppointmentMainId());
//        model.setAppointmentDetailDate(DateUtil.dateToString(entity.getAppointmentDetailDate(), DateUtil.PATTEN_YEAR_MONTH_DAY));
//        return model;
//    }
//
//    public static List<GeoKgoAppointmentDetailInsertModel> changeListToQueryDateModel(List<GeoKgoAppointmentDetail> entityList) {
//        List<GeoKgoAppointmentDetailInsertModel> modelList = null;
//        if (entityList != null) {
//            modelList = new ArrayList<>();
//            for (int i = 0; i < entityList.size(); i++) {
//                GeoKgoAppointmentDetailInsertModel model = changeToQueryDateModel(entityList.get(i));
//                modelList.add(model);
//            }
//        }
//        return modelList;
//    }
}
