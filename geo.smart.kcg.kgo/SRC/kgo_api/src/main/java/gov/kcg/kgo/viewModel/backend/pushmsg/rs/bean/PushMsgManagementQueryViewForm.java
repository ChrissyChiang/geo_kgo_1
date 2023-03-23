package gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 推播訊息-初始畫面
 */
@ApiModel(description = "推播訊息管理-初始畫面")
public class PushMsgManagementQueryViewForm extends BaseViewForm {

    @ApiModelProperty(value = "使用者ID")
    private String userId;

    @ApiModelProperty(value = "受理機關")
    private String organId;

    @ApiModelProperty(value = "案件狀態")
    private String status;

    @ApiModelProperty(value = "案件狀態名稱")
    private String caseStatusName;

    @ApiModelProperty(value = "是否發送推播")
    private String isEnable;

    @ApiModelProperty(value = "是否為預設")
    private String isDefault;

    @ApiModelProperty(value = "發送主旨")
    private String msgTitle;

    @ApiModelProperty(value = "推播文字")
    private String msgBody;

    @ApiModelProperty(value = "推播詳細內容")
    private String clickDetail;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

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

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getClickDetail() {
        return clickDetail;
    }

    public void setClickDetail(String clickDetail) {
        this.clickDetail = clickDetail;
    }

}
