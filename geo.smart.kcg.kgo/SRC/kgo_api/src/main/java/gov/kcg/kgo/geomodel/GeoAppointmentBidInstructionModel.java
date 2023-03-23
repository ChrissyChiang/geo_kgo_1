package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 20220811 GEO add
 * 前台-線上預約臨櫃:取得同意說明頁
 */
@Component
@ApiModel(description = "取得同意說明頁")
public class GeoAppointmentBidInstructionModel implements Serializable {

    @ApiModelProperty(value = "線上預約臨櫃名稱")
    private String appointmentName;

    /** 身分驗證設定CheckBox元件集合 **/
    @ApiModelProperty(value = "身分驗證設定CheckBox元件集合")
    private List<CheckBox> identityVerifyCheckBox;

    @ApiModelProperty(value = "啟用同意說明頁")
    private Boolean isServiceHtml;

    @ApiModelProperty(value = "說明頁內容")
    private String serviceHtmlContent;

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public List<CheckBox> getIdentityVerifyCheckBox() {
        return identityVerifyCheckBox;
    }

    public void setIdentityVerifyCheckBox(List<CheckBox> identityVerifyCheckBox) {
        this.identityVerifyCheckBox = identityVerifyCheckBox;
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
}
