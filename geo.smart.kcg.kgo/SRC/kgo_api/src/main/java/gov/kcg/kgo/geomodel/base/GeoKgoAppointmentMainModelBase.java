package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentMain; 

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃主檔
 */
@Component
@ApiModel(description = "線上預約臨櫃主檔")
public class GeoKgoAppointmentMainModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "機關id")
    private String organId; 

    @ApiModelProperty(notes = "科室id")
    private String unitId; 

    @ApiModelProperty(notes = "線上預約臨櫃名稱")
    private String appointmentName; 

    @ApiModelProperty(notes = "啟用狀態（on off delete）")
    private String appointmentStatus; 

    @ApiModelProperty(notes = "myData連結網址")
    private String mydataUrl;

    @ApiModelProperty(notes = "編輯單位")
    private String editOrgan; 

    @ApiModelProperty(notes = "編輯人員")
    private String editUser; 

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime; 

    public String getAppointmentMainId() { 
        return appointmentMainId; 
    } 

    public void setAppointmentMainId(String appointmentMainId) { 
        this.appointmentMainId = appointmentMainId; 
    } 

    public String getOrganId() { 
        return organId; 
    } 

    public void setOrganId(String organId) { 
        this.organId = organId; 
    } 

    public String getUnitId() { 
        return unitId; 
    } 

    public void setUnitId(String unitId) { 
        this.unitId = unitId; 
    } 

    public String getAppointmentName() { 
        return appointmentName; 
    } 

    public void setAppointmentName(String appointmentName) { 
        this.appointmentName = appointmentName; 
    } 

    public String getAppointmentStatus() { 
        return appointmentStatus; 
    } 

    public void setAppointmentStatus(String appointmentStatus) { 
        this.appointmentStatus = appointmentStatus; 
    } 

    public String getMydataUrl() {
        return mydataUrl;
    } 

    public void setMydataUrl(String mydataUrl) {
        this.mydataUrl = mydataUrl;
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

    public static GeoKgoAppointmentMainModelBase changeToModel(GeoKgoAppointmentMain entity) {
        GeoKgoAppointmentMainModelBase model = new GeoKgoAppointmentMainModelBase();
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setOrganId(entity.getOrganId()); 
        model.setUnitId(entity.getUnitId()); 
        model.setAppointmentName(entity.getAppointmentName()); 
        model.setAppointmentStatus(entity.getAppointmentStatus()); 
        model.setMydataUrl(entity.getMydataUrl());
        model.setEditOrgan(entity.getEditOrgan()); 
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentMainModelBase> changeListToModel(List<GeoKgoAppointmentMain> entityList) { 
        List<GeoKgoAppointmentMainModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentMainModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
