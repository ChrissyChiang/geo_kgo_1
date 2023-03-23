package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentContactUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃承辦人
 */
@Component
@ApiModel(description = "線上預約臨櫃承辦人")
public class GeoKgoAppointmentContactUserInsertModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "使用者帳號")
    private String userId;

    @ApiModelProperty(notes = "使用者名稱")
    private String name;

    @ApiModelProperty(notes = "單位id")
    private String unitId;

    @ApiModelProperty(notes = "機關id")
    private String organId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public static GeoKgoAppointmentContactUserInsertModel changeToModel(GeoKgoAppointmentContactUser entity) {
        GeoKgoAppointmentContactUserInsertModel model = new GeoKgoAppointmentContactUserInsertModel();
        model.setOrganId(entity.getOrganId());
        model.setUnitId(entity.getUnitId());
        model.setUserId(entity.getUserId());
        model.setName(entity.getUserName());
        return model;
    } //changeToModel

    public static List<GeoKgoAppointmentContactUserInsertModel> changeListToModel(List<GeoKgoAppointmentContactUser> entityList) {
        List<GeoKgoAppointmentContactUserInsertModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentContactUserInsertModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
} 
