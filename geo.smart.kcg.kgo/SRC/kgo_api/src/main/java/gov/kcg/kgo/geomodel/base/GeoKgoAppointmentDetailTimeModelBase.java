package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailTime; 

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃細節-預約時段
 */
@Component
@ApiModel(description = "線上預約臨櫃細節-預約時段")
public class GeoKgoAppointmentDetailTimeModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段id")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "線上預約臨櫃細節id")
    private String appointmentDetailId; 

    @ApiModelProperty(notes = "預約時段起始")
    private Timestamp startTime; 

    @ApiModelProperty(notes = "預約時段結束")
    private Timestamp endTime; 

    @ApiModelProperty(notes = "可預約人數")
    private Integer availableUserQuota; 

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    } 

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    } 

    public String getAppointmentDetailId() { 
        return appointmentDetailId; 
    } 

    public void setAppointmentDetailId(String appointmentDetailId) { 
        this.appointmentDetailId = appointmentDetailId; 
    } 

    public Timestamp getStartTime() { 
        return startTime; 
    } 

    public void setStartTime(Timestamp startTime) { 
        this.startTime = startTime; 
    } 

    public Timestamp getEndTime() { 
        return endTime; 
    } 

    public void setEndTime(Timestamp endTime) { 
        this.endTime = endTime; 
    } 

    public Integer getAvailableUserQuota() { 
        return availableUserQuota; 
    } 

    public void setAvailableUserQuota(Integer availableUserQuota) { 
        this.availableUserQuota = availableUserQuota; 
    } 

    public static GeoKgoAppointmentDetailTimeModelBase changeToModel(GeoKgoAppointmentDetailTime entity) { 
        GeoKgoAppointmentDetailTimeModelBase model = new GeoKgoAppointmentDetailTimeModelBase(); 
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setAppointmentDetailId(entity.getAppointmentDetailId()); 
        model.setStartTime(entity.getStartTime()); 
        model.setEndTime(entity.getEndTime()); 
        model.setAvailableUserQuota(entity.getAvailableUserQuota());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentDetailTimeModelBase> changeListToModel(List<GeoKgoAppointmentDetailTime> entityList) { 
        List<GeoKgoAppointmentDetailTimeModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentDetailTimeModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
