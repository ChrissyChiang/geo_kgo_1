package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * GEO 20211115 add
 * 線上預約臨櫃 ExcelVo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoAppointmentExcelVo {
    // 項目
    private Integer order;

    /** 號碼牌 */
    private String numberName;

    /** 預約日期 */
    private String applyDate;

    /** 時段 */
    private String applyTime;

    /** 類型 */
    private String status;

    /** 姓名 */
    private String appointmentName;

    /** 身分證字號 */
    private String appointmentIdentity;

    /** 電子信箱 */
    private String appointmentEmail;

    /** 聯絡電話 */
    private String appointmentPhone;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getNumberName() {
        return numberName;
    }

    public void setNumberName(String numberName) {
        this.numberName = numberName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentIdentity() {
        return appointmentIdentity;
    }

    public void setAppointmentIdentity(String appointmentIdentity) {
        this.appointmentIdentity = appointmentIdentity;
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
}
