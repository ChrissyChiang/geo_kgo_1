package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/** 
 * GEO 20211015 add
 * Model for 該預約單當日之後的預約名額與資訊
 */
@Component
@ApiModel(description = "該預約單當日之後的預約名額與資訊")
public class GeoKgoAppointmentNumbersSearchModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId;

    @ApiModelProperty(notes = "線上預約臨櫃細節id")
    private String appointmentDetailId;

    @ApiModelProperty(notes = "預約日期 yyyy-MM-dd")
    private String appointmentDetailDate;

    @ApiModelProperty(notes = "剩餘名額")
    private Integer availableNumbers;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getAppointmentDetailId() {
        return appointmentDetailId;
    }

    public void setAppointmentDetailId(String appointmentDetailId) {
        this.appointmentDetailId = appointmentDetailId;
    }

    public String getAppointmentDetailDate() {
        return appointmentDetailDate;
    }

    public void setAppointmentDetailDate(String appointmentDetailDate) {
        this.appointmentDetailDate = appointmentDetailDate;
    }

    public Integer getAvailableNumbers() {
        return availableNumbers;
    }

    public void setAvailableNumbers(Integer availableNumbers) {
        this.availableNumbers = availableNumbers;
    }
}
