package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃-登錄預約主檔
 */
@Component
@ApiModel(description = "線上預約臨櫃-登錄預約主檔")
public class GeoKgoAppointmentOrderEditModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約單id 新增不填，編輯必填")
    private String appointmentId;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段id")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌id")
    private String appointmentDetailNumbersId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌稱")
    private String appointmentDetailNumbersName;

    @ApiModelProperty(notes = "預約者身分證字號")
    private String appointmentIdentity;

    @ApiModelProperty(notes = "預約者姓名")
    private String appointmentName;

    @ApiModelProperty(notes = "預約者電子信箱")
    private String appointmentEmail;

    @ApiModelProperty(notes = "預約者聯絡電話")
    private String appointmentPhone;

    @ApiModelProperty(notes = "是否線上預約 1-是，0-不是")
    private Integer isOnline;

    @ApiModelProperty(notes = "是否有效，1-有效，0-取消無效 新增時預設1")
    private Integer isAvailable;

    /** GEO 20211230 add 預約成功 顯示號碼牌、日期、時間 */
    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段 年")
    private String appointmentYear;

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段 月")
    private String appointmentMonth;

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段 日")
    private String appointmentDay;

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段 時間")
    private String appointmentTime;

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

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getAppointmentDetailNumbersName() {
        return appointmentDetailNumbersName;
    }

    public void setAppointmentDetailNumbersName(String appointmentDetailNumbersName) {
        this.appointmentDetailNumbersName = appointmentDetailNumbersName;
    }

    public String getAppointmentYear() {
        return appointmentYear;
    }

    public void setAppointmentYear(String appointmentYear) {
        this.appointmentYear = appointmentYear;
    }

    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public static GeoKgoAppointmentOrderEditModel changeToModel(GeoKgoAppointment entity) {
        GeoKgoAppointmentOrderEditModel model = new GeoKgoAppointmentOrderEditModel();
        model.setAppointmentId(entity.getAppointmentId());
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setAppointmentDetailNumbersId(entity.getAppointmentDetailNumbersId());
        model.setAppointmentIdentity(entity.getAppointmentIdentity());
        model.setAppointmentName(entity.getAppointmentName());
        model.setAppointmentEmail(entity.getAppointmentEmail());
        model.setAppointmentPhone(entity.getAppointmentPhone());
        model.setIsAvailable(entity.getIsAvailable());
        model.setIsOnline(entity.getIsOnline());
        return model;
    } //changeToModel 

    public static List<GeoKgoAppointmentOrderEditModel> changeListToModel(List<GeoKgoAppointment> entityList) {
        List<GeoKgoAppointmentOrderEditModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentOrderEditModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel 
} 
