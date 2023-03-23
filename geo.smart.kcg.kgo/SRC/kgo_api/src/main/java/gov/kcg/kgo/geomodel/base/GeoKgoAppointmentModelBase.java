package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoAppointment; 

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃-預約主檔
 */
@Component
@ApiModel(description = "線上預約臨櫃-預約主檔")
public class GeoKgoAppointmentModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "預約單id")
    private String appointmentId; 

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId; 

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段id")
    private String appointmentDetailTimeId; 

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌id")
    private String appointmentDetailNumbersId; 

    @ApiModelProperty(notes = "預約者身分證字號")
    private String appointmentIdentity; 

    @ApiModelProperty(notes = "預約者姓名")
    private String appointmentName; 

    @ApiModelProperty(notes = "預約者電子信箱")
    private String appointmentEmail; 

    @ApiModelProperty(notes = "預約者聯絡電話")
    private String appointmentPhone; 

    @ApiModelProperty(notes = "是否有效(GeoBooleanType)，取消無效")
    private Integer isAvailable; 

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime; 

    @ApiModelProperty(notes = "是否線上預約(GeoBooleanType)")
    private Integer isOnline; 

    public String getAppointmentId() { 
        return appointmentId; 
    } 

    public void setAppointmentId(String appointmentId) { 
        this.appointmentId = appointmentId; 
    } 

    public String getAppointmentMainId() { 
        return appointmentMainId; 
    } 

    public void setAppointmentMainId(String appointmentMainId) { 
        this.appointmentMainId = appointmentMainId; 
    } 

    public String getAppointmentDetailTimeId() { 
        return appointmentDetailTimeId; 
    } 

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) { 
        this.appointmentDetailTimeId = appointmentDetailTimeId; 
    } 

    public String getAppointmentDetailNumbersId() { 
        return appointmentDetailNumbersId; 
    } 

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) { 
        this.appointmentDetailNumbersId = appointmentDetailNumbersId; 
    } 

    public String getAppointmentIdentity() { 
        return appointmentIdentity; 
    } 

    public void setAppointmentIdentity(String appointmentIdentity) { 
        this.appointmentIdentity = appointmentIdentity; 
    } 

    public String getAppointmentName() { 
        return appointmentName; 
    } 

    public void setAppointmentName(String appointmentName) { 
        this.appointmentName = appointmentName; 
    } 

    public String getAppointmentEmail() { 
        return appointmentEmail; 
    } 

    public void setAppointmentEmail(String appointmentEmail) { 
        this.appointmentEmail = appointmentEmail; 
    } 

    public String getAppointmentPhone() { 
        return appointmentPhone; 
    } 

    public void setAppointmentPhone(String appointmentPhone) { 
        this.appointmentPhone = appointmentPhone; 
    } 

    public Integer getIsAvailable() { 
        return isAvailable; 
    } 

    public void setIsAvailable(Integer isAvailable) { 
        this.isAvailable = isAvailable; 
    } 

    public Timestamp getEditTime() { 
        return editTime; 
    } 

    public void setEditTime(Timestamp editTime) { 
        this.editTime = editTime; 
    } 

    public Integer getIsOnline() { 
        return isOnline; 
    } 

    public void setIsOnline(Integer isOnline) { 
        this.isOnline = isOnline; 
    } 

    public static GeoKgoAppointmentModelBase changeToModel(GeoKgoAppointment entity) { 
        GeoKgoAppointmentModelBase model = new GeoKgoAppointmentModelBase(); 
        model.setAppointmentId(entity.getAppointmentId()); 
        model.setAppointmentMainId(entity.getAppointmentMainId()); 
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId()); 
        model.setAppointmentDetailNumbersId(entity.getAppointmentDetailNumbersId()); 
        model.setAppointmentIdentity(entity.getAppointmentIdentity()); 
        model.setAppointmentName(entity.getAppointmentName()); 
        model.setAppointmentEmail(entity.getAppointmentEmail()); 
        model.setAppointmentPhone(entity.getAppointmentPhone()); 
        model.setIsAvailable(entity.getIsAvailable()); 
        model.setEditTime(entity.getEditTime()); 
        model.setIsOnline(entity.getIsOnline());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentModelBase> changeListToModel(List<GeoKgoAppointment> entityList) { 
        List<GeoKgoAppointmentModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
