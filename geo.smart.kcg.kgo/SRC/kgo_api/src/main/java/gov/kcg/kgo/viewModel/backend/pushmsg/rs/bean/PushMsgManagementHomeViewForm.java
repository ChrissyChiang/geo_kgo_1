package gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 推播訊息-初始畫面
 */
@ApiModel(description = "推播訊息管理-初始畫面")
public class PushMsgManagementHomeViewForm extends BaseViewForm {

    @ApiModelProperty(value = "使用者ID")
    private String userId;

    @ApiModelProperty(value = "受理機關")
    private String organId;

    @ApiModelProperty(value = "訊息清單")
    private List<PushMsgManagementHomeDataGrid> pushMsgManagementHomeDataGridList;

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

    public List<PushMsgManagementHomeDataGrid> getPushMsgManagementHomeDataGridList() {
        return pushMsgManagementHomeDataGridList;
    }

    public void setPushMsgManagementHomeDataGridList(List<PushMsgManagementHomeDataGrid> pushMsgManagementHomeDataGridList) {
        this.pushMsgManagementHomeDataGridList = pushMsgManagementHomeDataGridList;
    }

}
