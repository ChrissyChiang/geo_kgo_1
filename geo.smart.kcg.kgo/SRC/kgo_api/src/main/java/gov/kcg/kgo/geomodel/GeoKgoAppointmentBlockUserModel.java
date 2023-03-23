package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentBlockUser;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃名稱黑名單
 */
@Component
@ApiModel(description = "線上預約臨櫃名稱黑名單")
public class GeoKgoAppointmentBlockUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "黑名單使用者id")
    private String blockUserId;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "黑名單使用者姓名")
    private String blockUseName;

    @ApiModelProperty(notes = "黑名單使用者身分證字號")
    private String blockUserIdentity;

    @ApiModelProperty(notes = "黑名單鎖定起始時間")
    private String blockStartTime;

    @ApiModelProperty(notes = "黑名單鎖定結束時間")
    private String blockEndTime;

    @ApiModelProperty(notes = "是否啟用封鎖(GeoBooleanType)")
    private Integer isTriggerBlock;

    @ApiModelProperty(notes = "編輯單位")
    private String editOrgan;

    @ApiModelProperty(notes = "編輯人員")
    private String editUser;

    @ApiModelProperty(notes = "編輯時間")
    private String editTime;

    public String getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(String blockUserId) {
        this.blockUserId = blockUserId;
    }

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getBlockUseName() {
        return blockUseName;
    }

    public void setBlockUseName(String blockUseName) {
        this.blockUseName = blockUseName;
    }

    public String getBlockUserIdentity() {
        return blockUserIdentity;
    }

    public void setBlockUserIdentity(String blockUserIdentity) {
        this.blockUserIdentity = blockUserIdentity;
    } 

    public Integer getIsTriggerBlock() {
        return isTriggerBlock; 
    } 

    public void setIsTriggerBlock(Integer isTriggerBlock) {
        this.isTriggerBlock = isTriggerBlock; 
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

    public String getBlockStartTime() {
        return blockStartTime;
    }

    public void setBlockStartTime(Timestamp blockStartTime) {
        this.blockStartTime = DateUtil.dateToString(blockStartTime, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
    }

    public String getBlockEndTime() {
        return blockEndTime;
    }

    public void setBlockEndTime(Timestamp blockEndTime) {
        this.blockEndTime = DateUtil.dateToString(blockEndTime, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = DateUtil.dateToString(editTime, DateUtil.PATTEN_FULL_TIME_SLASH);
    }

    public static GeoKgoAppointmentBlockUserModel changeToModel(GeoKgoAppointmentBlockUser entity) {
        GeoKgoAppointmentBlockUserModel model = new GeoKgoAppointmentBlockUserModel();
        model.setBlockUserId(entity.getBlockUserId()); 
        model.setAppointmentMainId(entity.getAppointmentMainId()); 
        model.setBlockUseName(entity.getBlockUseName()); 
        model.setBlockUserIdentity(entity.getBlockUserIdentity());
        model.setBlockStartTime(entity.getBlockStartTime()); 
        model.setBlockEndTime(entity.getBlockEndTime()); 
        model.setIsTriggerBlock(entity.getIsTriggerBlock()); 
        model.setEditOrgan(entity.getEditOrgan()); 
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentBlockUserModel> changeListToModel(List<GeoKgoAppointmentBlockUser> entityList) {
        List<GeoKgoAppointmentBlockUserModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentBlockUserModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
