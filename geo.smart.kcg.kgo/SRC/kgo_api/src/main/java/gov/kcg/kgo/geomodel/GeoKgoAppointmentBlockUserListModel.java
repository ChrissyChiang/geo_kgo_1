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
 * GEO 20211104 add
 * Model for 線上預約臨櫃名稱黑名單
 */
@Component
@ApiModel(description = "線上預約臨櫃名稱黑名單")
public class GeoKgoAppointmentBlockUserListModel implements Serializable {

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

    public static GeoKgoAppointmentBlockUserListModel changeToModel(GeoKgoAppointmentBlockUser entity) {
        GeoKgoAppointmentBlockUserListModel model = new GeoKgoAppointmentBlockUserListModel();
        model.setBlockUserId(entity.getBlockUserId()); 
        model.setAppointmentMainId(entity.getAppointmentMainId()); 
        model.setBlockUseName(entity.getBlockUseName()); 
        model.setBlockUserIdentity(entity.getBlockUserIdentity());
        model.setBlockStartTime(entity.getBlockStartTime()); 
        model.setBlockEndTime(entity.getBlockEndTime());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentBlockUserListModel> changeListToModel(List<GeoKgoAppointmentBlockUser> entityList) {
        List<GeoKgoAppointmentBlockUserListModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentBlockUserListModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
