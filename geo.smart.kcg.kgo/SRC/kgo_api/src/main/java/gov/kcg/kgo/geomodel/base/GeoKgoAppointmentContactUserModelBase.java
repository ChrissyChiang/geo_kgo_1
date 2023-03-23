package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentContactUser; 

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃承辦人
 */
@Component
@ApiModel(description = "線上預約臨櫃承辦人")
public class GeoKgoAppointmentContactUserModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "人員id")
    private String userId; 

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId; 

    @ApiModelProperty(notes = "機關id")
    private String organId; 

    @ApiModelProperty(notes = "科室id")
    private String unitId; 

    @ApiModelProperty(notes = "承辦人姓名")
    private String userName; 

    @ApiModelProperty(notes = "編輯單位")
    private String editOrgan; 

    @ApiModelProperty(notes = "編輯人員")
    private String editUser; 

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime; 

    public String getUserId() { 
        return userId; 
    } 

    public void setUserId(String userId) { 
        this.userId = userId; 
    } 

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

    public String getUserName() { 
        return userName; 
    } 

    public void setUserName(String userName) { 
        this.userName = userName; 
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

    public static GeoKgoAppointmentContactUserModelBase changeToModel(GeoKgoAppointmentContactUser entity) { 
        GeoKgoAppointmentContactUserModelBase model = new GeoKgoAppointmentContactUserModelBase(); 
        model.setUserId(entity.getUserId()); 
        model.setAppointmentMainId(entity.getAppointmentMainId()); 
        model.setOrganId(entity.getOrganId()); 
        model.setUnitId(entity.getUnitId()); 
        model.setUserName(entity.getUserName()); 
        model.setEditOrgan(entity.getEditOrgan()); 
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentContactUserModelBase> changeListToModel(List<GeoKgoAppointmentContactUser> entityList) { 
        List<GeoKgoAppointmentContactUserModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentContactUserModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
