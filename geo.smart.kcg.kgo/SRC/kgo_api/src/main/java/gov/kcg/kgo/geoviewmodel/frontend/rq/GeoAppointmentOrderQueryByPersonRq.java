package gov.kcg.kgo.geoviewmodel.frontend.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃:搜尋該民眾已登錄預約單 rq
 */

@ApiModel(description = "前台-線上預約臨櫃:搜尋該民眾已登錄預約單 rq")
public class GeoAppointmentOrderQueryByPersonRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約者身分證字號", required = true)
    private String appointmentIdentity;

    @ApiModelProperty(notes = "預約者姓名", required = true)
    private String appointmentName;

    @ApiModelProperty(notes = "預約者電子信箱", required = true)
    private String appointmentEmail;

    @ApiModelProperty(notes = "預約者聯絡電話", required = true)
    private String appointmentPhone;
    
    @ApiModelProperty(notes = "預約類型", required = true)
    private List<String> casesetCategory;    

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

	public List<String> getCasesetCategory() {
		return casesetCategory;
	}

	public void setCasesetCategory(List<String> casesetCategory) {
		this.casesetCategory = casesetCategory;
	}
    
    
}
