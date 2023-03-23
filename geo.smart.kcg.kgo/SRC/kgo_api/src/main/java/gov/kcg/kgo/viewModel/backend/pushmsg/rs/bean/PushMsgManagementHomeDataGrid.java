package gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean;

import io.swagger.annotations.ApiModelProperty;

public class PushMsgManagementHomeDataGrid {

    @ApiModelProperty(value = "案件狀態")
    private String status;

    @ApiModelProperty(value = "案件狀態名稱")
    private String caseStatusName;

    @ApiModelProperty(value = "是否為預設")
    private String isDefault;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}
