package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentMain;
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
 * Model for 線上預約臨櫃主檔 + 承辦人清單
 */
@Component
@ApiModel(description = "線上預約臨櫃主檔 + 承辦人清單")
public class GeoKgoAppointmentMainQueryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "機關id")
    private String organId;

    @ApiModelProperty(notes = "科室id")
    private String unitId;

    @ApiModelProperty(notes = "機關名稱")
    private String organName;

    @ApiModelProperty(notes = "科室名稱")
    private String unitName;

    @ApiModelProperty(notes = "線上預約臨櫃名稱")
    private String appointmentName;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "啟用狀態（on off delete）")
    private String appointmentStatus;

    @ApiModelProperty(notes = "myData連結網址")
    private String mydataUrl;

    @ApiModelProperty(notes = "編輯時間")
    private String editTime;

    @ApiModelProperty(notes = "是否允許刪除")
    private Boolean allowDelete;

    @ApiModelProperty(notes = "線上預約臨櫃承辦人清單", hidden = true)
    private List<GeoKgoAppointmentContactUserModel> contactUserList;

    /** 20220811 GEO add */
    @ApiModelProperty(notes = "啟用說明頁 (on,true)")
    private Boolean isServiceHtml ;

    /** 20220811 GEO add */
    @ApiModelProperty(notes = "說明頁,預設空字串")
    private String serviceHtmlContent ;

    public Boolean getServiceHtml() {
        return isServiceHtml;
    }

    public void setServiceHtml(Boolean serviceHtml) {
        isServiceHtml = serviceHtml;
    }

    public String getServiceHtmlContent() {
        return serviceHtmlContent;
    }

    public void setServiceHtmlContent(String serviceHtmlContent) {
        this.serviceHtmlContent = serviceHtmlContent;
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

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = DateUtil.dateToString(editTime, DateUtil.PATTEN_FULL_TIME_MS);
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Boolean getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public List<GeoKgoAppointmentContactUserModel> getContactUserList() {
        return contactUserList;
    }

    public void setContactUserList(List<GeoKgoAppointmentContactUserModel> contactUserList) {
        this.contactUserList = contactUserList;
    }

    public static GeoKgoAppointmentMainQueryModel changeToModel(GeoKgoAppointmentMain entity) {
        GeoKgoAppointmentMainQueryModel model = new GeoKgoAppointmentMainQueryModel();
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setOrganId(entity.getOrganId());
        model.setUnitId(entity.getUnitId());
        model.setAppointmentName(entity.getAppointmentName());
        model.setAppointmentStatus(entity.getAppointmentStatus());
        model.setMydataUrl(entity.getMydataUrl());
        model.setEditTime(entity.getEditTime());
        return model;
    } //changeToModel 

    public static List<GeoKgoAppointmentMainQueryModel> changeListToModel(List<GeoKgoAppointmentMain> entityList) {
        List<GeoKgoAppointmentMainQueryModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentMainQueryModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel 
} 
