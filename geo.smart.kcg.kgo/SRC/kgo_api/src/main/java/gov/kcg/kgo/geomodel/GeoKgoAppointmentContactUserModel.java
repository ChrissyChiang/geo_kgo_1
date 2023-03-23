package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentContactUser;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentMain;
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
public class GeoKgoAppointmentContactUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "使用者帳號")
    private String userId;

    @ApiModelProperty(notes = "使用者名稱")
    private String name;

    @ApiModelProperty(notes = "單位名稱")
    private String unitName;

    @ApiModelProperty(notes = "機關名稱")
    private String organName;

    @ApiModelProperty(notes = "單位id")
    private String unitId;

    @ApiModelProperty(notes = "機關id")
    private String organId;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id 若為空字串表示未綁定該預約服務")
    private String appointmentMainId;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
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

    public static GeoKgoAppointmentContactUserModel changeToModel(GeoKgoAppointmentContactUser entity) {
        GeoKgoAppointmentContactUserModel model = new GeoKgoAppointmentContactUserModel();
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setOrganId(entity.getOrganId());
        model.setUnitId(entity.getUnitId());
        model.setUserId(entity.getUserId());
        model.setName(entity.getUserName());
        return model;
    } //changeToModel

    public static List<GeoKgoAppointmentContactUserModel> changeListToModel(List<GeoKgoAppointmentContactUser> entityList) {
        List<GeoKgoAppointmentContactUserModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentContactUserModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
} 
