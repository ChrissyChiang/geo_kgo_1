package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailNumbers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃細節-號碼牌
 */
@Component
@ApiModel(description = "線上預約臨櫃細節-號碼牌")
public class GeoKgoAppointmentDetailNumbersInsertModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌id 新增不填")
    private String appointmentDetailNumbersId;

    @ApiModelProperty(notes = "預約時段id 新增不填")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "號碼牌名稱", required = true)
    private String numberName;

    @ApiModelProperty(notes = "是否可使用 1-可使用，0-不可使用，新增時預設1")
    private Integer isUsed;

    public String getAppointmentDetailNumbersId() {
        return appointmentDetailNumbersId;
    }

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) {
        this.appointmentDetailNumbersId = appointmentDetailNumbersId;
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

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    }

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    }

    public static GeoKgoAppointmentDetailNumbersInsertModel changeToModel(GeoKgoAppointmentDetailNumbers entity) {
        GeoKgoAppointmentDetailNumbersInsertModel model = new GeoKgoAppointmentDetailNumbersInsertModel();
        model.setAppointmentDetailNumbersId(entity.getAppointmentDetailNumbersId());
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setNumberName(entity.getNumberName());
        model.setIsUsed(entity.getIsUsed());
        return model;
    } //changeToModel 

    public static List<GeoKgoAppointmentDetailNumbersInsertModel> changeListToModel(List<GeoKgoAppointmentDetailNumbers> entityList) {
        List<GeoKgoAppointmentDetailNumbersInsertModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentDetailNumbersInsertModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel 
} 
