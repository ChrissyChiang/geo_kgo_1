package gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean;

import io.swagger.annotations.ApiModelProperty;

public class PushMsgCaseSetQueryDataGrid {

    @ApiModelProperty(value = "訊息維護管理Seq")
    private Integer StatusMsgSeq;

    @ApiModelProperty(value = "案件狀態名稱")
    private String caseStatusName;

    @ApiModelProperty(value = "是否開啟")
    private String isEnable;

    public Integer getStatusMsgSeq() {
        return StatusMsgSeq;
    }

    public void setStatusMsgSeq(Integer statusMsgSeq) {
        StatusMsgSeq = statusMsgSeq;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

}
