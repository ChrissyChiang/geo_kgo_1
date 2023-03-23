package gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean;

import io.swagger.annotations.ApiModelProperty;

public class PushMsgCaseSetEditDataGrid {

    @ApiModelProperty(value = "訊息維護管理Seq")
    private Integer statusMsgSeq;

    @ApiModelProperty(value = "是否開啟")
    private String isEnable;

    public Integer getStatusMsgSeq() {
        return statusMsgSeq;
    }

    public void setStatusMsgSeq(Integer statusMsgSeq) {
        this.statusMsgSeq = statusMsgSeq;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

}
