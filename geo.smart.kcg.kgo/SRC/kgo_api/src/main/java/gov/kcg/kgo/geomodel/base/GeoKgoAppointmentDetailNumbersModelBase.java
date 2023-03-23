package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailNumbers; 

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃細節-號碼牌
 */
@Component
@ApiModel(description = "線上預約臨櫃細節-號碼牌")
public class GeoKgoAppointmentDetailNumbersModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌id")
    private String appointmentDetailNumbersId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段id")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "號碼牌名稱")
    private String numberName; 

    @ApiModelProperty(notes = "是否已使用(GeoBooleanType)")
    private Integer isUsed;

    public String getAppointmentDetailNumbersId() {
        return appointmentDetailNumbersId;
    } 

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) {
        this.appointmentDetailNumbersId = appointmentDetailNumbersId;
    } 

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    } 

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    } 

    public String getNumberName() { 
        return numberName; 
    } 

    public void setNumberName(String numberName) { 
        this.numberName = numberName; 
    } 

    public Integer getIsUsed() { 
        return isUsed; 
    } 

    public void setIsUsed(Integer isUsed) { 
        this.isUsed = isUsed; 
    } 

    public static GeoKgoAppointmentDetailNumbersModelBase changeToModel(GeoKgoAppointmentDetailNumbers entity) { 
        GeoKgoAppointmentDetailNumbersModelBase model = new GeoKgoAppointmentDetailNumbersModelBase(); 
        model.setAppointmentDetailNumbersId(entity.getAppointmentDetailNumbersId());
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setNumberName(entity.getNumberName()); 
        model.setIsUsed(entity.getIsUsed());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentDetailNumbersModelBase> changeListToModel(List<GeoKgoAppointmentDetailNumbers> entityList) { 
        List<GeoKgoAppointmentDetailNumbersModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentDetailNumbersModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
