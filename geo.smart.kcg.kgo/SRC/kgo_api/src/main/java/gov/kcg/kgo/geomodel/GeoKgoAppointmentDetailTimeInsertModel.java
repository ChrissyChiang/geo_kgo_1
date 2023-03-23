package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailTime;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃細節-預約時段
 */
@Component
@ApiModel(description = "線上預約臨櫃細節-預約時段")
public class GeoKgoAppointmentDetailTimeInsertModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約時段id 新增不填")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "線上預約臨櫃細節id 新增不填")
    private String appointmentDetailId;

    @ApiModelProperty(notes = "預約時段起始 yyyy-MM-dd hh:mm", required = true)
    private String startTime;

    @ApiModelProperty(notes = "預約時段結束 yyyy-MM-dd hh:mm", required = true)
    private String endTime;

    @ApiModelProperty(notes = "可預約人數", required = true)
    private Integer availableUserQuota;

    @ApiModelProperty(notes = "號碼牌清單")
    private List<GeoKgoAppointmentDetailNumbersInsertModel> detailNumbersList;

    @ApiModelProperty(notes = "是否可刪除預約時段")
    private Integer isDeleteDetailTime;

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    }

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {

        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {

        this.endTime = endTime;
    }

    public Integer getAvailableUserQuota() {
        return availableUserQuota;
    }

    public void setAvailableUserQuota(Integer availableUserQuota) {
        this.availableUserQuota = availableUserQuota;
    }

    public List<GeoKgoAppointmentDetailNumbersInsertModel> getDetailNumbersList() {
        return detailNumbersList;
    }

    public void setDetailNumbersList(List<GeoKgoAppointmentDetailNumbersInsertModel> detailNumbersList) {
        this.detailNumbersList = detailNumbersList;
    }

    public String getAppointmentDetailId() {
        return appointmentDetailId;
    }

    public Integer getIsDeleteDetailTime() {
        return isDeleteDetailTime;
    }

    public void setIsDeleteDetailTime(Integer isDeleteDetailTime) {
        this.isDeleteDetailTime = isDeleteDetailTime;
    }

    public void setAppointmentDetailId(String appointmentDetailId) {
        this.appointmentDetailId = appointmentDetailId;
    }

    public static GeoKgoAppointmentDetailTimeInsertModel changeToModel(GeoKgoAppointmentDetailTime entity) {
        GeoKgoAppointmentDetailTimeInsertModel model = new GeoKgoAppointmentDetailTimeInsertModel();
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setAppointmentDetailId(entity.getAppointmentDetailId());
        model.setStartTime(DateUtil.dateToString(entity.getStartTime(), DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
        model.setEndTime(DateUtil.dateToString(entity.getEndTime(), DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
        model.setAvailableUserQuota(entity.getAvailableUserQuota());
        return model;
    } //changeToModel 

    public static List<GeoKgoAppointmentDetailTimeInsertModel> changeListToModel(List<GeoKgoAppointmentDetailTime> entityList) {
        List<GeoKgoAppointmentDetailTimeInsertModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentDetailTimeInsertModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel

    public static GeoKgoAppointmentDetailTimeInsertModel changeToQueryDateModel(GeoKgoAppointmentDetailTime entity) {
        GeoKgoAppointmentDetailTimeInsertModel model = new GeoKgoAppointmentDetailTimeInsertModel();
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setAppointmentDetailId(entity.getAppointmentDetailId());
        model.setStartTime(DateUtil.dateToString(entity.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE));
        model.setEndTime(DateUtil.dateToString(entity.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE));
        return model;
    } //changeToModel

    public static List<GeoKgoAppointmentDetailTimeInsertModel> changeListToQueryDateModel(List<GeoKgoAppointmentDetailTime> entityList) {
        List<GeoKgoAppointmentDetailTimeInsertModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentDetailTimeInsertModel model = changeToQueryDateModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
} 
