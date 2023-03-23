package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentMain;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211015 add
 * Model for 線上預約臨櫃主檔
 */
@Component
@ApiModel(description = "線上預約臨櫃主檔")
public class GeoKgoAppointmentMainModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "機關id", required = true, position = 1)
    private String organId;

    @ApiModelProperty(notes = "科室id", required = true, position = 2)
    private String unitId;

    @ApiModelProperty(notes = "線上預約臨櫃名稱", required = true, position = 3)
    private String appointmentName;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id, 新增不填，編輯必填", position = 4)
    private String appointmentMainId;

    @ApiModelProperty(notes = "啟用狀態（on off delete），新增時預設為off，編輯必填", position = 5)
    private String appointmentStatus;

    @ApiModelProperty(notes = "myData連結網址", position = 6)
    private String myDataUrl;

    @ApiModelProperty(notes = "編輯單位，不填", position = 7)
    private String editOrgan;

    @ApiModelProperty(notes = "編輯人員，不填", position = 7)
    private String editUser;

    @ApiModelProperty(notes = "編輯時間，不填", position = 7)
    private String editTime;

    /**
     * 20220811 GEO add 身分驗證
     */
    @ApiModelProperty(value = "身份驗證設定(用逗號區隔)")
    private String checkType;

    @ApiModelProperty(value = "啟用同意說明頁")
    private Boolean isServiceHtml;

    @ApiModelProperty(value = "說明頁內容")
    private String serviceHtmlContent;

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

    public String getMyDataUrl() {
        return myDataUrl;
    }

    public void setMyDataUrl(String myDataUrl) {
        this.myDataUrl = myDataUrl;
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

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckType() {
        return checkType;
    }

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

    public static GeoKgoAppointmentMainModel changeToModel(GeoKgoAppointmentMain entity) {
        GeoKgoAppointmentMainModel model = new GeoKgoAppointmentMainModel();
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setOrganId(entity.getOrganId());
        model.setUnitId(entity.getUnitId());
        model.setAppointmentName(entity.getAppointmentName());
        model.setAppointmentStatus(entity.getAppointmentStatus());
        model.setMyDataUrl(entity.getMydataUrl());
        model.setEditOrgan(entity.getEditOrgan());
        model.setEditUser(entity.getEditUser());
        model.setEditTime(DateUtil.dateToString(entity.getEditTime(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH_TIME));
        model.setServiceHtml(entity.getServiceHtml());
        model.setServiceHtmlContent(entity.getServiceHtmlContent());
        return model;
    } //changeToModel 

    public static List<GeoKgoAppointmentMainModel> changeListToModel(List<GeoKgoAppointmentMain> entityList) {
        List<GeoKgoAppointmentMainModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoAppointmentMainModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null) 
        return modelList;
    } //changeListToModel 
} 
