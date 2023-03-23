package gov.kcg.kgo.geomodel.base;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃細節
 */
@Component
@ApiModel(description = "線上預約臨櫃細節")
public class GeoKgoAppointmentDetailModelBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃細節id")
    private String appointmentDetailId;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "線上預約臨櫃地點")
    private String location;

    @ApiModelProperty(notes = "預約日期")
    private Timestamp appointmentDetailDate;

    @ApiModelProperty(notes = "最早可預約時間 HH:mm")
    private String earliestTime;

    @ApiModelProperty(notes = "最晚可預約時間 HH:mm")
    private String latestTime;

    @ApiModelProperty(notes = "最早可預約天數 ")
    private Integer earliestDay;

    @ApiModelProperty(notes = "最晚可預約天數")
    private Integer latestDay;

    @ApiModelProperty(notes = "是否提供線上預約臨櫃服務(GeoBooleanType)")
    private Integer isEnable;

    @ApiModelProperty(notes = "編輯單位")
    private String editOrgan;

    @ApiModelProperty(notes = "編輯人員")
    private String editUser;

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime;

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

    public Timestamp getAppointmentDetailDate() {
        return appointmentDetailDate;
    }

    public void setAppointmentDetailDate(Timestamp appointmentDetailDate) {
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

    public String getEditOrgan() {
        return editOrgan;
    }

    public void setEditOrgan(String editOrgan) {
        this.editOrgan = editOrgan;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public static GeoKgoAppointmentDetailModelBase changeToModel(GeoKgoAppointmentDetail entity) {
        GeoKgoAppointmentDetailModelBase model = new GeoKgoAppointmentDetailModelBase();
        model.setAppointmentDetailId(entity.getAppointmentDetailId());
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setLocation(entity.getLocation());
        model.setAppointmentDetailDate(entity.getAppointmentDetailDate());
        model.setEarliestTime(entity.getEarliestTime());
        model.setLatestTime(entity.getLatestTime());
        model.setEarliestDay(entity.getEarliestDay());
        model.setLatestDay(entity.getLatestDay());
        model.setIsEnable(entity.getIsEnable());
        model.setEditOrgan(entity.getEditOrgan());
        model.setEditUser(entity.getEditUser());
        model.setEditTime(entity.getEditTime());
        return model;
    } //changeToModel 

    public static List<GeoKgoAppointmentDetailModelBase> changeListToModel(List<GeoKgoAppointmentDetail> entityList) {
        List<GeoKgoAppointmentDetailModelBase> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentDetailModelBase model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel 
} 
